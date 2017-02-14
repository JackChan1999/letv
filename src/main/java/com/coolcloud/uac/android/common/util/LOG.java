package com.coolcloud.uac.android.common.util;

import android.util.Log;

public class LOG {
    private static String stag = "UAC";

    public static void setTag(String tag) {
        Log.w("LOG", "log tag [" + stag + "] be replaced to [" + tag + "]");
        stag = tag;
    }

    public static void d(String TAG, String msg) {
        Log.d(stag, "[" + TAG + "] " + msg);
    }

    public static void d(String TAG, String msg, Throwable t) {
        Log.d(stag, "[" + TAG + "] " + msg, t);
    }

    public static void i(String tag, String msg) {
        Log.i(stag, "[" + tag + "] " + msg);
    }

    public static void i(String tag, String msg, Throwable t) {
        Log.i(stag, "[" + tag + "] " + msg, t);
    }

    public static void w(String tag, String msg) {
        Log.w(stag, "[" + tag + "] " + msg);
    }

    public static void w(String tag, String msg, Throwable t) {
        Log.w(stag, "[" + tag + "] " + msg, t);
    }

    public static void e(String tag, String msg) {
        Log.e(stag, "[" + tag + "] " + msg);
    }

    public static void e(String tag, String msg, Throwable t) {
        Log.e(stag, "[" + tag + "] " + msg, t);
    }
}
