package master.flame.danmaku.danmaku.model.objectpool;

public interface Pool<T extends Poolable<T>> {
    T acquire();

    void release(T t);
}
