package cn.jpush.b.a.b;

import cn.jpush.a.a.o;
import cn.jpush.a.a.p;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class g extends q {
    @a
    String a;
    @a
    String b;
    @a
    int c;
    @a
    int d;

    protected final p a(long j, String str) {
        p a = o.n().b(this.c).a(this.d);
        if (this.a != null) {
            a.a(c.a(this.a));
        }
        if (this.b != null) {
            a.b(c.a(this.b));
        }
        return new p(8, 1, j, str, a.a());
    }
}
