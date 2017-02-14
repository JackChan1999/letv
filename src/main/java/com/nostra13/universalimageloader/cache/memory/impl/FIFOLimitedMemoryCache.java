package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.LimitedMemoryCache;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class FIFOLimitedMemoryCache extends LimitedMemoryCache {
    private final List<Bitmap> queue = Collections.synchronizedList(new LinkedList());

    public FIFOLimitedMemoryCache(int sizeLimit) {
        super(sizeLimit);
    }

    public boolean put(String key, Bitmap value) {
        if (!super.put(key, value)) {
            return false;
        }
        this.queue.add(value);
        return true;
    }

    public Bitmap remove(String key) {
        Bitmap value = super.get(key);
        if (value != null) {
            this.queue.remove(value);
        }
        return super.remove(key);
    }

    public void clear() {
        this.queue.clear();
        super.clear();
    }

    protected int getSize(Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    protected Bitmap removeNext() {
        return (Bitmap) this.queue.remove(0);
    }

    protected Reference<Bitmap> createReference(Bitmap value) {
        return new WeakReference(value);
    }
}
