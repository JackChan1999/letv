package cn.jpush.android.api;

import android.content.Context;

final class h implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ e b;

    h(e eVar, Context context) {
        this.b = eVar;
        this.a = context;
    }

    public final void run() {
        e.a(this.b, this.a);
    }
}
