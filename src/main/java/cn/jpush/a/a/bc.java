package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class bc extends h {
    private static final bc a;
    private int b;
    private long c;
    private int d;
    private long e;
    private long f;
    private c g;
    private byte h;
    private int i;

    static {
        bc bcVar = new bc();
        a = bcVar;
        bcVar.c = 0;
        bcVar.d = 0;
        bcVar.e = 0;
        bcVar.f = 0;
        bcVar.g = c.a;
    }

    private bc() {
        this.h = (byte) -1;
        this.i = -1;
    }

    private bc(bd bdVar) {
        super((byte) 0);
        this.h = (byte) -1;
        this.i = -1;
    }

    public static bc a() {
        return a;
    }

    public static bd a(bc bcVar) {
        return new bd().a(bcVar);
    }

    public static bd m() {
        return new bd();
    }

    public final void a(e eVar) {
        c();
        if ((this.b & 1) == 1) {
            eVar.a(1, this.c);
        }
        if ((this.b & 2) == 2) {
            eVar.a(2, this.d);
        }
        if ((this.b & 4) == 4) {
            eVar.a(3, this.e);
        }
        if ((this.b & 8) == 8) {
            eVar.a(4, this.f);
        }
        if ((this.b & 16) == 16) {
            eVar.a(5, this.g);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.i;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.b(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.c(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i += e.b(3, this.e);
            }
            if ((this.b & 8) == 8) {
                i += e.b(4, this.f);
            }
            if ((this.b & 16) == 16) {
                i += e.b(5, this.g);
            }
            this.i = i;
        }
        return i;
    }

    public final long d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final int f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final long h() {
        return this.e;
    }

    public final boolean i() {
        return (this.b & 8) == 8;
    }

    public final long j() {
        return this.f;
    }

    public final boolean k() {
        return (this.b & 16) == 16;
    }

    public final c l() {
        return this.g;
    }
}
