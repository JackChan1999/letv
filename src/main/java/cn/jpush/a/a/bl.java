package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class bl extends h {
    private static final bl a;
    private int b;
    private c c;
    private c d;
    private int e;
    private c f;
    private byte g;
    private int h;

    static {
        bl blVar = new bl();
        a = blVar;
        blVar.c = c.a;
        blVar.d = c.a;
        blVar.e = 0;
        blVar.f = c.a;
    }

    private bl() {
        this.g = (byte) -1;
        this.h = -1;
    }

    private bl(bm bmVar) {
        super((byte) 0);
        this.g = (byte) -1;
        this.h = -1;
    }

    public static bl a() {
        return a;
    }

    public static bm a(bl blVar) {
        return new bm().a(blVar);
    }

    public static bm l() {
        return new bm();
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
        if ((this.b & 8) == 8) {
            eVar.a(4, this.f);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.h;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.b(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.b(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i += e.c(3, this.e);
            }
            if ((this.b & 8) == 8) {
                i += e.b(4, this.f);
            }
            this.h = i;
        }
        return i;
    }

    public final c d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final c f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final int h() {
        return this.e;
    }

    public final boolean i() {
        return (this.b & 8) == 8;
    }

    public final c j() {
        return this.f;
    }

    public final boolean k() {
        byte b = this.g;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.g = (byte) 1;
            return true;
        }
    }
}
