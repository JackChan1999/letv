package cn.jpush.b.a.b;

import cn.jpush.a.a.bn;
import cn.jpush.a.a.bo;
import com.google.gson.jpush.annotations.a;
import com.google.protobuf.jpush.c;

public class s extends q {
    @a
    String a;

    final p a(long j, String str) {
        bo f = bn.f();
        if (this.a != null) {
            f.a(c.a(this.a));
        }
        return new p(2, 1, j, str, f.a());
    }
}
