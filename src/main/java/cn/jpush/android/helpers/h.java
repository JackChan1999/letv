package cn.jpush.android.helpers;

import android.content.Context;
import android.telephony.TelephonyManager;
import cn.jpush.android.e;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;

public final class h {
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u001e\u00058o).\u0005\u0002|,=\u00058";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0068;
            case 1: goto L_0x006b;
            case 2: goto L_0x006e;
            case 3: goto L_0x0071;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 64;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            case 4: goto L_0x0063;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0019\b/96(\u00129p/#.+t%m\t99.\"\u0014jo!!\t.5`\u001d\f/x3(@)q%.\u000bj`/8\u0012jX.)\u0012%p$\u0000\u0001$p&(\u0013>78 \f";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0018\u000e!w/:\u000e";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "iD";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "=\b%w%";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = ",\u000e.k/$\u0004di%?\r#j3$\u000f$7\u0012\b!\u000eF\u0010\u0005/\u0004\\\u001f\u001e4\u000bM\u0005";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0063:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0068:
        r9 = 77;
        goto L_0x001f;
    L_0x006b:
        r9 = 96;
        goto L_0x001f;
    L_0x006e:
        r9 = 74;
        goto L_0x001f;
    L_0x0071:
        r9 = 25;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.h.<clinit>():void");
    }

    public static String a(Context context) {
        String subscriberId;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(z[4]);
        String h = a.h(context);
        if (a.c(context, z[5])) {
            try {
                subscriberId = telephonyManager.getSubscriberId();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (h == null) {
                h = " ";
            }
            if (subscriberId == null) {
                subscriberId = " ";
            }
            return h + z[3] + subscriberId + z[3] + context.getPackageName() + z[3] + e.f;
        }
        subscriberId = null;
        if (h == null) {
            h = " ";
        }
        if (subscriberId == null) {
            subscriberId = " ";
        }
        return h + z[3] + subscriberId + z[3] + context.getPackageName() + z[3] + e.f;
    }

    public static boolean a(int i) {
        return i == 14 || i == 13 || i == 15;
    }

    public static String b(Context context) {
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str.length() <= 30) {
                return str;
            }
            z.e(z[0], z[1]);
            return str.substring(0, 30);
        } catch (Exception e) {
            return z[2];
        }
    }

    public static void c(Context context) {
        if (cn.jpush.android.a.d(context)) {
            z.b();
            ServiceInterface.a(context, 1);
            cn.jpush.android.a.a(context, false);
            return;
        }
        z.b();
    }
}
