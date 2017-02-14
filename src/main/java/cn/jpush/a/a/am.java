package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class am extends h {
    private static final am a;
    private int b;
    private long c;
    private long d;
    private byte e;
    private int f;

    static {
        am amVar = new am();
        a = amVar;
        amVar.c = 0;
        amVar.d = 0;
    }

    private am() {
        this.e = (byte) -1;
        this.f = -1;
    }

    private am(an anVar) {
        super((byte) 0);
        this.e = (byte) -1;
        this.f = -1;
    }

    public static am a() {
        return a;
    }

    public static an a(am amVar) {
        return new an().a(amVar);
    }

    public static an h() {
        return new an();
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
                i = e.b(1, this.c) + 0;
            }
            if ((this.b & 2) == 2) {
                i += e.b(2, this.d);
            }
            this.f = i;
        }
        return i;
    }

    public final long d() {
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
