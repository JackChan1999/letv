package com.sina.weibo.sdk.net;

import com.sina.weibo.sdk.exception.WeiboException;

public interface RequestListener {
    void onComplete(String str);

    void onWeiboException(WeiboException weiboException);
}
