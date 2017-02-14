package master.flame.danmaku.controller;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;

public class DanmakuFilters {
    public static final int FILTER_TYPE_DUPLICATE_MERGE = 128;
    public static final int FILTER_TYPE_ELAPSED_TIME = 4;
    public static final int FILTER_TYPE_MAXIMUM_LINES = 256;
    public static final int FILTER_TYPE_OVERLAPPING = 512;
    public static final int FILTER_TYPE_TEXTCOLOR = 8;
    public static final int FILTER_TYPE_TYPE = 1;
    public static final int FILTER_TYPE_USER_GUEST = 64;
    public static final int FILTER_TYPE_USER_HASH = 32;
    public static final int FILTER_TYPE_USER_ID = 16;
    public static final int FILYER_TYPE_QUANTITY = 2;
    public static final String TAG_DUPLICATE_FILTER = "1017_Filter";
    public static final String TAG_ELAPSED_TIME_FILTER = "1012_Filter";
    public static final String TAG_GUEST_FILTER = "1016_Filter";
    public static final String TAG_MAXIMUN_LINES_FILTER = "1018_Filter";
    public static final String TAG_OVERLAPPING_FILTER = "1019_Filter";
    public static final String TAG_QUANTITY_DANMAKU_FILTER = "1011_Filter";
    public static final String TAG_TEXT_COLOR_DANMAKU_FILTER = "1013_Filter";
    public static final String TAG_TYPE_DANMAKU_FILTER = "1010_Filter";
    public static final String TAG_USER_HASH_FILTER = "1015_Filter";
    public static final String TAG_USER_ID_FILTER = "1014_Filter";
    public final Exception filterException = new Exception("not suuport this filter tag");
    private final Map<String, IDanmakuFilter<?>> filters = Collections.synchronizedSortedMap(new TreeMap());
    private final Map<String, IDanmakuFilter<?>> filtersSecondary = Collections.synchronizedSortedMap(new TreeMap());
    IDanmakuFilter<?>[] mFilterArray = new IDanmakuFilter[0];
    IDanmakuFilter<?>[] mFilterArraySecondary = new IDanmakuFilter[0];

    public interface IDanmakuFilter<T> {
        void clear();

        boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext);

        void reset();

        void setData(T t);
    }

    public static abstract class BaseDanmakuFilter<T> implements IDanmakuFilter<T> {
        public void clear() {
        }
    }

    public static class DuplicateMergingFilter extends BaseDanmakuFilter<Void> {
        protected final IDanmakus blockedDanmakus = new Danmakus(4);
        protected final LinkedHashMap<String, BaseDanmaku> currentDanmakus = new LinkedHashMap();
        private final IDanmakus passedDanmakus = new Danmakus(4);

        private final void removeTimeoutDanmakus(IDanmakus danmakus, long limitTime) {
            IDanmakuIterator it = danmakus.iterator();
            long startTime = SystemClock.uptimeMillis();
            while (it.hasNext()) {
                try {
                    if (it.next().isTimeOut()) {
                        it.remove();
                        if (SystemClock.uptimeMillis() - startTime > limitTime) {
                            return;
                        }
                    }
                    return;
                } catch (Exception e) {
                    return;
                }
            }
        }

        private void removeTimeoutDanmakus(LinkedHashMap<String, BaseDanmaku> danmakus, int limitTime) {
            Iterator<Entry<String, BaseDanmaku>> it = danmakus.entrySet().iterator();
            long startTime = SystemClock.uptimeMillis();
            while (it.hasNext()) {
                try {
                    if (((BaseDanmaku) ((Entry) it.next()).getValue()).isTimeOut()) {
                        it.remove();
                        if (SystemClock.uptimeMillis() - startTime > ((long) limitTime)) {
                            return;
                        }
                    }
                    return;
                } catch (Exception e) {
                    return;
                }
            }
        }

        public synchronized boolean needFilter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask) {
            boolean z = true;
            synchronized (this) {
                removeTimeoutDanmakus(this.blockedDanmakus, 2);
                removeTimeoutDanmakus(this.passedDanmakus, 2);
                removeTimeoutDanmakus(this.currentDanmakus, 3);
                if (!this.blockedDanmakus.contains(danmaku) || danmaku.isOutside()) {
                    if (this.passedDanmakus.contains(danmaku)) {
                        z = false;
                    } else if (this.currentDanmakus.containsKey(danmaku.text)) {
                        this.currentDanmakus.put(String.valueOf(danmaku.text), danmaku);
                        this.blockedDanmakus.removeItem(danmaku);
                        this.blockedDanmakus.addItem(danmaku);
                    } else {
                        this.currentDanmakus.put(String.valueOf(danmaku.text), danmaku);
                        this.passedDanmakus.addItem(danmaku);
                        z = false;
                    }
                }
            }
            return z;
        }

        public boolean filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = needFilter(danmaku, index, totalsizeInScreen, timer, fromCachingTask);
            if (filtered) {
                danmaku.mFilterParam |= 128;
            }
            return filtered;
        }

        public void setData(Void data) {
        }

        public synchronized void reset() {
            this.passedDanmakus.clear();
            this.blockedDanmakus.clear();
            this.currentDanmakus.clear();
        }

        public void clear() {
            reset();
        }
    }

    public static class ElapsedTimeFilter extends BaseDanmakuFilter<Object> {
        long mMaxTime = 20;

        private synchronized boolean needFilter(BaseDanmaku danmaku, int orderInScreen, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask) {
            boolean z = false;
            synchronized (this) {
                if (timer != null) {
                    if (danmaku.isOutside()) {
                        if (SystemClock.uptimeMillis() - timer.currMillisecond >= this.mMaxTime) {
                            z = true;
                        }
                    }
                }
            }
            return z;
        }

        public boolean filter(BaseDanmaku danmaku, int orderInScreen, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = needFilter(danmaku, orderInScreen, totalsizeInScreen, timer, fromCachingTask);
            if (filtered) {
                danmaku.mFilterParam |= 4;
            }
            return filtered;
        }

        public void setData(Object data) {
            reset();
        }

        public synchronized void reset() {
        }

        public void clear() {
            reset();
        }
    }

    public static class GuestFilter extends BaseDanmakuFilter<Boolean> {
        private Boolean mBlock = Boolean.valueOf(false);

        public boolean filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = this.mBlock.booleanValue() && danmaku.isGuest;
            if (filtered) {
                danmaku.mFilterParam |= 64;
            }
            return filtered;
        }

        public void setData(Boolean data) {
            this.mBlock = data;
        }

        public void reset() {
            this.mBlock = Boolean.valueOf(false);
        }
    }

    public static class MaximumLinesFilter extends BaseDanmakuFilter<Map<Integer, Integer>> {
        private Map<Integer, Integer> mMaximumLinesPairs;

        public boolean filter(BaseDanmaku danmaku, int lines, int totalsizeInScreen, DanmakuTimer timer, boolean willHit, DanmakuContext config) {
            boolean filtered = false;
            if (this.mMaximumLinesPairs != null) {
                Integer maxLines = (Integer) this.mMaximumLinesPairs.get(Integer.valueOf(danmaku.getType()));
                filtered = maxLines != null && lines >= maxLines.intValue();
                if (filtered) {
                    danmaku.mFilterParam |= 256;
                }
            }
            return filtered;
        }

        public void setData(Map<Integer, Integer> data) {
            this.mMaximumLinesPairs = data;
        }

        public void reset() {
            this.mMaximumLinesPairs = null;
        }
    }

    public static class OverlappingFilter extends BaseDanmakuFilter<Map<Integer, Boolean>> {
        private Map<Integer, Boolean> mEnabledPairs;

        public boolean filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean willHit, DanmakuContext config) {
            boolean filtered = false;
            if (this.mEnabledPairs != null) {
                Boolean enabledValue = (Boolean) this.mEnabledPairs.get(Integer.valueOf(danmaku.getType()));
                filtered = enabledValue != null && enabledValue.booleanValue() && willHit;
                if (filtered) {
                    danmaku.mFilterParam |= 512;
                }
            }
            return filtered;
        }

        public void setData(Map<Integer, Boolean> data) {
            this.mEnabledPairs = data;
        }

        public void reset() {
            this.mEnabledPairs = null;
        }
    }

    public static class QuantityDanmakuFilter extends BaseDanmakuFilter<Integer> {
        protected BaseDanmaku mLastSkipped = null;
        protected int mMaximumSize = -1;

        private boolean needFilter(BaseDanmaku danmaku, int orderInScreen, int totalSizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext context) {
            Log.v("XX", "QuantityDanmakuFilter needFilter mMaximumSize : " + this.mMaximumSize + " totalSizeInScreen : " + totalSizeInScreen + " orderInScreen : " + orderInScreen + " danmaku.isTimeOut() " + danmaku.isTimeOut() + " mMaximumSize : " + this.mMaximumSize + " isShown :" + danmaku.isShown());
            if (this.mMaximumSize == 0) {
                return true;
            }
            if (totalSizeInScreen < this.mMaximumSize || (this.mLastSkipped != null && danmaku.time - this.mLastSkipped.time > context.mDanmakuFactory.MAX_DANMAKU_DURATION / 4)) {
                Log.v("xx", " QuantityDanmakuFilter not guolv2222 >>>>>>>>");
                this.mLastSkipped = danmaku;
                return false;
            } else if (orderInScreen <= this.mMaximumSize || danmaku.isTimeOut()) {
                this.mLastSkipped = danmaku;
                Log.v("xx", " QuantityDanmakuFilter not guolv11111 >>>>>>>>");
                return false;
            } else {
                Log.v("xx", " QuantityDanmakuFilter guolv >>>>>>>>");
                return true;
            }
        }

        public synchronized boolean filter(BaseDanmaku danmaku, int orderInScreen, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered;
            filtered = needFilter(danmaku, orderInScreen, totalsizeInScreen, timer, fromCachingTask, config);
            if (filtered) {
                danmaku.mFilterParam |= 2;
            }
            return filtered;
        }

        public void setData(Integer data) {
            reset();
            if (data != null && data.intValue() != this.mMaximumSize) {
                this.mMaximumSize = data.intValue();
            }
        }

        public synchronized void reset() {
            this.mLastSkipped = null;
        }

        public void clear() {
            reset();
        }
    }

    public static class TextColorFilter extends BaseDanmakuFilter<List<Integer>> {
        public List<Integer> mWhiteList = new ArrayList();

        private void addToWhiteList(Integer color) {
            if (!this.mWhiteList.contains(color)) {
                this.mWhiteList.add(color);
            }
        }

        public boolean filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = (danmaku == null || this.mWhiteList.contains(Integer.valueOf(danmaku.textColor))) ? false : true;
            if (filtered) {
                danmaku.mFilterParam |= 8;
            }
            return filtered;
        }

        public void setData(List<Integer> data) {
            reset();
            if (data != null) {
                for (Integer i : data) {
                    addToWhiteList(i);
                }
            }
        }

        public void reset() {
            this.mWhiteList.clear();
        }
    }

    public static class TypeDanmakuFilter extends BaseDanmakuFilter<List<Integer>> {
        final List<Integer> mFilterTypes = Collections.synchronizedList(new ArrayList());

        public void enableType(Integer type) {
            if (!this.mFilterTypes.contains(type)) {
                this.mFilterTypes.add(type);
            }
        }

        public void disableType(Integer type) {
            if (this.mFilterTypes.contains(type)) {
                this.mFilterTypes.remove(type);
            }
        }

        public boolean filter(BaseDanmaku danmaku, int orderInScreen, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = danmaku != null && this.mFilterTypes.contains(Integer.valueOf(danmaku.getType()));
            if (filtered) {
                danmaku.mFilterParam |= 1;
                danmaku.isDanmakuTypeFiltered = true;
            }
            return filtered;
        }

        public void setData(List<Integer> data) {
            reset();
            if (data != null) {
                for (Integer i : data) {
                    enableType(i);
                }
            }
        }

        public void reset() {
            this.mFilterTypes.clear();
        }
    }

    public static abstract class UserFilter<T> extends BaseDanmakuFilter<List<T>> {
        public List<T> mBlackList = new ArrayList();

        public abstract boolean filter(BaseDanmaku baseDanmaku, int i, int i2, DanmakuTimer danmakuTimer, boolean z, DanmakuContext danmakuContext);

        private void addToBlackList(T id) {
            if (!this.mBlackList.contains(id)) {
                this.mBlackList.add(id);
            }
        }

        public void setData(List<T> data) {
            reset();
            if (data != null) {
                for (T i : data) {
                    addToBlackList(i);
                }
            }
        }

        public void reset() {
            this.mBlackList.clear();
        }
    }

    public static class UserHashFilter extends UserFilter<String> {
        public boolean filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = danmaku != null && this.mBlackList.contains(danmaku.userHash);
            if (filtered) {
                danmaku.mFilterParam |= 32;
            }
            return filtered;
        }
    }

    public static class UserIdFilter extends UserFilter<Integer> {
        public boolean filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext config) {
            boolean filtered = danmaku != null && this.mBlackList.contains(Integer.valueOf(danmaku.userId));
            if (filtered) {
                danmaku.mFilterParam |= 16;
            }
            return filtered;
        }
    }

    public void filter(BaseDanmaku danmaku, int index, int totalsizeInScreen, DanmakuTimer timer, boolean fromCachingTask, DanmakuContext context) {
        for (IDanmakuFilter<?> f : this.mFilterArray) {
            if (f != null) {
                boolean filtered = f.filter(danmaku, index, totalsizeInScreen, timer, fromCachingTask, context);
                danmaku.filterResetFlag = context.mGlobalFlagValues.FILTER_RESET_FLAG;
                if (filtered) {
                    return;
                }
            }
        }
    }

    public boolean filterSecondary(BaseDanmaku danmaku, int lines, int totalsizeInScreen, DanmakuTimer timer, boolean willHit, DanmakuContext context) {
        for (IDanmakuFilter<?> f : this.mFilterArraySecondary) {
            if (f != null) {
                boolean filtered = f.filter(danmaku, lines, totalsizeInScreen, timer, willHit, context);
                danmaku.filterResetFlag = context.mGlobalFlagValues.FILTER_RESET_FLAG;
                if (filtered) {
                    return true;
                }
            }
        }
        return false;
    }

    public IDanmakuFilter<?> get(String tag) {
        return get(tag, true);
    }

    public IDanmakuFilter<?> get(String tag, boolean primary) {
        IDanmakuFilter<?> f = primary ? (IDanmakuFilter) this.filters.get(tag) : (IDanmakuFilter) this.filtersSecondary.get(tag);
        if (f == null) {
            return registerFilter(tag, primary);
        }
        return f;
    }

    public IDanmakuFilter<?> registerFilter(String tag) {
        return registerFilter(tag, true);
    }

    public IDanmakuFilter<?> registerFilter(String tag, boolean primary) {
        if (tag == null) {
            throwFilterException();
            return null;
        }
        IDanmakuFilter<?> filter = (IDanmakuFilter) this.filters.get(tag);
        if (filter == null) {
            if (TAG_TYPE_DANMAKU_FILTER.equals(tag)) {
                filter = new TypeDanmakuFilter();
            } else if (TAG_QUANTITY_DANMAKU_FILTER.equals(tag)) {
                filter = new QuantityDanmakuFilter();
            } else if (TAG_ELAPSED_TIME_FILTER.equals(tag)) {
                filter = new ElapsedTimeFilter();
            } else if (TAG_TEXT_COLOR_DANMAKU_FILTER.equals(tag)) {
                filter = new TextColorFilter();
            } else if (TAG_USER_ID_FILTER.equals(tag)) {
                filter = new UserIdFilter();
            } else if (TAG_USER_HASH_FILTER.equals(tag)) {
                filter = new UserHashFilter();
            } else if (TAG_GUEST_FILTER.equals(tag)) {
                filter = new GuestFilter();
            } else if (TAG_DUPLICATE_FILTER.equals(tag)) {
                filter = new DuplicateMergingFilter();
            } else if (TAG_MAXIMUN_LINES_FILTER.equals(tag)) {
                filter = new MaximumLinesFilter();
            } else if (TAG_OVERLAPPING_FILTER.equals(tag)) {
                filter = new OverlappingFilter();
            }
        }
        if (filter == null) {
            throwFilterException();
            return null;
        }
        filter.setData(null);
        if (primary) {
            this.filters.put(tag, filter);
            this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
            return filter;
        }
        this.filtersSecondary.put(tag, filter);
        this.mFilterArraySecondary = (IDanmakuFilter[]) this.filtersSecondary.values().toArray(this.mFilterArraySecondary);
        return filter;
    }

    public void unregisterFilter(String tag) {
        unregisterFilter(tag, true);
    }

    public void unregisterFilter(String tag, boolean primary) {
        IDanmakuFilter<?> f = primary ? (IDanmakuFilter) this.filters.remove(tag) : (IDanmakuFilter) this.filtersSecondary.remove(tag);
        if (f != null) {
            f.clear();
            if (primary) {
                this.mFilterArray = (IDanmakuFilter[]) this.filters.values().toArray(this.mFilterArray);
            } else {
                this.mFilterArraySecondary = (IDanmakuFilter[]) this.filtersSecondary.values().toArray(this.mFilterArraySecondary);
            }
        }
    }

    public void clear() {
        IDanmakuFilter<?> f;
        int i = 0;
        for (IDanmakuFilter<?> f2 : this.mFilterArray) {
            if (f2 != null) {
                f2.clear();
            }
        }
        IDanmakuFilter[] iDanmakuFilterArr = this.mFilterArraySecondary;
        int length = iDanmakuFilterArr.length;
        while (i < length) {
            f2 = iDanmakuFilterArr[i];
            if (f2 != null) {
                f2.clear();
            }
            i++;
        }
    }

    public void reset() {
        int i = 0;
        for (IDanmakuFilter<?> f : this.mFilterArray) {
            IDanmakuFilter<?> f2;
            if (f2 != null) {
                f2.reset();
            }
        }
        IDanmakuFilter[] iDanmakuFilterArr = this.mFilterArraySecondary;
        int length = iDanmakuFilterArr.length;
        while (i < length) {
            f2 = iDanmakuFilterArr[i];
            if (f2 != null) {
                f2.reset();
            }
            i++;
        }
    }

    public void release() {
        clear();
        this.filters.clear();
        this.mFilterArray = new IDanmakuFilter[0];
        this.filtersSecondary.clear();
        this.mFilterArraySecondary = new IDanmakuFilter[0];
    }

    private void throwFilterException() {
        try {
            throw this.filterException;
        } catch (Exception e) {
        }
    }
}
