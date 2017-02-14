package com.letv.android.client.commonlib.fragement;

public interface LetvFragmentListener {
    public static final int HIDE = 0;
    public static final int KEEP = 2;
    public static final int REMOVE = 1;

    int getContainerId();

    int getDisappearFlag();

    String getTagName();

    void onHide();

    void onShow();
}
