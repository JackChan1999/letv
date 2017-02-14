package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class t extends i<s, t> {
    private int a;
    private long b;

    private t() {
    }

    private t c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.b();
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

    private t f() {
        return new t().a(b());
    }

    public final s a() {
        s b = b();
        if (b.e()) {
            return b;
        }
        throw new m();
    }

    public final t a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final t a(s sVar) {
        if (sVar != s.a() && sVar.b()) {
            a(sVar.d());
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final s b() {
        int i = 1;
        s sVar = new s();
        if ((this.a & 1) != 1) {
            i = 0;
        }
        sVar.c = this.b;
        sVar.b = i;
        return sVar;
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
