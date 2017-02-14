package com.flurry.sdk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class iz {
    private static final List<Class> b = new ArrayList();
    private final String a = iz.class.getSimpleName();
    private final Map<Class, Object> c = new LinkedHashMap();

    public static void a(Class cls) {
        if (cls != null) {
            synchronized (b) {
                b.add(cls);
            }
        }
    }

    public static void b(Class cls) {
        if (cls != null) {
            synchronized (b) {
                b.remove(cls);
            }
        }
    }

    public iz() {
        synchronized (b) {
            List<Class> arrayList = new ArrayList(b);
        }
        for (Class cls : arrayList) {
            try {
                Object newInstance = cls.newInstance();
                synchronized (this.c) {
                    this.c.put(cls, newInstance);
                }
            } catch (Throwable e) {
                ib.a(5, this.a, "Module data " + cls + " is not available:", e);
            }
        }
    }

    public Object c(Class cls) {
        if (cls == null) {
            return null;
        }
        Object obj;
        synchronized (this.c) {
            obj = this.c.get(cls);
        }
        return obj;
    }
}
