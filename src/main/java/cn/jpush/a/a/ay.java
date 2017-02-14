package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class ay extends h {
    private static final ay a;
    private int b;
    private long c;
    private int d;
    private int e;
    private byte f;
    private int g;

    static {
        ay ayVar = new ay();
        a = ayVar;
        ayVar.c = 0;
        ayVar.d = 0;
        ayVar.e = 0;
    }

    private ay() {
        this.f = (byte) -1;
        this.g = -1;
    }

    private ay(az azVar) {
        super((byte) 0);
        this.f = (byte) -1;
        this.g = -1;
    }

    public static ay a() {
        return a;
    }

    public static az a(ay ayVar) {
        return new az().a(ayVar);
    }

    public static az i() {
        return new az();
    }

    public final void a(e eVar) {
        c();
        if ((this.b & 1) == 1) {
            eVar.a(1, this.c);
        }
        if ((this.b & 2) == 2) {
            eVar.a(2, this.d);
        }
        if ((this.b & 4) == 4) {
            eVar.a(3, this.e);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.g;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.b(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.c(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i += e.c(3, this.e);
            }
            this.g = i;
        }
        return i;
    }

    public final long d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final int f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final int h() {
        return this.e;
    }
}
