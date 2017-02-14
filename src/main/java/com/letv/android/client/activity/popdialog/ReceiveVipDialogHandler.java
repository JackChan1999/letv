package com.letv.android.client.activity.popdialog;

import android.text.TextUtils;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.view.ReceiveVipDialog;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ReceiveVipDialogHandler extends DialogHandler {
    private static final String TAG = ReceiveVipDialogHandler.class.getSimpleName();
    private static boolean mContinuePayDialogShow = false;
    private static boolean mVipDialogShow = false;
    private ReceiveVipDialog mReceiveVipDialog;

    public static void setContimuePayDialogShow(boolean continuePayDialogShow) {
        mContinuePayDialogShow = continuePayDialogShow;
    }

    public ReceiveVipDialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(mainActivity);
    }

    public void handleRequest() {
        setFragmentRecordVisiable(false);
        if (!isShowReceiveVipDialog()) {
            setFragmentRecordVisiable(true);
            if (getSuccessor() != null) {
                getSuccessor().handleRequest();
            }
        }
    }

    public boolean isShowReceiveVipDialog() {
        if (!PreferencesManager.getInstance().isLogin() || PreferencesManager.getInstance().getReceiveVipActivityStatus() != 1) {
            return false;
        }
        String url = PreferencesManager.getInstance().getReceiveVipActivityUrl();
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (this.mMainActivity == null || this.mMainActivity.isFinishing() || mContinuePayDialogShow) {
            return false;
        }
        LogInfo.log("ZSM 会员领取弹框 mReceiveVipDialog== " + this.mReceiveVipDialog + " mVipDialogShow == " + mVipDialogShow);
        if (this.mReceiveVipDialog == null) {
            if (!mVipDialogShow) {
                this.mReceiveVipDialog = new ReceiveVipDialog(this.mMainActivity, 2131230820, url);
            }
        } else if (!this.mReceiveVipDialog.isShowing()) {
            this.mReceiveVipDialog.show();
        }
        if (this.mReceiveVipDialog == null) {
            return false;
        }
        this.mReceiveVipDialog.setOnDismissListener(new 1(this));
        if (!mVipDialogShow) {
            mVipDialogShow = true;
        }
        return true;
    }
}
