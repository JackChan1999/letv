package io.fabric.sdk.android.services.concurrency;

public interface PriorityProvider<T> extends Comparable<T> {
    Priority getPriority();
}
