package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class ap extends i<ao, ap> {
    private int a;
    private long b;
    private long c;
    private long d;
    private int e;
    private bg f = bg.a();
    private int g;
    private int h;

    private ap() {
    }

    private ap c(d dVar, g gVar) {
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
                case 24:
                    this.a |= 4;
                    this.d = dVar.b();
                    continue;
                case 32:
                    this.a |= 8;
                    this.e = dVar.c();
                    continue;
                case 42:
                    Object j = bg.j();
                    if (((this.a & 16) == 16 ? 1 : null) != null) {
                        j.a(this.f);
                    }
                    dVar.a(j, gVar);
                    bg b = j.b();
                    if (b == null) {
                        throw new NullPointerException();
                    }
                    this.f = b;
                    this.a |= 16;
                    continue;
                case 48:
                    this.a |= 32;
                    this.g = dVar.e();
                    continue;
                case 56:
                    this.a |= 64;
                    this.h = dVar.c();
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

    private ap f() {
        ap apVar = new ap();
        ao b = b();
        if (b != ao.a()) {
            if (b.b()) {
                apVar.a(b.d());
            }
            if (b.e()) {
                apVar.b(b.f());
            }
            if (b.g()) {
                apVar.c(b.h());
            }
            if (b.i()) {
                apVar.a(b.j());
            }
            if (b.k()) {
                bg l = b.l();
                if ((apVar.a & 16) != 16 || apVar.f == bg.a()) {
                    apVar.f = l;
                } else {
                    apVar.f = bg.a(apVar.f).a(l).b();
                }
                apVar.a |= 16;
            }
            if (b.m()) {
                int n = b.n();
                apVar.a |= 32;
                apVar.g = n;
            }
            if (b.o()) {
                int p = b.p();
                apVar.a |= 64;
                apVar.h = p;
            }
        }
        return apVar;
    }

    public final ao a() {
        ao b = b();
        if (b.q()) {
            return b;
        }
        throw new m();
    }

    public final ap a(int i) {
        this.a |= 8;
        this.e = i;
        return this;
    }

    public final ap a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final ao b() {
        int i = 1;
        ao aoVar = new ao();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        aoVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        aoVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        aoVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        aoVar.f = this.e;
        if ((i2 & 16) == 16) {
            i |= 16;
        }
        aoVar.g = this.f;
        if ((i2 & 32) == 32) {
            i |= 32;
        }
        aoVar.h = this.g;
        if ((i2 & 64) == 64) {
            i |= 64;
        }
        aoVar.i = this.h;
        aoVar.b = i;
        return aoVar;
    }

    public final ap b(long j) {
        this.a |= 2;
        this.c = j;
        return this;
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final ap c(long j) {
        this.a |= 4;
        this.d = j;
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
