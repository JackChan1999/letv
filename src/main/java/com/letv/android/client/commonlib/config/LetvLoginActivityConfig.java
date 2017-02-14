package com.letv.android.client.commonlib.config;

import android.content.Context;
import com.letv.android.client.commonlib.R;
import com.letv.core.messagebus.config.LeIntentConfig;
import com.letv.core.messagebus.config.LeIntentConfig.IntentFlag;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.ToastUtils;

public class LetvLoginActivityConfig extends LeIntentConfig {
    public static final String AWARDURL = "awardUrl";
    public static final String FOR_WHAT = "forWhat";
    public static final String FROM_HOME = "fromHome";
    public static final int LOGIN = 16;
    public static final int RED_PACKET_LOGIN_REQUEST_CODE = 10;

    public LetvLoginActivityConfig(Context context) {
        super(context);
    }

    public LetvLoginActivityConfig create() {
        if (checkHK(16)) {
            return null;
        }
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(16);
        return this;
    }

    public LetvLoginActivityConfig create(int requestCode) {
        if (checkHK(requestCode)) {
            return null;
        }
        if (requestCode == 0) {
            return this;
        }
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(requestCode);
        return this;
    }

    public LetvLoginActivityConfig create(int forwhat, int requestCode) {
        if (checkHK(requestCode)) {
            return null;
        }
        getIntent().putExtra(FOR_WHAT, forwhat);
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(requestCode);
        return this;
    }

    public LetvLoginActivityConfig createForWhat(int forwhat) {
        if (checkHKForWhat(forwhat)) {
            return null;
        }
        getIntent().putExtra(FOR_WHAT, forwhat);
        return this;
    }

    private boolean checkHK(int requestCode) {
        if (!LetvUtils.isInHongKong()) {
            return false;
        }
        if (NetworkUtils.isNetworkAvailable()) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new HongKongLoginWebviewConfig(this.mContext).create(requestCode)));
            return true;
        }
        ToastUtils.showToast(R.string.load_data_no_net);
        return true;
    }

    private boolean checkHKForWhat(int forWhat) {
        if (!LetvUtils.isInHongKong()) {
            return false;
        }
        if (NetworkUtils.isNetworkAvailable()) {
            LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new HongKongLoginWebviewConfig(this.mContext).create(forWhat, 0)));
            return true;
        }
        ToastUtils.showToast(R.string.load_data_no_net);
        return true;
    }

    public LetvLoginActivityConfig createAward(String award, int requestCode) {
        if (checkHK(requestCode)) {
            return null;
        }
        getIntent().putExtra(AWARDURL, award);
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(requestCode);
        return this;
    }

    public LetvLoginActivityConfig createFromHome() {
        if (checkHK(16)) {
            return null;
        }
        getIntent().putExtra(FROM_HOME, 16);
        setIntentFlag(IntentFlag.START_ACTIVITY_FOR_RESULT);
        setRequestCode(16);
        return this;
    }
}
