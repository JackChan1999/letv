package cn.jpush.android.data;

import android.content.Context;
import cn.jpush.android.helpers.d;
import cn.jpush.android.util.i;
import cn.jpush.android.util.z;
import java.util.ArrayList;
import org.json.JSONObject;

public final class m extends c {
    private static final String[] M;
    public String E;
    public int F;
    public int G;
    public int H;
    public ArrayList<String> I;
    public String J;
    public String K;
    public String L;
    public String a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 10;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0015Nztb";
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
            case 0: goto L_0x008c;
            case 1: goto L_0x008f;
            case 2: goto L_0x0092;
            case 3: goto L_0x0095;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 14;
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
            case 7: goto L_0x007d;
            case 8: goto L_0x0087;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0015Njtk\u0003";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0004~Ph{\u001d";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0015N{oz\u001ct";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0016c`kQ\u001edb";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0015Nesc\u0000Nbij\u0015";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0015N}om\u0018N{~\u0015";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0018e{v4_>";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "1uk&f\u0004e&z\u001f1ai`]a}ch\u0019i/s|\u001c+/";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0015N|na\u0007";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        M = r4;
        return;
    L_0x008c:
        r9 = 112; // 0x70 float:1.57E-43 double:5.53E-322;
        goto L_0x0020;
    L_0x008f:
        r9 = 17;
        goto L_0x0020;
    L_0x0092:
        r9 = 15;
        goto L_0x0020;
    L_0x0095:
        r9 = 6;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.m.<clinit>():void");
    }

    public m() {
        this.I = new ArrayList();
        this.J = "";
        this.K = "";
        this.o = 0;
    }

    public final void a(Context context) {
        z.a();
        new n(this, this, context).start();
    }

    public final boolean a(Context context, JSONObject jSONObject) {
        z.a();
        this.a = jSONObject.optString(M[0], "").trim();
        this.E = jSONObject.optString(M[3], "").trim();
        if (!d.a(this.a)) {
            this.a = new StringBuilder(M[7]).append(this.a).toString();
            new StringBuilder(M[8]).append(this.a);
            z.c();
        }
        this.G = jSONObject.optInt(M[6], 0);
        this.F = jSONObject.optInt(M[5], 0);
        this.H = jSONObject.optInt(M[9], 0);
        if (3 == this.G || 2 == this.G || 1 == this.G) {
            this.I = i.a(jSONObject.optJSONArray(M[1]));
        }
        this.J = jSONObject.optString(M[4], "");
        this.K = jSONObject.optString(M[2], "");
        return true;
    }
}
