package master.flame.danmaku.danmaku.model;

public interface IDanmakus {
    boolean addItem(BaseDanmaku baseDanmaku);

    void clear();

    boolean contains(BaseDanmaku baseDanmaku);

    BaseDanmaku first();

    boolean isEmpty();

    IDanmakuIterator iterator();

    BaseDanmaku last();

    boolean removeItem(BaseDanmaku baseDanmaku);

    void setSubItemsDuplicateMergingEnabled(boolean z);

    int size();

    IDanmakus sub(long j, long j2);

    IDanmakus subnew(long j, long j2);
}
