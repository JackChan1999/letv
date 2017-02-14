package com.letv.android.client.constant;

import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class LiveFragmentConstant {
    public static final int FROM_CHANNEL = 2;
    public static final int FROM_HOME = 1;
    public static final int FROM_LIVE = 0;
    public static final int FROM_STAR = 3;
    public static final String KEY_NAME = "name";

    public LiveFragmentConstant() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }
}
