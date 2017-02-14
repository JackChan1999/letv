package com.letv.plugin.pluginloader.apk.compat;

import android.app.Instrumentation;
import android.os.Handler;
import android.os.Looper;
import com.letv.plugin.pluginloader.apk.utils.MethodUtils;
import java.lang.reflect.InvocationTargetException;

public class ActivityThreadCompat {
    private static Object mActivityThread;
    private static Class mClass = null;

    public static final synchronized Object currentActivityThread() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object obj;
        synchronized (ActivityThreadCompat.class) {
            if (mActivityThread == null) {
                mActivityThread = MethodUtils.invokeStaticMethod(activityThreadClass(), "currentActivityThread", new Object[0]);
                if (mActivityThread == null) {
                    mActivityThread = currentActivityThread2();
                }
            }
            obj = mActivityThread;
        }
        return obj;
    }

    public static final Class activityThreadClass() throws ClassNotFoundException {
        if (mClass == null) {
            mClass = Class.forName("android.app.ActivityThread");
        }
        return mClass;
    }

    private static Object currentActivityThread2() {
        Handler handler = new Handler(Looper.getMainLooper());
        Object sLock = new Object();
        handler.post(new 1(sLock));
        if (mActivityThread == null && Looper.getMainLooper() != Looper.myLooper()) {
            synchronized (sLock) {
                try {
                    sLock.wait(300);
                } catch (InterruptedException e) {
                }
            }
        }
        return null;
    }

    public static Instrumentation getInstrumentation() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        return (Instrumentation) MethodUtils.invokeMethod(currentActivityThread(), "getInstrumentation", new Object[0]);
    }
}
