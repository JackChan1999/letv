package com.letv.business.flow.album.hot.listener;

public interface HotPlayListener {
    int getCurrentTime();

    boolean isComplete();

    boolean isPlaying();

    boolean isShow3gToast();

    boolean isVideoviewNull();

    void pause();

    void pauseFor3GtoWifi();

    void pauseToPlay(boolean z);

    void playNet(String str, int i);

    void setAutoPlay(boolean z);

    void setIsOnPreparePause(boolean z);

    void setPlayTime(int i);

    void setPlayUri(String str);

    void setShowToast(boolean z);

    void setUrlNull();

    void start(int i);
}
