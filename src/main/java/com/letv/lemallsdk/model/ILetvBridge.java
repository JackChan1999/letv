package com.letv.lemallsdk.model;

public abstract class ILetvBridge {
    public abstract void goldData(Object obj);

    public void shitData(Object failObj) {
    }

    public void onProgress() {
    }
}
