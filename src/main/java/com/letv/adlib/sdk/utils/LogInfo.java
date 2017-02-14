package com.letv.adlib.sdk.utils;

import android.util.Log;

public class LogInfo {
    private static final int DEBUG_LEVEL = 4;
    private static String TAG = "ads";
    private static boolean isShowLog = true;

    public static void setIsShowLog(boolean showLog) {
        isShowLog = showLog;
    }

    public static boolean isShowLog() {
        return isShowLog;
    }

    public static void log(String msg) {
        if (isShowLog) {
            switch (4) {
                case 0:
                    Log.i(TAG, msg);
                    return;
                case 1:
                    Log.d(TAG, msg);
                    return;
                case 2:
                    Log.v(TAG, msg);
                    return;
                case 3:
                    Log.w(TAG, msg);
                    return;
                case 4:
                    Log.e(TAG, msg);
                    return;
                default:
                    Log.i(TAG, msg);
                    return;
            }
        }
    }

    public static void log(String Tag, String msg) {
        if (isShowLog) {
            switch (4) {
                case 0:
                    Log.i(Tag, msg);
                    return;
                case 1:
                    Log.d(Tag, msg);
                    return;
                case 2:
                    Log.v(Tag, msg);
                    return;
                case 3:
                    Log.w(Tag, msg);
                    return;
                case 4:
                    Log.e(Tag, msg);
                    return;
                default:
                    Log.i(Tag, msg);
                    return;
            }
        }
    }
}
