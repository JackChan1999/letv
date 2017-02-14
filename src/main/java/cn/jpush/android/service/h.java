package cn.jpush.android.service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import cn.jpush.android.data.f;
import cn.jpush.android.util.z;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class h {
    private static h a = null;
    private static ExecutorService b = Executors.newSingleThreadExecutor();
    private static f f = null;
    private static cn.jpush.android.data.h g = new cn.jpush.android.data.h();
    private static Object h = new Object();
    private static final String[] z;
    private Handler c = null;
    private Context d = null;
    private String e = "";

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 14;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "[Nj4&\u001cM|/$\u001b\u0010E\u0016\u001e&vP\u000b\u000e&mN\u0001\u000e";
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
            case 0: goto L_0x00d2;
            case 1: goto L_0x00d6;
            case 2: goto L_0x00da;
            case 3: goto L_0x00de;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 75;
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
            case 12: goto L_0x00b3;
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "\u001bQ{/-\u001c]n/$\u001ba{?;\u0010";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0044:
        r3[r2] = r1;
        r2 = 2;
        r1 = "\u0014N\u000f/";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004c:
        r3[r2] = r1;
        r2 = 3;
        r1 = "\u0016P!,;\u0000Mgh*\u001bZ})\"\u0011\u0010f(?\u0010P{h\u0005:jF\u0000\u00026[\u000f\u0004;a]\u0003\b0wY\u0003\u000f*n]\t\u0013,";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0054:
        r3[r2] = r1;
        r2 = 4;
        r1 = "\u0018[|5*\u0012[";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x005c:
        r3[r2] = r1;
        r2 = 5;
        r1 = "\u0006[a\".\u0007wk";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0064:
        r3[r2] = r1;
        r2 = 6;
        r1 = "\u0005Q|2k\u0011[c'2\u0010Z/|k";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x006c:
        r3[r2] = r1;
        r2 = 7;
        r1 = "\u001cPf2k9Ql'';Q{/-\u001c]n2\"\u001aP/%*\u0006J/#3\u0005J5";
        r0 = 6;
        r3 = r4;
        goto L_0x0009;
    L_0x0074:
        r3[r2] = r1;
        r2 = 8;
        r1 = "\u0016Rj'9U_c*k\u0019Ql''UP`2\"\u0013Wl'?\u001cQaf";
        r0 = 7;
        r3 = r4;
        goto L_0x0009;
    L_0x007d:
        r3[r2] = r1;
        r2 = 9;
        r1 = "?nz5#9Ql'';Q{/-\u001c]n2\"\u001aPL#%\u0001[}";
        r0 = 8;
        r3 = r4;
        goto L_0x0009;
    L_0x0087:
        r3[r2] = r1;
        r2 = 10;
        r1 = "\u0014Zkf\u0007\u001a]n*\u0005\u001aJf \"\u0016_{/$\u001b";
        r0 = 9;
        r3 = r4;
        goto L_0x0009;
    L_0x0092:
        r3[r2] = r1;
        r2 = 11;
        r1 = "\u0001Lf!,\u0010LC\b\u0000\u001cRc\u00169\u001a]j58O\u001e";
        r0 = 10;
        r3 = r4;
        goto L_0x0009;
    L_0x009d:
        r3[r2] = r1;
        r2 = 12;
        r1 = "\u0007[b)=\u0010\u001eC)(\u0014RA)?\u001cXf%*\u0001W`(k";
        r0 = 11;
        r3 = r4;
        goto L_0x0009;
    L_0x00a8:
        r3[r2] = r1;
        r2 = 13;
        r1 = "\u0007[b)=\u0010\u001ec)(\u0014R/%$\u0000P{fqU";
        r0 = 12;
        r3 = r4;
        goto L_0x0009;
    L_0x00b3:
        r3[r2] = r1;
        z = r4;
        r0 = 0;
        a = r0;
        r0 = java.util.concurrent.Executors.newSingleThreadExecutor();
        b = r0;
        r0 = 0;
        f = r0;
        r0 = new cn.jpush.android.data.h;
        r0.<init>();
        g = r0;
        r0 = new java.lang.Object;
        r0.<init>();
        h = r0;
        return;
    L_0x00d2:
        r9 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
        goto L_0x0020;
    L_0x00d6:
        r9 = 62;
        goto L_0x0020;
    L_0x00da:
        r9 = 15;
        goto L_0x0020;
    L_0x00de:
        r9 = 70;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.h.<clinit>():void");
    }

    private h(Context context) {
        z.b();
        this.c = new Handler(Looper.getMainLooper());
        this.d = context;
        this.e = this.d.getPackageName();
    }

    public static h a(Context context) {
        z.b();
        synchronized (h) {
            if (a == null) {
                a = new h(context);
            }
        }
        return a;
    }

    private synchronized void a(long j, long j2) {
        z.b();
        if (j < 0) {
            z.e();
        } else if (this.c != null) {
            Runnable jVar = new j(this, j);
            if (j2 <= 0) {
                z.b();
                this.c.post(jVar);
            } else {
                new StringBuilder(z[6]).append(j2);
                z.b();
                this.c.postDelayed(jVar, j2);
            }
        }
    }

    private void a(Context context, String str, String str2, String str3) {
        z.b();
        Intent intent = new Intent(z[3]);
        intent.putExtra(z[5], str3);
        intent.putExtra(z[2], str2);
        intent.putExtra(z[4], str);
        intent.putExtra(z[1], 1);
        intent.addCategory(str2);
        context.sendOrderedBroadcast(intent, str2 + z[0]);
        z.b();
    }

    private synchronized void e(Context context) {
        Exception e;
        Throwable th;
        z.b();
        Cursor cursor = null;
        Cursor a;
        try {
            if (f == null) {
                f = new f(context);
            }
            f.a(true);
            a = f.a(1, System.currentTimeMillis());
            try {
                if (a.moveToFirst()) {
                    do {
                        f.a(a, g);
                        a(context, g.d(), this.e, "");
                        f.b(g.a(), 0, 0, 0, g.d(), g.f(), g.e());
                    } while (a.moveToNext());
                }
                f.a();
                if (a != null) {
                    a.close();
                }
            } catch (Exception e2) {
                e = e2;
                cursor = a;
                try {
                    z.d(z[9], new StringBuilder(z[11]).append(e.getMessage()).toString());
                    e.printStackTrace();
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    a = cursor;
                    if (a != null) {
                        a.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (a != null) {
                    a.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            z.d(z[9], new StringBuilder(z[11]).append(e.getMessage()).toString());
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th4) {
            th = th4;
            a = null;
            if (a != null) {
                a.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(android.content.Context r17, long r18) {
        /*
        r16 = this;
        monitor-enter(r16);
        r2 = z;	 Catch:{ all -> 0x0083 }
        r3 = 9;
        r2 = r2[r3];	 Catch:{ all -> 0x0083 }
        r3 = z;	 Catch:{ all -> 0x0083 }
        r4 = 12;
        r3 = r3[r4];	 Catch:{ all -> 0x0083 }
        cn.jpush.android.util.z.b(r2, r3);	 Catch:{ all -> 0x0083 }
        r2 = f;	 Catch:{ all -> 0x0083 }
        if (r2 != 0) goto L_0x001d;
    L_0x0014:
        r2 = new cn.jpush.android.data.f;	 Catch:{ all -> 0x0083 }
        r0 = r17;
        r2.<init>(r0);	 Catch:{ all -> 0x0083 }
        f = r2;	 Catch:{ all -> 0x0083 }
    L_0x001d:
        r2 = 0;
        r3 = f;	 Catch:{ Exception -> 0x007c, all -> 0x0086 }
        r4 = 1;
        r3.a(r4);	 Catch:{ Exception -> 0x007c, all -> 0x0086 }
        r3 = f;	 Catch:{ Exception -> 0x007c, all -> 0x0086 }
        r4 = 0;
        r0 = r18;
        r2 = r3.a(r0, r4);	 Catch:{ Exception -> 0x007c, all -> 0x0086 }
        r3 = f;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = g;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3.a(r2, r4);	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3 = g;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3 = r3.b();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        if (r3 <= 0) goto L_0x006f;
    L_0x003c:
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = z;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r5 = 13;
        r4 = r4[r5];	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = g;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = r4.b();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3.append(r4);	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        cn.jpush.android.util.z.b();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3 = f;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r6 = 0;
        r7 = 1;
        r8 = 0;
        r4 = g;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r9 = r4.d();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = g;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r10 = r4.f();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = g;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r12 = r4.e();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r4 = r18;
        r3.b(r4, r6, r7, r8, r9, r10, r12);	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
    L_0x006f:
        r3 = f;	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        r3.a();	 Catch:{ Exception -> 0x007c, all -> 0x0090 }
        if (r2 == 0) goto L_0x0079;
    L_0x0076:
        r2.close();	 Catch:{ all -> 0x0083 }
    L_0x0079:
        r2 = 1;
        monitor-exit(r16);
        return r2;
    L_0x007c:
        r3 = move-exception;
        if (r2 == 0) goto L_0x0079;
    L_0x007f:
        r2.close();	 Catch:{ all -> 0x0083 }
        goto L_0x0079;
    L_0x0083:
        r2 = move-exception;
        monitor-exit(r16);
        throw r2;
    L_0x0086:
        r3 = move-exception;
        r14 = r3;
        r3 = r2;
        r2 = r14;
    L_0x008a:
        if (r3 == 0) goto L_0x008f;
    L_0x008c:
        r3.close();	 Catch:{ all -> 0x0083 }
    L_0x008f:
        throw r2;	 Catch:{ all -> 0x0083 }
    L_0x0090:
        r3 = move-exception;
        r14 = r3;
        r3 = r2;
        r2 = r14;
        goto L_0x008a;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.h.a(android.content.Context, long):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized boolean a(android.content.Context r18, cn.jpush.android.data.JPushLocalNotification r19) {
        /*
        r17 = this;
        monitor-enter(r17);
        r2 = z;	 Catch:{ all -> 0x00a4 }
        r3 = 9;
        r2 = r2[r3];	 Catch:{ all -> 0x00a4 }
        r3 = z;	 Catch:{ all -> 0x00a4 }
        r4 = 10;
        r3 = r3[r4];	 Catch:{ all -> 0x00a4 }
        cn.jpush.android.util.z.b(r2, r3);	 Catch:{ all -> 0x00a4 }
        r12 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x00a4 }
        r2 = r19.getBroadcastTime();	 Catch:{ all -> 0x00a4 }
        r14 = r2 - r12;
        r2 = cn.jpush.android.service.ServiceInterface.e(r18);	 Catch:{ all -> 0x00a4 }
        if (r2 == 0) goto L_0x0023;
    L_0x0020:
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x00a4 }
    L_0x0023:
        r2 = f;	 Catch:{ all -> 0x00a4 }
        if (r2 != 0) goto L_0x0030;
    L_0x0027:
        r2 = new cn.jpush.android.data.f;	 Catch:{ all -> 0x00a4 }
        r0 = r18;
        r2.<init>(r0);	 Catch:{ all -> 0x00a4 }
        f = r2;	 Catch:{ all -> 0x00a4 }
    L_0x0030:
        r2 = 0;
        r3 = f;	 Catch:{ Exception -> 0x009d, all -> 0x00a7 }
        r4 = 1;
        r3.a(r4);	 Catch:{ Exception -> 0x009d, all -> 0x00a7 }
        r3 = f;	 Catch:{ Exception -> 0x009d, all -> 0x00a7 }
        r4 = r19.getNotificationId();	 Catch:{ Exception -> 0x009d, all -> 0x00a7 }
        r6 = 0;
        r2 = r3.a(r4, r6);	 Catch:{ Exception -> 0x009d, all -> 0x00a7 }
        r3 = f;	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r4 = g;	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r3.a(r2, r4);	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r3 = g;	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r4 = r3.a();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r6 = r19.getNotificationId();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r3 == 0) goto L_0x0088;
    L_0x0057:
        r3 = f;	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r4 = r19.getNotificationId();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r6 = 1;
        r7 = 0;
        r8 = 0;
        r9 = r19.toJSON();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r10 = r19.getBroadcastTime();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r3.a(r4, r6, r7, r8, r9, r10, r12);	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
    L_0x006b:
        r3 = f;	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r3.a();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        if (r2 == 0) goto L_0x0075;
    L_0x0072:
        r2.close();	 Catch:{ all -> 0x00a4 }
    L_0x0075:
        r2 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
        r2 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1));
        if (r2 >= 0) goto L_0x00b3;
    L_0x007c:
        r2 = r19.getNotificationId();	 Catch:{ all -> 0x00a4 }
        r0 = r17;
        r0.a(r2, r14);	 Catch:{ all -> 0x00a4 }
        r2 = 1;
    L_0x0086:
        monitor-exit(r17);
        return r2;
    L_0x0088:
        r3 = f;	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r4 = r19.getNotificationId();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r6 = 1;
        r7 = 0;
        r8 = 0;
        r9 = r19.toJSON();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r10 = r19.getBroadcastTime();	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        r3.b(r4, r6, r7, r8, r9, r10, r12);	 Catch:{ Exception -> 0x009d, all -> 0x00b5 }
        goto L_0x006b;
    L_0x009d:
        r3 = move-exception;
        if (r2 == 0) goto L_0x0075;
    L_0x00a0:
        r2.close();	 Catch:{ all -> 0x00a4 }
        goto L_0x0075;
    L_0x00a4:
        r2 = move-exception;
        monitor-exit(r17);
        throw r2;
    L_0x00a7:
        r3 = move-exception;
        r16 = r3;
        r3 = r2;
        r2 = r16;
    L_0x00ad:
        if (r3 == 0) goto L_0x00b2;
    L_0x00af:
        r3.close();	 Catch:{ all -> 0x00a4 }
    L_0x00b2:
        throw r2;	 Catch:{ all -> 0x00a4 }
    L_0x00b3:
        r2 = 1;
        goto L_0x0086;
    L_0x00b5:
        r3 = move-exception;
        r16 = r3;
        r3 = r2;
        r2 = r16;
        goto L_0x00ad;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.h.a(android.content.Context, cn.jpush.android.data.JPushLocalNotification):boolean");
    }

    public final synchronized void b(Context context) {
        z.b(z[9], z[8]);
        if (f == null) {
            f = new f(context);
        }
        f.a(true);
        if (f.b()) {
            z.b();
        }
        f.a();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void c(android.content.Context r10) {
        /*
        r9 = this;
        monitor-enter(r9);
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x006e }
        r0 = f;	 Catch:{ Exception -> 0x0059 }
        if (r0 != 0) goto L_0x000f;
    L_0x0008:
        r0 = new cn.jpush.android.data.f;	 Catch:{ Exception -> 0x0059 }
        r0.<init>(r10);	 Catch:{ Exception -> 0x0059 }
        f = r0;	 Catch:{ Exception -> 0x0059 }
    L_0x000f:
        r0 = 0;
        r1 = f;	 Catch:{ Exception -> 0x0052, all -> 0x0071 }
        r2 = 0;
        r1.a(r2);	 Catch:{ Exception -> 0x0052, all -> 0x0071 }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0052, all -> 0x0071 }
        r1 = f;	 Catch:{ Exception -> 0x0052, all -> 0x0071 }
        r4 = 300000; // 0x493e0 float:4.2039E-40 double:1.482197E-318;
        r0 = r1.a(r2, r4);	 Catch:{ Exception -> 0x0052, all -> 0x0071 }
        r1 = r0.moveToFirst();	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        if (r1 == 0) goto L_0x0046;
    L_0x0029:
        r1 = f;	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r4 = g;	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r1.a(r0, r4);	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r1 = g;	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r4 = r1.a();	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r1 = g;	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r6 = r1.f();	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r6 = r6 - r2;
        r9.a(r4, r6);	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r1 = r0.moveToNext();	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        if (r1 != 0) goto L_0x0029;
    L_0x0046:
        r1 = f;	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        r1.a();	 Catch:{ Exception -> 0x0052, all -> 0x007b }
        if (r0 == 0) goto L_0x0050;
    L_0x004d:
        r0.close();	 Catch:{ Exception -> 0x0059 }
    L_0x0050:
        monitor-exit(r9);
        return;
    L_0x0052:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0050;
    L_0x0055:
        r0.close();	 Catch:{ Exception -> 0x0059 }
        goto L_0x0050;
    L_0x0059:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006e }
        r2 = z;	 Catch:{ all -> 0x006e }
        r3 = 7;
        r2 = r2[r3];	 Catch:{ all -> 0x006e }
        r1.<init>(r2);	 Catch:{ all -> 0x006e }
        r1.append(r0);	 Catch:{ all -> 0x006e }
        cn.jpush.android.util.z.b();	 Catch:{ all -> 0x006e }
        r0.printStackTrace();	 Catch:{ all -> 0x006e }
        goto L_0x0050;
    L_0x006e:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x0071:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
    L_0x0075:
        if (r1 == 0) goto L_0x007a;
    L_0x0077:
        r1.close();	 Catch:{ Exception -> 0x0059 }
    L_0x007a:
        throw r0;	 Catch:{ Exception -> 0x0059 }
    L_0x007b:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.service.h.c(android.content.Context):void");
    }

    public final void d(Context context) {
        z.b();
        b.execute(new i(this, context));
    }
}
