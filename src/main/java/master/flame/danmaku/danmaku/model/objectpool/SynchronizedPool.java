package master.flame.danmaku.danmaku.model.objectpool;

class SynchronizedPool<T extends Poolable<T>> implements Pool<T> {
    private final Object mLock;
    private final Pool<T> mPool;

    public SynchronizedPool(Pool<T> pool) {
        this.mPool = pool;
        this.mLock = this;
    }

    public SynchronizedPool(Pool<T> pool, Object lock) {
        this.mPool = pool;
        this.mLock = lock;
    }

    public T acquire() {
        T acquire;
        synchronized (this.mLock) {
            acquire = this.mPool.acquire();
        }
        return acquire;
    }

    public void release(T element) {
        synchronized (this.mLock) {
            this.mPool.release(element);
        }
    }
}
