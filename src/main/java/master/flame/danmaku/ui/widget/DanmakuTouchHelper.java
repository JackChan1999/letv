package master.flame.danmaku.ui.widget;

import android.graphics.RectF;
import android.view.MotionEvent;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.Danmakus;

public class DanmakuTouchHelper {
    private IDanmakuView danmakuView;
    private RectF mDanmakuBounds = new RectF();

    private DanmakuTouchHelper(IDanmakuView danmakuView) {
        this.danmakuView = danmakuView;
    }

    public static synchronized DanmakuTouchHelper instance(IDanmakuView danmakuView) {
        DanmakuTouchHelper danmakuTouchHelper;
        synchronized (DanmakuTouchHelper.class) {
            danmakuTouchHelper = new DanmakuTouchHelper(danmakuView);
        }
        return danmakuTouchHelper;
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 1:
                IDanmakus clickDanmakus = touchHitDanmaku(event.getX(), event.getY());
                BaseDanmaku newestDanmaku = null;
                if (!(clickDanmakus == null || clickDanmakus.isEmpty())) {
                    performClick(clickDanmakus);
                    newestDanmaku = fetchLatestOne(clickDanmakus);
                }
                if (newestDanmaku != null) {
                    performClickWithlatest(newestDanmaku);
                    break;
                }
                break;
        }
        return false;
    }

    private void performClickWithlatest(BaseDanmaku newest) {
        if (this.danmakuView.getOnDanmakuClickListener() != null) {
            this.danmakuView.getOnDanmakuClickListener().onDanmakuClick(newest);
        }
    }

    private void performClick(IDanmakus danmakus) {
        if (this.danmakuView.getOnDanmakuClickListener() != null) {
            this.danmakuView.getOnDanmakuClickListener().onDanmakuClick(danmakus);
        }
    }

    public IDanmakus touchHitDanmaku(float x, float y) {
        IDanmakus hitDanmakus = new Danmakus();
        this.mDanmakuBounds.setEmpty();
        IDanmakus danmakus = this.danmakuView.getCurrentVisibleDanmakus();
        if (danmakus != null && !danmakus.isEmpty()) {
            IDanmakuIterator iterator = danmakus.iterator();
            while (iterator.hasNext()) {
                BaseDanmaku danmaku = iterator.next();
                if (danmaku != null) {
                    this.mDanmakuBounds.set(danmaku.getLeft(), danmaku.getTop() - 30.0f, danmaku.getRight(), danmaku.getBottom() + 30.0f);
                    if (this.mDanmakuBounds.contains(x, y)) {
                        danmaku.clickX = x;
                        danmaku.clickY = y;
                        danmaku.setRectF(this.mDanmakuBounds);
                        hitDanmakus.addItem(danmaku);
                        break;
                    }
                }
            }
        }
        return hitDanmakus;
    }

    private BaseDanmaku fetchLatestOne(IDanmakus danmakus) {
        if (danmakus.isEmpty()) {
            return null;
        }
        return danmakus.last();
    }
}
