package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class at extends i<as, at> {
    private int a;
    private int b;
    private long c;

    private at() {
    }

    private at c(d dVar, g gVar) {
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
                    this.c = dVar.b();
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

    private at f() {
        return new at().a(b());
    }

    public final as a() {
        as b = b();
        if (b.g()) {
            return b;
        }
        throw new m();
    }

    public final at a(as asVar) {
        if (asVar != as.a()) {
            if (asVar.b()) {
                int d = asVar.d();
                this.a |= 1;
                this.b = d;
            }
            if (asVar.e()) {
                long f = asVar.f();
                this.a |= 2;
                this.c = f;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final as b() {
        int i = 1;
        as asVar = new as();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        asVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        asVar.d = this.c;
        asVar.b = i;
        return asVar;
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
