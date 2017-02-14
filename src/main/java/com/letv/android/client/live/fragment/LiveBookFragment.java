package com.letv.android.client.live.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.letv.android.client.R;
import com.letv.android.client.adapter.LiveBookLeftDateAdapter;
import com.letv.android.client.adapter.LiveBookSectionedAdapter;
import com.letv.android.client.commonlib.fragement.LetvBaseFragment;
import com.letv.android.client.commonlib.view.PinnedHeaderListView;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.android.client.utils.CursorLoader;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.LiveBookList;
import com.letv.core.constant.DatabaseConstant.LiveBookTrace.Field;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.db.LetvContentProvider;
import com.letv.core.network.volley.Volley;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.LiveBookParse;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.HashSet;
import java.util.Set;

public class LiveBookFragment extends LetvBaseFragment implements OnRefreshListener, LoaderCallbacks<Cursor> {
    private final String TAG;
    private LiveBookSectionedAdapter mAdapter;
    private boolean mIsScroll;
    private LiveBookLeftDateAdapter mLeftDateAdapter;
    private ListView mLeftListView;
    private LiveBookList mLiveBookList;
    private PinnedHeaderListView mRightListView;
    private PublicLoadLayout mRoot;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mType;

    public LiveBookFragment() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.TAG = LiveBookFragment.class.getName();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mRoot = PublicLoadLayout.createPage(this.mContext, (int) R.layout.fragment_live_book_layout, true);
        Bundle bundle = getArguments();
        if (bundle != null) {
            this.mType = bundle.getString("name");
        }
        return this.mRoot;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loading();
        findViewAndInit();
        requestData(false);
    }

    private void findViewAndInit() {
        this.mAdapter = new LiveBookSectionedAdapter(this.mContext);
        this.mLeftDateAdapter = new LiveBookLeftDateAdapter(this.mContext);
        this.mSwipeRefreshLayout = (SwipeRefreshLayout) this.mRoot.findViewById(R.id.swipe_container);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.mSwipeRefreshLayout.setColorSchemeColors(2131493042);
        this.mLeftListView = (ListView) this.mRoot.findViewById(R.id.left_date_list);
        this.mLeftListView.bringToFront();
        this.mLeftListView.setOnItemClickListener(new 1(this));
        this.mRightListView = (PinnedHeaderListView) this.mRoot.findViewById(R.id.right_data_list);
        this.mRightListView.setOnScrollListener(new 2(this));
        this.mRoot.setRefreshData(new 3(this));
        this.mRightListView.setAdapter(this.mAdapter);
        this.mLeftListView.setAdapter(this.mLeftDateAdapter);
        getLoaderManager().initLoader(LiveRoomConstant.LIVE_ROOM_LOADER_BOOK_ID, null, this);
    }

    public void onRefresh() {
        if (this.mSwipeRefreshLayout != null) {
            requestData(true);
        }
    }

    private void requestData(boolean isRefresh) {
        Volley.getQueue().cancelWithTag(this.TAG + this.mType);
        new LetvRequest().setUrl(LetvUrlMaker.getLiveBookUrl(this.mType)).setRequestType(RequestManner.NETWORK_ONLY).setParser(new LiveBookParse()).setCache(new VolleyDiskCache(this.TAG + this.mType)).setTag(this.TAG + this.mType).setCallback(new 4(this, isRefresh)).add();
    }

    private void refreshView(LiveBookList list) {
        finishLoading();
        if (list != null) {
            this.mLeftDateAdapter.setList(list.mListdate);
            this.mAdapter.setData(list);
        }
    }

    private void finishRefresh(boolean isRefresh) {
        if (isRefresh && this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void setDataError() {
        if (this.mRoot != null) {
            this.mRoot.refreshLiveBookError(2131101571);
        }
    }

    private void setNetError() {
        if (this.mRoot != null) {
            this.mRoot.netError(false);
        }
    }

    private void finishLoading() {
        if (this.mRoot != null) {
            this.mRoot.finish();
        }
    }

    private void loading() {
        if (this.mRoot != null) {
            this.mRoot.loading(false);
        }
    }

    public void onDestroy() {
        Volley.getQueue().cancelWithTag(this.TAG + this.mType);
        if (this.mRoot != null) {
            this.mRoot.removeAllViews();
            this.mRoot = null;
        }
        if (this.mSwipeRefreshLayout != null) {
            this.mSwipeRefreshLayout.removeAllViews();
            this.mSwipeRefreshLayout = null;
        }
        super.onDestroy();
    }

    public Loader<Cursor> onCreateLoader(int arg0, Bundle cursor) {
        return new CursorLoader(this.mContext, LetvContentProvider.URI_LIVEBOOKTRACE, null, null, null, null);
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
            if (this.mAdapter != null) {
                this.mAdapter.setBookedPrograms(mBookedPrograms);
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        if (this.mAdapter != null) {
            this.mAdapter.clearBookedPrograms();
        }
    }

    public String getTagName() {
        return null;
    }

    public int getContainerId() {
        return 0;
    }
}
