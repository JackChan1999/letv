package com.letv.android.client.barrage.live;

import android.view.MotionEvent;
import com.letv.core.bean.BarrageBean;

public interface LiveBarrageCallback {
    boolean isClickInterpretBarrage(MotionEvent motionEvent);

    String onGetCurrentPlayUrl();

    float onGetCurrentVideoPlayTime();

    void onLiveBarrageSent(BarrageBean barrageBean);

    void onReceiveInterpretBarrage(BarrageBean barrageBean);

    void onResumePlay();

    void onSettingInterpretBarrage();
}
