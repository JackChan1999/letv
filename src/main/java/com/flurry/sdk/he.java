package com.flurry.sdk;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Locale;
import java.util.TimeZone;

public class he {
    private static he a;

    public static synchronized he a() {
        he heVar;
        synchronized (he.class) {
            if (a == null) {
                a = new he();
            }
            heVar = a;
        }
        return heVar;
    }

    public static void b() {
        a = null;
    }

    private he() {
    }

    public String c() {
        return Locale.getDefault().getLanguage() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + Locale.getDefault().getCountry();
    }

    public String d() {
        return TimeZone.getDefault().getID();
    }
}
