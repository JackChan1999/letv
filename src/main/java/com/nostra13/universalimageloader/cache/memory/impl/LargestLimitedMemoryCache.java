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

public class LargestLimitedMemoryCache extends LimitedMemoryCache {
    private final Map<Bitmap, Integer> valueSizes = Collections.synchronizedMap(new HashMap());

    public LargestLimitedMemoryCache(int sizeLimit) {
        super(sizeLimit);
    }

    public boolean put(String key, Bitmap value) {
        if (!super.put(key, value)) {
            return false;
        }
        this.valueSizes.put(value, Integer.valueOf(getSize(value)));
        return true;
    }

    public Bitmap remove(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            this.valueSizes.remove(value);
        }
        return super.remove(key);
    }

    public void clear() {
        this.valueSizes.clear();
        super.clear();
    }

    protected int getSize(Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    protected Bitmap removeNext() {
        Integer maxSize = null;
        Bitmap largestValue = null;
        Set<Entry<Bitmap, Integer>> entries = this.valueSizes.entrySet();
        synchronized (this.valueSizes) {
            for (Entry<Bitmap, Integer> entry : entries) {
                if (largestValue == null) {
                    largestValue = (Bitmap) entry.getKey();
                    maxSize = (Integer) entry.getValue();
                } else {
                    Integer size = (Integer) entry.getValue();
                    if (size.intValue() > maxSize.intValue()) {
                        maxSize = size;
                        largestValue = (Bitmap) entry.getKey();
                    }
                }
            }
        }
        this.valueSizes.remove(largestValue);
        return largestValue;
    }

    protected Reference<Bitmap> createReference(Bitmap value) {
        return new WeakReference(value);
    }
}
