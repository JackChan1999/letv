package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class an extends i<am, an> {
    private int a;
    private long b;
    private long c;

    private an() {
    }

    private an c(d dVar, g gVar) {
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

    private an f() {
        return new an().a(b());
    }

    public final am a() {
        am b = b();
        if (b.g()) {
            return b;
        }
        throw new m();
    }

    public final an a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final an a(am amVar) {
        if (amVar != am.a()) {
            if (amVar.b()) {
                a(amVar.d());
            }
            if (amVar.e()) {
                long f = amVar.f();
                this.a |= 2;
                this.c = f;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final am b() {
        int i = 1;
        am amVar = new am();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        amVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        amVar.d = this.c;
        amVar.b = i;
        return amVar;
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
