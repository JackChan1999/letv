package com.letv.android.client.album.controller;

import android.view.View;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.commonlib.messagemodel.DownloadPageLaunchConfig.LaunchToDownloadPage;
import com.letv.core.audiotrack.AudioTrackManager;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoFileBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.task.LeMessageTask;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvTools;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AlbumCacheController {
    private CacheState curCacheState = CacheState.DISABLE_CACHE;
    private AlbumPlayActivity mActivity;
    private List<AlbumCacheListener> mListenerList = new ArrayList();

    public AlbumCacheController(AlbumPlayActivity activity) {
        this.mActivity = activity;
        LeMessageManager.getInstance().registerTask(new LeMessageTask(102, new 1(this)));
    }

    public void addCacheListener(AlbumCacheListener listener) {
        if (listener != null) {
            this.mListenerList.add(listener);
        }
    }

    public void updateCacheState() {
        setCacheState(canDownload() ? CacheState.ABLE_CACHE : CacheState.DISABLE_CACHE);
    }

    public void setCacheState(CacheState state) {
        this.curCacheState = state;
        if (NetworkUtils.isNetworkAvailable()) {
            for (AlbumCacheListener albumCacheListener : this.mListenerList) {
                albumCacheListener.setCacheState(state);
            }
        }
    }

    public CacheState getCurCacheState() {
        return this.curCacheState;
    }

    public boolean canDownload() {
        VideoBean video = getCurrPlayingVideo();
        AlbumCardList cardList = getCardList();
        AlbumInfo album = getAlbum();
        VideoFileBean videoFileBean = getCurrPlayingVideoFile();
        if (cardList == null || videoFileBean == null || AudioTrackManager.getInstance().isAudioTrackVideo(videoFileBean, this.mActivity.getFlow().mPlayLevel)) {
            return false;
        }
        if (video == null) {
            video = cardList.videoInfo;
        }
        boolean isPostive = AlbumPageCard.isPostiveVideo(cardList, video);
        if (cardList.mIsAlbum) {
            if (isPostive) {
                if (album == null || !album.canDownload()) {
                    return false;
                }
                return true;
            } else if (video == null || !video.canDownload()) {
                return false;
            } else {
                return true;
            }
        } else if (album != null && !BaseTypeUtils.isListEmpty(cardList.topicAlbumList)) {
            return album.canDownload();
        } else {
            if (video != null) {
                return video.canDownload();
            }
            return false;
        }
    }

    public void cacheClick(View v) {
        if (NetworkUtils.isNetworkAvailable()) {
            VideoBean video = getCurrPlayingVideo();
            if (this.curCacheState == CacheState.ABLE_CACHE) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(107, new LaunchToDownloadPage(this.mActivity, v)));
            } else {
                ToastUtils.showToast(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_100016, R.string.half_bottom_download_unable));
            }
            if (video != null) {
                LogInfo.LogStatistics("缓存");
                StatisticsUtils.staticticsInfoPost(this.mActivity, "0", "h22", "0006", 2, null, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage, video.cid + "", video.pid + "", video.vid + "", null, null);
                return;
            }
            return;
        }
        ToastUtils.showToast(LetvTools.getTextFromServer("500003", this.mActivity.getString(R.string.network_unavailable)));
    }

    public AlbumCardList getCardList() {
        if (this.mActivity.getHalfFragment() != null) {
            return this.mActivity.getHalfFragment().getAlbumCardList();
        }
        return null;
    }

    public AlbumInfo getAlbum() {
        AlbumCardList cardList = getCardList();
        if (cardList != null) {
            return cardList.albumInfo;
        }
        return null;
    }

    public VideoBean getCurrPlayingVideo() {
        if (this.mActivity.getHalfFragment() != null) {
            return this.mActivity.getHalfFragment().getCurrPlayingVideo();
        }
        return null;
    }

    public VideoFileBean getCurrPlayingVideoFile() {
        if (this.mActivity == null || this.mActivity.getFlow() == null || this.mActivity.getFlow().mVideoFile == null) {
            return null;
        }
        return this.mActivity.getFlow().mVideoFile;
    }

    private Map<String, List<VideoBean>> getVideoMap() {
        AlbumCardList cardList = getCardList();
        if (cardList != null) {
            return cardList.videoList.videoListMap;
        }
        return null;
    }
}
