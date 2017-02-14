package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class ao extends h {
    private static final ao a;
    private int b;
    private long c;
    private long d;
    private long e;
    private int f;
    private bg g;
    private int h;
    private int i;
    private byte j;
    private int k;

    static {
        ao aoVar = new ao();
        a = aoVar;
        aoVar.c = 0;
        aoVar.d = 0;
        aoVar.e = 0;
        aoVar.f = 0;
        aoVar.g = bg.a();
        aoVar.h = 0;
        aoVar.i = 0;
    }

    private ao() {
        this.j = (byte) -1;
        this.k = -1;
    }

    private ao(ap apVar) {
        super((byte) 0);
        this.j = (byte) -1;
        this.k = -1;
    }

    public static ao a() {
        return a;
    }

    public static ap r() {
        return new ap();
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
        if ((this.b & 32) == 32) {
            eVar.b(6, this.h);
        }
        if ((this.b & 64) == 64) {
            eVar.a(7, this.i);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.k;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.b(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.b(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i += e.b(3, this.e);
            }
            if ((this.b & 8) == 8) {
                i += e.c(4, this.f);
            }
            if ((this.b & 16) == 16) {
                i += e.b(5, this.g);
            }
            if ((this.b & 32) == 32) {
                i += e.d(6, this.h);
            }
            if ((this.b & 64) == 64) {
                i += e.c(7, this.i);
            }
            this.k = i;
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
        return (this.b & 4) == 4;
    }

    public final long h() {
        return this.e;
    }

    public final boolean i() {
        return (this.b & 8) == 8;
    }

    public final int j() {
        return this.f;
    }

    public final boolean k() {
        return (this.b & 16) == 16;
    }

    public final bg l() {
        return this.g;
    }

    public final boolean m() {
        return (this.b & 32) == 32;
    }

    public final int n() {
        return this.h;
    }

    public final boolean o() {
        return (this.b & 64) == 64;
    }

    public final int p() {
        return this.i;
    }

    public final boolean q() {
        byte b = this.j;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.j = (byte) 1;
            return true;
        }
    }
}
