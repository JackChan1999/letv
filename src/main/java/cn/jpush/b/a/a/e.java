package cn.jpush.b.a.a;

import cn.jpush.b.a.c.a;
import java.nio.ByteBuffer;

public final class e {
    private static final String[] z;
    int a;
    int b;
    int c;
    Long d;
    int e;
    long f;
    private boolean g;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\nZ%Tk\u001c";
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
            case 0: goto L_0x0072;
            case 1: goto L_0x0075;
            case 2: goto L_0x0078;
            case 3: goto L_0x007b;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 15;
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
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006d;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "}0\u001fXnB'w\u0010/J\u001f9\u0007";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\nZ=HfB@";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\nZ$Tk\u001c";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\nZ4RbK\u001b9Y5";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\nZ!X}U\u00138S5";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "r\u00122\u001dgC\u001b3\u001dfUZ9R{\u0006\u00139T{O\u001b;TuC\u001ewDjRT";
        r0 = 5;
        r3 = r4;
        goto L_0x0008;
    L_0x006d:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0072:
        r9 = 38;
        goto L_0x001f;
    L_0x0075:
        r9 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        goto L_0x001f;
    L_0x0078:
        r9 = 87;
        goto L_0x001f;
    L_0x007b:
        r9 = 61;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.e.<clinit>():void");
    }

    public e(boolean z, int i, int i2, int i3, long j, int i4, long j2) {
        this.g = false;
        this.g = z;
        this.a = 0;
        this.b = i2;
        this.c = i3;
        this.d = Long.valueOf(j);
        this.e = i4;
        this.f = j2;
    }

    public e(boolean z, int i, int i2, long j) {
        this(z, 0, i, i2, j, 0, 0);
    }

    public e(boolean z, byte[] bArr) {
        this.g = false;
        this.g = false;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        this.a = wrap.getShort();
        this.b = wrap.get();
        this.c = wrap.get();
        this.d = Long.valueOf(wrap.getLong());
        this.f = wrap.getLong();
    }

    public final Long a() {
        return this.d;
    }

    public final long b() {
        return this.f;
    }

    public final byte[] c() {
        if (this.a == 0) {
            throw new IllegalStateException(z[6]);
        }
        ByteBuffer allocate = ByteBuffer.allocate(24);
        allocate.putShort((short) this.a);
        allocate.put((byte) this.b);
        allocate.put((byte) this.c);
        allocate.putLong(this.d.longValue());
        if (this.g) {
            allocate.putInt(this.e);
        }
        allocate.putLong(this.f);
        allocate.flip();
        return a.a(allocate);
    }

    public final String toString() {
        return new StringBuilder(z[1]).append(this.a).append(z[5]).append(this.b).append(z[4]).append(this.c).append(z[0]).append(this.d).append(this.g ? new StringBuilder(z[3]).append(this.e).toString() : "").append(z[2]).append(this.f).toString();
    }
}
