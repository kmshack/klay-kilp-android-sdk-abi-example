package com.klipwallet.app2app.api.response.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.klipwallet.app2app.api.KlipProtocol;

/**
 * Klip 에러 정보
 */
public class KlipError {
    private int code; // required
    private String message; // required

    /**
     * 에러 코드를 가져온다.
     * @return 에러 코드
     */
    public int getCode() {
        return code;
    }

    /**
     * 에러 메시지를 가져온다.
     * @return 에러 메시지
     */
    public String getMessage() {
        return message;
    }

    public void fromJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        code = obj.getInt(KlipProtocol.CODE);
        message = obj.getString(KlipProtocol.MESSAGE);
    }

    public JSONObject toJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(KlipProtocol.CODE, code);
            obj.put(KlipProtocol.MESSAGE, message);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided to Klip API. detailed error message: " + e.toString());
        }
    }
}
