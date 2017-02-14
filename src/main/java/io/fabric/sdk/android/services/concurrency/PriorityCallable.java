package io.fabric.sdk.android.services.concurrency;

import java.util.concurrent.Callable;

public abstract class PriorityCallable<V> extends PriorityTask implements Callable<V> {
}
