package com.letv.android.client.album.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.controller.AlbumErrorTopController.AlbumErrorFlag;
import com.letv.android.client.album.controller.ScreenProjectionController.OnPushToTvResponseCallback;
import com.letv.android.client.album.controller.ScreenProjectionController.SCREEN_PROJECTION_RESPONSE;
import com.letv.android.client.album.service.SimplePluginDownloadService;
import com.letv.android.client.album.view.BlockDialog;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.view.PlayLoadLayout;
import com.letv.android.client.commonlib.view.PlayLoadLayout.PlayLoadLayoutCallBack;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.listener.LoadLayoutFragmentListener;
import com.letv.business.flow.album.listener.LoadLayoutFragmentListener.IpErrorArea;
import com.letv.core.bean.VideoBean;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.download.manager.DownloadManager;

public class AlbumLoadController implements PlayLoadLayoutCallBack, LoadLayoutFragmentListener, OnPushToTvResponseCallback {
    private static final String UNDERSTAND = "http://m.lemall.com/products/view/pid-GWGT601006.html?cps_id=le_app_apprx_other_wbqyddtv_";
    public static boolean sShouldBlockDialog = true;
    private BlockDialog blockDialog;
    private AlbumPlayActivity mActivity;
    private Handler mHandler = new Handler();
    private View mInterputView;
    private PlayLoadLayout mLoadLayout;

    public AlbumLoadController(AlbumPlayActivity activity, FrameLayout parent) {
        this.mActivity = activity;
        this.mLoadLayout = new PlayLoadLayout(activity);
        parent.addView(this.mLoadLayout, new LayoutParams(-1, -1));
        init();
    }

    private void init() {
        this.mInterputView = this.mLoadLayout.findViewById(R.id.loadlayout_interput_click);
        this.mLoadLayout.loadingVideo(null);
        this.mLoadLayout.setCallBack(this);
        if (this.mActivity.mIsPlayingNonCopyright) {
            this.mLoadLayout.initWithNonCopyright();
        }
        this.mInterputView.setOnClickListener(new 1(this));
    }

    public void setVisibility(boolean hide) {
        this.mLoadLayout.setVisibility(hide ? 8 : 0);
    }

    public void onRequestErr() {
        if (!this.mActivity.mShouldWaitDrmPluginInstall) {
            this.mActivity.coverBlackOnVideoView(true);
            if (this.mActivity.getFlow() != null) {
                this.mActivity.getFlow().requestErr();
            }
        } else if (NetworkUtils.isNetworkAvailable()) {
            loading(R.string.plugin_drm_downloading);
            String pageId = UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage;
            SimplePluginDownloadService.launchSimplePluginDownloadService(this.mActivity, pageId);
            StatisticsUtils.statisticsActionInfo(this.mActivity, pageId, "0", "drm03", null, 2, null);
        }
    }

    public void onNetChangeErr() {
        onRequestErr();
    }

    public void onVipErr(boolean isLogin) {
    }

    public void onJumpErr() {
        if (this.mActivity.getFlow() != null) {
            AlbumPlayFlow flow = this.mActivity.getFlow();
            flow.mCdeStatusCode = -1;
            if (flow.mCurrentPlayingVideo != null) {
                VideoBean video = flow.mCurrentPlayingVideo;
                String type = video.jumptype;
                if (TextUtils.equals(type, "WEB_JUMP")) {
                    if (!TextUtils.isEmpty(video.jumpLink)) {
                        if (video.openby == 1) {
                            new LetvWebViewActivityConfig(this.mActivity).launch(video.jumpLink, true, false, 16);
                            return;
                        }
                        try {
                            this.mActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(video.jumpLink)));
                        } catch (Exception e) {
                        }
                    }
                } else if (TextUtils.equals(type, "TV_JUMP")) {
                    if (TextUtils.equals(this.mLoadLayout.getJumpBtnText(), TipUtils.getTipMessage("100057", this.mActivity.getString(R.string.dialog_understand_screen_projection)))) {
                        new LetvWebViewActivityConfig(this.mActivity).launch(TipUtils.getTipMessage("100065", UNDERSTAND), true, false, 16);
                    } else {
                        this.mActivity.getVideoController().processScreenProjection(this.mActivity, 0, this);
                    }
                }
            }
        }
    }

    public void onDemandErr() {
    }

    public void onPlayFailed() {
    }

    public void calledInError() {
    }

    public void calledInFinish() {
    }

    public void closeDlna(boolean isStopDlnaPlay) {
    }

    public void commitErrorInfo() {
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(108, this.mActivity));
    }

    public boolean isLoadingShow() {
        if (this.mLoadLayout != null) {
            return this.mLoadLayout.isLoadingShow();
        }
        return false;
    }

    public boolean isErrorTagShow() {
        if (this.mLoadLayout != null) {
            return this.mLoadLayout.isErrorTagShow();
        }
        return false;
    }

    public int getErrState() {
        if (this.mLoadLayout != null) {
            return this.mLoadLayout.getErrState();
        }
        return 0;
    }

    public void finish() {
        if (this.mLoadLayout != null) {
            this.mLoadLayout.finish();
        }
        checkInterput();
    }

    public void noPlay() {
        if (this.mLoadLayout != null) {
            this.mLoadLayout.notPlay();
        }
        checkInterput();
    }

    public void requestError(String msg, String errorCode, String btnMsg) {
        if (this.mLoadLayout != null) {
            if (TextUtils.isEmpty(msg)) {
                this.mLoadLayout.requestError();
            } else if (TextUtils.isEmpty(errorCode)) {
                this.mLoadLayout.requestError(msg, btnMsg);
            } else {
                this.mLoadLayout.requestError(msg, errorCode, btnMsg);
            }
        }
        checkInterput();
    }

    public void onLeboxErr(String msg) {
        if (this.mLoadLayout != null) {
            this.mLoadLayout.leboxError(msg);
        }
        checkInterput();
    }

    public void loading() {
        loading(true, null, false);
    }

    public void loading(int showTextResId) {
        loading(true, this.mActivity.getString(showTextResId), false);
        if (showTextResId == R.string.plugin_drm_downloading) {
            StatisticsUtils.statisticsActionInfo(this.mActivity, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, "19", "drm02", null, 1, null);
        }
    }

    public void loading(String loadingMsg) {
        loading(true, loadingMsg, false);
    }

    public void loading(boolean showText, String loadingMsg, boolean showWillPlay) {
        if (this.mLoadLayout != null) {
            this.mLoadLayout.loading(showText, loadingMsg, showWillPlay);
        }
        checkInterput();
    }

    public void jumpError(int value) {
        if (this.mLoadLayout != null) {
            LogInfo.log("zhuqiao", "jumpError");
            if (value == -1) {
                this.mLoadLayout.jumpError();
            } else {
                this.mLoadLayout.jumpError(value);
            }
        }
        checkInterput();
    }

    public void jumpError(String title, String msg, boolean showBtn) {
        if (this.mLoadLayout != null) {
            LogInfo.log("zhuqiao", "jumpError");
            this.mLoadLayout.jumpError(title, msg, showBtn);
        }
        checkInterput();
    }

    public void ipError(String msg, IpErrorArea area) {
        if (this.mLoadLayout != null) {
            this.mLoadLayout.ipError(msg, area);
        }
        checkInterput();
    }

    public void autoJumpWeb(VideoBean video) {
        if (video != null) {
            if (video.openby == 1) {
                new LetvWebViewActivityConfig(this.mActivity).launch(video.jumpLink, true, false, 16);
                return;
            }
            try {
                this.mActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(video.jumpLink)));
            } catch (Exception e) {
            }
        }
    }

    private void checkInterput() {
        if (this.mActivity != null) {
            AlbumErrorTopController topController = this.mActivity.getErrorTopController();
            if (topController != null) {
                if (isErrorTagShow()) {
                    topController.show(AlbumErrorFlag.LoadError);
                    this.mInterputView.setVisibility(0);
                    if (!(this.mActivity.getFlow() == null || this.mActivity.getFlow().mCurrentPlayingVideo == null)) {
                        topController.setTitle(this.mActivity.getFlow().mCurrentPlayingVideo.nameCn);
                    }
                } else {
                    topController.hide(AlbumErrorFlag.LoadError);
                    this.mInterputView.setVisibility(8);
                }
                if (isLoadingShow()) {
                    this.mActivity.coverBlackOnVideoView(true);
                }
            }
        }
    }

    public boolean isDownLoading() {
        if (this.mActivity.mIsPlayingNonCopyright) {
            return false;
        }
        boolean isDownLoading = DownloadManager.isHasDownloadRunning();
        LogInfo.log("zhuqiao", "isDownLoading: " + isDownLoading + "LetvSdkPlayerLibs.hasBlockDialogShow: " + sShouldBlockDialog);
        if (!isDownLoading || !sShouldBlockDialog) {
            return false;
        }
        showBlockDialog();
        return true;
    }

    private void showBlockDialog() {
        LogInfo.log("zhuqiao", "下载框弹出");
        try {
            if (this.blockDialog == null) {
                this.blockDialog = new BlockDialog(this.mActivity);
                this.blockDialog.setCanceledOnTouchOutside(false);
            }
            if (!this.blockDialog.isShowing()) {
                if (this.mActivity.getVideoController() != null) {
                    this.mActivity.getVideoController().setVisibility(8);
                }
                this.blockDialog.setCallback(new 2(this));
                this.blockDialog.setOnCancelListener(new 3(this));
                this.blockDialog.show();
                LogInfo.log("zhuqiao", "暂停缓存弹出框曝光");
                StatisticsUtils.staticticsInfoPost(this.mActivity, "19", "c68", null, 7, null, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, null, null, null, null, null);
                if (this.mActivity.getController() != null) {
                    this.mActivity.getController().pause(false);
                }
                AlbumPlayActivity.sIsBlockPause = true;
            }
        } catch (Exception e) {
            LogInfo.log("zhuqiao", "下载提示框异常: " + e.getMessage());
            e.printStackTrace();
            AlbumPlayActivity.sIsBlockPause = false;
            sShouldBlockDialog = true;
        }
    }

    public void dismissDialog() {
        if (this.blockDialog != null && this.blockDialog.isShowing()) {
            this.blockDialog.dismiss();
        }
    }

    public void onPushResponse(SCREEN_PROJECTION_RESPONSE result) {
        String titleId = "";
        String msgId = "";
        String defaultTitle = "";
        String defaultMsg = "";
        boolean showButton = false;
        if (result == SCREEN_PROJECTION_RESPONSE.PUSH_SUCCESS) {
            titleId = "100052";
            msgId = "100063";
            defaultTitle = this.mActivity.getString(R.string.screen_projection_jump);
            defaultMsg = this.mActivity.getString(R.string.dialog_screen_projection_success);
        } else {
            titleId = "100064";
            msgId = "100057";
            defaultTitle = this.mActivity.getString(R.string.dialog_screen_projection_fail);
            defaultMsg = this.mActivity.getString(R.string.dialog_understand_screen_projection);
            showButton = true;
        }
        this.mHandler.post(new 4(this, TipUtils.getTipMessage(titleId, defaultTitle), TipUtils.getTipMessage(msgId, defaultMsg), showButton));
    }
}
