package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.annotations.c;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import java.util.HashMap;
import java.util.Map;

final class bg<T extends Enum<T>> extends al<T> {
    private final Map<String, T> a = new HashMap();
    private final Map<T, String> b = new HashMap();

    public bg(Class<T> cls) {
        try {
            for (Enum enumR : (Enum[]) cls.getEnumConstants()) {
                String name = enumR.name();
                c cVar = (c) cls.getField(name).getAnnotation(c.class);
                if (cVar != null) {
                    name = cVar.a();
                    for (Object put : cVar.b()) {
                        this.a.put(put, enumR);
                    }
                }
                String str = name;
                this.a.put(str, enumR);
                this.b.put(enumR, str);
            }
        } catch (NoSuchFieldException e) {
            throw new AssertionError();
        }
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != com.google.gson.jpush.b.c.i) {
            return (Enum) this.a.get(aVar.h());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Enum enumR = (Enum) obj;
        dVar.b(enumR == null ? null : (String) this.b.get(enumR));
    }
}
