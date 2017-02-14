package com.letv.android.client.activity.popdialog;

import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.view.ContinuePayDialog;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ContinuePayDialogHandler extends DialogHandler {
    private static final String TAG = ContinuePayDialogHandler.class.getSimpleName();
    private ContinuePayDialog mContinuePayDialog;
    private String mPageId;

    public ContinuePayDialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(mainActivity);
    }

    public void handleRequest() {
        setFragmentRecordVisiable(false);
        if (!isShowContinuePayDialog()) {
            setFragmentRecordVisiable(true);
            if (getSuccessor() != null) {
                getSuccessor().handleRequest();
            }
        }
    }

    public boolean isShowContinuePayDialog() {
        LogInfo.log(TAG, "isShowContinuePayDialog isLogin : " + PreferencesManager.getInstance().isLogin());
        if (!PreferencesManager.getInstance().isLogin()) {
            return false;
        }
        long lastdays = PreferencesManager.getInstance().getLastdays();
        long chkvipday = PreferencesManager.getInstance().getChkvipday();
        LogInfo.log(TAG, " lastdays : " + lastdays + " chkvipday : " + chkvipday);
        if (Math.abs(lastdays) > chkvipday) {
            return false;
        }
        if (this.mContinuePayDialog == null) {
            this.mContinuePayDialog = new ContinuePayDialog(this.mMainActivity, 2131230820);
        }
        ReceiveVipDialogHandler.setContimuePayDialogShow(this.mContinuePayDialog.isShowing());
        this.mContinuePayDialog.setOnDismissListener(new 1(this));
        this.mPageId = this.mMainActivity.getCurrentPageId();
        this.mContinuePayDialog.setCurrentPageId(this.mPageId);
        return this.mContinuePayDialog.showPopDialogContinuePay();
    }
}
