package com.klipwallet.app2app.network.tasks;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.network.exception.ResponseStatusException;
import com.klipwallet.app2app.api.response.KlipErrorResponse;

public abstract class KlipResultTask<T> {
    private final static Handler mainHandler = new Handler(Looper.getMainLooper());
    private final KlipCallback<T> callback;

    public KlipResultTask() {
        this.callback = null;
    }

    public KlipResultTask(KlipCallback<T> callback) {
        this.callback = callback;
    }

    public Handler getHandler() {
        return this.mainHandler;
    }

    private final Callable<T> task = new Callable<T>() {
        @Override
        public T call() throws Exception {
            T result = null;
            Exception ex = null;

            try {
                onDidStart();
                result = KlipResultTask.this.call();
            } catch (Exception e) {
                ex = e;
            }

            final T response = result;
            final Exception t = ex;

            final CountDownLatch lock = new CountDownLatch(1);
            getHandler().post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (callback == null) {
                            return;
                        }

                        if (t != null) {
                            KlipErrorResponse errorResult;
                            if (t instanceof ResponseStatusException) {
                                ResponseStatusException err = (ResponseStatusException) t;
                                errorResult = new KlipErrorResponse(err);
                            } else {
                                errorResult = new KlipErrorResponse(t);
                            }
                            callback.onFail(errorResult);

                        } else {
                            // 정의된 Response 타입이 아닌 경우 발생
                            try {
                                callback.onSuccess(response);
                            } catch (ClassCastException e) {
                                KlipErrorResponse errorResult = new KlipErrorResponse(e);
                                callback.onFail(errorResult);
                            }
                        }
                    } finally {
                        lock.countDown();
                    }
                }
            });

            lock.await();
            onDidEnd();
            return result;
        }
    };

    public abstract T call() throws Exception;

    final public Callable<T> getCallable() {
        return task;
    }

    public void onDidStart() {
    }

    public void onDidEnd() {
    }
}
