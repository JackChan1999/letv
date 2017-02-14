package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.j;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class aa extends i<z, aa> {
    private int a;
    private ad b = ad.a();
    private ab c = ab.a();

    private aa() {
    }

    static /* synthetic */ z a(aa aaVar) {
        z f = aaVar.f();
        if (f.g()) {
            return f;
        }
        throw new j(new m().getMessage());
    }

    private aa c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            Object p;
            switch (a) {
                case 0:
                    break;
                case 10:
                    p = ad.p();
                    if (((this.a & 1) == 1 ? 1 : null) != null) {
                        p.a(this.b);
                    }
                    dVar.a(p, gVar);
                    a(p.b());
                    continue;
                case 18:
                    p = ab.ad();
                    if (((this.a & 2) == 2 ? 1 : null) != null) {
                        p.a(this.c);
                    }
                    dVar.a(p, gVar);
                    a(p.b());
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

    private aa e() {
        aa aaVar = new aa();
        z f = f();
        if (f != z.a()) {
            if (f.b()) {
                ad d = f.d();
                if ((aaVar.a & 1) != 1 || aaVar.b == ad.a()) {
                    aaVar.b = d;
                } else {
                    aaVar.b = ad.a(aaVar.b).a(d).b();
                }
                aaVar.a |= 1;
            }
            if (f.e()) {
                ab f2 = f.f();
                if ((aaVar.a & 2) != 2 || aaVar.c == ab.a()) {
                    aaVar.c = f2;
                } else {
                    aaVar.c = ab.a(aaVar.c).a(f2).b();
                }
                aaVar.a |= 2;
            }
        }
        return aaVar;
    }

    private z f() {
        int i = 1;
        z zVar = new z();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        zVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        zVar.d = this.c;
        zVar.b = i;
        return zVar;
    }

    public final aa a(ab abVar) {
        if (abVar == null) {
            throw new NullPointerException();
        }
        this.c = abVar;
        this.a |= 2;
        return this;
    }

    public final aa a(ad adVar) {
        if (adVar == null) {
            throw new NullPointerException();
        }
        this.b = adVar;
        this.a |= 1;
        return this;
    }

    public final z a() {
        z f = f();
        if (f.g()) {
            return f;
        }
        throw new m();
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
