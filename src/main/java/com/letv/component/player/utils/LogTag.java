package com.letv.component.player.utils;

import android.util.Log;

public class LogTag {
    private static boolean DEBUG = false;
    private static final String TAG = "Letv_player";

    public static void setDebugMode(boolean debug) {
        DEBUG = debug;
    }

    public static void d(String info) {
        if (DEBUG) {
            Log.d(TAG, info);
        }
    }

    public static void d(String tag, String info) {
        if (DEBUG) {
            Log.d(tag, info);
        }
    }

    public static void i(String info) {
        if (DEBUG) {
            Log.i(TAG, info);
        }
    }

    public static void i(String tag, String info) {
        if (DEBUG) {
            Log.i(tag, info);
        }
    }

    public static void v(String info) {
        if (DEBUG) {
            Log.v(TAG, info);
        }
    }

    public static void v(String tag, String info) {
        if (DEBUG) {
            Log.v(tag, info);
        }
    }

    public static void w(String info) {
        if (DEBUG) {
            Log.w(TAG, info);
        }
    }

    public static void w(String tag, String info) {
        if (DEBUG) {
            Log.w(tag, info);
        }
    }

    public static void e(String info) {
        if (DEBUG) {
            Log.e(TAG, info);
        }
    }

    public static void error(String tag, String info) {
        if (DEBUG) {
            Log.e(tag, info);
        }
    }
}
