package com.flurry.sdk;

import android.app.Activity;

public class hq extends hv {
    public Activity a;
    public a b;

    public enum a {
        kCreated,
        kDestroyed,
        kPaused,
        kResumed,
        kStarted,
        kStopped,
        kSaveState
    }

    public hq() {
        super("com.flurry.android.sdk.ActivityLifecycleEvent");
    }
}
