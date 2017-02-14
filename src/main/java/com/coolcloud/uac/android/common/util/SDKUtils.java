package com.coolcloud.uac.android.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.StatFs;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.NumberPicker;

@SuppressLint({"NewApi"})
public class SDKUtils {
    private static final String TAG = "SDKUtils";

    public static void setAlpha(View view, float value) {
        try {
            if (VERSION.SDK_INT >= 11) {
                view.setAlpha(value);
                return;
            }
            view.getBackground().setAlpha((int) (255.0f * value));
        } catch (Throwable e) {
            LOG.w(TAG, "set alpha failed(Throwable): " + e.getMessage());
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        try {
            if (VERSION.SDK_INT >= 16) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        } catch (Throwable e) {
            LOG.w(TAG, "set background failed(Throwable): ", e);
        }
    }

    public static void setCheckBoxBackground(CheckBox mChechBox, Drawable drawable) {
        mChechBox.setButtonDrawable(drawable);
    }

    public static int getChildCount(NumberPicker np) {
        try {
            if (VERSION.SDK_INT >= 11) {
                return np.getChildCount();
            }
        } catch (Throwable e) {
            LOG.w(TAG, "get child count failed(Throwable): " + e.getMessage());
        }
        return 0;
    }

    public static View getChildAt(NumberPicker np, int index) {
        try {
            if (VERSION.SDK_INT >= 11) {
                return np.getChildAt(index);
            }
        } catch (Throwable e) {
            LOG.w(TAG, "get child failed(Throwable): " + e.getMessage());
        }
        return null;
    }

    public static Builder buildAlertDialog(Context context) {
        try {
            if (VERSION.SDK_INT >= 11) {
                return new Builder(context, 5);
            }
            return new Builder(context);
        } catch (Throwable e) {
            LOG.w(TAG, "build alert dialog failed(Throwable): " + e.getMessage());
            return null;
        }
    }

    public static Point getScreenHeightWidth(WindowManager mWindowManager) {
        Point point = new Point();
        try {
            Display display = mWindowManager.getDefaultDisplay();
            if (VERSION.SDK_INT >= 17) {
                display.getRealSize(point);
            } else {
                point.y = display.getHeight();
                point.x = display.getWidth();
            }
        } catch (Throwable e) {
            LOG.w(TAG, "getScreenHeightWidth failed(Throwable): " + e.getMessage());
        }
        return point;
    }

    public static Long getFreeSpaceSize(StatFs statFs) {
        long freeSpaceSize = 0;
        try {
            if (VERSION.SDK_INT >= 18) {
                freeSpaceSize = statFs.getFreeBlocksLong() * statFs.getBlockSizeLong();
            } else {
                freeSpaceSize = ((long) statFs.getFreeBlocks()) * ((long) statFs.getBlockSize());
            }
        } catch (Throwable e) {
            LOG.w(TAG, "getScreenHeightWidth failed(Throwable): " + e.getMessage());
        }
        return Long.valueOf(freeSpaceSize);
    }

    public static void setFullscreen(Activity activity) {
        try {
            if (VERSION.SDK_INT >= 8) {
                activity.getWindow().setFlags(-1, -1);
            } else {
                activity.getWindow().setFlags(-1, -1);
            }
        } catch (Throwable e) {
            LOG.w(TAG, "setFullscreen failed(Throwable): " + e.getMessage());
        }
    }
}
