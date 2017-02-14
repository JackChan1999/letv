package com.letv.android.client.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import com.letv.android.client.activity.ChannelDetailItemActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.adapter.channel.ChannelDetailExpandableListAdapter;
import com.letv.android.client.adapter.channel.ChannelDetailListAdapter;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.android.client.commonlib.utils.UIControllerUtils;
import com.letv.android.client.episode.callback.ChanneDetailFragmentCallBack;
import com.letv.android.client.listener.ChannelDetailCallback;
import com.letv.android.client.task.ChannelDetailTask;
import com.letv.android.client.task.RequestSiftedOrDolbyDatas;
import com.letv.android.client.task.RequestSiftedOrDolbyDatas.RequestSiftedOrDolbyDatasCallBack;
import com.letv.android.client.utils.CursorLoader;
import com.letv.android.client.view.channel.ChannelTabsView;
import com.letv.android.client.view.channel.ChannelVipTipsView;
import com.letv.core.bean.ChannelListBean.Channel;
import com.letv.core.bean.HomeBlock;
import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.SiftKVP;
import com.letv.core.bean.channel.AlbumNewList;
import com.letv.core.bean.channel.ChannelHomeBean;
import com.letv.core.bean.channel.ChannelNavigation;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.db.LetvContentProvider;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ChannelBaseFragment extends HomeBaseFragment implements ChannelDetailCallback, RequestSiftedOrDolbyDatasCallBack, ChanneDetailFragmentCallBack, LoaderCallbacks<Cursor> {
    protected ChannelDetailTask mChanelDetailtask;
    protected Channel mChannel;
    protected ChannelHomeBean mChannelHomeBean;
    protected ChannelDetailListAdapter mDolbyListAdapter;
    protected boolean mHasInitCursor;
    protected RequestSiftedOrDolbyDatas mRequestSiftedOrDolbyData;
    protected ChannelTabsView mTabsView;
    private ChannelVipTipsView mVipTipsView;

    abstract ChannelDetailExpandableListAdapter getAdapter();

    abstract void moreSelect(int i, ArrayList<SiftKVP> arrayList, String str);

    public ChannelBaseFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public ChannelVipTipsView getVipTipsView() {
        return this.mVipTipsView;
    }

    protected void initTabView(ChannelHomeBean homeBean) {
        if (homeBean != null) {
            if (this.mTabsView == null) {
                this.mTabsView = new ChannelTabsView(this.mContext);
                this.mTabsView.showView(true);
                ((ExpandableListView) this.mPullView.getRefreshableView()).addHeaderView(this.mTabsView.getView());
            }
            this.mChannelHomeBean = homeBean;
            setTabsView();
        }
    }

    private void setTabsView() {
        try {
            if (this.mChannelHomeBean == null || BaseTypeUtils.getElementFromList(this.mChannelHomeBean.block, this.mChannelHomeBean.tabIndex) == null) {
                removeTabView();
                return;
            }
            HomeBlock block = (HomeBlock) this.mChannelHomeBean.block.get(this.mChannelHomeBean.tabIndex);
            Iterator it = block.tabsNavigation.iterator();
            while (it.hasNext()) {
                ChannelNavigation navigation = (ChannelNavigation) it.next();
                navigation.reid = block.reid;
                navigation.area = block.area;
                navigation.bucket = block.bucket;
            }
            this.mTabsView.setTabs(block.tabsNavigation, this.mChannel);
            if (this.mTabsView.getmGridView() != null && (this.mContext instanceof MainActivity)) {
                this.mTabsView.getmGridView().setFootView(((MainActivity) this.mContext).getHomeFragment().getViewPager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initLiveCursorLoader() {
        if (!this.mHasInitCursor) {
            this.mHasInitCursor = true;
            getLoaderManager().initLoader(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID, null, this);
        }
    }

    public void onLiveListSuccess(LiveRemenListBean result) {
        if (getAdapter() instanceof ChannelDetailExpandableListAdapter) {
            getAdapter().setLiveData(result);
        }
        initLiveCursorLoader();
        finishLoading();
    }

    protected void removeTabView() {
        if (this.mTabsView != null && ((ExpandableListView) this.mPullView.getRefreshableView()).getHeaderViewsCount() > 1) {
            ((ExpandableListView) this.mPullView.getRefreshableView()).removeHeaderView(this.mTabsView.getView());
            this.mTabsView = null;
        }
    }

    protected void clearHead() {
        removeTabView();
        removeFocusView();
        removeVipTipView();
    }

    public void updateUI(AlbumNewList result, boolean isFromCache) {
        refreshComplete();
        finishLoading();
        if (!BaseTypeUtils.isListEmpty(result) && this.mRequestSiftedOrDolbyData != null) {
            if (this.mDolbyListAdapter == null) {
                this.mDolbyListAdapter = new ChannelDetailListAdapter(this.mContext, this.mChannel.id);
                this.mDolbyListAdapter.setPageCardList(this.mPageCardList);
                ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mDolbyListAdapter);
            }
            if (this.mRequestSiftedOrDolbyData.isLoadingMore()) {
                this.mDolbyListAdapter.addList(result);
            } else {
                ((ExpandableListView) this.mPullView.getRefreshableView()).setAdapter(this.mDolbyListAdapter);
                this.mDolbyListAdapter.setList((ExpandableListView) this.mPullView.getRefreshableView(), result);
            }
            if (this.mRequestSiftedOrDolbyData.isDolby() && !PreferencesManager.getInstance().getDoublySwitch()) {
                addDolbyTextView();
            }
        }
    }

    private void addDolbyTextView() {
        if (this.mDoblyTextView != null) {
            if (this.mRequestSiftedOrDolbyData.isDolby()) {
                if (!PreferencesManager.getInstance().getDoublySwitch()) {
                    this.mDoblyTextView.setVisibility(0);
                }
                this.mDoblyTextView.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ ChannelBaseFragment this$0;

                    {
                        if (HotFix.PREVENT_VERIFY) {
                            System.out.println(VerifyLoad.class);
                        }
                        this.this$0 = this$0;
                    }

                    public void onClick(View v) {
                        v.setVisibility(8);
                        PreferencesManager.getInstance().setDoublySwitch(true);
                    }
                });
                return;
            }
            this.mDoblyTextView.setVisibility(8);
        }
    }

    protected void setVipTipsView() {
        try {
            if (this.mVipTipsView == null) {
                this.mVipTipsView = new ChannelVipTipsView(this.mContext);
                ((ExpandableListView) this.mPullView.getRefreshableView()).addHeaderView(this.mVipTipsView.getView());
            }
            this.mVipTipsView.getView().setVisibility(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void removeVipTipView() {
        if (this.mVipTipsView != null && ((ExpandableListView) this.mPullView.getRefreshableView()).getHeaderViewsCount() > 0) {
            ((ExpandableListView) this.mPullView.getRefreshableView()).removeHeaderView(this.mVipTipsView.getView());
            this.mVipTipsView = null;
        }
    }

    public void callBack(ArrayList<SiftKVP> siftKVPTmps, String name) {
        moreSelect(1, siftKVPTmps, name);
        if (siftKVPTmps != null) {
            if (this.mContext instanceof MainActivity) {
                UIControllerUtils.gotoChannelDetailItemActivity(this.mContext, this.mChannel, true, null, siftKVPTmps, name);
            } else if (this.mRequestSiftedOrDolbyData != null) {
                clearHead();
                this.mRequestSiftedOrDolbyData.setSiftKvps(siftKVPTmps);
                this.mRequestSiftedOrDolbyData.getChannelListAfterSift(false);
                loading();
            }
        }
    }

    public void callBack(HomeBlock channelHomeBlock, int toWhere) {
        if (toWhere == 2) {
            if (channelHomeBlock != null) {
                String redirectPageId = channelHomeBlock.redirectPageId;
                ChannelNavigation navigations = new ChannelNavigation();
                navigations.pageid = redirectPageId;
                navigations.nameCn = channelHomeBlock.blockname;
                navigations.reid = channelHomeBlock.reid;
                navigations.area = channelHomeBlock.area;
                navigations.bucket = channelHomeBlock.bucket;
                if (this.mContext instanceof MainActivity) {
                    UIControllerUtils.gotoChannelDetailItemActivity(this.mContext, this.mChannel, false, navigations, getSiftKVP(channelHomeBlock), null);
                } else if ((this.mContext instanceof ChannelDetailItemActivity) && this.mChanelDetailtask != null) {
                    clearHead();
                    this.mChanelDetailtask.reSetPageId(redirectPageId);
                    this.mChanelDetailtask.requestChannelDetailList(false, false, 1, this.mPageCardList);
                    loading();
                }
                moreSelect(2, getSiftKVP(channelHomeBlock), channelHomeBlock.blockname);
            }
        } else if (toWhere == 3) {
            new LetvWebViewActivityConfig(this.mContext).launch(BaseTypeUtils.checkUrl(channelHomeBlock.redirectUrl), channelHomeBlock.blockname);
        }
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle cursor) {
        return new CursorLoader(this.mContext, LetvContentProvider.URI_LIVEBOOKTRACE, null, null, null, null);
    }

    public void setData(Channel channel) {
        this.mChannel = channel;
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
            if ((getAdapter() instanceof ChannelDetailExpandableListAdapter) && getAdapter().getChannelLivehallView() != null) {
                getAdapter().getChannelLivehallView().setBookedPrograms(mBookedPrograms);
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        if ((getAdapter() instanceof ChannelDetailExpandableListAdapter) && getAdapter().getChannelLivehallView() != null) {
            getAdapter().getChannelLivehallView().clearBookedPrograms();
        }
    }

    private ArrayList<SiftKVP> getSiftKVP(HomeBlock channelHomeBlock) {
        if (channelHomeBlock == null) {
            return null;
        }
        return LetvUtils.getStringSKfList(channelHomeBlock.redField);
    }
}
