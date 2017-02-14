package com.letv.business.flow.statistics;

import android.content.Context;
import android.text.TextUtils;
import com.letv.ads.ex.ui.AdPlayFragmentProxy;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.datastatistics.util.DataUtils;
import com.letv.pp.utils.NetworkUtils;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class StatisticsInfo {
    public int mAdCount = 0;
    public String mCid = NetworkUtils.DELIMITER_LINE;
    public boolean mHasStatistics = false;
    public int mInterruptNum = 0;
    public int mIpt = 0;
    public boolean mIsCload = false;
    public boolean mIsGslb = false;
    public boolean mIsInLiveHall = true;
    public boolean mIsLunboWeiData = false;
    public boolean mIsPay;
    public boolean mIsPlayingAds = false;
    public String mLastAction;
    public int mLaunchMode = 0;
    public String mLiveId;
    public LiveRemenBaseBean mLiveRenmenBase;
    public String mPageId = "";
    public int mPageIndex = 0;
    public AdPlayFragmentProxy mPlayAdFragment;
    public String mPlayRef = "";
    public String mRealUrl;
    public int mReplayType = 1;
    public RequestTimeInfo mRequestTimeInfo = new RequestTimeInfo();
    public String mStation;
    public int mStatus = -1;
    public String mTitle;
    public String mUuid;
    public String mVt = NetworkUtils.DELIMITER_LINE;

    public static class RequestTimeInfo {
        public long mAdConsumeTime = 0;
        public String mAdUrl = "";
        public long mAdsJoinTime = 0;
        public long mAdsLoadConsumeTime = 0;
        public long mAdsPlayFirstFrameTime = 0;
        public long mAdsRequestTime = 0;
        public long mAdsToalTime = 0;
        public long mLoadingConsumeTime = 0;
        public long mPlayVideoFirstFrameTime;
        public long mStartTime = 0;
        public long mTimeRequestAd;
        public long mTimeRequestCanplay;
        public long mTimeRequestProgramList;
        public long mTimeRequestRealUrl;
        public long mTotalConsumeTime = 0;
        public long mVideoLoadConsumeTime = 0;
    }

    public String getUuidTime(Context context) {
        if (TextUtils.isEmpty(this.mUuid)) {
            this.mUuid = DataUtils.getUUID(context);
        }
        String uuidTime = this.mUuid;
        if (this.mInterruptNum > 0) {
            return this.mUuid + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mInterruptNum;
        }
        return uuidTime;
    }

    public void resetTimeInfos() {
        this.mRequestTimeInfo = new RequestTimeInfo();
    }
}
