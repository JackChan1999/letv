package io.fabric.sdk.android;

public class SilentLogger implements Logger {
    private int logLevel = 7;

    public boolean isLoggable(String tag, int level) {
        return false;
    }

    public void d(String tag, String text, Throwable throwable) {
    }

    public void v(String tag, String text, Throwable throwable) {
    }

    public void i(String tag, String text, Throwable throwable) {
    }

    public void w(String tag, String text, Throwable throwable) {
    }

    public void e(String tag, String text, Throwable throwable) {
    }

    public void d(String tag, String text) {
    }

    public void v(String tag, String text) {
    }

    public void i(String tag, String text) {
    }

    public void w(String tag, String text) {
    }

    public void e(String tag, String text) {
    }

    public void log(int priority, String tag, String msg) {
    }

    public void log(int priority, String tag, String msg, boolean forceLog) {
    }

    public int getLogLevel() {
        return this.logLevel;
    }

    public void setLogLevel(int logLevel) {
    }
}
