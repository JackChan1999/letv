package com.letv.android.client.album.controller;

import android.content.Context;
import android.text.TextUtils;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.observable.ScreenObservable;
import com.letv.android.client.commonlib.listener.AlbumFindNextVideoCallback;
import com.letv.android.client.commonlib.messagemodel.AlbumTask$AlbumPlayNexProtocol;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.PlayObservable;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.PlayConstant.PlayerType;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.parser.PlayRecommendParser;
import com.letv.core.parser.VideoListParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.manager.DownloadManager;
import com.letv.mobile.lebox.LeboxApiManager;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;
import com.letv.pp.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class AlbumPlayNextController implements Observer, AlbumTask$AlbumPlayNexProtocol {
    private static final int MAX_RECOMMEND_COUNT = 20;
    private static final String TAG = AlbumPlayNextController.class.getSimpleName();
    private static final String TAG_REQUEST_EPISODE_NEXT_PAGE = (TAG + "request_episode_next_page");
    private static final String TAG_REQUEST_PERIODS_NEXT_PAGE = (TAG + "request_periods_next_page");
    private static final String TAG_REQUEST_PLAY_RECOMMEND = (TAG + "request_play_recommend");
    private long featureVid = -1;
    private AlbumFindNextVideoCallback mCallBack;
    private Context mContext;
    public boolean mIsPlayingRelate;
    public boolean mIsPlayingWatch;
    public AlbumInfo mNextAlbum;
    public LeboxVideoBean mNextLeboxVideo;
    public VideoBean mNextVideo;
    public String mNextVideoFl = NetworkUtils.DELIMITER_LINE;
    public int mNextVideoWz = -1;
    private PlayNextBtnController mPlayNextBtnController;
    public boolean mPlayPeriods;
    private PlayerType mPlayType = PlayerType.MAIN;
    private int mRecommendCount;
    public VideoBean mRecommendVideo;

    public AlbumPlayNextController(Context context, PlayerType playType) {
        this.mContext = context;
        this.mPlayType = playType;
        this.mPlayNextBtnController = PlayNextBtnController.newInstance(context, playType);
    }

    public VideoBean getNextVideoBean() {
        return this.mNextVideo;
    }

    public void update(Observable observable, Object data) {
        if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(ScreenObservable.ON_CONFIG_CHANGE, notify)) {
                if (UIsUtils.isLandscape(BaseApplication.getInstance())) {
                    this.mPlayNextBtnController.onChangFull();
                } else {
                    this.mPlayNextBtnController.onChangHalf();
                }
            } else if (TextUtils.equals(PlayObservable.ON_NET_CHANGE, notify)) {
                this.mPlayNextBtnController.onNetChange();
            }
        }
    }

    public void findNextVideo(AlbumCardList cardList, VideoBean currPlayingVideo, AlbumPageCard pageCard, AlbumFindNextVideoCallback callback) {
        this.mCallBack = callback;
        this.mPlayNextBtnController.setPlayNextAble(false);
        if ((this.mContext instanceof AlbumPlayActivity) && ((AlbumPlayActivity) this.mContext).mIsLebox) {
            LogInfo.log(TAG, "查找乐盒下一集视频");
            doFindNextLeboxVideoBean();
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            LogInfo.log(TAG, "查找点播下一集视频");
            doFindNextVideo(cardList, currPlayingVideo, pageCard);
        } else {
            new LetvRequest().setRequestType(RequestManner.CACHE_ONLY).setCache(new 2(this, currPlayingVideo)).setCallback(new 1(this, callback)).add();
            LogInfo.log(TAG, "查找已缓存下一集视频");
        }
    }

    private boolean doFindNextLeboxVideoBean() {
        this.mNextLeboxVideo = null;
        if (this.mContext instanceof AlbumPlayActivity) {
            AlbumPlayFlow albumPlayFlow = this.mContext.getFlow();
            if (albumPlayFlow != null) {
                LeboxVideoBean curLeboxVideo = albumPlayFlow.mBoxBean;
                if (curLeboxVideo != null) {
                    List<LeboxVideoBean> list = LeboxApiManager.getInstance().getVideoByPid(curLeboxVideo.pid, 1);
                    if (!BaseTypeUtils.isListEmpty(list)) {
                        for (int i = 0; i < list.size(); i++) {
                            if (((LeboxVideoBean) list.get(i)).vid.equals(curLeboxVideo.vid)) {
                                LeboxVideoBean nextLeboxBean = (LeboxVideoBean) BaseTypeUtils.getElementFromList(list, i + 1);
                                if (nextLeboxBean != null) {
                                    LogInfo.log(TAG, "找到乐盒下一集");
                                    setLeboxVideoBean(nextLeboxBean);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private void doFindNextVideo(AlbumCardList cardList, VideoBean currPlayingVideo, AlbumPageCard pageCard) {
        VideoBean videoBean = null;
        boolean isGridPreview = false;
        this.mNextVideo = null;
        this.mRecommendVideo = null;
        this.mNextAlbum = null;
        this.mIsPlayingWatch = false;
        this.featureVid = -1;
        this.mPlayPeriods = false;
        this.mIsPlayingRelate = false;
        if (cardList != null && currPlayingVideo != null && pageCard != null) {
            long originVid = currPlayingVideo.vid;
            if (cardList.videoList.style == 1 && currPlayingVideo.isPreview()) {
                isGridPreview = true;
            }
            resetStatisticsInfo();
            boolean hasFind = findInWatch(cardList, currPlayingVideo);
            if (hasFind) {
                this.mNextVideoFl = "h25";
            }
            if (!(hasFind || pageCard.listCard.position == -1)) {
                this.mNextVideoFl = "h27";
                hasFind = findMovieMergeSurrounding(cardList, currPlayingVideo);
            }
            if (!hasFind) {
                if (this.featureVid == -1) {
                    videoBean = currPlayingVideo;
                }
                if (AlbumPageCard.isPostiveVideo(cardList, videoBean) || !cardList.mIsAlbum || isGridPreview) {
                    this.mNextVideoFl = "h27";
                    if (this.featureVid != -1) {
                        this.mPlayPeriods = true;
                        currPlayingVideo.vid = this.featureVid;
                    }
                    if (cardList.videoList.style != 1 && cardList.videoList.style != 2) {
                        if (pageCard.periodsCard.position != -1) {
                            hasFind = findInPeriods(cardList, currPlayingVideo);
                        }
                        if (hasFind) {
                            this.mPlayPeriods = true;
                        }
                    } else if (!(pageCard.gridCard.position == -1 && pageCard.listCard.position == -1)) {
                        hasFind = findInEpisode(cardList, currPlayingVideo);
                    }
                }
            }
            if (!(hasFind || pageCard.relateCard.position == -1)) {
                this.mNextVideoFl = "h214";
                hasFind = findInRelate(cardList, currPlayingVideo);
            }
            if (!(hasFind || pageCard.surroundingCard.position == -1)) {
                this.mNextVideoFl = "h26";
                findInSurrounding(cardList, currPlayingVideo);
            }
            currPlayingVideo.vid = originVid;
            if (!hasFind) {
                resetStatisticsInfo();
            }
        }
    }

    private boolean findInWatch(AlbumCardList cardList, VideoBean currPlayingVideo) {
        int currPosition = -1;
        long periodsId = -1;
        List<VideoBean> watchList = cardList.videoList.fragmentList;
        if (!BaseTypeUtils.isListEmpty(watchList)) {
            for (int i = 0; i < watchList.size(); i++) {
                VideoBean video = (VideoBean) watchList.get(i);
                if (video.isFeature()) {
                    periodsId = video.vid;
                } else if (video.vid == currPlayingVideo.vid) {
                    currPosition = i;
                    break;
                }
            }
        }
        if (currPosition != -1) {
            if (currPosition == watchList.size() - 1) {
                this.featureVid = periodsId;
            } else {
                setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(watchList, currPosition + 1));
                this.mIsPlayingWatch = true;
                this.mNextVideoWz = currPosition + 1;
                return true;
            }
        }
        return false;
    }

    private boolean findMovieMergeSurrounding(AlbumCardList cardList, VideoBean currPlayingVideo) {
        VideoBean albumVideo = cardList.videoInfo;
        if (albumVideo != null && albumVideo.cid == 1 && albumVideo.isFeature()) {
            List<VideoBean> mergeDataList = cardList.videoList.videoList;
            if (!BaseTypeUtils.isListEmpty(mergeDataList)) {
                for (int i = 0; i < mergeDataList.size(); i++) {
                    if (((VideoBean) mergeDataList.get(i)).vid == currPlayingVideo.vid) {
                        if (i < mergeDataList.size() - 1) {
                            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(mergeDataList, i + 1));
                            this.mNextVideoWz = i + 1;
                            return true;
                        }
                        requestPlayRecommend(currPlayingVideo);
                    }
                }
            }
        }
        return false;
    }

    private boolean findInEpisode(AlbumCardList cardList, VideoBean currPlayingVideo) {
        int i;
        int currPosition = -1;
        List<VideoBean> findList = null;
        String findPage = "";
        Map<String, List<VideoBean>> videoListMap = cardList.videoList.videoListMap;
        if (!BaseTypeUtils.isMapEmpty(videoListMap)) {
            for (String key : videoListMap.keySet()) {
                findList = (List) videoListMap.get(key);
                findPage = key;
                if (!BaseTypeUtils.isListEmpty(findList)) {
                    for (i = 0; i < findList.size(); i++) {
                        if (((VideoBean) findList.get(i)).vid == currPlayingVideo.vid) {
                            currPosition = i;
                            break;
                        }
                    }
                    if (currPosition != -1) {
                        break;
                    }
                }
            }
        }
        if (currPosition == -1 || BaseTypeUtils.isListEmpty(findList)) {
            return false;
        }
        if (currPosition < findList.size() - 1) {
            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(findList, currPosition + 1));
            this.mNextVideoWz = currPosition + 1;
        } else if (BaseTypeUtils.stoi(findPage) != videoListMap.size() - 1) {
            requestEpisodeNextPage(cardList, currPlayingVideo, (BaseTypeUtils.stoi(findPage) + 2) + "");
        } else if (cardList.mIsAlbum || BaseTypeUtils.isListEmpty(cardList.topicAlbumList)) {
            requestPlayRecommend(currPlayingVideo);
        } else {
            int currAlbumPosition = -1;
            for (i = 0; i < cardList.topicAlbumList.size(); i++) {
                if (currPlayingVideo.pid == ((AlbumInfo) cardList.topicAlbumList.get(i)).pid) {
                    currAlbumPosition = i;
                    break;
                }
            }
            if (currAlbumPosition == -1 || currAlbumPosition == cardList.topicAlbumList.size() - 1) {
                requestPlayRecommend(currPlayingVideo);
            } else {
                setAlbumInfo((AlbumInfo) BaseTypeUtils.getElementFromList(cardList.topicAlbumList, currAlbumPosition + 1));
                this.mNextVideoWz = currAlbumPosition + 1;
            }
        }
        return true;
    }

    private boolean findInPeriods(AlbumCardList cardList, VideoBean currPlayingVideo) {
        int currPosition = -1;
        List<VideoBean> findList = null;
        String findPage = "";
        Map<String, List<VideoBean>> videoListMap = cardList.videoList.periodsVideoListMap;
        if (!BaseTypeUtils.isMapEmpty(videoListMap)) {
            for (String key : videoListMap.keySet()) {
                findList = (List) videoListMap.get(key);
                findPage = key;
                if (!BaseTypeUtils.isListEmpty(findList)) {
                    for (int i = 0; i < findList.size(); i++) {
                        if (((VideoBean) findList.get(i)).vid == currPlayingVideo.vid) {
                            currPosition = i;
                            break;
                        }
                    }
                    if (currPosition != -1) {
                        break;
                    }
                }
            }
        }
        if (currPosition == -1 || BaseTypeUtils.isListEmpty(findList)) {
            return false;
        }
        if (currPosition < findList.size() - 1) {
            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(findList, currPosition + 1));
            this.mNextVideoWz = currPosition + 1;
        } else {
            List<String> pageList = cardList.videoList.periodsYearList;
            if (BaseTypeUtils.isListEmpty(pageList)) {
                return false;
            }
            if (TextUtils.equals((CharSequence) BaseTypeUtils.getElementFromList(pageList, pageList.size() - 1), findPage)) {
                requestPlayRecommend(currPlayingVideo);
            } else {
                requestPeriodsVideolist(cardList, currPlayingVideo, (String) BaseTypeUtils.getElementFromList(pageList, pageList.indexOf(findPage) + 1));
            }
        }
        return true;
    }

    private boolean findInRelate(AlbumCardList cardList, VideoBean currPlayingVideo) {
        int currPosition = -1;
        List<VideoBean> list = new ArrayList();
        if (!BaseTypeUtils.isListEmpty(cardList.relateBean.recList)) {
            list.addAll(cardList.relateBean.recList);
        }
        if (!BaseTypeUtils.isListEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                if (((VideoBean) list.get(i)).vid == currPlayingVideo.vid) {
                    currPosition = i;
                    break;
                }
            }
        }
        if (currPosition == -1) {
            return false;
        }
        if (currPosition == list.size() - 1) {
            requestPlayRecommend(currPlayingVideo);
        } else {
            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(list, currPosition + 1));
            this.mIsPlayingRelate = true;
            this.mNextVideoWz = currPosition + 1;
        }
        return true;
    }

    private boolean findInSurrounding(AlbumCardList cardList, VideoBean currPlayingVideo) {
        int currPosition = -1;
        List<VideoBean> list = cardList.outList;
        if (!BaseTypeUtils.isListEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                if (((VideoBean) list.get(i)).vid == currPlayingVideo.vid) {
                    currPosition = i;
                    break;
                }
            }
        }
        if (currPosition == -1) {
            return false;
        }
        if (currPosition == list.size() - 1) {
            requestPlayRecommend(currPlayingVideo);
        } else {
            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(list, currPosition + 1));
            this.mNextVideoWz = currPosition + 1;
        }
        return true;
    }

    private VideoBean doFindCacheNextVideo(VideoBean currPlayingVideo) {
        VideoBean videoBean = null;
        if (currPlayingVideo != null && currPlayingVideo.pid > 0) {
            ArrayList<DownloadVideo> downloadVideoList = DownloadManager.getDownloadVideoFinishByAid(currPlayingVideo.pid);
            if (!(BaseTypeUtils.isListEmpty(downloadVideoList) || downloadVideoList.size() == 1)) {
                int nListSize = downloadVideoList.size();
                Collections.sort(downloadVideoList, new 3(this));
                int i = 0;
                while (i < nListSize) {
                    DownloadVideo downloadVideo = (DownloadVideo) downloadVideoList.get(i);
                    if (downloadVideo != null && i < nListSize - 1 && currPlayingVideo.vid == downloadVideo.vid) {
                        DownloadVideo dbBean = (DownloadVideo) BaseTypeUtils.getElementFromList(downloadVideoList, i + 1);
                        if (dbBean != null) {
                            videoBean = new VideoBean();
                            videoBean.vid = dbBean.vid;
                            videoBean.cid = (int) dbBean.cid;
                            videoBean.mid = dbBean.mmsid;
                            videoBean.pid = dbBean.aid;
                            videoBean.nameCn = dbBean.name;
                            videoBean.etime = dbBean.etime;
                            videoBean.btime = dbBean.btime;
                            videoBean.duration = dbBean.duration;
                            if (LetvUtils.getClientVersionCode() < 87 || TextUtils.isEmpty(dbBean.videoTypeKey)) {
                                videoBean.videoTypeKey = LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN;
                            } else {
                                videoBean.videoTypeKey = dbBean.videoTypeKey;
                            }
                        }
                    }
                    i++;
                }
            }
        }
        return videoBean;
    }

    private void requestPlayRecommend(VideoBean currPlayingVideo) {
        resetStatisticsInfo();
        if (this.mPlayType != PlayerType.FLOATING_WINDOW && this.mRecommendCount <= 20) {
            this.mRecommendCount++;
            Volley.getQueue().cancelWithTag(TAG_REQUEST_PLAY_RECOMMEND);
            new LetvRequest().setRequestType(RequestManner.CACHE_THEN_NETROWK).setTag(TAG_REQUEST_PLAY_RECOMMEND).setParser(new PlayRecommendParser()).setCallback(new 4(this, currPlayingVideo)).add();
        }
    }

    private void requestEpisodeNextPage(AlbumCardList cardList, VideoBean currPlayingVideo, String curPage) {
        if (this.mPlayType != PlayerType.FLOATING_WINDOW) {
            Volley.getQueue().cancelWithTag(TAG_REQUEST_EPISODE_NEXT_PAGE);
            new LetvRequest().setUrl(MediaAssetApi.getInstance().getEpisodeVListUrl(String.valueOf(currPlayingVideo.pid), curPage, String.valueOf(50))).setParser(new VideoListParser()).setTag(TAG_REQUEST_EPISODE_NEXT_PAGE).setCallback(new 5(this, cardList, curPage)).add();
        }
    }

    private void initDataForEpisode(AlbumCardList cardList, String currPage, VideoListBean list) {
        if (!BaseTypeUtils.isListEmpty(list)) {
            cardList.videoList.videoListMap.put(String.valueOf(currPage), list);
            if (!(cardList == null || cardList.videoList == null || cardList.videoList.videoList == null)) {
                cardList.videoList.videoList.clear();
                for (String key : cardList.videoList.videoListMap.keySet()) {
                    cardList.videoList.videoList.addAll((Collection) cardList.videoList.videoListMap.get(key));
                }
            }
            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(list, 0));
            this.mNextVideoWz = 1;
        }
    }

    private void requestPeriodsVideolist(AlbumCardList cardList, VideoBean currPlayingVideo, String year) {
        if (this.mPlayType != PlayerType.FLOATING_WINDOW) {
            Volley.getQueue().cancelWithTag(TAG_REQUEST_PERIODS_NEXT_PAGE);
            new LetvRequest(VideoListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setUrl(MediaAssetApi.getInstance().getPeriodsVListUrl(String.valueOf(currPlayingVideo.pid), year, "")).setParser(new VideoListParser()).setTag(TAG_REQUEST_PERIODS_NEXT_PAGE).setCallback(new 6(this, cardList, year)).add();
        }
    }

    private void initDataForPeriods(AlbumCardList cardList, String year, VideoListBean list) {
        if (!BaseTypeUtils.isListEmpty(list)) {
            cardList.videoList.periodsVideoListMap.put(year, list);
            setVideoBean((VideoBean) BaseTypeUtils.getElementFromList(list, 0));
        }
    }

    private void setVideoBean(VideoBean video) {
        this.mNextVideo = video;
        if (this.mCallBack != null) {
            this.mCallBack.callBack(video, false);
        }
        if (video != null) {
            this.mPlayNextBtnController.setPlayNextAble(true);
        }
    }

    private void setLeboxVideoBean(LeboxVideoBean leboxVideoBean) {
        this.mNextLeboxVideo = leboxVideoBean;
        if (leboxVideoBean != null) {
            this.mPlayNextBtnController.setPlayNextAble(true);
        }
    }

    private void setRecommendBean(VideoBean video) {
        this.mRecommendVideo = video;
        if (this.mCallBack != null) {
            this.mCallBack.callBack(video, true);
        }
        if (video != null) {
            this.mPlayNextBtnController.setPlayNextAble(true);
        }
    }

    private void setAlbumInfo(AlbumInfo albumInfo) {
        this.mNextAlbum = albumInfo;
        if (this.mCallBack != null) {
            this.mCallBack.callBack(albumInfo, false);
        }
        if (albumInfo != null) {
            this.mPlayNextBtnController.setPlayNextAble(true);
        }
    }

    private void resetStatisticsInfo() {
        this.mNextVideoFl = NetworkUtils.DELIMITER_LINE;
        this.mNextVideoWz = -1;
    }
}
