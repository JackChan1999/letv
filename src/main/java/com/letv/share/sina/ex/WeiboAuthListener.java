package com.letv.share.sina.ex;

import android.os.Bundle;

public interface WeiboAuthListener {
    void onCancel();

    void onComplete(Bundle bundle);

    void onError(WeiboDialogError weiboDialogError);

    void onWeiboException(WeiboException weiboException);
}
