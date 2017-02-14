package rx;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import rx.annotations.Experimental;
import rx.exceptions.Exceptions;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.operators.CompletableOnSubscribeConcat;
import rx.internal.operators.CompletableOnSubscribeConcatArray;
import rx.internal.operators.CompletableOnSubscribeConcatIterable;
import rx.internal.operators.CompletableOnSubscribeMerge;
import rx.internal.operators.CompletableOnSubscribeMergeArray;
import rx.internal.operators.CompletableOnSubscribeMergeDelayErrorArray;
import rx.internal.operators.CompletableOnSubscribeMergeDelayErrorIterable;
import rx.internal.operators.CompletableOnSubscribeMergeIterable;
import rx.internal.operators.CompletableOnSubscribeTimeout;
import rx.internal.util.UtilityFunctions;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;
import rx.subscriptions.MultipleAssignmentSubscription;

@Experimental
public class Completable {
    static final Completable COMPLETE = create(new 1());
    static final RxJavaErrorHandler ERROR_HANDLER = RxJavaPlugins.getInstance().getErrorHandler();
    static final Completable NEVER = create(new 2());
    private final CompletableOnSubscribe onSubscribe;

    public static Completable amb(Completable... sources) {
        requireNonNull(sources);
        if (sources.length == 0) {
            return complete();
        }
        if (sources.length == 1) {
            return sources[0];
        }
        return create(new 3(sources));
    }

    public static Completable amb(Iterable<? extends Completable> sources) {
        requireNonNull(sources);
        return create(new 4(sources));
    }

    public static Completable complete() {
        return COMPLETE;
    }

    public static Completable concat(Completable... sources) {
        requireNonNull(sources);
        if (sources.length == 0) {
            return complete();
        }
        if (sources.length == 1) {
            return sources[0];
        }
        return create(new CompletableOnSubscribeConcatArray(sources));
    }

    public static Completable concat(Iterable<? extends Completable> sources) {
        requireNonNull(sources);
        return create(new CompletableOnSubscribeConcatIterable(sources));
    }

    public static Completable concat(Observable<? extends Completable> sources) {
        return concat(sources, 2);
    }

    public static Completable concat(Observable<? extends Completable> sources, int prefetch) {
        requireNonNull(sources);
        if (prefetch >= 1) {
            return create(new CompletableOnSubscribeConcat(sources, prefetch));
        }
        throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
    }

    public static Completable create(CompletableOnSubscribe onSubscribe) {
        requireNonNull(onSubscribe);
        try {
            return new Completable(onSubscribe);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Throwable ex2) {
            ERROR_HANDLER.handleError(ex2);
            NullPointerException toNpe = toNpe(ex2);
        }
    }

    public static Completable defer(Func0<? extends Completable> completableFunc0) {
        requireNonNull(completableFunc0);
        return create(new 5(completableFunc0));
    }

    public static Completable error(Func0<? extends Throwable> errorFunc0) {
        requireNonNull(errorFunc0);
        return create(new 6(errorFunc0));
    }

    public static Completable error(Throwable error) {
        requireNonNull(error);
        return create(new 7(error));
    }

    public static Completable fromAction(Action0 action) {
        requireNonNull(action);
        return create(new 8(action));
    }

    public static Completable fromCallable(Callable<?> callable) {
        requireNonNull(callable);
        return create(new 9(callable));
    }

    public static Completable fromFuture(Future<?> future) {
        requireNonNull(future);
        return fromObservable(Observable.from((Future) future));
    }

    public static Completable fromObservable(Observable<?> flowable) {
        requireNonNull(flowable);
        return create(new 10(flowable));
    }

    public static Completable fromSingle(Single<?> single) {
        requireNonNull(single);
        return create(new 11(single));
    }

    public static Completable merge(Completable... sources) {
        requireNonNull(sources);
        if (sources.length == 0) {
            return complete();
        }
        if (sources.length == 1) {
            return sources[0];
        }
        return create(new CompletableOnSubscribeMergeArray(sources));
    }

    public static Completable merge(Iterable<? extends Completable> sources) {
        requireNonNull(sources);
        return create(new CompletableOnSubscribeMergeIterable(sources));
    }

    public static Completable merge(Observable<? extends Completable> sources) {
        return merge0(sources, Integer.MAX_VALUE, false);
    }

    public static Completable merge(Observable<? extends Completable> sources, int maxConcurrency) {
        return merge0(sources, maxConcurrency, false);
    }

    protected static Completable merge0(Observable<? extends Completable> sources, int maxConcurrency, boolean delayErrors) {
        requireNonNull(sources);
        if (maxConcurrency >= 1) {
            return create(new CompletableOnSubscribeMerge(sources, maxConcurrency, delayErrors));
        }
        throw new IllegalArgumentException("maxConcurrency > 0 required but it was " + maxConcurrency);
    }

    public static Completable mergeDelayError(Completable... sources) {
        requireNonNull(sources);
        return create(new CompletableOnSubscribeMergeDelayErrorArray(sources));
    }

    public static Completable mergeDelayError(Iterable<? extends Completable> sources) {
        requireNonNull(sources);
        return create(new CompletableOnSubscribeMergeDelayErrorIterable(sources));
    }

    public static Completable mergeDelayError(Observable<? extends Completable> sources) {
        return merge0(sources, Integer.MAX_VALUE, true);
    }

    public static Completable mergeDelayError(Observable<? extends Completable> sources, int maxConcurrency) {
        return merge0(sources, maxConcurrency, true);
    }

    public static Completable never() {
        return NEVER;
    }

    static <T> T requireNonNull(T o) {
        if (o != null) {
            return o;
        }
        throw new NullPointerException();
    }

    public static Completable timer(long delay, TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    public static Completable timer(long delay, TimeUnit unit, Scheduler scheduler) {
        requireNonNull(unit);
        requireNonNull(scheduler);
        return create(new 12(scheduler, delay, unit));
    }

    static NullPointerException toNpe(Throwable ex) {
        NullPointerException npe = new NullPointerException("Actually not, but can't pass out an exception otherwise...");
        npe.initCause(ex);
        return npe;
    }

    public static <R> Completable using(Func0<R> resourceFunc0, Func1<? super R, ? extends Completable> completableFunc1, Action1<? super R> disposer) {
        return using(resourceFunc0, completableFunc1, disposer, true);
    }

    public static <R> Completable using(Func0<R> resourceFunc0, Func1<? super R, ? extends Completable> completableFunc1, Action1<? super R> disposer, boolean eager) {
        requireNonNull(resourceFunc0);
        requireNonNull(completableFunc1);
        requireNonNull(disposer);
        return create(new 13(resourceFunc0, completableFunc1, disposer, eager));
    }

    protected Completable(CompletableOnSubscribe onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public final Completable ambWith(Completable other) {
        requireNonNull(other);
        return amb(this, other);
    }

    public final void await() {
        CountDownLatch cdl = new CountDownLatch(1);
        Throwable[] err = new Throwable[1];
        subscribe(new 14(this, cdl, err));
        if (cdl.getCount() != 0) {
            try {
                cdl.await();
                if (err[0] != null) {
                    Exceptions.propagate(err[0]);
                }
            } catch (InterruptedException ex) {
                throw Exceptions.propagate(ex);
            }
        } else if (err[0] != null) {
            Exceptions.propagate(err[0]);
        }
    }

    public final boolean await(long timeout, TimeUnit unit) {
        boolean z = true;
        requireNonNull(unit);
        CountDownLatch cdl = new CountDownLatch(1);
        Throwable[] err = new Throwable[1];
        subscribe(new 15(this, cdl, err));
        if (cdl.getCount() != 0) {
            try {
                z = cdl.await(timeout, unit);
                if (z && err[0] != null) {
                    Exceptions.propagate(err[0]);
                }
            } catch (InterruptedException ex) {
                throw Exceptions.propagate(ex);
            }
        } else if (err[0] != null) {
            Exceptions.propagate(err[0]);
        }
        return z;
    }

    public final Completable compose(CompletableTransformer transformer) {
        return (Completable) to(transformer);
    }

    public final <T> Observable<T> andThen(Observable<T> next) {
        requireNonNull(next);
        return next.delaySubscription(toObservable());
    }

    public final <T> Single<T> andThen(Single<T> next) {
        requireNonNull(next);
        return next.delaySubscription(toObservable());
    }

    public final Completable concatWith(Completable other) {
        requireNonNull(other);
        return concat(this, other);
    }

    public final Completable delay(long delay, TimeUnit unit) {
        return delay(delay, unit, Schedulers.computation(), false);
    }

    public final Completable delay(long delay, TimeUnit unit, Scheduler scheduler) {
        return delay(delay, unit, scheduler, false);
    }

    public final Completable delay(long delay, TimeUnit unit, Scheduler scheduler, boolean delayError) {
        requireNonNull(unit);
        requireNonNull(scheduler);
        return create(new 16(this, scheduler, delay, unit, delayError));
    }

    @Deprecated
    public final Completable doOnComplete(Action0 onComplete) {
        return doOnCompleted(onComplete);
    }

    public final Completable doOnCompleted(Action0 onCompleted) {
        return doOnLifecycle(Actions.empty(), Actions.empty(), onCompleted, Actions.empty(), Actions.empty());
    }

    public final Completable doOnUnsubscribe(Action0 onUnsubscribe) {
        return doOnLifecycle(Actions.empty(), Actions.empty(), Actions.empty(), Actions.empty(), onUnsubscribe);
    }

    public final Completable doOnError(Action1<? super Throwable> onError) {
        return doOnLifecycle(Actions.empty(), onError, Actions.empty(), Actions.empty(), Actions.empty());
    }

    protected final Completable doOnLifecycle(Action1<? super Subscription> onSubscribe, Action1<? super Throwable> onError, Action0 onComplete, Action0 onAfterComplete, Action0 onUnsubscribe) {
        requireNonNull(onSubscribe);
        requireNonNull(onError);
        requireNonNull(onComplete);
        requireNonNull(onAfterComplete);
        requireNonNull(onUnsubscribe);
        return create(new 17(this, onComplete, onAfterComplete, onError, onSubscribe, onUnsubscribe));
    }

    public final Completable doOnSubscribe(Action1<? super Subscription> onSubscribe) {
        return doOnLifecycle(onSubscribe, Actions.empty(), Actions.empty(), Actions.empty(), Actions.empty());
    }

    public final Completable doOnTerminate(Action0 onTerminate) {
        return doOnLifecycle(Actions.empty(), new 18(this, onTerminate), onTerminate, Actions.empty(), Actions.empty());
    }

    public final Completable endWith(Completable other) {
        return concatWith(other);
    }

    public final <T> Observable<T> endWith(Observable<T> next) {
        return next.startWith(toObservable());
    }

    public final Completable doAfterTerminate(Action0 onAfterComplete) {
        return doOnLifecycle(Actions.empty(), Actions.empty(), Actions.empty(), onAfterComplete, Actions.empty());
    }

    public final Throwable get() {
        CountDownLatch cdl = new CountDownLatch(1);
        Throwable[] err = new Throwable[1];
        subscribe(new 19(this, cdl, err));
        if (cdl.getCount() == 0) {
            return err[0];
        }
        try {
            cdl.await();
            return err[0];
        } catch (InterruptedException ex) {
            throw Exceptions.propagate(ex);
        }
    }

    public final Throwable get(long timeout, TimeUnit unit) {
        requireNonNull(unit);
        CountDownLatch cdl = new CountDownLatch(1);
        Throwable[] err = new Throwable[1];
        subscribe(new 20(this, cdl, err));
        if (cdl.getCount() == 0) {
            return err[0];
        }
        try {
            if (cdl.await(timeout, unit)) {
                return err[0];
            }
            Exceptions.propagate(new TimeoutException());
            return null;
        } catch (InterruptedException ex) {
            throw Exceptions.propagate(ex);
        }
    }

    public final Completable lift(CompletableOperator onLift) {
        requireNonNull(onLift);
        return create(new 21(this, onLift));
    }

    public final Completable mergeWith(Completable other) {
        requireNonNull(other);
        return merge(this, other);
    }

    public final Completable observeOn(Scheduler scheduler) {
        requireNonNull(scheduler);
        return create(new 22(this, scheduler));
    }

    public final Completable onErrorComplete() {
        return onErrorComplete(UtilityFunctions.alwaysTrue());
    }

    public final Completable onErrorComplete(Func1<? super Throwable, Boolean> predicate) {
        requireNonNull(predicate);
        return create(new 23(this, predicate));
    }

    public final Completable onErrorResumeNext(Func1<? super Throwable, ? extends Completable> errorMapper) {
        requireNonNull(errorMapper);
        return create(new 24(this, errorMapper));
    }

    public final Completable repeat() {
        return fromObservable(toObservable().repeat());
    }

    public final Completable repeat(long times) {
        return fromObservable(toObservable().repeat(times));
    }

    public final Completable repeatWhen(Func1<? super Observable<? extends Void>, ? extends Observable<?>> handler) {
        requireNonNull(handler);
        return fromObservable(toObservable().repeatWhen(handler));
    }

    public final Completable retry() {
        return fromObservable(toObservable().retry());
    }

    public final Completable retry(Func2<Integer, Throwable, Boolean> predicate) {
        return fromObservable(toObservable().retry((Func2) predicate));
    }

    public final Completable retry(long times) {
        return fromObservable(toObservable().retry(times));
    }

    public final Completable retryWhen(Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> handler) {
        return fromObservable(toObservable().retryWhen(handler));
    }

    public final Completable startWith(Completable other) {
        requireNonNull(other);
        return concat(other, this);
    }

    public final <T> Observable<T> startWith(Observable<T> other) {
        requireNonNull(other);
        return toObservable().startWith((Observable) other);
    }

    public final Subscription subscribe() {
        MultipleAssignmentSubscription mad = new MultipleAssignmentSubscription();
        subscribe(new 25(this, mad));
        return mad;
    }

    public final Subscription subscribe(Action0 onComplete) {
        requireNonNull(onComplete);
        MultipleAssignmentSubscription mad = new MultipleAssignmentSubscription();
        subscribe(new 26(this, onComplete, mad));
        return mad;
    }

    public final Subscription subscribe(Action1<? super Throwable> onError, Action0 onComplete) {
        requireNonNull(onError);
        requireNonNull(onComplete);
        MultipleAssignmentSubscription mad = new MultipleAssignmentSubscription();
        subscribe(new 27(this, onComplete, mad, onError));
        return mad;
    }

    private static void deliverUncaughtException(Throwable e) {
        Thread thread = Thread.currentThread();
        thread.getUncaughtExceptionHandler().uncaughtException(thread, e);
    }

    public final void subscribe(CompletableSubscriber s) {
        requireNonNull(s);
        try {
            this.onSubscribe.call(s);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Throwable ex2) {
            ERROR_HANDLER.handleError(ex2);
            Exceptions.throwIfFatal(ex2);
            NullPointerException toNpe = toNpe(ex2);
        }
    }

    public final <T> void subscribe(Subscriber<T> s) {
        requireNonNull(s);
        Subscriber<?> sw = s;
        if (sw == null) {
            try {
                throw new NullPointerException("The RxJavaPlugins.onSubscribe returned a null Subscriber");
            } catch (NullPointerException ex) {
                throw ex;
            } catch (Throwable ex2) {
                ERROR_HANDLER.handleError(ex2);
                NullPointerException toNpe = toNpe(ex2);
            }
        } else {
            subscribe(new 28(this, sw));
        }
    }

    public final Completable subscribeOn(Scheduler scheduler) {
        requireNonNull(scheduler);
        return create(new 29(this, scheduler));
    }

    public final Completable timeout(long timeout, TimeUnit unit) {
        return timeout0(timeout, unit, Schedulers.computation(), null);
    }

    public final Completable timeout(long timeout, TimeUnit unit, Completable other) {
        requireNonNull(other);
        return timeout0(timeout, unit, Schedulers.computation(), other);
    }

    public final Completable timeout(long timeout, TimeUnit unit, Scheduler scheduler) {
        return timeout0(timeout, unit, scheduler, null);
    }

    public final Completable timeout(long timeout, TimeUnit unit, Scheduler scheduler, Completable other) {
        requireNonNull(other);
        return timeout0(timeout, unit, scheduler, other);
    }

    public final Completable timeout0(long timeout, TimeUnit unit, Scheduler scheduler, Completable other) {
        requireNonNull(unit);
        requireNonNull(scheduler);
        return create(new CompletableOnSubscribeTimeout(this, timeout, unit, scheduler, other));
    }

    public final <U> U to(Func1<? super Completable, U> converter) {
        return converter.call(this);
    }

    public final <T> Observable<T> toObservable() {
        return Observable.create(new 30(this));
    }

    public final <T> Single<T> toSingle(Func0<? extends T> completionValueFunc0) {
        requireNonNull(completionValueFunc0);
        return Single.create(new 31(this, completionValueFunc0));
    }

    public final <T> Single<T> toSingleDefault(T completionValue) {
        requireNonNull(completionValue);
        return toSingle(new 32(this, completionValue));
    }

    public final Completable unsubscribeOn(Scheduler scheduler) {
        requireNonNull(scheduler);
        return create(new 33(this, scheduler));
    }
}
