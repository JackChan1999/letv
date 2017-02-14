package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.internal.b;
import com.google.gson.jpush.internal.f;
import com.google.gson.jpush.k;
import java.lang.reflect.Type;
import java.util.Map;

public final class l implements am {
    private final f a;
    private final boolean b;

    public l(f fVar, boolean z) {
        this.a = fVar;
        this.b = z;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        Type b = aVar.b();
        if (!Map.class.isAssignableFrom(aVar.a())) {
            return null;
        }
        Type[] b2 = b.b(b, b.b(b));
        b = b2[0];
        al a = (b == Boolean.TYPE || b == Boolean.class) ? z.f : kVar.a(a.a(b));
        return new m(this, kVar, b2[0], a, b2[1], kVar.a(a.a(b2[1])), this.a.a((a) aVar));
    }
}
