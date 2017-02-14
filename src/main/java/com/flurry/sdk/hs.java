package com.flurry.sdk;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class hs<K, V> {
    private final Map<K, List<V>> a;
    private int b;

    public hs() {
        this.a = new HashMap();
    }

    public hs(Map<K, List<V>> map) {
        this.a = map;
    }

    public void a() {
        this.a.clear();
    }

    public List<V> a(K k) {
        if (k == null) {
            return Collections.emptyList();
        }
        List<V> a = a((Object) k, false);
        if (a == null) {
            return Collections.emptyList();
        }
        return a;
    }

    public void a(K k, V v) {
        if (k != null) {
            a((Object) k, true).add(v);
        }
    }

    public void a(hs<K, V> hsVar) {
        if (hsVar != null) {
            for (Entry entry : hsVar.a.entrySet()) {
                a(entry.getKey(), true).addAll((Collection) entry.getValue());
            }
        }
    }

    public boolean b(K k, V v) {
        boolean z = false;
        if (k != null) {
            List a = a((Object) k, false);
            if (a != null) {
                z = a.remove(v);
                if (a.size() == 0) {
                    this.a.remove(k);
                }
            }
        }
        return z;
    }

    public boolean b(K k) {
        if (k == null) {
            return false;
        }
        return ((List) this.a.remove(k)) != null;
    }

    public boolean c(K k, V v) {
        if (k == null) {
            return false;
        }
        List a = a((Object) k, false);
        if (a != null) {
            return a.contains(v);
        }
        return false;
    }

    public Collection<Entry<K, V>> b() {
        Collection arrayList = new ArrayList();
        for (Entry entry : this.a.entrySet()) {
            for (Object simpleImmutableEntry : (List) entry.getValue()) {
                arrayList.add(new SimpleImmutableEntry(entry.getKey(), simpleImmutableEntry));
            }
        }
        return arrayList;
    }

    public Set<K> c() {
        return this.a.keySet();
    }

    public Collection<V> d() {
        Collection arrayList = new ArrayList();
        for (Entry value : this.a.entrySet()) {
            arrayList.addAll((Collection) value.getValue());
        }
        return arrayList;
    }

    private List<V> a(K k, boolean z) {
        List<V> list = (List) this.a.get(k);
        if (z && list == null) {
            if (this.b > 0) {
                list = new ArrayList(this.b);
            } else {
                list = new ArrayList();
            }
            this.a.put(k, list);
        }
        return list;
    }
}
