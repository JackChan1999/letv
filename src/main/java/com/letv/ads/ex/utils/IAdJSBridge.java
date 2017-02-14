package com.letv.ads.ex.utils;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;

public interface IAdJSBridge {
    void setJSBridge(Activity activity, WebView webView, Handler handler, View view);
}
