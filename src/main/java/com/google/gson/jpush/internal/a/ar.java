package com.google.gson.jpush.internal.a;

import com.google.gson.jpush.al;
import com.google.gson.jpush.b.a;
import com.google.gson.jpush.b.c;
import com.google.gson.jpush.b.d;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Locale;
import java.util.StringTokenizer;

final class ar extends al<Locale> {
    ar() {
    }

    public final /* synthetic */ Object a(a aVar) {
        if (aVar.f() == c.i) {
            aVar.j();
            return null;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(aVar.h(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        String nextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
        return (nextToken2 == null && nextToken3 == null) ? new Locale(nextToken) : nextToken3 == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, nextToken3);
    }

    public final /* synthetic */ void a(d dVar, Object obj) {
        Locale locale = (Locale) obj;
        dVar.b(locale == null ? null : locale.toString());
    }
}
