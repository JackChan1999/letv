package com.letv.android.client.live.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.commonlib.view.PublicLoadLayout.RefreshData;
import com.letv.android.client.live.activity.LiveSubTypeActivity;
import com.letv.android.client.live.activity.LiveSubTypeLunboActivity;
import com.letv.android.client.live.adapter.LiveAdapter;
import com.letv.android.client.live.adapter.LiveRemenChannelAdapter;
import com.letv.android.client.live.adapter.LiveRemenHkChannelAdapter;
import com.letv.android.client.live.adapter.LiveRemenLivePagerAdapter;
import com.letv.android.client.live.adapter.LiveRemenLivePagerAdapter.InstantiateItemEvent;
import com.letv.android.client.live.adapter.LiveRemenLivePagerAdapter.ViewHolder;
import com.letv.android.client.live.bean.LivePageBean;
import com.letv.android.client.live.controller.LivePlayerController.FullScreenBtnClickEvent;
import com.letv.android.client.live.event.LiveEvent.LivePageFlowRequestErrorEvent;
import com.letv.android.client.live.event.LiveEvent.LiveWatchNumEvent;
import com.letv.android.client.live.event.LiveEvent.OnActivityResultEvent;
import com.letv.android.client.live.flow.LivePageFlow;
import com.letv.android.client.live.flow.LivePageFlow.ProgramListResultEvent;
import com.letv.android.client.live.flow.LivePageFlow.RequestPageDataResultEvent;
import com.letv.android.client.live.utils.LiveUtils;
import com.letv.android.client.live.view.LivePlayerView;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.android.client.view.WatchAndBuyView;
import com.letv.core.BaseApplication;
import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.LiveBeanLeChannel;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.db.LetvContentProvider;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LiveLunboUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.RxBus;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class LiveFragment extends LetvBaseFragment {
    private static final String TAG = "LiveFragment";
    private final int SCROLL_LEFT;
    private final int SCROLL_RIGTH;
    private int curPagePosition;
    private int direction;
    private boolean isFull;
    private int lastPagePosition;
    private Subscription mAddViewSubscription;
    private LetvBaseBean mBackgroundBean;
    LoaderCallbacks<Cursor> mBookLoaderCallback;
    private Adapter mChannelAdapter;
    private LetvBaseBean mCurrentBean;
    private LivePageFlow mFlow;
    private int mGotoChildPageIndex;
    private RelativeLayout mHeaderView;
    private ImageView mIvDouble;
    private ImageView mIvSingle;
    private RelativeLayout mLayoutContainer;
    private LayoutManager mLayoutManager;
    protected ListView mListView;
    private LiveAdapter mLiveAdapter;
    private ArrayList<LetvBaseBean> mLiveList;
    private LivePageBean mPageBean;
    private LiveRemenLivePagerAdapter mPagerAdapter;
    private LivePlayerView mPlayerView;
    private PullToRefreshListView mPullToRefreshListView;
    private RecyclerView mRecyclerview;
    private RefreshData mRefreshData;
    private Subscription mRefreshSubscription;
    private PublicLoadLayout mRootView;
    private RxBus mRxBus;
    private CompositeSubscription mSubscription;
    private ViewHolder mViewHolder;
    private int mViewPagerHeight;
    private ViewPager mViewpager;

    public static class COUNTRY_CHANGE_EVENT {
        public COUNTRY_CHANGE_EVENT() {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
        }
    }

    public LiveFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.SCROLL_LEFT = 1;
        this.SCROLL_RIGTH = -1;
        this.mLiveList = new ArrayList();
        this.mGotoChildPageIndex = -1;
        this.mRefreshData = new RefreshData(this) {
            final /* synthetic */ LiveFragment this$0;

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
        this.mBookLoaderCallback = new LoaderCallbacks<Cursor>(this) {
            final /* synthetic */ LiveFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return new CursorLoader(this.this$0.getContext(), LetvContentProvider.URI_LIVEBOOKTRACE, null, null, null, null);
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
                        } catch (SQLiteException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    if (this.this$0.mLiveAdapter != null) {
                        this.this$0.mLiveAdapter.setBookedPrograms(mBookedPrograms);
                    }
                }
            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        };
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRootView = PublicLoadLayout.createPage(getContext(), (int) R.layout.fragment_live);
        this.mRootView.setRefreshData(this.mRefreshData);
        this.mRootView.loading(false);
        return this.mRootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPullToRefreshListView = (PullToRefreshListView) this.mRootView.findViewById(R.id.live_pulllistview);
        this.mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener(this) {
            final /* synthetic */ LiveFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onRefresh() {
                this.this$0.getData();
            }
        });
        this.mLayoutContainer = (RelativeLayout) this.mRootView.findViewById(R.id.live_container);
        this.mRxBus = RxBus.getInstance();
        initListView();
        initHeaderView();
        getLoaderManager().initLoader(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID, null, this.mBookLoaderCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mPlayerView != null) {
            this.mPlayerView.setActivityResultEvent(new OnActivityResultEvent(requestCode, resultCode, data));
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        getLoaderManager().destroyLoader(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID);
        if (this.mPlayerView != null) {
            this.mPlayerView.destroy();
        }
        if (this.mPagerAdapter != null) {
            this.mPagerAdapter.destroy();
        }
        this.lastPagePosition = 0;
    }

    private void getData() {
        if (this.mFlow == null) {
            this.mFlow = new LivePageFlow();
        }
        this.mFlow.start(false);
    }

    private void initListView() {
        this.mListView = (ListView) this.mPullToRefreshListView.getRefreshableView();
        this.mListView.setDivider(null);
        this.mListView.setDividerHeight(0);
        this.mLiveAdapter = new LiveAdapter(getContext());
        this.mListView.setAdapter(this.mLiveAdapter);
    }

    private void initHeaderView() {
        this.mHeaderView = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_live_header, null);
        this.mViewpager = (ViewPager) this.mHeaderView.findViewById(R.id.view_live_header_viewpager);
        this.mRecyclerview = (RecyclerView) this.mHeaderView.findViewById(R.id.view_live_header_recyclerview);
        this.mIvSingle = (ImageView) this.mHeaderView.findViewById(R.id.view_live_header_iv_single);
        this.mIvDouble = (ImageView) this.mHeaderView.findViewById(R.id.view_live_header_iv_double);
        this.mHeaderView.setClipChildren(false);
        int width = UIsUtils.getScreenWidth() - UIsUtils.dipToPx(40.0f);
        this.mViewPagerHeight = (width * 240) / 320;
        LayoutParams params = (LayoutParams) this.mViewpager.getLayoutParams();
        params.width = width;
        params.height = this.mViewPagerHeight;
        this.mViewpager.setLayoutParams(params);
        initHeaderImageView();
        initViewPager();
        initRecyclerView();
        this.mListView.addHeaderView(this.mHeaderView);
    }

    private void initHeaderImageView() {
        LayoutParams params;
        this.mViewPagerHeight = this.mViewPagerHeight == 0 ? this.mViewpager.getHeight() : this.mViewPagerHeight;
        if (LetvUtils.isInHongKong()) {
            params = new LayoutParams(-1, this.mViewPagerHeight + UIsUtils.dipToPx(76.0f));
        } else {
            params = new LayoutParams(-1, this.mViewPagerHeight + UIsUtils.dipToPx(136.0f));
        }
        getShowingIv().setLayoutParams(params);
        getBackgroudIv().setLayoutParams(params);
        this.mHeaderView.requestLayout();
    }

    private void initViewPager() {
        this.mPagerAdapter = new LiveRemenLivePagerAdapter(getContext(), this.mLiveList);
        this.mViewpager.setAdapter(this.mPagerAdapter);
        this.mViewpager.setOffscreenPageLimit(2);
        this.mViewpager.setPageMargin(20);
        this.mViewpager.addOnPageChangeListener(new OnPageChangeListener(this) {
            private boolean isScrolling;
            private int lastPosition;
            final /* synthetic */ LiveFragment this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (this.isScrolling) {
                    int offset;
                    if (this.lastPosition == 0) {
                        this.lastPosition = positionOffsetPixels;
                        this.this$0.direction = 0;
                    }
                    if (positionOffsetPixels > this.lastPosition) {
                        if (this.this$0.direction == 0) {
                            this.this$0.direction = 1;
                        } else if (this.this$0.direction == -1) {
                            this.lastPosition = positionOffsetPixels;
                            this.this$0.direction = 1;
                        }
                        offset = positionOffsetPixels - this.lastPosition;
                    } else {
                        if (this.this$0.direction == 0) {
                            this.this$0.direction = -1;
                        } else if (this.this$0.direction == 1) {
                            this.lastPosition = positionOffsetPixels;
                            this.this$0.direction = -1;
                        }
                        offset = this.lastPosition - positionOffsetPixels;
                    }
                    if (this.this$0.mViewHolder != null) {
                        if (offset < this.this$0.mViewHolder.mLayoutTitle.getHeight() * 2) {
                            this.this$0.mViewHolder.mLayoutTitle.setVisibility(0);
                            this.this$0.mViewHolder.mLayoutTitle.setTranslationY((float) ((-offset) / 4));
                            this.this$0.mViewHolder.mLayoutTop.setTranslationY((float) (offset / 4));
                        } else {
                            this.this$0.mViewHolder.mLayoutTop.setTranslationY(0.0f);
                            this.this$0.mViewHolder.mLayoutTitle.setVisibility(8);
                        }
                    }
                    this.this$0.showScrollTopImage(offset);
                } else if (this.this$0.mViewHolder != null) {
                    this.this$0.mViewHolder.mLayoutTitle.setVisibility(0);
                    this.this$0.mViewHolder.mLayoutTitle.setTranslationY(0.0f);
                    this.this$0.mViewHolder.mLayoutTop.setTranslationY(0.0f);
                }
            }

            public void onPageSelected(int position) {
                LogInfo.log("pjf", "onPageSelected : " + position);
                this.this$0.onPageSelected(position);
            }

            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    this.isScrolling = true;
                    return;
                }
                LogInfo.log("pjf", "onPageScrollStateChanged idle or setted");
                this.isScrolling = false;
                this.lastPosition = 0;
                this.this$0.showTopImage();
            }
        });
    }

    private void initRecyclerView() {
        ViewGroup.LayoutParams params = this.mRecyclerview.getLayoutParams();
        if (LetvUtils.isInHongKong()) {
            this.mLayoutManager = new LinearLayoutManager(getContext(), 0, false);
            this.mChannelAdapter = new LiveRemenHkChannelAdapter(getContext());
            params.height = UIsUtils.dipToPx(60.0f);
        } else {
            this.mLayoutManager = new StaggeredGridLayoutManager(2, 0);
            this.mChannelAdapter = new LiveRemenChannelAdapter(getContext());
            params.height = UIsUtils.dipToPx(120.0f);
        }
        this.mRecyclerview.setLayoutParams(params);
        this.mRecyclerview.setLayoutManager(this.mLayoutManager);
        this.mRecyclerview.setAdapter(this.mChannelAdapter);
    }

    private void autoRefresh() {
        if (!(this.mFlow == null || this.mPageBean == null)) {
            if (this.mPageBean.mLunboData != null) {
                this.mFlow.requestProgram(this.mPageBean.mLunboData, true);
            }
            if (this.mPageBean.mWeiShiData != null) {
                this.mFlow.requestProgram(this.mPageBean.mWeiShiData, false);
            }
        }
        if (this.mRefreshSubscription == null || this.mRefreshSubscription.isUnsubscribed()) {
            this.mRefreshSubscription = Observable.timer(180, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>(this) {
                final /* synthetic */ LiveFragment this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void call(Long aLong) {
                    this.this$0.autoRefresh();
                }
            });
        }
    }

    private void onPageSelected(final int position) {
        if (this.mLiveList != null && this.mLiveList.size() != 0 && this.mGotoChildPageIndex == -1 && this.mPageBean != null) {
            if (this.mPageBean.mLiveData != null || this.mPageBean.mLunboData != null) {
                this.curPagePosition = position;
                this.mCurrentBean = (LetvBaseBean) this.mLiveList.get(this.curPagePosition % this.mLiveList.size());
                showTopImage();
                LetvBaseBean baseBean = (LetvBaseBean) this.mLiveList.get(position % this.mLiveList.size());
                if (baseBean instanceof LiveBeanLeChannel) {
                    requestWatchNum(((LiveBeanLeChannel) baseBean).channelId);
                } else {
                    requestWatchNum(((LiveRemenBaseBean) baseBean).id);
                }
                stopPlayerView(false, true);
                View view = this.mPagerAdapter.getViewByIndex(position);
                if (view != null) {
                    if (this.mViewHolder != null) {
                        this.mViewHolder.mLayoutTitle.setVisibility(8);
                        this.mViewHolder.mLayoutTitle.setTranslationY(0.0f);
                        this.mViewHolder.mLayoutTop.setTranslationY(0.0f);
                    }
                    this.mViewHolder = (ViewHolder) view.getTag();
                    showTopTitle();
                }
                if (this.mAddViewSubscription == null || this.mAddViewSubscription.isUnsubscribed()) {
                    this.mAddViewSubscription = Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>(this) {
                        final /* synthetic */ LiveFragment this$0;

                        public void call(Long aLong) {
                            if (this.this$0.mViewHolder == null) {
                                View view = this.this$0.mPagerAdapter.getViewByIndex(position);
                                if (view != null) {
                                    this.this$0.mViewHolder = (ViewHolder) view.getTag();
                                    this.this$0.showTopTitle();
                                } else {
                                    return;
                                }
                            }
                            this.this$0.mPlayerView = new LivePlayerView(this.this$0.getContext());
                            this.this$0.mPlayerView.init(-1, this.this$0.getChildFragmentManager());
                            this.this$0.mViewHolder.mLayoutVideoview.addView(this.this$0.mPlayerView);
                            LetvBaseBean baseBean = (LetvBaseBean) this.this$0.mLiveList.get(position % this.this$0.mLiveList.size());
                            if (baseBean instanceof LiveBeanLeChannel) {
                                this.this$0.mPlayerView.setLunboChannel((LiveBeanLeChannel) baseBean);
                            } else {
                                this.this$0.mPlayerView.setLiveRemenBaseBean((LiveRemenBaseBean) baseBean);
                            }
                            this.this$0.mPlayerView.canShowTitle(false);
                            this.this$0.mPlayerView.onResume();
                        }
                    }, new Action1<Throwable>(this) {
                        final /* synthetic */ LiveFragment this$0;

                        {
                            if (HotFix.PREVENT_VERIFY) {
                                System.out.println(VerifyLoad.class);
                            }
                            this.this$0 = this$0;
                        }

                        public void call(Throwable throwable) {
                        }
                    });
                }
            }
        }
    }

    private void showTopTitle() {
        if (this.mViewHolder != null && this.mViewHolder.mLayoutTitle.getVisibility() != 0) {
            TranslateAnimation topAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (UIsUtils.dipToPx(60.0f) / 2), 0.0f);
            topAnimation.setDuration(400);
            this.mViewHolder.mLayoutTop.startAnimation(topAnimation);
            this.mViewHolder.mLayoutTitle.setVisibility(0);
            TranslateAnimation animation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f);
            animation.setDuration(400);
            this.mViewHolder.mLayoutTitle.startAnimation(animation);
        }
    }

    private void showTopImage() {
        LogInfo.log("pjf", "showTopImage");
        if (this.mPageBean != null) {
            getShowingIv().setAlpha(255);
            getBackgroudIv().setAlpha(255);
            getShowingIv().setVisibility(0);
            getBackgroudIv().setVisibility(8);
            getShowingIv().setImageResource(getTypeImage(this.mCurrentBean));
        }
    }

    private void showScrollTopImage(int offset) {
        if (offset < 510 && this.curPagePosition >= 1) {
            if (this.direction == 1) {
                this.mBackgroundBean = (LetvBaseBean) this.mLiveList.get((this.curPagePosition + 1) % this.mLiveList.size());
            } else {
                this.mBackgroundBean = (LetvBaseBean) this.mLiveList.get((this.curPagePosition - 1) % this.mLiveList.size());
            }
            if (this.mBackgroundBean != null && this.mCurrentBean != null && getTypeImage(this.mBackgroundBean) != getTypeImage(this.mCurrentBean)) {
                getShowingIv().setAlpha(255 - (offset / 2));
                getBackgroudIv().setVisibility(0);
                getBackgroudIv().setImageResource(getTypeImage(this.mBackgroundBean));
                getBackgroudIv().setAlpha(offset / 2);
            }
        }
    }

    private int getTypeImage(LetvBaseBean baseBean) {
        if (!(baseBean instanceof LiveRemenBaseBean)) {
            return 2130838475;
        }
        String ch = ((LiveRemenBaseBean) baseBean).ch;
        if (ch.equals(LiveRoomConstant.LIVE_TYPE_SPORT)) {
            return 2130838476;
        }
        if (ch.equals(LiveRoomConstant.LIVE_TYPE_MUSIC)) {
            return 2130838474;
        }
        return 2130838475;
    }

    private ImageView getShowingIv() {
        if (this.curPagePosition % 2 == 0) {
            return this.mIvDouble;
        }
        return this.mIvSingle;
    }

    private ImageView getBackgroudIv() {
        if (this.curPagePosition % 2 == 0) {
            return this.mIvSingle;
        }
        return this.mIvDouble;
    }

    private void requestWatchNum(String id) {
        if (this.mFlow != null) {
            this.mFlow.requestLiveWatchNum(id);
        }
    }

    private void stopPlayerView(boolean immediately, final boolean destroy) {
        if (!(this.mAddViewSubscription == null || this.mAddViewSubscription.isUnsubscribed())) {
            this.mAddViewSubscription.unsubscribe();
        }
        if (this.mPlayerView != null && this.mPlayerView.getParent() != null) {
            if (immediately) {
                this.mPlayerView.onPause();
                if (destroy) {
                    this.mPlayerView.destroy();
                    return;
                }
                return;
            }
            Observable.timer(360, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>(this) {
                final /* synthetic */ LiveFragment this$0;

                public void call(Long aLong) {
                    this.this$0.mPlayerView.onPause();
                    if (destroy) {
                        this.this$0.mPlayerView.destroy();
                    }
                }
            });
        }
    }

    public void onResume() {
        super.onResume();
        resume();
    }

    private void resume() {
        if (!this.mIsHidden) {
            registerRxBus();
            if (this.mGotoChildPageIndex == -1) {
                if (!this.isFull || this.mPlayerView == null) {
                    if (this.mFlow == null) {
                        this.mFlow = new LivePageFlow();
                    }
                    this.mFlow.start(false);
                    initHeaderImageView();
                    initRecyclerView();
                    if (!(this.mLiveList == null || this.mLiveList.size() == 0)) {
                        int position = 500 - (LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA % this.mLiveList.size());
                        LogInfo.log("pjf", "LiveFragment onResume position : " + position);
                        LogInfo.log("pjf", "LiveFragment onResume lastPosition : " + this.lastPagePosition);
                        if (PreferencesManager.getInstance().getSwitchEndlessRecyclerEnable() != 1) {
                            position = 0;
                        }
                        if (this.lastPagePosition == position) {
                            onPageSelected(this.lastPagePosition);
                        }
                    }
                } else {
                    this.mPlayerView.onResume();
                }
            } else if (LiveLunboUtils.isLunBoWeiShiType(this.mGotoChildPageIndex)) {
                LiveSubTypeLunboActivity.launch(this.mContext, this.mGotoChildPageIndex);
            } else {
                LiveSubTypeActivity.launch(this.mContext, this.mGotoChildPageIndex);
            }
            LogInfo.log("jc666", "livefragment onResume");
        }
    }

    public void onPause() {
        super.onPause();
        this.mGotoChildPageIndex = -1;
        unRegisterRxBus();
        if (!(this.mRefreshSubscription == null || this.mRefreshSubscription.isUnsubscribed())) {
            this.mRefreshSubscription.unsubscribe();
        }
        if (this.mFlow != null) {
            this.mFlow.destroy();
        }
        this.lastPagePosition = this.curPagePosition;
        stopPlayerView(true, false);
        LogInfo.log("jc666", "livefragment onPause");
    }

    private void registerRxBus() {
        LogInfo.log(RxBus.TAG, "LiveFragment注册RxBus");
        if (this.mSubscription == null) {
            this.mSubscription = new CompositeSubscription();
        }
        if (!this.mSubscription.hasSubscriptions()) {
            LogInfo.log(RxBus.TAG, "LiveFragment添加RxBus Event");
            this.mSubscription.add(this.mRxBus.toObserverable().observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Object>(this) {
                final /* synthetic */ LiveFragment this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void call(Object event) {
                    LogInfo.log(RxBus.TAG, "LiveFragment接收到" + event.getClass().getName());
                    if (event instanceof RequestPageDataResultEvent) {
                        this.this$0.mPageBean = ((RequestPageDataResultEvent) event).result;
                        this.this$0.mPullToRefreshListView.onRefreshComplete();
                        if (this.this$0.mPageBean.mLiveData != null) {
                            this.this$0.mLiveList.clear();
                            this.this$0.mLiveList.addAll(this.this$0.mPageBean.mLiveData);
                            if (this.this$0.mPageBean.mLiveData.size() < 3 && this.this$0.mPageBean.mLunboData != null) {
                                this.this$0.mLiveList.addAll(this.this$0.mPageBean.mLunboData);
                            }
                        } else if (this.this$0.mPageBean.mLunboData != null) {
                            this.this$0.mLiveList.clear();
                            this.this$0.mLiveList.addAll(this.this$0.mPageBean.mLunboData);
                        }
                        this.this$0.mPagerAdapter.notifyDataSetChanged();
                        int position = 500 - (LeMessageIds.MSG_FLOAT_BALL_REQUEST_DATA % this.this$0.mLiveList.size());
                        if (PreferencesManager.getInstance().getSwitchEndlessRecyclerEnable() != 1) {
                            position = 0;
                        }
                        LogInfo.log("pjf", "LiveFragment rxbus position : " + position);
                        LogInfo.log("pjf", "LiveFragment rxbus lastPosition : " + this.this$0.lastPagePosition);
                        LogInfo.log("pjf", "LiveFragment rxbus currPosition : " + this.this$0.mViewpager.getCurrentItem());
                        if (this.this$0.lastPagePosition != position) {
                            this.this$0.mViewpager.setCurrentItem(position, false);
                        }
                        this.this$0.mLiveAdapter.setPageBean(this.this$0.mPageBean);
                        this.this$0.mLiveAdapter.notifyDataSetChanged();
                        this.this$0.mRootView.finish();
                        if (this.this$0.mPageBean.mLunboData != null || this.this$0.mPageBean.mWeiShiData != null) {
                            this.this$0.autoRefresh();
                        }
                    } else if (event instanceof ProgramListResultEvent) {
                        ProgramListResultEvent resultEvent = (ProgramListResultEvent) event;
                        this.this$0.mLiveAdapter.setPrograms(resultEvent.isLunboData, resultEvent.result);
                    } else if (event instanceof LivePageFlowRequestErrorEvent) {
                        this.this$0.mPullToRefreshListView.onRefreshComplete();
                        if (this.this$0.mPageBean == null) {
                            this.this$0.mRootView.netError(false);
                            this.this$0.mRootView.setErrorBackgroundColor(this.this$0.getResources().getColor(2131492949));
                            return;
                        }
                        ToastUtils.showToast(this.this$0.mContext, 2131100495);
                    } else if (event instanceof FullScreenBtnClickEvent) {
                        this.this$0.handleFullScreenEvent((FullScreenBtnClickEvent) event);
                    } else if (event instanceof LiveWatchNumEvent) {
                        LiveWatchNumEvent watchNumEvent = (LiveWatchNumEvent) event;
                        if (this.this$0.mViewHolder == null) {
                            return;
                        }
                        if (watchNumEvent.watchNumBean == null) {
                            this.this$0.mViewHolder.mTvPeople.setText(2131100320);
                            return;
                        }
                        this.this$0.mViewHolder.mTvPeople.setVisibility(0);
                        this.this$0.mViewHolder.mTvPeople.setText(String.format(this.this$0.getString(2131100319), new Object[]{LiveUtils.getPeopleNum(this.this$0.mContext, watchNumEvent.watchNumBean.number)}));
                    } else if (event instanceof InstantiateItemEvent) {
                        InstantiateItemEvent itemEvent = (InstantiateItemEvent) event;
                        if (itemEvent.position == this.this$0.mViewpager.getCurrentItem()) {
                            this.this$0.onPageSelected(itemEvent.position);
                        }
                    }
                }
            }));
        }
    }

    private void handleFullScreenEvent(FullScreenBtnClickEvent fullScreenBtnClickEvent) {
        this.isFull = fullScreenBtnClickEvent.isFull;
        ((MainActivity) getActivity()).setRedPacketEntryLocation(fullScreenBtnClickEvent.isFull);
        if (fullScreenBtnClickEvent.isFull) {
            if (this.mPlayerView != null) {
                getActivity().setRequestedOrientation(0);
                this.mLayoutContainer.setVisibility(0);
                ((ViewGroup) this.mPlayerView.getParent()).removeView(this.mPlayerView);
                this.mLayoutContainer.addView(this.mPlayerView, new ViewGroup.LayoutParams(-1, -1));
                UIsUtils.zoomViewFull(this.mPlayerView);
                UIsUtils.fullScreen(getActivity());
                ((MainActivity) getActivity()).mMainTopNavigationView.setVisibility(8);
                ((MainActivity) getActivity()).mMainBottomNavigationView.setVisibility(8);
                getActivity().findViewById(R.id.main_red_point_layout).setVisibility(8);
                getActivity().findViewById(R.id.main_bottom_line).setVisibility(8);
                getActivity().findViewById(R.id.main_top_line).setVisibility(8);
            }
        } else if (this.mPlayerView != null) {
            getActivity().setRequestedOrientation(1);
            this.mLayoutContainer.removeAllViews();
            this.mLayoutContainer.setVisibility(8);
            if (this.mViewHolder != null) {
                this.mViewHolder.mLayoutVideoview.addView(this.mPlayerView);
            }
            UIsUtils.zoomView(320, 180, this.mPlayerView);
            UIsUtils.cancelFullScreen(getActivity());
            ((MainActivity) getActivity()).mMainTopNavigationView.setVisibility(0);
            ((MainActivity) getActivity()).mMainBottomNavigationView.setVisibility(0);
            getActivity().findViewById(R.id.main_red_point_layout).setVisibility(0);
            getActivity().findViewById(R.id.main_bottom_line).setVisibility(0);
            getActivity().findViewById(R.id.main_top_line).setVisibility(0);
        }
    }

    private void unRegisterRxBus() {
        LogInfo.log(RxBus.TAG, "LiveFragment取消注册RxBus");
        if (this.mSubscription != null && this.mSubscription.hasSubscriptions()) {
            this.mSubscription.unsubscribe();
        }
        this.mSubscription = null;
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
    }

    public boolean onBackPressed() {
        if (!this.isFull) {
            return false;
        }
        this.mRxBus.send(new FullScreenBtnClickEvent(false));
        return true;
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogInfo.log("pjf", "onHiddenChanged");
        if (hidden) {
            WatchAndBuyView.sAddToCartGoods = null;
            onPause();
            stopPlayerView(true, true);
            BaseApplication.getInstance().onAppMemoryLow();
            return;
        }
        resume();
    }

    public String getTagName() {
        return "LiveFragment";
    }

    public int getContainerId() {
        return R.id.main_content;
    }

    public String getPageId() {
        return PageIdConstant.onLiveremenCtegoryPage;
    }

    public void gotoChildPage(String childId) {
        int index = LiveUtils.getIndexByChannelType(childId);
        if (index != -1) {
            this.mGotoChildPageIndex = index;
        }
    }

    public void startFromBackground() {
        LogInfo.log("jc666", "livefragment start from background");
        if (this.mPlayerView != null) {
            this.mPlayerView.startFromBackground();
        }
    }
}
