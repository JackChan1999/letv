package android.support.v7.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mMergedSize;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public static abstract class Callback<T2> implements Comparator<T2> {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        public abstract int compare(T2 t2, T2 t22);

        public abstract void onChanged(int i, int i2);

        public abstract void onInserted(int i, int i2);

        public abstract void onMoved(int i, int i2);

        public abstract void onRemoved(int i, int i2);
    }

    public static class BatchedCallback<T2> extends Callback<T2> {
        static final int TYPE_ADD = 1;
        static final int TYPE_CHANGE = 3;
        static final int TYPE_MOVE = 4;
        static final int TYPE_NONE = 0;
        static final int TYPE_REMOVE = 2;
        int mLastEventCount = -1;
        int mLastEventPosition = -1;
        int mLastEventType = 0;
        private final Callback<T2> mWrappedCallback;

        public BatchedCallback(Callback<T2> wrappedCallback) {
            this.mWrappedCallback = wrappedCallback;
        }

        public int compare(T2 o1, T2 o2) {
            return this.mWrappedCallback.compare(o1, o2);
        }

        public void onInserted(int position, int count) {
            if (this.mLastEventType != 1 || position < this.mLastEventPosition || position > this.mLastEventPosition + this.mLastEventCount) {
                dispatchLastEvent();
                this.mLastEventPosition = position;
                this.mLastEventCount = count;
                this.mLastEventType = 1;
                return;
            }
            this.mLastEventCount += count;
            this.mLastEventPosition = Math.min(position, this.mLastEventPosition);
        }

        public void onRemoved(int position, int count) {
            if (this.mLastEventType == 2 && this.mLastEventPosition == position) {
                this.mLastEventCount += count;
                return;
            }
            dispatchLastEvent();
            this.mLastEventPosition = position;
            this.mLastEventCount = count;
            this.mLastEventType = 2;
        }

        public void onMoved(int fromPosition, int toPosition) {
            dispatchLastEvent();
            this.mWrappedCallback.onMoved(fromPosition, toPosition);
        }

        public void onChanged(int position, int count) {
            if (this.mLastEventType != 3 || position > this.mLastEventPosition + this.mLastEventCount || position + count < this.mLastEventPosition) {
                dispatchLastEvent();
                this.mLastEventPosition = position;
                this.mLastEventCount = count;
                this.mLastEventType = 3;
                return;
            }
            int previousEnd = this.mLastEventPosition + this.mLastEventCount;
            this.mLastEventPosition = Math.min(position, this.mLastEventPosition);
            this.mLastEventCount = Math.max(previousEnd, position + count) - this.mLastEventPosition;
        }

        public boolean areContentsTheSame(T2 oldItem, T2 newItem) {
            return this.mWrappedCallback.areContentsTheSame(oldItem, newItem);
        }

        public boolean areItemsTheSame(T2 item1, T2 item2) {
            return this.mWrappedCallback.areItemsTheSame(item1, item2);
        }

        public void dispatchLastEvent() {
            if (this.mLastEventType != 0) {
                switch (this.mLastEventType) {
                    case 1:
                        this.mWrappedCallback.onInserted(this.mLastEventPosition, this.mLastEventCount);
                        break;
                    case 2:
                        this.mWrappedCallback.onRemoved(this.mLastEventPosition, this.mLastEventCount);
                        break;
                    case 3:
                        this.mWrappedCallback.onChanged(this.mLastEventPosition, this.mLastEventCount);
                        break;
                }
                this.mLastEventType = 0;
            }
        }
    }

    public SortedList(Class<T> klass, Callback<T> callback) {
        this(klass, callback, 10);
    }

    public SortedList(Class<T> klass, Callback<T> callback, int initialCapacity) {
        this.mTClass = klass;
        this.mData = (Object[]) Array.newInstance(klass, initialCapacity);
        this.mCallback = callback;
        this.mSize = 0;
    }

    public int size() {
        return this.mSize;
    }

    public int add(T item) {
        throwIfMerging();
        return add(item, true);
    }

    public void addAll(T[] items, boolean mayModifyInput) {
        throwIfMerging();
        if (items.length != 0) {
            if (mayModifyInput) {
                addAllInternal(items);
                return;
            }
            Object[] copy = (Object[]) ((Object[]) Array.newInstance(this.mTClass, items.length));
            System.arraycopy(items, 0, copy, 0, items.length);
            addAllInternal(copy);
        }
    }

    public void addAll(T... items) {
        addAll(items, false);
    }

    public void addAll(Collection<T> items) {
        addAll(items.toArray((Object[]) ((Object[]) Array.newInstance(this.mTClass, items.size()))), true);
    }

    private void addAllInternal(T[] newItems) {
        boolean forceBatchedUpdates;
        if (this.mCallback instanceof BatchedCallback) {
            forceBatchedUpdates = false;
        } else {
            forceBatchedUpdates = true;
        }
        if (forceBatchedUpdates) {
            beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        Arrays.sort(newItems, this.mCallback);
        int newSize = deduplicate(newItems);
        if (this.mSize == 0) {
            this.mData = newItems;
            this.mSize = newSize;
            this.mMergedSize = newSize;
            this.mCallback.onInserted(0, newSize);
        } else {
            merge(newItems, newSize);
        }
        this.mOldData = null;
        if (forceBatchedUpdates) {
            endBatchedUpdates();
        }
    }

    private int deduplicate(T[] items) {
        if (items.length == 0) {
            throw new IllegalArgumentException("Input array must be non-empty");
        }
        int rangeStart = 0;
        int rangeEnd = 1;
        for (int i = 1; i < items.length; i++) {
            T currentItem = items[i];
            int compare = this.mCallback.compare(items[rangeStart], currentItem);
            if (compare > 0) {
                throw new IllegalArgumentException("Input must be sorted in ascending order.");
            }
            if (compare == 0) {
                int sameItemPos = findSameItem(currentItem, items, rangeStart, rangeEnd);
                if (sameItemPos != -1) {
                    items[sameItemPos] = currentItem;
                } else {
                    if (rangeEnd != i) {
                        items[rangeEnd] = currentItem;
                    }
                    rangeEnd++;
                }
            } else {
                if (rangeEnd != i) {
                    items[rangeEnd] = currentItem;
                }
                rangeStart = rangeEnd;
                rangeEnd++;
            }
        }
        return rangeEnd;
    }

    private int findSameItem(T item, T[] items, int from, int to) {
        for (int pos = from; pos < to; pos++) {
            if (this.mCallback.areItemsTheSame(items[pos], item)) {
                return pos;
            }
        }
        return -1;
    }

    private void merge(T[] newData, int newDataSize) {
        this.mData = (Object[]) Array.newInstance(this.mTClass, (this.mSize + newDataSize) + 10);
        this.mMergedSize = 0;
        int newDataStart = 0;
        while (true) {
            if (this.mOldDataStart >= this.mOldDataSize && newDataStart >= newDataSize) {
                return;
            }
            int itemCount;
            if (this.mOldDataStart == this.mOldDataSize) {
                itemCount = newDataSize - newDataStart;
                System.arraycopy(newData, newDataStart, this.mData, this.mMergedSize, itemCount);
                this.mMergedSize += itemCount;
                this.mSize += itemCount;
                this.mCallback.onInserted(this.mMergedSize - itemCount, itemCount);
                return;
            } else if (newDataStart == newDataSize) {
                itemCount = this.mOldDataSize - this.mOldDataStart;
                System.arraycopy(this.mOldData, this.mOldDataStart, this.mData, this.mMergedSize, itemCount);
                this.mMergedSize += itemCount;
                return;
            } else {
                T oldItem = this.mOldData[this.mOldDataStart];
                T newItem = newData[newDataStart];
                int compare = this.mCallback.compare(oldItem, newItem);
                Object[] objArr;
                int i;
                if (compare > 0) {
                    objArr = this.mData;
                    i = this.mMergedSize;
                    this.mMergedSize = i + 1;
                    objArr[i] = newItem;
                    this.mSize++;
                    newDataStart++;
                    this.mCallback.onInserted(this.mMergedSize - 1, 1);
                } else if (compare == 0 && this.mCallback.areItemsTheSame(oldItem, newItem)) {
                    objArr = this.mData;
                    i = this.mMergedSize;
                    this.mMergedSize = i + 1;
                    objArr[i] = newItem;
                    newDataStart++;
                    this.mOldDataStart++;
                    if (!this.mCallback.areContentsTheSame(oldItem, newItem)) {
                        this.mCallback.onChanged(this.mMergedSize - 1, 1);
                    }
                } else {
                    objArr = this.mData;
                    i = this.mMergedSize;
                    this.mMergedSize = i + 1;
                    objArr[i] = oldItem;
                    this.mOldDataStart++;
                }
            }
        }
    }

    private void throwIfMerging() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Cannot call this method from within addAll");
        }
    }

    public void beginBatchedUpdates() {
        throwIfMerging();
        if (!(this.mCallback instanceof BatchedCallback)) {
            if (this.mBatchedCallback == null) {
                this.mBatchedCallback = new BatchedCallback(this.mCallback);
            }
            this.mCallback = this.mBatchedCallback;
        }
    }

    public void endBatchedUpdates() {
        throwIfMerging();
        if (this.mCallback instanceof BatchedCallback) {
            ((BatchedCallback) this.mCallback).dispatchLastEvent();
        }
        if (this.mCallback == this.mBatchedCallback) {
            this.mCallback = this.mBatchedCallback.mWrappedCallback;
        }
    }

    private int add(T item, boolean notify) {
        int index = findIndexOf(item, this.mData, 0, this.mSize, 1);
        if (index == -1) {
            index = 0;
        } else if (index < this.mSize) {
            T existing = this.mData[index];
            if (this.mCallback.areItemsTheSame(existing, item)) {
                if (this.mCallback.areContentsTheSame(existing, item)) {
                    this.mData[index] = item;
                    return index;
                }
                this.mData[index] = item;
                this.mCallback.onChanged(index, 1);
                return index;
            }
        }
        addToData(index, item);
        if (notify) {
            this.mCallback.onInserted(index, 1);
        }
        return index;
    }

    public boolean remove(T item) {
        throwIfMerging();
        return remove(item, true);
    }

    public T removeItemAt(int index) {
        throwIfMerging();
        T item = get(index);
        removeItemAtIndex(index, true);
        return item;
    }

    private boolean remove(T item, boolean notify) {
        int index = findIndexOf(item, this.mData, 0, this.mSize, 2);
        if (index == -1) {
            return false;
        }
        removeItemAtIndex(index, notify);
        return true;
    }

    private void removeItemAtIndex(int index, boolean notify) {
        System.arraycopy(this.mData, index + 1, this.mData, index, (this.mSize - index) - 1);
        this.mSize--;
        this.mData[this.mSize] = null;
        if (notify) {
            this.mCallback.onRemoved(index, 1);
        }
    }

    public void updateItemAt(int index, T item) {
        boolean contentsChanged;
        throwIfMerging();
        T existing = get(index);
        if (existing == item || !this.mCallback.areContentsTheSame(existing, item)) {
            contentsChanged = true;
        } else {
            contentsChanged = false;
        }
        if (existing == item || this.mCallback.compare(existing, item) != 0) {
            if (contentsChanged) {
                this.mCallback.onChanged(index, 1);
            }
            removeItemAtIndex(index, false);
            int newIndex = add(item, false);
            if (index != newIndex) {
                this.mCallback.onMoved(index, newIndex);
                return;
            }
            return;
        }
        this.mData[index] = item;
        if (contentsChanged) {
            this.mCallback.onChanged(index, 1);
        }
    }

    public void recalculatePositionOfItemAt(int index) {
        throwIfMerging();
        T item = get(index);
        removeItemAtIndex(index, false);
        int newIndex = add(item, false);
        if (index != newIndex) {
            this.mCallback.onMoved(index, newIndex);
        }
    }

    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= this.mSize || index < 0) {
            throw new IndexOutOfBoundsException("Asked to get item at " + index + " but size is " + this.mSize);
        } else if (this.mOldData == null || index < this.mMergedSize) {
            return this.mData[index];
        } else {
            return this.mOldData[(index - this.mMergedSize) + this.mOldDataStart];
        }
    }

    public int indexOf(T item) {
        if (this.mOldData != null) {
            int index = findIndexOf(item, this.mData, 0, this.mMergedSize, 4);
            if (index != -1) {
                return index;
            }
            index = findIndexOf(item, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
            return index != -1 ? (index - this.mOldDataStart) + this.mMergedSize : -1;
        } else {
            return findIndexOf(item, this.mData, 0, this.mSize, 4);
        }
    }

    private int findIndexOf(T item, T[] mData, int left, int right, int reason) {
        while (left < right) {
            int middle = (left + right) / 2;
            T myItem = mData[middle];
            int cmp = this.mCallback.compare(myItem, item);
            if (cmp < 0) {
                left = middle + 1;
            } else if (cmp != 0) {
                right = middle;
            } else if (this.mCallback.areItemsTheSame(myItem, item)) {
                return middle;
            } else {
                int exact = linearEqualitySearch(item, middle, left, right);
                if (reason != 1) {
                    return exact;
                }
                if (exact != -1) {
                    return exact;
                }
                return middle;
            }
        }
        if (reason != 1) {
            left = -1;
        }
        return left;
    }

    private int linearEqualitySearch(T item, int middle, int left, int right) {
        int next = middle - 1;
        while (next >= left) {
            T nextItem = this.mData[next];
            if (this.mCallback.compare(nextItem, item) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(nextItem, item)) {
                return next;
            } else {
                next--;
            }
        }
        next = middle + 1;
        while (next < right) {
            nextItem = this.mData[next];
            if (this.mCallback.compare(nextItem, item) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(nextItem, item)) {
                return next;
            } else {
                next++;
            }
        }
        return -1;
    }

    private void addToData(int index, T item) {
        if (index > this.mSize) {
            throw new IndexOutOfBoundsException("cannot add item to " + index + " because size is " + this.mSize);
        }
        if (this.mSize == this.mData.length) {
            Object[] newData = (Object[]) ((Object[]) Array.newInstance(this.mTClass, this.mData.length + 10));
            System.arraycopy(this.mData, 0, newData, 0, index);
            newData[index] = item;
            System.arraycopy(this.mData, index, newData, index + 1, this.mSize - index);
            this.mData = newData;
        } else {
            System.arraycopy(this.mData, index, this.mData, index + 1, this.mSize - index);
            this.mData[index] = item;
        }
        this.mSize++;
    }

    public void clear() {
        throwIfMerging();
        if (this.mSize != 0) {
            int prevSize = this.mSize;
            Arrays.fill(this.mData, 0, prevSize, null);
            this.mSize = 0;
            this.mCallback.onRemoved(0, prevSize);
        }
    }
}
