package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.math.BigDecimal;

final class af extends al<BigDecimal> {
    af() {
    }

    private static BigDecimal b(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        try {
            return new BigDecimal(aVar.h());
        } catch (Throwable e) {
            throw new com.google.gson.jpush.af(e);
        }
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }

    public final /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
        dVar.a((BigDecimal) obj);
    }
}
