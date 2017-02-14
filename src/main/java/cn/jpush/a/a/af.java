package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class af extends h {
    private static final af a;
    private int b;
    private int c;
    private c d;
    private byte e;
    private int f;

    static {
        af afVar = new af();
        a = afVar;
        afVar.c = 0;
        afVar.d = c.a;
    }

    private af() {
        this.e = (byte) -1;
        this.f = -1;
    }

    private af(ag agVar) {
        super((byte) 0);
        this.e = (byte) -1;
        this.f = -1;
    }

    public static af a() {
        return a;
    }

    public static ag a(af afVar) {
        return new ag().a(afVar);
    }

    public static ag g() {
        return new ag();
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

    public final c f() {
        return this.d;
    }
}
