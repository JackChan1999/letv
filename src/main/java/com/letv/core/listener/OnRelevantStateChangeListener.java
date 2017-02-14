package com.letv.core.listener;

public interface OnRelevantStateChangeListener {
    void onBatteryChange(int i, int i2);

    void onDownloadStateChange();

    void onHeadsetPlug();

    void onNetChange();

    void onTimeChange();
}
