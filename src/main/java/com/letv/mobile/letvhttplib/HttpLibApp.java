package com.letv.mobile.letvhttplib;

import android.app.Application;
import android.content.Context;

public class HttpLibApp {
    private static Application mApplication;
    private static HttpLibApp mHttpLibApp;

    private HttpLibApp(Application application) {
        mApplication = application;
    }

    public static HttpLibApp initCreate(Application application) {
        if (mHttpLibApp == null) {
            synchronized (HttpLibApp.class) {
                mHttpLibApp = new HttpLibApp(application);
            }
        }
        return mHttpLibApp;
    }

    public static Application getInstance() {
        if (mApplication != null) {
            return mApplication;
        }
        throw new RuntimeException("HttpLibApp is not initCreate");
    }

    public static Context getContext() {
        if (mApplication != null) {
            return mApplication;
        }
        throw new RuntimeException("HttpLibApp is not initCreate");
    }
}
