package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class e extends i<d, e> {
    private int a;
    private long b;
    private c c = c.a;
    private c d = c.a;
    private int e;
    private c f = c.a;

    private e() {
    }

    private e c(d dVar, g gVar) {
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
                    this.a |= 2;
                    this.c = dVar.d();
                    continue;
                case 26:
                    this.a |= 4;
                    this.d = dVar.d();
                    continue;
                case 32:
                    this.a |= 8;
                    this.e = dVar.c();
                    continue;
                case 42:
                    this.a |= 16;
                    this.f = dVar.d();
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

    private e f() {
        return new e().a(b());
    }

    public final d a() {
        d b = b();
        if (b.m()) {
            return b;
        }
        throw new m();
    }

    public final e a(int i) {
        this.a |= 8;
        this.e = i;
        return this;
    }

    public final e a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final e a(d dVar) {
        if (dVar != d.a()) {
            if (dVar.b()) {
                a(dVar.d());
            }
            if (dVar.e()) {
                a(dVar.f());
            }
            if (dVar.g()) {
                b(dVar.h());
            }
            if (dVar.i()) {
                a(dVar.j());
            }
            if (dVar.k()) {
                c(dVar.l());
            }
        }
        return this;
    }

    public final e a(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 2;
        this.c = cVar;
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final d b() {
        int i = 1;
        d dVar = new d();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        dVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        dVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        dVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        dVar.f = this.e;
        if ((i2 & 16) == 16) {
            i |= 16;
        }
        dVar.g = this.f;
        dVar.b = i;
        return dVar;
    }

    public final e b(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 4;
        this.d = cVar;
        return this;
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final e c(c cVar) {
        if (cVar == null) {
            throw new NullPointerException();
        }
        this.a |= 16;
        this.f = cVar;
        return this;
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
