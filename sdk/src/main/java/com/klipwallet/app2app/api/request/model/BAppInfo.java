package com.klipwallet.app2app.api.request.model;

import org.json.JSONException;
import org.json.JSONObject;

import static com.klipwallet.app2app.api.KlipProtocol.BAPP_CALLBACK;
import static com.klipwallet.app2app.api.KlipProtocol.BAPP_NAME;

/**
 * 요청 BApp 정보
 */
public class BAppInfo {
    private String name; // required
    private BAppDeepLinkCB callback;

    /**
     * 요청 BApp 정보 생성자
     * @param name BApp 이름
     */
    public BAppInfo(String name) {
        this.name = name;
    }

    /**
     * 요청 BApp의 처리 결과 Callback URL 정보를 입력한다.
     * @param callback BApp Callback URL
     */
    public void setCallback(BAppDeepLinkCB callback) {
        this.callback = callback;
    }

    public String getName() {
        return name;
    }

    public BAppDeepLinkCB getCallback() {
        return callback;
    }

    public JSONObject toJson() {
        JSONObject params = new JSONObject();
        try {
            params.put(BAPP_NAME, name);
            if(callback != null)
                params.put(BAPP_CALLBACK, callback.toJson());
            return params;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. detailed error message: " + e.toString());
        }
    }
}
