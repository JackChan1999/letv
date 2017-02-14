package com.flurry.sdk;

import android.content.Context;
import android.os.SystemClock;
import com.flurry.sdk.ja.a;
import java.lang.ref.WeakReference;

public class hm {
    private static final String a = hm.class.getSimpleName();
    private final hw<ja> b = new hw<ja>(this) {
        final /* synthetic */ hm a;

        {
            this.a = r1;
        }

        public void a(ja jaVar) {
            if (this.a.c == null || jaVar.b == this.a.c.get()) {
                switch (jaVar.c) {
                    case START:
                        this.a.a(jaVar.b, (Context) jaVar.a.get());
                        return;
                    case RESUME:
                        this.a.a((Context) jaVar.a.get());
                        return;
                    case PAUSE:
                        this.a.b((Context) jaVar.a.get());
                        return;
                    case END:
                        hx.a().b("com.flurry.android.sdk.FlurrySessionEvent", this.a.b);
                        this.a.a();
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private WeakReference<iz> c;
    private volatile long d = 0;
    private volatile long e = 0;
    private volatile long f = -1;
    private volatile long g = 0;
    private volatile long h = 0;

    public hm() {
        hx.a().a("com.flurry.android.sdk.FlurrySessionEvent", this.b);
    }

    public void a(iz izVar, Context context) {
        this.c = new WeakReference(izVar);
        this.d = System.currentTimeMillis();
        this.e = SystemClock.elapsedRealtime();
        b(izVar, context);
        hn.a().b(new jp(this) {
            final /* synthetic */ hm a;

            {
                this.a = r1;
            }

            public void a() {
                hf.a().c();
            }
        });
    }

    private void b(iz izVar, Context context) {
        if (izVar == null || context == null) {
            ib.a(3, a, "Flurry session id cannot be created.");
            return;
        }
        ib.a(3, a, "Flurry session id started:" + this.d);
        ja jaVar = new ja();
        jaVar.a = new WeakReference(context);
        jaVar.b = izVar;
        jaVar.c = a.SESSION_ID_CREATED;
        jaVar.b();
    }

    public void a(Context context) {
        long c = jb.a().c();
        if (c > 0) {
            this.g = (System.currentTimeMillis() - c) + this.g;
        }
    }

    public void b(Context context) {
        this.f = SystemClock.elapsedRealtime() - this.e;
    }

    public void a() {
    }

    public String b() {
        return Long.toString(this.d);
    }

    public long c() {
        return this.d;
    }

    public long d() {
        return this.e;
    }

    public long e() {
        return this.f;
    }

    public long f() {
        return this.g;
    }

    public synchronized long g() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.e;
        if (elapsedRealtime <= this.h) {
            elapsedRealtime = this.h + 1;
            this.h = elapsedRealtime;
        }
        this.h = elapsedRealtime;
        return this.h;
    }
}
