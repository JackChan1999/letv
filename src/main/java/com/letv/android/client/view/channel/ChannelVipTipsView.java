package com.letv.android.client.view.channel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.R;
import com.letv.android.client.activity.LetvVipActivity;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ChannelVipTipsView {
    private Context mContext;
    private View mView;
    private TextView mVipView;

    public ChannelVipTipsView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mContext = context;
        init();
    }

    private void init() {
        this.mView = LayoutInflater.from(this.mContext).inflate(R.layout.channel_detail_vip_tips_layout, null);
        this.mVipView = (TextView) this.mView.findViewById(R.id.channel_detail_vip_tips_button);
        showVipText();
        this.mVipView.setOnClickListener(new 1(this));
    }

    private void rechargeOrBeVip(boolean isVip, boolean isSeniorVip) {
        String title = this.mContext.getResources().getString(isVip ? 2131100646 : 2131100645);
        if (NetworkUtils.isNetworkAvailable()) {
            LogInfo.LogStatistics(isVip ? "会员续费" : "开通会员");
            StatisticsUtils.staticticsInfoPost(this.mContext, "0", "vp10", "", isVip ? 2 : 1, null, "010", null, null, null, null, null);
            LetvVipActivity.launch(this.mContext, title);
            return;
        }
        ToastUtils.showToast(LetvApplication.getInstance(), 2131100493);
    }

    public View getView() {
        return this.mView;
    }

    public void showVipText() {
        boolean isUserLogin = PreferencesManager.getInstance().isLogin();
        boolean isVip = PreferencesManager.getInstance().isVip();
        TextView textView = this.mVipView;
        int i = (isUserLogin && isVip) ? 2131100552 : 2131100535;
        textView.setText(i);
    }
}
