package com.google.gson.jpush;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.internal.a.c;
import com.google.gson.jpush.internal.a.e;
import com.google.gson.jpush.internal.a.g;
import com.google.gson.jpush.internal.a.l;
import com.google.gson.jpush.internal.a.n;
import com.google.gson.jpush.internal.a.q;
import com.google.gson.jpush.internal.a.u;
import com.google.gson.jpush.internal.a.w;
import com.google.gson.jpush.internal.a.z;
import com.google.gson.jpush.internal.af;
import com.google.gson.jpush.internal.ag;
import com.google.gson.jpush.internal.f;
import com.google.gson.jpush.internal.s;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class k {
    private static final String[] z;
    final u a;
    final ad b;
    private final ThreadLocal<Map<a<?>, q<?>>> c;
    private final Map<a<?>, al<?>> d;
    private final List<am> e;
    private final f f;
    private final boolean g;
    private final boolean h;
    private final boolean i;
    private final boolean j;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 9;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "K0`H\u0007e\fLsJd\r[&P`\u0010\u000fhHuCIsKm\u001a\u000feHo\u0010ZkBeM";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0083;
            case 1: goto L_0x0085;
            case 2: goto L_0x0088;
            case 3: goto L_0x008b;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 39;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0075;
            case 7: goto L_0x007e;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "!C";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "(>R!-";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "!\n\\&In\u0017\u000fg\u0007w\u0002CoC!\u0007@sEm\u0006\u000fpFm\u0016J&FrC_cU!)|Ii!\u0010_cDh\u0005FeFu\n@h\t!7@&Hw\u0006]tNe\u0006\u000frOh\u0010\u000fdBi\u0002YoHsO\u000fsTdChuHo!ZoKe\u0006](Td\u0011FgKh\u0019JUWd\u0000FgKG\u000f@gSh\rHVHh\r[PFm\u0016Ju\u000f(CBcSi\fK(";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "F0`H\u0007b\u0002AhHuC\\cUh\u0002Co]dC";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "F0`H\u0007b\u0002AhHuCGgIe\u000fJ&";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "-\nAuS`\rLcds\u0006NrHs\u0010\u0015";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "z\u0010JtN`\u000fF|BO\u0016CjT;";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0075:
        r3[r2] = r1;
        r2 = 8;
        r1 = "g\u0002LrHs\nJu\u001d";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007e:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0083:
        r9 = 1;
        goto L_0x0020;
    L_0x0085:
        r9 = 99;
        goto L_0x0020;
    L_0x0088:
        r9 = 47;
        goto L_0x0020;
    L_0x008b:
        r9 = 6;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.k.<clinit>():void");
    }

    public k() {
        this(s.a, d.a, Collections.emptyMap(), false, false, false, true, false, false, ag.a, Collections.emptyList());
    }

    k(s sVar, j jVar, Map<Type, s<?>> map, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, ag agVar, List<am> list) {
        this.c = new ThreadLocal();
        this.d = Collections.synchronizedMap(new HashMap());
        this.a = new l(this);
        this.b = new m(this);
        this.f = new f(map);
        this.g = z;
        this.i = z3;
        this.h = z4;
        this.j = z5;
        List arrayList = new ArrayList();
        arrayList.add(z.Q);
        arrayList.add(n.a);
        arrayList.add(sVar);
        arrayList.addAll(list);
        arrayList.add(z.x);
        arrayList.add(z.m);
        arrayList.add(z.g);
        arrayList.add(z.i);
        arrayList.add(z.k);
        arrayList.add(z.a(Long.TYPE, Long.class, agVar == ag.a ? z.n : new p(this)));
        arrayList.add(z.a(Double.TYPE, Double.class, z6 ? z.p : new n(this)));
        arrayList.add(z.a(Float.TYPE, Float.class, z6 ? z.o : new o(this)));
        arrayList.add(z.r);
        arrayList.add(z.t);
        arrayList.add(z.z);
        arrayList.add(z.B);
        arrayList.add(z.a(BigDecimal.class, z.v));
        arrayList.add(z.a(BigInteger.class, z.w));
        arrayList.add(z.D);
        arrayList.add(z.F);
        arrayList.add(z.J);
        arrayList.add(z.O);
        arrayList.add(z.H);
        arrayList.add(z.d);
        arrayList.add(e.a);
        arrayList.add(z.M);
        arrayList.add(w.a);
        arrayList.add(u.a);
        arrayList.add(z.K);
        arrayList.add(com.google.gson.jpush.internal.a.a.a);
        arrayList.add(z.b);
        arrayList.add(new c(this.f));
        arrayList.add(new l(this.f, z2));
        arrayList.add(new g(this.f));
        arrayList.add(z.R);
        arrayList.add(new q(this.f, jVar, sVar));
        this.e = Collections.unmodifiableList(arrayList);
    }

    private d a(Writer writer) {
        if (this.i) {
            writer.write(z[2]);
        }
        d dVar = new d(writer);
        if (this.j) {
            dVar.c(z[1]);
        }
        dVar.d(this.g);
        return dVar;
    }

    private <T> T a(com.google.gson.jpush.b.a aVar, Type type) {
        boolean z = true;
        boolean p = aVar.p();
        aVar.a(true);
        try {
            aVar.f();
            z = false;
            T a = a(a.a(type)).a(aVar);
            aVar.a(p);
            return a;
        } catch (Throwable e) {
            if (z) {
                aVar.a(p);
                return null;
            }
            throw new af(e);
        } catch (Throwable e2) {
            throw new af(e2);
        } catch (Throwable e22) {
            throw new af(e22);
        } catch (Throwable th) {
            aVar.a(p);
        }
    }

    static /* synthetic */ void a(k kVar, double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + z[3]);
        }
    }

    private static void a(Object obj, com.google.gson.jpush.b.a aVar) {
        if (obj != null) {
            try {
                if (aVar.f() != com.google.gson.jpush.b.c.j) {
                    throw new x(z[0]);
                }
            } catch (Throwable e) {
                throw new af(e);
            } catch (Throwable e2) {
                throw new x(e2);
            }
        }
    }

    public final <T> al<T> a(a<T> aVar) {
        al<T> alVar = (al) this.d.get(aVar);
        if (alVar == null) {
            Map map;
            Map map2 = (Map) this.c.get();
            Object obj = null;
            if (map2 == null) {
                HashMap hashMap = new HashMap();
                this.c.set(hashMap);
                map = hashMap;
                obj = 1;
            } else {
                map = map2;
            }
            q qVar = (q) map.get(aVar);
            if (qVar == null) {
                try {
                    q qVar2 = new q();
                    map.put(aVar, qVar2);
                    for (am a : this.e) {
                        al a2 = a.a(this, aVar);
                        if (a2 != null) {
                            qVar2.a(a2);
                            this.d.put(aVar, a2);
                            map.remove(aVar);
                            if (obj != null) {
                                this.c.remove();
                            }
                        }
                    }
                    throw new IllegalArgumentException(new StringBuilder(z[5]).append(aVar).toString());
                } catch (Throwable th) {
                    map.remove(aVar);
                    if (obj != null) {
                        this.c.remove();
                    }
                }
            }
        }
        return alVar;
    }

    public final <T> al<T> a(am amVar, a<T> aVar) {
        Object obj = null;
        if (!this.e.contains(amVar)) {
            obj = 1;
        }
        Object obj2 = obj;
        for (am amVar2 : this.e) {
            if (obj2 != null) {
                al<T> a = amVar2.a(this, aVar);
                if (a != null) {
                    return a;
                }
            } else if (amVar2 == amVar) {
                obj2 = 1;
            }
        }
        throw new IllegalArgumentException(new StringBuilder(z[4]).append(aVar).toString());
    }

    public final <T> al<T> a(Class<T> cls) {
        return a(a.a((Class) cls));
    }

    public final <T> T a(String str, Class<T> cls) {
        Object obj;
        if (str == null) {
            obj = null;
        } else {
            com.google.gson.jpush.b.a aVar = new com.google.gson.jpush.b.a(new StringReader(str));
            obj = a(aVar, (Type) cls);
            a(obj, aVar);
        }
        return af.a((Class) cls).cast(obj);
    }

    public final void a(w wVar, Appendable appendable) {
        try {
            d a = a(ag.a(appendable));
            boolean g = a.g();
            a.b(true);
            boolean h = a.h();
            a.c(this.h);
            boolean i = a.i();
            a.d(this.g);
            try {
                ag.a(wVar, a);
                a.b(g);
                a.c(h);
                a.d(i);
            } catch (Throwable e) {
                throw new x(e);
            } catch (Throwable th) {
                a.b(g);
                a.c(h);
                a.d(i);
            }
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    public final void a(Object obj, Type type, Appendable appendable) {
        try {
            d a = a(ag.a(appendable));
            al a2 = a(a.a(type));
            boolean g = a.g();
            a.b(true);
            boolean h = a.h();
            a.c(this.h);
            boolean i = a.i();
            a.d(this.g);
            try {
                a2.a(a, obj);
                a.b(g);
                a.c(h);
                a.d(i);
            } catch (Throwable e) {
                throw new x(e);
            } catch (Throwable th) {
                a.b(g);
                a.c(h);
                a.d(i);
            }
        } catch (Throwable e2) {
            throw new x(e2);
        }
    }

    public final String toString() {
        return new StringBuilder(z[7]).append(this.g).append(z[8]).append(this.e).append(z[6]).append(this.f).append("}").toString();
    }
}
