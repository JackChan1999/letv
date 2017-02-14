package com.letv.android.client.barrage;

import android.view.MotionEvent;
import com.letv.android.client.barrage.engine.DanmakuDanmakuEngine.Builder;
import com.letv.core.bean.BarrageBean;
import master.flame.danmaku.danmaku.model.BaseDanmaku;

public interface BarragePlayControl {
    void addDanmaku(BaseDanmaku baseDanmaku);

    int getMaximumVisibleSizeInScreen();

    void hideBarrage();

    void initBarrage(Builder builder);

    boolean isClickDanmuku(MotionEvent motionEvent);

    boolean isFBDanmakuVisibility();

    boolean isFTDanmakuVisibility();

    boolean isPause();

    boolean isPrepare();

    boolean isR2LDanmakuVisibility();

    boolean isShow();

    void pauseBarrage();

    void removeAllDanmaku();

    void resumeBarrage();

    void sendBarrage(BarrageBean barrageBean);

    void setFBDanmakuVisibility(boolean z);

    void setFTDanmakuVisibility(boolean z);

    void setMaximumVisibleSizeInScreen(int i);

    void setR2LDanmakuVisibility(boolean z);

    void showBarrage();

    void startBarrage(Runnable runnable);

    void stopBarrage();
}
