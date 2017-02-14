package com.letv.business.flow.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.letv.ads.ex.client.IVideoStatusInformer;
import com.letv.business.flow.album.controller.AlbumFlowRequestUrlController;
import com.letv.business.flow.album.model.AlbumPlayInfo;
import com.letv.business.flow.album.model.AlbumStreamSupporter;
import com.letv.business.flow.album.model.AlbumUrl;
import com.letv.core.BaseApplication;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPayInfoBean;
import com.letv.core.bean.DDUrlsResultBean;
import com.letv.core.bean.DownloadDBListBean;
import com.letv.core.bean.DownloadDBListBean.DownloadDBBean;
import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.LanguageSettings;
import com.letv.core.bean.PlayRecord;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.bean.VideoPlayerBean.AdInfoBean;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StringUtils;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import master.flame.danmaku.danmaku.parser.IDataSource;

public abstract class AlbumPlayBaseFlow {
    public static String sRef = "";
    public static long sRequestAdsCallbackConsumetime = 0;
    public static long sRequestAdsConsumetime = 0;
    public static long sRequestLocalConsumetime = 0;
    public static long sRequestRealUrlConsumetime = 0;
    public static long sRequestRecodeConsumetime = 0;
    public static long sToPlayConsumetime = 0;
    public int mActivityLaunchMode;
    public AdInfoBean mAdInfo;
    public long mAid = 0;
    public final AlbumUrl mAlbumUrl = new AlbumUrl();
    public boolean mAlreadyPlayAd = false;
    public boolean mBackToOriginalApp;
    public LeboxVideoBean mBoxBean;
    public long mBoxPlayedDuration;
    public int mCdeStatusCode = -1;
    public long mCid = 0;
    public Context mContext;
    public int mCurPage = 1;
    public boolean mCurrDownloadFileIsHd = false;
    public VideoBean mCurrentPlayingVideo;
    public DDUrlsResultBean mDdUrlsResult;
    public DownloadDBBean mDownloadDBBean;
    public DownloadDBListBean mDownloadDBListBean;
    public boolean mDownloadHd;
    public int mDownloadStreamLevel = 0;
    public PlayErrorState mErrorState = PlayErrorState.NORMAL;
    public String mFilePath;
    public long mFirstRequest = 0;
    public VideoBean mFirstVideo;
    public int mFrom;
    protected Handler mHandler = new Handler();
    public boolean mHardDecode = false;
    public boolean mHasAd = false;
    protected boolean mHasCancelWo = false;
    public boolean mHasFetchDataSuccess = false;
    public IVideoStatusInformer mIVideoStatusInformer;
    public boolean mIsChangeingStream;
    public boolean mIsClickShipAd = false;
    public boolean mIsCombineAd;
    public boolean mIsDownloadFile = false;
    public boolean mIsFirstPlay;
    public boolean mIsFromPush = false;
    public boolean mIsFrontAdFinished = true;
    public boolean mIsInitReport = false;
    public boolean mIsLaunchPlay = true;
    public boolean mIsMidAdFinished = true;
    public boolean mIsMidAdPlayed = false;
    public boolean mIsNonCopyright;
    public boolean mIsPauseAdIsShow = false;
    public boolean mIsPlayFreeUrl = false;
    protected boolean mIsPlayTopic = false;
    public boolean mIsRetry = false;
    public boolean mIsScanVideo;
    public boolean mIsSdkInitFail = false;
    public boolean mIsSeparateAdFinished = true;
    public boolean mIsShowSkipEnd = true;
    public boolean mIsSkip;
    public boolean mIsStartPlay;
    public boolean mIsStartPlayLocalDownloadEnter = false;
    public boolean mIsStarted;
    public boolean mIsUseCde = true;
    public boolean mIsWo3GUser = false;
    public LanguageSettings mLanguageSettings;
    public AlbumPlayInfo mLastPlayInfo;
    public int mLaunchMode;
    public final List<Integer> mLevelList = new ArrayList();
    public String mLocalPath;
    public long mLocalSeek;
    public boolean mNeedPlayAd = true;
    public int mOldNetState = -1;
    public OverloadProtectionState mOverloadProtectionState = OverloadProtectionState.NORMAL;
    public AlbumPayInfoBean mPayInfo;
    public AlbumPlayInfo mPlayInfo = new AlbumPlayInfo();
    public int mPlayLevel;
    public PlayRecord mPlayRecord;
    public long mRequestStep = 500;
    protected AlbumFlowRequestUrlController mRequestUrlController;
    public int mRetrySeek = -1;
    public long mSeek;
    public int mSelectStream = -1;
    public boolean mShouldAskAdPrepare = true;
    public boolean mShouldDeclineStream;
    public boolean mShouldWaitDrmPluginInstall;
    public String mStreamLevel;
    public final AlbumStreamSupporter mStreamSupporter = new AlbumStreamSupporter();
    public long mVid = 0;
    public AlbumInfo mVideoBelongedAlbum;
    public VideoFileBean mVideoFile;
    public HomeMetaData mVideoRecommend;
    public int mVideoRecommendIndex = 0;
    public ArrayList<HomeMetaData> mVideoRecommendList;
    public VideoType mVideoType = VideoType.Normal;
    @SuppressLint({"UseSparseArrays"})
    public final HashMap<Integer, VideoListBean> mVideos = new HashMap();
    public long mZid = 0;

    public enum PlayErrorState {
        NORMAL,
        VIDEO_INFO_API_ERROR,
        COMBILE_API_ERROR,
        CND_API_ERROR,
        WO_REAL_URL_API_ERROR,
        PLAY_ERROR,
        DATA_ERROR
    }

    public abstract void start();

    public AlbumPlayBaseFlow(Context context, int launchMode, Bundle bundle) {
        this.mContext = context;
        this.mLaunchMode = launchMode;
        initDataFromIntent(bundle);
        initDataWithLaunchMode(bundle);
        addPlayInfo("内网ip", LetvUtils.getLocalIP());
        String netIP = PreferencesManager.getInstance().getNetIp();
        if (!TextUtils.isEmpty(netIP)) {
            addPlayInfo("公网ip", netIP);
        }
        BaseApplication app = BaseApplication.getInstance();
        addPlayInfo("mSupportLevel=" + app.getSuppportTssLevel() + ";videoFormat=" + app.getVideoFormat() + ";defaultHardStreamDecorder=" + app.getDefaultHardStreamDecorder(), "");
        this.mOldNetState = NetworkUtils.getNetworkType();
    }

    protected void initDataFromIntent(Bundle bundle) {
        boolean z = true;
        this.mFrom = bundle.getInt("from", 1);
        this.mBackToOriginalApp = bundle.getBoolean(PlayConstant.BACK);
        this.mActivityLaunchMode = bundle.getInt("launchMode", 2);
        if (TextUtils.isEmpty(sRef)) {
            sRef = bundle.getString(PlayConstant.REF, "");
        }
        if (this.mFrom == 5) {
            initDataWhenFromRecommend(bundle);
        } else if (this.mFrom == 15) {
            this.mPlayInfo.hTime = bundle.getLong(PlayConstant.HTIME, 0);
        } else if (this.mFrom == 17) {
        }
        if (this.mFrom != 13) {
            z = false;
        }
        this.mIsFromPush = z;
    }

    private void initDataWhenFromRecommend(Bundle bundle) {
        if (bundle.getSerializable(PlayConstant.VIDEO_LIST) != null) {
            this.mVideoRecommendList = (ArrayList) bundle.getSerializable(PlayConstant.VIDEO_LIST);
        }
        this.mVideoRecommendIndex = bundle.getInt(PlayConstant.RECOMMEND_INDEX, this.mVideoRecommendIndex) + 1;
        if (this.mVideoRecommendList != null) {
            this.mVideoRecommend = (HomeMetaData) BaseTypeUtils.getElementFromList(this.mVideoRecommendList, this.mVideoRecommendIndex);
        }
    }

    protected void initDataWithLaunchMode(Bundle bundle) {
        if (this.mLaunchMode == 1 || this.mLaunchMode == 3) {
            this.mAid = Math.max(0, bundle.getLong("aid"));
            this.mVid = Math.max(0, bundle.getLong("vid"));
            if (bundle.getSerializable(PlayConstant.VIDEO_TYPE) instanceof VideoType) {
                this.mVideoType = (VideoType) bundle.getSerializable(PlayConstant.VIDEO_TYPE);
            }
        } else if (this.mLaunchMode == 2) {
            this.mVid = Math.max(0, bundle.getLong("vid"));
            if (bundle.getSerializable(PlayConstant.VIDEO_TYPE) instanceof VideoType) {
                this.mVideoType = (VideoType) bundle.getSerializable(PlayConstant.VIDEO_TYPE);
            }
            if (this.mVideoType != VideoType.Normal || this.mFrom == 20) {
                this.mAid = bundle.getLong("aid", 0);
            }
        } else if (this.mLaunchMode == 4) {
            this.mBoxBean = (LeboxVideoBean) bundle.getSerializable(PlayConstant.LEBOX_VIDEO);
        } else {
            this.mAlbumUrl.realUrl = bundle.getString(PlayConstant.URI);
        }
        this.mSeek = bundle.getLong("seek");
    }

    public void addPlayInfo(String key, String value) {
        String msg = "点播Current Time :" + StringUtils.getTimeStamp() + "  " + key;
        if (!TextUtils.isEmpty(value)) {
            msg = msg + " : " + value + "  ";
        }
        LetvLogApiTool.getInstance().saveExceptionInfo(msg);
        LogInfo.log("albumPLayLog", msg);
    }

    public void setAlbum(AlbumInfo album) {
        this.mVideoBelongedAlbum = album;
    }

    public long getLastVideoPos() {
        if (this.mVideos == null || this.mVideos.size() == 0) {
            return -1;
        }
        int maxPage = -1;
        for (Integer page : this.mVideos.keySet()) {
            maxPage = Math.max(maxPage, page.intValue());
        }
        VideoListBean listBean = (VideoListBean) this.mVideos.get(Integer.valueOf(maxPage));
        return ((VideoBean) listBean.get(listBean.size() - 1)).vid;
    }

    public boolean isLocalFile() {
        return this.mIsDownloadFile || !(TextUtils.isEmpty(this.mAlbumUrl.realUrl) || this.mAlbumUrl.realUrl.startsWith(IDataSource.SCHEME_HTTP_TAG));
    }

    public boolean needSkipAd() {
        return TextUtils.equals(sRef, "infinitemovies");
    }

    public boolean needPlayAd() {
        return (!this.mIsFrontAdFinished && this.mPlayInfo.frontAdDuration > 0) || (!this.mIsMidAdPlayed && this.mPlayInfo.midDuration > 0);
    }

    public boolean isPlayingAd() {
        boolean z = false;
        if (this.mIsCombineAd) {
            if (!(this.mIsFrontAdFinished && this.mIsMidAdFinished)) {
                z = true;
            }
            return z;
        } else if (this.mIsSeparateAdFinished) {
            return false;
        } else {
            return true;
        }
    }

    public boolean shouldCombineAdPlay() {
        return this.mIsCombineAd && this.mPlayInfo.frontAdDuration > 0 && !this.mIsFrontAdFinished;
    }

    public boolean isLebox() {
        return this.mLaunchMode == 4 && this.mBoxBean != null;
    }

    public boolean enableDoublePlayer() {
        return PreferencesManager.getInstance().getSwitchStreamEnable() == 1;
    }

    public boolean isUseDoublePlayerAndChangeStream() {
        return this.mIsChangeingStream && enableDoublePlayer();
    }
}
