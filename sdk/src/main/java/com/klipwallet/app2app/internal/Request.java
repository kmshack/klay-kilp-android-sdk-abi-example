package com.klipwallet.app2app.internal;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String requestKey;

    private static final String REQUEST_KEY = "request_key";

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> obj = new HashMap<>();
        obj.put(REQUEST_KEY, requestKey);
        return obj;
    }
}
