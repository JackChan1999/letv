package com.letv.datastatistics.http;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

public final class ThreadPoolManager {
    private static final int CorePoolSize = 3;
    private static final long KeepAliveTime = 10;
    private static final int MaximumPoolSize = 5;
    private static ThreadPoolManager mInstance = null;
    private static final Object mInstanceSync = new Object();
    private ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(3, 5, KeepAliveTime, TimeUnit.SECONDS, new LinkedBlockingQueue(), new CallerRunsPolicy());

    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        synchronized (mInstanceSync) {
            if (mInstance != null) {
                ThreadPoolManager threadPoolManager = mInstance;
                return threadPoolManager;
            }
            mInstance = new ThreadPoolManager();
            return mInstance;
        }
    }

    public void executeThreadWithPool(Runnable runnable) {
        this.mThreadPoolExecutor.execute(runnable);
    }

    public void closeThreadPool() {
        if (this.mThreadPoolExecutor != null) {
            this.mThreadPoolExecutor.shutdown();
        }
        if (mInstance != null) {
            mInstance = null;
        }
    }
}
