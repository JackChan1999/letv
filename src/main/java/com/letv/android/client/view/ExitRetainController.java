package com.letv.android.client.view;

import android.app.Activity;
import android.view.View;

public interface ExitRetainController {

    public interface ExitRetainCallBack {
        void onClickExitBtnReportData(String str);

        void onClickLookBtnReportData(String str);

        void onDismissPopWindow();

        void onExitAppliation();

        void onShowPopWindow();

        void onShowReportData(String str);
    }

    void clearCacheCmsID();

    void dismissPopupwindow();

    String getCurrentPageID();

    void getExitRetainData();

    boolean isShow();

    void setActivity(Activity activity);

    void setCurrentPageID(String str);

    void setExitRetainPopupwindowCallBack(ExitRetainCallBack exitRetainCallBack);

    boolean showExitRetainPopupwindow(View view);
}
