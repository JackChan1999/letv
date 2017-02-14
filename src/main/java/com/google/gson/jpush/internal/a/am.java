package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import java.net.InetAddress;

final class am extends al<InetAddress> {
    am() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return InetAddress.getByName(aVar.h());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        InetAddress inetAddress = (InetAddress) obj;
        dVar.b(inetAddress == null ? null : inetAddress.getHostAddress());
    }
}
