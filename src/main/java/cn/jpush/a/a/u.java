package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class u extends h {
    private static final u a;
    private int b;
    private long c;
    private c d;
    private c e;
    private byte f;
    private int g;

    static {
        u uVar = new u();
        a = uVar;
        uVar.c = 0;
        uVar.d = c.a;
        uVar.e = c.a;
    }

    private u() {
        this.f = (byte) -1;
        this.g = -1;
    }

    private u(v vVar) {
        super((byte) 0);
        this.f = (byte) -1;
        this.g = -1;
    }

    public static u a() {
        return a;
    }

    public static v a(u uVar) {
        return new v().a(uVar);
    }

    public static v j() {
        return new v();
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

    public final c f() {
        return this.d;
    }

    public final boolean g() {
        return (this.b & 4) == 4;
    }

    public final c h() {
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
