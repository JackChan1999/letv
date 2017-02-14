package com.letv.business.flow.album.listener;

import com.letv.core.bean.PlayRecord;

public interface PlayVideoFragmentListener {
    void buyWo3G();

    void checkDrmPlugin();

    void finishPlayer(boolean z);

    int getBufferPercentage();

    long getCurrentPosition();

    long getDuration();

    PlayRecord getPoint(int i, int i2, boolean z);

    void handlerFloatBall(String str, int i);

    void hideRecommendTip();

    void initVideoView(boolean z, boolean z2);

    boolean isEnforcementPause();

    boolean isPaused();

    boolean isPlaying();

    void loadDrmPlugin();

    void onChangeStreamError();

    void pause();

    void playAnotherVideo(boolean z);

    void rePlay(boolean z);

    void resetPlayFlag();

    void seekTo(long j, boolean z);

    void setEnforcementPause(boolean z);

    void setEnforcementWait(boolean z);

    void start();

    void startOverall();

    void startPlayLocal(String str, long j, boolean z);

    void startPlayNet(String str, long j, boolean z, boolean z2);

    void stopPlayback();
}
