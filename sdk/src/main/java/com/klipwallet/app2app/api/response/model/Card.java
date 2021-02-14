package com.klipwallet.app2app.api.response.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.klipwallet.app2app.api.KlipProtocol;

/**
 * Card 정보
 */
public class Card {
    private int createdAt; // required
    private int updatedAt; // required
    private String owner; // required
    private String sender; // required
    private int cardId; // required
    private String cardUri; // required
    private String transactionHash; // required

    /**
     * 카드 생성 시간을 가져온다.
     * @return 카드 생성 시간
     */
    public int getCreatedAt() {
        return createdAt;
    }

    /**
     * 카드 업데이트 시간을 가져온다.
     * @return 카드 업데이트 시간
     */
    public int getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 카드 소유자 EOA를 가져온다.
     * @return 카드 소유자 EOA
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 카드 송신자 EOA를 가져온다.
     * @return 카드 송신자 EOA
     */
    public String getSender() {
        return sender;
    }

    /**
     * 카드 고유번호를 가져온다.
     * @return 카드 고유번호
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * 카드 정보 URI를 가져온다.
     * @return 카드 정보 URI
     */
    public String getCardUri() {
        return cardUri;
    }

    /**
     * (카드 전송 처리에 대한) 트랜잭션 해시를 가져온다.
     * @return 트랜잭션 해시
     */
    public String getTransactionHash() {
        return transactionHash;
    }

    public void fromJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        createdAt = obj.getInt(KlipProtocol.CREATED_AT);
        updatedAt = obj.getInt(KlipProtocol.UPDATED_AT);
        owner = obj.getString(KlipProtocol.OWNER);
        sender = obj.getString(KlipProtocol.SENDER);
        cardId = obj.getInt(KlipProtocol.CARD_ID);
        cardUri = obj.getString(KlipProtocol.CARD_URI);
        transactionHash = obj.getString(KlipProtocol.TRANSACTION_HASH);
    }

    public JSONObject toJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(KlipProtocol.CREATED_AT, createdAt);
            obj.put(KlipProtocol.UPDATED_AT, updatedAt);
            obj.put(KlipProtocol.OWNER, owner);
            obj.put(KlipProtocol.SENDER, sender);
            obj.put(KlipProtocol.CARD_ID, cardId);
            obj.put(KlipProtocol.CARD_URI, cardUri);
            obj.put(KlipProtocol.TRANSACTION_HASH, transactionHash);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided to Klip API. detailed error message: " + e.toString());
        }
    }
}
