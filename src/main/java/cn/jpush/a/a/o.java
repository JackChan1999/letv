package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class o extends h {
    private static final o a;
    private int b;
    private c c;
    private c d;
    private int e;
    private int f;
    private long g;
    private byte h;
    private int i;

    static {
        o oVar = new o();
        a = oVar;
        oVar.c = c.a;
        oVar.d = c.a;
        oVar.e = 0;
        oVar.f = 0;
        oVar.g = 0;
    }

    private o() {
        this.h = (byte) -1;
        this.i = -1;
    }

    private o(p pVar) {
        super((byte) 0);
        this.h = (byte) -1;
        this.i = -1;
    }

    public static o a() {
        return a;
    }

    public static p a(o oVar) {
        return new p().a(oVar);
    }

    public static p n() {
        return new p();
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
                i += e.b(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i += e.c(3, this.e);
            }
            if ((this.b & 8) == 8) {
                i += e.c(4, this.f);
            }
            if ((this.b & 16) == 16) {
                i += e.b(5, this.g);
            }
            this.i = i;
        }
        return i;
    }

    public final c d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final c f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final int h() {
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

    public final long l() {
        return this.g;
    }

    public final boolean m() {
        byte b = this.h;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.h = (byte) 1;
            return true;
        }
    }
}
