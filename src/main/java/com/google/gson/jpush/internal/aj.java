package com.google.gson.jpush.internal;

import java.lang.reflect.Method;

final class aj extends UnsafeAllocator {
    final /* synthetic */ Method a;
    final /* synthetic */ Object b;

    aj(Method method, Object obj) {
        this.a = method;
        this.b = obj;
    }

    public final <T> T newInstance(Class<T> cls) {
        return this.a.invoke(this.b, new Object[]{cls});
    }
}
