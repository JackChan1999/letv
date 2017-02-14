package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryThreadPoolExecutor;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils {
    private static final long DEFAULT_TERMINATION_TIMEOUT = 2;

    private ExecutorUtils() {
    }

    public static ExecutorService buildSingleThreadExecutorService(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor(getNamedThreadFactory(name));
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static RetryThreadPoolExecutor buildRetryThreadPoolExecutor(String name, int corePoolSize, RetryPolicy retryPolicy, Backoff backoff) {
        RetryThreadPoolExecutor executor = new RetryThreadPoolExecutor(corePoolSize, getNamedThreadFactory(name), retryPolicy, backoff);
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String name) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(name));
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static final ThreadFactory getNamedThreadFactory(final String threadNameTemplate) {
        final AtomicLong count = new AtomicLong(1);
        return new ThreadFactory() {
            public Thread newThread(final Runnable runnable) {
                Thread thread = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable() {
                    public void onRun() {
                        runnable.run();
                    }
                });
                thread.setName(threadNameTemplate + count.getAndIncrement());
                return thread;
            }
        };
    }

    private static final void addDelayedShutdownHook(String serviceName, ExecutorService service) {
        addDelayedShutdownHook(serviceName, service, 2, TimeUnit.SECONDS);
    }

    public static final void addDelayedShutdownHook(String serviceName, ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        final String str = serviceName;
        final ExecutorService executorService = service;
        final long j = terminationTimeout;
        final TimeUnit timeUnit2 = timeUnit;
        Runtime.getRuntime().addShutdownHook(new Thread(new BackgroundPriorityRunnable() {
            public void onRun() {
                try {
                    Fabric.getLogger().d(Fabric.TAG, "Executing shutdown hook for " + str);
                    executorService.shutdown();
                    if (!executorService.awaitTermination(j, timeUnit2)) {
                        Fabric.getLogger().d(Fabric.TAG, str + " did not shut down in the" + " allocated time. Requesting immediate shutdown.");
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    Fabric.getLogger().d(Fabric.TAG, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[]{str}));
                    executorService.shutdownNow();
                }
            }
        }, "Crashlytics Shutdown Hook for " + serviceName));
    }
}
