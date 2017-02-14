package cn.jpush.android.a;

import android.content.Context;
import android.support.v4.internal.view.SupportMenu;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class b {
    private static final String[] z;
    private int a = 0;
    private int b = 0;
    private int c = 0;
    private boolean d = false;
    private boolean e = false;
    private int f = 0;
    private double g = 0.0d;
    private PhoneStateListener h;
    private double i = 0.0d;
    private int j = 0;
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private String n = "";
    private String o = "";
    private String p = "";
    private TelephonyManager q;
    private String r;
    private ArrayList<a> s = new ArrayList();
    private Context t = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 26;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "f\\fETnV,G^u_kDHn]l\u0019zDqGdhXqMviTw]{tDsV~tI";
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
            case 0: goto L_0x0141;
            case 1: goto L_0x0144;
            case 2: goto L_0x0148;
            case 3: goto L_0x014b;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 59;
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
            case 1: goto L_0x004d;
            case 2: goto L_0x0055;
            case 3: goto L_0x005d;
            case 4: goto L_0x0065;
            case 5: goto L_0x006d;
            case 6: goto L_0x0076;
            case 7: goto L_0x0080;
            case 8: goto L_0x008a;
            case 9: goto L_0x0095;
            case 10: goto L_0x00a0;
            case 11: goto L_0x00ab;
            case 12: goto L_0x00b6;
            case 13: goto L_0x00c1;
            case 14: goto L_0x00cc;
            case 15: goto L_0x00d7;
            case 16: goto L_0x00e2;
            case 17: goto L_0x00ed;
            case 18: goto L_0x00f8;
            case 19: goto L_0x0103;
            case 20: goto L_0x010f;
            case 21: goto L_0x011b;
            case 22: goto L_0x0126;
            case 23: goto L_0x0131;
            case 24: goto L_0x013c;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "dVoV";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "wZmY^";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004d:
        r3[r2] = r1;
        r2 = 3;
        r1 = "DWn[riTmzZiSeRI";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0055:
        r3[r2] = r1;
        r2 = 4;
        r1 = "k]aVOn]lhZuWchXhVg";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005d:
        r3[r2] = r1;
        r2 = 5;
        r1 = "dWn[dnV";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0065:
        r3[r2] = r1;
        r2 = 6;
        r1 = "X\\kS\u0001";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006d:
        r3[r2] = r1;
        r2 = 7;
        r1 = "uSf^TXF{G^";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0076:
        r3[r2] = r1;
        r2 = 8;
        r1 = "uSf^TSKrR\u0001";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x0080:
        r3[r2] = r1;
        r2 = 9;
        r1 = "j]`^WbmaXNiFpNdd]fR";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x008a:
        r3[r2] = r1;
        r2 = 10;
        r1 = "XPkS\u0001";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0095:
        r3[r2] = r1;
        r2 = 11;
        r1 = "`Wvy^sEmEPNV";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x00a0:
        r3[r2] = r1;
        r2 = 12;
        r1 = "R\\gOKbQvR_&\u0012aRWk~mTZs[mY\u001bnA\"YNk^.\u0017\\nDg\u0017Nw\u0012pRKh@v\u0017Xb^n\u001aRiTm";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00ab:
        r3[r2] = r1;
        r2 = 13;
        r1 = "`WvuZtWQCZs[mYwfFkCNcW";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b6:
        r3[r2] = r1;
        r2 = 14;
        r1 = "dWn[rc\b";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00c1:
        r3[r2] = r1;
        r2 = 15;
        r1 = "`WvuZtWQCZs[mYrc";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00cc:
        r3[r2] = r1;
        r2 = 16;
        r1 = "+\u0012eRUb@cCRh\\8";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d7:
        r3[r2] = r1;
        r2 = 17;
        r1 = "`WvdBtFgZrc";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e2:
        r3[r2] = r1;
        r2 = 18;
        r1 = "fUg";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00ed:
        r3[r2] = r1;
        r2 = 19;
        r1 = "j]`^WbmlROp]p\\dd]fR";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f8:
        r3[r2] = r1;
        r2 = 20;
        r1 = "dSpERb@";
        r0 = 19;
        r3 = r4;
        goto L_0x0009;
    L_0x0103:
        r3[r2] = r1;
        r2 = 21;
        r1 = "t[f\r";
        r0 = 20;
        r3 = r4;
        goto L_0x0009;
    L_0x010f:
        r3[r2] = r1;
        r2 = 22;
        r1 = "t[eYZkmqCIb\\eCS";
        r0 = 21;
        r3 = r4;
        goto L_0x0009;
    L_0x011b:
        r3[r2] = r1;
        r2 = 23;
        r1 = "`WvuZtWQCZs[mYwh\\e^OrVg";
        r0 = 22;
        r3 = r4;
        goto L_0x0009;
    L_0x0126:
        r3[r2] = r1;
        r2 = 24;
        r1 = "+\u0012aVIu[gE\u0001";
        r0 = 23;
        r3 = r4;
        goto L_0x0009;
    L_0x0131:
        r3[r2] = r1;
        r2 = 25;
        r1 = "`WlRIfFkXU";
        r0 = 24;
        r3 = r4;
        goto L_0x0009;
    L_0x013c:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0141:
        r9 = 7;
        goto L_0x0020;
    L_0x0144:
        r9 = 50;
        goto L_0x0020;
    L_0x0148:
        r9 = 2;
        goto L_0x0020;
    L_0x014b:
        r9 = 55;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.b.<clinit>():void");
    }

    public b(Context context) {
        this.t = context;
        if (a.c(context, z[0])) {
            try {
                this.h = new c(this);
                this.q = (TelephonyManager) context.getSystemService(z[2]);
                this.q.listen(this.h, 18);
            } catch (Exception e) {
                z.d();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String h() {
        /*
        r8 = this;
        r1 = 0;
        r6 = 4669142098048450560; // 0x40cc200000000000 float:0.0 double:14400.0;
        r3 = 0;
        r0 = r8.t;
        if (r0 == 0) goto L_0x0019;
    L_0x000b:
        r0 = r8.t;
        r2 = z;
        r2 = r2[r3];
        r0 = cn.jpush.android.util.a.c(r0, r2);
        if (r0 != 0) goto L_0x0019;
    L_0x0017:
        r0 = r1;
    L_0x0018:
        return r0;
    L_0x0019:
        r0 = r8.q;	 Catch:{ Exception -> 0x00e9 }
        r0 = r0.getCellLocation();	 Catch:{ Exception -> 0x00e9 }
        r2 = r8.q;	 Catch:{ Exception -> 0x00e9 }
        r2 = r2.getNetworkOperator();	 Catch:{ Exception -> 0x00e9 }
        r3 = r2.length();	 Catch:{ Exception -> 0x00e9 }
        r4 = 5;
        if (r3 == r4) goto L_0x00d0;
    L_0x002c:
        r4 = 6;
        if (r3 == r4) goto L_0x002f;
    L_0x002f:
        r3 = r8.q;	 Catch:{ Exception -> 0x00e9 }
        r3 = r3.getPhoneType();	 Catch:{ Exception -> 0x00e9 }
        r4 = 2;
        if (r3 != r4) goto L_0x00ea;
    L_0x0038:
        r3 = r0 instanceof android.telephony.cdma.CdmaCellLocation;	 Catch:{ Exception -> 0x00e9 }
        if (r3 == 0) goto L_0x00ea;
    L_0x003c:
        if (r0 == 0) goto L_0x00ea;
    L_0x003e:
        r0 = (android.telephony.cdma.CdmaCellLocation) r0;	 Catch:{ Exception -> 0x00e9 }
        r3 = r0.getBaseStationLatitude();	 Catch:{ Exception -> 0x00e9 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00e9 }
        r4 = r4 / r6;
        r8.g = r4;	 Catch:{ Exception -> 0x00e9 }
        r3 = r0.getBaseStationLongitude();	 Catch:{ Exception -> 0x00e9 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00e9 }
        r4 = r4 / r6;
        r8.i = r4;	 Catch:{ Exception -> 0x00e9 }
        r3 = r0.getSystemId();	 Catch:{ Exception -> 0x00e9 }
        r8.m = r3;	 Catch:{ Exception -> 0x00e9 }
        r3 = r0.getBaseStationId();	 Catch:{ Exception -> 0x00e9 }
        r8.b = r3;	 Catch:{ Exception -> 0x00e9 }
        r0 = r0.getNetworkId();	 Catch:{ Exception -> 0x00e9 }
        r8.l = r0;	 Catch:{ Exception -> 0x00e9 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e9 }
        r0.<init>();	 Catch:{ Exception -> 0x00e9 }
        r3 = r8.m;	 Catch:{ Exception -> 0x00e9 }
        r0.append(r3);	 Catch:{ Exception -> 0x00e9 }
        cn.jpush.android.util.z.c();	 Catch:{ Exception -> 0x00e9 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e9 }
        r0.<init>();	 Catch:{ Exception -> 0x00e9 }
        r3 = r8.b;	 Catch:{ Exception -> 0x00e9 }
        r0.append(r3);	 Catch:{ Exception -> 0x00e9 }
        cn.jpush.android.util.z.c();	 Catch:{ Exception -> 0x00e9 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e9 }
        r0.<init>();	 Catch:{ Exception -> 0x00e9 }
        r3 = r8.l;	 Catch:{ Exception -> 0x00e9 }
        r0.append(r3);	 Catch:{ Exception -> 0x00e9 }
        cn.jpush.android.util.z.c();	 Catch:{ Exception -> 0x00e9 }
        r0 = new cn.jpush.android.a.a;	 Catch:{ Exception -> 0x00e9 }
        r0.<init>();	 Catch:{ Exception -> 0x00e9 }
        r3 = r8.b;	 Catch:{ Exception -> 0x00e9 }
        r0.a(r3);	 Catch:{ Exception -> 0x00e9 }
        r3 = r8.l;	 Catch:{ Exception -> 0x00e9 }
        r0.d(r3);	 Catch:{ Exception -> 0x00e9 }
        r3 = r8.m;	 Catch:{ Exception -> 0x00e9 }
        r0.c(r3);	 Catch:{ Exception -> 0x00e9 }
        r3 = 0;
        r4 = 3;
        r2 = r2.substring(r3, r4);	 Catch:{ Exception -> 0x00e9 }
        r2 = java.lang.Integer.parseInt(r2);	 Catch:{ Exception -> 0x00e9 }
        r0.b(r2);	 Catch:{ Exception -> 0x00e9 }
        r2 = z;	 Catch:{ Exception -> 0x00e9 }
        r3 = 1;
        r2 = r2[r3];	 Catch:{ Exception -> 0x00e9 }
        r0.a(r2);	 Catch:{ Exception -> 0x00e9 }
        r2 = r8.s;	 Catch:{ Exception -> 0x00e9 }
        r2.add(r0);	 Catch:{ Exception -> 0x00e9 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00e9 }
        r2.<init>();	 Catch:{ Exception -> 0x00e9 }
        r3 = r0.a();	 Catch:{ Exception -> 0x00e9 }
        r2.append(r3);	 Catch:{ Exception -> 0x00e9 }
        cn.jpush.android.util.z.b();	 Catch:{ Exception -> 0x00e9 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x00e9 }
        r8.r = r0;	 Catch:{ Exception -> 0x00e9 }
        r0 = r8.r;	 Catch:{ Exception -> 0x00e9 }
        goto L_0x0018;
    L_0x00d0:
        r4 = 0;
        r5 = 3;
        r4 = r2.substring(r4, r5);	 Catch:{ Exception -> 0x00e9 }
        r4 = java.lang.Integer.parseInt(r4);	 Catch:{ Exception -> 0x00e9 }
        r8.j = r4;	 Catch:{ Exception -> 0x00e9 }
        r4 = 3;
        r3 = r2.substring(r4, r3);	 Catch:{ Exception -> 0x00e9 }
        r3 = java.lang.Integer.parseInt(r3);	 Catch:{ Exception -> 0x00e9 }
        r8.k = r3;	 Catch:{ Exception -> 0x00e9 }
        goto L_0x002f;
    L_0x00e9:
        r0 = move-exception;
    L_0x00ea:
        r0 = r1;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.b.h():java.lang.String");
    }

    public final int a() {
        return this.b;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final org.json.JSONArray b() {
        /*
        r8 = this;
        r1 = 0;
        r6 = 4669142098048450560; // 0x40cc200000000000 float:0.0 double:14400.0;
        r3 = 0;
        r0 = r8.t;
        if (r0 == 0) goto L_0x0019;
    L_0x000b:
        r0 = r8.t;
        r2 = z;
        r2 = r2[r3];
        r0 = cn.jpush.android.util.a.c(r0, r2);
        if (r0 != 0) goto L_0x0019;
    L_0x0017:
        r0 = r1;
    L_0x0018:
        return r0;
    L_0x0019:
        r0 = r8.q;	 Catch:{ Exception -> 0x00d6 }
        r0 = r0.getCellLocation();	 Catch:{ Exception -> 0x00d6 }
        r2 = r8.q;	 Catch:{ Exception -> 0x00d6 }
        r2 = r2.getNetworkOperator();	 Catch:{ Exception -> 0x00d6 }
        r3 = r2.length();	 Catch:{ Exception -> 0x00d6 }
        r4 = 5;
        if (r3 == r4) goto L_0x00bd;
    L_0x002c:
        r4 = 6;
        if (r3 == r4) goto L_0x002f;
    L_0x002f:
        r3 = r8.q;	 Catch:{ Exception -> 0x00d6 }
        r3 = r3.getPhoneType();	 Catch:{ Exception -> 0x00d6 }
        r4 = 2;
        if (r3 != r4) goto L_0x00da;
    L_0x0038:
        r3 = r0 instanceof android.telephony.cdma.CdmaCellLocation;	 Catch:{ Exception -> 0x00d6 }
        if (r3 == 0) goto L_0x00da;
    L_0x003c:
        if (r0 == 0) goto L_0x00da;
    L_0x003e:
        r0 = (android.telephony.cdma.CdmaCellLocation) r0;	 Catch:{ Exception -> 0x00d6 }
        r3 = r0.getBaseStationLatitude();	 Catch:{ Exception -> 0x00d6 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00d6 }
        r4 = r4 / r6;
        r8.g = r4;	 Catch:{ Exception -> 0x00d6 }
        r3 = r0.getBaseStationLongitude();	 Catch:{ Exception -> 0x00d6 }
        r4 = (double) r3;	 Catch:{ Exception -> 0x00d6 }
        r4 = r4 / r6;
        r8.i = r4;	 Catch:{ Exception -> 0x00d6 }
        r3 = r0.getSystemId();	 Catch:{ Exception -> 0x00d6 }
        r8.m = r3;	 Catch:{ Exception -> 0x00d6 }
        r3 = r0.getBaseStationId();	 Catch:{ Exception -> 0x00d6 }
        r8.b = r3;	 Catch:{ Exception -> 0x00d6 }
        r0 = r0.getNetworkId();	 Catch:{ Exception -> 0x00d6 }
        r8.l = r0;	 Catch:{ Exception -> 0x00d6 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00d6 }
        r0.<init>();	 Catch:{ Exception -> 0x00d6 }
        r3 = r8.m;	 Catch:{ Exception -> 0x00d6 }
        r0.append(r3);	 Catch:{ Exception -> 0x00d6 }
        cn.jpush.android.util.z.c();	 Catch:{ Exception -> 0x00d6 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00d6 }
        r0.<init>();	 Catch:{ Exception -> 0x00d6 }
        r3 = r8.b;	 Catch:{ Exception -> 0x00d6 }
        r0.append(r3);	 Catch:{ Exception -> 0x00d6 }
        cn.jpush.android.util.z.c();	 Catch:{ Exception -> 0x00d6 }
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00d6 }
        r0.<init>();	 Catch:{ Exception -> 0x00d6 }
        r3 = r8.l;	 Catch:{ Exception -> 0x00d6 }
        r0.append(r3);	 Catch:{ Exception -> 0x00d6 }
        cn.jpush.android.util.z.c();	 Catch:{ Exception -> 0x00d6 }
        r0 = new cn.jpush.android.a.a;	 Catch:{ Exception -> 0x00d6 }
        r0.<init>();	 Catch:{ Exception -> 0x00d6 }
        r3 = r8.b;	 Catch:{ Exception -> 0x00d6 }
        r0.a(r3);	 Catch:{ Exception -> 0x00d6 }
        r3 = r8.l;	 Catch:{ Exception -> 0x00d6 }
        r0.d(r3);	 Catch:{ Exception -> 0x00d6 }
        r3 = r8.m;	 Catch:{ Exception -> 0x00d6 }
        r0.c(r3);	 Catch:{ Exception -> 0x00d6 }
        r3 = 0;
        r4 = 3;
        r2 = r2.substring(r3, r4);	 Catch:{ Exception -> 0x00d6 }
        r2 = java.lang.Integer.parseInt(r2);	 Catch:{ Exception -> 0x00d6 }
        r0.b(r2);	 Catch:{ Exception -> 0x00d6 }
        r2 = z;	 Catch:{ Exception -> 0x00d6 }
        r3 = 1;
        r2 = r2[r3];	 Catch:{ Exception -> 0x00d6 }
        r0.a(r2);	 Catch:{ Exception -> 0x00d6 }
        r2 = r8.s;	 Catch:{ Exception -> 0x00d6 }
        r2.add(r0);	 Catch:{ Exception -> 0x00d6 }
        r0 = r0.b();	 Catch:{ Exception -> 0x00d6 }
        goto L_0x0018;
    L_0x00bd:
        r4 = 0;
        r5 = 3;
        r4 = r2.substring(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = java.lang.Integer.parseInt(r4);	 Catch:{ Exception -> 0x00d6 }
        r8.j = r4;	 Catch:{ Exception -> 0x00d6 }
        r4 = 3;
        r3 = r2.substring(r4, r3);	 Catch:{ Exception -> 0x00d6 }
        r3 = java.lang.Integer.parseInt(r3);	 Catch:{ Exception -> 0x00d6 }
        r8.k = r3;	 Catch:{ Exception -> 0x00d6 }
        goto L_0x002f;
    L_0x00d6:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x00da:
        r0 = r1;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.b.b():org.json.JSONArray");
    }

    public final JSONArray c() {
        CellLocation cellLocation;
        int phoneType;
        Exception e;
        String networkOperator;
        int length;
        CdmaCellLocation cdmaCellLocation;
        Class cls;
        Class[] clsArr;
        Method method;
        Method method2;
        Method method3;
        Object[] objArr;
        Method method4;
        JSONArray jSONArray;
        int[] d;
        int i;
        try {
            cellLocation = this.q.getCellLocation();
        } catch (Exception e2) {
            cellLocation = null;
            z.i();
        }
        if (cellLocation == null) {
            z.d(z[3], z[12]);
            return null;
        }
        long j;
        int i2;
        int i3;
        JSONObject jSONObject;
        this.e = false;
        this.d = false;
        this.c = 0;
        this.f = 0;
        this.j = 0;
        this.k = 0;
        this.n = "";
        this.o = "";
        this.p = "";
        if (this.t == null || a.c(this.t, z[0])) {
            Object cellLocation2;
            try {
                cellLocation2 = this.q.getCellLocation();
            } catch (Exception e3) {
                cellLocation2 = null;
                z.i();
            }
            if (cellLocation2 == null) {
                z.a();
            } else {
                a aVar;
                this.p = this.q.getNetworkOperatorName();
                this.n = a.a(this.q.getNetworkType());
                this.o = a.r(this.t);
                new StringBuilder(z[8]).append(this.n).append(z[24]).append(this.p).append(z[16]).append(this.o);
                z.a();
                try {
                    phoneType = this.q.getPhoneType();
                    try {
                        if (cellLocation2 instanceof GsmCellLocation) {
                            this.e = true;
                            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation2;
                            this.c = gsmCellLocation.getCid();
                            this.f = gsmCellLocation.getLac();
                        }
                    } catch (Exception e4) {
                        e = e4;
                        e.printStackTrace();
                        networkOperator = this.q.getNetworkOperator();
                        length = networkOperator.length();
                        if (length > 3) {
                            this.j = Integer.parseInt(networkOperator.substring(0, 3));
                            this.k = Integer.parseInt(networkOperator.substring(3, length));
                        }
                        if (phoneType == 2) {
                            if (cellLocation2 instanceof CdmaCellLocation) {
                                cdmaCellLocation = (CdmaCellLocation) cellLocation2;
                                this.g = ((double) cdmaCellLocation.getBaseStationLatitude()) / 14400.0d;
                                this.i = ((double) cdmaCellLocation.getBaseStationLongitude()) / 14400.0d;
                                this.m = cdmaCellLocation.getSystemId();
                                this.b = cdmaCellLocation.getBaseStationId();
                                this.l = cdmaCellLocation.getNetworkId();
                                new StringBuilder(z[21]).append(this.m).append(z[10]).append(this.b).append(z[6]).append(this.l);
                                z.c();
                                aVar = new a();
                                aVar.a(this.b);
                                aVar.d(this.l);
                                aVar.c(this.m);
                                aVar.b(Integer.parseInt(networkOperator.substring(0, 3)));
                                aVar.a(z[1]);
                                this.s.add(aVar);
                                new StringBuilder(z[14]).append(aVar.a());
                                z.b();
                                this.r = aVar.toString();
                                this.d = true;
                            } else {
                                cls = cellLocation2.getClass();
                                clsArr = new Class[0];
                                method = cls.getMethod(z[15], clsArr);
                                method2 = cls.getMethod(z[17], clsArr);
                                method3 = cls.getMethod(z[11], clsArr);
                                objArr = new Object[0];
                                this.b = ((Integer) method.invoke(cellLocation2, objArr)).intValue();
                                this.m = ((Integer) method2.invoke(cellLocation2, objArr)).intValue();
                                this.l = ((Integer) method3.invoke(cellLocation2, objArr)).intValue();
                                method = cls.getMethod(z[13], clsArr);
                                method4 = cls.getMethod(z[23], clsArr);
                                this.g = (double) ((Integer) method.invoke(cellLocation2, objArr)).intValue();
                                this.i = (double) ((Integer) method4.invoke(cellLocation2, objArr)).intValue();
                                this.d = true;
                            }
                        }
                        jSONArray = new JSONArray();
                        d = d();
                        j = (long) this.f;
                        i2 = this.j;
                        i3 = this.k;
                        if (d.length < 2) {
                            d = new int[]{this.c, -60};
                        }
                        i = 0;
                        while (i < d.length) {
                            length = i + 1;
                            if (length >= 0) {
                            }
                            try {
                                jSONObject = new JSONObject();
                                jSONObject.put(z[5], d[i]);
                                jSONObject.put(z[4], j);
                                jSONObject.put(z[9], i2);
                                jSONObject.put(z[19], i3);
                                jSONObject.put(z[22], length);
                                jSONObject.put(z[18], 0);
                                jSONObject.put(z[7], this.n);
                                jSONObject.put(z[25], this.o);
                                jSONObject.put(z[20], this.p);
                                jSONArray.put(jSONObject);
                            } catch (Exception e5) {
                                e5.getMessage();
                                z.e();
                            }
                            i += 2;
                        }
                        if (this.d) {
                            try {
                                return new JSONArray().put(new JSONObject(h()));
                            } catch (JSONException e6) {
                            } catch (NullPointerException e7) {
                            }
                        }
                        return jSONArray;
                    }
                } catch (Exception e8) {
                    e = e8;
                    phoneType = 0;
                    e.printStackTrace();
                    networkOperator = this.q.getNetworkOperator();
                    length = networkOperator.length();
                    if (length > 3) {
                        this.j = Integer.parseInt(networkOperator.substring(0, 3));
                        this.k = Integer.parseInt(networkOperator.substring(3, length));
                    }
                    if (phoneType == 2) {
                        if (cellLocation2 instanceof CdmaCellLocation) {
                            cls = cellLocation2.getClass();
                            clsArr = new Class[0];
                            method = cls.getMethod(z[15], clsArr);
                            method2 = cls.getMethod(z[17], clsArr);
                            method3 = cls.getMethod(z[11], clsArr);
                            objArr = new Object[0];
                            this.b = ((Integer) method.invoke(cellLocation2, objArr)).intValue();
                            this.m = ((Integer) method2.invoke(cellLocation2, objArr)).intValue();
                            this.l = ((Integer) method3.invoke(cellLocation2, objArr)).intValue();
                            method = cls.getMethod(z[13], clsArr);
                            method4 = cls.getMethod(z[23], clsArr);
                            this.g = (double) ((Integer) method.invoke(cellLocation2, objArr)).intValue();
                            this.i = (double) ((Integer) method4.invoke(cellLocation2, objArr)).intValue();
                            this.d = true;
                        } else {
                            cdmaCellLocation = (CdmaCellLocation) cellLocation2;
                            this.g = ((double) cdmaCellLocation.getBaseStationLatitude()) / 14400.0d;
                            this.i = ((double) cdmaCellLocation.getBaseStationLongitude()) / 14400.0d;
                            this.m = cdmaCellLocation.getSystemId();
                            this.b = cdmaCellLocation.getBaseStationId();
                            this.l = cdmaCellLocation.getNetworkId();
                            new StringBuilder(z[21]).append(this.m).append(z[10]).append(this.b).append(z[6]).append(this.l);
                            z.c();
                            aVar = new a();
                            aVar.a(this.b);
                            aVar.d(this.l);
                            aVar.c(this.m);
                            aVar.b(Integer.parseInt(networkOperator.substring(0, 3)));
                            aVar.a(z[1]);
                            this.s.add(aVar);
                            new StringBuilder(z[14]).append(aVar.a());
                            z.b();
                            this.r = aVar.toString();
                            this.d = true;
                        }
                    }
                    jSONArray = new JSONArray();
                    d = d();
                    j = (long) this.f;
                    i2 = this.j;
                    i3 = this.k;
                    if (d.length < 2) {
                        d = new int[]{this.c, -60};
                    }
                    i = 0;
                    while (i < d.length) {
                        length = i + 1;
                        if (length >= 0) {
                        }
                        jSONObject = new JSONObject();
                        jSONObject.put(z[5], d[i]);
                        jSONObject.put(z[4], j);
                        jSONObject.put(z[9], i2);
                        jSONObject.put(z[19], i3);
                        jSONObject.put(z[22], length);
                        jSONObject.put(z[18], 0);
                        jSONObject.put(z[7], this.n);
                        jSONObject.put(z[25], this.o);
                        jSONObject.put(z[20], this.p);
                        jSONArray.put(jSONObject);
                        i += 2;
                    }
                    if (this.d) {
                        return new JSONArray().put(new JSONObject(h()));
                    }
                    return jSONArray;
                }
                try {
                    networkOperator = this.q.getNetworkOperator();
                    length = networkOperator.length();
                    if (length > 3) {
                        this.j = Integer.parseInt(networkOperator.substring(0, 3));
                        this.k = Integer.parseInt(networkOperator.substring(3, length));
                    }
                    if (phoneType == 2) {
                        if (cellLocation2 instanceof CdmaCellLocation) {
                            cdmaCellLocation = (CdmaCellLocation) cellLocation2;
                            this.g = ((double) cdmaCellLocation.getBaseStationLatitude()) / 14400.0d;
                            this.i = ((double) cdmaCellLocation.getBaseStationLongitude()) / 14400.0d;
                            this.m = cdmaCellLocation.getSystemId();
                            this.b = cdmaCellLocation.getBaseStationId();
                            this.l = cdmaCellLocation.getNetworkId();
                            new StringBuilder(z[21]).append(this.m).append(z[10]).append(this.b).append(z[6]).append(this.l);
                            z.c();
                            aVar = new a();
                            aVar.a(this.b);
                            aVar.d(this.l);
                            aVar.c(this.m);
                            if (networkOperator != null && networkOperator.length() >= 3) {
                                aVar.b(Integer.parseInt(networkOperator.substring(0, 3)));
                            }
                            aVar.a(z[1]);
                            this.s.add(aVar);
                            new StringBuilder(z[14]).append(aVar.a());
                            z.b();
                            this.r = aVar.toString();
                            this.d = true;
                        } else {
                            cls = cellLocation2.getClass();
                            clsArr = new Class[0];
                            method = cls.getMethod(z[15], clsArr);
                            method2 = cls.getMethod(z[17], clsArr);
                            method3 = cls.getMethod(z[11], clsArr);
                            objArr = new Object[0];
                            this.b = ((Integer) method.invoke(cellLocation2, objArr)).intValue();
                            this.m = ((Integer) method2.invoke(cellLocation2, objArr)).intValue();
                            this.l = ((Integer) method3.invoke(cellLocation2, objArr)).intValue();
                            method = cls.getMethod(z[13], clsArr);
                            method4 = cls.getMethod(z[23], clsArr);
                            this.g = (double) ((Integer) method.invoke(cellLocation2, objArr)).intValue();
                            this.i = (double) ((Integer) method4.invoke(cellLocation2, objArr)).intValue();
                            this.d = true;
                        }
                    }
                } catch (Exception e9) {
                    z.i();
                }
            }
        } else {
            z.d();
        }
        jSONArray = new JSONArray();
        d = d();
        j = (long) this.f;
        i2 = this.j;
        i3 = this.k;
        if (d.length < 2) {
            d = new int[]{this.c, -60};
        }
        i = 0;
        while (i < d.length && i <= 4) {
            length = i + 1;
            length = (length >= 0 || length > 31) ? 0 : (length * 2) - 113;
            jSONObject = new JSONObject();
            jSONObject.put(z[5], d[i]);
            jSONObject.put(z[4], j);
            jSONObject.put(z[9], i2);
            jSONObject.put(z[19], i3);
            jSONObject.put(z[22], length);
            jSONObject.put(z[18], 0);
            jSONObject.put(z[7], this.n);
            jSONObject.put(z[25], this.o);
            jSONObject.put(z[20], this.p);
            jSONArray.put(jSONObject);
            i += 2;
        }
        if (this.d) {
            return new JSONArray().put(new JSONObject(h()));
        }
        return jSONArray;
    }

    public final int[] d() {
        if (this.c == 0) {
            return new int[0];
        }
        List<NeighboringCellInfo> neighboringCellInfo = this.q.getNeighboringCellInfo();
        if (neighboringCellInfo == null || neighboringCellInfo.size() == 0) {
            return new int[]{this.c};
        }
        Object obj = new int[((neighboringCellInfo.size() * 2) + 2)];
        obj[0] = this.c;
        obj[1] = this.a;
        int i = 2;
        for (NeighboringCellInfo neighboringCellInfo2 : neighboringCellInfo) {
            int cid = neighboringCellInfo2.getCid();
            if (cid > 0 && cid != SupportMenu.USER_MASK) {
                int i2 = i + 1;
                obj[i] = cid;
                i = i2 + 1;
                obj[i2] = neighboringCellInfo2.getRssi();
            }
        }
        Object obj2 = new int[i];
        System.arraycopy(obj, 0, obj2, 0, i);
        return obj2;
    }

    public final boolean e() {
        return this.d;
    }

    public final boolean f() {
        return this.e;
    }

    public final float g() {
        if (!this.d && this.e) {
            d();
        }
        return 1.06535322E9f;
    }
}
