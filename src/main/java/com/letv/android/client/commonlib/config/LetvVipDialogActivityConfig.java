package com.letv.android.client.commonlib.config;

import android.content.Context;
import com.letv.android.client.commonlib.R;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;

public class LetvVipDialogActivityConfig extends LeIntentConfig {
    public static final String TITLE = "title";

    public LetvVipDialogActivityConfig(Context context) {
        super(context);
    }

    public LetvVipDialogActivityConfig create(String title) {
        if (LetvUtils.isInHongKong()) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new LetvVipActivityConfig(this.mContext).create("")));
            return null;
        } else if (NetworkUtils.isNetworkAvailable()) {
            getIntent().putExtra("title", title);
            return this;
        } else {
            ToastUtils.showToast(this.mContext, R.string.load_data_no_net);
            return null;
        }
    }
}
