package com.coolcloud.uac.android.api;

import android.os.Bundle;

public interface OnResultListener {
    void onCancel();

    void onError(ErrInfo errInfo);

    void onResult(Bundle bundle);
}
