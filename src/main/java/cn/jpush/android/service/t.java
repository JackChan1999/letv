package cn.jpush.android.service;

import android.os.PowerManager.WakeLock;

public final class t {
    private static t a = null;
    private WakeLock b = null;

    private t() {
    }

    public static t a() {
        if (a == null) {
            a = new t();
        }
        return a;
    }

    public final void a(WakeLock wakeLock) {
        this.b = wakeLock;
    }

    public final WakeLock b() {
        return this.b;
    }
}
