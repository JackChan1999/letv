package com.letv.marlindrm.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public final class HandlerUtils {
    private static final String TAG = "HandlerUtils";
    private static final HandlerThread mWorkingThread = new HandlerThread(TAG);
    private static Handler sHandler;
    private static final Handler sUiHandler = new Handler(Looper.getMainLooper());

    static {
        sHandler = null;
        mWorkingThread.start();
        sHandler = new Handler(mWorkingThread.getLooper());
    }

    private HandlerUtils() {
    }

    public static Handler getUiThreadHandler() {
        return sUiHandler;
    }

    public static Handler getWorkingThreadHandler() {
        return sHandler;
    }
}
