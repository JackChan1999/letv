package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.internal.ae;
import com.google.gson.jpush.k;
import java.lang.reflect.Type;
import java.util.Collection;

final class d<E> extends al<Collection<E>> {
    private final al<E> a;
    private final ae<? extends Collection<E>> b;

    public d(k kVar, Type type, al<E> alVar, ae<? extends Collection<E>> aeVar) {
        this.a = new y(kVar, alVar, type);
        this.b = aeVar;
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        Collection collection = (Collection) this.b.a();
        aVar.a();
        while (aVar.e()) {
            collection.add(this.a.a(aVar));
        }
        aVar.b();
        return collection;
    }

    public final /* synthetic */ void a(com.google.gson.jpush.b.d dVar, Object obj) {
        Collection<Object> collection = (Collection) obj;
        if (collection == null) {
            dVar.f();
            return;
        }
        dVar.b();
        for (Object a : collection) {
            this.a.a(dVar, a);
        }
        dVar.c();
    }
}
