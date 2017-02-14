package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.k;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

final class y<T> extends al<T> {
    private final k a;
    private final al<T> b;
    private final Type c;

    y(k kVar, al<T> alVar, Type type) {
        this.a = kVar;
        this.b = alVar;
        this.c = type;
    }

    public final T a(a aVar) {
        return this.b.a(aVar);
    }

    public final void a(d dVar, T t) {
        al a;
        al alVar = this.b;
        Type type = this.c;
        if (t != null && (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class))) {
            type = t.getClass();
        }
        if (type != this.c) {
            a = this.a.a(com.google.gson.jpush.a.a.a(type));
            if ((a instanceof s) && !(this.b instanceof s)) {
                a = this.b;
            }
        } else {
            a = alVar;
        }
        a.a(dVar, t);
    }
}
