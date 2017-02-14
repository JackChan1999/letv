package com.google.gson.jpush;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.ag;

final class aj<T> extends al<T> {
    private final ae<T> a;
    private final v<T> b;
    private final k c;
    private final a<T> d;
    private final am e;
    private al<T> f;

    private aj(ae<T> aeVar, v<T> vVar, k kVar, a<T> aVar, am amVar) {
        this.a = aeVar;
        this.b = vVar;
        this.c = kVar;
        this.d = aVar;
        this.e = amVar;
    }

    private al<T> a() {
        al<T> alVar = this.f;
        if (alVar != null) {
            return alVar;
        }
        alVar = this.c.a(this.e, this.d);
        this.f = alVar;
        return alVar;
    }

    public static am a(a<?> aVar, Object obj) {
        return new ak(obj, aVar, false, null, (byte) 0);
    }

    public final T a(com.google.gson.jpush.b.a aVar) {
        if (this.b == null) {
            return a().a(aVar);
        }
        w a = ag.a(aVar);
        return a instanceof y ? null : this.b.a(a, this.d.b(), this.c.a);
    }

    public final void a(d dVar, T t) {
        if (this.a == null) {
            a().a(dVar, t);
        } else if (t == null) {
            dVar.f();
        } else {
            ag.a(this.a.a(t, this.d.b(), this.c.b), dVar);
        }
    }
}
