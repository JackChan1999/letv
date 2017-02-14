package cn.jpush.a.a;

import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class y extends i<x, y> {
    private int a;
    private List<Integer> b = Collections.emptyList();

    private y() {
    }

    private y c(d dVar, g gVar) {
        while (true) {
            int a = dVar.a();
            switch (a) {
                case 0:
                    break;
                case 8:
                    f();
                    this.b.add(Integer.valueOf(dVar.c()));
                    continue;
                case 10:
                    a = dVar.c(dVar.f());
                    while (dVar.g() > 0) {
                        int c = dVar.c();
                        f();
                        this.b.add(Integer.valueOf(c));
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

    private y e() {
        return new y().a(a());
    }

    private void f() {
        if ((this.a & 1) != 1) {
            this.b = new ArrayList(this.b);
            this.a |= 1;
        }
    }

    public final x a() {
        x xVar = new x();
        if ((this.a & 1) == 1) {
            this.b = Collections.unmodifiableList(this.b);
            this.a &= -2;
        }
        xVar.b = this.b;
        return xVar;
    }

    public final y a(x xVar) {
        if (!(xVar == x.a() || xVar.b.isEmpty())) {
            if (this.b.isEmpty()) {
                this.b = xVar.b;
                this.a &= -2;
            } else {
                f();
                this.b.addAll(xVar.b);
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final /* synthetic */ i c() {
        return e();
    }

    public final /* synthetic */ Object clone() {
        return e();
    }

    public final /* synthetic */ b d() {
        return e();
    }
}
