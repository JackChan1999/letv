package com.letv.android.client.view.pullzoom;

public interface ObservableScrollViewCallbacks {
    void onDownMotionEvent();

    void onScrollChanged(int i, boolean z, boolean z2);

    void onUpOrCancelMotionEvent(ScrollState scrollState);
}
