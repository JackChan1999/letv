package com.letv.business.flow.live;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.letv.business.flow.live.LiveFlowCallback.LivePlayCallback;
import com.letv.core.bean.CurrentProgram;
import com.letv.core.bean.LivePriceBean;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveStreamBean;
import com.letv.core.bean.LiveStreamBean.StreamType;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.OverloadProtectionState;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import org.cybergarage.upnp.Device;

public abstract class BasePlayLiveFlow {
    public static final String LIVE_FLOW_TAG = "liveFlowTag_";
    public static final String REQUEST_ADD_BOOK_LIVE = "liveFlowTag_RequestAddBookLive";
    public static final String REQUEST_CAN_PLAY = "liveFlowTag_RequestCanPlay";
    public static final String REQUEST_CONSUME_LIVE_TICKET = "liveFlowTag_ConsumeLiveTicket";
    public static final String REQUEST_LIVE_JIANQUAN = "liveFlowTag_RequestLiveJianQuan";
    public static final String REQUEST_LIVE_LUNBO_PROGRAMLIST = "liveFlowTag_RequestLiveLunboProgramList";
    public static final String REQUEST_LIVE_ROOMLIST = "liveFlowTag_RequestLiveRoomList";
    public static final String REQUEST_LIVE_TICKET = "liveFlowTag_QueryLiveTicket";
    public static final String REQUEST_MIGU_URL_BY_CHANNELID = "liveFlowTag_RequestMiGuUrlByChannelId";
    public static final String REQUEST_PAYINFO_BY_ID = "liveFlowTag_RequestPayInfoById";
    public static final String REQUEST_PLAYING_PROGRAM = "liveFlowTag_RequestPlayingProgram";
    public static final String REQUEST_QUERY_LIVE_PRICE = "liveFlowTag_QueryLivePrice";
    public static final String REQUEST_REAL_LINK = "liveFlowTag_RequestRealLink";
    public static final String REQUEST_REAL_LINK_SYNC = "liveFlowTag_RequestRealLink_sync";
    public static final String REQUEST_URL_BY_CHANNELID = "liveFlowTag_RequestUrlByChannelId";
    protected String mChannelId;
    public String mChannelName;
    public String mChannelNum;
    protected String mCode;
    protected Context mContext;
    protected CurrentProgram mCurrentProgram;
    protected boolean mDefaultLow = false;
    protected int mFlowState;
    protected int mFrom;
    protected String mGuestImgUrl;
    protected String mGuestName = "";
    protected String mHomeImgUrl;
    protected String mHomeName = "";
    protected boolean mIsDanmaku;
    protected boolean mIsP2PMode = false;
    protected boolean mIsPanoramaVideo;
    protected boolean mIsPay;
    protected boolean mIsPayed;
    protected boolean mIsPlayFreeUrl = false;
    protected boolean mIsPlayedAd = false;
    protected boolean mIsPlayedAdFinish = false;
    protected boolean mIsShowOrderDialog = true;
    protected boolean mIsWo3GUser = false;
    protected int mLaunchMode;
    protected LiveRemenBaseBean mLiveBean;
    protected LivePlayCallback mLivePlayCallback;
    protected LivePriceBean mLivePriceBean;
    protected LiveRemenBaseBean mLiveRemenBase = null;
    protected LiveRemenListBean mLiveRoomListBean;
    protected LiveStreamBean mLiveStreamBean;
    protected String mLiveid;
    protected int mOldNetState = -1;
    protected boolean mOnlyFull;
    protected OverloadProtectionState mOverloadState = OverloadProtectionState.NORMAL;
    protected String mPartId;
    protected String mPlayTime;
    protected ProgramEntity mPlayingProgram;
    public String mProgramName;
    protected String mPushLiveId;
    protected String mRealUrl;
    protected String mScreenings;
    protected String mSelectId;
    protected String mSignal;
    protected int mTicketCount;
    protected String mToken;
    protected String mUniqueId;
    protected boolean mUserControllLevel;
    protected StreamType mUserStreamType;
    protected boolean mWebPlayExeception;

    protected abstract void initLiveData();

    public abstract void run();

    public abstract String syncGetPlayUrl(Device device);

    public void start() {
        initLiveData();
        run();
    }

    public void initBaseData() {
        Intent intent = ((Activity) this.mContext).getIntent();
        if (intent != null) {
            boolean z;
            this.mFrom = intent.getIntExtra("from", 0);
            if (intent.getSerializableExtra(PlayConstant.VIDEO_TYPE) == VideoType.Panorama) {
                z = true;
            } else {
                z = false;
            }
            this.mIsPanoramaVideo = z;
            this.mChannelName = intent.getStringExtra(PlayConstant.LIVE_CHANNEL_NAME);
            this.mChannelNum = intent.getStringExtra(PlayConstant.LIVE_CHANNEL_LUNBO_NUMBER);
            this.mCode = intent.getStringExtra("code");
            this.mProgramName = intent.getStringExtra(PlayConstant.LIVE_PROGRAM_NAME);
            this.mChannelId = intent.getStringExtra(PlayConstant.LIVE_CHANNEL_ID);
            this.mSignal = intent.getStringExtra(PlayConstant.LIVE_CHANNEL_SIGNAL);
            LogInfo.log("clf", "initBaseData....mChannelId=" + this.mChannelId);
            this.mPushLiveId = intent.getStringExtra(PlayConstant.LIVE_LAUNCH_ID);
            this.mUniqueId = intent.getStringExtra(PlayConstant.LIVE_ID);
            LogInfo.log("clf", "....mUniqueId=" + this.mUniqueId + ",mPushLiveId=" + this.mPushLiveId);
            Bundle payBundle = intent.getBundleExtra(PlayConstant.BUNDLE_KEY_YC_PARAM);
            if (payBundle != null) {
                this.mIsPay = payBundle.getBoolean(PlayConstant.LIVE_IS_PAY, false);
                this.mScreenings = payBundle.getString(PlayConstant.LIVE_SCREENINGS);
                this.mHomeImgUrl = payBundle.getString(PlayConstant.LIVE_HOME_ICON);
                this.mGuestImgUrl = payBundle.getString(PlayConstant.LIVE_GUEST_ICON);
                this.mPlayTime = payBundle.getString("play_time");
            } else {
                this.mIsPay = intent.getBooleanExtra(PlayConstant.LIVE_IS_PAY, false);
                this.mScreenings = intent.getStringExtra(PlayConstant.LIVE_SCREENINGS);
                this.mHomeImgUrl = intent.getStringExtra(PlayConstant.LIVE_HOME_ICON);
                this.mGuestImgUrl = intent.getStringExtra(PlayConstant.LIVE_GUEST_ICON);
                this.mPlayTime = intent.getStringExtra("play_time");
            }
            this.mOldNetState = NetworkUtils.getNetworkType();
        }
    }

    protected Activity getActivity() {
        return (Activity) this.mContext;
    }

    public void setLaunchMode(int launchMode) {
        this.mLaunchMode = launchMode;
    }

    public void setmIsPay(boolean mIsPay) {
        this.mIsPay = mIsPay;
    }

    public boolean ismIsPayed() {
        return this.mIsPayed;
    }

    public void setmIsPayed(boolean mIsPayed) {
        this.mIsPayed = mIsPayed;
    }

    public String getRealUrl() {
        return this.mRealUrl;
    }

    public int getFrom() {
        return this.mFrom;
    }

    public String getChannelId() {
        return this.mChannelId;
    }

    public String getCode() {
        return this.mCode;
    }

    public void setPay(boolean mIsPay) {
        this.mIsPay = mIsPay;
    }

    public boolean isPay() {
        return this.mIsPay;
    }

    public boolean isPayed() {
        return this.mIsPayed;
    }

    public String getChannelNum() {
        return this.mChannelNum;
    }

    public String getChannelName() {
        return this.mChannelName;
    }

    public String getProgramName() {
        return this.mProgramName;
    }

    public String getHomeImgUrl() {
        return this.mHomeImgUrl;
    }

    public String getGuestImgUrl() {
        return this.mGuestImgUrl;
    }

    public String getHomeName() {
        return this.mHomeName;
    }

    public String getGuestName() {
        return this.mGuestName;
    }

    public LivePriceBean getLivePriceBean() {
        return this.mLivePriceBean;
    }

    public String getPlayTime() {
        return this.mPlayTime;
    }

    public int getTicketCount() {
        return this.mTicketCount;
    }

    public CurrentProgram getCurrentProgram() {
        return this.mCurrentProgram;
    }

    public boolean isWo3GUser() {
        return this.mIsWo3GUser;
    }

    public String getUniqueId() {
        return this.mUniqueId;
    }

    public void setUniqueId(String mUniqueId) {
        this.mUniqueId = mUniqueId;
    }

    public void setFlowState(int mFlowState) {
        this.mFlowState = mFlowState;
    }

    public int getFlowState() {
        return this.mFlowState;
    }

    public void setChannelName(String mChannelName) {
        this.mChannelName = mChannelName;
    }

    public void setChannelId(String mChannelId) {
        this.mChannelId = mChannelId;
    }

    public void setCode(String mCode) {
        this.mCode = mCode;
    }

    public void setProgramName(String mProgramName) {
        this.mProgramName = mProgramName;
    }

    public void setChannelNum(String mChannelNum) {
        this.mChannelNum = mChannelNum;
    }

    public String getLiveid() {
        return this.mLiveid;
    }

    public LiveStreamBean getLiveStreamBean() {
        return this.mLiveStreamBean;
    }

    public void setUserStreamType(StreamType mUserStreamType) {
        this.mUserStreamType = mUserStreamType;
        this.mLiveStreamBean.streamType = mUserStreamType;
    }

    public void setUserControllLevel(boolean mUserControllLevel) {
        this.mUserControllLevel = mUserControllLevel;
    }

    public String getPushLiveId() {
        return this.mPushLiveId;
    }

    public boolean isWebPlayExeception() {
        return this.mWebPlayExeception;
    }

    public void setWebPlayExeception(boolean mWebPlayExeception) {
        this.mWebPlayExeception = mWebPlayExeception;
    }

    public LiveRemenBaseBean getLiveRemenBase() {
        return this.mLiveRemenBase;
    }

    public String getSignal() {
        return this.mSignal;
    }

    public String getPartId() {
        return this.mPartId;
    }

    public void updatePartId(LiveRemenBaseBean bean) {
        if (bean != null) {
            this.mPartId = bean.partId;
            if (this.mLivePlayCallback != null && "1".equals(bean.buyFlag)) {
                this.mLivePlayCallback.updatePartId(this.mPartId);
                return;
            }
        }
        if (this.mLivePlayCallback != null) {
            this.mLivePlayCallback.updatePartId("");
        }
    }

    public boolean isDanmaku() {
        return this.mIsDanmaku;
    }

    public boolean isMigu() {
        return "9".equals(this.mSignal);
    }

    public boolean isOverLoadCutOut() {
        return this.mOverloadState == OverloadProtectionState.CUTOUT;
    }

    public boolean isOverLoadDownStream() {
        return this.mOverloadState == OverloadProtectionState.DOWNLOAD_STREAM;
    }

    public LiveRemenBaseBean getLiveInfo() {
        return this.mLiveBean;
    }
}
