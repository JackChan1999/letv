package cn.jpush.android.util;

final class v implements Runnable {
    v() {
    }

    public final void run() {
        JRecorder.f = false;
        z.b();
        JRecorder.a(JRecorder.c);
    }
}
