package com.coolcloud.uac.android.common.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Executor {
    private static final String TAG = "Executor";
    private static ScheduledExecutorService executor;

    public static abstract class RunNoThrowable implements Runnable {
        private String env;

        public abstract void rundo();

        public RunNoThrowable() {
            this("[unknown]");
        }

        public RunNoThrowable(String env) {
            this.env = null;
            this.env = env;
        }

        public final void run() {
            try {
                rundo();
            } catch (Throwable e) {
                LOG.e(Executor.TAG, this.env + " rundo failed(Throwable)", e);
            }
        }
    }

    static {
        executor = null;
        executor = Executors.newScheduledThreadPool(5);
    }

    public static ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
        if (!executor.isShutdown() && !executor.isTerminated()) {
            return executor.schedule(runnable, delay, unit);
        }
        LOG.e(TAG, "[executor:" + executor.hashCode() + "] executor is shutdown, schedule failed");
        return null;
    }

    public static void execute(Runnable runnable) {
        if (executor.isShutdown() || executor.isTerminated()) {
            LOG.e(TAG, "[executor:" + executor.hashCode() + "] executor is shutdown, execute failed");
        } else {
            executor.execute(runnable);
        }
    }
}
