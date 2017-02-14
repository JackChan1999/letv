package com.flurry.sdk;

import android.os.Build.VERSION;

public final class ic {
    private final Class<? extends ie> a;
    private final int b;

    public ic(Class<? extends ie> cls, int i) {
        this.a = cls;
        this.b = i;
    }

    public Class<? extends ie> a() {
        return this.a;
    }

    public boolean b() {
        return this.a != null && VERSION.SDK_INT >= this.b;
    }
}
