package com.letv.android.client.commonlib.view.title;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import com.letv.android.client.commonlib.R;
import com.letv.core.utils.UIsUtils;
import com.letv.mobile.lebox.view.BadgeView;
import com.tencent.open.yyb.TitleBar;
import java.util.regex.Pattern;

public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {
    private static final CharSequence EMPTY_TITLE = "";
    protected static final int TAB_HEIGHT = UIsUtils.dipToPx(40.0f);
    protected static final int WOLD_DEFAULT_WIDTH = UIsUtils.dipToPx(16.0f);
    private boolean autoWidth;
    private BadgeView mBadgeView;
    protected Context mContext;
    protected boolean mIsCustomTabWidth;
    protected OnPageChangeListener mListener;
    private int mMaxTabWidth;
    protected int mSelectedTabIndex;
    private final OnClickListener mTabClickListener;
    protected final IcsLinearLayout mTabLayout;
    protected OnTabReselectedListener mTabReselectedListener;
    private Runnable mTabSelector;
    protected ViewPager mViewPager;
    private int screenWidth;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mIsCustomTabWidth = false;
        this.mTabClickListener = new 1(this);
        this.autoWidth = false;
        this.screenWidth = 0;
        this.mContext = context;
        setHorizontalScrollBarEnabled(false);
        this.screenWidth = Math.min(UIsUtils.getScreenWidth(), UIsUtils.getScreenHeight());
        this.mTabLayout = new IcsLinearLayout(context, R.attr.vpiTabPageIndicatorStyle);
        addView(this.mTabLayout, new LayoutParams(-2, -1));
        setBackgroundColor(getResources().getColor(R.color.letv_base_bg));
    }

    public void setOnTabReselectedListener(OnTabReselectedListener listener) {
        this.mTabReselectedListener = listener;
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        boolean lockedExpanded = widthMode == 1073741824;
        setFillViewport(lockedExpanded);
        int childCount = this.mTabLayout.getChildCount();
        if (childCount <= 1 || !(widthMode == 1073741824 || widthMode == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = -1;
        } else if (childCount > 2) {
            this.mMaxTabWidth = (int) (((float) MeasureSpec.getSize(widthMeasureSpec)) * 0.4f);
        } else {
            this.mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
        }
        int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newWidth = getMeasuredWidth();
        if (lockedExpanded && oldWidth != newWidth) {
            setCurrentItem(this.mSelectedTabIndex);
        }
    }

    protected void animateToTab(int position) {
        View tabView = this.mTabLayout.getChildAt(position);
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
        }
        this.mTabSelector = new 2(this, tabView);
        post(this.mTabSelector);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTabSelector != null) {
            post(this.mTabSelector);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTabSelector != null) {
            removeCallbacks(this.mTabSelector);
        }
    }

    protected void addTab(int index, CharSequence text, int iconResId) {
        TabView tabView = new TabView(this, getContext());
        TabView.access$102(tabView, index);
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mTabClickListener);
        tabView.setText(text);
        if (iconResId != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        }
        this.mTabLayout.addView(tabView, new LayoutParams(-2, -1, 1.0f));
    }

    public void onPageScrollStateChanged(int arg0) {
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(arg0);
        }
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (this.mListener != null) {
            this.mListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void onPageSelected(int arg0) {
        setCurrentItem(arg0);
        if (this.mListener != null) {
            this.mListener.onPageSelected(arg0);
        }
    }

    public void setViewPager(ViewPager view) {
        if (view == null) {
            removeAllViews();
            this.mViewPager = null;
        } else if (this.mViewPager != view) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (view.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = view;
            view.setOnPageChangeListener(this);
            notifyDataSetChanged();
        }
    }

    public void setViewPagerOnly(ViewPager view) {
        if (view == null) {
            this.mViewPager = null;
        } else if (this.mViewPager != view) {
            this.mViewPager = view;
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        this.mTabLayout.removeAllViews();
        PagerAdapter adapter = this.mViewPager.getAdapter();
        IconPagerAdapter iconAdapter = null;
        if (adapter instanceof IconPagerAdapter) {
            iconAdapter = (IconPagerAdapter) adapter;
        }
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            int iconResId = 0;
            if (iconAdapter != null) {
                iconResId = iconAdapter.getIconResId(i);
            }
            addTab(i, title, iconResId);
        }
        if (this.mSelectedTabIndex > count) {
            this.mSelectedTabIndex = count - 1;
        }
        setCurrentItem(this.mSelectedTabIndex);
        requestLayout();
    }

    public void setViewPager(ViewPager view, int initialPosition) {
        setViewPager(view);
        setCurrentItem(initialPosition);
    }

    public void setCurrentItem(int item) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mSelectedTabIndex = item;
        if (this.mSelectedTabIndex != item) {
            this.mViewPager.setCurrentItem(item);
        }
        int tabCount = this.mTabLayout.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            boolean isSelected;
            View child = this.mTabLayout.getChildAt(i);
            if (i == item) {
                isSelected = true;
            } else {
                isSelected = false;
            }
            child.setSelected(isSelected);
            if (isSelected) {
                animateToTab(item);
                ((TabView) child).setTextColor(this.mContext.getResources().getColor(R.color.letv_color_ffef534e));
                child.setBackgroundResource(R.drawable.tab_indicator_bg);
            } else {
                ((TabView) child).setTextColor(this.mContext.getResources().getColor(R.color.letv_color_ff444444));
                child.setBackgroundDrawable(null);
            }
            if (i == 0 && (child instanceof FrameLayout)) {
                ((FrameLayout) child).getChildAt(0).setBackgroundDrawable(null);
            }
        }
    }

    private void createBadgeView() {
        if (this.mBadgeView == null) {
            this.mBadgeView = new BadgeView(this.mContext, this.mTabLayout.getChildAt(0));
            this.mBadgeView.setText("");
            this.mBadgeView.setBackgroundResource(R.drawable.vip_service_red_dot);
            this.mBadgeView.setTextSize(16.0f);
        }
    }

    public void clear() {
        this.mSelectedTabIndex = -1;
    }

    public void setVipDotTagDisp() {
        createBadgeView();
        if (!this.mBadgeView.isShown()) {
            this.mBadgeView.show(false, null);
        }
    }

    public void setVipDotTagHide() {
        if (this.mBadgeView != null && this.mBadgeView.isShown()) {
            this.mBadgeView.hide(false, null);
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mListener = listener;
    }

    public void setAutoWidth(boolean auto) {
        this.autoWidth = auto;
    }

    protected int getTabWidth(CharSequence text) {
        if (TextUtils.isEmpty(text) || TextUtils.getTrimmedLength(text) == 0) {
            return 0;
        }
        Pattern p = Pattern.compile("[一-龥]");
        int len = 0;
        for (int i = 0; i < text.length(); i++) {
            char cr = text.charAt(i);
            int i2 = p.matcher(String.valueOf(cr)).matches() ? WOLD_DEFAULT_WIDTH : !Character.isLowerCase(cr) ? (WOLD_DEFAULT_WIDTH / 3) * 2 : WOLD_DEFAULT_WIDTH / 2;
            len += i2;
        }
        return UIsUtils.dipToPx(TitleBar.SHAREBTN_RIGHT_MARGIN) + len;
    }
}
