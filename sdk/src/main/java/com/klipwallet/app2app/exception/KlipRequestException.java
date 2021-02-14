package com.klipwallet.app2app.exception;

/**
 * Klip API 요청 관련 Exception Class
 */
public class KlipRequestException extends Exception {
    private int errorCode;
    private String errorMsg;

    public KlipRequestException(int errCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errCode;
        this.errorMsg = errorMsg;
    }

    public KlipRequestException(int errCode, Exception e) {
        super(e);
        this.errorCode = errCode;
    }

    /**
     * 에러코드를 가져온다.
     * @return 에러코드
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 에러코드 설명을 가져온다.
     * @return 에러코드 설명
     */
    public String getErrorMsg() {
        return errorMsg;
    }
}
