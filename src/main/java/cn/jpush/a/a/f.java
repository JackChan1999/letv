package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;
import java.util.Collections;
import java.util.List;

public final class f extends h {
    private static final f a;
    private List<Long> b;
    private byte c;
    private int d;

    static {
        f fVar = new f();
        a = fVar;
        fVar.b = Collections.emptyList();
    }

    private f() {
        this.c = (byte) -1;
        this.d = -1;
    }

    private f(g gVar) {
        super((byte) 0);
        this.c = (byte) -1;
        this.d = -1;
    }

    public static f a() {
        return a;
    }

    public static g a(f fVar) {
        return new g().a(fVar);
    }

    public static g d() {
        return new g();
    }

    public final void a(e eVar) {
        c();
        for (int i = 0; i < this.b.size(); i++) {
            eVar.a(1, ((Long) this.b.get(i)).longValue());
        }
    }

    public final boolean b() {
        byte b = this.c;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.c = (byte) 1;
            return true;
        }
    }

    public final int c() {
        int i = this.d;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.b.size(); i++) {
            i2 += e.a(((Long) this.b.get(i)).longValue());
        }
        int size = (i2 + 0) + (this.b.size() * 1);
        this.d = size;
        return size;
    }
}
