package cn.jpush.b.a.b;

import cn.jpush.a.a.d;
import cn.jpush.a.a.e;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class b extends q {
    @a
    long a;
    @a
    String b;
    @a
    String c;
    @a
    int d;
    @a
    String e;

    final p a(long j, String str) {
        e a = d.n().a(this.a).a(this.d);
        if (this.b != null) {
            a.a(c.a(this.b));
        }
        if (this.c != null) {
            a.b(c.a(this.c));
        }
        if (this.e != null) {
            a.c(c.a(this.e));
        }
        return new p(5, 1, j, str, a.a());
    }
}
