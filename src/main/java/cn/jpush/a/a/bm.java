package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class bm extends i<bl, bm> {
    private int a;
    private c b = c.a;
    private c c = c.a;
    private int d;
    private c e = c.a;

    private bm() {
    }

    private bm c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 10:
                    this.a |= 1;
                    this.b = dVar.d();
                    continue;
                case 18:
                    this.a |= 2;
                    this.c = dVar.d();
                    continue;
                case 24:
                    this.a |= 4;
                    this.d = dVar.c();
                    continue;
                case 34:
                    this.a |= 8;
                    this.e = dVar.d();
                    continue;
                default:
                    if (!dVar.b(a)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    private bm f() {
        return new bm().a(b());
    }

    public final bl a() {
        bl b = b();
        if (b.k()) {
            return b;
        }
        throw new m();
    }

    public final bm a(int i) {
        this.a |= 4;
        this.d = i;
        return this;
    }

    public final bm a(bl blVar) {
        if (blVar != bl.a()) {
            if (blVar.b()) {
                a(blVar.d());
            }
            if (blVar.e()) {
                b(blVar.f());
            }
            if (blVar.g()) {
                a(blVar.h());
            }
            if (blVar.i()) {
                c(blVar.j());
            }
        }
        return this;
    }

    public final bm a(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 1;
        this.b = cVar;
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final bl b() {
        int i = 1;
        bl blVar = new bl();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        blVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        blVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        blVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        blVar.f = this.e;
        blVar.b = i;
        return blVar;
    }

    public final bm b(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 2;
        this.c = cVar;
        return this;
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final bm c(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 8;
        this.e = cVar;
        return this;
    }

    public final /* synthetic */ i c() {
        return f();
    }

    public final /* synthetic */ Object clone() {
        return f();
    }

    public final /* synthetic */ b d() {
        return f();
    }
}
