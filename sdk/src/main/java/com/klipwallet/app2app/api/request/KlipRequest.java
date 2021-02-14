package com.klipwallet.app2app.api.request;

import org.json.JSONObject;

public interface KlipRequest {
    String AUTH = "auth";
    String SEND_KLAY = "send_klay";
    String SEND_TOKEN = "send_token";
    String SEND_CARD = "send_card";
    String EXECUTE_CONTRACT = "execute_contract";

    JSONObject toJson();
}