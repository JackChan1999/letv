package com.letv.mobile.lebox.view.pulltorefresh;

import android.annotation.TargetApi;
import android.util.Log;
import android.view.View;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.Mode;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.State;

@TargetApi(9)
public final class OverscrollHelper {
    static final float DEFAULT_OVERSCROLL_SCALE = 1.0f;
    static final String LOG_TAG = "OverscrollHelper";

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, boolean isTouchEvent) {
        overScrollBy(view, deltaX, scrollX, deltaY, scrollY, 0, isTouchEvent);
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, int scrollRange, boolean isTouchEvent) {
        overScrollBy(view, deltaX, scrollX, deltaY, scrollY, scrollRange, 0, DEFAULT_OVERSCROLL_SCALE, isTouchEvent);
    }

    public static void overScrollBy(PullToRefreshBase<?> view, int deltaX, int scrollX, int deltaY, int scrollY, int scrollRange, int fuzzyThreshold, float scaleFactor, boolean isTouchEvent) {
        int deltaValue;
        int scrollValue;
        int currentScrollValue;
        switch (1.$SwitchMap$com$letv$mobile$lebox$view$pulltorefresh$PullToRefreshBase$Orientation[view.getPullToRefreshScrollDirection().ordinal()]) {
            case 1:
                deltaValue = deltaX;
                scrollValue = scrollX;
                currentScrollValue = view.getScrollX();
                break;
            default:
                deltaValue = deltaY;
                scrollValue = scrollY;
                currentScrollValue = view.getScrollY();
                break;
        }
        if (view.isPullToRefreshOverScrollEnabled() && !view.isRefreshing()) {
            Mode mode = view.getMode();
            if (mode.permitsPullToRefresh() && !isTouchEvent && deltaValue != 0) {
                int newScrollValue = deltaValue + scrollValue;
                Log.d(LOG_TAG, "OverScroll. DeltaX: " + deltaX + ", ScrollX: " + scrollX + ", DeltaY: " + deltaY + ", ScrollY: " + scrollY + ", NewY: " + newScrollValue + ", ScrollRange: " + scrollRange + ", CurrentScroll: " + currentScrollValue);
                if (newScrollValue < 0 - fuzzyThreshold) {
                    if (mode.showHeaderLoadingLayout()) {
                        if (currentScrollValue == 0) {
                            view.setState(State.OVERSCROLLING, new boolean[0]);
                        }
                        view.setHeaderScroll((int) (((float) (currentScrollValue + newScrollValue)) * scaleFactor));
                    }
                } else if (newScrollValue > scrollRange + fuzzyThreshold) {
                    if (mode.showFooterLoadingLayout()) {
                        if (currentScrollValue == 0) {
                            view.setState(State.OVERSCROLLING, new boolean[0]);
                        }
                        view.setHeaderScroll((int) (((float) ((currentScrollValue + newScrollValue) - scrollRange)) * scaleFactor));
                    }
                } else if (Math.abs(newScrollValue) <= fuzzyThreshold || Math.abs(newScrollValue - scrollRange) <= fuzzyThreshold) {
                    view.setState(State.RESET, new boolean[0]);
                }
            } else if (isTouchEvent && State.OVERSCROLLING == view.getState()) {
                view.setState(State.RESET, new boolean[0]);
            }
        }
    }

    static boolean isAndroidOverScrollEnabled(View view) {
        return view.getOverScrollMode() != 2;
    }
}
