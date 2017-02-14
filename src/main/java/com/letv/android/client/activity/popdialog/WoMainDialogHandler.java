package com.letv.android.client.activity.popdialog;

import android.content.Context;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.activity.popdialog.DialogHandler.IMainPopDialogHandler;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class WoMainDialogHandler extends DialogHandler {
    private static final String TAG = WoMainDialogHandler.class.getSimpleName();
    private Context mContext;
    private MainActivity mMainActivity;

    public WoMainDialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(mainActivity);
        this.mContext = LetvApplication.getInstance();
    }

    public void handleRequest() {
        setFragmentRecordVisiable(false);
        isShowWoMainDialog(new 1(this));
    }

    private void isShowWoMainDialog(IMainPopDialogHandler imainPopDialogHandler) {
        if (NetworkUtils.isUnicom3G(false)) {
            LogInfo.log("king", "showWoMainDialog");
            new UnicomWoFlowDialogUtils().showWoMainDialog(this.mMainActivity, new 2(this, imainPopDialogHandler), "MainActivityGroup");
            return;
        }
        imainPopDialogHandler.onSetSuccessor();
    }
}
