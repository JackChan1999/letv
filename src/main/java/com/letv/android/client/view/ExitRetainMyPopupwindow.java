package com.letv.android.client.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.widget.PopupWindow;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class ExitRetainMyPopupwindow extends PopupWindow {
    private DismissListener mPreDismissListener;

    public interface DismissListener {
        void onDismissed();

        void onPreDismiss();
    }

    public ExitRetainMyPopupwindow(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context);
    }

    public DismissListener getDismissListener() {
        return this.mPreDismissListener;
    }

    public void setDismissListener(DismissListener DismissListener) {
        this.mPreDismissListener = DismissListener;
    }

    public void dismiss() {
        if (VERSION.SDK_INT >= 11) {
            if (this.mPreDismissListener != null) {
                this.mPreDismissListener.onPreDismiss();
            }
            super.dismiss();
            if (this.mPreDismissListener != null) {
                this.mPreDismissListener.onDismissed();
                return;
            }
            return;
        }
        LogInfo.log("dismiss", "dismiss>>> SDK_INT > 11");
        super.dismiss();
        setFocusable(false);
    }
}
