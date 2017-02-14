package cn.jpush.android.a;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import cn.jpush.android.util.z;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.tencent.open.yyb.TitleBar;

public final class g {
    private static final String[] z;
    public double a;
    public double b;
    private Context c;
    private LocationManager d;
    private Location e;
    private String f;
    private long g;
    private final LocationListener h = new h(this);

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:37)
	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:61)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r4 = 3;
        r3 = 2;
        r2 = 1;
        r1 = 0;
        r0 = 5;
        r6 = new java.lang.String[r0];
        r5 = "2\u00153P/k\rzWwk[1Vys\u00129\u001dy([+\u0013alQs\u00139,V-\u0013alQs\u0013:*T}\u000b~/J";
        r0 = -1;
        r7 = r6;
        r8 = r6;
        r6 = r1;
    L_0x000d:
        r5 = r5.toCharArray();
        r9 = r5.length;
        if (r9 > r2) goto L_0x0075;
    L_0x0014:
        r10 = r1;
    L_0x0015:
        r11 = r5;
        r12 = r10;
        r15 = r9;
        r9 = r5;
        r5 = r15;
    L_0x001a:
        r14 = r9[r10];
        r13 = r12 % 5;
        switch(r13) {
            case 0: goto L_0x0069;
            case 1: goto L_0x006c;
            case 2: goto L_0x006f;
            case 3: goto L_0x0072;
            default: goto L_0x0021;
        };
    L_0x0021:
        r13 = 91;
    L_0x0023:
        r13 = r13 ^ r14;
        r13 = (char) r13;
        r9[r10] = r13;
        r10 = r12 + 1;
        if (r5 != 0) goto L_0x002f;
    L_0x002b:
        r9 = r11;
        r12 = r10;
        r10 = r5;
        goto L_0x001a;
    L_0x002f:
        r9 = r5;
        r5 = r11;
    L_0x0031:
        if (r9 > r10) goto L_0x0015;
    L_0x0033:
        r9 = new java.lang.String;
        r9.<init>(r5);
        r5 = r9.intern();
        switch(r0) {
            case 0: goto L_0x0048;
            case 1: goto L_0x0051;
            case 2: goto L_0x005a;
            case 3: goto L_0x0064;
            default: goto L_0x003f;
        };
    L_0x003f:
        r7[r6] = r5;
        r0 = "'R+F4;\\";
        r5 = r0;
        r6 = r2;
        r7 = r8;
        r0 = r1;
        goto L_0x000d;
    L_0x0048:
        r7[r6] = r5;
        r0 = "9V,B2?R";
        r5 = r0;
        r6 = r3;
        r7 = r8;
        r0 = r2;
        goto L_0x000d;
    L_0x0051:
        r7[r6] = r5;
        r0 = ".G,";
        r5 = r0;
        r6 = r4;
        r7 = r8;
        r0 = r3;
        goto L_0x000d;
    L_0x005a:
        r7[r6] = r5;
        r5 = 4;
        r0 = "%X<P/ X1";
        r6 = r5;
        r7 = r8;
        r5 = r0;
        r0 = r4;
        goto L_0x000d;
    L_0x0064:
        r7[r6] = r5;
        z = r8;
        return;
    L_0x0069:
        r13 = 73;
        goto L_0x0023;
    L_0x006c:
        r13 = 55;
        goto L_0x0023;
    L_0x006f:
        r13 = 95;
        goto L_0x0023;
    L_0x0072:
        r13 = 49;
        goto L_0x0023;
    L_0x0075:
        r10 = r1;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.jpush.android.a.g.<clinit>():void");
    }

    public g(Context context) {
        this.c = context;
        this.d = (LocationManager) this.c.getSystemService(z[4]);
    }

    private void a(Location location) {
        if (location != null) {
            try {
                this.a = location.getLatitude();
                this.b = location.getLongitude();
                this.g = System.currentTimeMillis();
                this.f = String.format(z[0], new Object[]{Double.valueOf(this.a), Double.valueOf(this.b), Double.valueOf(location.getAltitude()), Float.valueOf(location.getBearing()), Float.valueOf(location.getAccuracy())});
                return;
            } catch (Exception e) {
                e.getMessage();
                z.e();
            }
        }
        this.f = "";
    }

    public final boolean a() {
        try {
            return this.d != null ? this.d.isProviderEnabled(z[3]) || this.d.isProviderEnabled(z[1]) || this.d.isProviderEnabled(z[2]) : false;
        } catch (SecurityException e) {
            z.d();
            return false;
        } catch (IllegalArgumentException e2) {
            z.d();
            return false;
        }
    }

    public final void b() {
        try {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(1);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(1);
            String bestProvider = this.d.getBestProvider(criteria, true);
            if (bestProvider != null) {
                this.e = this.d.getLastKnownLocation(bestProvider);
                if (this.e != null) {
                    a(this.e);
                }
                this.d.requestLocationUpdates(bestProvider, SPConstant.DELAY_BUFFER_DURATION, TitleBar.SHAREBTN_RIGHT_MARGIN, this.h);
            }
        } catch (SecurityException e) {
            z.d();
        } catch (Exception e2) {
            z.d();
        }
    }

    public final void c() {
        try {
            if (this.h != null) {
                this.d.removeUpdates(this.h);
            } else {
                z.d();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final String d() {
        return this.f;
    }

    public final long e() {
        return this.g;
    }
}
