package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class LruMemoryCache implements MemoryCache {
    private final LinkedHashMap<String, Bitmap> map;
    private final int maxSize;
    private int size;

    public LruMemoryCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public final Bitmap get(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        Bitmap bitmap;
        synchronized (this) {
            bitmap = (Bitmap) this.map.get(key);
        }
        return bitmap;
    }

    public final boolean put(String key, Bitmap value) {
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.size += sizeOf(key, value);
            Bitmap previous = (Bitmap) this.map.put(key, value);
            if (previous != null) {
                this.size -= sizeOf(key, previous);
            }
        }
        trimToSize(this.maxSize);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r7) {
        /*
        r6 = this;
    L_0x0000:
        monitor-enter(r6);
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        if (r3 < 0) goto L_0x0011;
    L_0x0005:
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0032 }
        if (r3 == 0) goto L_0x0035;
    L_0x000d:
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        if (r3 == 0) goto L_0x0035;
    L_0x0011:
        r3 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0032 }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0032 }
        r4.<init>();	 Catch:{ all -> 0x0032 }
        r5 = r6.getClass();	 Catch:{ all -> 0x0032 }
        r5 = r5.getName();	 Catch:{ all -> 0x0032 }
        r4 = r4.append(r5);	 Catch:{ all -> 0x0032 }
        r5 = ".sizeOf() is reporting inconsistent results!";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0032 }
        r4 = r4.toString();	 Catch:{ all -> 0x0032 }
        r3.<init>(r4);	 Catch:{ all -> 0x0032 }
        throw r3;	 Catch:{ all -> 0x0032 }
    L_0x0032:
        r3 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
        throw r3;
    L_0x0035:
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        if (r3 <= r7) goto L_0x0041;
    L_0x0039:
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3 = r3.isEmpty();	 Catch:{ all -> 0x0032 }
        if (r3 == 0) goto L_0x0043;
    L_0x0041:
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
    L_0x0042:
        return;
    L_0x0043:
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3 = r3.entrySet();	 Catch:{ all -> 0x0032 }
        r3 = r3.iterator();	 Catch:{ all -> 0x0032 }
        r1 = r3.next();	 Catch:{ all -> 0x0032 }
        r1 = (java.util.Map.Entry) r1;	 Catch:{ all -> 0x0032 }
        if (r1 != 0) goto L_0x0057;
    L_0x0055:
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
        goto L_0x0042;
    L_0x0057:
        r0 = r1.getKey();	 Catch:{ all -> 0x0032 }
        r0 = (java.lang.String) r0;	 Catch:{ all -> 0x0032 }
        r2 = r1.getValue();	 Catch:{ all -> 0x0032 }
        r2 = (android.graphics.Bitmap) r2;	 Catch:{ all -> 0x0032 }
        r3 = r6.map;	 Catch:{ all -> 0x0032 }
        r3.remove(r0);	 Catch:{ all -> 0x0032 }
        r3 = r6.size;	 Catch:{ all -> 0x0032 }
        r4 = r6.sizeOf(r0, r2);	 Catch:{ all -> 0x0032 }
        r3 = r3 - r4;
        r6.size = r3;	 Catch:{ all -> 0x0032 }
        monitor-exit(r6);	 Catch:{ all -> 0x0032 }
        goto L_0x0000;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache.trimToSize(int):void");
    }

    public final Bitmap remove(String key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        Bitmap previous;
        synchronized (this) {
            previous = (Bitmap) this.map.remove(key);
            if (previous != null) {
                this.size -= sizeOf(key, previous);
            }
        }
        return previous;
    }

    public Collection<String> keys() {
        Collection hashSet;
        synchronized (this) {
            hashSet = new HashSet(this.map.keySet());
        }
        return hashSet;
    }

    public void clear() {
        trimToSize(-1);
    }

    private int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    public final synchronized String toString() {
        return String.format("LruCache[maxSize=%d]", new Object[]{Integer.valueOf(this.maxSize)});
    }
}
