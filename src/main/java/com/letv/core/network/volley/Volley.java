package com.letv.core.network.volley;

import com.letv.core.BaseApplication;
import com.letv.core.download.image.DiskLruCache;
import java.io.File;
import java.io.IOException;

public class Volley {
    private static final int MAX = 20971520;
    private static Volley instance = null;
    private static VolleyRequestQueue sQueue = null;
    private DiskLruCache mDiskLruCache;

    private Volley() {
        File cacheDir = BaseApplication.getInstance().getFilesDir();
        if (cacheDir != null) {
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            this.mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1, 20971520);
        }
    }

    public static VolleyRequestQueue getQueue() {
        if (sQueue == null) {
            sQueue = newRequestQueue();
        }
        return sQueue;
    }

    public static Volley getInstance() {
        if (instance == null) {
            instance = new Volley();
        }
        return instance;
    }

    public DiskLruCache getDiskLurCache() {
        return this.mDiskLruCache;
    }

    public void flush() {
        if (this.mDiskLruCache != null) {
            try {
                this.mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static VolleyRequestQueue newRequestQueue() {
        VolleyRequestQueue queue = new VolleyRequestQueue(new BasicNetwork(new HurlStack()), new BasicNetwork(new HurlFileStack()));
        queue.start();
        return queue;
    }
}
