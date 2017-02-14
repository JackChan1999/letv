package cn.jpush.a.a;

import android.support.v4.view.MotionEventCompat;
import com.google.protobuf.jpush.b;
import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.d;
import com.google.protobuf.jpush.g;
import com.google.protobuf.jpush.i;
import com.google.protobuf.jpush.l;
import com.google.protobuf.jpush.m;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class bb extends i<ba, bb> {
    private int a;
    private long b;
    private int c;
    private long d;
    private long e;
    private List<Long> f = Collections.emptyList();
    private c g = c.a;
    private int h;
    private int i;
    private int j;

    private bb() {
    }

    private bb c(d dVar, g gVar) {
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
                    this.a |= 4;
                    this.d = dVar.b();
                    continue;
                case 32:
                    this.a |= 8;
                    this.e = dVar.b();
                    continue;
                case MotionEventCompat.AXIS_GENERIC_9 /*40*/:
                    g();
                    this.f.add(Long.valueOf(dVar.b()));
                    continue;
                case 42:
                    a = dVar.c(dVar.f());
                    while (dVar.g() > 0) {
                        long b = dVar.b();
                        g();
                        this.f.add(Long.valueOf(b));
                    }
                    dVar.d(a);
                    continue;
                case 50:
                    this.a |= 32;
                    this.g = dVar.d();
                    continue;
                case 56:
                    this.a |= 64;
                    this.h = dVar.e();
                    continue;
                case 64:
                    this.a |= 128;
                    this.i = dVar.c();
                    continue;
                case 72:
                    this.a |= 256;
                    this.j = dVar.c();
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

    private bb f() {
        return new bb().a(b());
    }

    private void g() {
        if ((this.a & 16) != 16) {
            this.f = new ArrayList(this.f);
            this.a |= 16;
        }
    }

    public final ba a() {
        ba b = b();
        if (b.s()) {
            return b;
        }
        throw new m();
    }

    public final bb a(int i) {
        this.a |= 2;
        this.c = i;
        return this;
    }

    public final bb a(long j) {
        this.a |= 1;
        this.b = j;
        return this;
    }

    public final bb a(ba baVar) {
        if (baVar != ba.a()) {
            int n;
            if (baVar.b()) {
                a(baVar.d());
            }
            if (baVar.e()) {
                a(baVar.f());
            }
            if (baVar.g()) {
                b(baVar.h());
            }
            if (baVar.i()) {
                c(baVar.j());
            }
            if (!baVar.g.isEmpty()) {
                if (this.f.isEmpty()) {
                    this.f = baVar.g;
                    this.a &= -17;
                } else {
                    g();
                    this.f.addAll(baVar.g);
                }
            }
            if (baVar.k()) {
                c l = baVar.l();
                if (l == null) {
                    throw new NullPointerException();
                }
                this.a |= 32;
                this.g = l;
            }
            if (baVar.m()) {
                n = baVar.n();
                this.a |= 64;
                this.h = n;
            }
            if (baVar.o()) {
                n = baVar.p();
                this.a |= 128;
                this.i = n;
            }
            if (baVar.q()) {
                n = baVar.r();
                this.a |= 256;
                this.j = n;
            }
        }
        return this;
    }

    public final /* synthetic */ b a(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final ba b() {
        int i = 1;
        ba baVar = new ba();
        int i2 = this.a;
        if ((i2 & 1) != 1) {
            i = 0;
        }
        baVar.c = this.b;
        if ((i2 & 2) == 2) {
            i |= 2;
        }
        baVar.d = this.c;
        if ((i2 & 4) == 4) {
            i |= 4;
        }
        baVar.e = this.d;
        if ((i2 & 8) == 8) {
            i |= 8;
        }
        baVar.f = this.e;
        if ((this.a & 16) == 16) {
            this.f = Collections.unmodifiableList(this.f);
            this.a &= -17;
        }
        baVar.g = this.f;
        if ((i2 & 32) == 32) {
            i |= 16;
        }
        baVar.h = this.g;
        if ((i2 & 64) == 64) {
            i |= 32;
        }
        baVar.i = this.h;
        if ((i2 & 128) == 128) {
            i |= 64;
        }
        baVar.j = this.i;
        if ((i2 & 256) == 256) {
            i |= 128;
        }
        baVar.k = this.j;
        baVar.b = i;
        return baVar;
    }

    public final bb b(long j) {
        this.a |= 4;
        this.d = j;
        return this;
    }

    public final /* synthetic */ l b(d dVar, g gVar) {
        return c(dVar, gVar);
    }

    public final bb c(long j) {
        this.a |= 8;
        this.e = j;
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
