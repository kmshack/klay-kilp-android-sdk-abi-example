package com.klipwallet.app2app.network.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KlipTaskQueue extends AbstractTaskQueue {
    private static KlipTaskQueue instance = new KlipTaskQueue(Executors.newCachedThreadPool());

    public static KlipTaskQueue getInstance() {
        return instance;
    }

    public KlipTaskQueue(final ExecutorService e) {
        super(e);
    }
}