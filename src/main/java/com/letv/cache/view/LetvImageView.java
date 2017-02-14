package com.letv.cache.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.util.Observable;
import java.util.Observer;

public class LetvImageView extends ImageView implements Observer {
    private Observable mObservable;

    public LetvImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetvImageView(Context context) {
        super(context);
    }

    public void requestLayout() {
    }

    public final void boundObservable(Observable observable) {
        if (!(this.mObservable == observable || this.mObservable == null)) {
            this.mObservable.deleteObserver(this);
        }
        this.mObservable = observable;
        this.mObservable.addObserver(this);
    }

    public final void update(Observable observable, final Object data) {
        if (this.mObservable != observable) {
            return;
        }
        if (data != null) {
            ((Activity) getContext()).runOnUiThread(new Runnable() {
                public void run() {
                    LetvImageView.this.setImageDrawable((Drawable) data);
                }
            });
        } else if (this.mObservable != null) {
            this.mObservable.deleteObserver(this);
            this.mObservable = null;
        }
    }

    public final void setImageResource(int resId) {
        if (this.mObservable != null) {
            this.mObservable.deleteObserver(this);
            this.mObservable = null;
        }
        super.setImageResource(resId);
    }

    public final void setImageBitmap(Bitmap bm) {
        if (this.mObservable != null) {
            this.mObservable.deleteObserver(this);
            this.mObservable = null;
        }
        super.setImageBitmap(bm);
    }

    public final void setImageDrawable(Drawable drawable) {
        if (this.mObservable != null) {
            this.mObservable.deleteObserver(this);
            this.mObservable = null;
        }
        super.setImageDrawable(drawable);
    }
}
