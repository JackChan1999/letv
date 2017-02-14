package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class bn extends h {
    private static final bn a;
    private int b;
    private c c;
    private byte d;
    private int e;

    static {
        bn bnVar = new bn();
        a = bnVar;
        bnVar.c = c.a;
    }

    private bn() {
        this.d = (byte) -1;
        this.e = -1;
    }

    private bn(bo boVar) {
        super((byte) 0);
        this.d = (byte) -1;
        this.e = -1;
    }

    public static bn a() {
        return a;
    }

    public static bo a(bn bnVar) {
        return new bo().a(bnVar);
    }

    public static bo f() {
        return new bo();
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

    public final c d() {
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
