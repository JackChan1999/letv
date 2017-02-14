package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class bj extends i<bi, bj> {
    private int a;
    private long b;
    private bg c = bg.a();
    private long d;

    private bj() {
    }

    private bj c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.b();
                    continue;
                case 18:
                    Object j = bg.j();
                    if (((this.a & 2) == 2 ? 1 : null) != null) {
                        j.a(this.c);
                    }
                    dVar.a(j, gVar);
                    a(j.b());
                    continue;
                case 24:
                    this.a |= 4;
                    this.d = dVar.b();
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

    private bj f() {
        return new bj().a(b());
    }

    public final bi a() {
        bi b = b();
        if (b.i()) {
            return b;
        }
        throw new m();
    }

    public final bj a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final bj a(bg bgVar) {
        if (bgVar == null) {
            throw new NullPointerException();
        }
        this.c = bgVar;
        this.a |= 2;
        return this;
    }

    public final bj a(bi biVar) {
        if (biVar != bi.a()) {
            if (biVar.b()) {
                a(biVar.d());
            }
            if (biVar.e()) {
                bg f = biVar.f();
                if ((this.a & 2) != 2 || this.c == bg.a()) {
                    this.c = f;
                } else {
                    this.c = bg.a(this.c).a(f).b();
                }
                this.a |= 2;
            }
            if (biVar.g()) {
                long h = biVar.h();
                this.a |= 4;
                this.d = h;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final bi b() {
        int i = 1;
        bi biVar = new bi();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        biVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        biVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        biVar.e = this.d;
        biVar.b = i;
        return biVar;
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
