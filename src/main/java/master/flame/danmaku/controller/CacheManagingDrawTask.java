package master.flame.danmaku.controller;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Pair;
import master.flame.danmaku.controller.IDrawTask.TaskListener;
import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.ICacheManager;
import master.flame.danmaku.danmaku.model.IDanmakuIterator;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.IDrawingCache;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.DanmakuContext.DanmakuConfigTag;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.DrawingCache;
import master.flame.danmaku.danmaku.model.android.DrawingCachePoolManager;
import master.flame.danmaku.danmaku.model.objectpool.Pool;
import master.flame.danmaku.danmaku.model.objectpool.Pools;
import master.flame.danmaku.danmaku.parser.DanmakuFactory;
import master.flame.danmaku.danmaku.renderer.IRenderer.RenderingState;
import master.flame.danmaku.danmaku.util.DanmakuUtils;
import tv.cjump.jni.NativeBitmapFactory;

public class CacheManagingDrawTask extends DrawTask {
    static final /* synthetic */ boolean $assertionsDisabled = (!CacheManagingDrawTask.class.desiredAssertionStatus());
    private static final int MAX_CACHE_SCREEN_SIZE = 3;
    private CacheManager mCacheManager;
    private DanmakuTimer mCacheTimer;
    private final Object mDrawingNotify = new Object();
    private int mMaxCacheSize = 2;

    public class CacheManager implements ICacheManager {
        public static final byte RESULT_FAILED = (byte) 1;
        public static final byte RESULT_FAILED_OVERSIZE = (byte) 2;
        public static final byte RESULT_SUCCESS = (byte) 0;
        private static final String TAG = "CacheManager";
        Pool<DrawingCache> mCachePool = Pools.finitePool(this.mCachePoolManager, 800);
        DrawingCachePoolManager mCachePoolManager = new DrawingCachePoolManager();
        Danmakus mCaches = new Danmakus();
        private boolean mEndFlag = false;
        private CacheHandler mHandler;
        private int mMaxSize;
        private int mRealSize = 0;
        private int mScreenSize = 3;
        public HandlerThread mThread;

        public class CacheHandler extends Handler {
            public static final int ADD_DANMAKKU = 2;
            public static final int BUILD_CACHES = 3;
            public static final int CLEAR_ALL_CACHES = 7;
            public static final int CLEAR_OUTSIDE_CACHES = 8;
            public static final int CLEAR_OUTSIDE_CACHES_AND_RESET = 9;
            public static final int CLEAR_TIMEOUT_CACHES = 4;
            public static final int DISPATCH_ACTIONS = 16;
            private static final int PREPARE = 1;
            public static final int QUIT = 6;
            public static final int REBUILD_CACHE = 17;
            public static final int SEEK = 5;
            private boolean mCancelFlag;
            private boolean mPause;
            private boolean mSeekedFlag;

            public CacheHandler(Looper looper) {
                super(looper);
            }

            public void requestCancelCaching() {
                this.mCancelFlag = true;
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        CacheManager.this.evictAllNotInScreen();
                        for (int i = 0; i < 300; i++) {
                            CacheManager.this.mCachePool.release(new DrawingCache());
                        }
                        break;
                    case 2:
                        addDanmakuAndBuildCache(msg.obj);
                        return;
                    case 3:
                        removeMessages(3);
                        boolean repositioned = !(CacheManagingDrawTask.this.mTaskListener == null || CacheManagingDrawTask.this.mReadyState) || this.mSeekedFlag;
                        prepareCaches(repositioned);
                        if (repositioned) {
                            this.mSeekedFlag = false;
                        }
                        if (CacheManagingDrawTask.this.mTaskListener != null && !CacheManagingDrawTask.this.mReadyState) {
                            CacheManagingDrawTask.this.mTaskListener.ready();
                            CacheManagingDrawTask.this.mReadyState = true;
                            return;
                        }
                        return;
                    case 4:
                        CacheManager.this.clearTimeOutCaches();
                        return;
                    case 5:
                        Long seekMills = msg.obj;
                        if (seekMills != null) {
                            CacheManagingDrawTask.this.mCacheTimer.update(seekMills.longValue());
                            this.mSeekedFlag = true;
                            CacheManager.this.evictAllNotInScreen();
                            resume();
                            return;
                        }
                        return;
                    case 6:
                        removeCallbacksAndMessages(null);
                        this.mPause = true;
                        CacheManager.this.evictAll();
                        CacheManager.this.clearCachePool();
                        getLooper().quit();
                        return;
                    case 7:
                        CacheManager.this.evictAll();
                        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond - CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                        this.mSeekedFlag = true;
                        return;
                    case 8:
                        CacheManager.this.evictAllNotInScreen(true);
                        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                        return;
                    case 9:
                        CacheManager.this.evictAllNotInScreen(true);
                        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                        CacheManagingDrawTask.this.requestClear();
                        return;
                    case 16:
                        break;
                    case 17:
                        Pair<BaseDanmaku, Boolean> pair = msg.obj;
                        if (pair != null) {
                            BaseDanmaku cacheitem = pair.first;
                            if (((Boolean) pair.second).booleanValue()) {
                                cacheitem.requestFlags |= 1;
                            }
                            cacheitem.requestFlags |= 2;
                            if (!((Boolean) pair.second).booleanValue() && cacheitem.hasDrawingCache() && !cacheitem.cache.hasReferences()) {
                                cacheitem.cache = DanmakuUtils.buildDanmakuDrawingCache(cacheitem, CacheManagingDrawTask.this.mDisp, (DrawingCache) cacheitem.cache);
                                CacheManager.this.push(cacheitem, 0, true);
                                return;
                            } else if (cacheitem.isLive) {
                                CacheManager.this.clearCache(cacheitem);
                                createCache(cacheitem);
                                return;
                            } else {
                                CacheManager.this.entryRemoved(true, cacheitem, null);
                                addDanmakuAndBuildCache(cacheitem);
                                return;
                            }
                        }
                        return;
                    default:
                        return;
                }
                long delayed = dispatchAction();
                if (delayed <= 0) {
                    delayed = CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION / 2;
                }
                sendEmptyMessageDelayed(16, delayed);
            }

            private long dispatchAction() {
                float level = CacheManager.this.getPoolPercent();
                BaseDanmaku firstCache = CacheManager.this.mCaches.first();
                long gapTime = firstCache != null ? firstCache.time - CacheManagingDrawTask.this.mTimer.currMillisecond : 0;
                long doubleScreenDuration = CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * 2;
                if (level < 0.6f && gapTime > CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                    CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                    removeMessages(3);
                    sendEmptyMessage(3);
                    return 0;
                } else if (level > 0.4f && gapTime < (-doubleScreenDuration)) {
                    removeMessages(4);
                    sendEmptyMessage(4);
                    return 0;
                } else if (level >= 0.9f) {
                    return 0;
                } else {
                    long deltaTime = CacheManagingDrawTask.this.mCacheTimer.currMillisecond - CacheManagingDrawTask.this.mTimer.currMillisecond;
                    if (deltaTime < 0) {
                        CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond);
                        sendEmptyMessage(8);
                        sendEmptyMessage(3);
                        return 0;
                    } else if (deltaTime > doubleScreenDuration) {
                        return 0;
                    } else {
                        removeMessages(3);
                        sendEmptyMessage(3);
                        return 0;
                    }
                }
            }

            private void releaseDanmakuCache(BaseDanmaku item, DrawingCache cache) {
                if (cache == null) {
                    cache = item.cache;
                }
                item.cache = null;
                if (cache != null) {
                    cache.destroy();
                    CacheManager.this.mCachePool.release(cache);
                }
            }

            private long prepareCaches(boolean repositioned) {
                long curr = CacheManagingDrawTask.this.mCacheTimer.currMillisecond;
                long end = curr + (CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * ((long) CacheManager.this.mScreenSize));
                if (end < CacheManagingDrawTask.this.mTimer.currMillisecond) {
                    return 0;
                }
                long startTime = SystemClock.uptimeMillis();
                IDanmakus danmakus = null;
                int tryCount = 0;
                boolean hasException = false;
                do {
                    BaseDanmaku first;
                    try {
                        danmakus = CacheManagingDrawTask.this.danmakuList.subnew(curr, end);
                    } catch (Exception e) {
                        hasException = true;
                        SystemClock.sleep(10);
                    }
                    tryCount++;
                    if (tryCount >= 3 || danmakus != null) {
                        if (danmakus != null) {
                            CacheManagingDrawTask.this.mCacheTimer.update(end);
                            return 0;
                        }
                        first = danmakus.first();
                        BaseDanmaku last = danmakus.last();
                        if (first != null || last == null) {
                            CacheManagingDrawTask.this.mCacheTimer.update(end);
                            return 0;
                        }
                        long sleepTime = Math.min(100, 30 + ((10 * (first.time - CacheManagingDrawTask.this.mTimer.currMillisecond)) / CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION));
                        if (repositioned) {
                            sleepTime = 0;
                        }
                        IDanmakuIterator itr = danmakus.iterator();
                        BaseDanmaku item = null;
                        int orderInScreen = 0;
                        int currScreenIndex = 0;
                        int sizeInScreen = danmakus.size();
                        while (!this.mPause && !this.mCancelFlag && itr.hasNext()) {
                            item = itr.next();
                            if (last.time >= CacheManagingDrawTask.this.mTimer.currMillisecond) {
                                if (!item.hasDrawingCache() && (repositioned || (!item.isTimeOut() && item.isOutside()))) {
                                    if (!item.hasPassedFilter()) {
                                        CacheManagingDrawTask.this.mContext.mDanmakuFilters.filter(item, orderInScreen, sizeInScreen, null, true, CacheManagingDrawTask.this.mContext);
                                    }
                                    if (!((item.priority == (byte) 0 && item.isFiltered()) || (item.isDanmakuTypeFiltered && item.isFiltered()))) {
                                        int screenIndex = (int) ((item.time - curr) / CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                                        if (currScreenIndex == screenIndex) {
                                            orderInScreen++;
                                        } else {
                                            orderInScreen = 0;
                                            currScreenIndex = screenIndex;
                                        }
                                        if (!repositioned) {
                                            try {
                                                synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                                                    CacheManagingDrawTask.this.mDrawingNotify.wait(sleepTime);
                                                }
                                            } catch (InterruptedException e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                        if (buildCache(item, false) != (byte) 1) {
                                            if (!repositioned && SystemClock.uptimeMillis() - startTime >= DanmakuFactory.COMMON_DANMAKU_DURATION * ((long) CacheManager.this.mScreenSize)) {
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                        long consumingTime = SystemClock.uptimeMillis() - startTime;
                        if (item != null) {
                            CacheManagingDrawTask.this.mCacheTimer.update(item.time);
                            return consumingTime;
                        }
                        CacheManagingDrawTask.this.mCacheTimer.update(end);
                        return consumingTime;
                    }
                } while (hasException);
                if (danmakus != null) {
                    first = danmakus.first();
                    BaseDanmaku last2 = danmakus.last();
                    if (first != null) {
                    }
                    CacheManagingDrawTask.this.mCacheTimer.update(end);
                    return 0;
                }
                CacheManagingDrawTask.this.mCacheTimer.update(end);
                return 0;
            }

            public boolean createCache(BaseDanmaku item) {
                if (!item.isMeasured()) {
                    item.measure(CacheManagingDrawTask.this.mDisp, true);
                }
                DrawingCache cache = null;
                try {
                    cache = DanmakuUtils.buildDanmakuDrawingCache(item, CacheManagingDrawTask.this.mDisp, (DrawingCache) CacheManager.this.mCachePool.acquire());
                    item.cache = cache;
                    return true;
                } catch (OutOfMemoryError e) {
                    if (cache != null) {
                        CacheManager.this.mCachePool.release(cache);
                    }
                    item.cache = null;
                    return false;
                } catch (Exception e2) {
                    if (cache != null) {
                        CacheManager.this.mCachePool.release(cache);
                    }
                    item.cache = null;
                    return false;
                }
            }

            private byte buildCache(BaseDanmaku item, boolean forceInsert) {
                if (!item.isMeasured()) {
                    item.measure(CacheManagingDrawTask.this.mDisp, true);
                }
                DrawingCache cache = null;
                try {
                    BaseDanmaku danmaku = CacheManager.this.findReuseableCache(item, true, 20);
                    if (danmaku != null) {
                        cache = (DrawingCache) danmaku.cache;
                    }
                    if (cache != null) {
                        cache.increaseReference();
                        item.cache = cache;
                        CacheManagingDrawTask.this.mCacheManager.push(item, 0, forceInsert);
                        return (byte) 0;
                    }
                    danmaku = CacheManager.this.findReuseableCache(item, false, 50);
                    if (danmaku != null) {
                        cache = (DrawingCache) danmaku.cache;
                    }
                    if (cache != null) {
                        danmaku.cache = null;
                        item.cache = DanmakuUtils.buildDanmakuDrawingCache(item, CacheManagingDrawTask.this.mDisp, cache);
                        CacheManagingDrawTask.this.mCacheManager.push(item, 0, forceInsert);
                        return (byte) 0;
                    }
                    if (!forceInsert) {
                        if (CacheManager.this.mRealSize + DanmakuUtils.getCacheSize((int) item.paintWidth, (int) item.paintHeight) > CacheManager.this.mMaxSize) {
                            return (byte) 1;
                        }
                    }
                    cache = DanmakuUtils.buildDanmakuDrawingCache(item, CacheManagingDrawTask.this.mDisp, (DrawingCache) CacheManager.this.mCachePool.acquire());
                    item.cache = cache;
                    boolean pushed = CacheManagingDrawTask.this.mCacheManager.push(item, CacheManager.this.sizeOf(item), forceInsert);
                    if (!pushed) {
                        releaseDanmakuCache(item, cache);
                    }
                    return pushed ? (byte) 0 : (byte) 1;
                } catch (OutOfMemoryError e) {
                    releaseDanmakuCache(item, null);
                    return (byte) 1;
                } catch (Exception e2) {
                    releaseDanmakuCache(item, null);
                    return (byte) 1;
                }
            }

            private final void addDanmakuAndBuildCache(BaseDanmaku danmaku) {
                if (!danmaku.isTimeOut() && danmaku.time <= CacheManagingDrawTask.this.mCacheTimer.currMillisecond + CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION) {
                    if (danmaku.priority != (byte) 0 || !danmaku.isFiltered()) {
                        if (!danmaku.isDanmakuTypeFiltered || !danmaku.isFiltered()) {
                            if (!danmaku.hasDrawingCache()) {
                                buildCache(danmaku, true);
                            }
                            if (danmaku.isLive) {
                                CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond + (CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION * ((long) CacheManager.this.mScreenSize)));
                            }
                        }
                    }
                }
            }

            public void begin() {
                sendEmptyMessage(1);
                sendEmptyMessageDelayed(4, CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
            }

            public void pause() {
                this.mPause = true;
                removeCallbacksAndMessages(null);
                sendEmptyMessage(6);
            }

            public void resume() {
                this.mCancelFlag = false;
                this.mPause = false;
                removeMessages(16);
                sendEmptyMessage(16);
                sendEmptyMessageDelayed(4, CacheManagingDrawTask.this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
            }

            public boolean isPause() {
                return this.mPause;
            }

            public void requestBuildCacheAndDraw(long correctionTime) {
                removeMessages(3);
                this.mSeekedFlag = true;
                this.mCancelFlag = false;
                CacheManagingDrawTask.this.mCacheTimer.update(CacheManagingDrawTask.this.mTimer.currMillisecond + correctionTime);
                sendEmptyMessage(3);
            }
        }

        public CacheManager(int maxSize, int screenSize) {
            this.mMaxSize = maxSize;
            this.mScreenSize = screenSize;
        }

        public void seek(long mills) {
            if (this.mHandler != null) {
                this.mHandler.requestCancelCaching();
                this.mHandler.removeMessages(3);
                this.mHandler.obtainMessage(5, Long.valueOf(mills)).sendToTarget();
            }
        }

        public void addDanmaku(BaseDanmaku danmaku) {
            if (this.mHandler == null) {
                return;
            }
            if (!danmaku.isLive) {
                this.mHandler.obtainMessage(2, danmaku).sendToTarget();
            } else if (!danmaku.isTimeOut()) {
                this.mHandler.createCache(danmaku);
            }
        }

        public void invalidateDanmaku(BaseDanmaku danmaku, boolean remeasure) {
            if (this.mHandler != null) {
                this.mHandler.requestCancelCaching();
                this.mHandler.obtainMessage(17, new Pair(danmaku, Boolean.valueOf(remeasure))).sendToTarget();
            }
        }

        public void begin() {
            if (this.mThread == null) {
                this.mThread = new HandlerThread("DFM Cache-Building Thread");
                this.mThread.start();
            }
            if (this.mHandler == null) {
                this.mHandler = new CacheHandler(this.mThread.getLooper());
            }
            this.mHandler.begin();
        }

        public void end() {
            this.mEndFlag = true;
            synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                CacheManagingDrawTask.this.mDrawingNotify.notifyAll();
            }
            if (this.mHandler != null) {
                this.mHandler.pause();
                this.mHandler = null;
            }
            if (this.mThread != null) {
                try {
                    this.mThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.mThread.quit();
                this.mThread = null;
            }
        }

        public void resume() {
            if (this.mHandler != null) {
                this.mHandler.resume();
            } else {
                begin();
            }
        }

        public float getPoolPercent() {
            if (this.mMaxSize == 0) {
                return 0.0f;
            }
            return ((float) this.mRealSize) / ((float) this.mMaxSize);
        }

        public boolean isPoolFull() {
            return this.mRealSize + 5120 >= this.mMaxSize;
        }

        private void evictAll() {
            if (this.mCaches != null) {
                IDanmakuIterator it = this.mCaches.iterator();
                while (it.hasNext()) {
                    entryRemoved(true, it.next(), null);
                }
                this.mCaches.clear();
            }
            this.mRealSize = 0;
        }

        private void evictAllNotInScreen() {
            evictAllNotInScreen(false);
        }

        private void evictAllNotInScreen(boolean removeAllReferences) {
            if (this.mCaches != null) {
                IDanmakuIterator it = this.mCaches.iterator();
                while (it.hasNext()) {
                    BaseDanmaku danmaku = it.next();
                    IDrawingCache<?> cache = danmaku.cache;
                    boolean hasReferences;
                    if (cache == null || !cache.hasReferences()) {
                        hasReferences = false;
                    } else {
                        hasReferences = true;
                    }
                    if (removeAllReferences && hasReferences) {
                        if (cache.get() != null) {
                            this.mRealSize -= cache.size();
                            cache.destroy();
                        }
                        entryRemoved(true, danmaku, null);
                        it.remove();
                    } else if (!danmaku.hasDrawingCache() || danmaku.isOutside()) {
                        entryRemoved(true, danmaku, null);
                        it.remove();
                    }
                }
            }
            this.mRealSize = 0;
        }

        protected void entryRemoved(boolean evicted, BaseDanmaku oldValue, BaseDanmaku newValue) {
            if (oldValue.cache != null) {
                IDrawingCache<?> cache = oldValue.cache;
                long releasedSize = clearCache(oldValue);
                if (oldValue.isTimeOut()) {
                    CacheManagingDrawTask.this.mContext.getDisplayer().getCacheStuffer().releaseResource(oldValue);
                }
                if (releasedSize > 0) {
                    this.mRealSize = (int) (((long) this.mRealSize) - releasedSize);
                    this.mCachePool.release((DrawingCache) cache);
                }
            }
        }

        private long clearCache(BaseDanmaku oldValue) {
            if (oldValue.cache.hasReferences()) {
                oldValue.cache.decreaseReference();
                oldValue.cache = null;
                return 0;
            }
            long size = (long) sizeOf(oldValue);
            oldValue.cache.destroy();
            oldValue.cache = null;
            return size;
        }

        protected int sizeOf(BaseDanmaku value) {
            if (value.cache == null || value.cache.hasReferences()) {
                return 0;
            }
            return value.cache.size();
        }

        private void clearCachePool() {
            while (true) {
                DrawingCache item = (DrawingCache) this.mCachePool.acquire();
                if (item != null) {
                    item.destroy();
                } else {
                    return;
                }
            }
        }

        private boolean push(BaseDanmaku item, int itemSize, boolean forcePush) {
            int size = itemSize;
            while (this.mRealSize + size > this.mMaxSize && this.mCaches.size() > 0) {
                BaseDanmaku oldValue = this.mCaches.first();
                if (oldValue.isTimeOut()) {
                    entryRemoved(false, oldValue, item);
                    this.mCaches.removeItem(oldValue);
                } else if (!forcePush) {
                    return false;
                }
            }
            this.mCaches.addItem(item);
            this.mRealSize += size;
            return true;
        }

        private void clearTimeOutCaches() {
            clearTimeOutCaches(CacheManagingDrawTask.this.mTimer.currMillisecond);
        }

        private void clearTimeOutCaches(long time) {
            IDanmakuIterator it = this.mCaches.iterator();
            while (it.hasNext() && !this.mEndFlag) {
                BaseDanmaku val = it.next();
                if (val.isTimeOut()) {
                    synchronized (CacheManagingDrawTask.this.mDrawingNotify) {
                        try {
                            CacheManagingDrawTask.this.mDrawingNotify.wait(30);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    entryRemoved(false, val, null);
                    it.remove();
                } else {
                    return;
                }
            }
        }

        private BaseDanmaku findReuseableCache(BaseDanmaku refDanmaku, boolean strictMode, int maximumTimes) {
            return null;
        }

        public long getFirstCacheTime() {
            if (this.mCaches == null || this.mCaches.size() <= 0) {
                return 0;
            }
            BaseDanmaku firstItem = this.mCaches.first();
            if (firstItem == null) {
                return 0;
            }
            return firstItem.time;
        }

        public void requestBuild(long correctionTime) {
            if (this.mHandler != null) {
                this.mHandler.requestBuildCacheAndDraw(correctionTime);
            }
        }

        public void requestClearAll() {
            if (this.mHandler != null) {
                this.mHandler.removeMessages(3);
                this.mHandler.requestCancelCaching();
                this.mHandler.removeMessages(7);
                this.mHandler.sendEmptyMessage(7);
            }
        }

        public void requestClearUnused() {
            if (this.mHandler != null) {
                this.mHandler.removeMessages(9);
                this.mHandler.sendEmptyMessage(9);
            }
        }

        public void requestClearTimeout() {
            if (this.mHandler != null) {
                this.mHandler.removeMessages(4);
                this.mHandler.sendEmptyMessage(4);
            }
        }

        public void post(Runnable runnable) {
            if (this.mHandler != null) {
                this.mHandler.post(runnable);
            }
        }
    }

    public CacheManagingDrawTask(DanmakuTimer timer, DanmakuContext config, TaskListener taskListener, int maxCacheSize) {
        super(timer, config, taskListener);
        NativeBitmapFactory.loadLibs();
        this.mMaxCacheSize = maxCacheSize;
        if (NativeBitmapFactory.isInNativeAlloc()) {
            this.mMaxCacheSize = maxCacheSize * 2;
        }
        this.mCacheManager = new CacheManager(maxCacheSize, 3);
        this.mRenderer.setCacheManager(this.mCacheManager);
    }

    protected void initTimer(DanmakuTimer timer) {
        this.mTimer = timer;
        this.mCacheTimer = new DanmakuTimer();
        this.mCacheTimer.update(timer.currMillisecond);
    }

    public void addDanmaku(BaseDanmaku danmaku) {
        super.addDanmaku(danmaku);
        if (this.mCacheManager != null) {
            this.mCacheManager.addDanmaku(danmaku);
        }
    }

    public void invalidateDanmaku(BaseDanmaku item, boolean remeasure) {
        if (this.mCacheManager == null) {
            super.invalidateDanmaku(item, remeasure);
        } else {
            this.mCacheManager.invalidateDanmaku(item, remeasure);
        }
    }

    protected void onDanmakuRemoved(BaseDanmaku danmaku) {
        super.onDanmakuRemoved(danmaku);
        if (danmaku.hasDrawingCache()) {
            if (danmaku.cache.hasReferences()) {
                danmaku.cache.decreaseReference();
            } else {
                danmaku.cache.destroy();
            }
            danmaku.cache = null;
        }
    }

    public RenderingState draw(AbsDisplayer displayer) {
        RenderingState result = super.draw(displayer);
        synchronized (this.mDrawingNotify) {
            this.mDrawingNotify.notify();
        }
        if (!(result == null || this.mCacheManager == null || result.incrementCount >= -20)) {
            this.mCacheManager.requestClearTimeout();
            this.mCacheManager.requestBuild(-this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
        }
        return result;
    }

    public void reset() {
        if (this.mRenderer != null) {
            this.mRenderer.clear();
        }
    }

    public void seek(long mills) {
        super.seek(mills);
        this.mCacheManager.seek(mills);
    }

    public void start() {
        super.start();
        NativeBitmapFactory.loadLibs();
        if (this.mCacheManager == null) {
            this.mCacheManager = new CacheManager(this.mMaxCacheSize, 3);
            this.mCacheManager.begin();
            this.mRenderer.setCacheManager(this.mCacheManager);
            return;
        }
        this.mCacheManager.resume();
    }

    public void quit() {
        super.quit();
        reset();
        this.mRenderer.setCacheManager(null);
        if (this.mCacheManager != null) {
            this.mCacheManager.end();
            this.mCacheManager = null;
        }
        NativeBitmapFactory.releaseLibs();
    }

    public void prepare() {
        if ($assertionsDisabled || this.mParser != null) {
            loadDanmakus(this.mParser);
            this.mCacheManager.begin();
            return;
        }
        throw new AssertionError();
    }

    public boolean onDanmakuConfigChanged(DanmakuContext config, DanmakuConfigTag tag, Object... values) {
        if (!super.handleOnDanmakuConfigChanged(config, tag, values)) {
            if (DanmakuConfigTag.SCROLL_SPEED_FACTOR.equals(tag)) {
                this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
                requestClear();
            } else if (tag.isVisibilityRelatedTag()) {
                if (values != null && values.length > 0 && values[0] != null && ((!(values[0] instanceof Boolean) || ((Boolean) values[0]).booleanValue()) && this.mCacheManager != null)) {
                    this.mCacheManager.requestBuild(0);
                }
                requestClear();
            } else if (DanmakuConfigTag.TRANSPARENCY.equals(tag) || DanmakuConfigTag.SCALE_TEXTSIZE.equals(tag) || DanmakuConfigTag.DANMAKU_STYLE.equals(tag)) {
                if (DanmakuConfigTag.SCALE_TEXTSIZE.equals(tag)) {
                    this.mDisp.resetSlopPixel(this.mContext.scaleTextSize);
                }
                if (this.mCacheManager != null) {
                    this.mCacheManager.requestClearAll();
                    this.mCacheManager.requestBuild(-this.mContext.mDanmakuFactory.MAX_DANMAKU_DURATION);
                }
            } else if (this.mCacheManager != null) {
                this.mCacheManager.requestClearUnused();
                this.mCacheManager.requestBuild(0);
            }
        }
        if (!(this.mTaskListener == null || this.mCacheManager == null)) {
            this.mCacheManager.post(new Runnable() {
                public void run() {
                    CacheManagingDrawTask.this.mTaskListener.onDanmakuConfigChanged();
                }
            });
        }
        return true;
    }
}
