package cn.jpush.android.util;

final class u implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ JRecorder b;

    u(JRecorder jRecorder, int i) {
        this.b = jRecorder;
        this.a = i;
    }

    public final void run() {
        JRecorder.a(this.b, (double) this.a);
    }
}
