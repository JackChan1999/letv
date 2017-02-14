package com.letv.android.client.live.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.view.PlayLoadLayout;
import com.letv.android.client.live.controller.LivePlayerController;
import com.letv.android.client.live.controller.LivePlayerController.PlayLevelChangeEvent;
import com.letv.android.client.live.event.LiveEvent.OnActivityResultEvent;
import com.letv.android.client.live.flow.LivePlayerFlow;
import com.letv.business.flow.live.LiveWatchAndBuyFlow;
import com.letv.business.flow.statistics.LivePlayStatisticsHelper;
import com.letv.business.flow.statistics.StatisticsInfo;
import com.letv.business.flow.unicom.UnicomWoFlowManager;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.LiveStreamBean.StreamType;
import com.letv.core.pagecard.GenerateViewId;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class LivePlayerView extends RelativeLayout {
    private static final int START_TYPE_CHANNELINFO = 3;
    private static final int START_TYPE_LIVEINFO = 2;
    private static final int START_TYPE_PAGEINDEX = 1;
    private OnActivityResultEvent mActivityResultEvent;
    private int mBarrageContainId;
    private LiveRemenBaseBean mBaseBean;
    private BroadcastReceiver mBroadcastReceiver;
    private LiveBeanLeChannel mChannel;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private LiveGestureLayout mGestureLayout;
    private boolean mIsStartAfterHome;
    private int mOldNetType;
    private PlayLoadLayout mPlayLoadLayout;
    private LivePlayerController mPlayerController;
    private LivePlayerFlow mPlayerFlow;
    private RxBus mRxBus;
    private int mStartType;
    public LivePlayStatisticsHelper mStatisticsHelper;
    private CompositeSubscription mSubscription;
    private boolean mUse3G;
    private LiveVideoView mVideoView;
    private LiveWatchAndBuyFlow mWatchAndBuyFlow;
    private boolean mWoOrder;
    private int pageIndex;

    public LivePlayerView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mOldNetType = -1;
        this.mIsStartAfterHome = false;
        this.mBroadcastReceiver = new 4(this);
        this.mContext = context;
    }

    public LivePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mOldNetType = -1;
        this.mIsStartAfterHome = false;
        this.mBroadcastReceiver = new 4(this);
        this.mContext = context;
    }

    public LivePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mOldNetType = -1;
        this.mIsStartAfterHome = false;
        this.mBroadcastReceiver = new 4(this);
        this.mContext = context;
    }

    public void init(int pageIndex, FragmentManager fragmentManager) {
        this.mRxBus = RxBus.getInstance();
        setBackgroundColor(-16777216);
        this.pageIndex = pageIndex;
        this.mFragmentManager = fragmentManager;
        if (pageIndex != -1) {
            this.mStartType = 1;
        }
        if (this.mStatisticsHelper == null) {
            this.mStatisticsHelper = new LivePlayStatisticsHelper();
            this.mStatisticsHelper.mStatisticsInfo.mPageIndex = pageIndex;
            this.mStatisticsHelper.mStatisticsInfo.mIsInLiveHall = true;
            this.mStatisticsHelper.mStatisticsInfo.mPageId = StatisticsUtils.getLivePageId(pageIndex);
        }
        if (pageIndex == -1) {
            StatisticsUtils.setActionProperty("c21", -1, PageIdConstant.onLiveremenCtegoryPage);
        }
        this.mPlayerFlow = new LivePlayerFlow(this.mContext);
        this.mPlayerFlow.setStatisticsInfo(this.mStatisticsHelper);
        initVideoView();
        initBarrageLayout();
        initGestureLayout();
        initPlayLoadLayout();
        initControllerLayout();
    }

    public void onResume() {
        this.mOldNetType = NetworkUtils.getNetworkType();
        registerRxBus();
        registerNetChangeReceiver();
        if (this.mVideoView != null) {
            this.mVideoView.onResume();
        }
        if (this.mPlayerController != null) {
            this.mPlayerController.onResume();
        }
        this.mPlayerFlow.setRequestTag(String.valueOf(this.pageIndex));
        LogInfo.log("jc666", "liveplayerview start");
        startPlay();
        if (this.mActivityResultEvent != null) {
            this.mRxBus.send(new OnActivityResultEvent(this.mActivityResultEvent.requestCode, this.mActivityResultEvent.resultCode, this.mActivityResultEvent.intent));
            this.mActivityResultEvent = null;
        }
    }

    public void onPause() {
        unRegisterRxBus();
        unRegisterNetChangeReceive();
        if (this.mVideoView != null) {
            this.mVideoView.onPause();
        }
        if (this.mPlayerController != null) {
            this.mPlayerController.onPause();
        }
        if (this.mPlayerFlow != null) {
            this.mPlayerFlow.destroy();
        }
    }

    public void destroy() {
        LogInfo.log("pjf", "LivePlayerView destroy");
        if (this.mVideoView != null) {
            removeView(this.mVideoView);
        }
        if (this.mPlayerController != null) {
            this.mPlayerController.onDestroy();
        }
        this.mFragmentManager = null;
        this.mBaseBean = null;
        this.mChannel = null;
        this.pageIndex = 0;
        if (this.mStatisticsHelper != null) {
            this.mStatisticsHelper.stopTimeStatistics();
            this.mStatisticsHelper.clear();
        }
    }

    public void setLiveRemenBaseBean(LiveRemenBaseBean baseBean) {
        this.mBaseBean = baseBean;
        this.mStartType = 2;
        if (this.mStatisticsHelper != null) {
            this.mStatisticsHelper.mStatisticsInfo.mLiveRenmenBase = this.mBaseBean;
        }
    }

    public void setLunboChannel(LiveBeanLeChannel channel) {
        this.mChannel = channel;
        this.mStartType = 3;
    }

    public void setActivityResultEvent(OnActivityResultEvent event) {
        this.mActivityResultEvent = event;
    }

    public void canShowTitle(boolean show) {
        if (this.mPlayerController != null) {
            this.mPlayerController.canShowTitle(show);
        }
    }

    public void startPlay() {
        LogInfo.log("jc666", "liveplayerview startPlay");
        if (isNetAvailable()) {
            statisticsLaunch();
            if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
                if (this.mChannel != null) {
                    this.mPlayerFlow.requestLunboData(this.mChannel, null);
                }
            } else if (this.mStartType == 1) {
                this.mPlayerFlow.start(this.pageIndex);
            } else if (this.mStartType == 2 && this.mBaseBean != null) {
                this.mPlayerFlow.requestLiveData(this.mBaseBean);
            } else if (this.mStartType == 3 && this.mChannel != null) {
                this.mPlayerFlow.requestLunboData(this.mChannel, null);
            }
        } else if (NetworkUtils.getNetworkType() == 0) {
            if (this.mPlayerController != null) {
                this.mPlayerController.setVisibility(8);
            }
            if (this.mPlayLoadLayout != null) {
                this.mPlayLoadLayout.requestError();
            }
        } else {
            checkWoOrder();
        }
    }

    private boolean isNetAvailable() {
        switch (NetworkUtils.getNetworkType()) {
            case 0:
                return false;
            case 1:
                return true;
            case 2:
            case 3:
                if (this.mWoOrder && !isPlayingMiGu()) {
                    return true;
                }
                if (this.mUse3G) {
                    return true;
                }
                return false;
            default:
                return true;
        }
    }

    private boolean isPlayingMiGu() {
        if (this.mChannel == null || !this.mChannel.signal.equals("9")) {
            return false;
        }
        return true;
    }

    private void checkWoOrder() {
        UnicomWoFlowManager.getInstance().checkUnicomWoFreeFlow(this.mContext, new 1(this));
    }

    private void show3GLayout() {
        if (isPlayingMiGu()) {
            this.mPlayLoadLayout.requestNotWifi(true);
        } else {
            this.mPlayLoadLayout.requestNotWifi(false);
        }
    }

    private void initVideoView() {
        if (this.mVideoView == null) {
            this.mVideoView = new LiveVideoView(this.mContext);
            this.mVideoView.setStatisticsHelper(this.mStatisticsHelper);
            LayoutParams params = new LayoutParams(-1, -1);
            params.addRule(13);
            addView(this.mVideoView, params);
        }
    }

    private void initBarrageLayout() {
        this.mBarrageContainId = GenerateViewId.generateViewId();
        LayoutParams params = new LayoutParams(-1, -1);
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.live_barrage_contain, null);
        view.setId(this.mBarrageContainId);
        addView(view, params);
    }

    private void initControllerLayout() {
        if (this.mPlayerController != null) {
            removeView(this.mPlayerController);
        }
        this.mPlayerController = new LivePlayerController(this.mContext);
        addView(this.mPlayerController, new LayoutParams(-1, -1));
        this.mPlayerController.init(this.pageIndex, this.mFragmentManager, this.mBarrageContainId);
        this.mPlayerController.setVisibility(8);
    }

    private void initGestureLayout() {
        if (this.mGestureLayout == null) {
            this.mGestureLayout = new LiveGestureLayout(this.mContext);
            addView(this.mGestureLayout, new LayoutParams(-1, -1));
        }
    }

    private void initPlayLoadLayout() {
        if (this.mPlayLoadLayout == null) {
            this.mPlayLoadLayout = new PlayLoadLayout(this.mContext);
            addView(this.mPlayLoadLayout, new LayoutParams(-1, -1));
            this.mPlayLoadLayout.setCallBack(new 2(this));
            return;
        }
        this.mPlayLoadLayout.finish();
    }

    private void registerRxBus() {
        LogInfo.log(RxBus.TAG, "LivePlayerView注册RxBus");
        if (this.mSubscription == null) {
            this.mSubscription = new CompositeSubscription();
        }
        if (!this.mSubscription.hasSubscriptions()) {
            LogInfo.log(RxBus.TAG, "LivePlayerView添加RxBus Event");
            this.mSubscription.add(this.mRxBus.toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new 3(this)));
        }
    }

    private void handlerPlayLevelChangeEvent(PlayLevelChangeEvent changeEvent) {
        int level = changeEvent.level;
        if (this.mPlayLoadLayout != null) {
            this.mPlayLoadLayout.loading();
        }
        if (this.mVideoView != null) {
            this.mVideoView.pause();
            if (this.mStatisticsHelper != null) {
                this.mStatisticsHelper.stopTimeStatistics();
            }
        }
        if (this.mPlayerFlow != null) {
            switch (level) {
                case 1:
                    this.mPlayerFlow.setUserStreamType(StreamType.STREAM_350);
                    break;
                case 2:
                    this.mPlayerFlow.setUserStreamType(StreamType.STREAM_1000);
                    break;
                case 3:
                    this.mPlayerFlow.setUserStreamType(StreamType.STREAM_1300);
                    break;
                case 4:
                    this.mPlayerFlow.setUserStreamType(StreamType.STREAM_720p);
                    break;
            }
            if (this.mPlayerFlow.getLiveStreamBean() != null) {
                statisticsPlayHd(this.mPlayerFlow.getLiveStreamBean().getCode());
            }
            this.mPlayerFlow.setUserControllLevel(true);
            if (this.pageIndex != 2 || this.mChannel == null) {
                this.mPlayerFlow.playUrl(this.mPlayerFlow.getLiveStreamBean());
            } else if (this.mChannel.signal.equals("9")) {
                this.mPlayerFlow.requestLunboData(this.mChannel, null);
            } else {
                this.mPlayerFlow.playUrl(this.mPlayerFlow.getLiveStreamBean());
            }
        }
    }

    private void unRegisterRxBus() {
        LogInfo.log(RxBus.TAG, "LivePlayerView取消注册RxBus");
        if (this.mSubscription != null && this.mSubscription.hasSubscriptions()) {
            this.mSubscription.unsubscribe();
        }
        this.mSubscription = null;
    }

    private void registerNetChangeReceiver() {
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.mContext.registerReceiver(this.mBroadcastReceiver, filter);
        } catch (Exception e) {
        }
    }

    private void unRegisterNetChangeReceive() {
        try {
            this.mContext.unregisterReceiver(this.mBroadcastReceiver);
        } catch (Exception e) {
        }
    }

    public void statisticsLaunch() {
        if (this.mStatisticsHelper != null) {
            if (this.mIsStartAfterHome) {
                StatisticsInfo statisticsInfo = this.mStatisticsHelper.mStatisticsInfo;
                statisticsInfo.mInterruptNum++;
                this.mStatisticsHelper.mStatisticsInfo.mReplayType = 3;
                this.mStatisticsHelper.mStatisticsInfo.resetTimeInfos();
                this.mIsStartAfterHome = false;
            } else {
                this.mStatisticsHelper.resetUuid();
            }
            this.mStatisticsHelper.statisticsPlayActions("launch");
        }
    }

    public void startFromBackground() {
        this.mIsStartAfterHome = true;
    }

    private void statisticsPlayHd(String vt) {
        if (this.mStatisticsHelper != null) {
            this.mStatisticsHelper.statisticsPlayActions("tg");
            StatisticsInfo statisticsInfo = this.mStatisticsHelper.mStatisticsInfo;
            statisticsInfo.mInterruptNum++;
            this.mStatisticsHelper.mStatisticsInfo.mReplayType = 2;
            this.mStatisticsHelper.mStatisticsInfo.mVt = vt;
            this.mStatisticsHelper.statisticsPlayActions("launch");
            this.mStatisticsHelper.statisticsPlayActions("init");
        }
    }
}
