package com.tencent.open.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import java.io.File;

/* compiled from: ProGuard */
public final class Global {
    private static Context a;

    public static final Context getContext() {
        if (a == null) {
            return null;
        }
        return a;
    }

    public static final void setContext(Context context) {
        a = context;
    }

    public static final String getPackageName() {
        if (getContext() == null) {
            return "";
        }
        return getContext().getPackageName();
    }

    public static final SharedPreferences getSharedPreferences(String str, int i) {
        if (getContext() == null) {
            return null;
        }
        return getContext().getSharedPreferences(str, i);
    }

    public static final File getFilesDir() {
        if (getContext() == null) {
            return null;
        }
        return getContext().getFilesDir();
    }

    public static void saveVersionCode() {
        Context context = getContext();
        if (context != null) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                if (packageInfo != null) {
                    Editor edit = context.getSharedPreferences("openSdk.pref", 0).edit();
                    edit.putInt("app.vercode", packageInfo.versionCode);
                    edit.commit();
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getVersionCode() {
        return a.getSharedPreferences("openSdk.pref", 0).getInt("app.vercode", 0);
    }
}
