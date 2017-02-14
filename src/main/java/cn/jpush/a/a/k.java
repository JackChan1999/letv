package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class k extends i<j, k> {
    private int a;
    private long b;
    private c c = c.a;
    private c d = c.a;

    private k() {
    }

    private k c(d dVar, g gVar) {
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
                default:
                    if (!dVar.b(a)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    private k f() {
        return new k().a(b());
    }

    public final j a() {
        j b = b();
        if (b.i()) {
            return b;
        }
        throw new m();
    }

    public final k a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final k a(j jVar) {
        if (jVar != j.a()) {
            if (jVar.b()) {
                a(jVar.d());
            }
            if (jVar.e()) {
                a(jVar.f());
            }
            if (jVar.g()) {
                b(jVar.h());
            }
        }
        return this;
    }

    public final k a(c cVar) {
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

    public final j b() {
        int i = 1;
        j jVar = new j();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        jVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        jVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        jVar.e = this.d;
        jVar.b = i;
        return jVar;
    }

    public final k b(c cVar) {
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
