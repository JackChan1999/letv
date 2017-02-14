package cn.jpush.android.api;

import android.content.Context;

final class g implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ e b;

    g(e eVar, Context context) {
        this.b = eVar;
        this.a = context;
    }

    public final void run() {
        e.b(this.b, this.a);
    }
}
