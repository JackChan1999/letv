package com.letv.core.utils.external.alipay;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import com.letv.base.R;
import java.io.IOException;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONObject;

public class BaseHelper {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String convertStreamToString(java.io.InputStream r5) {
        /*
        r2 = new java.io.BufferedReader;
        r4 = new java.io.InputStreamReader;
        r4.<init>(r5);
        r2.<init>(r4);
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r1 = 0;
    L_0x0010:
        r1 = r2.readLine();	 Catch:{ IOException -> 0x001a }
        if (r1 == 0) goto L_0x0026;
    L_0x0016:
        r3.append(r1);	 Catch:{ IOException -> 0x001a }
        goto L_0x0010;
    L_0x001a:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0034 }
        r5.close();	 Catch:{ IOException -> 0x002f }
    L_0x0021:
        r4 = r3.toString();
        return r4;
    L_0x0026:
        r5.close();	 Catch:{ IOException -> 0x002a }
        goto L_0x0021;
    L_0x002a:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0021;
    L_0x002f:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0021;
    L_0x0034:
        r4 = move-exception;
        r5.close();	 Catch:{ IOException -> 0x0039 }
    L_0x0038:
        throw r4;
    L_0x0039:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.core.utils.external.alipay.BaseHelper.convertStreamToString(java.io.InputStream):java.lang.String");
    }

    public static void showDialog(Activity context, String strTitle, String strText, int icon) {
        if (context != null && !context.isFinishing() && !context.isRestricted()) {
            Builder tDialog = new Builder(context);
            tDialog.setIcon(icon);
            tDialog.setTitle(strTitle);
            tDialog.setMessage(strText);
            tDialog.setPositiveButton(R.string.Ensure, null);
            try {
                tDialog.show();
            } catch (Exception e) {
            }
        }
    }

    public static void log(String tag, String info) {
    }

    public static void chmod(String permission, String path) {
        try {
            Runtime.getRuntime().exec("chmod " + permission + " " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ProgressDialog showProgress(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
        }
        return dialog;
    }

    public static JSONObject string2JSON(String str, String split) {
        JSONObject json = new JSONObject();
        try {
            String[] arrStr = str.split(split);
            for (int i = 0; i < arrStr.length; i++) {
                String[] arrKeyValue = arrStr[i].split(SearchCriteria.EQ);
                json.put(arrKeyValue[0], arrStr[i].substring(arrKeyValue[0].length() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
