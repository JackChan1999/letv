package com.letv.share.tencent.weibo.ex;

import android.content.Context;

public interface ITWeiboNew {

    public interface TWeiboListener {
        void onComplete();

        void onError();

        void onFail(String str);
    }

    int isLogin(Context context);

    void login(Context context, TWeiboListener tWeiboListener);

    void logout(Context context);

    void share(Context context, TWeiboListener tWeiboListener, String str, String str2, boolean z);

    void sharePic(Context context, TWeiboListener tWeiboListener, String str, String str2, boolean z);
}
