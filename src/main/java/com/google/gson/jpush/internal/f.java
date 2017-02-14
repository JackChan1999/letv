package com.google.gson.jpush.internal;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.s;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

public final class f {
    private final Map<Type, s<?>> a;

    public f(Map<Type, s<?>> map) {
        this.a = map;
    }

    private <T> ae<T> a(Class<? super T> cls) {
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new l(this, declaredConstructor);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public final <T> ae<T> a(a<T> aVar) {
        Type b = aVar.b();
        Class a = aVar.a();
        s sVar = (s) this.a.get(b);
        if (sVar != null) {
            return new g(this, sVar, b);
        }
        sVar = (s) this.a.get(a);
        if (sVar != null) {
            return new k(this, sVar, b);
        }
        ae<T> a2 = a(a);
        if (a2 != null) {
            return a2;
        }
        a2 = Collection.class.isAssignableFrom(a) ? SortedSet.class.isAssignableFrom(a) ? new m(this) : EnumSet.class.isAssignableFrom(a) ? new n(this, b) : Set.class.isAssignableFrom(a) ? new o(this) : Queue.class.isAssignableFrom(a) ? new p(this) : new q(this) : Map.class.isAssignableFrom(a) ? SortedMap.class.isAssignableFrom(a) ? new r(this) : (!(b instanceof ParameterizedType) || String.class.isAssignableFrom(a.a(((ParameterizedType) b).getActualTypeArguments()[0]).a())) ? new i(this) : new h(this) : null;
        return a2 == null ? new j(this, a, b) : a2;
    }

    public final String toString() {
        return this.a.toString();
    }
}
