package io.fabric.sdk.android.services.cache;

import android.content.Context;

public class MemoryValueCache<T> extends AbstractValueCache<T> {
    private T value;

    public MemoryValueCache() {
        this(null);
    }

    public MemoryValueCache(ValueCache<T> childCache) {
        super(childCache);
    }

    protected void doInvalidate(Context context) {
        this.value = null;
    }

    protected T getCached(Context context) {
        return this.value;
    }

    protected void cacheValue(Context context, T value) {
        this.value = value;
    }
}
