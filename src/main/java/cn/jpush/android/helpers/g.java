package cn.jpush.android.helpers;

import android.content.Context;
import cn.jpush.android.a;
import cn.jpush.android.a.i;
import cn.jpush.android.service.r;
import cn.jpush.android.util.ac;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.s;
import cn.jpush.android.util.z;
import com.letv.pp.utils.NetworkUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class g {
    private static int a = 20480;
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 16;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "h\u0001\u0006/*gX\u0000#5f\u0010\u0006\t5l\u0010\u00132,f\fRkej\r\u001c2 g\u0016H";
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
            case 0: goto L_0x00d6;
            case 1: goto L_0x00da;
            case 2: goto L_0x00de;
            case 3: goto L_0x00e2;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 69;
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
            case 3: goto L_0x005d;
            case 4: goto L_0x0066;
            case 5: goto L_0x006e;
            case 6: goto L_0x0076;
            case 7: goto L_0x007f;
            case 8: goto L_0x0089;
            case 9: goto L_0x0094;
            case 10: goto L_0x00a0;
            case 11: goto L_0x00ab;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00c2;
            case 14: goto L_0x00cd;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "G7>\nej\r\u001c2 q\u0016";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "%B\u0016'1hB\u0001/?lXRke";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "h\u0012\u0002\u0019)`\u0011\u0006";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "y\u0003\u0011-$n\u0007R/+o\rR2*}\u0003\u001ef+|\u000fRke";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "r@\u0006)1h\u000eP|`mNP6$n\u0007P|`mNP5 g\u0006\u00174,m@Hd`z@^d0`\u0006P|`zNP6 {\u000f\u001b56`\r\u001c\u0019)`\u0011\u0006d,\u0011\u000f";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0066:
        r3[r2] = r1;
        r2 = 6;
        r1 = "%@";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006e:
        r3[r2] = r1;
        r2 = 7;
        r1 = "d\u0011\u0015\u00196}\u0003\u000636";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "`\u0016\u001b+ ";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007f:
        r3[r2] = r1;
        r2 = 9;
        r1 = ")\u0010\u00176*{\u0016R%*g\u0016\u0017(13B";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0089:
        r3[r2] = r1;
        r2 = 10;
        r1 = "%B\u0011)!lXR";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0094:
        r3[r2] = r1;
        r2 = 11;
        r1 = "}\u001b\u0002#";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a0:
        r3[r2] = r1;
        r2 = 12;
        r1 = "m\u0003\u0006'";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ab:
        r3[r2] = r1;
        r2 = 13;
        r1 = "h\u0001\u0006/*gX\u0000#5f\u0010\u0006\u0007&}\u000b\u001d(\u0017l\u0011\u0007*1)OR+ z\u0011\u0013! @\u0006Hf";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b6:
        r3[r2] = r1;
        r2 = 14;
        r1 = "{\u0007\u00013)}";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c2:
        r3[r2] = r1;
        r2 = 15;
        r1 = "d\u0011\u0015\u0019,m";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cd:
        r3[r2] = r1;
        z = r4;
        r0 = 20480; // 0x5000 float:2.8699E-41 double:1.01185E-319;
        a = r0;
        return;
    L_0x00d6:
        r9 = 9;
        goto L_0x0020;
    L_0x00da:
        r9 = 98;
        goto L_0x0020;
    L_0x00de:
        r9 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        goto L_0x0020;
    L_0x00e2:
        r9 = 70;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.g.<clinit>():void");
    }

    public static void a(Context context) {
        if (!a.u()) {
            z.d();
        } else if (context == null) {
            z.d();
        } else {
            JSONArray l = cn.jpush.android.util.a.l(context);
            if (l != null && l.length() != 0) {
                int length = l.length();
                int length2 = l.toString().length();
                new StringBuilder(z[4]).append(length).append(z[2]).append(length2);
                z.c();
                JSONObject a;
                if (length2 <= a) {
                    a = cn.jpush.android.util.a.a(z[3], l);
                    if (a != null && a.length() > 0) {
                        ac.a(context, a);
                        return;
                    }
                    return;
                }
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                while (i < length) {
                    try {
                        jSONArray.put(l.getJSONObject(i));
                    } catch (JSONException e) {
                        z.e();
                    }
                    if (jSONArray.toString().length() > a || length - 1 == i) {
                        a = cn.jpush.android.util.a.a(z[3], jSONArray);
                        if (a != null && a.length() > 0) {
                            ac.a(context, a);
                        }
                        jSONArray = new JSONArray();
                    }
                    i++;
                }
            }
        }
    }

    public static void a(Context context, JSONObject jSONObject) {
        if (!a.u()) {
            return;
        }
        if (context == null) {
            throw new IllegalArgumentException(z[1]);
        } else if (jSONObject != null && jSONObject.length() > 0) {
            ac.a(context, jSONObject);
            new StringBuilder(z[0]).append(jSONObject.toString());
            z.b();
        }
    }

    public static void a(Context context, boolean z, String str, boolean z2, boolean z3) {
        z.b();
        new i(context, true, str, z2, z3).f();
    }

    public static void a(String str, int i, Context context) {
        a(str, i, null, context);
    }

    public static void a(String str, int i, String str2, Context context) {
        if (!a.u()) {
            z.a();
        } else if (context == null) {
            z.b();
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(new StringBuilder(z[13]).append(str).append(z[10]).append(i).append(NetworkUtils.DELIMITER_LINE).append(r.b(i)).toString());
            if (!ai.a(str2)) {
                stringBuffer.append(new StringBuilder(z[9]).append(str2).toString());
            }
            z.b();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(z[15], str);
                jSONObject.put(z[14], i);
                if (!ai.a(str2)) {
                    jSONObject.put(z[12], str2);
                }
                jSONObject.put(z[8], a.j());
                jSONObject.put(z[11], z[7]);
                ac.a(context, jSONObject);
            } catch (JSONException e) {
            }
        }
    }

    public static void b(Context context) {
        z.a();
        String[] a = s.a(context);
        if (a == null || a.length == 0) {
            z.e();
            return;
        }
        int length = a.length;
        String str = "[";
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            String str2 = a[i];
            str = i2 == 0 ? str + "\"" + str2 + "\"" : str + z[6] + str2 + "\"";
            int i4 = i + 1;
            i = i2 + 1;
            if (i >= 50 || str.length() > 1000 || i4 == length) {
                String str3 = str + "]";
                str3 = String.format(z[5], new Object[]{Integer.valueOf(length), Integer.valueOf(i3), a.A(), Long.valueOf(a.t()), str3});
                z.b();
                ac.a(context, cn.jpush.android.util.a.a(z[3], str3));
                str = "[";
                i3++;
                i = 0;
            }
            i2 = i;
            i = i4;
        }
    }
}
