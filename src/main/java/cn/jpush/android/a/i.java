package cn.jpush.android.a;

import android.content.Context;
import cn.jpush.android.a;
import cn.jpush.android.util.ac;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class i extends d {
    private static final String[] B;
    private Context e;
    private String f;
    private boolean g;
    private boolean h;
    private boolean i;
    private String j = null;
    private String k = null;
    private String l = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 18;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "irgf4`qh";
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
            case 0: goto L_0x00e8;
            case 1: goto L_0x00eb;
            case 2: goto L_0x00ef;
            case 3: goto L_0x00f2;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 87;
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
            case 4: goto L_0x0065;
            case 5: goto L_0x006d;
            case 6: goto L_0x0075;
            case 7: goto L_0x007e;
            case 8: goto L_0x0088;
            case 9: goto L_0x0094;
            case 10: goto L_0x009f;
            case 11: goto L_0x00ab;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00c1;
            case 14: goto L_0x00cc;
            case 15: goto L_0x00d7;
            case 16: goto L_0x00e3;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "limT2";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "irgf0un";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "bmw";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "bmwx3aoaJ$?";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "qdt\\";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        r2 = 6;
        r1 = "RtbPwixj^#m'$";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006d:
        r3[r2] = r1;
        r2 = 7;
        r1 = "kxpN8wv[M.ux";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0075:
        r3[r2] = r1;
        r2 = 8;
        r1 = "irgf l{m";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007e:
        r3[r2] = r1;
        r2 = 9;
        r1 = "fxhU";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0088:
        r3[r2] = r1;
        r2 = 10;
        r1 = "rtbP";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "fxhU\bqrs\\%v";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009f:
        r3[r2] = r1;
        r2 = 12;
        r1 = "rtbP\bqrs\\%v";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ab:
        r3[r2] = r1;
        r2 = 13;
        r1 = "dqh";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b6:
        r3[r2] = r1;
        r2 = 14;
        r1 = "IrgX#lrj\u0003w";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c1:
        r3[r2] = r1;
        r2 = 15;
        r1 = "irgX;ZyjJ";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cc:
        r3[r2] = r1;
        r2 = 16;
        r1 = "irgf>k{k";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        r2 = 17;
        r1 = "vnm]";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e3:
        r3[r2] = r1;
        B = r4;
        return;
    L_0x00e8:
        r9 = 5;
        goto L_0x0020;
    L_0x00eb:
        r9 = 29;
        goto L_0x0020;
    L_0x00ef:
        r9 = 4;
        goto L_0x0020;
    L_0x00f2:
        r9 = 57;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.i.<clinit>():void");
    }

    public i(Context context, boolean z, String str, boolean z2, boolean z3) {
        super(context, str, z2, z3);
        this.e = context;
        this.f = str;
        this.g = z2;
        this.h = z3;
        this.i = z;
    }

    private boolean a(JSONArray jSONArray, JSONArray jSONArray2, String str) {
        if (ai.a(str)) {
            if (!ai.a(this.l)) {
                return false;
            }
        } else if (!str.equals(this.l)) {
            return false;
        }
        if (ai.a(this.k)) {
            if (!(jSONArray2 == null || jSONArray2.length() == 0)) {
                return false;
            }
        } else if (jSONArray2 == null) {
            return false;
        } else {
            if (jSONArray2.length() == 0) {
                return false;
            }
            if (!this.k.equals(jSONArray2.toString())) {
                return false;
            }
            z.c();
        }
        if (ai.a(this.j)) {
            if (!(jSONArray == null || jSONArray.length() == 0)) {
                return false;
            }
        } else if (jSONArray == null) {
            return false;
        } else {
            if (jSONArray.length() == 0) {
                return false;
            }
            try {
                String optString = ((JSONObject) jSONArray.get(0)).optString(B[17]);
                if (!(ai.a(optString) || optString.equals(this.j))) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public final void d() {
        try {
            if (this.i) {
                JSONObject a;
                if (this.f.equals(B[11])) {
                    if (a.u()) {
                        a = cn.jpush.android.util.a.a(B[0], b());
                        if (a != null && a.length() > 0) {
                            ac.a(this.e, a);
                            new StringBuilder(B[14]).append(a);
                            z.c();
                        }
                    }
                } else if (this.f.equals(B[12])) {
                    if (a.u()) {
                        a = cn.jpush.android.util.a.a(B[8], c());
                        if (a != null && a.length() > 0) {
                            ac.a(this.e, a);
                            new StringBuilder(B[6]).append(a.toString().getBytes().length);
                            z.c();
                            new StringBuilder(B[14]).append(a);
                            z.c();
                        }
                    }
                } else if (this.f.equals(B[3])) {
                    if (a.u()) {
                        String a2 = a();
                        if (!(a2 == null || "".equals(a2))) {
                            try {
                                JSONObject jSONObject = new JSONObject(a2);
                                r0 = new JSONArray();
                                r0.put(jSONObject);
                                a = cn.jpush.android.util.a.a(B[2], r0);
                                if (a != null && a.length() > 0) {
                                    ac.a(this.e, a);
                                    new StringBuilder(B[14]).append(a);
                                    z.c();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                                z.e();
                            }
                        }
                    }
                } else if (this.f.equals(B[13]) && a.u()) {
                    JSONArray c = c();
                    JSONArray b = b();
                    r0 = new JSONArray();
                    String a3 = a();
                    new StringBuilder(B[4]).append(a3);
                    z.b();
                    if (a(c, b, a3)) {
                        z.c();
                    } else {
                        JSONArray jSONArray;
                        if (a3 == null || "".equals(a3)) {
                            jSONArray = r0;
                        } else {
                            try {
                                r0.put(new JSONObject(a3));
                                jSONArray = r0;
                            } catch (Exception e2) {
                                jSONArray = null;
                            }
                        }
                        JSONObject jSONObject2 = new JSONObject();
                        try {
                            jSONObject2.put(B[5], B[16]);
                            jSONObject2.put(B[1], a.j());
                            jSONObject2.put(B[7], cn.jpush.android.util.a.c(this.e));
                            jSONObject2.put(B[15], cn.jpush.android.util.a.d());
                            if (c != null && c.length() > 0) {
                                jSONObject2.put(B[10], c);
                                this.j = ((JSONObject) c.get(0)).optString(B[17]);
                            }
                            if (b != null && b.length() > 0) {
                                jSONObject2.put(B[9], b);
                                this.k = b.toString();
                            }
                            if (jSONArray != null && jSONArray.length() > 0) {
                                jSONObject2.put(B[3], jSONArray);
                                this.l = a3;
                            }
                            ac.a(this.e, jSONObject2);
                        } catch (JSONException e3) {
                        }
                    }
                }
                g();
            }
        } catch (Exception e4) {
            z.i();
        } finally {
            g();
        }
    }
}
