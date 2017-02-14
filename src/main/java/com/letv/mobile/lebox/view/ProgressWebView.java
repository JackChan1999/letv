package com.letv.mobile.lebox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ProgressBar;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.utils.Util;

public class ProgressWebView extends WebView {
    private final ProgressBar progressbar;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 16842885);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.progressbar = new ProgressBar(context, null, 16842872);
        this.progressbar.setLayoutParams(new LayoutParams(-1, Util.dip2px(context, 3.0f), 0, 0));
        this.progressbar.setProgressDrawable(context.getResources().getDrawable(R.drawable.webview_loading_progress_style));
        addView(this.progressbar);
        setWebChromeClient(new WebChromeClient(this));
    }

    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) this.progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        this.progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
