package com.letv.android.client.album.half;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.letv.android.client.album.AlbumPlayActivity;
import com.letv.android.client.album.R;
import com.letv.android.client.album.half.adapter.AlbumHalfAdapter;
import com.letv.android.client.album.half.controller.AlbumCompositeInterface;
import com.letv.android.client.album.half.controller.AlbumHalfAdController;
import com.letv.android.client.album.half.controller.AlbumHalfBaseController;
import com.letv.android.client.album.half.controller.AlbumHalfCmsOperateController;
import com.letv.android.client.album.half.controller.AlbumHalfCommentController;
import com.letv.android.client.album.half.controller.AlbumHalfCommentHeadController;
import com.letv.android.client.album.half.controller.AlbumHalfCommentVoteController;
import com.letv.android.client.album.half.controller.AlbumHalfFullVersionController;
import com.letv.android.client.album.half.controller.AlbumHalfGridController;
import com.letv.android.client.album.half.controller.AlbumHalfIntroController;
import com.letv.android.client.album.half.controller.AlbumHalfListController;
import com.letv.android.client.album.half.controller.AlbumHalfMusicController;
import com.letv.android.client.album.half.controller.AlbumHalfPeriodsController;
import com.letv.android.client.album.half.controller.AlbumHalfPositionInterface;
import com.letv.android.client.album.half.controller.AlbumHalfRedPacketController;
import com.letv.android.client.album.half.controller.AlbumHalfRelateController;
import com.letv.android.client.album.half.controller.AlbumHalfStarController;
import com.letv.android.client.album.half.controller.AlbumHalfSurroundingController;
import com.letv.android.client.album.half.controller.AlbumHalfTopicAlbumListController;
import com.letv.android.client.album.half.controller.AlbumHalfVideoRelateController;
import com.letv.android.client.album.half.controller.AlbumHalfVipController;
import com.letv.android.client.album.half.controller.AlbumHalfWatchController;
import com.letv.android.client.album.half.controller.AlbumHalfYourLikeController;
import com.letv.android.client.album.half.fragment.AlbumHalfExpandFragment;
import com.letv.android.client.album.half.helper.AlbumCardCommonInfoSetter;
import com.letv.android.client.album.half.helper.AlbumCardEpisodeInfoSetter;
import com.letv.android.client.album.half.widget.HSlowListView;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.business.flow.album.AlbumPlayFlow;
import com.letv.business.flow.album.AlbumPlayFlowObservable;
import com.letv.business.flow.album.AlbumPlayFlowObservable.RequestCombineParams;
import com.letv.business.flow.album.PlayObservable;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.api.MediaAssetApi;
import com.letv.core.bean.AlbumCardList;
import com.letv.core.bean.AlbumInfo;
import com.letv.core.bean.AlbumPageCard;
import com.letv.core.bean.BaseIntroductionBean;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.VideoBean;
import com.letv.core.constant.LetvConstant;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.pagecard.AlbumPageCardFetcher;
import com.letv.core.parser.AlbumCardParser;
import com.letv.core.parser.IntroductionParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.util.DataUtils;
import com.letv.download.bean.DownloadVideo;
import com.letv.download.manager.DownloadManager;
import com.letv.lemallsdk.util.Constants;
import com.letv.mobile.lebox.LeboxApiManager;
import com.letv.mobile.lebox.LeboxApiManager.LeboxVideoBean;
import com.tencent.open.yyb.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlbumHalfFragment extends AlbumCompositeInterface implements Observer, LoaderCallbacks<Cursor> {
    public static final String HALF_TAG = "half_tag_";
    private static final int LOADER_MANAGER_ID = 4660;
    private static final String LOG_TAG = AlbumHalfFragment.class.getSimpleName();
    public static boolean sHasFindPlayingVideoInWatch;
    private AlbumHalfExpandFragment expendFragment;
    private AtomicBoolean isRequestingAd = new AtomicBoolean(false);
    private boolean lastNetStateAvailable = NetworkUtils.isNetworkAvailable();
    public Pair<ArrayList<LeboxVideoBean>, ArrayList<VideoBean>> leboxPairBean = new Pair(new ArrayList(), new ArrayList());
    private AlbumPlayActivity mActivity;
    private AlbumHalfAdController mAdController1;
    private AlbumHalfAdController mAdController2;
    private AlbumHalfAdapter mAdapter;
    private AlbumCardList mAlbumCardList;
    private AlbumHalfCmsOperateController mCmsOperateController;
    private RequestCombineParams mCombineParams;
    private AlbumHalfCommentController mCommentController;
    private AlbumHalfCommentHeadController mCommentHeadController;
    private AlbumHalfCommentVoteController mCommentVoteController;
    public AlbumCardCommonInfoSetter mCommonInfoSetter;
    public List<AlbumHalfPositionInterface> mControllerList = new ArrayList();
    private VideoBean mCurrPlayingVideo;
    public Map<Long, DownloadVideo> mDownloadedVideoMap = new HashMap();
    public AlbumCardEpisodeInfoSetter mEpisodeInfoSetter;
    private View mFooterView;
    private FrameLayout mFragmentContain;
    private AlbumHalfFullVersionController mFullVersionController;
    public AlbumHalfGridController mGridController;
    private AlbumHalfIntroController mIntroController;
    public AtomicBoolean mIsOnClickWatch = new AtomicBoolean(true);
    public boolean mIsPlayNext = false;
    public boolean mIsReloadHalfScreen = false;
    public AlbumHalfListController mListController;
    private HSlowListView mListView;
    private AlbumHalfMusicController mMusicController;
    public boolean mOpenCommentDetail = false;
    private AlbumPageCard mPageCard;
    public AlbumHalfPeriodsController mPeriodsController;
    public List<Integer> mPlayRecordVidList = new ArrayList();
    private AlbumHalfRedPacketController mRedPacketController;
    public String mReid = "";
    public AlbumHalfRelateController mRelateController;
    private PublicLoadLayout mRoot;
    private AlbumHalfStarController mStarController;
    private AlbumHalfSurroundingController mSurroundingController;
    private AlbumHalfTopBar mTopBar;
    private AlbumHalfTopicAlbumListController mTopicAlbumListController;
    private AlbumHalfVideoRelateController mVideoRelateController;
    private AlbumHalfVipController mVipController;
    public AlbumHalfWatchController mWatchController;
    private AlbumHalfYourLikeController mYourLikeController;

    public AlbumHalfFragment(AlbumPlayActivity activity) {
        super(activity, null);
        this.mActivity = activity;
        init();
        initTask();
    }

    private void init() {
        this.mActivity.getSupportLoaderManager().initLoader(LOADER_MANAGER_ID, null, this);
        this.mListView = new HSlowListView(this.mActivity);
        this.mListView.setLayoutParams(new LayoutParams(-1, -1));
        this.mListView.setSelector(new ColorDrawable(0));
        this.mListView.setCacheColorHint(this.mActivity.getResources().getColor(R.color.letv_color_00000000));
        this.mListView.setDivider(null);
        this.mListView.setFadingEdgeLength(0);
        this.mRoot = PublicLoadLayout.createPage(this.mActivity, this.mListView);
        ((FrameLayout) this.mActivity.findViewById(R.id.play_album_half_frame)).addView(this.mRoot);
        this.mFooterView = new View(this.mActivity);
        this.mFooterView.setBackgroundColor(Color.parseColor("#f1f1f1"));
        this.mFooterView.setLayoutParams(new AbsListView.LayoutParams(-1, UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN)));
        this.mFragmentContain = (FrameLayout) this.mActivity.findViewById(R.id.play_album_fragment_contain);
        this.mTopBar = new AlbumHalfTopBar(this.mActivity, this);
        this.mAdapter = new AlbumHalfAdapter(this.mActivity, this.mControllerList);
        this.expendFragment = (AlbumHalfExpandFragment) this.mActivity.getSupportFragmentManager().findFragmentById(R.id.play_album_expend_fragment);
        this.mCommentController = new AlbumHalfCommentController(this.mActivity, this);
        this.mCommentHeadController = new AlbumHalfCommentHeadController(this.mActivity, this);
        this.mCommentController.setHalfCommentHeadController(this.mCommentHeadController);
        this.mCommentHeadController.setCommentController(this.mCommentController);
        this.mCommentVoteController = new AlbumHalfCommentVoteController(this.mActivity, this);
        this.mVipController = new AlbumHalfVipController(this.mActivity, this);
        this.mIntroController = new AlbumHalfIntroController(this.mActivity, this);
        this.mGridController = new AlbumHalfGridController(this.mActivity, this);
        this.mListController = new AlbumHalfListController(this.mActivity, this);
        this.mPeriodsController = new AlbumHalfPeriodsController(this.mActivity, this);
        this.mTopicAlbumListController = new AlbumHalfTopicAlbumListController(this.mActivity, this);
        this.mFullVersionController = new AlbumHalfFullVersionController(this.mActivity, this);
        this.mSurroundingController = new AlbumHalfSurroundingController(this.mActivity, this);
        this.mRelateController = new AlbumHalfRelateController(this.mActivity, this);
        this.mYourLikeController = new AlbumHalfYourLikeController(this.mActivity, this);
        this.mMusicController = new AlbumHalfMusicController(this.mActivity, this);
        this.mStarController = new AlbumHalfStarController(this.mActivity, this);
        this.mVideoRelateController = new AlbumHalfVideoRelateController(this.mActivity, this);
        this.mWatchController = new AlbumHalfWatchController(this.mActivity, this);
        this.mCmsOperateController = new AlbumHalfCmsOperateController(this.mActivity, this);
        this.mRedPacketController = new AlbumHalfRedPacketController(this.mActivity, this);
        this.mAdController1 = new AlbumHalfAdController(this.mActivity, this, 1);
        this.mAdController2 = new AlbumHalfAdController(this.mActivity, this, 2);
        this.mCommonInfoSetter = new AlbumCardCommonInfoSetter(this.mActivity, this);
        this.mEpisodeInfoSetter = new AlbumCardEpisodeInfoSetter(this.mActivity, this);
        this.mRoot.loading(false);
        this.mRoot.setRefreshData(new 1(this));
    }

    private void initTask() {
        LeMessageManager.getInstance().registerRxOnMainThread(104).subscribe(new 2(this));
    }

    public AlbumPlayActivity getAlbumPlayActivity() {
        return this.mActivity;
    }

    public void requestRefreshCard() {
        if (this.mPageCard == null) {
            requestPageCard();
        } else {
            requestPlayCard();
        }
    }

    private void requestPageCard() {
        LogInfo.log("half_tab", "---requestPageCard---");
        this.mTopBar.hide();
        this.mCommentHeadController.hideSendOutsideView();
        if (isLebox()) {
            AlbumPageCardFetcher.fetchDefaultPageCard(this.mActivity, new 3(this));
        } else {
            AlbumPageCardFetcher.fetchPageCard(this.mActivity, new 4(this));
        }
    }

    private void requestPlayCard() {
        if (this.mCombineParams != null && !TextUtils.isEmpty(this.mCombineParams.cid + this.mCombineParams.pid + this.mCombineParams.vid + this.mCombineParams.zid)) {
            LogInfo.log("half_tab", "---requestPlayCard---");
            this.mTopBar.hide();
            this.mCommentHeadController.hideSendOutsideView();
            this.mActivity.getHalfController().resetStatusBar();
            this.mRoot.loading(false);
            this.mAdapter.clear();
            new LetvRequest().setTag("half_tag_play_card").setCache(new VolleyDiskCache("requestPlayCard" + this.mCombineParams.cid + this.mCombineParams.pid + this.mCombineParams.vid + this.mCombineParams.zid)).setUrl(LetvUrlMaker.getPlayCardsUrl(this.mCombineParams.cid, this.mCombineParams.pid, this.mCombineParams.vid, this.mCombineParams.zid, PreferencesManager.getInstance().getUserId())).setParser(new AlbumCardParser()).setAlwaysCallbackNetworkResponse(true).setCallback(new 5(this)).add();
        }
    }

    private void requestAd(@NonNull AlbumHalfAdController... adControllers) {
        if (this.mCombineParams == null) {
            LogInfo.log("half_tag_", "mCombineParams == null 不请求广告");
        } else if (!this.isRequestingAd.getAndSet(true)) {
            for (AlbumHalfAdController adController : adControllers) {
                adController.onPrepareRequestAd();
            }
            HashMap<String, String> map = new HashMap();
            map.put(Constants.UUID, DataUtils.getUUID(this.mActivity));
            map.put("uid", PreferencesManager.getInstance().getUserId());
            map.put("py", "");
            map.put("ty", "0");
            map.put("cid", this.mCombineParams.cid);
            map.put("pid", this.mCombineParams.pid);
            map.put("vid", this.mCombineParams.vid);
            new Thread(new 6(this, map, adControllers)).start();
        }
    }

    public void refreshPlayingVideoInfo() {
        LogInfo.log("songhang", "刷新视频");
        refreshIntro();
        refreshCommentInfo();
    }

    private void refreshIntro() {
        if (this.mCombineParams != null) {
            IntroductionParser introductionParser = new IntroductionParser();
            introductionParser.setCid(BaseTypeUtils.stoi(this.mCombineParams.cid));
            introductionParser.setZid(BaseTypeUtils.stoi(this.mCombineParams.zid));
            new LetvRequest(BaseIntroductionBean.class).setUrl(MediaAssetApi.getInstance().getIntroduceDataUrl("0", this.mCombineParams.cid, this.mCombineParams.zid, this.mCombineParams.vid, this.mCombineParams.pid)).setParser(introductionParser).setCallback(new 7(this)).add();
        }
    }

    public void refreshCommentInfo() {
        if (this.mPageCard != null && isAlbum()) {
            this.mCommentController.refreshComment(this.mPageCard);
            this.mCommentVoteController.refreshVote();
        }
    }

    public void refreshCommentOnly() {
        if (this.mPageCard != null && this.mAlbumCardList != null && this.mAlbumCardList.mIsAlbum && this.mCommentController != null) {
            this.mCommentController.refreshCommentList();
        }
    }

    private void initData(AlbumCardList result, boolean isNetSuccess) {
        this.mAlbumCardList = result;
        if (this.mAlbumCardList != null) {
            boolean z;
            if (result.videoInfo == null || result.videoInfo.vid <= 0) {
                AlbumPlayFlow flow = this.mActivity.getFlow();
                if (flow != null && result.videoList != null && !BaseTypeUtils.isListEmpty(result.videoList.videoList)) {
                    for (VideoBean video : result.videoList.videoList) {
                        if (flow.mVid != 0) {
                            if (video.vid == flow.mVid) {
                                setCurrPlayingVideo(video);
                                break;
                            }
                        }
                        setCurrPlayingVideo(video);
                        break;
                    }
                }
            } else {
                setCurrPlayingVideo(result.videoInfo);
            }
            if (this.mActivity.getFlow() != null) {
                this.mActivity.getFlow().setAlbum(result.albumInfo);
            }
            if (isNetSuccess) {
                refreshCommentInfo();
                this.mActivity.getHalfController().updateStatusBar();
            }
            if (this.mActivity.getVideoController() != null) {
                this.mActivity.getVideoController().checkVideoType(result);
            }
            this.mTopBar.show();
            this.mTopBar.setShareEnable(true);
            this.mTopBar.setAppEnable(true);
            this.mTopBar.setCommentEnable(this.mAlbumCardList.mIsAlbum);
            this.mIsReloadHalfScreen = true;
            onSuccessRequestPlayCard(isNetSuccess);
            if (isNetSuccess) {
                z = false;
            } else {
                z = true;
            }
            refreshView(true, z);
        }
    }

    private void onDownLoadFinished() {
        LogInfo.log(LOG_TAG, "download onLoadFinished notifyCardDataSetChanged");
        if (!BaseTypeUtils.isListEmpty(this.mControllerList)) {
            getDownload(new 8(this));
        }
    }

    public void refreshView(boolean isReloadHalfScreen, boolean isCacheSuccess) {
        if (this.mAlbumCardList != null) {
            sHasFindPlayingVideoInWatch = false;
            getDownload();
            getWatched();
            this.mPageCard.reOrderCards(this.mAlbumCardList, new 9(this, isReloadHalfScreen, isCacheSuccess));
        }
    }

    private void doRefresh(boolean isReloadHalfScreen, boolean isReloadCacheSuccess) {
        if (this.mAlbumCardList != null) {
            LogInfo.log("half_tab", "doRefresh");
            this.mControllerList.clear();
            AlbumCardList result = this.mAlbumCardList;
            setCardRecommendId(result);
            if (this.mPageCard.vipCard.position != -1) {
                this.mVipController.setData(this.mPageCard);
                this.mControllerList.add(this.mVipController);
            }
            if (this.mPageCard.introCard.position != -1) {
                this.mIntroController.setData(result.intro, this.mPageCard);
                this.mControllerList.add(this.mIntroController);
            }
            this.mCommentController.setCommentShow(this.mAlbumCardList.mIsAlbum);
            this.mListView.removeFooterView(this.mFooterView);
            if (!this.mAlbumCardList.mIsAlbum) {
                this.mListView.addFooterView(this.mFooterView);
            }
            if (this.mPageCard.watchCard.position != -1) {
                this.mWatchController.setData(result.videoList.fragmentList, this.mPageCard);
                this.mControllerList.add(this.mWatchController);
            }
            if (result.videoList != null) {
                if (result.videoList.style == 1) {
                    if (result.mIsAlbum) {
                        if (this.mPageCard.gridCard.position != -1) {
                            this.mGridController.setData(result.videoList, result.albumInfo, this.mPageCard, false, isReloadHalfScreen);
                            this.mControllerList.add(this.mGridController);
                        }
                    } else if (!(BaseTypeUtils.isListEmpty(result.topicAlbumList) || BaseTypeUtils.isListEmpty(result.videoList.videoList))) {
                        VideoBean video = (VideoBean) BaseTypeUtils.getElementFromList(result.videoList.videoList, 0);
                        if (video != null) {
                            long pid = video.pid;
                            int i = 0;
                            while (i < result.topicAlbumList.size()) {
                                AlbumInfo album = (AlbumInfo) result.topicAlbumList.get(i);
                                if (album.pid != pid) {
                                    i++;
                                } else if (this.mPageCard.gridCard.position != -1) {
                                    this.mGridController.setData(result.videoList, album, this.mPageCard, true, isReloadHalfScreen);
                                    this.mControllerList.add(this.mGridController);
                                }
                            }
                        }
                    }
                } else if (result.videoList.style == 2) {
                    if (this.mPageCard.listCard.position != -1) {
                        this.mListController.setData(result.videoList, result.albumInfo, this.mPageCard, !result.mIsAlbum, isReloadHalfScreen);
                        this.mControllerList.add(this.mListController);
                    }
                } else if (result.videoList.style == 3 && this.mPageCard.periodsCard.position != -1) {
                    this.mPeriodsController.setData(result.videoList, result.albumInfo, this.mPageCard, !result.mIsAlbum, isReloadHalfScreen);
                    this.mControllerList.add(this.mPeriodsController);
                }
            }
            if (this.mPageCard.topicAlbumCard.position != -1) {
                this.mTopicAlbumListController.setData(result.topicAlbumList, this.mPageCard);
                this.mControllerList.add(this.mTopicAlbumListController);
            }
            if (this.mPageCard.fullVersionCard.position != -1) {
                this.mFullVersionController.setData(result.fullVersionList, this.mPageCard);
                this.mControllerList.add(this.mFullVersionController);
            }
            if (this.mPageCard.surroundingCard.position != -1) {
                this.mSurroundingController.setData(result.outList, this.mPageCard);
                this.mControllerList.add(this.mSurroundingController);
            }
            if (this.mPageCard.relateCard.position != -1) {
                this.mRelateController.setData(result.relateBean, this.mPageCard);
                this.mControllerList.add(this.mRelateController);
            }
            if (this.mPageCard.yourLikeCard.position != -1) {
                this.mYourLikeController.setData(result.yourLikeList, this.mPageCard);
                this.mControllerList.add(this.mYourLikeController);
            }
            if (this.mPageCard.musicCard.position != -1) {
                this.mMusicController.setData(result.musicList, this.mPageCard);
                this.mControllerList.add(this.mMusicController);
            }
            if (this.mPageCard.starCard.position != -1) {
                this.mStarController.setData(result.starList, this.mPageCard);
                this.mControllerList.add(this.mStarController);
            }
            if (this.mPageCard.videoRelateCard.position != -1) {
                this.mVideoRelateController.setData(result.videoRelateList, this.mPageCard);
                this.mControllerList.add(this.mVideoRelateController);
            }
            if (this.mPageCard.cmsOperateCard.position != -1) {
                this.mCmsOperateController.setData(result.cmsOperateList, this.mPageCard);
                this.mControllerList.add(this.mCmsOperateController);
            }
            if (this.mPageCard.redPacketCard.position != -1) {
                this.mRedPacketController.setData(result.redPacket, this.mPageCard);
                this.mControllerList.add(this.mRedPacketController);
            }
            if (this.mPageCard.adCard1.position != -1) {
                this.mAdController1.setData(this.mPageCard.adCard1.position);
                this.mControllerList.add(this.mAdController1);
            }
            if (this.mPageCard.adCard2.position != -1) {
                this.mAdController2.setData(this.mPageCard.adCard2.position);
                this.mControllerList.add(this.mAdController2);
            }
            if (!((this.mPageCard.adCard1.position == -1 && this.mPageCard.adCard2.position == -1) || isReloadCacheSuccess)) {
                requestAd(this.mAdController1, this.mAdController2);
            }
            if (this.mPageCard.voteCard.position != -1) {
                this.mCommentVoteController.controllerPosition = this.mPageCard.voteCard.position;
                this.mControllerList.add(this.mCommentVoteController);
            }
            if (isAlbum()) {
                this.mControllerList.add(this.mCommentHeadController);
            }
            Collections.sort(this.mControllerList, new 10(this));
            Iterator<AlbumHalfPositionInterface> iterator = this.mControllerList.iterator();
            while (iterator.hasNext()) {
                if (((AlbumHalfPositionInterface) iterator.next()).controllerPosition < 0) {
                    iterator.remove();
                }
            }
            if (this.mListView.getAdapter() == null || (isReloadHalfScreen && this.mListView.getChildCount() > 0)) {
                this.mListView.setAdapter(this.mAdapter);
            } else {
                this.mAdapter.notifyDataSetChanged();
            }
            this.mIsReloadHalfScreen = false;
            this.mRoot.finish();
            if (UIsUtils.isLandscape(this.mActivity)) {
                this.mActivity.getVideoController().refreshEpisodeView();
            }
            LogInfo.log("half_tab", "doRefresh end");
        }
    }

    private void getDownload() {
        getDownload(null);
    }

    public void getDownload(Runnable runnable) {
        LogInfo.log("half_tab", "download");
        new 11(this, runnable).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new String[0]);
    }

    private void getWatched() {
        if (this.mCurrPlayingVideo != null && this.mCurrPlayingVideo.pid > 0) {
            new 12(this).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new String[0]);
        }
    }

    public void update(Observable observable, Object data) {
        if (data instanceof RequestCombineParams) {
            this.mCombineParams = (RequestCombineParams) data;
            requestPageCard();
        } else if (data instanceof String) {
            String notify = (String) data;
            if (TextUtils.equals(PlayObservable.ON_NET_CHANGE, notify)) {
                onNetChange();
                if (this.mTopBar != null) {
                    this.mTopBar.onNetChange();
                    boolean curNetStateAvailable = NetworkUtils.isNetworkAvailable();
                    if (curNetStateAvailable && !this.lastNetStateAvailable) {
                        LogInfo.log("songhang", "----------- 半屏无网切换有网刷新topBar");
                        this.mActivity.getHalfController().updateStatusBar();
                    }
                    this.lastNetStateAvailable = curNetStateAvailable;
                }
            }
            if (TextUtils.equals(AlbumPlayFlowObservable.REFRESH_DATA_AFTER_REQUEST_VIDEO_URL, notify)) {
                this.mActivity.getHalfController().updateStatusBar();
            }
        }
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.mActivity, DownloadManager.DOWNLOAD_ALBUM_URI, null, "albumVideoNum != 0", null, "timestamp DESC ");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == LOADER_MANAGER_ID) {
            onDownLoadFinished();
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onDestroy() {
        super.onDestroy();
        this.mActivity.getSupportLoaderManager().destroyLoader(LOADER_MANAGER_ID);
    }

    public AlbumHalfAdapter getAdapter() {
        return this.mAdapter;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public VideoBean getCurrPlayingVideo() {
        return this.mCurrPlayingVideo;
    }

    public void setCurrPlayingVideo(VideoBean video) {
        this.mCurrPlayingVideo = video;
        if (video != null) {
            onPlayVideo();
            String zid = video.zid;
            boolean isTopic = (this.mAlbumCardList == null || this.mAlbumCardList.mIsAlbum) ? false : true;
            if (isTopic) {
                zid = this.mCombineParams.zid;
            }
            this.mCombineParams = new RequestCombineParams(video.pid + "", video.vid + "", video.cid + "", zid);
            if (isTopic && this.mAlbumCardList != null && !BaseTypeUtils.isListEmpty(this.mAlbumCardList.topicAlbumList)) {
                Iterator it = this.mAlbumCardList.topicAlbumList.iterator();
                while (it.hasNext()) {
                    AlbumInfo album = (AlbumInfo) it.next();
                    if (album.pid == video.pid) {
                        this.mAlbumCardList.albumInfo = album;
                        break;
                    }
                }
            }
            if (this.mActivity.getFlow().mIsDownloadFile) {
                String title = video.nameCn;
                if (TextUtils.isEmpty(title)) {
                    title = video.title;
                }
                this.mActivity.getVideoController().setTitle(title);
            }
        }
    }

    public RequestCombineParams getCombineParams() {
        return this.mCombineParams;
    }

    public AlbumCardList getAlbumCardList() {
        return this.mAlbumCardList;
    }

    public int getChannelId() {
        if (isLebox()) {
            return BaseTypeUtils.stoi(this.mActivity.getFlow().mBoxBean.cid);
        }
        if (this.mAlbumCardList == null) {
            return -1;
        }
        return this.mAlbumCardList.videoInfo.cid;
    }

    public AlbumInfo getAlbumInfo() {
        if (this.mAlbumCardList != null) {
            return this.mAlbumCardList.albumInfo;
        }
        return null;
    }

    public AlbumPageCard getPageCard() {
        return this.mPageCard;
    }

    public List<AlbumHalfPositionInterface> getControllerList() {
        return this.mControllerList;
    }

    public boolean isPlayingVideo(LetvBaseBean bean) {
        boolean z = true;
        if (bean == null) {
            return false;
        }
        VideoBean curVideoBean = isLebox() ? convertLeboxBeanToVideoBean(this.mActivity.getFlow().mBoxBean) : getCurrPlayingVideo();
        if (curVideoBean == null) {
            return false;
        }
        if (bean instanceof VideoBean) {
            if (((VideoBean) bean).vid != curVideoBean.vid) {
                z = false;
            }
            return z;
        } else if (!(bean instanceof AlbumInfo)) {
            return false;
        } else {
            if (((AlbumInfo) bean).pid != curVideoBean.pid) {
                z = false;
            }
            return z;
        }
    }

    public boolean isSameAlbumWithCurrentPlayingVideo(long pid) {
        VideoBean video = getCurrPlayingVideo();
        return video != null && video.pid == pid;
    }

    public boolean hasPlayed(LetvBaseBean bean) {
        if (!(bean instanceof VideoBean) || BaseTypeUtils.isListEmpty(this.mPlayRecordVidList)) {
            return false;
        }
        return this.mPlayRecordVidList.contains(Integer.valueOf((int) ((VideoBean) bean).vid));
    }

    public <T extends LetvBaseBean> int getPlayingPositionOfList(List<T> list) {
        if (BaseTypeUtils.isListEmpty(list)) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            if (isPlayingVideo((LetvBaseBean) list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public void expandMore(AlbumHalfBaseController baseController) {
        if (baseController != null) {
            updateExpandLayoutParams();
            baseController.statisticsCardExpandMore();
            this.expendFragment.open(UIsUtils.isLandscape() ? baseController.generateLandscapeExpendContainerView() : baseController.generatePortraitExpendContainerView(), this.mPageCard, baseController.getTitle(), baseController.getSubTitle());
        }
    }

    public void openExpandError() {
        openExpandError(null);
    }

    public void openExpandError(String errorStr) {
        updateExpandLayoutParams();
        this.expendFragment.openErrorView(errorStr);
    }

    private void updateExpandLayoutParams() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        if (UIsUtils.isLandscape(this.mActivity)) {
            lp.width = AlbumPlayActivity.LANDSCAPE_EXPAND_WIDTH;
            lp.addRule(11);
        } else {
            lp.topMargin = this.mActivity.getHalfVideoHeight();
            lp.addRule(10);
        }
        this.mFragmentContain.setLayoutParams(lp);
    }

    public void closeExpand() {
        this.expendFragment.close();
    }

    public void share(int type, String comment) {
        if (this.mActivity.getShareWindowProtocol() != null) {
            this.mActivity.getShareWindowProtocol().share(type, comment, this.mListView, this.mCurrPlayingVideo, this.mAlbumCardList);
        }
    }

    public void share() {
        if (this.mActivity.getShareWindowProtocol() != null) {
            this.mActivity.getShareWindowProtocol().share(this.mListView, this.mCurrPlayingVideo, this.mAlbumCardList);
        }
    }

    public boolean isLebox() {
        return this.mActivity != null && this.mActivity.mIsLebox;
    }

    public boolean isAlbum() {
        return this.mAlbumCardList != null && this.mAlbumCardList.mIsAlbum;
    }

    public AlbumHalfExpandFragment getExpendFragment() {
        return this.expendFragment;
    }

    public void getLeboxBeanListAndConvertVideoBeanList() {
        ((ArrayList) this.leboxPairBean.first).clear();
        ((ArrayList) this.leboxPairBean.second).clear();
        AlbumPlayFlow flow = getAlbumPlayActivity().getFlow();
        if (flow != null && flow.mBoxBean != null) {
            List<LeboxVideoBean> leboxVideoBeanList = LeboxApiManager.getInstance().getVideoByPid(flow.mBoxBean.pid, 1);
            if (!BaseTypeUtils.isListEmpty(leboxVideoBeanList)) {
                ((ArrayList) this.leboxPairBean.first).addAll(leboxVideoBeanList);
                for (LeboxVideoBean leboxVideoBean : leboxVideoBeanList) {
                    ((ArrayList) this.leboxPairBean.second).add(convertLeboxBeanToVideoBean(leboxVideoBean));
                }
            }
        }
    }

    private VideoBean convertLeboxBeanToVideoBean(LeboxVideoBean leboxVideoBean) {
        if (leboxVideoBean == null) {
            return null;
        }
        VideoBean videoBean = new VideoBean();
        videoBean.cid = BaseTypeUtils.stoi(leboxVideoBean.cid);
        videoBean.pid = (long) BaseTypeUtils.stoi(leboxVideoBean.pid);
        videoBean.vid = (long) BaseTypeUtils.stoi(leboxVideoBean.vid);
        videoBean.episode = leboxVideoBean.episode;
        videoBean.nameCn = leboxVideoBean.videoName;
        videoBean.subTitle = leboxVideoBean.videoName;
        videoBean.pidname = leboxVideoBean.albumName;
        videoBean.duration = BaseTypeUtils.stol(leboxVideoBean.duration);
        videoBean.videoTypeKey = LetvConstant.VIDEO_TYPE_KEY_ZHENG_PIAN;
        return videoBean;
    }

    public AlbumHalfCommentController getCommentController() {
        return this.mCommentController;
    }

    private void setCardRecommendId(AlbumCardList result) {
        this.mReid = null;
        if (!BaseTypeUtils.isListEmpty(result.relateBean.relateAlbumList)) {
            for (AlbumInfo info : result.relateBean.relateAlbumList) {
                if (!TextUtils.isEmpty(info.relationId)) {
                    this.mReid = info.relationId;
                    break;
                }
            }
        }
        if (TextUtils.isEmpty(this.mReid)) {
            List<VideoBean> list = null;
            if (!BaseTypeUtils.isListEmpty(result.relateBean.recList)) {
                list = result.relateBean.recList;
            } else if (!BaseTypeUtils.isListEmpty(result.yourLikeList)) {
                list = result.yourLikeList;
            }
            if (list != null) {
                for (VideoBean videoBean : list) {
                    if (!TextUtils.isEmpty(videoBean.reid)) {
                        this.mReid = videoBean.reid;
                        return;
                    }
                }
            }
        }
    }
}
