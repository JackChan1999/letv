package cn.dreamtobe.kpswitch.handler;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import cn.dreamtobe.kpswitch.IPanelConflictLayout;
import cn.dreamtobe.kpswitch.util.StatusBarHeightUtil;

public class KPSwitchRootLayoutHandler {
    private static final String TAG = "KPSRootLayoutHandler";
    private int mOldHeight = -1;
    private IPanelConflictLayout mPanelLayout;
    private final int mStatusBarHeight;
    private final View mTargetRootView;

    public KPSwitchRootLayoutHandler(View rootView) {
        this.mTargetRootView = rootView;
        this.mStatusBarHeight = StatusBarHeightUtil.getStatusBarHeight(rootView.getContext());
    }

    public void handleBeforeMeasure(int width, int height) {
        Log.d(TAG, "onMeasure, width: " + width + " height: " + height);
        if (height >= 0) {
            if (this.mOldHeight < 0) {
                this.mOldHeight = height;
                return;
            }
            int offset = this.mOldHeight - height;
            if (offset == 0) {
                Log.d(TAG, "" + offset + " == 0 break;");
            } else if (Math.abs(offset) == this.mStatusBarHeight) {
                Log.w(TAG, String.format("offset just equal statusBar height %d", new Object[]{Integer.valueOf(offset)}));
            } else {
                this.mOldHeight = height;
                IPanelConflictLayout panel = getPanelLayout(this.mTargetRootView);
                if (panel == null) {
                    Log.w(TAG, "can't find the valid panel conflict layout, give up!");
                } else if (offset > 0) {
                    panel.handleHide();
                } else if (panel.isKeyboardShowing() && panel.isVisible()) {
                    panel.handleShow();
                }
            }
        }
    }

    private IPanelConflictLayout getPanelLayout(View view) {
        if (this.mPanelLayout != null) {
            return this.mPanelLayout;
        }
        if (view instanceof IPanelConflictLayout) {
            this.mPanelLayout = (IPanelConflictLayout) view;
            return this.mPanelLayout;
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                IPanelConflictLayout v = getPanelLayout(((ViewGroup) view).getChildAt(i));
                if (v != null) {
                    this.mPanelLayout = v;
                    return this.mPanelLayout;
                }
            }
        }
        return null;
    }
}
