package com.tencent.map.b;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class d {
    private Context a = null;
    private TelephonyManager b = null;
    private a c = null;
    private c d = null;
    private b e = null;
    private boolean f = false;
    private List<NeighboringCellInfo> g = new LinkedList();
    private byte[] h = new byte[0];
    private byte[] i = new byte[0];
    private boolean j = false;

    public class a extends PhoneStateListener {
        private int a = 0;
        private int b = 0;
        private int c = 0;
        private int d = 0;
        private int e = 0;
        private int f = -1;
        private int g = Integer.MAX_VALUE;
        private int h = Integer.MAX_VALUE;
        private Method i = null;
        private Method j = null;
        private Method k = null;
        private Method l = null;
        private Method m = null;
        private /* synthetic */ d n;

        public a(d dVar, int i, int i2) {
            this.n = dVar;
            this.b = i;
            this.a = i2;
        }

        public final void onCellLocationChanged(CellLocation cellLocation) {
            GsmCellLocation gsmCellLocation;
            this.f = -1;
            this.e = -1;
            this.d = -1;
            this.c = -1;
            if (cellLocation != null) {
                switch (this.a) {
                    case 1:
                        Object obj;
                        String networkOperator;
                        try {
                            gsmCellLocation = (GsmCellLocation) cellLocation;
                            try {
                                if (gsmCellLocation.getLac() <= 0 && gsmCellLocation.getCid() <= 0) {
                                    gsmCellLocation = (GsmCellLocation) this.n.b.getCellLocation();
                                }
                                obj = 1;
                            } catch (Exception e) {
                                obj = null;
                                networkOperator = this.n.b.getNetworkOperator();
                                if (networkOperator != null) {
                                    try {
                                        if (networkOperator.length() > 3) {
                                            this.c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                        }
                                    } catch (Exception e2) {
                                        this.e = -1;
                                        this.d = -1;
                                        this.c = -1;
                                    }
                                }
                                this.d = gsmCellLocation.getLac();
                                this.e = gsmCellLocation.getCid();
                                d.c(this.n);
                                this.n.e = new b(this.n, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
                                if (this.n.d == null) {
                                    this.n.d.a(this.n.e);
                                }
                            }
                        } catch (Exception e3) {
                            gsmCellLocation = null;
                            obj = null;
                            networkOperator = this.n.b.getNetworkOperator();
                            if (networkOperator != null) {
                                if (networkOperator.length() > 3) {
                                    this.c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                }
                            }
                            this.d = gsmCellLocation.getLac();
                            this.e = gsmCellLocation.getCid();
                            d.c(this.n);
                            this.n.e = new b(this.n, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
                            if (this.n.d == null) {
                                this.n.d.a(this.n.e);
                            }
                        }
                        if (!(obj == null || gsmCellLocation == null)) {
                            networkOperator = this.n.b.getNetworkOperator();
                            if (networkOperator != null) {
                                if (networkOperator.length() > 3) {
                                    this.c = Integer.valueOf(networkOperator.substring(3)).intValue();
                                }
                            }
                            this.d = gsmCellLocation.getLac();
                            this.e = gsmCellLocation.getCid();
                            d.c(this.n);
                        }
                    case 2:
                        if (cellLocation != null) {
                            try {
                                if (this.i == null) {
                                    this.i = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationId", new Class[0]);
                                    this.j = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getSystemId", new Class[0]);
                                    this.k = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getNetworkId", new Class[0]);
                                    this.l = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationLatitude", new Class[0]);
                                    this.m = Class.forName("android.telephony.cdma.CdmaCellLocation").getMethod("getBaseStationLongitude", new Class[0]);
                                }
                                this.c = ((Integer) this.j.invoke(cellLocation, new Object[0])).intValue();
                                this.d = ((Integer) this.k.invoke(cellLocation, new Object[0])).intValue();
                                this.e = ((Integer) this.i.invoke(cellLocation, new Object[0])).intValue();
                                this.g = ((Integer) this.l.invoke(cellLocation, new Object[0])).intValue();
                                this.h = ((Integer) this.m.invoke(cellLocation, new Object[0])).intValue();
                                break;
                            } catch (Exception e4) {
                                this.e = -1;
                                this.d = -1;
                                this.c = -1;
                                this.g = Integer.MAX_VALUE;
                                this.h = Integer.MAX_VALUE;
                                break;
                            }
                        }
                        break;
                }
            }
            this.n.e = new b(this.n, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
            if (this.n.d == null) {
                this.n.d.a(this.n.e);
            }
        }

        public final void onSignalStrengthChanged(int i) {
            if (this.a == 1) {
                d.c(this.n);
            }
            if (Math.abs(i - ((this.f + 113) / 2)) <= 3) {
                return;
            }
            if (this.f == -1) {
                this.f = (i << 1) - 113;
                return;
            }
            this.f = (i << 1) - 113;
            this.n.e = new b(this.n, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
            if (this.n.d != null) {
                this.n.d.a(this.n.e);
            }
        }
    }

    public class b implements Cloneable {
        public int a = 0;
        public int b = 0;
        public int c = 0;
        public int d = 0;
        public int e = 0;
        public int f = 0;
        public int g = Integer.MAX_VALUE;
        public int h = Integer.MAX_VALUE;

        public b(d dVar, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
            this.g = i7;
            this.h = i8;
        }

        public final Object clone() {
            try {
                return (b) super.clone();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public interface c {
        void a(b bVar);
    }

    private int a(int i) {
        int intValue;
        String networkOperator = this.b.getNetworkOperator();
        if (networkOperator != null && networkOperator.length() >= 3) {
            try {
                intValue = Integer.valueOf(networkOperator.substring(0, 3)).intValue();
            } catch (Exception e) {
            }
            return (i == 2 || intValue != -1) ? intValue : 0;
        }
        intValue = -1;
        if (i == 2) {
        }
    }

    static /* synthetic */ void c(d dVar) {
        if (!dVar.j) {
            dVar.j = true;
            new Thread(dVar) {
                private /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public final void run() {
                    if (this.a.b != null) {
                        Collection neighboringCellInfo = this.a.b.getNeighboringCellInfo();
                        synchronized (this.a.i) {
                            if (neighboringCellInfo != null) {
                                this.a.g.clear();
                                this.a.g.addAll(neighboringCellInfo);
                            }
                        }
                    }
                    this.a.j = false;
                }
            }.start();
        }
    }

    public final void a() {
        synchronized (this.h) {
            if (this.f) {
                if (!(this.b == null || this.c == null)) {
                    try {
                        this.b.listen(this.c, 0);
                    } catch (Exception e) {
                        this.f = false;
                    }
                }
                this.f = false;
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Context r6, com.tencent.map.b.d.c r7) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = r5.h;
        monitor-enter(r2);
        r3 = r5.f;	 Catch:{ all -> 0x0056 }
        if (r3 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r2);	 Catch:{ all -> 0x0056 }
    L_0x000a:
        return r0;
    L_0x000b:
        if (r6 == 0) goto L_0x000f;
    L_0x000d:
        if (r7 != 0) goto L_0x0012;
    L_0x000f:
        monitor-exit(r2);
        r0 = r1;
        goto L_0x000a;
    L_0x0012:
        r5.a = r6;	 Catch:{ all -> 0x0056 }
        r5.d = r7;	 Catch:{ all -> 0x0056 }
        r0 = r5.a;	 Catch:{ Exception -> 0x0052 }
        r3 = "phone";
        r0 = r0.getSystemService(r3);	 Catch:{ Exception -> 0x0052 }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x0052 }
        r5.b = r0;	 Catch:{ Exception -> 0x0052 }
        r0 = r5.b;	 Catch:{ Exception -> 0x0052 }
        if (r0 != 0) goto L_0x002a;
    L_0x0027:
        monitor-exit(r2);	 Catch:{ all -> 0x0056 }
        r0 = r1;
        goto L_0x000a;
    L_0x002a:
        r0 = r5.b;	 Catch:{ Exception -> 0x0052 }
        r0 = r0.getPhoneType();	 Catch:{ Exception -> 0x0052 }
        r3 = r5.a(r0);	 Catch:{ Exception -> 0x0052 }
        r4 = new com.tencent.map.b.d$a;	 Catch:{ Exception -> 0x0052 }
        r4.<init>(r5, r3, r0);	 Catch:{ Exception -> 0x0052 }
        r5.c = r4;	 Catch:{ Exception -> 0x0052 }
        r0 = r5.c;	 Catch:{ Exception -> 0x0052 }
        if (r0 != 0) goto L_0x0042;
    L_0x003f:
        monitor-exit(r2);	 Catch:{ all -> 0x0056 }
        r0 = r1;
        goto L_0x000a;
    L_0x0042:
        r0 = r5.b;	 Catch:{ Exception -> 0x0052 }
        r3 = r5.c;	 Catch:{ Exception -> 0x0052 }
        r4 = 18;
        r0.listen(r3, r4);	 Catch:{ Exception -> 0x0052 }
        r0 = 1;
        r5.f = r0;	 Catch:{ all -> 0x0056 }
        monitor-exit(r2);	 Catch:{ all -> 0x0056 }
        r0 = r5.f;
        goto L_0x000a;
    L_0x0052:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0056 }
        r0 = r1;
        goto L_0x000a;
    L_0x0056:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.d.a(android.content.Context, com.tencent.map.b.d$c):boolean");
    }

    public final List<NeighboringCellInfo> b() {
        List<NeighboringCellInfo> list = null;
        synchronized (this.i) {
            if (this.g != null) {
                list = new LinkedList();
                list.addAll(this.g);
            }
        }
        return list;
    }
}
