package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class bb extends al<Number> {
    bb() {
    }

    private static Number b(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        try {
            return Byte.valueOf((byte) aVar.m());
        } catch (Throwable e) {
            throw new af(e);
        }
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }

    public final /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
        dVar.a((Number) obj);
    }
}
