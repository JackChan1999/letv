package com.letv.download.util;

import android.util.Log;
import com.letv.core.config.LetvConfig;

public class L {
    public static final String TAG = "LetvDownload";
    public static final boolean mode_for_release = (!LetvConfig.isDebug());

    public static void v(String tag, String msg) {
        if (!mode_for_release) {
            Log.v(tag, msg);
        }
    }

    public static void v(String tag, String type, String msg) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%s", new Object[]{tag, type, msg}));
        }
    }

    public static void v(String tag, String type, String msg1, String msg2) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%s%s", new Object[]{tag, type, msg1, msg2}));
        }
    }

    public static void v(String tag, String type, String msg1, String msg2, String msg3, String msg4) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%s%s%s%s", new Object[]{tag, type, msg1, msg2, msg3, msg4}));
        }
    }

    public static void v(String tag, String type, String msg1, int msg) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%s%d", new Object[]{tag, type, msg1, Integer.valueOf(msg)}));
        }
    }

    public static void v(String tag, String type, Object msg1, Object msg2, Object msg3, Object msg4) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%s%s%s%s", new Object[]{tag, type, String.valueOf(msg1), String.valueOf(msg2), String.valueOf(msg3), String.valueOf(msg4)}));
        }
    }

    public static void v(String tag, String type, Object msg1, Object msg2) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%s%s", new Object[]{tag, type, String.valueOf(msg1), String.valueOf(msg2)}));
        }
    }

    public static void v(String tag, String type, int msg) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%d", new Object[]{tag, type, Integer.valueOf(msg)}));
        }
    }

    public static void v(String tag, String type, boolean msg) {
        if (!mode_for_release) {
            String str = "[%s][%s]%s";
            Object[] objArr = new Object[3];
            objArr[0] = tag;
            objArr[1] = type;
            objArr[2] = msg ? "true" : "false";
            Log.v(TAG, String.format(str, objArr));
        }
    }

    public static void i(String tag, String type, String msg) {
        if (!mode_for_release) {
            Log.i(TAG, String.format("[%s][%s]%s", new Object[]{tag, type, msg}));
        }
    }

    public static void i(String tag, String type, String msg1, String msg2) {
        if (!mode_for_release) {
            Log.i(TAG, String.format("[%s][%s]%s%s", new Object[]{tag, type, msg1, msg2}));
        }
    }

    public static void i(String tag, String type, int msg) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s][%s]%d", new Object[]{tag, type, Integer.valueOf(msg)}));
        }
    }

    public static void i(String tag, String type, boolean msg) {
        if (!mode_for_release) {
            String str = "[%s][%s]%s";
            Object[] objArr = new Object[3];
            objArr[0] = tag;
            objArr[1] = type;
            objArr[2] = msg ? "true" : "false";
            Log.v(TAG, String.format(str, objArr));
        }
    }

    public static void i(String tag, String msg) {
        if (!mode_for_release) {
            Log.v(TAG, String.format("[%s]%s", new Object[]{tag, msg}));
        }
    }

    public static void e(String tag, String type, String msg) {
        if (!mode_for_release) {
            Log.e(TAG, String.format("[%s][%s]%s", new Object[]{tag, type, msg}));
        }
    }

    public static void e(String tag, String type, int msg) {
        if (!mode_for_release) {
            Log.e(TAG, String.format("[%s][%s]%d", new Object[]{tag, type, Integer.valueOf(msg)}));
        }
    }

    public static void e(String tag, String type, boolean msg) {
        if (!mode_for_release) {
            String str = "[%s][%s]%d";
            Object[] objArr = new Object[3];
            objArr[0] = tag;
            objArr[1] = type;
            objArr[2] = msg ? "true" : "false";
            Log.e(TAG, String.format(str, objArr));
        }
    }
}
