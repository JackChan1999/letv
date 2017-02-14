package com.letv.android.client.activity.popdialog;

import com.letv.android.client.activity.MainActivity;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public abstract class DialogHandler {
    private static final String TAG = DialogHandler.class.getSimpleName();
    protected MainActivity mMainActivity;
    protected DialogHandler mSuccessor;

    public interface IMainPopDialogHandler {
        void onSetSuccessor();
    }

    public abstract void handleRequest();

    public DialogHandler(MainActivity mainActivity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mMainActivity = mainActivity;
    }

    public void showHomeRecommend(boolean visible) {
        LogInfo.log(TAG, "showHomeRecommend visible : " + visible);
        if (this.mMainActivity != null) {
            this.mMainActivity.getHomeFragment().setHomeRecommendVisible(visible);
        }
    }

    public void setFragmentRecordVisiable(boolean isvisible) {
        LogInfo.log(TAG, "setFragmentRecordVisiable visible : " + isvisible);
        if (this.mMainActivity != null) {
            this.mMainActivity.getHomeFragment().setHomeRecordVisible(isvisible);
        }
    }

    public void setThreeDialogShowAlready(boolean bShowFlag) {
        if (this.mMainActivity != null) {
            this.mMainActivity.setThreeDialogShowAlready(bShowFlag);
        }
    }

    public DialogHandler getSuccessor() {
        return this.mSuccessor;
    }

    public void setSuccessor(DialogHandler successor) {
        this.mSuccessor = successor;
    }
}
