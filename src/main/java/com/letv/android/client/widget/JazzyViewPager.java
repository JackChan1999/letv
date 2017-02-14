package com.letv.android.client.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.letv.android.client.R;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.android.client.share.videoshot.ViewHelper;
import com.letv.android.client.widget.videoshot.OutlineContainer;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class JazzyViewPager extends ViewPager {
    private static final boolean API_11 = (VERSION.SDK_INT >= 11);
    private static final float ROT_MAX = 15.0f;
    private static final float SCALE_MAX = 0.5f;
    public static final String TAG = "JazzyViewPager";
    private static final float ZOOM_MAX = 0.5f;
    public static boolean isAnimating = false;
    public static int sOutlineColor = -1;
    private Handler jvpInnerHandler;
    private Camera mCamera;
    private TransitionEffect mEffect;
    private boolean mEnabled;
    private boolean mFadeEnabled;
    private View mLeft;
    private Matrix mMatrix;
    private HashMap<Integer, Object> mObjs;
    private boolean mOutlineEnabled;
    private View mRight;
    private float mRot;
    private float mScale;
    private State mState;
    private float[] mTempFloat2;
    private float mTrans;
    private int oldPage;

    public enum TransitionEffect {
        Standard,
        Tablet,
        CubeIn,
        CubeOut,
        FlipVertical,
        FlipHorizontal,
        Stack,
        ZoomIn,
        ZoomOut,
        RotateUp,
        RotateDown,
        Accordion
    }

    public void setHandler(Handler handler) {
        this.jvpInnerHandler = handler;
    }

    public JazzyViewPager(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this(context, null);
    }

    public JazzyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mEnabled = true;
        this.mFadeEnabled = false;
        this.mOutlineEnabled = false;
        this.mEffect = TransitionEffect.Standard;
        this.mObjs = new LinkedHashMap();
        this.jvpInnerHandler = null;
        this.mMatrix = new Matrix();
        this.mCamera = new Camera();
        this.mTempFloat2 = new float[2];
        setClipChildren(false);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.JazzyViewPager);
        setTransitionEffect(TransitionEffect.valueOf(getResources().getStringArray(R.array.jazzy_effects)[ta.getInt(0, 0)]));
        setFadeEnabled(ta.getBoolean(1, false));
        setOutlineEnabled(ta.getBoolean(2, false));
        setOutlineColor(ta.getColor(3, -1));
        switch (1.$SwitchMap$com$letv$android$client$widget$JazzyViewPager$TransitionEffect[this.mEffect.ordinal()]) {
            case 1:
            case 2:
                setFadeEnabled(true);
                break;
        }
        ta.recycle();
    }

    public void setTransitionEffect(TransitionEffect effect) {
        this.mEffect = effect;
    }

    public void setPagingEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public boolean isPagingEnabled() {
        return this.mEnabled;
    }

    public void setFadeEnabled(boolean enabled) {
        this.mFadeEnabled = enabled;
    }

    public boolean getFadeEnabled() {
        return this.mFadeEnabled;
    }

    public void setOutlineEnabled(boolean enabled) {
        this.mOutlineEnabled = enabled;
        wrapWithOutlines();
    }

    public void setOutlineColor(int color) {
        sOutlineColor = color;
    }

    private void wrapWithOutlines() {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (!(v instanceof OutlineContainer)) {
                removeView(v);
                super.addView(wrapChild(v), i);
            }
        }
    }

    private View wrapChild(View child) {
        if (!this.mOutlineEnabled || (child instanceof OutlineContainer)) {
            return child;
        }
        View out = new OutlineContainer(getContext());
        out.setLayoutParams(generateDefaultLayoutParams());
        child.setLayoutParams(new LayoutParams(-1, -1));
        out.addView(child);
        return out;
    }

    public void addView(View child) {
        super.addView(wrapChild(child));
    }

    public void addView(View child, int index) {
        super.addView(wrapChild(child), index);
    }

    public void addView(View child, ViewPager.LayoutParams params) {
        super.addView(wrapChild(child), params);
    }

    public void addView(View child, int width, int height) {
        super.addView(wrapChild(child), width, height);
    }

    public void addView(View child, int index, ViewPager.LayoutParams params) {
        super.addView(wrapChild(child), index, params);
    }

    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        try {
            return this.mEnabled ? super.onInterceptTouchEvent(arg0) : false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void logState(View v, String title) {
        Log.v(TAG, title + ": ROT (" + ViewHelper.getRotation(v) + ", " + ViewHelper.getRotationX(v) + ", " + ViewHelper.getRotationY(v) + "), TRANS (" + ViewHelper.getTranslationX(v) + ", " + ViewHelper.getTranslationY(v) + "), SCALE (" + ViewHelper.getScaleX(v) + ", " + ViewHelper.getScaleY(v) + "), ALPHA " + ViewHelper.getAlpha(v));
    }

    protected void animateScroll(int position, float positionOffset) {
        if (this.mState != State.IDLE) {
            this.mRot = (((float) (1.0d - Math.cos(6.283185307179586d * ((double) positionOffset)))) / 2.0f) * 30.0f;
            ViewHelper.setRotationY(this, this.mState == State.GOING_RIGHT ? this.mRot : -this.mRot);
            ViewHelper.setPivotX(this, ((float) getMeasuredWidth()) * 0.5f);
            ViewHelper.setPivotY(this, ((float) getMeasuredHeight()) * 0.5f);
        }
    }

    protected void animateTablet(View left, View right, float positionOffset) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                this.mRot = 30.0f * positionOffset;
                this.mTrans = getOffsetXForRotation(this.mRot, left.getMeasuredWidth(), left.getMeasuredHeight());
                ViewHelper.setPivotX(left, (float) (left.getMeasuredWidth() / 2));
                ViewHelper.setPivotY(left, (float) (left.getMeasuredHeight() / 2));
                ViewHelper.setTranslationX(left, this.mTrans);
                ViewHelper.setRotationY(left, this.mRot);
                logState(left, "Left");
            }
            if (right != null) {
                manageLayer(right, true);
                this.mRot = -30.0f * (1.0f - positionOffset);
                this.mTrans = getOffsetXForRotation(this.mRot, right.getMeasuredWidth(), right.getMeasuredHeight());
                ViewHelper.setPivotX(right, ((float) right.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(right, ((float) right.getMeasuredHeight()) * 0.5f);
                ViewHelper.setTranslationX(right, this.mTrans);
                ViewHelper.setRotationY(right, this.mRot);
                logState(right, "Right");
            }
        }
    }

    private void animateCube(View left, View right, float positionOffset, boolean in) {
        float f = 90.0f;
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                this.mRot = (in ? 90.0f : -90.0f) * positionOffset;
                ViewHelper.setPivotX(left, (float) left.getMeasuredWidth());
                ViewHelper.setPivotY(left, ((float) left.getMeasuredHeight()) * 0.5f);
                ViewHelper.setRotationY(left, this.mRot);
            }
            if (right != null) {
                manageLayer(right, true);
                if (!in) {
                    f = -90.0f;
                }
                this.mRot = (-f) * (1.0f - positionOffset);
                ViewHelper.setPivotX(right, 0.0f);
                ViewHelper.setPivotY(right, ((float) right.getMeasuredHeight()) * 0.5f);
                ViewHelper.setRotationY(right, this.mRot);
            }
        }
    }

    private void animateAccordion(View left, View right, float positionOffset) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                ViewHelper.setPivotX(left, (float) left.getMeasuredWidth());
                ViewHelper.setPivotY(left, 0.0f);
                ViewHelper.setScaleX(left, 1.0f - positionOffset);
            }
            if (right != null) {
                manageLayer(right, true);
                ViewHelper.setPivotX(right, 0.0f);
                ViewHelper.setPivotY(right, 0.0f);
                ViewHelper.setScaleX(right, positionOffset);
            }
        }
    }

    private void animateZoom(View left, View right, float positionOffset, boolean in) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                this.mScale = in ? ((1.0f - positionOffset) * 0.5f) + 0.5f : 1.5f - ((1.0f - positionOffset) * 0.5f);
                ViewHelper.setPivotX(left, ((float) left.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(left, ((float) left.getMeasuredHeight()) * 0.5f);
                ViewHelper.setScaleX(left, this.mScale);
                ViewHelper.setScaleY(left, this.mScale);
            }
            if (right != null) {
                manageLayer(right, true);
                this.mScale = in ? (0.5f * positionOffset) + 0.5f : 1.5f - (0.5f * positionOffset);
                ViewHelper.setPivotX(right, ((float) right.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(right, ((float) right.getMeasuredHeight()) * 0.5f);
                ViewHelper.setScaleX(right, this.mScale);
                ViewHelper.setScaleY(right, this.mScale);
            }
        }
    }

    private void animateRotate(View left, View right, float positionOffset, boolean up) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                this.mRot = ((float) (up ? 1 : -1)) * (ROT_MAX * positionOffset);
                this.mTrans = ((float) (up ? -1 : 1)) * ((float) (((double) getMeasuredHeight()) - (((double) getMeasuredHeight()) * Math.cos((((double) this.mRot) * 3.141592653589793d) / 180.0d))));
                ViewHelper.setPivotX(left, ((float) left.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(left, up ? 0.0f : (float) left.getMeasuredHeight());
                ViewHelper.setTranslationY(left, this.mTrans);
                ViewHelper.setRotation(left, this.mRot);
            }
            if (right != null) {
                manageLayer(right, true);
                this.mRot = ((float) (up ? 1 : -1)) * (-15.0f + (ROT_MAX * positionOffset));
                this.mTrans = ((float) (up ? -1 : 1)) * ((float) (((double) getMeasuredHeight()) - (((double) getMeasuredHeight()) * Math.cos((((double) this.mRot) * 3.141592653589793d) / 180.0d))));
                ViewHelper.setPivotX(right, ((float) right.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(right, up ? 0.0f : (float) right.getMeasuredHeight());
                ViewHelper.setTranslationY(right, this.mTrans);
                ViewHelper.setRotation(right, this.mRot);
            }
        }
    }

    private void animateFlipHorizontal(View left, View right, float positionOffset, int positionOffsetPixels) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                this.mRot = 180.0f * positionOffset;
                if (this.mRot > 90.0f) {
                    left.setVisibility(4);
                } else {
                    if (left.getVisibility() == 4) {
                        left.setVisibility(0);
                    }
                    this.mTrans = (float) positionOffsetPixels;
                    ViewHelper.setPivotX(left, ((float) left.getMeasuredWidth()) * 0.5f);
                    ViewHelper.setPivotY(left, ((float) left.getMeasuredHeight()) * 0.5f);
                    ViewHelper.setTranslationX(left, this.mTrans);
                    ViewHelper.setRotationY(left, this.mRot);
                }
            }
            if (right != null) {
                manageLayer(right, true);
                this.mRot = -180.0f * (1.0f - positionOffset);
                if (this.mRot < -90.0f) {
                    right.setVisibility(4);
                    return;
                }
                if (right.getVisibility() == 4) {
                    right.setVisibility(0);
                }
                this.mTrans = (float) (((-getWidth()) - getPageMargin()) + positionOffsetPixels);
                ViewHelper.setPivotX(right, ((float) right.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(right, ((float) right.getMeasuredHeight()) * 0.5f);
                ViewHelper.setTranslationX(right, this.mTrans);
                ViewHelper.setRotationY(right, this.mRot);
            }
        }
    }

    private void animateFlipVertical(View left, View right, float positionOffset, int positionOffsetPixels) {
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                this.mRot = 180.0f * positionOffset;
                if (this.mRot > 90.0f) {
                    left.setVisibility(4);
                } else {
                    if (left.getVisibility() == 4) {
                        left.setVisibility(0);
                    }
                    this.mTrans = (float) positionOffsetPixels;
                    ViewHelper.setPivotX(left, ((float) left.getMeasuredWidth()) * 0.5f);
                    ViewHelper.setPivotY(left, ((float) left.getMeasuredHeight()) * 0.5f);
                    ViewHelper.setTranslationX(left, this.mTrans);
                    ViewHelper.setRotationX(left, this.mRot);
                }
            }
            if (right != null) {
                manageLayer(right, true);
                this.mRot = -180.0f * (1.0f - positionOffset);
                if (this.mRot < -90.0f) {
                    right.setVisibility(4);
                    return;
                }
                if (right.getVisibility() == 4) {
                    right.setVisibility(0);
                }
                this.mTrans = (float) (((-getWidth()) - getPageMargin()) + positionOffsetPixels);
                ViewHelper.setPivotX(right, ((float) right.getMeasuredWidth()) * 0.5f);
                ViewHelper.setPivotY(right, ((float) right.getMeasuredHeight()) * 0.5f);
                ViewHelper.setTranslationX(right, this.mTrans);
                ViewHelper.setRotationX(right, this.mRot);
            }
        }
    }

    protected void animateStack(View left, View right, float positionOffset, int positionOffsetPixels) {
        if (this.mState != State.IDLE) {
            if (right != null) {
                manageLayer(right, true);
                this.mScale = (0.5f * positionOffset) + 0.5f;
                this.mTrans = (float) (((-getWidth()) - getPageMargin()) + positionOffsetPixels);
                ViewHelper.setScaleX(right, this.mScale);
                ViewHelper.setScaleY(right, this.mScale);
                ViewHelper.setTranslationX(right, this.mTrans);
            }
            if (left != null) {
                left.bringToFront();
            }
        }
    }

    @TargetApi(11)
    private void manageLayer(View v, boolean enableHardware) {
        if (API_11) {
            int layerType = enableHardware ? 2 : 0;
            if (layerType != v.getLayerType()) {
                v.setLayerType(layerType, null);
            }
        }
    }

    @TargetApi(11)
    private void disableHardwareLayer() {
        if (API_11) {
            for (int i = 0; i < getChildCount(); i++) {
                View v = getChildAt(i);
                if (v.getLayerType() != 0) {
                    v.setLayerType(0, null);
                }
            }
        }
    }

    protected float getOffsetXForRotation(float degrees, int width, int height) {
        this.mMatrix.reset();
        this.mCamera.save();
        this.mCamera.rotateY(Math.abs(degrees));
        this.mCamera.getMatrix(this.mMatrix);
        this.mCamera.restore();
        this.mMatrix.preTranslate(((float) (-width)) * 0.5f, ((float) (-height)) * 0.5f);
        this.mMatrix.postTranslate(((float) width) * 0.5f, ((float) height) * 0.5f);
        this.mTempFloat2[0] = (float) width;
        this.mTempFloat2[1] = (float) height;
        this.mMatrix.mapPoints(this.mTempFloat2);
        return (degrees > 0.0f ? 1.0f : -1.0f) * (((float) width) - this.mTempFloat2[0]);
    }

    protected void animateFade(View left, View right, float positionOffset) {
        if (left != null) {
            ViewHelper.setAlpha(left, 1.0f - positionOffset);
        }
        if (right != null) {
            ViewHelper.setAlpha(right, positionOffset);
        }
    }

    protected void animateOutline(View left, View right) {
        if (!(left instanceof OutlineContainer)) {
            return;
        }
        if (this.mState != State.IDLE) {
            if (left != null) {
                manageLayer(left, true);
                ((OutlineContainer) left).setOutlineAlpha(1.0f);
            }
            if (right != null) {
                manageLayer(right, true);
                ((OutlineContainer) right).setOutlineAlpha(1.0f);
                return;
            }
            return;
        }
        if (left != null) {
            ((OutlineContainer) left).start();
        }
        if (right != null) {
            ((OutlineContainer) right).start();
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        float effectOffset;
        isAnimating = true;
        if (this.mState == State.IDLE && positionOffset > 0.0f) {
            this.oldPage = getCurrentItem();
            this.mState = position == this.oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
        }
        boolean goingRight;
        if (position == this.oldPage) {
            goingRight = true;
        } else {
            goingRight = false;
        }
        if (this.mState == State.GOING_RIGHT && !goingRight) {
            this.mState = State.GOING_LEFT;
        } else if (this.mState == State.GOING_LEFT && goingRight) {
            this.mState = State.GOING_RIGHT;
        }
        if (isSmall(positionOffset)) {
            effectOffset = 0.0f;
        } else {
            effectOffset = positionOffset;
        }
        this.mLeft = findViewFromObject(position);
        this.mRight = findViewFromObject(position + 1);
        if (this.mFadeEnabled) {
            animateFade(this.mLeft, this.mRight, effectOffset);
        }
        if (this.mOutlineEnabled) {
            animateOutline(this.mLeft, this.mRight);
        }
        switch (1.$SwitchMap$com$letv$android$client$widget$JazzyViewPager$TransitionEffect[this.mEffect.ordinal()]) {
            case 1:
                break;
            case 2:
                animateZoom(this.mLeft, this.mRight, effectOffset, false);
                break;
            case 4:
                animateTablet(this.mLeft, this.mRight, effectOffset);
                break;
            case 5:
                animateCube(this.mLeft, this.mRight, effectOffset, true);
                break;
            case 6:
                animateCube(this.mLeft, this.mRight, effectOffset, false);
                break;
            case 7:
                animateFlipVertical(this.mLeft, this.mRight, positionOffset, positionOffsetPixels);
                break;
            case 8:
                animateFlipHorizontal(this.mLeft, this.mRight, effectOffset, positionOffsetPixels);
                break;
            case 9:
                animateZoom(this.mLeft, this.mRight, effectOffset, true);
                break;
            case 10:
                animateRotate(this.mLeft, this.mRight, effectOffset, true);
                break;
            case 11:
                animateRotate(this.mLeft, this.mRight, effectOffset, false);
                break;
            case 12:
                animateAccordion(this.mLeft, this.mRight, effectOffset);
                break;
        }
        animateStack(this.mLeft, this.mRight, effectOffset, positionOffsetPixels);
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        if (effectOffset == 0.0f) {
            disableHardwareLayer();
            this.mState = State.IDLE;
        }
        if (this.jvpInnerHandler != null) {
            this.jvpInnerHandler.sendEmptyMessage(VideoShotEditActivity.VIDEOSHOT_VIEWPAGER_FLIP);
        }
    }

    private boolean isSmall(float positionOffset) {
        return ((double) Math.abs(positionOffset)) < 1.0E-4d;
    }

    public void setObjectForPosition(Object obj, int position) {
        this.mObjs.put(Integer.valueOf(position), obj);
    }

    public View findViewFromObject(int position) {
        Object o = this.mObjs.get(Integer.valueOf(position));
        if (o == null) {
            return null;
        }
        PagerAdapter a = getAdapter();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (a.isViewFromObject(v, o)) {
                return v;
            }
        }
        return null;
    }
}
