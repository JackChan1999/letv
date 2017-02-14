package com.letv.component.upgrade.core.upgrade;

import com.letv.component.upgrade.bean.UpgradeInfo;

public interface UpgradeCallBack {
    void exitApp();

    void setUpgradeData(UpgradeInfo upgradeInfo);

    void setUpgradeDialog(int i, UpgradeInfo upgradeInfo);

    void setUpgradeState(int i);

    void setUpgradeType(int i, int i2);

    void upgradeData(UpgradeInfo upgradeInfo, int i, int i2);
}
