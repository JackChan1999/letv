package io.fabric.sdk.android;

import android.util.Log;

public class DefaultLogger implements Logger {
    private int logLevel;

    public DefaultLogger(int logLevel) {
        this.logLevel = logLevel;
    }

    public DefaultLogger() {
        this.logLevel = 4;
    }

    public boolean isLoggable(String tag, int level) {
        return this.logLevel <= level;
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void d(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 3)) {
            Log.d(tag, text, throwable);
        }
    }

    public void v(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 2)) {
            Log.v(tag, text, throwable);
        }
    }

    public void i(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 4)) {
            Log.i(tag, text, throwable);
        }
    }

    public void w(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 5)) {
            Log.w(tag, text, throwable);
        }
    }

    public void e(String tag, String text, Throwable throwable) {
        if (isLoggable(tag, 6)) {
            Log.e(tag, text, throwable);
        }
    }

    public void d(String tag, String text) {
        d(tag, text, null);
    }

    public void v(String tag, String text) {
        v(tag, text, null);
    }

    public void i(String tag, String text) {
        i(tag, text, null);
    }

    public void w(String tag, String text) {
        w(tag, text, null);
    }

    public void e(String tag, String text) {
        e(tag, text, null);
    }

    public void log(int priority, String tag, String msg) {
        log(priority, tag, msg, false);
    }

    public void log(int priority, String tag, String msg, boolean forceLog) {
        if (forceLog || isLoggable(tag, priority)) {
            Log.println(priority, tag, msg);
        }
    }
}
