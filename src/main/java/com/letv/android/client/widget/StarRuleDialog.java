package com.letv.android.client.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class StarRuleDialog extends Dialog {
    private Context mContext;
    private ImageView mImageClose;
    private StarRuleDialogListener mListener;
    private LinearLayout mRootView;
    private LinearLayout mRuleContent;
    private String mRuleMessage;
    private TextView mRuleTip;
    private int mViewLayout;
    private OnClickListener onClickEvent;

    public interface StarRuleDialogListener {
        void onClick(boolean z);
    }

    public StarRuleDialog(Context context, String ruleMessage, int layoutId, int styleId, StarRuleDialogListener dialogListener) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, styleId);
        this.onClickEvent = new 2(this);
        this.mContext = context;
        this.mViewLayout = layoutId;
        this.mRuleMessage = ruleMessage;
        this.mListener = dialogListener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(this.mViewLayout);
        findView();
        setRootView();
        setText();
    }

    private void findView() {
        this.mRuleContent = (LinearLayout) findViewById(R.id.rule_ll_content);
        this.mRuleTip = (TextView) findViewById(R.id.rule_desc_tv);
        this.mImageClose = (ImageView) findViewById(R.id.rule_close);
        this.mRootView = (LinearLayout) findViewById(R.id.star_rule_ll);
        this.mImageClose.setOnClickListener(this.onClickEvent);
    }

    private void setRootView() {
        this.mRootView.setLayoutParams(new LayoutParams(UIsUtils.getScreenWidth() - UIsUtils.dipToPx(30.0f), -2));
    }

    private void setText() {
        this.mRuleMessage.replace("#", "\n");
        this.mRuleTip.setText(this.mRuleMessage);
        this.mRuleTip.measure(0, 0);
        getMessage(this.mRuleTip);
    }

    private void setView(int viewHeight) {
        LinearLayout.LayoutParams lltype;
        if (viewHeight < UIsUtils.dipToPx(360.0f)) {
            lltype = new LinearLayout.LayoutParams(-1, -2);
        } else {
            lltype = new LinearLayout.LayoutParams(-1, UIsUtils.dipToPx(360.0f));
        }
        lltype.setMargins(UIsUtils.dipToPx(15.0f), 0, 0, UIsUtils.dipToPx(15.0f));
        this.mRuleContent.setLayoutParams(lltype);
    }

    private void getMessage(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new 1(this, view));
    }
}
