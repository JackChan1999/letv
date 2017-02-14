package com.letv.android.client.constant;

import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ShareConstant {

    public interface BindState {
        public static final int BIND = 1;
        public static final int BINDPASS = 2;
        public static final int UNBIND = 0;
    }

    public ShareConstant() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }
}
