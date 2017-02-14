package io.fabric.sdk.android.services.common;

public class SystemCurrentTimeProvider implements CurrentTimeProvider {
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
