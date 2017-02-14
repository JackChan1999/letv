package com.google.gson.jpush;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class o extends al<Number> {
    final /* synthetic */ k a;

    o(k kVar) {
        this.a = kVar;
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return Float.valueOf((float) aVar.k());
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
        k.a(this.a, (double) number.floatValue());
        dVar.a(number);
    }
}
