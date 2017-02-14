package com.google.gson.jpush;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class p extends al<Number> {
    final /* synthetic */ k a;

    p(k kVar) {
        this.a = kVar;
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return Long.valueOf(aVar.l());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Number number = (Number) obj;
        if (number == null) {
            dVar.f();
        } else {
            dVar.b(number.toString());
        }
    }
}
