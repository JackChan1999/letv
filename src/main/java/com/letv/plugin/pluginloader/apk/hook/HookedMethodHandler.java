package com.letv.plugin.pluginloader.apk.hook;

import android.content.Context;
import com.letv.plugin.pluginloader.apk.utils.Log;
import java.lang.reflect.Method;

public class HookedMethodHandler {
    private static final String TAG = HookedMethodHandler.class.getSimpleName();
    private Object mFakedResult = null;
    protected final Context mHostContext;
    private boolean mUseFakedResult = false;

    public HookedMethodHandler(Context hostContext) {
        this.mHostContext = hostContext;
    }

    public synchronized Object doHookInner(Object receiver, Method method, Object[] args) throws Throwable {
        Object invokeResult;
        long b = System.currentTimeMillis();
        try {
            this.mUseFakedResult = false;
            this.mFakedResult = null;
            invokeResult = null;
            if (!beforeInvoke(receiver, method, args)) {
                invokeResult = method.invoke(receiver, args);
            }
            afterInvoke(receiver, method, args, invokeResult);
            if (this.mUseFakedResult) {
                invokeResult = this.mFakedResult;
                if (System.currentTimeMillis() - b > 5) {
                    Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(System.currentTimeMillis() - b));
                }
            } else {
                if (System.currentTimeMillis() - b > 5) {
                    Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(System.currentTimeMillis() - b));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (System.currentTimeMillis() - b > 5) {
                Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(System.currentTimeMillis() - b));
            }
            invokeResult = null;
        } catch (Throwable th) {
            if (System.currentTimeMillis() - b > 5) {
                Log.i(TAG, "doHookInner method(%s.%s) cost %s ms", method.getDeclaringClass().getName(), method.getName(), Long.valueOf(System.currentTimeMillis() - b));
            }
        }
        return invokeResult;
    }

    public void setFakedResult(Object fakedResult) {
        this.mFakedResult = fakedResult;
        this.mUseFakedResult = true;
    }

    protected boolean beforeInvoke(Object receiver, Method method, Object[] args) throws Throwable {
        return false;
    }

    protected void afterInvoke(Object receiver, Method method, Object[] args, Object invokeResult) throws Throwable {
    }

    public boolean isFakedResult() {
        return this.mUseFakedResult;
    }

    public Object getFakedResult() {
        return this.mFakedResult;
    }
}
