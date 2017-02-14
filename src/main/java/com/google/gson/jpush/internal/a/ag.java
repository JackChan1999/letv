package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.math.BigInteger;

final class ag extends al<BigInteger> {
    ag() {
    }

    private static BigInteger b(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        try {
            return new BigInteger(aVar.h());
        } catch (Throwable e) {
            throw new af(e);
        }
    }

    public final /* synthetic */ Object a(a aVar) {
        return b(aVar);
    }

    public final /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
        dVar.a((BigInteger) obj);
    }
}
