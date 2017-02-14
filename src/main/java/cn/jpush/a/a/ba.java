package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;
import java.util.Collections;
import java.util.List;

public final class ba extends h {
    private static final ba a;
    private int b;
    private long c;
    private int d;
    private long e;
    private long f;
    private List<Long> g;
    private c h;
    private int i;
    private int j;
    private int k;
    private byte l;
    private int m;

    static {
        ba baVar = new ba();
        a = baVar;
        baVar.c = 0;
        baVar.d = 0;
        baVar.e = 0;
        baVar.f = 0;
        baVar.g = Collections.emptyList();
        baVar.h = c.a;
        baVar.i = 0;
        baVar.j = 0;
        baVar.k = 0;
    }

    private ba() {
        this.l = (byte) -1;
        this.m = -1;
    }

    private ba(bb bbVar) {
        super((byte) 0);
        this.l = (byte) -1;
        this.m = -1;
    }

    public static ba a() {
        return a;
    }

    public static bb a(ba baVar) {
        return new bb().a(baVar);
    }

    public static bb t() {
        return new bb();
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
        for (int i = 0; i < this.g.size(); i++) {
            eVar.a(5, ((Long) this.g.get(i)).longValue());
        }
        if ((this.b & 16) == 16) {
            eVar.a(6, this.h);
        }
        if ((this.b & 32) == 32) {
            eVar.b(7, this.i);
        }
        if ((this.b & 64) == 64) {
            eVar.a(8, this.j);
        }
        if ((this.b & 128) == 128) {
            eVar.a(9, this.k);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = 0;
        int i2 = this.m;
        if (i2 == -1) {
            i2 = (this.b & 1) == 1 ? e.b(1, this.c) + 0 : 0;
            if ((this.b & 2) == 2) {
                i2 += e.c(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i2 += e.b(3, this.e);
            }
            int b = (this.b & 8) == 8 ? i2 + e.b(4, this.f) : i2;
            int i3 = 0;
            while (i < this.g.size()) {
                i++;
                i3 = e.a(((Long) this.g.get(i)).longValue()) + i3;
            }
            i2 = (b + i3) + (this.g.size() * 1);
            if ((this.b & 16) == 16) {
                i2 += e.b(6, this.h);
            }
            if ((this.b & 32) == 32) {
                i2 += e.d(7, this.i);
            }
            if ((this.b & 64) == 64) {
                i2 += e.c(8, this.j);
            }
            if ((this.b & 128) == 128) {
                i2 += e.c(9, this.k);
            }
            this.m = i2;
        }
        return i2;
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
        return this.h;
    }

    public final boolean m() {
        return (this.b & 32) == 32;
    }

    public final int n() {
        return this.i;
    }

    public final boolean o() {
        return (this.b & 64) == 64;
    }

    public final int p() {
        return this.j;
    }

    public final boolean q() {
        return (this.b & 128) == 128;
    }

    public final int r() {
        return this.k;
    }

    public final boolean s() {
        byte b = this.l;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.l = (byte) 1;
            return true;
        }
    }
}
