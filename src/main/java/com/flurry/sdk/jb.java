package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import com.flurry.sdk.jf.a;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class jb implements a {
    private static jb a;
    private static final String b = jb.class.getSimpleName();
    private final Map<Context, iz> c = new WeakHashMap();
    private final jc d = new jc();
    private final Object e = new Object();
    private long f;
    private long g;
    private iz h;
    private hw<jd> i = new hw<jd>(this) {
        final /* synthetic */ jb a;

        {
            this.a = r1;
        }

        public void a(jd jdVar) {
            this.a.i();
        }
    };
    private hw<hq> j = new hw<hq>(this) {
        final /* synthetic */ jb a;

        {
            this.a = r1;
        }

        public void a(hq hqVar) {
            switch (hqVar.b) {
                case kStarted:
                    ib.a(3, jb.b, "Automatic onStartSession for context:" + hqVar.a);
                    this.a.e(hqVar.a);
                    return;
                case kStopped:
                    ib.a(3, jb.b, "Automatic onEndSession for context:" + hqVar.a);
                    this.a.d(hqVar.a);
                    return;
                case kDestroyed:
                    ib.a(3, jb.b, "Automatic onEndSession (destroyed) for context:" + hqVar.a);
                    this.a.d(hqVar.a);
                    return;
                default:
                    return;
            }
        }
    };

    public static synchronized jb a() {
        jb jbVar;
        synchronized (jb.class) {
            if (a == null) {
                a = new jb();
            }
            jbVar = a;
        }
        return jbVar;
    }

    public static synchronized void b() {
        synchronized (jb.class) {
            if (a != null) {
                hx.a().a(a.i);
                hx.a().a(a.j);
                je.a().b("ContinueSessionMillis", a);
            }
            a = null;
        }
    }

    private jb() {
        jf a = je.a();
        this.f = 0;
        this.g = ((Long) a.a("ContinueSessionMillis")).longValue();
        a.a("ContinueSessionMillis", (a) this);
        ib.a(4, b, "initSettings, ContinueSessionMillis = " + this.g);
        hx.a().a("com.flurry.android.sdk.ActivityLifecycleEvent", this.j);
        hx.a().a("com.flurry.android.sdk.FlurrySessionTimerEvent", this.i);
    }

    public long c() {
        return this.f;
    }

    public synchronized int d() {
        return this.c.size();
    }

    public iz e() {
        iz izVar;
        synchronized (this.e) {
            izVar = this.h;
        }
        return izVar;
    }

    private void a(iz izVar) {
        synchronized (this.e) {
            this.h = izVar;
        }
    }

    private void b(iz izVar) {
        synchronized (this.e) {
            if (this.h == izVar) {
                this.h = null;
            }
        }
    }

    public synchronized void a(Context context) {
        if (context instanceof Activity) {
            if (hr.a().c()) {
                ib.a(3, b, "bootstrap for context:" + context);
                e(context);
            }
        }
    }

    public synchronized void b(Context context) {
        if (!(hr.a().c() && (context instanceof Activity))) {
            ib.a(3, b, "Manual onStartSession for context:" + context);
            e(context);
        }
    }

    public synchronized void c(Context context) {
        if (!(hr.a().c() && (context instanceof Activity))) {
            ib.a(3, b, "Manual onEndSession for context:" + context);
            d(context);
        }
    }

    public synchronized boolean f() {
        boolean z;
        if (e() == null) {
            ib.a(2, b, "Session not found. No active session");
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    public synchronized void g() {
        for (Entry entry : this.c.entrySet()) {
            ja jaVar = new ja();
            jaVar.a = new WeakReference(entry.getKey());
            jaVar.b = (iz) entry.getValue();
            jaVar.c = ja.a.PAUSE;
            jaVar.d = ha.a().d();
            jaVar.b();
        }
        this.c.clear();
        hn.a().b(new jp(this) {
            final /* synthetic */ jb a;

            {
                this.a = r1;
            }

            public void a() {
                this.a.i();
            }
        });
    }

    private synchronized void e(Context context) {
        if (((iz) this.c.get(context)) == null) {
            ja jaVar;
            this.d.a();
            iz e = e();
            if (e == null) {
                e = new iz();
                ib.e(b, "Flurry session started for context:" + context);
                jaVar = new ja();
                jaVar.a = new WeakReference(context);
                jaVar.b = e;
                jaVar.c = ja.a.START;
                jaVar.b();
            }
            this.c.put(context, e);
            a(e);
            ib.e(b, "Flurry session resumed for context:" + context);
            jaVar = new ja();
            jaVar.a = new WeakReference(context);
            jaVar.b = e;
            jaVar.c = ja.a.RESUME;
            jaVar.b();
            this.f = 0;
        } else if (hr.a().c()) {
            ib.a(3, b, "Session already started with context:" + context);
        } else {
            ib.e(b, "Session already started with context:" + context);
        }
    }

    synchronized void d(Context context) {
        iz izVar = (iz) this.c.remove(context);
        if (izVar != null) {
            ib.e(b, "Flurry session paused for context:" + context);
            ja jaVar = new ja();
            jaVar.a = new WeakReference(context);
            jaVar.b = izVar;
            jaVar.d = ha.a().d();
            jaVar.c = ja.a.PAUSE;
            jaVar.b();
            if (d() == 0) {
                this.d.a(this.g);
                this.f = System.currentTimeMillis();
            } else {
                this.f = 0;
            }
        } else if (hr.a().c()) {
            ib.a(3, b, "Session cannot be ended, session not found for context:" + context);
        } else {
            ib.e(b, "Session cannot be ended, session not found for context:" + context);
        }
    }

    private synchronized void i() {
        int d = d();
        if (d > 0) {
            ib.a(5, b, "Session cannot be finalized, sessionContextCount:" + d);
        } else {
            final iz e = e();
            if (e == null) {
                ib.a(5, b, "Session cannot be finalized, current session not found");
            } else {
                ib.e(b, "Flurry session ended");
                ja jaVar = new ja();
                jaVar.b = e;
                jaVar.c = ja.a.END;
                jaVar.d = ha.a().d();
                jaVar.b();
                hn.a().b(new jp(this) {
                    final /* synthetic */ jb b;

                    public void a() {
                        this.b.b(e);
                    }
                });
            }
        }
    }

    public void a(String str, Object obj) {
        if (str.equals("ContinueSessionMillis")) {
            this.g = ((Long) obj).longValue();
            ib.a(4, b, "onSettingUpdate, ContinueSessionMillis = " + this.g);
            return;
        }
        ib.a(6, b, "onSettingUpdate internal error!");
    }
}
