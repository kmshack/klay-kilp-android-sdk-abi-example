package com.klipwallet.app2app.api.request.model;

import org.json.JSONException;
import org.json.JSONObject;

import static com.klipwallet.app2app.api.KlipProtocol.BAPP_SUCCESS_URL;
import static com.klipwallet.app2app.api.KlipProtocol.BAPP_FAIL_URL;

/**
 * 요청 BApp의 처리 결과 Callback 정보
 */
public class BAppDeepLinkCB {
    private String successURL;
    private String failURL;

    /**
     * 요청 BApp의 처리 결과 Callback 정보 생성자
     * @param successURL 성공시, BApp Callback URL
     * @param failURL 실패시, BApp Callback URL
     */
    public BAppDeepLinkCB(String successURL, String failURL) {
        this.successURL = successURL;
        this.failURL = failURL;
    }

    public String getSuccessUrl() {
        return successURL;
    }

    public String getFailUrl() {
        return failURL;
    }

    public JSONObject toJson() {
        JSONObject params = new JSONObject();
        try {
            params.put(BAPP_SUCCESS_URL, successURL);
            params.put(BAPP_FAIL_URL, failURL);
            return params;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. detailed error message: " + e.toString());
        }
    }
}
