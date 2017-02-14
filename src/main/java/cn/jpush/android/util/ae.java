package cn.jpush.android.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ApplicationInfo.DisplayNameComparator;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ae {
    private static final String a = ae.class.getSimpleName();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 8;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "0=\\&\u00028*Q";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002e;
    L_0x0012:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0017:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0081;
            case 1: goto L_0x0084;
            case 2: goto L_0x0087;
            case 3: goto L_0x008a;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
    L_0x0020:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002c;
    L_0x0028:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0017;
    L_0x002c:
        r5 = r1;
        r1 = r7;
    L_0x002e:
        if (r5 > r6) goto L_0x0012;
    L_0x0030:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0044;
            case 1: goto L_0x004c;
            case 2: goto L_0x0054;
            case 3: goto L_0x005c;
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            case 6: goto L_0x0074;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "[\"%3y[\"\"B\bX";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0004\nnbL";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "0.X\u0010\u001a03M";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "=7^*+\";K \u001a5-";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "22I<\u0007\u000e0I\"\u0011";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "!5O\u0010\u001a03M";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\";Z9\u001d2;w#\u001d\"*";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        z = r4;
        r0 = cn.jpush.android.util.ae.class;
        r0 = r0.getSimpleName();
        a = r0;
        return;
    L_0x0081:
        r9 = 81;
        goto L_0x0020;
    L_0x0084:
        r9 = 94;
        goto L_0x0020;
    L_0x0087:
        r9 = 40;
        goto L_0x0020;
    L_0x008a:
        r9 = 79;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.util.ae.<clinit>():void");
    }

    private static String a(String str, int i) {
        if (str == null) {
            return str;
        }
        String replaceAll = Pattern.compile(z[1]).matcher(str).replaceAll("");
        try {
            byte[] bytes = replaceAll.getBytes();
            return bytes.length > 30 ? replaceAll.substring(0, new String(bytes, 0, 30, z[2]).length()) : replaceAll;
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
            z.e();
            return replaceAll;
        }
    }

    private static Set<String> a(ActivityManager activityManager) {
        Set<String> hashSet = new HashSet();
        for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            String[] strArr = runningAppProcessInfo.pkgList;
            for (Object add : strArr) {
                hashSet.add(add);
            }
        }
        return hashSet;
    }

    private static JSONArray a(ActivityManager activityManager, PackageManager packageManager) {
        JSONArray jSONArray = new JSONArray();
        Set a = a(activityManager);
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(8192);
        List<RunningServiceInfo> runningServices = activityManager.getRunningServices(200);
        Collections.sort(installedApplications, new DisplayNameComparator(packageManager));
        long elapsedRealtime = SystemClock.elapsedRealtime();
        for (ApplicationInfo applicationInfo : installedApplications) {
            String a2 = a(applicationInfo.loadLabel(packageManager).toString(), 30);
            if (a.contains(applicationInfo.packageName)) {
                JSONObject jSONObject = new JSONObject();
                JSONArray jSONArray2 = new JSONArray();
                for (RunningServiceInfo runningServiceInfo : runningServices) {
                    if (runningServiceInfo.service.getPackageName().equals(applicationInfo.packageName)) {
                        JSONObject jSONObject2 = new JSONObject();
                        long round = (long) Math.round((float) ((elapsedRealtime - runningServiceInfo.activeSince) / 1000));
                        try {
                            jSONObject2.put(z[5], runningServiceInfo.service.getShortClassName());
                            jSONObject2.put(z[4], round);
                            jSONArray2.put(jSONObject2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    jSONObject.put(z[3], a2);
                    jSONObject.put(z[6], applicationInfo.packageName);
                    jSONObject.put(z[7], jSONArray2);
                    jSONArray.put(jSONObject);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return jSONArray;
    }

    public static void a(Context context) {
        z.b();
        JSONArray a = a((ActivityManager) context.getSystemService(z[0]), context.getPackageManager());
        if (a != null && a.length() != 0) {
            ac.a(context, a);
        }
    }
}
