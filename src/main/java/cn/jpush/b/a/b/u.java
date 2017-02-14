package cn.jpush.b.a.b;

import cn.jpush.a.a.bp;
import cn.jpush.a.a.bq;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class u extends q {
    @a
    String a;

    final p a(long j, String str) {
        bq f = bp.f();
        if (this.a != null) {
            f.a(c.a(this.a));
        }
        return new p(23, 1, j, str, f.a());
    }
}
