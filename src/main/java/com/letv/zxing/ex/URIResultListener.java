package com.letv.zxing.ex;

import android.app.Activity;

public interface URIResultListener {
    void handleResult(Activity activity, ParseResultEntity parseResultEntity);
}
