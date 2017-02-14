package com.tencent.open.b;

import com.tencent.open.utils.Global;
import com.tencent.open.utils.OpenConfig;

/* compiled from: ProGuard */
public class e {
    public static int a(String str) {
        int i = OpenConfig.getInstance(Global.getContext(), str).getInt("Common_BusinessReportFrequency");
        if (i == 0) {
            return 100;
        }
        return i;
    }

    public static int a() {
        int i = OpenConfig.getInstance(Global.getContext(), null).getInt("Common_HttpRetryCount");
        if (i == 0) {
            return 2;
        }
        return i;
    }
}
