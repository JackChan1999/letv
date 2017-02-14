package com.letv.android.client.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import com.letv.android.client.utils.UIs;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class BottomRedPointView extends View {
    private static final int DEFAULT_CORNER_RADIUS_DIP = 80;
    private static Animation fadeIn;
    private static Animation fadeOut;
    private ShapeDrawable badgeBg;
    private int badgeColor;
    private Context context;
    private int d;
    private boolean isShown;
    private int marginLeft;
    private int marginTop;
    private View target;

    public BottomRedPointView(Context context, View target) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null, target);
    }

    public BottomRedPointView(Context context, AttributeSet attrs, View target) {
        this(context, attrs, 0, target);
    }

    public BottomRedPointView(Context context, AttributeSet attrs, int defStyleAttr, View target) {
        super(context, attrs, defStyleAttr);
        this.badgeColor = Color.parseColor("#f04b2b");
        this.d = UIs.dipToPx(7.0f);
        this.marginTop = UIs.dipToPx(5.0f);
        this.marginLeft = UIs.dipToPx(17.0f);
        init(context, target);
    }

    private void init(Context context, View target) {
        this.context = context;
        this.target = target;
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);
        fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(200);
        this.isShown = false;
        if (this.target != null) {
            applyTo(this.target);
        } else {
            show();
        }
    }

    private void applyTo(View target) {
        LayoutParams lp = target.getLayoutParams();
        ViewParent parent = target.getParent();
        FrameLayout container = new FrameLayout(this.context);
        ViewGroup group = (ViewGroup) parent;
        int index = group.indexOfChild(target);
        group.removeView(target);
        group.addView(container, index, lp);
        container.addView(target);
        container.addView(this);
        group.invalidate();
    }

    public void show() {
        show(false, null);
    }

    public void show(boolean animate) {
        show(animate, fadeIn);
    }

    public void show(Animation anim) {
        show(true, anim);
    }

    public void hide() {
        hide(false, null);
    }

    public void hide(boolean animate) {
        hide(animate, fadeOut);
    }

    public void hide(Animation anim) {
        hide(true, anim);
    }

    public void toggle() {
        toggle(false, null, null);
    }

    public void toggle(boolean animate) {
        toggle(animate, fadeIn, fadeOut);
    }

    public void toggle(Animation animIn, Animation animOut) {
        toggle(true, animIn, animOut);
    }

    private void show(boolean animate, Animation anim) {
        int w = getWidth();
        if (getBackground() == null) {
            if (this.badgeBg == null) {
                this.badgeBg = getDefaultBackground();
            }
            setBackgroundDrawable(this.badgeBg);
        }
        applyLayoutParams();
        if (animate) {
            startAnimation(anim);
        }
        setVisibility(0);
        this.isShown = true;
    }

    private void hide(boolean animate, Animation anim) {
        setVisibility(8);
        if (animate) {
            startAnimation(anim);
        }
        this.isShown = false;
    }

    private void toggle(boolean animate, Animation animIn, Animation animOut) {
        boolean z = true;
        if (this.isShown) {
            if (!animate || animOut == null) {
                z = false;
            }
            hide(z, animOut);
            return;
        }
        if (!animate || animIn == null) {
            z = false;
        }
        show(z, animIn);
    }

    private ShapeDrawable getDefaultBackground() {
        int r = dipToPixels(80);
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(new float[]{(float) r, (float) r, (float) r, (float) r, (float) r, (float) r, (float) r, (float) r}, null, null));
        drawable.getPaint().setColor(this.badgeColor);
        return drawable;
    }

    private void applyLayoutParams() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(this.d, this.d);
        lp.gravity = 49;
        lp.setMargins(this.marginLeft, this.marginTop, 0, 0);
        setLayoutParams(lp);
    }

    public View getTarget() {
        return this.target;
    }

    public boolean isShown() {
        return this.isShown;
    }

    private int dipToPixels(int dip) {
        return (int) TypedValue.applyDimension(1, (float) dip, getResources().getDisplayMetrics());
    }
}
