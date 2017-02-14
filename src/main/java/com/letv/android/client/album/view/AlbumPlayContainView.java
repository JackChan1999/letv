package com.letv.android.client.album.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.letv.android.client.album.controller.LongWatchController;

public class AlbumPlayContainView extends RelativeLayout {
    private LongWatchController mLongWatchController;

    public AlbumPlayContainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlbumPlayContainView(Context context) {
        super(context);
    }

    public void setLongWatchController(LongWatchController controller) {
        this.mLongWatchController = controller;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0 && this.mLongWatchController != null) {
            this.mLongWatchController.reCalcTime();
        }
        return super.onInterceptTouchEvent(ev);
    }
}
