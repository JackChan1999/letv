package com.letv.core.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
    public static final String TAG = "RxBus";
    private static RxBus instance = null;
    private final Subject<Object, Object> _bus = new SerializedSubject(PublishSubject.create());

    private RxBus() {
    }

    public static synchronized RxBus getInstance() {
        RxBus rxBus;
        synchronized (RxBus.class) {
            if (instance == null) {
                instance = new RxBus();
            }
            rxBus = instance;
        }
        return rxBus;
    }

    public void send(Object o) {
        LogInfo.log(TAG, "发送事件：" + o.getClass().getName());
        this._bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return this._bus;
    }

    public boolean hasObservers() {
        return this._bus.hasObservers();
    }
}
