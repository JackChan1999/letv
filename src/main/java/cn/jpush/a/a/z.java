package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class z extends h {
    private static final z a;
    private int b;
    private ad c;
    private ab d;
    private byte e;
    private int f;

    static {
        z zVar = new z();
        a = zVar;
        zVar.c = ad.a();
        zVar.d = ab.a();
    }

    private z() {
        this.e = (byte) -1;
        this.f = -1;
    }

    private z(aa aaVar) {
        super((byte) 0);
        this.e = (byte) -1;
        this.f = -1;
    }

    public static z a() {
        return a;
    }

    public static z a(byte[] bArr) {
        return aa.a((aa) new aa().a(bArr, 0, bArr.length));
    }

    public static aa h() {
        return new aa();
    }

    public final void a(e eVar) {
        c();
        if ((this.b & 1) == 1) {
            eVar.a(1, this.c);
        }
        if ((this.b & 2) == 2) {
            eVar.a(2, this.d);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.f;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.b(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.b(2, this.d);
            }
            this.f = i;
        }
        return i;
    }

    public final ad d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final ab f() {
        return this.d;
    }

    public final boolean g() {
        byte b = this.e;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.e = (byte) 1;
            return true;
        }
    }
}
