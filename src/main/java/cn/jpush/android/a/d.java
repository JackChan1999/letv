package cn.jpush.android.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.telephony.CellLocation;
import android.widget.Toast;
import cn.jpush.android.util.a;
import cn.jpush.android.util.z;
import com.letv.core.constant.LetvConstant;
import java.util.Date;
import org.json.JSONArray;

public abstract class d {
    private static final String[] B;
    public static int b = LetvConstant.WIDGET_UPDATE_UI_TIME;
    public static boolean c = true;
    private static boolean f = false;
    private JSONArray A;
    public String a;
    protected boolean d;
    private boolean e;
    private int g;
    private b h;
    private g i;
    private Context j;
    private int[] k;
    private f l;
    private boolean m;
    private final BroadcastReceiver n;
    private long o;
    private int p;
    private boolean q;
    private boolean r;
    private j s;
    private int t;
    private final Date u;
    private JSONArray v;
    private boolean w;
    private boolean x;
    private boolean y;
    private String z;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static {
        /*
        r0 = 7;
        r3 = new java.lang.String[r0];
        r2 = 0;
        r1 = "w!h=D+\"?Nd\"e<X bajU\fI\u001cxI\fC\u000eyE\nS\u0003dU\u000eX\u0006dX";
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
            case 0: goto L_0x0081;
            case 1: goto L_0x0084;
            case 2: goto L_0x0087;
            case 3: goto L_0x008a;
            default: goto L_0x001e;
        };
    L_0x001e:
        r9 = 43;
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
            default: goto L_0x003c;
        };
    L_0x003c:
        r3[r2] = r1;
        r2 = 1;
        r1 = "w#`";
        r0 = 0;
        r3 = r4;
        goto L_0x0009;
    L_0x0045:
        r3[r2] = r1;
        r2 = 2;
        r1 = "w!h=D+\"?Nd\"e<X bajU\fI\u001cxI\tE\u0001nI\u0003C\fjB\u0006C\u0001";
        r0 = 1;
        r3 = r4;
        goto L_0x0009;
    L_0x004e:
        r3[r2] = r1;
        r2 = 3;
        r1 = "w!h=D+\"?Nd\"e<X bah^\u000eB\bnI\u0018E\tbI\u001cX\u000eS";
        r0 = 2;
        r3 = r4;
        goto L_0x0009;
    L_0x0057:
        r3[r2] = r1;
        r2 = 4;
        r1 = "w!h=D+\"?Nd\"e<X bajU\fI\u001cxI\u0018E\tbI\u001cX\u000eS";
        r0 = 3;
        r3 = r4;
        goto L_0x0009;
    L_0x0060:
        r3[r2] = r1;
        r2 = 5;
        r1 = "w!h=D+\"!Nba{&Ma[\u0006m_\u0010_\u001bjB\nS\fcW\u0001K\no";
        r0 = 4;
        r3 = r4;
        goto L_0x0009;
    L_0x0069:
        r3[r2] = r1;
        r2 = 6;
        r1 = "w!h=D+\"!Nba{&Ma_\fjX\u0010^\nxC\u0003X\u001c";
        r0 = 5;
        r3 = r4;
        goto L_0x0009;
    L_0x0072:
        r3[r2] = r1;
        B = r4;
        r0 = 15000; // 0x3a98 float:2.102E-41 double:7.411E-320;
        b = r0;
        r0 = 1;
        c = r0;
        r0 = 0;
        f = r0;
        return;
    L_0x0081:
        r9 = 22;
        goto L_0x0020;
    L_0x0084:
        r9 = 79;
        goto L_0x0020;
    L_0x0087:
        r9 = 12;
        goto L_0x0020;
    L_0x008a:
        r9 = 79;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.d.<clinit>():void");
    }

    private d(Context context) {
        this.e = false;
        this.q = false;
        this.u = new Date();
        this.x = false;
        this.y = false;
        this.n = new e();
        this.j = context.getApplicationContext();
        this.h = new b(context);
        this.s = new j(context);
        this.i = new g(context);
    }

    private d(Context context, String str) {
        this(context);
        if (str == null || "".equals(str)) {
            this.a = B[1];
        } else {
            this.a = str;
        }
    }

    private d(Context context, String str, boolean z) {
        this(context, str);
        this.x = z;
    }

    public d(Context context, String str, boolean z, boolean z2) {
        this(context, str, z);
        this.e = z2;
    }

    static /* synthetic */ void a(d dVar, Object obj) {
        if (f) {
            obj.toString();
            z.b();
            Toast.makeText(dVar.j, String.valueOf(obj), 0).show();
        }
    }

    private static boolean a(Context context) {
        return a.c(context, B[0]) && a.c(context, B[4]) && a.c(context, B[3]) && a.c(context, B[2]);
    }

    public final String a() {
        String d = this.i.d();
        return d == null ? "" : d;
    }

    public final void a(JSONArray jSONArray) {
        this.v = jSONArray;
    }

    public final JSONArray b() {
        return !a.c(this.j, B[0]) ? null : this.h.e() ? this.h.b() : this.v;
    }

    public final void b(JSONArray jSONArray) {
        this.A = jSONArray;
    }

    public final JSONArray c() {
        return this.A != null ? this.A : null;
    }

    public abstract void d();

    public final void e() {
        if (this.p == 1) {
            CellLocation.requestLocationUpdate();
            this.p = 2;
            this.l.sendEmptyMessage(1);
            if (this.s.b().isWifiEnabled()) {
                this.s.b().startScan();
                this.r = false;
            } else if (this.e) {
                this.o = System.currentTimeMillis();
                if (c && this.s.b().setWifiEnabled(true)) {
                    this.r = true;
                } else {
                    this.l.sendEmptyMessageDelayed(5, 8000);
                }
            } else {
                this.l.sendEmptyMessageDelayed(5, 0);
            }
        }
    }

    public final void f() {
        if (!this.y) {
            this.y = true;
            this.e = false;
            this.x = false;
            if (a.c(this.j, B[0])) {
                this.v = this.h.c();
            } else {
                z.d();
                this.v = null;
            }
            if (!a.c(this.j, B[4])) {
                z.d();
                this.A = null;
            } else if (a(this.j) || a.u(this.j)) {
                this.w = this.s.a();
                if (this.w) {
                    this.A = this.s.c();
                } else if (!this.e) {
                    this.A = null;
                } else if (!a.c(this.j, B[3])) {
                    z.d();
                    this.A = null;
                } else if (!this.x) {
                    this.j.registerReceiver(this.n, new IntentFilter(B[6]));
                    this.j.registerReceiver(this.n, new IntentFilter(B[5]));
                    this.s.b().setWifiEnabled(true);
                    this.q = true;
                }
            } else {
                this.A = null;
            }
            if (!a.c(this.j, B[2])) {
                z.d();
            } else if (this.i.a()) {
                this.i.b();
                if (!("" == this.i.d() || this.i.d() == null || System.currentTimeMillis() - this.i.e() >= 30000)) {
                    this.t = 0;
                    this.z = this.i.d();
                }
                if (!this.q && !this.x) {
                    d();
                    return;
                }
            }
            this.z = "";
            if (!this.q) {
            }
        }
    }

    public final void g() {
        this.y = false;
        if (a.c(this.j, B[2]) && this.i.a()) {
            this.i.c();
        }
        if (!this.w && a.c(this.j, B[3])) {
            this.s.b().setWifiEnabled(false);
        }
        if (this.p > 0 && a(this.j)) {
            this.j.unregisterReceiver(this.n);
            this.l = null;
            this.p = 0;
            if (!this.w) {
                this.d = false;
                this.s.b().setWifiEnabled(false);
            }
        }
    }
}
