package com.flurry.sdk;

public class ji {
    private static long a = 100;
    private static ji b = null;
    private final jj c = new jj();

    public static synchronized ji a() {
        ji jiVar;
        synchronized (ji.class) {
            if (b == null) {
                b = new ji();
            }
            jiVar = b;
        }
        return jiVar;
    }

    public static synchronized void b() {
        synchronized (ji.class) {
            if (b != null) {
                b.c();
                b = null;
            }
        }
    }

    public ji() {
        this.c.a(a);
        this.c.a(true);
    }

    public synchronized void a(hw<jh> hwVar) {
        hx.a().a("com.flurry.android.sdk.TickEvent", hwVar);
        if (hx.a().b("com.flurry.android.sdk.TickEvent") > 0) {
            this.c.a();
        }
    }

    public synchronized void b(hw<jh> hwVar) {
        hx.a().b("com.flurry.android.sdk.TickEvent", hwVar);
        if (hx.a().b("com.flurry.android.sdk.TickEvent") == 0) {
            this.c.b();
        }
    }

    public synchronized void c() {
        hx.a().a("com.flurry.android.sdk.TickEvent");
        this.c.b();
    }
}
