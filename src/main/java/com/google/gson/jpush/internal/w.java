package com.google.gson.jpush.internal;

import com.google.gson.jpush.internal.w$com.google.gson.jpush.internal.aa;
import com.google.gson.jpush.internal.w$com.google.gson.jpush.internal.y;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Set;

public final class w<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean f;
    private static final Comparator<Comparable> g = new x();
    private static final String[] z;
    Comparator<? super K> a;
    ad<K, V> b;
    int c;
    int d;
    final ad<K, V> e;
    private y h;
    private aa i;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = "t_\u0004E^;BW&_9F\u0016\u0017Q6Z\u0012";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000c:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x006b;
    L_0x0013:
        r8 = r1;
    L_0x0014:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0019:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x005d;
            case 1: goto L_0x0060;
            case 2: goto L_0x0063;
            case 3: goto L_0x0066;
            default: goto L_0x0020;
        };
    L_0x0020:
        r11 = 48;
    L_0x0022:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002e;
    L_0x002a:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0019;
    L_0x002e:
        r7 = r2;
        r2 = r9;
    L_0x0030:
        if (r7 > r8) goto L_0x0014;
    L_0x0032:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            default: goto L_0x003e;
        };
    L_0x003e:
        r5[r3] = r2;
        r0 = "?S\u000eE\ri\u0016\u0019\u0010\\8";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r5[r3] = r2;
        z = r6;
        r0 = com.google.gson.jpush.internal.w.class;
        r0 = r0.desiredAssertionStatus();
        if (r0 != 0) goto L_0x0069;
    L_0x0053:
        f = r4;
        r0 = new com.google.gson.jpush.internal.x;
        r0.<init>();
        g = r0;
        return;
    L_0x005d:
        r11 = 84;
        goto L_0x0022;
    L_0x0060:
        r11 = 54;
        goto L_0x0022;
    L_0x0063:
        r11 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x0022;
    L_0x0066:
        r11 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x0022;
    L_0x0069:
        r4 = r1;
        goto L_0x0053;
    L_0x006b:
        r8 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.w.<clinit>():void");
    }

    public w() {
        this(g);
    }

    private w(Comparator<? super K> comparator) {
        Comparator comparator2;
        this.c = 0;
        this.d = 0;
        this.e = new ad();
        if (comparator == null) {
            comparator2 = g;
        }
        this.a = comparator2;
    }

    private ad<K, V> a(K k, boolean z) {
        int i;
        Comparator comparator = this.a;
        ad<K, V> adVar = this.b;
        if (adVar != null) {
            int compareTo;
            Comparable comparable = comparator == g ? (Comparable) k : null;
            while (true) {
                compareTo = comparable != null ? comparable.compareTo(adVar.f) : comparator.compare(k, adVar.f);
                if (compareTo != 0) {
                    ad<K, V> adVar2 = compareTo < 0 ? adVar.b : adVar.c;
                    if (adVar2 == null) {
                        break;
                    }
                    adVar = adVar2;
                } else {
                    return adVar;
                }
            }
            int i2 = compareTo;
            ad adVar3 = adVar;
            i = i2;
        } else {
            ad<K, V> adVar4 = adVar;
            i = 0;
        }
        if (!z) {
            return null;
        }
        ad<K, V> adVar5;
        ad adVar6 = this.e;
        if (adVar3 != null) {
            adVar5 = new ad(adVar3, k, adVar6, adVar6.e);
            if (i < 0) {
                adVar3.b = adVar5;
            } else {
                adVar3.c = adVar5;
            }
            b(adVar3, true);
        } else if (comparator != g || (k instanceof Comparable)) {
            adVar5 = new ad(adVar3, k, adVar6, adVar6.e);
            this.b = adVar5;
        } else {
            throw new ClassCastException(k.getClass().getName() + z[0]);
        }
        this.c++;
        this.d++;
        return adVar5;
    }

    private void a(ad<K, V> adVar) {
        int i = 0;
        ad adVar2 = adVar.b;
        ad adVar3 = adVar.c;
        ad adVar4 = adVar3.b;
        ad adVar5 = adVar3.c;
        adVar.c = adVar4;
        if (adVar4 != null) {
            adVar4.a = adVar;
        }
        a((ad) adVar, adVar3);
        adVar3.b = adVar;
        adVar.a = adVar3;
        adVar.h = Math.max(adVar2 != null ? adVar2.h : 0, adVar4 != null ? adVar4.h : 0) + 1;
        int i2 = adVar.h;
        if (adVar5 != null) {
            i = adVar5.h;
        }
        adVar3.h = Math.max(i2, i) + 1;
    }

    private void a(ad<K, V> adVar, ad<K, V> adVar2) {
        ad adVar3 = adVar.a;
        adVar.a = null;
        if (adVar2 != null) {
            adVar2.a = adVar3;
        }
        if (adVar3 == null) {
            this.b = adVar2;
        } else if (adVar3.b == adVar) {
            adVar3.b = adVar2;
        } else if (f || adVar3.c == adVar) {
            adVar3.c = adVar2;
        } else {
            throw new AssertionError();
        }
    }

    private ad<K, V> b(Object obj) {
        ad<K, V> adVar = null;
        if (obj != null) {
            try {
                adVar = a(obj, false);
            } catch (ClassCastException e) {
            }
        }
        return adVar;
    }

    private void b(ad<K, V> adVar) {
        int i = 0;
        ad adVar2 = adVar.b;
        ad adVar3 = adVar.c;
        ad adVar4 = adVar2.b;
        ad adVar5 = adVar2.c;
        adVar.b = adVar5;
        if (adVar5 != null) {
            adVar5.a = adVar;
        }
        a((ad) adVar, adVar2);
        adVar2.c = adVar;
        adVar.a = adVar2;
        adVar.h = Math.max(adVar3 != null ? adVar3.h : 0, adVar5 != null ? adVar5.h : 0) + 1;
        int i2 = adVar.h;
        if (adVar4 != null) {
            i = adVar4.h;
        }
        adVar2.h = Math.max(i2, i) + 1;
    }

    private void b(ad<K, V> adVar, boolean z) {
        ad adVar2;
        while (adVar2 != null) {
            ad adVar3 = adVar2.b;
            ad adVar4 = adVar2.c;
            int i = adVar3 != null ? adVar3.h : 0;
            int i2 = adVar4 != null ? adVar4.h : 0;
            int i3 = i - i2;
            ad adVar5;
            if (i3 == -2) {
                adVar3 = adVar4.b;
                adVar5 = adVar4.c;
                i2 = (adVar3 != null ? adVar3.h : 0) - (adVar5 != null ? adVar5.h : 0);
                if (i2 == -1 || (i2 == 0 && !z)) {
                    a(adVar2);
                } else if (f || i2 == 1) {
                    b(adVar4);
                    a(adVar2);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                adVar4 = adVar3.b;
                adVar5 = adVar3.c;
                i2 = (adVar4 != null ? adVar4.h : 0) - (adVar5 != null ? adVar5.h : 0);
                if (i2 == 1 || (i2 == 0 && !z)) {
                    b(adVar2);
                } else if (f || i2 == -1) {
                    a(adVar3);
                    b(adVar2);
                } else {
                    throw new AssertionError();
                }
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                adVar2.h = i + 1;
                if (z) {
                    return;
                }
            } else if (f || i3 == -1 || i3 == 1) {
                adVar2.h = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
            adVar2 = adVar2.a;
        }
    }

    final ad<K, V> a(Object obj) {
        ad b = b(obj);
        if (b != null) {
            a(b, true);
        }
        return b;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final com.google.gson.jpush.internal.ad<K, V> a(java.util.Map.Entry<?, ?> r6) {
        /*
        r5 = this;
        r1 = 1;
        r2 = 0;
        r0 = r6.getKey();
        r0 = r5.b(r0);
        if (r0 == 0) goto L_0x0024;
    L_0x000c:
        r3 = r0.g;
        r4 = r6.getValue();
        if (r3 == r4) goto L_0x001c;
    L_0x0014:
        if (r3 == 0) goto L_0x0022;
    L_0x0016:
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x0022;
    L_0x001c:
        r3 = r1;
    L_0x001d:
        if (r3 == 0) goto L_0x0024;
    L_0x001f:
        if (r1 == 0) goto L_0x0026;
    L_0x0021:
        return r0;
    L_0x0022:
        r3 = r2;
        goto L_0x001d;
    L_0x0024:
        r1 = r2;
        goto L_0x001f;
    L_0x0026:
        r0 = 0;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.jpush.internal.w.a(java.util.Map$Entry):com.google.gson.jpush.internal.ad<K, V>");
    }

    final void a(ad<K, V> adVar, boolean z) {
        int i = 0;
        if (z) {
            adVar.e.d = adVar.d;
            adVar.d.e = adVar.e;
        }
        ad adVar2 = adVar.b;
        ad adVar3 = adVar.c;
        ad adVar4 = adVar.a;
        if (adVar2 == null || adVar3 == null) {
            if (adVar2 != null) {
                a((ad) adVar, adVar2);
                adVar.b = null;
            } else if (adVar3 != null) {
                a((ad) adVar, adVar3);
                adVar.c = null;
            } else {
                a((ad) adVar, null);
            }
            b(adVar4, false);
            this.c--;
            this.d++;
            return;
        }
        int i2;
        if (adVar2.h > adVar3.h) {
            adVar4 = adVar2;
            for (adVar3 = adVar2.c; adVar3 != null; adVar3 = adVar3.c) {
                adVar4 = adVar3;
            }
        } else {
            adVar4 = adVar3;
            for (adVar3 = adVar3.b; adVar3 != null; adVar3 = adVar3.b) {
                adVar4 = adVar3;
            }
        }
        a(adVar4, false);
        adVar2 = adVar.b;
        if (adVar2 != null) {
            i2 = adVar2.h;
            adVar4.b = adVar2;
            adVar2.a = adVar4;
            adVar.b = null;
        } else {
            i2 = 0;
        }
        adVar2 = adVar.c;
        if (adVar2 != null) {
            i = adVar2.h;
            adVar4.c = adVar2;
            adVar2.a = adVar4;
            adVar.c = null;
        }
        adVar4.h = Math.max(i2, i) + 1;
        a((ad) adVar, adVar4);
    }

    public final void clear() {
        this.b = null;
        this.c = 0;
        this.d++;
        ad adVar = this.e;
        adVar.e = adVar;
        adVar.d = adVar;
    }

    public final boolean containsKey(Object obj) {
        return b(obj) != null;
    }

    public final Set<Entry<K, V>> entrySet() {
        Set set = this.h;
        if (set != null) {
            return set;
        }
        set = new y(this);
        this.h = set;
        return set;
    }

    public final V get(Object obj) {
        ad b = b(obj);
        return b != null ? b.g : null;
    }

    public final Set<K> keySet() {
        Set set = this.i;
        if (set != null) {
            return set;
        }
        set = new aa(this);
        this.i = set;
        return set;
    }

    public final V put(K k, V v) {
        if (k == null) {
            throw new NullPointerException(z[1]);
        }
        ad a = a((Object) k, true);
        V v2 = a.g;
        a.g = v;
        return v2;
    }

    public final V remove(Object obj) {
        ad a = a(obj);
        return a != null ? a.g : null;
    }

    public final int size() {
        return this.c;
    }
}
