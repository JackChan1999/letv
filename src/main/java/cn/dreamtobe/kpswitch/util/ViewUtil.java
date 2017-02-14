package cn.dreamtobe.kpswitch.util;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ViewUtil {
    private static final String TAG = "ViewUtil";

    public static boolean refreshHeight(View view, int aimHeight) {
        if (view.isInEditMode()) {
            return false;
        }
        Log.d(TAG, String.format("refresh Height %d %d", new Object[]{Integer.valueOf(view.getHeight()), Integer.valueOf(aimHeight)}));
        if (view.getHeight() == aimHeight || Math.abs(view.getHeight() - aimHeight) == StatusBarHeightUtil.getStatusBarHeight(view.getContext())) {
            return false;
        }
        int validPanelHeight = KeyboardUtil.getValidPanelHeight(view.getContext());
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            view.setLayoutParams(new LayoutParams(-1, validPanelHeight));
        } else {
            layoutParams.height = validPanelHeight;
            view.requestLayout();
        }
        return true;
    }

    public static boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags & 1024) != 0;
    }
}
