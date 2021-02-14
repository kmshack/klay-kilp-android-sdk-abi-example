package com.klipwallet.app2app.api.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.klipwallet.app2app.api.KlipProtocol;

/**
 * Contract 실행 트랜잭션 요청 정보
 */
public class ContractTxRequest implements KlipRequest {
    private String to;
    private String value;
    private String abi;
    private String params;
    private String from;

    public static class Builder {
        private String to;
        private String value;
        private String abi;
        private ArrayList<Object> params;
        private String from;

        public Builder() {
        }

        /**
         * 실행할 Contract 주소를 입력한다.
         * @param val Contract Address
         * @return ContractTxRequest.Builder
         */
        public Builder to(String val) {
            to = val;
            return this;
        }

        /**
         * 전송할 KLAY 금액을 입력한다.
         * @param val KLAY
         * @return ContractTxRequest.Builder
         */
        public Builder value(String val) {
            value = val;
            return this;
        }

        /**
         * 실행할 Contract의 ABI를 입력한다.
         * @param val ABI
         * @return ContractTxRequest.Builder
         */
        public Builder abi(String val) {
            abi = val;
            return this;
        }

        /**
         * 실행할 Contract의 ABI에 대한 Parameters를 입력한다.
         * @param val Parameters
         * @return ContractTxRequest.Builder
         */
        public Builder params(ArrayList<Object> val) {
            params = val;
            return this;
        }

        /**
         * (optional) 실행할 EOA 주소를 입력한다.
         * @param val EOA
         * @return ContractTxRequest.Builder
         */
        public Builder from(String val) {
            from = val;
            return this;
        }

        public ContractTxRequest build() {
            return new ContractTxRequest(this);
        }
    }

    public ContractTxRequest(Builder builder) {
        to = builder.to;
        value = builder.value;
        abi = builder.abi;
        from = builder.from;

        if(builder.params != null) {
            JSONArray list = new JSONArray();
            for (Object param : builder.params) {
                list.put(param);
            }
            params = list.toString();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(KlipProtocol.TO, to);
            obj.put(KlipProtocol.VALUE, value);
            obj.put(KlipProtocol.ABI, abi);
            obj.put(KlipProtocol.PARAMS, params);
            obj.put(KlipProtocol.FROM, from);
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided. detailed error message: " + e.toString());
        }
    }
}
