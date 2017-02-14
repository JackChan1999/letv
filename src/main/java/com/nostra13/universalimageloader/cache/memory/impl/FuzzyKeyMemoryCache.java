package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import java.util.Collection;
import java.util.Comparator;

public class FuzzyKeyMemoryCache implements MemoryCache {
    private final MemoryCache cache;
    private final Comparator<String> keyComparator;

    public FuzzyKeyMemoryCache(MemoryCache cache, Comparator<String> keyComparator) {
        this.cache = cache;
        this.keyComparator = keyComparator;
    }

    public boolean put(String key, Bitmap value) {
        synchronized (this.cache) {
            String keyToRemove = null;
            for (String cacheKey : this.cache.keys()) {
                if (this.keyComparator.compare(key, cacheKey) == 0) {
                    keyToRemove = cacheKey;
                    break;
                }
            }
            if (keyToRemove != null) {
                this.cache.remove(keyToRemove);
            }
        }
        return this.cache.put(key, value);
    }

    public Bitmap get(String key) {
        return (Bitmap) this.cache.get(key);
    }

    public Bitmap remove(String key) {
        return (Bitmap) this.cache.remove(key);
    }

    public void clear() {
        this.cache.clear();
    }

    public Collection<String> keys() {
        return this.cache.keys();
    }
}
