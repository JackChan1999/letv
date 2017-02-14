package rx.schedulers;

import rx.Scheduler;
import rx.Scheduler.Worker;

@Deprecated
public final class TrampolineScheduler extends Scheduler {
    private TrampolineScheduler() {
        throw new AssertionError();
    }

    public Worker createWorker() {
        return null;
    }
}
