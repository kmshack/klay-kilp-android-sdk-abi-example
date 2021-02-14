package com.klipwallet.app2app.network.tasks;

import java.util.concurrent.Future;

public interface ITaskQueue {
    <T> Future<T> addTask(KlipResultTask<T> task);
}
