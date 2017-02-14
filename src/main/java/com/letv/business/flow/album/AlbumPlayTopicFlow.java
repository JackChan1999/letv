package com.letv.business.flow.album;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.business.R;
import com.letv.business.flow.album.AlbumPlayBaseFlow.PlayErrorState;
import com.letv.business.flow.album.AlbumPlayFlow.RequestVideoPlayUrl;
import com.letv.business.flow.album.AlbumPlayFlowObservable.PlayErrorCodeNotify;
import com.letv.business.flow.album.AlbumPlayFlowObservable.VideoTitleNotify;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.PlayRecord;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.PlayConstant.VideoType;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;

public class AlbumPlayTopicFlow extends AlbumPlayFlow {
    private long mPid;
    private long mPvid;

    public AlbumPlayTopicFlow(Context context, int launchMode, Bundle bundle) {
        super(context, launchMode, bundle);
    }

    protected void initDataFromIntent(Bundle bundle) {
        boolean z = true;
        this.mFrom = bundle.getInt("from", 1);
        this.mBackToOriginalApp = bundle.getBoolean(PlayConstant.BACK);
        if (this.mFrom != 13) {
            z = false;
        }
        this.mIsFromPush = z;
    }

    protected void initDataWithLaunchMode(Bundle bundle) {
        this.mZid = Math.max(0, bundle.getLong(PlayConstant.ZID));
        this.mPid = Math.max(0, bundle.getLong("pid"));
        this.mVid = Math.max(0, bundle.getLong("vid"));
        if (this.mVideoType == VideoType.Dolby || this.mFrom == 20) {
            this.mAid = bundle.getLong("aid", 0);
        }
        this.mSeek = bundle.getLong("seek");
        this.mIsPlayTopic = true;
    }

    protected void startLoadingData() {
        super.startLoadingData();
        if (this.mLaunchMode == 11) {
            this.mAid = this.mPid;
            sRequestRealUrlConsumetime = System.currentTimeMillis();
            checkPlayRecord(true);
        } else if (!TextUtils.isEmpty(this.mAlbumUrl.realUrl)) {
            this.mVideoListener.initVideoView(false, this.mIsChangeingStream);
            this.mVideoListener.startPlayLocal(this.mAlbumUrl.realUrl, (long) (((int) this.mSeek) * 1000), this.mIsChangeingStream);
            this.mLocalPath = this.mAlbumUrl.realUrl;
            this.mLocalSeek = this.mSeek;
        }
    }

    protected void onRequestPlayRecord(PlayRecord result) {
        if (this.mIsDownloadFile && this.mDownloadDBBean != null) {
            this.mObservable.notifyObservers(new VideoTitleNotify(this.mDownloadDBBean.episodetitle));
        } else if (!(result == null || TextUtils.isEmpty(result.title))) {
            this.mObservable.notifyObservers(new VideoTitleNotify(result.title));
        }
        if (result != null && LetvConstant.VIDEO_TYPE_KEY_TIDBITSTS.equals(Integer.valueOf(result.videoType))) {
            result = null;
        }
        this.mPlayRecord = result;
        if (this.mPlayRecord != null) {
            if (this.mPlayRecord.totalDuration > 1800) {
                this.mLoadListener.loading(LetvUtils.getPlayRecordType(this.mContext, this.mPlayRecord, this.mPlayInfo.beginTime));
            }
            if (this.mSeek > 0) {
                this.mPlayRecord.playedDuration = this.mSeek / 1000;
            }
            this.mVid = (long) this.mPlayRecord.videoId;
            this.mPlayInfo.videoTotalTime = this.mPlayRecord.totalDuration * 1000;
        }
        requestVideo();
        notifyTabRequestData();
    }

    protected void requestVideo() {
        this.mObservable.notifyObservers(AlbumPlayFlowObservable.ON_START_FETCHING);
        Volley.getQueue().cancelWithTag(AlbumPlayFlow.REQUEST_VIDEO_PLAY_URL);
        if (!isUseDoublePlayerAndChangeStream()) {
            this.mIsStartPlay = false;
            this.mIsFirstPlay = true;
            this.mIsStarted = false;
        }
        new RequestVideoPlayUrl() {
            protected void onError(NetworkResponseState state, DataHull hull) {
                AlbumPlayTopicFlow.this.mErrorState = PlayErrorState.VIDEO_INFO_API_ERROR;
                String subErrorCode = StatisticsUtils.getSubErroCode(state, hull);
                if (state == NetworkResponseState.NETWORK_NOT_AVAILABLE) {
                    if (AlbumPlayTopicFlow.this.mIsDownloadFile) {
                        AlbumPlayTopicFlow.this.startPlayLocal();
                        return;
                    }
                    AlbumPlayTopicFlow.this.mLoadListener.requestError("", "", "");
                    AlbumPlayTopicFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", false, subErrorCode));
                } else if (state == NetworkResponseState.RESULT_ERROR) {
                    AlbumPlayTopicFlow.this.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                    AlbumPlayTopicFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", true, subErrorCode));
                } else if (state == NetworkResponseState.NETWORK_ERROR) {
                    AlbumPlayTopicFlow.this.mLoadListener.requestError(TipUtils.getTipMessage("100077", R.string.commit_error_info), "0302", "");
                    AlbumPlayTopicFlow.this.mObservable.notifyObservers(new PlayErrorCodeNotify("0302", true, subErrorCode));
                }
            }
        }.startRequestCache();
    }

    protected void startPlayLocal() {
        this.mIsScanVideo = false;
        this.mVideoListener.resetPlayFlag();
        this.mIsShowSkipEnd = true;
        if (this.mIsDownloadFile) {
            updatePlayDataStatistics("play", -1);
        }
        setPlayingVideoBeanStatus();
        if (this.mDownloadDBBean != null) {
            this.mPlayLevel = this.mDownloadDBBean.isHd == 1 ? 2 : 1;
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
        } else {
            this.mPlayLevel = 2;
            this.mCurrDownloadFileIsHd = true;
            this.mStreamSupporter.reset();
            this.mStreamSupporter.hasHd = true;
        }
        addOffAd();
    }

    protected void handlerFloatBall() {
        if (this.mCurrentPlayingVideo != null) {
            this.mVideoListener.handlerFloatBall("9", this.mCurrentPlayingVideo.cid);
        }
    }
}
