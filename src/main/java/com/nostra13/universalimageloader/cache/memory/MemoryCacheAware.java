package com.nostra13.universalimageloader.cache.memory;

import java.util.Collection;

@Deprecated
public interface MemoryCacheAware<K, V> {
    void clear();

    V get(K k);

    Collection<K> keys();

    boolean put(K k, V v);

    V remove(K k);
}
