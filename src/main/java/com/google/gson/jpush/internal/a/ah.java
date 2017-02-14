package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class ah extends al<StringBuilder> {
    ah() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return new StringBuilder(aVar.h());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        StringBuilder stringBuilder = (StringBuilder) obj;
        dVar.b(stringBuilder == null ? null : stringBuilder.toString());
    }
}
