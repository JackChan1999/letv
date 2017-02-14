package com.letv.mobile.lebox.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;

public class DialogUtils {
    public static AlertDialog buildBottomDialog(Activity activity, View contentView) {
        AlertDialog dialog = new Builder(activity).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setContentView(contentView);
        Window dialogWindow = dialog.getWindow();
        Display d = activity.getWindowManager().getDefaultDisplay();
        LayoutParams p = dialogWindow.getAttributes();
        p.width = d.getWidth();
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(80);
        return dialog;
    }

    public static PopupWindow dialog(View dialogView, int gravity, int anim, int x, int y, int width, int heigh) {
        if (dialogView == null) {
            throw new NullPointerException("dialogView is null from method:dialog Class:DialogUtils");
        }
        PopupWindow popup = new PopupWindow(dialogView, width, heigh);
        if (anim != 0) {
            popup.setAnimationStyle(anim);
        }
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setOutsideTouchable(true);
        popup.showAtLocation(dialogView, gravity, x, y);
        return popup;
    }

    public static void closeDialog(PopupWindow popup) {
        if (popup != null) {
            popup.dismiss();
        }
    }
}
