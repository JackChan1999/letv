package cn.jpush.android.data;

import android.content.Context;
import cn.jpush.android.api.m;
import cn.jpush.android.helpers.d;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import org.json.JSONObject;

public final class r extends i {
    private static final String[] R;

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
        r4 = 1;
        r1 = 0;
        r0 = 2;
        r3 = new java.lang.String[r0];
        r2 = ".\t\u0006C9'\u0016\u001a";
        r0 = -1;
        r5 = r3;
        r6 = r3;
        r3 = r1;
    L_0x000b:
        r2 = r2.toCharArray();
        r7 = r2.length;
        if (r7 > r4) goto L_0x0057;
    L_0x0012:
        r8 = r1;
    L_0x0013:
        r9 = r2;
        r10 = r8;
        r13 = r7;
        r7 = r2;
        r2 = r13;
    L_0x0018:
        r12 = r7[r8];
        r11 = r10 % 5;
        switch(r11) {
            case 0: goto L_0x004b;
            case 1: goto L_0x004e;
            case 2: goto L_0x0051;
            case 3: goto L_0x0054;
            default: goto L_0x001f;
        };
    L_0x001f:
        r11 = 74;
    L_0x0021:
        r11 = r11 ^ r12;
        r11 = (char) r11;
        r7[r8] = r11;
        r8 = r10 + 1;
        if (r2 != 0) goto L_0x002d;
    L_0x0029:
        r7 = r9;
        r10 = r8;
        r8 = r2;
        goto L_0x0018;
    L_0x002d:
        r7 = r2;
        r2 = r9;
    L_0x002f:
        if (r7 > r8) goto L_0x0013;
    L_0x0031:
        r7 = new java.lang.String;
        r7.<init>(r2);
        r2 = r7.intern();
        switch(r0) {
            case 0: goto L_0x0046;
            default: goto L_0x003d;
        };
    L_0x003d:
        r5[r3] = r2;
        r0 = "\u001a\u0017\u0006r%8\u0017Mo\" \u000eMq%+\u001cM1j";
        r2 = r0;
        r3 = r4;
        r5 = r6;
        r0 = r1;
        goto L_0x000b;
    L_0x0046:
        r5[r3] = r2;
        R = r6;
        return;
    L_0x004b:
        r11 = 79;
        goto L_0x0021;
    L_0x004e:
        r11 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        goto L_0x0021;
    L_0x0051:
        r11 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        goto L_0x0021;
    L_0x0054:
        r11 = 28;
        goto L_0x0021;
    L_0x0057:
        r8 = r1;
        goto L_0x002f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.r.<clinit>():void");
    }

    public r() {
        this.o = 3;
        this.L = null;
    }

    public final void a(Context context) {
        z.a();
        if (d.a(this.G, this.H, context)) {
            ServiceInterface.a(context, (c) this);
        } else if (this.J == 1) {
            context.startActivity(a.a(context, (c) this, true));
        } else if (this.J == 0) {
            m.b(context, (c) this);
        } else {
            new StringBuilder(R[1]).append(this.J);
            z.b();
        }
    }

    public final boolean a(Context context, JSONObject jSONObject) {
        z.a();
        boolean a = super.a(context, jSONObject);
        this.J = jSONObject.optInt(R[0], 0);
        return a;
    }
}
