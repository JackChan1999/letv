package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.data.c;
import com.alipay.sdk.data.d;
import com.alipay.sdk.exception.NetErrorException;
import com.alipay.sdk.sys.b;
import com.alipay.sdk.util.h;
import com.alipay.sdk.util.k;
import com.alipay.sdk.widget.a;
import com.letv.lemallsdk.util.Constants;
import org.json.JSONObject;

public class PayTask {
    static final Object a = h.class;
    private Activity b;
    private a c = null;

    public PayTask(Activity activity) {
        this.b = activity;
    }

    private String a(com.alipay.sdk.protocol.a aVar) {
        String[] a = com.alipay.sdk.util.a.a(aVar.e());
        Intent intent = new Intent(this.b, H5PayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("url", a[0]);
        if (a.length == 2) {
            bundle.putString("cookie", a[1]);
        }
        intent.putExtras(bundle);
        this.b.startActivity(intent);
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

    private String a(String str) {
        return new h(this.b).a(str);
    }

    private String b(String str) {
        try {
            if (!(this.b == null || this.b.isFinishing())) {
                this.c = new a(this.b);
                this.c.b();
                b.a().a(this.b, d.a());
            }
        } catch (Exception e) {
            this.c = null;
        }
        return c(str);
    }

    private String c(String str) {
        com.alipay.sdk.tid.a aVar;
        m mVar;
        int i = 0;
        try {
            com.alipay.sdk.protocol.a[] a = com.alipay.sdk.protocol.a.a(com.alipay.sdk.protocol.b.a(new com.alipay.sdk.net.d(new c()).a(this.b, com.alipay.sdk.data.b.a(new c(), str, new JSONObject()), false).c.optJSONObject("form"), "onload"));
            for (com.alipay.sdk.protocol.a aVar2 : a) {
                if (aVar2 == com.alipay.sdk.protocol.a.f) {
                    String[] a2 = com.alipay.sdk.util.a.a(aVar2.e());
                    if (a2.length == 3 && TextUtils.equals("tid", a2[0])) {
                        Context context = b.a().a;
                        com.alipay.sdk.tid.b a3 = com.alipay.sdk.tid.b.a();
                        if (!(TextUtils.isEmpty(a2[1]) || TextUtils.isEmpty(a2[2]))) {
                            a3.a = a2[1];
                            a3.b = a2[2];
                            aVar = new com.alipay.sdk.tid.a(context);
                            aVar.a(com.alipay.sdk.util.b.a(context).a(), com.alipay.sdk.util.b.a(context).b(), a3.a, a3.b);
                            aVar.close();
                        }
                    }
                }
            }
            if (this.c != null) {
                this.c.c();
            }
            int length = a.length;
            while (i < length) {
                com.alipay.sdk.protocol.a aVar3 = a[i];
                if (aVar3 == com.alipay.sdk.protocol.a.a) {
                    String a4 = a(aVar3);
                    if (this.c == null) {
                        return a4;
                    }
                    this.c.c();
                    return a4;
                }
                i++;
            }
            if (this.c != null) {
                this.c.c();
                mVar = null;
                if (mVar == null) {
                    mVar = m.a(m.FAILED.a());
                }
                return l.a(mVar.a(), mVar.b(), "");
            }
        } catch (Exception e) {
            aVar.close();
        } catch (NetErrorException e2) {
            mVar = m.a(m.NETWORK_ERROR.a());
            if (this.c != null) {
                this.c.c();
            }
        } catch (Exception e3) {
            if (this.c != null) {
                this.c.c();
                mVar = null;
            }
        } catch (Throwable th) {
            if (this.c != null) {
                this.c.c();
            }
        }
        mVar = null;
        if (mVar == null) {
            mVar = m.a(m.FAILED.a());
        }
        return l.a(mVar.a(), mVar.b(), "");
    }

    public boolean checkAccountIfExist() {
        boolean z = false;
        try {
            z = new com.alipay.sdk.net.d().a(this.b, com.alipay.sdk.data.b.a(), true).c.optBoolean("hasAccount", false);
        } catch (Exception e) {
        }
        return z;
    }

    public String getVersion() {
        return "15.0.0";
    }

    public synchronized String pay(String str) {
        String b;
        if (!str.contains("bizcontext=")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("&bizcontext=\"");
            stringBuilder.append(new com.alipay.sdk.sys.a(this.b).toString());
            stringBuilder.append("\"");
            str = stringBuilder.toString();
        }
        if (str.contains("paymethod=\"expressGateway\"")) {
            b = b(str);
        } else if (k.b(this.b)) {
            b = new h(this.b).a(str);
            if (TextUtils.equals(b, Constants.CALLBACK_FAILD)) {
                b = b(str);
            } else if (TextUtils.isEmpty(b)) {
                b = l.a();
            }
        } else {
            b = b(str);
        }
        return b;
    }
}
