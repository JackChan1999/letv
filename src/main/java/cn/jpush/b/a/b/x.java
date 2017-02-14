package cn.jpush.b.a.b;

import cn.jpush.a.a.j;
import cn.jpush.a.a.k;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class x extends q {
    @a
    long a;
    @a
    String b;
    @a
    String c;

    final p a(long j, String str) {
        k a = j.j().a(this.a);
        if (this.b != null) {
            a.a(c.a(this.b));
        }
        if (this.c != null) {
            a.b(c.a(this.c));
        }
        return new p(7, 1, j, str, a.a());
    }
}
