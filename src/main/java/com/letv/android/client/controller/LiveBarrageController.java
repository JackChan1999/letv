package com.letv.android.client.controller;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.BasePlayActivity;
import com.letv.android.client.barrage.BarrageControl;
import com.letv.android.client.barrage.BarrageControl$BarrageStatistics;
import com.letv.android.client.barrage.BarrageControl.LiveBarrageControl;
import com.letv.android.client.barrage.BarrageUtil;
import com.letv.android.client.barrage.live.LiveBarrageCallback;
import com.letv.android.client.barrage.live.LiveBarrageSentCallback;
import com.letv.android.client.barrage.widget.BarrageFragment;
import com.letv.android.client.view.LiveInterpretBarrageView;
import com.letv.business.flow.live.PlayLiveFlow;
import com.letv.core.bean.BarrageBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;

public class LiveBarrageController implements LiveBarrageCallback, BarrageControl$BarrageStatistics {
    private static final String TAG = "fornia";
    Runnable barrageGuideViewRunnable = new 4(this);
    private FragmentActivity mActivity = null;
    private TextView mBarrageButton;
    public BarrageControl mBarrageControl;
    View mBarrageGuideView;
    private ImageView mBarrageInputButton;
    private Context mContext;
    Handler mHandler = new Handler();
    private LiveInterpretBarrageView mInterpretViewBottom;
    private LiveInterpretBarrageView mInterpretViewTop;
    public LiveBarrageControl mLiveBarrageControl;
    private LiveBarrageSentCallback mLiveBarrageSentCallback;

    public LiveBarrageController(Context activity, LiveBarrageSentCallback liveBarrageSentCallback, FragmentManager fragmentManager, int containId, int danmakuEngineType) {
        this.mActivity = (FragmentActivity) activity;
        this.mLiveBarrageSentCallback = liveBarrageSentCallback;
        this.mContext = activity.getApplicationContext();
        initBarrage(fragmentManager, containId, danmakuEngineType);
        initTask();
    }

    private void initBarrage(FragmentManager fragmentManager, int containId, int danmakuEngineType) {
        LogInfo.log(TAG, "liveBarrageController initBarrage>>");
        if (this.mActivity instanceof BasePlayActivity) {
            this.mInterpretViewTop = (LiveInterpretBarrageView) this.mActivity.findViewById(R.id.live_interpret_top);
            this.mInterpretViewBottom = (LiveInterpretBarrageView) this.mActivity.findViewById(R.id.live_interpret_bottom);
            this.mBarrageGuideView = this.mActivity.findViewById(2131361909);
            this.mBarrageGuideView.setOnTouchListener(new 1(this));
        }
        this.mBarrageControl = BarrageFragment.getBarrageControl(this.mActivity, 2, this.mLiveBarrageSentCallback, danmakuEngineType);
        this.mBarrageControl.attachBarrageFragment(fragmentManager, containId, new 2(this));
        this.mBarrageControl.setBarrageStatisticsCallBack(this);
    }

    private void initTask() {
        LeMessageManager.getInstance().registerTask(new LeMessageTask(LeMessageIds.MSG_BARRAGE_ON_TOUCH, new 3(this)));
    }

    public void showInterpretBarrage(BarrageBean barrage) {
        if (!(this.mActivity instanceof BasePlayActivity) || barrage == null) {
            return;
        }
        if (barrage.position == 5) {
            LogInfo.log(TAG, "直播弹幕信息 顶部" + barrage.txt);
            this.mInterpretViewTop.updateStatus();
            this.mInterpretViewTop.showBarrage(barrage);
        } else if (barrage.position == 6) {
            LogInfo.log(TAG, "直播弹幕信息 底部" + barrage.txt);
            this.mInterpretViewBottom.updateStatus();
            this.mInterpretViewBottom.showBarrage(barrage);
        }
    }

    private void clickBarrageGuideView() {
        LogInfo.log(TAG, "clickBarrageGuideView >>>");
        if (this.mBarrageGuideView != null && this.mBarrageControl.isOpenBarrage() && this.mBarrageGuideView.getVisibility() == 0) {
            BarrageUtil.setBarrageGuideFirst(false);
            this.mBarrageGuideView.setVisibility(8);
        }
    }

    public void doFullScreen() {
        this.mBarrageControl.onFullScreen();
        if (this.mBarrageControl != null && this.mBarrageControl.isOpenBarrage()) {
            if (!this.mBarrageControl.getBarragePlayControl().isShow()) {
                this.mBarrageControl.getBarragePlayControl().showBarrage();
            }
            if (this.mBarrageControl.getBarragePlayControl().isPause()) {
                this.mBarrageControl.getBarragePlayControl().resumeBarrage();
            }
        }
        if (this.mBarrageGuideView != null && BarrageUtil.getBarrageGuideFirst() && this.mBarrageControl.isOpenBarrage()) {
            this.mBarrageGuideView.setVisibility(0);
            this.mHandler.removeCallbacks(this.barrageGuideViewRunnable);
            this.mHandler.postDelayed(this.barrageGuideViewRunnable, 5000);
        }
        showInterpretView();
    }

    public void doHalfScreen() {
        this.mBarrageControl.onHalfScreen();
        if (this.mBarrageGuideView != null) {
            this.mBarrageGuideView.setVisibility(8);
        }
        hideInterpretView();
    }

    private void hideInterpretView() {
        if (this.mActivity instanceof BasePlayActivity) {
            this.mInterpretViewTop.setVisibility(8);
            this.mInterpretViewBottom.setVisibility(8);
        }
    }

    private void showInterpretView() {
        if (this.mActivity instanceof BasePlayActivity) {
            if (this.mInterpretViewTop.isShow()) {
                this.mInterpretViewTop.setVisibility(0);
            }
            if (this.mInterpretViewBottom.isShow()) {
                this.mInterpretViewBottom.setVisibility(0);
            }
        }
    }

    public void checkBarrageOnOff() {
        LogInfo.log(TAG, "mLiveBarrageControl checkBarrageOnOff");
        if (this.mBarrageButton != null && this.mBarrageInputButton != null) {
            if (PreferencesManager.getInstance().getBarrageSwitch() && !this.mBarrageControl.isOpenBarrage()) {
                LogInfo.log(TAG, " barrage is not open  server isDanmaku true ");
                openBarrage(new 5(this));
            } else if (PreferencesManager.getInstance().getBarrageSwitch()) {
                this.mBarrageControl.getBarragePlayControl().resumeBarrage();
                this.mBarrageControl.getBarragePlayControl().showBarrage();
                if (UIsUtils.isLandscape()) {
                    this.mBarrageInputButton.setVisibility(0);
                }
                this.mLiveBarrageControl.setLiveCallBack(this);
                this.mLiveBarrageControl.showLiveBarrageContent();
                showInterpretView();
                if (!UIsUtils.isLandscape(this.mContext) && this.mBarrageControl.isOpenBarrage() && this.mBarrageControl.getBarragePlayControl().isShow()) {
                    this.mBarrageControl.getBarragePlayControl().hideBarrage();
                    hideInterpretView();
                }
            } else {
                this.mBarrageInputButton.setVisibility(8);
                if (!UIsUtils.isLandscape(this.mContext) && this.mBarrageControl.isOpenBarrage() && this.mBarrageControl.getBarragePlayControl().isShow()) {
                    this.mBarrageControl.getBarragePlayControl().hideBarrage();
                    hideInterpretView();
                }
            }
        }
    }

    public BarrageControl getBarrageControl() {
        return this.mBarrageControl;
    }

    public void setBarrageButton(TextView barrageButton, ImageView barrageInputButton) {
        LogInfo.log(TAG, "mLiveBarrageControl setBarrageButton");
        this.mBarrageButton = barrageButton;
        this.mBarrageInputButton = barrageInputButton;
    }

    public void onClickBarrage(boolean isPlaying) {
        if (this.mBarrageControl == null || this.mBarrageControl.isOpenBarrage()) {
            closeBarrage();
            hideInterpretView();
            return;
        }
        openBarrage(new 6(this, isPlaying));
    }

    @TargetApi(16)
    private void closeBarrage() {
        LogInfo.log(TAG, "liveBarrageController closeBarrage>>");
        this.mBarrageControl.closeBarrage();
        if (this.mBarrageButton != null && this.mBarrageInputButton != null) {
            if (VERSION.SDK_INT >= 16) {
                this.mBarrageButton.setBackground(this.mContext.getResources().getDrawable(2130837618));
            } else {
                this.mBarrageButton.setBackgroundDrawable(this.mContext.getResources().getDrawable(2130837618));
            }
            this.mBarrageInputButton.setVisibility(8);
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "l658", null, 2, null, PageIdConstant.fullPlayPage, null, null, null, PageIdConstant.fullPlayPage, null);
        }
    }

    @TargetApi(16)
    private void openBarrage(Runnable runnable) {
        LogInfo.log(TAG, "liveBarrageController openBarrage>>mBarrageButton+mBarrageInputButton" + this.mBarrageButton + this.mBarrageInputButton);
        this.mBarrageControl.openBarrage(runnable);
        if (this.mBarrageButton != null && this.mBarrageInputButton != null) {
            if (VERSION.SDK_INT >= 16) {
                this.mBarrageButton.setBackground(this.mContext.getResources().getDrawable(2130837620));
            } else {
                this.mBarrageButton.setBackgroundDrawable(this.mContext.getResources().getDrawable(2130837620));
            }
            this.mBarrageInputButton.setVisibility(UIsUtils.isLandscape() ? 0 : 8);
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "l658", null, 1, null, PageIdConstant.fullPlayPage, null, null, null, PageIdConstant.fullPlayPage, null);
        }
    }

    public float onGetCurrentVideoPlayTime() {
        return 0.0f;
    }

    public void onResumePlay() {
    }

    public void onLiveBarrageSent(BarrageBean text) {
    }

    public void onReceiveInterpretBarrage(BarrageBean text) {
        if (BarrageUtil.getDanmukuOfficial() && UIsUtils.isLandscape(this.mContext)) {
            showInterpretBarrage(text);
        }
    }

    public boolean isClickInterpretBarrage(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        int screenHeight = UIsUtils.getScreenHeight();
        if (this.mInterpretViewTop != null && this.mInterpretViewTop.getVisibility() == 0) {
            int topHeight = this.mInterpretViewTop.getMeasuredHeight();
            LogInfo.log(TAG, "x:" + x + "y:" + y + "sceenh:" + screenHeight + "topHeight:" + topHeight);
            if (y < ((float) topHeight)) {
                this.mInterpretViewTop.onInterpretClicked(this.mActivity);
                return true;
            }
        }
        if (this.mInterpretViewBottom != null && this.mInterpretViewBottom.getVisibility() == 0) {
            int bottomHeight = this.mInterpretViewBottom.getMeasuredHeight();
            LogInfo.log(TAG, "x:" + x + "y:" + y + "sceenh:" + screenHeight + "bottomHeight:" + bottomHeight);
            if (y > ((float) (screenHeight - bottomHeight))) {
                this.mInterpretViewBottom.onInterpretClicked(this.mActivity);
                return true;
            }
        }
        return false;
    }

    public void onSettingInterpretBarrage() {
        if (this.mActivity instanceof BasePlayActivity) {
            if (this.mInterpretViewTop != null) {
                this.mInterpretViewTop.updateStatus();
            }
            if (this.mInterpretViewBottom != null) {
                this.mInterpretViewBottom.updateStatus();
            }
        }
    }

    public String onGetCurrentPlayUrl() {
        return null;
    }

    public void onRedPackageShow() {
        statistics("19");
    }

    public void onRedPackageClick() {
        statistics("0");
    }

    public void statistics(String actionCode) {
        if (this.mActivity instanceof BasePlayActivity) {
            PlayLiveFlow liveFlow = ((PlayLiveController) ((BasePlayActivity) this.mActivity).mPlayController).getLiveFlow();
            String liveId = "";
            if (!(liveFlow == null || liveFlow.mStatisticsHelper == null)) {
                liveId = liveFlow.mStatisticsHelper.mStatisticsInfo.mLiveId;
            }
            StatisticsUtils.statisticsActionInfo(this.mContext, PageIdConstant.fullPlayPage, actionCode, "c659", null, 10, null, null, null, null, null, liveId);
        }
    }
}
