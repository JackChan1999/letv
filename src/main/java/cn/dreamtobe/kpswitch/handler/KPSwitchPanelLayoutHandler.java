package cn.dreamtobe.kpswitch.handler;

import android.view.View;
import android.view.View.MeasureSpec;
import cn.dreamtobe.kpswitch.IPanelConflictLayout;

public class KPSwitchPanelLayoutHandler implements IPanelConflictLayout {
    private boolean mIsHide = false;
    private boolean mIsKeyboardShowing = false;
    private final View panelLayout;
    private final int[] processedMeasureWHSpec = new int[2];

    public KPSwitchPanelLayoutHandler(View panelLayout) {
        this.panelLayout = panelLayout;
    }

    public boolean filterSetVisibility(int visibility) {
        if (visibility == 0) {
            this.mIsHide = false;
        }
        if (visibility == this.panelLayout.getVisibility()) {
            return true;
        }
        if (isKeyboardShowing() && visibility == 0) {
            return true;
        }
        return false;
    }

    public int[] processOnMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mIsHide) {
            this.panelLayout.setVisibility(8);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(0, 1073741824);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, 1073741824);
        }
        this.processedMeasureWHSpec[0] = widthMeasureSpec;
        this.processedMeasureWHSpec[1] = heightMeasureSpec;
        return this.processedMeasureWHSpec;
    }

    public void setIsKeyboardShowing(boolean isKeyboardShowing) {
        this.mIsKeyboardShowing = isKeyboardShowing;
    }

    public boolean isKeyboardShowing() {
        return this.mIsKeyboardShowing;
    }

    public boolean isVisible() {
        return !this.mIsHide;
    }

    public void handleShow() {
        throw new IllegalAccessError("You can't invoke handle show in handler, please instead of handling in the panel layout, maybe just need invoke super.setVisibility(View.VISIBLE)");
    }

    public void handleHide() {
        this.mIsHide = true;
    }
}
