package com.letv.plugin.pluginloader.util;

import android.util.Log;

public class JLog {
    private static final String TAG = "JAR";
    private static final String TAG_APK = "apk";

    public static void log(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void logApk(String msg) {
        Log.i(TAG_APK, msg);
    }

    public static void eApk(String msg) {
        Log.e(TAG_APK, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void d(Class clazz, String msg) {
        Log.d(TAG, clazz.getSimpleName() + "---" + msg);
    }
}
