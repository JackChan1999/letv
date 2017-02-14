package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;
import java.util.Collections;
import java.util.List;

public final class q extends h {
    private static final q a;
    private int b;
    private long c;
    private int d;
    private List<Long> e;
    private byte f;
    private int g;

    static {
        q qVar = new q();
        a = qVar;
        qVar.c = 0;
        qVar.d = 0;
        qVar.e = Collections.emptyList();
    }

    private q() {
        this.f = (byte) -1;
        this.g = -1;
    }

    private q(r rVar) {
        super((byte) 0);
        this.f = (byte) -1;
        this.g = -1;
    }

    public static q a() {
        return a;
    }

    public static r a(q qVar) {
        return new r().a(qVar);
    }

    public static r h() {
        return new r();
    }

    public final void a(e eVar) {
        c();
        if ((this.b & 1) == 1) {
            eVar.a(1, this.c);
        }
        if ((this.b & 2) == 2) {
            eVar.a(2, this.d);
        }
        for (int i = 0; i < this.e.size(); i++) {
            eVar.a(3, ((Long) this.e.get(i)).longValue());
        }
    }

    public final boolean b() {
        return (this.b & 1) == 1;
    }

    public final int c() {
        int i = 0;
        int i2 = this.g;
        if (i2 != -1) {
            return i2;
        }
        i2 = (this.b & 1) == 1 ? e.b(1, this.c) + 0 : 0;
        int c = (this.b & 2) == 2 ? i2 + e.c(2, this.d) : i2;
        int i3 = 0;
        while (i < this.e.size()) {
            i++;
            i3 = e.a(((Long) this.e.get(i)).longValue()) + i3;
        }
        i2 = (c + i3) + (this.e.size() * 1);
        this.g = i2;
        return i2;
    }

    public final long d() {
        return this.c;
    }

    public final boolean e() {
        return (this.b & 2) == 2;
    }

    public final int f() {
        return this.d;
    }

    public final boolean g() {
        byte b = this.f;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.f = (byte) 1;
            return true;
        }
    }
}
