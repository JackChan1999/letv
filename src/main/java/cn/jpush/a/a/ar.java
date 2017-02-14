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

public final class ar extends i<aq, ar> {
    private int a;
    private List<ao> b = Collections.emptyList();

    private ar() {
    }

    private ar c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 10:
                    Object r = ao.r();
                    dVar.a(r, gVar);
                    a(r.b());
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

    private ar f() {
        return new ar().a(b());
    }

    private void g() {
        if ((this.a & 1) != 1) {
            this.b = new ArrayList(this.b);
            this.a |= 1;
        }
    }

    public final aq a() {
        aq b = b();
        if (b.d()) {
            return b;
        }
        throw new m();
    }

    public final ar a(ao aoVar) {
        if (aoVar == null) {
            throw new NullPointerException();
        }
        g();
        this.b.add(aoVar);
        return this;
    }

    public final ar a(aq aqVar) {
        if (!(aqVar == aq.a() || aqVar.b.isEmpty())) {
            if (this.b.isEmpty()) {
                this.b = aqVar.b;
                this.a &= -2;
            } else {
                g();
                this.b.addAll(aqVar.b);
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final aq b() {
        aq aqVar = new aq();
        if ((this.a & 1) == 1) {
            this.b = Collections.unmodifiableList(this.b);
            this.a &= -2;
        }
        aqVar.b = this.b;
        return aqVar;
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
