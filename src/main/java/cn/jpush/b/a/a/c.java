package cn.jpush.b.a.a;

import cn.jpush.b.a.b.p;
import cn.jpush.b.a.c.a;
import com.google.protobuf.jpush.j;
import java.nio.ByteBuffer;

public final class c extends h {
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
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = ":zJ=8\u0017NJ32\u001dP\u001e\u0017w^\u0017";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006b;
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
            case 0: goto L_0x005f;
            case 1: goto L_0x0062;
            case 2: goto L_0x0065;
            case 3: goto L_0x0068;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 87;
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
        r0 = "S\u001aJ";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r6[r5] = r4;
        r0 = "(~'-2\u0000G\u0005\u0011$\u0016jJRw\u0003E\u0005\u000b8\u0010X\u0006E";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0050:
        r6[r5] = r4;
        r4 = 3;
        r0 = "=b&3w<U\u0000\u001a4\u0007";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000c;
    L_0x005a:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x005f:
        r12 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        goto L_0x0022;
    L_0x0062:
        r12 = 55;
        goto L_0x0022;
    L_0x0065:
        r12 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        goto L_0x0022;
    L_0x0068:
        r12 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        goto L_0x0022;
    L_0x006b:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.c.<clinit>():void");
    }

    public c(e eVar, ByteBuffer byteBuffer) {
        super(eVar, byteBuffer);
    }

    public final p a() {
        return this.b;
    }

    public final void b() {
        super.b();
        if (this.b != null) {
            byte[] ae = this.b.a().ae();
            this.a = ae.length;
            new StringBuilder(z[0]).append(this.a);
            b(this.a);
            a(ae);
        }
    }

    public final void c() {
        super.c();
        this.a = this.f.getShort();
        new StringBuilder(z[0]).append(this.a);
        try {
            this.b = new p(a.a(this.f));
        } catch (j e) {
            e.printStackTrace();
        }
    }

    public final String toString() {
        return new StringBuilder(z[2]).append(this.b == null ? z[3] : this.b.toString()).append(z[1]).append(super.toString()).toString();
    }
}
