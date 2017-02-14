package cn.jpush.android.api;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.content.Context;
import cn.jpush.android.e;

public class BasicPushNotificationBuilder extends DefaultPushNotificationBuilder {
    private static final String[] z;
    protected Context a;
    public String developerArg0 = z[2];
    public int notificationDefaults = -1;
    public int notificationFlags = 16;
    public int statusBarDrawable = e.b;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u000e5\u0005G\u000e3\u000b)q2";
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
        r9 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
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
        r1 = "3\u000b)q2";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\b1\u0000K\u0001\u0003$\u0013\\,\u001e3F";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\"\u0001:bM\u000f;\u0018Z\b\u0014 ";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u000f!\u0005Z\u0002\u0001";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u000e5\u0005G\u000e";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0063:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0068:
        r9 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        goto L_0x001f;
    L_0x006b:
        r9 = 84;
        goto L_0x001f;
    L_0x006e:
        r9 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        goto L_0x001f;
    L_0x0071:
        r9 = 46;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.BasicPushNotificationBuilder.<clinit>():void");
    }

    public BasicPushNotificationBuilder(Context context) {
        if (context == null) {
            throw new IllegalArgumentException(z[3]);
        }
        this.a = context;
    }

    static PushNotificationBuilder a(String str) {
        String[] split = str.split(z[1]);
        Object obj = split[0];
        PushNotificationBuilder basicPushNotificationBuilder = z[5].equals(obj) ? new BasicPushNotificationBuilder(e.e) : z[4].equals(obj) ? new CustomPushNotificationBuilder(e.e) : new BasicPushNotificationBuilder(e.e);
        basicPushNotificationBuilder.a(split);
        return basicPushNotificationBuilder;
    }

    @TargetApi(16)
    final Notification a(Builder builder) {
        builder.setDefaults(this.notificationDefaults);
        builder.setSmallIcon(this.statusBarDrawable);
        Notification build = builder.build();
        build.flags = this.notificationFlags;
        return build;
    }

    public final String a() {
        return this.developerArg0;
    }

    final void a(Notification notification) {
        notification.defaults = this.notificationDefaults;
        notification.flags = this.notificationFlags;
        notification.icon = this.statusBarDrawable;
    }

    void a(String[] strArr) {
        this.notificationDefaults = Integer.parseInt(strArr[1]);
        this.notificationFlags = Integer.parseInt(strArr[2]);
        this.statusBarDrawable = Integer.parseInt(strArr[3]);
        this.developerArg0 = strArr[4];
    }

    String b() {
        return this.notificationDefaults + z[1] + this.notificationFlags + z[1] + this.statusBarDrawable + z[1] + this.developerArg0;
    }

    public String toString() {
        return new StringBuilder(z[0]).append(b()).toString();
    }
}
