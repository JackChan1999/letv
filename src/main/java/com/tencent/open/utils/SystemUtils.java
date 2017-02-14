package com.tencent.open.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import com.facebook.internal.NativeProtocol;
import com.tencent.open.a.f;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.MessageDigest;

/* compiled from: ProGuard */
public class SystemUtils {
    public static final String ACTION_LOGIN = "action_login";
    public static final String ACTION_SHARE = "action_share";
    public static final String H5_SHARE_DATA = "h5_share_data";
    public static final String IS_LOGIN = "is_login";
    public static final String IS_QQ_MOBILE_SHARE = "is_qq_mobile_share";
    public static final String QQDATALINE_CALLBACK_ACTION = "sendToMyComputer";
    public static final String QQFAVORITES_CALLBACK_ACTION = "addToQQFavorites";
    public static final String QQ_SHARE_CALLBACK_ACTION = "shareToQQ";
    public static final String QQ_VERSION_NAME_4_2_0 = "4.2.0";
    public static final String QQ_VERSION_NAME_4_3_0 = "4.3.0";
    public static final String QQ_VERSION_NAME_4_5_0 = "4.5.0";
    public static final String QQ_VERSION_NAME_4_6_0 = "4.6.0";
    public static final String QQ_VERSION_NAME_5_0_0 = "5.0.0";
    public static final String QQ_VERSION_NAME_5_1_0 = "5.1.0";
    public static final String QQ_VERSION_NAME_5_2_0 = "5.2.0";
    public static final String QQ_VERSION_NAME_5_3_0 = "5.3.0";
    public static final String QZONE_SHARE_CALLBACK_ACTION = "shareToQzone";
    public static final String TROOPBAR_CALLBACK_ACTION = "shareToTroopBar";
    private static final String a = (f.d + ".SysUtils");

    public static String getAppVersionName(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static int compareVersion(String str, String str2) {
        if (str == null && str2 == null) {
            return 0;
        }
        if (str != null && str2 == null) {
            return 1;
        }
        if (str == null && str2 != null) {
            return -1;
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int i = 0;
        while (i < split.length && i < split2.length) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt < parseInt2) {
                    return -1;
                }
                if (parseInt > parseInt2) {
                    return 1;
                }
                i++;
            } catch (NumberFormatException e) {
                return str.compareTo(str2);
            }
        }
        if (split.length > i) {
            return 1;
        }
        if (split2.length > i) {
            return -1;
        }
        return 0;
    }

    public static boolean isAppSignatureValid(Context context, String str, String str2) {
        f.a(f.d, "OpenUi, validateAppSignatureForPackage");
        try {
            for (Signature toCharsString : context.getPackageManager().getPackageInfo(str, 64).signatures) {
                if (Util.encrypt(toCharsString.toCharsString()).equals(str2)) {
                    return true;
                }
            }
            return false;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String getAppSignatureMD5(Context context, String str) {
        String packageName;
        Throwable e;
        f.a(f.d, "OpenUi, getSignValidString");
        String str2 = "";
        try {
            packageName = context.getPackageName();
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(packageName, 64).signatures;
            MessageDigest instance = MessageDigest.getInstance(CommonUtils.MD5_INSTANCE);
            instance.update(signatureArr[0].toByteArray());
            String toHexString = Util.toHexString(instance.digest());
            instance.reset();
            f.b("SystemUtils", "-->sign: " + toHexString);
            instance.update(Util.getBytesUTF8(packageName + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + toHexString + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + str + ""));
            packageName = Util.toHexString(instance.digest());
            try {
                instance.reset();
                f.b("SystemUtils", "-->signEncryped: " + packageName);
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                f.b(f.d, "OpenUi, getSignValidString error", e);
                return packageName;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            packageName = str2;
            e = th;
            e.printStackTrace();
            f.b(f.d, "OpenUi, getSignValidString error", e);
            return packageName;
        }
        return packageName;
    }

    public static boolean isActivityExist(Context context, Intent intent) {
        if (context == null || intent == null || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0) {
            return false;
        }
        return true;
    }

    public static String getRealPathFromUri(Activity activity, Uri uri) {
        Cursor managedQuery = activity.managedQuery(uri, new String[]{"_data"}, null, null, null);
        if (managedQuery == null) {
            return null;
        }
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public static String getAppName(Context context) {
        return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }

    public static int compareQQVersion(Context context, String str) {
        return compareVersion(getAppVersionName(context, "com.tencent.mobileqq"), str);
    }

    public static boolean checkMobileQQ(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0);
        } catch (NameNotFoundException e) {
            f.b("checkMobileQQ", NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE);
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        }
        String str = packageInfo.versionName;
        try {
            f.b("MobileQQ verson", str);
            String[] split = str.split("\\.");
            int parseInt = Integer.parseInt(split[0]);
            int parseInt2 = Integer.parseInt(split[1]);
            if (parseInt > 4 || (parseInt == 4 && parseInt2 >= 1)) {
                return true;
            }
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static int getAndroidSDKVersion() {
        int i = 0;
        try {
            i = Integer.valueOf(VERSION.SDK).intValue();
        } catch (NumberFormatException e) {
        }
        return i;
    }

    public static boolean isSupportMultiTouch() {
        boolean z = false;
        boolean z2 = false;
        for (Method method : MotionEvent.class.getDeclaredMethods()) {
            if (method.getName().equals("getPointerCount")) {
                z2 = true;
            }
            if (method.getName().equals("getPointerId")) {
                z = true;
            }
        }
        if (getAndroidSDKVersion() >= 7) {
            return true;
        }
        if (z2 && r2) {
            return true;
        }
        return false;
    }

    @SuppressLint({"SdCardPath"})
    public static boolean extractSecureLib(String str, String str2, int i) {
        Throwable e;
        Throwable th;
        OutputStream outputStream = null;
        f.c(a, "-->extractSecureLib, libName: " + str);
        Context context = Global.getContext();
        if (context == null) {
            f.c(a, "-->extractSecureLib, global context is null. ");
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("secure_lib", 0);
        File file = new File(context.getFilesDir(), str2);
        if (file.exists()) {
            int i2 = sharedPreferences.getInt("version", 0);
            f.c(a, "-->extractSecureLib, libVersion: " + i + " | oldVersion: " + i2);
            if (i == i2) {
                return true;
            }
        } else if (file.getParentFile().mkdirs()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        InputStream open;
        try {
            open = context.getAssets().open(str);
            try {
                outputStream = context.openFileOutput(str2, 0);
                a(open, outputStream);
                Editor edit = sharedPreferences.edit();
                edit.putInt("version", i);
                edit.commit();
                if (open != null) {
                    try {
                        open.close();
                    } catch (IOException e3) {
                    }
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e4) {
                    }
                }
                return true;
            } catch (Exception e5) {
                e = e5;
                try {
                    f.b(a, "-->extractSecureLib, when copy lib execption.", e);
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e6) {
                        }
                    }
                    if (outputStream != null) {
                        return false;
                    }
                    try {
                        outputStream.close();
                        return false;
                    } catch (IOException e7) {
                        return false;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (open != null) {
                        try {
                            open.close();
                        } catch (IOException e8) {
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e9) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e10) {
            e = e10;
            open = null;
            f.b(a, "-->extractSecureLib, when copy lib execption.", e);
            if (open != null) {
                open.close();
            }
            if (outputStream != null) {
                return false;
            }
            outputStream.close();
            return false;
        } catch (Throwable th3) {
            th = th3;
            open = null;
            if (open != null) {
                open.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            throw th;
        }
    }

    @SuppressLint({"SdCardPath"})
    public static boolean isLibExtracted(String str, int i) {
        Context context = Global.getContext();
        if (context == null) {
            f.c(a, "-->isSecureLibExtracted, global context is null. ");
            return false;
        }
        File file = new File(context.getFilesDir(), str);
        SharedPreferences sharedPreferences = context.getSharedPreferences("secure_lib", 0);
        if (!file.exists()) {
            return false;
        }
        int i2 = sharedPreferences.getInt("version", 0);
        f.c(a, "-->extractSecureLib, libVersion: " + i + " | oldVersion: " + i2);
        if (i == i2) {
            return true;
        }
        Editor edit = sharedPreferences.edit();
        edit.putInt("version", i);
        edit.commit();
        return false;
    }

    private static long a(InputStream inputStream, OutputStream outputStream) throws IOException {
        long j = 0;
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr, 0, bArr.length);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
                j += (long) read;
            } else {
                f.c(a, "-->copy, copyed size is: " + j);
                return j;
            }
        }
    }
}
