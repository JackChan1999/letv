package com.letv.android.client.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnTouchModeChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Checkable;
import android.widget.ListAdapter;
import com.letv.android.client.R;
import com.letv.android.client.view.AdapterView.AdapterContextMenuInfo;
import com.letv.android.client.view.ViewHelperFactory.ViewHelper;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;
import java.util.List;

@TargetApi(11)
public abstract class AbsHListView extends AdapterView<ListAdapter> implements OnGlobalLayoutListener, OnTouchModeChangeListener {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    private static final int INVALID_POINTER = -1;
    public static final int LAYOUT_FORCE_LEFT = 1;
    public static final int LAYOUT_FORCE_RIGHT = 3;
    public static final int LAYOUT_MOVE_SELECTION = 6;
    public static final int LAYOUT_NORMAL = 0;
    public static final int LAYOUT_SET_SELECTION = 2;
    public static final int LAYOUT_SPECIFIC = 4;
    public static final int LAYOUT_SYNC = 5;
    protected static final int OVERSCROLL_LIMIT_DIVISOR = 3;
    public static final int[] STATESET_NOTHING = new int[]{0};
    private static final String TAG = "AbsListView";
    public static final int TOUCH_MODE_DONE_WAITING = 2;
    public static final int TOUCH_MODE_DOWN = 0;
    public static final int TOUCH_MODE_FLING = 4;
    private static final int TOUCH_MODE_OFF = 1;
    private static final int TOUCH_MODE_ON = 0;
    public static final int TOUCH_MODE_OVERFLING = 6;
    public static final int TOUCH_MODE_OVERSCROLL = 5;
    public static final int TOUCH_MODE_REST = -1;
    public static final int TOUCH_MODE_SCROLL = 3;
    public static final int TOUCH_MODE_TAP = 1;
    private static final int TOUCH_MODE_UNKNOWN = -1;
    public static final int TRANSCRIPT_MODE_ALWAYS_SCROLL = 2;
    public static final int TRANSCRIPT_MODE_DISABLED = 0;
    public static final int TRANSCRIPT_MODE_NORMAL = 1;
    static final Interpolator sLinearInterpolator = new LinearInterpolator();
    private ListItemAccessibilityDelegate mAccessibilityDelegate;
    private int mActivePointerId;
    protected ListAdapter mAdapter;
    boolean mAdapterHasStableIds;
    private int mCacheColorHint;
    protected boolean mCachingActive;
    protected boolean mCachingStarted;
    protected SparseArrayCompat<Boolean> mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    int mCheckedItemCount;
    public Object mChoiceActionMode;
    protected int mChoiceMode;
    private Runnable mClearScrollingCache;
    private ContextMenuInfo mContextMenuInfo;
    protected AdapterDataSetObserver mDataSetObserver;
    private int mDirection;
    boolean mDrawSelectorOnTop;
    private EdgeEffectCompat mEdgeGlowBottom;
    private EdgeEffectCompat mEdgeGlowTop;
    boolean mFastScrollEnabled;
    private int mFirstPositionDistanceGuess;
    private FlingRunnable mFlingRunnable;
    private boolean mForceTranscriptScroll;
    private int mGlowPaddingBottom;
    private int mGlowPaddingTop;
    protected int mHeightMeasureSpec;
    private float mHorizontalScrollFactor;
    protected boolean mIsAttached;
    private boolean mIsChildViewEnabled;
    protected final boolean[] mIsScrap;
    private int mLastAccessibilityScrollEventFromIndex;
    private int mLastAccessibilityScrollEventToIndex;
    private int mLastHandledItemCount;
    private int mLastPositionDistanceGuess;
    private int mLastScrollState;
    private int mLastTouchMode;
    int mLastX;
    protected int mLayoutMode;
    protected Rect mListPadding;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    int mMotionCorrection;
    protected int mMotionPosition;
    int mMotionViewNewLeft;
    int mMotionViewOriginalLeft;
    int mMotionX;
    int mMotionY;
    Object mMultiChoiceModeCallback;
    private OnScrollListener mOnScrollListener;
    int mOverflingDistance;
    int mOverscrollDistance;
    protected int mOverscrollMax;
    private CheckForKeyLongPress mPendingCheckForKeyLongPress;
    private CheckForLongPress mPendingCheckForLongPress;
    private Runnable mPendingCheckForTap;
    private SavedState mPendingSync;
    private PerformClick mPerformClick;
    protected Runnable mPositionScrollAfterLayout;
    protected PositionScroller mPositionScroller;
    protected final RecycleBin mRecycler;
    protected int mResurrectToPosition;
    View mScrollLeft;
    View mScrollRight;
    boolean mScrollingCacheEnabled;
    protected int mSelectedLeft;
    int mSelectionBottomPadding;
    int mSelectionLeftPadding;
    int mSelectionRightPadding;
    int mSelectionTopPadding;
    Drawable mSelector;
    int mSelectorPosition;
    protected Rect mSelectorRect;
    private boolean mSmoothScrollbarEnabled;
    protected boolean mStackFromRight;
    private Rect mTouchFrame;
    protected int mTouchMode;
    private Runnable mTouchModeReset;
    private int mTouchSlop;
    private int mTranscriptMode;
    private float mVelocityScale;
    private VelocityTracker mVelocityTracker;
    ViewHelper mViewHelper;

    protected abstract void fillGap(boolean z);

    protected abstract int findMotionCol(int i);

    public abstract void setSelectionInt(int i);

    public AbsHListView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mChoiceMode = 0;
        this.mLayoutMode = 0;
        this.mDrawSelectorOnTop = false;
        this.mSelectorPosition = -1;
        this.mSelectorRect = new Rect();
        this.mRecycler = new RecycleBin(this);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mListPadding = new Rect();
        this.mHeightMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedLeft = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mLastScrollState = 0;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mActivePointerId = -1;
        this.mDirection = 0;
        initAbsListView();
    }

    public AbsHListView(Context context, AttributeSet attrs) {
        this(context, attrs, 2130771969);
    }

    public AbsHListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mChoiceMode = 0;
        this.mLayoutMode = 0;
        this.mDrawSelectorOnTop = false;
        this.mSelectorPosition = -1;
        this.mSelectorRect = new Rect();
        this.mRecycler = new RecycleBin(this);
        this.mSelectionLeftPadding = 0;
        this.mSelectionTopPadding = 0;
        this.mSelectionRightPadding = 0;
        this.mSelectionBottomPadding = 0;
        this.mListPadding = new Rect();
        this.mHeightMeasureSpec = 0;
        this.mTouchMode = -1;
        this.mSelectedLeft = 0;
        this.mSmoothScrollbarEnabled = true;
        this.mResurrectToPosition = -1;
        this.mContextMenuInfo = null;
        this.mLastTouchMode = -1;
        this.mLastScrollState = 0;
        this.mVelocityScale = 1.0f;
        this.mIsScrap = new boolean[1];
        this.mActivePointerId = -1;
        this.mDirection = 0;
        initAbsListView();
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AbsHListView, defStyle, 0);
        Drawable listSelector = null;
        boolean drawSelectorOnTop = false;
        boolean stackFromRight = false;
        boolean scrollingCacheEnabled = true;
        int transcriptMode = 0;
        int color = 0;
        boolean smoothScrollbar = true;
        int choiceMode = 0;
        if (array != null) {
            listSelector = array.getDrawable(0);
            drawSelectorOnTop = array.getBoolean(1, false);
            stackFromRight = array.getBoolean(6, false);
            scrollingCacheEnabled = array.getBoolean(2, true);
            transcriptMode = array.getInt(7, 0);
            color = array.getColor(3, 0);
            smoothScrollbar = array.getBoolean(5, true);
            choiceMode = array.getInt(4, 0);
            array.recycle();
        }
        if (listSelector != null) {
            setSelector(listSelector);
        }
        this.mDrawSelectorOnTop = drawSelectorOnTop;
        setStackFromRight(stackFromRight);
        setScrollingCacheEnabled(scrollingCacheEnabled);
        setTranscriptMode(transcriptMode);
        setCacheColorHint(color);
        setSmoothScrollbarEnabled(smoothScrollbar);
        setChoiceMode(choiceMode);
    }

    private void initAbsListView() {
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setScrollingCacheEnabled(true);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        this.mTouchSlop = configuration.getScaledTouchSlop();
        this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = configuration.getScaledOverscrollDistance() + 200;
        this.mOverflingDistance = configuration.getScaledOverflingDistance() + 100;
        this.mViewHelper = ViewHelperFactory.create(this);
    }

    public void setOverScrollMode(int mode) {
        if (mode == 2) {
            this.mEdgeGlowTop = null;
            this.mEdgeGlowBottom = null;
        } else if (this.mEdgeGlowTop == null) {
            Context context = getContext();
            this.mEdgeGlowTop = new EdgeEffectCompat(context);
            this.mEdgeGlowBottom = new EdgeEffectCompat(context);
        }
        super.setOverScrollMode(mode);
    }

    public void setAdapter(ListAdapter adapter) {
        if (adapter != null) {
            this.mAdapterHasStableIds = this.mAdapter.hasStableIds();
            if (this.mChoiceMode != 0 && this.mAdapterHasStableIds && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray();
            }
        }
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public boolean isItemChecked(int position) {
        if (this.mChoiceMode == 0 || this.mCheckStates == null) {
            return false;
        }
        return ((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue();
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode == 1 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public SparseArrayCompat<Boolean> getCheckedItemPositions() {
        if (this.mChoiceMode != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode == 0 || this.mCheckedIdStates == null || this.mAdapter == null) {
            return new long[0];
        }
        LongSparseArray<Integer> idStates = this.mCheckedIdStates;
        int count = idStates.size();
        long[] ids = new long[count];
        for (int i = 0; i < count; i++) {
            ids[i] = idStates.keyAt(i);
        }
        return ids;
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    public void setItemChecked(int position, boolean value) {
        if (this.mChoiceMode != 0) {
            if (VERSION.SDK_INT >= 11 && value && this.mChoiceMode == 3 && this.mChoiceActionMode == null) {
                if (this.mMultiChoiceModeCallback == null || !((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback).hasWrappedCallback()) {
                    throw new IllegalStateException("AbsListView: attempted to start selection mode for CHOICE_MODE_MULTIPLE_MODAL but no choice mode callback was supplied. Call setMultiChoiceModeListener to set a callback.");
                }
                this.mChoiceActionMode = startActionMode((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback);
            }
            if (this.mChoiceMode == 2 || (VERSION.SDK_INT >= 11 && this.mChoiceMode == 3)) {
                boolean oldValue = ((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue();
                this.mCheckStates.put(position, Boolean.valueOf(value));
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (value) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                    }
                }
                if (oldValue != value) {
                    if (value) {
                        this.mCheckedItemCount++;
                    } else {
                        this.mCheckedItemCount--;
                    }
                }
                if (this.mChoiceActionMode != null) {
                    ((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback).onItemCheckedStateChanged((ActionMode) this.mChoiceActionMode, position, this.mAdapter.getItemId(position), value);
                }
            } else {
                boolean updateIds;
                if (this.mCheckedIdStates == null || !this.mAdapter.hasStableIds()) {
                    updateIds = false;
                } else {
                    updateIds = true;
                }
                if (value || isItemChecked(position)) {
                    this.mCheckStates.clear();
                    if (updateIds) {
                        this.mCheckedIdStates.clear();
                    }
                }
                if (value) {
                    this.mCheckStates.put(position, Boolean.valueOf(true));
                    if (updateIds) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !((Boolean) this.mCheckStates.valueAt(0)).booleanValue()) {
                    this.mCheckedItemCount = 0;
                }
            }
            if (!this.mInLayout && !this.mBlockLayoutRequests) {
                this.mDataChanged = true;
                rememberSyncState();
                requestLayout();
            }
        }
    }

    public boolean performItemClick(View view, int position, long id) {
        boolean handled = false;
        boolean dispatchItemClick = true;
        if (this.mChoiceMode != 0) {
            handled = true;
            boolean checkedStateChanged = false;
            boolean checked;
            if (this.mChoiceMode == 2 || (VERSION.SDK_INT >= 11 && this.mChoiceMode == 3 && this.mChoiceActionMode != null)) {
                if (((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue()) {
                    checked = false;
                } else {
                    checked = true;
                }
                this.mCheckStates.put(position, Boolean.valueOf(checked));
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (checked) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                    }
                }
                if (checked) {
                    this.mCheckedItemCount++;
                } else {
                    this.mCheckedItemCount--;
                }
                if (this.mChoiceActionMode != null) {
                    ((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback).onItemCheckedStateChanged((ActionMode) this.mChoiceActionMode, position, id, checked);
                    dispatchItemClick = false;
                }
                checkedStateChanged = true;
            } else if (this.mChoiceMode == 1) {
                if (((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue()) {
                    checked = false;
                } else {
                    checked = true;
                }
                if (checked) {
                    this.mCheckStates.clear();
                    this.mCheckStates.put(position, Boolean.valueOf(true));
                    if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                        this.mCheckedIdStates.clear();
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !((Boolean) this.mCheckStates.valueAt(0)).booleanValue()) {
                    this.mCheckedItemCount = 0;
                }
                checkedStateChanged = true;
            }
            if (checkedStateChanged) {
                updateOnScreenCheckedViews();
            }
        }
        if (dispatchItemClick) {
            return handled | super.performItemClick(view, position, id);
        }
        return handled;
    }

    private void updateOnScreenCheckedViews() {
        boolean useActivated;
        int firstPos = this.mFirstPosition;
        int count = getChildCount();
        if (VERSION.SDK_INT >= 11) {
            useActivated = true;
        } else {
            useActivated = false;
        }
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int position = firstPos + i;
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue());
            } else if (useActivated) {
                child.setActivated(((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue());
            }
        }
    }

    public int getChoiceMode() {
        return this.mChoiceMode;
    }

    @TargetApi(11)
    public void setChoiceMode(int choiceMode) {
        this.mChoiceMode = choiceMode;
        if (VERSION.SDK_INT >= 11 && this.mChoiceActionMode != null) {
            if (VERSION.SDK_INT >= 11) {
                ((ActionMode) this.mChoiceActionMode).finish();
            }
            this.mChoiceActionMode = null;
        }
        if (this.mChoiceMode != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseArrayCompat();
            }
            if (this.mCheckedIdStates == null && this.mAdapter != null && this.mAdapter.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray();
            }
            if (VERSION.SDK_INT >= 11 && this.mChoiceMode == 3) {
                clearChoices();
                setLongClickable(true);
            }
        }
    }

    @TargetApi(11)
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        if (VERSION.SDK_INT >= 11) {
            if (this.mMultiChoiceModeCallback == null) {
                this.mMultiChoiceModeCallback = new MultiChoiceModeWrapper(this);
            }
            ((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback).setWrapped(listener);
            return;
        }
        LogInfo.log(TAG, "setMultiChoiceModeListener not supported for this version of Android");
    }

    private boolean contentFits() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        if (childCount != this.mItemCount) {
            return false;
        }
        if (getChildAt(0).getLeft() < this.mListPadding.left || getChildAt(childCount - 1).getRight() > getWidth() - this.mListPadding.right) {
            return false;
        }
        return true;
    }

    protected int getHorizontalScrollbarHeight() {
        return super.getHorizontalScrollbarHeight();
    }

    public void setSmoothScrollbarEnabled(boolean enabled) {
        this.mSmoothScrollbarEnabled = enabled;
    }

    @ExportedProperty
    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListener = l;
        invokeOnItemScrollListener();
    }

    protected void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    public void sendAccessibilityEvent(int eventType) {
        if (eventType == 4096) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int lastVisiblePosition = getLastVisiblePosition();
            if (this.mLastAccessibilityScrollEventFromIndex != firstVisiblePosition || this.mLastAccessibilityScrollEventToIndex != lastVisiblePosition) {
                this.mLastAccessibilityScrollEventFromIndex = firstVisiblePosition;
                this.mLastAccessibilityScrollEventToIndex = lastVisiblePosition;
            } else {
                return;
            }
        }
        super.sendAccessibilityEvent(eventType);
    }

    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(AbsHListView.class.getName());
    }

    @TargetApi(14)
    @SuppressLint({"Override"})
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AbsHListView.class.getName());
        if (isEnabled()) {
            if (getFirstVisiblePosition() > 0) {
                info.addAction(8192);
            }
            if (getLastVisiblePosition() < getCount() - 1) {
                info.addAction(4096);
            }
        }
    }

    @TargetApi(16)
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        switch (action) {
            case 4096:
                if (!isEnabled() || getLastVisiblePosition() >= getCount() - 1) {
                    return false;
                }
                smoothScrollBy((getWidth() - this.mListPadding.left) - this.mListPadding.right, 200);
                return true;
            case 8192:
                if (!isEnabled() || this.mFirstPosition <= 0) {
                    return false;
                }
                smoothScrollBy(-((getWidth() - this.mListPadding.left) - this.mListPadding.right), 200);
                return true;
            default:
                return false;
        }
    }

    @ExportedProperty
    public boolean isScrollingCacheEnabled() {
        return this.mScrollingCacheEnabled;
    }

    public void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled && !enabled) {
            clearScrollingCache();
        }
        this.mScrollingCacheEnabled = enabled;
    }

    public void getFocusedRect(Rect r) {
        View view = getSelectedView();
        if (view == null || view.getParent() != this) {
            super.getFocusedRect(r);
            return;
        }
        view.getFocusedRect(r);
        offsetDescendantRectToMyCoords(view, r);
    }

    private void useDefaultSelector() {
        setSelector(getResources().getDrawable(17301602));
    }

    public boolean isStackFromRight() {
        return this.mStackFromRight;
    }

    public void setStackFromRight(boolean stackFromRight) {
        if (this.mStackFromRight != stackFromRight) {
            this.mStackFromRight = stackFromRight;
            requestLayoutIfNecessary();
        }
    }

    void requestLayoutIfNecessary() {
        if (getChildCount() > 0) {
            resetList();
            requestLayout();
            invalidate();
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSync != null) {
            ss.selectedId = this.mPendingSync.selectedId;
            ss.firstId = this.mPendingSync.firstId;
            ss.viewLeft = this.mPendingSync.viewLeft;
            ss.position = this.mPendingSync.position;
            ss.width = this.mPendingSync.width;
            ss.filter = this.mPendingSync.filter;
            ss.inActionMode = this.mPendingSync.inActionMode;
            ss.checkedItemCount = this.mPendingSync.checkedItemCount;
            ss.checkState = this.mPendingSync.checkState;
            ss.checkIdState = this.mPendingSync.checkIdState;
        } else {
            boolean haveChildren = getChildCount() > 0 && this.mItemCount > 0;
            long selectedId = getSelectedItemId();
            ss.selectedId = selectedId;
            ss.width = getWidth();
            if (selectedId >= 0) {
                ss.viewLeft = this.mSelectedLeft;
                ss.position = getSelectedItemPosition();
                ss.firstId = -1;
            } else if (!haveChildren || this.mFirstPosition <= 0) {
                ss.viewLeft = 0;
                ss.firstId = -1;
                ss.position = 0;
            } else {
                ss.viewLeft = getChildAt(0).getLeft();
                int firstPos = this.mFirstPosition;
                if (firstPos >= this.mItemCount) {
                    firstPos = this.mItemCount - 1;
                }
                ss.position = firstPos;
                ss.firstId = this.mAdapter.getItemId(firstPos);
            }
            ss.filter = null;
            boolean z = VERSION.SDK_INT >= 11 && this.mChoiceMode == 3 && this.mChoiceActionMode != null;
            ss.inActionMode = z;
            if (this.mCheckStates != null) {
                try {
                    ss.checkState = this.mCheckStates.clone();
                } catch (NoSuchMethodError e) {
                    e.printStackTrace();
                    ss.checkState = new SparseArrayCompat();
                }
            }
            if (this.mCheckedIdStates != null) {
                LongSparseArray<Integer> idState = new LongSparseArray();
                int count = this.mCheckedIdStates.size();
                for (int i = 0; i < count; i++) {
                    idState.put(this.mCheckedIdStates.keyAt(i), this.mCheckedIdStates.valueAt(i));
                }
                ss.checkIdState = idState;
            }
            ss.checkedItemCount = this.mCheckedItemCount;
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncWidth = (long) ss.width;
        if (ss.selectedId >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncColId = ss.selectedId;
            this.mSyncPosition = ss.position;
            this.mSpecificLeft = ss.viewLeft;
            this.mSyncMode = 0;
        } else if (ss.firstId >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncColId = ss.firstId;
            this.mSyncPosition = ss.position;
            this.mSpecificLeft = ss.viewLeft;
            this.mSyncMode = 1;
        }
        if (ss.checkState != null) {
            this.mCheckStates = ss.checkState;
        }
        if (ss.checkIdState != null) {
            this.mCheckedIdStates = ss.checkIdState;
        }
        this.mCheckedItemCount = ss.checkedItemCount;
        if (VERSION.SDK_INT >= 11 && ss.inActionMode && this.mChoiceMode == 3 && this.mMultiChoiceModeCallback != null) {
            this.mChoiceActionMode = startActionMode((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback);
        }
        requestLayout();
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.mSelectedPosition < 0 && !isInTouchMode()) {
            if (!(this.mIsAttached || this.mAdapter == null)) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            resurrectSelection();
        }
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    protected void resetList() {
        removeAllViewsInLayout();
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mPositionScrollAfterLayout = null;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedColId = Long.MIN_VALUE;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        this.mSelectedLeft = 0;
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        invalidate();
    }

    protected int computeHorizontalScrollExtent() {
        int count = getChildCount();
        if (count <= 0) {
            return 0;
        }
        if (!this.mSmoothScrollbarEnabled) {
            return 1;
        }
        int extent = count * 100;
        View view = getChildAt(0);
        int left = view.getLeft();
        int width = view.getWidth();
        if (width > 0) {
            extent += (left * 100) / width;
        }
        view = getChildAt(count - 1);
        int right = view.getRight();
        width = view.getWidth();
        if (width > 0) {
            return extent - (((right - getWidth()) * 100) / width);
        }
        return extent;
    }

    protected int computeHorizontalScrollOffset() {
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        if (firstPosition < 0 || childCount <= 0) {
            return 0;
        }
        if (this.mSmoothScrollbarEnabled) {
            View view = getChildAt(0);
            int left = view.getLeft();
            int width = view.getWidth();
            if (width > 0) {
                return Math.max(((firstPosition * 100) - ((left * 100) / width)) + ((int) (((((float) getScrollX()) / ((float) getWidth())) * ((float) this.mItemCount)) * 100.0f)), 0);
            }
            return 0;
        }
        int index;
        int count = this.mItemCount;
        if (firstPosition == 0) {
            index = 0;
        } else if (firstPosition + childCount == count) {
            index = count;
        } else {
            index = firstPosition + (childCount / 2);
        }
        return (int) (((float) firstPosition) + (((float) childCount) * (((float) index) / ((float) count))));
    }

    protected int computeHorizontalScrollRange() {
        if (!this.mSmoothScrollbarEnabled) {
            return this.mItemCount;
        }
        int result = Math.max(this.mItemCount * 100, 0);
        if (getScrollX() != 0) {
            return result + Math.abs((int) (((((float) getScrollX()) / ((float) getWidth())) * ((float) this.mItemCount)) * 100.0f));
        }
        return result;
    }

    protected float getLeftFadingEdgeStrength() {
        int count = getChildCount();
        float fadeEdge = super.getLeftFadingEdgeStrength();
        if (count == 0) {
            return fadeEdge;
        }
        if (this.mFirstPosition > 0) {
            return 1.0f;
        }
        int left = getChildAt(0).getLeft();
        return left < getPaddingLeft() ? ((float) (-(left - getPaddingLeft()))) / ((float) getHorizontalFadingEdgeLength()) : fadeEdge;
    }

    protected float getRightFadingEdgeStrength() {
        int count = getChildCount();
        float fadeEdge = super.getRightFadingEdgeStrength();
        if (count == 0) {
            return fadeEdge;
        }
        if ((this.mFirstPosition + count) - 1 < this.mItemCount - 1) {
            return 1.0f;
        }
        int right = getChildAt(count - 1).getRight();
        int width = getWidth();
        return right > width - getPaddingRight() ? ((float) ((right - width) + getPaddingRight())) / ((float) getHorizontalFadingEdgeLength()) : fadeEdge;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean z = true;
        if (this.mSelector == null) {
            useDefaultSelector();
        }
        Rect listPadding = this.mListPadding;
        listPadding.left = this.mSelectionLeftPadding + getPaddingLeft();
        listPadding.top = this.mSelectionTopPadding + getPaddingTop();
        listPadding.right = this.mSelectionRightPadding + getPaddingRight();
        listPadding.bottom = this.mSelectionBottomPadding + getPaddingBottom();
        if (this.mTranscriptMode == 1) {
            int childCount = getChildCount();
            int listRight = getWidth() - getPaddingRight();
            View lastChild = getChildAt(childCount - 1);
            int lastRight;
            if (lastChild != null) {
                lastRight = lastChild.getRight();
            } else {
                lastRight = listRight;
            }
            if (this.mFirstPosition + childCount < this.mLastHandledItemCount || lastRight > listRight) {
                z = false;
            }
            this.mForceTranscriptScroll = z;
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mInLayout = true;
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).forceLayout();
            }
            this.mRecycler.markChildrenDirty();
        }
        layoutChildren();
        this.mInLayout = false;
        this.mOverscrollMax = (r - l) / 3;
    }

    protected void layoutChildren() {
    }

    protected void updateScrollIndicators() {
        int i = 0;
        if (this.mScrollLeft != null) {
            int i2;
            boolean canScrollLeft = this.mFirstPosition > 0;
            if (!canScrollLeft && getChildCount() > 0) {
                canScrollLeft = getChildAt(0).getLeft() < this.mListPadding.left;
            }
            View view = this.mScrollLeft;
            if (canScrollLeft) {
                i2 = 0;
            } else {
                i2 = 4;
            }
            view.setVisibility(i2);
        }
        if (this.mScrollRight != null) {
            boolean canScrollRight;
            int count = getChildCount();
            if (this.mFirstPosition + count < this.mItemCount) {
                canScrollRight = true;
            } else {
                canScrollRight = false;
            }
            if (!canScrollRight && count > 0) {
                if (getChildAt(count - 1).getRight() > getRight() - this.mListPadding.right) {
                    canScrollRight = true;
                } else {
                    canScrollRight = false;
                }
            }
            View view2 = this.mScrollRight;
            if (!canScrollRight) {
                i = 4;
            }
            view2.setVisibility(i);
        }
    }

    @ExportedProperty
    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    }

    public int getListPaddingTop() {
        return this.mListPadding.top;
    }

    public int getListPaddingBottom() {
        return this.mListPadding.bottom;
    }

    public int getListPaddingLeft() {
        return this.mListPadding.left;
    }

    public int getListPaddingRight() {
        return this.mListPadding.right;
    }

    @SuppressLint({"NewApi"})
    protected View obtainView(int position, boolean[] isScrap) {
        isScrap[0] = false;
        View scrapView = this.mRecycler.getTransientStateView(position);
        if (scrapView != null) {
            return scrapView;
        }
        View child;
        scrapView = this.mRecycler.getScrapView(position);
        if (scrapView != null) {
            child = this.mAdapter.getView(position, scrapView, this);
            if (VERSION.SDK_INT >= 16 && child.getImportantForAccessibility() == 0) {
                child.setImportantForAccessibility(1);
            }
            if (child != scrapView) {
                this.mRecycler.addScrapView(scrapView, position);
                if (this.mCacheColorHint != 0) {
                    child.setDrawingCacheBackgroundColor(this.mCacheColorHint);
                }
            } else {
                isScrap[0] = true;
                child.onFinishTemporaryDetach();
            }
        } else {
            child = this.mAdapter.getView(position, null, this);
            if (VERSION.SDK_INT >= 16 && child.getImportantForAccessibility() == 0) {
                child.setImportantForAccessibility(1);
            }
            if (this.mCacheColorHint != 0) {
                child.setDrawingCacheBackgroundColor(this.mCacheColorHint);
            }
        }
        if (this.mAdapterHasStableIds) {
            LayoutParams lp;
            LayoutParams vlp = child.getLayoutParams();
            if (vlp == null) {
                lp = (LayoutParams) generateDefaultLayoutParams();
            } else if (checkLayoutParams(vlp)) {
                lp = (LayoutParams) vlp;
            } else {
                lp = (LayoutParams) generateLayoutParams(vlp);
            }
            lp.itemId = this.mAdapter.getItemId(position);
            child.setLayoutParams(lp);
        }
        if (!this.mAccessibilityManager.isEnabled() || this.mAccessibilityDelegate != null) {
            return child;
        }
        this.mAccessibilityDelegate = new ListItemAccessibilityDelegate(this);
        return child;
    }

    protected void positionSelector(int position, View sel) {
        if (position != -1) {
            this.mSelectorPosition = position;
        }
        Rect selectorRect = this.mSelectorRect;
        selectorRect.set(sel.getLeft(), sel.getTop(), sel.getRight(), sel.getBottom());
        if (sel instanceof SelectionBoundsAdjuster) {
            ((SelectionBoundsAdjuster) sel).adjustListItemSelectionBounds(selectorRect);
        }
        positionSelector(selectorRect.left, selectorRect.top, selectorRect.right, selectorRect.bottom);
        boolean isChildViewEnabled = this.mIsChildViewEnabled;
        if (sel.isEnabled() != isChildViewEnabled) {
            this.mIsChildViewEnabled = !isChildViewEnabled;
            if (getSelectedItemPosition() != -1) {
                refreshDrawableState();
            }
        }
    }

    private void positionSelector(int l, int t, int r, int b) {
        this.mSelectorRect.set(l - this.mSelectionLeftPadding, t - this.mSelectionTopPadding, this.mSelectionRightPadding + r, this.mSelectionBottomPadding + b);
    }

    protected void dispatchDraw(Canvas canvas) {
        boolean drawSelectorOnTop = this.mDrawSelectorOnTop;
        if (!drawSelectorOnTop) {
            drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (drawSelectorOnTop) {
            drawSelector(canvas);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (getChildCount() > 0) {
            this.mDataChanged = true;
            rememberSyncState();
        }
    }

    boolean touchModeDrawsInPressedState() {
        switch (this.mTouchMode) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    protected boolean shouldShowSelector() {
        return (hasFocus() && !isInTouchMode()) || touchModeDrawsInPressedState();
    }

    private void drawSelector(Canvas canvas) {
        if (!this.mSelectorRect.isEmpty()) {
            Drawable selector = this.mSelector;
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
    }

    public void setDrawSelectorOnTop(boolean onTop) {
        this.mDrawSelectorOnTop = onTop;
    }

    public void setSelector(int resID) {
        setSelector(getResources().getDrawable(resID));
    }

    public void setSelector(Drawable sel) {
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = sel;
        Rect padding = new Rect();
        sel.getPadding(padding);
        this.mSelectionLeftPadding = padding.left;
        this.mSelectionTopPadding = padding.top;
        this.mSelectionRightPadding = padding.right;
        this.mSelectionBottomPadding = padding.bottom;
        sel.setCallback(this);
        updateSelectorState();
    }

    public Drawable getSelector() {
        return this.mSelector;
    }

    protected void keyPressed() {
        if (isEnabled() && isClickable()) {
            Drawable selector = this.mSelector;
            Rect selectorRect = this.mSelectorRect;
            if (selector == null) {
                return;
            }
            if ((isFocused() || touchModeDrawsInPressedState()) && !selectorRect.isEmpty()) {
                View v = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (v != null) {
                    if (!v.hasFocusable()) {
                        v.setPressed(true);
                    } else {
                        return;
                    }
                }
                setPressed(true);
                boolean longClickable = isLongClickable();
                Drawable d = selector.getCurrent();
                if (d != null && (d instanceof TransitionDrawable)) {
                    if (longClickable) {
                        ((TransitionDrawable) d).startTransition(ViewConfiguration.getLongPressTimeout());
                    } else {
                        ((TransitionDrawable) d).resetTransition();
                    }
                }
                if (longClickable && !this.mDataChanged) {
                    if (this.mPendingCheckForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new CheckForKeyLongPress(this, null);
                    }
                    this.mPendingCheckForKeyLongPress.rememberWindowAttachCount();
                    postDelayed(this.mPendingCheckForKeyLongPress, (long) ViewConfiguration.getLongPressTimeout());
                }
            }
        }
    }

    public void setScrollIndicators(View left, View right) {
        this.mScrollLeft = left;
        this.mScrollRight = right;
    }

    void updateSelectorState() {
        if (this.mSelector == null) {
            return;
        }
        if (shouldShowSelector()) {
            this.mSelector.setState(getDrawableState());
        } else {
            this.mSelector.setState(STATESET_NOTHING);
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    @SuppressLint({"Override"})
    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.mIsChildViewEnabled) {
            return super.onCreateDrawableState(extraSpace);
        }
        int enabledState = ENABLED_STATE_SET[0];
        int[] state = super.onCreateDrawableState(extraSpace + 1);
        int enabledPos = -1;
        for (int i = state.length - 1; i >= 0; i--) {
            if (state[i] == enabledState) {
                enabledPos = i;
                break;
            }
        }
        if (enabledPos < 0) {
            return state;
        }
        System.arraycopy(state, enabledPos + 1, state, enabledPos, (state.length - enabledPos) - 1);
        return state;
    }

    public boolean verifyDrawable(Drawable dr) {
        return this.mSelector == dr || super.verifyDrawable(dr);
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mSelector != null) {
            this.mSelector.jumpToCurrentState();
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnTouchModeChangeListener(this);
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new AdapterDataSetObserver(this);
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        this.mIsAttached = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycler.clear();
        getViewTreeObserver().removeOnTouchModeChangeListener(this);
        if (!(this.mAdapter == null || this.mDataSetObserver == null)) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
        if (this.mFlingRunnable != null) {
            removeCallbacks(this.mFlingRunnable);
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (this.mClearScrollingCache != null) {
            removeCallbacks(this.mClearScrollingCache);
        }
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mTouchModeReset != null) {
            removeCallbacks(this.mTouchModeReset);
            this.mTouchModeReset = null;
        }
        this.mIsAttached = false;
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        int touchMode;
        super.onWindowFocusChanged(hasWindowFocus);
        if (isInTouchMode()) {
            touchMode = 0;
        } else {
            touchMode = 1;
        }
        if (!hasWindowFocus) {
            setChildrenDrawingCacheEnabled(false);
            if (this.mFlingRunnable != null) {
                removeCallbacks(this.mFlingRunnable);
                this.mFlingRunnable.endFling();
                if (this.mPositionScroller != null) {
                    this.mPositionScroller.stop();
                }
                if (getScrollX() != 0) {
                    this.mViewHelper.setScrollX(0);
                    finishGlows();
                    invalidate();
                }
            }
            if (touchMode == 1) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
        } else if (!(touchMode == this.mLastTouchMode || this.mLastTouchMode == -1)) {
            if (touchMode == 1) {
                resurrectSelection();
            } else {
                hideSelector();
                this.mLayoutMode = 0;
                layoutChildren();
            }
        }
        this.mLastTouchMode = touchMode;
    }

    ContextMenuInfo createContextMenuInfo(View view, int position, long id) {
        return new AdapterContextMenuInfo(view, position, id);
    }

    boolean performLongPress(View child, int longPressPosition, long longPressId) {
        boolean z = true;
        if (VERSION.SDK_INT < 11 || this.mChoiceMode != 3) {
            z = false;
            if (this.mOnItemLongClickListener != null) {
                z = this.mOnItemLongClickListener.onItemLongClick(this, child, longPressPosition, longPressId);
            }
            if (!z) {
                this.mContextMenuInfo = createContextMenuInfo(child, longPressPosition, longPressId);
                z = super.showContextMenuForChild(this);
            }
            if (z) {
                performHapticFeedback(0);
            }
        } else if (this.mChoiceActionMode == null) {
            ActionMode startActionMode = startActionMode((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback);
            this.mChoiceActionMode = startActionMode;
            if (startActionMode != null) {
                setItemChecked(longPressPosition, true);
                performHapticFeedback(0);
            }
        }
        return z;
    }

    protected ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public boolean showContextMenu(float x, float y, int metaState) {
        int position = pointToPosition((int) x, (int) y);
        if (position != -1) {
            long id = this.mAdapter.getItemId(position);
            View child = getChildAt(position - this.mFirstPosition);
            if (child != null) {
                this.mContextMenuInfo = createContextMenuInfo(child, position, id);
                return super.showContextMenuForChild(this);
            }
        }
        return showContextMenu(x, y, metaState);
    }

    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = getPositionForView(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        long longPressId = this.mAdapter.getItemId(longPressPosition);
        boolean handled = false;
        if (this.mOnItemLongClickListener != null) {
            handled = this.mOnItemLongClickListener.onItemLongClick(this, originalView, longPressPosition, longPressId);
        }
        if (handled) {
            return handled;
        }
        this.mContextMenuInfo = createContextMenuInfo(getChildAt(longPressPosition - this.mFirstPosition), longPressPosition, longPressId);
        return super.showContextMenuForChild(originalView);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 23:
            case 66:
                if (!isEnabled()) {
                    return true;
                }
                if (isClickable() && isPressed() && this.mSelectedPosition >= 0 && this.mAdapter != null && this.mSelectedPosition < this.mAdapter.getCount()) {
                    View view = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                    if (view != null) {
                        performItemClick(view, this.mSelectedPosition, this.mSelectedColId);
                        view.setPressed(false);
                    }
                    setPressed(false);
                    return true;
                }
        }
        return super.onKeyUp(keyCode, event);
    }

    protected void dispatchSetPressed(boolean pressed) {
    }

    public int pointToPosition(int x, int y) {
        Rect frame = this.mTouchFrame;
        if (frame == null) {
            this.mTouchFrame = new Rect();
            frame = this.mTouchFrame;
        }
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    public long pointToColId(int x, int y) {
        int position = pointToPosition(x, y);
        if (position >= 0) {
            return this.mAdapter.getItemId(position);
        }
        return Long.MIN_VALUE;
    }

    private boolean startScrollIfNeeded(int x) {
        boolean overscroll;
        int deltaX = x - this.mMotionX;
        int distance = Math.abs(deltaX);
        if (getScrollX() != 0) {
            overscroll = true;
        } else {
            overscroll = false;
        }
        if (!overscroll && distance <= this.mTouchSlop) {
            return false;
        }
        createScrollingCache();
        if (overscroll) {
            this.mTouchMode = 5;
            this.mMotionCorrection = 0;
        } else {
            this.mTouchMode = 3;
            this.mMotionCorrection = deltaX > 0 ? this.mTouchSlop : -this.mTouchSlop;
        }
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mPendingCheckForLongPress);
        }
        setPressed(false);
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(false);
        }
        reportScrollStateChange(1);
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(x);
        return true;
    }

    private void scrollIfNeeded(int x) {
        int incrementalDeltaX;
        int rawDeltaX = x - this.mMotionX;
        int deltaX = rawDeltaX - this.mMotionCorrection;
        if (this.mLastX != Integer.MIN_VALUE) {
            incrementalDeltaX = x - this.mLastX;
        } else {
            incrementalDeltaX = deltaX;
        }
        View motionView;
        int overscrollMode;
        if (this.mTouchMode == 3) {
            if (x != this.mLastX) {
                int motionIndex;
                if (Math.abs(rawDeltaX) > this.mTouchSlop) {
                    ViewParent parent = getParent();
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                }
                if (this.mMotionPosition >= 0) {
                    motionIndex = this.mMotionPosition - this.mFirstPosition;
                } else {
                    motionIndex = getChildCount() / 2;
                }
                int motionViewPrevLeft = 0;
                motionView = getChildAt(motionIndex);
                if (motionView != null) {
                    motionViewPrevLeft = motionView.getLeft();
                }
                boolean atEdge = false;
                if (incrementalDeltaX != 0) {
                    atEdge = trackMotionScroll(deltaX, incrementalDeltaX);
                }
                motionView = getChildAt(motionIndex);
                if (motionView != null) {
                    int motionViewRealLeft = motionView.getLeft();
                    if (atEdge) {
                        int overscroll = (-incrementalDeltaX) - (motionViewRealLeft - motionViewPrevLeft);
                        overScrollBy(overscroll, 0, getScrollX(), 0, 0, 0, this.mOverscrollDistance, 0, true);
                        if (Math.abs(this.mOverscrollDistance) == Math.abs(getScrollX()) && this.mVelocityTracker != null) {
                            this.mVelocityTracker.clear();
                        }
                        overscrollMode = getOverScrollMode();
                        if (overscrollMode == 0 || (overscrollMode == 1 && !contentFits())) {
                            this.mDirection = 0;
                            this.mTouchMode = 5;
                            if (rawDeltaX > 0) {
                                this.mEdgeGlowTop.onPull(((float) overscroll) / ((float) getWidth()));
                                if (!this.mEdgeGlowBottom.isFinished()) {
                                    this.mEdgeGlowBottom.onRelease();
                                }
                                invalidate();
                            } else if (rawDeltaX < 0) {
                                this.mEdgeGlowBottom.onPull(((float) overscroll) / ((float) getWidth()));
                                if (!this.mEdgeGlowTop.isFinished()) {
                                    this.mEdgeGlowTop.onRelease();
                                }
                                invalidate();
                            }
                        }
                    }
                    this.mMotionX = x;
                }
                this.mLastX = x;
            }
        } else if (this.mTouchMode == 5 && x != this.mLastX) {
            int oldScroll = getScrollX();
            int newScroll = oldScroll - incrementalDeltaX;
            int newDirection = x > this.mLastX ? 1 : -1;
            if (this.mDirection == 0) {
                this.mDirection = newDirection;
            }
            int overScrollDistance = -incrementalDeltaX;
            if ((newScroll >= 0 || oldScroll < 0) && (newScroll <= 0 || oldScroll > 0)) {
                incrementalDeltaX = 0;
            } else {
                overScrollDistance = -oldScroll;
                incrementalDeltaX += overScrollDistance;
            }
            if (overScrollDistance != 0) {
                overScrollBy(overScrollDistance, 0, getScrollX(), 0, 0, 0, this.mOverscrollDistance, 0, true);
                overscrollMode = getOverScrollMode();
                if (overscrollMode == 0 || (overscrollMode == 1 && !contentFits())) {
                    if (rawDeltaX > 0) {
                        this.mEdgeGlowTop.onPull(((float) overScrollDistance) / ((float) getWidth()));
                        if (!this.mEdgeGlowBottom.isFinished()) {
                            this.mEdgeGlowBottom.onRelease();
                        }
                        invalidate();
                    } else if (rawDeltaX < 0) {
                        this.mEdgeGlowBottom.onPull(((float) overScrollDistance) / ((float) getWidth()));
                        if (!this.mEdgeGlowTop.isFinished()) {
                            this.mEdgeGlowTop.onRelease();
                        }
                        invalidate();
                    }
                }
            }
            if (incrementalDeltaX != 0) {
                if (getScrollX() != 0) {
                    this.mViewHelper.setScrollX(0);
                    invalidateParentIfNeeded();
                }
                trackMotionScroll(incrementalDeltaX, incrementalDeltaX);
                this.mTouchMode = 3;
                int motionPosition = findClosestMotionCol(x);
                this.mMotionCorrection = 0;
                motionView = getChildAt(motionPosition - this.mFirstPosition);
                this.mMotionViewOriginalLeft = motionView != null ? motionView.getLeft() : 0;
                this.mMotionX = x;
                this.mMotionPosition = motionPosition;
            }
            this.mLastX = x;
            this.mDirection = newDirection;
        }
    }

    @TargetApi(11)
    protected void invalidateParentIfNeeded() {
        if (this.mViewHelper.isHardwareAccelerated() && (getParent() instanceof View)) {
            ((View) getParent()).invalidate();
        }
    }

    public void onTouchModeChanged(boolean isInTouchMode) {
        if (isInTouchMode) {
            hideSelector();
            if (getWidth() > 0 && getChildCount() > 0) {
                layoutChildren();
            }
            updateSelectorState();
            return;
        }
        int touchMode = this.mTouchMode;
        if (touchMode == 5 || touchMode == 6) {
            if (this.mFlingRunnable != null) {
                this.mFlingRunnable.endFling();
            }
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            if (getScrollX() != 0) {
                this.mViewHelper.setScrollX(0);
                finishGlows();
                invalidate();
            }
        }
    }

    @TargetApi(14)
    protected boolean performButtonActionOnTouchDown(MotionEvent event) {
        if (VERSION.SDK_INT < 14 || (event.getButtonState() & 2) == 0 || !showContextMenu(event.getX(), event.getY(), event.getMetaState())) {
            return false;
        }
        return true;
    }

    @SuppressLint({"Override"})
    public boolean onTouchEvent(MotionEvent ev) {
        if (isEnabled()) {
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
            }
            if (!this.mIsAttached) {
                return false;
            }
            int action = ev.getAction();
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(ev);
            int x;
            int y;
            int motionPosition;
            Handler handler;
            switch (action & 255) {
                case 0:
                    switch (this.mTouchMode) {
                        case 6:
                            this.mFlingRunnable.endFling();
                            if (this.mPositionScroller != null) {
                                this.mPositionScroller.stop();
                            }
                            this.mTouchMode = 5;
                            this.mMotionY = (int) ev.getY();
                            int x2 = (int) ev.getX();
                            this.mLastX = x2;
                            this.mMotionX = x2;
                            this.mMotionCorrection = 0;
                            this.mActivePointerId = ev.getPointerId(0);
                            this.mDirection = 0;
                            break;
                        default:
                            this.mActivePointerId = ev.getPointerId(0);
                            x = (int) ev.getX();
                            y = (int) ev.getY();
                            motionPosition = pointToPosition(x, y);
                            if (!this.mDataChanged) {
                                if (this.mTouchMode != 4 && motionPosition >= 0 && ((ListAdapter) getAdapter()).isEnabled(motionPosition)) {
                                    this.mTouchMode = 0;
                                    if (this.mPendingCheckForTap == null) {
                                        this.mPendingCheckForTap = new CheckForTap(this);
                                    }
                                    postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
                                } else if (this.mTouchMode == 4) {
                                    createScrollingCache();
                                    this.mTouchMode = 3;
                                    this.mMotionCorrection = 0;
                                    motionPosition = findMotionCol(x);
                                    this.mFlingRunnable.flywheelTouch();
                                }
                            }
                            if (motionPosition >= 0) {
                                this.mMotionViewOriginalLeft = getChildAt(motionPosition - this.mFirstPosition).getLeft();
                            }
                            this.mMotionX = x;
                            this.mMotionY = y;
                            this.mMotionPosition = motionPosition;
                            this.mLastX = Integer.MIN_VALUE;
                            break;
                    }
                    if (performButtonActionOnTouchDown(ev) && this.mTouchMode == 0) {
                        removeCallbacks(this.mPendingCheckForTap);
                        break;
                    }
                    break;
                case 1:
                    VelocityTracker velocityTracker;
                    int initialVelocity;
                    switch (this.mTouchMode) {
                        case 0:
                        case 1:
                        case 2:
                            motionPosition = this.mMotionPosition;
                            View child = getChildAt(motionPosition - this.mFirstPosition);
                            float x3 = ev.getX();
                            boolean inList = x3 > ((float) this.mListPadding.left) && x3 < ((float) (getWidth() - this.mListPadding.right));
                            if (!(child == null || child.hasFocusable() || !inList)) {
                                if (this.mTouchMode != 0) {
                                    child.setPressed(false);
                                }
                                if (this.mPerformClick == null) {
                                    this.mPerformClick = new PerformClick(this, null);
                                }
                                PerformClick performClick = this.mPerformClick;
                                performClick.mClickMotionPosition = motionPosition;
                                performClick.rememberWindowAttachCount();
                                this.mResurrectToPosition = motionPosition;
                                if (this.mTouchMode == 0 || this.mTouchMode == 1) {
                                    handler = getHandler();
                                    if (handler != null) {
                                        Runnable runnable;
                                        if (this.mTouchMode == 0) {
                                            runnable = this.mPendingCheckForTap;
                                        } else {
                                            runnable = this.mPendingCheckForLongPress;
                                        }
                                        handler.removeCallbacks(runnable);
                                    }
                                    this.mLayoutMode = 0;
                                    if (this.mDataChanged || !this.mAdapter.isEnabled(motionPosition)) {
                                        this.mTouchMode = -1;
                                        updateSelectorState();
                                    } else {
                                        this.mTouchMode = 1;
                                        setSelectedPositionInt(this.mMotionPosition);
                                        layoutChildren();
                                        child.setPressed(true);
                                        positionSelector(this.mMotionPosition, child);
                                        setPressed(true);
                                        if (this.mSelector != null) {
                                            Drawable d = this.mSelector.getCurrent();
                                            if (d != null && (d instanceof TransitionDrawable)) {
                                                ((TransitionDrawable) d).resetTransition();
                                            }
                                        }
                                        if (this.mTouchModeReset != null) {
                                            removeCallbacks(this.mTouchModeReset);
                                        }
                                        this.mTouchModeReset = new 1(this, child, performClick);
                                        postDelayed(this.mTouchModeReset, (long) ViewConfiguration.getPressedStateDuration());
                                    }
                                    return true;
                                } else if (!this.mDataChanged && this.mAdapter.isEnabled(motionPosition)) {
                                    performClick.run();
                                }
                            }
                            this.mTouchMode = -1;
                            updateSelectorState();
                            break;
                        case 3:
                            int childCount = getChildCount();
                            if (childCount <= 0) {
                                this.mTouchMode = -1;
                                reportScrollStateChange(0);
                                break;
                            }
                            int firstChildLeft = getChildAt(0).getLeft();
                            int lastChildRight = getChildAt(childCount - 1).getRight();
                            int contentLeft = this.mListPadding.left;
                            int contentRight = getWidth() - this.mListPadding.right;
                            if (this.mFirstPosition == 0 && firstChildLeft >= contentLeft && this.mFirstPosition + childCount < this.mItemCount && lastChildRight <= getWidth() - contentRight) {
                                this.mTouchMode = -1;
                                reportScrollStateChange(0);
                                break;
                            }
                            velocityTracker = this.mVelocityTracker;
                            velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                            initialVelocity = (int) (velocityTracker.getXVelocity(this.mActivePointerId) * this.mVelocityScale);
                            if (Math.abs(initialVelocity) > this.mMinimumVelocity && ((this.mFirstPosition != 0 || firstChildLeft != contentLeft - this.mOverscrollDistance) && (this.mFirstPosition + childCount != this.mItemCount || lastChildRight != this.mOverscrollDistance + contentRight))) {
                                if (this.mFlingRunnable == null) {
                                    this.mFlingRunnable = new FlingRunnable(this);
                                }
                                reportScrollStateChange(2);
                                this.mFlingRunnable.start(-initialVelocity);
                                break;
                            }
                            this.mTouchMode = -1;
                            reportScrollStateChange(0);
                            if (this.mFlingRunnable != null) {
                                this.mFlingRunnable.endFling();
                            }
                            if (this.mPositionScroller != null) {
                                this.mPositionScroller.stop();
                                break;
                            }
                            break;
                        case 5:
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable(this);
                            }
                            velocityTracker = this.mVelocityTracker;
                            velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                            initialVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                            reportScrollStateChange(2);
                            if (Math.abs(initialVelocity) <= this.mMinimumVelocity) {
                                this.mFlingRunnable.startSpringback();
                                break;
                            }
                            this.mFlingRunnable.startOverfling(-initialVelocity);
                            break;
                    }
                    setPressed(false);
                    if (this.mEdgeGlowTop != null) {
                        this.mEdgeGlowTop.onRelease();
                        this.mEdgeGlowBottom.onRelease();
                    }
                    invalidate();
                    handler = getHandler();
                    if (handler != null) {
                        handler.removeCallbacks(this.mPendingCheckForLongPress);
                    }
                    recycleVelocityTracker();
                    this.mActivePointerId = -1;
                    break;
                case 2:
                    int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                    if (pointerIndex == -1) {
                        pointerIndex = 0;
                        this.mActivePointerId = ev.getPointerId(0);
                    }
                    x = (int) ev.getX(pointerIndex);
                    if (this.mDataChanged) {
                        layoutChildren();
                    }
                    switch (this.mTouchMode) {
                        case 0:
                        case 1:
                        case 2:
                            startScrollIfNeeded(x);
                            break;
                        case 3:
                        case 5:
                            scrollIfNeeded(x);
                            break;
                        default:
                            break;
                    }
                case 3:
                    switch (this.mTouchMode) {
                        case 5:
                            if (this.mFlingRunnable == null) {
                                this.mFlingRunnable = new FlingRunnable(this);
                            }
                            this.mFlingRunnable.startSpringback();
                            break;
                        case 6:
                            break;
                        default:
                            this.mTouchMode = -1;
                            setPressed(false);
                            View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
                            if (motionView != null) {
                                motionView.setPressed(false);
                            }
                            clearScrollingCache();
                            handler = getHandler();
                            if (handler != null) {
                                handler.removeCallbacks(this.mPendingCheckForLongPress);
                            }
                            recycleVelocityTracker();
                            break;
                    }
                    if (this.mEdgeGlowTop != null) {
                        this.mEdgeGlowTop.onRelease();
                        this.mEdgeGlowBottom.onRelease();
                    }
                    this.mActivePointerId = -1;
                    break;
                case 5:
                    int index = ev.getActionIndex();
                    int id = ev.getPointerId(index);
                    x = (int) ev.getX(index);
                    y = (int) ev.getY(index);
                    this.mMotionCorrection = 0;
                    this.mActivePointerId = id;
                    this.mMotionX = x;
                    this.mMotionY = y;
                    motionPosition = pointToPosition(x, y);
                    if (motionPosition >= 0) {
                        this.mMotionViewOriginalLeft = getChildAt(motionPosition - this.mFirstPosition).getLeft();
                        this.mMotionPosition = motionPosition;
                    }
                    this.mLastX = x;
                    break;
                case 6:
                    onSecondaryPointerUp(ev);
                    x = this.mMotionX;
                    motionPosition = pointToPosition(x, this.mMotionY);
                    if (motionPosition >= 0) {
                        this.mMotionViewOriginalLeft = getChildAt(motionPosition - this.mFirstPosition).getLeft();
                        this.mMotionPosition = motionPosition;
                    }
                    this.mLastX = x;
                    break;
            }
            return true;
        } else if (isClickable() || isLongClickable()) {
            return true;
        } else {
            return false;
        }
    }

    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (getScrollX() != scrollX) {
            onScrollChanged(scrollX, getScrollY(), getScrollX(), getScrollY());
            this.mViewHelper.setScrollX(scrollX);
            invalidateParentIfNeeded();
            awakenScrollBars();
        }
    }

    @TargetApi(12)
    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & 2) != 0) {
            switch (event.getAction()) {
                case 8:
                    if (this.mTouchMode == -1) {
                        float hscroll = event.getAxisValue(10);
                        if (hscroll != 0.0f) {
                            int delta = (int) (getHorizontalScrollFactor() * hscroll);
                            if (!trackMotionScroll(delta, delta)) {
                                return true;
                            }
                        }
                    }
                    break;
            }
        }
        return super.onGenericMotionEvent(event);
    }

    protected float getHorizontalScrollFactor() {
        if (this.mHorizontalScrollFactor == 0.0f) {
            TypedValue outValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(2130771970, outValue, true)) {
                this.mHorizontalScrollFactor = outValue.getDimension(getContext().getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define hlv_listPreferredItemWidth.");
            }
        }
        return this.mHorizontalScrollFactor;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void setOverScrollEffectPadding(int topPadding, int bottomPadding) {
        this.mGlowPaddingTop = topPadding;
        this.mGlowPaddingBottom = bottomPadding;
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        if (!this.mIsAttached) {
            return false;
        }
        int x;
        switch (action & 255) {
            case 0:
                int touchMode = this.mTouchMode;
                if (touchMode == 6 || touchMode == 5) {
                    this.mMotionCorrection = 0;
                    return true;
                }
                x = (int) ev.getX();
                int y = (int) ev.getY();
                this.mActivePointerId = ev.getPointerId(0);
                int motionPosition = findMotionCol(x);
                if (touchMode != 4 && motionPosition >= 0) {
                    this.mMotionViewOriginalLeft = getChildAt(motionPosition - this.mFirstPosition).getLeft();
                    this.mMotionX = x;
                    this.mMotionY = y;
                    this.mMotionPosition = motionPosition;
                    this.mTouchMode = 0;
                    clearScrollingCache();
                }
                this.mLastX = Integer.MIN_VALUE;
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(ev);
                if (touchMode == 4) {
                    return true;
                }
                return false;
            case 1:
            case 3:
                this.mTouchMode = -1;
                this.mActivePointerId = -1;
                recycleVelocityTracker();
                reportScrollStateChange(0);
                return false;
            case 2:
                switch (this.mTouchMode) {
                    case 0:
                        int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                        if (pointerIndex == -1) {
                            pointerIndex = 0;
                            this.mActivePointerId = ev.getPointerId(0);
                        }
                        x = (int) ev.getX(pointerIndex);
                        initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement(ev);
                        if (startScrollIfNeeded(x)) {
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            case 6:
                onSecondaryPointerUp(ev);
                return false;
            default:
                return false;
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mMotionX = (int) ev.getX(newPointerIndex);
            this.mMotionY = (int) ev.getY(newPointerIndex);
            this.mMotionCorrection = 0;
            this.mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    public void addTouchables(ArrayList<View> views) {
        int count = getChildCount();
        int firstPosition = this.mFirstPosition;
        ListAdapter adapter = this.mAdapter;
        if (adapter != null) {
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (adapter.isEnabled(firstPosition + i)) {
                    views.add(child);
                }
                child.addTouchables(views);
            }
        }
    }

    void reportScrollStateChange(int newState) {
        if (newState != this.mLastScrollState && this.mOnScrollListener != null) {
            this.mLastScrollState = newState;
            this.mOnScrollListener.onScrollStateChanged(this, newState);
        }
    }

    public void setFriction(float friction) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable(this);
        }
        FlingRunnable.access$1000(this.mFlingRunnable).setFriction(friction);
    }

    public void setVelocityScale(float scale) {
        this.mVelocityScale = scale;
    }

    public void smoothScrollToPosition(int position) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller(this);
        }
        this.mPositionScroller.start(position);
    }

    public void smoothScrollToPositionFromLeft(int position, int offset, int duration) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller(this);
        }
        this.mPositionScroller.startWithOffset(position, offset, duration);
    }

    public void smoothScrollToPositionFromLeft(int position, int offset) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller(this);
        }
        this.mPositionScroller.startWithOffset(position, offset);
    }

    public void smoothScrollToPosition(int position, int boundPosition) {
        if (this.mPositionScroller == null) {
            this.mPositionScroller = new PositionScroller(this);
        }
        this.mPositionScroller.start(position, boundPosition);
    }

    public void smoothScrollBy(int distance, int duration) {
        smoothScrollBy(distance, duration, false);
    }

    public void smoothScrollBy(int distance, int duration, boolean linear) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable(this);
        }
        int firstPos = this.mFirstPosition;
        int childCount = getChildCount();
        int lastPos = firstPos + childCount;
        int leftLimit = getPaddingLeft();
        int rightLimit = getWidth() - getPaddingRight();
        if (distance == 0 || this.mItemCount == 0 || childCount == 0 || ((firstPos == 0 && getChildAt(0).getLeft() == leftLimit && distance < 0) || (lastPos == this.mItemCount && getChildAt(childCount - 1).getRight() == rightLimit && distance > 0))) {
            this.mFlingRunnable.endFling();
            if (this.mPositionScroller != null) {
                this.mPositionScroller.stop();
                return;
            }
            return;
        }
        reportScrollStateChange(2);
        this.mFlingRunnable.startScroll(distance, duration, linear);
    }

    protected void smoothScrollByOffset(int position) {
        int index = -1;
        if (position < 0) {
            index = getFirstVisiblePosition();
        } else if (position > 0) {
            index = getLastVisiblePosition();
        }
        if (index > -1) {
            View child = getChildAt(index - getFirstVisiblePosition());
            if (child != null) {
                Rect visibleRect = new Rect();
                if (child.getGlobalVisibleRect(visibleRect)) {
                    float visibleArea = ((float) (visibleRect.width() * visibleRect.height())) / ((float) (child.getWidth() * child.getHeight()));
                    if (position < 0 && visibleArea < 0.75f) {
                        index++;
                    } else if (position > 0 && visibleArea < 0.75f) {
                        index--;
                    }
                }
                smoothScrollToPosition(Math.max(0, Math.min(getCount(), index + position)));
            }
        }
    }

    private void createScrollingCache() {
        if (this.mScrollingCacheEnabled && !this.mCachingStarted && !this.mViewHelper.isHardwareAccelerated()) {
            setChildrenDrawnWithCacheEnabled(true);
            setChildrenDrawingCacheEnabled(true);
            this.mCachingActive = true;
            this.mCachingStarted = true;
        }
    }

    private void clearScrollingCache() {
        if (!this.mViewHelper.isHardwareAccelerated()) {
            if (this.mClearScrollingCache == null) {
                this.mClearScrollingCache = new 2(this);
            }
            post(this.mClearScrollingCache);
        }
    }

    boolean trackMotionScroll(int deltaX, int incrementalDeltaX) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        int firstLeft = getChildAt(0).getLeft();
        int lastRight = getChildAt(childCount - 1).getRight();
        Rect listPadding = this.mListPadding;
        int spaceBefore = 0 - firstLeft;
        int spaceAfter = lastRight - (getWidth() - 0);
        int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
        if (deltaX < 0) {
            deltaX = Math.max(-(width - 1), deltaX);
        } else {
            deltaX = Math.min(width - 1, deltaX);
        }
        if (incrementalDeltaX < 0) {
            incrementalDeltaX = Math.max(-(width - 1), incrementalDeltaX);
        } else {
            incrementalDeltaX = Math.min(width - 1, incrementalDeltaX);
        }
        int firstPosition = this.mFirstPosition;
        if (firstPosition == 0) {
            this.mFirstPositionDistanceGuess = firstLeft - listPadding.left;
        } else {
            this.mFirstPositionDistanceGuess += incrementalDeltaX;
        }
        if (firstPosition + childCount == this.mItemCount) {
            this.mLastPositionDistanceGuess = listPadding.right + lastRight;
        } else {
            this.mLastPositionDistanceGuess += incrementalDeltaX;
        }
        boolean cannotScrollRight = firstPosition == 0 && firstLeft >= listPadding.left && incrementalDeltaX >= 0;
        boolean cannotScrollLeft = firstPosition + childCount == this.mItemCount && lastRight <= getWidth() - listPadding.right && incrementalDeltaX <= 0;
        if (!cannotScrollRight && !cannotScrollLeft) {
            boolean down = incrementalDeltaX < 0;
            boolean inTouchMode = isInTouchMode();
            if (inTouchMode) {
                hideSelector();
            }
            int headerViewsCount = getHeaderViewsCount();
            int footerViewsStart = this.mItemCount - getFooterViewsCount();
            int start = 0;
            int count = 0;
            int i;
            View child;
            int position;
            if (!down) {
                int bottom = getWidth() - incrementalDeltaX;
                for (i = childCount - 1; i >= 0; i--) {
                    child = getChildAt(i);
                    if (child.getLeft() <= bottom) {
                        break;
                    }
                    start = i;
                    count++;
                    position = firstPosition + i;
                    if (position >= headerViewsCount && position < footerViewsStart) {
                        this.mRecycler.addScrapView(child, position);
                    }
                }
            } else {
                int top = -incrementalDeltaX;
                for (i = 0; i < childCount; i++) {
                    child = getChildAt(i);
                    if (child.getRight() >= top) {
                        break;
                    }
                    count++;
                    position = firstPosition + i;
                    if (position >= headerViewsCount && position < footerViewsStart) {
                        this.mRecycler.addScrapView(child, position);
                    }
                }
            }
            this.mMotionViewNewLeft = this.mMotionViewOriginalLeft + deltaX;
            this.mBlockLayoutRequests = true;
            if (count > 0) {
                detachViewsFromParent(start, count);
                this.mRecycler.removeSkippedScrap();
            }
            if (!awakenScrollBars()) {
                invalidate();
            }
            offsetChildrenLeftAndRight(incrementalDeltaX);
            if (down) {
                this.mFirstPosition += count;
            }
            int absIncrementalDeltaX = Math.abs(incrementalDeltaX);
            if (spaceBefore < absIncrementalDeltaX || spaceAfter < absIncrementalDeltaX) {
                fillGap(down);
            }
            int childIndex;
            if (!inTouchMode && this.mSelectedPosition != -1) {
                childIndex = this.mSelectedPosition - this.mFirstPosition;
                if (childIndex >= 0 && childIndex < getChildCount()) {
                    positionSelector(this.mSelectedPosition, getChildAt(childIndex));
                }
            } else if (this.mSelectorPosition != -1) {
                childIndex = this.mSelectorPosition - this.mFirstPosition;
                if (childIndex >= 0 && childIndex < getChildCount()) {
                    positionSelector(-1, getChildAt(childIndex));
                }
            } else {
                this.mSelectorRect.setEmpty();
            }
            this.mBlockLayoutRequests = false;
            invokeOnItemScrollListener();
            return false;
        } else if (incrementalDeltaX != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void offsetChildrenLeftAndRight(int offset) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).offsetLeftAndRight(offset);
        }
    }

    protected int getHeaderViewsCount() {
        return 0;
    }

    protected int getFooterViewsCount() {
        return 0;
    }

    protected void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectedLeft = 0;
        }
    }

    protected int reconcileSelectedPosition() {
        int position = this.mSelectedPosition;
        if (position < 0) {
            position = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, position), this.mItemCount - 1);
    }

    protected int findClosestMotionCol(int x) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int motionCol = findMotionCol(x);
        return motionCol == -1 ? (this.mFirstPosition + childCount) - 1 : motionCol;
    }

    public void invalidateViews() {
        this.mDataChanged = true;
        rememberSyncState();
        requestLayout();
        invalidate();
    }

    protected boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition >= 0 || !resurrectSelection()) {
            return false;
        }
        updateSelectorState();
        return true;
    }

    boolean resurrectSelection() {
        int childCount = getChildCount();
        if (childCount <= 0) {
            return false;
        }
        int selectedPos;
        int selectedLeft = 0;
        int childrenLeft = this.mListPadding.left;
        int childrenRight = (getRight() - getLeft()) - this.mListPadding.right;
        int firstPosition = this.mFirstPosition;
        int toPosition = this.mResurrectToPosition;
        boolean down = true;
        if (toPosition >= firstPosition && toPosition < firstPosition + childCount) {
            selectedPos = toPosition;
            View selected = getChildAt(selectedPos - this.mFirstPosition);
            selectedLeft = selected.getLeft();
            int selectedRight = selected.getRight();
            if (selectedLeft < childrenLeft) {
                selectedLeft = childrenLeft + getHorizontalFadingEdgeLength();
            } else if (selectedRight > childrenRight) {
                selectedLeft = (childrenRight - selected.getMeasuredWidth()) - getHorizontalFadingEdgeLength();
            }
        } else if (toPosition < firstPosition) {
            selectedPos = firstPosition;
            for (i = 0; i < childCount; i++) {
                left = getChildAt(i).getLeft();
                if (i == 0) {
                    selectedLeft = left;
                    if (firstPosition > 0 || left < childrenLeft) {
                        childrenLeft += getHorizontalFadingEdgeLength();
                    }
                }
                if (left >= childrenLeft) {
                    selectedPos = firstPosition + i;
                    selectedLeft = left;
                    break;
                }
            }
        } else {
            int itemCount = this.mItemCount;
            down = false;
            selectedPos = (firstPosition + childCount) - 1;
            for (i = childCount - 1; i >= 0; i--) {
                View v = getChildAt(i);
                left = v.getLeft();
                int right = v.getRight();
                if (i == childCount - 1) {
                    selectedLeft = left;
                    if (firstPosition + childCount < itemCount || right > childrenRight) {
                        childrenRight -= getHorizontalFadingEdgeLength();
                    }
                }
                if (right <= childrenRight) {
                    selectedPos = firstPosition + i;
                    selectedLeft = left;
                    break;
                }
            }
        }
        this.mResurrectToPosition = -1;
        removeCallbacks(this.mFlingRunnable);
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        this.mTouchMode = -1;
        clearScrollingCache();
        this.mSpecificLeft = selectedLeft;
        selectedPos = lookForSelectablePosition(selectedPos, down);
        if (selectedPos < firstPosition || selectedPos > getLastVisiblePosition()) {
            selectedPos = -1;
        } else {
            this.mLayoutMode = 4;
            updateSelectorState();
            setSelectionInt(selectedPos);
            invokeOnItemScrollListener();
        }
        reportScrollStateChange(0);
        if (selectedPos >= 0) {
            return true;
        }
        return false;
    }

    void confirmCheckedPositionsById() {
        this.mCheckStates.clear();
        boolean checkedCountChanged = false;
        int checkedIndex = 0;
        while (checkedIndex < this.mCheckedIdStates.size()) {
            long id = this.mCheckedIdStates.keyAt(checkedIndex);
            int lastPos = ((Integer) this.mCheckedIdStates.valueAt(checkedIndex)).intValue();
            if (id != this.mAdapter.getItemId(lastPos)) {
                int start = Math.max(0, lastPos - 20);
                int end = Math.min(lastPos + 20, this.mItemCount);
                boolean found = false;
                for (int searchPos = start; searchPos < end; searchPos++) {
                    if (id == this.mAdapter.getItemId(searchPos)) {
                        found = true;
                        this.mCheckStates.put(searchPos, Boolean.valueOf(true));
                        this.mCheckedIdStates.setValueAt(checkedIndex, Integer.valueOf(searchPos));
                        break;
                    }
                }
                if (!found) {
                    this.mCheckedIdStates.delete(id);
                    checkedIndex--;
                    this.mCheckedItemCount--;
                    checkedCountChanged = true;
                    if (!(VERSION.SDK_INT <= 11 || this.mChoiceActionMode == null || this.mMultiChoiceModeCallback == null)) {
                        ((MultiChoiceModeWrapper) this.mMultiChoiceModeCallback).onItemCheckedStateChanged((ActionMode) this.mChoiceActionMode, lastPos, id, false);
                    }
                }
            } else {
                this.mCheckStates.put(lastPos, Boolean.valueOf(true));
            }
            checkedIndex++;
        }
        if (checkedCountChanged && this.mChoiceActionMode != null && VERSION.SDK_INT > 11) {
            ((ActionMode) this.mChoiceActionMode).invalidate();
        }
    }

    protected void handleDataChanged() {
        int i = 3;
        int count = this.mItemCount;
        int lastHandledItemCount = this.mLastHandledItemCount;
        this.mLastHandledItemCount = this.mItemCount;
        if (!(this.mChoiceMode == 0 || this.mAdapter == null || !this.mAdapter.hasStableIds())) {
            confirmCheckedPositionsById();
        }
        this.mRecycler.clearTransientStateViews();
        if (count > 0) {
            int newPos;
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                if (this.mTranscriptMode == 2) {
                    this.mLayoutMode = 3;
                    return;
                }
                if (this.mTranscriptMode == 1) {
                    if (this.mForceTranscriptScroll) {
                        this.mForceTranscriptScroll = false;
                        this.mLayoutMode = 3;
                        return;
                    }
                    int childCount = getChildCount();
                    int listRight = getWidth() - getPaddingRight();
                    View lastChild = getChildAt(childCount - 1);
                    int lastRight;
                    if (lastChild != null) {
                        lastRight = lastChild.getBottom();
                    } else {
                        lastRight = listRight;
                    }
                    if (this.mFirstPosition + childCount < lastHandledItemCount || lastRight > listRight) {
                        awakenScrollBars();
                    } else {
                        this.mLayoutMode = 3;
                        return;
                    }
                }
                switch (this.mSyncMode) {
                    case 0:
                        if (isInTouchMode()) {
                            this.mLayoutMode = 5;
                            this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), count - 1);
                            return;
                        }
                        newPos = findSyncPosition();
                        if (newPos >= 0 && lookForSelectablePosition(newPos, true) == newPos) {
                            this.mSyncPosition = newPos;
                            if (this.mSyncWidth == ((long) getWidth())) {
                                this.mLayoutMode = 5;
                            } else {
                                this.mLayoutMode = 2;
                            }
                            setNextSelectedPositionInt(newPos);
                            return;
                        }
                    case 1:
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), count - 1);
                        return;
                }
            }
            if (!isInTouchMode()) {
                newPos = getSelectedItemPosition();
                if (newPos >= count) {
                    newPos = count - 1;
                }
                if (newPos < 0) {
                    newPos = 0;
                }
                int selectablePos = lookForSelectablePosition(newPos, true);
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    return;
                }
                selectablePos = lookForSelectablePosition(newPos, false);
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    return;
                }
            } else if (this.mResurrectToPosition >= 0) {
                return;
            }
        }
        if (!this.mStackFromRight) {
            i = 1;
        }
        this.mLayoutMode = i;
        this.mSelectedPosition = -1;
        this.mSelectedColId = Long.MIN_VALUE;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedColId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = -1;
        checkSelectionChanged();
    }

    public static int getDistance(Rect source, Rect dest, int direction) {
        int sX;
        int sY;
        int dX;
        int dY;
        switch (direction) {
            case 1:
            case 2:
                sX = source.right + (source.width() / 2);
                sY = source.top + (source.height() / 2);
                dX = dest.left + (dest.width() / 2);
                dY = dest.top + (dest.height() / 2);
                break;
            case 17:
                sX = source.left;
                sY = source.top + (source.height() / 2);
                dX = dest.right;
                dY = dest.top + (dest.height() / 2);
                break;
            case 33:
                sX = source.left + (source.width() / 2);
                sY = source.top;
                dX = dest.left + (dest.width() / 2);
                dY = dest.bottom;
                break;
            case 66:
                sX = source.right;
                sY = source.top + (source.height() / 2);
                dX = dest.left;
                dY = dest.top + (dest.height() / 2);
                break;
            case 130:
                sX = source.left + (source.width() / 2);
                sY = source.bottom;
                dX = dest.left + (dest.width() / 2);
                dY = dest.top;
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
        int deltaX = dX - sX;
        int deltaY = dY - sY;
        return (deltaY * deltaY) + (deltaX * deltaX);
    }

    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return null;
    }

    public boolean checkInputConnectionProxy(View view) {
        return false;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -1, 0);
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new LayoutParams(p);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setTranscriptMode(int mode) {
        this.mTranscriptMode = mode;
    }

    public int getTranscriptMode() {
        return this.mTranscriptMode;
    }

    public int getSolidColor() {
        return this.mCacheColorHint;
    }

    public void setCacheColorHint(int color) {
        if (color != this.mCacheColorHint) {
            this.mCacheColorHint = color;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                getChildAt(i).setDrawingCacheBackgroundColor(color);
            }
            this.mRecycler.setCacheColorHint(color);
        }
    }

    @ExportedProperty(category = "drawing")
    public int getCacheColorHint() {
        return this.mCacheColorHint;
    }

    @SuppressLint({"NewApi"})
    public void reclaimViews(List<View> views) {
        int childCount = getChildCount();
        RecyclerListener listener = RecycleBin.access$2200(this.mRecycler);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp != null && this.mRecycler.shouldRecycleViewType(lp.viewType)) {
                views.add(child);
                if (VERSION.SDK_INT >= 14) {
                    child.setAccessibilityDelegate(null);
                }
                if (listener != null) {
                    listener.onMovedToScrapHeap(child);
                }
            }
        }
        this.mRecycler.reclaimScrapViews(views);
        removeAllViewsInLayout();
    }

    private void finishGlows() {
        if (this.mEdgeGlowTop != null) {
            this.mEdgeGlowTop.finish();
            this.mEdgeGlowBottom.finish();
        }
    }

    protected void setVisibleRangeHint(int start, int end) {
    }

    public void setRecyclerListener(RecyclerListener listener) {
        RecycleBin.access$2202(this.mRecycler, listener);
    }

    static View retrieveFromScrap(ArrayList<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size <= 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            View view = (View) scrapViews.get(i);
            if (((LayoutParams) view.getLayoutParams()).scrappedFromPosition == position) {
                scrapViews.remove(i);
                return view;
            }
        }
        return (View) scrapViews.remove(size - 1);
    }
}
