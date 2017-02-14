package com.letv.android.client.commonlib.share;

public interface ShareResultObserver {
    void onCanneled();

    void onShareFail();

    void onShareSucceed();
}
