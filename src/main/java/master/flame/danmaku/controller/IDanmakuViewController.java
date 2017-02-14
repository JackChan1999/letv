package master.flame.danmaku.controller;

import android.content.Context;

public interface IDanmakuViewController {
    void clear();

    long drawDanmakus();

    Context getContext();

    int getHeight();

    int getWidth();

    boolean isDanmakuDrawingCacheEnabled();

    boolean isHardwareAccelerated();

    boolean isViewReady();
}
