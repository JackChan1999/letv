package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;

public interface Dependency<T> {
    void addDependency(T t);

    boolean areDependenciesMet();

    Collection<T> getDependencies();
}
