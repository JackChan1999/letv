package cn.jpush.android.data;

import cn.jpush.android.util.ai;
import cn.jpush.android.util.z;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class JPushLocalNotification implements Serializable {
    private static final String[] z;
    private int a = 1;
    private String b = "";
    private String c = z[14];
    private String d = z[14];
    private long e = 0;
    private String f;
    private String g;
    private String h;
    private long i;
    private long j = 1;
    private int k = 1;
    private String l = "";
    private String m = "";

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 15;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "F|\u0016L _F\u001bW";
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
            case 0: goto L_0x00c4;
            case 1: goto L_0x00c8;
            case 2: goto L_0x00cc;
            case 3: goto L_0x00d0;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 78;
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
            case 12: goto L_0x00b4;
            case 13: goto L_0x00bf;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "E|\u001aM\"R";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "HL\u001bW+EW*W7[F";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "FP\u0012|'O";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "EL\u0001J(B@\u0014J!E|\u0001Z>N";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "DU\u0010Q<BG\u0010|#XD*J*";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "JG*W";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "E|\u0001J:GF";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "E|\u0017V'GG\u0010Q\u0011BG";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "E|\u0010[:YB\u0006";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "XK\u001aT\u0011_Z\u0005F";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "E|\u0016L _F\u001bW";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "as\u0000P&gL\u0016B\"eL\u0001J(B@\u0014W'DM";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "xF\u0001\u0003:BN\u0010\u0003(JJ\u0019\u0002n{O\u0010B=N\u0003\u0016K+HHUZ!^QUB<LPT";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b4:
        r3[r2] = r1;
        r2 = 14;
        r1 = "\u001b\u0013";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00bf:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x00c4:
        r9 = 43;
        goto L_0x0020;
    L_0x00c8:
        r9 = 35;
        goto L_0x0020;
    L_0x00cc:
        r9 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        goto L_0x0020;
    L_0x00d0:
        r9 = 35;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.data.JPushLocalNotification.<clinit>():void");
    }

    private static void a(String str, String str2, JSONObject jSONObject) {
        if (!ai.a(str2)) {
            jSONObject.put(str, str2);
        }
    }

    public boolean equals(Object obj) {
        return this.j == ((JPushLocalNotification) obj).j;
    }

    public long getBroadcastTime() {
        return this.e;
    }

    public long getBuilderId() {
        return this.i;
    }

    public String getContent() {
        return this.f;
    }

    public String getExtras() {
        return this.h;
    }

    public long getNotificationId() {
        return this.j;
    }

    public String getTitle() {
        return this.g;
    }

    public int hashCode() {
        return (this.j).hashCode();
    }

    public void setBroadcastTime(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i < 0 || i2 <= 0 || i2 > 12 || i3 <= 0 || i3 > 31 || i4 < 0 || i4 > 23 || i5 < 0 || i5 > 59 || i6 < 0 || i6 > 59) {
            z.e(z[12], z[13]);
            return;
        }
        Calendar instance = Calendar.getInstance();
        instance.set(i, i2 - 1, i3, i4, i5, i6);
        Date time = instance.getTime();
        long currentTimeMillis = System.currentTimeMillis();
        if (time.getTime() < currentTimeMillis) {
            this.e = currentTimeMillis;
        } else {
            this.e = time.getTime();
        }
    }

    public void setBroadcastTime(long j) {
        this.e = j;
    }

    public void setBroadcastTime(Date date) {
        this.e = date.getTime();
    }

    public void setBuilderId(long j) {
        this.i = j;
    }

    public void setContent(String str) {
        this.f = str;
    }

    public void setExtras(String str) {
        this.h = str;
    }

    public void setNotificationId(long j) {
        this.j = j;
    }

    public void setTitle(String str) {
        this.g = str;
    }

    public String toJSON() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (!ai.a(this.h)) {
                jSONObject2.put(z[9], new JSONObject(this.h));
            }
            a(z[11], this.f, jSONObject2);
            a(z[7], this.g, jSONObject2);
            a(z[11], this.f, jSONObject2);
            jSONObject2.put(z[6], 0);
            jSONObject.put(z[0], jSONObject2);
            a(z[3], this.j, jSONObject);
            a(z[2], this.m, jSONObject);
            a(z[5], this.l, jSONObject);
            jSONObject.put(z[1], this.k);
            jSONObject.put(z[8], this.i);
            jSONObject.put(z[10], 3);
            jSONObject.put(z[4], 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }
}
