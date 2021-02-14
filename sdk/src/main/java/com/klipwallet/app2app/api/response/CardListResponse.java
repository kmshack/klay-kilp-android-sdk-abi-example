package com.klipwallet.app2app.api.response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.api.response.model.Card;
import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.exception.KlipResponseException;
import com.klipwallet.app2app.network.response.ResponseStringConverter;

/**
 * Card 목록
 */
public class CardListResponse {
    private String name;
    private String symbolImg;
    private List<Card> cards;
    private String nextCursor;

    /**
     * 카드 이름을 가져온다.
     * @return 카드 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 카드 심볼 이미지 URL을 가져온다.
     * @return 카드 심볼 이미지 URL
     */
    public String getSymbolImg() {
        return symbolImg;
    }

    /**
     * 카드 목록을 가져온다.
     * @return 카드 목록
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * 추가로 가져올 수 있는 다음 카드 목록을 위한 커서를 가져온다.
     * 이 값은 카드 목록이 100개 이상인 경우, 다음 카드 목록을 가져오기 위해 사용됩니다.
     * @return 다음 카드 목록 커서
     */
    public String getNextCursor() {
        return nextCursor;
    }

    public void fromJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            name = obj.getString(KlipProtocol.NAME);
            symbolImg = obj.getString(KlipProtocol.SYMBOL_IMG);

            if (!obj.isNull(KlipProtocol.CARDS)) {
                cards = new ArrayList<Card>();

                JSONArray array = obj.getJSONArray(KlipProtocol.CARDS);
                for (int i = 0; i < array.length(); i++) {
                    String cardJson = array.getString(i);
                    Card card = new Card();
                    card.fromJson(cardJson);
                    cards.add(card);
                }
            }
            nextCursor = obj.getString(KlipProtocol.NEXT_CURSOR);
        } catch (JSONException e) {
            throw new KlipResponseException(KlipErrorCode.PROTOCOL_ERROR_CODE,
                    "JSON parsing error. detail: " + e.toString(),
                    HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    public static ResponseStringConverter<CardListResponse> getConverter() {
        return new ResponseStringConverter<CardListResponse>() {
            @Override
            public CardListResponse convert(String o) {
                CardListResponse res = new CardListResponse();
                res.fromJson(o);
                return res;
            }
        };
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public JSONObject toJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(KlipProtocol.NAME, name);
            obj.put(KlipProtocol.SYMBOL_IMG, symbolImg);
            if(cards != null) {
                JSONArray array = new JSONArray();
                for(int i=0; i<cards.size(); i++) {
                    array.put(cards.get(i).toJson());
                }
                obj.put(KlipProtocol.CARDS, array);
            }
            obj.put(KlipProtocol.NEXT_CURSOR, nextCursor);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided to Klip API. detailed error message: " + e.toString());
        }
    }
}
