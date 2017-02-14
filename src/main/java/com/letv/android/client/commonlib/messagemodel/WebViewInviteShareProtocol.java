package com.letv.android.client.commonlib.messagemodel;

import android.content.Intent;
import android.support.v4.app.DialogFragment;

public interface WebViewInviteShareProtocol {
    void activityResult(int i, int i2, Intent intent);

    DialogFragment getFragment();

    void setShareText(String str);
}
