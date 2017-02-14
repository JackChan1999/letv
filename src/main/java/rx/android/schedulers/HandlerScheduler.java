package rx.android.schedulers;

import android.os.Handler;
import rx.Scheduler.Worker;

@Deprecated
public final class HandlerScheduler extends LooperScheduler {
    public /* bridge */ /* synthetic */ Worker createWorker() {
        return super.createWorker();
    }

    @Deprecated
    public static HandlerScheduler from(Handler handler) {
        if (handler != null) {
            return new HandlerScheduler(handler);
        }
        throw new NullPointerException("handler == null");
    }

    private HandlerScheduler(Handler handler) {
        super(handler);
    }
}
