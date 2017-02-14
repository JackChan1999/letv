package com.letv.android.client.listener;

public interface PlayActivityCallback {
    void blockEnd();

    void blockStart();

    void blockTwiceAlert();

    void callAdsInterface(int i, boolean z);

    void cancelLongTimeWatch();

    void closeSensor();

    void finishPlayer();

    int getCurrentPlayPosition();

    int getPlayLevel();

    boolean isDlnaPlaying();

    boolean isEnforcementPause();

    boolean isEnforcementWait();

    boolean isPlaying();

    void lockOnce(int i);

    void openSensor();

    void pauseVideo();

    void playNet(String str, boolean z, boolean z2, int i);

    void playPause();

    void resumeVideo();

    void setEnforcementPause(boolean z);

    void setEnforcementWait(boolean z);

    void setFloatBallVisible(boolean z);

    void setLock(boolean z);

    int setOneFingertouchInfomation(float f, float f2, float f3, float f4);

    int setTwoScale(float f);

    void start(String str, boolean z);

    void stopPlayback();
}
