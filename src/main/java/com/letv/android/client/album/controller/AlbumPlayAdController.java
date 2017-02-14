package com.letv.android.client.album.controller;

import android.os.Handler;
import android.os.Message;
import com.letv.ads.ex.client.IVideoStatusInformer;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.ads.ex.ui.AdPlayFragmentProxy.OnPauseADListener;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.view.AlbumAdFragment;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.listener.PlayAdFragmentListener;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import java.util.HashMap;

public class AlbumPlayAdController implements PlayAdFragmentListener, OnPauseADListener {
    private AlbumPlayActivity mActivity;
    private Handler mHandler = new Handler();
    private AlbumAdFragment mPlayAdFragment;

    public AlbumPlayAdController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        this.mPlayAdFragment = new AlbumAdFragment(this.mActivity);
        this.mPlayAdFragment.setPauseAdsListener(this);
        this.mPlayAdFragment.setClientFunction(new 1(this));
        this.mPlayAdFragment.setAdListener(new 2(this));
        LeMessageManager.getInstance().registerTask(new LeMessageTask(325, new 3(this)));
    }

    public void initState() {
        if (this.mActivity.isForceFull()) {
            this.mPlayAdFragment.setAdsViewHalfFullBtnVisible(false);
        } else {
            this.mPlayAdFragment.setAdsViewHalfFullBtnVisible(true);
        }
    }

    public void clearAdFullProcessTimeout() {
        if (this.mPlayAdFragment != null) {
            this.mPlayAdFragment.clearAdFullProcessTimeout();
        }
    }

    public AdPlayFragmentProxy getAdFragment() {
        return this.mPlayAdFragment;
    }

    public void onPauseAdVisible(boolean visible) {
        if (visible) {
            LogInfo.log("zhuqiao", "pause ad show");
            if (this.mActivity.getFlow() != null) {
                AlbumPlayFlow flow = this.mActivity.getFlow();
                if (!AlbumPlayActivity.sIsShowingLongwatch) {
                    flow.mIsPauseAdIsShow = true;
                    if (UIsUtils.isLandscape(this.mActivity) && this.mActivity.getRecommendController() != null) {
                        this.mActivity.getRecommendController().hideRecommendTipView();
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        LogInfo.log("zhuqiao", "pause ad close");
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().mIsPauseAdIsShow = false;
        }
    }

    public void setADPause(boolean pause) {
        this.mPlayAdFragment.setADPause(pause);
    }

    public void stopPlayback(boolean notifyOnFinish) {
        this.mPlayAdFragment.stopPlayback(notifyOnFinish);
    }

    public void stopPlayback(boolean notifyOnFinish, boolean isBreakRetry) {
        this.mPlayAdFragment.stopPlayback(notifyOnFinish, isBreakRetry);
    }

    public void onResume() {
        this.mPlayAdFragment.onResume();
    }

    public void cancelRequestFrontAdTask() {
        this.mPlayAdFragment.cancelRequestFrontAdTask();
    }

    public void getOfflineFrontAd(int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty, boolean isVipVideo, boolean disableAvd, boolean toShowLoading, boolean isOfflineAds, boolean isRequestCacheAD) {
        this.mPlayAdFragment.getOfflineFrontAd(cid, aid, vid, mmsid, uuid, uid, vlen, py, ty, isVipVideo, disableAvd, toShowLoading, isOfflineAds, isRequestCacheAD);
    }

    public void getDemandPauseAd(int cid, long aid, long vid, String mmsid, String uuid, String uid, String vlen, String py, String ty) {
        this.mPlayAdFragment.getDemandPauseAd();
    }

    public void closePauseAd() {
        this.mPlayAdFragment.closePauseAd();
    }

    public boolean isPlaying() {
        return this.mPlayAdFragment.isPlaying();
    }

    public IVideoStatusInformer getIVideoStatusInformer() {
        return this.mPlayAdFragment.getIVideoStatusInformer();
    }

    public void setIVideoStatusInformer(IVideoStatusInformer format) {
        this.mPlayAdFragment.setIVideoStatusInformer(format);
    }

    public HashMap<String, String> getVODFrontADParameter(String uuid, String uid, String py, String ty, boolean isSupportM3U8, boolean disableAvd, boolean toShowLoading, boolean isWoOrderUser, boolean isOpenCde, boolean isPanorama, boolean isRequestCacheAD, boolean isPush, boolean isNeedProllAd, boolean isNeedMidProllAd) {
        return this.mPlayAdFragment.getVODFrontADParameter(uuid, uid, py, ty, isSupportM3U8, disableAvd, toShowLoading, isWoOrderUser, isOpenCde, isPanorama, isRequestCacheAD, isPush, isNeedProllAd, isNeedMidProllAd);
    }

    public void notifyADEvent(Message msg) {
        if (this.mActivity.getFlow() != null && this.mPlayAdFragment.getIADEventInformer() != null) {
            this.mPlayAdFragment.getIADEventInformer().notifyADEvent(msg);
        }
    }
}
