package com.letv.mobile.http.utils;

import android.content.Context;

public final class ContextProvider {
    private static Context sContext = null;

    public static void initIfNotInited(Context context) {
        if (sContext == null) {
            init(context);
        }
    }

    public static void init(Context context) {
        if (context == null) {
            throw new NullPointerException("Can not use null initlialized application context");
        }
        sContext = context;
    }

    public static Context getApplicationContext() {
        if (sContext != null) {
            return sContext;
        }
        throw new NullPointerException("Global application uninitialized");
    }

    private ContextProvider() {
    }
}
