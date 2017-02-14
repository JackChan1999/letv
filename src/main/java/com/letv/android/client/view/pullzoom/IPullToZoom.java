package com.letv.android.client.view.pullzoom;

import android.content.res.TypedArray;
import android.view.View;

public interface IPullToZoom<T extends View> {
    View getHeaderView();

    T getRootView();

    View getZoomView();

    void handleStyledAttributes(TypedArray typedArray);

    boolean isHideHeader();

    boolean isParallax();

    boolean isPullToZoomEnabled();

    boolean isZooming();
}
