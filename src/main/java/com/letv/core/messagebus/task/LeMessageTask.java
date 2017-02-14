package com.letv.core.messagebus.task;

import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;

public class LeMessageTask {
    private final int mId;
    private final TaskRunnable mRunnable;

    public interface TaskRunnable {
        LeResponseMessage run(LeMessage leMessage);
    }

    public LeMessageTask(int id, TaskRunnable runnable) {
        this.mId = id;
        this.mRunnable = runnable;
    }

    public int getId() {
        return this.mId;
    }

    public TaskRunnable getRunnable() {
        return this.mRunnable;
    }
}
