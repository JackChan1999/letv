package com.letv.android.client.barrage.engine;

import com.letv.android.client.barrage.BarragePlayControl;
import com.letv.core.bean.BarrageBean;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;

public interface IDanmakuEngineManager extends BarragePlayControl {
    void addDanmaku(boolean z, BaseDanmaku baseDanmaku);

    DanmakuContext getDanmakuContext(boolean z);

    void hideAllDanmaku();

    void hideDanmaku(boolean z);

    void pauseDanmaku(boolean z);

    void resumeDanmaku(boolean z);

    void sendDanmaku(boolean z, BarrageBean barrageBean);

    void showAllDanmaku();

    void showDanmaku(boolean z);
}
