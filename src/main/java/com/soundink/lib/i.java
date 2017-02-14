package com.soundink.lib;

final class i implements Runnable {
    private String[] a;

    public i(String[] strArr) {
        this.a = strArr;
    }

    public final void run() {
        int i = 0;
        while (i < this.a.length) {
            try {
                g.a().a(this.a[i]);
                i++;
            } catch (Throwable th) {
                th.printStackTrace();
                return;
            }
        }
    }
}
