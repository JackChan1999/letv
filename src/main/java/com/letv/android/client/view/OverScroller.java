package com.letv.android.client.view;

import android.content.Context;
import android.util.FloatMath;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class OverScroller {
    private static final int DEFAULT_DURATION = 250;
    private static final int FLING_MODE = 1;
    private static final int SCROLL_MODE = 0;
    private static float sViscousFluidNormalize;
    private static float sViscousFluidScale = 8.0f;
    private final boolean mFlywheel;
    private Interpolator mInterpolator;
    private int mMode;
    private final SplineOverScroller mScrollerX;
    private final SplineOverScroller mScrollerY;

    static {
        sViscousFluidNormalize = 1.0f;
        sViscousFluidNormalize = 1.0f / viscousFluid(1.0f);
    }

    public OverScroller(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public static float viscousFluid(float x) {
        x *= sViscousFluidScale;
        if (x < 1.0f) {
            x -= 1.0f - ((float) Math.exp((double) (-x)));
        } else {
            x = 0.36787945f + ((1.0f - 0.36787945f) * (1.0f - ((float) Math.exp((double) (1.0f - x)))));
        }
        return x * sViscousFluidNormalize;
    }

    public OverScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, true);
    }

    public OverScroller(Context context, Interpolator interpolator, boolean flywheel) {
        this.mInterpolator = interpolator;
        this.mFlywheel = flywheel;
        this.mScrollerX = new SplineOverScroller(context);
        this.mScrollerY = new SplineOverScroller(context);
    }

    public OverScroller(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY) {
        this(context, interpolator, true);
    }

    public OverScroller(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY, boolean flywheel) {
        this(context, interpolator, flywheel);
    }

    void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public final void setFriction(float friction) {
        this.mScrollerX.setFriction(friction);
        this.mScrollerY.setFriction(friction);
    }

    public final boolean isFinished() {
        return SplineOverScroller.access$000(this.mScrollerX) && SplineOverScroller.access$000(this.mScrollerY);
    }

    public final void forceFinished(boolean finished) {
        SplineOverScroller.access$002(this.mScrollerX, SplineOverScroller.access$002(this.mScrollerY, finished));
    }

    public final int getCurrX() {
        return SplineOverScroller.access$100(this.mScrollerX);
    }

    public final int getCurrY() {
        return SplineOverScroller.access$100(this.mScrollerY);
    }

    public float getCurrVelocity() {
        return FloatMath.sqrt((SplineOverScroller.access$200(this.mScrollerX) * SplineOverScroller.access$200(this.mScrollerX)) + (SplineOverScroller.access$200(this.mScrollerY) * SplineOverScroller.access$200(this.mScrollerY)));
    }

    public final int getStartX() {
        return SplineOverScroller.access$300(this.mScrollerX);
    }

    public final int getStartY() {
        return SplineOverScroller.access$300(this.mScrollerY);
    }

    public final int getFinalX() {
        return SplineOverScroller.access$400(this.mScrollerX);
    }

    public final int getFinalY() {
        return SplineOverScroller.access$400(this.mScrollerY);
    }

    @Deprecated
    public final int getDuration() {
        return Math.max(SplineOverScroller.access$500(this.mScrollerX), SplineOverScroller.access$500(this.mScrollerY));
    }

    @Deprecated
    public void extendDuration(int extend) {
        this.mScrollerX.extendDuration(extend);
        this.mScrollerY.extendDuration(extend);
    }

    @Deprecated
    public void setFinalX(int newX) {
        this.mScrollerX.setFinalPosition(newX);
    }

    @Deprecated
    public void setFinalY(int newY) {
        this.mScrollerY.setFinalPosition(newY);
    }

    public boolean computeScrollOffset() {
        if (isFinished()) {
            return false;
        }
        switch (this.mMode) {
            case 0:
                long elapsedTime = AnimationUtils.currentAnimationTimeMillis() - SplineOverScroller.access$600(this.mScrollerX);
                int duration = SplineOverScroller.access$500(this.mScrollerX);
                if (elapsedTime >= ((long) duration)) {
                    abortAnimation();
                    break;
                }
                float q = ((float) elapsedTime) / ((float) duration);
                if (this.mInterpolator == null) {
                    q = viscousFluid(q);
                } else {
                    q = this.mInterpolator.getInterpolation(q);
                }
                this.mScrollerX.updateScroll(q);
                this.mScrollerY.updateScroll(q);
                break;
            case 1:
                if (!(SplineOverScroller.access$000(this.mScrollerX) || this.mScrollerX.update() || this.mScrollerX.continueWhenFinished())) {
                    this.mScrollerX.finish();
                }
                if (!(SplineOverScroller.access$000(this.mScrollerY) || this.mScrollerY.update() || this.mScrollerY.continueWhenFinished())) {
                    this.mScrollerY.finish();
                    break;
                }
        }
        return true;
    }

    public void startScroll(int startX, int startY, int dx, int dy) {
        startScroll(startX, startY, dx, dy, 250);
    }

    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        this.mMode = 0;
        this.mScrollerX.startScroll(startX, dx, duration);
        this.mScrollerY.startScroll(startY, dy, duration);
    }

    public boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        this.mMode = 1;
        boolean spingbackX = this.mScrollerX.springback(startX, minX, maxX);
        boolean spingbackY = this.mScrollerY.springback(startY, minY, maxY);
        if (spingbackX || spingbackY) {
            return true;
        }
        return false;
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, 0, 0);
    }

    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        if (this.mFlywheel && !isFinished()) {
            float oldVelocityX = SplineOverScroller.access$200(this.mScrollerX);
            float oldVelocityY = SplineOverScroller.access$200(this.mScrollerY);
            if (Math.signum((float) velocityX) == Math.signum(oldVelocityX) && Math.signum((float) velocityY) == Math.signum(oldVelocityY)) {
                velocityX = (int) (((float) velocityX) + oldVelocityX);
                velocityY = (int) (((float) velocityY) + oldVelocityY);
            }
        }
        this.mMode = 1;
        this.mScrollerX.fling(startX, velocityX, minX, maxX, overX);
        this.mScrollerY.fling(startY, velocityY, minY, maxY, overY);
    }

    public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
        this.mScrollerX.notifyEdgeReached(startX, finalX, overX);
    }

    public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
        this.mScrollerY.notifyEdgeReached(startY, finalY, overY);
    }

    public boolean isOverScrolled() {
        return ((SplineOverScroller.access$000(this.mScrollerX) || SplineOverScroller.access$700(this.mScrollerX) == 0) && (SplineOverScroller.access$000(this.mScrollerY) || SplineOverScroller.access$700(this.mScrollerY) == 0)) ? false : true;
    }

    public void abortAnimation() {
        this.mScrollerX.finish();
        this.mScrollerY.finish();
    }

    public int timePassed() {
        return (int) (AnimationUtils.currentAnimationTimeMillis() - Math.min(SplineOverScroller.access$600(this.mScrollerX), SplineOverScroller.access$600(this.mScrollerY)));
    }

    public boolean isScrollingInDirection(float xvel, float yvel) {
        return !isFinished() && Math.signum(xvel) == Math.signum((float) (SplineOverScroller.access$400(this.mScrollerX) - SplineOverScroller.access$300(this.mScrollerX))) && Math.signum(yvel) == Math.signum((float) (SplineOverScroller.access$400(this.mScrollerY) - SplineOverScroller.access$300(this.mScrollerY)));
    }
}
