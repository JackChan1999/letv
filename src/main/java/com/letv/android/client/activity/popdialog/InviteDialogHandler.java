package com.letv.android.client.activity.popdialog;

import android.content.Context;
import android.content.Intent;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.InviteActivity;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.remotedevice.Constant.ControlAction;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.TipUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class InviteDialogHandler extends DialogHandler {
    private Context mContext;

    public InviteDialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(mainActivity);
        this.mContext = LetvApplication.getInstance();
    }

    public void handleRequest() {
        setFragmentRecordVisiable(false);
        if (isShowInvite()) {
            setThreeDialogShowAlready(true);
            return;
        }
        setFragmentRecordVisiable(true);
        if (getSuccessor() != null) {
            getSuccessor().handleRequest();
        }
    }

    private boolean isShowInvite() {
        boolean invite_switch = PreferencesManager.getInstance().getInviteVisibleFlag();
        LogInfo.log("+->", "invite_switch" + invite_switch + "--");
        boolean inviteFlag = PreferencesManager.getInstance().getInviteFlag();
        LogInfo.log("king", "invite_switch= : " + invite_switch + "---inviteFlag : " + inviteFlag);
        if (!invite_switch || !inviteFlag || !NetworkUtils.isNetworkAvailable() || this.mMainActivity.isHomeFragmentHidden()) {
            return false;
        }
        LogInfo.log("king", "invite....1");
        String left = TipUtils.getTipMessage(DialogMsgConstantId.INVITE_LEFT_BUTTON_TEXT, this.mContext.getString(2131100203));
        String check = TipUtils.getTipMessage(DialogMsgConstantId.INVITE_CHECK_BUTTON_TEXT, this.mContext.getString(2131100204));
        String bottom = TipUtils.getTipMessage(DialogMsgConstantId.INVITE_BOTTM_BUTTON_TEXT, this.mContext.getString(2131100205));
        Intent intent = new Intent();
        intent.setClass(this.mContext, InviteActivity.class);
        intent.putExtra(ControlAction.ACTION_KEY_LEFT, left);
        intent.putExtra("check", check);
        intent.putExtra("bottom", bottom);
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
        return true;
    }
}
