package com.letv.mobile.lebox.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.view.LetvCommonDialog;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import java.lang.reflect.InvocationTargetException;

public class DialogUtil {
    private static final int animTotallTime = 220;
    private static float[] collectionAniScaleRec = new float[]{1.0f, 0.6f, 1.5f, 0.8f, 1.0f};
    private static LetvCommonDialog mCommonDialog;
    private static float scaleRate = 0.1f;
    private static float[] unConllectionAniScalRec = new float[]{1.0f, 0.6f, 1.5f, 0.6f, 1.0f};

    public static void hideSoftkeyboard(Activity mActivity) {
        if (mActivity != null && mActivity.getCurrentFocus() != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService("input_method");
            if (mInputMethodManager != null) {
                mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 2);
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
        mCommonDialog = new LetvCommonDialog(context);
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
            try {
                mCommonDialog.show();
            } catch (Exception e) {
            }
        }
    }

    public static void showDialog(Activity context, CharSequence title, CharSequence centerText, OnClickListener centerListener) {
        if (mCommonDialog != null && mCommonDialog.isShowing()) {
            mCommonDialog.dismiss();
        }
        mCommonDialog = new LetvCommonDialog(context);
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
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener, View view) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setView(view).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
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
        try {
            dialog.show();
        } catch (Exception e) {
        }
        return true;
    }

    public static void call(Context activity, String title, String message, int yes, int no, OnClickListener yesListener, OnClickListener noListener, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(title).setIcon(R.drawable.dialog_icon).setMessage(message).setCancelable(cancelable).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if ((activity instanceof Activity) && !((Activity) activity).isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, int titleId, int messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            try {
                Dialog dialog = new Builder(activity).setTitle(titleId).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).setNegativeButton(R.string.dialog_default_no, no).create();
                if (!activity.isFinishing() && !activity.isRestricted()) {
                    try {
                        dialog.show();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void call(Activity activity, int messageId, OnClickListener yes) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, int title, int messageId, int yes, OnClickListener yesListener, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(title).setIcon(R.drawable.dialog_icon).setMessage(messageId).setCancelable(cancelable).setPositiveButton(yes, yesListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, String message, OnClickListener yes) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(message).setPositiveButton(R.string.dialog_default_ok, yes).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, String message, OnClickListener yes, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(R.string.dialog_default_title).setIcon(R.drawable.dialog_icon).setMessage(message).setPositiveButton(R.string.dialog_default_ok, yes).create();
            dialog.setCancelable(cancelable);
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener, boolean cancelable) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setMessage(messageId).setCancelable(cancelable).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, String messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(R.string.dialog_default_ok, yes).setNegativeButton(R.string.dialog_default_no, no).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void call(Activity activity, String messageId, int yesId, int noId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setIcon(R.drawable.dialog_icon).setMessage(messageId).setPositiveButton(yesId, yes).setNegativeButton(noId, no).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
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

    public static void animCollection(Context context, View view) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() == null || view.getAnimation().hasEnded()) {
            view.post(new 1(view));
        }
    }

    private static void animCollection(View view, float[] aniScaleRec, int index) {
        animFrames(view, index, aniScaleRec, new 2(index, aniScaleRec, view));
    }

    private static void animFrames(View view, int index, float[] aniScaleRec, Runnable callback) {
        float totalFrame = 0.0f;
        for (int i = 0; i < aniScaleRec.length - 1; i++) {
            totalFrame += Math.abs(aniScaleRec[i + 1] - aniScaleRec[i]);
        }
        int animFrameTime = animTotallTime / ((int) (totalFrame / scaleRate));
        float animEnd = aniScaleRec[index + 1];
        float animStart = aniScaleRec[index];
        int duration = Math.abs((((int) (100.0f * animEnd)) - ((int) (100.0f * animStart))) / ((int) (scaleRate * 100.0f))) * animFrameTime;
        ScaleAnimation sa = new ScaleAnimation(animStart, animEnd, animStart, animEnd, 1, 0.5f, 1, 0.5f);
        sa.setFillAfter(true);
        sa.setDuration((long) duration);
        sa.setAnimationListener(new 3(callback));
        view.startAnimation(sa);
    }

    public static void animCollectionPop(View view) {
        if (view != null) {
            view.setVisibility(0);
            ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.0f, 1, 1.0f);
            sa.setDuration(400);
            view.startAnimation(sa);
        }
    }

    public static void deleteShortCutIcon(Context ctx, int string_app_name, String className) {
        Intent shortcutIntent = new Intent("android.intent.action.MAIN");
        shortcutIntent.setClassName(ctx, className);
        shortcutIntent.addCategory("android.intent.category.LAUNCHER");
        shortcutIntent.addFlags(268435456);
        shortcutIntent.addFlags(2097152);
        Intent delShortcut = new Intent();
        delShortcut.setAction(ApkConstant.ACTION_UNINSTALL_SHORTCUT);
        delShortcut.putExtra("android.intent.extra.shortcut.NAME", ctx.getResources().getString(string_app_name));
        delShortcut.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
        ctx.sendBroadcast(delShortcut);
    }

    public static Bitmap loadResourcesBitmap(Context context, int resourcesId) {
        return BitmapFactory.decodeStream(context.getResources().openRawResource(resourcesId));
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (!(bitmap == null || bitmap.isRecycled())) {
            bitmap.recycle();
        }
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Config.RGB_565);
        new Canvas(b).drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, width, height), new Paint());
        return b;
    }

    public static void overridePendingTransition(Activity activity) {
        try {
            activity.getClass().getMethod("overridePendingTransition", new Class[]{Integer.TYPE, Integer.TYPE}).invoke(activity, new Object[]{Integer.valueOf(0), Integer.valueOf(0)});
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        } catch (IllegalAccessException e4) {
            e4.printStackTrace();
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
        }
    }
}
