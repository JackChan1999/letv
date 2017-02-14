package com.letv.android.client.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LetvSlipSwitch extends View implements OnTouchListener, OnClickListener {
    private Bitmap btn_slip;
    private float currentX;
    private boolean isSlipping;
    private boolean isSwitchListener;
    private boolean isSwitchOn;
    private boolean preSwitchState;
    private float preX;
    private Rect rect_off;
    private Rect rect_on;
    private OnSlipSwitchListener switchListenerState;
    private Bitmap switch_off_bkg;
    private Bitmap switch_on_bkg;

    public interface OnSlipSwitchListener {
        void onSwitched(boolean z);
    }

    public LetvSlipSwitch(Context context, AttributeSet attrs) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs);
        init();
    }

    public LetvSlipSwitch(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnClickListener(this);
        setImageRes(2130838968, 2130838967, 2130838966);
    }

    public void onClick(View v) {
        boolean z = false;
        this.preSwitchState = this.isSwitchOn;
        this.isSlipping = false;
        if (!this.isSwitchOn) {
            z = true;
        }
        this.isSwitchOn = z;
        if (this.isSwitchListener && this.preSwitchState != this.isSwitchOn) {
            this.switchListenerState.onSwitched(this.isSwitchOn);
        }
        invalidate();
    }

    public boolean onTouch(View v, MotionEvent event) {
        boolean z = false;
        switch (event.getAction()) {
            case 0:
                this.isSlipping = true;
                this.preX = event.getX();
                this.currentX = this.preX;
                break;
            case 2:
                this.currentX = event.getX();
                break;
            default:
                this.preSwitchState = this.isSwitchOn;
                this.isSlipping = false;
                if (event.getX() > ((float) (this.switch_on_bkg.getWidth() / 2))) {
                    z = true;
                }
                this.isSwitchOn = z;
                if (this.isSwitchListener && this.preSwitchState != this.isSwitchOn) {
                    this.switchListenerState.onSwitched(this.isSwitchOn);
                    break;
                }
        }
        invalidate();
        return true;
    }

    public void setImageRes(int bkgSwitchOn, int bkgSwitchOff, int btnSlip) {
        this.switch_on_bkg = BitmapFactory.decodeResource(getResources(), bkgSwitchOn);
        this.switch_off_bkg = BitmapFactory.decodeResource(getResources(), bkgSwitchOff);
        this.btn_slip = BitmapFactory.decodeResource(getResources(), btnSlip);
        this.rect_on = new Rect(this.switch_on_bkg.getWidth() - this.btn_slip.getWidth(), 0, this.switch_on_bkg.getWidth(), this.btn_slip.getHeight());
        this.rect_off = new Rect(0, 0, this.btn_slip.getWidth(), this.btn_slip.getHeight());
    }

    public void setSwitchState(boolean state) {
        this.isSwitchOn = state;
        invalidate();
    }

    public void setSlipSwitchListener(OnSlipSwitchListener listener) {
        this.switchListenerState = listener;
        this.isSwitchListener = true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.switch_on_bkg.getWidth(), this.switch_on_bkg.getHeight());
    }

    protected void onDraw(Canvas canvas) {
        int left_slip;
        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        Paint paint = new Paint();
        if (this.isSwitchOn) {
            left_slip = this.rect_on.left;
            canvas.drawBitmap(this.switch_on_bkg, matrix, paint);
        } else {
            left_slip = this.rect_off.left;
            canvas.drawBitmap(this.switch_off_bkg, matrix, paint);
        }
        canvas.drawBitmap(this.btn_slip, (float) left_slip, 0.0f, paint);
    }
}
