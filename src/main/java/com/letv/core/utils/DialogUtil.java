package com.letv.core.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.View;
import com.letv.base.R;
import com.letv.core.db.PreferencesManager;

public class DialogUtil {
    private static CommonDialog mCommonDialog;

    public static boolean canDownload3g(Activity activity) {
        if (activity == null) {
            Log.e("", " canDownload3g  activity == null ");
            return true;
        } else if (!NetworkUtils.isNetworkAvailable() || NetworkUtils.isWifi() || PreferencesManager.getInstance().isAllowMobileNetwork()) {
            return true;
        } else {
            call(activity, activity.getResources().getString(R.string.dialog_3g_download), new 1());
            return false;
        }
    }

    public static void call(Activity activity, int messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).setNegativeButton(R.string.dialog_default_no, no).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showDialog(Activity context, CharSequence title, CharSequence leftText, CharSequence rightText, OnClickListener leftListener, OnClickListener rightListener) {
        showDialog(context, title, "", leftText, rightText, leftListener, rightListener);
    }

    public static void showDialog(Activity context, CharSequence title, CharSequence content, CharSequence leftText, CharSequence rightText, OnClickListener leftListener, OnClickListener rightListener) {
        if (mCommonDialog != null && mCommonDialog.isShowing()) {
            mCommonDialog.dismiss();
        }
        mCommonDialog = new CommonDialog(context);
        mCommonDialog.setTitle(title);
        mCommonDialog.setContent(content.toString());
        mCommonDialog.setButtonText(leftText, rightText);
        if (leftListener != null) {
            mCommonDialog.setLeftOnClickListener(leftListener);
        }
        if (rightListener != null) {
            mCommonDialog.setRightOnClickListener(rightListener);
        }
        if (!context.isFinishing() && !context.isRestricted()) {
            mCommonDialog.show();
        }
    }

    public static void showDialog(Activity context, CharSequence title, CharSequence content, CharSequence leftText, CharSequence rightText, OnClickListener leftListener, OnClickListener rightListener, int layoutId) {
        if (mCommonDialog != null && mCommonDialog.isShowing()) {
            mCommonDialog.dismiss();
        }
        mCommonDialog = new CommonDialog(context, layoutId);
        mCommonDialog.setTitle(title);
        mCommonDialog.setContent(content.toString());
        mCommonDialog.setButtonText(leftText, rightText);
        if (leftListener != null) {
            mCommonDialog.setLeftOnClickListener(leftListener);
        }
        if (rightListener != null) {
            mCommonDialog.setRightOnClickListener(rightListener);
        }
        if (!context.isFinishing() && !context.isRestricted()) {
            mCommonDialog.show();
        }
    }

    public static void showDialog(Activity context, CharSequence title, CharSequence centerText, OnClickListener centerListener) {
        if (mCommonDialog != null && mCommonDialog.isShowing()) {
            mCommonDialog.dismiss();
        }
        mCommonDialog = new CommonDialog(context);
        mCommonDialog.setTitle(title);
        mCommonDialog.setButtonText(centerText);
        if (centerListener != null) {
            mCommonDialog.setCenterOnClickListener(centerListener);
        }
        if (!context.isFinishing() && !context.isRestricted()) {
            try {
                mCommonDialog.show();
            } catch (Exception e) {
            }
        }
    }

    public static void call(Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener, View view) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setView(view).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static boolean call(Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener, View view, boolean cancelable) {
        if (activity == null) {
            return false;
        }
        Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setCancelable(cancelable).setView(view).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
        if (activity.isFinishing() || activity.isRestricted()) {
            return false;
        }
        dialog.show();
        return true;
    }

    public static void call(Context activity, String title, String message, int yes, int no, OnClickListener yesListener, OnClickListener noListener, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(title).setIcon(R.drawable.dialog_icon).setMessage(message).setCancelable(cancelable).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if ((activity instanceof Activity) && !((Activity) activity).isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, int titleId, int messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            try {
                Dialog dialog = new Builder(activity).setTitle(titleId).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).setNegativeButton(R.string.dialog_default_no, no).create();
                if (!activity.isFinishing() && !activity.isRestricted()) {
                    dialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void call(Activity activity, int messageId, OnClickListener yes) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, int title, int messageId, int yes, OnClickListener yesListener, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setCancelable(cancelable).setPositiveButton(yes, yesListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, String message, OnClickListener yes) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(message).setPositiveButton(R.string.dialog_default_ok, yes).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, String message, OnClickListener yes, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(message).setPositiveButton(R.string.dialog_default_ok, yes).create();
            dialog.setCancelable(cancelable);
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setMessage(messageId).setCancelable(cancelable).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, String messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).setNegativeButton(R.string.dialog_default_no, no).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, String messageId, int yesId, int noId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(yesId, yes).setNegativeButton(noId, no).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                dialog.show();
            }
        }
    }

    public static void call(Activity activity, int messageId, int yesId, OnClickListener yes, boolean cancelable) {
        if (activity != null) {
            showDialog(activity, activity.getResources().getString(messageId), activity.getResources().getString(yesId), yes);
        }
    }

    public static void call(Activity activity, int titleId, String messageId, int yesId, int noId, OnClickListener yes, OnClickListener no, boolean cancelable) {
        if (activity != null) {
            showDialog(activity, messageId, activity.getResources().getString(yesId), activity.getResources().getString(noId), yes, no);
        }
    }
}
