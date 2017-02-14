package rx.android;

import android.os.Looper;
import java.util.concurrent.atomic.AtomicBoolean;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

public abstract class MainThreadSubscription implements Subscription {
    private final AtomicBoolean unsubscribed = new AtomicBoolean();

    protected abstract void onUnsubscribe();

    public static void verifyMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Expected to be called on the main thread but was " + Thread.currentThread().getName());
        }
    }

    public final boolean isUnsubscribed() {
        return this.unsubscribed.get();
    }

    public final void unsubscribe() {
        if (!this.unsubscribed.compareAndSet(false, true)) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            onUnsubscribe();
        } else {
            AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
                public void call() {
                    MainThreadSubscription.this.onUnsubscribe();
                }
            });
        }
    }
}
