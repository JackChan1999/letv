package com.tencent.open.c;

import android.content.Context;
import android.webkit.WebView;
import java.lang.reflect.Method;

/* compiled from: ProGuard */
public class b extends WebView {
    public b(Context context) {
        super(context);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Method method = getSettings().getClass().getMethod("removeJavascriptInterface", new Class[]{String.class});
            if (method != null) {
                method.invoke(this, new Object[]{"searchBoxJavaBridge_"});
            }
        } catch (Exception e) {
        }
    }
}
