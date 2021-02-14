package com.klipwallet.app2app.network.tasks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AbstractTaskQueue implements ITaskQueue {
    private ExecutorService executor;

    public AbstractTaskQueue(final ExecutorService e) {
        executor = e;
    }

    public <T> Future<T> addTask(KlipResultTask<T> task) {
        return executor.submit(task.getCallable());
    }
}
