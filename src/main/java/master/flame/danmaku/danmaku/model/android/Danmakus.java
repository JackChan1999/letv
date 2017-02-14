package master.flame.danmaku.danmaku.model.android;

import com.letv.plugin.pluginconfig.commom.JarConstant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.Danmaku;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.util.DanmakuUtils;

public class Danmakus implements IDanmakus {
    public static final int ST_BY_LIST = 4;
    public static final int ST_BY_TIME = 0;
    public static final int ST_BY_YPOS = 1;
    public static final int ST_BY_YPOS_DESC = 2;
    private BaseDanmaku endItem;
    private BaseDanmaku endSubItem;
    public Collection<BaseDanmaku> items;
    private DanmakuIterator iterator;
    private BaseComparator mComparator;
    private boolean mDuplicateMergingEnabled;
    private int mSize;
    private int mSortType;
    private BaseDanmaku startItem;
    private BaseDanmaku startSubItem;
    private Danmakus subItems;

    private class BaseComparator implements Comparator<BaseDanmaku> {
        protected boolean mDuplicateMergingEnable;

        public BaseComparator(boolean duplicateMergingEnabled) {
            setDuplicateMergingEnabled(duplicateMergingEnabled);
        }

        public void setDuplicateMergingEnabled(boolean enable) {
            this.mDuplicateMergingEnable = enable;
        }

        public int compare(BaseDanmaku obj1, BaseDanmaku obj2) {
            if (this.mDuplicateMergingEnable && DanmakuUtils.isDuplicate(obj1, obj2)) {
                return 0;
            }
            return DanmakuUtils.compare(obj1, obj2);
        }
    }

    private class DanmakuIterator implements IDanmakuIterator {
        private Iterator<BaseDanmaku> it;
        private Collection<BaseDanmaku> mData;
        private boolean mIteratorUsed;

        public DanmakuIterator(Collection<BaseDanmaku> datas) {
            setDatas(datas);
        }

        public synchronized void reset() {
            if (this.mIteratorUsed || this.it == null) {
                if (this.mData == null || Danmakus.this.mSize <= 0) {
                    this.it = null;
                } else {
                    this.it = this.mData.iterator();
                }
            }
        }

        public synchronized void setDatas(Collection<BaseDanmaku> datas) {
            if (this.mData != datas) {
                this.mIteratorUsed = false;
                this.it = null;
            }
            this.mData = datas;
        }

        public synchronized BaseDanmaku next() {
            this.mIteratorUsed = true;
            return this.it != null ? (BaseDanmaku) this.it.next() : null;
        }

        public synchronized boolean hasNext() {
            boolean z;
            z = this.it != null && this.it.hasNext();
            return z;
        }

        public synchronized void remove() {
            this.mIteratorUsed = true;
            if (this.it != null) {
                this.it.remove();
                Danmakus.this.mSize = Danmakus.this.mSize - 1;
            }
        }
    }

    private class TimeComparator extends BaseComparator {
        public TimeComparator(boolean duplicateMergingEnabled) {
            super(duplicateMergingEnabled);
        }

        public int compare(BaseDanmaku obj1, BaseDanmaku obj2) {
            return super.compare(obj1, obj2);
        }
    }

    private class YPosComparator extends BaseComparator {
        public YPosComparator(boolean duplicateMergingEnabled) {
            super(duplicateMergingEnabled);
        }

        public int compare(BaseDanmaku obj1, BaseDanmaku obj2) {
            if (this.mDuplicateMergingEnable && DanmakuUtils.isDuplicate(obj1, obj2)) {
                return 0;
            }
            return Float.compare(obj1.getTop(), obj2.getTop());
        }
    }

    private class YPosDescComparator extends BaseComparator {
        public YPosDescComparator(boolean duplicateMergingEnabled) {
            super(duplicateMergingEnabled);
        }

        public int compare(BaseDanmaku obj1, BaseDanmaku obj2) {
            if (this.mDuplicateMergingEnable && DanmakuUtils.isDuplicate(obj1, obj2)) {
                return 0;
            }
            return Float.compare(obj2.getTop(), obj1.getTop());
        }
    }

    public Danmakus() {
        this(0, false);
    }

    public Danmakus(int sortType) {
        this(sortType, false);
    }

    public Danmakus(int sortType, boolean duplicateMergingEnabled) {
        this.mSize = 0;
        this.mSortType = 0;
        BaseComparator comparator = null;
        if (sortType == 0) {
            comparator = new TimeComparator(duplicateMergingEnabled);
        } else if (sortType == 1) {
            comparator = new YPosComparator(duplicateMergingEnabled);
        } else if (sortType == 2) {
            comparator = new YPosDescComparator(duplicateMergingEnabled);
        }
        if (sortType == 4) {
            this.items = new ArrayList();
        } else {
            this.mDuplicateMergingEnabled = duplicateMergingEnabled;
            comparator.setDuplicateMergingEnabled(duplicateMergingEnabled);
            this.items = new TreeSet(comparator);
            this.mComparator = comparator;
        }
        this.mSortType = sortType;
        this.mSize = 0;
        this.iterator = new DanmakuIterator(this.items);
    }

    public Danmakus(Collection<BaseDanmaku> items) {
        this.mSize = 0;
        this.mSortType = 0;
        setItems(items);
    }

    public Danmakus(boolean duplicateMergingEnabled) {
        this(0, duplicateMergingEnabled);
    }

    public void setItems(Collection<BaseDanmaku> items) {
        if (!this.mDuplicateMergingEnabled || this.mSortType == 4) {
            this.items = items;
        } else {
            this.items.clear();
            this.items.addAll(items);
            items = this.items;
        }
        if (items instanceof List) {
            this.mSortType = 4;
        }
        this.mSize = items == null ? 0 : items.size();
        if (this.iterator == null) {
            this.iterator = new DanmakuIterator(items);
        } else {
            this.iterator.setDatas(items);
        }
    }

    public IDanmakuIterator iterator() {
        this.iterator.reset();
        return this.iterator;
    }

    public boolean addItem(BaseDanmaku item) {
        if (this.items != null) {
            try {
                if (this.items.add(item)) {
                    this.mSize++;
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean removeItem(BaseDanmaku item) {
        if (item == null) {
            return false;
        }
        if (item.isOutside()) {
            item.setVisibility(false);
        }
        if (!this.items.remove(item)) {
            return false;
        }
        this.mSize--;
        return true;
    }

    private Collection<BaseDanmaku> subset(long startTime, long endTime) {
        if (this.mSortType == 4 || this.items == null || this.items.size() == 0) {
            return null;
        }
        if (this.subItems == null) {
            this.subItems = new Danmakus(this.mDuplicateMergingEnabled);
        }
        if (this.startSubItem == null) {
            this.startSubItem = createItem(JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START);
        }
        if (this.endSubItem == null) {
            this.endSubItem = createItem("end");
        }
        this.startSubItem.time = startTime;
        this.endSubItem.time = endTime;
        return ((SortedSet) this.items).subSet(this.startSubItem, this.endSubItem);
    }

    public IDanmakus subnew(long startTime, long endTime) {
        Collection<BaseDanmaku> subset = subset(startTime, endTime);
        if (subset == null || subset.isEmpty()) {
            return null;
        }
        return new Danmakus(new ArrayList(subset));
    }

    public IDanmakus sub(long startTime, long endTime) {
        if (this.items == null || this.items.size() == 0) {
            return null;
        }
        if (this.subItems == null) {
            if (this.mSortType == 4) {
                this.subItems = new Danmakus(4);
                this.subItems.setItems(this.items);
            } else {
                this.subItems = new Danmakus(this.mDuplicateMergingEnabled);
            }
        }
        if (this.mSortType == 4) {
            return this.subItems;
        }
        if (this.startItem == null) {
            this.startItem = createItem(JarConstant.PLUGIN_WINDOW_PLAYER_STATIC_METHOD_NAME_START);
        }
        if (this.endItem == null) {
            this.endItem = createItem("end");
        }
        if (this.subItems != null && startTime - this.startItem.time >= 0 && endTime <= this.endItem.time) {
            return this.subItems;
        }
        this.startItem.time = startTime;
        this.endItem.time = endTime;
        this.subItems.setItems(((SortedSet) this.items).subSet(this.startItem, this.endItem));
        return this.subItems;
    }

    private BaseDanmaku createItem(String text) {
        return new Danmaku(text);
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        if (this.items != null) {
            this.items.clear();
            this.mSize = 0;
        }
        if (this.subItems != null) {
            this.subItems.clear();
        }
    }

    public BaseDanmaku first() {
        if (this.items == null || this.items.isEmpty()) {
            return null;
        }
        if (this.mSortType == 4) {
            return (BaseDanmaku) ((ArrayList) this.items).get(0);
        }
        return (BaseDanmaku) ((SortedSet) this.items).first();
    }

    public BaseDanmaku last() {
        if (this.items == null || this.items.isEmpty()) {
            return null;
        }
        if (this.mSortType == 4) {
            return (BaseDanmaku) ((ArrayList) this.items).get(this.items.size() - 1);
        }
        return (BaseDanmaku) ((SortedSet) this.items).last();
    }

    public boolean contains(BaseDanmaku item) {
        return this.items != null && this.items.contains(item);
    }

    public boolean isEmpty() {
        return this.items == null || this.items.isEmpty();
    }

    private void setDuplicateMergingEnabled(boolean enable) {
        this.mComparator.setDuplicateMergingEnabled(enable);
        this.mDuplicateMergingEnabled = enable;
    }

    public void setSubItemsDuplicateMergingEnabled(boolean enable) {
        this.mDuplicateMergingEnabled = enable;
        this.endItem = null;
        this.startItem = null;
        if (this.subItems == null) {
            this.subItems = new Danmakus(enable);
        }
        this.subItems.setDuplicateMergingEnabled(enable);
    }
}
