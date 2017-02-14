package com.tencent.connect.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.lemallsdk.util.Constants;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.connect.a.a;
import com.tencent.connect.common.AssistActivity;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.ApiTask;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.ThreadManager;
import com.tencent.open.utils.Util;
import com.tencent.open.yyb.TitleBar;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthAgent extends BaseApi {
    public static final String SECURE_LIB_FILE_NAME = "libwbsafeedit";
    public static final String SECURE_LIB_NAME = "libwbsafeedit.so";
    private IUiListener a;
    private String b;
    private Activity c;

    /* compiled from: ProGuard */
    private class CheckLoginListener implements IUiListener {
        IUiListener a;
        final /* synthetic */ AuthAgent b;

        public CheckLoginListener(AuthAgent authAgent, IUiListener iUiListener) {
            this.b = authAgent;
            this.a = iUiListener;
        }

        public void onComplete(Object obj) {
            if (obj == null) {
                f.e("CheckLoginListener", "response data is null");
                return;
            }
            JSONObject jSONObject = (JSONObject) obj;
            try {
                int i = jSONObject.getInt("ret");
                Object string = i == 0 ? Constants.CALLBACK_SUCCESS : jSONObject.getString("msg");
                if (this.a != null) {
                    this.a.onComplete(new JSONObject().put("ret", i).put("msg", string));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                f.e("CheckLoginListener", "response data format error");
            }
        }

        public void onError(UiError uiError) {
            if (this.a != null) {
                this.a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.a != null) {
                this.a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    private class FeedConfirmListener implements IUiListener {
        IUiListener a;
        final /* synthetic */ AuthAgent b;
        private final String c = "sendinstall";
        private final String d = "installwording";
        private final String e = "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi";

        /* compiled from: ProGuard */
        private abstract class ButtonListener implements OnClickListener {
            Dialog d;
            final /* synthetic */ FeedConfirmListener e;

            ButtonListener(FeedConfirmListener feedConfirmListener, Dialog dialog) {
                this.e = feedConfirmListener;
                this.d = dialog;
            }
        }

        public FeedConfirmListener(AuthAgent authAgent, IUiListener iUiListener) {
            this.b = authAgent;
            this.a = iUiListener;
        }

        public void onComplete(Object obj) {
            Object obj2 = null;
            if (obj != null) {
                JSONObject jSONObject = (JSONObject) obj;
                if (jSONObject != null) {
                    String string;
                    Object obj3;
                    Object obj4;
                    String str = "";
                    try {
                        if (jSONObject.getInt("sendinstall") == 1) {
                            obj2 = 1;
                        }
                        string = jSONObject.getString("installwording");
                        obj3 = obj2;
                    } catch (JSONException e) {
                        obj4 = null;
                        f.d("FeedConfirm", "There is no value for sendinstall.");
                        String str2 = str;
                        obj3 = obj4;
                        string = str2;
                    }
                    obj4 = URLDecoder.decode(string);
                    f.b("TAG", " WORDING = " + obj4 + "xx");
                    if (obj3 != null && !TextUtils.isEmpty(obj4)) {
                        a(obj4, this.a, obj);
                    } else if (this.a != null) {
                        this.a.onComplete(obj);
                    }
                }
            }
        }

        private void a(String str, final IUiListener iUiListener, final Object obj) {
            PackageInfo packageInfo;
            Drawable drawable = null;
            Dialog dialog = new Dialog(this.b.c);
            dialog.requestWindowFeature(1);
            PackageManager packageManager = this.b.c.getPackageManager();
            try {
                packageInfo = packageManager.getPackageInfo(this.b.c.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
                packageInfo = null;
            }
            if (packageInfo != null) {
                drawable = packageInfo.applicationInfo.loadIcon(packageManager);
            }
            OnClickListener anonymousClass1 = new ButtonListener(this, dialog) {
                final /* synthetic */ FeedConfirmListener c;

                public void onClick(View view) {
                    this.c.a();
                    if (this.d != null && this.d.isShowing()) {
                        this.d.dismiss();
                    }
                    if (iUiListener != null) {
                        iUiListener.onComplete(obj);
                    }
                }
            };
            OnClickListener anonymousClass2 = new ButtonListener(this, dialog) {
                final /* synthetic */ FeedConfirmListener c;

                public void onClick(View view) {
                    if (this.d != null && this.d.isShowing()) {
                        this.d.dismiss();
                    }
                    if (iUiListener != null) {
                        iUiListener.onComplete(obj);
                    }
                }
            };
            Drawable colorDrawable = new ColorDrawable();
            colorDrawable.setAlpha(0);
            dialog.getWindow().setBackgroundDrawable(colorDrawable);
            dialog.setContentView(a(this.b.c, drawable, str, anonymousClass1, anonymousClass2));
            dialog.setOnCancelListener(new OnCancelListener(this) {
                final /* synthetic */ FeedConfirmListener c;

                public void onCancel(DialogInterface dialogInterface) {
                    if (iUiListener != null) {
                        iUiListener.onComplete(obj);
                    }
                }
            });
            if (this.b.c != null && !this.b.c.isFinishing()) {
                dialog.show();
            }
        }

        private Drawable a(String str, Context context) {
            Drawable createFromStream;
            IOException e;
            try {
                InputStream open = context.getApplicationContext().getAssets().open(str);
                if (open == null) {
                    return null;
                }
                if (str.endsWith(".9.png")) {
                    Bitmap decodeStream = BitmapFactory.decodeStream(open);
                    if (decodeStream == null) {
                        return null;
                    }
                    byte[] ninePatchChunk = decodeStream.getNinePatchChunk();
                    NinePatch.isNinePatchChunk(ninePatchChunk);
                    return new NinePatchDrawable(decodeStream, ninePatchChunk, new Rect(), null);
                }
                createFromStream = Drawable.createFromStream(open, str);
                try {
                    open.close();
                    return createFromStream;
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    return createFromStream;
                }
            } catch (IOException e3) {
                IOException iOException = e3;
                createFromStream = null;
                e = iOException;
                e.printStackTrace();
                return createFromStream;
            }
        }

        private View a(Context context, Drawable drawable, String str, OnClickListener onClickListener, OnClickListener onClickListener2) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            View relativeLayout = new RelativeLayout(context);
            View imageView = new ImageView(context);
            imageView.setImageDrawable(drawable);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setId(1);
            int i = (int) (14.0f * f);
            i = (int) (18.0f * f);
            int i2 = (int) (6.0f * f);
            int i3 = (int) (18.0f * f);
            LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (60.0f * f), (int) (60.0f * f));
            layoutParams.addRule(9);
            layoutParams.setMargins(0, i, i2, i3);
            relativeLayout.addView(imageView, layoutParams);
            imageView = new TextView(context);
            imageView.setText(str);
            imageView.setTextSize(14.0f);
            imageView.setGravity(3);
            imageView.setIncludeFontPadding(false);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setLines(2);
            imageView.setId(5);
            imageView.setMinWidth((int) (185.0f * f));
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(1, 1);
            layoutParams2.addRule(6, 1);
            int i4 = (int) (TitleBar.SHAREBTN_RIGHT_MARGIN * f);
            layoutParams2.setMargins(0, 0, (int) (5.0f * f), 0);
            relativeLayout.addView(imageView, layoutParams2);
            imageView = new View(context);
            imageView.setBackgroundColor(Color.rgb(214, 214, 214));
            imageView.setId(3);
            layoutParams2 = new RelativeLayout.LayoutParams(-2, 2);
            layoutParams2.addRule(3, 1);
            layoutParams2.addRule(5, 1);
            layoutParams2.addRule(7, 5);
            layoutParams2.setMargins(0, 0, 0, (int) (12.0f * f));
            relativeLayout.addView(imageView, layoutParams2);
            imageView = new LinearLayout(context);
            layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams2.addRule(5, 1);
            layoutParams2.addRule(7, 5);
            layoutParams2.addRule(3, 3);
            View button = new Button(context);
            button.setText("跳过");
            button.setBackgroundDrawable(a("buttonNegt.png", context));
            button.setTextColor(Color.rgb(36, 97, 131));
            button.setTextSize(TitleBar.BACKBTN_LEFT_MARGIN);
            button.setOnClickListener(onClickListener2);
            button.setId(4);
            LayoutParams layoutParams3 = new LinearLayout.LayoutParams(0, (int) (45.0f * f));
            layoutParams3.rightMargin = (int) (14.0f * f);
            layoutParams3.leftMargin = (int) (4.0f * f);
            layoutParams3.weight = 1.0f;
            imageView.addView(button, layoutParams3);
            button = new Button(context);
            button.setText("确定");
            button.setTextSize(TitleBar.BACKBTN_LEFT_MARGIN);
            button.setTextColor(Color.rgb(255, 255, 255));
            button.setBackgroundDrawable(a("buttonPost.png", context));
            button.setOnClickListener(onClickListener);
            layoutParams3 = new LinearLayout.LayoutParams(0, (int) (45.0f * f));
            layoutParams3.weight = 1.0f;
            layoutParams3.rightMargin = (int) (4.0f * f);
            imageView.addView(button, layoutParams3);
            relativeLayout.addView(imageView, layoutParams2);
            LayoutParams layoutParams4 = new FrameLayout.LayoutParams((int) (279.0f * f), (int) (163.0f * f));
            relativeLayout.setPadding((int) (14.0f * f), 0, (int) (12.0f * f), (int) (12.0f * f));
            relativeLayout.setLayoutParams(layoutParams4);
            relativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
            Drawable paintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
            paintDrawable.setCornerRadius(f * 5.0f);
            relativeLayout.setBackgroundDrawable(paintDrawable);
            return relativeLayout;
        }

        protected void a() {
            HttpUtils.requestAsync(this.b.mToken, this.b.c, "http://appsupport.qq.com/cgi-bin/qzapps/mapp_addapp.cgi", this.b.composeActivityParams(), "POST", null);
        }

        public void onError(UiError uiError) {
            if (this.a != null) {
                this.a.onError(uiError);
            }
        }

        public void onCancel() {
            if (this.a != null) {
                this.a.onCancel();
            }
        }
    }

    /* compiled from: ProGuard */
    private class TokenListener implements IUiListener {
        final /* synthetic */ AuthAgent a;
        private final IUiListener b;
        private final boolean c;
        private final Context d;

        public TokenListener(AuthAgent authAgent, Context context, IUiListener iUiListener, boolean z, boolean z2) {
            this.a = authAgent;
            this.d = context;
            this.b = iUiListener;
            this.c = z;
            f.b(f.d, "OpenUi, TokenListener()");
        }

        public void onComplete(Object obj) {
            f.b(f.d, "OpenUi, TokenListener() onComplete");
            JSONObject jSONObject = (JSONObject) obj;
            try {
                String string = jSONObject.getString("access_token");
                String string2 = jSONObject.getString("expires_in");
                String string3 = jSONObject.getString("openid");
                if (!(string == null || this.a.mToken == null || string3 == null)) {
                    this.a.mToken.setAccessToken(string, string2);
                    this.a.mToken.setOpenId(string3);
                    a.d(this.d, this.a.mToken);
                }
                string = jSONObject.getString(com.tencent.connect.common.Constants.PARAM_PLATFORM_ID);
                if (string != null) {
                    try {
                        this.d.getSharedPreferences(com.tencent.connect.common.Constants.PREFERENCE_PF, 0).edit().putString(com.tencent.connect.common.Constants.PARAM_PLATFORM_ID, string).commit();
                    } catch (Throwable e) {
                        e.printStackTrace();
                        f.b(f.d, "OpenUi, TokenListener() onComplete error", e);
                    }
                }
                if (this.c) {
                    CookieSyncManager.getInstance().sync();
                }
            } catch (Throwable e2) {
                e2.printStackTrace();
                f.b(f.d, "OpenUi, TokenListener() onComplete error", e2);
            }
            this.b.onComplete(jSONObject);
            this.a.releaseResource();
            f.b();
        }

        public void onError(UiError uiError) {
            f.b(f.d, "OpenUi, TokenListener() onError");
            this.b.onError(uiError);
            f.b();
        }

        public void onCancel() {
            f.b(f.d, "OpenUi, TokenListener() onCancel");
            this.b.onCancel();
            f.b();
        }
    }

    public AuthAgent(QQToken qQToken) {
        super(qQToken);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener) {
        return doLogin(activity, str, iUiListener, false, null);
    }

    public int doLogin(Activity activity, String str, IUiListener iUiListener, boolean z, Fragment fragment) {
        this.b = str;
        this.c = activity;
        this.a = iUiListener;
        if (a(activity, fragment, z)) {
            f.c(f.d, "OpenUi, showUi, return Constants.UI_ACTIVITY");
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", "1", "5", "0", "0", "0");
            return 1;
        }
        d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), "2", "1", "5", "1", "0", "0");
        f.d(f.d, "startActivity fail show dialog.");
        this.a = new FeedConfirmListener(this, this.a);
        return a(z, this.a);
    }

    public void releaseResource() {
        this.c = null;
        this.a = null;
    }

    private int a(boolean z, IUiListener iUiListener) {
        f.c(f.d, "OpenUi, showDialog -- start");
        CookieSyncManager.createInstance(Global.getContext());
        Bundle composeCGIParams = composeCGIParams();
        if (z) {
            composeCGIParams.putString("isadd", "1");
        }
        composeCGIParams.putString("scope", this.b);
        composeCGIParams.putString("client_id", this.mToken.getAppId());
        if (isOEM) {
            composeCGIParams.putString(com.tencent.connect.common.Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + NetworkUtils.DELIMITER_LINE + AbstractSpiCall.ANDROID_CLIENT_TYPE + NetworkUtils.DELIMITER_LINE + registerChannel + NetworkUtils.DELIMITER_LINE + businessId);
        } else {
            composeCGIParams.putString(com.tencent.connect.common.Constants.PARAM_PLATFORM_ID, com.tencent.connect.common.Constants.DEFAULT_PF);
        }
        String str = (System.currentTimeMillis() / 1000) + "";
        composeCGIParams.putString(TradeInfo.SIGN, SystemUtils.getAppSignatureMD5(Global.getContext(), str));
        composeCGIParams.putString("time", str);
        composeCGIParams.putString("display", "mobile");
        composeCGIParams.putString("response_type", UserInfoDb.TOKEN);
        composeCGIParams.putString("redirect_uri", ServerSetting.DEFAULT_REDIRECT_URI);
        composeCGIParams.putString("cancel_display", "1");
        composeCGIParams.putString("switch", "1");
        composeCGIParams.putString("status_userip", Util.getUserIp());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_CGI_AUTHORIZE));
        stringBuilder.append(Util.encodeUrl(composeCGIParams));
        final String stringBuilder2 = stringBuilder.toString();
        final IUiListener tokenListener = new TokenListener(this, Global.getContext(), iUiListener, true, false);
        f.b(f.d, "OpenUi, showDialog TDialog");
        ThreadManager.executeOnSubThread(new Runnable(this) {
            final /* synthetic */ AuthAgent c;

            public void run() {
                SystemUtils.extractSecureLib(AuthAgent.SECURE_LIB_FILE_NAME, AuthAgent.SECURE_LIB_NAME, 2);
                this.c.c.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        new AuthDialog(this.a.c.c, SystemUtils.ACTION_LOGIN, stringBuilder2, tokenListener, this.a.c.mToken).show();
                    }
                });
            }
        });
        f.c(f.d, "OpenUi, showDialog -- end");
        return 2;
    }

    private boolean a(Activity activity, Fragment fragment, boolean z) {
        f.c(f.d, "startActionActivity() -- start");
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        if (targetActivityIntent != null) {
            Bundle composeCGIParams = composeCGIParams();
            if (z) {
                composeCGIParams.putString("isadd", "1");
            }
            composeCGIParams.putString("scope", this.b);
            composeCGIParams.putString("client_id", this.mToken.getAppId());
            if (isOEM) {
                composeCGIParams.putString(com.tencent.connect.common.Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + NetworkUtils.DELIMITER_LINE + AbstractSpiCall.ANDROID_CLIENT_TYPE + NetworkUtils.DELIMITER_LINE + registerChannel + NetworkUtils.DELIMITER_LINE + businessId);
            } else {
                composeCGIParams.putString(com.tencent.connect.common.Constants.PARAM_PLATFORM_ID, com.tencent.connect.common.Constants.DEFAULT_PF);
            }
            composeCGIParams.putString("need_pay", "1");
            composeCGIParams.putString(com.tencent.connect.common.Constants.KEY_APP_NAME, SystemUtils.getAppName(Global.getContext()));
            targetActivityIntent.putExtra(com.tencent.connect.common.Constants.KEY_ACTION, SystemUtils.ACTION_LOGIN);
            targetActivityIntent.putExtra(com.tencent.connect.common.Constants.KEY_PARAMS, composeCGIParams);
            this.mActivityIntent = targetActivityIntent;
            if (hasActivityForIntent()) {
                this.a = new FeedConfirmListener(this, this.a);
                if (fragment != null) {
                    f.b("AuthAgent", "startAssitActivity fragment");
                    startAssitActivity(fragment, this.a);
                } else {
                    f.b("AuthAgent", "startAssitActivity activity");
                    startAssitActivity(activity, this.a);
                }
                f.c(f.d, "startActionActivity() -- end");
                d.a().a(0, "LOGIN_CHECK_SDK", com.tencent.connect.common.Constants.DEFAULT_UIN, this.mToken.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
                return true;
            }
        }
        d.a().a(1, "LOGIN_CHECK_SDK", com.tencent.connect.common.Constants.DEFAULT_UIN, this.mToken.getAppId(), "", Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "startActionActivity fail");
        f.c(f.d, "startActionActivity() -- end");
        return false;
    }

    protected void a(IUiListener iUiListener) {
        f.c(f.d, "reportDAU() -- start");
        String str = "tencent&sdk&qazxc***14969%%";
        String str2 = "qzone3.4";
        Object accessToken = this.mToken.getAccessToken();
        Object openId = this.mToken.getOpenId();
        Object appId = this.mToken.getAppId();
        Object obj = "";
        if (!(TextUtils.isEmpty(accessToken) || TextUtils.isEmpty(openId) || TextUtils.isEmpty(appId))) {
            obj = Util.encrypt(str + accessToken + appId + openId + str2);
        }
        if (TextUtils.isEmpty(obj)) {
            f.e(f.d, "reportDAU -- encrytoken is null");
            return;
        }
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("encrytoken", obj);
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://openmobile.qq.com/user/user_login_statis", composeCGIParams, "POST", null);
        f.c(f.d, "reportDAU() -- end");
    }

    protected void b(IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        composeCGIParams.putString("reqType", "checkLogin");
        IRequestListener tempRequestListener = new TempRequestListener(new CheckLoginListener(this, iUiListener));
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "https://openmobile.qq.com/v3/user/get_info", composeCGIParams, "GET", tempRequestListener);
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        IUiListener iUiListener;
        ThreadManager.executeOnSubThread(new Runnable(this) {
            final /* synthetic */ AuthAgent a;

            {
                this.a = r1;
            }

            public void run() {
                Global.saveVersionCode();
            }
        });
        for (ApiTask apiTask : this.mTaskList) {
            if (apiTask.mRequestCode == i) {
                IUiListener iUiListener2 = apiTask.mListener;
                this.mTaskList.remove(apiTask);
                iUiListener = iUiListener2;
                break;
            }
        }
        iUiListener = null;
        if (intent != null) {
            a(intent.getStringExtra(com.tencent.connect.common.Constants.KEY_RESPONSE));
            if (iUiListener == null) {
                AssistActivity.setResultDataForLogin(activity, intent);
                return;
            }
            if (i2 == -1) {
                BaseApi.handleDataToListener(intent, iUiListener);
            } else {
                f.b(f.d, "OpenUi, onActivityResult, Constants.ACTIVITY_CANCEL");
                iUiListener.onCancel();
            }
            releaseResource();
            f.b();
        } else if (iUiListener != null) {
            iUiListener.onCancel();
        }
    }

    private void a(String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            Object string = parseJson.getString("access_token");
            Object string2 = parseJson.getString("expires_in");
            Object string3 = parseJson.getString("openid");
            if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2) && !TextUtils.isEmpty(string3)) {
                this.mToken.setAccessToken(string, string2);
                this.mToken.setOpenId(string3);
            }
        } catch (Exception e) {
        }
    }
}
