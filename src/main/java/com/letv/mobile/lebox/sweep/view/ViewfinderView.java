package com.letv.mobile.lebox.sweep.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.sweep.camera.CameraManager;

public final class ViewfinderView extends View {
    private static final long ANIMATION_DELAY = 10;
    private static int MIDDLE_LINE_PADDING = 20;
    private static int MIDDLE_LINE_WIDTH = 40;
    private static final int SPEEN_DISTANCE = 6;
    private boolean bitmapShow = true;
    private CameraManager cameraManager;
    boolean isFirst = true;
    private final int maskColor;
    private final Paint paint = new Paint(1);
    private Bitmap resultBitmap;
    private final int resultColor;
    private final float scale;
    private int slideBottom;
    private int slideTop;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = getResources();
        this.maskColor = resources.getColor(R.color.letv_color_7f000000);
        this.resultColor = resources.getColor(R.color.letv_color_b0000000);
        this.scale = context.getResources().getDisplayMetrics().density;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        int W = canvas.getWidth();
        int H = canvas.getHeight();
        Rect frame = CameraManager.get().getFramingRect();
        if (frame != null) {
            drawCover(canvas, frame, W, H);
            if (this.resultBitmap != null) {
                canvas.drawBitmap(this.resultBitmap, (float) frame.left, (float) frame.top, this.paint);
                return;
            }
            drawScanningLine(canvas, frame);
            drawRectEdge(canvas, frame);
            this.paint.reset();
            postInvalidateDelayed(ANIMATION_DELAY);
        }
    }

    private void drawCover(Canvas canvas, Rect frame, int width, int height) {
        this.paint.setColor(this.resultBitmap != null ? this.resultColor : this.maskColor);
        canvas.drawRect(0.0f, 0.0f, (float) width, (float) frame.top, this.paint);
        canvas.drawRect(0.0f, (float) frame.top, (float) frame.left, (float) (frame.bottom + 1), this.paint);
        canvas.drawRect((float) (frame.right + 1), (float) frame.top, (float) width, (float) (frame.bottom + 1), this.paint);
        canvas.drawRect(0.0f, (float) (frame.bottom + 1), (float) height, (float) height, this.paint);
    }

    private void drawScanningLine(Canvas canvas, Rect frame) {
        if (this.isFirst) {
            this.isFirst = false;
            this.slideTop = 0;
            this.slideBottom = frame.bottom - frame.top;
        }
        this.slideTop += 6;
        if (this.slideTop >= this.slideBottom - MIDDLE_LINE_WIDTH) {
            this.slideTop = 0;
        }
        Rect lineRect = new Rect();
        lineRect.left = frame.left + MIDDLE_LINE_PADDING;
        lineRect.right = frame.right - MIDDLE_LINE_PADDING;
        lineRect.top = frame.top + this.slideTop;
        lineRect.bottom = (frame.top + this.slideTop) + MIDDLE_LINE_WIDTH;
        canvas.drawBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.sweep_laser)).getBitmap(), null, lineRect, this.paint);
    }

    private void drawRectEdge(Canvas canvas, Rect frame) {
        Resources resources = getResources();
        Bitmap bitmapCornerTopleft = BitmapFactory.decodeResource(resources, R.drawable.sweep_corner_top_left);
        Bitmap bitmapCornerTopright = BitmapFactory.decodeResource(resources, R.drawable.sweep_corner_top_right);
        Bitmap bitmapCornerBottomLeft = BitmapFactory.decodeResource(resources, R.drawable.sweep_corner_bottom_left);
        Bitmap bitmapCornerBottomRight = BitmapFactory.decodeResource(resources, R.drawable.sweep_corner_bottom_right);
        int sc = canvas.save();
        canvas.drawBitmap(bitmapCornerTopleft, (float) frame.left, (float) frame.top, this.paint);
        canvas.restoreToCount(sc);
        canvas.drawBitmap(bitmapCornerTopright, (float) (frame.right - bitmapCornerTopright.getWidth()), (float) frame.top, this.paint);
        canvas.restoreToCount(sc);
        canvas.drawBitmap(bitmapCornerBottomLeft, (float) frame.left, (float) (frame.bottom - bitmapCornerBottomLeft.getHeight()), this.paint);
        canvas.restoreToCount(sc);
        canvas.drawBitmap(bitmapCornerBottomRight, (float) (frame.right - bitmapCornerBottomRight.getWidth()), (float) (frame.bottom - bitmapCornerBottomRight.getHeight()), this.paint);
        canvas.restoreToCount(sc);
        bitmapCornerTopleft.recycle();
        bitmapCornerTopright.recycle();
        bitmapCornerBottomLeft.recycle();
        bitmapCornerBottomRight.recycle();
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    public void drawResultBitmap(Bitmap barcode, boolean bitmapShow) {
        this.bitmapShow = bitmapShow;
        this.resultBitmap = barcode;
        invalidate();
    }
}
