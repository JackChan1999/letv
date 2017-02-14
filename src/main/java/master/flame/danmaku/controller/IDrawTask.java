package master.flame.danmaku.controller;

import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;

public interface IDrawTask {

    public interface TaskListener {
        void onDanmakuAdd(BaseDanmaku baseDanmaku);

        void onDanmakuConfigChanged();

        void onDanmakuShown(BaseDanmaku baseDanmaku);

        void onDanmakusDrawingFinished();

        void ready();
    }

    void addDanmaku(BaseDanmaku baseDanmaku);

    void clearDanmakusOnScreen(long j);

    RenderingState draw(AbsDisplayer absDisplayer);

    BaseDanmaku getLastDanmaku();

    IDanmakus getVisibleDanmakusOnTime(long j);

    void invalidateDanmaku(BaseDanmaku baseDanmaku, boolean z);

    void prepare();

    void quit();

    void removeAllDanmakus();

    void removeAllLiveDanmakus();

    void requestClear();

    void requestHide();

    void reset();

    void seek(long j);

    void setParser(BaseDanmakuParser baseDanmakuParser);

    void start();
}
