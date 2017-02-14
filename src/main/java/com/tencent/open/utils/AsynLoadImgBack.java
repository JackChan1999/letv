package com.tencent.open.utils;

import java.util.ArrayList;

/* compiled from: ProGuard */
public interface AsynLoadImgBack {
    public static final int LOAD_IMAGE_COMPLETED = 0;
    public static final int LOAD_IMAGE_IMAGE_FORMAT_ERROR = 3;
    public static final int LOAD_IMAGE_NO_SDCARD = 2;
    public static final int LOAD_IMAGE_PATH_NULL = 1;

    void batchSaved(int i, ArrayList<String> arrayList);

    void saved(int i, String str);
}
