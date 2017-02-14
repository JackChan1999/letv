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

public final class r extends i<q, r> {
    private int a;
    private long b;
    private int c;
    private List<Long> d = Collections.emptyList();

    private r() {
    }

    private r c(d dVar, g gVar) {
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
                    this.c = dVar.c();
                    continue;
                case 24:
                    g();
                    this.d.add(Long.valueOf(dVar.b()));
                    continue;
                case 26:
                    a = dVar.c(dVar.f());
                    while (dVar.g() > 0) {
                        long b = dVar.b();
                        g();
                        this.d.add(Long.valueOf(b));
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

    private r f() {
        return new r().a(b());
    }

    private void g() {
        if ((this.a & 4) != 4) {
            this.d = new ArrayList(this.d);
            this.a |= 4;
        }
    }

    public final q a() {
        q b = b();
        if (b.g()) {
            return b;
        }
        throw new m();
    }

    public final r a(int i) {
        this.a |= 2;
        this.c = i;
        return this;
    }

    public final r a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final r a(q qVar) {
        if (qVar != q.a()) {
            if (qVar.b()) {
                a(qVar.d());
            }
            if (qVar.e()) {
                a(qVar.f());
            }
            if (!qVar.e.isEmpty()) {
                if (this.d.isEmpty()) {
                    this.d = qVar.e;
                    this.a &= -5;
                } else {
                    g();
                    this.d.addAll(qVar.e);
                }
            }
        }
        return this;
    }

    public final r a(Iterable<? extends Long> iterable) {
        g();
        b.a((Iterable) iterable, this.d);
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final q b() {
        int i = 1;
        q qVar = new q();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        qVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        qVar.d = this.c;
        if ((this.a & 4) == 4) {
            this.d = Collections.unmodifiableList(this.d);
            this.a &= -5;
        }
        qVar.e = this.d;
        qVar.b = i;
        return qVar;
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
