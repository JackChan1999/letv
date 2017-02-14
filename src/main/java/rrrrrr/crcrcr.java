package rrrrrr;

import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;

public /* synthetic */ class crcrcr {
    public static final /* synthetic */ int[] b041B041BЛ041B041B041B;
    public static int b04270427Ч042704270427 = 49;

    static {
        try {
            b041B041BЛ041B041B041B = new int[SDKStatus.values().length];
            try {
                b041B041BЛ041B041B041B[SDKStatus.NOT_INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b041B041BЛ041B041B041B[SDKStatus.INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b041B041BЛ041B041B041B[SDKStatus.PLAYING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                try {
                    b041B041BЛ041B041B041B[SDKStatus.PAUSED.ordinal()] = 4;
                    try {
                        b041B041BЛ041B041B041B[SDKStatus.PAUSED_DUE_TO_TIMEOUT.ordinal()] = 5;
                        int bЧ0427Ч042704270427 = bЧ0427Ч042704270427();
                        switch ((bЧ0427Ч042704270427 * (b04270427Ч042704270427 + bЧ0427Ч042704270427)) % bЧЧ0427042704270427()) {
                            case 0:
                                break;
                            default:
                                break;
                        }
                    } catch (NoSuchFieldError e4) {
                    }
                    try {
                        b041B041BЛ041B041B041B[SDKStatus.PAUSED_DUE_TO_BUFFERING.ordinal()] = 6;
                    } catch (NoSuchFieldError e5) {
                    }
                    try {
                        b041B041BЛ041B041B041B[SDKStatus.STOPPED.ordinal()] = 7;
                    } catch (NoSuchFieldError e6) {
                    }
                } catch (Exception e7) {
                    throw e7;
                }
            } catch (NoSuchFieldError e8) {
            }
        } catch (Exception e72) {
            throw e72;
        }
    }

    public static int bЧ0427Ч042704270427() {
        return 26;
    }

    public static int bЧЧ0427042704270427() {
        return 2;
    }
}
