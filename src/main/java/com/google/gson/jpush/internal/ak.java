package com.google.gson.jpush.internal;

import java.lang.reflect.Method;

final class ak extends UnsafeAllocator {
    final /* synthetic */ Method a;
    final /* synthetic */ int b;

    ak(Method method, int i) {
        this.a = method;
        this.b = i;
    }

    public final <T> T newInstance(Class<T> cls) {
        return this.a.invoke(null, new Object[]{cls, Integer.valueOf(this.b)});
    }
}
