package master.flame.danmaku.danmaku.model.android;

import master.flame.danmaku.danmaku.model.objectpool.PoolableManager;

public class DrawingCachePoolManager implements PoolableManager<DrawingCache> {
    public DrawingCache newInstance() {
        return null;
    }

    public void onAcquired(DrawingCache element) {
    }

    public void onReleased(DrawingCache element) {
    }
}
