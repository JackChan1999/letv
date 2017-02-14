package com.letv.android.client.album.controller;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumGestureController.VolumnChangeStyle;
import com.letv.android.client.album.observable.AlbumGestureObservable.VolumeChangeNotify;
import com.letv.android.client.album.view.AlbumPlayFragment;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.MainActivityConfig;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.listener.PlayAdFragmentListener;
import com.letv.core.BaseApplication;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.listener.OrientationSensorListener;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.ChangeOrientationHandler;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.StaticticsName;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.VType;
import com.letv.pp.utils.NetworkUtils;

public class AlbumController {
    private AlbumPlayActivity mActivity;
    private boolean mIsBuyWo3G;
    private boolean mIsLock;
    private boolean mIsOpenVip = false;
    private int mLockOnce = -1;
    private OrientationSensorListener mOrientationSensorListener;
    private SensorEventListener mPanoramaSensorListener;
    private SensorEventListener mPanoramaSensorListenerG;
    private View mPanoramaTipView;
    private SensorManager mSensorManager;
    private UserState mUserState;

    public AlbumController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        this.mPanoramaTipView = activity.findViewById(R.id.panorama_tip);
        this.mPanoramaTipView.setOnClickListener(new 1(this));
        this.mUserState = getUserState();
    }

    public void openSensor() {
        closeSensor();
        if (this.mActivity.mIsPanoramaVideo) {
            initPanoramaSensor();
        } else if (!this.mActivity.isForceFull()) {
            initDefaultSensor();
        }
        setLock(this.mIsLock);
        lockOnce(this.mLockOnce);
    }

    private void initDefaultSensor() {
        Handler orientationHandler = new ChangeOrientationHandler(this.mActivity);
        this.mSensorManager = (SensorManager) this.mActivity.getSystemService("sensor");
        Sensor sensor = this.mSensorManager.getDefaultSensor(1);
        this.mOrientationSensorListener = new OrientationSensorListener(orientationHandler, this.mActivity);
        this.mSensorManager.registerListener(this.mOrientationSensorListener, sensor, 1);
    }

    private void initPanoramaSensor() {
        LogInfo.log("tanfulun", "albumController--initPanoramaSensor");
        this.mSensorManager = (SensorManager) this.mActivity.getSystemService("sensor");
        Sensor orientationSensorG = this.mSensorManager.getDefaultSensor(9);
        this.mPanoramaSensorListener = new 2(this);
        this.mPanoramaSensorListenerG = new 3(this);
        this.mSensorManager.registerListener(this.mPanoramaSensorListener, this.mSensorManager.getDefaultSensor(4), 1);
        this.mSensorManager.registerListener(this.mPanoramaSensorListenerG, orientationSensorG, 1);
    }

    public boolean checkPanoramaTip() {
        if (this.mActivity.mIsPanoramaVideo && PreferencesManager.getInstance().isPanoramaPlayGuideVisible()) {
            PreferencesManager.getInstance().setPanoramaPlayGuideVisible(false);
            this.mPanoramaTipView.setVisibility(0);
            return true;
        }
        this.mPanoramaTipView.setVisibility(8);
        return false;
    }

    private void hidePanoramaTip() {
        this.mPanoramaTipView.setVisibility(8);
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().start();
        }
    }

    public boolean fullBack() {
        if (!this.mActivity.isForceFull()) {
            return false;
        }
        this.mActivity.getController().back();
        return true;
    }

    public void back() {
        Exception e;
        String pageId;
        InputMethodManager manager = (InputMethodManager) this.mActivity.getSystemService("input_method");
        if (!(this.mActivity.getCurrentFocus() == null || this.mActivity.getCurrentFocus().getWindowToken() == null)) {
            manager.hideSoftInputFromWindow(this.mActivity.getCurrentFocus().getWindowToken(), 2);
        }
        if (this.mPanoramaTipView.getVisibility() == 0) {
            hidePanoramaTip();
        } else if (this.mActivity.getFlow() != null) {
            String fl;
            AlbumPlayFlow flow = this.mActivity.getFlow();
            if (flow.mFrom == 24) {
                if (!BaseApplication.getInstance().mIsMainActivityAlive) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MainActivityConfig(this.mActivity).fromFaceBook()));
                }
            } else if (flow.mFrom == 20) {
                LogInfo.log("zhuqiao", "back:" + flow.mBackToOriginalApp);
                if (flow.mBackToOriginalApp) {
                    this.mActivity.finish();
                    ActivityUtils.getInstance().removeAll();
                    return;
                }
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.putExtra("from_M", true);
                    intent.setData(Uri.parse("video://video"));
                    this.mActivity.startActivity(intent);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                this.mActivity.finish();
                return;
            }
            String userClickBackTime = "";
            long userClickBackStartTime = 0;
            if (this.mActivity.getVideoController() != null) {
                userClickBackTime = this.mActivity.getVideoController().getUserClickBackTime();
                userClickBackStartTime = this.mActivity.getVideoController().getUserClickBackStartTime();
            }
            if (TextUtils.isEmpty(userClickBackTime)) {
                userClickBackTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
                userClickBackStartTime = System.currentTimeMillis();
            }
            StringBuilder sb = new StringBuilder();
            sb.append("time=").append(userClickBackTime).append("&");
            if (((double) StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - userClickBackStartTime)) == 0.0d) {
                sb.append("ut=").append(NetworkUtils.DELIMITER_LINE).append("&");
            } else {
                sb.append("ut=").append(StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - userClickBackStartTime)).append("&");
            }
            if (flow.mLaunchMode == 3 || flow.mLaunchMode == 0) {
                sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.fullPlayPage);
            } else {
                sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.halpPlayPage);
            }
            String cid = "";
            try {
                String cid2 = flow.mCurrentPlayingVideo != null ? String.valueOf(flow.mCurrentPlayingVideo.cid) : NetworkUtils.DELIMITER_LINE;
                try {
                    DataStatistics.getInstance().sendActionInfo(this.mActivity, "0", "0", LetvUtils.getPcode(), VType.FLV_720P_3D, sb.toString(), "0", cid2 + "", null, flow.mVid + "", LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
                    cid = cid2;
                } catch (Exception e3) {
                    e2 = e3;
                    cid = cid2;
                    e2.printStackTrace();
                    finishPlayer();
                    pageId = StatisticsUtils.getPageId();
                    fl = StatisticsUtils.getFl();
                    if (!TextUtils.isEmpty(pageId)) {
                        return;
                    }
                    return;
                }
            } catch (Exception e4) {
                e2 = e4;
                e2.printStackTrace();
                finishPlayer();
                pageId = StatisticsUtils.getPageId();
                fl = StatisticsUtils.getFl();
                if (!TextUtils.isEmpty(pageId)) {
                    return;
                }
                return;
            }
            finishPlayer();
            try {
                pageId = StatisticsUtils.getPageId();
                fl = StatisticsUtils.getFl();
                if (!TextUtils.isEmpty(pageId) && !pageId.equals("053") && flow.mFrom == 9) {
                    StatisticsUtils.staticticsInfoPost(this.mActivity, "19", fl, null, -1, null, pageId, cid, null, flow.mVid + "", null, null);
                    LogInfo.LogStatistics("点播-->回看：fl=" + fl + " ,pageid=" + pageId + " ,cid=" + cid);
                }
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
    }

    public void full() {
        lockOnce(this.mActivity.getRequestedOrientation());
        UIsUtils.setScreenLandscape(this.mActivity);
        refreshLandspaceWhenLock();
        if (this.mActivity.getHalfFragment() != null && this.mActivity.getHalfFragment().getCommentController() != null && this.mActivity.getHalfFragment().getCommentController().isForceAlbumFullScreen && this.mActivity.getVideoController() != null) {
            this.mActivity.getVideoController().setControllerVisibility(0, true);
            this.mActivity.getVideoController().showVideoShotActionGuide();
        }
    }

    public void fullLock() {
        setLock(true);
        UIsUtils.setScreenLandscape(this.mActivity);
    }

    public void half() {
        lockOnce(this.mActivity.getRequestedOrientation());
        UIsUtils.setScreenPortrait(this.mActivity);
        if (!(this.mActivity.getHalfFragment() == null || this.mActivity.getHalfFragment().getCommentController() == null || !this.mActivity.getHalfFragment().getCommentController().isForceAlbumFullScreen)) {
            this.mActivity.getHalfFragment().getCommentController().resumeSoftKeyboardFragment();
        }
        String userClickBackTime = "";
        long userClickBackStartTime = 0;
        if (this.mActivity.getVideoController() != null) {
            userClickBackTime = this.mActivity.getVideoController().getUserClickBackTime();
            userClickBackStartTime = this.mActivity.getVideoController().getUserClickBackStartTime();
        }
        if (TextUtils.isEmpty(userClickBackTime)) {
            userClickBackTime = StringUtils.timeClockString("yyyyMMdd_HH:mm:ss");
            userClickBackStartTime = System.currentTimeMillis();
        }
        StringBuilder sb = new StringBuilder();
        if (userClickBackTime == null) {
            sb.append("time=").append(NetworkUtils.DELIMITER_LINE).append("&");
        } else {
            sb.append("time=").append(userClickBackTime).append("&");
        }
        if (((double) StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - userClickBackStartTime)) == 0.0d) {
            sb.append("ut=").append(NetworkUtils.DELIMITER_LINE).append("&");
        } else {
            sb.append("ut=").append(StringUtils.staticticsLoadTimeInfoFormat(System.currentTimeMillis() - userClickBackStartTime)).append("&");
        }
        sb.append(StaticticsName.STATICTICS_NAM_PAGE_ID).append(PageIdConstant.fullPlayPage);
        String cid = "";
        String vid = "";
        if (this.mActivity.getFlow() == null || this.mActivity.getFlow().mCurrentPlayingVideo == null) {
            cid = NetworkUtils.DELIMITER_LINE;
            vid = NetworkUtils.DELIMITER_LINE;
            LogInfo.LogStatistics("cid or vid is null!");
        } else {
            cid = this.mActivity.getFlow().mCurrentPlayingVideo.cid + "";
            vid = this.mActivity.getFlow().mCurrentPlayingVideo.vid + "";
        }
        DataStatistics.getInstance().sendActionInfo(this.mActivity, "0", "0", LetvUtils.getPcode(), VType.FLV_720P_3D, sb.toString(), "0", cid, null, vid, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, null);
    }

    public void pause(boolean showPauseVideo) {
        if (this.mActivity.getFlow() != null && this.mActivity.getAlbumPlayFragment() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            AlbumPlayFragment playFragment = this.mActivity.getAlbumPlayFragment();
            playFragment.pause();
            if (flow.mCurrentPlayingVideo != null && showPauseVideo && !playFragment.mIsSeekByUser) {
                VideoBean video = flow.mCurrentPlayingVideo;
                if (!this.mActivity.mIsPlayingNonCopyright && !AlbumPlayActivity.sIsShowingLongwatch && !AlbumPlayActivity.sIsBlockPause && this.mActivity.getPlayAdListener() != null) {
                    LogInfo.log("zhaosumin", "获得暂停广告");
                    AdsManagerProxy.getInstance(this.mActivity).setFromPush(flow.mIsFromPush);
                    this.mActivity.getPlayAdListener().getDemandPauseAd(video.cid, flow.mAid, flow.mVid, video.mid, flow.mPlayInfo.mUuidTimp, PreferencesManager.getInstance().getUserId(), video.duration + "", "", "0");
                }
            }
        }
    }

    public void start() {
        if (this.mActivity.getFlow() != null && this.mActivity.getPlayAdListener() != null && this.mActivity.getAlbumPlayFragment() != null && this.mActivity.getVipTrailListener() != null && !this.mActivity.getVipTrailListener().isTryLookPause()) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            PlayAdFragmentListener adListener = this.mActivity.getPlayAdListener();
            AlbumPlayFragment playFragment = this.mActivity.getAlbumPlayFragment();
            playFragment.setEnforcementPause(false);
            flow.mIsPauseAdIsShow = false;
            if (flow.mIsDownloadFile && this.mActivity.getFlow().mPlayInfo.currTime != 0) {
                playFragment.start();
            } else if ((adListener instanceof AlbumPlayAdController) && adListener.getAdFragment() != null) {
                adListener.getAdFragment().closePauseAd();
                if (!adListener.isPlaying()) {
                    if ((playFragment.getVideoView() == null || !playFragment.getVideoView().isPaused()) && this.mActivity.getLoadListener() != null) {
                        this.mActivity.getLoadListener().loading(true, null, true);
                    }
                    playFragment.setEnforcementWait(false);
                }
                adListener.setADPause(false);
                playFragment.start();
            }
        }
    }

    public void leaveAlbumPlayActivity() {
        AdPlayFragmentProxy adFragment = this.mActivity.getPlayAdListener().getAdFragment();
        if (adFragment != null) {
            adFragment.closePauseAd();
        }
        pause(false);
    }

    public void syncUserInfo() {
        LeMessageManager.getInstance().registerRxOnMainThread(106).subscribe(new 4(this));
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(105));
    }

    public void finishPlayer() {
        if (this.mActivity.mIsVR && this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(flow.mAid, flow.mVid, VideoType.Panorama, flow.mFrom, this.mActivity.mIsPlayingNonCopyright, this.mActivity.mNonCopyrightUrl)));
        }
        this.mActivity.finish();
    }

    public void setLock(boolean isLock) {
        this.mIsLock = isLock;
        if (this.mOrientationSensorListener != null) {
            this.mOrientationSensorListener.setLock(isLock);
        }
    }

    public void lockOnce(int orientation) {
        this.mLockOnce = orientation;
        if (this.mOrientationSensorListener != null) {
            this.mOrientationSensorListener.lockOnce(orientation);
        }
    }

    public boolean isLock() {
        if (this.mOrientationSensorListener != null) {
            return this.mOrientationSensorListener.isLock();
        }
        return false;
    }

    private void refreshLandspaceWhenLock() {
        if (this.mOrientationSensorListener != null) {
            this.mOrientationSensorListener.refreshLandspaceWhenLock();
        }
    }

    private UserState getUserState() {
        if (!PreferencesManager.getInstance().isLogin()) {
            return UserState.UN_LOGIN;
        }
        if (PreferencesManager.getInstance().isVip()) {
            return UserState.VIP;
        }
        return UserState.LOGIN;
    }

    public void onStop() {
        this.mUserState = getUserState();
    }

    public void closeSensor() {
        if (this.mSensorManager != null) {
            if (this.mOrientationSensorListener != null) {
                this.mSensorManager.unregisterListener(this.mOrientationSensorListener);
            }
            if (this.mPanoramaSensorListener != null) {
                this.mSensorManager.unregisterListener(this.mPanoramaSensorListener);
            }
            if (this.mPanoramaSensorListenerG != null) {
                this.mSensorManager.unregisterListener(this.mPanoramaSensorListenerG);
            }
        }
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 82 && this.mActivity.getGestureController() != null) {
            int volNum = this.mActivity.getGestureController().getCurSoundVolume();
            boolean isUp = false;
            if (keyCode == 24) {
                volNum++;
                isUp = true;
            } else if (keyCode == 25) {
                volNum--;
                isUp = false;
            }
            this.mActivity.getVideoController().updateVolume(new VolumeChangeNotify(this.mActivity.getGestureController().getMaxSoundVolume(), volNum, isUp ? VolumnChangeStyle.UP : VolumnChangeStyle.DOWN));
            if (this.mActivity.getPlayAdListener() != null && this.mActivity.getPlayAdListener().getAdFragment() != null) {
                this.mActivity.getPlayAdListener().getAdFragment().setMuteViewStatus(volNum);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 0 && requestCode == 17) {
            onAfterPay(resultCode);
        }
    }

    private void onAfterPay(int resultCode) {
        if (this.mActivity.getLoadListener() != null) {
            this.mActivity.getLoadListener().loading();
        }
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            if (resultCode == 257) {
                flow.addPlayInfo("重走播放流程", "支付成功");
                flow.retryPlay(true, false);
            } else if (this.mActivity.getVipTrailListener() != null && flow.mCurrentPlayingVideo != null && flow.mCurrentPlayingVideo.needPay()) {
                if (PreferencesManager.getInstance().isLogin() && !PreferencesManager.getInstance().isVip()) {
                    this.mActivity.getVipTrailListener().setStateForStartByHasLogined(true);
                }
                this.mActivity.getVipTrailListener().checkVipTrailIsStateEnd();
            }
        }
    }

    public boolean onResume() {
        if (this.mActivity.getFlow() == null) {
            return false;
        }
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (this.mIsOpenVip) {
            if (getUserState() == UserState.VIP || isUserStateChanged()) {
                flow.addPlayInfo("重走播放流程", "用户状态变化");
                flow.retryPlay(true, false);
                return true;
            }
            this.mIsOpenVip = false;
            return false;
        } else if (isUserStateChanged()) {
            flow.addPlayInfo("重走播放流程", "用户状态变化");
            flow.retryPlay(true, false);
            return true;
        } else if (!this.mIsBuyWo3G) {
            return false;
        } else {
            flow.addPlayInfo("重走播放流程", "购买免流量服务");
            flow.retryPlay(true, false);
            this.mIsBuyWo3G = false;
            return true;
        }
    }

    public void setIsOpenVip(boolean isOpenVip) {
        this.mIsOpenVip = isOpenVip;
    }

    public boolean isUserStateChanged() {
        return this.mUserState != getUserState();
    }

    public void setIsBuyWo3G(boolean isBuyWo3G) {
        this.mIsBuyWo3G = isBuyWo3G;
    }

    private void changeToStream1080p() {
    }
}
