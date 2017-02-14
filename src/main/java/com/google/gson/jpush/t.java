package com.google.gson.jpush;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class t extends w implements Iterable<w> {
    private final List<w> a = new ArrayList();

    public final int a() {
        return this.a.size();
    }

    public final w a(int i) {
        return (w) this.a.get(2);
    }

    public final void a(w wVar) {
        Object obj;
        if (wVar == null) {
            obj = y.a;
        }
        this.a.add(obj);
    }

    public final Number b() {
        if (this.a.size() == 1) {
            return ((w) this.a.get(0)).b();
        }
        throw new IllegalStateException();
    }

    public final String c() {
        if (this.a.size() == 1) {
            return ((w) this.a.get(0)).c();
        }
        throw new IllegalStateException();
    }

    public final double d() {
        if (this.a.size() == 1) {
            return ((w) this.a.get(0)).d();
        }
        throw new IllegalStateException();
    }

    public final long e() {
        if (this.a.size() == 1) {
            return ((w) this.a.get(0)).e();
        }
        throw new IllegalStateException();
    }

    public final boolean equals(Object obj) {
        return obj == this || ((obj instanceof t) && ((t) obj).a.equals(this.a));
    }

    public final int f() {
        if (this.a.size() == 1) {
            return ((w) this.a.get(0)).f();
        }
        throw new IllegalStateException();
    }

    public final boolean g() {
        if (this.a.size() == 1) {
            return ((w) this.a.get(0)).g();
        }
        throw new IllegalStateException();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final Iterator<w> iterator() {
        return this.a.iterator();
    }
}
