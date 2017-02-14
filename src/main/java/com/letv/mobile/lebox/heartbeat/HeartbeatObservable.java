package com.letv.mobile.lebox.heartbeat;

import java.util.Observable;

public class HeartbeatObservable extends Observable {
    @Deprecated
    public void notifyObservers() {
    }

    @Deprecated
    public void notifyObservers(Object data) {
    }

    public void notifyObserversHasChanged(int type) {
        super.setChanged();
        super.notifyObservers(Integer.valueOf(type));
    }
}
