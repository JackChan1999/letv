package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;

abstract class t {
    final String g;
    final boolean h;
    final boolean i;

    protected t(String str, boolean z, boolean z2) {
        this.g = str;
        this.h = z;
        this.i = z2;
    }

    abstract void a(a aVar, Object obj);

    abstract void a(d dVar, Object obj);

    abstract boolean a(Object obj);
}
