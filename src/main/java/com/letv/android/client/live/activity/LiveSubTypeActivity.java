package com.letv.android.client.live.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.SearchMainActivity;
import com.letv.android.client.adapter.LiveLunboAdapter;
import com.letv.android.client.adapter.LiveLunboAdapter.LiveProgram;
import com.letv.android.client.adapter.LiveRoomAdapter;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.MyDownloadActivityConfig;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.live.controller.LivePlayerController.FullScreenBtnClickEvent;
import com.letv.android.client.live.controller.LivePlayerController.PlayBtnClickEvent;
import com.letv.android.client.live.controller.LivePlayerController.SysConfigChangeEvent;
import com.letv.android.client.live.event.LiveEvent.LiveSubTypeFlowRequestErrorEvent;
import com.letv.android.client.live.event.LiveEvent.OnActivityResultEvent;
import com.letv.android.client.live.flow.LiveSubTypeFlow;
import com.letv.android.client.live.flow.LiveSubTypeFlow.LiveLunboListBeanEvent;
import com.letv.android.client.live.flow.LiveSubTypeFlow.LiveLunboProgramListBeanEvent;
import com.letv.android.client.live.flow.LiveSubTypeFlow.LiveRemenListBeanEvent;
import com.letv.android.client.live.utils.LiveUtils;
import com.letv.android.client.live.view.LivePlayerView;
import com.letv.android.client.live.view.LiveVideoView.StateChangeEvent;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.android.client.view.WatchAndBuyView;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveBeanLeChannelProgramList.LiveLunboProgramListBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.bean.ProgramEntity;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.LetvConstant.SortType;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.db.DBManager;
import com.letv.core.db.LetvContentProvider;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class LiveSubTypeActivity extends LetvBaseActivity implements OnClickListener {
    public static final int ACTION_TYPE_ALL_CHANNELS = 1;
    public static final int ACTION_TYPE_FAVORITE = 2;
    public static final int ACTION_TYPE_HISTORY = 0;
    protected static final String INTENT_KEY_PAGEINDEX = "pageIndex";
    private static final String TAG = "LiveSubTypeActivity";
    private static final String TAG_LIST = "LIST";
    LoaderCallbacks<Cursor> mBookLoaderCallback;
    private LiveBeanLeChannel mChannel;
    protected int mCurrentActionType;
    private ArrayList<LiveRemenBaseBean> mData;
    protected LiveSubTypeFlow mFlow;
    private ImageView mFooterView;
    private boolean mFullScreen;
    private ImageView mFullScreenBtn;
    private RelativeLayout mHeaderView;
    private RelativeLayout mLayoutContainer;
    private RelativeLayout mLayoutFloatbar;
    protected ListView mListView;
    protected LiveLunboAdapter mLunboAdapter;
    protected ArrayList<LiveBeanLeChannel> mLunboData;
    private ImageView mPlayBtn;
    private LivePlayerView mPlayerView;
    protected HashMap<String, LiveProgram> mPrograms;
    private PullToRefreshListView mPullToRefreshListView;
    private RefreshData mRefreshData;
    private LiveRoomAdapter mRoomAdapter;
    private PublicLoadLayout mRootView;
    private RxBus mRxBus;
    private CompositeSubscription mSubscription;
    private ImageView mTopBack;
    private ImageView mTopDownload;
    private RelativeLayout mTopLayout;
    private ImageView mTopSearch;
    private TextView mTopTv;
    protected int pageIndex;

    public LiveSubTypeActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mData = new ArrayList();
        this.mLunboData = new ArrayList();
        this.mPrograms = new HashMap();
        this.mCurrentActionType = 1;
        this.mBookLoaderCallback = new LoaderCallbacks<Cursor>(this) {
            final /* synthetic */ LiveSubTypeActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(this.this$0, LetvContentProvider.URI_LIVEBOOKTRACE, null, null, null, null);
            }

            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                if (cursor != null) {
                    Set<String> mBookedPrograms = new HashSet();
                    while (cursor.moveToNext()) {
                        try {
                            int idx = cursor.getColumnIndexOrThrow(Field.MD5_ID);
                            if (idx != -1) {
                                mBookedPrograms.add(cursor.getString(idx));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if (this.this$0.mRoomAdapter != null) {
                        this.this$0.mRoomAdapter.setBookedPrograms(mBookedPrograms);
                    }
                }
            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        };
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ LiveSubTypeActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void refreshData() {
                this.this$0.mRootView.loading(false);
                this.this$0.getData();
            }
        };
    }

    public static void launch(Context context, int pageIndex) {
        Intent mIntent = new Intent(context, LiveSubTypeActivity.class);
        mIntent.putExtra(INTENT_KEY_PAGEINDEX, pageIndex);
        context.startActivity(mIntent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mRootView = PublicLoadLayout.createPage((Context) this, (int) R.layout.activity_live_subtype);
        this.mRootView.setRefreshData(this.mRefreshData);
        this.mRootView.loading(false);
        setContentView(this.mRootView);
        this.mRxBus = RxBus.getInstance();
        initView();
        getSupportLoaderManager().initLoader(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID, null, this.mBookLoaderCallback);
        registerHomeKeyEventReceiver();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mPlayerView != null) {
            this.mPlayerView.setActivityResultEvent(new OnActivityResultEvent(requestCode, resultCode, data));
        }
    }

    private void initView() {
        this.mTopLayout = (RelativeLayout) findViewById(R.id.live_subtype_top_layout);
        this.mTopTv = (TextView) findViewById(R.id.live_subtype_top_tv_title);
        this.mTopBack = (ImageView) findViewById(R.id.live_subtype_top_iv_back);
        this.mTopDownload = (ImageView) findViewById(R.id.live_subtype_top_iv_download);
        this.mTopSearch = (ImageView) findViewById(R.id.live_subtype_top_iv_search);
        if (LetvUtils.isInHongKong()) {
            this.mTopDownload.setVisibility(8);
        } else {
            this.mTopDownload.setVisibility(0);
        }
        this.mLayoutContainer = (RelativeLayout) findViewById(R.id.live_subtype_container);
        this.mLayoutFloatbar = (RelativeLayout) findViewById(R.id.live_subtype_floatbar);
        this.mPlayBtn = (ImageView) findViewById(R.id.live_subtype_floatbar_pause_btn);
        this.mFullScreenBtn = (ImageView) findViewById(R.id.live_subtype_floatbar_fullscreen_btn);
        this.mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.live_subtype_pulllistview);
        this.mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener(this) {
            final /* synthetic */ LiveSubTypeActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onRefresh() {
                if (this.this$0.mPlayerView != null) {
                    this.this$0.mPlayerView.startPlay();
                }
                this.this$0.getData();
            }
        });
        this.mListView = (ListView) this.mPullToRefreshListView.getRefreshableView();
        this.mListView.setSelector(new ColorDrawable(0));
        this.mListView.setScrollingCacheEnabled(false);
        this.pageIndex = getIntent() == null ? 0 : getIntent().getIntExtra(INTENT_KEY_PAGEINDEX, 0);
        this.mTopTv.setText(LiveUtils.getLiveChannelName(this, this.pageIndex));
        getData();
        if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
            this.mLunboAdapter = new LiveLunboAdapter(this);
            this.mListView.setAdapter(this.mLunboAdapter);
        } else {
            this.mRoomAdapter = new LiveRoomAdapter(this);
            this.mListView.setAdapter(this.mRoomAdapter);
        }
        addHeaderView();
        addFooterView();
        this.mTopTv.setOnClickListener(this);
        this.mTopBack.setOnClickListener(this);
        this.mTopDownload.setOnClickListener(this);
        this.mTopSearch.setOnClickListener(this);
        this.mFullScreenBtn.setOnClickListener(this);
        this.mPlayBtn.setOnClickListener(this);
        this.mLayoutFloatbar.setOnClickListener(this);
        this.mListView.setOnScrollListener(new OnScrollListener(this) {
            final /* synthetic */ LiveSubTypeActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.this$0.mHeaderView == null || firstVisibleItem <= 1) {
                    this.this$0.mLayoutFloatbar.setVisibility(8);
                } else {
                    this.this$0.mLayoutFloatbar.setVisibility(0);
                }
            }
        });
    }

    private void addHeaderView() {
        if (this.mListView != null && this.mListView.getHeaderViewsCount() == 1) {
            this.mHeaderView = new RelativeLayout(this);
            this.mHeaderView.setLayoutParams(new LayoutParams(-1, -2));
            this.mPlayerView = new LivePlayerView(this);
            this.mPlayerView.init(this.pageIndex, getSupportFragmentManager());
            this.mHeaderView.addView(this.mPlayerView, new RelativeLayout.LayoutParams(-1, -1));
            UIsUtils.zoomView(320, 180, this.mHeaderView);
            this.mListView.addHeaderView(this.mHeaderView);
        }
    }

    private void addFooterView() {
        if (this.mFooterView == null) {
            this.mFooterView = new ImageView(getActivity());
            LayoutParams l = new LayoutParams(-1, Math.min(UIsUtils.getScreenHeight(), UIsUtils.getScreenWidth()) / 6);
            this.mFooterView.setImageResource(2130838157);
            this.mFooterView.setLayoutParams(l);
            this.mFooterView.setBackgroundColor(getActivity().getResources().getColor(2131492949));
            this.mFooterView.setPadding(0, UIsUtils.zoomWidth(5), 0, UIsUtils.zoomWidth(10));
            this.mFooterView.setScaleType(ScaleType.CENTER);
        }
        if (this.mListView.getFooterViewsCount() == 0) {
            this.mListView.addFooterView(this.mFooterView);
        }
    }

    private void getData() {
        if (this.mFlow == null) {
            this.mFlow = new LiveSubTypeFlow("LiveSubTypeActivityLIST" + this.pageIndex);
        }
        this.mFlow.start(this.pageIndex);
    }

    protected void onStart() {
        super.onStart();
        if (mHomeKeyEventReceiver != null && mHomeKeyEventReceiver.isHomeClicked() && this.mPlayerView != null) {
            this.mPlayerView.startFromBackground();
        }
    }

    protected void onResume() {
        super.onResume();
        registerRxBus();
        if (LiveLunboUtils.isLunBoWeiShiType(this.pageIndex)) {
            this.mChannel = DBManager.getInstance().getChannelHisListTrace().getLastHisChannel(LiveLunboUtils.getChannelDBType(this.pageIndex));
            if (this.mChannel != null && this.mPlayerView != null) {
                this.mPlayerView.setLunboChannel(this.mChannel);
                this.mPlayerView.onResume();
            }
        } else if (this.mPlayerView != null) {
            this.mPlayerView.onResume();
        }
    }

    protected void onPause() {
        super.onPause();
        unRegisterRxBus();
        if (this.mPlayerView != null) {
            this.mPlayerView.onPause();
        }
    }

    public void finish() {
        super.finish();
        if (this.mPlayerView != null) {
            this.mPlayerView.destroy();
        }
        if (this.mFlow != null) {
            this.mFlow.destroy();
        }
        WatchAndBuyView.sAddToCartGoods = null;
        getSupportLoaderManager().destroyLoader(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID);
        unRegisterHomeKeyEventReceiver();
    }

    private void registerRxBus() {
        LogInfo.log(RxBus.TAG, "LiveSubTypeActivity注册RxBus");
        if (this.mSubscription == null) {
            this.mSubscription = new CompositeSubscription();
        }
        if (!this.mSubscription.hasSubscriptions()) {
            LogInfo.log(RxBus.TAG, "LiveSubTypeActivity添加RxBus Event");
            this.mSubscription.add(this.mRxBus.toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>(this) {
                final /* synthetic */ LiveSubTypeActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void call(Object event) {
                    LogInfo.log(RxBus.TAG, "LiveSubTypeActivity接收到" + event.getClass().getName());
                    if (event instanceof LiveRemenListBeanEvent) {
                        LiveRemenListBeanEvent result = (LiveRemenListBeanEvent) event;
                        this.this$0.mRootView.finish();
                        this.this$0.mPullToRefreshListView.onRefreshComplete();
                        if (result.bean != null) {
                            this.this$0.mData.clear();
                            this.this$0.mRoomAdapter.clear();
                            this.this$0.mData.addAll(result.bean.mRemenList);
                            this.this$0.mRoomAdapter.addList(this.this$0.mData);
                        }
                    } else if (event instanceof LiveLunboListBeanEvent) {
                        LiveLunboListBeanEvent result2 = (LiveLunboListBeanEvent) event;
                        this.this$0.mRootView.finish();
                        this.this$0.mPullToRefreshListView.onRefreshComplete();
                        if (result2 != null && result2.list != null && this.this$0.mCurrentActionType == 1) {
                            LetvUtils.sortChannelList(result2.list.mLiveBeanLeChannelList, SortType.SORT_BYNO);
                            this.this$0.mLunboData.clear();
                            this.this$0.mLunboAdapter.clear();
                            this.this$0.mLunboData.addAll(result2.list.mLiveBeanLeChannelList);
                            this.this$0.mLunboAdapter.addList(result2.list.mLiveBeanLeChannelList);
                            if (this.this$0.mChannel == null && result2.list.mLiveBeanLeChannelList.size() > 0) {
                                this.this$0.mChannel = (LiveBeanLeChannel) result2.list.mLiveBeanLeChannelList.get(0);
                                if (this.this$0.mChannel != null && this.this$0.mPlayerView != null) {
                                    this.this$0.mPlayerView.setLunboChannel(this.this$0.mChannel);
                                    this.this$0.mPlayerView.onResume();
                                }
                            }
                        }
                    } else if (event instanceof LiveLunboProgramListBeanEvent) {
                        LiveLunboProgramListBeanEvent result3 = (LiveLunboProgramListBeanEvent) event;
                        if (result3 != null && !BaseTypeUtils.isListEmpty(result3.list) && this.this$0.mLunboAdapter != null && !BaseTypeUtils.isListEmpty(result3.list)) {
                            for (LetvBaseBean bean : result3.list) {
                                LiveLunboProgramListBean channelBean = (LiveLunboProgramListBean) bean;
                                LiveProgram program = new LiveProgram();
                                program.mName = ((ProgramEntity) channelBean.programs.get(0)).title;
                                program.mIconUrl = ((ProgramEntity) channelBean.programs.get(0)).viewPic;
                                program.mNextName = ((ProgramEntity) channelBean.programs.get(1)).title;
                                program.mNextTime = ((ProgramEntity) channelBean.programs.get(1)).playTime;
                                this.this$0.mPrograms.put(channelBean.channelId, program);
                            }
                            this.this$0.mLunboAdapter.setLivePrograms(this.this$0.mPrograms);
                            this.this$0.mLunboAdapter.notifyDataSetChanged();
                        }
                    } else if (event instanceof FullScreenBtnClickEvent) {
                        this.this$0.handleFullScreenEvent((FullScreenBtnClickEvent) event);
                    } else if (event instanceof StateChangeEvent) {
                        switch (((StateChangeEvent) event).state) {
                            case -1:
                                this.this$0.mPlayBtn.setImageResource(2130837745);
                                return;
                            case 3:
                                this.this$0.mPlayBtn.setImageResource(2130837739);
                                return;
                            case 4:
                                this.this$0.mPlayBtn.setImageResource(2130837745);
                                return;
                            default:
                                return;
                        }
                    } else if (event instanceof LiveSubTypeFlowRequestErrorEvent) {
                        LiveSubTypeFlowRequestErrorEvent errorEvent = (LiveSubTypeFlowRequestErrorEvent) event;
                        this.this$0.mPullToRefreshListView.onRefreshComplete();
                        if (this.this$0.mData.size() != 0 || this.this$0.mLunboData.size() != 0) {
                            ToastUtils.showToast(this.this$0.mContext, 2131100495);
                        } else if (errorEvent.netError) {
                            this.this$0.mRootView.netError(false);
                            this.this$0.mRootView.setErrorBackgroundColor(this.this$0.getResources().getColor(2131492949));
                        } else {
                            this.this$0.mRootView.finish();
                        }
                    }
                }
            }, new Action1<Throwable>(this) {
                final /* synthetic */ LiveSubTypeActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void call(Throwable throwable) {
                }
            }));
        }
    }

    protected void handleFullScreenEvent(FullScreenBtnClickEvent fullScreenEvent) {
        this.mFullScreen = fullScreenEvent.isFull;
        setRedPacketEntryLocation(fullScreenEvent.isFull);
        if (fullScreenEvent.isFull) {
            if (this.mPlayerView != null) {
                getActivity().setRequestedOrientation(0);
                this.mLayoutContainer.setVisibility(0);
                ((ViewGroup) this.mPlayerView.getParent()).removeView(this.mPlayerView);
                this.mLayoutContainer.addView(this.mPlayerView, new ViewGroup.LayoutParams(-1, -1));
                UIsUtils.zoomViewFull(this.mPlayerView);
                UIsUtils.fullScreen(getActivity());
            }
            this.mFullScreenBtn.setImageResource(2130838715);
            return;
        }
        if (this.mPlayerView != null) {
            getActivity().setRequestedOrientation(1);
            this.mLayoutContainer.removeAllViews();
            this.mLayoutContainer.setVisibility(8);
            if (this.mHeaderView != null) {
                this.mHeaderView.addView(this.mPlayerView);
            }
            UIsUtils.zoomView(320, 180, this.mPlayerView);
            UIsUtils.cancelFullScreen(getActivity());
        }
        this.mFullScreenBtn.setImageResource(2130838126);
    }

    private void unRegisterRxBus() {
        LogInfo.log(RxBus.TAG, "LiveSubTypeActivity取消注册RxBus");
        if (this.mSubscription != null && this.mSubscription.hasSubscriptions()) {
            this.mSubscription.unsubscribe();
        }
        this.mSubscription = null;
    }

    public String[] getAllFragmentTags() {
        return new String[0];
    }

    public String getActivityName() {
        return getClass().getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void onClick(View v) {
        int i = 2;
        boolean z = true;
        if (v == this.mTopBack) {
            finish();
        } else if (v == this.mTopDownload) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new MyDownloadActivityConfig(this.mContext).create(0)));
        } else if (v == this.mTopSearch) {
            String[] searchWords = PreferencesManager.getInstance().getSearchWordsInfo();
            Context context = this.mContext;
            String str = "ref＝0101_channel";
            if (!TextUtils.equals("2", searchWords[2])) {
                i = 1;
            }
            SearchMainActivity.launch(context, str, i);
            StatisticsUtils.statisticsActionInfo(this.mContext, StatisticsUtils.getLivePageId(this.pageIndex), "0", "a2", "搜索", -1, "sname=" + this.mContext.getString(2131100238));
        } else if (v == this.mPlayBtn) {
            this.mRxBus.send(new PlayBtnClickEvent());
        } else if (v == this.mFullScreenBtn) {
            RxBus rxBus = this.mRxBus;
            if (this.mFullScreen) {
                z = false;
            }
            rxBus.send(new FullScreenBtnClickEvent(z));
        } else if (v == this.mLayoutFloatbar && this.mListView != null) {
            this.mListView.setSelection(0);
        }
    }

    public void onBackPressed() {
        if (this.mFullScreen) {
            this.mRxBus.send(new FullScreenBtnClickEvent(false));
        } else {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        RxBus.getInstance().send(new SysConfigChangeEvent());
    }
}
