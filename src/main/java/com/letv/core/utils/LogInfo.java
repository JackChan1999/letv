package com.letv.core.utils;

import android.text.TextUtils;
import android.util.Log;
import com.letv.core.config.LetvConfig;

public class LogInfo {
    private static final int DEBUG_LEVEL = 4;
    private static String TAG = "haitian";
    private static final String TAG_CONTENT_PRINT = "%s:%s.%s:%d";
    private static String TAG_STATISTICS = "statistics";
    private static final boolean isDebug = LetvConfig.isDebug();

    public static void log(String msg) {
        log(TAG, msg);
    }

    public static void LogStatistics(String msg) {
        log(TAG_STATISTICS, msg);
    }

    public static void log(String tag, String msg) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg) && isDebug) {
            switch (4) {
                case 0:
                    Log.i(tag, msg);
                    return;
                case 1:
                    Log.d(tag, msg);
                    return;
                case 2:
                    Log.v(tag, msg);
                    return;
                case 3:
                    Log.w(tag, msg);
                    return;
                case 4:
                    Log.e(tag, msg);
                    return;
                default:
                    Log.i(tag, msg);
                    return;
            }
        }
    }

    public static void trace(String tag) {
        if (isDebug) {
            Log.d(tag, getContent(getCurrentStackTraceElement(), tag));
        }
    }

    private static StackTraceElement getCurrentStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    private static String getContent(StackTraceElement trace, String tag) {
        return String.format(TAG_CONTENT_PRINT, new Object[]{tag, trace.getClassName(), trace.getMethodName(), Integer.valueOf(trace.getLineNumber())});
    }
}
