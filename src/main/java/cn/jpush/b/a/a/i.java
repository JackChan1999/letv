package cn.jpush.b.a.a;

import cn.jpush.android.util.g;
import cn.jpush.b.a.c.a;
import cn.jpush.b.a.c.b;
import java.nio.ByteBuffer;

public final class i extends h {
    private static final String[] z;
    int a;
    int b;
    String c;
    int d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "{B&f.N\\,r7O`:d\u001a\u0000#ir.D4";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0072;
            case 1: goto L_0x0075;
            case 2: goto L_0x0078;
            case 3: goto L_0x007b;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 71;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006d;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\f.:d4Sg&o\fEws";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\f.:d5Vk;U.Mks";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0000#i";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\f.:d5Vk;W\"R} n)\u001a";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\f.$d4So.d}";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "rk:q(N},!\"R|&sg\r.*n#E4";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006d:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0072:
        r9 = 32;
        goto L_0x0020;
    L_0x0075:
        r9 = 14;
        goto L_0x0020;
    L_0x0078:
        r9 = 73;
        goto L_0x0020;
    L_0x007b:
        r9 = 1;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.i.<clinit>():void");
    }

    public i(e eVar, ByteBuffer byteBuffer) {
        super(eVar, byteBuffer);
    }

    public final int a() {
        return this.a;
    }

    public final void b() {
        super.b();
        c(this.a);
        b(this.b);
        a(this.c);
        c(this.d);
    }

    public final void c() {
        super.c();
        if (this.g > 0) {
            b.b(new StringBuilder(z[6]).append(this.g).append(z[5]).append(this.h).toString());
            return;
        }
        ByteBuffer byteBuffer = this.f;
        this.a = g.a(byteBuffer, this);
        this.b = g.b(byteBuffer, this);
        this.c = a.a(byteBuffer, this);
        this.d = g.a(byteBuffer, this);
    }

    public final int g() {
        return this.d;
    }

    public final String toString() {
        return new StringBuilder(z[0]).append(this.a).append(z[4]).append(this.b).append(z[1]).append(this.c).append(z[2]).append(this.d).append(z[3]).append(super.toString()).toString();
    }
}
