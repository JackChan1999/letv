package io.fabric.sdk.android.services.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityTask implements Dependency<Task>, PriorityProvider, Task {
    private final List<Task> dependencies = new ArrayList();
    private final AtomicBoolean hasRun = new AtomicBoolean(false);
    private final AtomicReference<Throwable> throwable = new AtomicReference(null);

    public synchronized Collection<Task> getDependencies() {
        return Collections.unmodifiableCollection(this.dependencies);
    }

    public synchronized void addDependency(Task task) {
        this.dependencies.add(task);
    }

    public boolean areDependenciesMet() {
        for (Task task : getDependencies()) {
            if (!task.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public synchronized void setFinished(boolean finished) {
        this.hasRun.set(finished);
    }

    public boolean isFinished() {
        return this.hasRun.get();
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void setError(Throwable throwable) {
        this.throwable.set(throwable);
    }

    public Throwable getError() {
        return (Throwable) this.throwable.get();
    }

    public int compareTo(Object other) {
        return Priority.compareTo(this, other);
    }

    public static boolean isProperDelegate(Object object) {
        try {
            Task task = (Task) object;
            PriorityProvider provider = (PriorityProvider) object;
            if (((Dependency) object) == null || task == null || provider == null) {
                return false;
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }
}
