package com.letv.android.client.webview;

import android.util.Log;
import android.webkit.WebResourceResponse;
import com.letv.android.client.webapp.LetvWebAppManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class NativeWebViewUtils {
    private static NativeWebViewUtils mNativeWebViewUtils;
    private static boolean sFromNative = true;
    private Map<String, String> minitype;
    private String resource;

    public static NativeWebViewUtils getInstance() {
        if (mNativeWebViewUtils == null) {
            synchronized (NativeWebViewUtils.class) {
                if (mNativeWebViewUtils == null) {
                    mNativeWebViewUtils = new NativeWebViewUtils();
                }
            }
        }
        return mNativeWebViewUtils;
    }

    private NativeWebViewUtils() {
        this.resource = "";
        this.resource = LetvWebAppManager.WBE_APP_UNZIP_PATH;
        this.minitype = new 1(this);
    }

    public void setIsFromNative(boolean isFromNative) {
        sFromNative = isFromNative;
    }

    public boolean getFromNative() {
        return sFromNative;
    }

    public WebResourceResponse getResource(String path) {
        int index = path.lastIndexOf(".");
        if (index == -1) {
            return null;
        }
        String ext = path.substring(index);
        Log.d("wlx", ext);
        String mini = (String) this.minitype.get(ext);
        if (mini == null) {
            return null;
        }
        try {
            return new WebResourceResponse(mini, "UTF-8", new FileInputStream(this.resource + path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
