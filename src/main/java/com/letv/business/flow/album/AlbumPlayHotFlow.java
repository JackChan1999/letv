package com.letv.business.flow.album;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.letv.android.client.business.R;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.album.AlbumPlayFlow.RequestVideoPlayUrl;
import com.letv.business.flow.album.controller.AlbumFlowControllerHot;
import com.letv.business.flow.album.hot.listener.HotPlayListener;
import com.letv.business.flow.album.listener.AlbumPlayHotListener;
import com.letv.business.flow.album.listener.SimpleAlbumVipTrailListener;
import com.letv.business.flow.album.listener.SimpleLoadLayoutFragmentListener;
import com.letv.business.flow.album.listener.SimplePlayAdFragmentListener;
import com.letv.business.flow.album.listener.SimplePlayVideoFragmentListener;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils.UnicomDialogClickListener;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.RealPlayUrlInfoBean;
import com.letv.core.bean.VideoPlayerBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.RealPlayUrlInfoParser;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import java.util.Observable;

public class AlbumPlayHotFlow extends AlbumPlayFlow {
    public static boolean sAutoPlay = true;
    public static boolean sIsClickToPlay = false;
    public static boolean sIsWo3GUser;
    public AlbumPlayHotListener mListener;
    public HotPlayListener mPlayListener;
    private final String mPlayOn3GText;
    private boolean mShouldWifiToast;

    public AlbumPlayHotFlow(Context context, int launchMode, Bundle bundle) {
        super(context, launchMode, bundle);
        this.mShouldWifiToast = false;
        this.mPlayOn3GText = TipUtils.getTipMessage("100006", R.string.play_net_2g3g4g_tag);
        this.mOldNetState = NetworkUtils.getNetworkType();
        setObservable(new AlbumPlayFlowObservable());
        setVideoListener(new SimplePlayVideoFragmentListener());
        setLoadListener(new SimpleLoadLayoutFragmentListener());
        setAdListener(new SimplePlayAdFragmentListener());
        setAlbumVipTrailListener(new SimpleAlbumVipTrailListener());
    }

    public void startHot(AlbumPlayHotListener listener) {
        this.mListener = listener;
        if (listener == null) {
            throw new NullPointerException("AlbumPlayHotFlow listener is null!");
        }
        start();
    }

    public void setPlayListener(HotPlayListener playListener) {
        this.mPlayListener = playListener;
    }

    public void start() {
        final View view = this.mListener.getAttachView();
        if (view != null) {
            new RequestVideoPlayUrl() {
                protected void onSuccess(VideoPlayerBean result, DataHull hull) {
                    if (result.videoFile == null || !result.videoFile.isIpEnable) {
                        AlbumPlayHotFlow.this.mListener.onIpError(false, view.getTag(), view);
                        DataStatistics.getInstance().sendErrorInfo(AlbumPlayHotFlow.this.mContext, "0", "0", LetvErrorCode.VIDEO_FOREIGN_SHIELD, null, "ip error", null, null, null, null);
                        return;
                    }
                    AlbumPlayHotFlow.this.mCurrentPlayingVideo = result.video;
                    AlbumPlayHotFlow.this.mVideoFile = result.videoFile;
                    AlbumPlayHotFlow.this.checkWoFree(null);
                }

                protected void onError(NetworkResponseState state, DataHull hull) {
                    switch (state) {
                        case NETWORK_NOT_AVAILABLE:
                        case NETWORK_ERROR:
                            AlbumPlayHotFlow.this.mListener.onNetError(true, view.getTag(), view);
                            return;
                        case RESULT_ERROR:
                            AlbumPlayHotFlow.this.mListener.onDataError(true, view.getTag(), view);
                            return;
                        default:
                            return;
                    }
                }
            }.requestNetwork();
        }
    }

    protected void requestRealPlayUrl() {
        this.mRequestUrlController = new AlbumFlowControllerHot(this.mContext, this);
        this.mRequestUrlController.startRequestRealPlayUrl();
    }

    public void startPlayNet() {
        if (this.mPlayListener != null) {
            View view = this.mListener.getAttachView();
            if (view != null) {
                this.mPlayListener.setIsOnPreparePause(false);
                if (this.mOldNetState == 0) {
                    this.mListener.setErrorText(R.string.hot_play_error_net_null, true, view.getTag(), view);
                } else if (this.mOldNetState == 1 || (NetworkUtils.isUnicom3G(false) && sIsWo3GUser)) {
                    if (sAutoPlay) {
                        if (sIsClickToPlay) {
                            this.mPlayListener.setAutoPlay(false);
                            sIsClickToPlay = false;
                        } else {
                            this.mPlayListener.setAutoPlay(true);
                        }
                        if (this.mShouldWifiToast) {
                            UIsUtils.showToast(R.string.hot_play_wifi_toast);
                            this.mShouldWifiToast = false;
                            this.mPlayListener.setShowToast(false);
                        }
                        if (this.mPlayListener.isVideoviewNull() || this.mPlayListener.isComplete()) {
                            this.mPlayListener.playNet(this.mAlbumUrl.realUrl, this.mListener.getCurrentPlayTime());
                        } else {
                            this.mPlayListener.start(this.mListener.getCurrentPlayTime());
                        }
                        if (this.mListener.getHotSquareShareDialog() != null) {
                            hotPause();
                            return;
                        }
                        return;
                    }
                    this.mPlayListener.setPlayUri(this.mAlbumUrl.realUrl);
                    this.mPlayListener.setPlayTime(this.mListener.getCurrentPlayTime());
                    hotPause();
                } else if (this.mOldNetState != 2 && this.mOldNetState != 3) {
                } else {
                    if (sAutoPlay) {
                        if (sIsClickToPlay) {
                            this.mPlayListener.setAutoPlay(false);
                            sIsClickToPlay = false;
                        } else {
                            this.mPlayListener.setAutoPlay(true);
                        }
                        if (!this.mListener.showNetChangeDialog()) {
                            if (this.mPlayListener.isVideoviewNull() || this.mPlayListener.isComplete()) {
                                this.mPlayListener.playNet(this.mAlbumUrl.realUrl, this.mListener.getCurrentPlayTime());
                            } else {
                                this.mPlayListener.start(this.mListener.getCurrentPlayTime());
                            }
                            if (this.mListener.getHotSquareShareDialog() != null) {
                                hotPause();
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    this.mPlayListener.setPlayUri(this.mAlbumUrl.realUrl);
                    this.mPlayListener.setPlayTime(this.mListener.getCurrentPlayTime());
                    hotPause();
                }
            }
        }
    }

    public void hotPause() {
        if (this.mPlayListener != null) {
            this.mListener.setCurrentPlayTime(this.mPlayListener.getCurrentTime());
            this.mPlayListener.setIsOnPreparePause(true);
            this.mPlayListener.pause();
        }
    }

    protected void requestWifiRealUrl() {
        super.requestWifiRealUrl();
    }

    protected void getRealUrlForWo() {
        final View view = this.mListener.getAttachView();
        if (view != null) {
            new LetvRequest().setUrl(getSecurityChainUrl()).setCache(new VolleyNoCache()).setParser(new RealPlayUrlInfoParser()).setMaxRetries(2).setRequestType(RequestManner.NETWORK_ONLY).setTag(AlbumPlayFlow.REQUEST_REAL_URL_FOR_WO).setCallback(new SimpleResponse<RealPlayUrlInfoBean>() {
                public void onNetworkResponse(VolleyRequest<RealPlayUrlInfoBean> volleyRequest, RealPlayUrlInfoBean result, DataHull hull, NetworkResponseState state) {
                    if (state == NetworkResponseState.SUCCESS) {
                        AlbumPlayHotFlow.this.onAfterFetchWifiRealUrl(result, false);
                    } else {
                        AlbumPlayHotFlow.this.mListener.setErrorText(R.string.hot_play_error_net_null, true, view.getTag(), view);
                    }
                }
            }).add();
        }
    }

    protected void onAfterFetchWifiRealUrl(RealPlayUrlInfoBean result, boolean isCde) {
        final View view = this.mListener.getAttachView();
        if (view != null) {
            if (!this.mIsWo3GUser) {
                if (isCde) {
                    this.mAlbumUrl.realUrl = BaseApplication.getInstance().getCdeHelper().getPlayUrl(this.mAlbumUrl.p2pUrl, "", "");
                } else if (200 == result.code) {
                    this.mAlbumUrl.realUrl = result.realUrl;
                } else {
                    this.mListener.setErrorText(R.string.hot_play_error_net_null, true, view.getTag(), view);
                }
                startPlayNet();
            } else if (result == null) {
                this.mListener.setErrorText(R.string.hot_play_error_net_null, true, view.getTag(), view);
            } else {
                sAutoPlay = true;
                LogInfo.log("test", "real:" + this.mAlbumUrl.realUrl);
                ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).identifyWoVideoSDK(this.mContext, result.realUrl, 1, new LetvWoFlowListener() {
                    public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                        if (TextUtils.isEmpty(freeUrl)) {
                            AlbumPlayHotFlow.this.mHandler.post(new Runnable() {
                                public void run() {
                                    AlbumPlayHotFlow.this.mListener.setErrorText(R.string.hot_play_error_net_null, true, view.getTag(), view);
                                }
                            });
                            return;
                        }
                        AlbumPlayHotFlow.this.mAlbumUrl.realUrl = freeUrl;
                        AlbumPlayHotFlow.this.mHandler.post(new Runnable() {
                            public void run() {
                                UnicomWoFlowDialogUtils.showWoFreeActivatedToast(AlbumPlayHotFlow.this.mContext);
                                if (AlbumPlayHotFlow.this.mPlayListener != null) {
                                    AlbumPlayHotFlow.this.mPlayListener.setShowToast(false);
                                    if (AlbumPlayHotFlow.this.mListener.getHotSquareShareDialog() == null) {
                                        AlbumPlayHotFlow.this.mPlayListener.playNet(AlbumPlayHotFlow.this.mAlbumUrl.realUrl, AlbumPlayHotFlow.this.mListener.getCurrentPlayTime());
                                        return;
                                    }
                                    AlbumPlayHotFlow.this.mPlayListener.setPlayUri(AlbumPlayHotFlow.this.mAlbumUrl.realUrl);
                                    AlbumPlayHotFlow.this.mPlayListener.setPlayTime(AlbumPlayHotFlow.this.mListener.getCurrentPlayTime());
                                    AlbumPlayHotFlow.this.hotPause();
                                }
                            }
                        });
                    }
                });
            }
        }
    }

    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(PlayObservable.ON_USER_PRESENT, notify)) {
                this.mListener.setIsScreenOn(true);
                startPlayNet();
                LogInfo.log("zhuqiao", "解锁");
            } else if (TextUtils.equals(PlayObservable.ON_SCREEN_ON, notify)) {
                LogInfo.log("zhuqiao", "开屏");
            } else if (TextUtils.equals(PlayObservable.ON_SCREEN_OFF, notify)) {
                hotPause();
                this.mListener.setIsScreenOn(false);
                LogInfo.log("zhuqiao", "锁屏");
            }
        }
    }

    protected void onNetChange() {
        int currentState = NetworkUtils.getNetworkType();
        if (this.mOldNetState != currentState) {
            switch (currentState) {
                case 1:
                    net3gToWifi();
                    break;
                case 2:
                case 3:
                    if (this.mOldNetState != 1) {
                        if (this.mOldNetState == 0) {
                            noNetTo3g();
                            break;
                        }
                    }
                    wifiTo3g();
                    break;
                    break;
            }
            this.mOldNetState = currentState;
        }
    }

    private void wifiTo3g() {
        if (this.mListener.getAttachView() != null && this.mPlayListener != null) {
            hotPause();
            this.mPlayListener.setUrlNull();
            if (UnicomWoFlowManager.getInstance().supportWoFree()) {
                UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.mContext, new LetvWoFlowListener() {
                    public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                        AlbumPlayHotFlow.this.mIsWo3GUser = isOrder;
                        AlbumPlayHotFlow.sIsWo3GUser = isOrder;
                        boolean isNetWo = NetworkUtils.isUnicom3G(false);
                        final IWoFlowManager woFlowManager = (IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(AlbumPlayHotFlow.this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null);
                        boolean isPhoneNumNull = TextUtils.isEmpty(woFlowManager.getPhoneNum(AlbumPlayHotFlow.this.mContext));
                        if (!isOrder && isPhoneNumNull && isNetWo) {
                            new UnicomWoFlowDialogUtils().showOrderConfirmEnquireDialog(AlbumPlayHotFlow.this.mContext, new UnicomDialogClickListener() {
                                public void onConfirm() {
                                    LogInfo.log("wuxinrong", "AlbumPlayHotFlow 显示短信取号对话框");
                                    woFlowManager.showSMSVerificationDialog(AlbumPlayHotFlow.this.mContext, new LetvWoFlowListener() {
                                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                                            if (isSmsSuccess && isOrder) {
                                                LogInfo.log("wuxinrong", "AlbumPlayHotFlow 短信取号成功");
                                                LogInfo.log("wuxinrong", "AlbumPlayHotFlow 用户已订购");
                                                AlbumPlayHotFlow.this.requestVideo();
                                                return;
                                            }
                                            LogInfo.log("wuxinrong", "AlbumPlayHotFlow 用户未订购");
                                            AlbumPlayHotFlow.this.requestRealPlayUrl();
                                        }
                                    });
                                }

                                public void onCancel() {
                                    AlbumPlayHotFlow.this.mHandler.post(new Runnable() {
                                        public void run() {
                                            AlbumPlayHotFlow.this.mListener.showNetChangeDialog();
                                        }
                                    });
                                }

                                public void onResponse(boolean isShow) {
                                }
                            });
                        } else if (isOrder) {
                            AlbumPlayHotFlow.this.requestWifiRealUrl();
                        } else if (!isOrder) {
                            AlbumPlayHotFlow.this.mHandler.post(new Runnable() {
                                public void run() {
                                    if (!(TextUtils.isEmpty(AlbumPlayHotFlow.this.mAlbumUrl.realUrl) || AlbumPlayHotFlow.this.mPlayListener == null)) {
                                        AlbumPlayHotFlow.this.mPlayListener.setPlayUri(AlbumPlayHotFlow.this.mAlbumUrl.realUrl);
                                    }
                                    if (!AlbumPlayHotFlow.this.mListener.showNetChangeDialog()) {
                                        UIsUtils.showToast(AlbumPlayHotFlow.this.mPlayOn3GText);
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                this.mListener.showNetChangeDialog();
            }
        }
    }

    public void noNetTo3g() {
        hotPause();
        sAutoPlay = false;
        if (this.mListener.getAttachView() != null) {
            wifiTo3g();
        } else {
            this.mListener.resume();
        }
    }

    private void net3gToWifi() {
        if (sIsWo3GUser) {
            sIsWo3GUser = false;
            this.mIsWo3GUser = false;
            if (this.mPlayListener != null) {
                if (this.mPlayListener.isPlaying()) {
                    this.mPlayListener.pauseFor3GtoWifi();
                    UIsUtils.showToast(R.string.hot_play_wifi_toast);
                } else {
                    this.mPlayListener.setShowToast(true);
                    this.mShouldWifiToast = true;
                }
            }
            hotPause();
            requestWifiRealUrl();
            return;
        }
        boolean isToPlay;
        UIsUtils.showToast(R.string.hot_play_wifi_toast);
        if (this.mListener.getBaseNetChangeLayer() != null) {
            isToPlay = true;
        } else {
            isToPlay = false;
        }
        this.mListener.hide3gLayout();
        if (this.mPlayListener != null) {
            if (!this.mPlayListener.isPlaying()) {
                this.mPlayListener.setShowToast(true);
                this.mShouldWifiToast = true;
                if (isToPlay) {
                    this.mPlayListener.pauseToPlay(false);
                    return;
                }
                hotPause();
                if (this.mListener.getAttachView() == null) {
                    this.mListener.resume();
                }
            }
        } else if (isToPlay && this.mListener.getConvertView() != null) {
            sAutoPlay = true;
            this.mListener.initHotVideo(this.mListener.getConvertView());
        }
    }

    public void star3g() {
        showToast3g();
        PreferencesManager.getInstance().setShow3gDialog(false);
        if (this.mPlayListener == null) {
            sAutoPlay = true;
            sIsClickToPlay = true;
            this.mListener.resume();
        } else if (this.mPlayListener.isVideoviewNull() || this.mPlayListener.isComplete()) {
            this.mPlayListener.playNet(this.mAlbumUrl.realUrl, this.mListener.getCurrentPlayTime());
        } else {
            this.mPlayListener.start(this.mListener.getCurrentPlayTime());
        }
    }

    private void showToast3g() {
        if (!NetworkUtils.isNetworkAvailable() || !NetworkUtils.isMobileNetwork()) {
            return;
        }
        if ((this.mPlayListener != null && this.mPlayListener.isShow3gToast()) || this.mPlayListener == null) {
            UIsUtils.showToast(this.mPlayOn3GText);
        }
    }

    public void setPlayUuid(String playUuid) {
        if (this.mPlayInfo != null) {
            this.mPlayInfo.mUuidTimp = playUuid;
        }
    }

    public String getStreamLevel() {
        return this.mStreamLevel;
    }
}
