package io.fabric.sdk.android.services.settings;

public class SessionSettingsData {
    public final int identifierMask;
    public final int logBufferSize;
    public final int maxChainedExceptionDepth;
    public final int maxCustomExceptionEvents;
    public final int maxCustomKeyValuePairs;
    public final boolean sendSessionWithoutCrash;

    public SessionSettingsData(int logBufferSize, int maxChainedExceptionDepth, int maxCustomExceptionEvents, int maxCustomKeyValuePairs, int identifierMask, boolean sendSessionWithoutCrash) {
        this.logBufferSize = logBufferSize;
        this.maxChainedExceptionDepth = maxChainedExceptionDepth;
        this.maxCustomExceptionEvents = maxCustomExceptionEvents;
        this.maxCustomKeyValuePairs = maxCustomKeyValuePairs;
        this.identifierMask = identifierMask;
        this.sendSessionWithoutCrash = sendSessionWithoutCrash;
    }
}
