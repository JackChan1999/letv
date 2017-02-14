package com.letv.android.client.floatball;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.letv.core.utils.LogInfo;

public class ActiveItemImageView extends ImageView {
    static final /* synthetic */ boolean $assertionsDisabled = (!ActiveItemImageView.class.desiredAssertionStatus());
    public static final float DEFAULT_BORDER_WIDTH = 0.0f;
    public static final float DEFAULT_RADIUS = 0.0f;
    private static final ScaleType[] SCALE_TYPES = new ScaleType[]{ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE};
    public static final String TAG = "RoundedImageView";
    private ColorStateList borderColor;
    private float borderWidth;
    private float cornerRadius;
    private boolean isOval;
    private Drawable mBackgroundDrawable;
    private Drawable mDrawable;
    private int mResource;
    private ScaleType mScaleType;
    private boolean mutateBackground;

    public ActiveItemImageView(Context context) {
        super(context);
        this.cornerRadius = 0.0f;
        this.borderWidth = 0.0f;
        this.borderColor = ColorStateList.valueOf(-16777216);
        this.isOval = false;
        this.mutateBackground = false;
    }

    public ActiveItemImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActiveItemImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.cornerRadius = 0.0f;
        this.borderWidth = 0.0f;
        this.borderColor = ColorStateList.valueOf(-16777216);
        this.isOval = false;
        this.mutateBackground = false;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, 0);
        int index = a.getInt(R.styleable.RoundedImageView_android_scaleType, -1);
        if (index >= 0) {
            setScaleType(SCALE_TYPES[index]);
        } else {
            setScaleType(ScaleType.FIT_CENTER);
        }
        this.cornerRadius = (float) a.getDimensionPixelSize(R.styleable.RoundedImageView_riv_corner_radius, -1);
        this.borderWidth = (float) a.getDimensionPixelSize(R.styleable.RoundedImageView_riv_border_width, -1);
        if (this.cornerRadius < 0.0f) {
            this.cornerRadius = 0.0f;
        }
        if (this.borderWidth < 0.0f) {
            this.borderWidth = 0.0f;
        }
        this.borderColor = a.getColorStateList(R.styleable.RoundedImageView_riv_border_color);
        if (this.borderColor == null) {
            this.borderColor = ColorStateList.valueOf(-16777216);
        }
        this.mutateBackground = a.getBoolean(R.styleable.RoundedImageView_riv_mutate_background, false);
        this.isOval = a.getBoolean(R.styleable.RoundedImageView_riv_oval, false);
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(true);
        a.recycle();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        if (!$assertionsDisabled && scaleType == null) {
            throw new AssertionError();
        } else if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            switch (1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            invalidate();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        this.mResource = 0;
        this.mDrawable = RoundedDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    public void setImageBitmap(Bitmap bm) {
        this.mResource = 0;
        this.mDrawable = RoundedDrawable.fromBitmap(bm);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    public void setImageResource(int resId) {
        if (this.mResource != resId) {
            this.mResource = resId;
            this.mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(this.mDrawable);
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    private Drawable resolveResource() {
        Resources rsrc = getResources();
        if (rsrc == null) {
            return null;
        }
        Drawable d = null;
        if (this.mResource != 0) {
            try {
                d = rsrc.getDrawable(this.mResource);
            } catch (Exception e) {
                LogInfo.log("RoundedImageView", "Unable to find resource: " + this.mResource + "  " + e);
                this.mResource = 0;
            }
        }
        return RoundedDrawable.fromDrawable(d);
    }

    private void updateDrawableAttrs() {
        updateAttrs(this.mDrawable);
    }

    private void updateBackgroundDrawableAttrs(boolean convert) {
        if (this.mutateBackground) {
            if (convert) {
                this.mBackgroundDrawable = RoundedDrawable.fromDrawable(this.mBackgroundDrawable);
            }
            updateAttrs(this.mBackgroundDrawable);
        }
    }

    private void updateAttrs(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof RoundedDrawable) {
                ((RoundedDrawable) drawable).setScaleType(this.mScaleType).setCornerRadius(this.cornerRadius).setBorderWidth(this.borderWidth).setBorderColor(this.borderColor).setOval(this.isOval);
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable ld = (LayerDrawable) drawable;
                int layers = ld.getNumberOfLayers();
                for (int i = 0; i < layers; i++) {
                    updateAttrs(ld.getDrawable(i));
                }
            }
        }
    }

    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        this.mBackgroundDrawable = background;
        updateBackgroundDrawableAttrs(true);
        super.setBackgroundDrawable(this.mBackgroundDrawable);
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public void setCornerRadius(float radius) {
        if (this.cornerRadius != radius) {
            this.cornerRadius = radius;
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
        }
    }

    public float getBorderWidth() {
        return this.borderWidth;
    }

    public void setBorderWidth(float width) {
        if (this.borderWidth != width) {
            this.borderWidth = width;
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            invalidate();
        }
    }

    public int getBorderColor() {
        return this.borderColor.getDefaultColor();
    }

    public void setBorderColor(int color) {
        setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return this.borderColor;
    }

    public void setBorderColor(ColorStateList colors) {
        if (!this.borderColor.equals(colors)) {
            if (colors == null) {
                colors = ColorStateList.valueOf(-16777216);
            }
            this.borderColor = colors;
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs(false);
            if (this.borderWidth > 0.0f) {
                invalidate();
            }
        }
    }

    public boolean isOval() {
        return this.isOval;
    }

    public void setOval(boolean oval) {
        this.isOval = oval;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs(false);
        invalidate();
    }

    public boolean mutatesBackground() {
        return this.mutateBackground;
    }

    public void mutateBackground(boolean mutate) {
        if (this.mutateBackground != mutate) {
            this.mutateBackground = mutate;
            updateBackgroundDrawableAttrs(true);
            invalidate();
        }
    }
}
