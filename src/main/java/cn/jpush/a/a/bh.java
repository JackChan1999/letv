package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class bh extends i<bg, bh> {
    private int a;
    private c b = c.a;
    private c c = c.a;
    private c d = c.a;

    private bh() {
    }

    private bh c(d dVar, g gVar) {
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
                case 26:
                    this.a |= 4;
                    this.d = dVar.d();
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

    private bh f() {
        return new bh().a(b());
    }

    public final bg a() {
        bg b = b();
        if (b.i()) {
            return b;
        }
        throw new m();
    }

    public final bh a(bg bgVar) {
        if (bgVar != bg.a()) {
            c f;
            if (bgVar.b()) {
                a(bgVar.d());
            }
            if (bgVar.e()) {
                f = bgVar.f();
                if (f == null) {
                    throw new NullPointerException();
                }
                this.a |= 2;
                this.c = f;
            }
            if (bgVar.g()) {
                f = bgVar.h();
                if (f == null) {
                    throw new NullPointerException();
                }
                this.a |= 4;
                this.d = f;
            }
        }
        return this;
    }

    public final bh a(c cVar) {
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

    public final bg b() {
        int i = 1;
        bg bgVar = new bg();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        bgVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        bgVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        bgVar.e = this.d;
        bgVar.b = i;
        return bgVar;
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
