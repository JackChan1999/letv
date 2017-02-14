package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.ac;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.t;
import com.google.gson.jpush.w;
import com.google.gson.jpush.y;
import com.google.gson.jpush.z;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public final class j extends d {
    private static final Writer a = new k();
    private static final ac b = new ac(z[3]);
    private static final String[] z;
    private final List<w> c = new ArrayList();
    private String d;
    private w e = y.a;

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
        r6 = 3;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = "Z\n.\u001f\nv6\u00133Ct*A\u001fK^y\u0000?N00\u000f7C~0\u00158OccA";
        r0 = -1;
        r7 = r5;
        r8 = r5;
        r5 = r1;
    L_0x000d:
        r4 = r4.toCharArray();
        r9 = r4.length;
        if (r9 > r2) goto L_0x007e;
    L_0x0014:
        r10 = r1;
    L_0x0015:
        r11 = r4;
        r12 = r10;
        r15 = r9;
        r9 = r4;
        r4 = r15;
    L_0x001a:
        r14 = r9[r10];
        r13 = r12 % 5;
        switch(r13) {
            case 0: goto L_0x0072;
            case 1: goto L_0x0075;
            case 2: goto L_0x0078;
            case 3: goto L_0x007b;
            default: goto L_0x0021;
        };
    L_0x0021:
        r13 = 42;
    L_0x0023:
        r13 = r13 ^ r14;
        r13 = (char) r13;
        r9[r10] = r13;
        r10 = r12 + 1;
        if (r4 != 0) goto L_0x002f;
    L_0x002b:
        r9 = r11;
        r12 = r10;
        r10 = r4;
        goto L_0x001a;
    L_0x002f:
        r9 = r4;
        r4 = r11;
    L_0x0031:
        if (r9 > r10) goto L_0x0015;
    L_0x0033:
        r9 = new java.lang.String;
        r9.<init>(r4);
        r4 = r9.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            case 1: goto L_0x0051;
            case 2: goto L_0x005b;
            default: goto L_0x003f;
        };
    L_0x003f:
        r7[r5] = r4;
        r0 = "Y7\u0002>G`5\u0004%O0=\u000e2_}<\u000f%";
        r4 = r0;
        r5 = r2;
        r7 = r8;
        r0 = r1;
        goto L_0x000d;
    L_0x0048:
        r7[r5] = r4;
        r0 = "U!\u00114Id<\u0005qE~<A\u001by_\u0017A4Fu4\u0004?^0;\u0014%\ng8\u0012q";
        r4 = r0;
        r5 = r3;
        r7 = r8;
        r0 = r2;
        goto L_0x000d;
    L_0x0051:
        r7[r5] = r4;
        r0 = "s5\u000e\"Ot";
        r4 = r0;
        r5 = r6;
        r7 = r8;
        r0 = r3;
        goto L_0x000d;
    L_0x005b:
        r7[r5] = r4;
        z = r8;
        r0 = new com.google.gson.jpush.internal.a.k;
        r0.<init>();
        a = r0;
        r0 = new com.google.gson.jpush.ac;
        r1 = z;
        r1 = r1[r6];
        r0.<init>(r1);
        b = r0;
        return;
    L_0x0072:
        r13 = 16;
        goto L_0x0023;
    L_0x0075:
        r13 = 89;
        goto L_0x0023;
    L_0x0078:
        r13 = 97;
        goto L_0x0023;
    L_0x007b:
        r13 = 81;
        goto L_0x0023;
    L_0x007e:
        r10 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.j.<clinit>():void");
    }

    public j() {
        super(a);
    }

    private void a(w wVar) {
        if (this.d != null) {
            if (!(wVar instanceof y) || i()) {
                ((z) j()).a(this.d, wVar);
            }
            this.d = null;
        } else if (this.c.isEmpty()) {
            this.e = wVar;
        } else {
            w j = j();
            if (j instanceof t) {
                ((t) j).a(wVar);
                return;
            }
            throw new IllegalStateException();
        }
    }

    private w j() {
        return (w) this.c.get(this.c.size() - 1);
    }

    public final d a(long j) {
        a(new ac(Long.valueOf(j)));
        return this;
    }

    public final d a(Number number) {
        if (number == null) {
            return f();
        }
        if (!g()) {
            double doubleValue = number.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                throw new IllegalArgumentException(new StringBuilder(z[0]).append(number).toString());
            }
        }
        a(new ac(number));
        return this;
    }

    public final d a(String str) {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof z) {
            this.d = str;
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public final d a(boolean z) {
        a(new ac(Boolean.valueOf(z)));
        return this;
    }

    public final w a() {
        if (this.c.isEmpty()) {
            return this.e;
        }
        throw new IllegalStateException(new StringBuilder(z[2]).append(this.c).toString());
    }

    public final d b() {
        w tVar = new t();
        a(tVar);
        this.c.add(tVar);
        return this;
    }

    public final d b(String str) {
        if (str == null) {
            return f();
        }
        a(new ac(str));
        return this;
    }

    public final d c() {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof t) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public final void close() {
        if (this.c.isEmpty()) {
            this.c.add(b);
            return;
        }
        throw new IOException(z[1]);
    }

    public final d d() {
        w zVar = new z();
        a(zVar);
        this.c.add(zVar);
        return this;
    }

    public final d e() {
        if (this.c.isEmpty() || this.d != null) {
            throw new IllegalStateException();
        } else if (j() instanceof z) {
            this.c.remove(this.c.size() - 1);
            return this;
        } else {
            throw new IllegalStateException();
        }
    }

    public final d f() {
        a(y.a);
        return this;
    }

    public final void flush() {
    }
}
