package com.letv.mobile.lebox.ui.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import com.letv.mobile.lebox.utils.Logger;
import com.letv.mobile.lebox.utils.Util;
import java.util.ArrayList;
import java.util.List;

public class CircleRippleView extends View {
    private static final String TAG = CircleRippleView.class.getSimpleName();
    private final List<Float> alphaList = new ArrayList();
    private final int baseAlpha = 255;
    private final int baseColor = 5805549;
    private final int baseRadius = Util.dipToPx(33.0f);
    private final int borderThickness = Util.dipToPx(3.0f);
    private Paint clickPaint;
    private final int clickZoomInBase = 5;
    private boolean isStarting = true;
    private int maxWidth;
    private Paint paint;
    private final float paleBase = 0.6f;
    private final int rippleBaseAlpha = 200;
    private int screenHeight;
    private int screenWidth;
    private final List<Integer> startClickWidthList = new ArrayList();
    private final List<Integer> startWidthList = new ArrayList();
    private final int zoomInBase = 2;

    public CircleRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleRippleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.paint = new Paint();
        Paint paint = this.paint;
        getClass();
        paint.setColor(5805549);
        this.clickPaint = new Paint();
        this.clickPaint.setAntiAlias(true);
        this.clickPaint.setFilterBitmap(true);
        this.clickPaint.setDither(true);
        paint = this.clickPaint;
        getClass();
        paint.setColor(5805549);
        this.clickPaint.setStyle(Style.STROKE);
        this.clickPaint.setStrokeWidth((float) this.borderThickness);
        paint = this.clickPaint;
        getClass();
        paint.setAlpha(255);
        List list = this.alphaList;
        getClass();
        list.add(Float.valueOf(200.0f));
        this.startWidthList.add(Integer.valueOf(0));
        this.screenWidth = Util.getScreenWidth();
        this.screenHeight = Util.getScreenHeight();
        this.maxWidth = this.screenHeight > this.screenWidth ? this.screenHeight / 2 : this.screenWidth / 2;
    }

    public void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        setBackgroundColor(0);
        for (i = 0; i < this.alphaList.size(); i++) {
            float alpha = ((Float) this.alphaList.get(i)).floatValue();
            int startWidth = ((Integer) this.startWidthList.get(i)).intValue();
            this.paint.setAlpha((int) alpha);
            canvas.drawCircle((float) (this.screenWidth / 2), (float) (this.screenHeight / 2), (float) (this.baseRadius + startWidth), this.paint);
            if (this.isStarting && alpha > 0.0f && startWidth < this.maxWidth) {
                List list = this.alphaList;
                getClass();
                list.set(i, Float.valueOf(alpha - 0.6f));
                list = this.startWidthList;
                getClass();
                list.set(i, Integer.valueOf(startWidth + 2));
            }
        }
        if (this.isStarting && this.startWidthList.size() == 0) {
            list = this.alphaList;
            getClass();
            list.add(Float.valueOf(200.0f));
            this.startWidthList.add(Integer.valueOf(0));
        } else if (this.isStarting && ((Integer) this.startWidthList.get(this.startWidthList.size() - 1)).intValue() >= this.maxWidth / 5) {
            list = this.alphaList;
            getClass();
            list.add(Float.valueOf(200.0f));
            this.startWidthList.add(Integer.valueOf(0));
        }
        if ((this.alphaList.size() > 0 && ((Float) this.alphaList.get(0)).floatValue() <= 0.0f) || this.alphaList.size() > 6) {
            this.startWidthList.remove(0);
            this.alphaList.remove(0);
        }
        Paint paint = this.paint;
        getClass();
        paint.setAlpha(255);
        canvas.drawCircle((float) (this.screenWidth / 2), (float) (this.screenHeight / 2), (float) this.baseRadius, this.paint);
        for (i = 0; i < this.startClickWidthList.size(); i++) {
            startWidth = ((Integer) this.startClickWidthList.get(i)).intValue();
            canvas.drawCircle((float) (this.screenWidth / 2), (float) (this.screenHeight / 2), (float) (this.baseRadius + startWidth), this.clickPaint);
            if (startWidth <= this.maxWidth - this.baseRadius) {
                list = this.startClickWidthList;
                getClass();
                list.set(i, Integer.valueOf(startWidth + 5));
            }
        }
        if (this.startClickWidthList.size() > 0 && ((Integer) this.startClickWidthList.get(0)).intValue() >= this.maxWidth - this.baseRadius) {
            this.startClickWidthList.remove(0);
        }
        invalidate();
    }

    public void start() {
        this.isStarting = true;
    }

    public void stop() {
        this.isStarting = false;
    }

    public boolean isStarting() {
        return this.isStarting;
    }

    public synchronized void SetClickRipple() {
        Logger.d(TAG, "----------add-ripple-------------");
        this.startClickWidthList.add(Integer.valueOf(0));
    }
}
