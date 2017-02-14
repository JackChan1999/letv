package cn.jpush.b.a.b;

import cn.jpush.a.a.u;
import cn.jpush.a.a.v;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class w extends q {
    @a
    long a;
    @a
    String b;
    @a
    String c;

    final p a(long j, String str) {
        v a = u.j().a(this.a);
        if (this.b != null) {
            a.a(c.a(this.b));
        }
        if (this.c != null) {
            a.b(c.a(this.c));
        }
        return new p(12, 1, j, str, a.a());
    }
}
