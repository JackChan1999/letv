package cn.jpush.android.api;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import java.util.HashMap;

public final class k {
    public static boolean a = false;
    private static boolean b = true;
    private static String c;
    private static HashMap<String, Integer> d = new HashMap();
    private static String e = null;
    private static String f = null;
    private static String g = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r3 = 1;
        r2 = 0;
        r7 = 0;
        b = r3;
        a = r2;
        r0 = "证圻情毺丐wp\u0019\\L_g\u0014皱UXA\bFO[vE\u001c咶Y}=TOEvE\u001c皾Ef\u001dPH\u001e:呣谶甒盎兠纲讔斃泣）'eOE{$[NSa\u000bTYS=\u0002[hS`\u0018X_\u001e:M咹\u001a|C\u0018FR}\u0019PHPr\u000eP\u0014Y}=TOEvE\u001c";
        r0 = r0.toCharArray();
        r1 = r0.length;
        if (r1 > r3) goto L_0x0039;
    L_0x0011:
        r3 = r0;
        r4 = r2;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0016:
        r6 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x002b;
            case 1: goto L_0x002e;
            case 2: goto L_0x0031;
            case 3: goto L_0x0034;
            default: goto L_0x001d;
        };
    L_0x001d:
        r5 = 58;
    L_0x001f:
        r5 = r5 ^ r6;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0037;
    L_0x0027:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0016;
    L_0x002b:
        r5 = 54;
        goto L_0x001f;
    L_0x002e:
        r5 = 19;
        goto L_0x001f;
    L_0x0031:
        r5 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x001f;
    L_0x0034:
        r5 = 53;
        goto L_0x001f;
    L_0x0037:
        r1 = r0;
        r0 = r3;
    L_0x0039:
        if (r1 > r2) goto L_0x0011;
    L_0x003b:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        c = r0;
        r0 = new java.util.HashMap;
        r0.<init>();
        d = r0;
        e = r7;
        f = r7;
        g = r7;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.api.k.<clinit>():void");
    }

    public static void a(Application application) {
        ActivityLifecycleCallbacks lVar = new l();
        application.unregisterActivityLifecycleCallbacks(lVar);
        application.registerActivityLifecycleCallbacks(lVar);
    }
}
