package com.flurry.sdk;

import com.letv.pp.utils.NetworkUtils;
import java.util.concurrent.ThreadFactory;

public class jg implements ThreadFactory {
    private final ThreadGroup a;
    private final int b;

    public jg(String str, int i) {
        this.a = new ThreadGroup(str);
        this.b = i;
    }

    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(this.a, runnable);
        thread.setName(this.a.getName() + NetworkUtils.DELIMITER_COLON + thread.getId());
        thread.setPriority(this.b);
        return thread;
    }
}
