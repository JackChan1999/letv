package io.fabric.sdk.android.services.concurrency.internal;

public interface RetryPolicy {
    boolean shouldRetry(int i, Throwable th);
}
