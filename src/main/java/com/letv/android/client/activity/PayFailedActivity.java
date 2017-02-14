package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.core.constant.LoginConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class PayFailedActivity extends WrapActivity implements OnClickListener {
    private ImageView mBackImageView;
    private TextView mServiceCall;
    private TextView mTitleView;

    public PayFailedActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Activity context) {
        context.startActivityForResult(new Intent(context, PayFailedActivity.class), 17);
    }

    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.pay_failed);
        findView();
        setResult(258);
    }

    private void findView() {
        this.mTitleView = (TextView) findViewById(2131362352);
        this.mBackImageView = (ImageView) findViewById(2131362351);
        this.mServiceCall = (TextView) findViewById(R.id.service_phone);
        this.mTitleView.setText(2131100576);
        this.mBackImageView.setOnClickListener(this);
        this.mServiceCall.setOnClickListener(this);
        if (LoginConstant.isHongKong()) {
            this.mServiceCall.setVisibility(8);
        }
    }

    public void onClick(View v) {
        if (v.getId() == 2131362351) {
            finish();
        } else if (v.getId() == R.id.service_phone) {
            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + getResources().getString(2131100804))));
        }
    }

    public String getActivityName() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }
}
