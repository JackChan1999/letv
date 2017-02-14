package com.letv.android.client.view.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.letv.android.client.commonlib.view.title.TabPageIndicator;
import com.letv.android.client.commonlib.view.title.TabPageIndicator.TabView;
import com.letv.android.client.utils.ThemeDataManager;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

@SuppressLint({"ResourceAsColor"})
public class ChannelTabPageIndicator extends TabPageIndicator {
    private static final int MAX_TABS = 7;
    private static final int TAB_MARGIN = UIsUtils.dipToPx(8.0f);
    private boolean mIsHome;
    private int mLayoutWidh;
    private int mMeanWidth;
    private final OnClickListener mTabClickListener;

    public ChannelTabPageIndicator(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public ChannelTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLayoutWidh = 0;
        this.mMeanWidth = -1;
        this.mIsHome = true;
        this.mTabClickListener = new 1(this);
        setAutoWidth(false);
        this.mIsCustomTabWidth = true;
        setBackgroundColor(getResources().getColor(2131493356));
    }

    public void setWidth(int width) {
        if (width > 0) {
            this.mLayoutWidh = width;
            setLayoutParams(new LayoutParams(this.mLayoutWidh, UIsUtils.dipToPx(50.0f)));
        }
    }

    public void isHome(boolean home) {
        this.mIsHome = home;
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
            if (this.mLayoutWidh > width && count <= 7) {
                this.mMeanWidth = (this.mLayoutWidh - (TAB_MARGIN * (count + 1))) / count;
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
        int width = this.mMeanWidth == -1 ? getTabWidth(text) : this.mMeanWidth;
        if (this.mMeanWidth != -1) {
            tabView.setSize(this.mMeanWidth, UIsUtils.dipToPx(38.0f));
        } else {
            tabView.setSize(width, UIsUtils.dipToPx(38.0f));
        }
        RelativeLayout relativeLayout = new RelativeLayout(this.mContext);
        relativeLayout.setGravity(17);
        relativeLayout.setLayoutParams(new LayoutParams(-2, UIsUtils.dipToPx(38.0f)));
        LayoutParams params = new LayoutParams(-2, UIsUtils.dipToPx(38.0f));
        params.setMargins(TAB_MARGIN, 0, TAB_MARGIN, 0);
        tabView.setLayoutParams(params);
        relativeLayout.addView(tabView);
        if (this.mIsHome) {
            ThemeDataManager.getInstance(this.mContext).setContentTheme(tabView, ThemeDataManager.NAME_TOP_NAVIGATION_COLOR);
        }
        ImageView imageView = new ImageView(this.mContext);
        LayoutParams imageViewParams = new LayoutParams(width, UIsUtils.dipToPx(2.0f));
        imageViewParams.setMargins(TAB_MARGIN, UIsUtils.dipToPx(36.0f), TAB_MARGIN, 0);
        imageView.setLayoutParams(imageViewParams);
        relativeLayout.addView(imageView);
        imageView.setBackgroundDrawable(getResources().getDrawable(2130838177));
        if (this.mIsHome) {
            ThemeDataManager.getInstance(this.mContext).setShapeSelectorViewTheme(imageView, ThemeDataManager.NAME_TOP_NAVIGATION_COLOR, 2, true);
        }
        this.mTabLayout.addView(relativeLayout);
    }

    public void setCurrentItem(int item) {
        if (this.mViewPager != null) {
            if (item != -1 || this.mSelectedTabIndex != item) {
                this.mSelectedTabIndex = item;
                int tabCount = this.mTabLayout.getChildCount();
                for (int i = 0; i < tabCount; i++) {
                    RelativeLayout childLayout = (RelativeLayout) this.mTabLayout.getChildAt(i);
                    for (int j = 0; j < childLayout.getChildCount(); j++) {
                        boolean isSelected;
                        if (i == item) {
                            isSelected = true;
                        } else {
                            isSelected = false;
                        }
                        View view = childLayout.getChildAt(j);
                        if (view instanceof TabView) {
                            view.setSelected(isSelected);
                            if (isSelected) {
                                animateToTab(item);
                            }
                        } else {
                            view.setSelected(isSelected);
                        }
                    }
                }
                this.mViewPager.setCurrentItem(item, false);
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
