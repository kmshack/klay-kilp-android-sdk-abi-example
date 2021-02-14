package com.klipwallet.app2app.network.response;

public interface ResponseConverter<F, T> {
    T convert(F o);
}
