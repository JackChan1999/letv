package com.google.gson.jpush;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.internal.s;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class r {
    private s a = s.a;
    private ag b = ag.a;
    private j c = d.a;
    private final Map<Type, s<?>> d = new HashMap();
    private final List<am> e = new ArrayList();
    private final List<am> f = new ArrayList();
    private boolean g;
    private String h;
    private int i = 2;
    private int j = 2;
    private boolean k;
    private boolean l;
    private boolean m = true;
    private boolean n;
    private boolean o;

    public final r a() {
        this.a = this.a.a();
        return this;
    }

    public final k b() {
        Object aVar;
        List arrayList = new ArrayList();
        arrayList.addAll(this.e);
        Collections.reverse(arrayList);
        arrayList.addAll(this.f);
        String str = this.h;
        int i = this.i;
        int i2 = this.j;
        if (str == null || "".equals(str.trim())) {
            if (!(i == 2 || i2 == 2)) {
                aVar = new a(i, i2);
            }
            return new k(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.l, this.b, arrayList);
        }
        aVar = new a(str);
        arrayList.add(aj.a(a.a(Date.class), aVar));
        arrayList.add(aj.a(a.a(Timestamp.class), aVar));
        arrayList.add(aj.a(a.a(java.sql.Date.class), aVar));
        return new k(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.l, this.b, arrayList);
    }
}
