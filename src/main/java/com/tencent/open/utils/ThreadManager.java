package com.tencent.open.utils;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import java.lang.reflect.Field;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: ProGuard */
public final class ThreadManager {
    public static final boolean DEBUG_THREAD = false;
    public static final Executor NETWORK_EXECUTOR = a();
    private static Handler a;
    private static HandlerThread b;
    private static Handler c;
    private static HandlerThread d;

    private static Executor a() {
        Executor threadPoolExecutor;
        if (VERSION.SDK_INT >= 11) {
            threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue());
        } else {
            Executor executor;
            try {
                Field declaredField = AsyncTask.class.getDeclaredField("sExecutor");
                declaredField.setAccessible(true);
                executor = (Executor) declaredField.get(null);
            } catch (Exception e) {
                Object threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue());
            }
            threadPoolExecutor = executor;
        }
        if (threadPoolExecutor instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) threadPoolExecutor).setCorePoolSize(3);
        }
        return threadPoolExecutor;
    }

    public static void init() {
    }

    public static void executeOnNetWorkThread(Runnable runnable) {
        try {
            NETWORK_EXECUTOR.execute(runnable);
        } catch (RejectedExecutionException e) {
        }
    }

    public static Handler getFileThreadHandler() {
        if (c == null) {
            synchronized (ThreadManager.class) {
                d = new HandlerThread("QQ_FILE_RW");
                d.start();
                c = new Handler(d.getLooper());
            }
        }
        return c;
    }

    public static Looper getFileThreadLooper() {
        return getFileThreadHandler().getLooper();
    }

    public static Thread getSubThread() {
        if (b == null) {
            getSubThreadHandler();
        }
        return b;
    }

    public static Handler getSubThreadHandler() {
        if (a == null) {
            synchronized (ThreadManager.class) {
                b = new HandlerThread("QQ_SUB");
                b.start();
                a = new Handler(b.getLooper());
            }
        }
        return a;
    }

    public static Looper getSubThreadLooper() {
        return getSubThreadHandler().getLooper();
    }

    public static void executeOnSubThread(Runnable runnable) {
        getSubThreadHandler().post(runnable);
    }
}
