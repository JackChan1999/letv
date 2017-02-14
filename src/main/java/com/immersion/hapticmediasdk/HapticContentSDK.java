package com.immersion.hapticmediasdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.immersion.aws.Credential;
import com.immersion.aws.analytics.ImmrAnalytics;
import com.immersion.aws.pm.PolicyManager;
import com.immersion.aws.pm.PolicyPathInfo;
import com.immersion.aws.tvm.TVMAuthenticator;
import com.immersion.hapticmediasdk.utils.Log;
import com.immersion.hapticmediasdk.utils.RuntimeInfo;
import rrrrrr.ccccrc;
import rrrrrr.cccrcr;
import rrrrrr.ccrrcc;
import rrrrrr.ccrrcr;
import rrrrrr.rrrrrc;

public abstract class HapticContentSDK {
    public static final String DEFAULT_DNS = "DEFAULT";
    public static final int INACCESSIBLE_URL = -2;
    public static final int INVALID = -1;
    public static final int MALFORMED_URL = -4;
    public static final int PERMISSION_DENIED = -3;
    public static final int SDKMODE_MEDIAPLAYBACK = 0;
    public static final int SUCCESS = 0;
    public static final int UNSUPPORTED_PROTOCOL = -5;
    public static int b0425ХХ0425Х0425 = 1;
    private static final String b043D043D043Dн043D043D = "HapticContentSDK";
    public static final int b043Dнн043D043D043D = 1500;
    public static int bХ04250425ХХ0425 = 2;
    public static int bХХ0425ХХ0425 = 35;
    public static int bХХХ0425Х0425 = 0;
    private static final String bннн043D043D043D = "HapticMediaSDK - v2.0.87.3";
    private PolicyManager b041404140414ДДД;
    private ccrrcr b04140414ДДДД;
    private ccrrcc b0414Д0414ДДД;
    private String b0414ДДДДД;
    private Handler b043D043Dн043D043D043D;
    private RuntimeInfo b043Dн043D043D043D043D;
    private ImmrAnalytics bД04140414ДДД;
    private String bД0414ДДДД;
    private ccccrc bДД0414ДДД;
    private cccrcr bн043D043D043D043D043D;
    private HandlerThread bн043Dн043D043D043D;
    private Context bнн043D043D043D043D;
    public boolean mDisposed;
    public MediaTaskManager mMediaTaskManager;
    public SDKStatus mSDKStatus;

    public enum SDKStatus {
        NOT_INITIALIZED,
        INITIALIZED,
        PLAYING,
        STOPPED,
        STOPPED_DUE_TO_ERROR;
        
        public static int b041704170417З04170417 = 1;
        public static int b04170417З041704170417 = 0;
        public static int b0417З0417З04170417 = 40;
        public static int bЗЗЗ041704170417 = 2;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        static {
            /*
            r7 = 4;
            r6 = 3;
            r5 = 2;
            r4 = 1;
            r3 = 0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "NOT_INITIALIZED";
            r0.<init>(r1, r3);
            NOT_INITIALIZED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "INITIALIZED";
            r0.<init>(r1, r4);
            INITIALIZED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PLAYING";
            r0.<init>(r1, r5);
            PLAYING = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "STOPPED";
            r0.<init>(r1, r6);
            STOPPED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "STOPPED_DUE_TO_ERROR";
            r0.<init>(r1, r7);
            STOPPED_DUE_TO_ERROR = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = b0417ЗЗ041704170417();
            r2 = b041704170417З04170417;
            r1 = r1 + r2;
            r2 = b0417ЗЗ041704170417();
            r1 = r1 * r2;
            r2 = bЗЗЗ041704170417;
            r1 = r1 % r2;
            r2 = b04170417З041704170417;
            if (r1 == r2) goto L_0x0051;
        L_0x0047:
            r1 = b0417ЗЗ041704170417();
            b0417З0417З04170417 = r1;
            r1 = 44;
            b04170417З041704170417 = r1;
        L_0x0051:
            r1 = "PAUSED";
            r2 = 5;
            r0.<init>(r1, r2);
            PAUSED = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PAUSED_DUE_TO_TIMEOUT";
            r2 = 6;
            r0.<init>(r1, r2);
            PAUSED_DUE_TO_TIMEOUT = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "PAUSED_DUE_TO_BUFFERING";
            r2 = 7;
            r0.<init>(r1, r2);
            PAUSED_DUE_TO_BUFFERING = r0;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus;
            r1 = "DISPOSED";
            r2 = 8;
            r0.<init>(r1, r2);
            DISPOSED = r0;
            r0 = 9;
            r0 = new com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus[r0];
            r1 = NOT_INITIALIZED;
            r0[r3] = r1;
            r1 = INITIALIZED;
            r0[r4] = r1;
            r1 = PLAYING;
            r0[r5] = r1;
            r1 = STOPPED;
            r0[r6] = r1;
            r1 = STOPPED_DUE_TO_ERROR;
            r0[r7] = r1;
            r1 = 5;
            r2 = PAUSED;
            r0[r1] = r2;
            r1 = 6;
            r2 = PAUSED_DUE_TO_TIMEOUT;
            r0[r1] = r2;
            r1 = 7;
            r2 = PAUSED_DUE_TO_BUFFERING;
            r0[r1] = r2;
            r1 = 8;
            r2 = DISPOSED;
            r0[r1] = r2;
        L_0x00a5:
            switch(r4) {
                case 0: goto L_0x00a5;
                case 1: goto L_0x00ac;
                default: goto L_0x00a8;
            };
        L_0x00a8:
            switch(r3) {
                case 0: goto L_0x00ac;
                case 1: goto L_0x00a5;
                default: goto L_0x00ab;
            };
        L_0x00ab:
            goto L_0x00a8;
        L_0x00ac:
            bн043D043D043D043Dн = r0;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.<clinit>():void");
        }

        public static int b0417ЗЗ041704170417() {
            return 44;
        }

        public static int bЗ04170417З04170417() {
            return 0;
        }

        public static int bЗ0417З041704170417() {
            return 1;
        }

        public static SDKStatus valueOfCaseInsensitive(String str) {
            try {
                SDKStatus[] values = values();
                int length = values.length;
                int i = 0;
                while (i < length) {
                    if (((b0417ЗЗ041704170417() + b041704170417З04170417) * b0417ЗЗ041704170417()) % bЗЗЗ041704170417 != b04170417З041704170417) {
                        b0417З0417З04170417 = 70;
                        b04170417З041704170417 = 64;
                    }
                    SDKStatus sDKStatus = values[i];
                    try {
                        if (str.equalsIgnoreCase(sDKStatus.name())) {
                            return sDKStatus;
                        }
                        i++;
                    } catch (Exception e) {
                        throw e;
                    }
                }
                return null;
            } catch (Exception e2) {
                throw e2;
            }
        }
    }

    public HapticContentSDK(int i, Context context, String str, String str2, String str3) {
        try {
            this.mSDKStatus = SDKStatus.NOT_INITIALIZED;
            this.mDisposed = false;
            this.bнн043D043D043D043D = context;
            try {
                Credential credential = new Credential(str, str2);
                TVMAuthenticator tVMAuthenticator = new TVMAuthenticator(context, credential, str3);
                PolicyPathInfo generate = PolicyPathInfo.generate(context, credential, bннн043D043D043D);
                this.b041404140414ДДД = new PolicyManager();
                this.bД04140414ДДД = ImmrAnalytics.getInstance(context);
                try {
                    this.b041404140414ДДД.init(this.bнн043D043D043D043D, generate, extractMajorMinorVer(bннн043D043D043D));
                } catch (OutOfMemoryError e) {
                    Log.e(b043D043D043Dн043D043D, "Failed to initialize native policy manager");
                    this.b041404140414ДДД.dispose();
                } catch (UnsatisfiedLinkError e2) {
                    Log.e(b043D043D043Dн043D043D, "Failed to load native library libImmEndpointWarp.so");
                    this.b041404140414ДДД.dispose();
                }
                tVMAuthenticator.addListener(this.b041404140414ДДД);
                tVMAuthenticator.addListener(this.bД04140414ДДД);
                new Thread(new rrrrrc(this, tVMAuthenticator)).start();
                this.b043Dн043D043D043D043D = new RuntimeInfo();
                this.bн043D043D043D043D043D = new cccrcr(this);
                this.b04140414ДДДД = new ccrrcr(this);
                this.b0414Д0414ДДД = new ccrrcc(this, str);
                this.bДД0414ДДД = new ccccrc(this);
                int i2 = bХХ0425ХХ0425;
                switch ((i2 * (b0425Х0425ХХ0425() + i2)) % bХ04250425ХХ0425) {
                    case 0:
                        break;
                    default:
                        bХХ0425ХХ0425 = 83;
                        bХ04250425ХХ0425 = b042504250425ХХ0425();
                        break;
                }
                this.bДД0414ДДД.determineCallingPackage();
            } catch (Exception e3) {
                throw e3;
            }
        } catch (Exception e32) {
            throw e32;
        }
    }

    public static int b042504250425ХХ0425() {
        return 24;
    }

    public static int b04250425Х0425Х0425() {
        return 0;
    }

    public static int b0425Х0425ХХ0425() {
        return 1;
    }

    public static int bХ0425Х0425Х0425() {
        return 2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String extractMajorMinorVer(java.lang.String r4) {
        /*
        r3 = 0;
        r0 = 46;
        r0 = r4.indexOf(r0);
    L_0x0007:
        switch(r3) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0007;
            default: goto L_0x000a;
        };
    L_0x000a:
        switch(r3) {
            case 0: goto L_0x000e;
            case 1: goto L_0x0007;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x000a;
    L_0x000e:
        r1 = bХХ0425ХХ0425;
        r2 = b0425ХХ0425Х0425;
        r1 = r1 + r2;
        r2 = bХХ0425ХХ0425;
        r1 = r1 * r2;
        r2 = bХ04250425ХХ0425;
        r1 = r1 % r2;
        r2 = bХХХ0425Х0425;
        if (r1 == r2) goto L_0x0027;
    L_0x001d:
        r1 = 31;
        bХХ0425ХХ0425 = r1;
        r1 = b042504250425ХХ0425();
        bХХХ0425Х0425 = r1;
    L_0x0027:
        r0 = r0 + 2;
        r0 = r4.substring(r3, r0);
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.extractMajorMinorVer(java.lang.String):java.lang.String");
    }

    public int bщ0449щ044904490449() {
        try {
            if (this.bнн043D043D043D043D.getPackageManager().checkPermission("android.permission.VIBRATE", this.bнн043D043D043D043D.getPackageName()) == 0) {
                String str = "SDK Monitor";
                if (((b042504250425ХХ0425() + b0425ХХ0425Х0425) * b042504250425ХХ0425()) % bХ0425Х0425Х0425() != bХХХ0425Х0425) {
                    bХХ0425ХХ0425 = b042504250425ХХ0425();
                    bХХХ0425Х0425 = 20;
                }
                try {
                    this.bн043Dн043D043D043D = new HandlerThread(str);
                    this.bн043Dн043D043D043D.start();
                    this.b043D043Dн043D043D043D = new Handler(this.bн043Dн043D043D043D.getLooper());
                    this.mMediaTaskManager = new MediaTaskManager(this.b043D043Dн043D043D043D, this.bнн043D043D043D043D, this.b043Dн043D043D043D043D, this.b041404140414ДДД);
                    return 0;
                } catch (Exception e) {
                    throw e;
                }
            }
            Log.e(b043D043D043Dн043D043D, "Failed to create a Haptic Content SDK instance.Vibrate permission denied.");
            return -3;
        } catch (Exception e2) {
            throw e2;
        }
    }

    public final void dispose() {
        if (getSDKStatus() != SDKStatus.DISPOSED) {
            if (((bХХ0425ХХ0425 + b0425Х0425ХХ0425()) * bХХ0425ХХ0425) % bХ04250425ХХ0425 != b04250425Х0425Х0425()) {
                bХХ0425ХХ0425 = 17;
                bХХХ0425Х0425 = b042504250425ХХ0425();
            }
            this.mMediaTaskManager.transitToState(SDKStatus.NOT_INITIALIZED);
            this.bн043Dн043D043D043D.quit();
            this.bн043Dн043D043D043D = null;
            this.mMediaTaskManager = null;
            this.b041404140414ДДД.dispose();
            this.mDisposed = true;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void finalize() throws java.lang.Throwable {
        /*
        r2 = this;
        r0 = -1;
        r2.dispose();	 Catch:{ Throwable -> 0x000a }
        super.finalize();	 Catch:{ Exception -> 0x001b }
    L_0x0007:
        r1 = new int[r0];	 Catch:{ Exception -> 0x0013 }
        goto L_0x0007;
    L_0x000a:
        r0 = move-exception;
        throw r0;	 Catch:{ all -> 0x000c }
    L_0x000c:
        r0 = move-exception;
        super.finalize();	 Catch:{ Exception -> 0x0011 }
        throw r0;	 Catch:{ Exception -> 0x0011 }
    L_0x0011:
        r0 = move-exception;
        throw r0;
    L_0x0013:
        r0 = move-exception;
        r0 = b042504250425ХХ0425();
        bХХ0425ХХ0425 = r0;
        return;
    L_0x001b:
        r0 = move-exception;
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.finalize():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus getSDKStatus() {
        /*
        r2 = this;
        r0 = r2.mDisposed;
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
        if (r0 == 0) goto L_0x0010;
    L_0x000d:
        r0 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.DISPOSED;
    L_0x000f:
        return r0;
    L_0x0010:
        r0 = bХХ0425ХХ0425;
        r1 = b0425ХХ0425Х0425;
        r1 = r1 + r0;
        r0 = r0 * r1;
        r1 = bХ04250425ХХ0425;
        r0 = r0 % r1;
        switch(r0) {
            case 0: goto L_0x0026;
            default: goto L_0x001c;
        };
    L_0x001c:
        r0 = b042504250425ХХ0425();
        bХХ0425ХХ0425 = r0;
        r0 = 80;
        bХХХ0425Х0425 = r0;
    L_0x0026:
        r0 = r2.mMediaTaskManager;
        r0 = r0.getSDKStatus();
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.getSDKStatus():com.immersion.hapticmediasdk.HapticContentSDK$SDKStatus");
    }

    public final String getVersion() {
        if (((bХХ0425ХХ0425 + b0425Х0425ХХ0425()) * bХХ0425ХХ0425) % bХ04250425ХХ0425 != bХХХ0425Х0425) {
            bХХ0425ХХ0425 = 16;
            bХХХ0425Х0425 = 33;
        }
        try {
            return HapticMediaSDKVersion.Version;
        } catch (Exception e) {
            throw e;
        }
    }

    public final int mute() {
        if (getSDKStatus() == SDKStatus.DISPOSED) {
            return -1;
        }
        int i = bХХ0425ХХ0425;
        switch ((i * (b0425ХХ0425Х0425 + i)) % bХ04250425ХХ0425) {
            case 0:
                break;
            default:
                bХХ0425ХХ0425 = 5;
                bХХХ0425Х0425 = b042504250425ХХ0425();
                break;
        }
        this.b043Dн043D043D043D043D.mute();
        this.bн043D043D043D043D043D.setMuted();
        return 0;
    }

    public abstract int openHaptics(String str);

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int pause() {
        /*
        r4 = this;
        r2 = 1;
        r0 = r4.getSDKStatus();
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.DISPOSED;
        if (r0 == r1) goto L_0x000d;
    L_0x0009:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;
        if (r0 != r1) goto L_0x002e;
    L_0x000d:
        r0 = -1;
    L_0x000e:
        return r0;
    L_0x000f:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PAUSED;
        r2 = bХХ0425ХХ0425;
        r3 = b0425ХХ0425Х0425;
        r3 = r3 + r2;
        r2 = r2 * r3;
        r3 = bХ04250425ХХ0425;
        r2 = r2 % r3;
        switch(r2) {
            case 0: goto L_0x0029;
            default: goto L_0x001d;
        };
    L_0x001d:
        r2 = b042504250425ХХ0425();
        bХХ0425ХХ0425 = r2;
        r2 = b042504250425ХХ0425();
        bХХХ0425Х0425 = r2;
    L_0x0029:
        r0 = r0.transitToState(r1);
        goto L_0x000e;
    L_0x002e:
        r0 = r4.mMediaTaskManager;
    L_0x0030:
        switch(r2) {
            case 0: goto L_0x0030;
            case 1: goto L_0x000f;
            default: goto L_0x0033;
        };
    L_0x0033:
        switch(r2) {
            case 0: goto L_0x0030;
            case 1: goto L_0x000f;
            default: goto L_0x0036;
        };
    L_0x0036:
        goto L_0x0033;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.pause():int");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int play() {
        /*
        r5 = this;
        r4 = 1;
        r0 = r5.getSDKStatus();
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.INITIALIZED;
        if (r0 == r1) goto L_0x0026;
    L_0x0009:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED;
        if (r0 != r1) goto L_0x003d;
    L_0x000d:
        r0 = bХХ0425ХХ0425;
        r1 = b0425ХХ0425Х0425;
        r0 = r0 + r1;
        r1 = bХХ0425ХХ0425;
        r0 = r0 * r1;
        r1 = bХ04250425ХХ0425;
        r0 = r0 % r1;
        r1 = bХХХ0425Х0425;
        if (r0 == r1) goto L_0x0026;
    L_0x001c:
        r0 = 86;
        bХХ0425ХХ0425 = r0;
        r0 = b042504250425ХХ0425();
        bХХХ0425Х0425 = r0;
    L_0x0026:
        r0 = r5.mMediaTaskManager;
        r2 = 0;
        r0.setMediaTimestamp(r2);
        r0 = r5.mMediaTaskManager;
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.PLAYING;
    L_0x0031:
        switch(r4) {
            case 0: goto L_0x0031;
            case 1: goto L_0x0038;
            default: goto L_0x0034;
        };
    L_0x0034:
        switch(r4) {
            case 0: goto L_0x0031;
            case 1: goto L_0x0038;
            default: goto L_0x0037;
        };
    L_0x0037:
        goto L_0x0034;
    L_0x0038:
        r0 = r0.transitToState(r1);
    L_0x003c:
        return r0;
    L_0x003d:
        r0 = -1;
        goto L_0x003c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.play():int");
    }

    public final int resume() {
        SDKStatus sDKStatus = getSDKStatus();
        SDKStatus sDKStatus2 = SDKStatus.PAUSED;
        int b042504250425ХХ0425 = b042504250425ХХ0425();
        switch ((b042504250425ХХ0425 * (b0425ХХ0425Х0425 + b042504250425ХХ0425)) % bХ04250425ХХ0425) {
            case 0:
                break;
            default:
                bХХ0425ХХ0425 = 7;
                bХХХ0425Х0425 = 23;
                break;
        }
        if (sDKStatus != sDKStatus2 && sDKStatus != SDKStatus.PLAYING && sDKStatus != SDKStatus.STOPPED) {
            return -1;
        }
        this.mMediaTaskManager.setMediaReferenceTime();
        return this.mMediaTaskManager.transitToState(SDKStatus.PLAYING);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int seek(int r5) {
        /*
        r4 = this;
        r2 = 1;
        r0 = r4.getSDKStatus();
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.DISPOSED;
        if (r0 == r1) goto L_0x0033;
    L_0x0009:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.NOT_INITIALIZED;
    L_0x000b:
        switch(r2) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0012;
            default: goto L_0x000e;
        };
    L_0x000e:
        switch(r2) {
            case 0: goto L_0x000b;
            case 1: goto L_0x0012;
            default: goto L_0x0011;
        };
    L_0x0011:
        goto L_0x000e;
    L_0x0012:
        if (r0 == r1) goto L_0x0033;
    L_0x0014:
        r1 = com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus.STOPPED_DUE_TO_ERROR;
        r2 = bХХ0425ХХ0425;
        r3 = b0425ХХ0425Х0425;
        r2 = r2 + r3;
        r3 = bХХ0425ХХ0425;
        r2 = r2 * r3;
        r3 = bХ04250425ХХ0425;
        r2 = r2 % r3;
        r3 = bХХХ0425Х0425;
        if (r2 == r3) goto L_0x0031;
    L_0x0025:
        r2 = b042504250425ХХ0425();
        bХХ0425ХХ0425 = r2;
        r2 = b042504250425ХХ0425();
        bХХХ0425Х0425 = r2;
    L_0x0031:
        if (r0 != r1) goto L_0x0035;
    L_0x0033:
        r0 = -1;
    L_0x0034:
        return r0;
    L_0x0035:
        r0 = r4.mMediaTaskManager;
        r0 = r0.SeekTo(r5);
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.immersion.hapticmediasdk.HapticContentSDK.seek(int):int");
    }

    public final int stop() {
        try {
            int i;
            SDKStatus sDKStatus = getSDKStatus();
            if (sDKStatus == SDKStatus.DISPOSED || sDKStatus == SDKStatus.NOT_INITIALIZED) {
                i = -1;
                if (((bХХ0425ХХ0425 + b0425ХХ0425Х0425) * bХХ0425ХХ0425) % bХ04250425ХХ0425 != bХХХ0425Х0425) {
                    bХХ0425ХХ0425 = b042504250425ХХ0425();
                    bХХХ0425Х0425 = b042504250425ХХ0425();
                }
            } else {
                this.bД04140414ДДД.addAnalyticsDataCollector(this.bн043D043D043D043D043D);
                this.bД04140414ДДД.addAnalyticsDataCollector(this.b04140414ДДДД);
                this.bД04140414ДДД.addAnalyticsDataCollector(this.bДД0414ДДД);
                try {
                    this.bД04140414ДДД.addAnalyticsDataCollector(this.b0414Д0414ДДД);
                    i = this.mMediaTaskManager.transitToState(SDKStatus.STOPPED);
                } catch (Exception e) {
                    throw e;
                }
            }
            return i;
        } catch (Exception e2) {
            throw e2;
        }
    }

    public final int unmute() {
        try {
            SDKStatus sDKStatus = getSDKStatus();
            SDKStatus sDKStatus2 = SDKStatus.DISPOSED;
            if (((bХХ0425ХХ0425 + b0425ХХ0425Х0425) * bХХ0425ХХ0425) % bХ04250425ХХ0425 != bХХХ0425Х0425) {
                bХХ0425ХХ0425 = b042504250425ХХ0425();
                bХХХ0425Х0425 = b042504250425ХХ0425();
            }
            if (sDKStatus == sDKStatus2) {
                return -1;
            }
            try {
                this.b043Dн043D043D043D043D.unmute();
                this.bн043D043D043D043D043D.setUnMuted();
                return 0;
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }

    public final int update(long j) {
        int i = bХХ0425ХХ0425;
        switch ((i * (b0425ХХ0425Х0425 + i)) % bХ04250425ХХ0425) {
            case 0:
                break;
            default:
                bХХ0425ХХ0425 = 34;
                bХХХ0425Х0425 = 61;
                break;
        }
        try {
            SDKStatus sDKStatus = getSDKStatus();
            if (sDKStatus == SDKStatus.PLAYING || sDKStatus == SDKStatus.PAUSED_DUE_TO_TIMEOUT) {
                try {
                    this.mMediaTaskManager.setMediaTimestamp(j);
                    return this.mMediaTaskManager.transitToState(SDKStatus.PLAYING);
                } catch (Exception e) {
                    throw e;
                }
            } else if (sDKStatus != SDKStatus.PAUSED && sDKStatus != SDKStatus.PAUSED_DUE_TO_BUFFERING) {
                return -1;
            } else {
                this.mMediaTaskManager.setMediaTimestamp(j);
                return 0;
            }
        } catch (Exception e2) {
            throw e2;
        }
    }
}
