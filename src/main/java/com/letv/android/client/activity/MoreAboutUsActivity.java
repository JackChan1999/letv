package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.WrapActivity;
import com.letv.android.client.commonlib.config.LetvWebViewActivityConfig;
import com.letv.component.upgrade.utils.LetvUtil;
import com.letv.core.BaseApplication;
import com.letv.core.config.LetvConfig;
import com.letv.core.constant.LetvConstant;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MoreAboutUsActivity extends WrapActivity implements OnClickListener {
    private ImageView mBackImageView;
    private ImageView mLogoImageView;

    public MoreAboutUsActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MoreAboutUsActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_aboutus);
        initUI();
    }

    private void initUI() {
        this.mBackImageView = (ImageView) findViewById(2131362035);
        this.mLogoImageView = (ImageView) findViewById(R.id.logo_view);
        this.mLogoImageView.setOnClickListener(this);
        this.mBackImageView.setOnClickListener(this);
        findViewById(R.id.goto_letvwebsite).setOnClickListener(this);
        findViewById(R.id.goto_grade).setOnClickListener(this);
        findViewById(R.id.call).setOnClickListener(this);
        ((TextView) findViewById(R.id.letv_qq_group)).setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_90003, 2131100409));
        ((TextView) findViewById(R.id.textv_version)).setText(getResources().getString(2131100402) + LetvUtil.getClientVersionName(this));
        findViewById(R.id.letv_qq_group).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362035:
                finish();
                return;
            case R.id.logo_view /*2131363878*/:
                startActivity(new Intent(this, DeviceIdActivity.class));
                return;
            case R.id.letv_qq_group /*2131363881*/:
                if (!LetvConfig.isDebug()) {
                    return;
                }
                return;
            case R.id.goto_letvwebsite /*2131363883*/:
                new LetvWebViewActivityConfig(this).launch(LetvConstant.ABLUT_US_URL, getString(2131100448));
                return;
            case R.id.call /*2131363885*/:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                if (LetvUtils.isInHongKong()) {
                    intent.setData(Uri.parse(BaseApplication.getInstance().getString(2131100447)));
                } else {
                    intent.setData(Uri.parse(BaseApplication.getInstance().getString(2131100446)));
                }
                startActivity(intent);
                LogInfo.LogStatistics("more about call click");
                StatisticsUtils.staticticsInfoPost(this, "0", "e56", "拨打客服电话", 1, null, PageIdConstant.settingPage, null, null, null, null, null);
                return;
            default:
                return;
        }
    }

    public String getActivityName() {
        return MoreAboutUsActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }
}
