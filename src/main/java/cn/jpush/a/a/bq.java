package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class bq extends i<bp, bq> {
    private int a;
    private c b = c.a;

    private bq() {
    }

    private bq c(d dVar, g gVar) {
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

    private bq f() {
        return new bq().a(b());
    }

    public final bp a() {
        bp b = b();
        if (b.e()) {
            return b;
        }
        throw new m();
    }

    public final bq a(bp bpVar) {
        if (bpVar != bp.a() && bpVar.b()) {
            a(bpVar.d());
        }
        return this;
    }

    public final bq a(c cVar) {
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

    public final bp b() {
        int i = 1;
        bp bpVar = new bp();
        if ((this.a & 1) != 1) {
            i = 0;
        }
        bpVar.c = this.b;
        bpVar.b = i;
        return bpVar;
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
