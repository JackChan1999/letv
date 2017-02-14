package cn.dreamtobe.kpswitch.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Window;
import android.widget.LinearLayout;
import cn.dreamtobe.kpswitch.IFSPanelConflictLayout;
import cn.dreamtobe.kpswitch.IPanelHeightTarget;
import cn.dreamtobe.kpswitch.handler.KPSwitchFSPanelLayoutHandler;
import cn.dreamtobe.kpswitch.util.ViewUtil;

public class KPSwitchFSPanelLinearLayout extends LinearLayout implements IPanelHeightTarget, IFSPanelConflictLayout {
    private KPSwitchFSPanelLayoutHandler panelHandler;

    public KPSwitchFSPanelLinearLayout(Context context) {
        super(context);
        init();
    }

    public KPSwitchFSPanelLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(11)
    public KPSwitchFSPanelLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public KPSwitchFSPanelLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.panelHandler = new KPSwitchFSPanelLayoutHandler(this);
    }

    public void refreshHeight(int panelHeight) {
        ViewUtil.refreshHeight(this, panelHeight);
    }

    public void onKeyboardShowing(boolean showing) {
        this.panelHandler.onKeyboardShowing(showing);
    }

    public void recordKeyboardStatus(Window window) {
        this.panelHandler.recordKeyboardStatus(window);
    }
}
