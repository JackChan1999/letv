package com.letv.jarlibs.chat.ex;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class HashList<T> {
    Hashtable<String, TaggedList<T>> internal = new Hashtable();

    public Set<String> keySet() {
        return this.internal.keySet();
    }

    public synchronized <V> V tag(String key) {
        V v;
        TaggedList<T> list = (TaggedList) this.internal.get(key);
        if (list == null) {
            v = null;
        } else {
            v = list.tag();
        }
        return v;
    }

    public synchronized <V> void tag(String key, V tag) {
        TaggedList<T> list = (TaggedList) this.internal.get(key);
        if (list == null) {
            list = new TaggedList();
            this.internal.put(key, list);
        }
        list.tag(tag);
    }

    public synchronized ArrayList<T> remove(String key) {
        return (ArrayList) this.internal.remove(key);
    }

    public synchronized int size() {
        return this.internal.size();
    }

    public synchronized ArrayList<T> get(String key) {
        return (ArrayList) this.internal.get(key);
    }

    public synchronized boolean contains(String key) {
        boolean z;
        ArrayList<T> check = get(key);
        z = check != null && check.size() > 0;
        return z;
    }

    public synchronized void add(String key, T value) {
        ArrayList<T> ret = get(key);
        if (ret == null) {
            ArrayList<T> put = new TaggedList();
            ret = put;
            this.internal.put(key, put);
        }
        ret.add(value);
    }

    public synchronized T pop(String key) {
        T t = null;
        synchronized (this) {
            TaggedList<T> values = (TaggedList) this.internal.get(key);
            if (values != null) {
                if (values.size() != 0) {
                    t = values.remove(values.size() - 1);
                }
            }
        }
        return t;
    }

    public synchronized boolean removeItem(String key, T value) {
        boolean z = false;
        synchronized (this) {
            TaggedList<T> values = (TaggedList) this.internal.get(key);
            if (values != null) {
                values.remove(value);
                if (values.size() == 0) {
                    z = true;
                }
            }
        }
        return z;
    }
}
