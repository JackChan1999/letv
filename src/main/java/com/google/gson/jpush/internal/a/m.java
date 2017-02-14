package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.ac;
import com.google.gson.jpush.af;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.ae;
import com.google.gson.jpush.internal.ag;
import com.google.gson.jpush.internal.u;
import com.google.gson.jpush.k;
import com.google.gson.jpush.t;
import com.google.gson.jpush.w;
import com.google.gson.jpush.y;
import com.google.gson.jpush.z;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

final class m<K, V> extends al<Map<K, V>> {
    private static final String[] z;
    final /* synthetic */ l a;
    private final al<K> b;
    private final al<V> c;
    private final ae<? extends Map<K, V>> d;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "Z/\u00023`];\u0006:)U?\u000be)";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0057;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004b;
            case 1: goto L_0x004e;
            case 2: goto L_0x0051;
            case 3: goto L_0x0054;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 9;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0018;
    L_0x002d:
        r7 = r2;
        r2 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r3] = r2;
        r0 = "P/\u001e3";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r3] = r2;
        z = r6;
        return;
    L_0x004b:
        r11 = 62;
        goto L_0x0021;
    L_0x004e:
        r11 = 90;
        goto L_0x0021;
    L_0x0051:
        r11 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        goto L_0x0021;
    L_0x0054:
        r11 = 95;
        goto L_0x0021;
    L_0x0057:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.a.m.<clinit>():void");
    }

    public m(l lVar, k kVar, Type type, al<K> alVar, Type type2, al<V> alVar2, ae<? extends Map<K, V>> aeVar) {
        this.a = lVar;
        this.b = new y(kVar, alVar, type);
        this.c = new y(kVar, alVar2, type2);
        this.d = aeVar;
    }

    public final /* synthetic */ Object a(a aVar) {
        c f = aVar.f();
        if (f == c.i) {
            aVar.j();
            return null;
        }
        Map map = (Map) this.d.a();
        Object a;
        if (f == c.a) {
            aVar.a();
            while (aVar.e()) {
                aVar.a();
                a = this.b.a(aVar);
                if (map.put(a, this.c.a(aVar)) != null) {
                    throw new af(new StringBuilder(z[0]).append(a).toString());
                }
                aVar.b();
            }
            aVar.b();
            return map;
        }
        aVar.c();
        while (aVar.e()) {
            u.a.a(aVar);
            a = this.b.a(aVar);
            if (map.put(a, this.c.a(aVar)) != null) {
                throw new af(new StringBuilder(z[0]).append(a).toString());
            }
        }
        aVar.d();
        return map;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        int i = 0;
        Map map = (Map) obj;
        if (map == null) {
            dVar.f();
        } else if (this.a.b) {
            List arrayList = new ArrayList(map.size());
            List arrayList2 = new ArrayList(map.size());
            int i2 = 0;
            for (Entry entry : map.entrySet()) {
                w a = this.b.a(entry.getKey());
                arrayList.add(a);
                arrayList2.add(entry.getValue());
                int i3 = ((a instanceof t) || (a instanceof z)) ? 1 : 0;
                i2 = i3 | i2;
            }
            if (i2 != 0) {
                dVar.b();
                while (i < arrayList.size()) {
                    dVar.b();
                    ag.a((w) arrayList.get(i), dVar);
                    this.c.a(dVar, arrayList2.get(i));
                    dVar.c();
                    i++;
                }
                dVar.c();
                return;
            }
            dVar.d();
            while (i < arrayList.size()) {
                String valueOf;
                w wVar = (w) arrayList.get(i);
                if (wVar instanceof ac) {
                    ac j = wVar.j();
                    if (j.k()) {
                        valueOf = String.valueOf(j.b());
                    } else if (j.a()) {
                        valueOf = Boolean.toString(j.g());
                    } else if (j.l()) {
                        valueOf = j.c();
                    } else {
                        throw new AssertionError();
                    }
                } else if (wVar instanceof y) {
                    valueOf = z[1];
                } else {
                    throw new AssertionError();
                }
                dVar.a(valueOf);
                this.c.a(dVar, arrayList2.get(i));
                i++;
            }
            dVar.e();
        } else {
            dVar.d();
            for (Entry entry2 : map.entrySet()) {
                dVar.a(String.valueOf(entry2.getKey()));
                this.c.a(dVar, entry2.getValue());
            }
            dVar.e();
        }
    }
}
