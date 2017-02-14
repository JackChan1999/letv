package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.OpenConfig;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class SocialApiIml extends BaseApi {
    private static final String a = SocialApiIml.class.getName();
    private Activity b;

    /* compiled from: ProGuard */
    protected class a implements IUiListener {
        b a;
        final /* synthetic */ SocialApiIml b;

        public a(SocialApiIml socialApiIml, b bVar) {
            this.b = socialApiIml;
            this.a = bVar;
        }

        public void onComplete(Object obj) {
            boolean z = false;
            if (obj != null) {
                try {
                    z = ((JSONObject) obj).getBoolean("check_result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.b.b();
            if (z) {
                this.b.a(this.b.b, this.a.a, this.a.b, this.a.c, this.a.e);
                return;
            }
            e.a(this.a.c.getString(SocialConstants.PARAM_IMG_DATA));
            this.b.a(this.b.b, this.a.b, this.a.c, this.a.d, this.a.e);
        }

        public void onError(UiError uiError) {
            this.b.b();
            e.a(this.a.c.getString(SocialConstants.PARAM_IMG_DATA));
            this.b.a(this.b.b, this.a.b, this.a.c, this.a.d, this.a.e);
        }

        public void onCancel() {
            this.b.b();
            e.a(this.a.c.getString(SocialConstants.PARAM_IMG_DATA));
        }
    }

    /* compiled from: ProGuard */
    private static class b {
        Intent a;
        String b;
        Bundle c;
        String d;
        IUiListener e;
    }

    /* compiled from: ProGuard */
    private class c implements IUiListener {
        final /* synthetic */ SocialApiIml a;
        private IUiListener b;
        private String c;
        private String d;
        private Bundle e;
        private Activity f;

        c(SocialApiIml socialApiIml, Activity activity, IUiListener iUiListener, String str, String str2, Bundle bundle) {
            this.a = socialApiIml;
            this.b = iUiListener;
            this.c = str;
            this.d = str2;
            this.e = bundle;
        }

        public void onComplete(Object obj) {
            CharSequence string;
            CharSequence charSequence = null;
            try {
                string = ((JSONObject) obj).getString(SocialConstants.PARAM_ENCRY_EOKEN);
            } catch (Throwable e) {
                e.printStackTrace();
                f.b(f.d, "OpenApi, EncrytokenListener() onComplete error", e);
                string = charSequence;
            }
            this.e.putString("encrytoken", string);
            this.a.a(this.a.b, this.c, this.e, this.d, this.b);
            if (TextUtils.isEmpty(string)) {
                f.b("miles", "The token get from qq or qzone is empty. Write temp token to localstorage.");
                this.a.writeEncryToken(this.f);
            }
        }

        public void onError(UiError uiError) {
            f.b(f.d, "OpenApi, EncryptTokenListener() onError" + uiError.errorMessage);
            this.b.onError(uiError);
        }

        public void onCancel() {
            this.b.onCancel();
        }
    }

    public SocialApiIml(QQToken qQToken) {
        super(qQToken);
    }

    public SocialApiIml(QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        a(activity, SocialConstants.ACTION_GIFT, bundle, iUiListener);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        a(activity, SocialConstants.ACTION_ASK, bundle, iUiListener);
    }

    private void a(Activity activity, String str, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_ASK_GIFT);
        }
        bundle.putAll(composeActivityParams());
        if (SocialConstants.ACTION_ASK.equals(str)) {
            bundle.putString("type", SocialConstants.TYPE_REQUEST);
        } else if (SocialConstants.ACTION_GIFT.equals(str)) {
            bundle.putString("type", SocialConstants.TYPE_FREEGIFT);
        }
        a(activity, agentIntentWithTarget, str, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), "http://qzs.qq.com/open/mobile/request/sdk_request.html?"), iUiListener, false);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_INVITE);
        }
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        a(activity2, agentIntentWithTarget, SocialConstants.ACTION_INVITE, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_INVITE), iUiListener, false);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_STORY);
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        a(activity2, agentIntentWithTarget, SocialConstants.ACTION_STORY, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_SEND_STORY), iUiListener, false);
    }

    private b a(Bundle bundle, String str, String str2, IUiListener iUiListener) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
        b bVar = new b();
        bVar.a = intent;
        bVar.c = bundle;
        bVar.d = str2;
        bVar.e = iUiListener;
        bVar.b = str;
        return bVar;
    }

    private void b() {
        if (!this.b.isFinishing() && this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    private void a(Activity activity, Intent intent, String str, Bundle bundle, String str2, IUiListener iUiListener, boolean z) {
        f.b(f.d, "-->handleIntent " + str + " params=" + bundle + " activityIntent=" + intent);
        if (intent != null) {
            a(activity, intent, str, bundle, iUiListener);
            return;
        }
        Object obj = (z || OpenConfig.getInstance(Global.getContext(), this.mToken.getAppId()).getBoolean("C_LoginH5")) ? 1 : null;
        if (obj != null) {
            a(activity, str, bundle, str2, iUiListener);
        } else {
            handleDownloadLastestQQ(activity, bundle, iUiListener);
        }
    }

    private void a(Activity activity, Intent intent, String str, Bundle bundle, IUiListener iUiListener) {
        f.b(f.d, "-->handleIntentWithAgent " + str + " params=" + bundle + " activityIntent=" + intent);
        intent.putExtra(Constants.KEY_ACTION, str);
        intent.putExtra(Constants.KEY_PARAMS, bundle);
        this.mActivityIntent = intent;
        startAssitActivity(activity, iUiListener);
    }

    private void a(Activity activity, String str, Bundle bundle, String str2, IUiListener iUiListener) {
        f.b(f.d, "-->handleIntentWithH5 " + str + " params=" + bundle);
        Intent targetActivityIntent = getTargetActivityIntent("com.tencent.open.agent.AgentActivity");
        Object cVar = new c(this, activity, iUiListener, str, str2, bundle);
        Intent targetActivityIntent2 = getTargetActivityIntent("com.tencent.open.agent.EncryTokenActivity");
        if (targetActivityIntent2 == null || targetActivityIntent == null || targetActivityIntent.getComponent() == null || targetActivityIntent2.getComponent() == null || !targetActivityIntent.getComponent().getPackageName().equals(targetActivityIntent2.getComponent().getPackageName())) {
            String encrypt = Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(SocialConstants.PARAM_ENCRY_EOKEN, encrypt);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cVar.onComplete(jSONObject);
            return;
        }
        targetActivityIntent2.putExtra("oauth_consumer_key", this.mToken.getAppId());
        targetActivityIntent2.putExtra("openid", this.mToken.getOpenId());
        targetActivityIntent2.putExtra("access_token", this.mToken.getAccessToken());
        targetActivityIntent2.putExtra(Constants.KEY_ACTION, SocialConstants.ACTION_CHECK_TOKEN);
        this.mActivityIntent = targetActivityIntent2;
        if (hasActivityForIntent()) {
            startAssitActivity(activity, (IUiListener) cVar);
        }
    }

    private void a(Context context, String str, Bundle bundle, String str2, IUiListener iUiListener) {
        f.a(f.d, "OpenUi, showDialog --start");
        CookieSyncManager.createInstance(context);
        bundle.putString("oauth_consumer_key", this.mToken.getAppId());
        if (this.mToken.isSessionValid()) {
            bundle.putString("access_token", this.mToken.getAccessToken());
        }
        String openId = this.mToken.getOpenId();
        if (openId != null) {
            bundle.putString("openid", openId);
        }
        try {
            bundle.putString(Constants.PARAM_PLATFORM_ID, Global.getContext().getSharedPreferences(Constants.PREFERENCE_PF, 0).getString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF));
        } catch (Exception e) {
            e.printStackTrace();
            bundle.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str2);
        stringBuilder.append(Util.encodeUrl(bundle));
        String stringBuilder2 = stringBuilder.toString();
        f.b(f.d, "OpenUi, showDialog TDialog");
        if (SocialConstants.ACTION_CHALLENGE.equals(str) || SocialConstants.ACTION_BRAG.equals(str)) {
            f.b(f.d, "OpenUi, showDialog PKDialog");
            new PKDialog(this.b, str, stringBuilder2, iUiListener, this.mToken).show();
            return;
        }
        new TDialog(this.b, str, stringBuilder2, iUiListener, this.mToken).show();
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public void writeEncryToken(Context context) {
        String str = "tencent&sdk&qazxc***14969%%";
        String accessToken = this.mToken.getAccessToken();
        String appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        String str2 = "qzone3.4";
        if (accessToken == null || accessToken.length() <= 0 || appId == null || appId.length() <= 0 || openId == null || openId.length() <= 0) {
            str = null;
        } else {
            str = Util.encrypt(str + accessToken + appId + openId + str2);
        }
        com.tencent.open.c.b bVar = new com.tencent.open.c.b(context);
        WebSettings settings = bVar.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        accessToken = "<!DOCTYPE HTML><html lang=\"en-US\"><head><meta charset=\"UTF-8\"><title>localStorage Test</title><script type=\"text/javascript\">document.domain = 'qq.com';localStorage[\"" + this.mToken.getOpenId() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.mToken.getAppId() + "\"]=\"" + str + "\";</script></head><body></body></html>";
        str = ServerSetting.getInstance().getEnvUrl(context, ServerSetting.DEFAULT_LOCAL_STORAGE_URI);
        bVar.loadDataWithBaseURL(str, accessToken, "text/html", "utf-8", str);
    }

    protected boolean a() {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, SocialConstants.ACTIVITY_CHECK_FUNCTION);
        return SystemUtils.isActivityExist(Global.getContext(), intent);
    }

    protected void a(Activity activity, String str, IUiListener iUiListener) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, "com.tencent.open.agent.AgentActivity");
        intent.putExtra(Constants.KEY_ACTION, "action_check");
        Bundle bundle = new Bundle();
        bundle.putString("apiName", str);
        intent.putExtra(Constants.KEY_PARAMS, bundle);
        this.mActivityIntent = intent;
        startAssitActivity(activity, iUiListener);
    }

    protected Intent getTargetActivityIntent(String str) {
        Intent intent = new Intent();
        intent.setClassName(Constants.PACKAGE_QZONE, str);
        Intent intent2 = new Intent();
        intent2.setClassName("com.tencent.mobileqq", str);
        if (SystemUtils.isActivityExist(Global.getContext(), intent2) && SystemUtils.compareQQVersion(Global.getContext(), "4.7") >= 0) {
            return intent2;
        }
        if (!SystemUtils.isActivityExist(Global.getContext(), intent) || SystemUtils.compareVersion(SystemUtils.getAppVersionName(Global.getContext(), Constants.PACKAGE_QZONE), "4.2") < 0) {
            return null;
        }
        if (SystemUtils.isAppSignatureValid(Global.getContext(), intent.getComponent().getPackageName(), Constants.SIGNATRUE_QZONE)) {
            return intent;
        }
        return null;
    }

    public void reactive(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_FRIEND_CHOOSER);
        if (agentIntentWithTarget == null) {
            agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_REACTIVE);
        }
        bundle.putAll(composeActivityParams());
        String envUrl = ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_REACTIVE);
        if (agentIntentWithTarget == null && a()) {
            this.mProgressDialog = new ProgressDialog(activity);
            this.mProgressDialog.setMessage("请稍候...");
            this.mProgressDialog.show();
            bundle.putString("type", SocialConstants.TYPE_REACTIVE);
            a(activity, SocialConstants.ACTION_REACTIVE, new a(this, a(bundle, SocialConstants.ACTION_REACTIVE, envUrl, iUiListener)));
            return;
        }
        bundle.putString(SocialConstants.PARAM_SEND_IMG, bundle.getString("img"));
        bundle.putString("type", SocialConstants.TYPE_REACTIVE);
        bundle.remove("img");
        a(activity, agentIntentWithTarget, SocialConstants.ACTION_REACTIVE, bundle, envUrl, iUiListener, false);
    }

    public void brag(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_BRAG);
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        a(activity2, agentIntentWithTarget, SocialConstants.ACTION_BRAG, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_BRAG), iUiListener, false);
    }

    public void challenge(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_CHALLENGE);
        bundle.putAll(composeActivityParams());
        Activity activity2 = activity;
        a(activity2, agentIntentWithTarget, SocialConstants.ACTION_CHALLENGE, bundle, ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_BRAG), iUiListener, false);
    }

    public void grade(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.b = activity;
        bundle.putAll(composeActivityParams());
        bundle.putString("version", Util.getAppVersion(activity));
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_GRADE);
        String str = "http://qzs.qq.com/open/mobile/rate/sdk_rate.html?";
        if (agentIntentWithTarget == null && a()) {
            this.mProgressDialog = new ProgressDialog(activity);
            this.mProgressDialog.setMessage("请稍候...");
            this.mProgressDialog.show();
            a(activity, SocialConstants.ACTION_GRADE, new a(this, a(bundle, SocialConstants.ACTION_GRADE, str, iUiListener)));
            return;
        }
        a(activity, agentIntentWithTarget, SocialConstants.ACTION_GRADE, bundle, str, iUiListener, true);
    }

    public void voice(final Activity activity, final Bundle bundle, final IUiListener iUiListener) {
        this.b = activity;
        bundle.putAll(composeActivityParams());
        bundle.putString("version", Util.getAppVersion(activity));
        if (!e.a()) {
            iUiListener.onError(new UiError(-12, Constants.MSG_NO_SDCARD, Constants.MSG_NO_SDCARD));
        } else if (!bundle.containsKey(SocialConstants.PARAM_IMG_DATA) || ((Bitmap) bundle.getParcelable(SocialConstants.PARAM_IMG_DATA)) == null) {
            a(activity, bundle, iUiListener);
        } else {
            this.mProgressDialog = new ProgressDialog(activity);
            this.mProgressDialog.setMessage("请稍候...");
            this.mProgressDialog.show();
            new e(new com.tencent.open.e.a(this) {
                final /* synthetic */ SocialApiIml d;

                public void a(String str) {
                    bundle.remove(SocialConstants.PARAM_IMG_DATA);
                    if (!TextUtils.isEmpty(str)) {
                        bundle.putString(SocialConstants.PARAM_IMG_DATA, str);
                    }
                    this.d.a(activity, bundle, iUiListener);
                }

                public void b(String str) {
                    bundle.remove(SocialConstants.PARAM_IMG_DATA);
                    iUiListener.onError(new UiError(-5, Constants.MSG_IMAGE_ERROR, Constants.MSG_IMAGE_ERROR));
                    this.d.b();
                }
            }).execute(new Bitmap[]{r0});
        }
    }

    private void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        Intent agentIntentWithTarget = getAgentIntentWithTarget(SocialConstants.ACTIVITY_VOICE);
        String envUrl = ServerSetting.getInstance().getEnvUrl(Global.getContext(), ServerSetting.DEFAULT_URL_VOICE);
        if (agentIntentWithTarget == null && a()) {
            if (this.mProgressDialog == null || !this.mProgressDialog.isShowing()) {
                this.mProgressDialog = new ProgressDialog(activity);
                this.mProgressDialog.setTitle("请稍候");
                this.mProgressDialog.show();
            }
            a(activity, SocialConstants.ACTION_VOICE, new a(this, a(bundle, SocialConstants.ACTION_VOICE, envUrl, iUiListener)));
            return;
        }
        a(activity, agentIntentWithTarget, SocialConstants.ACTION_VOICE, bundle, envUrl, iUiListener, true);
    }
}
