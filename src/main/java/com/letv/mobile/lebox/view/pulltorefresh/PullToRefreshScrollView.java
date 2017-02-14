package com.letv.mobile.lebox.view.pulltorefresh;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.AnimationStyle;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.Mode;
import com.letv.mobile.lebox.view.pulltorefresh.PullToRefreshBase.Orientation;

public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {
    public PullToRefreshScrollView(Context context) {
        super(context);
    }

    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshScrollView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshScrollView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
        ScrollView scrollView;
        if (VERSION.SDK_INT >= 9) {
            scrollView = new InternalScrollViewSDK9(this, context, attrs);
        } else {
            scrollView = new ScrollView(context, attrs);
        }
        scrollView.setId(R.id.scrollview);
        return scrollView;
    }

    protected boolean isReadyForPullStart() {
        return ((ScrollView) this.mRefreshableView).getScrollY() == 0;
    }

    protected boolean isReadyForPullEnd() {
        View scrollViewChild = ((ScrollView) this.mRefreshableView).getChildAt(0);
        if (scrollViewChild == null) {
            return false;
        }
        if (((ScrollView) this.mRefreshableView).getScrollY() >= scrollViewChild.getHeight() - getHeight()) {
            return true;
        }
        return false;
    }
}
