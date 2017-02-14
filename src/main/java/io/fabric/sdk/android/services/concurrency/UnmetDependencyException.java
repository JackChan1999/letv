package io.fabric.sdk.android.services.concurrency;

public class UnmetDependencyException extends RuntimeException {
    public UnmetDependencyException(String detailMessage) {
        super(detailMessage);
    }

    public UnmetDependencyException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public UnmetDependencyException(Throwable throwable) {
        super(throwable);
    }
}
