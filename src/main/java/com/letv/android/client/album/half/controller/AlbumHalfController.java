package com.letv.android.client.album.half.controller;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.controller.AlbumCacheController.CacheState;
import com.letv.android.client.album.controller.AlbumCollectController.CollectState;
import com.letv.android.client.album.controller.AlbumPlayNextController;
import com.letv.android.client.album.half.AlbumHalfFragment;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.parser.VideoListParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;
import com.letv.pp.utils.NetworkUtils;

public class AlbumHalfController {
    public static final String REQUEST_EPISODE_EVIDEO_LIST_TAG = "half_tag_requestEpisodeVideolist";
    public static final String REQUEST_PERIODS_EVIDEO_LIST_TAG = "half_tag_requestPeriodsVideolist";
    private AlbumPlayActivity mActivity;

    public AlbumHalfController(AlbumPlayActivity activity) {
        this.mActivity = activity;
    }

    public void play(VideoBean video) {
        if (this.mActivity.getFlow() != null) {
            this.mActivity.getFlow().play(video);
            refreshHalfFragment(video);
        }
        if (!(!this.mActivity.mIsPlayingDlna || this.mActivity.getVideoController() == null || this.mActivity.getVideoController().mDlnaProtocol == null)) {
            this.mActivity.getVideoController().mDlnaProtocol.protocolPlayOther();
        }
        resetStatusBar();
        updateStatusBar();
        findNextVideo();
    }

    public void playLebox(@NonNull LeboxVideoBean leboxVideoBean) {
        AlbumPlayFlow albumPlayFlow = this.mActivity.getFlow();
        if (albumPlayFlow != null) {
            LeboxVideoBean curLexVideo = albumPlayFlow.mBoxBean;
            if (curLexVideo == null || curLexVideo.vid == null || !curLexVideo.vid.equals(leboxVideoBean.vid)) {
                albumPlayFlow.playLebox(leboxVideoBean);
                findNextVideo();
            }
        }
    }

    public void findNextVideo() {
        AlbumHalfFragment halfFragment = this.mActivity.getHalfFragment();
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (halfFragment != null && flow != null) {
            this.mActivity.getPlayNextController().findNextVideo(halfFragment.getAlbumCardList(), flow.mCurrentPlayingVideo, halfFragment.getPageCard(), null);
        }
    }

    public void playNext() {
        AlbumPlayNextController playNextController = this.mActivity.getPlayNextController();
        AlbumHalfFragment fragment = this.mActivity.getHalfFragment();
        AlbumPlayFlow flow = this.mActivity.getFlow();
        if (playNextController != null && fragment != null && flow != null) {
            if (this.mActivity.getLoadListener() != null) {
                this.mActivity.getLoadListener().loading();
            }
            StatisticsUtils.sPlayFromCard = true;
            String fl = playNextController.mNextVideoFl;
            int wz = playNextController.mNextVideoWz;
            if (!(TextUtils.equals(fl, NetworkUtils.DELIMITER_LINE) || TextUtils.isEmpty(fl))) {
                wz = wz == -1 ? 2 : wz + 1;
            }
            if (flow.mPlayInfo.mIsStatisticsPlay) {
                StatisticsUtils.sLastPlayRef = StatisticsUtils.getPlayInfoRef(-1);
            }
            StatisticsUtils.setActionProperty(fl, wz, UIsUtils.isLandscape(this.mActivity) ? PageIdConstant.fullPlayPage : PageIdConstant.halpPlayPage);
            LogInfo.LogStatistics("playNext----fl=" + fl + "----wz=" + wz);
            if (this.mActivity.mIsLebox) {
                LeboxVideoBean nextLeboxBean = playNextController.mNextLeboxVideo;
                if (nextLeboxBean != null) {
                    playLebox(nextLeboxBean);
                    return;
                } else {
                    this.mActivity.finish();
                    return;
                }
            }
            AlbumCardList cardList = fragment.getAlbumCardList();
            if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
                VideoBean nextVideo = playNextController.mNextVideo;
                VideoBean recommendVideo = playNextController.mRecommendVideo;
                AlbumInfo nextAlbum = playNextController.mNextAlbum;
                if (nextVideo == null && recommendVideo == null && nextAlbum == null) {
                    this.mActivity.finish();
                    return;
                } else if (nextAlbum != null || (nextVideo != null && nextVideo.vid == 0)) {
                    long zid = fragment.getCombineParams() != null ? BaseTypeUtils.stol(fragment.getCombineParams().zid) : 0;
                    fragment.closeExpand();
                    StatisticsUtils.sCont = 1;
                    boolean noCopyright = nextAlbum != null ? nextAlbum.noCopyright : nextVideo.noCopyright;
                    String noCopyrightUrl = nextAlbum != null ? nextAlbum.externalUrl : nextVideo.externalUrl;
                    if (playNextController.mIsPlayingRelate) {
                        if (zid == 0) {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextAlbum != null ? nextAlbum.pid : nextVideo.pid, 0, 25, noCopyright, noCopyrightUrl)));
                        } else {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).createTopic(zid, nextAlbum != null ? nextAlbum.pid : nextVideo.pid, 0, 25, noCopyright, noCopyrightUrl)));
                        }
                    } else if (this.mActivity.mIsPlayingNonCopyright) {
                        if (zid == 0) {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextAlbum != null ? nextAlbum.pid : nextVideo.pid, 0, 25, true, "-1")));
                        } else {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).createTopic(zid, nextAlbum != null ? nextAlbum.pid : nextVideo.pid, 0, 25, true, "-1")));
                        }
                    } else if (zid == 0) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextAlbum != null ? nextAlbum.pid : nextVideo.pid, 0, 25)));
                    } else {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).createTopic(zid, nextAlbum != null ? nextAlbum.pid : nextVideo.pid, 0, 25)));
                    }
                    flow.addPlayInfo("续播专辑", "");
                    return;
                } else if (nextVideo != null) {
                    if (nextVideo.noCopyright) {
                        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextVideo.pid, nextVideo.vid, 25, true, nextVideo.externalUrl)));
                        return;
                    }
                    StatisticsUtils.sCont = -1;
                    if (playNextController.mIsPlayingWatch) {
                        play(nextVideo);
                    } else if (this.mActivity.getHalfFragment().getAlbumCardList() != null && this.mActivity.getHalfFragment().getAlbumCardList().videoList.style == 3 && playNextController.mPlayPeriods) {
                        fragment.closeExpand();
                        if (this.mActivity.mIsPlayingNonCopyright) {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextVideo.pid, nextVideo.vid, 25, true, "-1")));
                        } else {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextVideo.pid, nextVideo.vid, 25)));
                        }
                        StatisticsUtils.sCont = 1;
                        StatisticsUtils.sPlayStatisticsRelateInfo.mIsRecommend = nextVideo.isRec;
                        StatisticsUtils.sPlayStatisticsRelateInfo.mReid = nextVideo.reid;
                    } else {
                        if (playNextController.mIsPlayingRelate) {
                            if (!fragment.isSameAlbumWithCurrentPlayingVideo(nextVideo.pid)) {
                                fragment.closeExpand();
                                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextVideo.pid, nextVideo.vid, 25)));
                            }
                        }
                        if (playNextController.mIsPlayingRelate && this.mActivity.mIsPlayingNonCopyright) {
                            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(nextVideo.pid, nextVideo.vid, 25)));
                        } else {
                            StatisticsUtils.sCont = 1;
                            play(nextVideo);
                        }
                    }
                    flow.addPlayInfo("续播视频", "");
                    return;
                } else if (recommendVideo == null || this.mActivity.mIsPlayingNonCopyright) {
                    flow.addPlayInfo("无法续播，关闭页面", "");
                    this.mActivity.finish();
                    return;
                } else {
                    fragment.closeExpand();
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.mActivity).create(recommendVideo.pid, recommendVideo.vid, 25)));
                    StatisticsUtils.sCont = 0;
                    StatisticsUtils.sPlayStatisticsRelateInfo.mIsRecommend = recommendVideo.isRec;
                    StatisticsUtils.sPlayStatisticsRelateInfo.mReid = recommendVideo.reid;
                    flow.addPlayInfo("续播推荐视频", "");
                    return;
                }
            }
            playNextController.findNextVideo(cardList, flow.mCurrentPlayingVideo, fragment.getPageCard(), new 1(this, cardList, fragment));
        }
    }

    public void resetStatusBar() {
        this.mActivity.getCollectController().setCollectState(CollectState.DISABLE_COLLECT);
        this.mActivity.getCacheController().setCacheState(CacheState.DISABLE_CACHE);
    }

    public void updateStatusBar() {
        this.mActivity.getCollectController().setCollectState(CollectState.DISABLE_COLLECT);
        updateFavStatus();
        this.mActivity.getCacheController().updateCacheState();
    }

    private void updateFavStatus() {
        VideoBean videoBean = getVideoBean();
        if (videoBean == null) {
            return;
        }
        if (videoBean.pid != 0 || videoBean.vid != 0) {
            if (!PreferencesManager.getInstance().isLogin()) {
                new 3(this, this.mActivity.getCollectController().generateAlbumNew()).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
            } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
                DBManager.getInstance().getFavoriteTrace().requestGetIsFavourite(videoBean.pid, videoBean.vid, 1, new 2(this));
            }
        }
    }

    private void refreshHalfFragment(VideoBean video) {
        if (this.mActivity.getHalfFragment() != null) {
            this.mActivity.getHalfFragment().setCurrPlayingVideo(video);
            this.mActivity.getHalfFragment().refreshView(false, false);
            this.mActivity.getHalfFragment().refreshPlayingVideoInfo();
        }
    }

    public VolleyRequest<VideoListBean> requestEpisodeVideolist(long pid, int curPage, int pageSize, SimpleResponse simpleResponse) {
        return new LetvRequest(VideoListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setAlwaysCallbackNetworkResponse(true).setUrl(MediaAssetApi.getInstance().getEpisodeVListUrl(String.valueOf(pid), String.valueOf(curPage), String.valueOf(pageSize))).setParser(new VideoListParser()).setTag(REQUEST_EPISODE_EVIDEO_LIST_TAG).setCallback(simpleResponse).add();
    }

    public VolleyRequest<VideoListBean> requestPeriodsVideolist(long pid, String year, SimpleResponse simpleResponse) {
        return new LetvRequest(VideoListBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setAlwaysCallbackNetworkResponse(true).setUrl(MediaAssetApi.getInstance().getPeriodsVListUrl(String.valueOf(pid), year, "")).setParser(new VideoListParser()).setTag(REQUEST_PERIODS_EVIDEO_LIST_TAG).setCallback(simpleResponse).add();
    }

    private VideoBean getVideoBean() {
        if (this.mActivity.getHalfFragment() != null) {
            return this.mActivity.getHalfFragment().getCurrPlayingVideo();
        }
        return null;
    }
}
