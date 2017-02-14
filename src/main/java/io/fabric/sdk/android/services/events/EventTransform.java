package io.fabric.sdk.android.services.events;

import java.io.IOException;

public interface EventTransform<T> {
    byte[] toBytes(T t) throws IOException;
}
