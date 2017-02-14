package com.letv.android.client.commonlib.config;

import android.view.MotionEvent;

public interface FloatBallConfig {
    boolean dispatchTouchEvent(MotionEvent motionEvent);

    void hideFloat();

    void hideFloat(boolean z);

    void onDestory();

    void onPause();

    void openFloat();

    String replaceNameTag(int i);

    void showFloat();

    void showFloat(String str);

    void showFloat(String str, String str2);
}
