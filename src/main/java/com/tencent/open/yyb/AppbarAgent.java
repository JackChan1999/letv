package com.tencent.open.yyb;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.facebook.internal.NativeProtocol;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.yyb.a.a;
import java.util.regex.Pattern;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;

/* compiled from: ProGuard */
public class AppbarAgent extends BaseApi {
    public static final String TO_APPBAR_DETAIL = "siteIndex";
    public static final String TO_APPBAR_NEWS = "myMessage";
    public static final String TO_APPBAR_SEND_BLOG = "newThread";
    public static final String wx_appid = "wx8e8dc60535c9cd93";
    private Bundle a;
    private String b;

    public AppbarAgent(QQToken qQToken) {
        super(qQToken);
    }

    public void startAppbarLabel(Activity activity, String str) {
        if (TextUtils.isEmpty(str)) {
            Toast.makeText(activity, Constants.MSG_PARAM_ERROR, 0).show();
            return;
        }
        this.a = new Bundle();
        this.a.putString(NativeProtocol.WEB_DIALOG_PARAMS, "label/" + str);
        startAppbar(activity, "sId");
    }

    public void startAppbarThread(Activity activity, String str) {
        if (d(str)) {
            this.b = str;
            startAppbar(activity, "toThread");
            return;
        }
        Toast.makeText(activity, Constants.MSG_PARAM_ERROR, 0).show();
    }

    public void startAppbar(Activity activity, String str) {
        if (a(str)) {
            String c = c(str);
            Object b = b();
            if (TextUtils.isEmpty(b) || SystemUtils.compareVersion(b, "4.2") < 0) {
                a(activity, c);
                return;
            }
            String str2 = c + a();
            f.b("AppbarAgent", "-->(AppbarAgent)startAppbar : yybUrl = " + str2);
            try {
                Intent intent = new Intent();
                intent.setClassName("com.tencent.android.qqdownloader", "com.tencent.assistant.activity.ExportBrowserActivity");
                intent.putExtra("com.tencent.assistant.BROWSER_URL", str2);
                activity.startActivity(intent);
                activity.overridePendingTransition(17432576, 17432577);
                return;
            } catch (Exception e) {
                f.b("AppbarAgent", "-->(AppbarAgent)startAppbar : ExportBrowserActivity not found, start H5");
                a(activity, c);
                return;
            }
        }
        Toast.makeText(activity, Constants.MSG_PARAM_ERROR, 0).show();
    }

    private boolean a(String str) {
        return TO_APPBAR_DETAIL.equals(str) || TO_APPBAR_NEWS.equals(str) || TO_APPBAR_SEND_BLOG.equals(str) || "sId".equals(str) || "toThread".equals(str);
    }

    private void a(Activity activity, String str) {
        if (this.mToken != null) {
            Intent intent = new Intent(activity, AppbarActivity.class);
            intent.putExtra("appid", this.mToken.getAppId());
            if (!(this.mToken.getAccessToken() == null || this.mToken.getOpenId() == null)) {
                a aVar = new a();
                aVar.b = this.mToken.getAccessToken();
                aVar.c = Long.parseLong(this.mToken.getAppId());
                aVar.a = this.mToken.getOpenId();
                a.a(activity, str, this.mToken.getOpenId(), this.mToken.getAccessToken(), this.mToken.getAppId());
            }
            intent.putExtra("url", str);
            f.b("AppbarAgent", "-->(AppbarAgent)startAppbar H5 : url = " + str);
            try {
                activity.startActivityForResult(intent, Constants.REQUEST_APPBAR);
            } catch (Exception e) {
                f.b("AppbarAgent", "-->(AppbarAgent)startAppbar : activity not found, start H5");
            }
        }
    }

    private Bundle b(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("pkgName", Global.getContext().getPackageName());
        if (!(TO_APPBAR_DETAIL.equals(str) || TO_APPBAR_SEND_BLOG.equals(str))) {
            if (TO_APPBAR_NEWS.equals(str)) {
                bundle.putString("source", "myapp");
            } else if ("sId".equals(str)) {
                if (this.a != null) {
                    bundle.putAll(this.a);
                }
            } else if ("toThread".equals(str)) {
                str = String.format("sId/t/%s", new Object[]{this.b});
            }
        }
        bundle.putString("route", str);
        return bundle;
    }

    private String c(String str) {
        StringBuilder stringBuilder = new StringBuilder("http://m.wsq.qq.com/direct?");
        stringBuilder.append(a(b(str)));
        return stringBuilder.toString();
    }

    private String a() {
        Bundle bundle = new Bundle();
        if (!(this.mToken == null || this.mToken.getAppId() == null || this.mToken.getAccessToken() == null || this.mToken.getOpenId() == null)) {
            bundle.putString("qOpenAppId", this.mToken.getAppId());
            bundle.putString("qOpenId", this.mToken.getOpenId());
            bundle.putString("qAccessToken", this.mToken.getAccessToken());
        }
        bundle.putString("qPackageName", Global.getContext().getPackageName());
        return "&" + a(bundle);
    }

    private String a(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : bundle.keySet()) {
            stringBuilder.append(str).append(SearchCriteria.EQ).append(bundle.get(str)).append("&");
        }
        String str2 = stringBuilder.toString();
        if (str2.endsWith("&")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        f.b("AppbarAgent", "-->encodeParams, result: " + str2);
        return str2;
    }

    private String b() {
        try {
            PackageInfo packageInfo = Global.getContext().getPackageManager().getPackageInfo("com.tencent.android.qqdownloader", 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean d(String str) {
        return !TextUtils.isEmpty(str) && Pattern.matches("^[1-9][0-9]*$", str);
    }
}
