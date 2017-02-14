package cn.jpush.b.a.b;

import cn.jpush.a.a.be;
import cn.jpush.a.a.bf;
import cn.jpush.a.a.bg;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class o extends q {
    @a
    long a;
    @a
    String b;

    final p a(long j, String str) {
        bf a = be.j().a(this.a);
        if (this.b != null) {
            a.a(bg.j().a(c.a(this.b)).a());
        }
        return new p(4, 1, j, str, a.a());
    }
}
