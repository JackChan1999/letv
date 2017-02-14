package com.letv.android.client.hot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.android.client.commonlib.view.title.TabPageIndicator;
import com.letv.android.client.commonlib.view.title.TabPageIndicator.TabView;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

@SuppressLint({"ResourceAsColor"})
public class HotTabPageIndicator extends TabPageIndicator {
    private static final int MAX_TABS = 7;
    private static final int TAB_MARGIN = UIsUtils.dipToPx(6.0f);
    private int mLayoutWidth;
    protected int mMeanWidth;
    protected final OnClickListener mTabClickListener;

    public HotTabPageIndicator(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public HotTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLayoutWidth = 0;
        this.mMeanWidth = -1;
        this.mTabClickListener = new 1(this);
        setAutoWidth(false);
        this.mIsCustomTabWidth = true;
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.mLayoutWidth = width;
            setLayoutParams(new LayoutParams(this.mLayoutWidth, UIsUtils.dipToPx(45.0f)));
        }
    }

    public void notifyDataSetChanged() {
        PagerAdapter adapter = this.mViewPager.getAdapter();
        int count = adapter.getCount();
        if (count != 0) {
            int width = TAB_MARGIN;
            for (int i = 0; i < count; i++) {
                width += getTabWidth(adapter.getPageTitle(i)) + TAB_MARGIN;
            }
            this.mMeanWidth = -1;
            if (this.mLayoutWidth > width && count <= 7) {
                this.mMeanWidth = this.mLayoutWidth / count;
            }
            super.notifyDataSetChanged();
        }
    }

    protected void addTab(int index, CharSequence text, int iconResId) {
        TabView tabView = new TabView(this, getContext(), text);
        tabView.setIndex(index);
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mTabClickListener);
        if (iconResId != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        }
        int width = this.mMeanWidth == -1 ? tabView.getCustomWidth() : this.mMeanWidth;
        if (this.mMeanWidth != -1) {
            tabView.setSize(this.mMeanWidth, UIsUtils.dipToPx(45.0f));
        }
        this.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(width, tabView.getCustomHeight(), 17.0f));
    }

    public void setCurrentItem(int item) {
        if (this.mViewPager != null) {
            if (!(item == -1 || this.mSelectedTabIndex == item)) {
                this.mViewPager.setCurrentItem(item);
            }
            this.mSelectedTabIndex = item;
            int tabCount = this.mTabLayout.getChildCount();
            int i = 0;
            while (i < tabCount) {
                View child = this.mTabLayout.getChildAt(i);
                boolean isSelected = i == item;
                child.setSelected(isSelected);
                if (isSelected) {
                    animateToTab(item);
                    ((TabView) child).setTextColor(this.mContext.getResources().getColor(2131493202));
                    child.setBackgroundResource(2130839011);
                } else {
                    ((TabView) child).setTextColor(this.mContext.getResources().getColor(2131493237));
                    child.setBackgroundDrawable(null);
                }
                i++;
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mIsCustomTabWidth) {
            setFillViewport(false);
        }
    }
}
