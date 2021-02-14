package com.klipwallet.app2app.exception;

/**
 * Klip API 에러 코드 Class
 */
public class KlipErrorCode {

    /**
     * (Request Error) Klip SDK Internal 에러 (ex, Http 연결 실패)
     */
    public static final int CLIENT_INTERNAL_ERROR = 10;

    /**
     * (Request Error) Klip SDK 필수 파라미터가 누락된 경우 발생하는 에러
     */
    public static final int CORE_PARAMETER_MISSING = 11;

    /**
     * (Request Error) Klip SDK에서 지원하지 않는 요청 타입을 사용하는 경우 발생하는 에러
     */
    public static final int NOT_SUPPORTED_REQUEST_TYPE = 12;

    /**
     * (Response Error) Klip REST API 미지원 에러코드
     */
    public static final int UNDEFINED_ERROR_CODE = 21;

    /**
     * (Response Error) Klip Protocol 에러
     */
    public static final int PROTOCOL_ERROR_CODE = 22;
}