package com.letv.core.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings.System;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.constant.LetvConstant.Global;
import com.tencent.open.yyb.TitleBar;
import io.fabric.sdk.android.services.common.AbstractSpiCall;

public class UIsUtils {
    private static CustomToast mLetvToast = null;

    public static View inflate(Context context, int resource, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(context).inflate(resource, root, attachToRoot);
    }

    public static View inflate(Context context, int resource, ViewGroup root) {
        return LayoutInflater.from(context).inflate(resource, root);
    }

    public static int getScreenWidth() {
        return ((WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        return ((WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getHeight();
    }

    public static int getMinScreen() {
        return Math.min(getScreenWidth(), getScreenHeight());
    }

    public static int getMaxScreen() {
        return Math.max(getScreenWidth(), getScreenHeight());
    }

    public static int getScreenHeightWithoutStatusBar(Activity activity) {
        return getScreenHeight() - getStatusBarHeight(activity);
    }

    public static int zoomWidth(int w) {
        return Math.round((((float) (w * Math.min(getScreenWidth(), getScreenHeight()))) / 320.0f) + 0.5f);
    }

    public static int zoomHeight(int h) {
        return (int) ((((float) (h * getScreenHeight())) / 480.0f) + 0.5f);
    }

    public static void zoomViewWidth(int w, View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.width = zoomWidth(w);
            }
        }
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

    public static void zoomViewFull(View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            LogInfo.log("clf", "zoomViewFull getScreenWidth()=" + getScreenWidth() + ",BaseApplication.sRawHeight=" + BaseApplication.sRawHeight + ",BaseApplication.sWidth=" + BaseApplication.sWidth);
            params.width = Math.max(BaseApplication.sWidth, BaseApplication.sRawHeight);
            params.height = Math.min(BaseApplication.sWidth, BaseApplication.sRawHeight);
            if (BaseApplication.sRawHeight != BaseApplication.sHeight) {
                view.invalidate();
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationBarHeight(Context context) {
        if (context.getResources().getConfiguration().orientation == 1) {
            return getNavigationBarPortraitHeight(context);
        }
        return getNavigationBarLandscapeWidth(context);
    }

    public static int getNavigationBarPortraitHeight(Context context) {
        if (!hasNavigationBar(context)) {
            return 0;
        }
        if (Build.MANUFACTURER.equals("Meizu")) {
            return getMeizuNaviogationBarHeight(context);
        }
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static int getNavigationBarLandscapeWidth(Context context) {
        if (!hasNavigationBar(context)) {
            return 0;
        }
        if (Build.MANUFACTURER.equals("Meizu")) {
            return getMeizuNaviogationBarHeight(context);
        }
        int resourceId;
        Resources resources = context.getResources();
        if (isTablet(context)) {
            resourceId = resources.getIdentifier("navigation_bar_height_landscape", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        } else {
            resourceId = resources.getIdentifier("navigation_bar_width", "dimen", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        }
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Nullable
    private static int getMeizuNaviogationBarHeight(Context context) {
        int dimensionPixelSize;
        boolean autoHideSmartBar = true;
        boolean z = false;
        boolean isMeiZu = Build.MANUFACTURER.equals("Meizu");
        if (System.getInt(context.getContentResolver(), "mz_smartbar_auto_hide", z) != 1) {
            autoHideSmartBar = z;
        }
        if (isMeiZu && !autoHideSmartBar) {
            try {
                Class c = Class.forName("com.android.internal.R$dimen");
                dimensionPixelSize = context.getResources().getDimensionPixelSize(
                        Integer.parseInt(c.getField("mz_action_button_min_height")
                                .get(c.newInstance()).toString()));
            } catch (Throwable th) {
            }
        }
        return dimensionPixelSize;
    }

    private static boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean hasNavigationBar(Context context) {
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", AbstractSpiCall.ANDROID_CLIENT_TYPE);
        return id > 0 && resources.getBoolean(id) && !Build.MANUFACTURER.equals("Meizu");
    }

    public static boolean isLandscape() {
        return isLandscape(BaseApplication.getInstance());
    }

    public static boolean isLandscape(Context activity) {
        if (activity != null && activity.getResources().getConfiguration().orientation == 2) {
            return true;
        }
        return false;
    }

    public static void fullScreen(Activity activity) {
        activity.getWindow().setFlags(1024, 1024);
    }

    public static void cancelFullScreen(Activity activity) {
        activity.getWindow().clearFlags(1024);
    }

    public static void setScreenLandscape(Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void setScreenPortrait(Activity activity) {
        activity.setRequestedOrientation(1);
    }

    public static int dipToPx(float dipValue) {
        return (int) ((dipValue * BaseApplication.getInstance().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float getDensity() {
        return BaseApplication.getInstance().getResources().getDisplayMetrics().density;
    }

    public static int px2dip(float pxValue) {
        return (int) ((pxValue / BaseApplication.getInstance().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void enableScreenAlwaysOn(Context context) {
        if (context instanceof Activity) {
            ((Activity) context).getWindow().addFlags(128);
        }
    }

    public static void disableScreenAlwaysOn(Context context) {
        if (!(context instanceof Activity)) {
        }
    }

    public static void measureView(View child) {
        int childHeightSpec;
        LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new LayoutParams(-1, -2);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public static int[] measure(View v) {
        v.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        return new int[]{v.getMeasuredWidth(), v.getMeasuredHeight()};
    }

    public static void zoomViewHeight(int h, View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.height = zoomWidth(h);
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

    public static void hideInputMethod(Activity mActivity) {
        if (mActivity != null && mActivity.getCurrentFocus() != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService("input_method");
            if (mInputMethodManager != null) {
                mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 2);
            }
        }
    }

    public static void showToast(String msg) {
        if (mLetvToast != null) {
            mLetvToast.cancel();
        }
        mLetvToast = new CustomToast(BaseApplication.getInstance());
        mLetvToast.setDuration(0);
        mLetvToast.setMessage(msg);
        mLetvToast.show();
    }

    public static void showToast(int msgId) {
        showToast(BaseApplication.getInstance().getString(msgId));
    }

    public static void cancelToast() {
        if (mLetvToast != null) {
            mLetvToast.cancel();
        }
    }

    public static int[] measureSize(View v) {
        v.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int width = v.getMeasuredWidth();
        int height = v.getMeasuredHeight();
        return new int[]{width, height};
    }

    public static int zoomRealHeight(int h) {
        return (int) ((((float) (h * Math.max(getScreenWidth(), getScreenHeight()))) / 480.0f) + 0.5f);
    }

    public static int getDisplayWidth() {
        int w = Global.displayMetrics.widthPixels;
        int h = Global.displayMetrics.heightPixels;
        return w < h ? w : h;
    }

    public static boolean isScreenOn(Context context) {
        return ((PowerManager) context.getSystemService(Context.POWER_SERVICE)).isScreenOn();
    }

    public static void callDialogMsgPosNeg(Activity activity, String msgId, int yes, int no, OnClickListener yesListener, OnClickListener noListener) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(msgId);
        if (activity != null && dialogMsgByMsg != null) {
            CharSequence string;
            Builder builder = new Builder(activity);
            if ("".equals(dialogMsgByMsg.title) || dialogMsgByMsg.title == null) {
                string = activity.getString(R.string.dialog_default_title);
            } else {
                string = dialogMsgByMsg.title;
            }
            Dialog dialog = builder.setTitle(string).setIcon(R.drawable.dialog_icon).setMessage(dialogMsgByMsg.message).setCancelable(false).setPositiveButton(yes, yesListener).setNegativeButton(no, noListener).create();
            if (!activity.isFinishing() && !activity.isRestricted()) {
                try {
                    dialog.show();
                } catch (Exception e) {
                }
            }
        }
    }

    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public static int[] getLocationInWindow(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    @TargetApi(17)
    public static Bitmap picBlur(Context context, Bitmap bitmap, Float radius) {
        Bitmap outPutBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outPutBitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blur.setInput(allIn);
        blur.setRadius(TitleBar.SHAREBTN_RIGHT_MARGIN);
        blur.forEach(allOut);
        allIn.copyTo(outPutBitmap);
        rs.destroy();
        return outPutBitmap;
    }

    public static ColorStateList createColorStateList(int normal, int pressed) {
        int[] colors = new int[]{pressed, pressed, pressed, pressed, normal, -1};
        int[][] states = new int[6][];
        states[4] = new int[]{android.R.attr.state_enabled};
        states[5] = new int[0];
        return new ColorStateList(states, colors);
    }

    public static StateListDrawable createStateDrawable(Drawable normal, Drawable pressed, Drawable focus, Drawable checked) {
        StateListDrawable sd = new StateListDrawable();
        sd.addState(new int[]{android.R.attr.state_pressed}, pressed);
        sd.addState(new int[]{android.R.attr.state_checked}, checked);
        sd.addState(new int[]{android.R.attr.state_focused}, focus);
        sd.addState(new int[]{android.R.attr.state_selected}, focus);
        sd.addState(new int[0], normal);
        return sd;
    }

    public static GradientDrawable createGradientDrawable(int radious, int color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius((float) radious);
        return drawable;
    }

    public static void hideSoftkeyboard(Activity mActivity) {
        if (mActivity != null && mActivity.getCurrentFocus() != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (mInputMethodManager != null) {
                mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 2);
            }
        }
    }
}
