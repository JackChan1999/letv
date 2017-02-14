package com.letv.share.sina.ex;

import java.io.IOException;

public interface RequestListener {
    void onComplete(String str);

    void onError(WeiboException weiboException);

    void onIOException(IOException iOException);
}
