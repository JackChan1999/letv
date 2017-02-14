package cn.com.iresearch.vvtracker.a;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

public final class d {
    private ThreadPoolExecutor a;
    private int b;
    private int c;
    private long d;

    private d(int i, int i2) {
        this.b = i;
        this.c = i2;
        this.d = 5;
    }

    public final synchronized void a(Runnable runnable) {
        if (runnable != null) {
            try {
                if (this.a == null || this.a.isShutdown()) {
                    this.a = new ThreadPoolExecutor(this.b, this.c, this.d, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), Executors.defaultThreadFactory(), new AbortPolicy());
                }
                this.a.execute(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
