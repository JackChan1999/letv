package cn.jpush.android.data;

import cn.jpush.android.util.i;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public final class l implements Serializable {
    private static final String[] z;
    public String a;
    public String b;
    public String c;
    public String d;
    public boolean e;
    public String f;
    public String g;
    public String h;
    public boolean i;
    public String j;
    public ArrayList<String> k = new ArrayList();
    public String l;
    public String m;
    public String n;
    final /* synthetic */ i o;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 14;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "(\u0017hU\b\u0012.d@\u0007";
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
            case 0: goto L_0x00b8;
            case 1: goto L_0x00bc;
            case 2: goto L_0x00c0;
            case 3: goto L_0x00c3;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
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
            case 9: goto L_0x0092;
            case 10: goto L_0x009d;
            case 11: goto L_0x00a8;
            case 12: goto L_0x00b3;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "(\t`V?\u0016\u0019`d\u000e\u0003\u0016";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "(\u0017f[\u0001'\u001fq\\";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0016!qM\u001f\u0012";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0016!vW\u0000\u0005\u001b";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0016!wQ\u001c";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0016!sQ\u001d";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0016!q]\u001b\u001b\u001b";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\u0016!lY\u000e\u0010\u001bZA\u001d\u001b";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "\u0016!v]\u0015\u0012";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\u0016!`A\u001d\u001b";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\u0016!lW\u0000\u0019!pF\u0003";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0016!lZ\t\u0018";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u0016!`F\n\u0004";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x00b8:
        r9 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x0020;
    L_0x00bc:
        r9 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        goto L_0x0020;
    L_0x00c0:
        r9 = 5;
        goto L_0x0020;
    L_0x00c3:
        r9 = 52;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.l.<clinit>():void");
    }

    public l(i iVar) {
        this.o = iVar;
    }

    private JSONObject a() {
        int i = 0;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(z[7], this.a);
            jSONObject.put(z[11], this.b);
            jSONObject.put(z[6], this.c);
            jSONObject.put(z[3], this.d);
            jSONObject.put(z[4], this.e ? 0 : 1);
            jSONObject.put(z[9], this.f);
            jSONObject.put(z[12], this.g);
            jSONObject.put(z[8], this.h);
            jSONObject.put(z[10], this.j);
            String str = z[5];
            if (!this.i) {
                i = 1;
            }
            jSONObject.put(str, i);
            jSONObject.put(z[13], i.a(this.k));
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    public final String toString() {
        JSONObject a = a();
        try {
            a.put(z[2], this.l);
            a.put(z[0], this.m);
            a.put(z[1], this.n);
        } catch (JSONException e) {
        }
        return a.toString();
    }
}
