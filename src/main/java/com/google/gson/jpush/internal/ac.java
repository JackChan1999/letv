package com.google.gson.jpush.internal;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class ac<T> implements Iterator<T> {
    ad<K, V> b;
    ad<K, V> c;
    int d;
    final /* synthetic */ w e;

    private ac(w wVar) {
        this.e = wVar;
        this.b = this.e.e.d;
        this.c = null;
        this.d = this.e.d;
    }

    final ad<K, V> a() {
        ad<K, V> adVar = this.b;
        if (adVar == this.e.e) {
            throw new NoSuchElementException();
        } else if (this.e.d != this.d) {
            throw new ConcurrentModificationException();
        } else {
            this.b = adVar.d;
            this.c = adVar;
            return adVar;
        }
    }

    public final boolean hasNext() {
        return this.b != this.e.e;
    }

    public final void remove() {
        if (this.c == null) {
            throw new IllegalStateException();
        }
        this.e.a(this.c, true);
        this.c = null;
        this.d = this.e.d;
    }
}
