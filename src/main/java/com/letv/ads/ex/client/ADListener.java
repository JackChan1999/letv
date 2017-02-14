package com.letv.ads.ex.client;

import android.os.Message;
import com.letv.adlib.sdk.types.AdElementMime;
import java.util.List;

public abstract class ADListener {
    public void handleADUrlAcquireDone(List<AdElementMime> list, List<AdElementMime> list2, long midTime, boolean needJoin) {
    }

    public void handleADCombineResult(Message message) {
    }

    public void handleADBufferDone() {
    }

    public void handleADFinish(boolean isFinishByHand) {
    }

    public void handleADMuteChange(boolean isMute) {
    }

    public void handleADStart(long time) {
    }

    public void onADVisibleEvent(int type, boolean visible) {
    }
}
