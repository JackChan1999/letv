package cn.jpush.b.a.a;

import cn.jpush.b.a.b.p;
import cn.jpush.b.a.c.a;
import cn.jpush.b.a.c.c;
import com.google.protobuf.jpush.j;

public final class b extends g {
    private static final String[] z;
    int a;
    p b;

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
        r13 = 4;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r5 = new java.lang.String[r13];
        r4 = "y}I~cSAa_r\u0014)\fvP[pCeMX>";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000d:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006b;
    L_0x0014:
        r9 = r1;
    L_0x0015:
        r10 = r4;
        r11 = r9;
        r15 = r8;
        r8 = r4;
        r4 = r15;
    L_0x001a:
        r14 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x0060;
            case 1: goto L_0x0063;
            case 2: goto L_0x0066;
            case 3: goto L_0x0068;
            default: goto L_0x0021;
        };
    L_0x0021:
        r12 = 6;
    L_0x0022:
        r12 = r12 ^ r14;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r4 != 0) goto L_0x002e;
    L_0x002a:
        r8 = r10;
        r11 = r9;
        r9 = r4;
        goto L_0x001a;
    L_0x002e:
        r8 = r4;
        r4 = r10;
    L_0x0030:
        if (r8 > r9) goto L_0x0015;
    L_0x0032:
        r8 = new java.lang.String;
        r8.<init>(r4);
        r4 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0051;
            case 2: goto L_0x005b;
            default: goto L_0x003e;
        };
    L_0x003e:
        r6[r5] = r4;
        r0 = "\u0002\u0019$";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000d;
    L_0x0047:
        r6[r5] = r4;
        r0 = "v\\a\fnGU`\f+\u0002";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000d;
    L_0x0051:
        r6[r5] = r4;
        r4 = 3;
        r0 = "ky$niFM$`cLSpD&\u000f\u0014";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000d;
    L_0x005b:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x0060:
        r12 = 34;
        goto L_0x0022;
    L_0x0063:
        r12 = 52;
        goto L_0x0022;
    L_0x0066:
        r12 = r13;
        goto L_0x0022;
    L_0x0068:
        r12 = 44;
        goto L_0x0022;
    L_0x006b:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.b.<clinit>():void");
    }

    public b(long j, p pVar) {
        super(1, 100, j);
        this.b = pVar;
    }

    public final p a() {
        return this.b;
    }

    public final void b() {
        if (this.b != null) {
            byte[] ae = this.b.a().ae();
            this.a = ae.length;
            b(this.a);
            a(ae);
        }
    }

    public final void c() {
        new StringBuilder(z[2]).append(c.a(this.e.c()));
        this.a = this.f.getShort();
        new StringBuilder(z[3]).append(this.a);
        try {
            this.b = new p(a.a(this.f));
        } catch (j e) {
            e.printStackTrace();
        }
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.b == null ? "" : this.b.toString()).append(z[1]).append(super.toString()).toString();
    }
}
