package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class bo extends i<bn, bo> {
    private int a;
    private c b = c.a;

    private bo() {
    }

    private bo c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 10:
                    this.a |= 1;
                    this.b = dVar.d();
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

    private bo f() {
        return new bo().a(b());
    }

    public final bn a() {
        bn b = b();
        if (b.e()) {
            return b;
        }
        throw new m();
    }

    public final bo a(bn bnVar) {
        if (bnVar != bn.a() && bnVar.b()) {
            a(bnVar.d());
        }
        return this;
    }

    public final bo a(c cVar) {
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

    public final bn b() {
        int i = 1;
        bn bnVar = new bn();
        if ((this.a & 1) != 1) {
            i = 0;
        }
        bnVar.c = this.b;
        bnVar.b = i;
        return bnVar;
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
