package cn.jpush.b.a.a;

import cn.jpush.android.util.g;
import cn.jpush.b.a.c.a;
import java.nio.ByteBuffer;

public final class j extends h {
    private static final String[] z;
    int a;
    long b;
    String c;

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
        r4 = "\u0007jnaN=@nBH/OV2\u0010|Jxui%Wn(";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006e;
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
            case 0: goto L_0x0062;
            case 1: goto L_0x0065;
            case 2: goto L_0x0068;
            case 3: goto L_0x006b;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 61;
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
            case 0: goto L_0x0048;
            case 1: goto L_0x0052;
            case 2: goto L_0x005d;
            default: goto L_0x003e;
        };
    L_0x003e:
        r6[r5] = r4;
        r0 = "p\u0007faZ\u001fHefX2S1";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0048:
        r6[r5] = r4;
        r0 = "|\n+";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0052:
        r6[r5] = r4;
        r4 = 3;
        r0 = "p\u0007faZ\u0015C1";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000c;
    L_0x005d:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x0062:
        r12 = 92;
        goto L_0x0022;
    L_0x0065:
        r12 = 39;
        goto L_0x0022;
    L_0x0068:
        r12 = 11;
        goto L_0x0022;
    L_0x006b:
        r12 = 18;
        goto L_0x0022;
    L_0x006e:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.j.<clinit>():void");
    }

    public j(e eVar, ByteBuffer byteBuffer) {
        super(eVar, byteBuffer);
    }

    public final int a() {
        return this.a;
    }

    public final void b() {
        super.b();
        a(this.a);
        a(this.b);
        a(this.c);
    }

    public final void c() {
        super.c();
        ByteBuffer byteBuffer = this.f;
        this.a = g.c(byteBuffer, this).byteValue();
        this.b = g.d(byteBuffer, this);
        this.c = a.a(byteBuffer, this);
    }

    public final long g() {
        return this.b;
    }

    public final String h() {
        return this.c;
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.a).append(z[3]).append(this.b).append(z[1]).append(this.c).append(z[2]).append(super.toString()).toString();
    }
}
