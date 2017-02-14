package com.sina.weibo.sdk.utils;

import android.util.Log;

public class LogUtil {
    public static boolean sIsLogEnable = false;

    public static void enableLog() {
        sIsLogEnable = true;
    }

    public static void disableLog() {
        sIsLogEnable = false;
    }

    public static void d(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.d(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + "(" + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void i(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.i(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + "(" + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void e(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.e(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + "(" + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void w(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.w(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + "(" + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void v(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.v(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + "(" + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static String getStackTraceMsg() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
        return stackTrace.getFileName() + "(" + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName();
    }
}
