package cn.jpush.a.a;

import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class ab extends h {
    private static final ab a;
    private ai A;
    private as B;
    private byte C;
    private int D;
    private int b;
    private bl c;
    private bn d;
    private bi e;
    private be f;
    private d g;
    private h h;
    private j i;
    private o j;
    private s k;
    private m l;
    private q m;
    private u n;
    private ba o;
    private aq p;
    private bc q;
    private ay r;
    private b s;
    private f t;
    private af u;
    private bp v;
    private am w;
    private aw x;
    private ak y;
    private au z;

    static {
        ab abVar = new ab();
        a = abVar;
        abVar.c = bl.a();
        abVar.d = bn.a();
        abVar.e = bi.a();
        abVar.f = be.a();
        abVar.g = d.a();
        abVar.h = h.a();
        abVar.i = j.a();
        abVar.j = o.a();
        abVar.k = s.a();
        abVar.l = m.a();
        abVar.m = q.a();
        abVar.n = u.a();
        abVar.o = ba.a();
        abVar.p = aq.a();
        abVar.q = bc.a();
        abVar.r = ay.a();
        abVar.s = b.a();
        abVar.t = f.a();
        abVar.u = af.a();
        abVar.v = bp.a();
        abVar.w = am.a();
        abVar.x = aw.a();
        abVar.y = ak.a();
        abVar.z = au.a();
        abVar.A = ai.a();
        abVar.B = as.a();
    }

    private ab() {
        this.C = (byte) -1;
        this.D = -1;
    }

    private ab(ac acVar) {
        super((byte) 0);
        this.C = (byte) -1;
        this.D = -1;
    }

    public static ab a() {
        return a;
    }

    public static ac a(ab abVar) {
        return new ac().a(abVar);
    }

    public static ac ad() {
        return new ac();
    }

    public final boolean A() {
        return (this.b & 4096) == 4096;
    }

    public final ba B() {
        return this.o;
    }

    public final boolean C() {
        return (this.b & 8192) == 8192;
    }

    public final aq D() {
        return this.p;
    }

    public final boolean E() {
        return (this.b & 16384) == 16384;
    }

    public final bc F() {
        return this.q;
    }

    public final boolean G() {
        return (this.b & 32768) == 32768;
    }

    public final ay H() {
        return this.r;
    }

    public final boolean I() {
        return (this.b & 65536) == 65536;
    }

    public final b J() {
        return this.s;
    }

    public final boolean K() {
        return (this.b & 131072) == 131072;
    }

    public final f L() {
        return this.t;
    }

    public final boolean M() {
        return (this.b & 262144) == 262144;
    }

    public final af N() {
        return this.u;
    }

    public final boolean O() {
        return (this.b & 524288) == 524288;
    }

    public final bp P() {
        return this.v;
    }

    public final boolean Q() {
        return (this.b & 1048576) == 1048576;
    }

    public final am R() {
        return this.w;
    }

    public final boolean S() {
        return (this.b & 2097152) == 2097152;
    }

    public final aw T() {
        return this.x;
    }

    public final boolean U() {
        return (this.b & 4194304) == 4194304;
    }

    public final ak V() {
        return this.y;
    }

    public final boolean W() {
        return (this.b & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == GravityCompat.RELATIVE_LAYOUT_DIRECTION;
    }

    public final au X() {
        return this.z;
    }

    public final boolean Y() {
        return (this.b & ViewCompat.MEASURED_STATE_TOO_SMALL) == ViewCompat.MEASURED_STATE_TOO_SMALL;
    }

    public final ai Z() {
        return this.A;
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
        if ((this.b & 64) == 64) {
            eVar.a(7, this.i);
        }
        if ((this.b & 128) == 128) {
            eVar.a(8, this.j);
        }
        if ((this.b & 256) == 256) {
            eVar.a(9, this.k);
        }
        if ((this.b & 512) == 512) {
            eVar.a(10, this.l);
        }
        if ((this.b & 1024) == 1024) {
            eVar.a(11, this.m);
        }
        if ((this.b & 2048) == 2048) {
            eVar.a(12, this.n);
        }
        if ((this.b & 4096) == 4096) {
            eVar.a(13, this.o);
        }
        if ((this.b & 8192) == 8192) {
            eVar.a(14, this.p);
        }
        if ((this.b & 16384) == 16384) {
            eVar.a(15, this.q);
        }
        if ((this.b & 32768) == 32768) {
            eVar.a(16, this.r);
        }
        if ((this.b & 65536) == 65536) {
            eVar.a(18, this.s);
        }
        if ((this.b & 131072) == 131072) {
            eVar.a(19, this.t);
        }
        if ((this.b & 262144) == 262144) {
            eVar.a(20, this.u);
        }
        if ((this.b & 524288) == 524288) {
            eVar.a(23, this.v);
        }
        if ((this.b & 1048576) == 1048576) {
            eVar.a(31, this.w);
        }
        if ((this.b & 2097152) == 2097152) {
            eVar.a(32, this.x);
        }
        if ((this.b & 4194304) == 4194304) {
            eVar.a(33, this.y);
        }
        if ((this.b & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == GravityCompat.RELATIVE_LAYOUT_DIRECTION) {
            eVar.a(34, this.z);
        }
        if ((this.b & ViewCompat.MEASURED_STATE_TOO_SMALL) == ViewCompat.MEASURED_STATE_TOO_SMALL) {
            eVar.a(35, this.A);
        }
        if ((this.b & 33554432) == 33554432) {
            eVar.a(36, this.B);
        }
    }

    public final boolean aa() {
        return (this.b & 33554432) == 33554432;
    }

    public final as ab() {
        return this.B;
    }

    public final boolean ac() {
        byte b = this.C;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.C = (byte) 1;
            return true;
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.D;
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
                i += e.b(4, this.f);
            }
            if ((this.b & 16) == 16) {
                i += e.b(5, this.g);
            }
            if ((this.b & 32) == 32) {
                i += e.b(6, this.h);
            }
            if ((this.b & 64) == 64) {
                i += e.b(7, this.i);
            }
            if ((this.b & 128) == 128) {
                i += e.b(8, this.j);
            }
            if ((this.b & 256) == 256) {
                i += e.b(9, this.k);
            }
            if ((this.b & 512) == 512) {
                i += e.b(10, this.l);
            }
            if ((this.b & 1024) == 1024) {
                i += e.b(11, this.m);
            }
            if ((this.b & 2048) == 2048) {
                i += e.b(12, this.n);
            }
            if ((this.b & 4096) == 4096) {
                i += e.b(13, this.o);
            }
            if ((this.b & 8192) == 8192) {
                i += e.b(14, this.p);
            }
            if ((this.b & 16384) == 16384) {
                i += e.b(15, this.q);
            }
            if ((this.b & 32768) == 32768) {
                i += e.b(16, this.r);
            }
            if ((this.b & 65536) == 65536) {
                i += e.b(18, this.s);
            }
            if ((this.b & 131072) == 131072) {
                i += e.b(19, this.t);
            }
            if ((this.b & 262144) == 262144) {
                i += e.b(20, this.u);
            }
            if ((this.b & 524288) == 524288) {
                i += e.b(23, this.v);
            }
            if ((this.b & 1048576) == 1048576) {
                i += e.b(31, this.w);
            }
            if ((this.b & 2097152) == 2097152) {
                i += e.b(32, this.x);
            }
            if ((this.b & 4194304) == 4194304) {
                i += e.b(33, this.y);
            }
            if ((this.b & GravityCompat.RELATIVE_LAYOUT_DIRECTION) == GravityCompat.RELATIVE_LAYOUT_DIRECTION) {
                i += e.b(34, this.z);
            }
            if ((this.b & ViewCompat.MEASURED_STATE_TOO_SMALL) == ViewCompat.MEASURED_STATE_TOO_SMALL) {
                i += e.b(35, this.A);
            }
            if ((this.b & 33554432) == 33554432) {
                i += e.b(36, this.B);
            }
            this.D = i;
        }
        return i;
    }

    public final bl d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final bn f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final bi h() {
        return this.e;
    }

    public final boolean i() {
        return (this.b & 8) == 8;
    }

    public final be j() {
        return this.f;
    }

    public final boolean k() {
        return (this.b & 16) == 16;
    }

    public final d l() {
        return this.g;
    }

    public final boolean m() {
        return (this.b & 32) == 32;
    }

    public final h n() {
        return this.h;
    }

    public final boolean o() {
        return (this.b & 64) == 64;
    }

    public final j p() {
        return this.i;
    }

    public final boolean q() {
        return (this.b & 128) == 128;
    }

    public final o r() {
        return this.j;
    }

    public final boolean s() {
        return (this.b & 256) == 256;
    }

    public final s t() {
        return this.k;
    }

    public final boolean u() {
        return (this.b & 512) == 512;
    }

    public final m v() {
        return this.l;
    }

    public final boolean w() {
        return (this.b & 1024) == 1024;
    }

    public final q x() {
        return this.m;
    }

    public final boolean y() {
        return (this.b & 2048) == 2048;
    }

    public final u z() {
        return this.n;
    }
}
