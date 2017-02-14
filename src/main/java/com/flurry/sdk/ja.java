package com.flurry.sdk;

import android.content.Context;
import java.lang.ref.WeakReference;

public class ja extends hv {
    public WeakReference<Context> a;
    public iz b;
    public a c;
    public long d;

    public enum a {
        START,
        SESSION_ID_CREATED,
        RESUME,
        PAUSE,
        END
    }

    public ja() {
        super("com.flurry.android.sdk.FlurrySessionEvent");
    }
}
