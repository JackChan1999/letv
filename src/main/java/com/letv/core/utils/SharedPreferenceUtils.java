package com.letv.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SharedPreferenceUtils {
    public static final String FILE_NAME = "share_data";

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        private SharedPreferencesCompat() {
        }

        private static Method findApplyMethod() {
            try {
                return Editor.class.getMethod("apply", new Class[0]);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }

        public static void apply(Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor, new Object[0]);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e2) {
            } catch (InvocationTargetException e3) {
            }
            editor.commit();
        }
    }

    public static void put(Context context, String fileName, String key, Object object) {
        put(context, false, fileName, key, object);
    }

    public static void put(Context context, String key, Object object) {
        put(context, false, FILE_NAME, key, object);
    }

    public static void putSyn(Context context, String key, Object object) {
        put(context, true, FILE_NAME, key, object);
    }

    public static void put(Context context, boolean isSyn, String fileName, String key, Object
            object) {
        if (object != null) {
            Editor editor = context.getSharedPreferences(fileName, 4).edit();
            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, ((Integer) object).intValue());
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, ((Boolean) object).booleanValue());
            } else if (object instanceof Float) {
                editor.putFloat(key, ((Float) object).floatValue());
            } else if (object instanceof Long) {
                editor.putLong(key, ((Long) object).longValue());
            } else {
                editor.putString(key, object.toString());
            }
            if (isSyn) {
                editor.commit();
            } else {
                SharedPreferencesCompat.apply(editor);
            }
        }
    }

    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, 4);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        }
        if (defaultObject instanceof Integer) {
            return Integer.valueOf(sp.getInt(key, ((Integer) defaultObject).intValue()));
        }
        if (defaultObject instanceof Boolean) {
            return Boolean.valueOf(sp.getBoolean(key, ((Boolean) defaultObject).booleanValue()));
        }
        if (defaultObject instanceof Float) {
            return Float.valueOf(sp.getFloat(key, ((Float) defaultObject).floatValue()));
        }
        if (defaultObject instanceof Long) {
            return Long.valueOf(sp.getLong(key, ((Long) defaultObject).longValue()));
        }
        return null;
    }

    public static Object get(Context context, String fileName, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(fileName, 4);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        }
        if (defaultObject instanceof Integer) {
            return Integer.valueOf(sp.getInt(key, ((Integer) defaultObject).intValue()));
        }
        if (defaultObject instanceof Boolean) {
            return Boolean.valueOf(sp.getBoolean(key, ((Boolean) defaultObject).booleanValue()));
        }
        if (defaultObject instanceof Float) {
            return Float.valueOf(sp.getFloat(key, ((Float) defaultObject).floatValue()));
        }
        if (defaultObject instanceof Long) {
            return Long.valueOf(sp.getLong(key, ((Long) defaultObject).longValue()));
        }
        return null;
    }

    public static void remove(Context context, String key) {
        Editor editor = context.getSharedPreferences(FILE_NAME, 0).edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void remove(Context context, String fileName, String key) {
        Editor editor = context.getSharedPreferences(fileName, 0).edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear(Context context) {
        Editor editor = context.getSharedPreferences(FILE_NAME, 0).edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static void clear(Context context, String fileName) {
        Editor editor = context.getSharedPreferences(fileName, 0).edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(Context context, String key) {
        return context.getSharedPreferences(FILE_NAME, 0).contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        return context.getSharedPreferences(FILE_NAME, 0).getAll();
    }
}
