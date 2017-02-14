package com.immersion.hapticmediasdk;

import android.content.Context;
import android.webkit.URLUtil;
import com.immersion.aws.analytics.ImmrAnalytics;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.utils.Log;
import java.io.File;
import rrrrrr.cccrrc;

public class MediaPlaybackSDK extends HapticContentSDK {
    public static int b04110411ББ0411Б = 1;
    public static int b0411БББ0411Б = 70;
    private static final String b0414ДДД0414Д = "HapticContentSDK";
    public static int bБ0411ББ0411Б = 0;
    public static int bББ0411Б0411Б = 2;
    private cccrrc b04140414ДД0414Д;
    private int bД0414ДД0414Д = 400;
    private ImmrAnalytics bДД0414Д0414Д;

    public MediaPlaybackSDK(Context context, String str, String str2, String str3) {
        super(0, context, str, str2, str3);
        if (((b0411БББ0411Б + b04110411ББ0411Б) * b0411БББ0411Б) % bББ0411Б0411Б != bБ0411ББ0411Б) {
            b0411БББ0411Б = b0411Б0411Б0411Б();
            bБ0411ББ0411Б = b0411Б0411Б0411Б();
        }
        this.b04140414ДД0414Д = new cccrrc(this);
        this.bДД0414Д0414Д = ImmrAnalytics.getInstance(context);
    }

    public static int b041104110411Б0411Б() {
        return 2;
    }

    public static int b0411Б0411Б0411Б() {
        return 7;
    }

    private int b04390439йййй(String str) {
        if (str == null) {
            Log.e(b0414ДДД0414Д, "invalid local hapt file url: null");
            return -4;
        }
        File file = new File(str);
        if (!file.isFile()) {
            Log.e(b0414ДДД0414Д, "invalid local hapt file url: directory");
            return -4;
        } else if (file.canRead()) {
            MediaTaskManager mediaTaskManager = this.mMediaTaskManager;
            if (((b0411БББ0411Б + b04110411ББ0411Б) * b0411БББ0411Б) % bББ0411Б0411Б != bБ0411ББ0411Б) {
                b0411БББ0411Б = 6;
                bБ0411ББ0411Б = b0411Б0411Б0411Б();
            }
            mediaTaskManager.setHapticsUrl(str, false);
            this.b04140414ДД0414Д.setIsPlayingLocally();
            this.bДД0414Д0414Д.addAnalyticsDataCollector(this.b04140414ДД0414Д);
            return this.mMediaTaskManager.transitToState(SDKStatus.INITIALIZED);
        } else {
            Log.e(b0414ДДД0414Д, "could not access local hapt file: permission denied");
            return -3;
        }
    }

    public static int bБ04110411Б0411Б() {
        return 1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean bй0439йййй(java.lang.String r4) {
        /*
        r3 = this;
        r0 = 0;
        r1 = "https";
        r2 = "http";
        r1 = r4.replaceFirst(r1, r2);
        r2 = new rrrrrr.rrcrcr;	 Catch:{ MalformedURLException -> 0x0038 }
        r2.<init>(r3, r1);	 Catch:{ MalformedURLException -> 0x0038 }
    L_0x000e:
        switch(r0) {
            case 0: goto L_0x0015;
            case 1: goto L_0x000e;
            default: goto L_0x0011;
        };
    L_0x0011:
        switch(r0) {
            case 0: goto L_0x0015;
            case 1: goto L_0x000e;
            default: goto L_0x0014;
        };
    L_0x0014:
        goto L_0x0011;
    L_0x0015:
        r0 = b0411БББ0411Б;
        r1 = b04110411ББ0411Б;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b041104110411Б0411Б();
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x002b;
            default: goto L_0x0023;
        };
    L_0x0023:
        r0 = 43;
        b0411БББ0411Б = r0;
        r0 = 65;
        bБ0411ББ0411Б = r0;
    L_0x002b:
        r0 = new java.lang.Thread;
        r1 = "ping url";
        r0.<init>(r2, r1);
        r0.start();
        r0 = 1;
    L_0x0037:
        return r0;
    L_0x0038:
        r1 = move-exception;
        r1 = "HapticContentSDK";
        r2 = "Malformed Haptic URL";
        com.immersion.hapticmediasdk.utils.Log.e(r1, r2);
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.MediaPlaybackSDK.bй0439йййй(java.lang.String):boolean");
    }

    public int openHaptics(String str) {
        try {
            SDKStatus sDKStatus = getSDKStatus();
            try {
                if (!(sDKStatus == SDKStatus.STOPPED || sDKStatus == SDKStatus.NOT_INITIALIZED || sDKStatus == SDKStatus.INITIALIZED)) {
                    if (sDKStatus != SDKStatus.STOPPED_DUE_TO_ERROR) {
                        return -1;
                    }
                }
                int transitToState = this.mMediaTaskManager.transitToState(SDKStatus.NOT_INITIALIZED);
                if (transitToState != 0) {
                    return transitToState;
                }
                if (!URLUtil.isValidUrl(str)) {
                    return b04390439йййй(str);
                }
                if (!URLUtil.isHttpUrl(str)) {
                    if (URLUtil.isHttpsUrl(str)) {
                        transitToState = b0411БББ0411Б;
                        switch ((transitToState * (bБ04110411Б0411Б() + transitToState)) % b041104110411Б0411Б()) {
                            case 0:
                                break;
                            default:
                                b0411БББ0411Б = b0411Б0411Б0411Б();
                                bБ0411ББ0411Б = b0411Б0411Б0411Б();
                                break;
                        }
                    } else if (URLUtil.isFileUrl(str)) {
                        return b04390439йййй(str);
                    } else {
                        Log.e(b0414ДДД0414Д, "could not access hapt file url: unsupposted protocol");
                        return -5;
                    }
                }
                if (!bй0439йййй(str)) {
                    return -2;
                }
                this.mMediaTaskManager.setHapticsUrl(str, true);
                this.b04140414ДД0414Д.setIsStreaming();
                this.bДД0414Д0414Д.addAnalyticsDataCollector(this.b04140414ДД0414Д);
                return this.mMediaTaskManager.transitToState(SDKStatus.INITIALIZED);
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
