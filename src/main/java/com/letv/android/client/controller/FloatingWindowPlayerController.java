package com.letv.android.client.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.activity.LetvVipDialogActivity;
import com.letv.android.client.commonlib.messagemodel.AlbumTask$AlbumPlayNexProtocol;
import com.letv.android.client.commonlib.view.PlayLoadLayout;
import com.letv.android.client.model.VideoAttributes;
import com.letv.android.client.utils.LetvPlayRecordFunction;
import com.letv.android.client.view.FloatingWindowPlayerView;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.component.player.LetvMediaPlayerControl;
import com.letv.component.player.LetvVideoViewBuilder;
import com.letv.component.player.LetvVideoViewBuilder.Type;
import com.letv.component.player.utils.NativeInfos;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.audiotrack.AudioTrackManager;
import com.letv.core.bean.AlbumPayInfoBean;
import com.letv.core.bean.DDUrlsResultBean;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.PlayRecord;
import com.letv.core.bean.TimestampBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.bean.VideoPlayerBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.PlayConstant.PlayerType;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.RealPlayUrlInfoParser;
import com.letv.core.parser.VideoPlayerParser;
import com.letv.core.subtitle.manager.SubtitleInfoManager;
import com.letv.core.subtitle.manager.SubtitleRenderManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.PlayUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.datastatistics.util.DataUtils;
import com.letv.download.manager.DownloadManager;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.io.File;
import java.util.ArrayList;

public class FloatingWindowPlayerController implements OnErrorListener, OnPreparedListener, OnCompletionListener, OnVideoViewStateChangeListener {
    public static final String BUNDLE_PARAM_FROM = "from";
    public static final String BUNDLE_PARAM_IS_DOLBY = "isDolby";
    public static final String BUNDLE_PARAM_IS_FROM_LOCAL = "isFromLocal";
    public static final String BUNDLE_PARAM_SEEK = "seek";
    public static final String BUNDLE_PARAM_URL = "url";
    public static final String BUNDLE_PARAM_VID = "vid";
    private static final int INVALID_INDEX = -1;
    protected static final int MSG_HIDE_LOADING_LAYOUT = 1;
    protected static final int MSG_SENDING_INTERVAL = 1000;
    protected static final int MSG_VIDEO_PLAYING = 0;
    public static final int SCREEN_OFF = 0;
    public static final int SCREEN_ON = 1;
    private static final String TAG = "FloatingWindowPlayer";
    public long bTime;
    public long mAid;
    private Bundle mBundle;
    private long mCid;
    private Context mContext;
    private String mControlViewTitle;
    public int mCurPage;
    private long mCurTime;
    protected DDUrlsResultBean mDDUrlsResult;
    protected DownloadDBBean mDownloadDBBean;
    protected String mFilePath;
    FloatingWindowPlayerView mFloatingWindowPlayerView;
    protected int mFrom;
    protected Handler mHandler;
    protected boolean mHasHd;
    protected boolean mHasStandard;
    public boolean mIsAlbum;
    protected boolean mIsDolby;
    private boolean mIsInitReport;
    public boolean mIsLocalFile;
    private boolean mIsSdkInitFail;
    protected boolean mIsShowSkipEnd;
    protected boolean mIsSkipOpenings;
    public boolean mIsWo3GUser;
    public int mLastVid;
    private LetvMediaPlayerControl mLetvMediaController;
    private long mLocalVideoPlayedPos;
    public int mMergeState;
    public String mOrder;
    protected AlbumPayInfoBean mPayInfo;
    protected int mPlayLevel;
    public AlbumTask$AlbumPlayNexProtocol mPlayNextProtocol;
    public PlayRecord mPlayRecord;
    protected String mRealPlayUrl;
    private long mScanTime;
    private int mScreenState;
    private SubtitleRenderManager mSubtitleManager;
    public int mTotal;
    long mTotalTime;
    public long mVid;
    private VideoBean mVideo;
    private long mVideoDispatchConsumeTime;
    private VideoFileBean mVideoFileBean;
    public long mVideoHeaderTime;
    private VideoAttributes mVideoParams;
    private int mVideoRecommendIndex;
    private ArrayList<HomeMetaData> mVideoRecommendList;
    private long mVideoTailTime;
    public long totleTime;

    public LetvMediaPlayerControl getMediaPlayerController() {
        return this.mLetvMediaController;
    }

    public FloatingWindowPlayerController(FloatingWindowPlayerView view, VideoAttributes attrs) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mScreenState = 1;
        this.mMergeState = 0;
        this.mOrder = "-1";
        this.mIsShowSkipEnd = true;
        this.mIsInitReport = false;
        this.mScanTime = 0;
        this.mVideoTailTime = 0;
        this.mIsWo3GUser = false;
        this.mIsSdkInitFail = false;
        this.mVideo = null;
        this.mVideoFileBean = null;
        this.mVideoRecommendIndex = 0;
        this.mTotal = 0;
        this.mHandler = new 1(this);
        this.mCurPage = 1;
        this.mIsLocalFile = false;
        this.mRealPlayUrl = null;
        init(view, attrs);
        start();
    }

    public LetvMediaPlayerControl getLetvMediaPlayerController() {
        return this.mLetvMediaController;
    }

    private void init(FloatingWindowPlayerView view, VideoAttributes attrs) {
        this.mFloatingWindowPlayerView = view;
        this.mVideoParams = attrs;
        this.mContext = view.getContext();
        this.mLetvMediaController = LetvVideoViewBuilder.getInstants().build(this.mContext, attrs.mediaType);
        this.mSubtitleManager = SubtitleRenderManager.getInstance();
        if (this.mVideoParams.album != null) {
            LogInfo.log(TAG, "FloatingWindow...mVideoMetaAttrs.album 对象不为NULL");
            this.mMergeState = LetvUtils.getMerge(this.mVideoParams.album.style);
            this.mOrder = LetvUtils.getOrder(this.mVideoParams.cid);
            this.mTotal = this.mMergeState == 0 ? this.mVideoParams.album.platformVideoInfo : this.mVideoParams.album.platformVideoNum;
        }
        readConfigParams();
        createPlayNextContorller();
    }

    private void createPlayNextContorller() {
        LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(this.mContext, new LeMessage(LeMessageIds.MSG_ALBUM_FETCH_PLAY_NEXT_CONTROLLER, PlayerType.FLOATING_WINDOW));
        if (LeResponseMessage.checkResponseMessageValidity(response, AlbumTask$AlbumPlayNexProtocol.class)) {
            this.mPlayNextProtocol = (AlbumTask$AlbumPlayNexProtocol) response.getData();
        }
        findNextVideo();
    }

    private void readConfigParams() {
        this.mIsSkipOpenings = PreferencesManager.getInstance().isSkip();
    }

    public void setScreenState(int state) {
        this.mScreenState = state;
    }

    protected void sendPlayingMessage() {
        this.mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    public void sendPlayingEndMessage() {
        this.mHandler.removeMessages(0);
    }

    public void sendHideLoadingMessage() {
        this.mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    public void setVideo(VideoBean vb) {
        this.mVideo = vb;
        if (vb != null) {
            this.mVideoTailTime = vb.etime;
            this.mVideoHeaderTime = vb.btime;
            LogInfo.log(TAG, "mVideoHeaderTime = " + this.mVideoHeaderTime);
            return;
        }
        this.mVideoTailTime = 0;
        this.mVideoHeaderTime = 0;
    }

    private void skipEpilogue() {
        if (this.mAid > 0) {
            onPlayNext();
        } else {
            onDestroy();
        }
    }

    public void updatePlayRecord() {
        submitPlayRecord();
    }

    private void submitPlayRecord() {
        if (this.mPlayRecord != null) {
            if (this.mLetvMediaController != null) {
                this.mPlayRecord.playedDuration = (long) (this.mLetvMediaController.getCurrentPosition() / 1000);
            }
            this.mCid = (long) this.mPlayRecord.channelId;
            if (!((this.mCid != 16 && this.mCid != 2 && this.mCid != 5 && this.mCid != 11 && this.mCid != 1021) || this.mPlayNextProtocol == null || this.mPlayNextProtocol.getNextVideoBean() == null)) {
                this.mPlayRecord.videoNextId = (int) this.mPlayNextProtocol.getNextVideoBean().vid;
            }
            LetvPlayRecordFunction.submitPlayTrace(this.mContext, this.mPlayRecord);
            DBManager.getInstance().getPlayTrace().insertPlayTraceByWatchedStatus((long) this.mPlayRecord.albumId, (long) this.mPlayRecord.videoId);
        }
    }

    public boolean isPlaying() {
        if (this.mLetvMediaController != null) {
            return this.mLetvMediaController.isPlaying();
        }
        return false;
    }

    public void onScreenResume() {
        if (this.mLetvMediaController != null) {
            LogInfo.log(TAG, "preparedPlay()");
            preparedPlay();
        }
    }

    public void start() {
        LogInfo.log(TAG, "FloatingWindowPlayerController.start()");
        if (this.mVideoParams.launchMode == 1) {
            this.mIsAlbum = true;
            this.mAid = this.mVideoParams.aid;
            this.mVid = this.mVideoParams.vid;
            LogInfo.log(TAG, "分支 ： PLAY_ALBUM mVid = " + this.mVid);
            this.mCurPage = this.mVideoParams.curPage;
            this.mPlayRecord = this.mVideoParams.playRecord;
            requestVideo(false);
        } else if (this.mVideoParams.launchMode == 2) {
            LogInfo.log(TAG, "分支 ： PLAY_VIDEO");
            this.mIsAlbum = false;
            this.mAid = this.mVideoParams.aid;
            this.mVid = this.mVideoParams.vid;
            this.mCurPage = this.mVideoParams.curPage;
            this.mPlayRecord = this.mVideoParams.playRecord;
            requestVideo(false);
        } else if (this.mVideoParams.launchMode == 3) {
            this.mAid = this.mVideoParams.aid;
            this.mVid = this.mVideoParams.vid;
            this.mCurPage = this.mVideoParams.curPage;
            this.mPlayRecord = this.mVideoParams.playRecord;
            LogInfo.log(TAG, "分支 ： PLAY_DOWNLOAD");
            checkDownload();
        } else {
            this.mIsLocalFile = true;
            this.mFilePath = this.mVideoParams.url;
            LogInfo.log(TAG, "分支 ： 本地视频 mFilePath = " + this.mFilePath);
            if (!TextUtils.isEmpty(this.mVideoParams.url)) {
                setControlViewTitle();
                performPlay(this.mVideoParams.url, this.mVideoParams.seek * 1000, true);
            }
        }
    }

    private void preparedPlay() {
        if (this.mScreenState == 1) {
            sendPlayingMessage();
            this.mLetvMediaController.start();
            return;
        }
        sendPlayingEndMessage();
        onPause();
    }

    public void requestVideo(boolean isPlayAlbum) {
        if (TimestampBean.getTm().mHasRecodeServerTime) {
            startRequestCache();
        } else {
            TimestampBean.getTm().getServerTimestamp(new 2(this));
        }
    }

    public void startRequestCache() {
        LogInfo.log(TAG, "请求缓存数据...");
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setCache(new 4(this)).setCallback(new 3(this)).add();
    }

    private void requestCache() {
        LogInfo.log(TAG, "请求本地视频...");
        requestLocalVideo();
        if (this.mVideo != null) {
            createPlayRecord();
            this.mPlayRecord.totalDuration = this.mVideo.duration;
        }
    }

    private void requestLocalVideo() {
        DownloadDBBean dbBean = DownloadManager.getLocalVideoBean(this.mVid);
        if (dbBean == null) {
            this.mIsLocalFile = false;
            return;
        }
        this.mIsLocalFile = true;
        VideoBean vb = new VideoBean();
        vb.vid = (long) dbBean.vid;
        vb.cid = dbBean.cid;
        vb.mid = dbBean.mmsid;
        vb.pid = (long) dbBean.aid;
        vb.nameCn = dbBean.episodetitle;
        vb.etime = dbBean.etime;
        vb.btime = dbBean.btime;
        vb.videoTypeKey = LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN;
        if (this.mVideo != null) {
            vb.watchingFocusList = this.mVideo.watchingFocusList;
        }
        if (LetvUtils.getClientVersionCode() < 87 || TextUtils.isEmpty(vb.videoTypeKey)) {
            vb.videoTypeKey = LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN;
        } else {
            vb.videoTypeKey = vb.videoTypeKey;
        }
        this.mVideo = vb;
    }

    public void requestNetwork() {
        String url = MediaAssetApi.getInstance().getVideoPlayUrl(this.mCid + "", this.mAid + "", null, this.mVid + "", PreferencesManager.getInstance().getUserId(), BaseApplication.getInstance().getVideoFormat(), "0", String.valueOf(TimestampBean.getTm().getCurServerTime()), "", null);
        LogInfo.log(TAG, "请求网络播放...");
        LogInfo.log(TAG, "请求网络播放...url = " + url);
        this.mVideoDispatchConsumeTime = System.currentTimeMillis();
        new LetvRequest().setUrl(url).setMaxRetries(2).setCache(new VolleyNoCache()).setParser(new VideoPlayerParser()).setTag("videoPlayUrl").setCallback(new 5(this)).add();
    }

    private void startPlayLocal() {
        if (this.mDownloadDBBean != null) {
            DBManager.getInstance().getPlayTrace().insertPlayTraceByWatchedStatus((long) this.mDownloadDBBean.aid, (long) this.mDownloadDBBean.vid);
        }
        if (this.mPlayRecord != null) {
            if (this.mFrom == 15) {
                long time = (!this.mIsSkipOpenings || this.mVideoHeaderTime <= 0 || this.mScanTime < 0 || this.mScanTime >= this.mVideoHeaderTime) ? this.mScanTime : this.mVideoHeaderTime;
                this.mPlayRecord.playedDuration = time;
            } else if (this.mIsSkipOpenings && this.mVideoHeaderTime > 0 && this.mPlayRecord.playedDuration <= 0) {
                this.mPlayRecord.playedDuration = this.mVideoHeaderTime;
            }
        }
        play();
        this.mDownloadDBBean = null;
    }

    private void checkCanPlay() {
        PlayLoadLayout pll = this.mFloatingWindowPlayerView.getPlayLoadLayout();
        if (this.mVideo.canPlay()) {
            LogInfo.log(TAG, "视频可以播放");
            if (pll.isShowLoading()) {
                LogInfo.log(TAG, "当前正在显示即将播放...");
                pll.loadingVideo(this.mVideo.nameCn);
                LogInfo.log(TAG, "显示标题: " + this.mVideo.nameCn);
            }
            if (this.mVideoFileBean == null) {
                this.mVideoFileBean = new VideoFileBean();
            }
            if (this.mVideoFileBean.isIpEnable) {
                LogInfo.log(TAG, "所属地域IP未屏蔽，检查是否联通免流量");
                checkWoFree();
                return;
            }
            String msg = this.mVideoFileBean.message;
            String country = this.mVideoFileBean.country;
            boolean isHK = TextUtils.equals(LetvUtils.COUNTRY_HONGKONG, country);
            if (TextUtils.isEmpty(msg)) {
                pll.ipError();
            } else {
                pll.ipError(msg, isHK);
            }
            String errCode = null;
            if (!TextUtils.isEmpty(country)) {
                if (isHK || country.equals(LetvUtils.COUNTRY_CHINA)) {
                    errCode = LetvErrorCode.VIDEO_FOREIGN_SHIELD;
                } else {
                    errCode = LetvErrorCode.REQUEST_CANPLAY_ERROR;
                }
            }
            setPlayErrorCode(errCode, true);
            return;
        }
        LogInfo.log(TAG, "视频不可播放");
        pll.jumpError();
        setPlayErrorCode("", false);
    }

    private void setPlayErrorCode(String code, boolean isShow) {
    }

    public boolean isNeedPay() {
        return this.mVideo.pay == 1 && this.mPayInfo.status == 0;
    }

    protected void onNetworkResponseSuccess(VideoPlayerBean result, DataHull hull) {
        setVideo(result.video);
        this.mVideoFileBean = result.videoFile;
        this.mPayInfo = result.payInfo;
        LogInfo.log(TAG, "mVideo.pay = " + this.mVideo.pay + " mPayInfo.status = " + this.mPayInfo.status);
        if (isNeedPay()) {
            this.mFloatingWindowPlayerView.loadVipAuthenticationView();
        } else {
            checkCanPlay();
        }
    }

    private void checkWoFree() {
        if (NetworkUtils.isUnicom3G(false) && UnicomWoFlowManager.getInstance().supportWoFree()) {
            UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.mContext, new 6(this));
        } else {
            requestRealPlayUrl();
        }
    }

    private int genRealPlayLevel() {
        int playLevel = PreferencesManager.getInstance().getPlayLevel();
        int memPlayLevel = LetvApplication.getInstance().getMemoryPlayLevel();
        if (!this.mVideoFileBean.isDeclineStream || LetvApplication.getInstance().isSettingPlayLevel()) {
            return playLevel;
        }
        if (memPlayLevel == -1) {
            return this.mVideoFileBean.getDeclineStreamLevel();
        }
        return memPlayLevel;
    }

    private void requestRealPlayUrl() {
        boolean z = true;
        LogInfo.log(TAG, "请求真实播放地址");
        int playLevel = genRealPlayLevel();
        VideoFileBean videoFileBean = this.mVideoFileBean;
        boolean z2 = this.mVideo != null ? this.mVideo.pay == 1 : false;
        DDUrlsResultBean bean = PlayUtils.getDDUrls(videoFileBean, playLevel, z2, this.mIsDolby ? VideoType.Dolby : VideoType.Normal);
        if (bean != null) {
            this.mPlayLevel = bean.playLevel;
            if (bean.videoType != VideoType.Dolby) {
                z = false;
            }
            this.mIsDolby = z;
            this.mHasHd = bean.hasHigh;
            this.mHasStandard = bean.hasLow;
            this.mDDUrlsResult = bean;
        }
        String audioTrackId = AudioTrackManager.getInstance().obtainId(this.mVideoFileBean, this.mVideoParams.languageSettings, this.mPlayLevel, this.mIsDolby);
        LogInfo.log("wuxinrong", "传过来的音轨ID = " + audioTrackId);
        String linkShellUrl = PlayUtils.getURLFromLinkShell(PlayUtils.getDdUrl(this.mDDUrlsResult.getDispatchUrl(), PlayUtils.getPlayToken(this.mDDUrlsResult, null), PreferencesManager.getInstance().getUserId(), this.mVid + "", DataUtils.getUUID(this.mContext), audioTrackId, this.mIsWo3GUser), "");
        LogInfo.log(TAG, "小窗播放linkShellUrl = " + linkShellUrl);
        new LetvRequest().setUrl(linkShellUrl).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setParser(new RealPlayUrlInfoParser()).setMaxRetries(2).setCallback(new 7(this)).add();
    }

    protected void onNetworkResponseError() {
        this.mFloatingWindowPlayerView.getPlayLoadLayout().requestError();
    }

    private void play() {
        int skippedTime;
        LogInfo.log(TAG, "开始播放，跳过片头吗？ = " + this.mIsSkipOpenings);
        if (!this.mIsSkipOpenings || this.mCurTime > 0 || ((this.mPlayRecord == null || this.mPlayRecord.playedDuration > 0) && this.mPlayRecord != null)) {
            skippedTime = ((int) this.mPlayRecord.playedDuration) * 1000;
        } else {
            skippedTime = ((int) this.mVideoHeaderTime) * 1000;
            LogInfo.log(TAG, "跳过片头" + skippedTime);
        }
        LogInfo.log(TAG, "跳过时间 = " + skippedTime);
        this.mVideoParams.seek = 0;
        if (this.mIsLocalFile) {
            changeVideoView(false);
            this.mRealPlayUrl = null;
        } else {
            changeVideoView(true);
            this.mFilePath = null;
        }
        setControlViewTitle();
        performPlay(this.mIsLocalFile ? this.mFilePath : this.mRealPlayUrl, (long) skippedTime, this.mIsLocalFile);
    }

    private void findNextVideo() {
        if (this.mVideoParams.albumPageCard != null && this.mPlayNextProtocol != null) {
            this.mPlayNextProtocol.findNextVideo(this.mVideoParams.albumCardList, this.mVideo, this.mVideoParams.albumPageCard, null);
        }
    }

    public void initNativeInfos() {
        NativeInfos.mOffLinePlay = false;
        NativeInfos.mIsLive = this.mVideoParams.isLive;
        String format = LetvApplication.getInstance().getVideoFormat();
        if ("ios".equals(format)) {
            NativeInfos.mOffLinePlay = false;
            NativeInfos.mIsLive = false;
        } else if ("no".equals(format)) {
            NativeInfos.mOffLinePlay = true;
            NativeInfos.mIfNative3gpOrMp4 = true;
            NativeInfos.mIsLive = false;
        }
        if (this.mIsDolby) {
            NativeInfos.mOffLinePlay = true;
            NativeInfos.mIfNative3gpOrMp4 = true;
        }
    }

    private void changeVideoView(boolean playOnline) {
        Type type = Type.MOBILE_H264_MP4;
        String format = LetvApplication.getInstance().getVideoFormat();
        if (!(!playOnline || "no".equals(format) || this.mIsDolby)) {
            type = Type.MOBILE_H264_M3U8;
        }
        if (this.mVideoParams.mediaType != type) {
            this.mVideoParams.mediaType = type;
            this.mFloatingWindowPlayerView.getMediaPlayerContainer().removeAllViews();
            this.mLetvMediaController.stopPlayback();
            this.mLetvMediaController = LetvVideoViewBuilder.getInstants().build(this.mContext, type);
            this.mFloatingWindowPlayerView.getMediaPlayerContainer().setGravity(17);
            this.mFloatingWindowPlayerView.getMediaPlayerContainer().addView(this.mLetvMediaController.getView(), new LayoutParams(-1, -1));
        }
    }

    private void showMobileNetworkTipMessage() {
        UIsUtils.showToast(TipUtils.getTipMessage("100006", 2131100656));
    }

    private void setControlViewTitle() {
        if (this.mIsLocalFile) {
            LogInfo.log(TAG, "===本地视频");
            if (this.mPlayRecord != null) {
                this.mControlViewTitle = this.mPlayRecord.title;
            } else {
                this.mControlViewTitle = parseTitleFromUrl(this.mFilePath);
            }
        } else {
            this.mControlViewTitle = this.mVideo.nameCn;
        }
        LogInfo.log(TAG, "设置的标题 = " + this.mControlViewTitle);
    }

    public String getControlViewTitle() {
        return this.mControlViewTitle;
    }

    public void performPlay(String url, long skippedTime, boolean isLocal) {
        if (isLocal) {
            NativeInfos.mIsLive = false;
            NativeInfos.mOffLinePlay = true;
            NativeInfos.doWithNativePlayUrl(url);
            initNativeInfos();
        } else {
            if (!(this.mIsWo3GUser || NetworkUtils.getNetworkType() == 1 || this.mVideoParams.launchMode == 0)) {
                showMobileNetworkTipMessage();
            }
            findNextVideo();
            this.mFloatingWindowPlayerView.getMediaControlView().initNextButtonState();
        }
        this.mLetvMediaController.setVideoViewStateChangeListener(this);
        this.mLetvMediaController.setOnErrorListener(this);
        this.mLetvMediaController.setOnCompletionListener(this);
        this.mLetvMediaController.setOnPreparedListener(this);
        this.mLetvMediaController.getView().requestFocus();
        this.mLetvMediaController.setVideoPath(url);
        if (skippedTime > 0) {
            this.mLetvMediaController.seekTo((int) skippedTime);
        }
    }

    private String parseTitleFromUrl(String url) {
        String title = "";
        File file = new File(url);
        if (file == null) {
            return title;
        }
        title = file.getName();
        if (TextUtils.isEmpty(title) || !title.contains(".")) {
            return title;
        }
        return String.valueOf(title.subSequence(0, title.lastIndexOf(".")));
    }

    private boolean isVideoDownloaded() {
        LogInfo.log(TAG, "本地缓存的该视频的vid = " + this.mVid);
        this.mDownloadDBBean = DownloadManager.getLocalVideoBean(this.mVid);
        if (this.mDownloadDBBean == null) {
            return false;
        }
        this.mIsLocalFile = true;
        this.mFilePath = this.mDownloadDBBean.filePath;
        return true;
    }

    private void checkPlayRecord() {
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setCache(new 9(this)).setCallback(new 8(this)).add();
    }

    private void checkDownload() {
        new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setCache(new 11(this)).setCallback(new 10(this)).add();
    }

    private void createPlayRecord() {
        if (this.mPlayRecord == null) {
            this.mPlayRecord = new PlayRecord();
            this.mPlayRecord.albumId = (int) this.mAid;
            if (this.mVideo != null) {
                this.mPlayRecord.videoType = this.mVideo.type;
                this.mPlayRecord.img300 = this.mVideo.pic200_150;
                this.mPlayRecord.title = getTitle(this.mVideo);
                LogInfo.log("wuxinrong", "------final-----创建播放记录 videotypekey = " + this.mVideo.videoTypeKey);
                this.mPlayRecord.videoTypeKey = this.mVideo.videoTypeKey;
                this.mPlayRecord.channelId = this.mVideo.cid;
                this.mPlayRecord.img = this.mVideo.pic120_90;
                this.mPlayRecord.curEpsoid = BaseTypeUtils.stof(this.mVideo.episode);
                this.mPlayRecord.totalDuration = this.mVideo.duration;
            } else if (this.mVideoParams.album != null) {
                this.mPlayRecord.videoType = this.mVideo.type;
                this.mPlayRecord.img300 = this.mVideo.pic200_150;
            }
            this.mPlayRecord.from = 2;
            if (this.mVideoParams.seek > 0) {
                this.mPlayRecord.playedDuration = this.mVideoParams.seek / 1000;
            } else {
                this.mPlayRecord.playedDuration = 0;
            }
            this.mPlayRecord.videoId = (int) this.mVid;
            this.mCurTime = this.mPlayRecord.playedDuration * 1000;
            this.mTotalTime = this.mPlayRecord.totalDuration * 1000;
            this.mPlayRecord.updateTime = System.currentTimeMillis();
        } else if (this.mVideo != null) {
            this.mPlayRecord.videoTypeKey = this.mVideo.videoTypeKey;
        }
    }

    public void setBundle(Bundle bundle) {
        this.mBundle = bundle;
    }

    private String getTitle(VideoBean video) {
        if (video == null) {
            return "";
        }
        String name = "";
        if (!TextUtils.equals(this.mContext.getString(2131099849), LetvUtils.getChannelName(this.mContext, video.cid))) {
            return video.nameCn;
        }
        return video.nameCn + "  " + BaseTypeUtils.ensureStringValidate(video.singer);
    }

    public void onFinish() {
        onPause();
        onStop();
    }

    public void onDestroy() {
        onFinish();
        FloatingWindowPlayerHelper.closeFloatingWindow(this.mContext);
    }

    public void onPause() {
        LogInfo.log(TAG, "FloatingWindowPlayerController.onPause()");
        if (this.mLetvMediaController.isPlaying()) {
            LogInfo.log(TAG, "执行内核暂停");
            this.mLetvMediaController.pause();
            sendPlayingEndMessage();
            return;
        }
        LogInfo.log(TAG, "执行内核播放");
        this.mLetvMediaController.start();
    }

    public void onStop() {
        LogInfo.log(TAG, "FloatingWindowPlayerController.onStop()");
        this.mLetvMediaController.stopPlayback();
    }

    public void onPlayNext() {
        sendPlayingEndMessage();
        onStop();
        this.mFloatingWindowPlayerView.getPlayLoadLayout().loading(true);
        this.mFloatingWindowPlayerView.getBackgroundView().setVisibility(0);
        this.mCurTime = 0;
        setFileSource(false);
        this.mFilePath = null;
        if (!isRecommended()) {
            if (this.mVideo == null) {
                LogInfo.log(TAG, "mVideo 为空！");
                onDestroy();
            }
            LogInfo.log(TAG, "准备播放下一集...");
            if (this.mPlayNextProtocol == null) {
                onDestroy();
            } else if (!NetworkUtils.isNetworkAvailable()) {
                this.mPlayNextProtocol.findNextVideo(this.mVideoParams.albumCardList, this.mVideo, this.mVideoParams.albumPageCard, new 12(this));
            } else if (this.mPlayNextProtocol.getNextVideoBean() != null) {
                play(this.mPlayNextProtocol.getNextVideoBean());
            } else {
                onDestroy();
            }
        }
    }

    public void play(VideoBean video) {
        if (video.vid != this.mVid) {
            onStop();
            this.mVid = video.vid;
            setVideo(video);
            setFileSource(false);
            this.mFilePath = null;
            this.mPlayRecord = null;
            createPlayRecord();
            requestVideo(false);
        }
    }

    public void setFileSource(boolean isLocal) {
        this.mIsLocalFile = isLocal;
        this.mFloatingWindowPlayerView.getMediaControlView().setFileSource(isLocal);
        if (isLocal) {
            this.mFloatingWindowPlayerView.getMediaControlView().setRealPlayUrl(null);
            this.mRealPlayUrl = null;
        }
    }

    private boolean isRecommended() {
        if (this.mVideoRecommendList == null || this.mVideoRecommendList.isEmpty() || !isVideoRecommend()) {
            return false;
        }
        if (isVideoRecommendIndexValid()) {
            playVideoRecommend();
            return true;
        }
        LogInfo.log(TAG, "推荐的视频索引不合法");
        onFinish();
        return false;
    }

    private void playVideoRecommend() {
        HomeMetaData hmd = (HomeMetaData) this.mVideoRecommendList.get(this.mVideoRecommendIndex);
        if (hmd != null) {
            LogInfo.log(TAG, "播放第一个推荐的视频");
            this.mCurPage = 0;
            this.mCid = (long) hmd.cid;
            this.mAid = (long) hmd.pid;
            this.mVid = (long) hmd.vid;
            checkPlayRecord();
            return;
        }
        onFinish();
    }

    private boolean isVideoRecommendIndexValid() {
        if (this.mVideoRecommendList == null) {
            return false;
        }
        int size = this.mVideoRecommendList.size();
        if (size <= 0 || this.mVideoRecommendIndex >= size) {
            return false;
        }
        return true;
    }

    private boolean isVideoRecommend() {
        if (this.mVideoRecommendList != null && this.mVideoRecommendList.size() > 0) {
            for (int i = 0; i < this.mVideoRecommendList.size(); i++) {
                if (this.mVideo.vid == ((long) ((HomeMetaData) this.mVideoRecommendList.get(i)).vid)) {
                    return true;
                }
            }
        }
        return false;
    }

    public VideoAttributes getVideoMetaAttributes() {
        return this.mVideoParams;
    }

    public Bundle getPlayBundle() {
        this.mBundle.putLong("vid", this.mVid);
        this.mBundle.putString("url", this.mFilePath);
        if (this.mIsLocalFile) {
            LogInfo.log(TAG, "本地视频，返回时间点 = " + this.mVideoParams.seek + " s");
            this.mBundle.putLong("seek", this.mVideoParams.seek);
        } else {
            LogInfo.log(TAG, "不是本地视频，返回时间点 = " + this.mLocalVideoPlayedPos + " ms");
            this.mBundle.putLong("seek", this.mLocalVideoPlayedPos);
        }
        this.mBundle.putBoolean(BUNDLE_PARAM_IS_FROM_LOCAL, this.mIsLocalFile);
        this.mBundle.putBoolean(BUNDLE_PARAM_IS_DOLBY, this.mIsDolby);
        this.mBundle.putInt("from", 21);
        this.mBundle.putInt("launch_mode", this.mVideoParams.launchMode);
        return this.mBundle;
    }

    public void onPlayComplete() {
        LogInfo.log(TAG, "根据是否是本地视频，决定是否自动续播下一集(期)");
        if (this.mVideoParams.launchMode == 0) {
            onDestroy();
        } else {
            onPlayNext();
        }
    }

    public void onCompletion(MediaPlayer arg0) {
        LogInfo.log(TAG, "播放器认为播放完成");
        onPlayComplete();
    }

    public void onPrepared(MediaPlayer arg0) {
        preparedPlay();
        LogInfo.log(TAG, "onPrepared 显示底部控制栏");
        this.mFloatingWindowPlayerView.getMediaControlView().show();
    }

    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        LogInfo.log(TAG, "onError()");
        return false;
    }

    private void prepareSubtitle() {
        this.mSubtitleManager.init(this.mContext, this.mFloatingWindowPlayerView.getMediaControlView(), this.mLetvMediaController);
        SubtitleInfoManager.getInstance().createSubtitleInfo(this.mContext, this.mVideoFileBean, this.mVideoParams.languageSettings);
        LogInfo.log("wuxinrong", "开始解析字幕");
        SubtitleRenderManager.getInstance().parse(SubtitleInfoManager.getInstance().getUri());
    }

    public void onChange(int state) {
        switch (state) {
            case 2:
                prepareSubtitle();
                return;
            case 3:
                this.mFloatingWindowPlayerView.getMediaControlView().setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                this.mFloatingWindowPlayerView.getMediaControlView().setVisibility(0);
                this.mSubtitleManager.start();
                this.mHandler.sendEmptyMessage(1);
                return;
            case 4:
                this.mSubtitleManager.pause();
                return;
            case 5:
                this.mSubtitleManager.stop();
                AudioTrackManager.getInstance().reset();
                SubtitleInfoManager.getInstance().reset();
                return;
            case 6:
                this.mSubtitleManager.stop();
                AudioTrackManager.getInstance().reset();
                SubtitleInfoManager.getInstance().reset();
                return;
            default:
                return;
        }
    }

    public void onOpenMember() {
        Context context = this.mFloatingWindowPlayerView.getContext();
        if (PreferencesManager.getInstance().isLogin()) {
            LetvVipDialogActivity.launch(context, this.mVideo.nameCn);
        } else {
            LetvLoginActivity.launch(context);
        }
    }

    public void onLoginRightNow() {
        LetvLoginActivity.launch(this.mFloatingWindowPlayerView.getContext());
    }
}
