package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class v extends i<u, v> {
    private int a;
    private long b;
    private c c = c.a;
    private c d = c.a;

    private v() {
    }

    private v c(d dVar, g gVar) {
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

    private v f() {
        return new v().a(b());
    }

    public final u a() {
        u b = b();
        if (b.i()) {
            return b;
        }
        throw new m();
    }

    public final v a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final v a(u uVar) {
        if (uVar != u.a()) {
            if (uVar.b()) {
                a(uVar.d());
            }
            if (uVar.e()) {
                a(uVar.f());
            }
            if (uVar.g()) {
                b(uVar.h());
            }
        }
        return this;
    }

    public final v a(c cVar) {
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

    public final u b() {
        int i = 1;
        u uVar = new u();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        uVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        uVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        uVar.e = this.d;
        uVar.b = i;
        return uVar;
    }

    public final v b(c cVar) {
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
