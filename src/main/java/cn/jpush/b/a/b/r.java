package cn.jpush.b.a.b;

import cn.jpush.a.a.bl;
import cn.jpush.a.a.bm;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class r extends q {
    @a
    String a;
    @a
    String b;
    @a
    int c;
    @a
    String d;

    final p a(long j, String str) {
        bm a = bl.l().a(this.c);
        if (this.a != null) {
            a.a(c.a(this.a));
        }
        if (this.b != null) {
            a.b(c.a(this.b));
        }
        if (this.d != null) {
            a.c(c.a(this.d));
        }
        return new p(1, 1, j, str, a.a());
    }
}
