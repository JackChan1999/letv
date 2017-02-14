package cn.jpush.android.service;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import cn.jpush.android.helpers.ConnectingHelper;
import cn.jpush.android.util.z;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public final class k implements Runnable {
    public static AtomicLong a = new AtomicLong(0);
    public static AtomicBoolean b = new AtomicBoolean(false);
    private static final String[] z;
    private Context c;
    private Handler d;
    private boolean e;
    private volatile boolean f = false;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 20;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "Bw,@\u001d~27SOS~7F\n0x6\\Os}6[\nsf1Z\u00010?x";
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
            case 0: goto L_0x010c;
            case 1: goto L_0x0110;
            case 2: goto L_0x0114;
            case 3: goto L_0x0118;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
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
            case 8: goto L_0x0088;
            case 9: goto L_0x0093;
            case 10: goto L_0x009e;
            case 11: goto L_0x00a9;
            case 12: goto L_0x00b4;
            case 13: goto L_0x00bf;
            case 14: goto L_0x00ca;
            case 15: goto L_0x00d5;
            case 16: goto L_0x00e0;
            case 17: goto L_0x00eb;
            case 18: goto L_0x00f6;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "Qq,\\\u0000~2u\u0015\f|}+P,|6P\fd{7[O=2;Z\u0001~w;A\u0006|b";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "^w,B\u0000by1[\bS~1P\u0001d";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "Qq,\\\u0000~2u\u0015\u001bbk\u000bA\u0000`2u\u0015\f|6P\fd{7[U";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "E|3[\u0000g|xt\f{2*P\u001eew+AO=2;X\u000b*";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "Qq,\\\u0000~2u\u0015\u0000~^7R\buv\u0011[O=2;Z\u0001~w;A\u0006|b";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "R`=T\u00040`=V\nyd1[\b0p!\u0015\u0018q|,f\u001bbx\u0018Os}6[\nsf1Z\u0001*";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "Qq,\\\u0000~2u\u0015\u001duq=\\\u0019uv\u001bZ\u0002}s6QO=2;X\u000b*";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "Qq,\\\u0000~2u\u0015\u0000~^7R\u0006~T9\\\u0003uvx\u0018Obw+E,v=\u000f";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "}Q7[\u0001uq,\\\u0000~21FObw+P\u001b0f7\u0015_0e0P\u00010|=A\u0018`3\u0015\u0003ya,P\u0001y|?\u001bOR`=T\u00040|7BA";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0088:
        r3[r2] = r1;
        r2 = 10;
        r1 = "Bw;P\u0006fw<\u0015\nb`7GObw+E\u0000~a=\u0015B0q7Q\n*";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0093:
        r3[r2] = r1;
        r2 = 11;
        r1 = "Bw;P\u0006fw<\u0015\rif=FO=24P\u0001*";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009e:
        r3[r2] = r1;
        r2 = 12;
        r1 = "Uj;P\u001fd{7[Obw;P\u0006fw<\u001bO^}/\u0015\rbw9^O=2*P\u001b*";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a9:
        r3[r2] = r1;
        r2 = 13;
        r1 = "^w,B\u0000byxY\u0006cf=[\u0006~uv\u001bA";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b4:
        r3[r2] = r1;
        r2 = 14;
        r1 = "Rw?\\\u00010f7\u0015\u001de|x\\\u00010Q7[\u0001uq,\\\u0001wF0G\nqvx\u0018Oyvb";
        r0 = 13;
        r3 = r4;
        goto L_0x0009;
    L_0x00bf:
        r3[r2] = r1;
        r2 = 15;
        r1 = "E|0T\u0001t~=QObw+E\u0000~a=\u0015\f5T\u0001t2u\u0015";
        r0 = 14;
        r3 = r4;
        goto L_0x0009;
    L_0x00ca:
        r3[r2] = r1;
        r2 = 16;
        r1 = "<2(^\b*";
        r0 = 15;
        r3 = r4;
        goto L_0x0009;
    L_0x00d5:
        r3[r2] = r1;
        r2 = 17;
        r1 = "S`=T\u001buvxV\u0000~|=V\u001by}6\u0015B0";
        r0 = 16;
        r3 = r4;
        goto L_0x0009;
    L_0x00e0:
        r3[r2] = r1;
        r2 = 18;
        r1 = "<2=G\u001d`b";
        r0 = 17;
        r3 = r4;
        goto L_0x0009;
    L_0x00eb:
        r3[r2] = r1;
        r2 = 19;
        r1 = "<2;Z\u0001~w;A\u0006|b";
        r0 = 18;
        r3 = r4;
        goto L_0x0009;
    L_0x00f6:
        r3[r2] = r1;
        z = r4;
        r0 = new java.util.concurrent.atomic.AtomicLong;
        r2 = 0;
        r0.<init>(r2);
        a = r0;
        r0 = new java.util.concurrent.atomic.AtomicBoolean;
        r1 = 0;
        r0.<init>(r1);
        b = r0;
        return;
    L_0x010c:
        r9 = 16;
        goto L_0x0020;
    L_0x0110:
        r9 = 18;
        goto L_0x0020;
    L_0x0114:
        r9 = 88;
        goto L_0x0020;
    L_0x0118:
        r9 = 53;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.k.<clinit>():void");
    }

    public k(Context context, Handler handler, boolean z) {
        this.c = context;
        this.d = handler;
        this.e = false;
    }

    private void b() {
        z.b(z[2], new StringBuilder(z[1]).append(a.get()).toString());
        if (0 != a.get()) {
            try {
                b.set(true);
                a.set((long) PushProtocol.Close(a.get()));
                z.b(z[2], new StringBuilder(z[0]).append(a.get()).toString());
                b.set(false);
            } catch (Exception e) {
                z.h();
            }
            ConnectingHelper.sendConnectionToHandler(Message.obtain(this.d, 7301), a.get());
            return;
        }
        z.b();
    }

    public final void a() {
        z.b(z[2], new StringBuilder(z[3]).append(a.get()).toString());
        this.f = true;
        PushProtocol.Stop(a.get());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
        r13 = this;
        r12 = 86400; // 0x15180 float:1.21072E-40 double:4.26873E-319;
        r2 = 1;
        r11 = 10;
        r3 = 0;
        r10 = 2;
        r0 = z;
        r0 = r0[r10];
        r1 = new java.lang.StringBuilder;
        r4 = z;
        r5 = 14;
        r4 = r4[r5];
        r1.<init>(r4);
        r4 = java.lang.Thread.currentThread();
        r4 = r4.getId();
        r1 = r1.append(r4);
        r1 = r1.toString();
        cn.jpush.android.util.z.c(r0, r1);
        r0 = a;
        r4 = cn.jpush.android.service.PushProtocol.InitConn();
        r0.set(r4);
        r0 = new java.lang.StringBuilder;
        r1 = z;
        r4 = 17;
        r1 = r1[r4];
        r0.<init>(r1);
        r1 = a;
        r4 = r1.get();
        r0.append(r4);
        cn.jpush.android.util.z.b();
        r0 = r13.c;
        r0 = cn.jpush.android.helpers.ConnectingHelper.sendSis(r0);
        if (r0 == 0) goto L_0x0055;
    L_0x0052:
        r0.configure();
    L_0x0055:
        r1 = r13.c;
        r4 = a;
        r4 = r4.get();
        r0 = cn.jpush.android.helpers.ConnectingHelper.openConnection(r1, r4, r0);
        if (r0 != 0) goto L_0x0067;
    L_0x0063:
        r13.b();
    L_0x0066:
        return;
    L_0x0067:
        r0 = cn.jpush.android.a.u();
        if (r0 != 0) goto L_0x0097;
    L_0x006d:
        r0 = r13.c;
        r1 = a;
        r4 = r1.get();
        r1 = r13.e;
        r0 = cn.jpush.android.helpers.ConnectingHelper.register(r0, r4, r1);
        if (r0 != 0) goto L_0x0097;
    L_0x007d:
        r0 = cn.jpush.android.a.n();
        if (r12 != r0) goto L_0x0093;
    L_0x0083:
        cn.jpush.android.util.z.a();
        r0 = r13.d;
        r1 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;
        r2 = 100;
        r0.sendEmptyMessageDelayed(r1, r2);
    L_0x008f:
        r13.b();
        goto L_0x0066;
    L_0x0093:
        cn.jpush.android.util.z.a();
        goto L_0x008f;
    L_0x0097:
        r0 = r13.c;
        r1 = a;
        r4 = r1.get();
        r0 = cn.jpush.android.helpers.ConnectingHelper.login(r0, r4);
        if (r0 >= 0) goto L_0x00a9;
    L_0x00a5:
        r13.b();
        goto L_0x0066;
    L_0x00a9:
        if (r0 <= 0) goto L_0x00d1;
    L_0x00ab:
        r1 = a;
        r2 = r1.get();
        r1 = new java.lang.StringBuilder;
        r4 = z;
        r5 = 8;
        r4 = r4[r5];
        r1.<init>(r4);
        r1.append(r0);
        cn.jpush.android.util.z.a();
        r0 = r13.d;
        r1 = 7306; // 0x1c8a float:1.0238E-41 double:3.6096E-320;
        r0 = android.os.Message.obtain(r0, r1);
        cn.jpush.android.helpers.ConnectingHelper.sendConnectionToHandler(r0, r2);
        r13.b();
        goto L_0x0066;
    L_0x00d1:
        r0 = 0;
        r4 = a;
        r4 = r4.get();
        r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x017f;
    L_0x00dd:
        r0 = a;
        r0 = r0.get();
        r4 = z;
        r4 = r4[r10];
        r5 = new java.lang.StringBuilder;
        r6 = z;
        r7 = 5;
        r6 = r6[r7];
        r5.<init>(r6);
        r5 = r5.append(r0);
        r5 = r5.toString();
        cn.jpush.android.util.z.a(r4, r5);
        r4 = r13.d;
        r5 = 7304; // 0x1c88 float:1.0235E-41 double:3.6087E-320;
        r4 = android.os.Message.obtain(r4, r5);
        cn.jpush.android.helpers.ConnectingHelper.sendConnectionToHandler(r4, r0);
        r0 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r4 = new byte[r0];
    L_0x010b:
        r0 = r13.f;
        if (r0 != 0) goto L_0x02c7;
    L_0x010f:
        r0 = z;
        r0 = r0[r10];
        r1 = z;
        r5 = 13;
        r1 = r1[r5];
        cn.jpush.android.util.z.b(r0, r1);
        r0 = a;
        r0 = r0.get();
        r0 = cn.jpush.android.service.PushProtocol.RecvPush(r0, r4, r12);
        r1 = z;
        r1 = r1[r10];
        r5 = new java.lang.StringBuilder;
        r6 = z;
        r7 = 11;
        r6 = r6[r7];
        r5.<init>(r6);
        r5 = r5.append(r0);
        r6 = z;
        r7 = 19;
        r6 = r6[r7];
        r5 = r5.append(r6);
        r6 = a;
        r6 = r6.get();
        r5 = r5.append(r6);
        r6 = z;
        r7 = 16;
        r6 = r6[r7];
        r5 = r5.append(r6);
        r6 = cn.jpush.android.e.c;
        r5 = r5.append(r6);
        r5 = r5.toString();
        cn.jpush.android.util.z.b(r1, r5);
        r6 = 0;
        r1 = a;
        r8 = r1.get();
        r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r1 != 0) goto L_0x0184;
    L_0x0170:
        r0 = z;
        r0 = r0[r10];
        r1 = z;
        r2 = 9;
        r1 = r1[r2];
        cn.jpush.android.util.z.d(r0, r1);
        goto L_0x0066;
    L_0x017f:
        cn.jpush.android.util.z.d();
        goto L_0x0066;
    L_0x0184:
        if (r0 <= 0) goto L_0x02a4;
    L_0x0186:
        r5 = r13.c;
        r6 = new byte[r0];
        java.lang.System.arraycopy(r4, r3, r6, r3, r0);
        r1 = cn.jpush.b.a.a.d.a(r6);
        if (r1 != 0) goto L_0x0198;
    L_0x0193:
        cn.jpush.android.util.z.e();
        goto L_0x010b;
    L_0x0198:
        r0 = z;
        r0 = r0[r10];
        r7 = new java.lang.StringBuilder;
        r8 = z;
        r9 = 7;
        r8 = r8[r9];
        r7.<init>(r8);
        r8 = r1.d();
        r7 = r7.append(r8);
        r7 = r7.toString();
        cn.jpush.android.util.z.b(r0, r7);
        r0 = z;
        r0 = r0[r10];
        r7 = r1.toString();
        cn.jpush.android.util.z.a(r0, r7);
        r0 = r1.d();
        switch(r0) {
            case 3: goto L_0x020b;
            case 19: goto L_0x0223;
            case 100: goto L_0x020d;
            default: goto L_0x01c7;
        };
    L_0x01c7:
        r0 = r3;
    L_0x01c8:
        if (r0 != 0) goto L_0x01db;
    L_0x01ca:
        r0 = r13.d;
        r7 = 7302; // 0x1c86 float:1.0232E-41 double:3.6077E-320;
        r0 = android.os.Message.obtain(r0, r7, r1);
        r7 = a;
        r8 = r7.get();
        cn.jpush.android.helpers.ConnectingHelper.sendConnectionToHandler(r0, r8);
    L_0x01db:
        r0 = r1.g;
        if (r0 == 0) goto L_0x0234;
    L_0x01df:
        r0 = z;
        r0 = r0[r10];
        r5 = new java.lang.StringBuilder;
        r6 = z;
        r6 = r6[r11];
        r5.<init>(r6);
        r6 = r1.g;
        r5 = r5.append(r6);
        r6 = z;
        r7 = 18;
        r6 = r6[r7];
        r5 = r5.append(r6);
        r1 = r1.h;
        r1 = r5.append(r1);
        r1 = r1.toString();
        cn.jpush.android.util.z.d(r0, r1);
        goto L_0x010b;
    L_0x020b:
        r0 = r2;
        goto L_0x01c8;
    L_0x020d:
        r0 = r1;
        r0 = (cn.jpush.b.a.a.c) r0;
        r0 = r0.a();
        if (r0 != 0) goto L_0x021a;
    L_0x0216:
        cn.jpush.android.util.z.e();
        goto L_0x01c7;
    L_0x021a:
        r0 = r0.b();
        r0 = cn.jpush.android.helpers.h.a(r0);
        goto L_0x01c8;
    L_0x0223:
        r0 = r1;
        r0 = (cn.jpush.b.a.a.a) r0;
        r7 = r0.a();
        if (r7 == r11) goto L_0x0232;
    L_0x022c:
        r0 = r0.a();
        if (r0 != r10) goto L_0x01c7;
    L_0x0232:
        r0 = r2;
        goto L_0x01c8;
    L_0x0234:
        r0 = r1.d();
        switch(r0) {
            case 3: goto L_0x0252;
            case 10: goto L_0x029f;
            case 19: goto L_0x025f;
            case 100: goto L_0x0296;
            default: goto L_0x023b;
        };
    L_0x023b:
        r0 = new java.lang.StringBuilder;
        r5 = z;
        r6 = 15;
        r5 = r5[r6];
        r0.<init>(r5);
        r1 = r1.d();
        r0.append(r1);
        cn.jpush.android.util.z.d();
        goto L_0x010b;
    L_0x0252:
        r0 = r13.d;
        r6 = a;
        r6 = r6.get();
        cn.jpush.android.helpers.f.a(r5, r0, r6, r1);
        goto L_0x010b;
    L_0x025f:
        r0 = a;
        r6 = r0.get();
        r1 = (cn.jpush.b.a.a.a) r1;
        r0 = r1.a();
        if (r0 != r10) goto L_0x027d;
    L_0x026d:
        cn.jpush.android.util.z.b();
        r0 = r13.d;
        r1 = 7303; // 0x1c87 float:1.0234E-41 double:3.608E-320;
        r0 = android.os.Message.obtain(r0, r1);
        cn.jpush.android.helpers.ConnectingHelper.sendConnectionToHandler(r0, r6);
        goto L_0x010b;
    L_0x027d:
        if (r0 != r11) goto L_0x0284;
    L_0x027f:
        cn.jpush.android.util.z.b();
        goto L_0x010b;
    L_0x0284:
        r1 = new java.lang.StringBuilder;
        r5 = z;
        r6 = 4;
        r5 = r5[r6];
        r1.<init>(r5);
        r1.append(r0);
        cn.jpush.android.util.z.d();
        goto L_0x010b;
    L_0x0296:
        r0 = r13.c;
        r5 = r13.d;
        cn.jpush.b.a.b.t.a(r0, r5, r1, r6);
        goto L_0x010b;
    L_0x029f:
        cn.jpush.android.util.z.d();
        goto L_0x010b;
    L_0x02a4:
        r1 = -994; // 0xfffffffffffffc1e float:NaN double:NaN;
        if (r0 != r1) goto L_0x02ad;
    L_0x02a8:
        cn.jpush.android.util.z.b();
        goto L_0x010b;
    L_0x02ad:
        r1 = z;
        r1 = r1[r10];
        r2 = new java.lang.StringBuilder;
        r3 = z;
        r4 = 12;
        r3 = r3[r4];
        r2.<init>(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        cn.jpush.android.util.z.b(r1, r0);
    L_0x02c7:
        r0 = r13.f;
        if (r0 == 0) goto L_0x02ea;
    L_0x02cb:
        r0 = z;
        r0 = r0[r10];
        r1 = new java.lang.StringBuilder;
        r2 = z;
        r3 = 6;
        r2 = r2[r3];
        r1.<init>(r2);
        r2 = a;
        r2 = r2.get();
        r1 = r1.append(r2);
        r1 = r1.toString();
        cn.jpush.android.util.z.b(r0, r1);
    L_0x02ea:
        r13.b();
        goto L_0x0066;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.k.run():void");
    }
}
