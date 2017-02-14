package com.tencent.connect.a;

import android.content.Context;
import com.letv.mobile.lebox.ui.qrcode.LeboxQrCodeBean;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.utils.OpenConfig;
import java.lang.reflect.Method;

/* compiled from: ProGuard */
public class a {
    private static Class<?> a = null;
    private static Class<?> b = null;
    private static Method c = null;
    private static Method d = null;
    private static Method e = null;
    private static Method f = null;
    private static boolean g = false;

    public static boolean a(Context context, QQToken qQToken) {
        return OpenConfig.getInstance(context, qQToken.getAppId()).getBoolean("Common_ta_enable");
    }

    public static void b(Context context, QQToken qQToken) {
        try {
            if (a(context, qQToken)) {
                f.invoke(a, new Object[]{Boolean.valueOf(true)});
                return;
            }
            f.invoke(a, new Object[]{Boolean.valueOf(false)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void c(Context context, QQToken qQToken) {
        String str = "Aqc" + qQToken.getAppId();
        try {
            a = Class.forName("com.tencent.stat.StatConfig");
            b = Class.forName("com.tencent.stat.StatService");
            c = b.getMethod("reportQQ", new Class[0]);
            d = b.getMethod("trackCustomEvent", new Class[0]);
            e = b.getMethod("commitEvents", new Class[0]);
            f = a.getMethod("setEnableStatService", new Class[0]);
            b(context, qQToken);
            a.getMethod("setAutoExceptionCaught", new Class[0]).invoke(a, new Object[]{Boolean.valueOf(false)});
            a.getMethod("setEnableSmartReporting", new Class[0]).invoke(a, new Object[]{Boolean.valueOf(true)});
            a.getMethod("setSendPeriodMinutes", new Class[0]).invoke(a, new Object[]{Integer.valueOf(1440)});
            a.getMethod("setStatSendStrategy", new Class[0]).invoke(a, new Object[]{Integer.valueOf(Class.forName("com.tencent.stat.StatReportStrategy").getField("PERIOD").getInt(null))});
            a.getMethod("setStatReportUrl", new Class[0]).invoke(a, new Object[]{"http://cgi.connect.qq.com/qqconnectutil/sdk"});
            b.getMethod("startStatService", new Class[0]).invoke(b, new Object[]{context, str, Integer.valueOf(Class.forName("com.tencent.stat.common.StatConstants").getField(LeboxQrCodeBean.KEY_VERSION).getInt(null))});
            g = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void d(Context context, QQToken qQToken) {
        if (g) {
            b(context, qQToken);
            if (qQToken.getOpenId() != null) {
                try {
                    c.invoke(b, new Object[]{context, qQToken.getOpenId()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void a(Context context, QQToken qQToken, String str, String... strArr) {
        if (g) {
            b(context, qQToken);
            try {
                d.invoke(b, new Object[]{context, str, strArr});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
