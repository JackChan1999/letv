package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.k;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class a<E> extends al<Object> {
    public static final am a = new b();
    private final Class<E> b;
    private final al<E> c;

    public a(k kVar, al<E> alVar, Class<E> cls) {
        this.c = new y(kVar, alVar, cls);
        this.b = cls;
    }

    public final Object a(com.google.gson.jpush.b.a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        List arrayList = new ArrayList();
        aVar.a();
        while (aVar.e()) {
            arrayList.add(this.c.a(aVar));
        }
        aVar.b();
        Object newInstance = Array.newInstance(this.b, arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Array.set(newInstance, i, arrayList.get(i));
        }
        return newInstance;
    }

    public final void a(d dVar, Object obj) {
        if (obj == null) {
            dVar.f();
            return;
        }
        dVar.b();
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            this.c.a(dVar, Array.get(obj, i));
        }
        dVar.c();
    }
}
