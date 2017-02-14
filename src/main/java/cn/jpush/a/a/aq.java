package cn.jpush.a.a;

import com.google.protobuf.jpush.e;
import com.google.protobuf.jpush.h;
import com.google.protobuf.jpush.k;
import java.util.Collections;
import java.util.List;

public final class aq extends h {
    private static final aq a;
    private List<ao> b;
    private byte c;
    private int d;

    static {
        aq aqVar = new aq();
        a = aqVar;
        aqVar.b = Collections.emptyList();
    }

    private aq() {
        this.c = (byte) -1;
        this.d = -1;
    }

    private aq(ar arVar) {
        super((byte) 0);
        this.c = (byte) -1;
        this.d = -1;
    }

    public static aq a() {
        return a;
    }

    public static ar a(aq aqVar) {
        return new ar().a(aqVar);
    }

    public static ar e() {
        return new ar();
    }

    public final void a(e eVar) {
        c();
        for (int i = 0; i < this.b.size(); i++) {
            eVar.a(1, (k) this.b.get(i));
        }
    }

    public final List<ao> b() {
        return this.b;
    }

    public final int c() {
        int i = this.d;
        if (i == -1) {
            i = 0;
            for (int i2 = 0; i2 < this.b.size(); i2++) {
                i += e.b(1, (k) this.b.get(i2));
            }
            this.d = i;
        }
        return i;
    }

    public final boolean d() {
        byte b = this.c;
        if (b != (byte) -1) {
            return b == (byte) 1;
        } else {
            this.c = (byte) 1;
            return true;
        }
    }
}
