package com.google.gson.jpush.internal;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.annotations.Until;
import com.google.gson.jpush.annotations.d;
import com.google.gson.jpush.b;
import com.google.gson.jpush.c;
import com.google.gson.jpush.k;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public final class s implements am, Cloneable {
    public static final s a = new s();
    private double b = -1.0d;
    private int c = 136;
    private boolean d = true;
    private boolean e;
    private List<b> f = Collections.emptyList();
    private List<b> g = Collections.emptyList();

    private boolean a(d dVar, Until until) {
        boolean z = dVar == null || dVar.a() <= this.b;
        if (z) {
            z = until == null || until.value() > this.b;
            if (z) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    private s b() {
        try {
            return (s) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private boolean b(Class<?> cls) {
        if (cls.isMemberClass()) {
            if (!((cls.getModifiers() & 8) != 0)) {
                return true;
            }
        }
        return false;
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        Class a = aVar.a();
        boolean a2 = a(a, true);
        boolean a3 = a(a, false);
        return (a2 || a3) ? new t(this, a3, a2, kVar, aVar) : null;
    }

    public final s a() {
        s b = b();
        b.e = true;
        return b;
    }

    public final boolean a(Class<?> cls, boolean z) {
        if (this.b != -1.0d && !a((d) cls.getAnnotation(d.class), (Until) cls.getAnnotation(Until.class))) {
            return true;
        }
        if (!this.d && b(cls)) {
            return true;
        }
        if (a(cls)) {
            return true;
        }
        for (b b : z ? this.f : this.g) {
            if (b.b()) {
                return true;
            }
        }
        return false;
    }

    public final boolean a(Field field, boolean z) {
        if ((this.c & field.getModifiers()) != 0) {
            return true;
        }
        if (this.b != -1.0d && !a((d) field.getAnnotation(d.class), (Until) field.getAnnotation(Until.class))) {
            return true;
        }
        if (field.isSynthetic()) {
            return true;
        }
        if (this.e) {
            com.google.gson.jpush.annotations.a aVar = (com.google.gson.jpush.annotations.a) field.getAnnotation(com.google.gson.jpush.annotations.a.class);
            if (aVar == null || (z ? !aVar.a() : !aVar.b())) {
                return true;
            }
        }
        if (!this.d && b(field.getType())) {
            return true;
        }
        if (a(field.getType())) {
            return true;
        }
        List<b> list = z ? this.f : this.g;
        if (!list.isEmpty()) {
            c cVar = new c(field);
            for (b a : list) {
                if (a.a()) {
                    return true;
                }
            }
        }
        return false;
    }

    protected final /* synthetic */ Object clone() {
        return b();
    }
}
