package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class aw extends h {
    private static final aw a;
    private int b;
    private long c;
    private long d;
    private byte e;
    private int f;

    static {
        aw awVar = new aw();
        a = awVar;
        awVar.c = 0;
        awVar.d = 0;
    }

    private aw() {
        this.e = (byte) -1;
        this.f = -1;
    }

    private aw(ax axVar) {
        super((byte) 0);
        this.e = (byte) -1;
        this.f = -1;
    }

    public static aw a() {
        return a;
    }

    public static ax a(aw awVar) {
        return new ax().a(awVar);
    }

    public static ax h() {
        return new ax();
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

    public final long d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final long f() {
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
