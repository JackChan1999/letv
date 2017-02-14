package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract class AbstractValueCache<T> implements ValueCache<T> {
    private final ValueCache<T> childCache;

    protected abstract void cacheValue(Context context, T t);

    protected abstract void doInvalidate(Context context);

    protected abstract T getCached(Context context);

    public AbstractValueCache() {
        this(null);
    }

    public AbstractValueCache(ValueCache<T> childCache) {
        this.childCache = childCache;
    }

    public final synchronized T get(Context context, ValueLoader<T> loader) throws Exception {
        T value;
        value = getCached(context);
        if (value == null) {
            value = this.childCache != null ? this.childCache.get(context, loader) : loader.load(context);
            cache(context, value);
        }
        return value;
    }

    public final synchronized void invalidate(Context context) {
        doInvalidate(context);
    }

    private void cache(Context context, T value) {
        if (value == null) {
            throw new NullPointerException();
        }
        cacheValue(context, value);
    }
}
