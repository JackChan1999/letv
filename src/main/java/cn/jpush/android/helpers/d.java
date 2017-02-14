package cn.jpush.android.helpers;

import android.content.Context;
import android.text.TextUtils;
import cn.jpush.android.data.a;
import cn.jpush.android.data.c;
import cn.jpush.android.data.i;
import cn.jpush.android.data.m;
import cn.jpush.android.data.r;
import cn.jpush.android.data.s;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONException;
import org.json.JSONObject;

public final class d {
    private static Queue<cn.jpush.android.data.d> a = new LinkedList();
    private static final String[] z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 27;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "XA=(";
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
            case 0: goto L_0x0153;
            case 1: goto L_0x0157;
            case 2: goto L_0x015b;
            case 3: goto L_0x015f;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 31;
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
            case 0: goto L_0x0045;
            case 1: goto L_0x004d;
            case 2: goto L_0x0056;
            case 3: goto L_0x005e;
            case 4: goto L_0x0067;
            case 5: goto L_0x006f;
            case 6: goto L_0x0077;
            case 7: goto L_0x0080;
            case 8: goto L_0x008a;
            case 9: goto L_0x0095;
            case 10: goto L_0x00a1;
            case 11: goto L_0x00ac;
            case 12: goto L_0x00b7;
            case 13: goto L_0x00c2;
            case 14: goto L_0x00cd;
            case 15: goto L_0x00d8;
            case 16: goto L_0x00e3;
            case 17: goto L_0x00ee;
            case 18: goto L_0x00f9;
            case 19: goto L_0x0104;
            case 20: goto L_0x010f;
            case 21: goto L_0x011b;
            case 22: goto L_0x0126;
            case 23: goto L_0x0131;
            case 24: goto L_0x013c;
            case 25: goto L_0x0147;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "wjB\u0011L~l&";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "XA=5{";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "D\u000b0z]\u0005\u00163?^@\u0016|uJJ\f|yKJ\u000f|jKIB>zZD\u0017/zVCB5qOD\u000e5{\u0019P\u00100?\u0014\u0005";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0056:
        r3[r2] = r1;
        r2 = 4;
        r1 = "Tz\u00013qM@\f(";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005e:
        r3[r2] = r1;
        r2 = 5;
        r1 = "wp.\u0010?ZJ\f(zAQ";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0067:
        r3[r2] = r1;
        r2 = 6;
        r1 = "lK\t2pN\u0005\u000f/x\u0019Q\u001b,z\u0019\bB";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006f:
        r3[r2] = r1;
        r2 = 7;
        r1 = "XF\u00165pW\u001f\u000e3~]h\u0011;UJJ\f\u001amVH7.s\u0019\bB";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0077:
        r3[r2] = r1;
        r2 = 8;
        r1 = "XF\u00165pW\u001f\u0012=mJ@-.v^L\f=stV\u0005\u0011zJV\u0003;z\u0019\bB3mPB\u000b2~Uo\u00113q\u0003/";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x0080:
        r3[r2] = r1;
        r2 = 9;
        r1 = "TV\u0005\u0003v]";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x008a:
        r3[r2] = r1;
        r2 = 10;
        r1 = "JM\r+@M\\\u00129";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0095:
        r3[r2] = r1;
        r2 = 11;
        r1 = "pK\u0014=sPAB)mU\u0005O|";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a1:
        r3[r2] = r1;
        r2 = 12;
        r1 = "g~\n(kIY\n(kIV?w%\u0016\nLv";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ac:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\\]\u0016.~J";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b7:
        r3[r2] = r1;
        r2 = 14;
        r1 = "XF\u00165pW\u001f\u0012.ziD\u0010/zvW\u000b;vWD\u000e\u0011l^h\u0007/lXB\u0007|2\u0019J\u00105xPK\u00030UJJ\ff\u0015";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c2:
        r3[r2] = r1;
        r2 = 15;
        r1 = "Wz\u0000)vUA\u0007.@PA";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cd:
        r3[r2] = r1;
        r2 = 16;
        r1 = "VS\u0007.mPA\u0007\u0003rJB=5{";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d8:
        r3[r2] = r1;
        r2 = 17;
        r1 = "T@\u0011/~^@";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e3:
        r3[r2] = r1;
        r2 = 18;
        r1 = "IW\u0007\f~KV\u0007\u0013mPB\u000b2~Uh\u0011;R\\V\u0011=x\\\u0005\u000f/xpABa?";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ee:
        r3[r2] = r1;
        r2 = 19;
        r1 = "ML\u00160z";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f9:
        r3[r2] = r1;
        r2 = 20;
        r1 = "ZJ\f(zWQ=(fI@";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0104:
        r3[r2] = r1;
        r2 = 21;
        r1 = "WJ\u00165yPF\u00035pWz\u0016%o\\";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010f:
        r3[r2] = r1;
        r2 = 22;
        r1 = "wj=\u0011L~l&";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011b:
        r3[r2] = r1;
        r2 = 23;
        r1 = "Wz\r2s@";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0126:
        r3[r2] = r1;
        r2 = 24;
        r1 = "nl$\u0015";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0131:
        r3[r2] = r1;
        r2 = 25;
        r1 = "lK\t2pNKB\u0011L~\u0005\u0012.pMJ\u00013s\u0019S\u0007.lPJ\fr?~L\u00149?LUBq?";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013c:
        r3[r2] = r1;
        r2 = 26;
        r1 = "lK\t2pN\u0005\u000f/x\u0019Q\u001b,z\u0019D\u0006\u0003k\u0019\u0018B";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x0147:
        r3[r2] = r1;
        z = r4;
        r0 = new java.util.LinkedList;
        r0.<init>();
        a = r0;
        return;
    L_0x0153:
        r9 = 57;
        goto L_0x0020;
    L_0x0157:
        r9 = 37;
        goto L_0x0020;
    L_0x015b:
        r9 = 98;
        goto L_0x0020;
    L_0x015f:
        r9 = 92;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.helpers.d.<clinit>():void");
    }

    public static a a(Context context, String str, String str2, String str3, String str4) {
        new StringBuilder(z[14]).append(str);
        z.b();
        if (context == null) {
            throw new IllegalArgumentException(z[5]);
        } else if (TextUtils.isEmpty(str)) {
            z.e();
            g.a(z[1], 996, context);
            return null;
        } else {
            JSONObject a = a(context, z[22], str);
            if (a == null) {
                z.b();
                return null;
            }
            String optString = a.optString(z[9], "");
            if (!ai.a(optString)) {
                str4 = optString;
            }
            if (ai.a(str4)) {
                str4 = a.optString(z[2], "");
            }
            new StringBuilder(z[18]).append(str4);
            z.b();
            boolean z = a.optInt(z[23], 0) == 1;
            int optInt = z ? a.optInt(z[15], 0) : 0;
            a aVar = new a();
            aVar.c = str4;
            aVar.a = a;
            aVar.b = a.optInt(z[10], 3);
            aVar.e = z;
            aVar.f = optInt;
            aVar.g = a.optInt(z[21], 0);
            aVar.i = a.optString(z[17], "");
            aVar.j = a.optString(z[20], "");
            aVar.k = a.optString(z[19], "");
            aVar.l = a.optString(z[13], "");
            aVar.m = str2;
            aVar.n = str3;
            aVar.d = a.optString(z[16], "");
            return aVar;
        }
    }

    private static JSONObject a(Context context, String str, String str2) {
        try {
            return new JSONObject(str2);
        } catch (JSONException e) {
            z.i();
            g.a(str, 996, context);
            return null;
        }
    }

    public static JSONObject a(Context context, String str, JSONObject jSONObject, String str2) {
        JSONObject jSONObject2 = null;
        if (jSONObject == null) {
            z.d();
            g.a(str, 996, context);
        } else if (TextUtils.isEmpty(str2)) {
            z.d();
        } else {
            try {
                if (!jSONObject.isNull(str2)) {
                    jSONObject2 = jSONObject.getJSONObject(str2);
                }
            } catch (JSONException e) {
                z.i();
                g.a(str, 996, context);
            }
        }
        return jSONObject2;
    }

    public static void a(Context context, a aVar) {
        z.a();
        if (context == null) {
            throw new IllegalArgumentException(z[5]);
        }
        int i = aVar.b;
        JSONObject jSONObject = aVar.a;
        String str = aVar.c;
        if (i == 3 || i == 4) {
            jSONObject = a(context, str, jSONObject, z[4]);
            if (jSONObject == null) {
                z.d();
                return;
            }
            int optInt = jSONObject.optInt(z[0], -1);
            if (optInt == 0) {
                c mVar = new m();
                mVar.c = str;
                mVar.b = i;
                mVar.o = optInt;
                mVar.h = aVar.h;
                mVar.e = aVar.e;
                mVar.f = aVar.f;
                mVar.m = aVar.m;
                mVar.d = aVar.d;
                mVar.g = aVar.g;
                boolean b = mVar.b(context, jSONObject);
                z.a();
                if (b) {
                    mVar.a(context);
                    z.a();
                    return;
                }
                z.d();
                return;
            }
            new StringBuilder(z[26]).append(optInt);
            z.d();
            g.a(str, 996, context);
            return;
        }
        new StringBuilder(z[25]).append(i);
        z.b();
        g.a(str, 996, context);
    }

    public static void a(Context context, String str) {
        new StringBuilder(z[8]).append(str);
        z.a();
        if (context == null) {
            throw new IllegalArgumentException(z[5]);
        } else if (TextUtils.isEmpty(str)) {
            z.e();
        } else {
            JSONObject a = a(context, z[1], str);
            if (a != null) {
                String optString = a.optString(z[9], "");
                if (ai.a(optString)) {
                    optString = a.optString(z[2], "");
                }
                int optInt = a.optInt(z[10], -1);
                if (optInt == 2) {
                    String trim = a.optString(z[4], "").trim();
                    if (a(trim)) {
                        new StringBuilder(z[7]).append(trim);
                        z.a();
                        if (context == null) {
                            throw new IllegalArgumentException(z[5]);
                        }
                        new e(trim, context, optString).start();
                        return;
                    }
                    new StringBuilder(z[3]).append(trim);
                    z.b();
                    g.a(optString, 996, context);
                    return;
                }
                a = optInt == 1 ? a(context, optString, a, z[4]) : null;
                if (a != null) {
                    c mVar;
                    int optInt2 = a.optInt(z[0], -1);
                    switch (optInt2) {
                        case 0:
                            mVar = new m();
                            break;
                        case 1:
                            mVar = new i();
                            break;
                        case 2:
                            mVar = new s();
                            break;
                        case 3:
                            mVar = new r();
                            break;
                        default:
                            new StringBuilder(z[6]).append(optInt2);
                            z.d();
                            g.a(optString, 996, context);
                            return;
                    }
                    boolean b = mVar.b(context, a);
                    z.a();
                    mVar.c = optString;
                    mVar.b = optInt;
                    mVar.o = optInt2;
                    if (b) {
                        mVar.a(context);
                        z.a();
                        return;
                    }
                    z.d();
                }
            }
        }
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String trim = str.trim();
        boolean matches = trim.matches(z[12]);
        if (matches) {
            return matches;
        }
        new StringBuilder(z[11]).append(trim);
        z.d();
        return matches;
    }

    public static boolean a(boolean z, int i, Context context) {
        return (z && i == 0) ? true : z && i == 1 && z[24].equalsIgnoreCase(cn.jpush.android.util.a.d(context));
    }
}
