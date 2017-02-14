package com.google.gson.jpush;

import java.lang.reflect.Field;

enum e extends d {
    e(String str, int i) {
        super(str, 0);
    }

    public final String a(Field field) {
        return field.getName();
    }
}
