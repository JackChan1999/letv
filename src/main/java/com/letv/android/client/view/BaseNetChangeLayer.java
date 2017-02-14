package com.letv.android.client.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class BaseNetChangeLayer implements NetChangeLayer {
    private boolean bind;
    private Context mContext;
    private View netRoot;
    private Button net_change_continue;
    private TextView net_change_text1;
    private View net_change_text2;
    private ImageView net_full_half;
    private ImageView net_top_back;
    private ImageView net_top_back_r;
    private View net_top_layout;
    private View net_top_layout_r;
    private TextView net_top_title;
    private TextView net_top_title_r;
    private ViewGroup parent;

    public BaseNetChangeLayer(Context context, ViewGroup parent) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        if (context == null || parent == null) {
            throw new RuntimeException("context or parent cann't be null");
        }
        this.parent = parent;
        this.mContext = context;
        this.netRoot = LayoutInflater.from(context).inflate(R.layout.net_change_layout, null, false);
        if (parent instanceof RelativeLayout) {
            this.netRoot.setLayoutParams(new LayoutParams(-1, -1));
        }
        parent.addView(this.netRoot);
        if (parent instanceof AbsListView) {
            parent.setLayoutParams(new AbsListView.LayoutParams(-1, -1));
        }
        LogInfo.log("3_g", getClass().getSimpleName() + "--------------addView netRoot = " + this.netRoot + " , parent = " + parent);
        this.bind = true;
        this.net_change_text1 = (TextView) this.netRoot.findViewById(R.id.net_change_text1);
        this.net_change_text2 = this.netRoot.findViewById(R.id.net_change_text2);
        this.net_change_continue = (Button) this.netRoot.findViewById(R.id.net_change_continue);
        this.net_top_back = (ImageView) this.netRoot.findViewById(R.id.net_top_back);
        this.net_full_half = (ImageView) this.netRoot.findViewById(R.id.net_full_half);
        this.net_top_title = (TextView) this.netRoot.findViewById(R.id.net_top_title);
        this.net_top_back_r = (ImageView) this.netRoot.findViewById(R.id.net_top_back_r);
        this.net_top_title_r = (TextView) this.netRoot.findViewById(R.id.net_top_title_r);
        this.net_top_layout = this.netRoot.findViewById(R.id.net_top_layout);
        this.net_top_layout_r = this.netRoot.findViewById(R.id.net_top_r_layout);
    }

    public void showBlack() {
        this.net_change_text1.setVisibility(8);
        this.net_change_continue.setVisibility(8);
    }

    public void show3gAlert(OnClickListener l) {
        this.netRoot.setVisibility(0);
        this.net_change_text1.setVisibility(0);
        this.net_change_continue.setVisibility(0);
        if (l != null) {
            this.net_change_continue.setOnClickListener(new 1(this, l));
        }
    }

    public void show3gAlert(OnClickListener l, boolean special) {
        this.netRoot.setVisibility(0);
        if (special) {
            this.net_change_text2.setVisibility(0);
        }
        this.net_change_continue.setVisibility(0);
        if (l != null) {
            this.net_change_continue.setOnClickListener(new 2(this, l));
        }
    }

    public void hide() {
        this.netRoot.setVisibility(8);
    }

    public void setBackListener(OnClickListener listener) {
        if (listener != null) {
            this.net_top_back.setOnClickListener(listener);
        }
    }

    public void setFullHalfListener(OnClickListener listener) {
        if (listener != null) {
            this.net_full_half.setOnClickListener(listener);
        }
    }

    public void setTopRight() {
        this.net_top_layout.setVisibility(8);
        this.net_top_layout_r.setVisibility(0);
    }

    public void setTopTitleR(String text) {
        TextView textView = this.net_top_title_r;
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        textView.setText(text);
    }

    public void setBackRightListener(OnClickListener listener) {
        if (listener != null) {
            this.net_top_back_r.setOnClickListener(listener);
        }
    }

    public void setBackBtnVisibility(boolean show) {
        if (show) {
            this.net_top_back.setVisibility(0);
        } else {
            this.net_top_back.setVisibility(8);
        }
    }

    public void setTopTitle(String text) {
        TextView textView = this.net_top_title;
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        textView.setText(text);
    }

    public void setHalfToIcon() {
        this.net_full_half.setImageResource(2130838715);
    }

    public void setHalfIConVisible(boolean isVisible) {
        if (isVisible) {
            this.net_full_half.setVisibility(0);
        } else {
            this.net_full_half.setVisibility(8);
        }
    }

    public void setSize(int height, int width) {
        this.netRoot.getLayoutParams().width = width;
        this.netRoot.getLayoutParams().height = height;
    }

    public void hideController() {
        this.net_top_back.setVisibility(8);
        this.net_full_half.setVisibility(8);
        this.net_top_title.setVisibility(8);
    }

    public void setMiguStatus(boolean isShowMiguTip) {
        if (isShowMiguTip) {
            this.net_change_text1.setText(this.mContext.getString(2131100666));
            this.net_change_continue.setText(this.mContext.getString(2131100665));
            return;
        }
        this.net_change_text1.setText(this.mContext.getString(2131100667));
        this.net_change_continue.setText(this.mContext.getString(2131099710));
    }
}
