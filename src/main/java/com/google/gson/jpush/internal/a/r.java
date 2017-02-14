package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.b.d;
import com.google.gson.jpush.k;
import java.lang.reflect.Field;

final class r extends t {
    final al<?> a = q.a(this.f, this.b, this.c, this.d);
    final /* synthetic */ k b;
    final /* synthetic */ Field c;
    final /* synthetic */ a d;
    final /* synthetic */ boolean e;
    final /* synthetic */ q f;

    r(q qVar, String str, boolean z, boolean z2, k kVar, Field field, a aVar, boolean z3) {
        this.f = qVar;
        this.b = kVar;
        this.c = field;
        this.d = aVar;
        this.e = z3;
        super(str, z, z2);
    }

    final void a(com.google.gson.jpush.b.a aVar, Object obj) {
        Object a = this.a.a(aVar);
        if (a != null || !this.e) {
            this.c.set(obj, a);
        }
    }

    final void a(d dVar, Object obj) {
        new y(this.b, this.a, this.d.b()).a(dVar, this.c.get(obj));
    }

    public final boolean a(Object obj) {
        return this.h && this.c.get(obj) != obj;
    }
}
