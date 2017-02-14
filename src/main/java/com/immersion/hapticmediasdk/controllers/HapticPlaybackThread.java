package com.immersion.hapticmediasdk.controllers;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.immersion.Log;
import com.immersion.aws.analytics.AnalyticsDataCollector;
import com.immersion.aws.analytics.ImmrAnalytics;
import com.immersion.aws.pm.PolicyManager;
import com.immersion.content.EndpointWarp;
import com.immersion.hapticmediasdk.utils.FileManager;
import com.immersion.hapticmediasdk.utils.Profiler;
import com.immersion.hapticmediasdk.utils.RuntimeInfo;
import java.util.ArrayList;
import rrrrrr.ccrccr;
import rrrrrr.rrcrrr;

public class HapticPlaybackThread extends Thread {
    public static final int HAPTIC_BYTES_AVAILABLE_TO_DOWNLOAD = 3;
    public static final int HAPTIC_DOWNLOAD_ERROR = 8;
    public static final String HAPTIC_DOWNLOAD_EXCEPTION_KEY = "haptic_download_exception";
    public static final int HAPTIC_PAUSE_PLAYBACK = 5;
    public static final int HAPTIC_PLAYBACK_FOR_TIME_CODE = 2;
    public static final int HAPTIC_PLAYBACK_IS_READY = 6;
    public static final int HAPTIC_QUIT_PLAYBACK = 9;
    public static final int HAPTIC_SET_BUFFERING_POSITION = 1;
    public static final int HAPTIC_STOP_PLAYBACK = 4;
    public static final int PAUSE_AV_FOR_HAPTIC_BUFFERING = 7;
    public static int b04290429042904290429Щ = 3;
    public static int b0429ЩЩЩЩ0429 = 1;
    private static final long b044E044Eю044E044E044E = 100;
    private static final String b044E044Eю044E044Eю = "playback_timecode";
    private static final String b044Eюю044E044Eю = "HapticPlaybackThread";
    public static int bЩ0429ЩЩЩ0429 = 2;
    public static int bЩЩЩЩЩ0429 = 0;
    private static final int bю044Eю044E044Eю = Integer.MIN_VALUE;
    private static final int bюю044E044E044E044E = 5;
    private static final String bюю044E044E044Eю = "playback_uptime";
    private Handler b044E044E044E044E044Eю;
    private long b044E044E044E044Eю044E;
    private RuntimeInfo b044E044E044Eю044E044E;
    private Object b044E044E044Eюю044E = new Object();
    private long b044E044Eю044Eю044E;
    public Context b044E044Eюю044E044E;
    private IHapticFileReader b044E044Eююю044E;
    private final Runnable b044Eю044E044E044E044E = new ccrccr(this);
    private int b044Eю044E044E044Eю = 0;
    private int b044Eю044E044Eю044E;
    public volatile boolean b044Eю044Eю044E044E = false;
    private final Profiler b044Eю044Eюю044E = new Profiler();
    private FileManager b044Eюю044E044E044E;
    private int b044Eюю044Eю044E;
    private boolean b044Eююю044E044E = false;
    private HapticDownloadThread b044Eюююю044E;
    private final Runnable bю044E044E044E044E044E;
    private final String bю044E044E044E044Eю;
    private int bю044E044E044Eю044E;
    private ArrayList bю044E044Eю044E044E;
    private Object bю044E044Eюю044E = new Object();
    private PolicyManager bю044Eю044E044E044E;
    private int bю044Eю044Eю044E;
    private boolean bю044Eюю044E044E = false;
    private Looper bю044Eююю044E;
    private int bюю044E044Eю044E;
    public volatile boolean bюю044Eю044E044E = false;
    private EndpointWarp bюю044Eюю044E;
    private boolean bююю044E044E044E = false;
    private int bююю044Eю044E;
    private ImmrAnalytics bюююю044E044E;
    private final Handler bююююю044E;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public HapticPlaybackThread(android.content.Context r4, java.lang.String r5, android.os.Handler r6, boolean r7, com.immersion.hapticmediasdk.utils.RuntimeInfo r8, com.immersion.aws.pm.PolicyManager r9) {
        /*
        r3 = this;
        r2 = 0;
        r0 = "HapticPlaybackThread";
        r3.<init>(r0);
        r3.b044Eю044E044E044Eю = r2;
        r0 = new com.immersion.hapticmediasdk.utils.Profiler;
        r0.<init>();
        r3.b044Eю044Eюю044E = r0;
        r0 = new java.lang.Object;
        r0.<init>();
        r3.bю044E044Eюю044E = r0;
        r0 = new java.lang.Object;
        r0.<init>();
        r3.b044E044E044Eюю044E = r0;
        r3.b044Eююю044E044E = r2;
        r3.bю044Eюю044E044E = r2;
        r3.bюю044Eю044E044E = r2;
        r3.b044Eю044Eю044E044E = r2;
        r3.bююю044E044E044E = r2;
        r0 = new rrrrrr.ccrccr;
        r0.<init>(r3);
        r3.b044Eю044E044E044E044E = r0;
        r0 = new rrrrrr.rrcccr;
    L_0x0030:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0030;
            case 1: goto L_0x0038;
            default: goto L_0x0034;
        };
    L_0x0034:
        switch(r2) {
            case 0: goto L_0x0038;
            case 1: goto L_0x0030;
            default: goto L_0x0037;
        };
    L_0x0037:
        goto L_0x0034;
    L_0x0038:
        r0.<init>(r3);
        r3.bю044E044E044E044E044E = r0;
        r3.bю044E044E044E044Eю = r5;
        r3.bююююю044E = r6;
        r3.b044E044Eюю044E044E = r4;
        r3.bююю044E044E044E = r7;
        r0 = new com.immersion.hapticmediasdk.utils.FileManager;
        r0.<init>(r4);
        r3.b044Eюю044E044E044E = r0;
        r3.b044E044E044Eю044E044E = r8;
        r0 = new java.util.ArrayList;
        r1 = b04290429042904290429Щ;
        r2 = b0429ЩЩЩЩ0429;
        r1 = r1 + r2;
        r2 = b04290429042904290429Щ;
        r1 = r1 * r2;
        r2 = bЩ0429ЩЩЩ0429;
        r1 = r1 % r2;
        r2 = bЩЩЩЩЩ0429;
        if (r1 == r2) goto L_0x006b;
    L_0x005f:
        r1 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r1;
        r1 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r1;
    L_0x006b:
        r0.<init>();
        r3.bю044E044Eю044E044E = r0;
        r0 = com.immersion.aws.analytics.ImmrAnalytics.getInstance(r4);
        r3.bюююю044E044E = r0;
        r3.bю044Eю044E044E044E = r9;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.<init>(android.content.Context, java.lang.String, android.os.Handler, boolean, com.immersion.hapticmediasdk.utils.RuntimeInfo, com.immersion.aws.pm.PolicyManager):void");
    }

    public static int b04290429ЩЩЩ0429() {
        return 30;
    }

    public static int b0429Щ04290429Щ0429() {
        return 1;
    }

    public static /* synthetic */ int b04390439043904390439й(HapticPlaybackThread hapticPlaybackThread, int i) {
        int i2 = b04290429042904290429Щ;
        switch ((i2 * (b0429ЩЩЩЩ0429 + i2)) % bЩ0429ЩЩЩ0429) {
            case 0:
                break;
            default:
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                break;
        }
        try {
            hapticPlaybackThread.bююю044Eю044E = i;
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ void b0439043904390439й0439(HapticPlaybackThread hapticPlaybackThread) {
        if (((b04290429042904290429Щ + b0429Щ04290429Щ0429()) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = b04290429ЩЩЩ0429();
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
        hapticPlaybackThread.bйй043904390439й();
    }

    public static /* synthetic */ int b043904390439й04390439(HapticPlaybackThread hapticPlaybackThread, int i) {
        try {
            hapticPlaybackThread.bюю044E044Eю044E = i;
            if (((b04290429042904290429Щ + b0429Щ04290429Щ0429()) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = 90;
            }
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    private void b043904390439й0439й() {
        try {
            if (!this.b044Eююю044E044E) {
                int i = this.bю044Eю044Eю044E;
                this.bю044Eю044Eю044E = i + 1;
                if (i == 5) {
                    this.bююююю044E.sendMessage(this.bююююю044E.obtainMessage(7, this.bююю044Eю044E, 0));
                    this.b044E044E044E044E044Eю.postDelayed(this.b044Eю044E044E044E044E, b044E044Eю044E044E044E);
                    return;
                }
                try {
                    if (this.b044E044Eююю044E != null) {
                        if (this.b044E044Eююю044E.bufferAtPlaybackPosition(this.bююю044Eю044E)) {
                            if (this.b044Eюю044Eю044E != Integer.MIN_VALUE) {
                                i = b04290429042904290429Щ;
                                switch ((i * (b0429ЩЩЩЩ0429 + i)) % bЩ0429ЩЩЩ0429) {
                                    case 0:
                                        break;
                                    default:
                                        b04290429042904290429Щ = 52;
                                        bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                                        break;
                                }
                                this.bююююю044E.sendMessage(this.bююююю044E.obtainMessage(6, this.bююю044Eю044E, this.b044Eюю044Eю044E));
                                return;
                            }
                            return;
                        }
                    }
                    this.b044E044E044E044E044Eю.postDelayed(this.b044Eю044E044E044E044E, b044E044Eю044E044E044E);
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.hapticmediasdk.utils.FileManager b043904390439йй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x0022;
    L_0x0018:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = 56;
        bЩЩЩЩЩ0429 = r0;
    L_0x0022:
        r0 = r2.b044Eюю044E044E044E;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b043904390439йй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):com.immersion.hapticmediasdk.utils.FileManager");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b04390439й04390439й(int r9, long r10) {
        /*
        r8 = this;
        r7 = 1;
        r6 = 0;
        r0 = r8.bю044Eюю044E044E;
        if (r0 != 0) goto L_0x0065;
    L_0x0006:
        r0 = r8.b044E044Eююю044E;	 Catch:{ Error -> 0x0026 }
        if (r0 != 0) goto L_0x003c;
    L_0x000a:
        return;
    L_0x000b:
        r1 = new com.immersion.content.EndpointWarp;	 Catch:{ Error -> 0x0026 }
        r2 = r8.b044E044Eюю044E044E;	 Catch:{ Error -> 0x0026 }
        r3 = r8.bю044Eю044E044E044E;	 Catch:{ Error -> 0x0026 }
        r4 = r3.getNativeInstance();	 Catch:{ Error -> 0x0026 }
        r1.<init>(r2, r4);	 Catch:{ Error -> 0x0026 }
        r8.bюю044Eюю044E = r1;	 Catch:{ Error -> 0x0026 }
        r1 = r8.bюю044Eюю044E;	 Catch:{ Error -> 0x0026 }
        if (r1 != 0) goto L_0x005a;
    L_0x001e:
        r0 = "HapticPlaybackThread";
        r1 = "Error creating endpointwarp";
        com.immersion.Log.d(r0, r1);	 Catch:{ Error -> 0x0026 }
        goto L_0x000a;
    L_0x0026:
        r0 = move-exception;
        r1 = r0.getMessage();
        if (r1 != 0) goto L_0x0089;
    L_0x002d:
        switch(r7) {
            case 0: goto L_0x002d;
            case 1: goto L_0x0034;
            default: goto L_0x0030;
        };
    L_0x0030:
        switch(r6) {
            case 0: goto L_0x0034;
            case 1: goto L_0x002d;
            default: goto L_0x0033;
        };
    L_0x0033:
        goto L_0x0030;
    L_0x0034:
        r0 = "Null Message";
    L_0x0036:
        r1 = "HapticPlaybackThread";
        com.immersion.Log.e(r1, r0);
        goto L_0x000a;
    L_0x003c:
        r0 = r8.b044E044Eююю044E;	 Catch:{ Error -> 0x0026 }
        r0 = r0.getEncryptedHapticHeader();	 Catch:{ Error -> 0x0026 }
        r1 = r8.bюю044Eюю044E;	 Catch:{ Error -> 0x0026 }
        if (r1 != 0) goto L_0x0050;
    L_0x0046:
        if (r0 != 0) goto L_0x000b;
    L_0x0048:
        r0 = "HapticPlaybackThread";
        r1 = "corrupted hapt file or unsupported format";
        com.immersion.Log.e(r0, r1);	 Catch:{ Error -> 0x0026 }
        goto L_0x000a;
    L_0x0050:
        if (r0 != 0) goto L_0x005a;
    L_0x0052:
        r0 = "HapticPlaybackThread";
        r1 = "corrupted hapt file or unsupported format";
        com.immersion.Log.e(r0, r1);	 Catch:{ Error -> 0x0026 }
        goto L_0x000a;
    L_0x005a:
        r1 = r8.bюю044Eюю044E;	 Catch:{ Error -> 0x0026 }
        r2 = r0.length;	 Catch:{ Error -> 0x0026 }
        r1.setMetaData(r0, r2);	 Catch:{ Error -> 0x0026 }
        r0 = r8.bюю044Eюю044E;	 Catch:{ Error -> 0x0026 }
        r0.start();	 Catch:{ Error -> 0x0026 }
    L_0x0065:
        r8.b044Eю044Eю044E044E = r6;
        r8.bю044Eюю044E044E = r7;
        r8.bю044E044E044Eю044E = r6;
        r1 = r8.bю044E044Eюю044E;
        monitor-enter(r1);
        r8.b044Eю044E044Eю044E = r9;	 Catch:{ all -> 0x008e }
        r0 = r8.b044Eю044E044Eю044E;	 Catch:{ all -> 0x008e }
        r8.bюю044E044Eю044E = r0;	 Catch:{ all -> 0x008e }
        r2 = r8.b044E044E044E044Eю044E;	 Catch:{ all -> 0x008e }
        r4 = 0;
        r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x0082;
    L_0x007c:
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x008e }
        r8.b044E044E044E044Eю044E = r2;	 Catch:{ all -> 0x008e }
    L_0x0082:
        monitor-exit(r1);	 Catch:{ all -> 0x008e }
        r8.b044E044Eю044Eю044E = r10;
        r8.bйй043904390439й();
        goto L_0x000a;
    L_0x0089:
        r0 = r0.getMessage();
        goto L_0x0036;
    L_0x008e:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x008e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04390439й04390439й(int, long):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void b04390439й0439й0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
    L_0x0000:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0000;
            case 1: goto L_0x0009;
            default: goto L_0x0004;
        };
    L_0x0004:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0000;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0004;
    L_0x0009:
        r2.b0439йй04390439й();
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩЩ04290429Щ0429();
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x0026;
    L_0x001d:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = 2;
        bЩЩЩЩЩ0429 = r0;
    L_0x0026:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b04390439й0439й0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):void");
    }

    public static /* synthetic */ RuntimeInfo b04390439йй04390439(HapticPlaybackThread hapticPlaybackThread) {
        if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = 83;
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
        try {
            return hapticPlaybackThread.b044E044E044Eю044E044E;
        } catch (Exception e) {
            throw e;
        }
    }

    private void b04390439йй0439й() {
        String str = null;
        int i = 2;
        while (this.b044Eюююю044E.isAlive()) {
            while (true) {
                try {
                    i /= 0;
                } catch (Exception e) {
                    b04290429042904290429Щ = b04290429ЩЩЩ0429();
                    while (true) {
                        try {
                            str.length();
                        } catch (Exception e2) {
                            b04290429042904290429Щ = b04290429ЩЩЩ0429();
                            while (true) {
                                try {
                                    str.length();
                                } catch (Exception e3) {
                                    b04290429042904290429Щ = b04290429ЩЩЩ0429();
                                    this.b044Eюююю044E.terminate();
                                    try {
                                        this.b044Eюююю044E.interrupt();
                                        Thread.currentThread();
                                        try {
                                            Thread.yield();
                                        } catch (Exception e4) {
                                            throw e4;
                                        }
                                    } catch (Exception e42) {
                                        throw e42;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static /* synthetic */ void b04390439ййй0439(HapticPlaybackThread hapticPlaybackThread, int i, long j) {
        try {
            hapticPlaybackThread.b04390439й04390439й(i, j);
            int b04290429ЩЩЩ0429 = b04290429ЩЩЩ0429();
            switch ((b04290429ЩЩЩ0429 * (b0429ЩЩЩЩ0429 + b04290429ЩЩЩ0429)) % bЩ0429ЩЩЩ0429) {
                case 0:
                    return;
                default:
                    b04290429042904290429Щ = 23;
                    bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                    return;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.lang.Runnable b0439й043904390439й(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3) {
        /*
        r0 = 0;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = r3.b044Eю044E044E044E044E;
        r1 = b04290429042904290429Щ;
        r2 = b0429ЩЩЩЩ0429;
        r1 = r1 + r2;
        r2 = b04290429042904290429Щ;
        r1 = r1 * r2;
        r2 = bЩ0429ЩЩЩ0429;
        r1 = r1 % r2;
        r2 = bЩ042904290429Щ0429();
        if (r1 == r2) goto L_0x0025;
    L_0x001b:
        r1 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r1;
        r1 = 19;
        bЩЩЩЩЩ0429 = r1;
    L_0x0025:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439й043904390439й(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):java.lang.Runnable");
    }

    public static /* synthetic */ void b0439й04390439й0439(HapticPlaybackThread hapticPlaybackThread, Message message) {
        hapticPlaybackThread.bй04390439й0439й(message);
        if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩ042904290429Щ0429()) {
            b04290429042904290429Щ = b04290429ЩЩЩ0429();
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.lang.Object b0439й0439й04390439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0016;
            default: goto L_0x000c;
        };
    L_0x000c:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = 20;
        bЩЩЩЩЩ0429 = r0;
    L_0x0016:
        r0 = r2.bю044E044Eюю044E;
    L_0x0018:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0018;
            case 1: goto L_0x0021;
            default: goto L_0x001c;
        };
    L_0x001c:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0021;
            case 1: goto L_0x0018;
            default: goto L_0x0020;
        };
    L_0x0020:
        goto L_0x001c;
    L_0x0021:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439й0439й04390439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):java.lang.Object");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b0439й0439й0439й() {
        /*
        r2 = this;
        r1 = 0;
        monitor-enter(r2);
        r2.notifyAll();	 Catch:{ all -> 0x0007 }
        monitor-exit(r2);	 Catch:{ all -> 0x0007 }
        return;
    L_0x0007:
        r0 = move-exception;
    L_0x0008:
        switch(r1) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0008;
            default: goto L_0x000b;
        };	 Catch:{ all -> 0x0007 }
    L_0x000b:
        switch(r1) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0008;
            default: goto L_0x000e;
        };	 Catch:{ all -> 0x0007 }
    L_0x000e:
        goto L_0x000b;
    L_0x000f:
        monitor-exit(r2);	 Catch:{ all -> 0x0007 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439й0439й0439й():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.hapticmediasdk.controllers.IHapticFileReader b0439й0439йй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r4, com.immersion.hapticmediasdk.controllers.IHapticFileReader r5) {
        /*
        r3 = 0;
        r1 = 0;
        r0 = 5;
        r2 = 0;
    L_0x0004:
        r0 = r0 / r2;
        goto L_0x0004;
    L_0x0006:
        r0 = move-exception;
        r0 = 22;
        b04290429042904290429Щ = r0;
    L_0x000b:
        switch(r3) {
            case 0: goto L_0x0013;
            case 1: goto L_0x000b;
            default: goto L_0x000e;
        };
    L_0x000e:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0013;
            default: goto L_0x0012;
        };
    L_0x0012:
        goto L_0x000e;
    L_0x0013:
        r4.b044E044Eююю044E = r5;
        return r5;
    L_0x0016:
        r0 = move-exception;
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
    L_0x001d:
        r1.length();	 Catch:{ Exception -> 0x0006 }
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439й0439йй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, com.immersion.hapticmediasdk.controllers.IHapticFileReader):com.immersion.hapticmediasdk.controllers.IHapticFileReader");
    }

    public static /* synthetic */ long b0439йй043904390439(HapticPlaybackThread hapticPlaybackThread, long j) {
        try {
            hapticPlaybackThread.b044E044E044E044Eю044E = j;
            if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
            }
            return j;
        } catch (Exception e) {
            throw e;
        }
    }

    private void b0439йй04390439й() {
        this.bю044Eюю044E044E = false;
        if (this.bюю044Eюю044E != null) {
            this.bюю044Eюю044E.stop();
        }
        if (this.b044E044Eююю044E != null) {
            for (AnalyticsDataCollector addAnalyticsDataCollector : this.b044E044Eююю044E.getCollectors()) {
                this.bюююю044E044E.addAnalyticsDataCollector(addAnalyticsDataCollector);
            }
        } else {
            this.bюююю044E044E.addAnalyticsDataCollector(new rrcrrr(this));
        }
        this.b044E044E044E044E044Eю.removeCallbacks(this.b044Eю044E044E044E044E);
        removePlaybackCallbacks();
        synchronized (this.bю044E044Eюю044E) {
            this.b044Eю044E044Eю044E = 0;
            this.bюю044E044Eю044E = 0;
            this.b044E044E044E044Eю044E = 0;
        }
        this.bю044E044E044Eю044E = 0;
        this.b044E044Eю044Eю044E = 0;
        this.b044Eю044Eю044E044E = true;
    }

    public static /* synthetic */ int b0439йй0439й0439(HapticPlaybackThread hapticPlaybackThread) {
        int i = b04290429042904290429Щ;
        switch ((i * (b0429ЩЩЩЩ0429 + i)) % bЩ0429ЩЩЩ0429) {
            case 0:
                break;
            default:
                b04290429042904290429Щ = 89;
                bЩЩЩЩЩ0429 = 83;
                break;
        }
        try {
            return hapticPlaybackThread.b044Eю044E044E044Eю;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ Object b0439ййй04390439(HapticPlaybackThread hapticPlaybackThread) {
        int i = b04290429042904290429Щ;
        switch ((i * (b0429ЩЩЩЩ0429 + i)) % bЩ0429ЩЩЩ0429) {
            case 0:
                break;
            default:
                b04290429042904290429Щ = 79;
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                break;
        }
        try {
            return hapticPlaybackThread.b044E044E044Eюю044E;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ int b0439йййй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3, int r4) {
        /*
        r2 = 1;
        r0 = b04290429042904290429Щ;
        r1 = b0429Щ04290429Щ0429();
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x001e;
    L_0x0012:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r0;
    L_0x001e:
        r3.bю044Eю044Eю044E = r4;
    L_0x0020:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0027;
            default: goto L_0x0023;
        };
    L_0x0023:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0027;
            default: goto L_0x0026;
        };
    L_0x0026:
        goto L_0x0023;
    L_0x0027:
        return r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.b0439йййй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, int):int");
    }

    public static int bЩ042904290429Щ0429() {
        return 0;
    }

    public static int bЩЩ04290429Щ0429() {
        return 2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ android.os.Handler bй0439043904390439й(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3) {
        /*
        r0 = r3.b044E044E044E044E044Eю;
    L_0x0002:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0002;
            default: goto L_0x0006;
        };
    L_0x0006:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0002;
            case 1: goto L_0x000b;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0006;
    L_0x000b:
        r1 = b04290429042904290429Щ;
        r2 = b0429Щ04290429Щ0429();
        r1 = r1 + r2;
        r2 = b04290429042904290429Щ;
        r1 = r1 * r2;
        r2 = bЩЩ04290429Щ0429();
        r1 = r1 % r2;
        r2 = bЩ042904290429Щ0429();
        if (r1 == r2) goto L_0x0028;
    L_0x0020:
        r1 = 35;
        b04290429042904290429Щ = r1;
        r1 = 17;
        bЩЩЩЩЩ0429 = r1;
    L_0x0028:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439043904390439й(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):android.os.Handler");
    }

    public static /* synthetic */ void bй043904390439й0439(HapticPlaybackThread hapticPlaybackThread) {
        if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = 58;
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
        try {
            hapticPlaybackThread.bййй04390439й();
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ int bй04390439й04390439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3, int r4) {
        /*
        r0 = r3.b044Eю044E044Eю044E;
    L_0x0002:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0002;
            case 1: goto L_0x000b;
            default: goto L_0x0006;
        };
    L_0x0006:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0002;
            default: goto L_0x000a;
        };
    L_0x000a:
        goto L_0x0006;
    L_0x000b:
        r1 = b04290429042904290429Щ;
        r2 = b0429ЩЩЩЩ0429;
        r1 = r1 + r2;
        r2 = b04290429042904290429Щ;
        r1 = r1 * r2;
        r2 = bЩ0429ЩЩЩ0429;
        r1 = r1 % r2;
        r2 = bЩЩЩЩЩ0429;
        if (r1 == r2) goto L_0x0024;
    L_0x001a:
        r1 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r1;
        r1 = 22;
        bЩЩЩЩЩ0429 = r1;
    L_0x0024:
        r0 = r0 + r4;
        r3.b044Eю044E044Eю044E = r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй04390439й04390439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, int):int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void bй04390439й0439й(android.os.Message r5) {
        /*
        r4 = this;
        r3 = 1;
        r4.b044Eююю044E044E = r3;
        r0 = r4.bююююю044E;
        r1 = b04290429042904290429Щ;
        r2 = b0429ЩЩЩЩ0429;
        r1 = r1 + r2;
        r2 = b04290429042904290429Щ;
        r1 = r1 * r2;
        r2 = bЩ0429ЩЩЩ0429;
        r1 = r1 % r2;
        r2 = bЩЩЩЩЩ0429;
        if (r1 == r2) goto L_0x001c;
    L_0x0014:
        r1 = 37;
        b04290429042904290429Щ = r1;
        r1 = 60;
        bЩЩЩЩЩ0429 = r1;
    L_0x001c:
        r1 = 8;
        r0 = r0.obtainMessage(r1);
        r1 = r5.getData();
        r0.setData(r1);
        r1 = r4.bююююю044E;
    L_0x002b:
        switch(r3) {
            case 0: goto L_0x002b;
            case 1: goto L_0x0032;
            default: goto L_0x002e;
        };
    L_0x002e:
        switch(r3) {
            case 0: goto L_0x002b;
            case 1: goto L_0x0032;
            default: goto L_0x0031;
        };
    L_0x0031:
        goto L_0x002e;
    L_0x0032:
        r1.sendMessage(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй04390439й0439й(android.os.Message):void");
    }

    public static /* synthetic */ String bй04390439йй0439(HapticPlaybackThread hapticPlaybackThread) {
        try {
            String str = hapticPlaybackThread.bю044E044E044E044Eю;
            int i = b04290429042904290429Щ;
            switch ((i * (b0429ЩЩЩЩ0429 + i)) % bЩ0429ЩЩЩ0429) {
                case 0:
                    break;
                default:
                    b04290429042904290429Щ = 64;
                    bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                    break;
            }
            return str;
        } catch (Exception e) {
            throw e;
        }
    }

    public static /* synthetic */ Runnable bй0439й043904390439(HapticPlaybackThread hapticPlaybackThread) {
        Runnable runnable = hapticPlaybackThread.bю044E044E044E044E044E;
        if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = b04290429ЩЩЩ0429();
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
        return runnable;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void bй0439й04390439й() {
        /*
        r3 = this;
        r2 = 0;
    L_0x0001:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0009;
            default: goto L_0x0005;
        };
    L_0x0005:
        switch(r2) {
            case 0: goto L_0x0009;
            case 1: goto L_0x0001;
            default: goto L_0x0008;
        };
    L_0x0008:
        goto L_0x0005;
    L_0x0009:
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x0022;
    L_0x0018:
        r0 = 55;
        b04290429042904290429Щ = r0;
        r0 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r0;
    L_0x0022:
        r3.bю044Eюю044E044E = r2;
        r3.removePlaybackCallbacks();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439й04390439й():void");
    }

    public static /* synthetic */ int bй0439й0439й0439(HapticPlaybackThread hapticPlaybackThread, int i) {
        try {
            hapticPlaybackThread.b044Eю044E044E044Eю = i;
            if (((b04290429042904290429Щ + b0429Щ04290429Щ0429()) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = 95;
            }
            return i;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.util.ArrayList bй0439йй04390439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r3) {
        /*
        r2 = 0;
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x001a;
    L_0x0010:
        r0 = 90;
        b04290429042904290429Щ = r0;
        r0 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r0;
    L_0x001a:
        switch(r2) {
            case 0: goto L_0x0021;
            case 1: goto L_0x001a;
            default: goto L_0x001d;
        };
    L_0x001d:
        switch(r2) {
            case 0: goto L_0x0021;
            case 1: goto L_0x001a;
            default: goto L_0x0020;
        };
    L_0x0020:
        goto L_0x001d;
    L_0x0021:
        r0 = r3.bю044E044Eю044E044E;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439йй04390439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):java.util.ArrayList");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ void bй0439ййй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x001b;
    L_0x000f:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r0;
    L_0x001b:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x001b;
            case 1: goto L_0x0024;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0024;
            case 1: goto L_0x001b;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x001f;
    L_0x0024:
        r2.b043904390439й0439й();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bй0439ййй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void bйй043904390439й() {
        /*
        r12 = this;
        r5 = 0;
        r0 = r12.bю044Eюю044E044E;
        if (r0 == 0) goto L_0x004f;
    L_0x0005:
        r1 = r12.bю044E044Eюю044E;
        monitor-enter(r1);
        r2 = r12.b044Eю044E044Eю044E;	 Catch:{ all -> 0x0063 }
        r4 = r12.bюю044E044Eю044E;	 Catch:{ all -> 0x0063 }
        monitor-exit(r1);	 Catch:{ all -> 0x0063 }
        r0 = r12.b044E044Eююю044E;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r6 = r0.getBufferForPlaybackPosition(r2);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r0 = r12.b044E044Eююю044E;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r8 = (long) r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r7 = r0.getHapticBlockIndex(r8);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r0 = r12.b044E044Eююю044E;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r8 = (long) r2;	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        r8 = r0.getBlockOffset(r8);	 Catch:{ NotEnoughHapticBytesAvailableException -> 0x0050 }
        if (r6 == 0) goto L_0x006d;
    L_0x0023:
        r0 = r12.b044E044Eю044Eю044E;
        r3 = r12.bю044E044E044Eю044E;
        r10 = (long) r3;
        r10 = r10 + r0;
        r0 = new rrrrrr.rcrrcr;
        r2 = (long) r2;
        r4 = (long) r4;
        r1 = r12;
        r0.<init>(r1, r2, r4, r6, r7, r8);
        r1 = r12.b044E044E044Eюю044E;
        monitor-enter(r1);
        r2 = r12.bю044E044Eю044E044E;	 Catch:{ all -> 0x0060 }
        r2.add(r0);	 Catch:{ all -> 0x0060 }
        monitor-exit(r1);	 Catch:{ all -> 0x0060 }
        r1 = r12.b044E044E044E044E044Eю;
        r2 = r12.b044Eю044E044E044Eю;
        r2 = (long) r2;
        r2 = r2 + r10;
        r1.postAtTime(r0, r2);
        r0 = r12.bю044E044E044Eю044E;
        r1 = r12.b044Eю044E044E044Eю;
        r0 = r0 + r1;
        r12.bю044E044E044Eю044E = r0;
        r0 = r12.b044Eю044Eюю044E;
        r0.startTimingII();
    L_0x004f:
        return;
    L_0x0050:
        r0 = move-exception;
        r12.bю044Eюю044E044E = r5;
        r0 = r12.bююююю044E;
        r1 = r12.bююююю044E;
        r3 = 7;
        r1 = r1.obtainMessage(r3, r2, r5);
        r0.sendMessage(r1);
        goto L_0x004f;
    L_0x0060:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0060 }
        throw r0;
    L_0x0063:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0063 }
    L_0x0065:
        switch(r5) {
            case 0: goto L_0x006c;
            case 1: goto L_0x0065;
            default: goto L_0x0068;
        };
    L_0x0068:
        switch(r5) {
            case 0: goto L_0x006c;
            case 1: goto L_0x0065;
            default: goto L_0x006b;
        };
    L_0x006b:
        goto L_0x0068;
    L_0x006c:
        throw r0;
    L_0x006d:
        r1 = r12.bю044E044Eюю044E;
        monitor-enter(r1);
        r0 = 0;
        r12.b044Eю044E044Eю044E = r0;	 Catch:{ all -> 0x0080 }
        r0 = 0;
        r12.bюю044E044Eю044E = r0;	 Catch:{ all -> 0x0080 }
        monitor-exit(r1);	 Catch:{ all -> 0x0080 }
        r12.bю044E044E044Eю044E = r5;
        r0 = 0;
        r12.b044E044Eю044Eю044E = r0;
        r12.bю044Eюю044E044E = r5;
        goto L_0x004f;
    L_0x0080:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0080 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bйй043904390439й():void");
    }

    public static /* synthetic */ void bйй04390439й0439(HapticPlaybackThread hapticPlaybackThread) {
        if (((b04290429042904290429Щ + b0429Щ04290429Щ0429()) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = b04290429ЩЩЩ0429();
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
        hapticPlaybackThread.bй0439й04390439й();
    }

    public static /* synthetic */ EndpointWarp bйй0439й04390439(HapticPlaybackThread hapticPlaybackThread) {
        int i = b04290429042904290429Щ;
        switch ((i * (b0429Щ04290429Щ0429() + i)) % bЩ0429ЩЩЩ0429) {
            case 0:
                break;
            default:
                b04290429042904290429Щ = 20;
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                break;
        }
        return hapticPlaybackThread.bюю044Eюю044E;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void bйй0439й0439й() {
        /*
        r4 = this;
        r2 = 0;
        r3 = 0;
        r0 = r4.b044Eюююю044E;
        if (r0 == 0) goto L_0x000b;
    L_0x0006:
        r4.b04390439йй0439й();
        r4.b044Eюююю044E = r3;
    L_0x000b:
        r0 = r4.b044E044Eююю044E;
        if (r0 == 0) goto L_0x0016;
    L_0x000f:
        r0 = r4.b044E044Eююю044E;
        r0.close();
        r4.b044E044Eююю044E = r3;
    L_0x0016:
        r1 = r4.b044E044E044Eюю044E;
        monitor-enter(r1);
    L_0x0019:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0019;
            default: goto L_0x001c;
        };
    L_0x001c:
        switch(r2) {
            case 0: goto L_0x0020;
            case 1: goto L_0x0019;
            default: goto L_0x001f;
        };
    L_0x001f:
        goto L_0x001c;
    L_0x0020:
        r0 = r4.b044E044E044E044E044Eю;	 Catch:{ all -> 0x0048 }
        r2 = 0;
        r0.removeCallbacksAndMessages(r2);	 Catch:{ all -> 0x0048 }
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        r0 = r4.bю044Eююю044E;
        if (r0 == 0) goto L_0x0032;
    L_0x002b:
        r0 = r4.bю044Eююю044E;
        r0.quit();
        r4.bю044Eююю044E = r3;
    L_0x0032:
        r0 = r4.bюю044Eюю044E;
        if (r0 == 0) goto L_0x0042;
    L_0x0036:
        r0 = r4.bюю044Eюю044E;
        r0.stop();
        r0 = r4.bюю044Eюю044E;
        r0.dispose();
        r4.bюю044Eюю044E = r3;
    L_0x0042:
        r0 = r4.b044Eюю044E044E044E;
        r0.deleteHapticStorage();
        return;
    L_0x0048:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0048 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bйй0439й0439й():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ com.immersion.hapticmediasdk.controllers.IHapticFileReader bйй0439йй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2) {
        /*
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩ042904290429Щ0429();
        if (r0 == r1) goto L_0x0025;
    L_0x0019:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r0;
    L_0x0025:
        r0 = r2.b044E044Eююю044E;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bйй0439йй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread):com.immersion.hapticmediasdk.controllers.IHapticFileReader");
    }

    private void bййй04390439й() {
        boolean z = ((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429;
        if (z != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = b04290429ЩЩЩ0429();
            z = true;
            bЩЩЩЩЩ0429 = 74;
        }
        try {
            bйй0439й0439й();
            try {
                this.bюю044Eю044E044E = z;
                b0439й0439й0439й();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            String message = e2.getMessage() == null ? "Null message" : e2.getMessage();
            String str = b044Eюю044E044Eю;
            z = "quit() : " + message;
            Log.e(str, z);
            this.bюю044Eю044E044E = z;
            b0439й0439й0439й();
        } finally {
            this.bюю044Eю044E044E = false;
            b0439й0439й0439й();
        }
    }

    public static /* synthetic */ boolean bйййй04390439(HapticPlaybackThread hapticPlaybackThread) {
        try {
            boolean z = hapticPlaybackThread.bю044Eюю044E044E;
            if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
            }
            return z;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ int bййййй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread r2, int r3) {
        /*
        r0 = 0;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0008;
            case 1: goto L_0x0001;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩЩЩЩЩ0429;
        if (r0 == r1) goto L_0x0021;
    L_0x0017:
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
        r0 = 71;
        bЩЩЩЩЩ0429 = r0;
    L_0x0021:
        r2.b044Eюю044Eю044E = r3;
        return r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.bййййй0439(com.immersion.hapticmediasdk.controllers.HapticPlaybackThread, int):int");
    }

    public Handler getHandler() {
        if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = 51;
            bЩЩЩЩЩ0429 = 38;
        }
        try {
            return this.b044E044E044E044E044Eю;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isStarted() {
        try {
            boolean z = this.bюю044Eю044E044E;
            if (((b04290429042904290429Щ + b0429Щ04290429Щ0429()) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
                b04290429042904290429Щ = 95;
                bЩЩЩЩЩ0429 = 50;
            }
            return z;
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean isStopped() {
        int i = b04290429042904290429Щ;
        switch ((i * (b0429Щ04290429Щ0429() + i)) % bЩЩ04290429Щ0429()) {
            case 0:
                break;
            default:
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                break;
        }
        return this.b044Eю044Eю044E044E;
    }

    public void pauseHapticPlayback() {
        if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩЩ04290429Щ0429() != bЩЩЩЩЩ0429) {
            b04290429042904290429Щ = b04290429ЩЩЩ0429();
            bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
        }
        try {
            this.b044E044E044E044E044Eю.sendEmptyMessage(5);
        } catch (Exception e) {
            throw e;
        }
    }

    public void playHapticForPlaybackPosition(int i, long j) {
        try {
            removePlaybackCallbacks();
            this.b044E044E044E044E044Eю.removeMessages(2);
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(b044E044Eю044E044Eю, i);
                bundle.putLong(bюю044E044E044Eю, j);
                if (((b04290429042904290429Щ + b0429ЩЩЩЩ0429) * b04290429042904290429Щ) % bЩ0429ЩЩЩ0429 != bЩЩЩЩЩ0429) {
                    b04290429042904290429Щ = b04290429ЩЩЩ0429();
                    bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                }
                Message obtainMessage = this.b044E044E044E044E044Eю.obtainMessage(2);
                obtainMessage.setData(bundle);
                this.b044E044E044E044E044Eю.sendMessage(obtainMessage);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void prepareHapticPlayback(int i, int i2) {
        this.b044E044E044E044E044Eю.removeMessages(1);
        Handler handler = this.b044E044E044E044E044Eю;
        Handler handler2 = this.b044E044E044E044E044Eю;
        int i3 = b04290429042904290429Щ;
        switch ((i3 * (b0429ЩЩЩЩ0429 + i3)) % bЩЩ04290429Щ0429()) {
            case 0:
                break;
            default:
                b04290429042904290429Щ = b04290429ЩЩЩ0429();
                bЩЩЩЩЩ0429 = b04290429ЩЩЩ0429();
                break;
        }
        handler.sendMessage(handler2.obtainMessage(1, i, i2));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void quitHapticPlayback() {
        /*
        r4 = this;
        r3 = 0;
        r0 = -1;
    L_0x0002:
        switch(r3) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0002;
            default: goto L_0x0005;
        };
    L_0x0005:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x0002;
            case 1: goto L_0x000a;
            default: goto L_0x0009;
        };
    L_0x0009:
        goto L_0x0005;
    L_0x000a:
        r1 = r4.b044E044E044E044E044Eю;
        r2 = 9;
        r1 = r1.sendEmptyMessage(r2);
        if (r1 != 0) goto L_0x0023;
    L_0x0014:
        r4.bюю044Eю044E044E = r3;
        r4.b0439й0439й0439й();
    L_0x0019:
        r1 = new int[r0];	 Catch:{ Exception -> 0x001c }
        goto L_0x0019;
    L_0x001c:
        r0 = move-exception;
        r0 = b04290429ЩЩЩ0429();
        b04290429042904290429Щ = r0;
    L_0x0023:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.quitHapticPlayback():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removePlaybackCallbacks() {
        /*
        r4 = this;
        r0 = 1;
    L_0x0001:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0004;
        };
    L_0x0004:
        switch(r0) {
            case 0: goto L_0x0001;
            case 1: goto L_0x0008;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0004;
    L_0x0008:
        r1 = r4.b044E044E044Eюю044E;
        monitor-enter(r1);
        r0 = r4.bю044E044Eю044E044E;	 Catch:{ all -> 0x0023 }
        r2 = r0.iterator();	 Catch:{ all -> 0x0023 }
    L_0x0011:
        r0 = r2.hasNext();	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x0026;
    L_0x0017:
        r0 = r2.next();	 Catch:{ all -> 0x0023 }
        r0 = (rrrrrr.rcrrcr) r0;	 Catch:{ all -> 0x0023 }
        r3 = r4.b044E044E044E044E044Eю;	 Catch:{ all -> 0x0023 }
        r3.removeCallbacks(r0);	 Catch:{ all -> 0x0023 }
        goto L_0x0011;
    L_0x0023:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0023 }
        throw r0;
    L_0x0026:
        r0 = r4.bю044E044Eю044E044E;	 Catch:{ all -> 0x0023 }
        r0.clear();	 Catch:{ all -> 0x0023 }
        monitor-exit(r1);	 Catch:{ all -> 0x0023 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.removePlaybackCallbacks():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r9 = this;
        r8 = 1;
        r1 = -1;
        r0 = 4;
        r2 = 0;
        r3 = -19;
        android.os.Process.setThreadPriority(r3);
        android.os.Looper.prepare();
        r3 = android.os.Looper.myLooper();
        r9.bю044Eююю044E = r3;
        r3 = new rrrrrr.crcrrr;
        r4 = 0;
        r3.<init>(r9, r4);
        r9.b044E044E044E044E044Eю = r3;
        r3 = new com.immersion.hapticmediasdk.controllers.HapticDownloadThread;
        r4 = r9.bю044E044E044E044Eю;
        r5 = r9.b044E044E044E044E044Eю;
        r6 = r9.bююю044E044E044E;
        r7 = r9.b044Eюю044E044E044E;
        r3.<init>(r4, r5, r6, r7);
        r9.b044Eюююю044E = r3;
        r3 = r9.b044Eюююю044E;
        r3.start();
        r9.bюю044Eю044E044E = r8;
        r9.b0439й0439й0439й();
    L_0x0033:
        switch(r8) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003a;
            default: goto L_0x0036;
        };
    L_0x0036:
        switch(r8) {
            case 0: goto L_0x0033;
            case 1: goto L_0x003a;
            default: goto L_0x0039;
        };
    L_0x0039:
        goto L_0x0036;
    L_0x003a:
        android.os.Looper.loop();
    L_0x003d:
        r0 = r0 / r2;
        goto L_0x003d;
    L_0x003f:
        r0 = move-exception;
        r0 = 62;
        b04290429042904290429Щ = r0;
    L_0x0044:
        r0 = new int[r1];	 Catch:{ Exception -> 0x0047 }
        goto L_0x0044;
    L_0x0047:
        r0 = move-exception;
        r0 = 83;
        b04290429042904290429Щ = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.run():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void stopHapticPlayback() {
        /*
        r2 = this;
        r0 = b04290429042904290429Щ;
        r1 = b0429ЩЩЩЩ0429;
        r0 = r0 + r1;
        r1 = b04290429042904290429Щ;
        r0 = r0 * r1;
        r1 = bЩ0429ЩЩЩ0429;
        r0 = r0 % r1;
        r1 = bЩ042904290429Щ0429();
        if (r0 == r1) goto L_0x001b;
    L_0x0011:
        r0 = 66;
        b04290429042904290429Щ = r0;
        r0 = b04290429ЩЩЩ0429();
        bЩЩЩЩЩ0429 = r0;
    L_0x001b:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x001b;
            case 1: goto L_0x0024;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = 0;
        switch(r0) {
            case 0: goto L_0x0024;
            case 1: goto L_0x001b;
            default: goto L_0x0023;
        };
    L_0x0023:
        goto L_0x001f;
    L_0x0024:
        r0 = r2.b044E044E044E044E044Eю;
        r1 = 4;
        r0.sendEmptyMessage(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.stopHapticPlayback():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void syncUpdate(int r11, long r12) {
        /*
        r10 = this;
        r8 = 1;
        r1 = r10.bю044E044Eюю044E;
        monitor-enter(r1);
        r2 = android.os.SystemClock.uptimeMillis();	 Catch:{ all -> 0x003f }
        r4 = (long) r11;	 Catch:{ all -> 0x003f }
        r6 = r2 - r12;
        r4 = r4 + r6;
        r0 = (int) r4;	 Catch:{ all -> 0x003f }
        r4 = r10.b044Eю044E044Eю044E;	 Catch:{ all -> 0x003f }
    L_0x000f:
        r5 = 0;
        switch(r5) {
            case 0: goto L_0x0017;
            case 1: goto L_0x000f;
            default: goto L_0x0013;
        };	 Catch:{ all -> 0x003f }
    L_0x0013:
        switch(r8) {
            case 0: goto L_0x000f;
            case 1: goto L_0x0017;
            default: goto L_0x0016;
        };	 Catch:{ all -> 0x003f }
    L_0x0016:
        goto L_0x0013;
    L_0x0017:
        r6 = r10.b044E044E044E044Eю044E;	 Catch:{ all -> 0x003f }
        r2 = r2 - r6;
        r2 = (int) r2;	 Catch:{ all -> 0x003f }
        r2 = r2 + r4;
        r2 = r0 - r2;
        r3 = 50;
        r4 = java.lang.Math.abs(r2);	 Catch:{ all -> 0x003f }
        if (r3 >= r4) goto L_0x003d;
    L_0x0026:
        r3 = r10.b044Eю044E044Eю044E;	 Catch:{ all -> 0x003f }
        r2 = r2 + r3;
        r10.b044Eю044E044Eю044E = r2;	 Catch:{ all -> 0x003f }
        r2 = r10.b044Eю044E044Eю044E;	 Catch:{ all -> 0x003f }
        r10.bюю044E044Eю044E = r2;	 Catch:{ all -> 0x003f }
        r2 = r10.b044E044E044E044E044Eю;	 Catch:{ all -> 0x003f }
        r3 = r10.b044E044E044E044E044Eю;	 Catch:{ all -> 0x003f }
        r4 = 1;
        r5 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r3.obtainMessage(r4, r0, r5);	 Catch:{ all -> 0x003f }
        r2.sendMessage(r0);	 Catch:{ all -> 0x003f }
    L_0x003d:
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        return;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x003f }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.HapticPlaybackThread.syncUpdate(int, long):void");
    }
}
