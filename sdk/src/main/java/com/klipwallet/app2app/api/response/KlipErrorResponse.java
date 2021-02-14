package com.klipwallet.app2app.api.response;

import java.net.HttpURLConnection;

import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.network.exception.ResponseStatusException;

/**
 * Klip 에러
 */
public class KlipErrorResponse {
    private int errorCode;
    private String errorMsg;
    private int httpStatus;
    private Exception exception; // (optional) Klip 예외

    /**
     * 클라이언트 사이드에서 예외가 발생한 경우에 사용하는 생성자
     * @param e 클라이언트 사이드에서 발생한 Exception
     */
    public KlipErrorResponse(Exception e) {
        this.errorCode = KlipErrorCode.CLIENT_INTERNAL_ERROR;
        this.errorMsg = "klip client error";
        this.httpStatus = HttpURLConnection.HTTP_INTERNAL_ERROR;
        this.exception = e;
    }

    /**
     * Http 상태코드가 200이 아닌 경우에 사용하는 생성자
     * @param e 서버에서 내려온 에러를 표현하는 Exception
     */
    public KlipErrorResponse(ResponseStatusException e) {
        this.errorCode = e.getErrorCode();
        this.errorMsg = e.getErrorMsg();
        this.httpStatus = e.getHttpStatusCode();
        this.exception = e;
    }

    /**
     * 에러코드를 가져온다.
     * @return 에러코드
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 에러코드 설명을 가제온다.
     * @return 에러코드 설명
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * HTTP 상태코드를 가져온다.
     * @return HTTP 상태코드
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public String toString() {
        String str = "httpStatus=" + httpStatus + "\n";
        str += "errorCode=" + errorCode + "\n";
        str += "errorMsg=" + errorMsg + "\n";
        return str;
    }
}
