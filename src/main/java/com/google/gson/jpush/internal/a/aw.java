package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class aw extends al<Boolean> {
    aw() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return aVar.f() == c.f ? Boolean.valueOf(Boolean.parseBoolean(aVar.h())) : Boolean.valueOf(aVar.i());
        } else {
            aVar.j();
            return null;
        }
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Boolean bool = (Boolean) obj;
        if (bool == null) {
            dVar.f();
        } else {
            dVar.a(bool.booleanValue());
        }
    }
}
