package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.d;
import java.sql.Timestamp;
import java.util.Date;

final class ap extends al<Timestamp> {
    final /* synthetic */ al a;
    final /* synthetic */ ao b;

    ap(ao aoVar, al alVar) {
        this.b = aoVar;
        this.a = alVar;
    }

    public final /* synthetic */ Object a(a aVar) {
        Date date = (Date) this.a.a(aVar);
        return date != null ? new Timestamp(date.getTime()) : null;
    }

    public final /* bridge */ /* synthetic */ void a(d dVar, Object obj) {
        this.a.a(dVar, (Timestamp) obj);
    }
}
