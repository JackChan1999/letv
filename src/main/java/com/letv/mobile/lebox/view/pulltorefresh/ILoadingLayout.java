package com.letv.mobile.lebox.view.pulltorefresh;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public interface ILoadingLayout {
    void setLastUpdatedLabel(CharSequence charSequence);

    void setLoadingDrawable(Drawable drawable);

    void setPullLabel(CharSequence charSequence);

    void setRefreshingLabel(CharSequence charSequence);

    void setReleaseLabel(CharSequence charSequence);

    void setTextTypeface(Typeface typeface);
}
