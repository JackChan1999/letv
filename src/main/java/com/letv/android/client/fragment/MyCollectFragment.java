package com.letv.android.client.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.activity.MyCollectActivity;
import com.letv.android.client.adapter.MyCollectListAdapter;
import com.letv.android.client.adapter.MyCollectListAdapter.OnDeleteListener;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.utils.FootViewUtil;
import com.letv.android.client.view.PullToRefreshBase.OnLastItemVisibleListener;
import com.letv.android.client.view.PullToRefreshBase.OnRefreshListener;
import com.letv.android.client.view.PullToRefreshListView;
import com.letv.core.bean.FavouriteBeanList;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.DBManager;
import com.letv.core.db.FavoriteTraceHandler;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

@SuppressLint({"ValidFragment"})
public class MyCollectFragment extends MyBaseFragment implements OnDeleteListener {
    private int curPage;
    private FavouriteLoginState favouriteLoginState;
    private VolleyRequest getFavouriteRequest;
    private MyCollectListAdapter mAdapter;
    private FootViewUtil mFootViewUtil;
    private ListView mListview;
    private LinearLayout mNullTip;
    private PublicLoadLayout mRootView;
    private TextView nullSubTitle;
    private TextView nullTitle;
    private OnClickListener onFootClickListener;
    private OnLastItemVisibleListener onLastItemVisibleListener;
    private OnRefreshListener onRefreshListener;
    private int pageSize;
    private FavoriteTraceHandler playRecordHandler;
    private PullToRefreshListView pullToRefreshListView;
    private int total;
    private VolleyRequest uploadFavouriteRequest;

    public MyCollectFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.favouriteLoginState = new FavouriteOffLine(this, null);
        this.playRecordHandler = DBManager.getInstance().getFavoriteTrace();
        this.curPage = 1;
        this.pageSize = 20;
        this.total = 0;
        this.onFootClickListener = new 2(this);
        this.onLastItemVisibleListener = new 3(this);
        this.onRefreshListener = new 4(this);
    }

    public void onStart() {
        super.onStart();
        updateFavouriteLoginState();
        this.favouriteLoginState.onStart();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRootView = PublicLoadLayout.createPage(this.mContext, inflater.inflate(R.layout.fragment_my_collect, container, false));
        this.mRootView.setRefreshData(new 1(this));
        findView();
        return this.mRootView;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.mAdapter == null) {
            this.mAdapter = new MyCollectListAdapter(getActivity(), this);
            this.mListview.setEmptyView(this.mNullTip);
            this.mListview.setAdapter(this.mAdapter);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.uploadFavouriteRequest != null) {
            this.uploadFavouriteRequest.cancel();
        }
        if (this.getFavouriteRequest != null) {
            this.getFavouriteRequest.cancel();
        }
    }

    private void findView() {
        this.pullToRefreshListView = (PullToRefreshListView) this.mRootView.findViewById(R.id.my_collect_listview);
        this.mListview = (ListView) this.pullToRefreshListView.getRefreshableView();
        this.mNullTip = (LinearLayout) this.mRootView.findViewById(R.id.my_collect_error_tip);
        this.nullTitle = (TextView) this.mRootView.findViewById(R.id.my_collect_null_title);
        this.nullSubTitle = (TextView) this.mRootView.findViewById(R.id.my_collect_null_subtitle);
        this.mFootViewUtil = new FootViewUtil(this.pullToRefreshListView);
        this.mFootViewUtil.getChannelListFootView().setOnClickListener(this.onFootClickListener);
        this.pullToRefreshListView.setOnLastItemVisibleListener(this.onLastItemVisibleListener);
        this.pullToRefreshListView.setOnRefreshListener(this.onRefreshListener);
        this.pullToRefreshListView.setParams(Boolean.valueOf(true), "MyCollectFragment");
        this.nullTitle.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_FAVORITE_CONTENT_NULL_TITLE, 2131100971));
        this.nullSubTitle.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_FAVORITE_CONTENT_NULL_SUBTITLE, 2131100972));
    }

    private void requestTopPageFavouriteList() {
        this.curPage = 1;
        this.pullToRefreshListView.setRefreshing();
        this.mRootView.loading(false);
        requestGetFavouriteList();
    }

    private void upLoadThenPullData() {
        this.uploadFavouriteRequest = this.playRecordHandler.requestPostFavourite(new 5(this));
    }

    private boolean isNoMore() {
        return this.mAdapter.getCount() == this.total;
    }

    private void requestGetFavouriteList() {
        this.getFavouriteRequest = this.playRecordHandler.requestGetFavouriteList(this.curPage, this.pageSize, new 6(this));
    }

    private void updateDatalist(FavouriteBeanList list) {
        this.mAdapter.setList(list);
        if (getActivity() != null) {
            ((MyCollectActivity) getActivity()).showEditView(list.size() > 0);
        }
    }

    private void addLocalFavourite() {
        FavouriteBeanList dbFavList = this.playRecordHandler.getAllFavoriteTrace();
        if (dbFavList != null && dbFavList.size() >= 0) {
            updateDatalist(dbFavList);
        }
    }

    public void changeBottomPadding() {
        if (this.mRootView != null) {
            int offset;
            if (((MyCollectActivity) getActivity()).isEditing()) {
                offset = UIsUtils.dipToPx(50.0f);
            } else {
                offset = 0;
            }
            this.mRootView.setPadding(0, 0, 0, offset);
        }
    }

    public void setPullToRefreshEnabled(boolean enable) {
        if (this.pullToRefreshListView != null) {
            this.pullToRefreshListView.setPullToRefreshEnabled(enable);
        }
    }

    public MyCollectListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void onViewPageScrollChangeEvent() {
    }

    public String getTagName() {
        return null;
    }

    public int getContainerId() {
        return 0;
    }

    public void onDeleteAll() {
        this.favouriteLoginState.onDeleteAll();
        if (getActivity() != null) {
            ((MyCollectActivity) getActivity()).resetEditState();
        }
    }

    public void onDeleteSelected() {
        this.favouriteLoginState.onDeleteSelected();
    }

    private void updateFavouriteLoginState() {
        if (PreferencesManager.getInstance().isLogin()) {
            this.favouriteLoginState = new FavouriteOnLine(this, null);
        } else {
            this.favouriteLoginState = new FavouriteOffLine(this, null);
        }
    }
}
