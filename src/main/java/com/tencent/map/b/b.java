package com.tencent.map.b;

import android.location.Location;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    private static b b;
    public String a = "";
    private double c = 0.0d;
    private double d = 0.0d;
    private double e = 0.0d;
    private double f = 0.0d;
    private double g = 0.0d;
    private double h = 0.0d;
    private a i;
    private b j = null;
    private boolean k = false;

    public interface a {
        void a(double d, double d2);
    }

    public class b extends Thread {
        private /* synthetic */ b a;

        public b(b bVar) {
            this.a = bVar;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
            r8 = this;
            r6 = 4645040803167600640; // 0x4076800000000000 float:0.0 double:360.0;
            r1 = 0;
            r0 = r8.a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.getBytes();	 Catch:{ Exception -> 0x0034 }
            r0 = com.tencent.map.b.j.a(r0);	 Catch:{ Exception -> 0x0034 }
            r2 = r8.a;	 Catch:{ Exception -> 0x0034 }
            r3 = 1;
            r2.k = r3;	 Catch:{ Exception -> 0x0034 }
            r2 = "http://ls.map.soso.com/deflect?c=1";
            r3 = "SOSO MAP LBS SDK";
            r0 = com.tencent.map.b.b.a(r2, r3, r0);	 Catch:{ Exception -> 0x0034 }
            r2 = r8.a;	 Catch:{ Exception -> 0x0034 }
            r3 = 0;
            r2.k = r3;	 Catch:{ Exception -> 0x0034 }
            r2 = r0.a;	 Catch:{ Exception -> 0x0034 }
            r2 = com.tencent.map.b.j.b(r2);	 Catch:{ Exception -> 0x0034 }
            r3 = r8.a;	 Catch:{ Exception -> 0x0034 }
            r0 = r0.b;	 Catch:{ Exception -> 0x0034 }
            com.tencent.map.b.b.a(r3, r2, r0);	 Catch:{ Exception -> 0x0034 }
        L_0x0033:
            return;
        L_0x0034:
            r0 = move-exception;
            r0 = r1;
        L_0x0036:
            r0 = r0 + 1;
            r2 = 3;
            if (r0 > r2) goto L_0x006a;
        L_0x003b:
            r2 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
            sleep(r2);	 Catch:{ Exception -> 0x0068 }
            r2 = r8.a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.getBytes();	 Catch:{ Exception -> 0x0068 }
            r2 = com.tencent.map.b.j.a(r2);	 Catch:{ Exception -> 0x0068 }
            r3 = "http://ls.map.soso.com/deflect?c=1";
            r4 = "SOSO MAP LBS SDK";
            r2 = com.tencent.map.b.b.a(r3, r4, r2);	 Catch:{ Exception -> 0x0068 }
            r3 = r8.a;	 Catch:{ Exception -> 0x0068 }
            r4 = 0;
            r3.k = r4;	 Catch:{ Exception -> 0x0068 }
            r3 = r2.a;	 Catch:{ Exception -> 0x0068 }
            r3 = com.tencent.map.b.j.b(r3);	 Catch:{ Exception -> 0x0068 }
            r4 = r8.a;	 Catch:{ Exception -> 0x0068 }
            r2 = r2.b;	 Catch:{ Exception -> 0x0068 }
            com.tencent.map.b.b.a(r4, r3, r2);	 Catch:{ Exception -> 0x0068 }
            goto L_0x0033;
        L_0x0068:
            r2 = move-exception;
            goto L_0x0036;
        L_0x006a:
            r0 = r8.a;
            r0.k = r1;
            r0 = r8.a;
            r0 = r0.i;
            if (r0 == 0) goto L_0x0033;
        L_0x0077:
            r0 = r8.a;
            r0 = r0.i;
            r0.a(r6, r6);
            goto L_0x0033;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.b.b.run():void");
        }
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    static /* synthetic */ void a(b bVar, byte[] bArr, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(new String(bArr, str));
        } catch (Exception e) {
            if (bVar.i != null) {
                bVar.i.a(360.0d, 360.0d);
            }
        }
        try {
            JSONObject jSONObject = new JSONObject(stringBuffer.toString()).getJSONObject(HOME_RECOMMEND_PARAMETERS.LOCATION);
            double d = jSONObject.getDouble("latitude");
            double d2 = jSONObject.getDouble("longitude");
            bVar.g = d - bVar.e;
            bVar.h = d2 - bVar.f;
            bVar.c = bVar.e;
            bVar.d = bVar.f;
            if (bVar.i != null) {
                bVar.i.a(d, d2);
            }
        } catch (JSONException e2) {
            if (bVar.i != null) {
                bVar.i.a(360.0d, 360.0d);
            }
        }
    }

    public final void a(double d, double d2, a aVar) {
        this.i = aVar;
        if (!(this.g == 0.0d || this.h == 0.0d)) {
            float[] fArr = new float[10];
            Location.distanceBetween(d, d2, this.c, this.d, fArr);
            if (fArr[0] < 1500.0f) {
                this.i.a(this.g + d, this.h + d2);
                return;
            }
        }
        if (!this.k) {
            this.a = "{\"source\":101,\"access_token\":\"160e7bd42dec9428721034e0146fc6dd\",\"location\":{\"latitude\":" + d + ",\"longitude\":" + d2 + "}\t}";
            this.e = d;
            this.f = d2;
            this.j = new b(this);
            this.j.start();
        }
    }

    public static boolean a(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static n a(String str, String str2, byte[] bArr) throws o, r, Exception {
        Object obj = 1;
        if (l.b() == null) {
            obj = null;
        }
        if (obj == null) {
            throw new o();
        }
        try {
            return q.a(false, str, str2, null, bArr, false, true);
        } catch (Exception e) {
            throw e;
        }
    }
}
