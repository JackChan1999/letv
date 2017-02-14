package com.letv.android.client.album.listener;

public interface VideoControllerMeditor$VideoToControllerListener {
    void initProcess(int i, int i2, int i3);

    void onAudioTrackSwitchFinish();

    void onStreamSwitchFinish(boolean z);

    void pause();

    void setBlockBtnVisibile(boolean z);

    void setControllerVisibility(int i, boolean z);

    void setEnable(boolean z);

    void setTotalTime(int i);

    void start(boolean z);

    void updateProgress(int i, int i2);
}
