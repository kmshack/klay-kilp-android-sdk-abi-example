package com.klipwallet.app2app.network;

public class ResponseData {
    private final int httpStatusCode;
    private final byte[] data;

    public ResponseData(int httpStatusCode, byte[] data) {
        this.httpStatusCode = httpStatusCode;
        this.data = data;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public byte[] getData() {
        return data;
    }

    public String getStringData() {
        if (data == null) {
            return null;
        }
        return new String(data);
    }
}
