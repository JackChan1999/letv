package io.fabric.sdk.android.services.persistence;

public interface PersistenceStrategy<T> {
    void clear();

    T restore();

    void save(T t);
}
