package com.letv.android.client.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.channel.ChannelDetailExpandableListAdapter;
import com.letv.android.client.adapter.channel.ChannelDetailListAdapter;
import com.letv.android.client.adapter.channel.ChannelDetailTopListAdapter;
import com.letv.android.client.adapter.channel.ChannelProgramGuidesListAdater;
import com.letv.android.client.fragment.HomeBaseFragment.CurrentPage;
import com.letv.android.client.task.ChannelDetailTask;
import com.letv.android.client.task.RequestSiftedOrDolbyDatas;
import com.letv.android.client.utils.FootViewUtil;
import com.letv.android.client.view.channel.ChannelFilterView;
import com.letv.core.BaseApplication;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.channel.AlbumNewList;
import com.letv.core.bean.channel.ChannelFilterTypes;
import com.letv.core.bean.channel.ChannelFilterTypes.ChannelFilterItemType;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.bean.channel.FilterBean;
import com.letv.core.bean.channel.TopList;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.datastatistics.util.DataConstant.StaticticsVersion2Constatnt.CategoryCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import java.util.Iterator;

public class ChannelDetailItemFragment extends ChannelBaseFragment {
    private boolean isFilter;
    private boolean isFilterButtonSelect;
    private boolean mCanRefrence;
    private ChannelFilterItemType mChannelFilterItemTypes;
    protected ChannelHomeBean mChannelHomeBean;
    private ChannelNavigation mChannelNavigation;
    private ArrayList<SiftKVP> mCurrentSiftKYPs;
    protected ChannelDetailListAdapter mDetailListAdapter;
    private OnClickListener mFilterImageOnclickListener;
    private ImageView mFilterImageView;
    private TextView mFilterText;
    private ChannelFilterView mFilterView;
    private FootViewUtil mFootViewUtil;
    private boolean mIsLoadingMore;
    private boolean mIsShowingFilterView;
    protected ChannelDetailExpandableListAdapter mListAdapter;
    private int mPageSize;
    private ChannelProgramGuidesListAdater mProgramGuidesListAdater;
    private RelativeLayout mRelativeLayout;
    private boolean mRequestFilter;
    OnScrollListener mScrollEvent;
    private ArrayList<SiftKVP> mSiftKYPs;
    private TextView mTitleView;

    public ChannelDetailItemFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.isFilter = false;
        this.isFilterButtonSelect = false;
        this.mSiftKYPs = new ArrayList();
        this.mCurrentSiftKYPs = new ArrayList();
        this.mPageSize = 1;
        this.mCanRefrence = false;
        this.mFilterImageOnclickListener = new 5(this);
        this.mScrollEvent = new 6(this);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mChannelNavigation = (ChannelNavigation) bundle.getSerializable("navigation");
            this.mChannel = (Channel) bundle.getSerializable("channel");
            this.mSiftKYPs = (ArrayList) bundle.getSerializable("mSiftKYPs");
            this.isFilter = ((Boolean) bundle.getSerializable("isFilter")).booleanValue();
        }
        super.onViewCreated(view, savedInstanceState);
        findView();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    public void onResume() {
        super.onResume();
        focusStartMove();
    }

    public void onPause() {
        super.onPause();
        focusStopMove();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            requestData(false);
        }
    }

    protected void findView() {
        this.mFilterImageView = (ImageView) getActivity().findViewById(R.id.title_channel_filter);
        this.mTitleView = (TextView) getActivity().findViewById(R.id.title_channel_name);
        this.mRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.channel_filter_conditions);
        this.mFilterText = (TextView) getActivity().findViewById(R.id.fifter_conditions);
        this.mRelativeLayout.setOnClickListener(this.mFilterImageOnclickListener);
        this.mFilterImageView.setVisibility(8);
    }

    public void reloadTabSelectData(Channel channel, ChannelNavigation navigation) {
        this.mChannel = channel;
        this.mChannelNavigation = navigation;
        if (navigation != null) {
            this.mTitleView.setText(navigation.nameCn);
        }
        clear();
        init();
    }

    private void init() {
        String str;
        if (this.mChannelNavigation != null && this.mChannelNavigation.subTitle == 1) {
            this.isFilterButtonSelect = true;
            this.mFilterImageView.setVisibility(0);
        }
        if (this.mChannel == null) {
            this.mChannel = new Channel();
        }
        Context instance = BaseApplication.getInstance();
        int i = this.mChannel.id;
        if (this.mChannelNavigation == null) {
            str = "";
        } else {
            str = String.valueOf(this.mChannelNavigation.pageid);
        }
        this.mChanelDetailtask = new ChannelDetailTask(instance, i, str);
        this.mChanelDetailtask.initCallBack(this);
        this.mFilterView = new ChannelFilterView(this.mContext);
        LayoutParams params = new LayoutParams(-1, -1);
        params.setMargins(0, 0, 0, 0);
        this.mRoot.addView(this.mFilterView, params);
        this.mFilterView.setVisibility(8);
        this.mFootViewUtil = new FootViewUtil(this.mPullView);
        if (this.isFilter || this.isFilterButtonSelect) {
            this.mDetailListAdapter = new ChannelDetailListAdapter(this.mContext, this.mChannel.id);
            ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mDetailListAdapter);
        } else {
            this.mListAdapter = new ChannelDetailExpandableListAdapter(this.mContext);
            this.mListAdapter.initMorecallBack(this);
            this.mListAdapter.setStatisticsInfo(this.mChannel.id, this.mChannelNavigation.pageid + "");
        }
        this.mFilterImageView.setOnClickListener(this.mFilterImageOnclickListener);
        this.mPullView.setOnRefreshListener(new 1(this));
        this.mPullView.setOnScrollListener(this.mScrollEvent);
        this.mFilterView.setSureClickListener(new 2(this));
        ((ExpandableListView) this.mPullView.getRefreshableView()).setOnGroupClickListener(new 3(this));
        this.mRoot.setRefreshData(new 4(this));
        this.mRequestSiftedOrDolbyData = new RequestSiftedOrDolbyDatas(this.mContext, this.mChannel);
        this.mRequestSiftedOrDolbyData.setRequestCallBack(this);
        requestData(false);
    }

    void loadData(boolean isRefresh) {
        if (this.mChanelDetailtask == null || this.mChannel == null || ((this.mChannelNavigation == null && !this.isFilter) || this.mPageCardList == null)) {
            dataError(false);
        } else if (this.mChanelDetailtask != null && this.isFilterButtonSelect) {
            this.mChanelDetailtask.requestSiftList(this.isFilterButtonSelect);
            loading();
            if (this.mDetailListAdapter != null) {
                this.mDetailListAdapter.setPageCardList(this.mPageCardList);
            }
        } else if (this.mChannel.id == 1001) {
            this.mRequestSiftedOrDolbyData.setStartPosition(0);
            this.mRequestSiftedOrDolbyData.getChannelListAfterSift(false);
            if (this.mDetailListAdapter != null) {
                this.mDetailListAdapter.setPageCardList(this.mPageCardList);
            }
        } else if (!this.isFilter && this.mChanelDetailtask != null) {
            if (this.mListAdapter != null) {
                this.mListAdapter.setPageCardList(this.mPageCardList);
            }
            this.mChanelDetailtask.requestChannelDetailList(false, false, this.mPageSize, this.mPageCardList);
            getFocusAd(String.valueOf(this.mChannel.id));
        } else if (this.mRequestSiftedOrDolbyData != null) {
            this.mChanelDetailtask.requestSiftList(this.isFilterButtonSelect);
            this.mFilterImageView.setVisibility(0);
            this.mRequestSiftedOrDolbyData.setSiftKvps(this.mSiftKYPs);
            this.mRequestSiftedOrDolbyData.getChannelListAfterSift(false);
            if (this.mDetailListAdapter != null) {
                this.mDetailListAdapter.setPageCardList(this.mPageCardList);
            }
        }
    }

    public void onChannelSuccess(ChannelHomeBean result, boolean isRefrence, boolean isLoadingMore, boolean isFromNet) {
        if (isRefrence && isFromNet) {
            refreshComplete();
        }
        setRootView(result, isLoadingMore, isFromNet);
        this.mIsLoadingMore = false;
    }

    public void onTopSuccess(TopList result) {
        showTopView(result);
    }

    public void onSiftListSuccess(ChannelFilterTypes result) {
        updateSiftView(result);
    }

    public void dataError(boolean isLoadingMore) {
        this.mFilterImageView.setVisibility(8);
        if (isLoadingMore) {
            this.mIsLoadingMore = false;
            this.mFootViewUtil.showFootNoMore();
        } else if (this.mChannelHomeBean == null) {
            showDataError();
        }
    }

    public void netError(boolean isLoadingMore) {
        this.mFilterImageView.setVisibility(8);
        if (isLoadingMore) {
            this.mIsLoadingMore = false;
            this.mFootViewUtil.showFootError();
        } else if (this.mChannelHomeBean == null) {
            showNetError();
        }
    }

    public void showErrorPage(boolean isNetError, boolean isFirstPage) {
        this.mRequestFilter = false;
        setFilterHeader();
        this.isFilter = true;
        refreshComplete();
        if (isNetError) {
            if (isFirstPage) {
                showNetError();
            } else {
                this.mFootViewUtil.showFootError();
            }
        } else if (isFilterTypeNull()) {
            showDataError(true, true);
        } else if (isFirstPage) {
            showDataError(true, false);
        } else {
            this.mFootViewUtil.showFootNoMore();
        }
    }

    public void updateUI(AlbumNewList result, boolean isFromCache) {
        this.mRequestFilter = false;
        if (this.mChannel.id == 1001) {
            super.updateUI(result, isFromCache);
        } else if (this.mPageCardList == null || this.mRequestSiftedOrDolbyData == null) {
            showDataError();
        } else {
            finishLoading();
            refreshComplete();
            this.mIsLoadingMore = false;
            if (!BaseTypeUtils.isListEmpty(result)) {
                this.isFilter = true;
                if (this.mDetailListAdapter == null) {
                    removeFocusView();
                    this.mDetailListAdapter = new ChannelDetailListAdapter(this.mContext, this.mChannel.id);
                    ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mDetailListAdapter);
                    this.mDetailListAdapter.setPageCardList(this.mPageCardList);
                }
                setFilterHeader();
                if (this.mRequestSiftedOrDolbyData.isLoadingMore()) {
                    this.mDetailListAdapter.addList(result);
                } else {
                    ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mDetailListAdapter);
                    this.mDetailListAdapter.setList((ExpandableListView) this.mPullView.getRefreshableView(), result);
                }
                if (this.mDetailListAdapter.getChildrenCount(0) > 5) {
                    this.mFootViewUtil.showLoadingView();
                } else {
                    this.mFootViewUtil.removeFootView();
                }
            } else if (!isFromCache && this.mRequestSiftedOrDolbyData.isLoadingMore() && ((ExpandableListView) this.mPullView.getRefreshableView()).getFooterViewsCount() > 0) {
                this.mFootViewUtil.showFootNoMore();
            }
        }
    }

    public void moreSelect(int type, ArrayList<SiftKVP> siftKVPTmps, String name) {
        if (type == 1) {
            this.isFilter = true;
            this.mFilterImageView.setVisibility(0);
            setSiftKYP(siftKVPTmps);
            this.mTitleView.setText(name);
        } else if (type == 2) {
            this.mPageSize = 1;
        }
    }

    private void setRootView(ChannelHomeBean result, boolean isLoadingMore, boolean isFromNet) {
        if (result == null || BaseTypeUtils.isListEmpty(result.block)) {
            finishLoading();
            if (isLoadingMore) {
                this.mFootViewUtil.showFootNoMore();
                return;
            } else if (this.mChannelHomeBean == null) {
                showDataError();
                return;
            } else {
                return;
            }
        }
        if (!BaseTypeUtils.isListEmpty(result.block)) {
            if (((HomeBlock) result.block.get(0)).contentType == 5) {
                getListSiftKVP((HomeBlock) result.block.get(0));
                this.mFilterImageView.setVisibility(8);
                this.isFilter = true;
                requestData(false);
                return;
            }
            this.mFilterImageView.setVisibility(8);
        }
        if (!BaseTypeUtils.isListEmpty(result.block) && TextUtils.equals("15", ((HomeBlock) result.block.get(0)).contentStyle)) {
            this.mChanelDetailtask.requestTopList();
        } else if (!BaseTypeUtils.isListEmpty(result.block)) {
            if (!isLoadingMore && !BaseTypeUtils.isListEmpty(result.focus)) {
                setFocusView(result.focus);
            } else if (!isLoadingMore) {
                removeFocusView();
            }
            if (result.block.size() == 1 && BaseTypeUtils.isListEmpty(((HomeBlock) result.block.get(0)).list)) {
                if (isLoadingMore) {
                    this.mFootViewUtil.showFootNoMore();
                } else {
                    showDataError();
                }
            }
            this.mPullView.setPullToRefreshEnabled(true);
            String style = ((HomeBlock) result.block.get(0)).contentStyle;
            if (TextUtils.equals("58", style) || TextUtils.equals("59", style) || TextUtils.equals("60", style) || TextUtils.equals("61", style)) {
                String str;
                finishLoading();
                this.mChannelHomeBean = result;
                this.mFilterImageView.setVisibility(8);
                if (this.mProgramGuidesListAdater == null) {
                    this.mProgramGuidesListAdater = new ChannelProgramGuidesListAdater(this.mContext, this.mChannel.id);
                }
                ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mProgramGuidesListAdater);
                this.mProgramGuidesListAdater.setList((ExpandableListView) this.mPullView.getRefreshableView(), result.block);
                Context context = this.mContext;
                String str2 = "19";
                String str3 = PageIdConstant.programGuides;
                if (this.mChannel != null) {
                    str = this.mChannel.id + "";
                } else {
                    str = null;
                }
                StatisticsUtils.staticticsInfoPost(context, str2, null, null, -1, null, str3, str, null, null, null, null);
            } else if (isLoadingMore) {
                this.mChannelHomeBean.block.addAll(result.block);
                this.mListAdapter.addDataList(result.block);
            } else {
                this.mChannelHomeBean = result;
                if (this.mChannelHomeBean.isShowLiveBlock) {
                    this.mChanelDetailtask.requestLiveList(RequestManner.NETWORK_THEN_CACHE);
                } else {
                    finishLoading();
                }
                this.mListAdapter.setVisibleToUser(true);
                initTabView(this.mChannelHomeBean);
                this.mPullView.setPullToRefreshEnabled(true);
                ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mListAdapter);
                this.mListAdapter.setDataList((ExpandableListView) this.mPullView.getRefreshableView(), result, isFromNet);
                if (canRefrenceByPageId()) {
                    this.mFootViewUtil.showLoadingView();
                } else {
                    this.mFootViewUtil.removeFootView();
                }
            }
        } else if (isLoadingMore) {
            this.mFootViewUtil.showFootNoMore();
        }
    }

    public void getListSiftKVP(HomeBlock channelHomeBlock) {
        if (channelHomeBlock != null) {
            setSiftKYP(LetvUtils.getStringSKfList(channelHomeBlock.redField));
        }
    }

    private void showTopView(TopList topList) {
        finishLoading();
        if (topList == null || this.mPageCardList == null) {
            showDataError();
            return;
        }
        ChannelDetailTopListAdapter adapter = new ChannelDetailTopListAdapter(this.mContext);
        adapter.setPageCardList(this.mPageCardList);
        this.mPullView.setPullToRefreshEnabled(false);
        ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(adapter);
        adapter.setList((ExpandableListView) this.mPullView.getRefreshableView(), topList.list);
        this.mFootViewUtil.removeFootView();
    }

    private void setFilterHeader() {
        if (this.mCurrentSiftKYPs == null) {
            this.mRelativeLayout.setVisibility(8);
            return;
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = this.mCurrentSiftKYPs.iterator();
        while (it.hasNext()) {
            SiftKVP siftKVP = (SiftKVP) it.next();
            if (!(siftKVP.key == null || siftKVP.key.contains(getResources().getString(2131099828)))) {
                sb.append(siftKVP.key).append("/");
            }
        }
        String filterString = new String(sb);
        if (filterString.endsWith("/")) {
            filterString = filterString.substring(0, filterString.length() - 1);
        }
        if (TextUtils.isEmpty(filterString)) {
            this.mRelativeLayout.setVisibility(8);
            return;
        }
        this.mRelativeLayout.setVisibility(0);
        this.mFilterText.setText(getResources().getString(2131099829) + filterString);
    }

    private void doFilterView(boolean isFromClick) {
        boolean z = this.mIsShowingFilterView ? this.mIsShowingFilterView : !BaseTypeUtils.isListEmpty(this.mSiftKYPs);
        this.mIsShowingFilterView = z;
        if (!NetworkUtils.isNetworkAvailable() && this.mChannelFilterItemTypes == null) {
            ToastUtils.showToast(2131100493);
        } else if (!isFilterTypeNull()) {
            if (this.mRelativeLayout != null) {
                this.mRelativeLayout.setVisibility(8);
            }
            if (isFromClick) {
                int i;
                boolean isShowing = this.mFilterView.getVisibility() == 0;
                if (isShowing) {
                    z = false;
                } else {
                    z = true;
                }
                controllFilterView(z);
                ImageView imageView = this.mFilterImageView;
                if (isShowing) {
                    i = 2130837805;
                } else {
                    i = 2130837806;
                }
                imageView.setImageResource(i);
                if (isShowing) {
                    setFilterHeader();
                }
            } else if (!this.mIsShowingFilterView) {
                this.mFilterView.setVisibility(0);
                this.mFilterImageView.setImageResource(2130837806);
            }
            if (!(this.mIsShowingFilterView || BaseTypeUtils.isListEmpty(this.mFilterView.getChannelSiftKVPs()) || this.mRequestSiftedOrDolbyData == null)) {
                if (this.mSiftKYPs != null) {
                    this.mFilterView.getChannelSiftKVPs().addAll(this.mSiftKYPs);
                }
                this.mRequestSiftedOrDolbyData.setSiftKvps(this.mFilterView.getChannelSiftKVPs());
                this.mRequestSiftedOrDolbyData.getChannelListAfterSift(false);
                this.mRequestFilter = true;
            }
            if (this.mRequestFilter) {
                loading();
            }
            this.mIsShowingFilterView = true;
        }
    }

    private boolean isFilterTypeNull() {
        if (this.mChannelFilterItemTypes != null && !BaseTypeUtils.isListEmpty(this.mChannelFilterItemTypes.channelFilterList)) {
            return false;
        }
        this.mFilterImageView.setVisibility(8);
        return true;
    }

    private synchronized void updateSiftView(ChannelFilterTypes result) {
        if (this.isFilterButtonSelect) {
            finishLoading();
        }
        if (this.mFilterView == null || result == null || result.mFilterItemList == null || result.mFilterItemList.get(this.mChannel.id) == null) {
            if (this.isFilter) {
                showDataError();
            }
            this.mFilterImageView.setVisibility(8);
        } else {
            this.mChannelFilterItemTypes = getFilterViewItems((ChannelFilterItemType) result.mFilterItemList.get(this.mChannel.id));
            if (!isFilterTypeNull()) {
                this.mFilterView.clear();
                this.mFilterView.setData(this.mChannelFilterItemTypes, this.mCurrentSiftKYPs);
                this.mFilterView.setFilterViewItems();
                if (this.isFilterButtonSelect) {
                    doFilterView(false);
                }
            }
        }
    }

    public ChannelFilterItemType getFilterViewItems(ChannelFilterItemType mFilterItemType) {
        if (mFilterItemType == null) {
            return null;
        }
        ChannelFilterItemType channelFilterItemType = new ChannelFilterItemType();
        ArrayList<FilterBean> channelFilterList = new ArrayList();
        int count = mFilterItemType.getCount();
        for (int i = 0; i < count; i++) {
            boolean isMatching = false;
            if (BaseTypeUtils.getElementFromList(mFilterItemType.channelFilterList, i) != null) {
                FilterBean filterBean = (FilterBean) mFilterItemType.channelFilterList.get(i);
                ArrayList<SiftKVP> channelSubList = filterBean.arrayList;
                if (!(BaseTypeUtils.isListEmpty(channelSubList) || BaseTypeUtils.isListEmpty(this.mSiftKYPs))) {
                    for (int j = 0; j < channelSubList.size(); j++) {
                        Iterator it = this.mSiftKYPs.iterator();
                        while (it.hasNext()) {
                            SiftKVP siftKTP = (SiftKVP) it.next();
                            String filterKey = "";
                            if (!TextUtils.isEmpty(siftKTP.filterKey)) {
                                if (TextUtils.equals(siftKTP.filterKey, "pt/141003")) {
                                    filterKey = "ispay/1";
                                } else if (TextUtils.equals(siftKTP.filterKey, "pt/-141003")) {
                                    filterKey = "ispay/0";
                                } else if (TextUtils.equals(siftKTP.filterKey, "pt/141003,-141003") || TextUtils.equals(siftKTP.filterKey, "pt/-141003,141003")) {
                                    filterKey = "ispay/0,1";
                                } else {
                                    filterKey = siftKTP.filterKey;
                                }
                            }
                            if (TextUtils.equals(((SiftKVP) channelSubList.get(j)).filterKey, filterKey)) {
                                isMatching = true;
                                break;
                            }
                        }
                    }
                }
                if (!isMatching) {
                    channelFilterList.add(filterBean);
                }
            }
        }
        channelFilterItemType.channelFilterList = channelFilterList;
        if (BaseTypeUtils.isListEmpty(channelFilterItemType.channelFilterList)) {
            return null;
        }
        return channelFilterItemType;
    }

    private void doFilterSelect() {
        String selections = "";
        if (!(BaseTypeUtils.isListEmpty(this.mFilterView.getChannelSiftKVPs()) || this.mRequestSiftedOrDolbyData == null)) {
            this.mIsShowingFilterView = true;
            if (!(this.mSiftKYPs == null || this.mFilterView.getChannelSiftKVPs().containsAll(this.mSiftKYPs))) {
                this.mFilterView.getChannelSiftKVPs().addAll(this.mSiftKYPs);
            }
            this.mRequestSiftedOrDolbyData.setSiftKvps(this.mFilterView.getChannelSiftKVPs());
            this.mRequestSiftedOrDolbyData.getChannelListAfterSift(false);
            this.mRequestSiftedOrDolbyData.setStartPosition(0);
            this.mCurrentSiftKYPs = this.mFilterView.getChannelSiftKVPs();
            if (this.mChannelFilterItemTypes != null) {
                finishLoading();
                StringBuilder sb = new StringBuilder();
                ArrayList<FilterBean> filterBeans = this.mChannelFilterItemTypes.channelFilterList;
                if (!(BaseTypeUtils.isListEmpty(filterBeans) || this.mCurrentSiftKYPs == null || filterBeans.size() != this.mCurrentSiftKYPs.size())) {
                    int i = 0;
                    while (i < this.mCurrentSiftKYPs.size()) {
                        if (!(BaseTypeUtils.getElementFromList(filterBeans, i) == null || BaseTypeUtils.getElementFromList(this.mCurrentSiftKYPs, i) == null)) {
                            sb.append(((FilterBean) filterBeans.get(i)).name + com.letv.pp.utils.NetworkUtils.DELIMITER_COLON + ((SiftKVP) this.mCurrentSiftKYPs.get(i)).key + ";");
                        }
                        i++;
                    }
                }
                selections = sb.toString();
                if (!TextUtils.isEmpty(selections)) {
                    selections = selections.substring(0, selections.length() - 1);
                }
            }
        }
        controllFilterView(false);
        this.mFilterImageView.setImageResource(2130837805);
        StringBuilder ap = new StringBuilder();
        ap.append("fl=ft01&wz=1&pageid=").append(PageIdConstant.filterPage);
        ap.append("&ft=").append(selections);
        DataStatistics.getInstance().sendActionInfo(getActivity(), "0", "0", LetvUtils.getPcode(), "0", ap.toString(), "0", String.valueOf(this.mChannel.id), null, null, LetvUtils.getUID(), null, null, null, null, PreferencesManager.getInstance().isLogin() ? 0 : 1, this.mChannel.pageid);
    }

    private void setSiftKYP(ArrayList<SiftKVP> siftKVPTmps) {
        if (siftKVPTmps != null) {
            if (this.mSiftKYPs == null) {
                this.mSiftKYPs = new ArrayList();
            }
            this.mSiftKYPs.clear();
            this.mSiftKYPs.addAll(siftKVPTmps);
            getFilterViewItems(this.mChannelFilterItemTypes);
        }
    }

    private boolean canRefrenceByPageId() {
        if ((this.mCanRefrence || (this.mChannelHomeBean != null && !BaseTypeUtils.isListEmpty(this.mChannelHomeBean.block) && this.mChannelHomeBean.block.size() <= 1 && BaseTypeUtils.getElementFromList(this.mChannelHomeBean.block, 0) != null && TextUtils.equals(((HomeBlock) this.mChannelHomeBean.block.get(0)).isPage, "1") && !TextUtils.equals(((HomeBlock) this.mChannelHomeBean.block.get(0)).contentStyle, "15"))) && !BaseTypeUtils.isListEmpty(((HomeBlock) this.mChannelHomeBean.block.get(0)).list) && ((HomeBlock) this.mChannelHomeBean.block.get(0)).list.size() >= ((HomeBlock) this.mChannelHomeBean.block.get(0)).num && BaseTypeUtils.getElementFromList(((HomeBlock) this.mChannelHomeBean.block.get(0)).list, 10) != null) {
            return true;
        }
        this.mFootViewUtil.removeFootView();
        return false;
    }

    private void doLoadingMore() {
        if (isNetworkAvailable()) {
            if (!this.isFilter || this.mDetailListAdapter == null) {
                if (this.mListAdapter == null) {
                    this.mFootViewUtil.removeFootView();
                    return;
                } else if (canRefrenceByPageId()) {
                    if (this.mListAdapter.getChildrenCount(0) * 2 >= ((this.mPageSize - 1) * 30) + this.mChanelDetailtask.getFirstPageDataSize()) {
                        this.mCanRefrence = true;
                        this.mFootViewUtil.showLoadingView();
                        this.mIsLoadingMore = true;
                        this.mPageSize++;
                        this.mChanelDetailtask.requestChannelDetailList(false, true, this.mPageSize, this.mPageCardList);
                    } else if (this.mListAdapter.getChildrenCount(0) * 2 <= 10) {
                        this.mFootViewUtil.removeFootView();
                        return;
                    } else {
                        this.mFootViewUtil.showFootNoMore();
                        return;
                    }
                } else {
                    return;
                }
            } else if (this.mDetailListAdapter.getChildrenCount(0) * 2 < this.mRequestSiftedOrDolbyData.getCurrentMaxCount()) {
                this.mIsLoadingMore = true;
                this.mFootViewUtil.showLoadingView();
                this.mRequestSiftedOrDolbyData.setStartPosition((this.mDetailListAdapter.getChildrenCount(0) * 2) + 1);
                this.mRequestSiftedOrDolbyData.getChannelListAfterSift(true);
            } else if (this.mDetailListAdapter != null) {
                if (this.mDetailListAdapter.getChildrenCount(0) * 2 <= 10) {
                    this.mFootViewUtil.removeFootView();
                    return;
                } else {
                    this.mFootViewUtil.showFootNoMore();
                    return;
                }
            }
            StatisticsUtils.staticticsInfoPost(this.mContext, CategoryCode.CHANNEL_CONTENT_HOME_BLOCK, this.mChannel.name, 0, -1, this.mChannel.id + "", null, null, null, null);
            return;
        }
        this.mFootViewUtil.showFootError();
    }

    private void controllFilterView(boolean isShow) {
        if (isShow && this.mFilterView.getVisibility() == 8) {
            this.mFilterView.setVisibility(0);
            TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, (float) (-UIsUtils.getScreenHeight()), 0.0f);
            animation.setDuration(200);
            animation.setFillAfter(true);
            this.mFilterView.setAnimation(animation);
            animation.setAnimationListener(new 7(this));
            this.mFilterView.startAnimation(animation);
        }
        if (!isShow && this.mFilterView.getVisibility() == 0) {
            this.mFilterView.setVisibility(8);
            TranslateAnimation animation1 = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-UIsUtils.getScreenHeight()));
            animation1.setDuration(200);
            animation1.setFillAfter(true);
            this.mFilterView.setAnimation(animation1);
            animation1.setAnimationListener(new 8(this));
            this.mFilterView.startAnimation(animation1);
        }
    }

    public String getTagName() {
        return "item_" + this.mChannel.id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mChannel.pageid;
    }

    public int getDisappearFlag() {
        return 1;
    }

    public int getContainerId() {
        return 0;
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDestroy() {
        super.onDestroy();
        clear();
    }

    private void clear() {
        try {
            this.mIsShowingFilterView = false;
            this.mCanRefrence = false;
            if (this.mChanelDetailtask != null) {
                this.mChanelDetailtask.onDestroy();
                this.mChanelDetailtask = null;
            }
            if (this.mFilterView != null) {
                this.mFilterView.clear();
                this.mFilterView.setVisibility(8);
            }
            if (this.mSiftKYPs != null) {
                this.mSiftKYPs.clear();
                this.mSiftKYPs = null;
            }
            if (this.mCurrentSiftKYPs != null) {
                this.mCurrentSiftKYPs.clear();
                this.mCurrentSiftKYPs = null;
            }
            if (this.mRequestSiftedOrDolbyData != null) {
                this.mRequestSiftedOrDolbyData.clear();
                this.mRequestSiftedOrDolbyData = null;
            }
            if (this.mChannelHomeBean != null) {
                this.mChannelHomeBean.clear();
                this.mChannelHomeBean = null;
            }
            if (this.mListAdapter != null) {
                this.mListAdapter.destroyData();
                this.mListAdapter = null;
            }
            this.mFootViewUtil = null;
            this.mHasInitCursor = false;
            clearHead();
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CurrentPage getCurrentPage() {
        return CurrentPage.CHANNEL;
    }

    public ChannelDetailExpandableListAdapter getAdapter() {
        return this.mListAdapter;
    }
}
