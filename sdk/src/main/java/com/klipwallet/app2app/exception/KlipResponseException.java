package com.klipwallet.app2app.exception;

import com.klipwallet.app2app.network.exception.ResponseStatusException;

/**
 * Klip API 응답 관련 Exception Class
 */
public class KlipResponseException extends ResponseStatusException {
    private int errorCode;
    private String errorMsg;
    private int httpStatus;

    public KlipResponseException(int errCode, String errorMsg, int httpStatus) {
        super(errorMsg);
        this.errorCode = errCode;
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
    }

    /**
     * 에러코드를 가져온다.
     * @return 에러코드
     */
    @Override
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 에러코드 설명을 가제온다.
     * @return 에러코드 설명
     */
    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * HTTP 상태코드를 가져온다.
     * @return HTTP 상태코드
     */
    @Override
    public int getHttpStatusCode() {
        return this.httpStatus;
    }
}
