package com.letv.mobile.lebox.view.pulltorefresh;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import java.util.HashSet;
import java.util.Iterator;

public class LoadingLayoutProxy implements ILoadingLayout {
    private final HashSet<LoadingLayout> mLoadingLayouts = new HashSet();

    LoadingLayoutProxy() {
    }

    public void addLayout(LoadingLayout layout) {
        if (layout != null) {
            this.mLoadingLayouts.add(layout);
        }
    }

    public void setLastUpdatedLabel(CharSequence label) {
        Iterator it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            ((LoadingLayout) it.next()).setLastUpdatedLabel(label);
        }
    }

    public void setLoadingDrawable(Drawable drawable) {
        Iterator it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            ((LoadingLayout) it.next()).setLoadingDrawable(drawable);
        }
    }

    public void setRefreshingLabel(CharSequence refreshingLabel) {
        Iterator it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            ((LoadingLayout) it.next()).setRefreshingLabel(refreshingLabel);
        }
    }

    public void setPullLabel(CharSequence label) {
        Iterator it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            ((LoadingLayout) it.next()).setPullLabel(label);
        }
    }

    public void setReleaseLabel(CharSequence label) {
        Iterator it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            ((LoadingLayout) it.next()).setReleaseLabel(label);
        }
    }

    public void setTextTypeface(Typeface tf) {
        Iterator it = this.mLoadingLayouts.iterator();
        while (it.hasNext()) {
            ((LoadingLayout) it.next()).setTextTypeface(tf);
        }
    }
}
