package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class ae extends i<ad, ae> {
    private int a;
    private int b;
    private int c;
    private long d;
    private c e = c.a;
    private x f = x.a();
    private int g;

    private ae() {
    }

    private ae c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.c();
                    continue;
                case 16:
                    this.a |= 2;
                    this.c = dVar.c();
                    continue;
                case 24:
                    this.a |= 4;
                    this.d = dVar.b();
                    continue;
                case 34:
                    this.a |= 8;
                    this.e = dVar.d();
                    continue;
                case 42:
                    Object b = x.b();
                    if (((this.a & 16) == 16 ? 1 : null) != null) {
                        b.a(this.f);
                    }
                    dVar.a(b, gVar);
                    x a2 = b.a();
                    if (a2 == null) {
                        throw new NullPointerException();
                    }
                    this.f = a2;
                    this.a |= 16;
                    continue;
                case 48:
                    this.a |= 32;
                    this.g = dVar.c();
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

    private ae f() {
        return new ae().a(b());
    }

    public final ad a() {
        ad b = b();
        if (b.o()) {
            return b;
        }
        throw new m();
    }

    public final ae a(int i) {
        this.a |= 1;
        this.b = i;
        return this;
    }

    public final ae a(long j) {
        this.a |= 4;
        this.d = j;
        return this;
    }

    public final ae a(ad adVar) {
        if (adVar != ad.a()) {
            if (adVar.b()) {
                a(adVar.d());
            }
            if (adVar.e()) {
                b(adVar.f());
            }
            if (adVar.g()) {
                a(adVar.h());
            }
            if (adVar.i()) {
                a(adVar.j());
            }
            if (adVar.k()) {
                x l = adVar.l();
                if ((this.a & 16) != 16 || this.f == x.a()) {
                    this.f = l;
                } else {
                    this.f = x.a(this.f).a(l).a();
                }
                this.a |= 16;
            }
            if (adVar.m()) {
                int n = adVar.n();
                this.a |= 32;
                this.g = n;
            }
        }
        return this;
    }

    public final ae a(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 8;
        this.e = cVar;
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final ad b() {
        int i = 1;
        ad adVar = new ad();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        adVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        adVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        adVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        adVar.f = this.e;
        if ((i2 & 16) == 16) {
            i |= 16;
        }
        adVar.g = this.f;
        if ((i2 & 32) == 32) {
            i |= 32;
        }
        adVar.h = this.g;
        adVar.b = i;
        return adVar;
    }

    public final ae b(int i) {
        this.a |= 2;
        this.c = i;
        return this;
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
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
