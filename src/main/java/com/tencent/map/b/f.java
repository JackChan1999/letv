package com.tencent.map.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.tencent.map.a.a.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public final class f implements com.tencent.map.b.b.a, com.tencent.map.b.d.c, com.tencent.map.b.e.c, com.tencent.map.b.g.c {
    private static boolean t = false;
    private static f u = null;
    private d A;
    private int B;
    private int C;
    private int D;
    private String E;
    private String F;
    private String G;
    private String H;
    private String I;
    private String J;
    private boolean K;
    private boolean L;
    private long M;
    private Handler N;
    private Runnable O;
    private final BroadcastReceiver P;
    private long a;
    private Context b;
    private e c;
    private d d;
    private g e;
    private int f;
    private int g;
    private c h;
    private b i;
    private com.tencent.map.a.a.b j;
    private int k;
    private int l;
    private int m;
    private byte[] n;
    private byte[] o;
    private boolean p;
    private c q;
    private b r;
    private a s;
    private long v;
    private com.tencent.map.b.e.b w;
    private com.tencent.map.b.d.b x;
    private com.tencent.map.b.g.b y;
    private d z;

    class a extends Thread {
        private com.tencent.map.b.e.b a = null;
        private com.tencent.map.b.d.b b = null;
        private com.tencent.map.b.g.b c = null;
        private /* synthetic */ f d;

        a(f fVar, com.tencent.map.b.e.b bVar, com.tencent.map.b.d.b bVar2, com.tencent.map.b.g.b bVar3) {
            this.d = fVar;
            if (bVar != null) {
                this.a = (com.tencent.map.b.e.b) bVar.clone();
            }
            if (bVar2 != null) {
                this.b = (com.tencent.map.b.d.b) bVar2.clone();
            }
            if (bVar3 != null) {
                this.c = (com.tencent.map.b.g.b) bVar3.clone();
            }
        }

        public final void run() {
            if (!f.t) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) this.d.b.getSystemService("phone");
                    this.d.E = telephonyManager.getDeviceId();
                    this.d.F = telephonyManager.getSubscriberId();
                    this.d.G = telephonyManager.getLine1Number();
                    Pattern compile = Pattern.compile("[0-9a-zA-Z+-]*");
                    this.d.E = this.d.E == null ? "" : this.d.E;
                    if (compile.matcher(this.d.E).matches()) {
                        this.d.E = this.d.E == null ? "" : this.d.E;
                    } else {
                        this.d.E = "";
                    }
                    this.d.F = this.d.F == null ? "" : this.d.F;
                    if (compile.matcher(this.d.F).matches()) {
                        this.d.F = this.d.F == null ? "" : this.d.F;
                    } else {
                        this.d.F = "";
                    }
                    this.d.G = this.d.G == null ? "" : this.d.G;
                    if (compile.matcher(this.d.G).matches()) {
                        this.d.G = this.d.G == null ? "" : this.d.G;
                    } else {
                        this.d.G = "";
                    }
                } catch (Exception e) {
                }
                f.t = true;
                this.d.E = this.d.E == null ? "" : this.d.E;
                this.d.F = this.d.F == null ? "" : this.d.F;
                this.d.G = this.d.G == null ? "" : this.d.G;
                this.d.I = j.a(this.d.E == null ? "0123456789ABCDEF" : this.d.E);
            }
            String a = this.d.g == 4 ? i.a(this.c) : "[]";
            String a2 = i.a(this.b, this.d.d.b());
            String a3 = i.a(this.d.E, this.d.F, this.d.G, this.d.H, this.d.K);
            String a4 = (this.a == null || !this.a.a()) ? "{}" : i.a(this.a);
            this.d.q.sendMessage(this.d.q.obtainMessage(16, (("{\"version\":\"1.1.8\",\"address\":" + this.d.l) + ",\"source\":203,\"access_token\":\"" + this.d.I + "\",\"app_name\":" + "\"" + this.d.J + "\",\"bearing\":1") + ",\"attribute\":" + a3 + ",\"location\":" + a4 + ",\"cells\":" + a2 + ",\"wifis\":" + a + "}"));
        }
    }

    class b extends Thread {
        private String a = null;
        private String b = null;
        private String c = null;
        private /* synthetic */ f d;

        public b(f fVar, String str) {
            this.d = fVar;
            this.a = str;
            this.b = (fVar.D == 0 ? "http://lstest.map.soso.com/loc?c=1" : "http://lbs.map.qq.com/loc?c=1") + "&mars=" + fVar.m;
        }

        private String a(byte[] bArr, String str) {
            this.d.M = System.currentTimeMillis();
            StringBuffer stringBuffer = new StringBuffer();
            try {
                stringBuffer.append(new String(bArr, str));
                return stringBuffer.toString();
            } catch (Exception e) {
                return null;
            }
        }

        public final void run() {
            Message message = new Message();
            message.what = 8;
            try {
                byte[] a = j.a(this.a.getBytes());
                this.d.p = true;
                n a2 = b.a(this.b, "SOSO MAP LBS SDK", a);
                this.d.p = false;
                this.c = a(j.b(a2.a), a2.b);
                if (this.c != null) {
                    message.arg1 = 0;
                    message.obj = this.c;
                } else {
                    message.arg1 = 1;
                }
            } catch (Exception e) {
                int i = 0;
                while (true) {
                    i++;
                    if (i > 3) {
                        break;
                    }
                    try {
                        sleep(1000);
                        byte[] a3 = j.a(this.a.getBytes());
                        this.d.p = true;
                        n a4 = b.a(this.b, "SOSO MAP LBS SDK", a3);
                        this.d.p = false;
                        this.c = a(j.b(a4.a), a4.b);
                        if (this.c != null) {
                            message.arg1 = 0;
                            message.obj = this.c;
                        } else {
                            message.arg1 = 1;
                        }
                    } catch (Exception e2) {
                    }
                }
                this.d.p = false;
                message.arg1 = 1;
            }
            f.j(this.d);
            this.d.q.sendMessage(message);
        }
    }

    class c extends Handler {
        private /* synthetic */ f a;

        public c(f fVar) {
            this.a = fVar;
            super(Looper.getMainLooper());
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    f.a(this.a, (com.tencent.map.b.e.b) message.obj);
                    return;
                case 2:
                    f.a(this.a, (com.tencent.map.b.d.b) message.obj);
                    return;
                case 3:
                    f.a(this.a, (com.tencent.map.b.g.b) message.obj);
                    return;
                case 4:
                    f.a(this.a, message.arg1);
                    return;
                case 5:
                    f.b(this.a, message.arg1);
                    return;
                case 6:
                    f.a(this.a, (Location) message.obj);
                    return;
                case 8:
                    if (message.arg1 == 0) {
                        this.a.a((String) message.obj);
                        return;
                    } else if (this.a.w == null || !this.a.w.a()) {
                        this.a.e();
                        return;
                    } else {
                        return;
                    }
                case 16:
                    if (message.obj != null) {
                        f.a(this.a, (String) message.obj);
                        this.a.s = null;
                        return;
                    }
                    return;
                case 256:
                    if (this.a.B == 1) {
                        this.a.d();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private f() {
        this.a = 5000;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = 1024;
        this.g = 4;
        this.h = null;
        this.i = null;
        this.j = null;
        this.n = new byte[0];
        this.o = new byte[0];
        this.p = false;
        this.q = null;
        this.r = null;
        this.s = null;
        this.v = -1;
        this.w = null;
        this.x = null;
        this.y = null;
        this.z = null;
        this.A = null;
        this.B = 0;
        this.C = 0;
        this.D = 1;
        this.E = "";
        this.F = "";
        this.G = "";
        this.H = "";
        this.I = "";
        this.J = "";
        this.K = false;
        this.L = false;
        this.M = 0;
        this.N = null;
        this.O = new Runnable(this) {
            private /* synthetic */ f a;

            {
                this.a = r1;
            }

            public final void run() {
                if (System.currentTimeMillis() - this.a.M >= 8000) {
                    if (this.a.e.b() && this.a.e.c()) {
                        this.a.e.a(0);
                    } else {
                        this.a.d();
                    }
                }
            }
        };
        this.P = new BroadcastReceiver(this) {
            private /* synthetic */ f a;

            {
                this.a = r1;
            }

            public final void onReceive(Context context, Intent intent) {
                if (!intent.getBooleanExtra("noConnectivity", false) && this.a.q != null) {
                    this.a.q.sendEmptyMessage(256);
                }
            }
        };
        this.c = new e();
        this.d = new d();
        this.e = new g();
    }

    public static synchronized f a() {
        f fVar;
        synchronized (f.class) {
            if (u == null) {
                u = new f();
            }
            fVar = u;
        }
        return fVar;
    }

    private static ArrayList<com.tencent.map.a.a.c> a(JSONArray jSONArray) throws Exception {
        int length = jSONArray.length();
        ArrayList<com.tencent.map.a.a.c> arrayList = new ArrayList();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            arrayList.add(new com.tencent.map.a.a.c(jSONObject.getString("name"), jSONObject.getString("addr"), jSONObject.getString("catalog"), jSONObject.getDouble("dist"), Double.parseDouble(jSONObject.getString("latitude")), Double.parseDouble(jSONObject.getString("longitude"))));
        }
        return arrayList;
    }

    static /* synthetic */ void a(f fVar, int i) {
        if (i == 0) {
            fVar.w = null;
        }
        fVar.f = i == 0 ? 1 : 2;
        if (fVar.j != null) {
            fVar.j.a(fVar.f);
        }
    }

    static /* synthetic */ void a(f fVar, Location location) {
        if (location == null || location.getLatitude() > 359.0d || location.getLongitude() > 359.0d) {
            if (fVar.w == null || !fVar.w.a()) {
                fVar.e();
            } else {
                fVar.b(true);
            }
        }
        fVar.z = new d();
        fVar.z.z = 0;
        fVar.z.y = 0;
        fVar.z.b = i.a(location.getLatitude(), 6);
        fVar.z.c = i.a(location.getLongitude(), 6);
        if (fVar.w != null && fVar.w.a()) {
            fVar.z.e = i.a((double) fVar.w.b().getAccuracy(), 1);
            fVar.z.d = i.a(fVar.w.b().getAltitude(), 1);
            fVar.z.f = i.a((double) fVar.w.b().getSpeed(), 1);
            fVar.z.g = i.a((double) fVar.w.b().getBearing(), 1);
            fVar.z.a = 0;
        }
        fVar.z.x = true;
        if (!(fVar.l == 0 || fVar.A == null || fVar.B != 0)) {
            if ((fVar.l == 3 || fVar.l == 4) && fVar.l == fVar.A.z) {
                fVar.z.i = fVar.A.i;
                fVar.z.j = fVar.A.j;
                fVar.z.k = fVar.A.k;
                fVar.z.l = fVar.A.l;
                fVar.z.m = fVar.A.m;
                fVar.z.n = fVar.A.n;
                fVar.z.o = fVar.A.o;
                fVar.z.p = fVar.A.p;
                fVar.z.z = 3;
            }
            if (fVar.l == 4 && fVar.l == fVar.A.z && fVar.A.w != null) {
                fVar.z.w = new ArrayList();
                Iterator it = fVar.A.w.iterator();
                while (it.hasNext()) {
                    fVar.z.w.add(new com.tencent.map.a.a.c((com.tencent.map.a.a.c) it.next()));
                }
                fVar.z.z = 4;
            }
            if (fVar.l == 7 && fVar.l == fVar.A.z) {
                fVar.z.z = 7;
                fVar.z.h = fVar.A.h;
                fVar.z.i = fVar.A.i;
                if (fVar.A.h == 0) {
                    fVar.z.j = fVar.A.j;
                    fVar.z.k = fVar.A.k;
                    fVar.z.l = fVar.A.l;
                    fVar.z.m = fVar.A.m;
                    fVar.z.n = fVar.A.n;
                    fVar.z.o = fVar.A.o;
                    fVar.z.p = fVar.A.p;
                } else {
                    fVar.z.q = fVar.A.q;
                    fVar.z.r = fVar.A.r;
                    fVar.z.s = fVar.A.s;
                    fVar.z.t = fVar.A.t;
                    fVar.z.u = fVar.A.u;
                    fVar.z.v = fVar.A.v;
                }
            }
        }
        if (fVar.B != 0 || fVar.A != null) {
            if (fVar.B != 0) {
                fVar.z.y = fVar.B;
            }
            if (System.currentTimeMillis() - fVar.v >= fVar.a && fVar.j != null && fVar.k == 1) {
                fVar.j.a(fVar.z);
                fVar.v = System.currentTimeMillis();
            }
        }
    }

    static /* synthetic */ void a(f fVar, com.tencent.map.b.d.b bVar) {
        fVar.x = bVar;
        if (fVar.e != null && fVar.e.b() && fVar.e.c()) {
            fVar.e.a(0);
            return;
        }
        if (fVar.C > 0 && !i.a(bVar.a, bVar.b, bVar.c, bVar.d, bVar.e)) {
            fVar.C--;
        }
        fVar.d();
    }

    static /* synthetic */ void a(f fVar, com.tencent.map.b.e.b bVar) {
        if (bVar != null) {
            fVar.w = bVar;
            if (fVar.k != 1 || fVar.w == null || !fVar.w.a()) {
                return;
            }
            if (fVar.m == 0) {
                fVar.b(false);
            } else if (fVar.m == 1 && fVar.i != null) {
                b bVar2 = fVar.i;
                double latitude = fVar.w.b().getLatitude();
                double longitude = fVar.w.b().getLongitude();
                Context context = fVar.b;
                bVar2.a(latitude, longitude, (com.tencent.map.b.b.a) fVar);
            }
        }
    }

    static /* synthetic */ void a(f fVar, com.tencent.map.b.g.b bVar) {
        if (bVar != null) {
            fVar.y = bVar;
            fVar.d();
        }
    }

    static /* synthetic */ void a(f fVar, String str) {
        if (i.c(str)) {
            if (fVar.k != 0 || fVar.j == null) {
                String b = fVar.h == null ? null : (fVar.x == null || fVar.y == null) ? null : fVar.h.b(fVar.x.b, fVar.x.c, fVar.x.d, fVar.x.e, fVar.y.a());
                if (b != null) {
                    fVar.a(b);
                    return;
                }
                if (!(fVar.h == null || fVar.x == null || fVar.y == null)) {
                    fVar.h.a(fVar.x.b, fVar.x.c, fVar.x.d, fVar.x.e, fVar.y.a());
                }
                if (!fVar.p) {
                    if (fVar.r != null) {
                        fVar.r.interrupt();
                    }
                    fVar.r = null;
                    fVar.r = new b(fVar, str);
                    fVar.r.start();
                    return;
                }
                return;
            }
            byte[] bytes;
            try {
                bytes = str.getBytes();
            } catch (Exception e) {
                bytes = null;
            }
            fVar.j.a(bytes, 0);
        } else if (fVar.C > 0) {
            fVar.C--;
        } else if (fVar.k == 0 && fVar.j != null) {
            fVar.j.a(null, -1);
        } else if (fVar.k == 1 && fVar.j != null) {
            fVar.z = new d();
            fVar.B = 3;
            fVar.z.y = 3;
            fVar.z.z = -1;
            fVar.j.a(fVar.z);
        }
    }

    private void a(String str) {
        int i = 0;
        try {
            double d;
            this.z = new d();
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = jSONObject.getJSONObject(HOME_RECOMMEND_PARAMETERS.LOCATION);
            this.z.a = 1;
            this.z.b = i.a(jSONObject2.getDouble("latitude"), 6);
            this.z.c = i.a(jSONObject2.getDouble("longitude"), 6);
            this.z.d = i.a(jSONObject2.getDouble("altitude"), 1);
            this.z.e = i.a(jSONObject2.getDouble("accuracy"), 1);
            this.z.x = this.m == 1;
            String string = jSONObject.getString("bearing");
            int i2 = -100;
            if (string != null && string.split(",").length > 1) {
                i = Integer.parseInt(string.split(",")[1]);
            }
            if (this.x != null) {
                i2 = this.x.f;
            }
            d dVar = this.z;
            double d2 = this.z.e;
            if (i >= 6) {
                d = 40.0d;
            } else if (i == 5) {
                d = 60.0d;
            } else if (i == 4) {
                d = 70.0d;
            } else if (i == 3) {
                d = 90.0d;
            } else if (i == 2) {
                d = 110.0d;
            } else {
                i2 = (i2 < -72 || i != 0) ? d2 <= 100.0d ? ((int) (((d2 - 1.0d) / 10.0d) + 1.0d)) * 10 : (d2 <= 100.0d || d2 > 800.0d) ? ((int) ((0.8d * d2) / 10.0d)) * 10 : ((int) ((0.85d * d2) / 10.0d)) * 10 : ((int) ((0.45d * d2) / 10.0d)) * 10;
                d = (double) i2;
            }
            dVar.e = d;
            this.z.z = 0;
            if ((this.l == 3 || this.l == 4) && this.m == 1) {
                jSONObject2 = jSONObject.getJSONObject("details").getJSONObject("subnation");
                this.z.a(jSONObject2.getString("name"));
                this.z.m = jSONObject2.getString("town");
                this.z.n = jSONObject2.getString("village");
                this.z.o = jSONObject2.getString("street");
                this.z.p = jSONObject2.getString("street_no");
                this.z.z = 3;
                this.z.h = 0;
            }
            if (this.l == 4 && this.m == 1) {
                this.z.w = a(jSONObject.getJSONObject("details").getJSONArray("poilist"));
                this.z.z = 4;
            }
            if (this.l == 7 && this.m == 1) {
                jSONObject2 = jSONObject.getJSONObject("details");
                i = jSONObject2.getInt("stat");
                jSONObject2 = jSONObject2.getJSONObject("subnation");
                if (i == 0) {
                    this.z.a(jSONObject2.getString("name"));
                    this.z.m = jSONObject2.getString("town");
                    this.z.n = jSONObject2.getString("village");
                    this.z.o = jSONObject2.getString("street");
                    this.z.p = jSONObject2.getString("street_no");
                } else if (i == 1) {
                    this.z.i = jSONObject2.getString("nation");
                    this.z.q = jSONObject2.getString("admin_level_1");
                    this.z.r = jSONObject2.getString("admin_level_2");
                    this.z.s = jSONObject2.getString("admin_level_3");
                    this.z.t = jSONObject2.getString("locality");
                    this.z.u = jSONObject2.getString("sublocality");
                    this.z.v = jSONObject2.getString("route");
                }
                this.z.h = i;
                this.z.z = 7;
            }
            this.z.y = 0;
            this.A = new d(this.z);
            this.B = 0;
            if (this.h != null) {
                this.h.a(str);
            }
        } catch (Exception e) {
            this.z = new d();
            this.z.z = -1;
            this.z.y = 2;
            this.B = 2;
        }
        if (this.j != null && this.k == 1) {
            if (this.w == null || !this.w.a()) {
                this.j.a(this.z);
                this.v = System.currentTimeMillis();
            }
        }
    }

    static /* synthetic */ void b(f fVar, int i) {
        int i2 = 3;
        if (i == 3) {
            i2 = 4;
        }
        fVar.g = i2;
        if (fVar.j != null) {
            fVar.j.a(fVar.g);
        }
    }

    private void b(boolean z) {
        if (this.w != null && this.w.a()) {
            Location b = this.w.b();
            this.z = new d();
            this.z.b = i.a(b.getLatitude(), 6);
            this.z.c = i.a(b.getLongitude(), 6);
            this.z.d = i.a(b.getAltitude(), 1);
            this.z.e = i.a((double) b.getAccuracy(), 1);
            this.z.f = i.a((double) b.getSpeed(), 1);
            this.z.g = i.a((double) b.getBearing(), 1);
            this.z.a = 0;
            this.z.x = false;
            if (z) {
                this.z.y = 1;
            } else {
                this.z.y = 0;
            }
            this.z.z = 0;
            this.A = new d(this.z);
            this.B = 0;
            if (System.currentTimeMillis() - this.v >= this.a && this.j != null && this.k == 1) {
                this.j.a(this.z);
                this.v = System.currentTimeMillis();
            }
        }
    }

    private void d() {
        if (this.s == null) {
            this.s = new a(this, this.w, this.x, this.y);
            this.s.start();
        }
    }

    private void e() {
        this.z = new d();
        this.B = 1;
        this.z.y = 1;
        this.z.z = -1;
        this.z.a = 1;
        if (this.j != null && this.k == 1) {
            this.j.a(this.z);
        }
    }

    static /* synthetic */ void j(f fVar) {
    }

    public final void a(double d, double d2) {
        synchronized (this.o) {
            Message obtainMessage = this.q.obtainMessage(6);
            Location location = new Location("Deflect");
            location.setLatitude(d);
            location.setLongitude(d2);
            obtainMessage.obj = location;
            this.q.sendMessage(obtainMessage);
        }
    }

    public final void a(int i) {
        synchronized (this.o) {
            this.q.sendMessage(this.q.obtainMessage(4, i, 0));
        }
    }

    public final void a(com.tencent.map.b.d.b bVar) {
        synchronized (this.o) {
            this.q.sendMessage(this.q.obtainMessage(2, bVar));
        }
    }

    public final void a(com.tencent.map.b.e.b bVar) {
        synchronized (this.o) {
            this.q.sendMessage(this.q.obtainMessage(1, bVar));
        }
    }

    public final void a(com.tencent.map.b.g.b bVar) {
        synchronized (this.o) {
            this.q.sendMessage(this.q.obtainMessage(3, bVar));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Context r9, com.tencent.map.a.a.b r10) {
        /*
        r8 = this;
        r2 = 1;
        r1 = 0;
        r3 = r8.n;
        monitor-enter(r3);
        if (r9 == 0) goto L_0x0009;
    L_0x0007:
        if (r10 != 0) goto L_0x000c;
    L_0x0009:
        monitor-exit(r3);
        r0 = r1;
    L_0x000b:
        return r0;
    L_0x000c:
        r0 = r8.J;	 Catch:{ all -> 0x00e5 }
        r0 = com.tencent.map.b.i.a(r0);	 Catch:{ all -> 0x00e5 }
        if (r0 != 0) goto L_0x0017;
    L_0x0014:
        monitor-exit(r3);	 Catch:{ all -> 0x00e5 }
        r0 = r1;
        goto L_0x000b;
    L_0x0017:
        r0 = new com.tencent.map.b.f$c;	 Catch:{ all -> 0x00e5 }
        r0.<init>(r8);	 Catch:{ all -> 0x00e5 }
        r8.q = r0;	 Catch:{ all -> 0x00e5 }
        r0 = new android.os.Handler;	 Catch:{ all -> 0x00e5 }
        r4 = android.os.Looper.getMainLooper();	 Catch:{ all -> 0x00e5 }
        r0.<init>(r4);	 Catch:{ all -> 0x00e5 }
        r8.N = r0;	 Catch:{ all -> 0x00e5 }
        r8.b = r9;	 Catch:{ all -> 0x00e5 }
        r8.j = r10;	 Catch:{ all -> 0x00e5 }
        r0 = com.tencent.map.b.l.a();	 Catch:{ all -> 0x00e5 }
        r4 = r8.b;	 Catch:{ all -> 0x00e5 }
        r4 = r4.getApplicationContext();	 Catch:{ all -> 0x00e5 }
        r0.a(r4);	 Catch:{ all -> 0x00e5 }
        r0 = "connectivity";
        r0 = r9.getSystemService(r0);	 Catch:{ Exception -> 0x00e8 }
        r0 = (android.net.ConnectivityManager) r0;	 Catch:{ Exception -> 0x00e8 }
        if (r0 == 0) goto L_0x0054;
    L_0x0044:
        r4 = r0.getActiveNetworkInfo();	 Catch:{ Exception -> 0x00e8 }
        if (r4 == 0) goto L_0x0054;
    L_0x004a:
        r0 = r0.getActiveNetworkInfo();	 Catch:{ Exception -> 0x00e8 }
        r0 = r0.isRoaming();	 Catch:{ Exception -> 0x00e8 }
        r8.K = r0;	 Catch:{ Exception -> 0x00e8 }
    L_0x0054:
        r0 = r8.b;	 Catch:{ Exception -> 0x00e8 }
        r4 = r8.P;	 Catch:{ Exception -> 0x00e8 }
        r5 = new android.content.IntentFilter;	 Catch:{ Exception -> 0x00e8 }
        r6 = "android.net.conn.CONNECTIVITY_CHANGE";
        r5.<init>(r6);	 Catch:{ Exception -> 0x00e8 }
        r0.registerReceiver(r4, r5);	 Catch:{ Exception -> 0x00e8 }
    L_0x0062:
        r0 = r8.j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.a();	 Catch:{ all -> 0x00e5 }
        r8.k = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.b();	 Catch:{ all -> 0x00e5 }
        r8.l = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.j;	 Catch:{ all -> 0x00e5 }
        r0 = r0.c();	 Catch:{ all -> 0x00e5 }
        r8.m = r0;	 Catch:{ all -> 0x00e5 }
        r4 = -1;
        r8.v = r4;	 Catch:{ all -> 0x00e5 }
        r0 = r8.l;	 Catch:{ all -> 0x00e5 }
        r4 = 7;
        if (r0 != r4) goto L_0x0086;
    L_0x0083:
        r0 = 0;
        r8.l = r0;	 Catch:{ all -> 0x00e5 }
    L_0x0086:
        r0 = 0;
        r8.L = r0;	 Catch:{ all -> 0x00e5 }
        r0 = 1;
        r8.D = r0;	 Catch:{ all -> 0x00e5 }
        r0 = r8.c;	 Catch:{ all -> 0x00e5 }
        r4 = r8.b;	 Catch:{ all -> 0x00e5 }
        r0 = r0.a(r8, r4);	 Catch:{ all -> 0x00e5 }
        r4 = r8.d;	 Catch:{ all -> 0x00e5 }
        r5 = r8.b;	 Catch:{ all -> 0x00e5 }
        r4 = r4.a(r5, r8);	 Catch:{ all -> 0x00e5 }
        r5 = r8.e;	 Catch:{ all -> 0x00e5 }
        r6 = r8.b;	 Catch:{ all -> 0x00e5 }
        r7 = 1;
        r5 = r5.a(r6, r8, r7);	 Catch:{ all -> 0x00e5 }
        r6 = com.tencent.map.b.c.a();	 Catch:{ all -> 0x00e5 }
        r8.h = r6;	 Catch:{ all -> 0x00e5 }
        r6 = com.tencent.map.b.b.a();	 Catch:{ all -> 0x00e5 }
        r8.i = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.w = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.x = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.y = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.z = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.A = r6;	 Catch:{ all -> 0x00e5 }
        r6 = 0;
        r8.B = r6;	 Catch:{ all -> 0x00e5 }
        r6 = r8.h;	 Catch:{ all -> 0x00e5 }
        if (r6 == 0) goto L_0x00cc;
    L_0x00c7:
        r6 = r8.h;	 Catch:{ all -> 0x00e5 }
        r6.b();	 Catch:{ all -> 0x00e5 }
    L_0x00cc:
        r6 = 1;
        r8.C = r6;	 Catch:{ all -> 0x00e5 }
        if (r0 == 0) goto L_0x00d9;
    L_0x00d1:
        r0 = r8.m;	 Catch:{ all -> 0x00e5 }
        if (r0 != 0) goto L_0x00d9;
    L_0x00d5:
        monitor-exit(r3);	 Catch:{ all -> 0x00e5 }
        r0 = r2;
        goto L_0x000b;
    L_0x00d9:
        if (r4 != 0) goto L_0x00dd;
    L_0x00db:
        if (r5 == 0) goto L_0x00e1;
    L_0x00dd:
        monitor-exit(r3);
        r0 = r2;
        goto L_0x000b;
    L_0x00e1:
        monitor-exit(r3);
        r0 = r1;
        goto L_0x000b;
    L_0x00e5:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x00e8:
        r0 = move-exception;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.f.a(android.content.Context, com.tencent.map.a.a.b):boolean");
    }

    public final boolean a(String str, String str2) {
        boolean z;
        synchronized (this.n) {
            if (a.a().a(str, str2)) {
                this.J = str;
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public final void b() {
        synchronized (this.n) {
            try {
                if (this.j != null) {
                    this.j = null;
                    this.N.removeCallbacks(this.O);
                    this.b.unregisterReceiver(this.P);
                    this.c.a();
                    this.d.a();
                    this.e.a();
                }
            } catch (Exception e) {
            }
        }
    }

    public final void b(int i) {
        synchronized (this.o) {
            this.q.sendMessage(this.q.obtainMessage(5, i, 0));
        }
    }
}
