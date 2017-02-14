package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class al extends i<ak, al> {
    private int a;
    private long b;
    private long c;

    private al() {
    }

    private al c(d dVar, g gVar) {
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

    private al f() {
        return new al().a(b());
    }

    public final ak a() {
        ak b = b();
        if (b.g()) {
            return b;
        }
        throw new m();
    }

    public final al a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final al a(ak akVar) {
        if (akVar != ak.a()) {
            if (akVar.b()) {
                a(akVar.d());
            }
            if (akVar.e()) {
                long f = akVar.f();
                this.a |= 2;
                this.c = f;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final ak b() {
        int i = 1;
        ak akVar = new ak();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        akVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        akVar.d = this.c;
        akVar.b = i;
        return akVar;
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
