package cn.jpush.android.api;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.os.Build.VERSION;
import android.widget.RemoteViews;
import cn.jpush.android.e;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import java.util.Map;

public class DefaultPushNotificationBuilder implements PushNotificationBuilder {
    private static final String[] z;
    protected String b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "wl\u0017+V_}!?P[G\u001e>JU`\u0012+WZf\u001f\bVZe\u0015/Q";
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
            case 0: goto L_0x006a;
            case 1: goto L_0x006d;
            case 2: goto L_0x0070;
            case 3: goto L_0x0073;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 35;
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
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005d;
            case 4: goto L_0x0065;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "Pg_ SFz\u0019dB]m\u0003%JW'?\u0005wzO8\tbg@>\u0004|pF?\u001ef}].\u001ejgE4";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "}fQ$LG`\u0017#@R}\u0018%M\u0013j\u001e$WVg\u0005jW\\)\u0002\"LD'Q\rJElQ?S\u001d";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "Uh\u0018&FW)\u0005%\u0003Tl\u0005jBCy\u001d#@R}\u0018%M\u0013`\u001f,L\u0013h\u001f.\u0003Zj\u001e$\r";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "Yy\u00049Klg\u001e>JU`\u0012+WZf\u001f\u0015JPf\u001f";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "W{\u0010=BQe\u0014";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x006a:
        r9 = 51;
        goto L_0x0020;
    L_0x006d:
        r9 = 9;
        goto L_0x0020;
    L_0x0070:
        r9 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x0020;
    L_0x0073:
        r9 = 74;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.DefaultPushNotificationBuilder.<clinit>():void");
    }

    @TargetApi(16)
    Notification a(Builder builder) {
        return builder.build();
    }

    public final Notification a(String str, Map<String, String> map) {
        if (ai.a(str)) {
            z.d(z[0], z[2]);
            return null;
        }
        if (map.containsKey(z[1])) {
            this.b = (String) map.get(z[1]);
        }
        if (this.b == null) {
            this.b = e.d;
        }
        if (VERSION.SDK_INT < 16) {
            Notification notification = new Notification(e.b, str, System.currentTimeMillis());
            a(notification);
            if (this.b == null) {
                this.b = e.d;
            }
            RemoteViews b = b(str);
            if (b != null) {
                notification.contentView = b;
                return notification;
            }
            m.a(notification, e.e, this.b, str, null);
            return notification;
        } else if (e.e != null) {
            int identifier = e.e.getResources().getIdentifier(z[4], z[5], e.e.getPackageName());
            if (identifier == 0) {
                if (e.b != 0) {
                    identifier = e.b;
                } else {
                    try {
                        identifier = e.e.getPackageManager().getApplicationInfo(e.e.getPackageName(), 0).icon;
                        z.c();
                    } catch (Throwable e) {
                        z.b(z[0], z[3], e);
                        return null;
                    }
                }
            }
            Builder smallIcon = new Builder(e.e).setContentTitle(this.b).setContentText(str).setTicker(str).setSmallIcon(identifier);
            RemoteViews b2 = b(str);
            if (b2 != null) {
                smallIcon.setContent(b2);
            } else {
                z.c();
            }
            return a(smallIcon);
        } else {
            z.d();
            return null;
        }
    }

    public String a() {
        return null;
    }

    void a(Notification notification) {
    }

    RemoteViews b(String str) {
        return null;
    }
}
