package com.klipwallet.app2app.network;

import java.util.concurrent.Future;

import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.network.response.ResponseStringConverter;
import com.klipwallet.app2app.network.tasks.ITaskQueue;
import com.klipwallet.app2app.network.tasks.KlipTaskQueue;

public interface NetworkService {
    <T> Future<T> execute(final String requestUrl, final String requestMethod, final String requestBody, final ResponseStringConverter<T> converter, KlipCallback<T> callback);

    class Factory {
        public static NetworkService getInstance() {
            ITaskQueue taskQueue = KlipTaskQueue.getInstance();
            return new DefaultNetworkService(taskQueue);
        }
    }
}
