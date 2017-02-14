package cn.jpush.a.a;

import android.support.v4.view.MotionEventCompat;
import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class p extends i<o, p> {
    private int a;
    private c b = c.a;
    private c c = c.a;
    private int d;
    private int e;
    private long f;

    private p() {
    }

    private p c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 10:
                    this.a |= 1;
                    this.b = dVar.d();
                    continue;
                case 18:
                    this.a |= 2;
                    this.c = dVar.d();
                    continue;
                case 24:
                    this.a |= 4;
                    this.d = dVar.c();
                    continue;
                case 32:
                    this.a |= 8;
                    this.e = dVar.c();
                    continue;
                case MotionEventCompat.AXIS_GENERIC_9 /*40*/:
                    this.a |= 16;
                    this.f = dVar.b();
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

    private p f() {
        return new p().a(b());
    }

    public final o a() {
        o b = b();
        if (b.m()) {
            return b;
        }
        throw new m();
    }

    public final p a(int i) {
        this.a |= 4;
        this.d = i;
        return this;
    }

    public final p a(o oVar) {
        if (oVar != o.a()) {
            if (oVar.b()) {
                a(oVar.d());
            }
            if (oVar.e()) {
                b(oVar.f());
            }
            if (oVar.g()) {
                a(oVar.h());
            }
            if (oVar.i()) {
                b(oVar.j());
            }
            if (oVar.k()) {
                long l = oVar.l();
                this.a |= 16;
                this.f = l;
            }
        }
        return this;
    }

    public final p a(c cVar) {
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

    public final o b() {
        int i = 1;
        o oVar = new o();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        oVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        oVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        oVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        oVar.f = this.e;
        if ((i2 & 16) == 16) {
            i |= 16;
        }
        oVar.g = this.f;
        oVar.b = i;
        return oVar;
    }

    public final p b(int i) {
        this.a |= 8;
        this.e = i;
        return this;
    }

    public final p b(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 2;
        this.c = cVar;
        return this;
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
