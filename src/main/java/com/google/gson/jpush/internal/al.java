package com.google.gson.jpush.internal;

import java.lang.reflect.Method;

final class al extends UnsafeAllocator {
    final /* synthetic */ Method a;

    al(Method method) {
        this.a = method;
    }

    public final <T> T newInstance(Class<T> cls) {
        return this.a.invoke(null, new Object[]{cls, Object.class});
    }
}
