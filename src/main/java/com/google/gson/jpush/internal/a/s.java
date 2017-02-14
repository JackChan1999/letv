package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.ae;
import java.util.Map;

public final class s<T> extends al<T> {
    private final ae<T> a;
    private final Map<String, t> b;

    private s(ae<T> aeVar, Map<String, t> map) {
        this.a = aeVar;
        this.b = map;
    }

    public final T a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        T a = this.a.a();
        try {
            aVar.c();
            while (aVar.e()) {
                t tVar = (t) this.b.get(aVar.g());
                if (tVar == null || !tVar.i) {
                    aVar.n();
                } else {
                    tVar.a(aVar, (Object) a);
                }
            }
            aVar.d();
            return a;
        } catch (Throwable e) {
            throw new af(e);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        }
    }

    public final void a(d dVar, T t) {
        if (t == null) {
            dVar.f();
            return;
        }
        dVar.d();
        try {
            for (t tVar : this.b.values()) {
                if (tVar.a(t)) {
                    dVar.a(tVar.g);
                    tVar.a(dVar, (Object) t);
                }
            }
            dVar.e();
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        }
    }
}
