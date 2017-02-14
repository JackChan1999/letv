package com.letv.business.flow.play;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import com.letv.component.player.Interface.OnVideoViewStateChangeListener;
import com.letv.core.listener.OnRelevantStateChangeListener;

public abstract class BasePlayFlow implements OnRelevantStateChangeListener, OnVideoViewStateChangeListener {
    protected Context context;
    private int mLaunchMode = 0;
    private boolean mOnlyFull;

    public abstract void cancelLongTimeWatch();

    public abstract void changeDirection(boolean z);

    public abstract void curVolume(int i, int i2);

    public abstract void curVolume(int i, int i2, boolean z);

    public abstract void format();

    public abstract int getPlayLevel();

    public abstract void initData();

    public abstract void initViews();

    public abstract void lockScreenPause();

    public abstract void onActivityResultLoginSuccess();

    public abstract void onActivityResultPaySuccess(boolean z);

    public abstract void onDestroy();

    public abstract void startLongWatchCountDown();

    public abstract void unLockSceenResume();

    public BasePlayFlow(Context context) {
        this.context = context;
    }

    public void create() {
        initData();
        initViews();
    }

    public Activity getActivity() {
        return (Activity) this.context;
    }

    public void setOnlyFull(boolean isFull) {
        this.mOnlyFull = isFull;
    }

    public int getCurSoundVolume() {
        return 0;
    }

    public int getMaxSoundVolume() {
        return 0;
    }

    public void wakeLock() {
    }

    public void setBrightness(float value) {
    }

    public void setLaunchMode(int launchMode) {
        this.mLaunchMode = launchMode;
    }

    public int getLaunchMode() {
        return this.mLaunchMode;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
