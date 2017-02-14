package io.fabric.sdk.android;

public class InitializationException extends RuntimeException {
    public InitializationException(String detailMessage) {
        super(detailMessage);
    }

    public InitializationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
