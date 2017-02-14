package com.letv.plugin.pluginloader.apk.hook;

import android.content.Context;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseHookHandle {
    protected Context mHostContext;
    protected Map<String, HookedMethodHandler> sHookedMethodHandlers = new HashMap(5);

    protected abstract void init();

    public BaseHookHandle(Context hostContext) {
        this.mHostContext = hostContext;
        init();
    }

    public Set<String> getHookedMethodNames() {
        return this.sHookedMethodHandlers.keySet();
    }

    public HookedMethodHandler getHookedMethodHandler(Method method) {
        if (method != null) {
            return (HookedMethodHandler) this.sHookedMethodHandlers.get(method.getName());
        }
        return null;
    }
}
