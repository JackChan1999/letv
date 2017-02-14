package com.coolcloud.uac.android.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
    private static final String PREFERENCE_NAME = "uacConfig";
    private static final PreferenceUtil sInstance = new PreferenceUtil();
    private SharedPreferences mPref;

    public static PreferenceUtil getInstance() {
        return sInstance;
    }

    public synchronized void init(Context mContext) {
        if (this.mPref == null) {
            this.mPref = mContext.getApplicationContext().getSharedPreferences(PREFERENCE_NAME, 0);
        }
    }

    public SharedPreferences pref() {
        return this.mPref;
    }

    public int getInt(String key, int defValue) {
        return (this.mPref != null || this.mPref == null) ? this.mPref.getInt(key, defValue) : defValue;
    }

    public PreferenceUtil putInt(String key, int value) {
        if (this.mPref != null) {
            this.mPref.edit().putInt(key, value).commit();
        }
        return this;
    }

    public String getString(String key, String defValue) {
        return this.mPref == null ? defValue : this.mPref.getString(key, defValue);
    }

    public PreferenceUtil putBoolean(String key, boolean value) {
        if (this.mPref != null) {
            this.mPref.edit().putBoolean(key, value).commit();
        }
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.mPref == null ? defValue : this.mPref.getBoolean(key, defValue);
    }

    public PreferenceUtil putString(String key, String value) {
        if (!(key == null || value == null || this.mPref == null)) {
            this.mPref.edit().putString(key, value).commit();
        }
        return this;
    }
}
