package com.letv.core.download.image;

import android.graphics.Bitmap;
import android.view.View;

public interface ImageDownloadStateListener {
    public static final int LOADING = 0;
    public static final int LOAD_ERROR = 2;
    public static final int LOAD_SUCCESS = 1;

    void loadFailed();

    void loadSuccess(Bitmap bitmap);

    void loadSuccess(Bitmap bitmap, String str);

    void loadSuccess(View view, Bitmap bitmap, String str);

    void loading();
}
