package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LimitedAgeMemoryCache implements MemoryCache {
    private final MemoryCache cache;
    private final Map<String, Long> loadingDates = Collections.synchronizedMap(new HashMap());
    private final long maxAge;

    public LimitedAgeMemoryCache(MemoryCache cache, long maxAge) {
        this.cache = cache;
        this.maxAge = 1000 * maxAge;
    }

    public boolean put(String key, Bitmap value) {
        boolean putSuccesfully = this.cache.put(key, value);
        if (putSuccesfully) {
            this.loadingDates.put(key, Long.valueOf(System.currentTimeMillis()));
        }
        return putSuccesfully;
    }

    public Bitmap get(String key) {
        Long loadingDate = (Long) this.loadingDates.get(key);
        if (loadingDate != null && System.currentTimeMillis() - loadingDate.longValue() > this.maxAge) {
            this.cache.remove(key);
            this.loadingDates.remove(key);
        }
        return (Bitmap) this.cache.get(key);
    }

    public Bitmap remove(String key) {
        this.loadingDates.remove(key);
        return (Bitmap) this.cache.remove(key);
    }

    public Collection<String> keys() {
        return this.cache.keys();
    }

    public void clear() {
        this.cache.clear();
        this.loadingDates.clear();
    }
}
