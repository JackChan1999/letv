package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.ac;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.t;
import com.google.gson.jpush.y;
import com.google.gson.jpush.z;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class h extends a {
    private static final Reader a = new i();
    private static final Object b = new Object();
    private static final String[] z;
    private final List<Object> c;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = "C)(9,\u0014*.m";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x0079;
    L_0x0013:
        r9 = r1;
    L_0x0014:
        r10 = r4;
        r11 = r9;
        r14 = r8;
        r8 = r4;
        r4 = r14;
    L_0x0019:
        r13 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x006d;
            case 1: goto L_0x0070;
            case 2: goto L_0x0073;
            case 3: goto L_0x0076;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 12;
    L_0x0022:
        r12 = r12 ^ r13;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r4 != 0) goto L_0x002e;
    L_0x002a:
        r8 = r10;
        r11 = r9;
        r9 = r4;
        goto L_0x0019;
    L_0x002e:
        r8 = r4;
        r4 = r10;
    L_0x0030:
        if (r8 > r9) goto L_0x0014;
    L_0x0032:
        r8 = new java.lang.String;
        r8.<init>(r4);
        r4 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0050;
            case 2: goto L_0x005a;
            default: goto L_0x003e;
        };
    L_0x003e:
        r6[r5] = r4;
        r0 = "&3-(o\u0017.9m";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r6[r5] = r4;
        r0 = ")82#^\u0006*9(~C\".mo\u000f$.(h";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0050:
        r6[r5] = r4;
        r4 = 3;
        r0 = ")\u0018\u0012\u0003,\u0005$//e\u00078}\u0003m-k<#hC\"3+e\r\")$i\u0010q}";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000c;
    L_0x005a:
        r6[r5] = r4;
        z = r7;
        r0 = new com.google.gson.jpush.internal.a.i;
        r0.<init>();
        a = r0;
        r0 = new java.lang.Object;
        r0.<init>();
        b = r0;
        return;
    L_0x006d:
        r12 = 99;
        goto L_0x0022;
    L_0x0070:
        r12 = 75;
        goto L_0x0022;
    L_0x0073:
        r12 = 93;
        goto L_0x0022;
    L_0x0076:
        r12 = 77;
        goto L_0x0022;
    L_0x0079:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.h.<clinit>():void");
    }

    private void a(c cVar) {
        if (f() != cVar) {
            throw new IllegalStateException(new StringBuilder(z[1]).append(cVar).append(z[0]).append(f()).toString());
        }
    }

    private Object r() {
        return this.c.get(this.c.size() - 1);
    }

    private Object s() {
        return this.c.remove(this.c.size() - 1);
    }

    public final void a() {
        a(c.a);
        this.c.add(((t) r()).iterator());
    }

    public final void b() {
        a(c.b);
        s();
        s();
    }

    public final void c() {
        a(c.c);
        this.c.add(((z) r()).a().iterator());
    }

    public final void close() {
        this.c.clear();
        this.c.add(b);
    }

    public final void d() {
        a(c.d);
        s();
        s();
    }

    public final boolean e() {
        c f = f();
        return (f == c.d || f == c.b) ? false : true;
    }

    public final c f() {
        while (!this.c.isEmpty()) {
            Object r = r();
            if (r instanceof Iterator) {
                boolean z = this.c.get(this.c.size() - 2) instanceof z;
                Iterator it = (Iterator) r;
                if (!it.hasNext()) {
                    return z ? c.d : c.b;
                } else {
                    if (z) {
                        return c.e;
                    }
                    this.c.add(it.next());
                }
            } else if (r instanceof z) {
                return c.c;
            } else {
                if (r instanceof t) {
                    return c.a;
                }
                if (r instanceof ac) {
                    ac acVar = (ac) r;
                    if (acVar.l()) {
                        return c.f;
                    }
                    if (acVar.a()) {
                        return c.h;
                    }
                    if (acVar.k()) {
                        return c.g;
                    }
                    throw new AssertionError();
                } else if (r instanceof y) {
                    return c.i;
                } else {
                    if (r == b) {
                        throw new IllegalStateException(z[2]);
                    }
                    throw new AssertionError();
                }
            }
        }
        return c.j;
    }

    public final String g() {
        a(c.e);
        Entry entry = (Entry) ((Iterator) r()).next();
        this.c.add(entry.getValue());
        return (String) entry.getKey();
    }

    public final String h() {
        c f = f();
        if (f == c.f || f == c.g) {
            return ((ac) s()).c();
        }
        throw new IllegalStateException(new StringBuilder(z[1]).append(c.f).append(z[0]).append(f).toString());
    }

    public final boolean i() {
        a(c.h);
        return ((ac) s()).g();
    }

    public final void j() {
        a(c.i);
        s();
    }

    public final double k() {
        c f = f();
        if (f == c.g || f == c.f) {
            double d = ((ac) r()).d();
            if (p() || !(Double.isNaN(d) || Double.isInfinite(d))) {
                s();
                return d;
            }
            throw new NumberFormatException(new StringBuilder(z[3]).append(d).toString());
        }
        throw new IllegalStateException(new StringBuilder(z[1]).append(c.g).append(z[0]).append(f).toString());
    }

    public final long l() {
        c f = f();
        if (f == c.g || f == c.f) {
            long e = ((ac) r()).e();
            s();
            return e;
        }
        throw new IllegalStateException(new StringBuilder(z[1]).append(c.g).append(z[0]).append(f).toString());
    }

    public final int m() {
        c f = f();
        if (f == c.g || f == c.f) {
            int f2 = ((ac) r()).f();
            s();
            return f2;
        }
        throw new IllegalStateException(new StringBuilder(z[1]).append(c.g).append(z[0]).append(f).toString());
    }

    public final void n() {
        if (f() == c.e) {
            g();
        } else {
            s();
        }
    }

    public final void o() {
        a(c.e);
        Entry entry = (Entry) ((Iterator) r()).next();
        this.c.add(entry.getValue());
        this.c.add(new ac((String) entry.getKey()));
    }

    public final String toString() {
        return getClass().getSimpleName();
    }
}
