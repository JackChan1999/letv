package com.flurry.sdk;

import android.text.TextUtils;

public abstract class hv {
    protected String f = "com.flurry.android.sdk.ReplaceMeWithAProperEventName";

    public hv(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Event must have a name!");
        }
        this.f = str;
    }

    public String a() {
        return this.f;
    }

    public void b() {
        hx.a().a(this);
    }
}
