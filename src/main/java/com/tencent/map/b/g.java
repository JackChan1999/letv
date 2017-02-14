package com.tencent.map.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

public final class g {
    private Context a = null;
    private WifiManager b = null;
    private a c = null;
    private Handler d = null;
    private Runnable e = new Runnable(this) {
        private /* synthetic */ g a;

        {
            this.a = r1;
        }

        public final void run() {
            g.a(this.a);
        }
    };
    private int f = 1;
    private c g = null;
    private b h = null;
    private boolean i = false;
    private byte[] j = new byte[0];

    public interface c {
        void a(b bVar);

        void b(int i);
    }

    public class a extends BroadcastReceiver {
        private int a = 4;
        private List<ScanResult> b = null;
        private boolean c = false;
        private /* synthetic */ g d;

        public a(g gVar) {
            this.d = gVar;
        }

        private void a(List<ScanResult> list) {
            if (list != null) {
                if (this.c) {
                    if (this.b == null) {
                        this.b = new ArrayList();
                    }
                    int size = this.b.size();
                    for (ScanResult scanResult : list) {
                        for (int i = 0; i < size; i++) {
                            if (((ScanResult) this.b.get(i)).BSSID.equals(scanResult.BSSID)) {
                                this.b.remove(i);
                                break;
                            }
                        }
                        this.b.add(scanResult);
                    }
                    return;
                }
                if (this.b == null) {
                    this.b = new ArrayList();
                } else {
                    this.b.clear();
                }
                for (ScanResult scanResult2 : list) {
                    this.b.add(scanResult2);
                }
            }
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                this.a = intent.getIntExtra("wifi_state", 4);
                if (this.d.g != null) {
                    this.d.g.b(this.a);
                }
            }
            if (intent.getAction().equals("android.net.wifi.SCAN_RESULTS") || intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                List list = null;
                if (this.d.b != null) {
                    list = this.d.b.getScanResults();
                }
                if (intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
                    if (list == null) {
                        return;
                    }
                    if (list != null && list.size() == 0) {
                        return;
                    }
                }
                if (this.c || this.b == null || this.b.size() < 4 || list == null || list.size() > 2) {
                    a(list);
                    this.c = false;
                    this.d.h = new b(this.d, this.b, System.currentTimeMillis(), this.a);
                    if (this.d.g != null) {
                        this.d.g.a(this.d.h);
                    }
                    this.d.a(((long) this.d.f) * 20000);
                    return;
                }
                a(list);
                this.c = true;
                this.d.a(0);
            }
        }
    }

    public class b implements Cloneable {
        private List<ScanResult> a = null;

        public b(g gVar, List<ScanResult> list, long j, int i) {
            if (list != null) {
                this.a = new ArrayList();
                for (ScanResult add : list) {
                    this.a.add(add);
                }
            }
        }

        public final List<ScanResult> a() {
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
                bVar.a = new ArrayList();
                bVar.a.addAll(this.a);
            }
            return bVar;
        }
    }

    static /* synthetic */ void a(g gVar) {
        if (gVar.b != null && gVar.b.isWifiEnabled()) {
            gVar.b.startScan();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
        r3 = this;
        r1 = r3.j;
        monitor-enter(r1);
        r0 = r3.i;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
    L_0x0008:
        return;
    L_0x0009:
        r0 = r3.a;	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x0011;
    L_0x000d:
        r0 = r3.c;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0016;
    L_0x0011:
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0013:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
    L_0x0016:
        r0 = r3.a;	 Catch:{ Exception -> 0x0029 }
        r2 = r3.c;	 Catch:{ Exception -> 0x0029 }
        r0.unregisterReceiver(r2);	 Catch:{ Exception -> 0x0029 }
    L_0x001d:
        r0 = r3.d;	 Catch:{ all -> 0x0013 }
        r2 = r3.e;	 Catch:{ all -> 0x0013 }
        r0.removeCallbacks(r2);	 Catch:{ all -> 0x0013 }
        r0 = 0;
        r3.i = r0;	 Catch:{ all -> 0x0013 }
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x0008;
    L_0x0029:
        r0 = move-exception;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.g.a():void");
    }

    public final void a(long j) {
        if (this.d != null && this.i) {
            this.d.removeCallbacks(this.e);
            this.d.postDelayed(this.e, j);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(android.content.Context r6, com.tencent.map.b.g.c r7, int r8) {
        /*
        r5 = this;
        r0 = 1;
        r1 = 0;
        r2 = r5.j;
        monitor-enter(r2);
        r3 = r5.i;	 Catch:{ all -> 0x0069 }
        if (r3 == 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r2);	 Catch:{ all -> 0x0069 }
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
        r0 = new android.os.Handler;	 Catch:{ all -> 0x0069 }
        r3 = android.os.Looper.getMainLooper();	 Catch:{ all -> 0x0069 }
        r0.<init>(r3);	 Catch:{ all -> 0x0069 }
        r5.d = r0;	 Catch:{ all -> 0x0069 }
        r5.a = r6;	 Catch:{ all -> 0x0069 }
        r5.g = r7;	 Catch:{ all -> 0x0069 }
        r0 = 1;
        r5.f = r0;	 Catch:{ all -> 0x0069 }
        r0 = r5.a;	 Catch:{ Exception -> 0x0065 }
        r3 = "wifi";
        r0 = r0.getSystemService(r3);	 Catch:{ Exception -> 0x0065 }
        r0 = (android.net.wifi.WifiManager) r0;	 Catch:{ Exception -> 0x0065 }
        r5.b = r0;	 Catch:{ Exception -> 0x0065 }
        r0 = new android.content.IntentFilter;	 Catch:{ Exception -> 0x0065 }
        r0.<init>();	 Catch:{ Exception -> 0x0065 }
        r3 = new com.tencent.map.b.g$a;	 Catch:{ Exception -> 0x0065 }
        r3.<init>(r5);	 Catch:{ Exception -> 0x0065 }
        r5.c = r3;	 Catch:{ Exception -> 0x0065 }
        r3 = r5.b;	 Catch:{ Exception -> 0x0065 }
        if (r3 == 0) goto L_0x0045;
    L_0x0041:
        r3 = r5.c;	 Catch:{ Exception -> 0x0065 }
        if (r3 != 0) goto L_0x0048;
    L_0x0045:
        monitor-exit(r2);	 Catch:{ all -> 0x0069 }
        r0 = r1;
        goto L_0x000a;
    L_0x0048:
        r3 = "android.net.wifi.WIFI_STATE_CHANGED";
        r0.addAction(r3);	 Catch:{ Exception -> 0x0065 }
        r3 = "android.net.wifi.SCAN_RESULTS";
        r0.addAction(r3);	 Catch:{ Exception -> 0x0065 }
        r3 = r5.a;	 Catch:{ Exception -> 0x0065 }
        r4 = r5.c;	 Catch:{ Exception -> 0x0065 }
        r3.registerReceiver(r4, r0);	 Catch:{ Exception -> 0x0065 }
        r0 = 0;
        r5.a(r0);	 Catch:{ all -> 0x0069 }
        r0 = 1;
        r5.i = r0;	 Catch:{ all -> 0x0069 }
        monitor-exit(r2);	 Catch:{ all -> 0x0069 }
        r0 = r5.i;
        goto L_0x000a;
    L_0x0065:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0069 }
        r0 = r1;
        goto L_0x000a;
    L_0x0069:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.map.b.g.a(android.content.Context, com.tencent.map.b.g$c, int):boolean");
    }

    public final boolean b() {
        return this.i;
    }

    public final boolean c() {
        return (this.a == null || this.b == null) ? false : this.b.isWifiEnabled();
    }
}
