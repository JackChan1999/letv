package cn.jpush.android.data;

import android.content.Context;
import android.text.TextUtils;
import cn.jpush.android.e;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.m;
import cn.jpush.android.util.o;
import cn.jpush.android.util.p;
import cn.jpush.android.util.z;
import com.letv.datastatistics.constant.PageIdConstant.Channel;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public abstract class c implements Serializable {
    private static final String[] E;
    public String A;
    public ArrayList<String> B = null;
    public String C;
    public String D;
    private boolean a = false;
    public int b;
    public String c;
    public String d;
    public boolean e;
    public int f;
    public int g = 0;
    public boolean h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public String n;
    public int o;
    public boolean p;
    public List<String> q = null;
    public int r;
    public String s;
    public String t;
    public List<c> u;
    public boolean v = false;
    public boolean w = false;
    public boolean x = false;
    public boolean y = false;
    public int z = -1;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 12;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u001b})]9'";
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
            case 0: goto L_0x00a2;
            case 1: goto L_0x00a6;
            case 2: goto L_0x00aa;
            case 3: goto L_0x00ae;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 77;
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
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\rf>W(;w}@\"~2U)~z0U*;3p\u0014";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "?p)]\"0)1[,:[)Y!\u0017~<S(\fv.[8,p8Gms3(F!\u000ea8R$&)";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\ta4@(~`)[??t8\u0014(,a2Fa~3>F(?g8\u0014$3t}R$2v}R,7s";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "6g)Dwq<";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "?p)]\"0)1[,:Z0S\u001f;`}\u0019m+a1\u000e";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "?w\u0002W\"0g8Z9";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "0L)]92v";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "0L;X,9";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "8f1X\u0012-p/Q(0";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "0L8L9,r.";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "0L>[#*v3@";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        E = r4;
        return;
    L_0x00a2:
        r9 = 94;
        goto L_0x0020;
    L_0x00a6:
        r9 = 19;
        goto L_0x0020;
    L_0x00aa:
        r9 = 93;
        goto L_0x0020;
    L_0x00ae:
        r9 = 52;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.c.<clinit>():void");
    }

    static String a(String str, String str2, String str3, Context context) {
        new StringBuilder(E[5]).append(str);
        z.a();
        String str4 = "";
        if (!(!d.a(str) || context == null || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3))) {
            byte[] a = p.a(str, 5, 5000, 4);
            if (a != null) {
                try {
                    String str5 = m.a(context, str2) + str3;
                    o.a(str5, a);
                    new StringBuilder(E[1]).append(str5);
                    z.a();
                    return str5;
                } catch (IOException e) {
                    z.i();
                    return str4;
                }
            }
            g.a(str2, Channel.TYPE_SCIENCECHANNEL, a.b(context, str), context);
        }
        return str4;
    }

    static boolean a(ArrayList<String> arrayList, Context context, String str, String str2, boolean z) {
        new StringBuilder(E[2]).append(str);
        z.a();
        if (!d.a(str) || context == null || arrayList.size() <= 0 || TextUtils.isEmpty(str2)) {
            return true;
        }
        Iterator it = arrayList.iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            String str3 = (String) it.next();
            String str4 = (str3 == null || str3.startsWith(E[4])) ? str3 : str + str3;
            byte[] a = p.a(str4, 5, 5000, 4);
            if (a != null) {
                try {
                    if (str3.startsWith(E[4])) {
                        str3 = o.c(str3);
                    }
                    str3 = !z ? m.a(context, str2) + str3 : m.b(context, str2) + str3;
                    o.a(str3, a);
                    new StringBuilder(E[1]).append(str3);
                    z.a();
                } catch (Throwable e) {
                    z.a(E[0], E[3], e);
                    z2 = false;
                }
            } else {
                g.a(str2, Channel.TYPE_SCIENCECHANNEL, a.b(context, str4), context);
                z2 = false;
            }
        }
        return z2;
    }

    public abstract void a(Context context);

    public final boolean a() {
        return this.o == 3 || this.o == 1;
    }

    protected abstract boolean a(Context context, JSONObject jSONObject);

    public final boolean b() {
        return this.o == 2;
    }

    public final boolean b(Context context, JSONObject jSONObject) {
        z.a();
        this.p = jSONObject.optInt(E[9], 0) > 0;
        this.r = jSONObject.optInt(E[8], 0);
        this.s = jSONObject.optString(E[7], "");
        this.t = jSONObject.optString(E[11], "");
        this.l = jSONObject.optString(E[10], "");
        if (ai.a(this.s)) {
            if (this.h) {
                z.b();
                this.s = e.d;
            } else {
                z.b();
                g.a(this.c, 996, context);
                return false;
            }
        }
        JSONObject a = d.a(context, this.c, jSONObject, E[6]);
        if (a == null) {
            return this.h && this.e;
        } else {
            if (this.h && this.e) {
                this.a = true;
            }
            return a(context, a);
        }
    }

    public final boolean c() {
        return this.o == 3;
    }

    public final String d() {
        return a() ? ((i) this).K : b() ? ((s) this).E : this.a ? this.D : "";
    }

    public final boolean e() {
        return this.a;
    }
}
