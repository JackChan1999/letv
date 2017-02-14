package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.k;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

final class b implements am {
    b() {
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        Type b = aVar.b();
        if (!(b instanceof GenericArrayType) && (!(b instanceof Class) || !((Class) b).isArray())) {
            return null;
        }
        b = com.google.gson.jpush.internal.b.d(b);
        return new a(kVar, kVar.a(a.a(b)), com.google.gson.jpush.internal.b.b(b));
    }
}
