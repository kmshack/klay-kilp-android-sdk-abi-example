package com.klipwallet.app2app.internal;

import org.json.JSONException;
import org.json.JSONObject;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.request.KlipRequest;

public class PrepareRequest {
    private BAppInfo bapp; // required
    private KlipRequest transaction;
    private String type; // required

    public void setBapp(BAppInfo bapp) {
        this.bapp = bapp;
    }

    public void setTransaction(KlipRequest transaction) {
        this.transaction = transaction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BAppInfo getBapp() {
        return bapp;
    }

    public KlipRequest getTransaction() {
        return transaction;
    }

    public String getType() {
        return type;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(KlipProtocol.TYPE, type);
            obj.put(KlipProtocol.BAPP, bapp.toJson());

            if(transaction != null) {
                obj.put(KlipProtocol.TRANSACTION, transaction.toJson());
            }
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. detailed error message: " + e.toString());
        }
    }
}
