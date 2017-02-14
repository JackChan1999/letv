package com.sina.weibo.sdk.component.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class LoadingBar extends TextView {
    private static final int MAX_PROGRESS = 100;
    private Handler mHander;
    private Paint mPaint;
    private int mProgress;
    private int mProgressColor;
    private Runnable mRunnable = new 1(this);

    public LoadingBar(Context context) {
        super(context);
        init(context);
    }

    public LoadingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mHander = new Handler();
        this.mPaint = new Paint();
        initSkin();
    }

    public void initSkin() {
        this.mProgressColor = -11693826;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setColor(this.mProgressColor);
        canvas.drawRect(getRect(), this.mPaint);
    }

    private Rect getRect() {
        return new Rect(0, 0, (getLeft() + (((getRight() - getLeft()) * this.mProgress) / 100)) - getLeft(), getBottom() - getTop());
    }

    public void drawProgress(int progress) {
        if (progress < 7) {
            this.mHander.postDelayed(this.mRunnable, 70);
        } else {
            this.mHander.removeCallbacks(this.mRunnable);
            this.mProgress = progress;
        }
        invalidate();
    }
}
