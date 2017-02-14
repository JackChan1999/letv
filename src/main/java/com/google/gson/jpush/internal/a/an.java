package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.util.UUID;

final class an extends al<UUID> {
    an() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return UUID.fromString(aVar.h());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        UUID uuid = (UUID) obj;
        dVar.b(uuid == null ? null : uuid.toString());
    }
}
