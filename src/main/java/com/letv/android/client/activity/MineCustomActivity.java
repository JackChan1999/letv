package com.letv.android.client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MineCustomActivity extends LetvBaseActivity implements OnClickListener {
    private ImageView mBtnBack;
    private TextView mChannelWallText;
    private TextView mFirstPageText;
    private TextView mTitle;

    public MineCustomActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void launch(Context context) {
        if (context != null) {
            context.startActivity(new Intent(context, MineCustomActivity.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_custom_layout);
        findView();
    }

    private void findView() {
        this.mTitle = (TextView) findViewById(2131362352);
        this.mTitle.setText(2131100376);
        this.mFirstPageText = (TextView) findViewById(R.id.first_page_custom);
        this.mFirstPageText.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700094, 2131100132));
        this.mFirstPageText.setOnClickListener(this);
        this.mChannelWallText = (TextView) findViewById(R.id.channel_wall_custom);
        this.mChannelWallText.setText(TipUtils.getTipMessage(DialogMsgConstantId.CONSTANT_700095, 2131099852));
        this.mChannelWallText.setOnClickListener(this);
        this.mBtnBack = (ImageView) findViewById(2131362351);
        this.mBtnBack.setOnClickListener(this);
    }

    protected void onResume() {
        super.onResume();
        StatisticsUtils.statisticsActionInfo(this, "114", "19", null, null, -1, null);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case 2131362351:
                finish();
                return;
            case R.id.first_page_custom /*2131363865*/:
                StatisticsUtils.statisticsActionInfo(this.mContext, "114", "0", "cu01", getString(2131100365), 1, null);
                FirstPageCustomActivity.launch(this.mContext);
                return;
            case R.id.channel_wall_custom /*2131363866*/:
                StatisticsUtils.statisticsActionInfo(this.mContext, "114", "0", "cu01", getString(2131099856), 2, null);
                Intent intent = new Intent(getActivity(), ChannelWallActivity.class);
                intent.putExtra("fromMine", true);
                startActivity(intent);
                return;
            default:
                return;
        }
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public Activity getActivity() {
        return this;
    }

    public String getActivityName() {
        return MineCustomActivity.class.getName();
    }
}
