package com.letv.plugin.pluginloader.apk.utils;

import android.text.TextUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FieldUtils {
    private static Map<String, Field> sFieldCache = new HashMap();

    private static String getKey(Class<?> cls, String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append(cls.toString()).append("#").append(fieldName);
        return sb.toString();
    }

    private static Field getField(Class<?> cls, String fieldName, boolean forceAccess) {
        Validate.isTrue(cls != null, "The class must not be null", new Object[0]);
        Validate.isTrue(!TextUtils.isEmpty(fieldName), "The field name must not be blank/empty", new Object[0]);
        String key = getKey(cls, fieldName);
        synchronized (sFieldCache) {
            Field cachedField = (Field) sFieldCache.get(key);
        }
        if (cachedField == null) {
            Class<?> acls = cls;
            while (acls != null) {
                try {
                    Field field = acls.getDeclaredField(fieldName);
                    if (!Modifier.isPublic(field.getModifiers())) {
                        if (forceAccess) {
                            field.setAccessible(true);
                        } else {
                            continue;
                            acls = acls.getSuperclass();
                        }
                    }
                    synchronized (sFieldCache) {
                        sFieldCache.put(key, field);
                    }
                    return field;
                } catch (NoSuchFieldException e) {
                }
            }
            Field match = null;
            for (Class<?> class1 : Utils.getAllInterfaces(cls)) {
                try {
                    Field test = class1.getField(fieldName);
                    Validate.isTrue(match == null, "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", new Object[]{fieldName, cls});
                    match = test;
                } catch (NoSuchFieldException e2) {
                }
            }
            synchronized (sFieldCache) {
                sFieldCache.put(key, match);
            }
            return match;
        } else if (!forceAccess || cachedField.isAccessible()) {
            return cachedField;
        } else {
            cachedField.setAccessible(true);
            return cachedField;
        }
    }

    public static Object readField(Field field, Object target, boolean forceAccess) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        if (!forceAccess || field.isAccessible()) {
            MemberUtils.setAccessibleWorkaround(field);
        } else {
            field.setAccessible(true);
        }
        return field.get(target);
    }

    public static void writeField(Field field, Object target, Object value, boolean forceAccess) throws IllegalAccessException {
        Validate.isTrue(field != null, "The field must not be null", new Object[0]);
        if (!forceAccess || field.isAccessible()) {
            MemberUtils.setAccessibleWorkaround(field);
        } else {
            field.setAccessible(true);
        }
        field.set(target, value);
    }

    public static Object readField(Field field, Object target) throws IllegalAccessException {
        return readField(field, target, true);
    }

    public static Field getField(Class<?> cls, String fieldName) {
        return getField(cls, fieldName, true);
    }

    public static Object readField(Object target, String fieldName) throws IllegalAccessException {
        boolean z;
        if (target != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "target object must not be null", new Object[0]);
        Field field = getField(target.getClass(), fieldName, true);
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "Cannot locate field %s on %s", new Object[]{fieldName, cls});
        return readField(field, target, false);
    }

    public static Object readField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
        boolean z;
        if (target != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "target object must not be null", new Object[0]);
        Field field = getField(target.getClass(), fieldName, forceAccess);
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "Cannot locate field %s on %s", new Object[]{fieldName, cls});
        return readField(field, target, forceAccess);
    }

    public static void writeField(Object target, String fieldName, Object value) throws IllegalAccessException {
        writeField(target, fieldName, value, true);
    }

    public static void writeField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
        boolean z;
        if (target != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "target object must not be null", new Object[0]);
        Field field = getField(target.getClass(), fieldName, true);
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "Cannot locate declared field %s.%s", new Object[]{cls.getName(), fieldName});
        writeField(field, target, value, forceAccess);
    }

    public static void writeField(Field field, Object target, Object value) throws IllegalAccessException {
        writeField(field, target, value, true);
    }

    public static Object readStaticField(Field field, boolean forceAccess) throws IllegalAccessException {
        boolean z;
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "The field must not be null", new Object[0]);
        Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field '%s' is not static", new Object[]{field.getName()});
        return readField(field, null, forceAccess);
    }

    public static Object readStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
        boolean z;
        Field field = getField(cls, fieldName, true);
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "Cannot locate field '%s' on %s", new Object[]{fieldName, cls});
        return readStaticField(field, true);
    }

    public static void writeStaticField(Field field, Object value, boolean forceAccess) throws IllegalAccessException {
        boolean z;
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "The field must not be null", new Object[0]);
        Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field %s.%s is not static", new Object[]{field.getDeclaringClass().getName(), field.getName()});
        writeField(field, null, value, forceAccess);
    }

    public static void writeStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
        boolean z;
        Field field = getField(cls, fieldName, true);
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "Cannot locate field %s on %s", new Object[]{fieldName, cls});
        writeStaticField(field, value, true);
    }

    public static Field getDeclaredField(Class<?> cls, String fieldName, boolean forceAccess) {
        boolean z = true;
        Validate.isTrue(cls != null, "The class must not be null", new Object[0]);
        if (TextUtils.isEmpty(fieldName)) {
            z = false;
        }
        Validate.isTrue(z, "The field name must not be blank/empty", new Object[0]);
        try {
            Field field = cls.getDeclaredField(fieldName);
            if (MemberUtils.isAccessible(field)) {
                return field;
            }
            if (!forceAccess) {
                return null;
            }
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
        boolean z;
        if (target != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "target object must not be null", new Object[0]);
        Field field = getDeclaredField(target.getClass(), fieldName, true);
        if (field != null) {
            z = true;
        } else {
            z = false;
        }
        Validate.isTrue(z, "Cannot locate declared field %s.%s", new Object[]{cls.getName(), fieldName});
        writeField(field, target, value, false);
    }
}
