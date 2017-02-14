package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.data.c;
import com.alipay.sdk.data.d;
import com.alipay.sdk.data.e;
import com.alipay.sdk.exception.NetErrorException;
import com.alipay.sdk.protocol.a;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.k;
import com.letv.lemallsdk.util.Constants;
import org.json.JSONObject;

public class AuthTask {
    static final Object a = h.class;
    private static final int b = 73;
    private Activity c;

    public AuthTask(Activity activity) {
        this.c = activity;
    }

    private String a(Activity activity, String str) {
        if (!a((Context) activity)) {
            return b(activity, str);
        }
        Object a = new h(activity).a(str);
        return TextUtils.equals(a, Constants.CALLBACK_FAILD) ? b(activity, str) : TextUtils.isEmpty(a) ? l.a() : a;
    }

    private String a(a aVar) {
        String[] a = com.alipay.sdk.util.a.a(aVar.e());
        Bundle bundle = new Bundle();
        bundle.putString("url", a[0]);
        Intent intent = new Intent(this.c, H5AuthActivity.class);
        intent.putExtras(bundle);
        this.c.startActivity(intent);
        synchronized (a) {
            try {
                a.wait();
            } catch (InterruptedException e) {
                return l.a();
            }
        }
        Object obj = l.a;
        return TextUtils.isEmpty(obj) ? l.a() : obj;
    }

    private static boolean a(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(k.b, 128);
            return packageInfo != null && packageInfo.versionCode >= b;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    private String b(Activity activity, String str) {
        com.alipay.sdk.widget.a aVar;
        e a;
        int i;
        m mVar;
        if (activity != null) {
            try {
                if (!activity.isFinishing()) {
                    aVar = new com.alipay.sdk.widget.a(activity);
                    aVar.b();
                    b.a().a(this.c, d.a());
                    a = com.alipay.sdk.data.b.a(new c(), str.toString(), new JSONObject());
                    a.a.b = "com.alipay.mobilecashier";
                    a.a.e = "com.alipay.mcpay";
                    a.a.d = "4.0.3";
                    a.a.c = "/cashier/main";
                    com.alipay.sdk.protocol.c a2 = new com.alipay.sdk.net.d(new c()).a(activity, a, false);
                    if (aVar != null) {
                        aVar.c();
                        Object obj = null;
                    }
                    for (a aVar2 : a.a(com.alipay.sdk.protocol.b.a(a2.c.optJSONObject("form"), "onload"))) {
                        if (aVar2 == a.a) {
                            return a(aVar2);
                        }
                    }
                    mVar = null;
                    if (mVar == null) {
                        mVar = m.a(m.FAILED.a());
                    }
                    return l.a(mVar.a(), mVar.b(), "");
                }
            } catch (Exception e) {
                aVar = null;
            }
        }
        aVar = null;
        b.a().a(this.c, d.a());
        a = com.alipay.sdk.data.b.a(new c(), str.toString(), new JSONObject());
        a.a.b = "com.alipay.mobilecashier";
        a.a.e = "com.alipay.mcpay";
        a.a.d = "4.0.3";
        a.a.c = "/cashier/main";
        try {
            com.alipay.sdk.protocol.c a22 = new com.alipay.sdk.net.d(new c()).a(activity, a, false);
            if (aVar != null) {
                aVar.c();
                Object obj2 = null;
            }
            for (i = 0; i < r4; i++) {
                if (aVar2 == a.a) {
                    return a(aVar2);
                }
            }
            mVar = null;
        } catch (NetErrorException e2) {
            r1 = m.a(m.NETWORK_ERROR.a());
            m a3;
            if (aVar != null) {
                aVar.c();
                mVar = a3;
            } else {
                mVar = a3;
            }
        } catch (Exception e3) {
            if (aVar != null) {
                aVar.c();
                mVar = null;
            } else {
                mVar = null;
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            r1 = aVar;
            Throwable th3 = th2;
            com.alipay.sdk.widget.a aVar3;
            if (aVar3 != null) {
                aVar3.c();
            }
        }
        if (mVar == null) {
            mVar = m.a(m.FAILED.a());
        }
        return l.a(mVar.a(), mVar.b(), "");
    }

    public synchronized String auth(String str) {
        String a;
        if (!str.contains("bizcontext=")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("&bizcontext=\"");
            stringBuilder.append(new com.alipay.sdk.sys.a(this.c).toString());
            stringBuilder.append("\"");
            str = stringBuilder.toString();
        }
        Context context = this.c;
        if (a(context)) {
            a = new h(context).a(str);
            if (!TextUtils.equals(a, Constants.CALLBACK_FAILD)) {
                if (TextUtils.isEmpty(a)) {
                    a = l.a();
                }
            }
        }
        a = b(context, str);
        return a;
    }
}
