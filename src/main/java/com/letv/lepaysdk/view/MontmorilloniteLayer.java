package com.letv.lepaysdk.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class MontmorilloniteLayer extends RelativeLayout {
    public MontmorilloniteLayer(Context context) {
        super(context);
    }

    public MontmorilloniteLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MontmorilloniteLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (getVisibility() == 0) {
            return true;
        }
        return false;
    }
}
