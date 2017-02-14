package cn.jpush.a.a;

import com.google.protobuf.jpush.c;
import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;

public final class bp extends h {
    private static final bp a;
    private int b;
    private c c;
    private byte d;
    private int e;

    static {
        bp bpVar = new bp();
        a = bpVar;
        bpVar.c = c.a;
    }

    private bp() {
        this.d = (byte) -1;
        this.e = -1;
    }

    private bp(bq bqVar) {
        super((byte) 0);
        this.d = (byte) -1;
        this.e = -1;
    }

    public static bp a() {
        return a;
    }

    public static bq a(bp bpVar) {
        return new bq().a(bpVar);
    }

    public static bq f() {
        return new bq();
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
