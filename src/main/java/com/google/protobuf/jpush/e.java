package com.google.protobuf.jpush;

import android.support.v4.media.TransportMediator;
import java.io.InputStream;
import java.io.OutputStream;

public final class e {
    private static final String[] z;
    private final byte[] a;
    private final int b;
    private int c;
    private final OutputStream d = null;

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
        r12 = 8;
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "?xiRm\u0000mnE e(kPflgf]qljm\u0011k-ddTllgf\u0011K#lmUG9|xD|\u001f|zTi!{(E`-|(Pz)(Ca8afV(8g(P(*diE(-zzPqb";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000d:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0060;
    L_0x0014:
        r8 = r1;
    L_0x0015:
        r9 = r3;
        r10 = r8;
        r14 = r7;
        r7 = r3;
        r3 = r14;
    L_0x001a:
        r13 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0056;
            case 1: goto L_0x0059;
            case 2: goto L_0x005b;
            case 3: goto L_0x005d;
            default: goto L_0x0021;
        };
    L_0x0021:
        r11 = r12;
    L_0x0022:
        r11 = r11 ^ r13;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002e;
    L_0x002a:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x001a;
    L_0x002e:
        r7 = r3;
        r3 = r9;
    L_0x0030:
        if (r7 > r8) goto L_0x0015;
    L_0x0032:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0051;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r4] = r3;
        r0 = "\u001emiU(*ia]m(7(b`#}dU(\"m~Tzl`iAx)f";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000d;
    L_0x0047:
        r5[r4] = r3;
        r3 = 2;
        r0 = "\u001fcaA(*ia]m(7(b`#}dU(\"m~Tzl`iAx)f&";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000d;
    L_0x0051:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0056:
        r11 = 76;
        goto L_0x0022;
    L_0x0059:
        r11 = r12;
        goto L_0x0022;
    L_0x005b:
        r11 = r12;
        goto L_0x0022;
    L_0x005d:
        r11 = 49;
        goto L_0x0022;
    L_0x0060:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.jpush.e.<clinit>():void");
    }

    private e(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.c = i;
        this.b = i + i2;
    }

    public static int a(int i) {
        return i >= 0 ? e(i) : 10;
    }

    public static int a(long j) {
        return (-128 & j) == 0 ? 1 : (-16384 & j) == 0 ? 2 : (-2097152 & j) == 0 ? 3 : (-268435456 & j) == 0 ? 4 : (-34359738368L & j) == 0 ? 5 : (-4398046511104L & j) == 0 ? 6 : (-562949953421312L & j) == 0 ? 7 : (-72057594037927936L & j) == 0 ? 8 : (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static e a(byte[] bArr, int i, int i2) {
        return new e(bArr, 0, i2);
    }

    public static int b(int i, long j) {
        return c(i) + a(j);
    }

    public static int b(int i, c cVar) {
        return c(i) + (e(cVar.a()) + cVar.a());
    }

    public static int b(int i, k kVar) {
        int c = c(i);
        int c2 = kVar.c();
        return c + (c2 + e(c2));
    }

    private void b() {
        if (this.d == null) {
            throw new f();
        }
        this.d.write(this.a, 0, this.c);
        this.c = 0;
    }

    private void b(int i) {
        byte b = (byte) i;
        if (this.c == this.b) {
            b();
        }
        byte[] bArr = this.a;
        int i2 = this.c;
        this.c = i2 + 1;
        bArr[i2] = b;
    }

    private void b(long j) {
        while ((-128 & j) != 0) {
            b((((int) j) & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            j >>>= 7;
        }
        b((int) j);
    }

    private static int c(int i) {
        return e(n.a(i, 0));
    }

    public static int c(int i, int i2) {
        return c(i) + a(i2);
    }

    public static int d(int i, int i2) {
        return c(i) + e(i2);
    }

    private void d(int i) {
        while ((i & -128) != 0) {
            b((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
        }
        b(i);
    }

    private static int e(int i) {
        return (i & -128) == 0 ? 1 : (i & -16384) == 0 ? 2 : (-2097152 & i) == 0 ? 3 : (-268435456 & i) == 0 ? 4 : 5;
    }

    private void e(int i, int i2) {
        d(n.a(i, i2));
    }

    public final int a() {
        if (this.d == null) {
            return this.b - this.c;
        }
        throw new UnsupportedOperationException(z[0]);
    }

    public final void a(int i, int i2) {
        e(i, 0);
        if (i2 >= 0) {
            d(i2);
        } else {
            b((long) i2);
        }
    }

    public final void a(int i, long j) {
        e(i, 0);
        b(j);
    }

    public final void a(int i, c cVar) {
        e(i, 2);
        d(cVar.a());
        int a = cVar.a();
        if (this.b - this.c >= a) {
            cVar.a(this.a, 0, this.c, a);
            this.c = a + this.c;
            return;
        }
        int i2 = this.b - this.c;
        cVar.a(this.a, 0, this.c, i2);
        int i3 = i2 + 0;
        a -= i2;
        this.c = this.b;
        b();
        if (a <= this.b) {
            cVar.a(this.a, i3, 0, a);
            this.c = a;
            return;
        }
        InputStream c = cVar.c();
        if (((long) i3) != c.skip((long) i3)) {
            throw new IllegalStateException(z[2]);
        }
        while (a > 0) {
            i3 = Math.min(a, this.b);
            int read = c.read(this.a, 0, i3);
            if (read != i3) {
                throw new IllegalStateException(z[1]);
            }
            this.d.write(this.a, 0, read);
            a -= read;
        }
    }

    public final void a(int i, k kVar) {
        e(i, 2);
        d(kVar.c());
        kVar.a(this);
    }

    public final void b(int i, int i2) {
        e(i, 0);
        d(i2);
    }
}
