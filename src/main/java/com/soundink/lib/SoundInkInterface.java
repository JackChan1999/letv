package com.soundink.lib;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.soundink.lib.a.a;
import com.soundink.lib.c.b;

public final class SoundInkInterface {
    public static final String ACTION_SEND_SOUNDINK_INVALID_SIGNAL = ("android.intent.action.soundink.invalid" + SoundInkInterface.class.getName());
    public static final String ACTION_SEND_SOUNDINK_LOG_SIGNAL = ("android.intent.action.soundink.log" + SoundInkInterface.class.getName());
    public static final String ACTION_SEND_SOUNDINK_SIGNAL = ("android.intent.action.soundink." + SoundInkInterface.class.getName());
    public static final String SOUNDINK_AGENT = "sounkink_agent";
    public static final String SOUNDINK_LOG_AGENT = "sounkink_log_agent";
    private static String a = "";
    private static int b = 300000;
    private static boolean c = true;
    private static boolean d = false;
    private static boolean e = false;
    private static boolean f = false;
    private static e g = new e();

    public static void init(String appKey, Boolean debug) {
        a = appKey;
        a.a = debug.booleanValue();
        if (debug.booleanValue()) {
            Log.d("SoundInkInterface", "soundInkSdk version:1.0");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void start(android.content.Context r3) {
        /*
        r1 = g;
        monitor-enter(r1);
        r0 = g;	 Catch:{ all -> 0x0031 }
        r0 = r0.k();	 Catch:{ all -> 0x0031 }
        if (r0 == 0) goto L_0x001f;
    L_0x000b:
        r0 = com.soundink.lib.a.a.a;	 Catch:{ all -> 0x0031 }
        if (r0 == 0) goto L_0x001d;
    L_0x000f:
        r0 = "soundink service is  already started";
        com.soundink.lib.c.b.a(r0);	 Catch:{ all -> 0x0031 }
        r0 = "SoundInkInterface";
        r2 = "soundink service is  already started";
        android.util.Log.d(r0, r2);	 Catch:{ all -> 0x0031 }
    L_0x001d:
        monitor-exit(r1);	 Catch:{ all -> 0x0031 }
    L_0x001e:
        return;
    L_0x001f:
        r0 = new android.content.Intent;	 Catch:{ all -> 0x0031 }
        r2 = com.soundink.lib.SoundInkService.class;
        r0.<init>(r3, r2);	 Catch:{ all -> 0x0031 }
        r3.startService(r0);	 Catch:{ all -> 0x0031 }
        r0 = g;	 Catch:{ all -> 0x0031 }
        r2 = 1;
        r0.a(r2);	 Catch:{ all -> 0x0031 }
        monitor-exit(r1);	 Catch:{ all -> 0x0031 }
        goto L_0x001e;
    L_0x0031:
        r0 = move-exception;
        monitor-exit(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.soundink.lib.SoundInkInterface.start(android.content.Context):void");
    }

    public static void stop(Context mContext) {
        g.a();
        g.b();
        g.a(false);
        mContext.stopService(new Intent(mContext, SoundInkService.class));
    }

    public static synchronized void pause() {
        synchronized (SoundInkInterface.class) {
            synchronized (g) {
                if (g.k()) {
                    SoundInkService.a();
                    g.a(false);
                } else {
                    if (a.a) {
                        b.a("soundink service is already paused");
                        Log.d("SoundInkInterface", "soundink service is already paused");
                    }
                }
            }
        }
    }

    public static void setIsSingleReceiveModel(boolean isSignalReceiveModel) {
        c = isSignalReceiveModel;
    }

    public static void setIsReceiveSignalAtNoNetWork(boolean isReceive) {
        e = isReceive;
    }

    public static void setIsReceiveNoSignal(boolean isReceiveNoSignal) {
        d = isReceiveNoSignal;
    }

    public static void clearSoundInkCache() {
        g.a();
        g.b();
    }

    public static void setReceiveDuplicateSignalInterval(int s) {
        f = true;
        b = s * 1000;
    }

    protected static String getLogTag() {
        return "SoundInkInterface";
    }

    protected static String getAppKey() {
        return a;
    }

    protected static int getSingleReceiveInterval() {
        return b;
    }

    protected static boolean getSingleReceiveModel() {
        return c;
    }

    protected static boolean getReceiveSignalAtNoNetWork() {
        return e;
    }

    protected static boolean getIsReceiveNoSignal() {
        return d;
    }

    protected static boolean getIsSetDuplicateSignalInterval() {
        return f;
    }
}
