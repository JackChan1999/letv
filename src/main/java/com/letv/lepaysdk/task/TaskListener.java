package com.letv.lepaysdk.task;

public interface TaskListener<T> {
    void onFinish(TaskResult<T> taskResult);

    void onPreExcuete();
}
