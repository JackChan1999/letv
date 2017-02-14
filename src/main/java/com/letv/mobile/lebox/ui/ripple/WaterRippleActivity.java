package com.letv.mobile.lebox.ui.ripple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.connect.LeboxConnectManager;
import com.letv.mobile.lebox.connect.LeboxConnectManager.ConnectProgressReciver;
import com.letv.mobile.lebox.httpmanager.HttpCacheAssistant;
import com.letv.mobile.lebox.jump.PageJumpUtil;
import com.letv.mobile.lebox.net.LeBoxNetworkManager;
import com.letv.mobile.lebox.utils.Util;

public class WaterRippleActivity extends Activity implements OnClickListener {
    private String delayShowText;
    private int delayTime;
    private TextView mBreathPromptText;
    private CircleRippleView mCircleRippleView;
    private ImageView mCloseButton;
    ConnectProgressReciver mConnectProgressReciver = new ConnectProgressReciver() {
        private int lastFailState;

        public void notifyProgress(int p) {
            switch (p) {
                case 27:
                    if (LeBoxNetworkManager.getInstance().isLeboxConnectedAvailable() && HttpCacheAssistant.getInstanced().isLogin()) {
                        WaterRippleActivity.this.exit();
                        return;
                    }
                    WaterRippleActivity.this.showErrorMsg(this.lastFailState);
                    LeboxConnectManager.getInstance().startConnect();
                    return;
                default:
                    saveFailState(p);
                    if (10 <= p && p < 27) {
                        String text = Util.getConnectProcessPrompt(p);
                        if (!TextUtils.isEmpty(text)) {
                            WaterRippleActivity.this.mConnectProgressText.setText(text);
                        }
                    }
                    if (p > WaterRippleActivity.this.showErrorMsg) {
                        WaterRippleActivity.this.dismissErrorMsg();
                        return;
                    }
                    return;
            }
        }

        private void saveFailState(int p) {
            switch (p) {
                case 13:
                case 16:
                case 20:
                case 24:
                case 25:
                    this.lastFailState = p;
                    return;
                default:
                    return;
            }
        }
    };
    private TextView mConnectProgressText;
    private LinearLayout mErrorMsgLayout;
    private TextView mErrorMsgText;
    private RoundImageView mRoundImageView;
    private int showErrorMsg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_ripple);
        initIntent();
        initView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        this.delayTime = intent.getIntExtra("delayTime", 0);
        this.delayShowText = intent.getStringExtra("delayShowText");
    }

    private void initView() {
        this.mCircleRippleView = (CircleRippleView) findViewById(R.id.ripple_view);
        this.mRoundImageView = (RoundImageView) findViewById(R.id.round_imageview);
        this.mCloseButton = (ImageView) findViewById(R.id.ripple_page_close);
        this.mConnectProgressText = (TextView) findViewById(R.id.connect_progress_state);
        this.mErrorMsgText = (TextView) findViewById(R.id.error_msg_text);
        this.mErrorMsgLayout = (LinearLayout) findViewById(R.id.error_msg_prompt_layout);
        this.mBreathPromptText = (TextView) findViewById(R.id.breath_prompt_text);
        this.mRoundImageView.setOnClickListener(this);
        this.mCloseButton.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        LeboxConnectManager.getInstance().setConnectProgressReciver(this.mConnectProgressReciver);
        if (this.delayTime > 0) {
            LeboxConnectManager.getInstance().startDelayConnect(this.delayTime);
            this.mConnectProgressText.setText(this.delayShowText);
            return;
        }
        LeboxConnectManager.getInstance().startConnect();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        LeboxConnectManager.release();
        super.onDestroy();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.round_imageview) {
            this.mCircleRippleView.SetClickRipple();
        } else if (id == R.id.ripple_page_close) {
            exit();
        }
    }

    private void showErrorMsg(int p) {
        if (p > 0) {
            this.showErrorMsg = p;
            this.mErrorMsgText.setText(Util.getConnectProcessPrompt(p));
            this.mErrorMsgLayout.setVisibility(0);
        }
    }

    private void dismissErrorMsg() {
        this.mErrorMsgText.setText("");
        this.mErrorMsgLayout.setVisibility(4);
    }

    private void exit() {
        PageJumpUtil.jumpLeBoxMainActivity(this);
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        exit();
        return true;
    }
}
