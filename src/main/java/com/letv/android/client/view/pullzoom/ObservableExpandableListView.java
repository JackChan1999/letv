package com.letv.android.client.view.pullzoom;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListView;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

public class ObservableExpandableListView extends PullToZoomExpandableListViewEx implements Scrollable {
    private final int SCROLL_REFRENCE_HEIGHT;
    private List<ObservableScrollViewCallbacks> mCallbackCollection;
    private ObservableScrollViewCallbacks mCallbacks;
    private SparseIntArray mChildrenHeights;
    private float mCurrentY;
    private boolean mDragging;
    private boolean mFirstScroll;
    private boolean mFirstVisible;
    private boolean mIntercepted;
    private OnScrollListener mOriginalScrollListener;
    private int mPrevFirstVisibleChildHeight;
    private int mPrevFirstVisiblePosition;
    private MotionEvent mPrevMoveEvent;
    private int mPrevScrollY;
    private int mPrevScrolledChildrenHeight;
    private OnScrollListener mScrollListener;
    private ScrollState mScrollState;
    private int mScrollY;
    private ViewGroup mTouchInterceptionViewGroup;

    public ObservableExpandableListView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mPrevFirstVisibleChildHeight = -1;
        this.SCROLL_REFRENCE_HEIGHT = 200;
        this.mScrollListener = new 1(this);
        init();
    }

    public ObservableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPrevFirstVisibleChildHeight = -1;
        this.SCROLL_REFRENCE_HEIGHT = 200;
        this.mScrollListener = new 1(this);
        init();
    }

    public void onRestoreInstanceState(Parcelable state) {
        LogInfo.log("star", "onRestoreInstanceState...state=" + state);
        if (state == null || !(state instanceof SavedState)) {
            ((Activity) getContext()).finish();
            return;
        }
        SavedState ss = (SavedState) state;
        this.mPrevFirstVisiblePosition = ss.prevFirstVisiblePosition;
        this.mPrevFirstVisibleChildHeight = ss.prevFirstVisibleChildHeight;
        this.mPrevScrolledChildrenHeight = ss.prevScrolledChildrenHeight;
        this.mPrevScrollY = ss.prevScrollY;
        this.mScrollY = ss.scrollY;
        this.mChildrenHeights = ss.childrenHeights;
        super.onRestoreInstanceState(ss.getSuperState());
    }

    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        LogInfo.log("star", "onSaveInstanceState...superState=" + superState);
        SavedState ss = new SavedState(superState);
        ss.prevFirstVisiblePosition = this.mPrevFirstVisiblePosition;
        ss.prevFirstVisibleChildHeight = this.mPrevFirstVisibleChildHeight;
        ss.prevScrolledChildrenHeight = this.mPrevScrolledChildrenHeight;
        ss.prevScrollY = this.mPrevScrollY;
        ss.scrollY = this.mScrollY;
        ss.childrenHeights = this.mChildrenHeights;
        return ss;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!(this.mCallbacks == null && this.mCallbackCollection == null)) {
            switch (ev.getActionMasked()) {
                case 0:
                    this.mDragging = true;
                    this.mFirstScroll = true;
                    this.mCurrentY = ev.getY();
                    dispatchOnDownMotionEvent();
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (!(this.mCallbacks == null && this.mCallbackCollection == null)) {
            switch (ev.getActionMasked()) {
                case 1:
                case 3:
                    this.mIntercepted = false;
                    this.mDragging = false;
                    dispatchOnUpOrCancelMotionEvent(this.mScrollState);
                    if (ev.getY() - this.mCurrentY >= ((float) UIsUtils.dipToPx(200.0f)) && this.mFirstVisible) {
                        dispatchOnUpOrCancelMotionEvent(ScrollState.UP_REFRENCE);
                        break;
                    }
                case 2:
                    if (this.mPrevMoveEvent == null) {
                        this.mPrevMoveEvent = ev;
                    }
                    float diffY = ev.getY() - this.mPrevMoveEvent.getY();
                    this.mPrevMoveEvent = MotionEvent.obtainNoHistory(ev);
                    if (((float) getCurrentScrollY()) - diffY <= 0.0f) {
                        if (this.mIntercepted) {
                            return false;
                        }
                        View parent;
                        if (this.mTouchInterceptionViewGroup == null) {
                            parent = (ViewGroup) getParent();
                        } else {
                            parent = this.mTouchInterceptionViewGroup;
                        }
                        float offsetX = 0.0f;
                        float offsetY = 0.0f;
                        View v = this;
                        while (v != null && v != parent) {
                            offsetX += (float) (v.getLeft() - v.getScrollX());
                            offsetY += (float) (v.getTop() - v.getScrollY());
                            try {
                                v = (View) v.getParent();
                            } catch (ClassCastException e) {
                            }
                        }
                        MotionEvent event = MotionEvent.obtainNoHistory(ev);
                        event.offsetLocation(offsetX, offsetY);
                        if (!parent.onInterceptTouchEvent(event)) {
                            return super.onTouchEvent(ev);
                        }
                        this.mIntercepted = true;
                        event.setAction(0);
                        post(new 2(this, parent, event));
                        return false;
                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mOriginalScrollListener = l;
    }

    public void setScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        this.mCallbacks = listener;
    }

    public void addScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        if (this.mCallbackCollection == null) {
            this.mCallbackCollection = new ArrayList();
        }
        this.mCallbackCollection.add(listener);
    }

    public void removeScrollViewCallbacks(ObservableScrollViewCallbacks listener) {
        if (this.mCallbackCollection != null) {
            this.mCallbackCollection.remove(listener);
        }
    }

    public void clearScrollViewCallbacks() {
        if (this.mCallbackCollection != null) {
            this.mCallbackCollection.clear();
        }
    }

    public void setTouchInterceptionViewGroup(ViewGroup viewGroup) {
        this.mTouchInterceptionViewGroup = viewGroup;
    }

    public void scrollVerticallyTo(int y) {
        View firstVisibleChild = ((ExpandableListView) this.mRootView).getChildAt(0);
        if (firstVisibleChild != null) {
            ((ExpandableListView) this.mRootView).setSelection(y / firstVisibleChild.getHeight());
        }
    }

    public int getCurrentScrollY() {
        return this.mScrollY;
    }

    private void init() {
        this.mChildrenHeights = new SparseIntArray();
        ((ExpandableListView) this.mRootView).setOnScrollListener(this.mScrollListener);
    }

    private void onScrollChanged() {
        if (!(this.mCallbacks == null && this.mCallbackCollection == null) && getChildCount() > 0) {
            int firstVisiblePosition = ((ExpandableListView) this.mRootView).getFirstVisiblePosition();
            int i = ((ExpandableListView) this.mRootView).getFirstVisiblePosition();
            int j = 0;
            while (i <= ((ExpandableListView) this.mRootView).getLastVisiblePosition()) {
                if (this.mChildrenHeights.indexOfKey(i) < 0 || ((ExpandableListView) this.mRootView).getChildAt(j).getHeight() != this.mChildrenHeights.get(i)) {
                    this.mChildrenHeights.put(i, ((ExpandableListView) this.mRootView).getChildAt(j).getHeight());
                }
                i++;
                j++;
            }
            View firstVisibleChild = ((ExpandableListView) this.mRootView).getChildAt(0);
            if (firstVisibleChild != null) {
                int skippedChildrenHeight;
                if (this.mPrevFirstVisiblePosition < firstVisiblePosition) {
                    skippedChildrenHeight = 0;
                    if (firstVisiblePosition - this.mPrevFirstVisiblePosition != 1) {
                        for (i = firstVisiblePosition - 1; i > this.mPrevFirstVisiblePosition; i--) {
                            if (this.mChildrenHeights.indexOfKey(i) > 0) {
                                skippedChildrenHeight += this.mChildrenHeights.get(i);
                            } else {
                                skippedChildrenHeight += firstVisibleChild.getHeight();
                            }
                        }
                    }
                    this.mPrevScrolledChildrenHeight += this.mPrevFirstVisibleChildHeight + skippedChildrenHeight;
                    this.mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                } else if (firstVisiblePosition < this.mPrevFirstVisiblePosition) {
                    skippedChildrenHeight = 0;
                    if (this.mPrevFirstVisiblePosition - firstVisiblePosition != 1) {
                        for (i = this.mPrevFirstVisiblePosition - 1; i > firstVisiblePosition; i--) {
                            if (this.mChildrenHeights.indexOfKey(i) > 0) {
                                skippedChildrenHeight += this.mChildrenHeights.get(i);
                            } else {
                                skippedChildrenHeight += firstVisibleChild.getHeight();
                            }
                        }
                    }
                    this.mPrevScrolledChildrenHeight -= firstVisibleChild.getHeight() + skippedChildrenHeight;
                    this.mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                } else if (firstVisiblePosition == 0) {
                    this.mPrevFirstVisibleChildHeight = firstVisibleChild.getHeight();
                    this.mPrevScrolledChildrenHeight = 0;
                }
                if (this.mPrevFirstVisibleChildHeight < 0) {
                    this.mPrevFirstVisibleChildHeight = 0;
                }
                this.mScrollY = this.mPrevScrolledChildrenHeight - firstVisibleChild.getTop();
                this.mPrevFirstVisiblePosition = firstVisiblePosition;
                dispatchOnScrollChanged(this.mScrollY, this.mFirstScroll, this.mDragging);
                if (this.mFirstScroll) {
                    this.mFirstScroll = false;
                }
                if (this.mPrevScrollY < this.mScrollY) {
                    this.mScrollState = ScrollState.UP;
                } else if (this.mScrollY < this.mPrevScrollY) {
                    this.mScrollState = ScrollState.DOWN;
                } else {
                    this.mScrollState = ScrollState.STOP;
                }
                this.mPrevScrollY = this.mScrollY;
            }
        }
    }

    private void dispatchOnDownMotionEvent() {
        if (this.mCallbacks != null) {
            this.mCallbacks.onDownMotionEvent();
        }
        if (this.mCallbackCollection != null) {
            for (int i = 0; i < this.mCallbackCollection.size(); i++) {
                ((ObservableScrollViewCallbacks) this.mCallbackCollection.get(i)).onDownMotionEvent();
            }
        }
    }

    private void dispatchOnScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (this.mCallbacks != null) {
            this.mCallbacks.onScrollChanged(scrollY, firstScroll, dragging);
        }
        if (this.mCallbackCollection != null) {
            for (int i = 0; i < this.mCallbackCollection.size(); i++) {
                ((ObservableScrollViewCallbacks) this.mCallbackCollection.get(i)).onScrollChanged(scrollY, firstScroll, dragging);
            }
        }
    }

    private void dispatchOnUpOrCancelMotionEvent(ScrollState scrollState) {
        if (this.mCallbacks != null) {
            this.mCallbacks.onUpOrCancelMotionEvent(scrollState);
        }
        if (this.mCallbackCollection != null) {
            for (int i = 0; i < this.mCallbackCollection.size(); i++) {
                ((ObservableScrollViewCallbacks) this.mCallbackCollection.get(i)).onUpOrCancelMotionEvent(scrollState);
            }
        }
    }
}
