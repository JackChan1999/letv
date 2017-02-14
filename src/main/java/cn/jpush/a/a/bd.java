package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;

public final class bd extends i<bc, bd> {
    private int a;
    private long b;
    private int c;
    private long d;
    private long e;
    private c f = c.a;

    private bd() {
    }

    private bd c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.b();
                    continue;
                case 16:
                    this.a |= 2;
                    this.c = dVar.c();
                    continue;
                case 24:
                    this.a |= 4;
                    this.d = dVar.b();
                    continue;
                case 32:
                    this.a |= 8;
                    this.e = dVar.b();
                    continue;
                case 42:
                    this.a |= 16;
                    this.f = dVar.d();
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

    private bd e() {
        return new bd().a(a());
    }

    public final bc a() {
        int i = 1;
        bc bcVar = new bc();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        bcVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        bcVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        bcVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        bcVar.f = this.e;
        if ((i2 & 16) == 16) {
            i |= 16;
        }
        bcVar.g = this.f;
        bcVar.b = i;
        return bcVar;
    }

    public final bd a(bc bcVar) {
        if (bcVar != bc.a()) {
            long d;
            if (bcVar.b()) {
                d = bcVar.d();
                this.a |= 1;
                this.b = d;
            }
            if (bcVar.e()) {
                int f = bcVar.f();
                this.a |= 2;
                this.c = f;
            }
            if (bcVar.g()) {
                d = bcVar.h();
                this.a |= 4;
                this.d = d;
            }
            if (bcVar.i()) {
                d = bcVar.j();
                this.a |= 8;
                this.e = d;
            }
            if (bcVar.k()) {
                c l = bcVar.l();
                if (l == null) {
                    throw new NullPointerException();
                }
                this.a |= 16;
                this.f = l;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final /* synthetic */ i c() {
        return e();
    }

    public final /* synthetic */ Object clone() {
        return e();
    }

    public final /* synthetic */ b d() {
        return e();
    }
}
