package cn.jpush.android.api;

import android.content.Context;
import cn.jpush.android.data.c;

final class n implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ c b;

    n(Context context, c cVar) {
        this.a = context;
        this.b = cVar;
    }

    public final void run() {
        m.b(this.a, this.b);
    }
}
