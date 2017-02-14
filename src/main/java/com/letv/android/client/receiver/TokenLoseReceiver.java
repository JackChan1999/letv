package com.letv.android.client.receiver;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.android.client.module.LoginManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class TokenLoseReceiver extends BroadcastReceiver {
    public static final String TAG = "TokenLoseReceiver";
    public static boolean sTokenTostShow = false;
    Dialog dialog;
    private Context mContext;

    public TokenLoseReceiver(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.dialog = null;
        this.mContext = context;
    }

    public void onReceive(Context context, Intent intent) {
        LogInfo.log("ZSM", "token失效收到广播消息 == " + intent.getAction());
        if (!intent.getAction().equals("TokenLoseReceiver1")) {
            doLogoutToast();
        } else if (this.mContext != null) {
            doLogout();
        }
    }

    private void doLogoutToast() {
        if (sTokenTostShow) {
            LogInfo.log("ZSM ", "isTokenTostShow == TRUE");
            return;
        }
        sTokenTostShow = true;
        LoginManager.getLoginManager().sendLogInOutIntent("logout_success", this.mContext);
        PreferencesManager.getInstance().logoutUser();
        ToastUtils.showToast(this.mContext, this.mContext.getString(2131101020));
        tokenStatistics();
    }

    private void doLogout() {
        if (this.mContext != null || (this.dialog != null && this.dialog.isShowing())) {
            LogInfo.log("ZSM", "activity.isFinishing()>>>");
            return;
        }
        this.dialog = new Builder(this.mContext).setTitle(2131100003).setIcon(2130837921).setMessage(2131101020).setPositiveButton(2131101021, new 2(this)).setNegativeButton(2131101022, new 1(this)).create();
        if (this.mContext != null) {
            this.dialog.show();
            this.dialog.setCancelable(false);
            tokenStatistics();
        }
    }

    private void tokenStatistics() {
    }
}
