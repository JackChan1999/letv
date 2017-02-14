package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.internal.b;
import com.google.gson.jpush.internal.f;
import com.google.gson.jpush.k;
import java.lang.reflect.Type;
import java.util.Collection;

public final class c implements am {
    private final f a;

    public c(f fVar) {
        this.a = fVar;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        Type b = aVar.b();
        Class a = aVar.a();
        if (!Collection.class.isAssignableFrom(a)) {
            return null;
        }
        Type a2 = b.a(b, a);
        return new d(kVar, a2, kVar.a(a.a(a2)), this.a.a((a) aVar));
    }
}
