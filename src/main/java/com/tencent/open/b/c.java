package com.tencent.open.b;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.letv.core.utils.LetvUtils;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import java.util.Locale;

/* compiled from: ProGuard */
public class c {
    static String a = null;
    static String b = null;
    static String c = null;
    private static String d;
    private static String e = null;

    public static String a() {
        try {
            Context context = Global.getContext();
            if (context == null) {
                return "";
            }
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager == null) {
                return "";
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo == null) {
                return "";
            }
            return connectionInfo.getMacAddress();
        } catch (Throwable e) {
            f.b("MobileInfoUtil", "getLocalMacAddress>>>", e);
            return "";
        }
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(d)) {
            return d;
        }
        if (context == null) {
            return "";
        }
        d = "";
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            int width = windowManager.getDefaultDisplay().getWidth();
            d = width + "x" + windowManager.getDefaultDisplay().getHeight();
        }
        return d;
    }

    public static String b() {
        return Locale.getDefault().getLanguage();
    }

    public static String b(Context context) {
        if (a != null && a.length() > 0) {
            return a;
        }
        if (context == null) {
            return "";
        }
        try {
            a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            return a;
        } catch (Exception e) {
            return "";
        }
    }

    public static String c(Context context) {
        if (b != null && b.length() > 0) {
            return b;
        }
        if (context == null) {
            return "";
        }
        try {
            b = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            return b;
        } catch (Exception e) {
            return "";
        }
    }

    public static String d(Context context) {
        if (c != null && c.length() > 0) {
            return c;
        }
        if (context == null) {
            return "";
        }
        try {
            c = Secure.getString(context.getContentResolver(), "android_id");
            return c;
        } catch (Exception e) {
            return "";
        }
    }

    public static String e(Context context) {
        try {
            if (e == null) {
                WindowManager windowManager = (WindowManager) context.getSystemService("window");
                DisplayMetrics displayMetrics = new DisplayMetrics();
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("imei=").append(b(context)).append(LetvUtils.CHARACTER_AND);
                stringBuilder.append("model=").append(Build.MODEL).append(LetvUtils.CHARACTER_AND);
                stringBuilder.append("os=").append(VERSION.RELEASE).append(LetvUtils.CHARACTER_AND);
                stringBuilder.append("apilevel=").append(VERSION.SDK_INT).append(LetvUtils.CHARACTER_AND);
                String b = a.b(context);
                if (b == null) {
                    b = "";
                }
                stringBuilder.append("network=").append(b).append(LetvUtils.CHARACTER_AND);
                stringBuilder.append("sdcard=").append(Environment.getExternalStorageState().equals("mounted") ? 1 : 0).append(LetvUtils.CHARACTER_AND);
                stringBuilder.append("display=").append(displayMetrics.widthPixels).append('*').append(displayMetrics.heightPixels).append(LetvUtils.CHARACTER_AND);
                stringBuilder.append("manu=").append(Build.MANUFACTURER).append("&");
                stringBuilder.append("wifi=").append(a.e(context));
                e = stringBuilder.toString();
            }
            return e;
        } catch (Exception e) {
            return null;
        }
    }
}
