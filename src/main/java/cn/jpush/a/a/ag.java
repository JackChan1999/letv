package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;

public final class ag extends i<af, ag> {
    private int a;
    private int b;
    private c c = c.a;

    private ag() {
    }

    private ag c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.c();
                    continue;
                case 18:
                    this.a |= 2;
                    this.c = dVar.d();
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

    private ag e() {
        return new ag().a(a());
    }

    public final af a() {
        int i = 1;
        af afVar = new af();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        afVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        afVar.d = this.c;
        afVar.b = i;
        return afVar;
    }

    public final ag a(af afVar) {
        if (afVar != af.a()) {
            if (afVar.b()) {
                int d = afVar.d();
                this.a |= 1;
                this.b = d;
            }
            if (afVar.e()) {
                c f = afVar.f();
                if (f == null) {
                    throw new NullPointerException();
                }
                this.a |= 2;
                this.c = f;
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
