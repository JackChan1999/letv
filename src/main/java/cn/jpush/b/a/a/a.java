package cn.jpush.b.a.a;

import cn.jpush.android.util.g;
import java.nio.ByteBuffer;

public final class a extends h {
    private static final String[] z;
    int a;
    int b;
    int c;
    long d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 5;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "clf\u001fQW_h\u0015se\r(Tm]\\p\u0011lLnj\u0019rYCaN";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0060;
            case 1: goto L_0x0063;
            case 2: goto L_0x0066;
            case 3: goto L_0x0068;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 31;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0014\rv\u0000vUH?";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0014\rv\u0000zH\u0017";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0014\rv\u0000~LXvN";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0018\u0000%";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0060:
        r9 = 56;
        goto L_0x001f;
    L_0x0063:
        r9 = 45;
        goto L_0x001f;
    L_0x0066:
        r9 = 5;
        goto L_0x001f;
    L_0x0068:
        r9 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.a.<clinit>():void");
    }

    public a(e eVar, ByteBuffer byteBuffer) {
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
        a(this.d);
    }

    public final void c() {
        super.c();
        ByteBuffer byteBuffer = this.f;
        this.a = g.c(byteBuffer, this).byteValue();
        this.b = g.c(byteBuffer, this).byteValue();
        this.c = g.c(byteBuffer, this).byteValue();
        this.d = g.d(byteBuffer, this);
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.a).append(z[2]).append(this.b).append(z[3]).append(this.c).append(z[1]).append(this.d).append(z[4]).append(super.toString()).toString();
    }
}
