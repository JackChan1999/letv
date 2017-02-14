package com.letv.android.client.hot;

import com.letv.android.client.view.PullToRefreshListView;

public interface IHotListChangeListener {
    void onListChanged(PullToRefreshListView pullToRefreshListView, boolean z);

    void onListChanging(PullToRefreshListView pullToRefreshListView);
}
