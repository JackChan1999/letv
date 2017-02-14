package com.letv.core.utils;

import java.util.Observable;

public class LetvBaseObservable extends Observable {
    public void notifyObservers(Object data) {
        setChanged();
        super.notifyObservers(data);
    }
}
