package com.google.gson.jpush.internal;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.k;

final class t extends al<T> {
    final /* synthetic */ boolean a;
    final /* synthetic */ boolean b;
    final /* synthetic */ k c;
    final /* synthetic */ a d;
    final /* synthetic */ s e;
    private al<T> f;

    t(s sVar, boolean z, boolean z2, k kVar, a aVar) {
        this.e = sVar;
        this.a = z;
        this.b = z2;
        this.c = kVar;
        this.d = aVar;
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

    public final T a(com.google.gson.jpush.b.a aVar) {
        if (!this.a) {
            return a().a(aVar);
        }
        aVar.n();
        return null;
    }

    public final void a(d dVar, T t) {
        if (this.b) {
            dVar.f();
        } else {
            a().a(dVar, t);
        }
    }
}
