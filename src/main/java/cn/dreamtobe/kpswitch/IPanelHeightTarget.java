package cn.dreamtobe.kpswitch;

public interface IPanelHeightTarget {
    int getHeight();

    void onKeyboardShowing(boolean z);

    void refreshHeight(int i);
}
