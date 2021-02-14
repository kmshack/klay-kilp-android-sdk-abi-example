package com.klipwallet.app2app.api.response.model;

import org.json.JSONException;
import org.json.JSONObject;

import com.klipwallet.app2app.api.KlipProtocol;

/**
 * Klip 결과 정보
 */
public class KlipResult {
    private String txHash; // exist if transaction type
    private String status; // exist if transaction type
    private String klaytnAddress; // exist if auth type

    /**
     * (Klip 트랜잭션 요청에 대한) 트랜잭션 해시를 가져온다.
     * @return 트랜잭션 해시
     */
    public String getTxHash() {
        return txHash;
    }

    /**
     * (Klip 트랜잭션 요청에 대한) 상태 정보를 가져온다.
     * @return 상태 정보
     */
    public String getStatus() {
        return status;
    }

    /**
     * (Klip 인증 요청에 대한) 사용자 EOA를 가져온다.
     * @return 사용자 EOA
     */
    public String getKlaytnAddress() {
        return klaytnAddress;
    }

    public void fromJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        if(!obj.isNull(KlipProtocol.TX_HASH))
            txHash = obj.getString(KlipProtocol.TX_HASH);
        if(!obj.isNull(KlipProtocol.STATUS))
            status = obj.getString(KlipProtocol.STATUS);
        if(!obj.isNull(KlipProtocol.KLAYTN_ADDRESS))
            klaytnAddress = obj.getString(KlipProtocol.KLAYTN_ADDRESS);
    }

    public JSONObject toJson() {
        try {
            JSONObject obj = new JSONObject();
            if(txHash != null)
                obj.put(KlipProtocol.TX_HASH, txHash);
            if(status != null)
                obj.put(KlipProtocol.STATUS, status);
            if(klaytnAddress != null)
                obj.put(KlipProtocol.KLAYTN_ADDRESS, klaytnAddress);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided to Klip API. detailed error message: " + e.toString());
        }
    }
}
