package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.config.InviteWebviewimplConfig;
import com.letv.android.client.utils.UIs;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.datastatistics.util.DataConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class InviteActivity extends LetvBaseActivity {
    private View view;

    public InviteActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.invite_home_layout);
        invite();
    }

    protected void onStart() {
        super.onStart();
    }

    public void invite() {
        Intent intent = getIntent();
        Button left_button = (Button) findViewById(R.id.open_button_linea);
        Button right_button = (Button) findViewById(R.id.check_button_linea);
        TextView bottom_text = (TextView) findViewById(R.id.invite_bottom_button_txt);
        findViewById(R.id.invite_image).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ InviteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LogInfo.log("+->", "invite_image--->>>");
                this.this$0.onInviteClickFunction("check");
            }
        });
        left_button.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ InviteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LogInfo.log("+->", "left_button--->>.");
                this.this$0.onInviteClickFunction("check");
                StatisticsUtils.staticticsInfoPost(this.this$0.getApplicationContext(), "0", "g12", "参与活动", 2, null, DataConstant.P3, null, null, null, null, null);
            }
        });
        right_button.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ InviteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LogInfo.log("+->", "---->>>>right_button");
                this.this$0.onInviteClickFunction(ControlAction.ACTION_KEY_LEFT);
                StatisticsUtils.staticticsInfoPost(this.this$0.getApplicationContext(), "0", "g12", "打开红包", 1, null, DataConstant.P3, null, null, null, null, null);
            }
        });
        findViewById(R.id.bottom_relea).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ InviteActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onClick(View v) {
                LogInfo.log("+->", "---->>>bottom_relea...");
                this.this$0.removeInviteView();
                StatisticsUtils.staticticsInfoPost(this.this$0.getApplicationContext(), "0", "g12", "我是来看视频的", 3, null, DataConstant.P3, null, null, null, null, null);
            }
        });
        UIs.zoomView(LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT, 42, left_button);
        UIs.zoomView(LeMessageIds.MSG_ALBUM_HALF_FETCH_EXPEND_VIEWPAGER_LAYOUT, 42, right_button);
        UIs.zoomView(130, 42, bottom_text);
        String left = intent.getStringExtra(ControlAction.ACTION_KEY_LEFT);
        String check = intent.getStringExtra("check");
        String bottom = intent.getStringExtra("bottom");
        LogInfo.log("+->", "111left--" + left + "--left1--" + check + "--left2--" + bottom);
        left_button.setText(left);
        right_button.setText(check);
        bottom_text.setText(bottom);
    }

    public void onInviteClickFunction(String flag) {
        if (!TextUtils.isEmpty(flag)) {
            LogInfo.log("+->", "flag" + flag);
            boolean loginFlag = PreferencesManager.getInstance().isLogin();
            if (ControlAction.ACTION_KEY_LEFT.equals(flag)) {
                if (loginFlag) {
                    LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new InviteWebviewimplConfig(this).create(ControlAction.ACTION_KEY_LEFT)));
                } else {
                    LetvLoginActivity.launch((Activity) this, 5);
                }
            } else if (loginFlag) {
                LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new InviteWebviewimplConfig(this).create("check")));
            } else {
                LetvLoginActivity.launch((Activity) this, 4);
            }
        }
        removeInviteView();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == 4) {
            PreferencesManager.getInstance().setInviteFlag(false);
            finish();
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
    }

    private void removeInviteView() {
        PreferencesManager.getInstance().setInviteFlag(false);
        finish();
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
