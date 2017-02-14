package com.letv.android.client.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.RemoteViews.RemoteView;
import com.letv.android.client.R;
import com.letv.android.client.view.AbsHListView.AdapterDataSetObserver;
import com.letv.android.client.view.AbsHListView.LayoutParams;
import com.letv.android.client.view.AbsHListView.OnScrollListener;
import com.letv.android.client.view.AbsHListView.RecycleBin;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.ArrayList;

@RemoteView
public class HListView extends AbsHListView implements OnScrollListener {
    public static final int LOADING_TYPE_LEFT = 2;
    public static final int LOADING_TYPE_RIGHT = 1;
    private static final String LOG_TAG = "HListView";
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 2;
    static final int NO_POSITION = -1;
    private boolean loading_footer;
    private boolean loading_header;
    private boolean mAreAllItemsSelectable;
    private final ArrowScrollFocusResult mArrowScrollFocusResult;
    Drawable mDivider;
    private boolean mDividerIsOpaque;
    private Paint mDividerPaint;
    int mDividerWidth;
    private FocusSelector mFocusSelector;
    private boolean mFooterDividersEnabled;
    private ArrayList<FixedViewInfo> mFooterViewInfos;
    private boolean mHeaderDividersEnabled;
    private ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mIsCacheColorOpaque;
    private boolean mItemsCanFocus;
    private int mLastSavedFirstVisibleItem;
    int mMeasureWithChild;
    private OnLastVisibleListener mOnLastVisibleListener;
    Drawable mOverScrollFooter;
    Drawable mOverScrollHeader;
    private final Rect mTempRect;

    public HListView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public HListView(Context context, AttributeSet attrs) {
        this(context, attrs, 2130771971);
    }

    public HListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mAreAllItemsSelectable = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.loading_header = false;
        this.loading_footer = false;
        this.mLastSavedFirstVisibleItem = -1;
        this.mArrowScrollFocusResult = new ArrowScrollFocusResult(null);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HListView, defStyle, 0);
        CharSequence[] entries = null;
        Drawable dividerDrawable = null;
        Drawable overscrollHeader = null;
        Drawable overscrollFooter = null;
        int dividerWidth = 0;
        boolean headerDividersEnabled = true;
        boolean footerDividersEnabled = true;
        int measureWithChild = -1;
        if (array != null) {
            entries = array.getTextArray(0);
            dividerDrawable = array.getDrawable(1);
            overscrollHeader = array.getDrawable(5);
            overscrollFooter = array.getDrawable(6);
            dividerWidth = array.getDimensionPixelSize(2, 0);
            headerDividersEnabled = array.getBoolean(3, true);
            footerDividersEnabled = array.getBoolean(4, true);
            measureWithChild = array.getInteger(7, -1);
            array.recycle();
        }
        if (entries != null) {
            setAdapter(new ArrayAdapter(context, 17367043, entries));
        }
        if (dividerDrawable != null) {
            setDivider(dividerDrawable);
        }
        if (overscrollHeader != null) {
            setOverscrollHeader(overscrollHeader);
        }
        if (overscrollFooter != null) {
            setOverscrollFooter(overscrollFooter);
        }
        if (dividerWidth != 0) {
            setDividerWidth(dividerWidth);
        }
        this.mHeaderDividersEnabled = headerDividersEnabled;
        this.mFooterDividersEnabled = footerDividersEnabled;
        this.mMeasureWithChild = measureWithChild;
        setOnScrollListener(this);
    }

    public int getMaxScrollAmount() {
        return (int) (MAX_SCROLL_FACTOR * ((float) (getRight() - getLeft())));
    }

    private void adjustViewsLeftOrRight() {
        int childCount = getChildCount();
        if (childCount > 0) {
            int delta;
            if (this.mStackFromRight) {
                delta = getChildAt(childCount - 1).getRight() - (getWidth() - this.mListPadding.right);
                if (this.mFirstPosition + childCount < this.mItemCount) {
                    delta += this.mDividerWidth;
                }
                if (delta > 0) {
                    delta = 0;
                }
            } else {
                delta = getChildAt(0).getLeft() - this.mListPadding.left;
                if (this.mFirstPosition != 0) {
                    delta -= this.mDividerWidth;
                }
                if (delta < 0) {
                    delta = 0;
                }
            }
            if (delta != 0) {
                offsetChildrenLeftAndRight(-delta);
            }
        }
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        if (this.mAdapter == null || (this.mAdapter instanceof HeaderViewListAdapter)) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = v;
            info.data = data;
            info.isSelectable = isSelectable;
            this.mHeaderViewInfos.add(info);
            if (this.mAdapter != null && this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot add header view to list -- setAdapter has already been called.");
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeHeader(v)) {
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mHeaderViewInfos);
        return result;
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = 0; i < len; i++) {
            if (((FixedViewInfo) where.get(i)).view == v) {
                where.remove(i);
                return;
            }
        }
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mFooterViewInfos.add(info);
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mDataSetObserver.onChanged();
        }
    }

    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeFooter(v)) {
            if (this.mDataSetObserver != null) {
                this.mDataSetObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mFooterViewInfos);
        return result;
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (!(this.mAdapter == null || this.mDataSetObserver == null)) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetList();
        this.mRecycler.clear();
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = new HeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
        } else {
            this.mAdapter = adapter;
        }
        this.mOldSelectedPosition = -1;
        this.mOldSelectedColId = Long.MIN_VALUE;
        super.setAdapter(adapter);
        if (this.mAdapter != null) {
            int position;
            this.mAreAllItemsSelectable = this.mAdapter.areAllItemsEnabled();
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            checkFocus();
            this.mDataSetObserver = new AdapterDataSetObserver(this);
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.setViewTypeCount(this.mAdapter.getViewTypeCount());
            if (this.mStackFromRight) {
                position = lookForSelectablePosition(this.mItemCount - 1, false);
            } else {
                position = lookForSelectablePosition(0, true);
            }
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            this.mAreAllItemsSelectable = true;
            checkFocus();
            checkSelectionChanged();
        }
        requestLayout();
    }

    public void notifyDataSetChanged(boolean isLeft) {
        updateFirstPostion(isLeft);
        try {
            ((BaseAdapter) ((HeaderViewListAdapter) this.mAdapter).getWrappedAdapter()).notifyDataSetChanged();
        } catch (Exception e) {
        }
    }

    protected void resetList() {
        clearRecycledState(this.mHeaderViewInfos);
        clearRecycledState(this.mFooterViewInfos);
        super.resetList();
        this.mLayoutMode = 0;
    }

    private void clearRecycledState(ArrayList<FixedViewInfo> infos) {
        if (infos != null) {
            int count = infos.size();
            for (int i = 0; i < count; i++) {
                LayoutParams p = (LayoutParams) ((FixedViewInfo) infos.get(i)).view.getLayoutParams();
                if (p != null) {
                    p.recycledHeaderFooter = false;
                }
            }
        }
    }

    private boolean showingLeftFadingEdge() {
        int listLeft = getScrollX() + this.mListPadding.left;
        if (this.mFirstPosition > 0 || getChildAt(0).getLeft() > listLeft) {
            return true;
        }
        return false;
    }

    private boolean showingRightFadingEdge() {
        int childCount = getChildCount();
        return (this.mFirstPosition + childCount) + -1 < this.mItemCount + -1 || getChildAt(childCount - 1).getRight() < (getScrollX() + getWidth()) - this.mListPadding.right;
    }

    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        int rectLeftWithinChild = rect.left;
        rect.offset(child.getLeft(), child.getTop());
        rect.offset(-child.getScrollX(), -child.getScrollY());
        int width = getWidth();
        int listUnfadedLeft = getScrollX();
        int listUnfadedRight = listUnfadedLeft + width;
        int fadingEdge = getHorizontalFadingEdgeLength();
        if (showingLeftFadingEdge() && (this.mSelectedPosition > 0 || rectLeftWithinChild > fadingEdge)) {
            listUnfadedLeft += fadingEdge;
        }
        int rightOfRightChild = getChildAt(getChildCount() - 1).getRight();
        if (showingRightFadingEdge() && (this.mSelectedPosition < this.mItemCount - 1 || rect.right < rightOfRightChild - fadingEdge)) {
            listUnfadedRight -= fadingEdge;
        }
        int scrollXDelta = 0;
        if (rect.right > listUnfadedRight && rect.left > listUnfadedLeft) {
            if (rect.width() > width) {
                scrollXDelta = 0 + (rect.left - listUnfadedLeft);
            } else {
                scrollXDelta = 0 + (rect.right - listUnfadedRight);
            }
            scrollXDelta = Math.min(scrollXDelta, rightOfRightChild - listUnfadedRight);
        } else if (rect.left < listUnfadedLeft && rect.right < listUnfadedRight) {
            if (rect.width() > width) {
                scrollXDelta = 0 - (listUnfadedRight - rect.right);
            } else {
                scrollXDelta = 0 - (listUnfadedLeft - rect.left);
            }
            scrollXDelta = Math.max(scrollXDelta, getChildAt(0).getLeft() - listUnfadedLeft);
        }
        boolean scroll = scrollXDelta != 0;
        if (scroll) {
            scrollListItemsBy(-scrollXDelta);
            positionSelector(-1, child);
            this.mSelectedLeft = child.getTop();
            invalidate();
        }
        return scroll;
    }

    protected void fillGap(boolean down) {
        int count = getChildCount();
        if (down) {
            int startOffset;
            if (count > 0) {
                startOffset = getChildAt(count - 1).getRight() + this.mDividerWidth;
            } else {
                startOffset = 0;
            }
            fillRight(this.mFirstPosition + count, startOffset);
            correctTooWide(getChildCount());
            return;
        }
        fillLeft(this.mFirstPosition - 1, count > 0 ? getChildAt(0).getLeft() - this.mDividerWidth : getWidth() - 0);
        correctTooSmall(getChildCount());
    }

    private View fillRight(int pos, int nextLeft) {
        View selectedView = null;
        int end = getRight() - getLeft();
        while (nextLeft < end && pos < this.mItemCount) {
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextLeft, true, this.mListPadding.top, selected);
            nextLeft = child.getRight() + this.mDividerWidth;
            if (selected) {
                selectedView = child;
            }
            pos++;
        }
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillLeft(int pos, int nextRight) {
        View selectedView = null;
        while (nextRight > 0 && pos >= 0) {
            boolean selected;
            if (pos == this.mSelectedPosition) {
                selected = true;
            } else {
                selected = false;
            }
            View child = makeAndAddView(pos, nextRight, false, this.mListPadding.top, selected);
            nextRight = child.getLeft() - this.mDividerWidth;
            if (selected) {
                selectedView = child;
            }
            pos--;
        }
        this.mFirstPosition = pos + 1;
        setVisibleRangeHint(this.mFirstPosition, (this.mFirstPosition + getChildCount()) - 1);
        return selectedView;
    }

    private View fillFromLeft(int nextLeft) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillRight(this.mFirstPosition, nextLeft);
    }

    private View fillFromMiddle(int childrenLeft, int childrenRight) {
        int width = childrenRight - childrenLeft;
        int position = reconcileSelectedPosition();
        View sel = makeAndAddView(position, childrenLeft, true, this.mListPadding.top, true);
        this.mFirstPosition = position;
        int selWidth = sel.getMeasuredWidth();
        if (selWidth <= width) {
            sel.offsetLeftAndRight((width - selWidth) / 2);
        }
        fillBeforeAndAfter(sel, position);
        if (this.mStackFromRight) {
            correctTooSmall(getChildCount());
        } else {
            correctTooWide(getChildCount());
        }
        return sel;
    }

    private void fillBeforeAndAfter(View sel, int position) {
        int dividerWidth = this.mDividerWidth;
        if (this.mStackFromRight) {
            fillRight(position + 1, sel.getRight() + dividerWidth);
            adjustViewsLeftOrRight();
            fillLeft(position - 1, sel.getLeft() - dividerWidth);
            return;
        }
        fillLeft(position - 1, sel.getLeft() - dividerWidth);
        adjustViewsLeftOrRight();
        fillRight(position + 1, sel.getRight() + dividerWidth);
    }

    private View fillFromSelection(int selectedLeft, int childrenLeft, int childrenRight) {
        int fadingEdgeLength = getHorizontalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int leftSelectionPixel = getLeftSelectionPixel(childrenLeft, fadingEdgeLength, selectedPosition);
        int rightSelectionPixel = getRightSelectionPixel(childrenRight, fadingEdgeLength, selectedPosition);
        View sel = makeAndAddView(selectedPosition, selectedLeft, true, this.mListPadding.top, true);
        if (sel.getRight() > rightSelectionPixel) {
            sel.offsetLeftAndRight(-Math.min(sel.getLeft() - leftSelectionPixel, sel.getRight() - rightSelectionPixel));
        } else if (sel.getLeft() < leftSelectionPixel) {
            sel.offsetLeftAndRight(Math.min(leftSelectionPixel - sel.getLeft(), rightSelectionPixel - sel.getRight()));
        }
        fillBeforeAndAfter(sel, selectedPosition);
        if (this.mStackFromRight) {
            correctTooSmall(getChildCount());
        } else {
            correctTooWide(getChildCount());
        }
        return sel;
    }

    private int getRightSelectionPixel(int childrenRight, int fadingEdgeLength, int selectedPosition) {
        int rightSelectionPixel = childrenRight;
        if (selectedPosition != this.mItemCount - 1) {
            return rightSelectionPixel - fadingEdgeLength;
        }
        return rightSelectionPixel;
    }

    private int getLeftSelectionPixel(int childrenLeft, int fadingEdgeLength, int selectedPosition) {
        int leftSelectionPixel = childrenLeft;
        if (selectedPosition > 0) {
            return leftSelectionPixel + fadingEdgeLength;
        }
        return leftSelectionPixel;
    }

    public void smoothScrollToPosition(int position) {
        super.smoothScrollToPosition(position);
    }

    public void smoothScrollByOffset(int offset) {
        super.smoothScrollByOffset(offset);
    }

    private View moveSelection(View oldSel, View newSel, int delta, int childrenLeft, int childrenRight) {
        View sel;
        int fadingEdgeLength = getHorizontalFadingEdgeLength();
        int selectedPosition = this.mSelectedPosition;
        int leftSelectionPixel = getLeftSelectionPixel(childrenLeft, fadingEdgeLength, selectedPosition);
        int rightSelectionPixel = getRightSelectionPixel(childrenLeft, fadingEdgeLength, selectedPosition);
        int halfHorizontalSpace;
        if (delta > 0) {
            oldSel = makeAndAddView(selectedPosition - 1, oldSel.getLeft(), true, this.mListPadding.top, false);
            int dividerWidth = this.mDividerWidth;
            sel = makeAndAddView(selectedPosition, oldSel.getRight() + dividerWidth, true, this.mListPadding.top, true);
            if (sel.getRight() > rightSelectionPixel) {
                halfHorizontalSpace = (childrenRight - childrenLeft) / 2;
                int offset = Math.min(Math.min(sel.getLeft() - leftSelectionPixel, sel.getRight() - rightSelectionPixel), halfHorizontalSpace);
                oldSel.offsetLeftAndRight(-offset);
                sel.offsetLeftAndRight(-offset);
            }
            if (this.mStackFromRight) {
                fillRight(this.mSelectedPosition + 1, sel.getRight() + dividerWidth);
                adjustViewsLeftOrRight();
                fillLeft(this.mSelectedPosition - 2, sel.getLeft() - dividerWidth);
            } else {
                fillLeft(this.mSelectedPosition - 2, sel.getLeft() - dividerWidth);
                adjustViewsLeftOrRight();
                fillRight(this.mSelectedPosition + 1, sel.getRight() + dividerWidth);
            }
        } else if (delta < 0) {
            if (newSel != null) {
                sel = makeAndAddView(selectedPosition, newSel.getLeft(), true, this.mListPadding.top, true);
            } else {
                sel = makeAndAddView(selectedPosition, oldSel.getLeft(), false, this.mListPadding.top, true);
            }
            if (sel.getLeft() < leftSelectionPixel) {
                halfHorizontalSpace = (childrenRight - childrenLeft) / 2;
                View view = sel;
                view.offsetLeftAndRight(Math.min(Math.min(leftSelectionPixel - sel.getLeft(), rightSelectionPixel - sel.getRight()), halfHorizontalSpace));
            }
            fillBeforeAndAfter(sel, selectedPosition);
        } else {
            int oldLeft = oldSel.getLeft();
            sel = makeAndAddView(selectedPosition, oldLeft, true, this.mListPadding.top, true);
            if (oldLeft < childrenLeft && sel.getRight() < childrenLeft + 20) {
                sel.offsetLeftAndRight(childrenLeft - sel.getLeft());
            }
            fillBeforeAndAfter(sel, selectedPosition);
        }
        return sel;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (getChildCount() > 0) {
            View focusedChild = getFocusedChild();
            if (focusedChild != null) {
                int childPosition = this.mFirstPosition + indexOfChild(focusedChild);
                int left = focusedChild.getLeft() - Math.max(0, focusedChild.getRight() - (w - getPaddingLeft()));
                if (this.mFocusSelector == null) {
                    this.mFocusSelector = new FocusSelector(this, null);
                }
                post(this.mFocusSelector.setup(childPosition, left));
            }
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @TargetApi(11)
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = 0;
        int childHeight = 0;
        int childState = 0;
        if (this.mAdapter == null) {
            i = 0;
        } else {
            i = this.mAdapter.getCount();
        }
        this.mItemCount = i;
        if (this.mItemCount > 0 && (widthMode == 0 || heightMode == 0)) {
            View child = obtainView(0, this.mIsScrap);
            measureScrapChildWidth(child, 0, heightMeasureSpec);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            if (VERSION.SDK_INT >= 11) {
                childState = combineMeasuredStates(0, child.getMeasuredState());
            }
            if (recycleOnMeasure() && this.mRecycler.shouldRecycleViewType(((LayoutParams) child.getLayoutParams()).viewType)) {
                this.mRecycler.addScrapView(child, -1);
            }
        }
        if (heightMode == 0) {
            heightSize = ((this.mListPadding.top + this.mListPadding.bottom) + childHeight) + getHorizontalScrollbarHeight();
        } else if (heightMode == Integer.MIN_VALUE && this.mItemCount > 0 && this.mMeasureWithChild > -1) {
            heightSize = measureWithLargeChildren(heightMeasureSpec, this.mMeasureWithChild, this.mMeasureWithChild, widthSize, heightSize, -1)[1];
        } else if (VERSION.SDK_INT >= 11) {
            heightSize |= -16777216 & childState;
        }
        if (widthMode == 0) {
            widthSize = ((this.mListPadding.left + this.mListPadding.right) + childWidth) + (getHorizontalFadingEdgeLength() * 2);
        }
        if (widthMode == Integer.MIN_VALUE) {
            widthSize = measureWidthOfChildren(heightMeasureSpec, 0, -1, widthSize, -1);
        }
        setMeasuredDimension(widthSize, heightSize);
        this.mHeightMeasureSpec = heightMeasureSpec;
    }

    private void measureScrapChildWidth(View child, int position, int heightMeasureSpec) {
        int childWidthSpec;
        LayoutParams p = (LayoutParams) child.getLayoutParams();
        if (p == null) {
            p = (LayoutParams) generateDefaultLayoutParams();
            child.setLayoutParams(p);
        }
        p.viewType = this.mAdapter.getItemViewType(position);
        p.forceAdd = true;
        int childHeightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, this.mListPadding.top + this.mListPadding.bottom, p.height);
        int lpWidth = p.width;
        if (lpWidth > 0) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
        } else {
            childWidthSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public int[] measureChild(View child) {
        measureItem(child);
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        return new int[]{w, h};
    }

    @ExportedProperty(category = "list")
    protected boolean recycleOnMeasure() {
        return true;
    }

    final int measureWidthOfChildren(int heightMeasureSpec, int startPosition, int endPosition, int maxWidth, int disallowPartialChildPosition) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return this.mListPadding.left + this.mListPadding.right;
        }
        int returnedWidth = this.mListPadding.left + this.mListPadding.right;
        int dividerWidth = (this.mDividerWidth <= 0 || this.mDivider == null) ? 0 : this.mDividerWidth;
        int prevWidthWithoutPartialChild = 0;
        if (endPosition == -1) {
            endPosition = adapter.getCount() - 1;
        }
        RecycleBin recycleBin = this.mRecycler;
        boolean recyle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        int i = startPosition;
        while (i <= endPosition) {
            View child = obtainView(i, isScrap);
            measureScrapChildWidth(child, i, heightMeasureSpec);
            if (i > 0) {
                returnedWidth += dividerWidth;
            }
            if (recyle && recycleBin.shouldRecycleViewType(((LayoutParams) child.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(child, -1);
            }
            returnedWidth += child.getMeasuredWidth();
            if (returnedWidth < maxWidth) {
                if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                    prevWidthWithoutPartialChild = returnedWidth;
                }
                i++;
            } else if (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevWidthWithoutPartialChild <= 0 || returnedWidth == maxWidth) {
                return maxWidth;
            } else {
                return prevWidthWithoutPartialChild;
            }
        }
        return returnedWidth;
    }

    final int[] measureWithLargeChildren(int heightMeasureSpec, int startPosition, int endPosition, int maxWidth, int maxHeight, int disallowPartialChildPosition) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return new int[]{this.mListPadding.left + this.mListPadding.right, this.mListPadding.top + this.mListPadding.bottom};
        }
        int returnedWidth = this.mListPadding.left + this.mListPadding.right;
        int returnedHeight = this.mListPadding.top + this.mListPadding.bottom;
        int dividerWidth = (this.mDividerWidth <= 0 || this.mDivider == null) ? 0 : this.mDividerWidth;
        int childWidth = 0;
        int childHeight = 0;
        if (endPosition == -1) {
            endPosition = adapter.getCount() - 1;
        }
        RecycleBin recycleBin = this.mRecycler;
        boolean recyle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        for (int i = startPosition; i <= endPosition; i++) {
            View child = obtainView(i, isScrap);
            measureScrapChildWidth(child, i, heightMeasureSpec);
            if (recyle && recycleBin.shouldRecycleViewType(((LayoutParams) child.getLayoutParams()).viewType)) {
                recycleBin.addScrapView(child, -1);
            }
            childWidth = Math.max(childWidth, child.getMeasuredWidth() + dividerWidth);
            childHeight = Math.max(childHeight, child.getMeasuredHeight());
        }
        returnedHeight += childHeight;
        return new int[]{Math.min(returnedWidth + childWidth, maxWidth), Math.min(returnedHeight, maxHeight)};
    }

    protected int findMotionCol(int x) {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i;
            if (this.mStackFromRight) {
                for (i = childCount - 1; i >= 0; i--) {
                    if (x >= getChildAt(i).getLeft()) {
                        return this.mFirstPosition + i;
                    }
                }
            } else {
                for (i = 0; i < childCount; i++) {
                    if (x <= getChildAt(i).getRight()) {
                        return this.mFirstPosition + i;
                    }
                }
            }
        }
        return -1;
    }

    private View fillSpecific(int position, int left) {
        View after;
        View before;
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, left, true, this.mListPadding.top, tempIsSelected);
        this.mFirstPosition = position;
        int dividerWidth = this.mDividerWidth;
        int childCount;
        if (this.mStackFromRight) {
            after = fillRight(position + 1, temp.getRight() + dividerWidth);
            adjustViewsLeftOrRight();
            before = fillLeft(position - 1, temp.getLeft() - dividerWidth);
            childCount = getChildCount();
            if (childCount > 0) {
                correctTooSmall(childCount);
            }
        } else {
            before = fillLeft(position - 1, temp.getLeft() - dividerWidth);
            adjustViewsLeftOrRight();
            after = fillRight(position + 1, temp.getRight() + dividerWidth);
            childCount = getChildCount();
            if (childCount > 0) {
                correctTooWide(childCount);
            }
        }
        if (tempIsSelected) {
            return temp;
        }
        if (before != null) {
            return before;
        }
        return after;
    }

    private void correctTooWide(int childCount) {
        if ((this.mFirstPosition + childCount) - 1 == this.mItemCount - 1 && childCount > 0) {
            int rightOffset = ((getRight() - getLeft()) - this.mListPadding.right) - getChildAt(childCount - 1).getRight();
            View firstChild = getChildAt(0);
            int firstLeft = firstChild.getLeft();
            if (rightOffset <= 0) {
                return;
            }
            if (this.mFirstPosition > 0 || firstLeft < this.mListPadding.top) {
                if (this.mFirstPosition == 0) {
                    rightOffset = Math.min(rightOffset, this.mListPadding.top - firstLeft);
                }
                offsetChildrenLeftAndRight(rightOffset);
                if (this.mFirstPosition > 0) {
                    fillLeft(this.mFirstPosition - 1, firstChild.getLeft() - this.mDividerWidth);
                    adjustViewsLeftOrRight();
                }
            }
        }
    }

    private void correctTooSmall(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            int end = (getRight() - getLeft()) - this.mListPadding.right;
            int leftOffset = getChildAt(0).getLeft() - this.mListPadding.left;
            View lastChild = getChildAt(childCount - 1);
            int lastRight = lastChild.getRight();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (leftOffset <= 0) {
                return;
            }
            if (lastPosition < this.mItemCount - 1 || lastRight > end) {
                if (lastPosition == this.mItemCount - 1) {
                    leftOffset = Math.min(leftOffset, lastRight - end);
                }
                offsetChildrenLeftAndRight(-leftOffset);
                if (lastPosition < this.mItemCount - 1) {
                    fillRight(lastPosition + 1, lastChild.getRight() + this.mDividerWidth);
                    adjustViewsLeftOrRight();
                }
            } else if (lastPosition == this.mItemCount - 1) {
                adjustViewsLeftOrRight();
            }
        }
    }

    protected void layoutChildren() {
        boolean blockLayoutRequests = this.mBlockLayoutRequests;
        if (!blockLayoutRequests) {
            this.mBlockLayoutRequests = true;
            try {
                super.layoutChildren();
                invalidate();
                if (this.mAdapter == null) {
                    resetList();
                    invokeOnItemScrollListener();
                    if (!blockLayoutRequests) {
                        this.mBlockLayoutRequests = false;
                        return;
                    }
                    return;
                }
                boolean dataChanged;
                int firstPosition;
                RecycleBin recycleBin;
                View focusLayoutRestoreDirectChild;
                int i;
                View focusedChild;
                View sel;
                int i2;
                int childrenLeft = this.mListPadding.left;
                int childrenRight = (getRight() - getLeft()) - this.mListPadding.right;
                int childCount = getChildCount();
                int delta = 0;
                View oldSel = null;
                View oldFirst = null;
                View newSel = null;
                View focusLayoutRestoreView = null;
                int index;
                switch (this.mLayoutMode) {
                    case 2:
                        index = this.mNextSelectedPosition - this.mFirstPosition;
                        if (index >= 0 && index < childCount) {
                            newSel = getChildAt(index);
                        }
                    case 1:
                    case 3:
                    case 4:
                    case 5:
                        dataChanged = this.mDataChanged;
                        if (dataChanged) {
                            handleDataChanged();
                        }
                        if (this.mItemCount == 0) {
                            resetList();
                            invokeOnItemScrollListener();
                            if (!blockLayoutRequests) {
                                this.mBlockLayoutRequests = false;
                            }
                        } else if (this.mItemCount == this.mAdapter.getCount()) {
                            throw new IllegalStateException("The content of the adapter has changed but ListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in ListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                        } else {
                            setSelectedPositionInt(this.mNextSelectedPosition);
                            firstPosition = this.mFirstPosition;
                            recycleBin = this.mRecycler;
                            focusLayoutRestoreDirectChild = null;
                            if (dataChanged) {
                                recycleBin.fillActiveViews(childCount, firstPosition);
                            } else {
                                for (i = 0; i < childCount; i++) {
                                    recycleBin.addScrapView(getChildAt(i), firstPosition + i);
                                }
                            }
                            focusedChild = getFocusedChild();
                            if (focusedChild != null) {
                                if (!dataChanged || isDirectChildHeaderOrFooter(focusedChild)) {
                                    focusLayoutRestoreDirectChild = focusedChild;
                                    focusLayoutRestoreView = findFocus();
                                    if (focusLayoutRestoreView != null) {
                                        focusLayoutRestoreView.onStartTemporaryDetach();
                                    }
                                }
                                requestFocus();
                            }
                            detachAllViewsFromParent();
                            recycleBin.removeSkippedScrap();
                            switch (this.mLayoutMode) {
                                case 1:
                                    this.mFirstPosition = 0;
                                    sel = fillFromLeft(childrenLeft);
                                    adjustViewsLeftOrRight();
                                    break;
                                case 2:
                                    if (newSel == null) {
                                        sel = fillFromMiddle(childrenLeft, childrenRight);
                                        break;
                                    } else {
                                        sel = fillFromSelection(newSel.getLeft(), childrenLeft, childrenRight);
                                        break;
                                    }
                                case 3:
                                    sel = fillLeft(this.mItemCount - 1, childrenRight);
                                    adjustViewsLeftOrRight();
                                    break;
                                case 4:
                                    sel = fillSpecific(reconcileSelectedPosition(), this.mSpecificLeft);
                                    break;
                                case 5:
                                    sel = fillSpecific(this.mSyncPosition, this.mSpecificLeft);
                                    break;
                                case 6:
                                    sel = moveSelection(oldSel, newSel, delta, childrenLeft, childrenRight);
                                    break;
                                default:
                                    if (childCount != 0) {
                                        if (this.mSelectedPosition >= 0 || this.mSelectedPosition >= this.mItemCount) {
                                            if (this.mFirstPosition >= this.mItemCount) {
                                                sel = fillSpecific(0, childrenLeft);
                                                break;
                                            }
                                            i2 = this.mFirstPosition;
                                            if (oldFirst != null) {
                                                childrenLeft = oldFirst.getLeft();
                                            }
                                            sel = fillSpecific(i2, childrenLeft);
                                            break;
                                        }
                                        i2 = this.mSelectedPosition;
                                        if (oldSel != null) {
                                            childrenLeft = oldSel.getLeft();
                                        }
                                        sel = fillSpecific(i2, childrenLeft);
                                        break;
                                    } else if (this.mStackFromRight) {
                                        setSelectedPositionInt(lookForSelectablePosition(this.mItemCount - 1, false));
                                        sel = fillLeft(this.mItemCount - 1, childrenRight);
                                        break;
                                    } else {
                                        setSelectedPositionInt(lookForSelectablePosition(0, true));
                                        sel = fillFromLeft(childrenLeft);
                                        break;
                                    }
                            }
                            recycleBin.scrapActiveViews();
                            if (sel == null) {
                                if (this.mItemsCanFocus || !hasFocus() || sel.hasFocus()) {
                                    positionSelector(-1, sel);
                                } else {
                                    boolean focusWasTaken = (sel == focusLayoutRestoreDirectChild && focusLayoutRestoreView != null && focusLayoutRestoreView.requestFocus()) || sel.requestFocus();
                                    if (focusWasTaken) {
                                        sel.setSelected(false);
                                        this.mSelectorRect.setEmpty();
                                    } else {
                                        View focused = getFocusedChild();
                                        if (focused != null) {
                                            focused.clearFocus();
                                        }
                                        positionSelector(-1, sel);
                                    }
                                }
                                this.mSelectedLeft = sel.getLeft();
                            } else {
                                if (this.mTouchMode > 0 || this.mTouchMode >= 3) {
                                    this.mSelectedLeft = 0;
                                    this.mSelectorRect.setEmpty();
                                } else {
                                    View child = getChildAt(this.mMotionPosition - this.mFirstPosition);
                                    if (child != null) {
                                        positionSelector(this.mMotionPosition, child);
                                    }
                                }
                                if (hasFocus() && focusLayoutRestoreView != null) {
                                    focusLayoutRestoreView.requestFocus();
                                }
                            }
                            if (!(focusLayoutRestoreView == null || focusLayoutRestoreView.getWindowToken() == null)) {
                                focusLayoutRestoreView.onFinishTemporaryDetach();
                            }
                            this.mLayoutMode = 0;
                            this.mDataChanged = false;
                            if (this.mPositionScrollAfterLayout != null) {
                                post(this.mPositionScrollAfterLayout);
                                this.mPositionScrollAfterLayout = null;
                            }
                            this.mNeedSync = false;
                            setNextSelectedPositionInt(this.mSelectedPosition);
                            updateScrollIndicators();
                            if (this.mItemCount > 0) {
                                checkSelectionChanged();
                            }
                            invokeOnItemScrollListener();
                            if (!blockLayoutRequests) {
                                this.mBlockLayoutRequests = false;
                            }
                        }
                    default:
                        index = this.mSelectedPosition - this.mFirstPosition;
                        if (index >= 0 && index < childCount) {
                            oldSel = getChildAt(index);
                        }
                        oldFirst = getChildAt(0);
                        if (this.mNextSelectedPosition >= 0) {
                            delta = this.mNextSelectedPosition - this.mSelectedPosition;
                        }
                        newSel = getChildAt(index + delta);
                }
                dataChanged = this.mDataChanged;
                if (dataChanged) {
                    handleDataChanged();
                }
                if (this.mItemCount == 0) {
                    resetList();
                    invokeOnItemScrollListener();
                    if (!blockLayoutRequests) {
                        this.mBlockLayoutRequests = false;
                    }
                } else if (this.mItemCount == this.mAdapter.getCount()) {
                    setSelectedPositionInt(this.mNextSelectedPosition);
                    firstPosition = this.mFirstPosition;
                    recycleBin = this.mRecycler;
                    focusLayoutRestoreDirectChild = null;
                    if (dataChanged) {
                        recycleBin.fillActiveViews(childCount, firstPosition);
                    } else {
                        for (i = 0; i < childCount; i++) {
                            recycleBin.addScrapView(getChildAt(i), firstPosition + i);
                        }
                    }
                    focusedChild = getFocusedChild();
                    if (focusedChild != null) {
                        focusLayoutRestoreDirectChild = focusedChild;
                        focusLayoutRestoreView = findFocus();
                        if (focusLayoutRestoreView != null) {
                            focusLayoutRestoreView.onStartTemporaryDetach();
                        }
                        requestFocus();
                    }
                    detachAllViewsFromParent();
                    recycleBin.removeSkippedScrap();
                    switch (this.mLayoutMode) {
                        case 1:
                            this.mFirstPosition = 0;
                            sel = fillFromLeft(childrenLeft);
                            adjustViewsLeftOrRight();
                            break;
                        case 2:
                            if (newSel == null) {
                                sel = fillFromSelection(newSel.getLeft(), childrenLeft, childrenRight);
                                break;
                            } else {
                                sel = fillFromMiddle(childrenLeft, childrenRight);
                                break;
                            }
                        case 3:
                            sel = fillLeft(this.mItemCount - 1, childrenRight);
                            adjustViewsLeftOrRight();
                            break;
                        case 4:
                            sel = fillSpecific(reconcileSelectedPosition(), this.mSpecificLeft);
                            break;
                        case 5:
                            sel = fillSpecific(this.mSyncPosition, this.mSpecificLeft);
                            break;
                        case 6:
                            sel = moveSelection(oldSel, newSel, delta, childrenLeft, childrenRight);
                            break;
                        default:
                            if (childCount != 0) {
                                if (this.mStackFromRight) {
                                    setSelectedPositionInt(lookForSelectablePosition(0, true));
                                    sel = fillFromLeft(childrenLeft);
                                    break;
                                }
                                setSelectedPositionInt(lookForSelectablePosition(this.mItemCount - 1, false));
                                sel = fillLeft(this.mItemCount - 1, childrenRight);
                                break;
                            }
                            if (this.mSelectedPosition >= 0) {
                                break;
                            }
                            if (this.mFirstPosition >= this.mItemCount) {
                                i2 = this.mFirstPosition;
                                if (oldFirst != null) {
                                    childrenLeft = oldFirst.getLeft();
                                }
                                sel = fillSpecific(i2, childrenLeft);
                                break;
                            }
                            sel = fillSpecific(0, childrenLeft);
                            break;
                    }
                    recycleBin.scrapActiveViews();
                    if (sel == null) {
                        if (this.mTouchMode > 0) {
                        }
                        this.mSelectedLeft = 0;
                        this.mSelectorRect.setEmpty();
                        focusLayoutRestoreView.requestFocus();
                    } else {
                        if (this.mItemsCanFocus) {
                        }
                        positionSelector(-1, sel);
                        this.mSelectedLeft = sel.getLeft();
                    }
                    focusLayoutRestoreView.onFinishTemporaryDetach();
                    this.mLayoutMode = 0;
                    this.mDataChanged = false;
                    if (this.mPositionScrollAfterLayout != null) {
                        post(this.mPositionScrollAfterLayout);
                        this.mPositionScrollAfterLayout = null;
                    }
                    this.mNeedSync = false;
                    setNextSelectedPositionInt(this.mSelectedPosition);
                    updateScrollIndicators();
                    if (this.mItemCount > 0) {
                        checkSelectionChanged();
                    }
                    invokeOnItemScrollListener();
                    if (!blockLayoutRequests) {
                        this.mBlockLayoutRequests = false;
                    }
                } else {
                    throw new IllegalStateException("The content of the adapter has changed but ListView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in ListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                }
            } catch (Throwable th) {
                if (!blockLayoutRequests) {
                    this.mBlockLayoutRequests = false;
                }
            }
        }
    }

    private View findAccessibilityFocusedChild(View focusedView) {
        View viewParent = focusedView.getParent();
        while ((viewParent instanceof View) && viewParent != this) {
            focusedView = viewParent;
            viewParent = viewParent.getParent();
        }
        if (viewParent instanceof View) {
            return focusedView;
        }
        return null;
    }

    private boolean isDirectChildHeaderOrFooter(View child) {
        int i;
        ArrayList<FixedViewInfo> headers = this.mHeaderViewInfos;
        int numHeaders = headers.size();
        for (i = 0; i < numHeaders; i++) {
            if (child == ((FixedViewInfo) headers.get(i)).view) {
                return true;
            }
        }
        ArrayList<FixedViewInfo> footers = this.mFooterViewInfos;
        int numFooters = footers.size();
        for (i = 0; i < numFooters; i++) {
            if (child == ((FixedViewInfo) footers.get(i)).view) {
                return true;
            }
        }
        return false;
    }

    private View makeAndAddView(int position, int x, boolean flow, int childrenTop, boolean selected) {
        View child;
        if (!this.mDataChanged) {
            child = this.mRecycler.getActiveView(position);
            if (child != null) {
                setupChild(child, position, x, flow, childrenTop, selected, true);
                return child;
            }
        }
        child = obtainView(position, this.mIsScrap);
        setupChild(child, position, x, flow, childrenTop, selected, this.mIsScrap[0]);
        return child;
    }

    @TargetApi(11)
    private void setupChild(View child, int position, int x, boolean flowDown, int childrenTop, boolean selected, boolean recycled) {
        boolean isSelected = selected && shouldShowSelector();
        boolean updateChildSelected = isSelected != child.isSelected();
        int mode = this.mTouchMode;
        boolean isPressed = mode > 0 && mode < 3 && this.mMotionPosition == position;
        boolean updateChildPressed = isPressed != child.isPressed();
        boolean needToMeasure = !recycled || updateChildSelected || child.isLayoutRequested();
        LayoutParams p = (LayoutParams) child.getLayoutParams();
        if (p == null) {
            p = (LayoutParams) generateDefaultLayoutParams();
        }
        p.viewType = this.mAdapter.getItemViewType(position);
        if ((!recycled || p.forceAdd) && !(p.recycledHeaderFooter && p.viewType == -2)) {
            p.forceAdd = false;
            if (p.viewType == -2) {
                p.recycledHeaderFooter = true;
            }
            addViewInLayout(child, flowDown ? -1 : 0, p, true);
        } else {
            attachViewToParent(child, flowDown ? -1 : 0, p);
        }
        if (updateChildSelected) {
            child.setSelected(isSelected);
        }
        if (updateChildPressed) {
            child.setPressed(isPressed);
        }
        if (!(this.mChoiceMode == 0 || this.mCheckStates == null)) {
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue());
            } else if (VERSION.SDK_INT >= 11) {
                child.setActivated(((Boolean) this.mCheckStates.get(position, Boolean.valueOf(false))).booleanValue());
            }
        }
        if (needToMeasure) {
            int childWidthSpec;
            int childHeightSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mListPadding.top + this.mListPadding.bottom, p.height);
            int lpWidth = p.width;
            if (lpWidth > 0) {
                childWidthSpec = MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
            } else {
                childWidthSpec = MeasureSpec.makeMeasureSpec(0, 0);
            }
            child.measure(childWidthSpec, childHeightSpec);
        } else {
            cleanupLayoutState(child);
        }
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childLeft = flowDown ? x : x - w;
        if (needToMeasure) {
            child.layout(childLeft, childrenTop, childLeft + w, childrenTop + h);
        } else {
            child.offsetLeftAndRight(childLeft - child.getLeft());
            child.offsetTopAndBottom(childrenTop - child.getTop());
        }
        if (this.mCachingStarted && !child.isDrawingCacheEnabled()) {
            child.setDrawingCacheEnabled(true);
        }
        if (VERSION.SDK_INT >= 11 && recycled && ((LayoutParams) child.getLayoutParams()).scrappedFromPosition != position) {
            child.jumpDrawablesToCurrentState();
        }
    }

    protected boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    public void setSelection(int position) {
        setSelectionFromLeft(position, 0);
    }

    public void setSelectionFromLeft(int position, int x) {
        if (this.mAdapter != null) {
            if (isInTouchMode()) {
                this.mResurrectToPosition = position;
            } else {
                position = lookForSelectablePosition(position, true);
                if (position >= 0) {
                    setNextSelectedPositionInt(position);
                }
            }
            if (position >= 0) {
                this.mLayoutMode = 4;
                this.mSpecificLeft = this.mListPadding.left + x;
                if (this.mNeedSync) {
                    this.mSyncPosition = position;
                    this.mSyncColId = this.mAdapter.getItemId(position);
                }
                if (this.mPositionScroller != null) {
                    this.mPositionScroller.stop();
                }
                requestLayout();
            }
        }
    }

    public void setSelectionInt(int position) {
        setNextSelectedPositionInt(position);
        boolean awakeScrollbars = false;
        int selectedPosition = this.mSelectedPosition;
        if (selectedPosition >= 0) {
            if (position == selectedPosition - 1) {
                awakeScrollbars = true;
            } else if (position == selectedPosition + 1) {
                awakeScrollbars = true;
            }
        }
        if (this.mPositionScroller != null) {
            this.mPositionScroller.stop();
        }
        layoutChildren();
        if (awakeScrollbars) {
            awakenScrollBars();
        }
    }

    protected int lookForSelectablePosition(int position, boolean lookDown) {
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int count = adapter.getCount();
        if (!this.mAreAllItemsSelectable) {
            if (lookDown) {
                position = Math.max(0, position);
                while (position < count && !adapter.isEnabled(position)) {
                    position++;
                }
            } else {
                position = Math.min(position, count - 1);
                while (position >= 0 && !adapter.isEnabled(position)) {
                    position--;
                }
            }
            if (position < 0 || position >= count) {
                return -1;
            }
            return position;
        } else if (position < 0 || position >= count) {
            return -1;
        } else {
            return position;
        }
    }

    public void setSelectionAfterHeaderView() {
        int count = this.mHeaderViewInfos.size();
        if (count > 0) {
            this.mNextSelectedPosition = 0;
        } else if (this.mAdapter != null) {
            setSelection(count);
        } else {
            this.mNextSelectedPosition = count;
            this.mLayoutMode = 2;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (handled || getFocusedChild() == null || event.getAction() != 0) {
            return handled;
        }
        return onKeyDown(event.getKeyCode(), event);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return commonKey(keyCode, repeatCount, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return commonKey(keyCode, 1, event);
    }

    @TargetApi(11)
    private boolean commonKey(int keyCode, int count, KeyEvent event) {
        if (this.mAdapter == null || !this.mIsAttached) {
            return false;
        }
        if (this.mDataChanged) {
            layoutChildren();
        }
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        boolean handled = false;
        int action = event.getAction();
        if (action != 1) {
            int count2;
            switch (keyCode) {
                case 19:
                    if (event.hasNoModifiers()) {
                        handled = handleHorizontalFocusWithinListItem(33);
                        break;
                    }
                    break;
                case 20:
                    if (event.hasNoModifiers()) {
                        handled = handleHorizontalFocusWithinListItem(130);
                        break;
                    }
                    break;
                case 21:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(33);
                            break;
                        }
                    }
                    handled = resurrectSelectionIfNeeded();
                    if (!handled) {
                        count2 = count;
                        while (true) {
                            count = count2 - 1;
                            if (count2 > 0 && arrowScroll(33)) {
                                handled = true;
                                count2 = count;
                            }
                        }
                    }
                    break;
                case 22:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(130);
                            break;
                        }
                    }
                    handled = resurrectSelectionIfNeeded();
                    if (!handled) {
                        count2 = count;
                        while (true) {
                            count = count2 - 1;
                            if (count2 > 0 && arrowScroll(130)) {
                                handled = true;
                                count2 = count;
                            }
                        }
                    }
                    break;
                case 23:
                case 66:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded();
                        if (!handled && event.getRepeatCount() == 0 && getChildCount() > 0) {
                            keyPressed();
                            handled = true;
                            break;
                        }
                    }
                    break;
                case 62:
                    if (event.hasNoModifiers()) {
                        if (resurrectSelectionIfNeeded() || pageScroll(130)) {
                            handled = true;
                        } else {
                            handled = false;
                        }
                    } else if (event.hasModifiers(1)) {
                        if (resurrectSelectionIfNeeded() || pageScroll(33)) {
                            handled = true;
                        } else {
                            handled = false;
                        }
                    }
                    handled = true;
                    break;
                case 92:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(33);
                            break;
                        }
                    }
                    handled = resurrectSelectionIfNeeded() || pageScroll(33);
                    break;
                    break;
                case 93:
                    if (!event.hasNoModifiers()) {
                        if (event.hasModifiers(2)) {
                            handled = resurrectSelectionIfNeeded() || fullScroll(130);
                            break;
                        }
                    }
                    handled = resurrectSelectionIfNeeded() || pageScroll(130);
                    break;
                    break;
                case 122:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(33);
                        break;
                    }
                    break;
                case 123:
                    if (event.hasNoModifiers()) {
                        handled = resurrectSelectionIfNeeded() || fullScroll(130);
                        break;
                    }
                    break;
            }
        }
        if (handled) {
            return true;
        }
        switch (action) {
            case 0:
                return super.onKeyDown(keyCode, event);
            case 1:
                return super.onKeyUp(keyCode, event);
            case 2:
                return super.onKeyMultiple(keyCode, count, event);
            default:
                return false;
        }
    }

    boolean pageScroll(int direction) {
        int nextPage = -1;
        boolean down = false;
        if (direction == 33) {
            nextPage = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
        } else if (direction == 130) {
            nextPage = Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
            down = true;
        }
        if (nextPage >= 0) {
            int position = lookForSelectablePosition(nextPage, down);
            if (position >= 0) {
                this.mLayoutMode = 4;
                this.mSpecificLeft = getPaddingLeft() + getHorizontalFadingEdgeLength();
                if (down && position > this.mItemCount - getChildCount()) {
                    this.mLayoutMode = 3;
                }
                if (!down && position < getChildCount()) {
                    this.mLayoutMode = 1;
                }
                setSelectionInt(position);
                invokeOnItemScrollListener();
                if (awakenScrollBars()) {
                    return true;
                }
                invalidate();
                return true;
            }
        }
        return false;
    }

    boolean fullScroll(int direction) {
        boolean moved = false;
        int position;
        if (direction == 33) {
            if (this.mSelectedPosition != 0) {
                position = lookForSelectablePosition(0, true);
                if (position >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(position);
                    invokeOnItemScrollListener();
                }
                moved = true;
            }
        } else if (direction == 130 && this.mSelectedPosition < this.mItemCount - 1) {
            position = lookForSelectablePosition(this.mItemCount - 1, true);
            if (position >= 0) {
                this.mLayoutMode = 3;
                setSelectionInt(position);
                invokeOnItemScrollListener();
            }
            moved = true;
        }
        if (moved && !awakenScrollBars()) {
            awakenScrollBars();
            invalidate();
        }
        return moved;
    }

    private boolean handleHorizontalFocusWithinListItem(int direction) {
        if (direction == 33 || direction == 130) {
            int numChildren = getChildCount();
            if (this.mItemsCanFocus && numChildren > 0 && this.mSelectedPosition != -1) {
                View selectedView = getSelectedView();
                if (selectedView != null && selectedView.hasFocus() && (selectedView instanceof ViewGroup)) {
                    View currentFocus = selectedView.findFocus();
                    View nextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, currentFocus, direction);
                    if (nextFocus != null) {
                        currentFocus.getFocusedRect(this.mTempRect);
                        offsetDescendantRectToMyCoords(currentFocus, this.mTempRect);
                        offsetRectIntoDescendantCoords(nextFocus, this.mTempRect);
                        if (nextFocus.requestFocus(direction, this.mTempRect)) {
                            return true;
                        }
                    }
                    View globalNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), currentFocus, direction);
                    if (globalNextFocus != null) {
                        return isViewAncestorOf(globalNextFocus, this);
                    }
                }
            }
            return false;
        }
        throw new IllegalArgumentException("direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN}");
    }

    boolean arrowScroll(int direction) {
        try {
            this.mInLayout = true;
            boolean handled = arrowScrollImpl(direction);
            if (handled) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
            this.mInLayout = false;
            return handled;
        } catch (Throwable th) {
            this.mInLayout = false;
        }
    }

    private boolean arrowScrollImpl(int direction) {
        if (getChildCount() <= 0) {
            return false;
        }
        boolean needToRedraw;
        View focused;
        View selectedView = getSelectedView();
        int selectedPos = this.mSelectedPosition;
        int nextSelectedPosition = lookForSelectablePositionOnScreen(direction);
        int amountToScroll = amountToScroll(direction, nextSelectedPosition);
        ArrowScrollFocusResult focusResult = this.mItemsCanFocus ? arrowScrollFocused(direction) : null;
        if (focusResult != null) {
            nextSelectedPosition = focusResult.getSelectedPosition();
            amountToScroll = focusResult.getAmountToScroll();
        }
        if (focusResult != null) {
            needToRedraw = true;
        } else {
            needToRedraw = false;
        }
        if (nextSelectedPosition != -1) {
            boolean z;
            if (focusResult != null) {
                z = true;
            } else {
                z = false;
            }
            handleNewSelectionChange(selectedView, direction, nextSelectedPosition, z);
            setSelectedPositionInt(nextSelectedPosition);
            setNextSelectedPositionInt(nextSelectedPosition);
            selectedView = getSelectedView();
            selectedPos = nextSelectedPosition;
            if (this.mItemsCanFocus && focusResult == null) {
                focused = getFocusedChild();
                if (focused != null) {
                    focused.clearFocus();
                }
            }
            needToRedraw = true;
            checkSelectionChanged();
        }
        if (amountToScroll > 0) {
            if (direction != 33) {
                amountToScroll = -amountToScroll;
            }
            scrollListItemsBy(amountToScroll);
            needToRedraw = true;
        }
        if (this.mItemsCanFocus && focusResult == null && selectedView != null && selectedView.hasFocus()) {
            focused = selectedView.findFocus();
            if (!isViewAncestorOf(focused, this) || distanceToView(focused) > 0) {
                focused.clearFocus();
            }
        }
        if (!(nextSelectedPosition != -1 || selectedView == null || isViewAncestorOf(selectedView, this))) {
            selectedView = null;
            hideSelector();
            this.mResurrectToPosition = -1;
        }
        if (!needToRedraw) {
            return false;
        }
        if (selectedView != null) {
            positionSelector(selectedPos, selectedView);
            this.mSelectedLeft = selectedView.getLeft();
        }
        if (!awakenScrollBars()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private void handleNewSelectionChange(View selectedView, int direction, int newSelectedPosition, boolean newFocusAssigned) {
        if (newSelectedPosition == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int leftViewIndex;
        int rightViewIndex;
        View leftView;
        View rightView;
        boolean leftSelected = false;
        int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
        int nextSelectedIndex = newSelectedPosition - this.mFirstPosition;
        if (direction == 33) {
            leftViewIndex = nextSelectedIndex;
            rightViewIndex = selectedIndex;
            leftView = getChildAt(leftViewIndex);
            rightView = selectedView;
            leftSelected = true;
        } else {
            leftViewIndex = selectedIndex;
            rightViewIndex = nextSelectedIndex;
            leftView = selectedView;
            rightView = getChildAt(rightViewIndex);
        }
        int numChildren = getChildCount();
        if (leftView != null) {
            boolean z = !newFocusAssigned && leftSelected;
            leftView.setSelected(z);
            measureAndAdjustRight(leftView, leftViewIndex, numChildren);
        }
        if (rightView != null) {
            z = (newFocusAssigned || leftSelected) ? false : true;
            rightView.setSelected(z);
            measureAndAdjustRight(rightView, rightViewIndex, numChildren);
        }
    }

    private void measureAndAdjustRight(View child, int childIndex, int numChildren) {
        int oldWidth = child.getWidth();
        measureItem(child);
        if (child.getMeasuredWidth() != oldWidth) {
            relayoutMeasuredItem(child);
            int widthDelta = child.getMeasuredWidth() - oldWidth;
            for (int i = childIndex + 1; i < numChildren; i++) {
                getChildAt(i).offsetLeftAndRight(widthDelta);
            }
        }
    }

    private void measureItem(View child) {
        int childWidthSpec;
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(-2, -1);
        }
        int childHeightSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mListPadding.top + this.mListPadding.bottom, p.height);
        int lpWidth = p.width;
        if (lpWidth > 0) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(lpWidth, 1073741824);
        } else {
            childWidthSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    private void relayoutMeasuredItem(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childTop = this.mListPadding.top;
        int childBottom = childTop + h;
        int childLeft = child.getLeft();
        child.layout(childLeft, childTop, childLeft + w, childBottom);
    }

    private int getArrowScrollPreviewLength() {
        return Math.max(2, getHorizontalFadingEdgeLength());
    }

    private int amountToScroll(int direction, int nextSelectedPosition) {
        int listRight = getWidth() - this.mListPadding.right;
        int listLeft = this.mListPadding.left;
        int numChildren = getChildCount();
        int indexToMakeVisible;
        int positionToMakeVisible;
        View viewToMakeVisible;
        int amountToScroll;
        if (direction == 130) {
            indexToMakeVisible = numChildren - 1;
            if (nextSelectedPosition != -1) {
                indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
            }
            positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
            viewToMakeVisible = getChildAt(indexToMakeVisible);
            int goalRight = listRight;
            if (positionToMakeVisible < this.mItemCount - 1) {
                goalRight -= getArrowScrollPreviewLength();
            }
            if (viewToMakeVisible.getRight() <= goalRight) {
                return 0;
            }
            if (nextSelectedPosition != -1 && goalRight - viewToMakeVisible.getLeft() >= getMaxScrollAmount()) {
                return 0;
            }
            amountToScroll = viewToMakeVisible.getRight() - goalRight;
            if (this.mFirstPosition + numChildren == this.mItemCount) {
                amountToScroll = Math.min(amountToScroll, getChildAt(numChildren - 1).getRight() - listRight);
            }
            return Math.min(amountToScroll, getMaxScrollAmount());
        }
        indexToMakeVisible = 0;
        if (nextSelectedPosition != -1) {
            indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
        }
        positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
        viewToMakeVisible = getChildAt(indexToMakeVisible);
        int goalLeft = listLeft;
        if (positionToMakeVisible > 0) {
            goalLeft += getArrowScrollPreviewLength();
        }
        if (viewToMakeVisible.getLeft() >= goalLeft) {
            return 0;
        }
        if (nextSelectedPosition != -1 && viewToMakeVisible.getRight() - goalLeft >= getMaxScrollAmount()) {
            return 0;
        }
        amountToScroll = goalLeft - viewToMakeVisible.getLeft();
        if (this.mFirstPosition == 0) {
            amountToScroll = Math.min(amountToScroll, listLeft - getChildAt(0).getLeft());
        }
        return Math.min(amountToScroll, getMaxScrollAmount());
    }

    private int lookForSelectablePositionOnScreen(int direction) {
        int firstPosition = this.mFirstPosition;
        int startPos;
        ListAdapter adapter;
        int pos;
        if (direction == 130) {
            if (this.mSelectedPosition != -1) {
                startPos = this.mSelectedPosition + 1;
            } else {
                startPos = firstPosition;
            }
            if (startPos >= this.mAdapter.getCount()) {
                return -1;
            }
            if (startPos < firstPosition) {
                startPos = firstPosition;
            }
            int lastVisiblePos = getLastVisiblePosition();
            adapter = getAdapter();
            pos = startPos;
            while (pos <= lastVisiblePos) {
                if (adapter.isEnabled(pos) && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
                pos++;
            }
        } else {
            int last = (getChildCount() + firstPosition) - 1;
            if (this.mSelectedPosition != -1) {
                startPos = this.mSelectedPosition - 1;
            } else {
                startPos = (getChildCount() + firstPosition) - 1;
            }
            if (startPos < 0 || startPos >= this.mAdapter.getCount()) {
                return -1;
            }
            if (startPos > last) {
                startPos = last;
            }
            adapter = getAdapter();
            pos = startPos;
            while (pos >= firstPosition) {
                if (adapter.isEnabled(pos) && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
                pos--;
            }
        }
        return -1;
    }

    private ArrowScrollFocusResult arrowScrollFocused(int direction) {
        View newFocus;
        View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            int xSearchPoint;
            if (direction == 130) {
                int listLeft = this.mListPadding.left + (this.mFirstPosition > 0 ? getArrowScrollPreviewLength() : 0);
                if (selectedView == null || selectedView.getLeft() <= listLeft) {
                    xSearchPoint = listLeft;
                } else {
                    xSearchPoint = selectedView.getLeft();
                }
                this.mTempRect.set(xSearchPoint, 0, xSearchPoint, 0);
            } else {
                int listRight = (getWidth() - this.mListPadding.right) - ((this.mFirstPosition + getChildCount()) + -1 < this.mItemCount ? getArrowScrollPreviewLength() : 0);
                if (selectedView == null || selectedView.getRight() >= listRight) {
                    xSearchPoint = listRight;
                } else {
                    xSearchPoint = selectedView.getRight();
                }
                this.mTempRect.set(xSearchPoint, 0, xSearchPoint, 0);
            }
            newFocus = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, direction);
        } else {
            newFocus = FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), direction);
        }
        if (newFocus != null) {
            int positionOfNewFocus = positionOfNewFocus(newFocus);
            if (!(this.mSelectedPosition == -1 || positionOfNewFocus == this.mSelectedPosition)) {
                int selectablePosition = lookForSelectablePositionOnScreen(direction);
                if (selectablePosition != -1 && ((direction == 130 && selectablePosition < positionOfNewFocus) || (direction == 33 && selectablePosition > positionOfNewFocus))) {
                    return null;
                }
            }
            int focusScroll = amountToScrollToNewFocus(direction, newFocus, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (focusScroll < maxScrollAmount) {
                newFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, focusScroll);
                return this.mArrowScrollFocusResult;
            } else if (distanceToView(newFocus) < maxScrollAmount) {
                newFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.populate(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    private int positionOfNewFocus(View newFocus) {
        int numChildren = getChildCount();
        for (int i = 0; i < numChildren; i++) {
            if (isViewAncestorOf(newFocus, getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private boolean isViewAncestorOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        ViewParent theParent = child.getParent();
        if ((theParent instanceof ViewGroup) && isViewAncestorOf((View) theParent, parent)) {
            return true;
        }
        return false;
    }

    private int amountToScrollToNewFocus(int direction, View newFocus, int positionOfNewFocus) {
        newFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(newFocus, this.mTempRect);
        int amountToScroll;
        if (direction != 33) {
            int listRight = getWidth() - this.mListPadding.right;
            if (this.mTempRect.bottom <= listRight) {
                return 0;
            }
            amountToScroll = this.mTempRect.right - listRight;
            if (positionOfNewFocus < this.mItemCount - 1) {
                return amountToScroll + getArrowScrollPreviewLength();
            }
            return amountToScroll;
        } else if (this.mTempRect.left >= this.mListPadding.left) {
            return 0;
        } else {
            amountToScroll = this.mListPadding.left - this.mTempRect.left;
            return positionOfNewFocus > 0 ? amountToScroll + getArrowScrollPreviewLength() : amountToScroll;
        }
    }

    private int distanceToView(View descendant) {
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        int listRight = (getRight() - getLeft()) - this.mListPadding.right;
        if (this.mTempRect.right < this.mListPadding.left) {
            return this.mListPadding.left - this.mTempRect.right;
        }
        if (this.mTempRect.left > listRight) {
            return this.mTempRect.left - listRight;
        }
        return 0;
    }

    private void scrollListItemsBy(int amount) {
        offsetChildrenLeftAndRight(amount);
        int listRight = getWidth() - this.mListPadding.right;
        int listLeft = this.mListPadding.left;
        RecycleBin recycleBin = this.mRecycler;
        View last;
        View first;
        if (amount < 0) {
            int numChildren = getChildCount();
            last = getChildAt(numChildren - 1);
            while (last.getRight() < listRight) {
                int lastVisiblePosition = (this.mFirstPosition + numChildren) - 1;
                if (lastVisiblePosition >= this.mItemCount - 1) {
                    break;
                }
                last = addViewAfter(last, lastVisiblePosition);
                numChildren++;
            }
            if (last.getBottom() < listRight) {
                offsetChildrenLeftAndRight(listRight - last.getRight());
            }
            first = getChildAt(0);
            while (first.getRight() < listLeft) {
                if (recycleBin.shouldRecycleViewType(((LayoutParams) first.getLayoutParams()).viewType)) {
                    detachViewFromParent(first);
                    recycleBin.addScrapView(first, this.mFirstPosition);
                } else {
                    removeViewInLayout(first);
                }
                first = getChildAt(0);
                this.mFirstPosition++;
            }
            return;
        }
        first = getChildAt(0);
        while (first.getLeft() > listLeft && this.mFirstPosition > 0) {
            first = addViewBefore(first, this.mFirstPosition);
            this.mFirstPosition--;
        }
        if (first.getLeft() > listLeft) {
            offsetChildrenLeftAndRight(listLeft - first.getLeft());
        }
        int lastIndex = getChildCount() - 1;
        last = getChildAt(lastIndex);
        while (last.getLeft() > listRight) {
            if (recycleBin.shouldRecycleViewType(((LayoutParams) last.getLayoutParams()).viewType)) {
                detachViewFromParent(last);
                recycleBin.addScrapView(last, this.mFirstPosition + lastIndex);
            } else {
                removeViewInLayout(last);
            }
            lastIndex--;
            last = getChildAt(lastIndex);
        }
    }

    private View addViewBefore(View theView, int position) {
        int abovePosition = position - 1;
        View view = obtainView(abovePosition, this.mIsScrap);
        setupChild(view, abovePosition, theView.getLeft() - this.mDividerWidth, false, this.mListPadding.top, false, this.mIsScrap[0]);
        return view;
    }

    private View addViewAfter(View theView, int position) {
        int belowPosition = position + 1;
        View view = obtainView(belowPosition, this.mIsScrap);
        setupChild(view, belowPosition, theView.getRight() + this.mDividerWidth, true, this.mListPadding.top, false, this.mIsScrap[0]);
        return view;
    }

    public void setItemsCanFocus(boolean itemsCanFocus) {
        this.mItemsCanFocus = itemsCanFocus;
        if (!itemsCanFocus) {
            setDescendantFocusability(393216);
        }
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    public boolean isOpaque() {
        boolean retValue = (this.mCachingActive && this.mIsCacheColorOpaque && this.mDividerIsOpaque) || super.isOpaque();
        if (!retValue) {
            return retValue;
        }
        int listLeft = this.mListPadding != null ? this.mListPadding.left : getPaddingLeft();
        View first = getChildAt(0);
        if (first == null || first.getLeft() > listLeft) {
            return false;
        }
        int listRight = getWidth() - (this.mListPadding != null ? this.mListPadding.right : getPaddingRight());
        View last = getChildAt(getChildCount() - 1);
        if (last == null || last.getRight() < listRight) {
            return false;
        }
        return retValue;
    }

    public void setCacheColorHint(int color) {
        boolean opaque = (color >>> 24) == 255;
        this.mIsCacheColorOpaque = opaque;
        if (opaque) {
            if (this.mDividerPaint == null) {
                this.mDividerPaint = new Paint();
            }
            this.mDividerPaint.setColor(color);
        }
        super.setCacheColorHint(color);
    }

    void drawOverscrollHeader(Canvas canvas, Drawable drawable, Rect bounds) {
        int width = drawable.getMinimumWidth();
        canvas.save();
        canvas.clipRect(bounds);
        if (bounds.right - bounds.left < width) {
            bounds.left = bounds.right - width;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    void drawOverscrollFooter(Canvas canvas, Drawable drawable, Rect bounds) {
        int width = drawable.getMinimumWidth();
        canvas.save();
        canvas.clipRect(bounds);
        if (bounds.right - bounds.left < width) {
            bounds.right = bounds.left + width;
        }
        drawable.setBounds(bounds);
        drawable.draw(canvas);
        canvas.restore();
    }

    protected void dispatchDraw(Canvas canvas) {
        if (this.mCachingStarted) {
            this.mCachingActive = true;
        }
        int dividerWidth = this.mDividerWidth;
        Drawable overscrollHeader = this.mOverScrollHeader;
        Drawable overscrollFooter = this.mOverScrollFooter;
        boolean drawOverscrollHeader = overscrollHeader != null;
        boolean drawOverscrollFooter = overscrollFooter != null;
        boolean drawDividers = dividerWidth > 0 && this.mDivider != null;
        if (drawDividers || drawOverscrollHeader || drawOverscrollFooter) {
            Rect bounds = this.mTempRect;
            bounds.top = getPaddingTop();
            bounds.bottom = (getBottom() - getTop()) - getPaddingBottom();
            int count = getChildCount();
            int headerCount = this.mHeaderViewInfos.size();
            int itemCount = this.mItemCount;
            int footerLimit = (itemCount - this.mFooterViewInfos.size()) - 1;
            boolean headerDividers = this.mHeaderDividersEnabled;
            boolean footerDividers = this.mFooterDividersEnabled;
            int first = this.mFirstPosition;
            boolean areAllItemsSelectable = this.mAreAllItemsSelectable;
            ListAdapter adapter = this.mAdapter;
            boolean fillForMissingDividers = isOpaque() && !super.isOpaque();
            if (fillForMissingDividers && this.mDividerPaint == null && this.mIsCacheColorOpaque) {
                this.mDividerPaint = new Paint();
                this.mDividerPaint.setColor(getCacheColorHint());
            }
            Paint paint = this.mDividerPaint;
            int listRight = ((getRight() - getLeft()) - 0) + getScrollX();
            int scrollX;
            int i;
            if (this.mStackFromRight) {
                scrollX = getScrollX();
                if (count > 0 && drawOverscrollHeader) {
                    bounds.left = scrollX;
                    bounds.right = getChildAt(0).getLeft();
                    drawOverscrollHeader(canvas, overscrollHeader, bounds);
                }
                i = drawOverscrollHeader ? 1 : 0;
                while (i < count) {
                    if ((headerDividers || first + i >= headerCount) && (footerDividers || first + i < footerLimit)) {
                        int left = getChildAt(i).getLeft();
                        if (left > 0) {
                            if (areAllItemsSelectable || (adapter.isEnabled(first + i) && (i == count - 1 || adapter.isEnabled((first + i) + 1)))) {
                                bounds.left = left - dividerWidth;
                                bounds.right = left;
                                drawDivider(canvas, bounds, i - 1);
                            } else if (fillForMissingDividers) {
                                bounds.left = left - dividerWidth;
                                bounds.right = left;
                                canvas.drawRect(bounds, paint);
                            }
                        }
                    }
                    i++;
                }
                if (count > 0 && scrollX > 0) {
                    if (drawOverscrollFooter) {
                        int absListRight = getRight();
                        bounds.left = absListRight;
                        bounds.right = absListRight + scrollX;
                        drawOverscrollFooter(canvas, overscrollFooter, bounds);
                    } else if (drawDividers) {
                        bounds.left = listRight;
                        bounds.right = listRight + dividerWidth;
                        drawDivider(canvas, bounds, -1);
                    }
                }
            } else {
                int right = 0;
                scrollX = getScrollX();
                if (count > 0 && scrollX < 0) {
                    if (drawOverscrollHeader) {
                        bounds.right = 0;
                        bounds.left = scrollX;
                        drawOverscrollHeader(canvas, overscrollHeader, bounds);
                    } else if (drawDividers) {
                        bounds.right = 0;
                        bounds.left = -dividerWidth;
                        drawDivider(canvas, bounds, -1);
                    }
                }
                i = 0;
                while (i < count) {
                    if ((headerDividers || first + i >= headerCount) && (footerDividers || first + i < footerLimit)) {
                        right = getChildAt(i).getRight();
                        if (drawDividers && right < listRight && !(drawOverscrollFooter && i == count - 1)) {
                            if (areAllItemsSelectable || (adapter.isEnabled(first + i) && (i == count - 1 || adapter.isEnabled((first + i) + 1)))) {
                                bounds.left = right;
                                bounds.right = right + dividerWidth;
                                drawDivider(canvas, bounds, i);
                            } else if (fillForMissingDividers) {
                                bounds.left = right;
                                bounds.right = right + dividerWidth;
                                canvas.drawRect(bounds, paint);
                            }
                        }
                    }
                    i++;
                }
                int overFooterBottom = getRight() + getScrollX();
                if (drawOverscrollFooter && first + count == itemCount && overFooterBottom > right) {
                    bounds.left = right;
                    bounds.right = overFooterBottom;
                    drawOverscrollFooter(canvas, overscrollFooter, bounds);
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean more = super.drawChild(canvas, child, drawingTime);
        if (this.mCachingActive) {
            this.mCachingActive = false;
        }
        return more;
    }

    void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
        Drawable divider = this.mDivider;
        divider.setBounds(bounds);
        divider.draw(canvas);
    }

    public Drawable getDivider() {
        return this.mDivider;
    }

    public void setDivider(Drawable divider) {
        boolean z = false;
        if (divider != null) {
            this.mDividerWidth = divider.getIntrinsicWidth();
        } else {
            this.mDividerWidth = 0;
        }
        this.mDivider = divider;
        if (divider == null || divider.getOpacity() == -1) {
            z = true;
        }
        this.mDividerIsOpaque = z;
        requestLayout();
        invalidate();
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public void setDividerWidth(int width) {
        this.mDividerWidth = width;
        requestLayout();
        invalidate();
    }

    public void setHeaderDividersEnabled(boolean headerDividersEnabled) {
        this.mHeaderDividersEnabled = headerDividersEnabled;
        invalidate();
    }

    public void setFooterDividersEnabled(boolean footerDividersEnabled) {
        this.mFooterDividersEnabled = footerDividersEnabled;
        invalidate();
    }

    public void setOverscrollHeader(Drawable header) {
        this.mOverScrollHeader = header;
        if (getScrollX() < 0) {
            invalidate();
        }
    }

    public Drawable getOverscrollHeader() {
        return this.mOverScrollHeader;
    }

    public void setOverscrollFooter(Drawable footer) {
        this.mOverScrollFooter = footer;
        invalidate();
    }

    public Drawable getOverscrollFooter() {
        return this.mOverScrollFooter;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        ListAdapter adapter = this.mAdapter;
        int closetChildIndex = -1;
        int closestChildLeft = 0;
        if (!(adapter == null || !gainFocus || previouslyFocusedRect == null)) {
            previouslyFocusedRect.offset(getScrollX(), getScrollY());
            if (adapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = 0;
                layoutChildren();
            }
            Rect otherRect = this.mTempRect;
            int minDistance = Integer.MAX_VALUE;
            int childCount = getChildCount();
            int firstPosition = this.mFirstPosition;
            for (int i = 0; i < childCount; i++) {
                if (adapter.isEnabled(firstPosition + i)) {
                    View other = getChildAt(i);
                    other.getDrawingRect(otherRect);
                    offsetDescendantRectToMyCoords(other, otherRect);
                    int distance = AbsHListView.getDistance(previouslyFocusedRect, otherRect, direction);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closetChildIndex = i;
                        closestChildLeft = other.getLeft();
                    }
                }
            }
        }
        if (closetChildIndex >= 0) {
            setSelectionFromLeft(this.mFirstPosition + closetChildIndex, closestChildLeft);
        } else {
            requestLayout();
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                addHeaderView(getChildAt(i));
            }
            removeAllViews();
        }
    }

    @Deprecated
    public long[] getCheckItemIds() {
        if (this.mAdapter != null && this.mAdapter.hasStableIds()) {
            return getCheckedItemIds();
        }
        if (this.mChoiceMode == 0 || this.mCheckStates == null || this.mAdapter == null) {
            return new long[0];
        }
        SparseArrayCompat<Boolean> states = this.mCheckStates;
        int count = states.size();
        long[] ids = new long[count];
        ListAdapter adapter = this.mAdapter;
        int i = 0;
        int checkedCount = 0;
        while (i < count) {
            int checkedCount2;
            if (((Boolean) states.valueAt(i)).booleanValue()) {
                checkedCount2 = checkedCount + 1;
                ids[checkedCount] = adapter.getItemId(states.keyAt(i));
            } else {
                checkedCount2 = checkedCount;
            }
            i++;
            checkedCount = checkedCount2;
        }
        if (checkedCount == count) {
            return ids;
        }
        long[] result = new long[checkedCount];
        System.arraycopy(ids, 0, result, 0, checkedCount);
        return result;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(HListView.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(HListView.class.getName());
    }

    public void onGlobalLayout() {
    }

    public OnLastVisibleListener getOnLastVisibleListener() {
        return this.mOnLastVisibleListener;
    }

    public void setOnLastVisibleListener(OnLastVisibleListener mOnLastVisibleListener) {
        this.mOnLastVisibleListener = mOnLastVisibleListener;
    }

    public void onLoadingComplete(boolean isRight) {
        if (isRight) {
            this.loading_footer = false;
        } else {
            this.loading_header = false;
        }
    }

    public void onLoadingDisable(boolean isRight) {
        if (isRight) {
            this.loading_footer = true;
        } else {
            this.loading_header = true;
        }
    }

    public void onScrollStateChanged(AbsHListView view, int scrollState) {
    }

    public void onScroll(AbsHListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mOnLastVisibleListener != null) {
            if (firstVisibleItem == 0 && !this.loading_header) {
                this.loading_header = true;
                this.mOnLastVisibleListener.onLastVisible(2);
            }
            if (visibleItemCount > 0 && firstVisibleItem + visibleItemCount == totalItemCount && !this.loading_footer) {
                this.loading_footer = true;
                this.mOnLastVisibleListener.onLastVisible(1);
            }
        }
    }
}
