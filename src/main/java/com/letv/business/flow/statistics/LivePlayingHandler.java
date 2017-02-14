package com.letv.business.flow.statistics;

import android.os.Handler;
import android.os.Message;
import com.letv.business.flow.statistics.LivePlayStatisticsHelper.LivePlayStatisticsHelperCallBack;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;

public class LivePlayingHandler extends Handler {
    public static final int HANDLER_TIME = 512;
    public static final int STATISTICS_TIME_ACTION = 257;
    private static final String TAG = "LivePlayingHandler";
    private final int DELAY_TIME = 1000;
    private LivePlayStatisticsHelperCallBack mHelperCallBack;
    public boolean mIsBlocking = false;
    private int mPlayTime = 0;
    private int mRealPlayTime = 0;
    private int mTimeStep = 0;

    public LivePlayingHandler(LivePlayStatisticsHelperCallBack callBack) {
        this.mHelperCallBack = callBack;
    }

    public void start() {
        removeMessages(512);
        sendEmptyMessage(512);
        removeMessages(257);
        sendEmptyMessageDelayed(257, 15000);
        log("live playing start");
    }

    public void pause() {
        removeMessages(512);
        removeMessages(257);
        log("live playing pause");
    }

    public void stop() {
        pause();
        statisticsPlayEnd();
        this.mPlayTime = 0;
        this.mRealPlayTime = 0;
        this.mTimeStep = 0;
        this.mIsBlocking = false;
        log("live playing stop");
    }

    public void handleMessage(Message msg) {
        switch (msg.what) {
            case 257:
                statisticsTimeAction();
                return;
            case 512:
                onProgressHandler();
                return;
            default:
                return;
        }
    }

    private void onProgressHandler() {
        this.mPlayTime++;
        if (!this.mIsBlocking) {
            this.mRealPlayTime++;
        }
        sendEmptyMessageDelayed(512, 1000);
    }

    private void statisticsTimeAction() {
        int time = this.mRealPlayTime;
        if (this.mTimeStep == 0) {
            if (time < 15) {
                this.mTimeStep = 0;
                sendEmptyMessageDelayed(257, (long) ((15 - time) * 1000));
                log("not playing 15s:" + time);
                return;
            }
            this.mTimeStep = 1;
            sendEmptyMessageDelayed(257, 60000);
            statisticsPlayTime(15);
            log("playing 15s");
        } else if (this.mTimeStep == 1) {
            time = Math.max(0, this.mRealPlayTime - 15);
            if (time < 60) {
                this.mTimeStep = 1;
                sendEmptyMessageDelayed(257, (long) ((60 - time) * 1000));
                log("not playing 60s:" + time);
                return;
            }
            this.mTimeStep = 2;
            sendEmptyMessageDelayed(257, 180000);
            statisticsPlayTime(60);
            log("playing 60s");
        } else {
            time = Math.max(0, ((this.mRealPlayTime - 60) - 15) - ((this.mTimeStep - 2) * 180));
            if (time < 180) {
                sendEmptyMessageDelayed(257, (long) ((180 - time) * 1000));
                log("not playing 180s:" + time + ",timestep=" + this.mTimeStep);
                return;
            }
            sendEmptyMessageDelayed(257, 180000);
            statisticsPlayTime(180);
            log("playing 180s,timestep=" + this.mTimeStep);
            this.mTimeStep++;
        }
    }

    private void statisticsPlayTime(int time) {
        if (this.mHelperCallBack != null) {
            this.mHelperCallBack.statisticsTimeAction(time);
            this.mHelperCallBack.statisticsTimeToPlay();
        }
    }

    private void statisticsPlayEnd() {
        if (this.mHelperCallBack != null) {
            int time;
            boolean isHomeClick = StatisticsUtils.mIsHomeClicked;
            if (this.mTimeStep == 0) {
                time = this.mRealPlayTime;
            } else if (this.mTimeStep == 1) {
                time = this.mRealPlayTime - 15;
            } else if (this.mTimeStep == 2) {
                time = (this.mRealPlayTime - 60) - 15;
            } else {
                time = ((this.mRealPlayTime - ((this.mTimeStep - 2) * 180)) - 60) - 15;
            }
            this.mHelperCallBack.statisticsTimeAction(Math.max(0, time));
            if (!isHomeClick) {
                this.mHelperCallBack.statisticsEndAction();
            }
            log("handler playing end:realPlayTime=" + this.mRealPlayTime + ",totalPlayTime:" + this.mPlayTime);
        }
    }

    private void log(String msg) {
        if (true) {
            LogInfo.log(TAG, msg);
        }
    }
}
