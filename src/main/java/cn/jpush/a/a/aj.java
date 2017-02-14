package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;

public final class aj extends i<ai, aj> {
    private int a;
    private int b;
    private long c;

    private aj() {
    }

    private aj c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    this.a |= 1;
                    this.b = dVar.c();
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

    private aj f() {
        return new aj().a(b());
    }

    public final ai a() {
        ai b = b();
        if (b.g()) {
            return b;
        }
        throw new m();
    }

    public final aj a(ai aiVar) {
        if (aiVar != ai.a()) {
            if (aiVar.b()) {
                int d = aiVar.d();
                this.a |= 1;
                this.b = d;
            }
            if (aiVar.e()) {
                long f = aiVar.f();
                this.a |= 2;
                this.c = f;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final ai b() {
        int i = 1;
        ai aiVar = new ai();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        aiVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        aiVar.d = this.c;
        aiVar.b = i;
        return aiVar;
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
