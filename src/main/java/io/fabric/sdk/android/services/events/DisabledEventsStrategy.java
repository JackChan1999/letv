package io.fabric.sdk.android.services.events;

public class DisabledEventsStrategy<T> implements EventsStrategy<T> {
    public void sendEvents() {
    }

    public void deleteAllEvents() {
    }

    public void recordEvent(T t) {
    }

    public void cancelTimeBasedFileRollOver() {
    }

    public void scheduleTimeBasedRollOverIfNeeded() {
    }

    public boolean rollFileOver() {
        return false;
    }

    public FilesSender getFilesSender() {
        return null;
    }
}
