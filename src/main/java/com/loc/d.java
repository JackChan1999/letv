package com.loc;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import com.amap.api.location.APSServiceBase;
import com.autonavi.aps.amapapi.model.AmapLoc;
import com.letv.core.constant.ShareConstant;
import com.loc.v.a;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: APSServiceCore */
public class d implements APSServiceBase {
    Context a;
    boolean b = false;
    String c = null;
    b d = new b(this, this);
    a e = null;
    boolean f = false;
    Vector<Messenger> g = new Vector();
    volatile boolean h = false;
    boolean i = false;
    Object j = new Object();
    Looper k;
    AmapLoc l;
    long m = bw.b();
    JSONObject n = new JSONObject();
    AmapLoc o;
    ServerSocket p = null;
    boolean q = false;
    Socket r = null;
    boolean s = false;
    c t;
    private volatile boolean u = false;
    private boolean v = false;
    private int w = 0;
    private boolean x = false;
    private boolean y = false;
    private aw z = null;

    public d(Context context) {
        this.a = context.getApplicationContext();
    }

    public Handler getHandler() {
        return this.d;
    }

    public void onCreate() {
        try {
            this.z = new aw();
            this.c = this.a.getApplicationContext().getPackageName();
            this.h = true;
            this.e = new a(this);
            this.e.setName("serviceThread");
            this.e.start();
        } catch (Throwable th) {
        }
    }

    private AmapLoc a(int i, String str) {
        try {
            AmapLoc amapLoc = new AmapLoc();
            amapLoc.b(i);
            amapLoc.b(str);
            return amapLoc;
        } catch (Throwable th) {
            return null;
        }
    }

    private void a(Messenger messenger) {
        try {
            if (bu.q() && messenger != null) {
                bu.a("0");
                Message obtain = Message.obtain();
                obtain.what = 100;
                messenger.send(obtain);
            }
            if (bu.a()) {
                this.z.a();
            }
            if (bu.d() && !this.x) {
                this.x = true;
                this.d.sendEmptyMessage(4);
            }
            if (bu.f() && !this.y) {
                this.y = true;
                this.d.sendEmptyMessage(5);
            }
        } catch (Throwable th) {
        }
    }

    private boolean c() {
        boolean z;
        synchronized (this.j) {
            z = this.h;
        }
        return z;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 0;
    }

    private AmapLoc a(boolean z) throws Exception {
        return this.z.a(z);
    }

    private void d() {
        try {
            this.z.g();
        } catch (Throwable th) {
        }
    }

    private void e() {
        try {
            if (!this.v) {
                this.v = true;
                this.z.b(this.a);
            }
        } catch (Throwable th) {
        }
    }

    private void f() {
        try {
            if (!this.u) {
                g();
            }
        } catch (Throwable th) {
            this.u = false;
        }
    }

    private void g() {
        v vVar = null;
        Looper.prepare();
        this.k = Looper.myLooper();
        e.a(this.a);
        this.z.a(this.a);
        this.z.a("api_serverSDK_130905##S128DF1572465B890OE3F7A13167KLEI##" + m.b(this.a) + ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER_OLD + m.e(this.a));
        try {
            vVar = new a("loc", "2.3.0", "AMAP_Location_SDK_Android 2.3.0").a(e.b()).a();
        } catch (l e) {
        }
        try {
            String a = o.a(this.a, vVar, null, true);
            try {
                this.n.put("key", m.e(this.a));
                this.n.put("X-INFO", a);
                this.n.put("User-Agent", "AMAP_Location_SDK_Android 2.3.0");
                this.n.put("netloc", "0");
                this.n.put("gpsstatus", "0");
                this.n.put("nbssid", "0");
                if (!this.n.has("reversegeo")) {
                    this.n.put("reversegeo", true);
                }
                if (!this.n.has("isOffset")) {
                    this.n.put("isOffset", true);
                }
                if (!this.n.has("wait1stwifi")) {
                    this.n.put("wait1stwifi", "0");
                }
                this.n.put("autoup", "0");
                this.n.put("upcolmobile", 1);
                this.n.put("enablegetreq", 1);
                this.n.put("wifiactivescan", 1);
            } catch (JSONException e2) {
            }
            this.z.a(this.n);
            this.u = true;
        } catch (Throwable th) {
        }
    }

    private void a(JSONObject jSONObject) {
        try {
            if (this.z != null) {
                this.z.a(jSONObject);
            }
        } catch (Throwable th) {
        }
    }

    public synchronized void a() {
        try {
            if (!this.s) {
                this.t = new c(this);
                this.t.start();
                this.s = true;
            }
        } catch (Throwable th) {
        }
    }

    public synchronized void b() {
        try {
            if (this.p != null) {
                this.p.close();
            }
            if (this.r != null) {
                this.r.close();
            }
        } catch (IOException e) {
        }
        try {
            this.p = null;
            this.t = null;
            this.s = false;
            this.q = false;
        } catch (Throwable th) {
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(java.net.Socket r12) {
        /*
        r11 = this;
        r3 = 0;
        r4 = 0;
        r9 = 1;
        if (r12 != 0) goto L_0x0006;
    L_0x0005:
        return;
    L_0x0006:
        r1 = 30000; // 0x7530 float:4.2039E-41 double:1.4822E-319;
        r0 = "jsonp1";
        java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x026d }
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x032e, all -> 0x0324 }
        r5 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x032e, all -> 0x0324 }
        r6 = r12.getInputStream();	 Catch:{ Throwable -> 0x032e, all -> 0x0324 }
        r7 = "UTF-8";
        r5.<init>(r6, r7);	 Catch:{ Throwable -> 0x032e, all -> 0x0324 }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x032e, all -> 0x0324 }
        r5 = r2.readLine();	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r5 == 0) goto L_0x0095;
    L_0x0023:
        r6 = r5.length();	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r6 <= 0) goto L_0x0095;
    L_0x0029:
        r6 = " ";
        r5 = r5.split(r6);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r5 == 0) goto L_0x0095;
    L_0x0031:
        r6 = r5.length;	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r6 <= r9) goto L_0x0095;
    L_0x0034:
        r6 = 1;
        r5 = r5[r6];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r6 = "\\?";
        r5 = r5.split(r6);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r5 == 0) goto L_0x0095;
    L_0x003f:
        r6 = r5.length;	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r6 <= r9) goto L_0x0095;
    L_0x0042:
        r6 = 1;
        r5 = r5[r6];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r6 = "&";
        r5 = r5.split(r6);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r5 == 0) goto L_0x0095;
    L_0x004d:
        r6 = r5.length;	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r6 <= 0) goto L_0x0095;
    L_0x0050:
        r6 = r5.length;	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r4 >= r6) goto L_0x0095;
    L_0x0053:
        r6 = r5[r4];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r7 = "=";
        r6 = r6.split(r7);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r6 == 0) goto L_0x0092;
    L_0x005d:
        r7 = r6.length;	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r7 <= r9) goto L_0x0092;
    L_0x0060:
        r7 = "to";
        r8 = 0;
        r8 = r6[r8];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r7 = r7.equals(r8);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r7 == 0) goto L_0x0073;
    L_0x006c:
        r1 = 1;
        r1 = r6[r1];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r1 = java.lang.Integer.parseInt(r1);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
    L_0x0073:
        r7 = "callback";
        r8 = 0;
        r8 = r6[r8];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r7 = r7.equals(r8);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r7 == 0) goto L_0x0081;
    L_0x007e:
        r7 = 1;
        r0 = r6[r7];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
    L_0x0081:
        r7 = "_";
        r8 = 0;
        r8 = r6[r8];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        r7 = r7.equals(r8);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        if (r7 == 0) goto L_0x0092;
    L_0x008c:
        r7 = 1;
        r6 = r6[r7];	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
        java.lang.Long.parseLong(r6);	 Catch:{ Throwable -> 0x0332, all -> 0x022e }
    L_0x0092:
        r4 = r4 + 1;
        goto L_0x0050;
    L_0x0095:
        r10 = r0;
        r0 = r1;
        r1 = r10;
        r4 = com.loc.e.j;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        com.loc.e.j = r0;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r11.o;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        if (r0 == 0) goto L_0x00b1;
    L_0x00a4:
        r0 = r11.o;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r8 = r0.k();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r6 = r6 - r8;
        r8 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r0 <= 0) goto L_0x0115;
    L_0x00b1:
        r0 = r11.a;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = com.loc.bw.d(r0);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        if (r0 != 0) goto L_0x0115;
    L_0x00b9:
        r0 = r11.z;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = 0;
        r0 = r0.a(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r11.o = r0;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r11.o;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r0.a();	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        if (r0 == 0) goto L_0x0113;
    L_0x00ca:
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0.<init>();	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = "&&";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = "({'package':'";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = r11.c;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = "','error_code':";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = r11.o;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = r5.a();	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = ",'error':'";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = r11.o;	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = r5.c();	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r5 = "'})";
        r0 = r0.append(r5);	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
        r3 = r0.toString();	 Catch:{ Exception -> 0x01b7, all -> 0x022a }
    L_0x0113:
        com.loc.e.j = r4;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
    L_0x0115:
        if (r3 != 0) goto L_0x0175;
    L_0x0117:
        r0 = r11.o;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        if (r0 != 0) goto L_0x0273;
    L_0x011b:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0.<init>();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "&&";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "({'package':'";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r11.c;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "','error_code':8,'error':'unknown error'})";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r3 = r0.toString();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
    L_0x0144:
        r0 = r11.a;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = com.loc.bw.d(r0);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        if (r0 == 0) goto L_0x0175;
    L_0x014c:
        r0 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0.<init>();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "&&";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "({'package':'";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r11.c;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "','error_code':36,'error':'app is background'})";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r3 = r0.toString();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
    L_0x0175:
        r0 = new java.io.PrintStream;	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r1 = r12.getOutputStream();	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r4 = 1;
        r5 = "UTF-8";
        r0.<init>(r1, r4, r5);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r1 = "HTTP/1.0 200 OK";
        r0.println(r1);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r1.<init>();	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r4 = "Content-Length:";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r4 = "UTF-8";
        r4 = r3.getBytes(r4);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r4 = r4.length;	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r1 = r1.toString();	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r0.println(r1);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r0.println();	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r0.println(r3);	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r0.close();	 Catch:{ Exception -> 0x02e3, all -> 0x02ef }
        r2.close();	 Catch:{ Exception -> 0x01b4 }
        r12.close();	 Catch:{ Exception -> 0x01b4 }
        goto L_0x0005;
    L_0x01b4:
        r0 = move-exception;
        goto L_0x0005;
    L_0x01b7:
        r0 = move-exception;
        com.loc.e.j = r4;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        goto L_0x0115;
    L_0x01bc:
        r0 = move-exception;
        r0 = r1;
        r1 = r2;
    L_0x01bf:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0328 }
        r2.<init>();	 Catch:{ all -> 0x0328 }
        r2 = r2.append(r0);	 Catch:{ all -> 0x0328 }
        r4 = "&&";
        r2 = r2.append(r4);	 Catch:{ all -> 0x0328 }
        r0 = r2.append(r0);	 Catch:{ all -> 0x0328 }
        r2 = "({'package':'";
        r0 = r0.append(r2);	 Catch:{ all -> 0x0328 }
        r2 = r11.c;	 Catch:{ all -> 0x0328 }
        r0 = r0.append(r2);	 Catch:{ all -> 0x0328 }
        r2 = "','error_code':1,'error':'params error'})";
        r0 = r0.append(r2);	 Catch:{ all -> 0x0328 }
        r0 = r0.toString();	 Catch:{ all -> 0x0328 }
        r2 = new java.io.PrintStream;	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r3 = r12.getOutputStream();	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r4 = 1;
        r5 = "UTF-8";
        r2.<init>(r3, r4, r5);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r3 = "HTTP/1.0 200 OK";
        r2.println(r3);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r3.<init>();	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r4 = "Content-Length:";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r4 = "UTF-8";
        r4 = r0.getBytes(r4);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r4 = r4.length;	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r2.println(r3);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r2.println();	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r2.println(r0);	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r2.close();	 Catch:{ Exception -> 0x02f7, all -> 0x0303 }
        r1.close();	 Catch:{ Exception -> 0x0227 }
        r12.close();	 Catch:{ Exception -> 0x0227 }
        goto L_0x0005;
    L_0x0227:
        r0 = move-exception;
        goto L_0x0005;
    L_0x022a:
        r0 = move-exception;
        com.loc.e.j = r4;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        throw r0;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
    L_0x022e:
        r0 = move-exception;
    L_0x022f:
        r1 = new java.io.PrintStream;	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r4 = r12.getOutputStream();	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r5 = 1;
        r6 = "UTF-8";
        r1.<init>(r4, r5, r6);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r4 = "HTTP/1.0 200 OK";
        r1.println(r4);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r4.<init>();	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r5 = "Content-Length:";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r5 = "UTF-8";
        r5 = r3.getBytes(r5);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r5 = r5.length;	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r1.println(r4);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r1.println();	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r1.println(r3);	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r1.close();	 Catch:{ Exception -> 0x030b, all -> 0x0317 }
        r2.close();	 Catch:{ Exception -> 0x0321 }
        r12.close();	 Catch:{ Exception -> 0x0321 }
    L_0x026c:
        throw r0;	 Catch:{ Throwable -> 0x026d }
    L_0x026d:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0005;
    L_0x0273:
        r0 = r11.o;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4.<init>();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r4.append(r1);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = "&&";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r4.append(r1);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = "({'package':'";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = r11.c;	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = "','error_code':0,'error':'','location':{'y':";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r6 = r0.i();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r4.append(r6);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = ",'precision':";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = r0.j();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r5 = ",'x':";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r6 = r0.h();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r0 = r4.append(r6);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "},'version_code':'";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "2.3.0";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "','version':'";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "2.3.0";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r4 = "'})";
        r0 = r0.append(r4);	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        r3 = r0.toString();	 Catch:{ Throwable -> 0x01bc, all -> 0x022e }
        goto L_0x0144;
    L_0x02e3:
        r0 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x02ec }
        r12.close();	 Catch:{ Exception -> 0x02ec }
        goto L_0x0005;
    L_0x02ec:
        r0 = move-exception;
        goto L_0x0005;
    L_0x02ef:
        r0 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x0336 }
        r12.close();	 Catch:{ Exception -> 0x0336 }
    L_0x02f6:
        throw r0;	 Catch:{ Throwable -> 0x026d }
    L_0x02f7:
        r0 = move-exception;
        r1.close();	 Catch:{ Exception -> 0x0300 }
        r12.close();	 Catch:{ Exception -> 0x0300 }
        goto L_0x0005;
    L_0x0300:
        r0 = move-exception;
        goto L_0x0005;
    L_0x0303:
        r0 = move-exception;
        r1.close();	 Catch:{ Exception -> 0x032c }
        r12.close();	 Catch:{ Exception -> 0x032c }
    L_0x030a:
        throw r0;	 Catch:{ Throwable -> 0x026d }
    L_0x030b:
        r1 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x0314 }
        r12.close();	 Catch:{ Exception -> 0x0314 }
        goto L_0x026c;
    L_0x0314:
        r1 = move-exception;
        goto L_0x026c;
    L_0x0317:
        r0 = move-exception;
        r2.close();	 Catch:{ Exception -> 0x031f }
        r12.close();	 Catch:{ Exception -> 0x031f }
    L_0x031e:
        throw r0;	 Catch:{ Throwable -> 0x026d }
    L_0x031f:
        r1 = move-exception;
        goto L_0x031e;
    L_0x0321:
        r1 = move-exception;
        goto L_0x026c;
    L_0x0324:
        r0 = move-exception;
        r2 = r3;
        goto L_0x022f;
    L_0x0328:
        r0 = move-exception;
        r2 = r1;
        goto L_0x022f;
    L_0x032c:
        r1 = move-exception;
        goto L_0x030a;
    L_0x032e:
        r1 = move-exception;
        r1 = r3;
        goto L_0x01bf;
    L_0x0332:
        r1 = move-exception;
        r1 = r2;
        goto L_0x01bf;
    L_0x0336:
        r1 = move-exception;
        goto L_0x02f6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.d.a(java.net.Socket):void");
    }

    private void h() {
        try {
            b();
            this.u = false;
            this.v = false;
            this.x = false;
            this.y = false;
            this.w = 0;
            this.z.b();
            this.g.clear();
            if (this.b) {
                Process.killProcess(Process.myPid());
            }
            if (this.d != null) {
                this.d.removeCallbacksAndMessages(null);
            }
        } catch (Throwable th) {
        }
    }

    public void onDestroy() {
        try {
            synchronized (this.j) {
                this.h = false;
                this.j.notify();
            }
        } catch (Throwable th) {
        }
    }

    private void i() {
        try {
            if (this.z != null) {
                this.z.h();
            }
        } catch (Throwable th) {
        }
    }
}
