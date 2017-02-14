package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.k;
import java.sql.Date;

final class v implements am {
    v() {
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        return aVar.a() == Date.class ? new u() : null;
    }
}
