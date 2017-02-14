package io.fabric.sdk.android.services.concurrency;

public interface DelegateProvider {
    <T extends Dependency<Task> & PriorityProvider & Task> T getDelegate();
}
