package com.google.gson.jpush;

import com.letv.pp.utils.NetworkUtils;
import java.lang.reflect.Field;
import java.util.Locale;

enum i extends d {
    i(String str, int i) {
        super(str, 4);
    }

    public final String a(Field field) {
        return d.a(field.getName(), NetworkUtils.DELIMITER_LINE).toLowerCase(Locale.ENGLISH);
    }
}
