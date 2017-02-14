package com.letv.android.client.commonlib.messagemodel;

import android.content.Intent;
import android.support.v4.app.DialogFragment;

public interface WebViewShareProtocol {

    public interface OnWebViewRefreshListener {
        void onRefresh();
    }

    void activityResult(int i, int i2, Intent intent);

    DialogFragment getFragment();

    void notifyShareLayout();

    void setDefaultShareText(String str, String str2, String str3, String str4, String str5);

    void setIsLoadComplete(boolean z);

    void setJsonShareText(String str);
}
