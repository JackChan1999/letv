package com.flurry.sdk;

import android.content.Context;
import com.flurry.android.FlurryEventRecordStatus;
import java.util.Map;

public class fu implements ie {
    private gm a;
    private gv b;
    private gq c;

    public static synchronized fu a() {
        fu fuVar;
        synchronized (fu.class) {
            fuVar = (fu) hn.a().a(fu.class);
        }
        return fuVar;
    }

    public void a(Context context) {
        iz.a(gy.class);
        this.a = new gm();
        this.b = new gv();
        this.c = new gq();
    }

    public void b() {
        if (this.c != null) {
            this.c.c();
            this.c = null;
        }
        if (this.b != null) {
            this.b.a();
            this.b = null;
        }
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        iz.b(gy.class);
    }

    public gm c() {
        return this.a;
    }

    public gv d() {
        return this.b;
    }

    public gq e() {
        return this.c;
    }

    public void f() {
        gy h = h();
        if (h != null) {
            h.a();
        }
    }

    public FlurryEventRecordStatus a(String str) {
        gy h = h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.a(str, null, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus a(String str, Map<String, String> map) {
        gy h = h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.a(str, map, false);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus a(String str, boolean z) {
        gy h = h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.a(str, null, z);
        }
        return flurryEventRecordStatus;
    }

    public FlurryEventRecordStatus a(String str, Map<String, String> map, boolean z) {
        gy h = h();
        FlurryEventRecordStatus flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        if (h != null) {
            return h.a(str, map, z);
        }
        return flurryEventRecordStatus;
    }

    public void b(String str) {
        gy h = h();
        if (h != null) {
            h.a(str, null);
        }
    }

    public void b(String str, Map<String, String> map) {
        gy h = h();
        if (h != null) {
            h.a(str, map);
        }
    }

    @Deprecated
    public void a(String str, String str2, String str3) {
        StackTraceElement[] stackTraceElementArr;
        Object stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length <= 2) {
            Object obj = stackTrace;
        } else {
            stackTraceElementArr = new StackTraceElement[(stackTrace.length - 2)];
            System.arraycopy(stackTrace, 2, stackTraceElementArr, 0, stackTraceElementArr.length);
        }
        Throwable th = new Throwable(str2);
        th.setStackTrace(stackTraceElementArr);
        gy h = h();
        if (h != null) {
            h.a(str, str2, str3, th);
        }
    }

    public void a(String str, String str2, Throwable th) {
        gy h = h();
        if (h != null) {
            h.a(str, str2, th.getClass().getName(), th);
        }
    }

    public void c(String str) {
        gy h = h();
        if (h != null) {
            h.a(str, null, false);
        }
    }

    public void c(String str, Map<String, String> map) {
        gy h = h();
        if (h != null) {
            h.a(str, map, false);
        }
    }

    public void g() {
        gy h = h();
        if (h != null) {
            h.b();
        }
    }

    private gy h() {
        return a(jb.a().e());
    }

    private gy a(iz izVar) {
        if (izVar == null) {
            return null;
        }
        return (gy) izVar.c(gy.class);
    }
}
