package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class ai extends h {
    private static final ai a;
    private int b;
    private int c;
    private long d;
    private byte e;
    private int f;

    static {
        ai aiVar = new ai();
        a = aiVar;
        aiVar.c = 0;
        aiVar.d = 0;
    }

    private ai() {
        this.e = (byte) -1;
        this.f = -1;
    }

    private ai(aj ajVar) {
        super((byte) 0);
        this.e = (byte) -1;
        this.f = -1;
    }

    public static ai a() {
        return a;
    }

    public static aj a(ai aiVar) {
        return new aj().a(aiVar);
    }

    public static aj h() {
        return new aj();
    }

    public final void a(e eVar) {
        c();
        if ((this.b & 1) == 1) {
            eVar.a(1, this.c);
        }
        if ((this.b & 2) == 2) {
            eVar.a(2, this.d);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.f;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.c(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.b(2, this.d);
            }
            this.f = i;
        }
        return i;
    }

    public final int d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final long f() {
        return this.d;
    }

    public final boolean g() {
        byte b = this.e;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.e = (byte) 1;
            return true;
        }
    }
}
