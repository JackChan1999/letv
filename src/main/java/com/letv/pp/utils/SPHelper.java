package com.letv.pp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Map;
import java.util.Set;

public class SPHelper {
    public static final String KEY_APP_VERSION_CODE = "app_version_code";
    public static final String KEY_APP_VERSION_NAME = "app_version_name";
    public static final String KEY_LIB_LOCAL_VERSION = "lib_local_version";
    public static final String KEY_LOG_LEVEL = "log_level";
    private static final String SP_NAME = "cde_config";
    private static SPHelper sSingleton;
    private final Editor mEditor = this.mSharedPreferences.edit();
    private final SharedPreferences mSharedPreferences;

    private SPHelper(Context context) {
        this.mSharedPreferences = context.getApplicationContext().getSharedPreferences(SP_NAME, 0);
    }

    public static SPHelper getInstance(Context context) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        if (sSingleton == null) {
            synchronized (SPHelper.class) {
                if (sSingleton == null) {
                    sSingleton = new SPHelper(context);
                }
            }
        }
        return sSingleton;
    }

    public boolean contains(String key) {
        return this.mSharedPreferences.contains(key);
    }

    public boolean commit() {
        return this.mEditor.commit();
    }

    public void apply() {
        this.mEditor.apply();
    }

    public SPHelper remove(String key) {
        this.mEditor.remove(key);
        return this;
    }

    public boolean removeAndCommit(String key) {
        this.mEditor.remove(key);
        return this.mEditor.commit();
    }

    public SPHelper clear() {
        this.mEditor.clear();
        return this;
    }

    public boolean clearAndCommit() {
        this.mEditor.clear();
        return this.mEditor.commit();
    }

    public SPHelper putString(String key, String value) {
        this.mEditor.putString(key, value);
        return this;
    }

    public boolean putStringAndCommit(String key, String value) {
        this.mEditor.putString(key, value);
        return this.mEditor.commit();
    }

    public SPHelper putBoolean(String key, boolean value) {
        this.mEditor.putBoolean(key, value);
        return this;
    }

    public boolean putBooleanAndCommit(String key, boolean value) {
        this.mEditor.putBoolean(key, value);
        return this.mEditor.commit();
    }

    public SPHelper putInt(String key, int value) {
        this.mEditor.putInt(key, value);
        return this;
    }

    public boolean putIntAndCommit(String key, int value) {
        this.mEditor.putInt(key, value);
        return this.mEditor.commit();
    }

    public SPHelper putLong(String key, long value) {
        this.mEditor.putLong(key, value);
        return this;
    }

    public boolean putLongAndCommit(String key, long value) {
        this.mEditor.putLong(key, value);
        return this.mEditor.commit();
    }

    public SPHelper putMap(Map<String, String> map) {
        for (String keyName : map.keySet()) {
            this.mEditor.putString(keyName, (String) map.get(keyName));
        }
        return this;
    }

    public boolean putMapAndCommit(Map<String, String> map) {
        for (String keyName : map.keySet()) {
            this.mEditor.putString(keyName, (String) map.get(keyName));
        }
        return this.mEditor.commit();
    }

    public SPHelper putSet(String key, Set<String> set) {
        this.mEditor.remove(key);
        this.mEditor.commit();
        this.mEditor.putStringSet(key, set);
        return this;
    }

    public boolean putSetAndCommit(String key, Set<String> set) {
        this.mEditor.remove(key);
        this.mEditor.commit();
        this.mEditor.putStringSet(key, set);
        return this.mEditor.commit();
    }

    public String getString(String key) {
        return this.mSharedPreferences.getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return this.mSharedPreferences.getString(key, defaultValue);
    }

    public boolean getBoolean(String key) {
        return this.mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.mSharedPreferences.getBoolean(key, defaultValue);
    }

    public int getInt(String key) {
        return this.mSharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return this.mSharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key) {
        return this.mSharedPreferences.getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return this.mSharedPreferences.getLong(key, defaultValue);
    }

    public Set<String> getSet(String key) {
        return this.mSharedPreferences.getStringSet(key, null);
    }
}
