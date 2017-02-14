package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;

final class ai extends al<StringBuffer> {
    ai() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() != c.i) {
            return new StringBuffer(aVar.h());
        }
        aVar.j();
        return null;
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        StringBuffer stringBuffer = (StringBuffer) obj;
        dVar.b(stringBuffer == null ? null : stringBuffer.toString());
    }
}
