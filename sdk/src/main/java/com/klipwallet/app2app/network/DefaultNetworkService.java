package com.klipwallet.app2app.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Future;

import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.exception.KlipErrorCode;
import com.klipwallet.app2app.exception.KlipResponseException;
import com.klipwallet.app2app.internal.ServerError;
import com.klipwallet.app2app.network.response.ResponseStringConverter;
import com.klipwallet.app2app.network.tasks.ITaskQueue;
import com.klipwallet.app2app.network.tasks.KlipResultTask;

public class DefaultNetworkService implements NetworkService {
    private ITaskQueue taskQueue;

    public DefaultNetworkService(final ITaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public <T> Future<T> execute(final String requestURL, final String requestMethod, final String requestBody, final ResponseStringConverter<T> converter, KlipCallback<T> callback) {
        return taskQueue.addTask(new KlipResultTask<T>(callback){
            @Override
            public T call() throws Exception {
                return execute(requestURL, requestMethod, requestBody, converter);
            }
        });
    }

    private <T> T execute(String requestURL, String requestMethod, String requestBody, ResponseStringConverter<T> converter) throws IOException {
        NetworkTask task = new NetworkTask();
        ResponseData data = task.request(requestURL, requestMethod, requestBody, null);

        // check http status
        int status = data.getHttpStatusCode();
        if (status == HttpURLConnection.HTTP_OK) {
            return converter.convert(data.getStringData());
        }
        else if (isSupportedHttpStatus(status) && data.getStringData() != null) {
            ServerError err = new ServerError();
            err.fromJson(data.getStringData());
            throw new KlipResponseException(err.getCode(), err.getErr(), status);
        }
        throw new KlipResponseException(KlipErrorCode.UNDEFINED_ERROR_CODE, data.getStringData(), status);
    }

    private boolean isSupportedHttpStatus(int status) {
        if (status == HttpURLConnection.HTTP_BAD_REQUEST || status == HttpURLConnection.HTTP_UNAUTHORIZED || status == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            return true;
        }
        return false;
    }
}
