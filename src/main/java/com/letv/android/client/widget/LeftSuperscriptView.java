package com.letv.android.client.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.widget.TextView;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LeftSuperscriptView extends TextView {
    private Animation mAnimation;
    private float mDegress;
    private int mHeight;
    private int mWidth;
    private float mX;
    private float mY;

    public LeftSuperscriptView(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
        this.mAnimation = new 1(this);
        init(context, null);
    }

    public LeftSuperscriptView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mAnimation = new 1(this);
        init(context, attrs);
    }

    public LeftSuperscriptView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAnimation = new 1(this);
        init(context, attrs);
    }

    public void setVisibility(int visibility) {
        setAnimation(visibility == 0 ? this.mAnimation : null);
        super.setVisibility(visibility);
    }

    private void init(Context context, AttributeSet attrs) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        calc(Math.round(TypedValue.applyDimension(1, 28.0f, dm)), Math.round(TypedValue.applyDimension(1, 28.0f, dm)));
        this.mAnimation.setFillBefore(true);
        this.mAnimation.setFillAfter(true);
        this.mAnimation.setFillEnabled(true);
        startAnimation(this.mAnimation);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mHeight < 1 || this.mWidth < 1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(this.mWidth, this.mHeight);
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int vi = getVisibility();
        setAnimation(null);
        setVisibility(vi);
    }

    private void calc(int leftEdge, int topEdge) {
        double ab = Math.sqrt(Math.pow((double) topEdge, 2.0d) + Math.pow((double) leftEdge, 2.0d));
        double sinB = ((double) leftEdge) / ab;
        this.mDegress = -((float) Math.toDegrees(Math.asin(sinB)));
        this.mHeight = Math.round((float) (((double) topEdge) * sinB));
        double de = sinB * ((double) leftEdge);
        this.mX = -((float) ((((double) topEdge) / ab) * de));
        this.mY = (float) (sinB * de);
        this.mWidth = Math.round((float) ab);
    }
}
