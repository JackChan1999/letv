package com.tencent.map.b;

/* compiled from: ProGuard */
public final class m {
    private static m b;
    private int a = 0;

    public static m a() {
        if (b == null) {
            b = new m();
        }
        return b;
    }

    private m() {
    }
}
