package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;

public final class az extends i<ay, az> {
    private int a;
    private long b;
    private int c;
    private int d;

    private az() {
    }

    private az c(d dVar, g gVar) {
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
                    this.d = dVar.c();
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

    private az e() {
        return new az().a(a());
    }

    public final ay a() {
        int i = 1;
        ay ayVar = new ay();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        ayVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        ayVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        ayVar.e = this.d;
        ayVar.b = i;
        return ayVar;
    }

    public final az a(ay ayVar) {
        if (ayVar != ay.a()) {
            int f;
            if (ayVar.b()) {
                long d = ayVar.d();
                this.a |= 1;
                this.b = d;
            }
            if (ayVar.e()) {
                f = ayVar.f();
                this.a |= 2;
                this.c = f;
            }
            if (ayVar.g()) {
                f = ayVar.h();
                this.a |= 4;
                this.d = f;
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
