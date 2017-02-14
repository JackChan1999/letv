package com.immersion.hapticmediasdk.controllers;

import android.os.Handler;
import android.os.Message;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.MediaTaskManager;
import com.immersion.hapticmediasdk.models.HttpUnsuccessfulException;
import com.immersion.hapticmediasdk.utils.Log;
import com.immersion.hapticmediasdk.utils.Profiler;
import java.util.concurrent.atomic.AtomicInteger;

public class MediaController {
    public static int b04170417ЗЗ0417З = 1;
    private static final String b043D043D043Dннн = "MediaController";
    private static final int b043Dнн043Dнн = 200;
    public static int bЗ04170417З0417З = 0;
    public static int bЗ0417ЗЗ0417З = 11;
    public static int bЗЗ0417З0417З = 2;
    private static final int bннн043Dнн = 1000;
    private MediaTaskManager b043D043D043D043Dнн;
    private AtomicInteger b043D043Dн043Dнн = new AtomicInteger();
    private HapticPlaybackThread b043Dн043D043Dнн;
    private Profiler bн043D043D043Dнн = new Profiler();
    private AtomicInteger bн043Dн043Dнн = new AtomicInteger();
    private Handler bнн043D043Dнн;
    private Runnable bнннн043Dн;

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaController(android.os.Looper r4, com.immersion.hapticmediasdk.MediaTaskManager r5) {
        /*
        r3 = this;
        r2 = 1;
        r3.<init>();
        r0 = new java.util.concurrent.atomic.AtomicInteger;
        r0.<init>();
        r3.bн043Dн043Dнн = r0;
        r0 = new java.util.concurrent.atomic.AtomicInteger;
        r0.<init>();
        r3.b043D043Dн043Dнн = r0;
        r0 = new com.immersion.hapticmediasdk.utils.Profiler;
        r0.<init>();
        r3.bн043D043D043Dнн = r0;
        r0 = bЗ0417ЗЗ0417З;
        r1 = b04170417ЗЗ0417З;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bЗЗ0417З0417З;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x002d;
            default: goto L_0x0025;
        };
    L_0x0025:
        r0 = 71;
        bЗ0417ЗЗ0417З = r0;
        r0 = 92;
        b04170417ЗЗ0417З = r0;
    L_0x002d:
        r0 = new rrrrrr.crcrcc;
        r0.<init>(r3);
        r3.bнннн043Dн = r0;
        r3.b043D043D043D043Dнн = r5;
    L_0x0036:
        switch(r2) {
            case 0: goto L_0x0036;
            case 1: goto L_0x003d;
            default: goto L_0x0039;
        };
    L_0x0039:
        switch(r2) {
            case 0: goto L_0x0036;
            case 1: goto L_0x003d;
            default: goto L_0x003c;
        };
    L_0x003c:
        goto L_0x0039;
    L_0x003d:
        r0 = new rrrrrr.crcrrc;
        r0.<init>(r3, r4);
        r3.bнн043D043Dнн = r0;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.<init>(android.os.Looper, com.immersion.hapticmediasdk.MediaTaskManager):void");
    }

    public static int b041704170417З0417З() {
        return 1;
    }

    public static int b0417З0417З0417З() {
        return 97;
    }

    public static int b0417ЗЗ04170417З() {
        return 2;
    }

    public static /* synthetic */ MediaTaskManager b04490449044904490449щ(MediaController mediaController) {
        try {
            MediaTaskManager mediaTaskManager = mediaController.b043D043D043D043Dнн;
            if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗ04170417З0417З) {
                bЗ0417ЗЗ0417З = 43;
                bЗ04170417З0417З = 84;
            }
            return mediaTaskManager;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b04490449щ04490449щ(int r4) {
        /*
        r3 = this;
        r1 = 0;
        r0 = r3.bн043Dн043Dнн;
        r0.set(r4);
    L_0x0006:
        switch(r1) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0006;
            default: goto L_0x0009;
        };
    L_0x0009:
        switch(r1) {
            case 0: goto L_0x000d;
            case 1: goto L_0x0006;
            default: goto L_0x000c;
        };
    L_0x000c:
        goto L_0x0009;
    L_0x000d:
        r0 = r3.b043D043D043D043Dнн;
        r1 = bЗ0417ЗЗ0417З;
        r2 = b04170417ЗЗ0417З;
        r1 = r1 + r2;
        r2 = bЗ0417ЗЗ0417З;
        r1 = r1 * r2;
        r2 = bЗЗ0417З0417З;
        r1 = r1 % r2;
        r2 = bЗ04170417З0417З;
        if (r1 == r2) goto L_0x002a;
    L_0x001e:
        r1 = b0417З0417З0417З();
        bЗ0417ЗЗ0417З = r1;
        r1 = b0417З0417З0417З();
        bЗ04170417З0417З = r1;
    L_0x002a:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED_DUE_TO_BUFFERING;
        r0.transitToState(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.b04490449щ04490449щ(int):void");
    }

    public static /* synthetic */ HapticPlaybackThread b04490449щщщ0449(MediaController mediaController) {
        try {
            HapticPlaybackThread hapticPlaybackThread = mediaController.b043Dн043D043Dнн;
            int b0417З0417З0417З = b0417З0417З0417З();
            switch ((b0417З0417З0417З * (b04170417ЗЗ0417З + b0417З0417З0417З)) % bЗЗ0417З0417З) {
                case 0:
                    break;
                default:
                    bЗ0417ЗЗ0417З = 15;
                    bЗ04170417З0417З = b0417З0417З0417З();
                    break;
            }
            return hapticPlaybackThread;
        } catch (Exception e) {
            throw e;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static /* synthetic */ java.util.concurrent.atomic.AtomicInteger b0449щ044904490449щ(com.immersion.hapticmediasdk.controllers.MediaController r2) {
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
        r0 = bЗ0417ЗЗ0417З;
        r1 = b04170417ЗЗ0417З;
        r0 = r0 + r1;
        r1 = bЗ0417ЗЗ0417З;
        r0 = r0 * r1;
        r1 = bЗЗ0417З0417З;
        r0 = r0 % r1;
        r1 = bЗ04170417З0417З;
        if (r0 == r1) goto L_0x0021;
    L_0x0017:
        r0 = 64;
        bЗ0417ЗЗ0417З = r0;
        r0 = b0417З0417З0417З();
        bЗ04170417З0417З = r0;
    L_0x0021:
        r0 = r2.bн043Dн043Dнн;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.b0449щ044904490449щ(com.immersion.hapticmediasdk.controllers.MediaController):java.util.concurrent.atomic.AtomicInteger");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b0449щщ04490449щ(int r5, long r6) {
        /*
        r4 = this;
        r0 = r4.b043Dн043D043Dнн;
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
        r1 = bЗ0417ЗЗ0417З;
        r2 = b04170417ЗЗ0417З;
        r1 = r1 + r2;
        r2 = bЗ0417ЗЗ0417З;
        r1 = r1 * r2;
        r2 = bЗЗ0417З0417З;
        r1 = r1 % r2;
        r2 = bЗ04170417З0417З;
        if (r1 == r2) goto L_0x0021;
    L_0x001a:
        r1 = 52;
        bЗ0417ЗЗ0417З = r1;
        r1 = 7;
        bЗ04170417З0417З = r1;
    L_0x0021:
        r0.playHapticForPlaybackPosition(r5, r6);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.b0449щщ04490449щ(int, long):void");
    }

    public static /* synthetic */ void b0449щщщщ0449(MediaController mediaController, int i) {
        try {
            mediaController.b04490449щ04490449щ(i);
        } catch (Exception e) {
            throw e;
        }
    }

    public static int bЗЗЗ04170417З() {
        return 0;
    }

    public static /* synthetic */ AtomicInteger bщ0449044904490449щ(MediaController mediaController) {
        AtomicInteger atomicInteger = mediaController.b043D043Dн043Dнн;
        if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗ04170417З0417З) {
            bЗ0417ЗЗ0417З = 34;
            bЗ04170417З0417З = b0417З0417З0417З();
        }
        return atomicInteger;
    }

    private int bщ0449щ04490449щ() {
        try {
            this.b043Dн043D043Dнн.pauseHapticPlayback();
            if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗ04170417З0417З) {
                bЗ0417ЗЗ0417З = 39;
                bЗ04170417З0417З = 15;
            }
            return 0;
        } catch (Exception e) {
            throw e;
        }
    }

    private void bщщ044904490449щ(Message message) {
        Exception exception;
        try {
            exception = (Exception) message.getData().getSerializable(HapticPlaybackThread.HAPTIC_DOWNLOAD_EXCEPTION_KEY);
            if (exception instanceof HttpUnsuccessfulException) {
                Log.e(b043D043D043Dннн, "caught HttpUnsuccessfulExcetion http status code = " + ((HttpUnsuccessfulException) exception).getHttpStatusCode());
            }
            try {
                Log.e(b043D043D043Dннн, "HapticDownloadError: " + (exception.getMessage() == null ? "Null Message" : exception.getMessage()));
                MediaTaskManager mediaTaskManager = this.b043D043D043D043Dнн;
                if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗ04170417З0417З) {
                    bЗ0417ЗЗ0417З = 34;
                    bЗ04170417З0417З = b0417З0417З0417З();
                }
                mediaTaskManager.transitToState(SDKStatus.STOPPED_DUE_TO_ERROR);
            } catch (Exception exception2) {
                throw exception2;
            }
        } catch (Exception exception22) {
            throw exception22;
        }
    }

    private void bщщщ04490449щ(boolean z) {
        int i = 0;
        boolean isStarted = this.b043Dн043D043Dнн.isStarted();
        while (true) {
            if (z) {
                if (isStarted) {
                    return;
                }
            } else if (!isStarted) {
                return;
            }
            synchronized (this.b043Dн043D043Dнн) {
                try {
                    this.b043Dн043D043Dнн.wait(200);
                } catch (InterruptedException e) {
                }
            }
            isStarted = this.b043Dн043D043Dнн.isStarted();
            i++;
            if (!z && i >= 5) {
                return;
            }
        }
    }

    public static /* synthetic */ void bщщщщщ0449(MediaController mediaController, int i, long j) {
        if (((b0417З0417З0417З() + b04170417ЗЗ0417З) * b0417З0417З0417З()) % bЗЗ0417З0417З != bЗ04170417З0417З) {
            bЗ0417ЗЗ0417З = 2;
            bЗ04170417З0417З = b0417З0417З0417З();
        }
        try {
            mediaController.b0449щщ04490449щ(i, j);
        } catch (Exception e) {
            throw e;
        }
    }

    public Handler getControlHandler() {
        return this.bнн043D043Dнн;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getCurrentPosition() {
        /*
        r4 = this;
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
        r0 = r4.b043D043D043D043Dнн;
        r0 = r0.getMediaTimestamp();
        r2 = bЗ0417ЗЗ0417З;
        r3 = b04170417ЗЗ0417З;
        r3 = r3 + r2;
        r2 = r2 * r3;
        r3 = bЗЗ0417З0417З;
        r2 = r2 % r3;
        switch(r2) {
            case 0: goto L_0x0024;
            default: goto L_0x001a;
        };
    L_0x001a:
        r2 = 11;
        bЗ0417ЗЗ0417З = r2;
        r2 = b0417З0417З0417З();
        bЗ04170417З0417З = r2;
    L_0x0024:
        r0 = (int) r0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.getCurrentPosition():int");
    }

    public long getReferenceTimeForCurrentPosition() {
        if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗЗЗ04170417З()) {
            bЗ0417ЗЗ0417З = b0417З0417З0417З();
            bЗ04170417З0417З = 74;
        }
        try {
            try {
                return this.b043D043D043D043Dнн.getMediaReferenceTime();
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public void initHapticPlayback(HapticPlaybackThread hapticPlaybackThread) {
        this.b043Dн043D043Dнн = hapticPlaybackThread;
        if (((b0417З0417З0417З() + b04170417ЗЗ0417З) * b0417З0417З0417З()) % bЗЗ0417З0417З != bЗ04170417З0417З) {
            bЗ0417ЗЗ0417З = b0417З0417З0417З();
            bЗ04170417З0417З = 14;
        }
        this.b043Dн043D043Dнн.start();
        bщщщ04490449щ(true);
        while (true) {
            switch (1) {
                case null:
                    break;
                case 1:
                    return;
                default:
                    while (true) {
                        switch (null) {
                            case null:
                                return;
                            case 1:
                                break;
                            default:
                        }
                    }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isPlaying() {
        /*
        r4 = this;
        r0 = 1;
        r1 = 0;
        r2 = r4.b043D043D043D043Dнн;
        r2 = r2.getSDKStatus();
        r3 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;
    L_0x000a:
        switch(r0) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0011;
            default: goto L_0x000d;
        };
    L_0x000d:
        switch(r1) {
            case 0: goto L_0x0011;
            case 1: goto L_0x000a;
            default: goto L_0x0010;
        };
    L_0x0010:
        goto L_0x000d;
    L_0x0011:
        if (r2 != r3) goto L_0x002c;
    L_0x0013:
        r1 = bЗ0417ЗЗ0417З;
        r2 = b04170417ЗЗ0417З;
        r2 = r2 + r1;
        r1 = r1 * r2;
        r2 = bЗЗ0417З0417З;
        r1 = r1 % r2;
        switch(r1) {
            case 0: goto L_0x002b;
            default: goto L_0x001f;
        };
    L_0x001f:
        r1 = b0417З0417З0417З();
        bЗ0417ЗЗ0417З = r1;
        r1 = b0417З0417З0417З();
        bЗ04170417З0417З = r1;
    L_0x002b:
        return r0;
    L_0x002c:
        r0 = r1;
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.isPlaying():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDestroy(android.os.Handler r5) {
        /*
        r4 = this;
        r3 = 0;
        r0 = r4.b043Dн043D043Dнн;
        r1 = bЗ0417ЗЗ0417З;
        r2 = b04170417ЗЗ0417З;
        r1 = r1 + r2;
        r2 = bЗ0417ЗЗ0417З;
        r1 = r1 * r2;
        r2 = bЗЗ0417З0417З;
        r1 = r1 % r2;
        r2 = bЗ04170417З0417З;
        if (r1 == r2) goto L_0x001e;
    L_0x0012:
        r1 = b0417З0417З0417З();
        bЗ0417ЗЗ0417З = r1;
        r1 = b0417З0417З0417З();
        bЗ04170417З0417З = r1;
    L_0x001e:
        if (r0 == 0) goto L_0x0033;
    L_0x0020:
        r0 = r4.b043Dн043D043Dнн;
        r0.quitHapticPlayback();
    L_0x0025:
        switch(r3) {
            case 0: goto L_0x002d;
            case 1: goto L_0x0025;
            default: goto L_0x0028;
        };
    L_0x0028:
        r0 = 1;
        switch(r0) {
            case 0: goto L_0x0025;
            case 1: goto L_0x002d;
            default: goto L_0x002c;
        };
    L_0x002c:
        goto L_0x0028;
    L_0x002d:
        r4.bщщщ04490449щ(r3);
        r0 = 0;
        r4.b043Dн043D043Dнн = r0;
    L_0x0033:
        r0 = r4.b043D043D043D043Dнн;
        r5.removeCallbacks(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.onDestroy(android.os.Handler):void");
    }

    public int onPause() {
        try {
            int bщ0449щ04490449щ = bщ0449щ04490449щ();
            if (((bЗ0417ЗЗ0417З + b041704170417З0417З()) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗ04170417З0417З) {
                bЗ0417ЗЗ0417З = b0417З0417З0417З();
                bЗ04170417З0417З = b0417З0417З0417З();
            }
            return bщ0449щ04490449щ;
        } catch (Exception e) {
            throw e;
        }
    }

    public int onPrepared() {
        int prepareHapticPlayback = prepareHapticPlayback();
        if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗ04170417З0417З) {
            bЗ0417ЗЗ0417З = b0417З0417З0417З();
            bЗ04170417З0417З = b0417З0417З0417З();
        }
        return prepareHapticPlayback;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void playbackStarted() {
        /*
        r4 = this;
        r0 = r4.b043Dн043D043Dнн;
        if (r0 == 0) goto L_0x0033;
    L_0x0004:
        r0 = r4.b043Dн043D043Dнн;
        r0 = r0.getHandler();
    L_0x000a:
        r1 = 1;
        switch(r1) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0013;
            default: goto L_0x000e;
        };
    L_0x000e:
        r1 = 0;
        switch(r1) {
            case 0: goto L_0x0013;
            case 1: goto L_0x000a;
            default: goto L_0x0012;
        };
    L_0x0012:
        goto L_0x000e;
    L_0x0013:
        r1 = r4.bнннн043Dн;
        r2 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0.postDelayed(r1, r2);
        r0 = bЗ0417ЗЗ0417З;
        r1 = b041704170417З0417З();
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = b0417ЗЗ04170417З();
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0032;
            default: goto L_0x002a;
        };
    L_0x002a:
        r0 = 32;
        bЗ0417ЗЗ0417З = r0;
        r0 = 19;
        bЗ04170417З0417З = r0;
    L_0x0032:
        return;
    L_0x0033:
        r0 = "MediaController";
        r1 = "Can't start periodic sync since haptic playback thread stopped.";
        com.immersion.hapticmediasdk.utils.Log.e(r0, r1);
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.controllers.MediaController.playbackStarted():void");
    }

    public int prepareHapticPlayback() {
        this.bн043D043D043Dнн.startTiming();
        this.b043Dн043D043Dнн.prepareHapticPlayback(this.bн043Dн043Dнн.get(), this.b043D043Dн043Dнн.incrementAndGet());
        return 0;
    }

    public void seekTo(int i) {
        AtomicInteger atomicInteger = this.bн043Dн043Dнн;
        if (i <= 0) {
            i = 0;
        }
        atomicInteger.set(i);
        if (this.b043Dн043D043Dнн != null) {
            this.b043Dн043D043Dнн.getHandler().removeCallbacks(this.bнннн043Dн);
            this.b043Dн043D043Dнн.removePlaybackCallbacks();
        }
    }

    public void setRequestBufferPosition(int i) {
        try {
            AtomicInteger atomicInteger = this.bн043Dн043Dнн;
            if (((bЗ0417ЗЗ0417З + b04170417ЗЗ0417З) * bЗ0417ЗЗ0417З) % bЗЗ0417З0417З != bЗЗЗ04170417З()) {
                bЗ0417ЗЗ0417З = 27;
                bЗ04170417З0417З = 36;
            }
            atomicInteger.set(i);
        } catch (Exception e) {
            throw e;
        }
    }

    public int stopHapticPlayback() {
        this.bн043Dн043Dнн.set(0);
        this.b043Dн043D043Dнн.stopHapticPlayback();
        this.b043Dн043D043Dнн.getHandler().removeCallbacks(this.bнннн043Dн);
        return 0;
    }

    public void waitHapticStopped() {
        boolean isStopped = this.b043Dн043D043Dнн.isStopped();
        int i = 0;
        while (!isStopped && i < 5) {
            synchronized (this.b043Dн043D043Dнн) {
                try {
                    this.b043Dн043D043Dнн.wait(200);
                } catch (InterruptedException e) {
                }
            }
            isStopped = this.b043Dн043D043Dнн.isStopped();
            i++;
        }
    }
}
