package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.w;
import com.google.gson.jpush.k;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class n extends al<Object> {
    public static final am a = new o();
    private final k b;

    private n(k kVar) {
        this.b = kVar;
    }

    public final Object a(a aVar) {
        switch (p.a[aVar.f().ordinal()]) {
            case 1:
                List arrayList = new ArrayList();
                aVar.a();
                while (aVar.e()) {
                    arrayList.add(a(aVar));
                }
                aVar.b();
                return arrayList;
            case 2:
                Map wVar = new w();
                aVar.c();
                while (aVar.e()) {
                    wVar.put(aVar.g(), a(aVar));
                }
                aVar.d();
                return wVar;
            case 3:
                return aVar.h();
            case 4:
                return Double.valueOf(aVar.k());
            case 5:
                return Boolean.valueOf(aVar.i());
            case 6:
                aVar.j();
                return null;
            default:
                throw new IllegalStateException();
        }
    }

    public final void a(d dVar, Object obj) {
        if (obj == null) {
            dVar.f();
            return;
        }
        al a = this.b.a(obj.getClass());
        if (a instanceof n) {
            dVar.d();
            dVar.e();
            return;
        }
        a.a(dVar, obj);
    }
}
