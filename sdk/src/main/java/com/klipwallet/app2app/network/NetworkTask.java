package com.klipwallet.app2app.network;

import java.io.IOException;
import java.util.Map;

import com.klipwallet.app2app.util.Logger;

public class NetworkTask {
    final private INetwork network;

    public NetworkTask() {
        this.network = new NetworkImpl();
    }

    public NetworkTask(INetwork network) {
        this.network = network;
    }

    public ResponseData request(final String url, final String method, String body, Map<String, String> header) throws IOException {
        try {
            Logger.debug("request [" + method + "] " + url + " " + body);

            network.create(url, method);

            // add header
            if (header != null) {
                for (String key : header.keySet()) {
                    if (key.equalsIgnoreCase("Expect")) {
                        throw new IllegalStateException("Expect: 100-Continue not supported");
                    }
                    network.addHeader(key, header.get(key));
                }
            }

            // add body
            if (body != null && !body.trim().isEmpty()) {
                network.addBody(body);
            }

            network.configure();
            network.connect();

            int statusCode = network.getStatusCode();
            return new ResponseData(statusCode, network.readFully());
        } finally {
            network.disconnect();
        }
    }
}
