package com.letv.mobile.lebox.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.letv.mobile.lebox.LeBoxApp;

public class SharedPreferencesUtil {
    public static final String LEBOX_NAME_KEY = "lebox_name_key";
    public static final String LEBOX_P2P_GATEWAY_KEY = "lebox_p2p_gateway_key";
    private static final String LEBOX_TABLE = "lebox_table";
    private static SharedPreferences mSharePreferences;

    public static Context getContext() {
        return LeBoxApp.getApplication();
    }

    public static synchronized void writeData(String key, String valus) {
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            if (needWriteData(key, valus)) {
                Editor editor = mSharePreferences.edit();
                editor.putString(key, valus);
                editor.commit();
            }
        }
    }

    public static synchronized void writeData(String key, boolean valus) {
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            if (needWriteData(key, valus)) {
                Editor editor = mSharePreferences.edit();
                editor.putBoolean(key, valus);
                editor.commit();
            }
        }
    }

    public static synchronized void writeData(String key, int valus) {
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            if (needWriteData(key, valus)) {
                Editor editor = mSharePreferences.edit();
                editor.putInt(key, valus);
                editor.commit();
            }
        }
    }

    public static synchronized void removeData(String key) {
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            Editor editor = mSharePreferences.edit();
            editor.remove(key);
            editor.commit();
        }
    }

    public static synchronized String readData(String key, String defValue) {
        String string;
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            string = mSharePreferences.getString(key, defValue);
        }
        return string;
    }

    public static synchronized boolean readData(String key, boolean defValue) {
        boolean z;
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            z = mSharePreferences.getBoolean(key, defValue);
        }
        return z;
    }

    public static synchronized int readData(String key, int defValue) {
        int i;
        synchronized (SharedPreferencesUtil.class) {
            if (mSharePreferences == null) {
                mSharePreferences = getContext().getSharedPreferences(LEBOX_TABLE, 0);
            }
            i = mSharePreferences.getInt(key, defValue);
        }
        return i;
    }

    private static boolean needWriteData(String key, String valus) {
        if (TextUtils.isEmpty(valus) || valus.equals(mSharePreferences.getString(key, null))) {
            return false;
        }
        return true;
    }

    private static boolean needWriteData(String key, boolean valus) {
        if (mSharePreferences.getBoolean(key, false) == valus) {
            return false;
        }
        return true;
    }

    private static boolean needWriteData(String key, int valus) {
        if (mSharePreferences.getInt(key, 0) == valus) {
            return false;
        }
        return true;
    }
}
