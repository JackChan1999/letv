package com.google.gson.jpush;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.lang.reflect.Field;
import java.util.Locale;

enum h extends d {
    h(String str, int i) {
        super(str, 3);
    }

    public final String a(Field field) {
        return d.a(field.getName(), EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR).toLowerCase(Locale.ENGLISH);
    }
}
