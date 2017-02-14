package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;
import java.util.Collections;
import java.util.List;

public final class x extends h {
    private static final x a;
    private List<Integer> b;
    private byte c;
    private int d;

    static {
        x xVar = new x();
        a = xVar;
        xVar.b = Collections.emptyList();
    }

    private x() {
        this.c = (byte) -1;
        this.d = -1;
    }

    private x(y yVar) {
        super((byte) 0);
        this.c = (byte) -1;
        this.d = -1;
    }

    public static x a() {
        return a;
    }

    public static y a(x xVar) {
        return new y().a(xVar);
    }

    public static y b() {
        return new y();
    }

    public final void a(e eVar) {
        c();
        for (int i = 0; i < this.b.size(); i++) {
            eVar.a(1, ((Integer) this.b.get(i)).intValue());
        }
    }

    public final int c() {
        int i = this.d;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (i = 0; i < this.b.size(); i++) {
            i2 += e.a(((Integer) this.b.get(i)).intValue());
        }
        int size = (i2 + 0) + (this.b.size() * 1);
        this.d = size;
        return size;
    }
}
