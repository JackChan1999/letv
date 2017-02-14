package com.tencent.open.a;

import android.os.Environment;
import android.text.TextUtils;
import com.tencent.open.a.d.a;
import com.tencent.open.a.d.c;
import com.tencent.open.a.d.d;
import com.tencent.open.utils.Global;
import java.io.File;

/* compiled from: ProGuard */
public class f {
    public static f a = null;
    protected static final b c = new b(c(), c.s, c.m, c.n, c.h, (long) c.o, 10, c.k, c.t);
    public static final String d = c.a;
    protected a b = new a(c);

    public static f a() {
        if (a == null) {
            synchronized (f.class) {
                if (a == null) {
                    a = new f();
                }
            }
        }
        return a;
    }

    private f() {
    }

    protected void a(int i, String str, String str2, Throwable th) {
        e.a.b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        if (a.a(c.c, i) && this.b != null) {
            this.b.b(i, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        }
    }

    public static final void a(String str, String str2) {
        a().a(1, str, str2, null);
    }

    public static final void b(String str, String str2) {
        a().a(2, str, str2, null);
    }

    public static final void a(String str, String str2, Throwable th) {
        a().a(2, str, str2, th);
    }

    public static final void c(String str, String str2) {
        a().a(4, str, str2, null);
    }

    public static final void d(String str, String str2) {
        a().a(8, str, str2, null);
    }

    public static final void e(String str, String str2) {
        a().a(16, str, str2, null);
    }

    public static final void b(String str, String str2, Throwable th) {
        a().a(16, str, str2, th);
    }

    public static void b() {
        synchronized (f.class) {
            a().d();
            if (a != null) {
                a = null;
            }
        }
    }

    protected static File c() {
        Object obj;
        String packageName = Global.getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            packageName = "default";
        }
        String str = c.i + File.separator + packageName;
        d b = c.b();
        if (b == null || b.c() <= c.l) {
            obj = null;
        } else {
            obj = 1;
        }
        if (obj != null) {
            return new File(Environment.getExternalStorageDirectory(), str);
        }
        return new File(Global.getFilesDir(), str);
    }

    protected void d() {
        if (this.b != null) {
            this.b.a();
            this.b.b();
            this.b = null;
        }
    }
}
