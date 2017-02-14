package cn.jpush.android.data;

import android.content.Context;
import cn.jpush.android.api.m;
import cn.jpush.android.helpers.d;
import cn.jpush.android.helpers.g;
import cn.jpush.android.service.ServiceInterface;
import cn.jpush.android.util.a;
import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import org.json.JSONObject;

public class i extends c {
    private static final String[] R;
    public String E;
    public boolean F;
    public boolean G;
    public int H;
    public boolean I;
    public int J;
    public String K;
    public l L;
    public String M;
    public boolean N;
    public boolean O;
    public String P;
    public boolean Q;
    public String a;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 28;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Fs\u001dq$v~\fl0)=\rg?}r\u000fgtcvXz<|jXd;wxX$t";
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
            case 0: goto L_0x016b;
            case 1: goto L_0x016f;
            case 2: goto L_0x0173;
            case 3: goto L_0x0177;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 84;
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
            case 1: goto L_0x004e;
            case 2: goto L_0x0057;
            case 3: goto L_0x0060;
            case 4: goto L_0x0069;
            case 5: goto L_0x0072;
            case 6: goto L_0x007b;
            case 7: goto L_0x0085;
            case 8: goto L_0x0091;
            case 9: goto L_0x009d;
            case 10: goto L_0x00a9;
            case 11: goto L_0x00b4;
            case 12: goto L_0x00bf;
            case 13: goto L_0x00cb;
            case 14: goto L_0x00d7;
            case 15: goto L_0x00e3;
            case 16: goto L_0x00ef;
            case 17: goto L_0x00fb;
            case 18: goto L_0x0107;
            case 19: goto L_0x0113;
            case 20: goto L_0x011f;
            case 21: goto L_0x012b;
            case 22: goto L_0x0137;
            case 23: goto L_0x0143;
            case 24: goto L_0x014f;
            case 25: goto L_0x015b;
            case 26: goto L_0x0166;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "rm\u0013V'{r\u000f";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "rm\u0013V5fi\u0017V=}n\fh8";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004e:
        r3[r2] = r1;
        r2 = 3;
        r1 = "rB\u0011d5tx'|&";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0057:
        r3[r2] = r1;
        r2 = 4;
        r1 = "rm\u0013V'{r\u000fV2zs\u0011z<vy'g;gt";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x0060:
        r3[r2] = r1;
        r2 = 5;
        r1 = "rB\u001d|&";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0069:
        r3[r2] = r1;
        r2 = 6;
        r1 = "rm\u0013V:";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x0072:
        r3[r2] = r1;
        r2 = 7;
        r1 = "rm\u0013V1pr\u0016";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x007b:
        r3[r2] = r1;
        r2 = 8;
        r1 = "rm\u0013V!aq";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x0085:
        r3[r2] = r1;
        r2 = 9;
        r1 = "rh\ff\u000b}";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0091:
        r3[r2] = r1;
        r2 = 10;
        r1 = "rB\u001d{1`";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 11;
        r1 = "rB\nl'";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a9:
        r3[r2] = r1;
        r2 = 12;
        r1 = "Lj\u001dk\u0004rz\u001dY5gu";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00b4:
        r3[r2] = r1;
        r2 = 13;
        r1 = "Lt\u0015h3vM\u0019}<";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00bf:
        r3[r2] = r1;
        r2 = 14;
        r1 = "rB\f` x";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00cb:
        r3[r2] = r1;
        r2 = 15;
        r1 = "rB\fp$v";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        r2 = 16;
        r1 = "rm\u0013V!";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00e3:
        r3[r2] = r1;
        r2 = 17;
        r1 = "rB\u001a{;`j\u001d{";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00ef:
        r3[r2] = r1;
        r2 = 18;
        r1 = "rB\u0011j;}B\r{8";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00fb:
        r3[r2] = r1;
        r2 = 19;
        r1 = "rB\u000el&";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x0107:
        r3[r2] = r1;
        r2 = 20;
        r1 = "rB\u000bj;ax";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0113:
        r3[r2] = r1;
        r2 = 21;
        r1 = "rh\ff\u000b~";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x011f:
        r3[r2] = r1;
        r2 = 22;
        r1 = "rB\u000b`.v";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x012b:
        r3[r2] = r1;
        r2 = 23;
        r1 = "rm\u0013V9w(";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0137:
        r3[r2] = r1;
        r2 = 24;
        r1 = "rh\ff\u000ba";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0143:
        r3[r2] = r1;
        r2 = 25;
        r1 = "rh\ff\u000ba~";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x014f:
        r3[r2] = r1;
        r2 = 26;
        r1 = "rB\u0011g2|";
        r0 = 25;
        r3 = r4;
        goto L_0x0009;
    L_0x015b:
        r3[r2] = r1;
        r2 = 27;
        r1 = "Lt\u001bf:C|\fa";
        r0 = 26;
        r3 = r4;
        goto L_0x0009;
    L_0x0166:
        r3[r2] = r1;
        R = r4;
        return;
    L_0x016b:
        r9 = 19;
        goto L_0x0020;
    L_0x016f:
        r9 = 29;
        goto L_0x0020;
    L_0x0173:
        r9 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x0020;
    L_0x0177:
        r9 = 9;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.i.<clinit>():void");
    }

    public i() {
        this.O = true;
        this.o = 1;
        this.L = new l(this);
    }

    static /* synthetic */ void a(i iVar, i iVar2, Context context) {
        if (d.a(iVar2.G, iVar2.H, context)) {
            if (a.b(context)) {
                ServiceInterface.a(context, (c) iVar2);
                return;
            }
            iVar2.G = false;
        }
        m.a(context, (c) iVar2);
    }

    public void a(Context context) {
        z.a();
        boolean f = a.f(context, this.a);
        int i = 995;
        if (this.F || !f) {
            if (this.F && f) {
                z.b();
                i = 998;
            }
            if (this.J == 1) {
                new j(this, this, context, i).start();
                return;
            } else if (this.J == 0) {
                new k(this, context, i, this).start();
                return;
            } else {
                new StringBuilder(R[0]).append(this.J);
                z.d();
                return;
            }
        }
        z.b();
        g.a(this.c, 997, context);
    }

    public boolean a(Context context, JSONObject jSONObject) {
        boolean z = false;
        z.a();
        this.a = jSONObject.optString(R[6], "");
        this.F = jSONObject.optInt(R[16], 0) > 0;
        this.G = jSONObject.optInt(R[21], 0) > 0;
        this.H = jSONObject.optInt(R[9], 0);
        this.I = jSONObject.optInt(R[24], 0) > 0;
        this.J = jSONObject.optInt(R[1], 1);
        this.K = jSONObject.optString(R[8], "").trim();
        this.M = jSONObject.optString(R[23], "");
        this.E = jSONObject.optString(R[25], "");
        this.N = jSONObject.optInt(R[4], 0) > 0;
        this.O = jSONObject.optInt(R[2], 1) == 1;
        if (this.o == 1) {
            JSONObject a = d.a(context, this.c, jSONObject, R[7]);
            if (a == null) {
                return false;
            }
            l lVar = this.L;
            z.a();
            lVar.a = a.optString(R[14], "");
            lVar.b = a.optString(R[18], "").trim();
            lVar.c = a.optString(R[19], "");
            lVar.d = a.optString(R[15], "");
            lVar.e = a.optInt(R[20], 0) == 0;
            lVar.f = a.optString(R[22], "");
            lVar.g = a.optString(R[26], "");
            lVar.h = a.optString(R[3], "").trim();
            lVar.j = a.optString(R[5], "").trim();
            lVar.o.y = a.optInt(R[17], 0) == 1;
            if (a.optInt(R[11], 0) == 0) {
                z = true;
            }
            lVar.i = z;
            if (lVar.i) {
                lVar.k = cn.jpush.android.util.i.a(a.optJSONArray(R[10]));
            }
            if (ai.a(lVar.m)) {
                lVar.m = a.optString(R[13], "").trim();
            }
            if (ai.a(lVar.l)) {
                lVar.l = a.optString(R[27], "").trim();
            }
            if (ai.a(lVar.l)) {
                lVar.l = a.optString(R[12], "").trim();
            }
        }
        return true;
    }
}
