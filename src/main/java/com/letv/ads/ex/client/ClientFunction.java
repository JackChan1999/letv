package com.letv.ads.ex.client;

import android.graphics.Rect;

public abstract class ClientFunction {
    public abstract Rect getPlayerRect();

    public abstract long getVideoCurrentTime();

    public abstract void onAdFullProcessTimeout(boolean z);

    public abstract void onHalfBackDown();

    public abstract void pauseVideo();

    public abstract void resumeVideo();

    public abstract void setHalfOrFullScreen(boolean z, boolean z2);

    public abstract void skipAd();

    public void closeAd() {
    }

    public long getADCurrentTime() {
        return 0;
    }

    public boolean isADPlaying() {
        return false;
    }

    public boolean isADPaused() {
        return false;
    }
}
