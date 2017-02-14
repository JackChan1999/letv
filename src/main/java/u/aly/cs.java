package u.aly;

import android.support.v4.media.TransportMediator;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: TCompactProtocol */
public class cs extends cy {
    private static final dd d = new dd("");
    private static final ct e = new ct("", (byte) 0, (short) 0);
    private static final byte[] f = new byte[16];
    private static final byte h = (byte) -126;
    private static final byte i = (byte) 1;
    private static final byte j = (byte) 31;
    private static final byte k = (byte) -32;
    private static final int l = 5;
    byte[] a;
    byte[] b;
    byte[] c;
    private bx m;
    private short n;
    private ct o;
    private Boolean p;
    private final long q;
    private byte[] r;

    /* compiled from: TCompactProtocol */
    public static class a implements da {
        private final long a;

        public a() {
            this.a = -1;
        }

        public a(int i) {
            this.a = (long) i;
        }

        public cy a(dm dmVar) {
            return new cs(dmVar, this.a);
        }
    }

    /* compiled from: TCompactProtocol */
    private static class b {
        public static final byte a = (byte) 1;
        public static final byte b = (byte) 2;
        public static final byte c = (byte) 3;
        public static final byte d = (byte) 4;
        public static final byte e = (byte) 5;
        public static final byte f = (byte) 6;
        public static final byte g = (byte) 7;
        public static final byte h = (byte) 8;
        public static final byte i = (byte) 9;
        public static final byte j = (byte) 10;
        public static final byte k = (byte) 11;
        public static final byte l = (byte) 12;

        private b() {
        }
    }

    static {
        f[0] = (byte) 0;
        f[2] = (byte) 1;
        f[3] = (byte) 3;
        f[6] = (byte) 4;
        f[8] = (byte) 5;
        f[10] = (byte) 6;
        f[4] = (byte) 7;
        f[11] = (byte) 8;
        f[15] = (byte) 9;
        f[14] = (byte) 10;
        f[13] = (byte) 11;
        f[12] = (byte) 12;
    }

    public cs(dm dmVar, long j) {
        super(dmVar);
        this.m = new bx(15);
        this.n = (short) 0;
        this.o = null;
        this.p = null;
        this.a = new byte[5];
        this.b = new byte[10];
        this.r = new byte[1];
        this.c = new byte[1];
        this.q = j;
    }

    public cs(dm dmVar) {
        this(dmVar, -1);
    }

    public void B() {
        this.m.c();
        this.n = (short) 0;
    }

    public void a(cw cwVar) throws cf {
        b((byte) h);
        d(((cwVar.b << 5) & -32) | 1);
        b(cwVar.c);
        a(cwVar.a);
    }

    public void a(dd ddVar) throws cf {
        this.m.a(this.n);
        this.n = (short) 0;
    }

    public void b() throws cf {
        this.n = this.m.a();
    }

    public void a(ct ctVar) throws cf {
        if (ctVar.b == (byte) 2) {
            this.o = ctVar;
        } else {
            a(ctVar, (byte) -1);
        }
    }

    private void a(ct ctVar, byte b) throws cf {
        if (b == (byte) -1) {
            b = e(ctVar.b);
        }
        if (ctVar.c <= this.n || ctVar.c - this.n > 15) {
            b(b);
            a(ctVar.c);
        } else {
            d(((ctVar.c - this.n) << 4) | b);
        }
        this.n = ctVar.c;
    }

    public void d() throws cf {
        b((byte) 0);
    }

    public void a(cv cvVar) throws cf {
        if (cvVar.c == 0) {
            d(0);
            return;
        }
        b(cvVar.c);
        d((e(cvVar.a) << 4) | e(cvVar.b));
    }

    public void a(cu cuVar) throws cf {
        a(cuVar.a, cuVar.b);
    }

    public void a(dc dcVar) throws cf {
        a(dcVar.a, dcVar.b);
    }

    public void a(boolean z) throws cf {
        byte b = (byte) 1;
        if (this.o != null) {
            ct ctVar = this.o;
            if (!z) {
                b = (byte) 2;
            }
            a(ctVar, b);
            this.o = null;
            return;
        }
        if (!z) {
            b = (byte) 2;
        }
        b(b);
    }

    public void a(byte b) throws cf {
        b(b);
    }

    public void a(short s) throws cf {
        b(c((int) s));
    }

    public void a(int i) throws cf {
        b(c(i));
    }

    public void a(long j) throws cf {
        b(c(j));
    }

    public void a(double d) throws cf {
        byte[] bArr = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        a(Double.doubleToLongBits(d), bArr, 0);
        this.g.b(bArr);
    }

    public void a(String str) throws cf {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new cf("UTF-8 not supported!");
        }
    }

    public void a(ByteBuffer byteBuffer) throws cf {
        a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
    }

    private void a(byte[] bArr, int i, int i2) throws cf {
        b(i2);
        this.g.b(bArr, i, i2);
    }

    public void a() throws cf {
    }

    public void e() throws cf {
    }

    public void f() throws cf {
    }

    public void g() throws cf {
    }

    public void c() throws cf {
    }

    protected void a(byte b, int i) throws cf {
        if (i <= 14) {
            d((i << 4) | e(b));
            return;
        }
        d(e(b) | 240);
        b(i);
    }

    private void b(int i) throws cf {
        int i2 = 0;
        while ((i & -128) != 0) {
            int i3 = i2 + 1;
            this.a[i2] = (byte) ((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
            i2 = i3;
        }
        int i4 = i2 + 1;
        this.a[i2] = (byte) i;
        this.g.b(this.a, 0, i4);
    }

    private void b(long j) throws cf {
        int i = 0;
        while ((-128 & j) != 0) {
            int i2 = i + 1;
            this.b[i] = (byte) ((int) ((127 & j) | 128));
            j >>>= 7;
            i = i2;
        }
        int i3 = i + 1;
        this.b[i] = (byte) ((int) j);
        this.g.b(this.b, 0, i3);
    }

    private long c(long j) {
        return (j << 1) ^ (j >> 63);
    }

    private int c(int i) {
        return (i << 1) ^ (i >> 31);
    }

    private void a(long j, byte[] bArr, int i) {
        bArr[i + 0] = (byte) ((int) (j & 255));
        bArr[i + 1] = (byte) ((int) ((j >> 8) & 255));
        bArr[i + 2] = (byte) ((int) ((j >> 16) & 255));
        bArr[i + 3] = (byte) ((int) ((j >> 24) & 255));
        bArr[i + 4] = (byte) ((int) ((j >> 32) & 255));
        bArr[i + 5] = (byte) ((int) ((j >> 40) & 255));
        bArr[i + 6] = (byte) ((int) ((j >> 48) & 255));
        bArr[i + 7] = (byte) ((int) ((j >> 56) & 255));
    }

    private void b(byte b) throws cf {
        this.r[0] = b;
        this.g.b(this.r);
    }

    private void d(int i) throws cf {
        b((byte) i);
    }

    public cw h() throws cf {
        byte u = u();
        if (u != h) {
            throw new cz("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(u));
        }
        u = u();
        byte b = (byte) (u & 31);
        if (b != (byte) 1) {
            throw new cz("Expected version 1 but got " + b);
        }
        return new cw(z(), (byte) ((u >> 5) & 3), E());
    }

    public dd j() throws cf {
        this.m.a(this.n);
        this.n = (short) 0;
        return d;
    }

    public void k() throws cf {
        this.n = this.m.a();
    }

    public ct l() throws cf {
        byte u = u();
        if (u == (byte) 0) {
            return e;
        }
        short s = (short) ((u & 240) >> 4);
        if (s == (short) 0) {
            s = v();
        } else {
            s = (short) (s + this.n);
        }
        ct ctVar = new ct("", d((byte) (u & 15)), s);
        if (c(u)) {
            this.p = ((byte) (u & 15)) == (byte) 1 ? Boolean.TRUE : Boolean.FALSE;
        }
        this.n = ctVar.c;
        return ctVar;
    }

    public cv n() throws cf {
        int E = E();
        int u = E == 0 ? 0 : u();
        return new cv(d((byte) (u >> 4)), d((byte) (u & 15)), E);
    }

    public cu p() throws cf {
        byte u = u();
        int i = (u >> 4) & 15;
        if (i == 15) {
            i = E();
        }
        return new cu(d(u), i);
    }

    public dc r() throws cf {
        return new dc(p());
    }

    public boolean t() throws cf {
        if (this.p != null) {
            boolean booleanValue = this.p.booleanValue();
            this.p = null;
            return booleanValue;
        } else if (u() != (byte) 1) {
            return false;
        } else {
            return true;
        }
    }

    public byte u() throws cf {
        if (this.g.h() > 0) {
            byte b = this.g.f()[this.g.g()];
            this.g.a(1);
            return b;
        }
        this.g.d(this.c, 0, 1);
        return this.c[0];
    }

    public short v() throws cf {
        return (short) g(E());
    }

    public int w() throws cf {
        return g(E());
    }

    public long x() throws cf {
        return d(F());
    }

    public double y() throws cf {
        byte[] bArr = new byte[8];
        this.g.d(bArr, 0, 8);
        return Double.longBitsToDouble(a(bArr));
    }

    public String z() throws cf {
        int E = E();
        f(E);
        if (E == 0) {
            return "";
        }
        try {
            if (this.g.h() < E) {
                return new String(e(E), "UTF-8");
            }
            String str = new String(this.g.f(), this.g.g(), E, "UTF-8");
            this.g.a(E);
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new cf("UTF-8 not supported!");
        }
    }

    public ByteBuffer A() throws cf {
        int E = E();
        f(E);
        if (E == 0) {
            return ByteBuffer.wrap(new byte[0]);
        }
        byte[] bArr = new byte[E];
        this.g.d(bArr, 0, E);
        return ByteBuffer.wrap(bArr);
    }

    private byte[] e(int i) throws cf {
        if (i == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i];
        this.g.d(bArr, 0, i);
        return bArr;
    }

    private void f(int i) throws cz {
        if (i < 0) {
            throw new cz("Negative length: " + i);
        } else if (this.q != -1 && ((long) i) > this.q) {
            throw new cz("Length exceeded max allowed: " + i);
        }
    }

    public void i() throws cf {
    }

    public void m() throws cf {
    }

    public void o() throws cf {
    }

    public void q() throws cf {
    }

    public void s() throws cf {
    }

    private int E() throws cf {
        int i = 0;
        int i2;
        if (this.g.h() >= 5) {
            byte[] f = this.g.f();
            int g = this.g.g();
            i2 = 0;
            int i3 = 0;
            while (true) {
                byte b = f[g + i];
                i3 |= (b & TransportMediator.KEYCODE_MEDIA_PAUSE) << i2;
                if ((b & 128) != 128) {
                    this.g.a(i + 1);
                    return i3;
                }
                i2 += 7;
                i++;
            }
        } else {
            i2 = 0;
            while (true) {
                byte u = u();
                i2 |= (u & TransportMediator.KEYCODE_MEDIA_PAUSE) << i;
                if ((u & 128) != 128) {
                    return i2;
                }
                i += 7;
            }
        }
    }

    private long F() throws cf {
        long j = null;
        long j2 = 0;
        if (this.g.h() >= 10) {
            int i;
            byte[] f = this.g.f();
            int g = this.g.g();
            long j3 = 0;
            while (true) {
                byte b = f[g + i];
                j2 |= ((long) (b & TransportMediator.KEYCODE_MEDIA_PAUSE)) << j3;
                if ((b & 128) != 128) {
                    break;
                }
                j3 += 7;
                i++;
            }
            this.g.a(i + 1);
        } else {
            while (true) {
                byte u = u();
                j2 |= ((long) (u & TransportMediator.KEYCODE_MEDIA_PAUSE)) << j;
                if ((u & 128) != 128) {
                    break;
                }
                j += 7;
            }
        }
        return j2;
    }

    private int g(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    private long d(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    private long a(byte[] bArr) {
        return ((((((((((long) bArr[7]) & 255) << 56) | ((((long) bArr[6]) & 255) << 48)) | ((((long) bArr[5]) & 255) << 40)) | ((((long) bArr[4]) & 255) << 32)) | ((((long) bArr[3]) & 255) << 24)) | ((((long) bArr[2]) & 255) << 16)) | ((((long) bArr[1]) & 255) << 8)) | (((long) bArr[0]) & 255);
    }

    private boolean c(byte b) {
        int i = b & 15;
        if (i == 1 || i == 2) {
            return true;
        }
        return false;
    }

    private byte d(byte b) throws cz {
        switch ((byte) (b & 15)) {
            case (byte) 0:
                return (byte) 0;
            case (byte) 1:
            case (byte) 2:
                return (byte) 2;
            case (byte) 3:
                return (byte) 3;
            case (byte) 4:
                return (byte) 6;
            case (byte) 5:
                return (byte) 8;
            case (byte) 6:
                return (byte) 10;
            case (byte) 7:
                return (byte) 4;
            case (byte) 8:
                return (byte) 11;
            case (byte) 9:
                return df.m;
            case (byte) 10:
                return df.l;
            case (byte) 11:
                return df.k;
            case (byte) 12:
                return (byte) 12;
            default:
                throw new cz("don't know what type: " + ((byte) (b & 15)));
        }
    }

    private byte e(byte b) {
        return f[b];
    }
}
