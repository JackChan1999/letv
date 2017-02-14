package io.fabric.sdk.android.services.common;

public abstract class Crash {
    private final String sessionId;

    public static class FatalException extends Crash {
        public FatalException(String sessionId) {
            super(sessionId);
        }
    }

    public static class LoggedException extends Crash {
        public LoggedException(String sessionId) {
            super(sessionId);
        }
    }

    public Crash(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
