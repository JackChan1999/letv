package com.google.gson.jpush.internal;

import java.util.Map.Entry;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

final class ad<K, V> implements Entry<K, V> {
    ad<K, V> a;
    ad<K, V> b;
    ad<K, V> c;
    ad<K, V> d;
    ad<K, V> e;
    final K f;
    V g;
    int h;

    ad() {
        this.f = null;
        this.e = this;
        this.d = this;
    }

    ad(ad<K, V> adVar, K k, ad<K, V> adVar2, ad<K, V> adVar3) {
        this.a = adVar;
        this.f = k;
        this.h = 1;
        this.d = adVar2;
        this.e = adVar3;
        adVar3.d = this;
        adVar2.e = this;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Entry)) {
            return false;
        }
        Entry entry = (Entry) obj;
        if (this.f == null) {
            if (entry.getKey() != null) {
                return false;
            }
        } else if (!this.f.equals(entry.getKey())) {
            return false;
        }
        if (this.g == null) {
            if (entry.getValue() != null) {
                return false;
            }
        } else if (!this.g.equals(entry.getValue())) {
            return false;
        }
        return true;
    }

    public final K getKey() {
        return this.f;
    }

    public final V getValue() {
        return this.g;
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = this.f == null ? 0 : this.f.hashCode();
        if (this.g != null) {
            i = this.g.hashCode();
        }
        return hashCode ^ i;
    }

    public final V setValue(V v) {
        V v2 = this.g;
        this.g = v;
        return v2;
    }

    public final String toString() {
        return this.f + SearchCriteria.EQ + this.g;
    }
}
