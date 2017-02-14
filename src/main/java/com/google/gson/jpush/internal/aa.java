package com.google.gson.jpush.internal;

import java.util.AbstractSet;
import java.util.Iterator;

final class aa extends AbstractSet<K> {
    final /* synthetic */ w a;

    aa(w wVar) {
        this.a = wVar;
    }

    public final void clear() {
        this.a.clear();
    }

    public final boolean contains(Object obj) {
        return this.a.containsKey(obj);
    }

    public final Iterator<K> iterator() {
        return new ab(this);
    }

    public final boolean remove(Object obj) {
        return this.a.a(obj) != null;
    }

    public final int size() {
        return this.a.c;
    }
}
