package com.klipwallet.app2app.internal;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.exception.KlipResponseException;

public class ServerError {
    private int code; // required
    private String err; // required

    public int getCode() {
        return code;
    }

    public String getErr() {
        return err;
    }

    public void fromJson(String json) throws KlipResponseException {
        try {
            JSONObject obj = new JSONObject(json);
            code = obj.getInt(KlipProtocol.CODE);
            err = obj.getString(KlipProtocol.ERR);
        } catch (JSONException e) {
            throw new KlipResponseException(KlipErrorCode.PROTOCOL_ERROR_CODE,
                    "JSON parsing error. detail: " + e.toString(),
                    HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }
}
