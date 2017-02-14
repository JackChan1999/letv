package cn.dreamtobe.kpswitch.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import cn.dreamtobe.kpswitch.IPanelConflictLayout;
import cn.dreamtobe.kpswitch.IPanelHeightTarget;
import cn.dreamtobe.kpswitch.handler.KPSwitchPanelLayoutHandler;
import cn.dreamtobe.kpswitch.util.ViewUtil;

public class KPSwitchPanelRelativeLayout extends RelativeLayout implements IPanelHeightTarget, IPanelConflictLayout {
    private KPSwitchPanelLayoutHandler panelLayoutHandler;

    public KPSwitchPanelRelativeLayout(Context context) {
        super(context);
        init();
    }

    public KPSwitchPanelRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KPSwitchPanelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public KPSwitchPanelRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.panelLayoutHandler = new KPSwitchPanelLayoutHandler(this);
    }

    public void setVisibility(int visibility) {
        if (!this.panelLayoutHandler.filterSetVisibility(visibility)) {
            super.setVisibility(visibility);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] processedMeasureWHSpec = this.panelLayoutHandler.processOnMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(processedMeasureWHSpec[0], processedMeasureWHSpec[1]);
    }

    public boolean isKeyboardShowing() {
        return this.panelLayoutHandler.isKeyboardShowing();
    }

    public boolean isVisible() {
        return this.panelLayoutHandler.isVisible();
    }

    public void handleShow() {
        super.setVisibility(0);
    }

    public void handleHide() {
        this.panelLayoutHandler.handleHide();
    }

    public void refreshHeight(int panelHeight) {
        ViewUtil.refreshHeight(this, panelHeight);
    }

    public void onKeyboardShowing(boolean showing) {
        this.panelLayoutHandler.setIsKeyboardShowing(showing);
    }
}
