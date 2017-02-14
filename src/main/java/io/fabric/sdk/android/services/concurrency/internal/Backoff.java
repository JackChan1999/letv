package io.fabric.sdk.android.services.concurrency.internal;

public interface Backoff {
    long getDelayMillis(int i);
}
