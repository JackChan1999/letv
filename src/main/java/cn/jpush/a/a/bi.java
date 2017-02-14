package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class bi extends h {
    private static final bi a;
    private int b;
    private long c;
    private bg d;
    private long e;
    private byte f;
    private int g;

    static {
        bi biVar = new bi();
        a = biVar;
        biVar.c = 0;
        biVar.d = bg.a();
        biVar.e = 0;
    }

    private bi() {
        this.f = (byte) -1;
        this.g = -1;
    }

    private bi(bj bjVar) {
        super((byte) 0);
        this.f = (byte) -1;
        this.g = -1;
    }

    public static bi a() {
        return a;
    }

    public static bj a(bi biVar) {
        return new bj().a(biVar);
    }

    public static bj j() {
        return new bj();
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
                i += e.b(2, this.d);
            }
            if ((this.b & 4) == 4) {
                i += e.b(3, this.e);
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

    public final bg f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final long h() {
        return this.e;
    }

    public final boolean i() {
        byte b = this.f;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.f = (byte) 1;
            return true;
        }
    }
}
