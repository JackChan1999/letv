package com.letv.plugin.pluginloader.application;

import android.app.Application;
import android.content.Context;

public class JarApplication extends Application {
    private static Context instance;

    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }

    public static void setInstance(Context application) {
        instance = application;
    }
}
