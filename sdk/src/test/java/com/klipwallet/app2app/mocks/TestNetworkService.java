package com.klipwallet.app2app.mocks;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.network.NetworkService;
import com.klipwallet.app2app.network.response.ResponseStringConverter;

public class TestNetworkService implements NetworkService {
    // handler in KlipResultTask
    private Handler handler;

    // executorService in KlipTaskQueue
    private ExecutorService executorService;

    public TestNetworkService(final Handler handler, final ExecutorService executorService) {
        this.handler = handler;
        this.executorService = executorService;
    }

    @Override
    public <T> Future<T> execute(String requestURL, String requestMethod, String requestBody, ResponseStringConverter<T> converter, final KlipCallback<T> callback) {
        return (Future<T>) executorService.submit(new Runnable() {
            @Override
            public void run() {
                // addTask
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        });
    }


}
