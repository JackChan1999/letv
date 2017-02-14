package com.letv.component.utils;

import android.text.TextUtils;
import android.util.Log;

public class DebugLog {
    private static final String TAG = "DebugLog";
    private static boolean isDebug = true;

    public static void setIsDebug(boolean b) {
        isDebug = b;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void log(Object msg) {
        log(TAG, msg);
    }

    public static void log(String LOG_CLASS_NAME, Object msg) {
        if (!TextUtils.isEmpty(LOG_CLASS_NAME) && msg != null && isDebug) {
            Log.i(LOG_CLASS_NAME, "[INFO " + LOG_CLASS_NAME + "] " + String.valueOf(msg));
        }
    }

    public static void log(String TAG, String LOG_CLASS_NAME, Object msg) {
        if (!TextUtils.isEmpty(TAG) && msg != null && isDebug) {
            Log.i(TAG, "[INFO " + LOG_CLASS_NAME + "] " + String.valueOf(msg));
        }
    }
}
