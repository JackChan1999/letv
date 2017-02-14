package com.letv.android.client.commonlib.share;

public interface WXShareResultObserver {
    void onWXCanneled();

    void onWXShareFail();

    void onWXShareSucceed();
}
