package com.flurry.sdk;

import android.content.Context;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class id {
    private static final String a = id.class.getSimpleName();
    private static final Map<Class<? extends ie>, ic> b = new LinkedHashMap();
    private final Map<Class<? extends ie>, ie> c = new LinkedHashMap();

    public static void a(Class<? extends ie> cls, int i) {
        if (cls != null) {
            synchronized (b) {
                b.put(cls, new ic(cls, i));
            }
        }
    }

    public synchronized void a(Context context) {
        if (context == null) {
            ib.a(5, a, "Null context.");
        } else {
            synchronized (b) {
                List<ic> arrayList = new ArrayList(b.values());
            }
            for (ic icVar : arrayList) {
                try {
                    if (icVar.b()) {
                        ie ieVar = (ie) icVar.a().newInstance();
                        ieVar.a(context);
                        this.c.put(icVar.a(), ieVar);
                    }
                } catch (Throwable e) {
                    ib.a(5, a, "Flurry Module for class " + icVar.a() + " is not available:", e);
                }
            }
            jb.a().a(context);
            hr.a();
        }
    }

    public synchronized void a() {
        hr.b();
        jb.b();
        List b = b();
        for (int size = b.size() - 1; size >= 0; size--) {
            try {
                ((ie) this.c.remove(((ie) b.get(size)).getClass())).b();
            } catch (Throwable e) {
                ib.a(5, a, "Error destroying module:", e);
            }
        }
    }

    public ie a(Class<? extends ie> cls) {
        if (cls == null) {
            return null;
        }
        synchronized (this.c) {
            ie ieVar = (ie) this.c.get(cls);
        }
        if (ieVar != null) {
            return ieVar;
        }
        throw new IllegalStateException("Module was not registered/initialized. " + cls);
    }

    private List<ie> b() {
        List<ie> arrayList = new ArrayList();
        synchronized (this.c) {
            arrayList.addAll(this.c.values());
        }
        return arrayList;
    }
}
