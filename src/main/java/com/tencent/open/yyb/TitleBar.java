package com.tencent.open.yyb;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/* compiled from: ProGuard */
public class TitleBar extends RelativeLayout {
    public static final float BACKBTN_LEFT_MARGIN = 20.0f;
    private static final float BACK_BTN_HEIGHT = 18.0f;
    private static final float BACK_BTN_WIDTH = 11.0f;
    private static final float BACK_PADDING_BOTTOM = 7.0f;
    private static final float BACK_PADDING_LEFT = 15.0f;
    private static final float BACK_PADDING_RIGHT = 20.0f;
    private static final float BACK_PADDING_TOP = 7.0f;
    private static final int ID_BACK_BTN = 10000;
    public static final float SHAREBTN_RIGHT_MARGIN = 10.0f;
    private static final float SHARE_BTN_SIZE = 52.0f;
    private static final String SOURCE_BACK_BTN = "yyb_icon_back.png";
    private static final String SOURCE_MORE_BTN = "yyb_appdetail_showmore.png";
    private static final String SOURCE_TITLEBAR = "yyb_topbar.9.png";
    public static final float TITLEBAR_HEIGHT = 51.0f;
    private ImageView backBtn;
    private RelativeLayout backLayout;
    private float density;
    private ImageView shareBtn;
    private TextView title;

    public TitleBar(Context context) {
        super(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.density = displayMetrics.density;
        setLayoutParams(new LayoutParams(-1, dip2px(TITLEBAR_HEIGHT)));
        setBackgroundDrawable(a.a(SOURCE_TITLEBAR, context));
        initBackLayout();
        initShareBtn();
    }

    public void setTitle(String str) {
        this.title.setText(str);
    }

    private void initBackLayout() {
        this.backLayout = new RelativeLayout(getContext());
        this.backLayout.setLayoutParams(new LayoutParams(-2, -1));
        addView(this.backLayout);
        initBackBtn();
        initTitle();
    }

    private void initBackBtn() {
        this.backBtn = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(dip2px(BACK_BTN_WIDTH), dip2px(BACK_BTN_HEIGHT));
        layoutParams.addRule(15);
        layoutParams.leftMargin = dip2px(20.0f);
        this.backBtn.setId(10000);
        this.backBtn.setLayoutParams(layoutParams);
        this.backBtn.setClickable(true);
        this.backBtn.setBackgroundDrawable(a.a(SOURCE_BACK_BTN, getContext()));
        this.backBtn.setPadding(dip2px(BACK_PADDING_LEFT), dip2px(7.0f), dip2px(20.0f), dip2px(7.0f));
        this.backLayout.addView(this.backBtn);
    }

    private void initTitle() {
        this.title = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(15);
        layoutParams.addRule(1, 10000);
        layoutParams.leftMargin = dip2px(20.0f);
        this.title.setTextColor(Color.parseColor("#fefefe"));
        this.title.setTextSize(20.0f);
        this.title.setShadowLayer(2.0f, 0.0f, 2.0f, Color.parseColor("#2E000000"));
        this.backLayout.addView(this.title, layoutParams);
    }

    private void initShareBtn() {
        this.shareBtn = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(dip2px(SHARE_BTN_SIZE), dip2px(SHARE_BTN_SIZE));
        layoutParams.addRule(15);
        layoutParams.addRule(11);
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        this.shareBtn.setLayoutParams(layoutParams);
        this.shareBtn.setClickable(true);
        this.shareBtn.setBackgroundDrawable(a.a(SOURCE_MORE_BTN, getContext()));
        addView(this.shareBtn);
    }

    public RelativeLayout getBackBtn() {
        return this.backLayout;
    }

    public ImageView getSharBtn() {
        return this.shareBtn;
    }

    private int dip2px(float f) {
        return (int) ((this.density * f) + 0.5f);
    }
}
