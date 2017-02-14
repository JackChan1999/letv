package com.letv.zxing.ex;

import android.content.Context;

public interface ZxingDecodeCallback {
    void callback(Context context, ParseResultEntity parseResultEntity);
}
