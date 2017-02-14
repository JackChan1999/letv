package com.letv.business.flow.statistics;

import android.os.Looper;
import com.letv.business.flow.statistics.LivePlayStatisticsHelper.LivePlayStatisticsHelperCallBack;

public class LivePlayingHandlerThread extends Thread {
    private LivePlayingHandler mHandler;
    private boolean mHasStart = false;

    public LivePlayingHandlerThread(LivePlayStatisticsHelperCallBack callBack) {
        this.mHandler = new LivePlayingHandler(callBack);
    }

    public void run() {
        try {
            Looper.prepare();
            this.mHandler.start();
            Looper.loop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        if (this.mHasStart || isAlive()) {
            this.mHandler.start();
        } else {
            super.start();
        }
        this.mHasStart = true;
    }

    public void pause() {
        this.mHandler.pause();
    }

    public void stopThread(boolean needRepot) {
        try {
            if (this.mHasStart && needRepot) {
                this.mHandler.stop();
            }
            this.mHasStart = false;
            interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIsBlocking(boolean isBlocking) {
        this.mHandler.mIsBlocking = isBlocking;
    }
}
