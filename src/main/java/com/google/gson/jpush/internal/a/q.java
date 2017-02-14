package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.annotations.b;
import com.google.gson.jpush.annotations.c;
import com.google.gson.jpush.internal.af;
import com.google.gson.jpush.internal.f;
import com.google.gson.jpush.internal.s;
import com.google.gson.jpush.j;
import com.google.gson.jpush.k;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class q implements am {
    private static final String z;
    private final f a;
    private final j b;
    private final s c;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = "7n\u001aK:vx\u001a[vz\u0013\\?gf\u001a\b\u001cDE1\b0~o\u0013L%7d\u001eE3s*";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        r3 = 1;
        if (r1 > r3) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0010:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003e;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 86;
    L_0x0019:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 23;
        goto L_0x0019;
    L_0x0038:
        r5 = 10;
        goto L_0x0019;
    L_0x003b:
        r5 = 127; // 0x7f float:1.78E-43 double:6.27E-322;
        goto L_0x0019;
    L_0x003e:
        r5 = 40;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.q.<clinit>():void");
    }

    public q(f fVar, j jVar, s sVar) {
        this.a = fVar;
        this.b = jVar;
        this.c = sVar;
    }

    static /* synthetic */ al a(q qVar, k kVar, Field field, a aVar) {
        b bVar = (b) field.getAnnotation(b.class);
        if (bVar != null) {
            al a = g.a(qVar.a, kVar, aVar, bVar);
            if (a != null) {
                return a;
            }
        }
        return kVar.a(aVar);
    }

    private List<String> a(Field field) {
        j jVar = this.b;
        c cVar = (c) field.getAnnotation(c.class);
        List<String> linkedList = new LinkedList();
        if (cVar == null) {
            linkedList.add(jVar.a(field));
        } else {
            linkedList.add(cVar.a());
            for (Object add : cVar.b()) {
                linkedList.add(add);
            }
        }
        return linkedList;
    }

    private Map<String, t> a(k kVar, a<?> aVar, Class<?> cls) {
        Map<String, t> linkedHashMap = new LinkedHashMap();
        if (cls.isInterface()) {
            return linkedHashMap;
        }
        Type b = aVar.b();
        Class a;
        while (a != Object.class) {
            for (Field field : a.getDeclaredFields()) {
                boolean a2 = a(field, true);
                boolean a3 = a(field, false);
                if (a2 || a3) {
                    field.setAccessible(true);
                    Type a4 = com.google.gson.jpush.internal.b.a(r21.b(), a, field.getGenericType());
                    List a5 = a(field);
                    t tVar = null;
                    int i = 0;
                    while (i < a5.size()) {
                        String str = (String) a5.get(i);
                        if (i != 0) {
                            a2 = false;
                        }
                        a a6 = a.a(a4);
                        t tVar2 = (t) linkedHashMap.put(str, new r(this, str, a2, a3, kVar, field, a6, af.a(a6.a())));
                        if (tVar != null) {
                            tVar2 = tVar;
                        }
                        i++;
                        tVar = tVar2;
                    }
                    if (tVar != null) {
                        throw new IllegalArgumentException(b + z + tVar.g);
                    }
                }
            }
            a a7 = a.a(com.google.gson.jpush.internal.b.a(a7.b(), a, a.getGenericSuperclass()));
            a = a7.a();
        }
        return linkedHashMap;
    }

    private boolean a(Field field, boolean z) {
        s sVar = this.c;
        return (sVar.a(field.getType(), z) || sVar.a(field, z)) ? false : true;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        Class a = aVar.a();
        return !Object.class.isAssignableFrom(a) ? null : new s(this.a.a((a) aVar), a(kVar, aVar, a), (byte) 0);
    }
}
