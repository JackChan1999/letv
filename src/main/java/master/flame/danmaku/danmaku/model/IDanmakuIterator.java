package master.flame.danmaku.danmaku.model;

public interface IDanmakuIterator {
    boolean hasNext();

    BaseDanmaku next();

    void remove();

    void reset();
}
