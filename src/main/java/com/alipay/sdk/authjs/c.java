package com.alipay.sdk.authjs;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.authjs.a.a;
import com.facebook.internal.NativeProtocol;
import com.letv.download.db.Download.DownloadVideoTable;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.util.Timer;
import org.json.JSONException;
import org.json.JSONObject;

public final class c {
    b a;
    Context b;

    public c(Context context, b bVar) {
        this.b = context;
        this.a = bVar;
    }

    private static /* synthetic */ a a(c cVar, a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            CharSequence optString = jSONObject.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
            int optInt = jSONObject.optInt(DownloadVideoTable.COLUMN_DURATION);
            int i = 1;
            if (optInt < 2500) {
                i = 0;
            }
            Toast.makeText(cVar.b, optString, i).show();
            new Timer().schedule(new e(cVar, aVar), (long) i);
        }
        return a.NONE_ERROR;
    }

    private static void a(Runnable runnable) {
        if (runnable != null) {
            if ((Looper.getMainLooper() == Looper.myLooper() ? 1 : null) != null) {
                runnable.run();
            } else {
                new Handler(Looper.getMainLooper()).post(runnable);
            }
        }
    }

    private void a(String str) {
        String str2;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(a.e);
            try {
                if (!TextUtils.isEmpty(string)) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject(a.f);
                    jSONObject2 = jSONObject2 instanceof JSONObject ? jSONObject2 : null;
                    String string2 = jSONObject.getString(a.g);
                    String string3 = jSONObject.getString(a.d);
                    a aVar = new a("call");
                    aVar.j = string3;
                    aVar.k = string2;
                    aVar.m = jSONObject2;
                    aVar.i = string;
                    a(aVar);
                }
            } catch (Exception e) {
                str2 = string;
                if (!TextUtils.isEmpty(str2)) {
                    try {
                        a(str2, a.RUNTIME_ERROR);
                    } catch (JSONException e2) {
                    }
                }
            }
        } catch (Exception e3) {
            str2 = null;
            if (!TextUtils.isEmpty(str2)) {
                a(str2, a.RUNTIME_ERROR);
            }
        }
    }

    private a b(a aVar) {
        if (aVar != null && "toast".equals(aVar.k)) {
            JSONObject jSONObject = aVar.m;
            CharSequence optString = jSONObject.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
            int optInt = jSONObject.optInt(DownloadVideoTable.COLUMN_DURATION);
            int i = 1;
            if (optInt < 2500) {
                i = 0;
            }
            Toast.makeText(this.b, optString, i).show();
            new Timer().schedule(new e(this, aVar), (long) i);
        }
        return a.NONE_ERROR;
    }

    private void c(a aVar) {
        JSONObject jSONObject = aVar.m;
        CharSequence optString = jSONObject.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
        int optInt = jSONObject.optInt(DownloadVideoTable.COLUMN_DURATION);
        int i = 1;
        if (optInt < 2500) {
            i = 0;
        }
        Toast.makeText(this.b, optString, i).show();
        new Timer().schedule(new e(this, aVar), (long) i);
    }

    public final void a(a aVar) throws JSONException {
        if (aVar != null) {
            if (TextUtils.isEmpty(aVar.k)) {
                a(aVar.i, a.INVALID_PARAMETER);
                return;
            }
            d dVar = new d(this, aVar);
            if ((Looper.getMainLooper() == Looper.myLooper() ? 1 : null) != null) {
                dVar.run();
            } else {
                new Handler(Looper.getMainLooper()).post(dVar);
            }
        }
    }

    public final void a(String str, a aVar) throws JSONException {
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, aVar.ordinal());
            a aVar2 = new a(a.c);
            aVar2.m = jSONObject;
            aVar2.i = str;
            this.a.a(aVar2);
        }
    }
}
