package cn.jpush.android.service;

import android.content.Context;

final class i implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ h b;

    i(h hVar, Context context) {
        this.b = hVar;
        this.a = context;
    }

    public final void run() {
        this.b.e(this.a);
        this.b.c(this.a);
    }
}
