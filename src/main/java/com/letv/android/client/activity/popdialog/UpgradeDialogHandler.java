package com.letv.android.client.activity.popdialog;

import com.letv.android.client.R;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.activity.popdialog.DialogHandler.IMainPopDialogHandler;
import com.letv.component.upgrade.core.upgrade.CheckUpgradeController;
import com.letv.component.upgrade.core.upgrade.UpgradeManager;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.datastatistics.util.DataConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class UpgradeDialogHandler extends DialogHandler {
    private static final String TAG = UpgradeDialogHandler.class.getSimpleName();
    UpgradeManager mUpgradeManager;

    public UpgradeDialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(mainActivity);
    }

    public void handleRequest() {
        setFragmentRecordVisiable(false);
        checkUpgrade(new 1(this));
    }

    public void destroyUpgrade() {
        try {
            if (this.mUpgradeManager != null) {
                this.mUpgradeManager.exitApp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkUpgrade(IMainPopDialogHandler iMainPopDialogHandler) {
        if (LetvUtils.isDelayUpgrade()) {
            LogInfo.log(TAG, "UpgradeDialogHandler handleRequest getUpgradeTime : " + PreferencesManager.getInstance().getUpgradeTime());
            if (PreferencesManager.getInstance().getUpgradeTime() != -1) {
                if (PreferencesManager.getInstance().getUpgradeTime() == 0) {
                    PreferencesManager.getInstance().setUpgradeTime(System.currentTimeMillis());
                    return;
                }
                long interval = System.currentTimeMillis() - PreferencesManager.getInstance().getUpgradeTime();
                LogInfo.log(TAG, "UpgradeDialogHandler handleRequest interval : " + interval);
                if (interval > 864000000) {
                    LogInfo.log(TAG, "UpgradeDialogHandler handleRequest interval > 10 day");
                    PreferencesManager.getInstance().setUpgradeTime(-1);
                } else {
                    return;
                }
            }
        }
        LogInfo.log(TAG, "checkUpgrade>>>>>> " + LetvConfig.getAppKey());
        this.mUpgradeManager = UpgradeManager.getInstance();
        this.mUpgradeManager.init(this.mMainActivity, LetvConfig.getPcode(), false, LetvConfig.getAppKey(), R.layout.upgrade_dialog_view, 2131230964, "0", "00", DataConstant.P3);
        if (!LetvUtils.isGooglePlay()) {
            this.mUpgradeManager.upgrade(new 2(this, iMainPopDialogHandler), CheckUpgradeController.CHECK_BY_SELF, 1);
        } else if (iMainPopDialogHandler != null) {
            iMainPopDialogHandler.onSetSuccessor();
        }
    }
}
