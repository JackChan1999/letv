package com.letv.ads.ex.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public abstract class LetvSimpleAsyncTask<T> extends Thread implements LetvSimpleAsyncTaskInterface<T> {
    protected Context context;
    private Handler handler;
    private boolean isCancel = false;

    public boolean isCancel() {
        return this.isCancel;
    }

    public void cancel(boolean isCancel) {
        this.isCancel = isCancel;
    }

    public LetvSimpleAsyncTask(Context context) {
        this.context = context;
        this.handler = new Handler(Looper.getMainLooper());
    }

    public final void run() {
        postUI(new Runnable() {
            public void run() {
                LetvSimpleAsyncTask.this.onPreExecute();
            }
        });
        if (!this.isCancel) {
            final T result = doInBackground();
            if (!this.isCancel) {
                postUI(new Runnable() {
                    public void run() {
                        if (!LetvSimpleAsyncTask.this.isCancel) {
                            LetvSimpleAsyncTask.this.onPostExecute(result);
                        }
                    }
                });
            }
        }
    }

    private void postUI(Runnable runnable) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            this.handler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
