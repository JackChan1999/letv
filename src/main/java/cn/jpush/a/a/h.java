package cn.jpush.a.a;

import com.google.protobuf.jpush.e;

public final class h extends com.google.protobuf.jpush.h {
    private static final h a;
    private int b;
    private long c;
    private byte d;
    private int e;

    static {
        h hVar = new h();
        a = hVar;
        hVar.c = 0;
    }

    private h() {
        this.d = (byte) -1;
        this.e = -1;
    }

    private h(i iVar) {
        super((byte) 0);
        this.d = (byte) -1;
        this.e = -1;
    }

    public static h a() {
        return a;
    }

    public static i a(h hVar) {
        return new i().a(hVar);
    }

    public static i f() {
        return new i();
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
