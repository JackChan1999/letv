package com.letv.business.flow.album.listener;

import android.os.Message;
import com.letv.ads.ex.client.IVideoStatusInformer;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import java.util.HashMap;

public class SimplePlayAdFragmentListener implements PlayAdFragmentListener {
    public void setADPause(boolean pause) {
    }

    public void stopPlayback(boolean notifyOnFinish) {
    }

    public void stopPlayback(boolean notifyOnFinish, boolean isBreakRetry) {
    }

    public boolean isPlaying() {
        return false;
    }

    public void closePauseAd() {
    }

    public void onResume() {
    }

    public AdPlayFragmentProxy getAdFragment() {
        return null;
    }

    public IVideoStatusInformer getIVideoStatusInformer() {
        return null;
    }

    public void setIVideoStatusInformer(IVideoStatusInformer format) {
    }

    public void cancelRequestFrontAdTask() {
    }

    public void getOfflineFrontAd(int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty, boolean isVipVideo, boolean disableAvd, boolean toShowLoading, boolean isOfflineAds, boolean isRequestCacheAD) {
    }

    public HashMap<String, String> getVODFrontADParameter(String uuid, String uid, String py, String ty, boolean isSupportM3U8, boolean disableAvd, boolean toShowLoading, boolean isWoOrderUser, boolean isOpenCde, boolean isPanorama, boolean isRequestCacheAD, boolean isPush, boolean isNeedProllAd, boolean isNeedMidProllAd) {
        return null;
    }

    public void getDemandPauseAd(int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty) {
    }

    public void notifyADEvent(Message msg) {
    }
}
