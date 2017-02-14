package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class ad extends h {
    private static final ad a;
    private int b;
    private int c;
    private int d;
    private long e;
    private c f;
    private x g;
    private int h;
    private byte i;
    private int j;

    static {
        ad adVar = new ad();
        a = adVar;
        adVar.c = 0;
        adVar.d = 0;
        adVar.e = 0;
        adVar.f = c.a;
        adVar.g = x.a();
        adVar.h = 0;
    }

    private ad() {
        this.i = (byte) -1;
        this.j = -1;
    }

    private ad(ae aeVar) {
        super((byte) 0);
        this.i = (byte) -1;
        this.j = -1;
    }

    public static ad a() {
        return a;
    }

    public static ae a(ad adVar) {
        return new ae().a(adVar);
    }

    public static ae p() {
        return new ae();
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
            eVar.a(6, this.h);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.j;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.c(1, this.c) + 0;
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
            if ((this.b & 32) == 32) {
                i += e.c(6, this.h);
            }
            this.j = i;
        }
        return i;
    }

    public final int d() {
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

    public final c j() {
        return this.f;
    }

    public final boolean k() {
        return (this.b & 16) == 16;
    }

    public final x l() {
        return this.g;
    }

    public final boolean m() {
        return (this.b & 32) == 32;
    }

    public final int n() {
        return this.h;
    }

    public final boolean o() {
        byte b = this.i;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.i = (byte) 1;
            return true;
        }
    }
}
