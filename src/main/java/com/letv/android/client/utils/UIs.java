package com.letv.android.client.utils;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.SplashActivity;
import com.letv.android.client.commonlib.view.PublicLoadLayout;
import com.letv.core.BaseApplication;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.CommonDialog;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.TipUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import java.util.List;

public class UIs {
    private static final int animTotallTime = 220;
    private static float[] collectionAniScaleRec = new float[]{1.0f, 0.6f, 1.5f, 0.8f, 1.0f};
    private static CommonDialog mCommonDialog;
    private static float scaleRate = 0.1f;
    private static float[] unConllectionAniScalRec = new float[]{1.0f, 0.6f, 1.5f, 0.6f, 1.0f};

    public UIs() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static PublicLoadLayout createLoadPage(Context context, int layoutId) {
        return createLoadPage(context, layoutId, false);
    }

    public static PublicLoadLayout createLoadPage(Context context, int layoutId, boolean isToFrameLayout) {
        PublicLoadLayout rootView = new PublicLoadLayout(context);
        rootView.addContent(layoutId, isToFrameLayout);
        return rootView;
    }

    public static View inflate(Context context, int resource, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(context).inflate(resource, root, attachToRoot);
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        return LayoutInflater.from(context).inflate(resource, root);
    }

    public static int[] measure(View v) {
        v.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int width = v.getMeasuredWidth();
        int height = v.getMeasuredHeight();
        return new int[]{width, height};
    }

    public static View inflate(LayoutInflater inflater, int resource, ViewGroup root, boolean attachToRoot) {
        return inflater.inflate(resource, root, attachToRoot);
    }

    public static int zoomWidth(int w) {
        return Math.round((((float) (w * Math.min(getScreenWidth(), getScreenHeight()))) / 320.0f) + 0.5f);
    }

    public static int zoomHeight(int h) {
        return (int) ((((float) (h * getScreenHeight())) / 480.0f) + 0.5f);
    }

    public static int zoomRealHeight(int h) {
        return (int) ((((float) (h * Math.max(getScreenWidth(), getScreenHeight()))) / 480.0f) + 0.5f);
    }

    public static void zoomView(int w, int h, View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.width = zoomWidth(w);
                params.height = zoomWidth(h);
            }
        }
    }

    public static void zoomViewHeight(int h, View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.height = zoomWidth(h);
            }
        }
    }

    public static int dipToPx(float dipValue) {
        return (int) ((dipValue * LetvApplication.getInstance().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float dipToPxFloat(int dipValue) {
        return ((float) dipValue) * Global.displayMetrics.density;
    }

    public static int getScreenWidth() {
        return ((WindowManager) LetvApplication.getInstance().getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        return ((WindowManager) LetvApplication.getInstance().getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static boolean isLandscape() {
        return isLandscape(LetvApplication.getInstance());
    }

    public static boolean isLandscape(Context activity) {
        if (activity != null && activity.getResources().getConfiguration().orientation == 2) {
            return true;
        }
        return false;
    }

    public static void call(Activity activity, int messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            Dialog dialog = new Builder(activity).setTitle(2131100003).setIcon(2130837921).setMessage(messageId).setPositiveButton(2131100002, yes).setNegativeButton(2131100001, no).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void showDialog(Activity context, CharSequence title, CharSequence message, int imageResId, CharSequence leftText, CharSequence rightText, OnClickListener leftListener, OnClickListener rightListener, int titleColor, int msgColor, int leftButtonColorResId, int rightButtonColorResId) {
        if (mCommonDialog != null && mCommonDialog.isShowing()) {
            mCommonDialog.dismiss();
        }
        mCommonDialog = new CommonDialog(context);
        mCommonDialog.setTitleColor(titleColor);
        mCommonDialog.setMessageColor(msgColor);
        mCommonDialog.setButtonTextColor(leftButtonColorResId, rightButtonColorResId);
        mCommonDialog.setTitle(title);
        if (!TextUtils.isEmpty(message)) {
            mCommonDialog.setContent(message.toString());
        }
        mCommonDialog.setImage(imageResId);
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

    public static void call(Context context, Activity activity, int messageId, int yes, int no, OnClickListener yesListener, OnClickListener noListener) {
        if (activity != null) {
            String title = context.getString(2131100003);
            String msg = context.getString(messageId);
            String y = context.getString(yes);
            Dialog dialog = new Builder(activity).setTitle(title).setMessage(msg).setPositiveButton(y, yesListener).setNegativeButton(context.getString(no), noListener).create();
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
            Dialog dialog = new Builder(activity).setTitle(2131100003).setIcon(2130837921).setMessage(messageId).setView(view).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
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
        Dialog dialog = new Builder(activity).setTitle(2131100003).setIcon(2130837921).setMessage(messageId).setCancelable(cancelable).setView(view).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
        if (activity.isFinishing() || activity.isRestricted()) {
            return false;
        }
        try {
            dialog.show();
        } catch (Exception e) {
        }
        return true;
    }

    public static void call(Activity activity, int titleId, int messageId, OnClickListener yes, OnClickListener no) {
        if (activity != null) {
            try {
                Dialog dialog = new Builder(activity).setTitle(titleId).setIcon(2130837921).setMessage(messageId).setPositiveButton(2131100002, yes).setNegativeButton(2131100001, no).create();
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
            Dialog dialog = new Builder(activity).setTitle(2131100003).setIcon(2130837921).setMessage(messageId).setPositiveButton(2131100002, yes).create();
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
            Dialog dialog = new Builder(activity).setTitle(2131100003).setIcon(2130837921).setMessage(message).setPositiveButton(2131100002, yes).create();
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
            Dialog dialog = new Builder(activity).setTitle(2131100003).setIcon(2130837921).setMessage(message).setPositiveButton(2131100002, yes).create();
            dialog.setCancelable(cancelable);
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void callDialogMsgPositiveButton(Activity activity, String msgId, OnClickListener yes) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(msgId);
        if (activity != null && dialogMsgByMsg != null) {
            CharSequence string;
            Builder builder = new Builder(activity);
            if ("".equals(dialogMsgByMsg.title) || dialogMsgByMsg.title == null) {
                string = activity.getString(2131100003);
            } else {
                string = dialogMsgByMsg.title;
            }
            Dialog dialog = builder.setTitle(string).setIcon(2130837921).setMessage(dialogMsgByMsg.message).setPositiveButton(2131100002, yes).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void callDialogMsgPosNeg(Activity activity, String msgId, int yes, int no, OnClickListener yesListener, OnClickListener noListener) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(msgId);
        if (activity != null && dialogMsgByMsg != null) {
            CharSequence string;
            Builder builder = new Builder(activity);
            if ("".equals(dialogMsgByMsg.title) || dialogMsgByMsg.title == null) {
                string = activity.getString(2131100003);
            } else {
                string = dialogMsgByMsg.title;
            }
            Dialog dialog = builder.setTitle(string).setIcon(2130837921).setMessage(dialogMsgByMsg.message).setCancelable(false).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void notifyDBShortNormal(Context context, String toastMsgID, String msgTxt) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(toastMsgID);
        if (context != null) {
            LogInfo.log("notifyDBShortNormal", "notifyDBShortNormal dialogMsgByMsg : " + dialogMsgByMsg);
            String txt = "";
            if (dialogMsgByMsg != null || TextUtils.isEmpty(msgTxt)) {
                txt = dialogMsgByMsg.message;
            } else {
                txt = msgTxt;
            }
            UIsUtils.showToast(txt);
        }
    }

    public static void animTop(Context context, final View view) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() == null || view.getAnimation().hasEnded()) {
            view.post(new Runnable() {
                public void run() {
                    UIs.animCollection(view, UIs.collectionAniScaleRec, 0);
                }
            });
        }
    }

    private static void animCollection(final View view, final float[] aniScaleRec, final int index) {
        animFrames(view, index, aniScaleRec, new Runnable() {
            public void run() {
                if (index + 1 < aniScaleRec.length - 1) {
                    UIs.animCollection(view, aniScaleRec, index + 1);
                }
            }
        });
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
        final Runnable runnable = callback;
        sa.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                runnable.run();
            }
        });
        view.startAnimation(sa);
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

    public static void createShortCut(Context context) {
        if (context != null && PreferencesManager.getInstance().getShortcut()) {
            PreferencesManager.getInstance().setShortcut(false);
            if (!hasShortcut()) {
                Intent shortcutintent = new Intent(ApkConstant.ACTION_INSTALL_SHORTCUT);
                shortcutintent.putExtra("duplicate", false);
                shortcutintent.putExtra("android.intent.extra.shortcut.NAME", context.getString(2131099758));
                shortcutintent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(context.getApplicationContext(), 2130838438));
                shortcutintent.putExtra("android.intent.extra.shortcut.INTENT", new Intent(context.getApplicationContext(), SplashActivity.class));
                context.sendBroadcast(shortcutintent);
            }
        }
    }

    private static boolean hasShortcut() {
        Cursor cursor = null;
        try {
            PackageManager pm = BaseApplication.getInstance().getPackageManager();
            String title = pm.getApplicationLabel(pm.getApplicationInfo(BaseApplication.getInstance().getPackageName(), 128)).toString();
            String authority = getAuthorityFromPermission("com.android.launcher.permission.READ_SETTINGS");
            if (authority != null) {
                Uri contentUri = Uri.parse("content://" + authority + "/favorites?notify=true");
                cursor = BaseApplication.getInstance().getContentResolver().query(contentUri, new String[]{"_id", "title", "iconResource"}, "title=?", new String[]{title}, null);
                if (cursor != null && cursor.getCount() > 0) {
                    cursor.close();
                    if (cursor != null) {
                        cursor.close();
                    }
                    return true;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    private static String getAuthorityFromPermission(String permission) {
        List<PackageInfo> packs = BaseApplication.getInstance().getPackageManager().getInstalledPackages(8);
        if (packs != null) {
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (!BaseTypeUtils.isArrayEmpty(providers)) {
                    for (ProviderInfo provider : providers) {
                        String readPermission = provider.readPermission;
                        if (readPermission != null && permission.equals(readPermission)) {
                            return provider.authority;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Config.RGB_565);
        new Canvas(b).drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(0, 0, width, height), new Paint());
        return b;
    }
}
