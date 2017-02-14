package io.fabric.sdk.android.services.concurrency;

public interface Task {
    Throwable getError();

    boolean isFinished();

    void setError(Throwable th);

    void setFinished(boolean z);
}
