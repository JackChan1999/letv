package com.letv.core.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.letv.base.R;
import com.letv.core.BaseApplication;
import com.letv.core.bean.TipMapBean.TipBean;

public class ToastUtils {
    private static CustomToast mToast = null;

    public static void notifyShort(Context context, String text) {
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setErr(false);
        mToast.setMessage(text);
        mToast.show();
    }

    public static void notifyShort(Context context, int textId) {
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setErr(false);
        mToast.setMessageById(textId);
        mToast.show();
    }

    public static void showToast(String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessage(text);
        mToast.setGravity(17, 0, 0);
        mToast.show();
    }

    public static void showToast(String text, int position, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.setMessage(text);
        mToast.setGravity(position, xOffset, yOffset);
        mToast.show();
    }

    public static void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public static void showCentorTextToast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_center_text, (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_root));
        ((TextView) layout.findViewById(R.id.text)).setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(16, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public static void showToast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        context = BaseApplication.getInstance();
        if (context != null) {
            mToast = new CustomToast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessage(text);
        mToast.show();
    }

    public static void showToast(Context context, int textResId) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (context != null) {
            mToast = new CustomToast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessage(context.getString(textResId));
        mToast.show();
    }

    public static void showNormalToast(int textResId) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessageById(textResId);
        mToast.show();
    }

    public static void showToast(int txtId) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessageById(txtId);
        mToast.setGravity(17, 0, 0);
        mToast.show();
    }

    public static void showToastString(String txt) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessage(txt);
        mToast.setGravity(17, 0, 0);
        mToast.show();
    }

    public static void notifyDBShortNormal(Context context, String toastMsgID) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(toastMsgID);
        if (context != null && dialogMsgByMsg != null) {
            if (mToast != null) {
                mToast.cancel();
            }
            if (context != null) {
                mToast = new CustomToast(context);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.setMessage(dialogMsgByMsg.message);
            mToast.setGravity(17, 0, 0);
            mToast.show();
        }
    }

    public static void notifyDBShortNormal(Context context, String toastMsgID, String msgTxt) {
        TipBean dialogMsgByMsg = TipUtils.getTipBean(toastMsgID);
        if (context != null) {
            String txt = "";
            if (dialogMsgByMsg != null || TextUtils.isEmpty(msgTxt)) {
                txt = dialogMsgByMsg.message;
            } else {
                txt = msgTxt;
            }
            if (mToast != null) {
                mToast.cancel();
            }
            if (context != null) {
                mToast = new CustomToast(context);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.setMessage(txt);
            mToast.setGravity(17, 0, 0);
            mToast.show();
        }
    }

    public static void notifyShortNormal(Context context, int textId) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (context != null) {
            mToast = new CustomToast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessageById(textId);
        mToast.show();
    }

    public static void notifyShortNormal(Context context, String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        if (context != null) {
            mToast = new CustomToast(context);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setMessage(msg);
        mToast.show();
    }

    public static void notifyLong(Context context, String text) {
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.setErr(false);
        mToast.setMessage(text);
        mToast.show();
    }

    public static void notifyLong(Context context, int textId) {
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.setErr(false);
        mToast.setMessageById(textId);
        mToast.show();
    }

    public static void notifyToast(int textId, int time) {
        if (BaseApplication.getInstance() != null) {
            mToast = new CustomToast(BaseApplication.getInstance());
            mToast.setDuration(time);
        }
        mToast.setErr(false);
        mToast.setMessageById(textId);
        mToast.show();
    }

    public static void show4dToast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_center_text, (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_root));
        ((TextView) layout.findViewById(R.id.text)).setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(48, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
