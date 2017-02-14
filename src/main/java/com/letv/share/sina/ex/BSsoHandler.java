package com.letv.share.sina.ex;

import android.content.Intent;

public abstract class BSsoHandler {
    public abstract void authorize(WeiboAuthListener weiboAuthListener);

    public abstract void authorizeCallBack(int i, int i2, Intent intent);
}
