package com.letv.android.client.tencentlogin;

import android.content.Context;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.tencent.tauth.Tencent;

public class TencentInstance {
    private static Tencent mTencent = null;

    public TencentInstance() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static Tencent getInstance(Context context) {
        if (mTencent == null) {
            mTencent = Tencent.createInstance(AppConstants.APP_ID, context);
        }
        return mTencent;
    }
}
