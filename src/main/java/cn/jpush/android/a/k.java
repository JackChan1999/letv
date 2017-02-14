package cn.jpush.android.a;

import android.net.wifi.ScanResult;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import org.json.JSONObject;

public final class k implements Comparable<k> {
    private static final String[] z;
    public final String a;
    public final int b;
    public final String c;
    final /* synthetic */ j d;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Q\u0002'\u0015";
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
            case 0: goto L_0x0071;
            case 1: goto L_0x0074;
            case 2: goto L_0x0077;
            case 3: goto L_0x007a;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
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
            case 4: goto L_0x0064;
            case 5: goto L_0x006c;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "Q\u0018)\u001f\u0007N.=\u0005\u0014G\u001f)\u0005\u000e";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "C\u0016+";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "O\u0010-.\u0007F\u0015<\u0014\u0015Q";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u000eQ=\u0002\u000fFLi";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "u\u0018(\u0018/L\u0017!\n\u0004Q\u0002'\u0015[\u0005";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u000eQ*3\u000b\u001f";
        r0 = 5;
        r3 = r4;
        goto L_0x0008;
    L_0x006c:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0071:
        r9 = 34;
        goto L_0x001f;
    L_0x0074:
        r9 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x001f;
    L_0x0077:
        r9 = 78;
        goto L_0x001f;
    L_0x007a:
        r9 = 113; // 0x71 float:1.58E-43 double:5.6E-322;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.k.<clinit>():void");
    }

    public k(j jVar, ScanResult scanResult) {
        this.d = jVar;
        this.a = scanResult.BSSID;
        this.b = scanResult.level;
        this.c = ai.c(scanResult.SSID);
    }

    public k(j jVar, String str, int i, String str2) {
        this.d = jVar;
        this.a = str;
        this.b = i;
        this.c = ai.c(str2);
    }

    public final JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[3], this.a);
            jSONObject.put(z[1], this.b);
            jSONObject.put(z[0], this.c);
            jSONObject.put(z[2], 0);
        } catch (Exception e) {
            z.i();
        }
        return jSONObject;
    }

    public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return ((k) obj).b - this.b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof k) {
            k kVar = (k) obj;
            if (kVar.b == this.b && kVar.a.equals(this.a)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.b ^ this.a.hashCode();
    }

    public final String toString() {
        return new StringBuilder(z[5]).append(this.a).append('\'').append(z[6]).append(this.b).append(z[4]).append(this.c).append('\'').append('}').toString();
    }
}
