package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.LimitedMemoryCache;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UsingFreqLimitedMemoryCache extends LimitedMemoryCache {
    private final Map<Bitmap, Integer> usingCounts = Collections.synchronizedMap(new HashMap());

    public UsingFreqLimitedMemoryCache(int sizeLimit) {
        super(sizeLimit);
    }

    public boolean put(String key, Bitmap value) {
        if (!super.put(key, value)) {
            return false;
        }
        this.usingCounts.put(value, Integer.valueOf(0));
        return true;
    }

    public Bitmap get(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            Integer usageCount = (Integer) this.usingCounts.get(value);
            if (usageCount != null) {
                this.usingCounts.put(value, Integer.valueOf(usageCount.intValue() + 1));
            }
        }
        return value;
    }

    public Bitmap remove(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            this.usingCounts.remove(value);
        }
        return super.remove(key);
    }

    public void clear() {
        this.usingCounts.clear();
        super.clear();
    }

    protected int getSize(Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    protected Bitmap removeNext() {
        Integer minUsageCount = null;
        Bitmap leastUsedValue = null;
        Set<Entry<Bitmap, Integer>> entries = this.usingCounts.entrySet();
        synchronized (this.usingCounts) {
            for (Entry<Bitmap, Integer> entry : entries) {
                if (leastUsedValue == null) {
                    leastUsedValue = (Bitmap) entry.getKey();
                    minUsageCount = (Integer) entry.getValue();
                } else {
                    Integer lastValueUsage = (Integer) entry.getValue();
                    if (lastValueUsage.intValue() < minUsageCount.intValue()) {
                        minUsageCount = lastValueUsage;
                        leastUsedValue = (Bitmap) entry.getKey();
                    }
                }
            }
        }
        this.usingCounts.remove(leastUsedValue);
        return leastUsedValue;
    }

    protected Reference<Bitmap> createReference(Bitmap value) {
        return new WeakReference(value);
    }
}
