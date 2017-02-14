package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.a.a;
import com.google.gson.jpush.al;
import com.google.gson.jpush.am;
import com.google.gson.jpush.k;
import java.sql.Timestamp;
import java.util.Date;

final class ao implements am {
    ao() {
    }

    public final <T> al<T> a(k kVar, a<T> aVar) {
        return aVar.a() != Timestamp.class ? null : new ap(this, kVar.a(Date.class));
    }
}
