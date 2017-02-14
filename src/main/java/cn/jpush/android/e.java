package cn.jpush.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import cn.jpush.android.api.k;
import cn.jpush.android.helpers.i;
import cn.jpush.android.service.PushProtocol;
import cn.jpush.android.service.PushService;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.f;
import cn.jpush.android.util.z;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public final class e {
    public static boolean a = false;
    public static int b;
    public static String c;
    public static String d;
    public static Context e;
    public static String f;
    public static long g = 0;
    public static String h = "";
    public static int i;
    public static String j;
    public static boolean k = false;
    public static boolean l = false;
    public static boolean m = false;
    public static boolean n = true;
    public static b o;
    private static AtomicBoolean p = new AtomicBoolean(false);
    private static ServiceConnection q = new f();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 22;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Ol`\u0017\u000elb4A\u0018zz}X\u0013(ou^\u0011)R莣叡\u000elb牜望奌购(I";
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
            case 0: goto L_0x0138;
            case 1: goto L_0x013c;
            case 2: goto L_0x0140;
            case 3: goto L_0x0144;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
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
            case 0: goto L_0x0045;
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005d;
            case 4: goto L_0x0066;
            case 5: goto L_0x006e;
            case 6: goto L_0x0076;
            case 7: goto L_0x007f;
            case 8: goto L_0x0089;
            case 9: goto L_0x0094;
            case 10: goto L_0x009f;
            case 11: goto L_0x00aa;
            case 12: goto L_0x00b5;
            case 13: goto L_0x00c0;
            case 14: goto L_0x00cb;
            case 15: goto L_0x00d6;
            case 16: goto L_0x00e1;
            case 17: goto L_0x00ec;
            case 18: goto L_0x00f7;
            case 19: goto L_0x0102;
            case 20: goto L_0x010d;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "{fBR\u000f{`{YG";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "BYaD\u0015Oe{U\u001cd";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "bhbVSfl`\u0019\rzlrR\u000fAYb\u0001<lmfR\u000e{lg";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "nhxD\u0018";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "|{aR";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0066:
        r3[r2] = r1;
        r2 = 6;
        r1 = "FF4A\u0018zz}X\u0013KfpR]g{4A\u0018zz}X\u0013FhyR]llr^\u0013mm4^\u0013(duY\u0014nlgCS";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006e:
        r3[r2] = r1;
        r2 = 7;
        r1 = "el`V\u0019i}u\r]Khz\u0017\u0013g}4P\u0018|)UG\rd`wV\tafz\u0017\u0014kfz\u001b]qfa\u0017\naex\u0017\u001fm)zX\t(hv[\u0018(}{\u0017\u000e`fc\u0017\u0013g}}Q\u0014kh`^\u0012f)pB\u0018(}{\u0017\t`l4V\rxe}T\u001c|`{Y]aj{Y]az4Y\bde:";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "BYaD\u0015(juY\u0013g}4U\u0018(`z^\tahx^\u0007mm4T\u0012eyxR\tmem\u0017\u0019}l4C\u0012(GA{1(hdG4fo{\u0019";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = "BYaD\u0015('gX]n`xR]lf4Y\u0012|)yV\tka4}-}z|\u0017Sbhf\u0017\u001baeq\u0017\u0014f)`_\u0018(yfX\u0017mj`\u001b]Nh}[\u0018l)`X]ag}C]BYaD\u0015";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0089:
        r3[r2] = r1;
        r2 = 10;
        r1 = "bhbVSfl`\u0019\rzlrR\u000fAYb\u0003.|hw\\";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "el`V\u0019i}u\r]Khz\u0017\u0013g}4P\u0018|)yR\tiMuC\u001c(ofX\u0010(HdG\u0011ajuC\u0014gg]Y\u001bg";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "FF4Z\u0018|h4S\u001c|h4S\u0018n`zR\u0019(`z\u0017\u0010ig}Q\u0018{}:";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00aa:
        r3[r2] = r1;
        r2 = 13;
        r1 = "BYAd5WJ\\v3FLX";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b5:
        r3[r2] = r1;
        r2 = 14;
        r1 = "el`V\u0019i}u\r]kauY\u0013me4\u001a]ff`\u0017\u0019mo}Y\u0018l)}Y]ehz^\u001bmz`";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c0:
        r3[r2] = r1;
        r2 = 15;
        r1 = "$)D[\u0018izq\u0017\u001am}4N\u0012}{4v\rxbqN]n{{Z]BYaD\u0015(~qU]kfzD\u0012dl5";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cb:
        r3[r2] = r1;
        r2 = 16;
        r1 = "el`V\u0019i}u\r]BYaD\u0015(hdG6mp4\u001a]ff`\u0017\u0019mo}Y\u0018l)}Y]ehz^\u001bmz`";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d6:
        r3[r2] = r1;
        r2 = 17;
        r1 = "el`V\u0019i}u\r]iyd|\u0018q)9\u0017";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e1:
        r3[r2] = r1;
        r2 = 18;
        r1 = "el`V\u0019i}u\r]kauY\u0013me4\u001a]";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ec:
        r3[r2] = r1;
        r2 = 19;
        r1 = "]gqO\rmj`R\u00192)rV\u0014dlp\u0017\tg)sR\t(jaE\u000fmg`\u0017\u001cxyx^\u001ei}}X\u0013(`zQ\u0012";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f7:
        r3[r2] = r1;
        r2 = 20;
        r1 = "AgbV\u0011am4V\rxBqN]2)";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0102:
        r3[r2] = r1;
        r2 = 21;
        r1 = "BYAd5WHDg6MP";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010d:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        a = r0;
        r0 = new java.util.concurrent.atomic.AtomicBoolean;
        r1 = 0;
        r0.<init>(r1);
        p = r0;
        r0 = 0;
        g = r0;
        r0 = "";
        h = r0;
        r0 = 0;
        k = r0;
        r0 = 0;
        l = r0;
        r0 = 0;
        m = r0;
        r0 = 1;
        n = r0;
        r0 = new cn.jpush.android.f;
        r0.<init>();
        q = r0;
        return;
    L_0x0138:
        r9 = 8;
        goto L_0x0020;
    L_0x013c:
        r9 = 9;
        goto L_0x0020;
    L_0x0140:
        r9 = 20;
        goto L_0x0020;
    L_0x0144:
        r9 = 55;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.e.<clinit>():void");
    }

    private static boolean a() {
        int GetSdkVersion;
        UnsatisfiedLinkError e;
        try {
            GetSdkVersion = PushProtocol.GetSdkVersion();
            try {
                new StringBuilder(z[1]).append(GetSdkVersion);
                z.a();
            } catch (UnsatisfiedLinkError e2) {
                e = e2;
                z.e(z[2], z[0]);
                e.printStackTrace();
                return GetSdkVersion >= 200;
            }
        } catch (UnsatisfiedLinkError e3) {
            e = e3;
            GetSdkVersion = 0;
            z.e(z[2], z[0]);
            e.printStackTrace();
            if (GetSdkVersion >= 200) {
            }
        }
        if (GetSdkVersion >= 200) {
        }
    }

    public static synchronized boolean a(Context context) {
        boolean z = false;
        synchronized (e.class) {
            if (p.get()) {
                z = true;
            } else {
                z.b();
                f.l(context.getApplicationContext());
                i.a(context);
                if (a()) {
                    c = context.getPackageName();
                    e = context.getApplicationContext();
                    g = a.t();
                    h = a.w();
                    ApplicationInfo c = c(context);
                    if (c == null) {
                        z.e(z[2], z[8]);
                    } else {
                        b = c.icon;
                        if (b == 0) {
                            z.e(z[2], z[7]);
                        }
                        d = context.getPackageManager().getApplicationLabel(c).toString();
                        try {
                            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                            i = packageInfo.versionCode;
                            String str = packageInfo.versionName;
                            j = str;
                            if (str.length() > 30) {
                                j = j.substring(0, 30);
                            }
                        } catch (Exception e) {
                            z.b(z[2], z[6]);
                        }
                        if (b(context)) {
                            if (VERSION.SDK_INT >= 14 && (context instanceof Application)) {
                                z = a.g(context);
                                k.a = z;
                                if (z) {
                                    k.a((Application) context.getApplicationContext());
                                }
                            }
                            if (VERSION.SDK_INT == 8) {
                                System.setProperty(z[10], z[5]);
                                System.setProperty(z[3], z[4]);
                            }
                            p.set(true);
                            Context applicationContext = context.getApplicationContext();
                            Intent intent = new Intent();
                            intent.setClass(applicationContext, PushService.class);
                            applicationContext.bindService(intent, q, 1);
                            z.a();
                            z = true;
                        }
                    }
                } else {
                    z.e(z[2], z[9]);
                }
            }
        }
        return z;
    }

    private static boolean b(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                Bundle bundle = applicationInfo.metaData;
                if (bundle != null) {
                    String string = bundle.getString(z[21]);
                    f = string;
                    if (ai.a(string)) {
                        z.e(z[2], z[16]);
                        return false;
                    } else if (f.length() != 24) {
                        z.e(z[2], new StringBuilder(z[20]).append(f).append(z[15]).toString());
                        a.c(context, 1008);
                        return false;
                    } else {
                        f = f.toLowerCase(Locale.getDefault());
                        z.b(z[2], new StringBuilder(z[17]).append(f).toString());
                        a.h(f);
                        String c = ai.c(bundle.getString(z[13]));
                        if (ai.a(c)) {
                            z.b(z[2], z[14]);
                        } else {
                            z.b(z[2], new StringBuilder(z[18]).append(c).toString());
                            a.k(c);
                        }
                        return true;
                    }
                }
                z.b(z[2], z[12]);
                return false;
            }
            z.b(z[2], z[11]);
            return false;
        } catch (Throwable e) {
            z.a(z[2], z[19], e);
            return false;
        }
    }

    private static ApplicationInfo c(Context context) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
        } catch (Throwable e) {
            z.b(z[2], z[19], e);
            return null;
        }
    }
}
