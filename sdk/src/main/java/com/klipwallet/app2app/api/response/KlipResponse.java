package com.klipwallet.app2app.api.response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

import com.klipwallet.app2app.api.KlipProtocol;
import com.klipwallet.app2app.api.response.model.KlipError;
import com.klipwallet.app2app.api.response.model.KlipResult;
import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.exception.KlipResponseException;
import com.klipwallet.app2app.network.response.ResponseStringConverter;

public class KlipResponse {
    private String requestKey; // required
    private String expirationTime; // required
    private String status; // required
    private KlipResult result; // exist if success
    private KlipError error; // exist if error

    /**
     * Klip 요청 키를 가져온다.
     * @return Klip 요청 키
     */
    public String getRequestKey() {
        return requestKey;
    }

    /**
     * Klip 요청 키 만료 시간을 가져온다.
     * @return Klip 요청 키 만료 시간
     */
    public String getExpirationTime() {
        return expirationTime;
    }

    /**
     * Klip 요청 키 상태를 가져온다.
     * @return Klip 요청 키 상태
     */
    public String getStatus() {
        return status;
    }

    /**
     * Klip 요청 성공시, 결과를 가져온다.
     * @return 결과 정보
     */
    public KlipResult getResult() {
        return result;
    }

    /**
     * Klip 요청 실패시, 결과를 가져온다.
     * @return 에러 정보
     */
    public KlipError getError() {
        return error;
    }

    public void fromJson(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            requestKey = obj.getString(KlipProtocol.REQUEST_KEY);
            expirationTime = obj.getString(KlipProtocol.EXPIRATION_TIME);
            status = obj.getString(KlipProtocol.STATUS);

            if (!obj.isNull(KlipProtocol.RESULT)) {
                JSONObject resultObj = obj.getJSONObject(KlipProtocol.RESULT);
                result = new KlipResult();
                result.fromJson(resultObj.toString());
            }

            if (!obj.isNull(KlipProtocol.ERROR)) {
                JSONObject errorObj = obj.getJSONObject(KlipProtocol.ERROR);
                error = new KlipError();
                error.fromJson(errorObj.toString());
            }
        } catch (JSONException e) {
            throw new KlipResponseException(KlipErrorCode.PROTOCOL_ERROR_CODE,
                    "JSON parsing error. detail: " + e.toString(),
                    HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    public static ResponseStringConverter<KlipResponse> getConverter() {
        return new ResponseStringConverter<KlipResponse>() {
            @Override
            public KlipResponse convert(String o) {
                KlipResponse res = new KlipResponse();
                res.fromJson(o);
                return res;
            }
        };
    }

    @Override
    public String toString() {
        return toJson().toString();
    }

    public JSONObject toJson() {
        try {
            JSONObject obj = new JSONObject();
            obj.put(KlipProtocol.REQUEST_KEY, requestKey);
            obj.put(KlipProtocol.EXPIRATION_TIME, expirationTime);
            obj.put(KlipProtocol.STATUS, status);
            if(result != null)
                obj.put(KlipProtocol.RESULT, result.toJson());
            if(error != null)
                obj.put(KlipProtocol.ERROR, error.toJson());
            return obj;
        } catch (JSONException e) {
            throw new IllegalArgumentException("JSON parsing error. Malformed parameters were provided to Klip API. detailed error message: " + e.toString());
        }
    }
}
