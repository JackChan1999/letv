package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PriorityFutureTask<V> extends FutureTask<V> implements Dependency<Task>, PriorityProvider, Task, DelegateProvider {
    final Object delegate;

    public PriorityFutureTask(Callable<V> callable) {
        super(callable);
        this.delegate = checkAndInitDelegate(callable);
    }

    public PriorityFutureTask(Runnable runnable, V result) {
        super(runnable, result);
        this.delegate = checkAndInitDelegate(runnable);
    }

    public int compareTo(Object another) {
        return ((PriorityProvider) getDelegate()).compareTo(another);
    }

    public void addDependency(Task task) {
        ((Dependency) ((PriorityProvider) getDelegate())).addDependency(task);
    }

    public Collection<Task> getDependencies() {
        return ((Dependency) ((PriorityProvider) getDelegate())).getDependencies();
    }

    public boolean areDependenciesMet() {
        return ((Dependency) ((PriorityProvider) getDelegate())).areDependenciesMet();
    }

    public Priority getPriority() {
        return ((PriorityProvider) getDelegate()).getPriority();
    }

    public void setFinished(boolean finished) {
        ((Task) ((PriorityProvider) getDelegate())).setFinished(finished);
    }

    public boolean isFinished() {
        return ((Task) ((PriorityProvider) getDelegate())).isFinished();
    }

    public void setError(Throwable throwable) {
        ((Task) ((PriorityProvider) getDelegate())).setError(throwable);
    }

    public Throwable getError() {
        return ((Task) ((PriorityProvider) getDelegate())).getError();
    }

    public <T extends Dependency<Task> & PriorityProvider & Task> T getDelegate() {
        return (Dependency) this.delegate;
    }

    protected <T extends Dependency<Task> & PriorityProvider & Task> T checkAndInitDelegate(Object object) {
        if (PriorityTask.isProperDelegate(object)) {
            return (Dependency) object;
        }
        return new PriorityTask();
    }
}
