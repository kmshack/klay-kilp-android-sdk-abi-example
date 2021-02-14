package com.klipwallet.app2app.api;

import com.klipwallet.app2app.api.response.KlipErrorResponse;

/**
 * Klip API 요청 결과를 위한 Callback Interface
 * @param <T> Klip 결과로 기대하는 응답 Class
 */
public interface KlipCallback<T> {
    /**
     * Klip 요청 성공 Callback
     * @param result 성공 결과
     */
    void onSuccess(T result);

    /**
     * Klip 요청 실패 Callback
     * @param error 실패 결과
     */
    void onFail(KlipErrorResponse error);
}
