package io.fabric.sdk.android.services.persistence;

public interface SerializationStrategy<T> {
    T deserialize(String str);

    String serialize(T t);
}
