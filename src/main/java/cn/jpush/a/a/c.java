package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class c extends i<b, c> {
    private int a;
    private List<Long> b = Collections.emptyList();

    private c() {
    }

    private c c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    g();
                    this.b.add(Long.valueOf(dVar.b()));
                    continue;
                case 10:
                    a = dVar.c(dVar.f());
                    while (dVar.g() > 0) {
                        long b = dVar.b();
                        g();
                        this.b.add(Long.valueOf(b));
                    }
                    dVar.d(a);
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

    private c f() {
        return new c().a(b());
    }

    private void g() {
        if ((this.a & 1) != 1) {
            this.b = new ArrayList(this.b);
            this.a |= 1;
        }
    }

    public final b a() {
        b b = b();
        if (b.b()) {
            return b;
        }
        throw new m();
    }

    public final c a(b bVar) {
        if (!(bVar == b.a() || bVar.b.isEmpty())) {
            if (this.b.isEmpty()) {
                this.b = bVar.b;
                this.a &= -2;
            } else {
                g();
                this.b.addAll(bVar.b);
            }
        }
        return this;
    }

    public final c a(Iterable<? extends Long> iterable) {
        g();
        b.a((Iterable) iterable, this.b);
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final b b() {
        b bVar = new b();
        if ((this.a & 1) == 1) {
            this.b = Collections.unmodifiableList(this.b);
            this.a &= -2;
        }
        bVar.b = this.b;
        return bVar;
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
