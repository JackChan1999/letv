package com.letv.android.client.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.letv.android.client.utils.ThemeDataManager;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MainBottomNavigationView extends LinearLayout {
    private NavigationType mCheckedNavigationType;
    private Context mContext;
    private IBottomNavItemCheckedListener mItemCheckedListener;

    public interface IBottomNavItemCheckedListener {
        void onBottomNavigationClick(NavigationType navigationType, NavigationType navigationType2);
    }

    public enum NavigationType {
        HOME(2130838587, 2131100365, 2131361801),
        VIP(2130838590, 2131101108, 2131361800),
        LIVE(2130838588, 2131100366, 2131361802),
        FIND(2130838586, 2131100364, 2131361804),
        MINE(2130838589, 2131100367, 2131361803);
        
        public final int mResourceId;
        public final int mTextId;
        public final int mWidgetId;

        private NavigationType(int resourceId, int textId, int widgetId) {
            this.mResourceId = resourceId;
            this.mTextId = textId;
            this.mWidgetId = widgetId;
        }
    }

    public MainBottomNavigationView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null, -1);
    }

    public MainBottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MainBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCheckedNavigationType = null;
        this.mContext = context;
    }

    public void setItemCheckedListener(IBottomNavItemCheckedListener itemOnSelectListener) {
        this.mItemCheckedListener = itemOnSelectListener;
    }

    public void setNavigations() {
        removeAllViews();
        int height = UIsUtils.dipToPx(49.0f);
        NavigationType[] types = NavigationType.values();
        int len = types.length;
        for (int i = 0; i < len; i++) {
            LayoutParams params = new LayoutParams(height, height);
            if (i == 0) {
                params.leftMargin = UIsUtils.dipToPx(14.0f);
            } else {
                params.leftMargin = ((UIsUtils.getScreenWidth() - (UIsUtils.dipToPx(14.0f) * 2)) - (len * height)) / (len - 1);
            }
            BottomNavigationItemView itemView = new BottomNavigationItemView(this, this.mContext);
            itemView.setData(types[i]);
            addView(itemView, params);
        }
        setSelectedType(NavigationType.HOME);
    }

    public void setSelectedType(NavigationType type) {
        NavigationType checkedType = this.mCheckedNavigationType;
        if (type != this.mCheckedNavigationType) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) getChildAt(i);
                itemView.setSelected(type == BottomNavigationItemView.access$000(itemView));
            }
        }
        if (this.mItemCheckedListener != null) {
            this.mItemCheckedListener.onBottomNavigationClick(type, checkedType);
        }
    }

    public void updateTheme() {
        ThemeDataManager.getInstance(this.mContext).setContentTheme(this, ThemeDataManager.NAME_BOTTOM_NAVIGATION_PIC);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            ((BottomNavigationItemView) getChildAt(i)).updateView();
        }
    }

    public NavigationType getCheckedNavigationType() {
        return this.mCheckedNavigationType;
    }
}
