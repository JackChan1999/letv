package cn.jpush.android.api;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;

final class l implements ActivityLifecycleCallbacks {
    private static final String[] z;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 4;
        r5 = new java.lang.String[r0];
        r4 = " =f\n((7,\u0011)56l\fi 0v\u0011(/}O9\u000e\u000f";
        r0 = -1;
        r6 = r5;
        r7 = r5;
        r5 = r1;
    L_0x000c:
        r4 = r4.toCharArray();
        r8 = r4.length;
        if (r8 > r2) goto L_0x006a;
    L_0x0013:
        r9 = r1;
    L_0x0014:
        r10 = r4;
        r11 = r9;
        r14 = r8;
        r8 = r4;
        r4 = r14;
    L_0x0019:
        r13 = r8[r9];
        r12 = r11 % 5;
        switch(r12) {
            case 0: goto L_0x005f;
            case 1: goto L_0x0062;
            case 2: goto L_0x0065;
            case 3: goto L_0x0067;
            default: goto L_0x0020;
        };
    L_0x0020:
        r12 = 71;
    L_0x0022:
        r12 = r12 ^ r13;
        r12 = (char) r12;
        r8[r9] = r12;
        r9 = r11 + 1;
        if (r4 != 0) goto L_0x002e;
    L_0x002a:
        r8 = r10;
        r11 = r9;
        r9 = r4;
        goto L_0x0019;
    L_0x002e:
        r8 = r4;
        r4 = r10;
    L_0x0030:
        if (r8 > r9) goto L_0x0014;
    L_0x0032:
        r8 = new java.lang.String;
        r8.<init>(r4);
        r4 = r8.intern();
        switch(r0) {
            case 0: goto L_0x0047;
            case 1: goto L_0x0050;
            case 2: goto L_0x005a;
            default: goto L_0x003e;
        };
    L_0x003e:
        r6[r5] = r4;
        r0 = ")2q\u0016(5sK\u00163$=vV\u0006\u0002\u0007K7\t\u001e\u001eC1\ta2l\u001cg\b=v\u001d)5}A9\u0013\u0004\u0014M*\u001e\u001e\u001fC-\t\u0002\u001bG*";
        r4 = r0;
        r5 = r2;
        r6 = r7;
        r0 = r1;
        goto L_0x000c;
    L_0x0047:
        r6[r5] = r4;
        r0 = " =f\n((7,\u0011)56l\fi\"2v\u001d .!{V\u000b\u0000\u0006L;\u000f\u0004\u0001";
        r4 = r0;
        r5 = r3;
        r6 = r7;
        r0 = r2;
        goto L_0x000c;
    L_0x0050:
        r6[r5] = r4;
        r4 = 3;
        r0 = "\r:d\u001d\u000480n\u001d\u0004 ?n\u001a&\"8q";
        r5 = r4;
        r6 = r7;
        r4 = r0;
        r0 = r3;
        goto L_0x000c;
    L_0x005a:
        r6[r5] = r4;
        z = r7;
        return;
    L_0x005f:
        r12 = 65;
        goto L_0x0022;
    L_0x0062:
        r12 = 83;
        goto L_0x0022;
    L_0x0065:
        r12 = r3;
        goto L_0x0022;
    L_0x0067:
        r12 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x0022;
    L_0x006a:
        r9 = r1;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.l.<clinit>():void");
    }

    l() {
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
        k.f = activity.getClass().getName();
        if (ai.a(k.e)) {
            k.e = activity.getClass().getName();
        }
        if (VERSION.SDK_INT >= 14 && k.a) {
            if (!e.a && e.b().a()) {
                k.d.put(k.e, Integer.valueOf(0));
                if (!ai.a(k.g) && k.g.equals(k.e)) {
                    a.b(activity, k.c, k.e, 0);
                }
            }
            if (activity instanceof TabActivity) {
                z.d();
            } else {
                e.a = false;
            }
        }
    }

    public final void onActivityResumed(Activity activity) {
        k.e = activity.getClass().getName();
        if (VERSION.SDK_INT >= 14 && k.a) {
            if (k.b) {
                Intent intent = new Intent(z[0]);
                intent.setPackage(activity.getPackageName());
                intent.addCategory(z[2]);
                ResolveInfo resolveActivity = activity.getPackageManager().resolveActivity(intent, 0);
                if (resolveActivity == null) {
                    z.d(z[3], z[1]);
                    return;
                }
                k.g = resolveActivity.activityInfo.name;
                k.b = false;
                return;
            }
            if (!(e.b || !e.b().a() || k.f == null)) {
                if (k.d.containsKey(k.f)) {
                    k.d.put(k.f, Integer.valueOf(2));
                    if (!ai.a(k.g) && k.g.equals(k.f)) {
                        a.b(activity, k.c, k.f, 2);
                    }
                } else {
                    k.d.put(k.f, Integer.valueOf(1));
                    if (!ai.a(k.g) && k.g.equals(k.f)) {
                        a.b(activity, k.c, k.f, 1);
                    }
                }
            }
            if (activity instanceof TabActivity) {
                z.d();
            } else {
                e.b = false;
            }
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }
}
