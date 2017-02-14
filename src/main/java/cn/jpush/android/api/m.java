package cn.jpush.android.api;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.widget.RemoteViews;
import cn.jpush.android.data.c;
import cn.jpush.android.e;
import cn.jpush.android.helpers.g;
import cn.jpush.android.service.PushReceiver;
import cn.jpush.android.service.PushService;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.o;
import cn.jpush.android.util.z;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.zip.Adler32;

public final class m {
    private static Queue<Integer> a = new LinkedList();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 34;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "dIKF}k\u0010\\CwdD~C~KEKFtlI^[{jD\u001f\u00022hOL\\sbOvK(";
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
            case 0: goto L_0x019e;
            case 1: goto L_0x01a1;
            case 2: goto L_0x01a5;
            case 3: goto L_0x01a9;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 18;
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
            case 2: goto L_0x0055;
            case 3: goto L_0x005d;
            case 4: goto L_0x0065;
            case 5: goto L_0x006d;
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
            case 21: goto L_0x0118;
            case 22: goto L_0x0123;
            case 23: goto L_0x012e;
            case 24: goto L_0x0139;
            case 25: goto L_0x0144;
            case 26: goto L_0x014f;
            case 27: goto L_0x015a;
            case 28: goto L_0x0165;
            case 29: goto L_0x0170;
            case 30: goto L_0x017c;
            case 31: goto L_0x0187;
            case 32: goto L_0x0192;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "kEKFtlI^[{jD";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "+x";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "uKMN`^Z]2wOL{kuO\u001f@`%LVJ~ad^Bwv\nZ]`jX\u0011";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "dIKF}k\u0010\\CwdDq@flLVLsqCPA2(\nQ@flLVLsqCPA[a\u0010";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "W\u000e[]srK]Cw";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        r2 = 6;
        r1 = "oZJ\\zZDP[{cC\\NflEQp{fEQ";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006d:
        r3[r2] = r1;
        r2 = 7;
        r1 = "vOKcsqOL[WsOQ[[kLP";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "dD[]}lN\u0011Nbu\u0004q@flLVLsqCPA";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = "dIKF}k\u0010\\CwdDq@flLVLsqCPA2(\nRJavKXJ[a\u0010";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0089:
        r3[r2] = r1;
        r2 = 10;
        r1 = "dZO";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004q`FLlvlSQcpaMQsoj";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "aOSFd`XF\u000f`lIW\u000fbpYW\u000ff|ZZ\u00152";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00aa:
        r3[r2] = r1;
        r2 = 13;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004~cWW~";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b5:
        r3[r2] = r1;
        r2 = 14;
        r1 = "KEKFtlI^[{jDwJ~uOM";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c0:
        r3[r2] = r1;
        r2 = 15;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004r|UZc{";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cb:
        r3[r2] = r1;
        r2 = 16;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004q`FLlvlSQcpaMAoij^Jzz}MDxx\u001f";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d6:
        r3[r2] = r1;
        r2 = 17;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004q`FLlvlSQcpaMFeq{WK~`{[Qfz";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e1:
        r3[r2] = r1;
        r2 = 18;
        r1 = "BEK\u000f\\Pfs\u000f|j^VI{fKKF}k\u0004\u001fh{sO\u001fZb%^P\u000famEH\u0001";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ec:
        r3[r2] = r1;
        r2 = 19;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004zwFWk";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f7:
        r3[r2] = r1;
        r2 = 20;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004VAf`DK\u0001\\J~vi[Fkkf]KupWKo{";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0102:
        r3[r2] = r1;
        r2 = 21;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004VAf`DK\u0001\\J~vi[Fkkf]KupWKo{pBWegv<";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010d:
        r3[r2] = r1;
        r2 = 22;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004w{_IumjA";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x0118:
        r3[r2] = r1;
        r2 = 23;
        r1 = "cCSJ(*\u0005";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0123:
        r3[r2] = r1;
        r2 = 24;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004VAf`DK\u0001\\J~vi[Fkkf]KumjQ@cijV";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x012e:
        r3[r2] = r1;
        r2 = 25;
        r1 = "+ZZ]lYLF}k\u0004uGVb`bWVy~hW";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x0139:
        r3[r2] = r1;
        r2 = 26;
        r1 = "m^K_(*\u0005";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0144:
        r3[r2] = r1;
        r2 = 27;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004w{_IuonFM";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x014f:
        r3[r2] = r1;
        r2 = 28;
        r1 = "VOQK2u_LG2wO\\J{sO[\u000fpwE^KqdYK\u000ffj\n[Jd`FP_ww\n[JtlDZK2wO\\J{sOM";
        r0 = 27;
        r3 = r4;
        goto L_0x0009;
    L_0x015a:
        r3[r2] = r1;
        r2 = 29;
        r1 = "hOL\\sbO`FdMZpaqKKJMgKMp~dSPZf";
        r0 = 28;
        r3 = r4;
        goto L_0x0009;
    L_0x0165:
        r3[r2] = r1;
        r2 = 30;
        r1 = "W\u000eVK";
        r0 = 29;
        r3 = r4;
        goto L_0x0009;
    L_0x0170:
        r3[r2] = r1;
        r2 = 31;
        r1 = "v^^[wZH^]MlG^HwZ\\VJe";
        r0 = 30;
        r3 = r4;
        goto L_0x0009;
    L_0x017c:
        r3[r2] = r1;
        r2 = 32;
        r1 = "W\u000eSNkj_K";
        r0 = 31;
        r3 = r4;
        goto L_0x0009;
    L_0x0187:
        r3[r2] = r1;
        r2 = 33;
        r1 = "fD\u0011EbpYW\u0001skNM@{a\u0004q`FLlvlSQcpaMLn";
        r0 = 32;
        r3 = r4;
        goto L_0x0009;
    L_0x0192:
        r3[r2] = r1;
        z = r4;
        r0 = new java.util.LinkedList;
        r0.<init>();
        a = r0;
        return;
    L_0x019e:
        r9 = 5;
        goto L_0x0020;
    L_0x01a1:
        r9 = 42;
        goto L_0x0020;
    L_0x01a5:
        r9 = 63;
        goto L_0x0020;
    L_0x01a9:
        r9 = 47;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.m.<clinit>():void");
    }

    public static int a(int i) {
        switch (i) {
            case -1:
                int intValue;
                try {
                    intValue = ((Integer) a(z[5], new String[]{z[6]}).get(z[6])).intValue();
                } catch (Exception e) {
                    intValue = 0;
                    z.e();
                }
                return intValue <= 0 ? 17301618 : intValue;
            case 0:
                return 17301647;
            case 2:
                return 17301618;
            case 3:
                return 17301567;
            default:
                return 17301586;
        }
    }

    public static int a(c cVar, int i) {
        String str = cVar.c;
        if (!ai.a(cVar.d)) {
            str = cVar.d;
        }
        return a(str, i);
    }

    private static int a(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            z.b();
            return 0;
        }
        try {
            return Integer.valueOf(str).intValue();
        } catch (Exception e) {
            z.d();
            Adler32 adler32 = new Adler32();
            adler32.update(str.getBytes());
            int value = (int) adler32.getValue();
            if (value < 0) {
                value = Math.abs(value);
            }
            value += 13889152 * i;
            return value < 0 ? Math.abs(value) : value;
        }
    }

    public static Notification a(Context context, int i, Intent intent, c cVar, boolean z, boolean z2) {
        int i2;
        int i3 = -1;
        if (z) {
            z.b();
            try {
                i2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 256).applicationInfo.icon;
            } catch (NameNotFoundException e) {
                i2 = i3;
                z.g();
            }
        } else {
            i2 = i3;
        }
        if (i2 < 0) {
            z.d();
            return null;
        }
        PendingIntent broadcast = z2 ? PendingIntent.getBroadcast(context, i, intent, 134217728) : PendingIntent.getActivity(context, i, intent, 134217728);
        Bitmap decodeFile;
        Integer num;
        Integer num2;
        if (VERSION.SDK_INT >= 11) {
            Builder ticker = new Builder(context.getApplicationContext()).setWhen(System.currentTimeMillis()).setSmallIcon(i2).setTicker(cVar.t);
            if (cVar.h) {
                ticker.setDefaults(3);
                if (a.n(context)) {
                    ticker.setDefaults(0);
                }
            }
            if (ai.a(cVar.A)) {
                ticker.setContentTitle(cVar.s).setContentText(cVar.t).setContentIntent(broadcast);
            } else {
                try {
                    decodeFile = BitmapFactory.decodeFile(cVar.A);
                    if (decodeFile != null) {
                        num = (Integer) a(z[30], new String[]{z[31]}).get(z[31]);
                        num2 = (Integer) a(z[32], new String[]{z[29]}).get(z[29]);
                        if (num == null || num2 == null || num.intValue() <= 0 || num2.intValue() <= 0) {
                            z.d();
                            return null;
                        }
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), num2.intValue());
                        remoteViews.setImageViewBitmap(num.intValue(), decodeFile);
                        ticker.setContent(remoteViews);
                        ticker.setContentIntent(broadcast);
                    } else {
                        z.d();
                        return null;
                    }
                } catch (Exception e2) {
                    z.h();
                    return null;
                }
            }
            Notification notification = ticker.getNotification();
            notification.flags = b(cVar.r);
            return notification;
        }
        Notification notification2 = new Notification();
        notification2.when = System.currentTimeMillis();
        notification2.icon = i2;
        notification2.tickerText = cVar.t;
        notification2.flags = b(cVar.r);
        if (cVar.h) {
            notification2.defaults = 3;
            if (a.n(context)) {
                notification2.defaults = 0;
            }
        }
        if (ai.a(cVar.A)) {
            a(notification2, context, cVar.s, cVar.t, broadcast);
        } else {
            try {
                decodeFile = BitmapFactory.decodeFile(cVar.A);
                if (decodeFile != null) {
                    num = (Integer) a(z[30], new String[]{z[31]}).get(z[31]);
                    num2 = (Integer) a(z[32], new String[]{z[29]}).get(z[29]);
                    if (num == null || num2 == null || num.intValue() <= 0 || num2.intValue() <= 0) {
                        z.d();
                        return null;
                    }
                    remoteViews = new RemoteViews(context.getPackageName(), num2.intValue());
                    remoteViews.setImageViewBitmap(num.intValue(), decodeFile);
                    notification2.contentView = remoteViews;
                    notification2.contentIntent = broadcast;
                } else {
                    z.d();
                    return null;
                }
            } catch (Exception e3) {
                z.h();
                return null;
            }
        }
        return notification2;
    }

    public static HashMap<String, Integer> a(String str, String[] strArr) {
        int i = 0;
        if (ai.a(str) || strArr.length == 0) {
            throw new NullPointerException(z[3]);
        }
        HashMap<String, Integer> hashMap = new HashMap();
        try {
            int length;
            for (Class cls : Class.forName(e.e.getPackageName() + z[2]).getDeclaredClasses()) {
                if (cls.getName().contains(str)) {
                    length = strArr.length;
                    while (i < length) {
                        String str2 = strArr[i];
                        hashMap.put(str2, Integer.valueOf(cls.getDeclaredField(str2).getInt(str2)));
                        i++;
                    }
                    return hashMap;
                }
            }
        } catch (Exception e) {
            z.h();
        }
        return hashMap;
    }

    public static void a(Notification notification, Context context, String str, String str2, PendingIntent pendingIntent) {
        try {
            Class.forName(z[8]).getDeclaredMethod(z[7], new Class[]{Context.class, CharSequence.class, CharSequence.class, PendingIntent.class}).invoke(notification, new Object[]{context, str, str2, pendingIntent});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(Context context) {
        while (true) {
            Integer num = (Integer) a.poll();
            if (num != null) {
                b(context, num.intValue());
            } else {
                return;
            }
        }
    }

    public static void a(Context context, int i) {
        int size = a.size() - i;
        if (size > 0) {
            for (int i2 = 0; i2 < size; i2++) {
                Integer num = (Integer) a.poll();
                if (num != null) {
                    b(context, num.intValue());
                }
            }
        }
    }

    public static void a(Context context, c cVar) {
        if (Thread.currentThread().getId() == PushService.a) {
            z.c();
            new Thread(new n(context, cVar)).start();
            return;
        }
        b(context, cVar);
    }

    public static void a(Context context, c cVar, int i) {
        new StringBuilder(z[9]).append(cVar.c);
        z.b();
        if (context == null) {
            context = e.e;
        }
        ((NotificationManager) context.getSystemService(z[1])).cancel(a(cVar, i));
    }

    public static void a(Context context, String str) {
        new StringBuilder(z[0]).append(str);
        z.b();
        if (context == null) {
            context = e.e;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(z[1]);
        notificationManager.cancel(a(str, 0));
        notificationManager.cancel(a(str, 1));
    }

    private static void a(Context context, Map<String, String> map, int i, String str, String str2, c cVar) {
        z.b(z[14], z[28]);
        Intent intent = new Intent(z[24]);
        a(intent, (Map) map, i);
        if (!ai.a(str)) {
            intent.putExtra(z[16], str);
        }
        if (cVar.e() && (cVar instanceof cn.jpush.android.data.m)) {
            cn.jpush.android.data.m mVar = (cn.jpush.android.data.m) cVar;
            if (!(mVar.G == 0 || mVar.G == 4)) {
                if (mVar.L != null && mVar.L.startsWith(z[23])) {
                    mVar.L = mVar.L.replaceFirst(z[23], "");
                    intent.putExtra(z[27], mVar.L);
                }
                if (mVar.I != null && mVar.I.size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String b = cn.jpush.android.util.m.b(context, cVar.c);
                    Iterator it = mVar.I.iterator();
                    while (it.hasNext()) {
                        String str3 = (String) it.next();
                        if (str3.startsWith(z[26])) {
                            str3 = o.c(str3);
                        }
                        if (ai.a(stringBuilder.toString())) {
                            stringBuilder.append(b).append(str3);
                        } else {
                            stringBuilder.append(",").append(b).append(str3);
                        }
                    }
                    intent.putExtra(z[22], stringBuilder.toString());
                }
            }
        }
        intent.addCategory(str2);
        context.sendBroadcast(intent, str2 + z[25]);
    }

    private static void a(Intent intent, Map<String, String> map, int i) {
        for (String str : map.keySet()) {
            intent.putExtra(str, (String) map.get(str));
        }
        if (i != 0) {
            intent.putExtra(z[33], i);
        }
    }

    private static int b(int i) {
        switch (i) {
            case 1:
                return 16;
            case 2:
                return 32;
            default:
                return 1;
        }
    }

    public static void b(Context context, int i) {
        new StringBuilder(z[4]).append(i);
        z.b();
        if (context == null) {
            context = e.e;
        }
        ((NotificationManager) context.getSystemService(z[1])).cancel(i);
    }

    public static void b(Context context, c cVar) {
        z.a();
        int a = a(cVar, 0);
        if (cVar.h && cVar.e) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(z[1]);
            if (cVar instanceof cn.jpush.android.data.m) {
                String str = cVar.t;
                CharSequence charSequence = cVar.s;
                String str2 = cVar.l;
                String packageName = ai.a(cVar.m) ? context.getPackageName() : cVar.m;
                Map hashMap = new HashMap();
                hashMap.put(z[15], cVar.c);
                hashMap.put(z[13], str);
                if (!TextUtils.isEmpty(charSequence)) {
                    hashMap.put(z[17], charSequence);
                }
                if (!ai.a(str2)) {
                    hashMap.put(z[19], str2);
                }
                if (ai.a(str)) {
                    a(context, hashMap, 0, "", packageName, cVar);
                    return;
                }
                PushNotificationBuilder b = JPushInterface.b(cVar.f);
                String a2 = b.a();
                Notification a3 = b.a(str, hashMap);
                if (a3 == null || ai.a(str)) {
                    z.d(z[14], z[18]);
                    return;
                }
                PendingIntent activity;
                Intent a4;
                if (cVar.e()) {
                    new StringBuilder(z[12]).append(((cn.jpush.android.data.m) cVar).G);
                    z.c();
                    a4 = (3 == ((cn.jpush.android.data.m) cVar).G || 4 == ((cn.jpush.android.data.m) cVar).G || ((cn.jpush.android.data.m) cVar).G == 0) ? a.a(context, cVar, false) : 2 == ((cn.jpush.android.data.m) cVar).G ? a.a(context, cVar) : a.a(context, cVar, false);
                    activity = PendingIntent.getActivity(context, a, a4, 134217728);
                } else {
                    if (a.d(context, PushReceiver.class.getCanonicalName())) {
                        a4 = new Intent(new StringBuilder(z[21]).append(UUID.randomUUID().toString()).toString());
                        a4.setClass(context, PushReceiver.class);
                        a4.putExtra(z[11], cVar.g);
                    } else {
                        z.c();
                        a4 = new Intent(z[20]);
                        a4.addCategory(packageName);
                    }
                    a(a4, hashMap, a);
                    a4.putExtra(z[13], str);
                    a4.putExtra(z[10], packageName);
                    if (!ai.a(a2)) {
                        a4.putExtra(z[16], a2);
                    }
                    activity = PendingIntent.getBroadcast(context, 0, a4, 1073741824);
                }
                a3.contentIntent = activity;
                if (!JPushInterface.a(cVar.f)) {
                    if (1 == cVar.g) {
                        cVar.r = 1;
                    }
                    a3.flags = b(cVar.r);
                    if (a3.defaults == 0) {
                        a3.defaults = 3;
                    }
                }
                if (a.n(context)) {
                    a3.defaults = 0;
                }
                if (a3 != null) {
                    notificationManager.notify(a, a3);
                }
                if (!(a.contains(Integer.valueOf(a)) || cVar.g == 1)) {
                    a.offer(Integer.valueOf(a));
                }
                int b2 = cn.jpush.android.a.b(context);
                if (a.size() > b2) {
                    try {
                        a(context, b2);
                    } catch (Exception e) {
                        z.e();
                    }
                }
                if (1 != cVar.g) {
                    g.a(cVar.c, 1018, context);
                }
                a(context, hashMap, a, a2, packageName, cVar);
            }
        }
    }
}
