package cn.jpush.android.data;

import cn.jpush.android.util.ai;
import org.json.JSONException;
import org.json.JSONObject;

public final class e {
    private static final String[] z;
    public int a;
    public String b;
    public String c;
    public String d;
    public String e;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 8;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u001c{";
        r0 = -1;
        r4 = r3;
    L_0x0009:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
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
            case 0: goto L_0x0078;
            case 1: goto L_0x007b;
            case 2: goto L_0x007e;
            case 3: goto L_0x0081;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 1;
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
        goto L_0x0017;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0012;
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
            case 5: goto L_0x006b;
            case 6: goto L_0x0073;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "c=r(6";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "`5";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "4th";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = ",py-m3a}";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = ",py-m";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0063:
        r3[r2] = r1;
        r2 = 6;
        r1 = "-fh";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006b:
        r3[r2] = r1;
        r2 = 7;
        r1 = "4|b-";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0073:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0078:
        r9 = 64;
        goto L_0x001f;
    L_0x007b:
        r9 = 21;
        goto L_0x001f;
    L_0x007e:
        r9 = 15;
        goto L_0x001f;
    L_0x0081:
        r9 = 72;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.e.<clinit>():void");
    }

    public e(int i, String str, String str2, String str3, String str4) {
        this.a = i;
        this.e = str;
        this.c = str3;
        this.b = str2;
        this.d = str4;
    }

    public final int a() {
        return toString().getBytes().length;
    }

    public final JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        Object obj = (ai.a(this.d) || ai.a(this.e) || ai.a(this.b) || ai.a(this.c)) ? null : 1;
        if (obj == null) {
            return null;
        }
        try {
            jSONObject.put(z[5], this.a);
            jSONObject.put(z[4], this.e);
            jSONObject.put(z[7], this.d);
            jSONObject.put(z[3], this.b);
            jSONObject.put(z[6], this.c);
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final String toString() {
        if (this.c != null && this.c.contains(z[0])) {
            this.c.replaceAll(z[0], z[1]);
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.a).append(z[2]);
        stringBuffer.append(this.e).append(z[2]);
        stringBuffer.append(this.d).append(z[2]);
        stringBuffer.append(this.b).append(z[2]);
        stringBuffer.append(this.c).append(z[2]);
        return stringBuffer.toString();
    }
}
