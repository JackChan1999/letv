package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class s extends h {
    private static final s a;
    private int b;
    private long c;
    private byte d;
    private int e;

    static {
        s sVar = new s();
        a = sVar;
        sVar.c = 0;
    }

    private s() {
        this.d = (byte) -1;
        this.e = -1;
    }

    private s(t tVar) {
        super((byte) 0);
        this.d = (byte) -1;
        this.e = -1;
    }

    public static s a() {
        return a;
    }

    public static t a(s sVar) {
        return new t().a(sVar);
    }

    public static t f() {
        return new t();
    }

    public final void a(e eVar) {
        c();
        if ((this.b & 1) == 1) {
            eVar.a(1, this.c);
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = this.e;
        if (i == -1) {
            i = 0;
            if ((this.b & 1) == 1) {
                i = e.b(1, this.c) + 0;
            }
            this.e = i;
        }
        return i;
    }

    public final long d() {
        return this.c;
    }

    public final boolean e() {
        byte b = this.d;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.d = (byte) 1;
            return true;
        }
    }
}
