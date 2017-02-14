package com.letv.android.client.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class PimBaseActivity extends WrapActivity {
    private ImageView mBack;
    public PublicLoadLayout mRootView;
    private Button mSendto;
    private TextView mTitle;
    private ImageView mVipIcon;

    class MyOnClickListener implements OnClickListener {
        final /* synthetic */ PimBaseActivity this$0;

        MyOnClickListener(PimBaseActivity this$0) {
            if (HotFix.PREVENT_VERIFY) {
                System.out.println(VerifyLoad.class);
            }
            this.this$0 = this$0;
        }

        public void onClick(View v) {
            ((InputMethodManager) this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
            this.this$0.getWindow().setSoftInputMode(3);
            this.this$0.finish();
        }
    }

    public abstract int getContentView();

    public PimBaseActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.mRootView = PublicLoadLayout.createPage((Context) this, getContentView());
        setContentView(this.mRootView);
    }

    public void showErrorLayoutMessage(String msg) {
        if (this.mRootView != null) {
            this.mRootView.showErrorMessage(msg);
        }
    }

    public void showNetNullMessage() {
        if (this.mRootView != null) {
            this.mRootView.netError(true);
        }
    }

    public void showLoading() {
        if (this.mRootView != null) {
            this.mRootView.loading(true);
        }
    }

    public void showErrorLayoutMessage(int msg) {
        if (this.mRootView != null) {
            this.mRootView.showErrorMessage(getResources().getString(msg));
        }
    }

    public void hideErrorLayoutMessage() {
        if (this.mRootView != null) {
            this.mRootView.finish();
        }
    }

    public void initUI() {
        MyOnClickListener l = new MyOnClickListener(this);
        this.mBack = (ImageView) findViewById(R.id.btn_back);
        this.mTitle = (TextView) findViewById(2131362147);
        this.mBack.setOnClickListener(l);
        this.mTitle.setOnClickListener(l);
        this.mVipIcon = (ImageView) findViewById(R.id.vip_icon);
        this.mSendto = (Button) findViewById(R.id.btn_send);
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

    public void setTitle(int title) {
        this.mTitle.setText(title);
    }

    protected void setOnBackClickListener(OnClickListener l) {
        this.mBack.setOnClickListener(l);
    }

    protected void setVipIconVisiable(boolean b) {
        this.mVipIcon.setVisibility(b ? 0 : 8);
    }

    protected void setSendBtnVisiable(boolean b) {
        this.mSendto.setVisibility(b ? 0 : 8);
    }

    protected void setOnSendClickListener(OnClickListener l) {
        this.mSendto.setOnClickListener(l);
    }
}
