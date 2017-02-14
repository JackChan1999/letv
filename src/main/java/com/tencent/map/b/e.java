package com.tencent.map.b;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.Iterator;

public final class e {
    private static LocationManager b = null;
    private static float d = 0.0f;
    private Context a = null;
    private a c = null;
    private c e = null;
    private b f = null;
    private boolean g = false;
    private byte[] h = new byte[0];
    private int i = 1024;
    private long j = 0;
    private boolean k = false;
    private int l = 0;
    private int m = 0;

    class a implements Listener, LocationListener {
        private /* synthetic */ e a;

        private a(e eVar) {
            this.a = eVar;
        }

        public final void onGpsStatusChanged(int i) {
            switch (i) {
                case 1:
                    e.a(this.a, 1);
                    break;
                case 2:
                    e.a(this.a, 0);
                    break;
                case 3:
                    e.a(this.a, 2);
                    break;
            }
            this.a.b();
        }

        public final void onLocationChanged(Location location) {
            Object obj = null;
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                if (latitude != 29.999998211860657d && longitude != 103.99999916553497d && Math.abs(latitude) >= 1.0E-8d && Math.abs(longitude) >= 1.0E-8d && latitude >= -90.0d && latitude <= 90.0d && longitude >= -180.0d && longitude <= 180.0d) {
                    obj = 1;
                }
                if (obj != null) {
                    this.a.j = System.currentTimeMillis();
                    this.a.b();
                    e.a(this.a, 2);
                    this.a.f = new b(this.a, location, this.a.l, this.a.m, this.a.i, this.a.j);
                    if (this.a.e != null) {
                        this.a.e.a(this.a.f);
                    }
                }
            }
        }

        public final void onProviderDisabled(String str) {
            if (str != null) {
                try {
                    if (str.equals("gps")) {
                        this.a.l = this.a.m = 0;
                        this.a.i = 0;
                        if (this.a.e != null) {
                            this.a.e.a(this.a.i);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        public final void onProviderEnabled(String str) {
            if (str != null) {
                try {
                    if (str.equals("gps")) {
                        this.a.i = 4;
                        if (this.a.e != null) {
                            this.a.e.a(this.a.i);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }
    }

    public class b implements Cloneable {
        private Location a = null;
        private long b = 0;
        private int c = 0;

        public b(e eVar, Location location, int i, int i2, int i3, long j) {
            if (location != null) {
                this.a = new Location(location);
                this.c = i2;
                this.b = j;
            }
        }

        public final boolean a() {
            return this.a == null ? false : (this.c <= 0 || this.c >= 3) && System.currentTimeMillis() - this.b <= 30000;
        }

        public final Location b() {
            return this.a;
        }

        public final Object clone() {
            b bVar;
            try {
                bVar = (b) super.clone();
            } catch (Exception e) {
                bVar = null;
            }
            if (this.a != null) {
                bVar.a = new Location(this.a);
            }
            return bVar;
        }
    }

    public interface c {
        void a(int i);

        void a(b bVar);
    }

    static /* synthetic */ int a(e eVar, int i) {
        int i2 = eVar.i | i;
        eVar.i = i2;
        return i2;
    }

    private void b() {
        this.m = 0;
        this.l = 0;
        GpsStatus gpsStatus = b.getGpsStatus(null);
        if (gpsStatus != null) {
            int maxSatellites = gpsStatus.getMaxSatellites();
            Iterator it = gpsStatus.getSatellites().iterator();
            if (it != null) {
                while (it.hasNext() && this.l <= maxSatellites) {
                    this.l++;
                    if (((GpsSatellite) it.next()).usedInFix()) {
                        this.m++;
                    }
                }
            }
        }
    }

    public final void a() {
        synchronized (this.h) {
            if (this.g) {
                if (!(b == null || this.c == null)) {
                    b.removeGpsStatusListener(this.c);
                    b.removeUpdates(this.c);
                }
                this.g = false;
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(com.tencent.map.b.e.c r9, android.content.Context r10) {
        /*
        r8 = this;
        r0 = 1;
        r6 = 0;
        r7 = r8.h;
        monitor-enter(r7);
        r1 = r8.g;	 Catch:{ all -> 0x0068 }
        if (r1 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
    L_0x000a:
        return r0;
    L_0x000b:
        if (r10 == 0) goto L_0x000f;
    L_0x000d:
        if (r9 != 0) goto L_0x0012;
    L_0x000f:
        monitor-exit(r7);
        r0 = r6;
        goto L_0x000a;
    L_0x0012:
        r8.a = r10;	 Catch:{ all -> 0x0068 }
        r8.e = r9;	 Catch:{ all -> 0x0068 }
        r0 = r8.a;	 Catch:{ Exception -> 0x0035 }
        r1 = "location";
        r0 = r0.getSystemService(r1);	 Catch:{ Exception -> 0x0035 }
        r0 = (android.location.LocationManager) r0;	 Catch:{ Exception -> 0x0035 }
        b = r0;	 Catch:{ Exception -> 0x0035 }
        r0 = new com.tencent.map.b.e$a;	 Catch:{ Exception -> 0x0035 }
        r1 = 0;
        r0.<init>();	 Catch:{ Exception -> 0x0035 }
        r8.c = r0;	 Catch:{ Exception -> 0x0035 }
        r0 = b;	 Catch:{ Exception -> 0x0035 }
        if (r0 == 0) goto L_0x0032;
    L_0x002e:
        r0 = r8.c;	 Catch:{ Exception -> 0x0035 }
        if (r0 != 0) goto L_0x0039;
    L_0x0032:
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r6;
        goto L_0x000a;
    L_0x0035:
        r0 = move-exception;
        monitor-exit(r7);
        r0 = r6;
        goto L_0x000a;
    L_0x0039:
        r0 = b;	 Catch:{ Exception -> 0x0064 }
        r1 = "gps";
        r2 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = 0;
        r5 = r8.c;	 Catch:{ Exception -> 0x0064 }
        r0.requestLocationUpdates(r1, r2, r4, r5);	 Catch:{ Exception -> 0x0064 }
        r0 = b;	 Catch:{ Exception -> 0x0064 }
        r1 = r8.c;	 Catch:{ Exception -> 0x0064 }
        r0.addGpsStatusListener(r1);	 Catch:{ Exception -> 0x0064 }
        r0 = b;	 Catch:{ Exception -> 0x0064 }
        r1 = "gps";
        r0 = r0.isProviderEnabled(r1);	 Catch:{ Exception -> 0x0064 }
        if (r0 == 0) goto L_0x0060;
    L_0x0056:
        r0 = 4;
        r8.i = r0;	 Catch:{ Exception -> 0x0064 }
    L_0x0059:
        r0 = 1;
        r8.g = r0;	 Catch:{ all -> 0x0068 }
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r8.g;
        goto L_0x000a;
    L_0x0060:
        r0 = 0;
        r8.i = r0;	 Catch:{ Exception -> 0x0064 }
        goto L_0x0059;
    L_0x0064:
        r0 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0068 }
        r0 = r6;
        goto L_0x000a;
    L_0x0068:
        r0 = move-exception;
        monitor-exit(r7);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.e.a(com.tencent.map.b.e$c, android.content.Context):boolean");
    }
}
