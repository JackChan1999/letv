package com.immersion.hapticmediasdk;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.DefaultAnalyticsCollectors;
import com.immersion.aws.analytics.ImmrAnalytics;
import com.immersion.aws.pm.PolicyManager;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.controllers.MediaController;
import com.immersion.hapticmediasdk.utils.Log;
import com.immersion.hapticmediasdk.utils.RuntimeInfo;
import rrrrrr.crrccc;

public class MediaTaskManager implements Runnable {
    private static final String b042DЭЭ042DЭ042D = "MediaTaskManager";
    public static int b044Aъ044Aъъъ = 2;
    public static int b044Aъъ044Aъъ = 0;
    public static int bъ044Aъъъъ = 42;
    public static int bъъ044Aъъъ = 1;
    private volatile SDKStatus b042D042D042D042DЭ042D;
    private PolicyManager b042D042D042DЭ042D042D;
    private final Object b042D042DЭ042DЭ042D;
    private Context b042D042DЭЭ042D042D;
    private long b042DЭ042D042DЭ042D;
    private ImmrAnalytics b042DЭ042DЭ042D042D;
    private String b042DЭЭЭ042D042D;
    private Handler bЭ042D042D042DЭ042D;
    private crrccc bЭ042D042DЭ042D042D;
    private final Object bЭ042DЭ042DЭ042D;
    private boolean bЭ042DЭЭ042D042D;
    private long bЭЭ042D042DЭ042D;
    private RuntimeInfo bЭЭ042DЭ042D042D;
    private MediaController bЭЭЭЭ042D042D;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaTaskManager(android.os.Handler r3, android.content.Context r4, com.immersion.hapticmediasdk.utils.RuntimeInfo r5, com.immersion.aws.pm.PolicyManager r6) {
        /*
        r2 = this;
        r0 = bъ044Aъъъъ;
        r1 = bъъ044Aъъъ;
        r0 = r0 + r1;
        r1 = bъ044Aъъъъ;
        r0 = r0 * r1;
        r1 = b044Aъ044Aъъъ;
        r0 = r0 % r1;
        r1 = b044A044Aъъъъ();
        if (r0 == r1) goto L_0x001d;
    L_0x0011:
        r0 = bъ044A044Aъъъ();
        bъ044Aъъъъ = r0;
        r0 = bъ044A044Aъъъ();
        bъъ044Aъъъ = r0;
    L_0x001d:
        r2.<init>();
        r0 = new java.lang.Object;
        r0.<init>();
        r2.bЭ042DЭ042DЭ042D = r0;
        r0 = new java.lang.Object;
        r0.<init>();
        r2.b042D042DЭ042DЭ042D = r0;
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.NOT_INITIALIZED;
        r2.b042D042D042D042DЭ042D = r0;
        r2.bЭ042D042D042DЭ042D = r3;
        r2.b042D042DЭЭ042D042D = r4;
        r2.bЭЭ042DЭ042D042D = r5;
    L_0x0038:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0038;
            case 1: goto L_0x0041;
            default: goto L_0x003c;
        };
    L_0x003c:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0041;
            case 1: goto L_0x0038;
            default: goto L_0x0040;
        };
    L_0x0040:
        goto L_0x003c;
    L_0x0041:
        r0 = r2.b042D042DЭЭ042D042D;
        r0 = com.immersion.aws.analytics.ImmrAnalytics.getInstance(r0);
        r2.b042DЭ042DЭ042D042D = r0;
        r0 = rrrrrr.crrccc.NOT_SENT;
        r2.bЭ042D042DЭ042D042D = r0;
        r2.b042D042D042DЭ042D042D = r6;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.<init>(android.os.Handler, android.content.Context, com.immersion.hapticmediasdk.utils.RuntimeInfo, com.immersion.aws.pm.PolicyManager):void");
    }

    private int b0449044904490449щщ() {
        try {
            int onPause = this.bЭЭЭЭ042D042D.onPause();
            if (onPause == 0) {
                this.b042D042D042D042DЭ042D = SDKStatus.PAUSED_DUE_TO_BUFFERING;
                int i = bъ044Aъъъъ;
                switch ((i * (b044A044Aъ044Aъъ() + i)) % b044Aъ044Aъъъ) {
                    case 0:
                        break;
                    default:
                        bъ044Aъъъъ = 10;
                        b044Aъъ044Aъъ = bъ044A044Aъъъ();
                        break;
                }
            }
            return onPause;
        } catch (Exception e) {
            throw e;
        }
    }

    private int b044904490449щщщ() {
        try {
            this.bЭ042D042D042DЭ042D.removeCallbacks(this);
            if (!(this.bЭЭЭЭ042D042D == null || b04490449щ0449щщ() == 0)) {
                Log.e(b042DЭЭ042DЭ042D, "Could not dispose haptics, reset anyway.");
            }
            int i = bъ044Aъъъъ;
            switch ((i * (bъъ044Aъъъ + i)) % b044Aъ044Aъъъ) {
                case 0:
                    break;
                default:
                    bъ044Aъъъъ = bъ044A044Aъъъ();
                    bъъ044Aъъъ = 69;
                    break;
            }
            try {
                this.b042DЭЭЭ042D042D = null;
                this.bЭЭ042D042DЭ042D = 0;
                this.b042D042D042D042DЭ042D = SDKStatus.NOT_INITIALIZED;
                return 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int b04490449щ0449щщ() {
        int bщ0449щ0449щщ = bщ0449щ0449щщ();
        if (bщ0449щ0449щщ == 0) {
            this.bЭЭЭЭ042D042D.onDestroy(this.bЭ042D042D042DЭ042D);
            this.bЭЭЭЭ042D042D = null;
        }
        return bщ0449щ0449щщ;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int b0449щ04490449щщ() {
        /*
        r5 = this;
        r0 = 0;
        r1 = r5.bЭ042D042D042DЭ042D;
        r1.removeCallbacks(r5);
        r1 = r5.bЭ042D042D042DЭ042D;
        r2 = 1500; // 0x5dc float:2.102E-42 double:7.41E-321;
    L_0x000a:
        switch(r0) {
            case 0: goto L_0x0012;
            case 1: goto L_0x000a;
            default: goto L_0x000d;
        };
    L_0x000d:
        r4 = 1;
        switch(r4) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0012;
            default: goto L_0x0011;
        };
    L_0x0011:
        goto L_0x000d;
    L_0x0012:
        r1 = r1.postDelayed(r5, r2);
        if (r1 == 0) goto L_0x002f;
    L_0x0018:
        r1 = bъ044Aъъъъ;
        r2 = bъъ044Aъъъ;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = b044Aъ044Aъъъ;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x002e;
            default: goto L_0x0024;
        };
    L_0x0024:
        r1 = 44;
        bъ044Aъъъъ = r1;
        r1 = bъ044A044Aъъъ();
        b044Aъъ044Aъъ = r1;
    L_0x002e:
        return r0;
    L_0x002f:
        r0 = -1;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.b0449щ04490449щщ():int");
    }

    private int b0449щщ0449щщ() {
        try {
            this.bЭ042D042D042DЭ042D.removeCallbacks(this);
            int onPrepared = this.bЭЭЭЭ042D042D.onPrepared();
            if (onPrepared == 0) {
                try {
                    this.b042D042D042D042DЭ042D = SDKStatus.PLAYING;
                    this.bЭ042D042D042DЭ042D.postDelayed(this, 1500);
                    int bъ044A044Aъъъ = bъ044A044Aъъъ();
                    switch ((bъ044A044Aъъъ * (bъъ044Aъъъ + bъ044A044Aъъъ)) % b044Aъ044Aъъъ) {
                        case 0:
                            break;
                        default:
                            bъ044Aъъъъ = bъ044A044Aъъъ();
                            b044Aъъ044Aъъ = bъ044A044Aъъъ();
                            break;
                    }
                    this.bЭ042D042DЭ042D042D = crrccc.NOT_SENT;
                } catch (Exception e) {
                    throw e;
                }
            }
            return onPrepared;
        } catch (Exception e2) {
            throw e2;
        }
    }

    private void b0449щщщ0449щ() {
        if (this.b042D042DЭЭ042D042D.checkCallingOrSelfPermission("android.permission.INTERNET") == 0) {
            for (AnalyticsDataCollector analyticsDataCollector : new DefaultAnalyticsCollectors().getDefaultAnalytics(this.b042D042DЭЭ042D042D)) {
                ImmrAnalytics immrAnalytics = this.b042DЭ042DЭ042D042D;
                if (((bъ044Aъъъъ + bъъ044Aъъъ) * bъ044Aъъъъ) % b044Aъ044Aъъъ != b044Aъъ044Aъъ) {
                    bъ044Aъъъъ = bъ044A044Aъъъ();
                    b044Aъъ044Aъъ = 2;
                }
                immrAnalytics.addAnalyticsDataCollector(analyticsDataCollector);
            }
            this.b042DЭ042DЭ042D042D.sendAnalytics();
        }
    }

    public static int b044A044Aъ044Aъъ() {
        return 1;
    }

    public static int b044A044Aъъъъ() {
        return 0;
    }

    private int bщ044904490449щщ() {
        int onPause = this.bЭЭЭЭ042D042D.onPause();
        if (onPause == 0) {
            this.b042D042D042D042DЭ042D = SDKStatus.PAUSED_DUE_TO_TIMEOUT;
            if (((bъ044Aъъъъ + bъъ044Aъъъ) * bъ044Aъъъъ) % b044Aъ044Aъъъ != b044Aъъ044Aъъ) {
                bъ044Aъъъъ = 59;
                b044Aъъ044Aъъ = 79;
            }
        }
        return onPause;
    }

    private int bщ0449щ0449щщ() {
        try {
            this.bЭ042D042D042DЭ042D.removeCallbacks(this);
            this.bЭЭ042D042DЭ042D = 0;
            int stopHapticPlayback = this.bЭЭЭЭ042D042D.stopHapticPlayback();
            if (stopHapticPlayback == 0) {
                this.b042D042D042D042DЭ042D = SDKStatus.STOPPED;
                crrccc rrrrrr_crrccc = this.bЭ042D042DЭ042D042D;
                crrccc rrrrrr_crrccc2 = crrccc.SENT;
                if (((bъ044Aъъъъ + bъъ044Aъъъ) * bъ044Aъъъъ) % b044Aъ044Aъъъ != b044A044Aъъъъ()) {
                    bъ044Aъъъъ = 55;
                    b044Aъъ044Aъъ = bъ044A044Aъъъ();
                }
                if (rrrrrr_crrccc != rrrrrr_crrccc2) {
                    try {
                        b0449щщщ0449щ();
                        this.bЭ042D042DЭ042D042D = crrccc.SENT;
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
            return stopHapticPlayback;
        } catch (Exception e2) {
            throw e2;
        }
    }

    private int bщщ04490449щщ() {
        try {
            this.bЭ042D042D042DЭ042D.removeCallbacks(this);
            try {
                int onPause = this.bЭЭЭЭ042D042D.onPause();
                if (onPause == 0) {
                    this.b042D042D042D042DЭ042D = SDKStatus.PAUSED;
                }
                int i = bъ044Aъъъъ;
                switch ((i * (bъъ044Aъъъ + i)) % b044Aъ044Aъъъ) {
                    case 0:
                        break;
                    default:
                        bъ044Aъъъъ = bъ044A044Aъъъ();
                        b044Aъъ044Aъъ = 54;
                        break;
                }
                return onPause;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int bщщщ0449щщ(com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus r9) {
        /*
        r8 = this;
        r7 = 0;
        r0 = -1;
        r1 = r8.bЭ042D042D042DЭ042D;
        r1.removeCallbacks(r8);
        r8.b042D042D042D042DЭ042D = r9;
    L_0x0009:
        r1 = new int[r0];	 Catch:{ Exception -> 0x000c }
        goto L_0x0009;
    L_0x000c:
        r0 = move-exception;
        r0 = bъ044A044Aъъъ();
        bъ044Aъъъъ = r0;
        r0 = r8.b042DЭЭЭ042D042D;
        if (r0 == 0) goto L_0x0048;
    L_0x0017:
        r0 = new com.immersion.hapticmediasdk.controllers.MediaController;
        r1 = r8.bЭ042D042D042DЭ042D;
        r1 = r1.getLooper();
        r0.<init>(r1, r8);
        r8.bЭЭЭЭ042D042D = r0;
        r0 = r8.bЭЭЭЭ042D042D;
        r3 = r0.getControlHandler();
        r0 = new com.immersion.hapticmediasdk.controllers.HapticPlaybackThread;
        r1 = r8.b042D042DЭЭ042D042D;
        r2 = r8.b042DЭЭЭ042D042D;
    L_0x0030:
        switch(r7) {
            case 0: goto L_0x0038;
            case 1: goto L_0x0030;
            default: goto L_0x0033;
        };
    L_0x0033:
        r4 = 1;
        switch(r4) {
            case 0: goto L_0x0030;
            case 1: goto L_0x0038;
            default: goto L_0x0037;
        };
    L_0x0037:
        goto L_0x0033;
    L_0x0038:
        r4 = r8.bЭ042DЭЭ042D042D;
        r5 = r8.bЭЭ042DЭ042D042D;
        r6 = r8.b042D042D042DЭ042D042D;
        r0.<init>(r1, r2, r3, r4, r5, r6);
        r1 = r8.bЭЭЭЭ042D042D;
        r1.initHapticPlayback(r0);
        r0 = r7;
    L_0x0047:
        return r0;
    L_0x0048:
        r0 = -4;
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.bщщщ0449щщ(com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus):int");
    }

    private int bщщщщ0449щ() {
        try {
            int b0449щщ0449щщ = b0449щщ0449щщ();
            if (((bъ044Aъъъъ + bъъ044Aъъъ) * bъ044Aъъъъ) % b044Aъ044Aъъъ != b044Aъъ044Aъъ) {
                bъ044Aъъъъ = bъ044A044Aъъъ();
                b044Aъъ044Aъъ = 26;
            }
            if (b0449щщ0449щщ == 0) {
                try {
                    b0449щщ0449щщ = b0449щ04490449щщ();
                } catch (Exception e) {
                    throw e;
                }
            }
            return b0449щщ0449щщ;
        } catch (Exception e2) {
            throw e2;
        }
    }

    public static int bъ044A044Aъъъ() {
        return 33;
    }

    public static int bъ044Aъ044Aъъ() {
        return 2;
    }

    public int SeekTo(int i) {
        setMediaTimestamp((long) i);
        this.bЭЭЭЭ042D042D.seekTo(i);
        if (getSDKStatus() != SDKStatus.PLAYING) {
            return 0;
        }
        if (((bъ044Aъъъъ + bъъ044Aъъъ) * bъ044Aъъъъ) % bъ044Aъ044Aъъ() != b044Aъъ044Aъъ) {
            bъ044Aъъъъ = 94;
            b044Aъъ044Aъъ = 7;
        }
        return this.bЭЭЭЭ042D042D.prepareHapticPlayback();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getMediaReferenceTime() {
        /*
        r4 = this;
        r0 = 1;
        r1 = r4.b042D042DЭ042DЭ042D;
    L_0x0003:
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0006;
        };
    L_0x0006:
        switch(r0) {
            case 0: goto L_0x0003;
            case 1: goto L_0x000a;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0006;
    L_0x000a:
        monitor-enter(r1);
        r2 = r4.b042DЭ042D042DЭ042D;	 Catch:{ all -> 0x000f }
        monitor-exit(r1);	 Catch:{ all -> 0x000f }
        return r2;
    L_0x000f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x000f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.getMediaReferenceTime():long");
    }

    public long getMediaTimestamp() {
        long j;
        synchronized (this.b042D042DЭ042DЭ042D) {
            j = this.bЭЭ042D042DЭ042D;
        }
        return j;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus getSDKStatus() {
        /*
        r2 = this;
        r1 = r2.bЭ042DЭ042DЭ042D;
        monitor-enter(r1);
        r0 = r2.b042D042D042D042DЭ042D;	 Catch:{ all -> 0x0010 }
        monitor-exit(r1);	 Catch:{ all -> 0x0010 }
    L_0x0006:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0006;
            default: goto L_0x000a;
        };	 Catch:{ all -> 0x0010 }
    L_0x000a:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0006;
            case 1: goto L_0x000f;
            default: goto L_0x000e;
        };	 Catch:{ all -> 0x0010 }
    L_0x000e:
        goto L_0x000a;
    L_0x000f:
        return r0;
    L_0x0010:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0010 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.getSDKStatus():com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r2 = this;
        r0 = 0;
        java.lang.System.currentTimeMillis();
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0004;
            default: goto L_0x0007;
        };
    L_0x0007:
        switch(r0) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0004;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0007;
    L_0x000b:
        r0 = bъ044Aъъъъ;
        r1 = b044A044Aъ044Aъъ();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bъ044Aъ044Aъъ();
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0024;
            default: goto L_0x001b;
        };
    L_0x001b:
        r0 = 2;
        bъ044Aъъъъ = r0;
        r0 = bъ044A044Aъъъ();
        b044Aъъ044Aъъ = r0;
    L_0x0024:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;
        r2.transitToState(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.run():void");
    }

    public void setHapticsUrl(String str, boolean z) {
        synchronized (this.bЭ042DЭ042DЭ042D) {
            this.b042DЭЭЭ042D042D = str;
            this.bЭ042DЭЭ042D042D = z;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMediaReferenceTime() {
        /*
        r4 = this;
        r1 = r4.b042D042DЭ042DЭ042D;
        monitor-enter(r1);
        r0 = r4.b042D042D042D042DЭ042D;	 Catch:{ all -> 0x001f }
    L_0x0005:
        r2 = 1;
        switch(r2) {
            case 0: goto L_0x0005;
            case 1: goto L_0x000e;
            default: goto L_0x0009;
        };	 Catch:{ all -> 0x001f }
    L_0x0009:
        r2 = 0;
        switch(r2) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0005;
            default: goto L_0x000d;
        };	 Catch:{ all -> 0x001f }
    L_0x000d:
        goto L_0x0009;
    L_0x000e:
        r2 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001f }
        if (r0 != r2) goto L_0x0017;
    L_0x0012:
        r0 = r4.bЭЭЭЭ042D042D;	 Catch:{ all -> 0x001f }
        r0.waitHapticStopped();	 Catch:{ all -> 0x001f }
    L_0x0017:
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x001f }
        r4.b042DЭ042D042DЭ042D = r2;	 Catch:{ all -> 0x001f }
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        return;
    L_0x001f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.setMediaReferenceTime():void");
    }

    public void setMediaTimestamp(long j) {
        synchronized (this.b042D042DЭ042DЭ042D) {
            if (this.b042D042D042D042DЭ042D == SDKStatus.STOPPED) {
                this.bЭЭЭЭ042D042D.waitHapticStopped();
            }
            this.b042DЭ042D042DЭ042D = SystemClock.uptimeMillis();
            this.bЭЭ042D042DЭ042D = j;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int transitToState(com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus r7) {
        /*
        r6 = this;
        r1 = 0;
        r0 = -1;
        r2 = r6.bЭ042DЭ042DЭ042D;
        monitor-enter(r2);
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.NOT_INITIALIZED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x000f;
    L_0x0009:
        r0 = r6.b044904490449щщщ();	 Catch:{ all -> 0x001e }
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
    L_0x000e:
        return r0;
    L_0x000f:
        r3 = rrrrrr.crcrcr.b041B041BЛ041B041B041B;	 Catch:{ all -> 0x001e }
        r4 = r6.b042D042D042D042DЭ042D;	 Catch:{ all -> 0x001e }
        r4 = r4.ordinal();	 Catch:{ all -> 0x001e }
        r3 = r3[r4];	 Catch:{ all -> 0x001e }
        switch(r3) {
            case 1: goto L_0x0049;
            case 2: goto L_0x0052;
            case 3: goto L_0x0104;
            case 4: goto L_0x0064;
            case 5: goto L_0x011c;
            case 6: goto L_0x013b;
            case 7: goto L_0x0142;
            default: goto L_0x001c;
        };	 Catch:{ all -> 0x001e }
    L_0x001c:
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
        goto L_0x000e;
    L_0x001e:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001e }
        throw r0;
    L_0x0021:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0025:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x002e:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x003f;
    L_0x0032:
        r0 = r6.bЭЭЭЭ042D042D;	 Catch:{ all -> 0x001e }
        r4 = r6.bЭЭ042D042DЭ042D;	 Catch:{ all -> 0x001e }
        r1 = (int) r4;	 Catch:{ all -> 0x001e }
        r0.setRequestBufferPosition(r1);	 Catch:{ all -> 0x001e }
        r0 = r6.bщщщщ0449щ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x003f:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00fa;
    L_0x0043:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r0;	 Catch:{ all -> 0x001e }
        r0 = r1;
        goto L_0x001c;
    L_0x0049:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.INITIALIZED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x004d:
        r0 = r6.bщщщ0449щщ(r7);	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0052:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x005b;
    L_0x0056:
        r0 = r6.bщщщщ0449щ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x005b:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0075;
    L_0x005f:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0064:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00b6;
    L_0x0068:
        r0 = r6.bЭЭЭЭ042D042D;	 Catch:{ all -> 0x001e }
        r4 = r6.bЭЭ042D042DЭ042D;	 Catch:{ all -> 0x001e }
        r1 = (int) r4;	 Catch:{ all -> 0x001e }
        r0.setRequestBufferPosition(r1);	 Catch:{ all -> 0x001e }
        r0 = r6.bщщщщ0449щ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0075:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0079:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0082:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x008b;
    L_0x0086:
        r0 = r6.bщщ04490449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x008b:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x009b;
    L_0x008f:
        r0 = "MediaTaskManager";
        r1 = "Haptic playback is paused due to update time-out. Call update() to resume playback";
        com.immersion.hapticmediasdk.utils.Log.w(r0, r1);	 Catch:{ all -> 0x001e }
        r0 = r6.bщ044904490449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x009b:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_BUFFERING;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x00ac;
    L_0x009f:
        r0 = r6.b0449044904490449щщ();	 Catch:{ all -> 0x001e }
        r1 = "MediaTaskManager";
        r3 = "Haptic playback is paused due to slow data buffering...";
        com.immersion.hapticmediasdk.utils.Log.w(r1, r3);	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00ac:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x00ec;
    L_0x00b0:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00b6:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00da;
    L_0x00ba:
        r0 = r1;
        goto L_0x001c;
    L_0x00bd:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00cf;
    L_0x00c1:
        r0 = r6.bЭЭЭЭ042D042D;	 Catch:{ all -> 0x001e }
        r4 = r6.bЭЭ042D042DЭ042D;	 Catch:{ all -> 0x001e }
        r1 = (int) r4;	 Catch:{ all -> 0x001e }
        r0.setRequestBufferPosition(r1);	 Catch:{ all -> 0x001e }
        r0 = r6.bщщщщ0449щ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00cf:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x0123;
    L_0x00d3:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r0;	 Catch:{ all -> 0x001e }
        r0 = r1;
        goto L_0x001c;
    L_0x00da:
        switch(r1) {
            case 0: goto L_0x00e2;
            case 1: goto L_0x00da;
            default: goto L_0x00dd;
        };	 Catch:{ all -> 0x001e }
    L_0x00dd:
        r3 = 1;
        switch(r3) {
            case 0: goto L_0x00da;
            case 1: goto L_0x00e2;
            default: goto L_0x00e1;
        };	 Catch:{ all -> 0x001e }
    L_0x00e1:
        goto L_0x00dd;
    L_0x00e2:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0021;
    L_0x00e6:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00ec:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x00f0:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x00fa:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x010e;
    L_0x00fe:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x0104:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x0082;
    L_0x0108:
        r0 = r6.b0449щ04490449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x010e:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0112:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x011c:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_TIMEOUT;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x00bd;
    L_0x0120:
        r0 = r1;
        goto L_0x001c;
    L_0x0123:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x012d;
    L_0x0127:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x012d:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        if (r7 != r1) goto L_0x001c;
    L_0x0131:
        r0 = r6.bщ0449щ0449щщ();	 Catch:{ all -> 0x001e }
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;	 Catch:{ all -> 0x001e }
        r6.b042D042D042D042DЭ042D = r1;	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x013b:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_BUFFERING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x002e;
    L_0x013f:
        r0 = r1;
        goto L_0x001c;
    L_0x0142:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x014c;
    L_0x0146:
        r0 = r6.bщщщщ0449щ();	 Catch:{ all -> 0x001e }
        goto L_0x001c;
    L_0x014c:
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;	 Catch:{ all -> 0x001e }
        if (r7 != r3) goto L_0x001c;
    L_0x0150:
        r0 = r1;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaTaskManager.transitToState(com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus):int");
    }
}
