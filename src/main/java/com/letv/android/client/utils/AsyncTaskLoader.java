package com.letv.android.client.utils;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.Loader;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class AsyncTaskLoader<D> extends Loader<D> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncTaskLoader";
    volatile LoadTask mCancellingTask;
    Handler mHandler;
    long mLastLoadCompleteTime;
    volatile LoadTask mTask;
    long mUpdateThrottle;

    public abstract D loadInBackground();

    public AsyncTaskLoader(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mLastLoadCompleteTime = -10000;
    }

    public void setUpdateThrottle(long delayMS) {
        this.mUpdateThrottle = delayMS;
        if (delayMS != 0) {
            this.mHandler = new Handler();
        }
    }

    protected void onForceLoad() {
        super.onForceLoad();
        cancelLoad();
        this.mTask = new LoadTask(this);
        executePendingTask();
    }

    public boolean cancelLoad() {
        boolean cancelled = false;
        if (this.mTask != null) {
            if (this.mCancellingTask != null) {
                if (this.mTask.waiting) {
                    this.mTask.waiting = false;
                    this.mHandler.removeCallbacks(this.mTask);
                }
                this.mTask = null;
            } else if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
                this.mTask = null;
            } else {
                cancelled = this.mTask.cancel(false);
                if (cancelled) {
                    this.mCancellingTask = this.mTask;
                }
                this.mTask = null;
            }
        }
        return cancelled;
    }

    public void onCanceled(D d) {
    }

    void executePendingTask() {
        if (this.mCancellingTask == null && this.mTask != null) {
            if (this.mTask.waiting) {
                this.mTask.waiting = false;
                this.mHandler.removeCallbacks(this.mTask);
            }
            if (this.mUpdateThrottle <= 0 || SystemClock.uptimeMillis() >= this.mLastLoadCompleteTime + this.mUpdateThrottle) {
                this.mTask.executeOnExecutor(ModernAsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
                return;
            }
            this.mTask.waiting = true;
            this.mHandler.postAtTime(this.mTask, this.mLastLoadCompleteTime + this.mUpdateThrottle);
        }
    }

    void dispatchOnCancelled(LoadTask task, D data) {
        onCanceled(data);
        if (this.mCancellingTask == task) {
            rollbackContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mCancellingTask = null;
            executePendingTask();
        }
    }

    void dispatchOnLoadComplete(LoadTask task, D data) {
        if (this.mTask != task) {
            dispatchOnCancelled(task, data);
        } else if (isAbandoned()) {
            onCanceled(data);
        } else {
            commitContentChanged();
            this.mLastLoadCompleteTime = SystemClock.uptimeMillis();
            this.mTask = null;
            deliverResult(data);
        }
    }

    protected D onLoadInBackground() {
        return loadInBackground();
    }

    public void waitForLoader() {
        LoadTask task = this.mTask;
        if (task != null) {
            try {
                LoadTask.access$000(task).await();
            } catch (InterruptedException e) {
            }
        }
    }
}
