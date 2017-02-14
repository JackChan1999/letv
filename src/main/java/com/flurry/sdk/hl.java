package com.flurry.sdk;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class hl extends ia<ij> {
    private static hl a = null;

    public static synchronized hl a() {
        hl hlVar;
        synchronized (hl.class) {
            if (a == null) {
                a = new hl();
            }
            hlVar = a;
        }
        return hlVar;
    }

    public static synchronized void b() {
        synchronized (hl.class) {
            if (a != null) {
                a.c();
            }
            a = null;
        }
    }

    protected hl() {
        super(hl.class.getName(), 0, 5, 5000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(11, new hy()));
    }
}
