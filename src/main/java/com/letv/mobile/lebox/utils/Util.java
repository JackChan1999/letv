package com.letv.mobile.lebox.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.letv.ads.db.DBConstant;
import com.letv.mobile.lebox.LeBoxApp;
import com.letv.mobile.lebox.R;
import com.letv.mobile.lebox.http.lebox.LeBoxHttpErrorCode;
import com.letv.plugin.pluginloader.apk.common.ApkConstant;
import com.letv.pp.utils.NetworkUtils;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Util {
    static final String LOG_TAG = "PullToRefresh";
    private static final int animTotallTime = 220;
    private static float[] collectionAniScaleRec = new float[]{1.0f, 0.6f, 1.5f, 0.8f, 1.0f};
    static Handler handler;
    private static float scaleRate = 0.1f;

    public static String getGB_Number(long size, int scale) {
        if (size > 1073741824) {
            return getDecimalsVal(((double) size) / 1.073741824E9d, 1) + "G";
        } else if (size > 1048576) {
            return getDecimalsVal(((double) size) / 1048576.0d, scale) + "M";
        } else if (size == 0) {
            return "0M";
        } else {
            return getMB_Decimal(size);
        }
    }

    public static String getDownLoadingSpeed(String speed) {
        double s = (double) getLong(speed, 0);
        if (s <= 0.0d) {
            return "0 KB/S";
        }
        if (s <= 1024.0d) {
            return "" + s + " KB/S";
        }
        return "" + ((double) Math.round(s / 1024.0d)) + " MB/s";
    }

    public static String getDecimalsVal(double val, int scale) {
        return new BigDecimal(val).setScale(scale, 4).toString() + "";
    }

    public static String getMB_Decimal(long size) {
        String mS = Double.valueOf(new DecimalFormat(".0").format(((double) size) / 1048576.0d)).toString();
        int start = mS.lastIndexOf(".");
        if (start <= 0) {
            return mS + ".0M";
        }
        if (mS.substring(start + 1).length() == 1) {
            return mS + "0M";
        }
        return mS + "M";
    }

    public static int getScreenWidth() {
        return ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        return ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static int dipToPx(float dipValue) {
        return (int) ((dipValue * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int dip2px(Context paramContext, float paramFloat) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity) paramContext).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        return (int) FloatMath.ceil(localDisplayMetrics.density * paramFloat);
    }

    public static int[] measure(View v) {
        v.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int width = v.getMeasuredWidth();
        int height = v.getMeasuredHeight();
        return new int[]{width, height};
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

    public static void zoomViewWidth(int w, View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.width = zoomWidth(w);
            }
        }
    }

    public static void zoomViewFull(View view) {
        if (view != null) {
            LayoutParams params = view.getLayoutParams();
            if (params != null) {
                params.width = getScreenWidth();
                params.height = getScreenHeight();
            }
        }
    }

    public static int getStatusBarHeight(Activity act) {
        Rect frame = new Rect();
        act.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int getTitleBarHeight(Activity act) {
        return act.getWindow().findViewById(16908290).getTop() - getStatusBarHeight(act);
    }

    public static int getContentHeight(Activity act) {
        return getScreenHeight() - act.getWindow().findViewById(16908290).getTop();
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

    public static void notFullScreen(Activity activity) {
        activity.getWindow().clearFlags(1024);
    }

    public static void screenLandscape(Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void screenPortrait(Activity activity) {
        activity.setRequestedOrientation(1);
    }

    public static void hideSoftkeyboard(Activity mActivity) {
        if (mActivity != null && mActivity.getCurrentFocus() != null) {
            InputMethodManager mInputMethodManager = (InputMethodManager) mActivity.getSystemService("input_method");
            if (mInputMethodManager != null) {
                mInputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 2);
            }
        }
    }

    public static void animTop(Context context, View view) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() == null || view.getAnimation().hasEnded()) {
            view.post(new 1(view));
        }
    }

    public static void animCollection(Context context, View view) {
        if (view == null) {
            return;
        }
        if (view.getAnimation() == null || view.getAnimation().hasEnded()) {
            view.post(new 2(view));
        }
    }

    private static void animCollection(View view, float[] aniScaleRec, int index) {
        animFrames(view, index, aniScaleRec, new 3(index, aniScaleRec, view));
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
        sa.setAnimationListener(new 4(callback));
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

    public static View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(getContext()).inflate(resource, root, attachToRoot);
    }

    public static void notifyDBShortNormal(Context context, String toastMsgID, String msgTxt) {
        if (context != null) {
        }
    }

    public static void showToast(Context context, int msgId) {
        Toast.makeText(context, msgId, 0).show();
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, 1).show();
    }

    public static void showToast(int msg_id) {
        Toast.makeText(getContext(), getContext().getResources().getString(msg_id), 1).show();
    }

    public static void showHttpErrorToast(String errorCode) {
        Context context = getContext();
        String errorMsg = LeBoxHttpErrorCode.getErrorCodeInfo(context, errorCode);
        if (!TextUtils.isEmpty(errorMsg)) {
            showToast(context, errorMsg);
        }
    }

    public static Context getContext() {
        return LeBoxApp.getApplication();
    }

    public static int getInt(String number, int defaultNumber) {
        return TextUtils.isEmpty(number) ? defaultNumber : Integer.valueOf(number).intValue();
    }

    public static float getFloat(String number, float defaultNumber) {
        return TextUtils.isEmpty(number) ? defaultNumber : Float.valueOf(number).floatValue();
    }

    public static long getLong(String number, long defaultNumber) {
        return TextUtils.isEmpty(number) ? defaultNumber : Long.valueOf(number).longValue();
    }

    public static Date stringToDate(String time) {
        int tempPos = time.indexOf(DBConstant.AD);
        time = time.trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        if (tempPos > -1) {
            time = time.substring(0, tempPos) + "公元" + time.substring(DBConstant.AD.length() + tempPos);
            simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
        }
        if (time.indexOf(NetworkUtils.DELIMITER_LINE) > -1 && time.indexOf(" ") < 0) {
            simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssZ");
        } else if (time.indexOf("/") > -1 && time.indexOf(" ") > -1) {
            simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else if (time.indexOf(NetworkUtils.DELIMITER_LINE) > -1 && time.indexOf(" ") > -1) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else if ((time.indexOf("/") > -1 && time.indexOf("am") > -1) || time.indexOf("pm") > -1) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        } else if ((time.indexOf(NetworkUtils.DELIMITER_LINE) > -1 && time.indexOf("am") > -1) || time.indexOf("pm") > -1) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
        }
        return simpleDateFormat.parse(time, new ParsePosition(0));
    }

    public static String getString(int id) {
        return LeBoxApp.getInstanced().getString(id);
    }

    public static String getString(int id, String s1) {
        return String.format(LeBoxApp.getInstanced().getString(id), new Object[]{s1});
    }

    public static String getString(int id, String s1, String s2) {
        return String.format(LeBoxApp.getInstanced().getString(id), new Object[]{s1, s2});
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        return Pattern.compile("[A-Z0-9a-z一-龥_。]+").matcher(str).matches() ? str : "";
    }

    public static void warnDeprecation(String depreacted, String replacement) {
        Logger.w(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
    }

    public static String getConnectProcessPrompt(int p) {
        String str = "";
        int resourcesId = -1;
        switch (p) {
            case 11:
                resourcesId = R.string.connect_lebox_state_wifi_opean_fail;
                break;
            case 12:
                resourcesId = R.string.connect_lebox_state_wifi_opeaning;
                break;
            case 13:
                resourcesId = R.string.connect_lebox_state_wifi_opean_fail;
                break;
            case 15:
                resourcesId = R.string.connect_lebox_state_Searching;
                break;
            case 16:
                resourcesId = R.string.connect_lebox_state_Search_fail;
                break;
            case 17:
                resourcesId = R.string.connect_lebox_state_connecting;
                break;
            case 19:
                resourcesId = R.string.connect_lebox_state_connecting;
                break;
            case 20:
                resourcesId = R.string.connect_lebox_state_connect_fail;
                break;
            case 21:
                resourcesId = R.string.connect_lebox_state_connected;
                break;
            case 23:
                resourcesId = R.string.connect_lebox_state_logining;
                break;
            case 24:
                resourcesId = R.string.connect_lebox_state_login_fail;
                break;
            case 25:
                resourcesId = R.string.connect_lebox_state_login_no_permission;
                break;
            case 26:
                resourcesId = R.string.connect_lebox_state_login_success;
                break;
        }
        if (resourcesId >= 0) {
            return LeBoxApp.getApplication().getResources().getString(resourcesId);
        }
        return str;
    }

    public static boolean isBackground(Context context) {
        List<RunningTaskInfo> tasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (tasks.isEmpty() || ((RunningTaskInfo) tasks.get(0)).topActivity.getPackageName().equals(context.getPackageName())) {
            return false;
        }
        return true;
    }
}
