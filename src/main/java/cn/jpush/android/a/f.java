package cn.jpush.android.a;

import android.os.Handler;
import android.os.Message;
import cn.jpush.android.util.z;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

final class f extends Handler {
    private static final String[] z;
    final /* synthetic */ d a;
    private float b;
    private JSONArray c;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 6;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "i@F";
        r0 = -1;
        r4 = r3;
    L_0x0008:
        r1 = r1.toCharArray();
        r5 = r1.length;
        r6 = 0;
        r7 = 1;
        if (r5 > r7) goto L_0x002d;
    L_0x0011:
        r7 = r1;
        r8 = r6;
        r11 = r5;
        r5 = r1;
        r1 = r11;
    L_0x0016:
        r10 = r5[r6];
        r9 = r8 % 5;
        switch(r9) {
            case 0: goto L_0x0068;
            case 1: goto L_0x006b;
            case 2: goto L_0x006e;
            case 3: goto L_0x0071;
            default: goto L_0x001d;
        };
    L_0x001d:
        r9 = 123; // 0x7b float:1.72E-43 double:6.1E-322;
    L_0x001f:
        r9 = r9 ^ r10;
        r9 = (char) r9;
        r5[r6] = r9;
        r6 = r8 + 1;
        if (r1 != 0) goto L_0x002b;
    L_0x0027:
        r5 = r7;
        r8 = r6;
        r6 = r1;
        goto L_0x0016;
    L_0x002b:
        r5 = r1;
        r1 = r7;
    L_0x002d:
        if (r5 > r6) goto L_0x0011;
    L_0x002f:
        r5 = new java.lang.String;
        r5.<init>(r1);
        r1 = r5.intern();
        switch(r0) {
            case 0: goto L_0x0043;
            case 1: goto L_0x004b;
            case 2: goto L_0x0053;
            case 3: goto L_0x005b;
            case 4: goto L_0x0063;
            default: goto L_0x003b;
        };
    L_0x003b:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u0019,/0:\u001b'F>>\u00052F>3\b0!8?";
        r0 = 0;
        r3 = r4;
        goto L_0x0008;
    L_0x0043:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0019\u0011\u0015\t[#\r\t\u0013";
        r0 = 1;
        r3 = r4;
        goto L_0x0008;
    L_0x004b:
        r3[r2] = r1;
        r2 = 3;
        r1 = "iUF";
        r0 = 2;
        r3 = r4;
        goto L_0x0008;
    L_0x0053:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u001e7 4/&\t\b\u0018\t\u0003-)3Ai";
        r0 = 3;
        r3 = r4;
        goto L_0x0008;
    L_0x005b:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\n\u001b\n\u0011/&\t\b\u0018\t\u0003-)3Ai^";
        r0 = 4;
        r3 = r4;
        goto L_0x0008;
    L_0x0063:
        r3[r2] = r1;
        z = r4;
        return;
    L_0x0068:
        r9 = 73;
        goto L_0x001f;
    L_0x006b:
        r9 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        goto L_0x001f;
    L_0x006e:
        r9 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        goto L_0x001f;
    L_0x0071:
        r9 = 125; // 0x7d float:1.75E-43 double:6.2E-322;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.f.<clinit>():void");
    }

    public final void handleMessage(Message message) {
        int i = 1;
        if (this.a.l == this) {
            switch (message.what) {
                case 1:
                    this.c = null;
                    this.b = Float.MIN_VALUE;
                    break;
                case 2:
                    break;
                case 5:
                    if (this.a.p == 2) {
                        removeMessages(2);
                        removeMessages(10);
                        this.a.p = 3;
                        if (this.a.h.f()) {
                            this.a.h.d();
                        }
                        JSONArray[] jSONArrayArr = new JSONArray[2];
                        jSONArrayArr[0] = this.c;
                        if (this.a.s.a()) {
                            jSONArrayArr[1] = this.a.s.c();
                        }
                        if (this.c != null) {
                            new StringBuilder(z[5]).append(this.c.toString());
                            z.c();
                            this.a.a(this.c);
                        }
                        if (jSONArrayArr[1] != null) {
                            new StringBuilder(z[4]).append(jSONArrayArr[1].toString());
                            z.c();
                            this.a.b(jSONArrayArr[1]);
                        }
                        d.a(this.a, z[2]);
                        return;
                    }
                    return;
                case 10:
                    if (this.a.p == 1 && !this.a.m) {
                        if (!this.a.h.f()) {
                            if (this.a.g != this.a.h.a()) {
                                i = 0;
                            }
                            if (i == 0) {
                                sendEmptyMessageDelayed(10, (long) d.b);
                                return;
                            }
                        } else if (this.a.k != null && this.a.k.length != 0) {
                            int[] d = this.a.h.d();
                            if (d.length != 0) {
                                if (this.a.k[0] == d[0]) {
                                    int i2;
                                    List<Object> arrayList = new ArrayList(this.a.k.length / 2);
                                    List arrayList2 = new ArrayList(d.length / 2);
                                    int length = this.a.k.length;
                                    for (i2 = 0; i2 < length; i2 += 2) {
                                        arrayList.add(Integer.valueOf(this.a.k[i2]));
                                    }
                                    for (i2 = 0; i2 < d.length; i2 += 2) {
                                        arrayList2.add(Integer.valueOf(d[i2]));
                                    }
                                    i2 = 0;
                                    for (Object contains : arrayList) {
                                        if (arrayList2.contains(contains)) {
                                            i2++;
                                        }
                                    }
                                    int size = arrayList.size() - i2;
                                    int size2 = arrayList2.size() - i2;
                                    if (size + size2 <= i2) {
                                        i = 0;
                                    }
                                    if (i != 0) {
                                        StringBuilder append = new StringBuilder(size).append(z[3]);
                                        append.append(size2).append(z[0]);
                                        append.append(i2);
                                        d.a(this.a, append.toString());
                                        return;
                                    }
                                    return;
                                }
                                d.a(this.a, z[1]);
                            }
                        } else {
                            return;
                        }
                        this.a.e();
                        return;
                    }
                    return;
                default:
                    return;
            }
            if (this.a.p == 2) {
                JSONArray c = this.a.h.c();
                this.a.h.g();
                if (c != null && 1.06535322E9f > this.b) {
                    this.c = c;
                    this.b = 1.06535322E9f;
                }
                sendEmptyMessageDelayed(2, 600);
            }
        }
    }
}
