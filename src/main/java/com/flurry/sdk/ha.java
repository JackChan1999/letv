package com.flurry.sdk;

import android.content.Context;
import com.flurry.sdk.hh.a;

public class ha implements ie {
    private static final String a = ha.class.getSimpleName();

    public static synchronized ha a() {
        ha haVar;
        synchronized (ha.class) {
            haVar = (ha) hn.a().a(ha.class);
        }
        return haVar;
    }

    public void a(Context context) {
        iz.a(hm.class);
        hx.a();
        ji.a();
        je.a();
        hp.a();
        hh.a();
        hb.a();
        hi.a();
        hf.a();
        hb.a();
        hk.a();
        he.a();
        hl.a();
    }

    public void b() {
        hl.b();
        he.b();
        hk.b();
        hb.b();
        hf.b();
        hi.b();
        hb.b();
        hh.b();
        hp.b();
        je.b();
        ji.b();
        hx.b();
        iz.b(hm.class);
    }

    public String c() {
        hm j = j();
        if (j != null) {
            return j.b();
        }
        return null;
    }

    public long d() {
        hm j = j();
        if (j != null) {
            return j.c();
        }
        return 0;
    }

    public long e() {
        hm j = j();
        if (j != null) {
            return j.d();
        }
        return 0;
    }

    public long f() {
        hm j = j();
        if (j != null) {
            return j.e();
        }
        return -1;
    }

    public long g() {
        hm j = j();
        if (j != null) {
            return j.g();
        }
        return 0;
    }

    public long h() {
        hm j = j();
        if (j != null) {
            return j.f();
        }
        return 0;
    }

    public a i() {
        return hh.a().d();
    }

    private hm j() {
        return a(jb.a().e());
    }

    private hm a(iz izVar) {
        if (izVar == null) {
            return null;
        }
        return (hm) izVar.c(hm.class);
    }
}
