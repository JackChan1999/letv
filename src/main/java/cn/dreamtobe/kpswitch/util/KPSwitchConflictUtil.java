package cn.dreamtobe.kpswitch.util;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class KPSwitchConflictUtil {

    public interface SwitchClickListener {
        void onClickSwitch(boolean z);
    }

    public static void attach(View panelLayout, View switchPanelKeyboardBtn, View focusView) {
        attach(panelLayout, switchPanelKeyboardBtn, focusView, null);
    }

    public static void attach(final View panelLayout, View switchPanelKeyboardBtn, final View focusView, final SwitchClickListener switchClickListener) {
        Activity activity = (Activity) panelLayout.getContext();
        if (switchPanelKeyboardBtn != null) {
            switchPanelKeyboardBtn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    boolean switchToPanel = KPSwitchConflictUtil.switchPanelAndKeyboard(panelLayout, focusView);
                    if (switchClickListener != null) {
                        switchClickListener.onClickSwitch(switchToPanel);
                    }
                }
            });
        }
        if (ViewUtil.isFullScreen(activity)) {
            focusView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 1) {
                        panelLayout.setVisibility(4);
                    }
                    return false;
                }
            });
        }
    }

    public static void showPanel(View panelLayout) {
        Activity activity = (Activity) panelLayout.getContext();
        panelLayout.setVisibility(0);
        if (activity.getCurrentFocus() != null) {
            KeyboardUtil.hideKeyboard(activity.getCurrentFocus());
        }
    }

    public static void showKeyboard(View panelLayout, View focusView) {
        Activity activity = (Activity) panelLayout.getContext();
        KeyboardUtil.showKeyboard(focusView);
        if (ViewUtil.isFullScreen(activity)) {
            panelLayout.setVisibility(4);
        }
    }

    public static boolean switchPanelAndKeyboard(View panelLayout, View focusView) {
        boolean switchToPanel = panelLayout.getVisibility() != 0;
        if (switchToPanel) {
            showPanel(panelLayout);
        } else {
            showKeyboard(panelLayout, focusView);
        }
        return switchToPanel;
    }

    public static void hidePanelAndKeyboard(View panelLayout) {
        Activity activity = (Activity) panelLayout.getContext();
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            KeyboardUtil.hideKeyboard(activity.getCurrentFocus());
            focusView.clearFocus();
        }
        panelLayout.setVisibility(8);
    }
}
