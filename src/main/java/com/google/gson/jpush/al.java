package com.google.gson.jpush;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.a.j;

public abstract class al<T> {
    public final w a(T t) {
        try {
            d jVar = new j();
            a(jVar, t);
            return jVar.a();
        } catch (Throwable e) {
            throw new x(e);
        }
    }

    public abstract T a(a aVar);

    public abstract void a(d dVar, T t);
}
