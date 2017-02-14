package rrrrrr;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import com.immersion.hapticmediasdk.HapticContentSDK.SDKStatus;
import com.immersion.hapticmediasdk.controllers.MediaController;

public class crcrrc extends Handler {
    public static int b0444044404440444фф = 13;
    public static int b0444ффф0444ф = 1;
    public static int bф0444фф0444ф = 2;
    public static int bфффф0444ф;
    public final /* synthetic */ MediaController b043Dн043Dнн043D;

    public crcrrc(MediaController mediaController, Looper looper) {
        if (((b0444044404440444фф + b0444ффф0444ф) * b0444044404440444фф) % bф0444фф0444ф != bфффф0444ф) {
            b0444044404440444фф = 55;
            bфффф0444ф = b04440444фф0444ф();
        }
        try {
            this.b043Dн043Dнн043D = mediaController;
            super(looper);
        } catch (Exception e) {
            throw e;
        }
    }

    public static int b04440444фф0444ф() {
        return 42;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 6:
                if (MediaController.b0449щ044904490449щ(this.b043Dн043Dнн043D).get() != message.arg1 || MediaController.bщ0449044904490449щ(this.b043Dн043Dнн043D).get() != message.arg2) {
                    return;
                }
                if (MediaController.b04490449044904490449щ(this.b043Dн043Dнн043D).getSDKStatus() == SDKStatus.PAUSED_DUE_TO_BUFFERING) {
                    MediaController.b04490449044904490449щ(this.b043Dн043Dнн043D).transitToState(SDKStatus.PLAYING);
                    int b04440444фф0444ф = b04440444фф0444ф();
                    switch ((b04440444фф0444ф * (b0444ффф0444ф + b04440444фф0444ф)) % bф0444фф0444ф) {
                        case 0:
                            return;
                        default:
                            b0444044404440444фф = 24;
                            bфффф0444ф = 40;
                            return;
                    }
                }
                MediaController.bщщщщщ0449(this.b043Dн043Dнн043D, MediaController.b0449щ044904490449щ(this.b043Dн043Dнн043D).get(), SystemClock.uptimeMillis());
                this.b043Dн043Dнн043D.playbackStarted();
                return;
            case 7:
                MediaController.b0449щщщщ0449(this.b043Dн043Dнн043D, message.arg1);
                return;
            case 8:
                this.b043Dн043Dнн043D.bщщ044904490449щ(message);
                return;
            default:
                return;
        }
    }
}
