package com.klipwallet.app2app.api.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.klipwallet.app2app.api.KlipProtocol;

/**
 * KLAY 전송 트랜잭션 요청 정보
 */
public class KlayTxRequest implements KlipRequest {
    private String to;
    private String amount;
    private String from;

    public static class Builder {
        private String to;
        private String amount;
        private String from;

        public Builder() {
        }

        /**
         * 전송받을 EOA 주소를 입력한다.
         * @param val EOA
         * @return KlayTxRequest.Builder
         */
        public Builder to(String val) {
            to = val;
            return this;
        }

        /**
         * 전송할 KLAY 금액을 입력한다.
         * @param val KLAY
         * @return KlayTxRequest.Builder
         */
        public Builder amount(String val) {
            amount = val;
            return this;
        }

        /**
         * (optional) 전송할 EOA 주소를 입력한다.
         * @param val EOA
         * @return KlayTxRequest.Builder
         */
        public Builder from(String val) {
            from = val;
            return this;
        }

        public KlayTxRequest build() {
            return new KlayTxRequest(this);
        }
    }

    public KlayTxRequest(Builder builder) {
        to = builder.to;
        amount = builder.amount;
        from = builder.from;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(KlipProtocol.TO, to);
            obj.put(KlipProtocol.AMOUNT, amount);
            obj.put(KlipProtocol.FROM, from);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. detailed error message: " + e.toString());
        }
    }
}
