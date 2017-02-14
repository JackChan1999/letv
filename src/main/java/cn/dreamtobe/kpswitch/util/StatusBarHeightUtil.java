package cn.dreamtobe.kpswitch.util;

import android.content.Context;
import android.util.Log;

public class StatusBarHeightUtil {
    private static boolean INIT = false;
    private static final String STATUS_BAR_DEF_PACKAGE = "android";
    private static final String STATUS_BAR_DEF_TYPE = "dimen";
    private static int STATUS_BAR_HEIGHT = 50;
    private static final String STATUS_BAR_NAME = "status_bar_height";

    public static synchronized int getStatusBarHeight(Context context) {
        int i;
        synchronized (StatusBarHeightUtil.class) {
            if (!INIT) {
                int resourceId = context.getResources().getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, "android");
                if (resourceId > 0) {
                    STATUS_BAR_HEIGHT = context.getResources().getDimensionPixelSize(resourceId);
                    INIT = true;
                    Log.d("StatusBarHeightUtil", String.format("Get status bar height %d", new Object[]{Integer.valueOf(STATUS_BAR_HEIGHT)}));
                }
            }
            i = STATUS_BAR_HEIGHT;
        }
        return i;
    }
}
