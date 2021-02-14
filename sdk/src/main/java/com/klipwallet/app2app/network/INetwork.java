package com.klipwallet.app2app.network;

import java.io.IOException;

public interface INetwork {
    void create(String url, String method) throws IOException;
    void configure() throws IOException;
    void connect() throws IOException;
    void disconnect();
    void addHeader(String key, String value);
    void addBody(String body);
    byte[] readFully() throws IOException;
    int getStatusCode();
}
