package com.letv.business.flow.main;

import com.letv.core.bean.LetvBaseBean;
import com.letv.core.bean.TipMapBean;

public interface MainFlowCallback {
    void beanCallBack(LetvBaseBean letvBaseBean);

    void checkAd(boolean z);

    void checkRedPacket();

    void checkUninstallEnable(boolean z);

    void checkUpdate(boolean z);

    void dexPatch();

    void homePageLoad();

    void locationChange();

    void onTipCallback(TipMapBean tipMapBean);

    void showChannelRecommend(boolean z);

    void updateUI();
}
