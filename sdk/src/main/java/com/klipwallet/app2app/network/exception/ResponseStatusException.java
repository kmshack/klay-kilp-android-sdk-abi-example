package com.klipwallet.app2app.network.exception;

public abstract class ResponseStatusException extends RuntimeException {
    public ResponseStatusException(String errorMsg) {
        super(errorMsg);
    }

    public abstract int getErrorCode();
    public abstract String getErrorMsg();
    public abstract int getHttpStatusCode();
}