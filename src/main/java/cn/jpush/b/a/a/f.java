package cn.jpush.b.a.a;

import cn.jpush.b.a.c.a;
import cn.jpush.b.a.c.b;
import cn.jpush.b.a.c.c;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public abstract class f {
    private static final String[] z;
    private boolean a = true;
    protected e e;
    protected ByteBuffer f;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "f\bgbuR4qtX";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002c;
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
            case 0: goto L_0x0069;
            case 1: goto L_0x006c;
            case 2: goto L_0x006f;
            case 3: goto L_0x0071;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 5;
    L_0x001e:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002a;
    L_0x0026:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002a:
        r5 = r1;
        r1 = r7;
    L_0x002c:
        if (r5 > r6) goto L_0x0011;
    L_0x002e:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0042;
            case 1: goto L_0x004a;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            case 4: goto L_0x0064;
            default: goto L_0x003a;
        };
    L_0x003a:
        r3[r2] = r1;
        r2 = 1;
        r1 = "f\bg`pX)vL";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0042:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u001dw\"";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004a:
        r3[r2] = r1;
        r2 = 3;
        r1 = "s5\"sjY#\"ej\u001d*ccvXt";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0011z`hqX)81";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "{3lpi\u001dw\"}`S`";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0064:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0069:
        r9 = 61;
        goto L_0x001e;
    L_0x006c:
        r9 = 90;
        goto L_0x001e;
    L_0x006f:
        r9 = 2;
        goto L_0x001e;
    L_0x0071:
        r9 = 17;
        goto L_0x001e;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.b.a.a.f.<clinit>():void");
    }

    public f(boolean z, int i, int i2, long j) {
        this.e = new e(true, i, i2, j);
        this.f = ByteBuffer.allocate(7168);
    }

    public f(boolean z, int i, int i2, long j, int i3, long j2) {
        this.e = new e(false, 0, i, i2, j, -1, j2);
        this.f = ByteBuffer.allocate(7168);
    }

    public f(boolean z, e eVar, ByteBuffer byteBuffer) {
        this.e = eVar;
        if (byteBuffer != null) {
            this.f = byteBuffer;
            c();
            return;
        }
        b.a(z[3]);
    }

    private final byte[] a() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteBuffer byteBuffer = this.f;
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.asReadOnlyBuffer().flip();
        byteBuffer.get(bArr);
        this.e.a = (this.a ? 24 : 20) + bArr.length;
        try {
            byteArrayOutputStream.write(this.e.c());
            byteArrayOutputStream.write(bArr);
        } catch (Exception e) {
        }
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        new StringBuilder(z[5]).append(toByteArray.length).append(z[4]).append(c.a(toByteArray));
        return toByteArray;
    }

    protected final void a(int i) {
        this.f.put((byte) i);
    }

    protected final void a(long j) {
        this.f.putLong(j);
    }

    protected final void a(String str) {
        this.f.put(a.a(str));
    }

    protected final void a(byte[] bArr) {
        this.f.put(bArr);
    }

    protected abstract void b();

    protected final void b(int i) {
        this.f.putShort((short) i);
    }

    protected abstract void c();

    protected final void c(int i) {
        this.f.putInt(i);
    }

    public final int d() {
        return this.e.c;
    }

    public final e e() {
        return this.e;
    }

    public final byte[] f() {
        this.f.clear();
        b();
        this.f.flip();
        return a();
    }

    public String toString() {
        return (this.a ? z[1] : z[0]) + z[2] + this.e.toString();
    }
}
