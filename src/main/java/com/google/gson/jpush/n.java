package com.google.gson.jpush;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class n extends al<Number> {
    final /* synthetic */ k a;

    n(k kVar) {
        this.a = kVar;
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return Double.valueOf(aVar.k());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Number number = (Number) obj;
        if (number == null) {
            dVar.f();
            return;
        }
        k.a(this.a, number.doubleValue());
        dVar.a(number);
    }
}
