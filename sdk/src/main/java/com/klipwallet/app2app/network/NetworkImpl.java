package com.klipwallet.app2app.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class NetworkImpl implements INetwork {
    private static final String UTF8_CHARSET = "UTF-8";
    private static final int DEFAULT_CONNECTION_TO_IN_MS = 5000;
    private static final int DEFAULT_REQUEST_TO_IN_MS = 30 * 1000;

    private HttpURLConnection urlConnection = null;
    private final Map<String, String> header = new HashMap<>();
    private String body = "";
    private int statusCode = -1;

    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(final String hostname, final SSLSession session) {
            return true;
        }
    };

    @Override
    public void create(String url, String method) throws IOException {
        // Note that starting from Android 4.4, the networking layer (so also the HttpUrlConnection APIs) is implemented through OkHttp.
        this.urlConnection = (HttpURLConnection) new URL(url).openConnection(Proxy.NO_PROXY);
        if (url.startsWith("https")) {
            final HttpsURLConnection secure = (HttpsURLConnection) urlConnection;
            secure.setHostnameVerifier(DO_NOT_VERIFY);
        }
        urlConnection.setRequestMethod(method);
    }

    @Override
    public void configure() throws IOException {
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(DEFAULT_CONNECTION_TO_IN_MS);
        urlConnection.setReadTimeout(DEFAULT_REQUEST_TO_IN_MS);
        urlConnection.setInstanceFollowRedirects(false);
        urlConnection.setRequestProperty("Connection", "keep-alive");

        if (header != null && !header.isEmpty()) {
            for (final String key : header.keySet()) {
                urlConnection.setRequestProperty(key, header.get(key).toString());
            }
        }

        final String reqType = urlConnection.getRequestMethod();
        if ("GET".equals(reqType)) {
            urlConnection.setDoOutput(false);
        }
        else if ("POST".equals(reqType) || "PUT".equals(reqType)) {
            urlConnection.setDoOutput(true);
            int contentLength = 0;
            if (body != null) {
                String params = body;
                contentLength += params.getBytes(UTF8_CHARSET).length;

                urlConnection.setRequestProperty("Accept-Charset", UTF8_CHARSET);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(contentLength));

                OutputStream os = urlConnection.getOutputStream();
                os.write(params.getBytes(UTF8_CHARSET));
                os.flush();
                os.close();
            }
        }
    }

    @Override
    public void connect() throws IOException {
        try {
            statusCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            statusCode = urlConnection.getResponseCode();
        }
    }

    @Override
    public void disconnect() {
        body = "";
        header.clear();
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        statusCode = HttpURLConnection.HTTP_OK;
    }

    @Override
    public void addHeader(String key, String value) {
        header.put(key, value);
    }

    @Override
    public void addBody(String body) {
        this.body = body;
    }

    @Override
    public byte[] readFully() throws IOException {
        final InputStream is = getInputStream(urlConnection);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            int nLength;
            while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, nLength);
            }
            return baos.toByteArray();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ignore) {
            }
        }
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    private InputStream getInputStream(final HttpURLConnection urlConnection) throws IOException {
        if (urlConnection.getResponseCode() < 400) {
            return urlConnection.getInputStream();
        } else {
            final InputStream ein = urlConnection.getErrorStream();
            return ein != null ? ein : new ByteArrayInputStream(new byte[0]);
        }
    }
}
