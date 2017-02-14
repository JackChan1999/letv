package com.google.gson.jpush;

import com.google.gson.jpush.a.a;

final class ak implements am {
    private final a<?> a;
    private final boolean b;
    private final Class<?> c;
    private final ae<?> d;
    private final v<?> e;

    private ak(Object obj, a<?> aVar, boolean z, Class<?> cls) {
        this.d = obj instanceof ae ? (ae) obj : null;
        this.e = obj instanceof v ? (v) obj : null;
        boolean z2 = (this.d == null && this.e == null) ? false : true;
        com.google.gson.jpush.internal.a.a(z2);
        this.a = aVar;
        this.b = z;
        this.c = cls;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        boolean isAssignableFrom = this.a != null ? this.a.equals(aVar) || (this.b && this.a.b() == aVar.a()) : this.c.isAssignableFrom(aVar.a());
        return isAssignableFrom ? new aj(this.d, this.e, kVar, aVar, this) : null;
    }
}
