package master.flame.danmaku.danmaku.model.android;

import master.flame.danmaku.danmaku.model.IDrawingCache;
import master.flame.danmaku.danmaku.model.objectpool.Poolable;

public class DrawingCache implements IDrawingCache<DrawingCacheHolder>, Poolable<DrawingCache> {
    private final DrawingCacheHolder mHolder = new DrawingCacheHolder();
    private boolean mIsPooled;
    private DrawingCache mNextElement;
    private int mSize = 0;
    private int referenceCount = 0;

    public void build(int w, int h, int density, boolean checkSizeEquals) {
        this.mHolder.buildCache(w, h, density, checkSizeEquals);
        this.mSize = this.mHolder.bitmap.getRowBytes() * this.mHolder.bitmap.getHeight();
    }

    public void erase() {
        this.mHolder.erase();
    }

    public DrawingCacheHolder get() {
        if (this.mHolder.bitmap == null) {
            return null;
        }
        return this.mHolder;
    }

    public void destroy() {
        if (this.mHolder != null) {
            this.mHolder.recycle();
        }
        this.mSize = 0;
        this.referenceCount = 0;
    }

    public int size() {
        return this.mSize;
    }

    public void setNextPoolable(DrawingCache element) {
        this.mNextElement = element;
    }

    public DrawingCache getNextPoolable() {
        return this.mNextElement;
    }

    public boolean isPooled() {
        return this.mIsPooled;
    }

    public void setPooled(boolean isPooled) {
        this.mIsPooled = isPooled;
    }

    public synchronized boolean hasReferences() {
        return this.referenceCount > 0;
    }

    public synchronized void increaseReference() {
        this.referenceCount++;
    }

    public synchronized void decreaseReference() {
        this.referenceCount--;
    }

    public int width() {
        return this.mHolder.width;
    }

    public int height() {
        return this.mHolder.height;
    }
}
