package cn.jpush.android.helpers;

import android.content.Context;
import android.text.TextUtils;
import cn.jpush.android.util.a;
import cn.jpush.android.util.p;
import cn.jpush.android.util.z;

final class e extends Thread {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ String c;

    e(String str, Context context, String str2) {
        this.a = str;
        this.b = context;
        this.c = str2;
    }

    public final void run() {
        Object obj = null;
        String str = null;
        int i = 0;
        while (i < 4) {
            i++;
            str = p.a(this.a, 5, 8000);
            if (!p.a(str)) {
                obj = 1;
                break;
            }
        }
        if (obj == null || TextUtils.isEmpty(str)) {
            g.a(this.c, 1021, a.b(this.b, this.a), this.b);
            g.a(this.c, 996, this.b);
            z.b();
            return;
        }
        d.a(this.b, str);
    }
}
