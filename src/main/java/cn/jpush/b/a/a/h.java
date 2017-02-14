package cn.jpush.b.a.a;

import cn.jpush.android.util.g;
import cn.jpush.b.a.c.a;
import java.nio.ByteBuffer;

public abstract class h extends f {
    private static final String[] z;
    public int g;
    public String h;

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
        r2 = 1;
        r1 = 0;
        r0 = 3;
        r4 = new java.lang.String[r0];
        r3 = "A{<ZN\u0002)c";
        r0 = -1;
        r5 = r4;
        r6 = r4;
        r4 = r1;
    L_0x000b:
        r3 = r3.toCharArray();
        r7 = r3.length;
        if (r7 > r2) goto L_0x0061;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r3;
        r10 = r8;
        r13 = r7;
        r7 = r3;
        r3 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x0055;
            case 1: goto L_0x0058;
            case 2: goto L_0x005b;
            case 3: goto L_0x005e;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 60;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r3 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r3;
        goto L_0x0018;
    L_0x002d:
        r7 = r3;
        r3 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r3);
        r3 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            case 1: goto L_0x0050;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r4] = r3;
        r0 = "Mvy";
        r3 = r0;
        r4 = r2;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r4] = r3;
        r3 = 2;
        r0 = "6\u0011\u000bMO\u001d47[Y0{t\b_\u0002?<\u0012";
        r4 = r3;
        r5 = r6;
        r3 = r0;
        r0 = r2;
        goto L_0x000b;
    L_0x0050:
        r5[r4] = r3;
        z = r6;
        return;
    L_0x0055:
        r11 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x0021;
    L_0x0058:
        r11 = 91;
        goto L_0x0021;
    L_0x005b:
        r11 = 89;
        goto L_0x0021;
    L_0x005e:
        r11 = 40;
        goto L_0x0021;
    L_0x0061:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.h.<clinit>():void");
    }

    public h(int i, int i2, long j, long j2, int i3, String str) {
        super(false, 2, 10, j, -1, j2);
        this.g = i3;
        this.h = str;
    }

    public h(e eVar, ByteBuffer byteBuffer) {
        super(false, eVar, byteBuffer);
    }

    protected void b() {
        if (this.g >= 0) {
            b(this.g);
            if (this.g > 0) {
                a(this.h);
            }
        }
    }

    protected void c() {
        int d = d();
        if (d != 19 && d != 3 && d != 100) {
            this.g = g.b(this.f, this);
            if (this.g > 0) {
                this.h = a.a(this.f, this);
            }
        }
    }

    public String toString() {
        return new StringBuilder(z[2]).append(this.g).append(this.h == null ? "" : new StringBuilder(z[0]).append(this.h).toString()).append(z[1]).append(super.toString()).toString();
    }
}
