package com.nostra13.universalimageloader.core.imageaware;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.utils.L;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class ViewAware implements ImageAware {
    public static final String WARN_CANT_SET_BITMAP = "Can't set a bitmap into view. You should call ImageLoader on UI thread for it.";
    public static final String WARN_CANT_SET_DRAWABLE = "Can't set a drawable into view. You should call ImageLoader on UI thread for it.";
    protected boolean checkActualViewSize;
    protected Reference<View> viewRef;

    protected abstract void setImageBitmapInto(Bitmap bitmap, View view);

    protected abstract void setImageDrawableInto(Drawable drawable, View view);

    public ViewAware(View view) {
        this(view, true);
    }

    public ViewAware(View view, boolean checkActualViewSize) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        this.viewRef = new WeakReference(view);
        this.checkActualViewSize = checkActualViewSize;
    }

    public int getWidth() {
        View view = (View) this.viewRef.get();
        if (view == null) {
            return 0;
        }
        LayoutParams params = view.getLayoutParams();
        int width = 0;
        if (!(!this.checkActualViewSize || params == null || params.width == -2)) {
            width = view.getWidth();
        }
        if (width > 0 || params == null) {
            return width;
        }
        return params.width;
    }

    public int getHeight() {
        View view = (View) this.viewRef.get();
        if (view == null) {
            return 0;
        }
        LayoutParams params = view.getLayoutParams();
        int height = 0;
        if (!(!this.checkActualViewSize || params == null || params.height == -2)) {
            height = view.getHeight();
        }
        if (height > 0 || params == null) {
            return height;
        }
        return params.height;
    }

    public ViewScaleType getScaleType() {
        return ViewScaleType.CROP;
    }

    public View getWrappedView() {
        return (View) this.viewRef.get();
    }

    public boolean isCollected() {
        return this.viewRef.get() == null;
    }

    public int getId() {
        View view = (View) this.viewRef.get();
        return view == null ? super.hashCode() : view.hashCode();
    }

    public boolean setImageDrawable(Drawable drawable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            View view = (View) this.viewRef.get();
            if (view == null) {
                return false;
            }
            setImageDrawableInto(drawable, view);
            return true;
        }
        L.w(WARN_CANT_SET_DRAWABLE, new Object[0]);
        return false;
    }

    public boolean setImageBitmap(Bitmap bitmap) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            View view = (View) this.viewRef.get();
            if (view == null) {
                return false;
            }
            setImageBitmapInto(bitmap, view);
            return true;
        }
        L.w(WARN_CANT_SET_BITMAP, new Object[0]);
        return false;
    }
}
