package com.klipwallet.app2app.api.request;

import org.json.JSONException;
import org.json.JSONObject;

import com.klipwallet.app2app.api.KlipProtocol;

/**
 * Token 전송 트랜잭션 요청 정보
 */
public class TokenTxRequest implements KlipRequest {
    private String contract;
    private String to;
    private String amount;
    private String from;

    public static class Builder {
        private String contract;
        private String to;
        private String amount;
        private String from;

        public Builder() {
        }

        /**
         * 전송할 Token 컨트랙트 주소를 입력한다.
         * @param val Token Contract Address
         * @return TokenTxRequest.Builder
         */
        public Builder contract(String val) {
            contract = val;
            return this;
        }

        /**
         * 전송받을 EOA 주소를 입력한다.
         * @param val EOA
         * @return TokenTxRequest.Builder
         */
        public Builder to(String val) {
            to = val;
            return this;
        }

        /**
         * 전송할 Token 금액을 입력한다.
         * @param val 전송할 Token
         * @return TokenTxRequest.Builder
         */
        public Builder amount(String val) {
            amount = val;
            return this;
        }

        /**
         * (optional) 전송할 EOA 주소를 입력한다.
         * @param val 전송할 EOA
         * @return TokenTxRequest.Builder
         */
        public Builder from(String val) {
            from = val;
            return this;
        }

        public TokenTxRequest build() {
            return new TokenTxRequest(this);
        }
    }

    public TokenTxRequest(Builder builder) {
        contract = builder.contract;
        to = builder.to;
        amount = builder.amount;
        from = builder.from;
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(KlipProtocol.CONTRACT, contract);
            obj.put(KlipProtocol.TO, to);
            obj.put(KlipProtocol.AMOUNT, amount);
            obj.put(KlipProtocol.FROM, from);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. detailed error message: " + e.toString());
        }
    }
}
