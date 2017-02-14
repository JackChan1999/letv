package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class i extends com.google.protobuf.jpush.i<h, i> {
    private int a;
    private long b;

    private i() {
    }

    private i c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.b();
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

    private i f() {
        return new i().a(b());
    }

    public final h a() {
        h b = b();
        if (b.e()) {
            return b;
        }
        throw new m();
    }

    public final i a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final i a(h hVar) {
        if (hVar != h.a() && hVar.b()) {
            a(hVar.d());
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final h b() {
        int i = 1;
        h hVar = new h();
        if ((this.a & 1) != 1) {
            i = 0;
        }
        hVar.c = this.b;
        hVar.b = i;
        return hVar;
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final /* synthetic */ com.google.protobuf.jpush.i c() {
        return f();
    }

    public final /* synthetic */ Object clone() {
        return f();
    }

    public final /* synthetic */ b d() {
        return f();
    }
}
