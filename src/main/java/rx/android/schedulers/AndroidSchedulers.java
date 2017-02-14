package rx.android.schedulers;

import android.os.Looper;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;

public final class AndroidSchedulers {
    private static final AndroidSchedulers INSTANCE = new AndroidSchedulers();
    private final Scheduler mainThreadScheduler;

    private AndroidSchedulers() {
        Scheduler main = RxAndroidPlugins.getInstance().getSchedulersHook().getMainThreadScheduler();
        if (main != null) {
            this.mainThreadScheduler = main;
        } else {
            this.mainThreadScheduler = new LooperScheduler(Looper.getMainLooper());
        }
    }

    public static Scheduler mainThread() {
        return INSTANCE.mainThreadScheduler;
    }

    public static Scheduler from(Looper looper) {
        if (looper != null) {
            return new LooperScheduler(looper);
        }
        throw new NullPointerException("looper == null");
    }
}
