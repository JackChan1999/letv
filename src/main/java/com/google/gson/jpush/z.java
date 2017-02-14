package com.google.gson.jpush;

import com.google.gson.jpush.internal.w;
import java.util.Map.Entry;
import java.util.Set;

public final class z extends w {
    private final w<String, w> a = new w();

    public final Set<Entry<String, w>> a() {
        return this.a.entrySet();
    }

    public final void a(String str, w wVar) {
        Object obj;
        if (wVar == null) {
            obj = y.a;
        }
        this.a.put(str, obj);
    }

    public final boolean a(String str) {
        return this.a.containsKey(str);
    }

    public final w b(String str) {
        return (w) this.a.get(str);
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof z) && ((z) obj).a.equals(this.a));
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
