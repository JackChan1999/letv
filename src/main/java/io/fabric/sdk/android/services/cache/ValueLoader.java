package io.fabric.sdk.android.services.cache;

import android.content.Context;

public interface ValueLoader<T> {
    T load(Context context) throws Exception;
}
