package com.google.gson.jpush;

import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;

final class q<T> extends al<T> {
    private al<T> a;

    q() {
    }

    public final T a(a aVar) {
        if (this.a != null) {
            return this.a.a(aVar);
        }
        throw new IllegalStateException();
    }

    public final void a(al<T> alVar) {
        if (this.a != null) {
            throw new AssertionError();
        }
        this.a = alVar;
    }

    public final void a(d dVar, T t) {
        if (this.a == null) {
            throw new IllegalStateException();
        }
        this.a.a(dVar, t);
    }
}
