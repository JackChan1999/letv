package cn.jpush.b.a.b;

import cn.jpush.a.a.bg;
import cn.jpush.a.a.bi;
import cn.jpush.a.a.bj;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class v extends q {
    public static final String a = v.class.getName();
    @a
    long b;
    @a
    String c;

    final p a(long j, String str) {
        bj a = bi.j().a(this.b);
        if (this.c != null) {
            a.a(bg.j().a(c.a(this.c)).a());
        }
        return new p(3, 1, j, str, a.a());
    }
}
