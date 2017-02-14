package com.letv.lepaysdk.utils;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
    private static final int CORE_POOL_SIZE = 2;
    private static final int KEEP_ALIVE = 1;
    private static final int MAXIMUM_POOL_SIZE = 8;
    private static final int S_CORE_POOL_SIZE = 3;
    private static final String TAG = "lepayTask";
    protected static Handler handler = new Handler();
    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(3, 8, 1, TimeUnit.SECONDS, sWorkQueue, sThreadFactory, new DiscardOldestPolicy());
    private static final ThreadFactory sThreadFactory = new 1();
    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue(128);
    private static final ThreadPoolExecutor uiExecutor = new ThreadPoolExecutor(2, 8, 1, TimeUnit.SECONDS, uiWorkQueue, uiThreadFactory, new DiscardOldestPolicy());
    private static final ThreadFactory uiThreadFactory = new 2();
    private static final BlockingQueue<Runnable> uiWorkQueue = new LinkedBlockingQueue(8);

    public interface IThreadCommand {
        void didCommand();
    }

    public interface IThreadCallback {
        void didCommandFinishInvokeMainThread();
    }

    public interface IThreadTask extends IThreadCommand, IThreadCallback {
    }

    public static void exec(Runnable command) {
        sExecutor.execute(new 3(command));
    }

    public static void execUi(Runnable command) {
        uiExecutor.execute(new 4(command));
    }

    public static void execUi(IThreadTask task) {
        execUi((IThreadCommand) task, (IThreadCallback) task);
    }

    public static void execUi(IThreadCommand command, IThreadCallback mainCallback) {
        uiExecutor.execute(new 5(command, mainCallback));
    }

    public static <A, B, C> void execUi(AsyncTask<A, B, C> task, A... params) {
        if (task != null) {
            if (VERSION.SDK_INT < 11) {
                task.execute(params);
            } else {
                task.executeOnExecutor(uiExecutor, params);
            }
        }
    }

    public static void initialize() {
    }
}
