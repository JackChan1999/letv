package com.letv.business.flow.album;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import cn.com.iresearch.vvtracker.IRVideo;
import com.letv.adlib.sdk.types.AdElementMime;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.ads.ex.utils.AdMapKey;
import com.letv.ads.ex.utils.PlayConstantUtils.PFConstant;
import com.letv.android.client.business.R;
import com.letv.android.wo.ex.IWoFlowManager;
import com.letv.android.wo.ex.WoInterface.LetvWoFlowListener;
import com.letv.business.flow.album.AlbumPlayBaseFlow.PlayErrorState;
import com.letv.business.flow.album.AlbumPlayFlowObservable.LocalVideoSubtitlesPath;
import com.letv.business.flow.album.AlbumPlayFlowObservable.PlayErrorCodeNotify;
import com.letv.business.flow.album.AlbumPlayFlowObservable.RequestCombineParams;
import com.letv.business.flow.album.AlbumPlayFlowObservable.VideoTitleNotify;
import com.letv.business.flow.album.controller.AlbumFlowControllerCombine;
import com.letv.business.flow.album.controller.AlbumFlowControllerSeparate;
import com.letv.business.flow.album.listener.AlbumPlayFlowListener;
import com.letv.business.flow.album.listener.AlbumVipTrailListener;
import com.letv.business.flow.album.listener.AlbumVipTrailListener.VipTrailErrorState;
import com.letv.business.flow.album.listener.LoadLayoutFragmentListener;
import com.letv.business.flow.album.listener.LoadLayoutFragmentListener.IpErrorArea;
import com.letv.business.flow.album.listener.PlayAdFragmentListener;
import com.letv.business.flow.album.listener.PlayVideoFragmentListener;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.business.flow.statistics.PlayStatisticsUtils;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils.UnicomDialogClickListener;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.utils.NativeInfos;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.audiotrack.AudioTrackManager;
import com.letv.core.bean.AlbumPayInfoBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.bean.LanguageSettings;
import com.letv.core.bean.PlayRecord;
import com.letv.core.bean.PlayRecord.PlayDeviceFrom;
import com.letv.core.bean.RealPlayUrlInfoBean;
import com.letv.core.bean.ShackVideoInfoListBean.ShackVideoInfoBean;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.TimestampBean.FetchServerTimeListener;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.bean.VideoPlayerBean;
import com.letv.core.bean.VideoPlayerBean.AdInfoBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.constant.ShareConstant;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyRequest.RequestPriority;
import com.letv.core.network.volley.VolleyRequestQueue.RequestFilter;
import com.letv.core.network.volley.VolleyResponse.CacheResponseState;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDbCache;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.RealPlayUrlInfoParser;
import com.letv.core.parser.VideoPlayerParser;
import com.letv.core.subtitle.manager.SubtitleRenderManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.PlayUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.bean.StatisticsPlayInfo;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataUtils;
import com.letv.download.manager.DownloadManager;
import com.letv.download.manager.DownloadSubtitleManager;
import com.letv.marlindrm.manager.DrmManager;
import com.letv.mobile.lebox.LeboxApiManager;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;
import com.letv.plugin.pluginconfig.commom.JarConstant;
import com.letv.plugin.pluginloader.apk.pm.ApkManager;
import com.letv.plugin.pluginloader.common.Constant;
import com.letv.plugin.pluginloader.loader.JarLoader;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class AlbumPlayFlow extends AlbumPlayBaseFlow implements AlbumPlayFlowListener, Observer {
    public static final String ALBUM_FLOW_TAG = "albumFlowTag_";
    public static final int MAX_RETRY_TIMES = 2;
    public static final String REQUEST_CHECK_PLAY_RECORD = "albumFlowTag_checkPlayRecord";
    public static final String REQUEST_COMBINES = "albumFlowTag_combines";
    public static final String REQUEST_COMBINES_GLOBAL = "albumFlowTag_combines_global";
    public static final String REQUEST_DOWNLOAD = "albumFlowTag_download";
    public static final String REQUEST_REAL_URL = "albumFlowTag_requestRealurl";
    public static final String REQUEST_REAL_URL_BY_CDE = "albumFlowTag_requestRealurlByCde";
    public static final String REQUEST_REAL_URL_FOR_WO = "albumFlowTag_requestRealurlForWo";
    public static final String REQUEST_VIDEO_CACHE = "albumFlowTag_requestVideoCache";
    public static final String REQUEST_VIDEO_PLAY_URL = "albumFlowTag_videoPlayUrl";
    public static final String REQUEST_WIFI_REAL_URL = "albumFlowTag_requestWifiRealUrl";
    public PlayAdFragmentListener mAdListener;
    public FlowStyle mFlowStyle = FlowStyle.Album;
    public LoadLayoutFragmentListener mLoadListener;
    public AlbumPlayFlowObservable mObservable;
    public AlbumVipTrailListener mTrailListener;
    public VideoChangeListener mVideoChangeListener;
    public PlayVideoFragmentListener mVideoListener;

    private interface Callback {
        void callback();
    }

    public enum FlowStyle {
        Album,
        Topic
    }

    public class RequestVideoPlayUrl {
        private boolean mIsFirstRequestCache = true;
        protected boolean mIsLocalSuccess = false;

        public void startRequestCache() {
            if (AlbumPlayFlow.this.mVid == 0) {
                this.mIsFirstRequestCache = false;
                AlbumPlayFlow.this.addPlayInfo("没有vid，先请求合并接口再检查是否缓存", "");
                requestNetwork();
                return;
            }
            AlbumPlayFlow.this.addPlayInfo("检查视频是否已缓存开始", "");
            AlbumPlayBaseFlow.sRequestLocalConsumetime = System.currentTimeMillis();
            requestCache();
            this.mIsLocalSuccess = AlbumPlayFlow.this.mIsDownloadFile;
            if (!AlbumPlayFlow.this.mIsDownloadFile && AlbumPlayFlow.this.mLaunchMode == 3) {
                AlbumPlayFlow.this.mLaunchMode = 1;
            }
            if (AlbumPlayFlow.this.isVideoDownloaded()) {
                AlbumPlayFlow.this.addPlayInfo("检查视频已缓存结束：有缓存", "");
                AlbumPlayFlow.this.startPlayLocal();
            } else {
                AlbumPlayFlow.this.addPlayInfo("检查视频已缓存结束：无缓存", "");
            }
            LogInfo.log("zhuqiao_time", "****************获取本地视频所消耗时间" + (System.currentTimeMillis() - AlbumPlayBaseFlow.sRequestLocalConsumetime) + "毫秒****************");
            AlbumPlayInfo albumPlayInfo = AlbumPlayFlow.this.mPlayInfo;
            albumPlayInfo.mType3 += System.currentTimeMillis() - AlbumPlayBaseFlow.sRequestLocalConsumetime;
            if (!AlbumPlayFlow.this.mIsDownloadFile) {
                if (this.mIsFirstRequestCache) {
                    requestNetwork();
                } else {
                    AlbumPlayFlow.this.checkWoFree(null);
                }
            }
            AlbumPlayFlow.this.updatePlayDataStatistics("init", -1);
        }

        private void requestCache() {
            AlbumPlayFlow.this.requestLocalVideo();
            if (AlbumPlayFlow.this.mCurrentPlayingVideo != null) {
                if (!BaseTypeUtils.isMapEmpty(AlbumPlayFlow.this.mVideos)) {
                    AlbumPlayFlow.this.mAid = AlbumPlayFlow.this.mCurrentPlayingVideo.pid;
                    AlbumPlayFlow.this.mVid = AlbumPlayFlow.this.mCurrentPlayingVideo.vid;
                    AlbumPlayFlow.this.mCid = (long) AlbumPlayFlow.this.mCurrentPlayingVideo.cid;
                }
                if (AlbumPlayFlow.this.mPlayRecord != null) {
                    AlbumPlayFlow.this.mPlayRecord.albumId = (int) AlbumPlayFlow.this.mAid;
                }
                if (AlbumPlayFlow.this.mIsDownloadFile) {
                    AlbumPlayFlow.this.handlerFloatBall();
                }
                if (AlbumPlayFlow.this.mPlayRecord == null && !AlbumPlayFlow.this.isUseDoublePlayerAndChangeStream()) {
                    AlbumPlayFlow.this.mLoadListener.loading(true, null, true);
                }
                AlbumPlayFlow.this.createPlayRecord();
                if (AlbumPlayFlow.this.mPlayRecord != null) {
                    AlbumPlayFlow.this.mPlayRecord.totalDuration = AlbumPlayFlow.this.mCurrentPlayingVideo.duration;
                    AlbumPlayFlow.this.mPlayInfo.videoTotalTime = AlbumPlayFlow.this.mPlayRecord.totalDuration * 1000;
                }
            }
        }

        public void requestNetwork() {
            if (AlbumPlayFlow.this.mCid == 0 && AlbumPlayFlow.this.mAid == 0 && AlbumPlayFlow.this.mZid == 0 && AlbumPlayFlow.this.mVid == 0) {
                AlbumPlayFlow.this.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), DialogMsgConstantId.FIFTEEN_ZERO_FIVE_CONSTANT, "");
                AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify(DialogMsgConstantId.FIFTEEN_ZERO_FIVE_CONSTANT, true));
                AlbumPlayFlow.this.addPlayInfo("请求合并接口结束：失败，播放参数错误，无id", "");
                return;
            }
            if (!this.mIsFirstRequestCache) {
                AlbumPlayBaseFlow.sRequestLocalConsumetime = System.currentTimeMillis();
            }
            AlbumPlayFlow.this.mPlayInfo.mType19 = System.currentTimeMillis();
            LogInfo.log("zhaosumin", "切换合并接口 : mCid == " + AlbumPlayFlow.this.mCid + " mAid == " + AlbumPlayFlow.this.mAid + " mZid == " + AlbumPlayFlow.this.mZid + "  mVid == " + AlbumPlayFlow.this.mVid);
            String uid = PreferencesManager.getInstance().getUserId();
            String tm = String.valueOf(TimestampBean.getTm().getCurServerTime());
            Map<String, String> adMap = null;
            if (!(AlbumPlayFlow.this.mAdListener == null || AlbumPlayFlow.this.needSkipAd() || AlbumPlayFlow.this.mIsNonCopyright)) {
                adMap = AlbumPlayFlow.this.mAdListener.getVODFrontADParameter(AlbumPlayFlow.this.mPlayInfo.mUuidTimp, uid, "", "0", BaseApplication.getInstance().getPinjie(), false, false, AlbumPlayFlow.this.mIsWo3GUser, PreferencesManager.getInstance().getUtp(), AlbumPlayFlow.this.mVideoType == VideoType.Panorama, AlbumPlayFlow.this.mIsRetry, AlbumPlayFlow.this.mIsFromPush, AlbumPlayFlow.this.mNeedPlayAd, !AlbumPlayFlow.this.mIsMidAdPlayed);
            }
            String vf = BaseApplication.getInstance().getVideoFormat();
            if (AlbumPlayFlow.this.mVideoType == VideoType.Dolby) {
                vf = "no";
            }
            String url = MediaAssetApi.getInstance().getVideoPlayUrl(AlbumPlayFlow.this.mCid + "", AlbumPlayFlow.this.mAid + "", AlbumPlayFlow.this.mZid + "", AlbumPlayFlow.this.mVid + "", uid, vf, "0", tm, AlbumPlayFlow.this.mPlayInfo.mUuidTimp, adMap);
            AlbumPlayFlow.this.addPlayInfo("请求合并接口开始", url);
            AlbumPlayFlow.this.addPlayInfo("请求合并接口token", PreferencesManager.getInstance().getSso_tk());
            LogInfo.log("jc666", "从开始到请求合并接口之前所用时间：" + (System.currentTimeMillis() - AlbumPlayFlow.this.mPlayInfo.mTotalConsumeTime));
            AlbumPlayFlow.this.mPlayInfo.mType19 = System.currentTimeMillis() - AlbumPlayFlow.this.mPlayInfo.mType19;
            new LetvRequest().setRequestType(RequestManner.NETWORK_ONLY).setUrl(url).setMaxRetries(2).setPriority(RequestPriority.IMMEDIATE).setCache(new VolleyNoCache()).setParser(new VideoPlayerParser()).setTag(AlbumPlayFlow.REQUEST_VIDEO_PLAY_URL).setShowTag(true).setCallback(new SimpleResponse<VideoPlayerBean>() {
                public void onNetworkResponse(VolleyRequest<VideoPlayerBean> request, VideoPlayerBean result, DataHull hull, NetworkResponseState state) {
                    AlbumPlayFlow.this.mPlayInfo.mRetryNum = request.getRetryPolicy().getRetries();
                    AlbumPlayFlow.this.mPlayInfo.type7 = request.getRequestNetConsumeTime();
                    AlbumPlayFlow.this.mPlayInfo.type7_1 = request.getClientConsumeTime();
                    AlbumPlayFlow.this.addPlayInfo("合并接口耗时", "接口耗时：" + AlbumPlayFlow.this.mPlayInfo.type7 + ";客户端耗时：" + AlbumPlayFlow.this.mPlayInfo.type7_1);
                    if (state == NetworkResponseState.SUCCESS) {
                        AlbumPlayFlow.this.addPlayInfo("请求合并接口结束：成功", "");
                        AlbumPlayFlow.this.mPlayInfo.mType21 = System.currentTimeMillis();
                        RequestVideoPlayUrl.this.onSuccess(result, hull);
                        LogInfo.log("combination", "请求合并接口结束");
                        return;
                    }
                    AlbumPlayFlow.this.addPlayInfo("请求合并接口结束：失败", "state = " + state + "");
                    if (AlbumPlayFlow.this.isUseDoublePlayerAndChangeStream()) {
                        AlbumPlayFlow.this.mVideoListener.onChangeStreamError();
                    } else {
                        RequestVideoPlayUrl.this.onError(state, hull);
                    }
                }
            }).add();
        }

        protected void onSuccess(VideoPlayerBean result, DataHull hull) {
            if (result.video == null) {
                AlbumPlayFlow.this.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "1506", "");
                AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("1506", true));
            } else if (checkDrm(result)) {
                AlbumPlayFlow.this.mHasFetchDataSuccess = true;
                AlbumPlayFlow.this.mAdInfo = result.adInfo;
                setWatched();
                initVideo(result.video);
                initVideoFile(result.videoFile);
                checkCanPlay(result.payInfo, result.videoFile, hull);
                AlbumPlayFlow.this.mObservable.notifyObservers(AlbumPlayFlowObservable.REFRESH_DATA_AFTER_REQUEST_VIDEO_URL);
            }
        }

        private boolean checkDrm(VideoPlayerBean result) {
            if (!TextUtils.equals(result.video.drmFlag, "1")) {
                return true;
            }
            AlbumPlayFlow.this.mVideoType = VideoType.Drm;
            if (ApkManager.getInstance().getPluginInstallState(Constant.DRM_LIBWASABIJNI) != 1) {
                AlbumPlayFlow.this.mVideoListener.checkDrmPlugin();
                return false;
            } else if (BaseApplication.getInstance().mHasLoadDrmSo) {
                return true;
            } else {
                AlbumPlayFlow.this.mVideoListener.loadDrmPlugin();
                return true;
            }
        }

        protected void initVideo(VideoBean video) {
            AlbumPlayFlow.this.setVideoBean(video);
            if (AlbumPlayFlow.this.mCurrentPlayingVideo != null) {
                AlbumPlayFlow.this.mPlayInfo.videoTotalTime = AlbumPlayFlow.this.mCurrentPlayingVideo.duration * 1000;
                AlbumPlayFlow.this.mAid = AlbumPlayFlow.this.mCurrentPlayingVideo.pid;
                AlbumPlayFlow.this.mVid = AlbumPlayFlow.this.mCurrentPlayingVideo.vid;
                AlbumPlayFlow.this.mCid = (long) AlbumPlayFlow.this.mCurrentPlayingVideo.cid;
                if (AlbumPlayFlow.this.mPlayRecord != null) {
                    AlbumPlayFlow.this.mPlayRecord.albumId = (int) AlbumPlayFlow.this.mAid;
                }
                AlbumPlayFlow.this.mObservable.notifyObservers(new VideoTitleNotify(AlbumPlayFlow.this.getTitle(AlbumPlayFlow.this.mCurrentPlayingVideo)));
                if (AlbumPlayFlow.this.mIsDownloadFile) {
                    AlbumPlayFlow.this.handlerFloatBall();
                }
                if (!this.mIsLocalSuccess) {
                    AlbumPlayFlow.this.createPlayRecord();
                    if (AlbumPlayFlow.this.mPlayRecord != null) {
                        AlbumPlayFlow.this.mPlayRecord.totalDuration = AlbumPlayFlow.this.mCurrentPlayingVideo.duration;
                        AlbumPlayFlow.this.mPlayInfo.videoTotalTime = AlbumPlayFlow.this.mPlayRecord.totalDuration * 1000;
                    }
                }
            }
        }

        private void setWatched() {
            new Thread() {
                public void run() {
                    DownloadDBBean info = DBManager.getInstance().getDownloadTrace().getTitleInFinish(AlbumPlayFlow.this.mVid);
                    if (info != null && info.isWatch == 0) {
                        info.isWatch = 1;
                        String filePath2 = info.filePath;
                        String substring = filePath2.substring(0, filePath2.lastIndexOf("/"));
                        LogInfo.log("zhuqiao", "video filepath=" + substring + "downloadinfo = " + filePath2);
                        info.filePath = substring;
                        DBManager.getInstance().getDownloadTrace().changeUserStatus(info);
                    }
                }
            }.start();
        }

        protected void initVideoFile(VideoFileBean videoFile) {
            int i = 1;
            AlbumPlayFlow.this.mVideoFile = videoFile;
            AlbumPlayFlow.this.mTrailListener.finish();
            if (!AlbumPlayFlow.this.isVideoDownloaded()) {
                AlbumPlayFlow.this.handlerFloatBall();
                AlbumPlayFlow.this.mIsDownloadFile = false;
                AlbumPlayFlow.this.mFilePath = null;
            }
            if (!AlbumPlayFlow.this.mIsInitReport && !UIsUtils.isLandscape(AlbumPlayFlow.this.mContext) && AlbumPlayFlow.this.mCurrentPlayingVideo != null) {
                AlbumPlayFlow.this.mIsInitReport = true;
                LogInfo.LogStatistics("half play show");
                StringBuilder append = new StringBuilder().append("vip=");
                if (!PreferencesManager.getInstance().isVip()) {
                    i = 0;
                }
                StatisticsUtils.statisticsActionInfo(AlbumPlayFlow.this.mContext, PageIdConstant.halpPlayPage, "19", null, null, -1, append.append(i).append("&ispay=").append(String.valueOf(AlbumPlayFlow.this.mCurrentPlayingVideo.pay)).toString(), String.valueOf(AlbumPlayFlow.this.mCurrentPlayingVideo.cid), String.valueOf(AlbumPlayFlow.this.mCurrentPlayingVideo.pid), String.valueOf(AlbumPlayFlow.this.mCurrentPlayingVideo.vid), String.valueOf(AlbumPlayFlow.this.mCurrentPlayingVideo.zid), null);
            }
        }

        protected void checkCanPlay(AlbumPayInfoBean payInfo, VideoFileBean videoFile, DataHull hull) {
            if (AlbumPlayFlow.this.mCurrentPlayingVideo != null) {
                AlbumPlayFlow.this.mPayInfo = payInfo;
                if (AlbumPlayFlow.this.mPayInfo == null) {
                    AlbumPlayFlow.this.mPayInfo = new AlbumPayInfoBean();
                    AlbumPlayFlow.this.mPayInfo.status = AlbumPlayFlow.this.mCurrentPlayingVideo.canPlay() ? 1 : 0;
                }
                if (AlbumPlayFlow.this.mCurrentPlayingVideo.canPlay()) {
                    if (videoFile.isIpEnable) {
                        if (AlbumPlayFlow.this.mCurrentPlayingVideo.pay == 1 && AlbumPlayFlow.this.mPayInfo.status == 0) {
                            AlbumPlayFlow.this.showVipTrailStart();
                        }
                        if (!this.mIsLocalSuccess || !AlbumPlayFlow.this.mIsDownloadFile) {
                            if (AlbumPlayFlow.this.mLoadListener.isLoadingShow() && !AlbumPlayFlow.this.isUseDoublePlayerAndChangeStream()) {
                                AlbumPlayFlow.this.mLoadListener.loading(true, null, true);
                            }
                            AlbumPlayFlow.this.mPlayInfo.mType21 = System.currentTimeMillis() - AlbumPlayFlow.this.mPlayInfo.mType21;
                            if (this.mIsFirstRequestCache) {
                                AlbumPlayFlow.this.checkWoFree(null);
                                return;
                            } else {
                                startRequestCache();
                                return;
                            }
                        }
                        return;
                    }
                    ipDisable(videoFile);
                } else if (AlbumPlayFlow.this.mIsNonCopyright) {
                    AlbumPlayFlow.this.mLoadListener.requestError(AlbumPlayFlow.this.mContext.getString(R.string.play_error_noncopyright), "01000", "");
                    AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("01000", true));
                    AlbumPlayFlow.this.addPlayInfo("无法播放", "无版权");
                } else {
                    authentication();
                }
            }
        }

        private void authentication() {
            if (AlbumPlayFlow.this.mCurrentPlayingVideo.needJump()) {
                String type = AlbumPlayFlow.this.mCurrentPlayingVideo.jumptype;
                AlbumPlayFlow.this.addPlayInfo("鉴权失败，需要外跳，jumpType", type);
                if (TextUtils.equals(type, "WEB")) {
                    AlbumPlayFlow.this.mLoadListener.jumpError(2);
                    return;
                } else if (TextUtils.equals(type, "WEB_JUMP")) {
                    AlbumPlayFlow.this.mLoadListener.jumpError(1);
                    return;
                } else if (TextUtils.equals(type, "TV_JUMP")) {
                    AlbumPlayFlow.this.mLoadListener.jumpError(TipUtils.getTipMessage("100052", AlbumPlayFlow.this.mContext.getString(R.string.screen_projection_jump)), TipUtils.getTipMessage("100055", AlbumPlayFlow.this.mContext.getString(R.string.screen_projection)), true);
                    return;
                } else if (TextUtils.equals(type, "NO_COPYRIGHT") || TextUtils.isEmpty(type)) {
                    AlbumPlayFlow.this.mLoadListener.jumpError(0);
                    AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("", false));
                    return;
                } else {
                    return;
                }
            }
            AlbumPlayFlow.this.addPlayInfo("鉴权失败", "下线视频");
            AlbumPlayFlow.this.mLoadListener.jumpError(0);
            AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("", false));
        }

        private void ipDisable(VideoFileBean videoFile) {
            AlbumPlayFlow.this.addPlayInfo("ip被屏蔽", "");
            if (TextUtils.equals(LetvUtils.COUNTRY_CHINA, videoFile.country)) {
                if (AlbumPlayFlow.this.mIsNonCopyright) {
                    AlbumPlayFlow.this.mLoadListener.ipError(AlbumPlayFlow.this.mContext.getString(R.string.cn_ip_error), IpErrorArea.CN);
                } else {
                    AlbumPlayFlow.this.mLoadListener.ipError(TipUtils.getTipMessage("100019", R.string.cn_ip_error), IpErrorArea.CN);
                }
                AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0012", true));
            } else if (TextUtils.equals(LetvUtils.COUNTRY_HONGKONG, videoFile.country)) {
                if (AlbumPlayFlow.this.mIsNonCopyright) {
                    msgArr = AlbumPlayFlow.this.mContext.getString(R.string.hk_ip_error).split(ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER_OLD);
                    if (!BaseTypeUtils.isArrayEmpty(msgArr)) {
                        AlbumPlayFlow.this.mLoadListener.ipError(msgArr[0], IpErrorArea.HK);
                    }
                } else {
                    AlbumPlayFlow.this.mLoadListener.ipError(TipUtils.getTipMessage("100030", R.string.hk_ip_error), IpErrorArea.HK);
                }
                AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify(LetvErrorCode.REQUEST_CANPLAY_ERROR, true));
            } else {
                if (AlbumPlayFlow.this.mIsNonCopyright) {
                    msgArr = AlbumPlayFlow.this.mContext.getString(R.string.other_ip_error).split(ShareConstant.SHARE_CUSTOM_TEXT_DIVIDER_OLD);
                    if (!BaseTypeUtils.isArrayEmpty(msgArr)) {
                        AlbumPlayFlow.this.mLoadListener.ipError(msgArr[0], IpErrorArea.OTHER);
                    }
                } else {
                    AlbumPlayFlow.this.mLoadListener.ipError(TipUtils.getTipMessage("100031", R.string.other_ip_error), IpErrorArea.OTHER);
                }
                AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify(LetvErrorCode.VIDEO_FOREIGN_SHIELD, true));
            }
        }

        protected void onError(NetworkResponseState state, DataHull hull) {
            if (state != NetworkResponseState.NETWORK_ERROR || !AlbumPlayFlow.this.mIsDownloadFile) {
                if (state == NetworkResponseState.NETWORK_ERROR || state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                    AlbumPlayFlow.this.requestLocalVideo();
                }
                if (!AlbumPlayFlow.this.mIsDownloadFile) {
                    AlbumPlayFlow.this.mTrailListener.hide();
                    String subErrorCode = StatisticsUtils.getSubErroCode(state, hull);
                    if (state == NetworkResponseState.RESULT_ERROR) {
                        AlbumPlayFlow.this.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                        AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", true, subErrorCode));
                        AlbumPlayFlow.this.staticticsErrorInfo(AlbumPlayFlow.this.mContext, "1008", "playerError", 0, -1);
                    } else if (state == NetworkResponseState.NETWORK_ERROR) {
                        AlbumPlayFlow.this.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                        AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", true, subErrorCode));
                    } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                        AlbumPlayFlow.this.mLoadListener.requestError("", "", "");
                        AlbumPlayFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", false, subErrorCode));
                    }
                    AlbumPlayFlow.this.mErrorState = PlayErrorState.VIDEO_INFO_API_ERROR;
                }
            }
        }
    }

    public interface VideoChangeListener {
        void onChange();
    }

    public AlbumPlayFlow(Context context, int launchMode, Bundle bundle) {
        super(context, launchMode, bundle);
    }

    public void setObservable(AlbumPlayFlowObservable flowObservable) {
        this.mObservable = flowObservable;
    }

    public void setVideoListener(PlayVideoFragmentListener videoListener) {
        this.mVideoListener = videoListener;
    }

    public void setLoadListener(LoadLayoutFragmentListener loadListener) {
        this.mLoadListener = loadListener;
    }

    public void setAdListener(PlayAdFragmentListener adListener) {
        this.mAdListener = adListener;
    }

    public void setAlbumVipTrailListener(AlbumVipTrailListener listener) {
        this.mTrailListener = listener;
    }

    public void start() {
        if (this.mContext == null || this.mObservable == null || this.mVideoListener == null || this.mLoadListener == null || this.mAdListener == null || this.mTrailListener == null) {
            throw new NullPointerException("main flow param is null!");
        }
        this.mIsSkip = PreferencesManager.getInstance().isSkip();
        this.mPlayLevel = PreferencesManager.getInstance().getPlayLevel();
        this.mDownloadHd = PreferencesManager.getInstance().isDownloadHd();
        this.mPlayInfo.mReplayType = 1;
        this.mHardDecode = LetvMediaPlayerManager.getInstance().getHardDecodeState() == 1;
        addPlayInfo("启动播放", JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START);
        this.mIsLaunchPlay = true;
        addPlayInfo("aid:" + this.mAid + ",vid:" + this.mVid + ",cid:" + this.mCid + ",zid:" + this.mZid + ",启动模式:" + this.mLaunchMode, "");
        this.mLoadListener.loading();
        startLoadingData();
    }

    protected void startLoadingData() {
        if (!this.mShouldWaitDrmPluginInstall) {
            sRequestRealUrlConsumetime = System.currentTimeMillis();
            statisticsLaunch(true);
            if (this.mLaunchMode == 1) {
                checkPlayRecord(true);
            } else if (this.mLaunchMode == 2) {
                checkPlayRecord(false);
            } else if (this.mLaunchMode == 3) {
                requestDownload();
            } else if (this.mLaunchMode != 4) {
                boolean z;
                AudioTrackManager instance = AudioTrackManager.getInstance();
                int i = this.mPlayLevel;
                if (this.mVideoType == VideoType.Dolby) {
                    z = true;
                } else {
                    z = false;
                }
                instance.obtainId(null, null, i, z);
                if (!TextUtils.isEmpty(this.mAlbumUrl.realUrl)) {
                    File localVideo = new File(this.mAlbumUrl.realUrl);
                    if (localVideo.exists()) {
                        String fileName = localVideo.getName();
                        this.mObservable.notifyObservers(new VideoTitleNotify(fileName.substring(0, fileName.indexOf("."))));
                    } else {
                        int start = this.mAlbumUrl.realUrl.lastIndexOf("/");
                        int end = this.mAlbumUrl.realUrl.lastIndexOf(".");
                        if (!(start == -1 || end == -1 || start >= end)) {
                            this.mObservable.notifyObservers(new VideoTitleNotify(this.mAlbumUrl.realUrl.substring(start + 1, end)));
                        }
                    }
                    this.mIsScanVideo = true;
                    this.mVideoListener.initVideoView(true, false);
                    this.mVideoListener.startPlayLocal(this.mAlbumUrl.realUrl, (long) (((int) this.mSeek) * 1000), false);
                    this.mLocalPath = this.mAlbumUrl.realUrl;
                    this.mLocalSeek = this.mSeek;
                    updatePlayDataStatistics("init", -1);
                }
            } else if (this.mBoxBean != null) {
                playLebox(this.mBoxBean);
                this.mObservable.notifyObservers(new RequestCombineParams("", "", "", ""));
            }
        }
    }

    protected void checkPlayRecord(final boolean isAlbum) {
        addPlayInfo("检查播放记录-开始", "");
        sRequestRecodeConsumetime = System.currentTimeMillis();
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setTag(REQUEST_CHECK_PLAY_RECORD).setCache(new VolleyDbCache<PlayRecord>() {
            public PlayRecord get(VolleyRequest<?> volleyRequest) {
                if (AlbumPlayFlow.this.mIsNonCopyright) {
                    return null;
                }
                DownloadDBBean info = DBManager.getInstance().getDownloadTrace().getTitleInFinish(AlbumPlayFlow.this.mVid);
                if (info != null) {
                    AlbumPlayFlow.this.mDownloadDBBean = info;
                    AlbumPlayFlow.this.mFilePath = info.filePath;
                    AlbumPlayFlow.this.mIsDownloadFile = true;
                    AlbumPlayFlow.this.mCurrDownloadFileIsHd = info.isHd == 1;
                    AlbumPlayFlow.this.mDownloadStreamLevel = info.isHd;
                }
                if (AlbumPlayFlow.this.mPlayRecord != null) {
                    return AlbumPlayFlow.this.mPlayRecord;
                }
                if (!isAlbum) {
                    return (PlayRecord) LetvTools.copyBean(AlbumPlayFlow.this.mVideoListener.getPoint(0, (int) AlbumPlayFlow.this.mVid, false), PlayRecord.class);
                }
                if (AlbumPlayFlow.this.mVid > 0) {
                    return (PlayRecord) LetvTools.copyBean(AlbumPlayFlow.this.mVideoListener.getPoint(0, (int) AlbumPlayFlow.this.mVid, false), PlayRecord.class);
                }
                if (AlbumPlayFlow.this.mCurPage == 1) {
                    return (PlayRecord) LetvTools.copyBean(AlbumPlayFlow.this.mVideoListener.getPoint((int) AlbumPlayFlow.this.mAid, 0, false), PlayRecord.class);
                }
                return null;
            }

            public void add(VolleyRequest<?> volleyRequest, PlayRecord response) {
            }
        }).setCallback(new SimpleResponse<PlayRecord>() {
            public void onCacheResponse(VolleyRequest<PlayRecord> volleyRequest, PlayRecord result, DataHull hull, CacheResponseState state) {
                LogInfo.log("zhuqiao_time", "****************获取播放记录所消耗时间" + (System.currentTimeMillis() - AlbumPlayBaseFlow.sRequestRecodeConsumetime) + "毫秒****************");
                AlbumPlayBaseFlow.sRequestRecodeConsumetime = System.currentTimeMillis() - AlbumPlayBaseFlow.sRequestRecodeConsumetime;
                AlbumPlayInfo albumPlayInfo = AlbumPlayFlow.this.mPlayInfo;
                albumPlayInfo.mType3 += AlbumPlayBaseFlow.sRequestRecodeConsumetime;
                LogInfo.log("zhuqiao", "播放记录 state:" + state);
                if (state == CacheResponseState.SUCCESS) {
                    AlbumPlayFlow.this.addPlayInfo("检查播放记录-结束：成功", "");
                } else {
                    AlbumPlayFlow.this.addPlayInfo("检查播放记录-结束：失败", "");
                }
                AlbumPlayFlow.this.onRequestPlayRecord(result);
            }
        }).add();
    }

    protected LanguageSettings queryLanguageSettings() {
        long pid = this.mCurrentPlayingVideo.pid;
        if (pid == 0) {
            pid = this.mCurrentPlayingVideo.vid;
        }
        LogInfo.log("wuxinrong", "查询language_settings表...pid = " + pid);
        return DBManager.getInstance().getLanguageSettingsTrace().query(pid);
    }

    protected void onRequestPlayRecord(PlayRecord result) {
        boolean notNeedPlayrecord = false;
        if (this.mIsDownloadFile && this.mDownloadDBBean != null) {
            this.mObservable.notifyObservers(new VideoTitleNotify(this.mDownloadDBBean.episodetitle));
        } else if (!(result == null || TextUtils.isEmpty(result.title))) {
            boolean isFromMobile;
            this.mPlayRecord = result;
            int playRecordCid = this.mPlayRecord.channelId;
            if (PlayDeviceFrom.MOBILE == this.mPlayRecord.getFrom()) {
                isFromMobile = true;
            } else {
                isFromMobile = false;
            }
            if (isFromMobile && !LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN.equals(this.mPlayRecord.videoTypeKey)) {
                notNeedPlayrecord = true;
            }
            if ((playRecordCid == 1 || playRecordCid == 2 || playRecordCid == 5 || playRecordCid == 16 || playRecordCid == 1021) && notNeedPlayrecord) {
                this.mPlayRecord = null;
            } else {
                this.mObservable.notifyObservers(new VideoTitleNotify(result.title));
                if (this.mPlayRecord.totalDuration < 180 || this.mPlayRecord.playedDuration < 60) {
                    this.mPlayRecord.playedDuration = 0;
                }
                if (this.mFrom == 15) {
                    long j;
                    PlayRecord playRecord = this.mPlayRecord;
                    if (!this.mIsSkip || this.mPlayInfo.beginTime <= 0 || this.mPlayInfo.hTime < 0 || this.mPlayInfo.hTime >= this.mPlayInfo.beginTime) {
                        j = this.mPlayInfo.hTime;
                    } else {
                        j = this.mPlayInfo.beginTime;
                    }
                    playRecord.playedDuration = j;
                    this.mPlayRecord.from = 1;
                }
                if (this.mPlayRecord.totalDuration > 600) {
                    this.mLoadListener.loading(LetvUtils.getPlayRecordType(this.mContext, this.mPlayRecord, this.mPlayInfo.beginTime));
                }
                if (this.mSeek > 0) {
                    this.mPlayRecord.playedDuration = this.mSeek / 1000;
                } else if (this.mPlayRecord.playedDuration > 0) {
                    this.mSeek = this.mPlayRecord.playedDuration * 1000;
                }
                this.mVid = (long) this.mPlayRecord.videoId;
                this.mPlayInfo.videoTotalTime = this.mPlayRecord.totalDuration * 1000;
            }
        }
        requestVideo();
        notifyTabRequestData();
    }

    protected void notifyTabRequestData() {
        this.mObservable.notifyObservers(new RequestCombineParams(this.mAid + "", this.mVid + "", this.mCid + "", this.mZid + ""));
    }

    private void requestDownload() {
        this.mLoadListener.loading();
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setTag(REQUEST_DOWNLOAD).setCache(new VolleyDbCache<DownloadDBBean>() {
            public DownloadDBBean get(VolleyRequest<?> volleyRequest) {
                boolean z = false;
                DownloadDBBean info = DownloadManager.getLocalVideoBean(AlbumPlayFlow.this.mVid);
                if (info == null) {
                    AlbumPlayFlow.this.mPlayRecord = AlbumPlayFlow.this.mVideoListener.getPoint(0, (int) AlbumPlayFlow.this.mVid, false);
                    return null;
                }
                LogInfo.log("zhuqiao", "------播放本地视频------");
                AlbumPlayFlow.this.mPlayRecord = AlbumPlayFlow.this.mVideoListener.getPoint(0, (int) AlbumPlayFlow.this.mVid, true);
                AlbumPlayFlow.this.mAid = (long) info.aid;
                if (AlbumPlayFlow.this.mAid > 0) {
                    AlbumPlayFlow.this.mDownloadDBListBean = DownloadManager.getDownloadDBBeanByAid(AlbumPlayFlow.this.mAid);
                }
                AlbumPlayFlow.this.mIsDownloadFile = true;
                AlbumPlayFlow albumPlayFlow = AlbumPlayFlow.this;
                if (info.isHd == 1) {
                    z = true;
                }
                albumPlayFlow.mCurrDownloadFileIsHd = z;
                AlbumPlayFlow.this.mDownloadStreamLevel = info.isHd;
                LogInfo.log("zhuqiao", "---initDownLoadStream checkDownload---" + info.isHd + AlbumPlayFlow.this.mCurrDownloadFileIsHd);
                AlbumPlayFlow.this.mFilePath = info.filePath;
                if (AlbumPlayFlow.this.mPlayRecord != null) {
                    AlbumPlayFlow.this.mPlayRecord.albumId = (int) AlbumPlayFlow.this.mAid;
                }
                if (AlbumPlayFlow.this.mPlayRecord == null) {
                    AlbumPlayFlow.this.mPlayRecord = new PlayRecord();
                    AlbumPlayFlow.this.mPlayRecord.albumId = info.aid;
                    AlbumPlayFlow.this.mPlayRecord.videoId = info.vid;
                    if (AlbumPlayFlow.this.mPlayRecord.albumId == AlbumPlayFlow.this.mPlayRecord.videoId) {
                        AlbumPlayFlow.this.mPlayRecord.videoType = 3;
                    } else if (AlbumPlayFlow.this.mCurrentPlayingVideo != null) {
                        LogInfo.log("Emerson", "----final-下载----videotypekey = " + AlbumPlayFlow.this.mCurrentPlayingVideo.videoTypeKey);
                        AlbumPlayFlow.this.mPlayRecord.videoType = 1;
                    }
                    AlbumPlayFlow.this.mPlayRecord.title = info.episodetitle;
                    AlbumPlayFlow.this.mPlayRecord.channelId = info.cid;
                    AlbumPlayFlow.this.mPlayRecord.img = info.icon;
                    AlbumPlayFlow.this.mPlayRecord.from = 2;
                    AlbumPlayFlow.this.mPlayRecord.playedDuration = 0;
                } else {
                    AlbumPlayFlow.this.mPlayRecord.albumId = info.aid;
                }
                AlbumPlayFlow.this.mPlayInfo.beginTime = info.btime;
                AlbumPlayFlow.this.mPlayInfo.endTime = info.etime;
                if (AlbumPlayFlow.this.mSeek > 0) {
                    AlbumPlayFlow.this.mPlayRecord.playedDuration = AlbumPlayFlow.this.mSeek / 1000;
                } else {
                    AlbumPlayFlow.this.mSeek = AlbumPlayFlow.this.mPlayRecord.playedDuration * 1000;
                }
                AlbumPlayFlow.this.mPlayInfo.videoTotalTime = AlbumPlayFlow.this.mPlayRecord.totalDuration * 1000;
                return info;
            }

            public void add(VolleyRequest<?> volleyRequest, DownloadDBBean response) {
            }
        }).setCallback(new SimpleResponse<DownloadDBBean>() {
            public void onCacheResponse(VolleyRequest<DownloadDBBean> volleyRequest, DownloadDBBean result, DataHull hull, CacheResponseState state) {
                AlbumPlayFlow.this.mDownloadDBBean = result;
                if (state != CacheResponseState.SUCCESS) {
                    UIsUtils.showToast(R.string.can_not_find_file);
                    AlbumPlayFlow.this.mVideoListener.finishPlayer(false);
                    return;
                }
                AlbumPlayFlow.this.setCurrentStreamFromDownload(result.isHd);
                AlbumPlayFlow.this.mStreamSupporter.resetHW();
                AlbumPlayFlow.this.mStreamSupporter.hasSuperHd = false;
                if (AlbumPlayFlow.this.mPlayLevel == 2) {
                    AlbumPlayFlow.this.mStreamSupporter.hasHd = true;
                    AlbumPlayFlow.this.mStreamSupporter.hasStandard = false;
                } else {
                    AlbumPlayFlow.this.mStreamSupporter.hasStandard = true;
                    AlbumPlayFlow.this.mStreamSupporter.hasHd = false;
                }
                AlbumPlayFlow.this.mStreamSupporter.hasLow = false;
                AlbumPlayFlow.this.mObservable.notifyObservers(new VideoTitleNotify(result.episodetitle));
                if (AlbumPlayFlow.this.mPlayRecord != null) {
                    AlbumPlayFlow.this.mPlayRecord.albumId = result.aid;
                }
                if (AlbumPlayFlow.this.mAid > 0 && AlbumPlayFlow.this.mAid != AlbumPlayFlow.this.mVid) {
                    AlbumPlayFlow.this.requestVideo();
                } else if (AlbumPlayFlow.this.mAid != AlbumPlayFlow.this.mVid) {
                    AlbumPlayFlow.this.checkPlayRecord(false);
                    return;
                } else {
                    AlbumPlayFlow.this.mAid = 0;
                    AlbumPlayFlow.this.requestVideo();
                }
                AlbumPlayFlow.this.notifyTabRequestData();
            }
        }).add();
    }

    protected void requestVideo() {
        BaseApplication.getInstance().onAppMemoryLow();
        addPlayInfo("是否是vip", PreferencesManager.getInstance().isVip() + "");
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_START_FETCHING);
        Volley.getQueue().cancelWithTag(REQUEST_VIDEO_PLAY_URL);
        if (!isUseDoublePlayerAndChangeStream()) {
            this.mIsStartPlay = false;
            this.mIsFirstPlay = true;
            this.mIsStarted = false;
        }
        this.mHasFetchDataSuccess = false;
        this.mOverloadProtectionState = OverloadProtectionState.NORMAL;
        final RequestVideoPlayUrl request = new RequestVideoPlayUrl();
        if (TimestampBean.getTm().mHasRecodeServerTime) {
            request.startRequestCache();
        } else {
            TimestampBean.getTm().getServerTimestamp(new FetchServerTimeListener() {
                public void afterFetch() {
                    request.startRequestCache();
                }
            });
        }
    }

    protected void checkWoFree(Callback callback) {
        LogInfo.log("unicom", "检查是否联通免流量");
        final boolean isUnicomNetwork = NetworkUtils.isUnicom3G(false);
        if (isUnicomNetwork && !this.mHasCancelWo && !this.mIsNonCopyright && UnicomWoFlowManager.getInstance().supportWoFree()) {
            LogInfo.log("unicom", "111111111111111");
            addPlayInfo("判断是否是联通免流量用户开始", "");
            final long startTime = System.currentTimeMillis();
            this.mPlayInfo.mType6_1 = System.currentTimeMillis();
            final Callback callback2 = callback;
            UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.mContext, new LetvWoFlowListener() {
                public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                    if (AlbumPlayFlow.this.mContext != null) {
                        LogInfo.log("jc666", "联通免流量用户检查所耗时间:" + (System.currentTimeMillis() - startTime));
                        AlbumPlayFlow.this.mPlayInfo.mType6_1 = System.currentTimeMillis() - AlbumPlayFlow.this.mPlayInfo.mType6_1;
                        AlbumPlayFlow.this.mIsWo3GUser = isOrder;
                        if (callback2 != null) {
                            AlbumPlayFlow.this.mHandler.post(new Runnable() {
                                public void run() {
                                    callback2.callback();
                                }
                            });
                            return;
                        }
                        AlbumPlayFlow.this.mIsPlayFreeUrl = false;
                        Class cls = JarLoader.loadClass(AlbumPlayFlow.this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager");
                        LogInfo.log("unicom", "从缓存读取手机号");
                        String phoneNumber = BaseApplication.getInstance().getUnicomFreeInfoCache().mPhoneNumber;
                        if (TextUtils.isEmpty(phoneNumber)) {
                            LogInfo.log("unicom", "缓存读取手机号结果为空，尝试请求联通手机号");
                            phoneNumber = ((IWoFlowManager) JarLoader.invokeStaticMethod(cls, "getInstance", null, null)).getPhoneNum(AlbumPlayFlow.this.mContext);
                            if (!TextUtils.isEmpty(phoneNumber)) {
                                LogInfo.log("unicom", "请求联通手机号结果有效，写入缓存");
                                BaseApplication.getInstance().getUnicomFreeInfoCache().mPhoneNumber = phoneNumber;
                            }
                        }
                        boolean isPhoneNumNull = TextUtils.isEmpty(phoneNumber);
                        AlbumPlayFlow.this.addPlayInfo("判断是否是联通免流量用户结束", "mIsWo3GUser:" + AlbumPlayFlow.this.mIsWo3GUser + ";isSupportProvince:" + isSupportProvince + ";isPhoneNumNull:" + isPhoneNumNull + ";isNetWo:" + isUnicomNetwork);
                        if (!isSupportProvince || isOrder || isPhoneNumNull) {
                            if (isOrder || !isPhoneNumNull) {
                                AlbumPlayFlow.this.addPlayInfo("联通", "使用流量播放");
                                AlbumPlayFlow.this.requestRealPlayUrl();
                                return;
                            }
                            AlbumPlayFlow.this.mVideoListener.pause();
                            AlbumPlayFlow.this.mIsSdkInitFail = true;
                            LogInfo.log("unicom", "AlbumPlayFlow 显示确认是否已订购对话框");
                            AlbumPlayFlow.this.addPlayInfo("联通", "弹出确认是否已订购对话框");
                            new UnicomWoFlowDialogUtils().showOrderConfirmEnquireDialog(AlbumPlayFlow.this.mContext, new UnicomDialogClickListener() {
                                public void onConfirm() {
                                    LogInfo.log("unicom", "AlbumPlayFlow 显示短信取号对话框");
                                    AlbumPlayFlow.this.addPlayInfo("联通", "显示短信取号对话框");
                                    ((IWoFlowManager) JarLoader.invokeStaticMethod(JarLoader.loadClass(AlbumPlayFlow.this.mContext, JarConstant.LETV_WO_NAME, JarConstant.LETV_WO_PACKAGENAME, "WoFlowManager"), "getInstance", null, null)).showSMSVerificationDialog(AlbumPlayFlow.this.mContext, new LetvWoFlowListener() {
                                        public void onResponseOrderInfo(boolean isSupportProvince, boolean isOrder, boolean isUnOrderSure, String freeUrl, boolean isSmsSuccess) {
                                            if (isSmsSuccess && isOrder) {
                                                LogInfo.log("unicom", "AlbumPlayFlow 用户已订购");
                                                AlbumPlayFlow.this.addPlayInfo("联通", "用户已订购,重走播放流程");
                                                AlbumPlayFlow.this.requestVideo();
                                                return;
                                            }
                                            LogInfo.log("unicom", "AlbumPlayFlow 短信取号失败或者用户未订购");
                                            AlbumPlayFlow.this.addPlayInfo("联通", "短信取号失败或者用户未订购,使用流量播放");
                                            AlbumPlayFlow.this.requestRealPlayUrl();
                                        }
                                    });
                                }

                                public void onCancel() {
                                    AlbumPlayFlow.this.addPlayInfo("联通", "选中未订购,使用流量播放");
                                    AlbumPlayFlow.this.requestRealPlayUrl();
                                }

                                public void onResponse(boolean isShow) {
                                }
                            });
                        } else if (PreferencesManager.getInstance().getWoFlowAlert()) {
                            AlbumPlayFlow.this.addPlayInfo("联通", "使用流量播放");
                            AlbumPlayFlow.this.mHasCancelWo = true;
                            AlbumPlayFlow.this.requestRealPlayUrl();
                        } else {
                            AlbumPlayFlow.this.addPlayInfo("联通", "弹出是否购买弹窗");
                            new UnicomWoFlowDialogUtils().showWoMainDialog(AlbumPlayFlow.this.mContext, new UnicomDialogClickListener() {
                                public void onResponse(boolean isShow) {
                                }

                                public void onConfirm() {
                                    AlbumPlayFlow.this.addPlayInfo("联通", "去购买3g页面");
                                    if (AlbumPlayFlow.this.mVideoListener != null) {
                                        AlbumPlayFlow.this.mVideoListener.buyWo3G();
                                    }
                                }

                                public void onCancel() {
                                    AlbumPlayFlow.this.addPlayInfo("联通", "使用流量播放");
                                    AlbumPlayFlow.this.mHasCancelWo = true;
                                    AlbumPlayFlow.this.requestRealPlayUrl();
                                }
                            }, "AlbumPlayActivity");
                        }
                    }
                }
            });
        } else if (callback != null) {
            callback.callback();
        } else {
            requestRealPlayUrl();
        }
    }

    private void initRequestUrlController() {
        checkSupportCombine();
        if (this.mIsCombineAd) {
            this.mRequestUrlController = new AlbumFlowControllerCombine(this.mContext, this);
            addPlayInfo("使用全拼接", "");
            return;
        }
        this.mRequestUrlController = new AlbumFlowControllerSeparate(this.mContext, this);
        addPlayInfo("不使用全拼接", "VideoType:" + this.mVideoType + ";VideoFormat:" + BaseApplication.getInstance().getVideoFormat() + ";LaunchMode" + this.mLaunchMode + ";IsWo3GUser:" + this.mIsWo3GUser + ";SupportCombine:" + PreferencesManager.getInstance().getSupportCombine() + ";pinjie:" + BaseApplication.getInstance().getPinjie());
    }

    protected void requestRealPlayUrl() {
        this.mPlayInfo.mType18 = System.currentTimeMillis();
        initRequestUrlController();
        this.mRequestUrlController.startRequestRealPlayUrl();
    }

    public boolean retryStream() {
        this.mVideoListener.stopPlayback();
        if (this.mRequestUrlController == null) {
            initRequestUrlController();
        }
        return this.mRequestUrlController.fetchDispatchUrl(true);
    }

    private void checkSupportCombine() {
        boolean z = (this.mVideoType != VideoType.Normal || TextUtils.equals(BaseApplication.getInstance().getVideoFormat(), "no") || this.mLaunchMode == 0 || this.mLaunchMode == 3 || this.mIsWo3GUser || !PreferencesManager.getInstance().getSupportCombine() || !BaseApplication.getInstance().getPinjie()) ? false : true;
        this.mIsCombineAd = z;
        this.mPlayInfo.mIsCombineAd = this.mIsCombineAd;
    }

    public String getSecurityChainUrl() {
        this.mLanguageSettings = queryLanguageSettings();
        return PlayUtils.getDdUrl(this.mAlbumUrl.dispatchUrl, PlayUtils.getPlayToken(this.mDdUrlsResult, this.mPayInfo), PreferencesManager.getInstance().getUserId(), this.mVid + "", this.mPlayInfo.mUuidTimp, AudioTrackManager.getInstance().obtainId(this.mVideoFile, this.mLanguageSettings, this.mPlayLevel, this.mVideoType == VideoType.Dolby));
    }

    public String getLinkShellUrl() {
        this.mLanguageSettings = queryLanguageSettings();
        return PlayUtils.getLinkShell(this.mAlbumUrl.dispatchUrl, PlayUtils.getPlayToken(this.mDdUrlsResult, this.mPayInfo), PreferencesManager.getInstance().getUserId(), this.mVid + "", this.mPlayInfo.mUuidTimp, AudioTrackManager.getInstance().obtainId(this.mVideoFile, this.mLanguageSettings, this.mPlayLevel, this.mVideoType == VideoType.Dolby));
    }

    public String getLinkShellUrl2() {
        this.mLanguageSettings = queryLanguageSettings();
        return PlayUtils.getLinkShell2(this.mAlbumUrl.dispatchUrl, PlayUtils.getPlayToken(this.mDdUrlsResult, this.mPayInfo), PreferencesManager.getInstance().getUserId(), this.mVid + "", this.mPlayInfo.mUuidTimp, AudioTrackManager.getInstance().obtainId(this.mVideoFile, this.mLanguageSettings, this.mPlayLevel, this.mVideoType == VideoType.Dolby));
    }

    public boolean getFrontAdNormal(boolean isPause) {
        addPlayInfo("解析广告地址开始", "");
        addPlayInfo("广告缓存开关", PreferencesManager.getInstance().getUtp() ? "开启" : "关闭");
        sRequestAdsConsumetime = System.currentTimeMillis();
        if (this.mAlreadyPlayAd) {
            this.mIsFrontAdFinished = true;
            return false;
        }
        boolean noAd;
        if (!this.mIsCombineAd) {
            this.mAlreadyPlayAd = true;
        }
        if (PreferencesManager.getInstance().isPipFlag() || ((!this.mNeedPlayAd && this.mIsMidAdPlayed) || needSkipAd() || this.mIsNonCopyright)) {
            noAd = true;
        } else {
            noAd = false;
        }
        if (PreferencesManager.getInstance().isPipFlag()) {
            PreferencesManager.getInstance().setPipFlag(false);
        }
        if (!noAd) {
            if (this.mFirstRequest == 0) {
                this.mFirstRequest = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - this.mFirstRequest < this.mRequestStep) {
                this.mFirstRequest = System.currentTimeMillis();
                return true;
            }
            if (this.mCurrentPlayingVideo != null) {
                this.mPlayInfo.mType15 = System.currentTimeMillis();
                sRequestAdsCallbackConsumetime = System.currentTimeMillis();
                this.mPlayInfo.mAdConsumeTime = System.currentTimeMillis();
                if (isPause) {
                    this.mAdListener.setADPause(true);
                }
                this.mAdInfo = this.mAdInfo == null ? new AdInfoBean() : this.mAdInfo;
                HashMap<String, String> adInfoMap = new HashMap();
                adInfoMap.put(AdMapKey.AD_DATA, this.mAdInfo.adData);
                adInfoMap.put("errorCode", this.mAdInfo.errCode);
                adInfoMap.put("errorCode", this.mAdInfo.errCode);
                adInfoMap.put(AdMapKey.ARKID, this.mAdInfo.arkId);
                adInfoMap.put(AdMapKey.IS_SUPPORT_FULLCOMBINE, this.mIsCombineAd ? "1" : "0");
                if (this.mCurrentPlayingVideo != null) {
                    adInfoMap.put("vid", this.mCurrentPlayingVideo.vid + "");
                    adInfoMap.put("pid", this.mCurrentPlayingVideo.pid + "");
                    String str = AdMapKey.IS_TRYLOOK;
                    Object obj = (this.mCurrentPlayingVideo.needPay() || this.mCurrentPlayingVideo.albumPay == 1) ? "1" : "0";
                    adInfoMap.put(str, obj);
                    adInfoMap.put(AdMapKey.VLEN, this.mCurrentPlayingVideo.duration + "");
                    adInfoMap.put("mmsid", this.mCurrentPlayingVideo.mid + "");
                    adInfoMap.put("cid", this.mCurrentPlayingVideo.cid + "");
                }
                Message msg = new Message();
                msg.what = 8;
                Bundle bundle = new Bundle();
                bundle.putSerializable(PFConstant.KEY_AD_DATA, adInfoMap);
                msg.setData(bundle);
                this.mAdListener.notifyADEvent(msg);
                this.mVideoListener.setEnforcementWait(true);
                LogInfo.log("zhuqiao_realurl", "请求广告");
                return true;
            }
        }
        this.mIsSeparateAdFinished = true;
        this.mNeedPlayAd = true;
        this.mIsFrontAdFinished = true;
        LogInfo.log("zhuqiao_realurl", "无广告");
        addPlayInfo("解析广告地址结束：无广告", "");
        return false;
    }

    public long getPlayRecordStep() {
        if (this.mPlayRecord == null) {
            LogInfo.log("zhuqiao", "getPlayRecordStep=0");
            return 0;
        }
        if (this.mFrom == 15) {
            PlayRecord playRecord = this.mPlayRecord;
            long j = (!this.mIsSkip || this.mPlayInfo.beginTime <= 0 || this.mPlayInfo.hTime < 0 || this.mPlayInfo.hTime >= this.mPlayInfo.beginTime) ? this.mPlayInfo.hTime : this.mPlayInfo.beginTime;
            playRecord.playedDuration = j;
        } else if (this.mIsSkip && this.mPlayInfo.beginTime > 0 && this.mPlayRecord.playedDuration <= this.mPlayInfo.beginTime) {
            this.mPlayRecord.playedDuration = this.mPlayInfo.beginTime;
        }
        LogInfo.log("zhuqiao", "getPlayRecordStep=" + this.mPlayRecord.playedDuration);
        return this.mPlayRecord.playedDuration;
    }

    private void requestLocalVideo() {
        DownloadDBBean dbBean = DownloadManager.getLocalVideoBean(this.mVid);
        if (dbBean == null) {
            this.mIsDownloadFile = false;
            return;
        }
        this.mIsDownloadFile = true;
        DownloadManager.updateDownloadWatched(dbBean);
        VideoBean vb = new VideoBean();
        vb.vid = (long) dbBean.vid;
        vb.cid = dbBean.cid;
        vb.mid = dbBean.mmsid;
        vb.pid = (long) dbBean.aid;
        vb.nameCn = dbBean.episodetitle;
        vb.etime = dbBean.etime;
        vb.btime = dbBean.btime;
        vb.duration = dbBean.duration;
        if (dbBean.hasSubtitle || dbBean.isMultipleAudio) {
            LogInfo.log("zhaosumin", "本地播放时候 是否有字幕: " + dbBean.hasSubtitle + " 字幕地址: " + dbBean.subtitleUrl + " 字幕编码 :" + dbBean.subtitleCode + " 是否有音轨: " + dbBean.isMultipleAudio + " 音轨编码: " + dbBean.multipleAudioCode);
            this.mObservable.notifyObservers(new LocalVideoSubtitlesPath(dbBean.hasSubtitle ? DownloadSubtitleManager.getLocalSubtitlePath(dbBean.subtitleUrl) : ""));
            if (this.mLanguageSettings == null) {
                this.mLanguageSettings = new LanguageSettings();
            }
            this.mLanguageSettings.pid = vb.pid;
            this.mLanguageSettings.subtitleCode = dbBean.subtitleCode;
            this.mLanguageSettings.audioTrackCode = dbBean.multipleAudioCode;
        }
        vb.videoTypeKey = LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN;
        if (this.mCurrentPlayingVideo != null) {
            vb.watchingFocusList = this.mCurrentPlayingVideo.watchingFocusList;
        }
        if (LetvUtils.getClientVersionCode() < 87 || TextUtils.isEmpty(dbBean.videoTypeKey)) {
            vb.videoTypeKey = LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN;
        } else {
            vb.videoTypeKey = dbBean.videoTypeKey;
        }
        setVideoBean(vb);
        if (this.mPlayRecord != null) {
            this.mPlayRecord.videoTypeKey = vb.videoTypeKey;
        }
    }

    private boolean isVideoDownloaded() {
        this.mDownloadDBBean = DownloadManager.getLocalVideoBean(this.mVid);
        if (this.mDownloadDBBean == null) {
            return false;
        }
        this.mIsDownloadFile = true;
        this.mFilePath = this.mDownloadDBBean.filePath;
        this.mCurrDownloadFileIsHd = this.mDownloadDBBean.isHd == 1;
        switch (this.mDownloadDBBean.isHd) {
            case 0:
                this.mPlayLevel = 0;
                break;
            case 1:
                this.mPlayLevel = 2;
                break;
            case 2:
                this.mPlayLevel = 1;
                break;
        }
        this.mStreamSupporter.reset();
        if (this.mDownloadDBBean.cid == 9 && NetworkUtils.isNetworkAvailable()) {
            return true;
        }
        this.mObservable.notifyObservers(new VideoTitleNotify(this.mDownloadDBBean.episodetitle));
        return true;
    }

    public void setCurrenStreamFromFullController() {
        DownloadDBBean info = DownloadManager.getLocalVideoBean(this.mVid);
        if (info != null) {
            this.mDownloadStreamLevel = info.isHd;
            setCurrentStreamFromDownload(this.mDownloadStreamLevel);
        }
    }

    private void setCurrentStreamFromDownload(int downloadStream) {
        switch (downloadStream) {
            case 0:
                this.mPlayLevel = 0;
                return;
            case 1:
                this.mPlayLevel = 2;
                return;
            case 2:
                this.mPlayLevel = 1;
                return;
            default:
                return;
        }
    }

    public void retryPlay(boolean needPlayAd, boolean isChangeStream) {
        boolean z = true;
        if (!this.mShouldWaitDrmPluginInstall) {
            sRequestRealUrlConsumetime = System.currentTimeMillis();
            this.mIsRetry = true;
            this.mRetrySeek = -1;
            this.mIsChangeingStream = isChangeStream;
            this.mCdeStatusCode = -1;
            if (this.mFrom == 15) {
                this.mFrom = 1;
            }
            this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_HIDE_3G);
            if (!this.mHasFetchDataSuccess) {
                this.mNeedPlayAd = true;
                this.mIsRetry = false;
            } else if (this.mIsFrontAdFinished && this.mIsSeparateAdFinished) {
                this.mNeedPlayAd = false;
            } else {
                this.mNeedPlayAd = needPlayAd;
            }
            this.mAlreadyPlayAd = false;
            if (this.mPlayInfo.currTime > 0) {
                if (this.mPlayInfo.midDuration <= 0 || !this.mIsMidAdPlayed || this.mPlayInfo.currTime < this.mPlayInfo.midDuration + this.mPlayInfo.midAdPlayTime) {
                    this.mRetrySeek = (int) this.mPlayInfo.currTime;
                } else {
                    this.mRetrySeek = (int) (this.mPlayInfo.currTime - this.mPlayInfo.midDuration);
                }
            } else if (!this.mIsFrontAdFinished) {
                this.mRetrySeek = 0;
            }
            clearRequest();
            clearAdInfo();
            if (!isUseDoublePlayerAndChangeStream()) {
                this.mVideoListener.pause();
                this.mVideoListener.stopPlayback();
                this.mAdListener.setADPause(true);
                this.mAdListener.stopPlayback(false, this.mIsRetry);
                this.mLoadListener.loading();
                this.mObservable.notifyObservers(new PlayErrorCodeNotify("", false));
            }
            if (this.mIsChangeingStream) {
                updatePlayDataStatistics("tg", -1);
                if (enableDoublePlayer()) {
                    this.mLastPlayInfo = this.mPlayInfo;
                    this.mPlayInfo = new AlbumPlayInfo();
                    this.mPlayInfo.mUuid = this.mLastPlayInfo.mUuid;
                    this.mPlayInfo.mGlsbNum = this.mLastPlayInfo.mGlsbNum;
                }
                this.mPlayInfo.mIpt = 2;
                this.mPlayInfo.mReplayType = 2;
            } else {
                this.mPlayInfo.mReplayType = 3;
            }
            AlbumPlayInfo albumPlayInfo = this.mPlayInfo;
            albumPlayInfo.mGlsbNum++;
            if (isPlayingAd()) {
                z = false;
            }
            statisticsLaunch(z);
            createPlayRecord();
            this.mVideoListener.rePlay(isChangeStream);
            requestVideo();
        }
    }

    public void timeOutToSkipAd() {
        addPlayInfo("全局超时,跳过广告", "");
        clearRequest();
        this.mVideoListener.pause();
        this.mVideoListener.stopPlayback();
        this.mAdListener.setADPause(true);
        this.mAdListener.stopPlayback(false);
        if (this.mAdListener.getIVideoStatusInformer() != null) {
            this.mAdListener.getIVideoStatusInformer().onForceSkipPrerollAd(1);
        }
        this.mNeedPlayAd = false;
        handleADUrlAcquireDone(null, null, 0);
        if (!this.mIsCombineAd && (this.mRequestUrlController instanceof AlbumFlowControllerSeparate)) {
            this.mRequestUrlController.startRequestRealPlayUrl();
        }
    }

    public void clearRequest() {
        LogInfo.log("zhuqiao", "清除请求");
        Volley.getQueue().cancelAll(new RequestFilter() {
            public boolean apply(VolleyRequest<?> request) {
                return (request == null || TextUtils.isEmpty(request.getTag()) || !request.getTag().startsWith(AlbumPlayFlow.ALBUM_FLOW_TAG)) ? false : true;
            }
        });
        DrmManager.getInstance().stopDrmThread();
    }

    protected void createPlayRecord() {
        if (this.mCurrentPlayingVideo != null) {
            if (this.mPlayRecord == null) {
                this.mPlayRecord = new PlayRecord();
            }
            if (this.mCurrentPlayingVideo.vid != 0) {
                this.mPlayRecord.albumId = (int) this.mAid;
                if (this.mCurrentPlayingVideo != null) {
                    this.mPlayRecord.videoType = this.mCurrentPlayingVideo.type;
                    this.mPlayRecord.img300 = this.mCurrentPlayingVideo.pic200_150;
                    this.mPlayRecord.title = getTitle(this.mCurrentPlayingVideo);
                    LogInfo.log("Emerson", "------final-----创建播放记录 videotypekey = " + this.mCurrentPlayingVideo.videoTypeKey);
                    this.mPlayRecord.videoTypeKey = this.mCurrentPlayingVideo.videoTypeKey;
                    this.mPlayRecord.channelId = this.mCurrentPlayingVideo.cid;
                    this.mPlayRecord.img = this.mCurrentPlayingVideo.pic120_90;
                    this.mPlayRecord.curEpsoid = BaseTypeUtils.stof(this.mCurrentPlayingVideo.episode);
                    this.mPlayRecord.totalDuration = this.mCurrentPlayingVideo.duration;
                } else if (this.mVideoBelongedAlbum != null) {
                    this.mPlayRecord.videoType = this.mVideoBelongedAlbum.type;
                    this.mPlayRecord.img300 = this.mVideoBelongedAlbum.pic300_300;
                }
                this.mPlayRecord.from = 2;
                if (this.mSeek > 0) {
                    this.mPlayRecord.playedDuration = this.mSeek / 1000;
                } else {
                    this.mPlayRecord.playedDuration = 0;
                }
                this.mPlayRecord.videoId = (int) this.mVid;
                this.mPlayInfo.videoTotalTime = this.mPlayRecord.totalDuration * 1000;
                this.mPlayRecord.updateTime = System.currentTimeMillis();
            }
        }
    }

    private String getTitle(VideoBean video) {
        if (video == null) {
            return "";
        }
        String name = "";
        if (!TextUtils.equals(this.mContext.getString(R.string.channel_music), LetvUtils.getChannelName(this.mContext, video.cid))) {
            return video.nameCn;
        }
        return video.nameCn + "  " + BaseTypeUtils.ensureStringValidate(video.singer);
    }

    public int getDownloadStreamLevel() {
        return this.mDownloadStreamLevel;
    }

    protected void setPlayingVideoBeanStatus() {
        if (this.mDownloadDBBean != null) {
            DBManager.getInstance().getPlayTrace().insertPlayTraceByWatchedStatus((long) this.mDownloadDBBean.aid, (long) this.mDownloadDBBean.vid);
        }
        if (this.mDownloadDBBean != null) {
            this.mStreamLevel = PreferencesManager.getInstance().getDownloadFileStreamLevel(this.mDownloadDBBean.vid + "");
        }
    }

    protected void startPlayLocal() {
        this.mIsScanVideo = false;
        this.mVideoListener.resetPlayFlag();
        this.mIsShowSkipEnd = true;
        setPlayingVideoBeanStatus();
        getPlayRecordStep();
        if (this.mDownloadDBBean != null) {
            this.mIsStartPlayLocalDownloadEnter = true;
            switch (this.mDownloadDBBean.isHd) {
                case 0:
                    this.mPlayLevel = 0;
                    break;
                case 1:
                    this.mPlayLevel = 2;
                    break;
                case 2:
                    this.mPlayLevel = 1;
                    break;
            }
            this.mStreamSupporter.resetHW();
            this.mStreamSupporter.hasSuperHd = false;
            if (this.mPlayLevel == 2) {
                this.mCurrDownloadFileIsHd = true;
                this.mStreamSupporter.hasHd = true;
                this.mStreamSupporter.hasStandard = false;
            } else {
                this.mStreamSupporter.hasHd = false;
                this.mStreamSupporter.hasStandard = true;
            }
            this.mStreamSupporter.hasLow = false;
        } else if (!this.mIsStartPlayLocalDownloadEnter) {
            this.mPlayLevel = 2;
            this.mCurrDownloadFileIsHd = true;
            this.mStreamSupporter.hasHd = true;
            this.mStreamSupporter.hasStandard = false;
            this.mStreamSupporter.hasLow = false;
            this.mStreamSupporter.hasSuperHd = false;
            this.mStreamSupporter.resetHW();
        }
        addOffAd();
        this.mDownloadDBBean = null;
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_CHECK_PIP_VISIBILE);
    }

    protected void addOffAd() {
        addPlayInfo("本地播放器init上报 vid :", this.mVid + "");
        if (PreferencesManager.getInstance().isPipFlag()) {
            PreferencesManager.getInstance().setPipFlag(false);
            this.mVideoListener.initVideoView(true, this.mIsChangeingStream);
            this.mVideoListener.setEnforcementPause(false);
            this.mVideoListener.setEnforcementWait(false);
            this.mVideoListener.startPlayLocal(this.mFilePath, this.mPlayRecord == null ? 0 : (long) (((int) this.mPlayRecord.playedDuration) * 1000), this.mIsChangeingStream);
        } else if (this.mDownloadDBBean == null || this.mAlreadyPlayAd || this.mPlayInfo.currTime != 0) {
            if (this.mIsSeparateAdFinished) {
                this.mVideoListener.initVideoView(true, this.mIsChangeingStream);
                this.mVideoListener.setEnforcementPause(false);
                this.mVideoListener.setEnforcementWait(false);
            }
            this.mVideoListener.startPlayLocal(this.mFilePath, this.mPlayRecord == null ? 0 : (long) (((int) this.mPlayRecord.playedDuration) * 1000), this.mIsChangeingStream);
        } else {
            this.mPlayInfo.mAdConsumeTime = System.currentTimeMillis();
            long currentTimeMillis = System.currentTimeMillis();
            sRequestAdsConsumetime = currentTimeMillis;
            sRequestAdsCallbackConsumetime = currentTimeMillis;
            this.mPlayInfo.mType15 = System.currentTimeMillis();
            addPlayInfo("请求离线广告", "");
            this.mIsSeparateAdFinished = false;
            this.mAdListener.getOfflineFrontAd(this.mDownloadDBBean.cid, (long) this.mDownloadDBBean.aid, (long) this.mDownloadDBBean.vid, this.mDownloadDBBean.mmsid, DataUtils.getUUID(this.mContext), PreferencesManager.getInstance().getUserId(), this.mDownloadDBBean.duration + "", "", "0", false, false, false, true, this.mIsRetry);
            this.mAlreadyPlayAd = true;
        }
    }

    public void startPlayNet() {
        this.mIsScanVideo = false;
        this.mVideoListener.resetPlayFlag();
        if (this.mIsCombineAd && this.mAdListener.getAdFragment() != null) {
            this.mPlayInfo.mAdsRequestTime = this.mAdListener.getAdFragment().getAdsLoadingTime();
        }
        if (!TextUtils.isEmpty(this.mAlbumUrl.realUrl)) {
            if (this.mIsSeparateAdFinished) {
                this.mVideoListener.initVideoView(false, this.mIsChangeingStream);
                if (this.mAdListener.isPlaying() && this.mVideoListener.getBufferPercentage() <= 0 && !this.mLoadListener.isLoadingShow()) {
                    this.mLoadListener.loading();
                }
            } else if (this.mCdeStatusCode != 200) {
                new Thread() {
                    public void run() {
                        LogInfo.log("zhuqiao", "开始预加载:");
                        try {
                            HttpURLConnection conn = (HttpURLConnection) new URL(AlbumPlayFlow.this.mAlbumUrl.realUrl).openConnection();
                            AlbumPlayFlow.this.mCdeStatusCode = conn.getResponseCode();
                            conn.getInputStream();
                            if (AlbumPlayFlow.this.mCdeStatusCode == 200) {
                                LogInfo.log("zhuqiao", "开始预加载-------");
                            }
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            if ("ios".equals(BaseApplication.getInstance().getVideoFormat())) {
                this.mPlayInfo.mVformat = "vformat=m3u8";
            } else {
                this.mPlayInfo.mVformat = "vformat=mp4";
            }
            if ((!this.mIsDownloadFile || TextUtils.isEmpty(this.mFilePath)) && this.mPlayRecord != null) {
                getPlayRecordStep();
                this.mIsShowSkipEnd = true;
                this.mPlayInfo.mIsCload = true;
                int position = ((int) this.mPlayRecord.playedDuration) * 1000;
                boolean forceSeek = false;
                if (this.mRetrySeek > 0) {
                    forceSeek = true;
                    position = this.mRetrySeek;
                    this.mPlayRecord.playedDuration = (long) (this.mRetrySeek / 1000);
                    this.mRetrySeek = -1;
                }
                if (this.mIsSeparateAdFinished) {
                    showToast3g();
                    this.mVideoListener.setEnforcementPause(false);
                    this.mVideoListener.setEnforcementWait(false);
                    this.mVideoListener.startPlayNet(this.mAlbumUrl.realUrl, (long) position, this.mIsChangeingStream, forceSeek);
                    if (!enableDoublePlayer()) {
                        this.mIsChangeingStream = false;
                    }
                    AudioTrackManager.getInstance().setAutoSelected(true);
                    if (this.mIsCombineAd) {
                        this.mAlreadyPlayAd = true;
                    }
                }
                if (this.mCurrentPlayingVideo != null && VERSION.SDK_INT > 8) {
                    IRVideo.getInstance().newVideoPlay(this.mContext, this.mCurrentPlayingVideo.vid + "", this.mCurrentPlayingVideo.duration, Boolean.valueOf(true));
                }
            }
        }
    }

    public boolean shouldShowNetChangeDialog() {
        this.mIsStarted = true;
        if (this.mIsWo3GUser || !PreferencesManager.getInstance().isShow3gDialog()) {
            return false;
        }
        boolean isDownLoad;
        LogInfo.log("zhuqiao", ".....showNetChangeDialog.....");
        if (this.mCurrentPlayingVideo == null) {
            isDownLoad = false;
        } else {
            isDownLoad = LetvUtils.isInFinish(this.mCurrentPlayingVideo.vid);
        }
        if (isDownLoad || this.mIsDownloadFile) {
            return false;
        }
        if (NetworkUtils.getNetworkType() != 2 && NetworkUtils.getNetworkType() != 3) {
            return false;
        }
        this.mLoadListener.finish();
        if (this.mHasAd) {
            this.mAdListener.setADPause(true);
        }
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_SHOW_3G);
        this.mVideoListener.setEnforcementPause(true);
        this.mVideoListener.pause();
        StatisticsUtils.staticticsInfoPost(this.mContext, "19", "c68", "0015", 4, null, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, null, null, null, null, null);
        return true;
    }

    private void showVipTrailStart() {
        if (this.mPayInfo == null) {
            this.mTrailListener.setStateForStartByHasLogined(true);
            return;
        }
        this.mTrailListener.setPayInfo(this.mPayInfo);
        if (TextUtils.equals(this.mPayInfo.isForbidden, "1")) {
            this.mTrailListener.forbidden();
            addPlayInfo("无法播放", "账号被封禁");
            return;
        }
        int type = this.mPayInfo.ticketType;
        if (type == -1) {
            addPlayInfo("无法播放", "非会员且没有观影券");
            if (PreferencesManager.getInstance().isLogin()) {
                this.mTrailListener.setStateForStartByHasLogined(true);
            } else {
                this.mTrailListener.setStateForStartByHasLogined(false);
            }
        } else if (type == 1) {
            addPlayInfo("无法播放", "有通用观影券但未使用");
            this.mTrailListener.setStateForStartByHasTicket(PreferencesManager.getInstance().isVip());
        } else {
            int ticketSize = this.mPayInfo.vipTicketSize;
            if (ticketSize == -1) {
                addPlayInfo("无法播放", "没有观影券");
                if (PreferencesManager.getInstance().isLogin()) {
                    this.mTrailListener.setStateForStartByHasLogined(true);
                } else {
                    this.mTrailListener.setStateForStartByHasLogined(false);
                }
            } else if (ticketSize == 0) {
                addPlayInfo("无法播放", "观影券数量为0");
                this.mTrailListener.setStateForStartByHasNoTicket();
            } else {
                addPlayInfo("无法播放", "有会员观影券但未使用");
                this.mTrailListener.setStateForStartByHasTicket(true);
            }
        }
    }

    protected void setVideoBean(VideoBean video) {
        if (video != null) {
            this.mCurrentPlayingVideo = video;
            this.mCid = (long) video.cid;
            if (this.mVideoChangeListener != null) {
                this.mVideoChangeListener.onChange();
            }
            if (this.mIsLaunchPlay) {
                this.mFirstVideo = video;
            }
            if (this.mPlayRecord != null) {
                this.mPlayRecord.videoTypeKey = video.videoTypeKey;
            }
            if (this.mAid <= 0 && video.pid > 0 && this.mIsLaunchPlay) {
                this.mAid = video.pid;
            }
            if (!(this.mFirstVideo == null || this.mFirstVideo == video || video.pid <= 0 || video.pid == this.mFirstVideo.pid)) {
                this.mAid = video.pid;
            }
            this.mObservable.notifyObservers(new VideoTitleNotify(getTitle(video)));
            this.mPlayInfo.endTime = video.etime;
            this.mPlayInfo.beginTime = video.btime;
            if (this.mPlayRecord != null) {
                if (this.mFrom == 15) {
                    long j;
                    PlayRecord playRecord = this.mPlayRecord;
                    if (!this.mIsSkip || this.mPlayInfo.beginTime <= 0 || this.mPlayInfo.hTime < 0 || this.mPlayInfo.hTime >= this.mPlayInfo.beginTime) {
                        j = this.mPlayInfo.hTime;
                    } else {
                        j = this.mPlayInfo.beginTime;
                    }
                    playRecord.playedDuration = j;
                    this.mPlayRecord.from = 5;
                }
                if (video.duration > 600 && !isUseDoublePlayerAndChangeStream()) {
                    this.mLoadListener.loading(LetvUtils.getPlayRecordType(this.mContext, this.mPlayRecord, this.mPlayInfo.beginTime));
                }
            }
            if (video.duration < 180 && this.mPlayRecord != null) {
                this.mPlayRecord.playedDuration = 0;
            }
            if (LetvUtils.isInFinish(video.vid)) {
                this.mIsDownloadFile = true;
            } else {
                this.mIsDownloadFile = false;
            }
        }
    }

    protected void requestWifiRealUrl() {
        if (this.mIsWo3GUser) {
            Volley.getQueue().cancelWithTag(REQUEST_REAL_URL_FOR_WO);
            if (TextUtils.isEmpty(getSecurityChainUrl())) {
                onAfterFetchWifiRealUrl(null, false);
                return;
            } else {
                getRealUrlForWo();
                return;
            }
        }
        requestRealPlayUrl();
    }

    protected void getRealUrlForWo() {
        new LetvRequest().setUrl(getSecurityChainUrl()).setCache(new VolleyNoCache()).setParser(new RealPlayUrlInfoParser()).setMaxRetries(2).setRequestType(RequestManner.NETWORK_ONLY).setTag(REQUEST_REAL_URL_FOR_WO).setCallback(new SimpleResponse<RealPlayUrlInfoBean>() {
            public void onNetworkResponse(VolleyRequest<RealPlayUrlInfoBean> volleyRequest, RealPlayUrlInfoBean result, DataHull hull, NetworkResponseState state) {
                if (state == NetworkResponseState.SUCCESS) {
                    AlbumPlayFlow.this.onAfterFetchWifiRealUrl(result, false);
                } else if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                    AlbumPlayFlow.this.mLoadListener.requestError("", "", "");
                    AlbumPlayFlow.this.mTrailListener.hide();
                    AlbumPlayFlow.this.mErrorState = PlayErrorState.WO_REAL_URL_API_ERROR;
                } else if (state == NetworkResponseState.NETWORK_ERROR) {
                    AlbumPlayFlow.this.mLoadListener.requestError(AlbumPlayFlow.this.mContext.getString(R.string.net_request_error), "", "");
                    AlbumPlayFlow.this.mTrailListener.hide();
                    AlbumPlayFlow.this.mErrorState = PlayErrorState.WO_REAL_URL_API_ERROR;
                    AlbumPlayFlow.this.staticticsErrorInfo(AlbumPlayFlow.this.mContext, "1008", "playerError", 0, -1);
                } else if (state == NetworkResponseState.RESULT_ERROR) {
                    AlbumPlayFlow.this.mLoadListener.requestError(AlbumPlayFlow.this.mContext.getString(R.string.data_request_error), "", "");
                    AlbumPlayFlow.this.mTrailListener.hide();
                    AlbumPlayFlow.this.mErrorState = PlayErrorState.WO_REAL_URL_API_ERROR;
                }
            }
        }).add();
    }

    protected void onAfterFetchWifiRealUrl(RealPlayUrlInfoBean result, boolean isCde) {
        if (!this.mIsWo3GUser) {
            if (isCde) {
                if (BaseApplication.getInstance().getCdeHelper() != null) {
                    this.mAlbumUrl.realUrl = BaseApplication.getInstance().getCdeHelper().getPlayUrl(this.mAlbumUrl.p2pUrl, "", "");
                    LetvLogApiTool.createPlayLogInfo("getRealPlayUrlForP2p", this.mVid + "", "realUrl=" + this.mAlbumUrl.realUrl + " playLevel=" + this.mPlayLevel);
                } else {
                    return;
                }
            } else if (result == null || 200 != result.code) {
                this.mLoadListener.requestError(this.mContext.getString(R.string.data_request_error), "", "");
                this.mTrailListener.hide();
                this.mErrorState = PlayErrorState.DATA_ERROR;
            } else {
                this.mAlbumUrl.realUrl = result.realUrl;
                LetvLogApiTool.createPlayLogInfo("getRealPlayUrlForNormalPlay", this.mVid + "", "realUrl=" + this.mAlbumUrl.realUrl + " playLevel=" + this.mPlayLevel);
            }
            startPlayNet();
        } else if (this.mRequestUrlController != null && result != null) {
            this.mRequestUrlController.onFetchRealUrlWithWo3GUser(result.realUrl, true);
        }
    }

    protected void onNetChange() {
        int currentState = NetworkUtils.getNetworkType();
        if (this.mOldNetState != currentState) {
            switch (currentState) {
                case 0:
                    onNetChangeToNoNet();
                    break;
                case 1:
                    onNetChangeToWifi();
                    break;
                case 2:
                case 3:
                    onNetChangeTo2GOr3G();
                    break;
            }
            this.mOldNetState = currentState;
        }
    }

    protected void onNetChangeTo2GOr3G() {
        if (isLebox()) {
            connectLeboxFailed();
        } else if (this.mCurrentPlayingVideo != null) {
            boolean isDownLoad = LetvUtils.isInFinish(this.mCurrentPlayingVideo.vid);
            if ((this.mLaunchMode == 0 && !isDownLoad && !this.mIsDownloadFile) || (this.mIsCombineAd && !this.mIsFrontAdFinished)) {
                UIsUtils.showToast(TipUtils.getTipMessage("100006", R.string.play_net_2g3g4g_tag));
            } else if (!isDownLoad && TextUtils.isEmpty(this.mFilePath) && !this.mIsDownloadFile && this.mLaunchMode != 3) {
                addPlayInfo("重走播放流程", "切换到非wifi环境");
                retryPlay(true, false);
            } else if (!isDownLoad && !this.mIsDownloadFile) {
                UIsUtils.showToast(TipUtils.getTipMessage("100006", R.string.play_net_2g3g4g_tag));
            }
        } else {
            startLoadingData();
        }
    }

    private void onNetChangeToWifi() {
        if (!this.mIsDownloadFile) {
            this.mVideoListener.setEnforcementPause(false);
            if (!this.mIsDownloadFile) {
                UIsUtils.showToast(TipUtils.getTipMessage("100007", R.string.play_net_iswifi_tag));
            }
            this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_HIDE_3G);
            if (isLebox()) {
                if (LeboxApiManager.getInstance().isLeboxConnection()) {
                    this.mVideoListener.startPlayLocal(this.mBoxBean.videoURL, this.mBoxPlayedDuration, this.mIsChangeingStream);
                } else {
                    connectLeboxFailed();
                }
            } else if (this.mIsPlayFreeUrl) {
                this.mIsPlayFreeUrl = false;
                this.mIsWo3GUser = false;
                this.mVideoListener.pause();
                requestWifiRealUrl();
            } else {
                addPlayInfo("重走播放流程", "切换到wifi环境");
                retryPlay(true, false);
            }
        }
    }

    private void onNetChangeToNoNet() {
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_NETWORK_DISCONNECTED);
        if (!(isLebox() || TextUtils.isEmpty(this.mAlbumUrl.realUrl) || isLocalFile())) {
            if ((this.mVideoListener.getCurrentPosition() >= (((long) this.mVideoListener.getBufferPercentage()) * this.mVideoListener.getDuration()) / 100) && this.mVideoListener.isPaused() && this.mTrailListener.getErrState() == VipTrailErrorState.INIT) {
                showNoNet();
            }
        }
        SubtitleRenderManager.getInstance().stop();
    }

    public void showNoNet() {
        if ((!TextUtils.isEmpty(this.mAlbumUrl.realUrl) && !isLocalFile()) || this.mShouldWaitDrmPluginInstall) {
            this.mLoadListener.requestError(TipUtils.getTipMessage("100075", R.string.network_cannot_use_try_later), "", "");
            this.mTrailListener.hide();
            this.mObservable.notifyObservers(new PlayErrorCodeNotify(LetvErrorCode.NO_NET, false));
        }
    }

    public void connectLeboxFailed() {
        this.mBoxPlayedDuration = this.mPlayInfo.currTime;
        if (LeboxApiManager.getInstance().isLeboxConnection()) {
            this.mLoadListener.onLeboxErr(TipUtils.getTipMessage("100112", R.string.lebox_play_error));
        } else {
            this.mLoadListener.onLeboxErr(TipUtils.getTipMessage("100111", R.string.lebox_connect_error));
        }
    }

    public void handleADUrlAcquireDone(List<AdElementMime> preRollAdUrls, List<AdElementMime> midRollAdUrls, long midTime) {
        boolean z;
        this.mPlayInfo.mType15 = System.currentTimeMillis() - this.mPlayInfo.mType15;
        addPlayInfo("收到前贴片广告素材", "");
        if (sRequestAdsCallbackConsumetime != 0) {
            addPlayInfo("广告回调耗时", (System.currentTimeMillis() - sRequestAdsCallbackConsumetime) + "");
            sRequestAdsCallbackConsumetime = 0;
        }
        this.mIVideoStatusInformer = this.mAdListener.getIVideoStatusInformer();
        LogInfo.log("mIVideoStatusInformer", "mIVideoStatusInformer is null:" + (this.mIVideoStatusInformer == null));
        if (BaseTypeUtils.isListEmpty(preRollAdUrls)) {
            z = false;
        } else {
            z = true;
        }
        this.mHasAd = z;
        if (this.mHasAd) {
            if (this.mIsCombineAd) {
                this.mIsSeparateAdFinished = true;
                this.mIsFrontAdFinished = false;
            } else {
                this.mIsSeparateAdFinished = false;
                this.mIsFrontAdFinished = true;
            }
            this.mPlayInfo.mAdCount = (long) preRollAdUrls.size();
            addPlayInfo("前贴广告数量", "" + this.mPlayInfo.mAdCount);
            if (this.mRetrySeek != -1) {
                this.mRetrySeek = (int) this.mPlayInfo.currRealTime;
            }
        } else {
            this.mPlayInfo.mAdCount = 0;
            this.mPlayInfo.frontAdDuration = 0;
            this.mPlayInfo.midDuration = 0;
            addPlayInfo("前贴广告数量", "0");
            this.mIsSeparateAdFinished = true;
            this.mIsFrontAdFinished = true;
            this.mAlreadyPlayAd = true;
            this.mPlayInfo.mAdCount = 0;
        }
        if (BaseTypeUtils.isListEmpty(midRollAdUrls)) {
            addPlayInfo("中贴广告数量", "0");
            this.mIsMidAdFinished = true;
        } else {
            addPlayInfo("中贴广告数量", midRollAdUrls.size() + "");
            if (!this.mIsCombineAd) {
                this.mIsMidAdFinished = true;
            }
        }
        if (this.mRequestUrlController != null) {
            this.mRequestUrlController.handleADUrlAcquireDone(preRollAdUrls, midRollAdUrls, midTime);
        }
    }

    public void handleADBufferDone() {
        addPlayInfo("广告缓冲完成，开始进行正片预加载", "");
        this.mIsSeparateAdFinished = false;
        if (!this.mIsDownloadFile && this.mDownloadDBBean == null && !this.mIsCombineAd && !TextUtils.isEmpty(this.mAlbumUrl.realUrl)) {
            LogInfo.log("zhuqiao_realurl", "---getMainClientBackGroundStart---");
            startPlayNet();
        }
    }

    public void onAdsStart(long time) {
        if (!this.mIsCombineAd) {
            if (this.mAdListener.getAdFragment() != null) {
                AdPlayFragmentProxy adFragment = this.mAdListener.getAdFragment();
                this.mPlayInfo.mAdsToalTime = (long) adFragment.getAdsVideoTotalTime();
                this.mPlayInfo.mAdsRequestTime = adFragment.getAdsLoadingTime();
                this.mPlayInfo.type8 = adFragment.getAdsCombineCostTime();
                this.mPlayInfo.mAdsLoadConsumeTime = adFragment.getAdsPlayLoadTime();
                LogInfo.LogStatistics("ads time:requestTime=" + adFragment.getAdsLoadingTime() + ",loadingTime=" + adFragment.getAdsPlayLoadTime() + ",totalTime=" + adFragment.getAdsVideoTotalTime() + ",combineTime=" + adFragment.getAdsCombineCostTime() + ",interactTime=" + adFragment.getAdsInteractiveTimeInFact() + ",playedTime=" + adFragment.getAdsCostPlayTimeInFact());
            }
            if (this.mPlayInfo.mAdsPlayFirstFrameTime == 0 && !this.mPlayInfo.mIsPlayingAds) {
                this.mPlayInfo.mAdsPlayFirstFrameTime = time;
                this.mPlayInfo.mIsPlayingAds = true;
            }
            LogInfo.log("jc666", "ads start play:" + this.mPlayInfo.mAdsPlayFirstFrameTime);
        }
    }

    public void onAdsFinish(boolean byHand) {
        addPlayInfo("请求前贴片广告结束", "");
        if (this.mPlayInfo.mIsPlayingAds) {
            this.mPlayInfo.mIsPlayingAds = false;
        }
        this.mIsSeparateAdFinished = true;
        this.mHasAd = false;
        if (!this.mIsCombineAd || (this.mIsDownloadFile && !TextUtils.isEmpty(this.mFilePath))) {
            LogInfo.log("zhuqiao_realurl", "广告播放完毕");
            LogInfo.log("zhuqiao_time", "****************广告所消耗时间" + (System.currentTimeMillis() - sRequestAdsConsumetime) + "毫秒****************");
            if (this.mHasAd && this.mVideoType != VideoType.Dolby) {
                this.mLoadListener.finish();
            }
            this.mVideoListener.setEnforcementWait(false);
            this.mPlayInfo.mAdConsumeTime = System.currentTimeMillis() - this.mPlayInfo.mAdConsumeTime;
            if (!byHand) {
                if (!this.mIsDownloadFile || TextUtils.isEmpty(this.mFilePath)) {
                    LogInfo.log("zhuqiao_realurl", "------------onAdsFinish------------");
                    if (this.mPlayRecord == null) {
                        startPlayNet();
                        return;
                    } else if (!shouldShowNetChangeDialog()) {
                        startPlayNet();
                        return;
                    } else {
                        return;
                    }
                }
                startPlayLocal();
            }
        }
    }

    public void onClickShipAd() {
        this.mIsClickShipAd = true;
    }

    public void requestErr() {
        this.mLoadListener.loading();
        this.mObservable.notifyObservers(new PlayErrorCodeNotify("", false));
        if (this.mIsFrontAdFinished && this.mCurrentPlayingVideo != null) {
            this.mNeedPlayAd = false;
        }
        switch (this.mErrorState) {
            case VIDEO_INFO_API_ERROR:
                requestVideo();
                staticticsErrorInfo(this.mContext, "1002", "playerError", 0, -1);
                return;
            case COMBILE_API_ERROR:
                requestVideo();
                return;
            case CND_API_ERROR:
            case WO_REAL_URL_API_ERROR:
            case DATA_ERROR:
                requestVideo();
                staticticsErrorInfo(this.mContext, "1006", "playerError", 0, -1);
                return;
            case PLAY_ERROR:
                if (TextUtils.isEmpty(this.mAlbumUrl.realUrl)) {
                    requestVideo();
                } else if (this.mAid > 0 || this.mVid > 0) {
                    requestVideo();
                } else {
                    this.mVideoListener.startPlayLocal(this.mAlbumUrl.realUrl, 0, this.mIsChangeingStream);
                }
                staticticsErrorInfo(this.mContext, "1007", "playerError", 0, -1);
                return;
            default:
                requestVideo();
                return;
        }
    }

    public void play(VideoBean video) {
        if (video != null) {
            if (this.mCurrentPlayingVideo != null) {
                setVideoBean(video);
                if (!this.mCurrentPlayingVideo.canPlay() && this.mCurrentPlayingVideo.needJump() && TextUtils.equals(this.mCurrentPlayingVideo.jumptype, "WEB_JUMP") && !TextUtils.isEmpty(video.jumpLink)) {
                    this.mLoadListener.autoJumpWeb(video);
                }
            }
            play(video.vid);
        }
    }

    public void playLebox(LeboxVideoBean videoBean) {
        if (videoBean != null) {
            this.mBoxBean = videoBean;
            this.mVideoListener.initVideoView(false, this.mIsChangeingStream);
            this.mVideoListener.startPlayLocal(videoBean.videoURL, 0, this.mIsChangeingStream);
            this.mObservable.notifyObservers(new VideoTitleNotify(this.mBoxBean.videoName));
            this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_STREAM_INIT);
        }
    }

    public void playNextLebox(LeboxVideoBean videoBean) {
        playLebox(videoBean);
    }

    private void play(long vId) {
        if (!this.mShouldWaitDrmPluginInstall) {
            this.mRetrySeek = -1;
            if (vId != this.mVid || this.mLoadListener.getErrState() != 0) {
                sRequestRealUrlConsumetime = System.currentTimeMillis();
                this.mAlreadyPlayAd = false;
                this.mCdeStatusCode = -1;
                this.mIsLaunchPlay = false;
                if (this.mFrom == 15) {
                    this.mFrom = 1;
                }
                format(true);
                this.mVid = vId;
                this.mFrom = 16;
                if (!StatisticsUtils.sPlayFromCard) {
                    StatisticsUtils.setActionProperty(com.letv.pp.utils.NetworkUtils.DELIMITER_LINE, -1, UIsUtils.isLandscape(this.mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage);
                }
                this.mPlayInfo = new AlbumPlayInfo();
                StatisticsUtils.mClickImageForPlayTime = 0;
                this.mSeek = 0;
                createPlayRecord();
                statisticsLaunch(true);
                this.mLoadListener.loading();
                requestVideo();
            }
        }
    }

    public void format(boolean isFromInter) {
        clearRequest();
        this.mVideoListener.pause();
        this.mVideoListener.stopPlayback();
        this.mVideoListener.setEnforcementWait(false);
        this.mVideoListener.setEnforcementPause(false);
        this.mVideoListener.hideRecommendTip();
        this.mVideoListener.playAnotherVideo(isFromInter);
        this.mAdListener.cancelRequestFrontAdTask();
        if (this.mAdListener.getIVideoStatusInformer() != null) {
            this.mAdListener.getIVideoStatusInformer().destory();
            this.mAdListener.setIVideoStatusInformer(null);
        }
        this.mAdListener.setADPause(true);
        this.mAdListener.stopPlayback(false);
        this.mIVideoStatusInformer = null;
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_CONTROLLER_DISABLE);
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_HIDE_3G);
        this.mObservable.notifyObservers(new VideoTitleNotify(""));
        this.mOverloadProtectionState = OverloadProtectionState.NORMAL;
        this.mHasFetchDataSuccess = false;
        this.mIsStartPlay = false;
        this.mIsRetry = false;
        this.mIsCombineAd = false;
        this.mIsDownloadFile = false;
        this.mIsStarted = false;
        this.mIsSeparateAdFinished = true;
        this.mIsFrontAdFinished = true;
        this.mIsFromPush = false;
        this.mIsMidAdPlayed = false;
        this.mPlayRecord = null;
        this.mFilePath = null;
        this.mVideoFile = null;
        this.mNeedPlayAd = true;
        this.mRequestUrlController = null;
        this.mIsMidAdFinished = true;
        this.mPlayInfo.type14 = 0;
        this.mPlayInfo.mPlayByTicket = false;
        this.mBoxPlayedDuration = 0;
        this.mIsChangeingStream = false;
        if (this.mVideoType == VideoType.Drm) {
            this.mVideoType = VideoType.Normal;
        }
        clearAdInfo();
    }

    private void clearAdInfo() {
        this.mPlayInfo.frontAdDuration = 0;
        this.mPlayInfo.midAdPlayTime = -1;
        this.mPlayInfo.midDuration = 0;
        this.mShouldAskAdPrepare = true;
    }

    public void startPlayWith3g() {
        PreferencesManager.getInstance().setShow3gDialog(false);
        LogInfo.log("zhuqiao", "---onChange---star3g");
        this.mVideoListener.setEnforcementPause(false);
        if (this.mHasAd) {
            this.mAdListener.onResume();
        }
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_HIDE_3G);
        if (this.mIsStarted) {
            showToast3g();
            startPlayNet();
            return;
        }
        this.mLoadListener.loading();
        requestVideo();
    }

    private void showToast3g() {
        if (this.mIsWo3GUser || !NetworkUtils.isNetworkAvailable()) {
            return;
        }
        if (NetworkUtils.getNetworkType() == 2 || NetworkUtils.getNetworkType() == 3) {
            UIsUtils.showToast(TipUtils.getTipMessage("100006", R.string.play_net_2g3g4g_tag));
        }
    }

    protected void handlerFloatBall() {
        if (this.mCurrentPlayingVideo != null) {
            this.mVideoListener.handlerFloatBall("7", this.mCurrentPlayingVideo.cid);
        }
    }

    public ShackVideoInfoBean getShackVideoInfo() {
        long j = 0;
        if (this.mAid <= 0 || this.mVid <= 0) {
            return null;
        }
        ShackVideoInfoBean info = new ShackVideoInfoBean();
        info.aid = this.mAid;
        info.vid = this.mVid;
        if (this.mPlayInfo.currTime > 0) {
            j = this.mPlayInfo.currTime;
        }
        info.playtime = j / 1000;
        return info;
    }

    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(PlayObservable.ON_NET_CHANGE, notify)) {
                onNetChange();
            } else if (TextUtils.equals(PlayObservable.ON_CALL_STATE_IDLE, notify)) {
                statisticsLaunch(true);
            }
        }
    }

    public void statisticsLaunch(boolean hasFinishAd) {
        if (this.mAdListener != null) {
            LogInfo.LogStatistics("点播上报launch和init hasFinishAd:" + (!this.mAdListener.isPlaying()));
        }
        if (hasFinishAd) {
            this.mPlayInfo.mTotalConsumeTime = StatisticsUtils.mClickImageForPlayTime == 0 ? System.currentTimeMillis() : StatisticsUtils.mClickImageForPlayTime;
            LogInfo.log("jc666", "起播起始时间：" + this.mPlayInfo.mTotalConsumeTime);
        }
        if (StatisticsUtils.mClickImageForPlayTime != 0) {
            LogInfo.log("jc666", "点击视频到launch所耗时间：" + (System.currentTimeMillis() - StatisticsUtils.mClickImageForPlayTime));
        }
        if (TextUtils.isEmpty(this.mPlayInfo.mUuid)) {
            this.mPlayInfo.mUuid = DataUtils.getUUID(this.mContext);
        }
        if (this.mPlayInfo.mGlsbNum > 0) {
            this.mPlayInfo.mUuidTimp = this.mPlayInfo.mUuid + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mPlayInfo.mGlsbNum;
        } else {
            this.mPlayInfo.mUuidTimp = this.mPlayInfo.mUuid;
        }
        this.mPlayInfo.mIsStatisticsLoadTime = false;
        this.mPlayInfo.mHasCollectTimeToPlay = false;
        this.mPlayInfo.mBlockType = "play";
        PlayStatisticsUtils.staticsLauncher(this.mContext, this.mAid, this.mVid, this.mCid, this.mZid, this.mActivityLaunchMode, this.mPlayInfo.mUuidTimp, this.mPlayInfo.mIpt);
    }

    public void staticticsErrorInfo(Context mContext, String fl, String name, int wz, int id) {
        long aid = 0;
        long vid = 0;
        long cid = 0;
        try {
            if (this.mCurrentPlayingVideo != null) {
                aid = this.mCurrentPlayingVideo.pid;
                vid = this.mCurrentPlayingVideo.vid;
                cid = (long) this.mCurrentPlayingVideo.cid;
            }
            StatisticsUtils.statisticsActionInfo(mContext, UIsUtils.isLandscape(mContext) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, "20", fl, name, wz, null, DataUtils.getData(cid), DataUtils.getData(aid), DataUtils.getData(vid), null, null);
            LogInfo.LogStatistics("albumplayflow: aid=" + aid + "_vid=" + vid + "_cid=" + cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePlayDragStatistics(long startTime, long endTime) {
        updatePlayDataStatistics("drag", -1, "&dr=" + StringUtils.time2StrBySec(startTime / 1000) + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + StringUtils.time2StrBySec(endTime / 1000), false);
    }

    public void updatePlayDataStatistics(String actionCode, long pt) {
        updatePlayDataStatistics(actionCode, pt, null, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updatePlayDataStatistics(java.lang.String r95, long r96, java.lang.String r98, boolean r99) {
        /*
        r94 = this;
        if (r99 == 0) goto L_0x0011;
    L_0x0002:
        r0 = r94;
        r0 = r0.mLastPlayInfo;	 Catch:{ Exception -> 0x0345 }
        r87 = r0;
    L_0x0008:
        if (r87 != 0) goto L_0x0018;
    L_0x000a:
        r2 = "play info is null";
        com.letv.core.utils.LogInfo.LogStatistics(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x0010:
        return;
    L_0x0011:
        r0 = r94;
        r0 = r0.mPlayInfo;	 Catch:{ Exception -> 0x0345 }
        r87 = r0;
        goto L_0x0008;
    L_0x0018:
        r0 = r94;
        r1 = r87;
        r93 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&replaytype=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r3 = r0.mReplayType;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r2 = r0.mIsPlayTopic;	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x0069;
    L_0x0042:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&adconfig=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r2 = r0.mContext;	 Catch:{ Exception -> 0x0345 }
        r2 = com.letv.ads.ex.utils.AdsManagerProxy.getInstance(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.isShowAd();	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x034b;
    L_0x005b:
        r2 = 1;
    L_0x005c:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x0069:
        r2 = "time";
        r0 = r95;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0078;
    L_0x0074:
        r2 = com.letv.core.utils.StatisticsUtils.mIsHomeClicked;	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x0082;
    L_0x0078:
        r2 = "end";
        r0 = r95;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x034e;
    L_0x0082:
        r92 = 1;
    L_0x0084:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&bf=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x0352;
    L_0x0091:
        r0 = r87;
        r2 = r0.bufferNum;	 Catch:{ Exception -> 0x0345 }
    L_0x0095:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&bt=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x0355;
    L_0x00af:
        r0 = r87;
        r4 = r0.bufferTime;	 Catch:{ Exception -> 0x0345 }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 * r6;
        r2 = com.letv.core.utils.StringUtils.staticticsLoadTimeInfoFormat(r4);	 Catch:{ Exception -> 0x0345 }
    L_0x00ba:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&bf1=";
        r4 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x0358;
    L_0x00d4:
        r0 = r87;
        r2 = r0.userBfCount;	 Catch:{ Exception -> 0x0345 }
    L_0x00d8:
        r2 = r4.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&bt1=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x035c;
    L_0x00f2:
        r0 = r87;
        r4 = r0.userBfTimeTotal;	 Catch:{ Exception -> 0x0345 }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 * r6;
        r2 = com.letv.core.utils.StringUtils.staticticsLoadTimeInfoFormat(r4);	 Catch:{ Exception -> 0x0345 }
    L_0x00fd:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&bf2=";
        r4 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x035f;
    L_0x0117:
        r0 = r87;
        r2 = r0.autofCount;	 Catch:{ Exception -> 0x0345 }
    L_0x011b:
        r2 = r4.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&bt2=";
        r3 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x0363;
    L_0x0135:
        r0 = r87;
        r4 = r0.autoBfTimeTotal;	 Catch:{ Exception -> 0x0345 }
        r6 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r4 = r4 * r6;
        r2 = com.letv.core.utils.StringUtils.staticticsLoadTimeInfoFormat(r4);	 Catch:{ Exception -> 0x0345 }
    L_0x0140:
        r2 = r3.append(r2);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
        if (r92 == 0) goto L_0x0196;
    L_0x014f:
        r2 = 0;
        com.letv.core.utils.StatisticsUtils.mIsHomeClicked = r2;	 Catch:{ Exception -> 0x0345 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "need report bf=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r3 = r0.bufferNum;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r3 = ",bt=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r4 = r0.bufferTime;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0345 }
        r3 = ",bf2=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r4 = r0.autofCount;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0345 }
        r3 = ",bt2=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r4 = r0.autoBfTimeTotal;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        com.letv.core.utils.LogInfo.LogStatistics(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x0196:
        r2 = android.text.TextUtils.isEmpty(r98);	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x01a3;
    L_0x019c:
        r0 = r93;
        r1 = r98;
        r0.append(r1);	 Catch:{ Exception -> 0x0345 }
    L_0x01a3:
        r2 = com.letv.core.utils.StatisticsUtils.sMStartType;	 Catch:{ Exception -> 0x0345 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x01c5;
    L_0x01ab:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = "&utype=";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r3 = com.letv.core.utils.StatisticsUtils.sMStartType;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r93;
        r0.append(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x01c5:
        r0 = r87;
        r0 = r0.mAdsPlayFirstFrameTime;	 Catch:{ Exception -> 0x0345 }
        r88 = r0;
        r2 = 0;
        r2 = (r88 > r2 ? 1 : (r88 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x0366;
    L_0x01d1:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r3 = com.letv.core.utils.StringUtils.staticticsLoadTimeInfoFormat(r88);	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r57 = r2.toString();	 Catch:{ Exception -> 0x0345 }
    L_0x01e8:
        r59 = 1;
        r2 = com.letv.core.BaseApplication.getInstance();	 Catch:{ Exception -> 0x0345 }
        r2 = r2.getCdeHelper();	 Catch:{ Exception -> 0x0345 }
        r28 = r2.getServiceVersion();	 Catch:{ Exception -> 0x0345 }
        r29 = "3000";
        r20 = r94.getPlayRef();	 Catch:{ Exception -> 0x0345 }
        r30 = new com.letv.datastatistics.bean.StatisticsPlayInfo;	 Catch:{ Exception -> 0x0345 }
        r30.<init>();	 Catch:{ Exception -> 0x0345 }
        r2 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x0345 }
        r0 = r30;
        r0.setcTime(r2);	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r2 = r0.mIpt;	 Catch:{ Exception -> 0x0345 }
        r0 = r30;
        r0.setIpt(r2);	 Catch:{ Exception -> 0x0345 }
        r12 = "-";
        r14 = "-";
        r13 = "-";
        r27 = "-";
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0272;
    L_0x0221:
        r0 = r94;
        r2 = r0.mCid;	 Catch:{ Exception -> 0x0345 }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 > 0) goto L_0x036a;
    L_0x022b:
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.cid;	 Catch:{ Exception -> 0x0345 }
        r2 = (long) r2;	 Catch:{ Exception -> 0x0345 }
        r12 = com.letv.datastatistics.util.DataUtils.getData(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x0236:
        r0 = r94;
        r2 = r0.mVid;	 Catch:{ Exception -> 0x0345 }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 > 0) goto L_0x0374;
    L_0x0240:
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.vid;	 Catch:{ Exception -> 0x0345 }
        r14 = com.letv.datastatistics.util.DataUtils.getData(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x024a:
        r0 = r94;
        r2 = r0.mAid;	 Catch:{ Exception -> 0x0345 }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 > 0) goto L_0x037e;
    L_0x0254:
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.pid;	 Catch:{ Exception -> 0x0345 }
        r13 = com.letv.datastatistics.util.DataUtils.getData(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x025e:
        r0 = r94;
        r2 = r0.mZid;	 Catch:{ Exception -> 0x0345 }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 > 0) goto L_0x0388;
    L_0x0268:
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.zid;	 Catch:{ Exception -> 0x0345 }
        r27 = com.letv.datastatistics.util.DataUtils.getData(r2);	 Catch:{ Exception -> 0x0345 }
    L_0x0272:
        r0 = r94;
        r2 = r0.mIsPlayTopic;	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x027a;
    L_0x0278:
        r27 = "-";
    L_0x027a:
        r17 = "0";
        r0 = r94;
        r2 = r0.mIsDownloadFile;	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0392;
    L_0x0282:
        r17 = "3";
    L_0x0284:
        if (r99 != 0) goto L_0x0298;
    L_0x0286:
        r0 = r94;
        r2 = r0.mStreamLevel;	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r0.mVt = r2;	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r2 = r0.mAlbumUrl;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.realUrl;	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r0.mPlayUrl = r2;	 Catch:{ Exception -> 0x0345 }
    L_0x0298:
        r0 = r87;
        r0 = r0.mVt;	 Catch:{ Exception -> 0x0345 }
        r18 = r0;
        r0 = r87;
        r0 = r0.mPlayUrl;	 Catch:{ Exception -> 0x0345 }
        r19 = r0;
        r2 = android.text.TextUtils.isEmpty(r18);	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x02b4;
    L_0x02aa:
        r2 = "-";
        r0 = r18;
        r2 = android.text.TextUtils.equals(r0, r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x02b6;
    L_0x02b4:
        r18 = "13";
    L_0x02b6:
        r2 = "init";
        r0 = r95;
        r2 = r0.equals(r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x03bf;
    L_0x02c0:
        r2 = com.letv.datastatistics.DataStatistics.getInstance();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r3 = r0.mContext;	 Catch:{ Exception -> 0x0345 }
        r4 = "0";
        r5 = "0";
        r7 = "0";
        r8 = "-";
        r9 = "-";
        r10 = com.letv.core.utils.LetvUtils.getUID();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r11 = r0.mUuidTimp;	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r6 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        if (r6 != 0) goto L_0x039c;
    L_0x02e0:
        r15 = 0;
    L_0x02e1:
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r6.<init>();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r0 = r0.mRetryNum;	 Catch:{ Exception -> 0x0345 }
        r16 = r0;
        r0 = r16;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x0345 }
        r16 = "";
        r0 = r16;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x0345 }
        r16 = r6.toString();	 Catch:{ Exception -> 0x0345 }
        r21 = r93.toString();	 Catch:{ Exception -> 0x0345 }
        r22 = 0;
        r23 = 0;
        r24 = com.letv.core.utils.LetvUtils.getPcode();	 Catch:{ Exception -> 0x0345 }
        r6 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0345 }
        r6 = r6.isLogin();	 Catch:{ Exception -> 0x0345 }
        if (r6 == 0) goto L_0x03bb;
    L_0x0314:
        r25 = 0;
    L_0x0316:
        r26 = 0;
        r6 = r95;
        r2.sendPlayInfoInit(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30);	 Catch:{ Exception -> 0x0345 }
        r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x0345 }
        r3 = 8;
        if (r2 <= r3) goto L_0x032e;
    L_0x0323:
        r2 = cn.com.iresearch.vvtracker.IRVideo.getInstance();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r3 = r0.mContext;	 Catch:{ Exception -> 0x0345 }
        r2.videoPlay(r3);	 Catch:{ Exception -> 0x0345 }
    L_0x032e:
        r2 = "end";
        r0 = r95;
        r2 = r0.equals(r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0010;
    L_0x0338:
        if (r99 == 0) goto L_0x0573;
    L_0x033a:
        r2 = 0;
        r0 = r94;
        r0.mLastPlayInfo = r2;	 Catch:{ Exception -> 0x0345 }
    L_0x033f:
        r2 = "";
        com.letv.core.utils.StatisticsUtils.sLastPlayRef = r2;	 Catch:{ Exception -> 0x0345 }
        goto L_0x0010;
    L_0x0345:
        r90 = move-exception;
        r90.printStackTrace();
        goto L_0x0010;
    L_0x034b:
        r2 = 0;
        goto L_0x005c;
    L_0x034e:
        r92 = 0;
        goto L_0x0084;
    L_0x0352:
        r2 = 0;
        goto L_0x0095;
    L_0x0355:
        r2 = 0;
        goto L_0x00ba;
    L_0x0358:
        r2 = 0;
        goto L_0x00d8;
    L_0x035c:
        r2 = 0;
        goto L_0x00fd;
    L_0x035f:
        r2 = 0;
        goto L_0x011b;
    L_0x0363:
        r2 = 0;
        goto L_0x0140;
    L_0x0366:
        r57 = 0;
        goto L_0x01e8;
    L_0x036a:
        r0 = r94;
        r2 = r0.mCid;	 Catch:{ Exception -> 0x0345 }
        r12 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x0345 }
        goto L_0x0236;
    L_0x0374:
        r0 = r94;
        r2 = r0.mVid;	 Catch:{ Exception -> 0x0345 }
        r14 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x0345 }
        goto L_0x024a;
    L_0x037e:
        r0 = r94;
        r2 = r0.mAid;	 Catch:{ Exception -> 0x0345 }
        r13 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x0345 }
        goto L_0x025e;
    L_0x0388:
        r0 = r94;
        r2 = r0.mZid;	 Catch:{ Exception -> 0x0345 }
        r27 = java.lang.String.valueOf(r2);	 Catch:{ Exception -> 0x0345 }
        goto L_0x0272;
    L_0x0392:
        r0 = r94;
        r2 = r0.mIsScanVideo;	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0284;
    L_0x0398:
        r17 = "4";
        goto L_0x0284;
    L_0x039c:
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r6.<init>();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r15 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r0 = r15.duration;	 Catch:{ Exception -> 0x0345 }
        r22 = r0;
        r0 = r22;
        r6 = r6.append(r0);	 Catch:{ Exception -> 0x0345 }
        r15 = "";
        r6 = r6.append(r15);	 Catch:{ Exception -> 0x0345 }
        r15 = r6.toString();	 Catch:{ Exception -> 0x0345 }
        goto L_0x02e1;
    L_0x03bb:
        r25 = 1;
        goto L_0x0316;
    L_0x03bf:
        r2 = "play";
        r0 = r95;
        r2 = r0.equals(r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x049b;
    L_0x03ca:
        r2 = r94.getPayPropertyValue();	 Catch:{ Exception -> 0x0345 }
        r0 = r30;
        r0.setPay(r2);	 Catch:{ Exception -> 0x0345 }
        r91 = 0;
        r0 = r87;
        r2 = r0.mAdCount;	 Catch:{ Exception -> 0x0345 }
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 <= 0) goto L_0x03f1;
    L_0x03df:
        r0 = r87;
        r2 = r0.mAdCount;	 Catch:{ Exception -> 0x0345 }
        r4 = 1;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x047a;
    L_0x03e9:
        r91 = 2;
    L_0x03eb:
        r2 = 0;
        r0 = r87;
        r0.mAdCount = r2;	 Catch:{ Exception -> 0x0345 }
    L_0x03f1:
        r0 = r30;
        r1 = r91;
        r0.setJoint(r1);	 Catch:{ Exception -> 0x0345 }
        r31 = com.letv.datastatistics.DataStatistics.getInstance();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r0 = r0.mContext;	 Catch:{ Exception -> 0x0345 }
        r32 = r0;
        r33 = "0";
        r34 = "0";
        r36 = "0";
        r37 = "-";
        r38 = "-";
        r39 = com.letv.core.utils.LetvUtils.getUID();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r0 = r0.mUuidTimp;	 Catch:{ Exception -> 0x0345 }
        r40 = r0;
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x047e;
    L_0x041c:
        r44 = 0;
    L_0x041e:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r3 = r0.mRetryNum;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r45 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r50 = r93.toString();	 Catch:{ Exception -> 0x0345 }
        r51 = 0;
        r52 = 0;
        r53 = com.letv.core.utils.LetvUtils.getPcode();	 Catch:{ Exception -> 0x0345 }
        r2 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0345 }
        r2 = r2.isLogin();	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0498;
    L_0x044b:
        r54 = 0;
    L_0x044d:
        r55 = 0;
        r58 = "-";
        r35 = r95;
        r41 = r12;
        r42 = r13;
        r43 = r14;
        r46 = r17;
        r47 = r18;
        r48 = r19;
        r49 = r20;
        r56 = r27;
        r60 = r30;
        r31.sendPlayInfoPlay(r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55, r56, r57, r58, r59, r60);	 Catch:{ Exception -> 0x0345 }
        r2 = 1;
        r0 = r87;
        r0.mIsStatisticsPlay = r2;	 Catch:{ Exception -> 0x0345 }
        r2 = 0;
        r0 = r87;
        r0.mIsPlayingAds = r2;	 Catch:{ Exception -> 0x0345 }
        r0 = r17;
        r1 = r87;
        r1.mPlayType = r0;	 Catch:{ Exception -> 0x0345 }
        goto L_0x032e;
    L_0x047a:
        r91 = 1;
        goto L_0x03eb;
    L_0x047e:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r3 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r4 = r3.duration;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0345 }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r44 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        goto L_0x041e;
    L_0x0498:
        r54 = 1;
        goto L_0x044d;
    L_0x049b:
        r66 = "-";
        r2 = "time";
        r0 = r95;
        r2 = r0.equals(r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x04d5;
    L_0x04a8:
        r2 = 0;
        r2 = (r96 > r2 ? 1 : (r96 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0010;
    L_0x04ae:
        r2 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
        r2 = (r96 > r2 ? 1 : (r96 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x04d1;
    L_0x04b4:
        r2 = "jc666";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r3.<init>();	 Catch:{ Exception -> 0x0345 }
        r4 = "time pt exception:";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0345 }
        r0 = r96;
        r3 = r3.append(r0);	 Catch:{ Exception -> 0x0345 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x0345 }
        com.letv.core.utils.LogInfo.log(r2, r3);	 Catch:{ Exception -> 0x0345 }
        r96 = 180; // 0xb4 float:2.52E-43 double:8.9E-322;
    L_0x04d1:
        r66 = java.lang.String.valueOf(r96);	 Catch:{ Exception -> 0x0345 }
    L_0x04d5:
        r2 = com.letv.core.utils.StatisticsUtils.sLastPlayRef;	 Catch:{ Exception -> 0x0345 }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0553;
    L_0x04dd:
        r20 = r94.getPlayRef();	 Catch:{ Exception -> 0x0345 }
    L_0x04e1:
        r60 = com.letv.datastatistics.DataStatistics.getInstance();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r0 = r0.mContext;	 Catch:{ Exception -> 0x0345 }
        r61 = r0;
        r62 = "0";
        r63 = "0";
        r65 = "0";
        r67 = "-";
        r68 = com.letv.core.utils.LetvUtils.getUID();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r0 = r0.mUuidTimp;	 Catch:{ Exception -> 0x0345 }
        r69 = r0;
        r0 = r94;
        r2 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        if (r2 != 0) goto L_0x0556;
    L_0x0503:
        r73 = 0;
    L_0x0505:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r3 = r0.mRetryNum;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r74 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        r0 = r87;
        r0 = r0.mPlayType;	 Catch:{ Exception -> 0x0345 }
        r75 = r0;
        r79 = r93.toString();	 Catch:{ Exception -> 0x0345 }
        r80 = 0;
        r81 = 0;
        r82 = com.letv.core.utils.LetvUtils.getPcode();	 Catch:{ Exception -> 0x0345 }
        r2 = com.letv.core.db.PreferencesManager.getInstance();	 Catch:{ Exception -> 0x0345 }
        r2 = r2.isLogin();	 Catch:{ Exception -> 0x0345 }
        if (r2 == 0) goto L_0x0570;
    L_0x0538:
        r83 = 0;
    L_0x053a:
        r84 = 0;
        r64 = r95;
        r70 = r12;
        r71 = r13;
        r72 = r14;
        r76 = r18;
        r77 = r19;
        r78 = r20;
        r85 = r27;
        r86 = r30;
        r60.sendPlayInfo24New(r61, r62, r63, r64, r65, r66, r67, r68, r69, r70, r71, r72, r73, r74, r75, r76, r77, r78, r79, r80, r81, r82, r83, r84, r85, r86);	 Catch:{ Exception -> 0x0345 }
        goto L_0x032e;
    L_0x0553:
        r20 = com.letv.core.utils.StatisticsUtils.sLastPlayRef;	 Catch:{ Exception -> 0x0345 }
        goto L_0x04e1;
    L_0x0556:
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0345 }
        r2.<init>();	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r3 = r0.mCurrentPlayingVideo;	 Catch:{ Exception -> 0x0345 }
        r4 = r3.duration;	 Catch:{ Exception -> 0x0345 }
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x0345 }
        r3 = "";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x0345 }
        r73 = r2.toString();	 Catch:{ Exception -> 0x0345 }
        goto L_0x0505;
    L_0x0570:
        r83 = 1;
        goto L_0x053a;
    L_0x0573:
        r2 = 0;
        r0 = r87;
        r0.mIpt = r2;	 Catch:{ Exception -> 0x0345 }
        r2 = 0;
        r0 = r87;
        r0.mIsStatisticsPlay = r2;	 Catch:{ Exception -> 0x0345 }
        r2 = "0";
        r0 = r87;
        r0.mPlayType = r2;	 Catch:{ Exception -> 0x0345 }
        r0 = r94;
        r1 = r87;
        r0.resetTime(r1);	 Catch:{ Exception -> 0x0345 }
        goto L_0x033f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.business.flow.album.AlbumPlayFlow.updatePlayDataStatistics(java.lang.String, long, java.lang.String, boolean):void");
    }

    public void updateBlockDataStatistics(boolean isStart, long ut, boolean isFromHandler, String blockType) {
        try {
            int i;
            StringBuilder sb = getProperty(this.mPlayInfo);
            sb.append("&time=" + (this.mPlayInfo.currTime / 1000));
            if (isFromHandler) {
                sb.append("&isplayer=0&bytpe=" + blockType);
            } else {
                sb.append("&isplayer=1&bytpe=-");
            }
            StatisticsPlayInfo playInfo = new StatisticsPlayInfo();
            playInfo.setcTime(System.currentTimeMillis());
            playInfo.setIpt(this.mPlayInfo.mIpt);
            if (!isStart && ut > 0 && this.mPlayInfo.mFirstBlockTime == 0) {
                this.mPlayInfo.mFirstBlockTime = ut;
            }
            String type = "0";
            if (this.mIsDownloadFile) {
                type = "3";
            } else if (this.mIsScanVideo) {
                type = "4";
            }
            String cid = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
            String vid = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
            String aid = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
            String zid = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
            if (this.mCurrentPlayingVideo != null) {
                cid = this.mCid <= 0 ? DataUtils.getData((long) this.mCurrentPlayingVideo.cid) : String.valueOf(this.mCid);
                vid = this.mVid <= 0 ? DataUtils.getData(this.mCurrentPlayingVideo.vid) : String.valueOf(this.mVid);
                aid = this.mAid <= 0 ? DataUtils.getData(this.mCurrentPlayingVideo.pid) : String.valueOf(this.mAid);
                zid = this.mZid <= 0 ? DataUtils.getData(this.mCurrentPlayingVideo.zid) : String.valueOf(this.mZid);
            }
            if (!this.mIsPlayTopic) {
                zid = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
            }
            String actionCode = isStart ? "block" : "eblock";
            DataStatistics instance = DataStatistics.getInstance();
            Context context = this.mContext;
            String str = "0";
            String str2 = "0";
            String str3 = "0";
            String str4 = com.letv.pp.utils.NetworkUtils.DELIMITER_LINE;
            StringBuilder stringBuilder = new StringBuilder();
            if (ut <= 0) {
                ut = 0;
            }
            String stringBuilder2 = stringBuilder.append(ut).append("").toString();
            String uid = LetvUtils.getUID();
            String str5 = this.mPlayInfo.mUuidTimp;
            String str6 = this.mCurrentPlayingVideo == null ? null : this.mCurrentPlayingVideo.duration + "";
            String str7 = this.mPlayInfo.mRetryNum + "";
            String str8 = this.mStreamLevel;
            String str9 = this.mAlbumUrl.realUrl;
            String playRef = getPlayRef();
            String stringBuilder3 = sb.toString();
            String pcode = LetvUtils.getPcode();
            if (PreferencesManager.getInstance().isLogin()) {
                i = 0;
            } else {
                i = 1;
            }
            instance.sendPlayInfo24New(context, str, str2, actionCode, str3, str4, stringBuilder2, uid, str5, cid, aid, vid, str6, str7, type, str8, str9, playRef, stringBuilder3, null, null, pcode, i, null, zid, playInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuilder getProperty(AlbumPlayInfo playInfo) throws Exception {
        int i;
        int i2 = 1;
        StringBuilder sb = new StringBuilder();
        if (playInfo.mIsGslb) {
            sb.append("gslb=1&");
        } else {
            sb.append("gslb=0&");
        }
        if (playInfo.mIsCload) {
            sb.append("cload=1&");
        } else {
            sb.append("cload=0&");
        }
        if (this.mIsFromPush) {
            sb.append("push=1");
            sb.append("&type=" + StatisticsUtils.sStatisticsPushData.mContentType);
            sb.append("&pushtype=" + StatisticsUtils.sStatisticsPushData.mType);
            sb.append("&pushmsg=" + StatisticsUtils.sStatisticsPushData.mAllMsg);
        } else {
            sb.append("&push=0");
            sb.append("&pushtype=-&");
        }
        sb.append(playInfo.mVideoSend + "&");
        sb.append(playInfo.mVformat + "&");
        sb.append("&time1=").append(DataUtils.getData(playInfo.userFirstBfTime));
        sb.append("&time2=").append(DataUtils.getData(playInfo.autoFirstBfTime));
        if (this.mIsDownloadFile) {
            sb.append("&offline=1");
        }
        sb.append("&pay=" + getPayPropertyValue());
        sb.append("&speed=" + StatisticsUtils.getSpeed());
        if (StatisticsUtils.mType != null) {
            sb.append("&player=" + StatisticsUtils.mType);
        }
        sb.append("&sdk_ver=" + LetvMediaPlayerManager.getInstance().getSdkVersion());
        sb.append("&cpu=" + NativeInfos.getCPUClock());
        if ("ios".equals(BaseApplication.getInstance().getVideoFormat())) {
            sb.append("&cs=m3u8");
        } else if ("no".equals(BaseApplication.getInstance().getVideoFormat())) {
            sb.append("&cs=mp4");
        }
        StringBuilder append = new StringBuilder().append("&su=");
        if (playInfo.mCurrentState == -1) {
            i = 0;
        } else {
            i = 1;
        }
        sb.append(append.append(i).toString());
        sb.append("&cont=" + StatisticsUtils.sCont);
        sb.append("&is_rec=" + (StatisticsUtils.sPlayStatisticsRelateInfo.mIsRecommend ? "1" : Integer.valueOf(0)));
        sb.append("&reid=" + StatisticsUtils.sPlayStatisticsRelateInfo.mReid);
        StringBuilder append2 = sb.append("&vip=");
        if (!PreferencesManager.getInstance().isVip()) {
            i2 = 0;
        }
        append2.append(i2);
        return sb;
    }

    private String getPlayRef() {
        int i = 5;
        if (this.mIsPlayTopic) {
            if (this.mFrom == 16) {
                return StatisticsUtils.getPlayInfoRef(7);
            }
            return StatisticsUtils.getPlayInfoRef(-1);
        } else if (this.mFrom == 5) {
            if (UIsUtils.isLandscape(this.mContext)) {
                i = 6;
            }
            return StatisticsUtils.getPlayInfoRef(i);
        } else if (this.mFrom == 4 && !TextUtils.equals(PageIdConstant.playRecord, StatisticsUtils.getPageId())) {
            return StatisticsUtils.getPlayInfoRef(8);
        } else {
            if (!TextUtils.isEmpty(StatisticsUtils.sFrom)) {
                return StatisticsUtils.getPlayInfoRef(0);
            }
            if (StatisticsUtils.sPlayFromCard) {
                return StatisticsUtils.getPlayInfoRef(-1);
            }
            if (this.mFrom == 16) {
                return StatisticsUtils.getPlayInfoRef(7);
            }
            return StatisticsUtils.getPlayInfoRef(-1);
        }
    }

    private int getPayPropertyValue() {
        if (this.mPayInfo == null || this.mCurrentPlayingVideo == null || this.mTrailListener == null) {
            return 2;
        }
        if (this.mPlayInfo.mPlayByTicket) {
            return 3;
        }
        if (this.mPayInfo.tryLookTime > 0 && !this.mTrailListener.isVipTrailEnd()) {
            return 0;
        }
        if (this.mCurrentPlayingVideo.pay != 1) {
            return 2;
        }
        return this.mPayInfo.status == 1 ? 1 : 0;
    }

    public void resetTime(AlbumPlayInfo playInfo) {
        playInfo.mUpdateCount = 0;
        playInfo.lastTimeElapsed = 0;
        playInfo.timeElapsed = 0;
        playInfo.bufferNum = 0;
        playInfo.bufferTime = 0;
        playInfo.autoBfTime = 0;
        playInfo.autoBfTimeTotal = 0;
        playInfo.userBfCount = 0;
        playInfo.userBfTimeTotal = 0;
        playInfo.mIsStatisticsPlay = false;
    }

    public void setVideoChangeListener(VideoChangeListener videoChangeListener) {
        this.mVideoChangeListener = videoChangeListener;
    }
}
