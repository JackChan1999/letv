package io.fabric.sdk.android.services.persistence;

import android.annotation.SuppressLint;

public class PreferenceStoreStrategy<T> implements PersistenceStrategy<T> {
    private final String key;
    private final SerializationStrategy<T> serializer;
    private final PreferenceStore store;

    public PreferenceStoreStrategy(PreferenceStore store, SerializationStrategy<T> serializer, String preferenceKey) {
        this.store = store;
        this.serializer = serializer;
        this.key = preferenceKey;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void save(T object) {
        this.store.save(this.store.edit().putString(this.key, this.serializer.serialize(object)));
    }

    public T restore() {
        return this.serializer.deserialize(this.store.get().getString(this.key, null));
    }

    public void clear() {
        this.store.edit().remove(this.key).commit();
    }
}
