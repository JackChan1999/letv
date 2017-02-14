package com.letv.download.service;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;

public class DownloadProgressObserver extends ContentObserver {
    private boolean hasUnhandledChange = false;
    private Context mContext;
    private Handler mHandler;
    private Runnable mRunnable = new 1(this);
    long newTime;
    long oldTime = System.currentTimeMillis();

    public DownloadProgressObserver(Handler handler) {
        super(handler);
        this.mHandler = handler;
    }

    public DownloadProgressObserver(Context context, Handler handler) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
    }

    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        this.newTime = System.currentTimeMillis();
        if (this.newTime - this.oldTime > SPConstant.DELAY_BUFFER_DURATION) {
            this.hasUnhandledChange = false;
            this.mHandler.removeCallbacks(this.mRunnable);
            if (this.mHandler != null) {
                this.mHandler.sendEmptyMessage(1010);
                return;
            }
            return;
        }
        this.hasUnhandledChange = true;
        this.mHandler.postDelayed(this.mRunnable, SPConstant.DELAY_BUFFER_DURATION);
    }
}
