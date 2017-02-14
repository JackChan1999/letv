package com.letv.android.client.mymessage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.letv.android.client.commonlib.view.title.TabPageIndicator.TabView;
import com.letv.android.client.hot.HotTabPageIndicator;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyMessageTabPageIndicator extends HotTabPageIndicator {
    public MyMessageTabPageIndicator(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
    }

    public MyMessageTabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void addTab(int index, CharSequence text, int iconResId) {
        TabView tabView = new TabView(this, getContext(), text);
        tabView.setIndex(index);
        tabView.setTextSize(1, 15.0f);
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mTabClickListener);
        if (iconResId != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(iconResId, 0, 0, 0);
        }
        int width = this.mMeanWidth == -1 ? tabView.getCustomWidth() : this.mMeanWidth;
        if (this.mMeanWidth != -1) {
            tabView.setSize(this.mMeanWidth, UIsUtils.dipToPx(43.0f));
        }
        tabView.setLayoutParams(new LayoutParams(width, tabView.getCustomHeight(), 17.0f));
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        linearLayout.setOrientation(1);
        linearLayout.setGravity(17);
        linearLayout.setLayoutParams(new LayoutParams(width, -2, 17.0f));
        linearLayout.addView(tabView);
        View imageView = new View(this.mContext);
        imageView.setLayoutParams(new LayoutParams(UIsUtils.dipToPx(76.0f), UIsUtils.dipToPx(2.0f), 17.0f));
        imageView.setBackgroundColor(this.mContext.getResources().getColor(2131493202));
        linearLayout.addView(imageView);
        this.mTabLayout.addView(linearLayout);
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
                LinearLayout childLayout = (LinearLayout) this.mTabLayout.getChildAt(i);
                for (int j = 0; j < childLayout.getChildCount(); j++) {
                    boolean isSelected = i == item;
                    View view = childLayout.getChildAt(j);
                    if (view instanceof TabView) {
                        view.setSelected(isSelected);
                        if (isSelected) {
                            animateToTab(item);
                            ((TabView) view).setTextColor(this.mContext.getResources().getColor(2131493202));
                        } else {
                            ((TabView) view).setTextColor(this.mContext.getResources().getColor(2131493237));
                        }
                    } else if (isSelected) {
                        view.setBackgroundColor(this.mContext.getResources().getColor(2131493202));
                    } else {
                        view.setBackgroundDrawable(null);
                    }
                }
                i++;
            }
        }
    }
}
