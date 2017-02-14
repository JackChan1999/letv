package cn.jpush.android.a;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class j {
    private static final String[] z;
    private WifiManager a;
    private Context b = null;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 8;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "\u0017b\u000ec\u001b\u0003s\u000fx";
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
            case 0: goto L_0x0079;
            case 1: goto L_0x007c;
            case 2: goto L_0x007f;
            case 3: goto L_0x0082;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
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
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0007y\u0012b\u0010\u0007b";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0013\u001aeU\u0007y\tb\u0001^";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0010w\u001b";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0013\u001aeO";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0005x\u0018~\u001a\rrR|\u0010\u0016{\u0015\u0006\ry\u0012\"4'U9_&;U3M'7S#@:'W(E:*";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0007c\u000e~\u0010\nb+e\u0013\r,";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u0013\u001ae";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0079:
        r9 = 100;
        goto L_0x0020;
    L_0x007c:
        r9 = 22;
        goto L_0x0020;
    L_0x007f:
        r9 = 124; // 0x7c float:1.74E-43 double:6.13E-322;
        goto L_0x0020;
    L_0x0082:
        r9 = 12;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.j.<clinit>():void");
    }

    public j(Context context) {
        this.a = (WifiManager) context.getSystemService(z[7]);
        this.b = context;
    }

    private List<k> d() {
        z.a();
        if (!a()) {
            return new ArrayList();
        }
        k kVar;
        WifiInfo connectionInfo = this.a.getConnectionInfo();
        if (connectionInfo != null) {
            k kVar2 = new k(this, connectionInfo.getBSSID(), connectionInfo.getRssi(), connectionInfo.getSSID());
            new StringBuilder(z[6]).append(kVar2.toString());
            kVar = kVar2;
            z.a();
        } else {
            kVar = null;
        }
        ArrayList arrayList = new ArrayList();
        if (kVar != null) {
            arrayList.add(kVar);
        }
        List scanResults = VERSION.SDK_INT < 23 ? this.a.getScanResults() : (this.b == null || !a.c(this.b, z[5])) ? null : this.a.getScanResults();
        if (r0 != null && r0.size() > 0) {
            int i = -200;
            k kVar3 = null;
            for (ScanResult kVar4 : r0) {
                int i2;
                k kVar5 = new k(this, kVar4);
                if (kVar5.c.equals(kVar.c) || kVar5.b <= i) {
                    kVar2 = kVar3;
                    i2 = i;
                } else {
                    new StringBuilder(z[4]).append(kVar5.toString());
                    z.a();
                    k kVar6 = kVar5;
                    i2 = kVar5.b;
                    kVar2 = kVar6;
                }
                kVar3 = kVar2;
                i = i2;
            }
            if (kVar3 != null) {
                arrayList.add(kVar3);
            }
        }
        return arrayList;
    }

    public final boolean a() {
        try {
            return this.a.isWifiEnabled();
        } catch (Exception e) {
            z.i();
            return false;
        }
    }

    public final WifiManager b() {
        return this.a;
    }

    public final JSONArray c() {
        JSONArray jSONArray = new JSONArray();
        try {
            List d = d();
            int size = d.size();
            new StringBuilder(z[2]).append(size);
            z.a();
            if (size == 0) {
                z.b();
                return jSONArray;
            }
            JSONObject a;
            if (size == 1) {
                a = ((k) d.get(0)).a();
                a.put(z[3], z[1]);
                jSONArray.put(a);
            } else if (size == 2) {
                a = ((k) d.get(0)).a();
                a.put(z[3], z[1]);
                jSONArray.put(a);
                a = ((k) d.get(1)).a();
                a.put(z[3], z[0]);
                jSONArray.put(a);
            }
            return jSONArray;
        } catch (Exception e) {
            z.i();
        }
    }
}
