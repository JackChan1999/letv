package io.fabric.sdk.android.services.concurrency.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public abstract class AbstractFuture<V> implements Future<V> {
    private final Sync<V> sync = new Sync();

    static final class Sync<V> extends AbstractQueuedSynchronizer {
        static final int CANCELLED = 4;
        static final int COMPLETED = 2;
        static final int COMPLETING = 1;
        static final int INTERRUPTED = 8;
        static final int RUNNING = 0;
        private static final long serialVersionUID = 0;
        private Throwable exception;
        private V value;

        Sync() {
        }

        protected int tryAcquireShared(int ignored) {
            if (isDone()) {
                return 1;
            }
            return -1;
        }

        protected boolean tryReleaseShared(int finalState) {
            setState(finalState);
            return true;
        }

        V get(long nanos) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
            if (tryAcquireSharedNanos(-1, nanos)) {
                return getValue();
            }
            throw new TimeoutException("Timeout waiting for task.");
        }

        V get() throws CancellationException, ExecutionException, InterruptedException {
            acquireSharedInterruptibly(-1);
            return getValue();
        }

        private V getValue() throws CancellationException, ExecutionException {
            int state = getState();
            switch (state) {
                case 2:
                    if (this.exception == null) {
                        return this.value;
                    }
                    throw new ExecutionException(this.exception);
                case 4:
                case 8:
                    throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.exception);
                default:
                    throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
            }
        }

        boolean isDone() {
            return (getState() & 14) != 0;
        }

        boolean isCancelled() {
            return (getState() & 12) != 0;
        }

        boolean wasInterrupted() {
            return getState() == 8;
        }

        boolean set(V v) {
            return complete(v, null, 2);
        }

        boolean setException(Throwable t) {
            return complete(null, t, 2);
        }

        boolean cancel(boolean interrupt) {
            return complete(null, null, interrupt ? 8 : 4);
        }

        private boolean complete(V v, Throwable t, int finalState) {
            boolean doCompletion = compareAndSetState(0, 1);
            if (doCompletion) {
                this.value = v;
                if ((finalState & 12) != 0) {
                    t = new CancellationException("Future.cancel() was called.");
                }
                this.exception = t;
                releaseShared(finalState);
            } else if (getState() == 1) {
                acquireShared(-1);
            }
            return doCompletion;
        }
    }

    protected AbstractFuture() {
    }

    static final CancellationException cancellationExceptionWithCause(String message, Throwable cause) {
        CancellationException exception = new CancellationException(message);
        exception.initCause(cause);
        return exception;
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        return this.sync.get(unit.toNanos(timeout));
    }

    public V get() throws InterruptedException, ExecutionException {
        return this.sync.get();
    }

    public boolean isDone() {
        return this.sync.isDone();
    }

    public boolean isCancelled() {
        return this.sync.isCancelled();
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        if (!this.sync.cancel(mayInterruptIfRunning)) {
            return false;
        }
        if (mayInterruptIfRunning) {
            interruptTask();
        }
        return true;
    }

    protected void interruptTask() {
    }

    protected final boolean wasInterrupted() {
        return this.sync.wasInterrupted();
    }

    protected boolean set(V value) {
        return this.sync.set(value);
    }

    protected boolean setException(Throwable throwable) {
        if (throwable != null) {
            return this.sync.setException(throwable);
        }
        throw new NullPointerException();
    }
}
