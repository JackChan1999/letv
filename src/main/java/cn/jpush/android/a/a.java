package cn.jpush.android.a;

import cn.jpush.android.util.z;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    private static final String z;
    private int a;
    private int b;
    private int c;
    private int d;
    private String e;
    private double f;
    private double g;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r6 = 1;
        r0 = "\u0006ZbNI\u00111e\t\u001fX\u001c-\tH\u0012\u001ahG@>\u0017tEQ\u000f\u0001BDA\u0018Z;\u000eAQZmDF\u001c\fhDK<\ndJf\u0012\u001cd\t\u001fX\u001c-\tH\u0012\u001ahG@3\u001du\\J\u000f\u0013BDA\u0018Z;\u000eAQZsJA\u0014\u0017URU\u0018Z;\t\u0000\u000eZ-\tI\u001c\f#\u0011\u0000\u001bT#GK\u001aZ;\u000eC\u0000";
        r0 = r0.toCharArray();
        r1 = r0.length;
        r2 = 0;
        if (r1 > r6) goto L_0x0027;
    L_0x000b:
        r3 = r0;
        r4 = r2;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0010:
        r7 = r1[r2];
        r5 = r4 % 5;
        switch(r5) {
            case 0: goto L_0x0035;
            case 1: goto L_0x0038;
            case 2: goto L_0x003b;
            case 3: goto L_0x003d;
            default: goto L_0x0017;
        };
    L_0x0017:
        r5 = 37;
    L_0x0019:
        r5 = r5 ^ r7;
        r5 = (char) r5;
        r1[r2] = r5;
        r2 = r4 + 1;
        if (r0 != 0) goto L_0x0025;
    L_0x0021:
        r1 = r3;
        r4 = r2;
        r2 = r0;
        goto L_0x0010;
    L_0x0025:
        r1 = r0;
        r0 = r3;
    L_0x0027:
        if (r1 > r2) goto L_0x000b;
    L_0x0029:
        r1 = new java.lang.String;
        r1.<init>(r0);
        r0 = r1.intern();
        z = r0;
        return;
    L_0x0035:
        r5 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        goto L_0x0019;
    L_0x0038:
        r5 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x0019;
    L_0x003b:
        r5 = r6;
        goto L_0x0019;
    L_0x003d:
        r5 = 43;
        goto L_0x0019;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.a.<clinit>():void");
    }

    public final int a() {
        return this.a;
    }

    public final void a(int i) {
        this.a = i;
    }

    public final void a(String str) {
        this.e = str;
    }

    public final JSONArray b() {
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray.put(new JSONObject(toString()));
            return jSONArray;
        } catch (JSONException e) {
            a.class.getSimpleName();
            e.getMessage();
            z.e();
            return null;
        }
    }

    public final void b(int i) {
        this.b = i;
    }

    public final void c(int i) {
        this.c = i;
    }

    public final void d(int i) {
        this.d = i;
    }

    public String toString() {
        try {
            return String.format(z, new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.d), Integer.valueOf(this.c), this.e, Double.valueOf(this.f), Double.valueOf(this.g)});
        } catch (Exception e) {
            return "";
        }
    }
}
