package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class av extends i<au, av> {
    private int a;
    private long b;
    private long c;

    private av() {
    }

    private av c(d dVar, g gVar) {
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

    private av f() {
        return new av().a(b());
    }

    public final au a() {
        au b = b();
        if (b.g()) {
            return b;
        }
        throw new m();
    }

    public final av a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final av a(au auVar) {
        if (auVar != au.a()) {
            if (auVar.b()) {
                a(auVar.d());
            }
            if (auVar.e()) {
                long f = auVar.f();
                this.a |= 2;
                this.c = f;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final au b() {
        int i = 1;
        au auVar = new au();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        auVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        auVar.d = this.c;
        auVar.b = i;
        return auVar;
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
