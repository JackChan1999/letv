package com.letv.lepaysdk.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.lepaysdk.utils.ResourceUtil;

public class LePayActionBar extends RelativeLayout {
    private LinearLayout layout;
    private Context mContext;
    private ImageView mLeftButton;
    private ImageView mRightButton;
    private TextView mTitle;
    private TextView mUserName;
    private LinearLayout mll_leftButton;
    private PopupWindow popupWindow;

    public LePayActionBar(Context context) {
        super(context);
        this.mContext = context;
    }

    public LePayActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mLeftButton = (ImageView) findViewById(ResourceUtil.getIdResource(this.mContext, "lepay_actionbar_left_icon"));
        this.mRightButton = (ImageView) findViewById(ResourceUtil.getIdResource(this.mContext, "lepay_action_right_icon"));
        this.mTitle = (TextView) findViewById(ResourceUtil.getIdResource(this.mContext, "lepay_actionbar_main_title"));
        this.mll_leftButton = (LinearLayout) findViewById(ResourceUtil.getIdResource(this.mContext, "lepay_ll_leftbutton"));
        this.mLeftButton.setVisibility(0);
        this.mRightButton.setVisibility(0);
    }

    public void setLeftButtonClickable(boolean clickable) {
        this.mll_leftButton.setClickable(clickable);
    }

    public void setLeftButtonVisable(int visibility) {
        this.mLeftButton.setVisibility(visibility);
    }

    public void setRightButtonVisable(int visibility) {
        this.mRightButton.setVisibility(visibility);
    }

    public void setTitleSize(int sp) {
        this.mTitle.setTextSize(2, (float) sp);
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    public void setLeftButtonOnClickListener(OnClickListener listener) {
        this.mll_leftButton.setOnClickListener(listener);
    }

    public void setRightButtonOnClickListener(OnClickListener listener) {
        this.mRightButton.setOnClickListener(listener);
    }

    public void showPopupWindow(String username) {
        this.layout = (LinearLayout) LayoutInflater.from(this.mContext).inflate(ResourceUtil.getLayoutResource(this.mContext, "lepay_actionbar_username"), null);
        this.mUserName = (TextView) this.layout.findViewById(ResourceUtil.getIdResource(this.mContext, "lepay_username"));
        this.popupWindow = new PopupWindow(this.layout);
        this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
        this.popupWindow.setWidth(-2);
        this.popupWindow.setHeight(-2);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setFocusable(true);
        this.mUserName.setText(username);
        this.popupWindow.showAsDropDown(this.mRightButton, 0, 0);
    }
}
