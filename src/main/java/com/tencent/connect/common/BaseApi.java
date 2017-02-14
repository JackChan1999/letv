package com.tencent.connect.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.TDialog;
import com.tencent.open.a.f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public abstract class BaseApi {
    protected static final String ACTION_CHECK_TOKEN = "action_check_token";
    protected static final String ACTIVITY_AGENT = "com.tencent.open.agent.AgentActivity";
    protected static final String ACTIVITY_ENCRY_TOKEN = "com.tencent.open.agent.EncryTokenActivity";
    protected static final String DEFAULT_PF = "openmobile_android";
    private static final String KEY_REQUEST_CODE = "key_request_code";
    private static final int MSG_COMPLETE = 0;
    protected static final String PARAM_ENCRY_EOKEN = "encry_token";
    protected static final String PLATFORM = "desktop_m_qq";
    protected static final String PREFERENCE_PF = "pfStore";
    private static final String TAG = BaseApi.class.getName();
    protected static final String VERSION = "android";
    public static String businessId = null;
    public static String installChannel = null;
    public static boolean isOEM = false;
    public static String registerChannel = null;
    protected static int sRequestCode = 1000;
    protected Intent mActivityIntent;
    protected ProgressDialog mProgressDialog;
    protected QQAuth mQQAuth;
    protected List<ApiTask> mTaskList;
    protected QQToken mToken;
    protected IUiListener mUiListener;

    /* compiled from: ProGuard */
    public class ApiTask {
        public IUiListener mListener;
        public int mRequestCode;

        public ApiTask(int i, IUiListener iUiListener) {
            this.mRequestCode = i;
            this.mListener = iUiListener;
        }
    }

    /* compiled from: ProGuard */
    public class TempRequestListener implements IRequestListener {
        private final Handler mHandler;
        private final IUiListener mListener;

        public TempRequestListener(IUiListener iUiListener) {
            this.mListener = iUiListener;
            this.mHandler = new Handler(Global.getContext().getMainLooper(), BaseApi.this) {
                public void handleMessage(Message message) {
                    if (message.what == 0) {
                        TempRequestListener.this.mListener.onComplete(message.obj);
                    } else {
                        TempRequestListener.this.mListener.onError(new UiError(message.what, (String) message.obj, null));
                    }
                }
            };
        }

        public void onComplete(JSONObject jSONObject) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = jSONObject;
            obtainMessage.what = 0;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onIOException(IOException iOException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = iOException.getMessage();
            obtainMessage.what = -2;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = malformedURLException.getMessage();
            obtainMessage.what = -3;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onJSONException(JSONException jSONException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = jSONException.getMessage();
            obtainMessage.what = -4;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = connectTimeoutException.getMessage();
            obtainMessage.what = -7;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = socketTimeoutException.getMessage();
            obtainMessage.what = -8;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = networkUnavailableException.getMessage();
            obtainMessage.what = -10;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = httpStatusException.getMessage();
            obtainMessage.what = -9;
            this.mHandler.sendMessage(obtainMessage);
        }

        public void onUnknowException(Exception exception) {
            Message obtainMessage = this.mHandler.obtainMessage();
            obtainMessage.obj = exception.getMessage();
            obtainMessage.what = -6;
            this.mHandler.sendMessage(obtainMessage);
        }
    }

    public BaseApi(QQAuth qQAuth, QQToken qQToken) {
        this.mTaskList = null;
        this.mActivityIntent = null;
        this.mUiListener = null;
        this.mQQAuth = qQAuth;
        this.mToken = qQToken;
        this.mTaskList = new ArrayList();
    }

    public BaseApi(QQToken qQToken) {
        this(null, qQToken);
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        IUiListener iUiListener;
        for (ApiTask apiTask : this.mTaskList) {
            if (apiTask.mRequestCode == i) {
                IUiListener iUiListener2 = apiTask.mListener;
                this.mTaskList.remove(apiTask);
                iUiListener = iUiListener2;
                break;
            }
        }
        iUiListener = null;
        if (iUiListener == null) {
            f.b(TAG, "BaseApi--onActivityResult-- listener == null");
            AssistActivity.setResultDataForLogin(activity, intent);
            return;
        }
        if (i2 == -1) {
            handleDataToListener(intent, iUiListener);
        } else {
            f.b(f.d, "OpenUi, onActivityResult, Constants.ACTIVITY_CANCEL");
            iUiListener.onCancel();
        }
        f.b();
    }

    public static void handleDataToListener(Intent intent, IUiListener iUiListener) {
        if (intent == null) {
            iUiListener.onCancel();
            return;
        }
        String stringExtra = intent.getStringExtra(Constants.KEY_ACTION);
        String stringExtra2;
        if (SystemUtils.ACTION_LOGIN.equals(stringExtra)) {
            int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
            if (intExtra == 0) {
                stringExtra2 = intent.getStringExtra(Constants.KEY_RESPONSE);
                if (stringExtra2 != null) {
                    try {
                        iUiListener.onComplete(Util.parseJson(stringExtra2));
                        return;
                    } catch (Throwable e) {
                        iUiListener.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra2));
                        f.b(f.d, "OpenUi, onActivityResult, json error", e);
                        return;
                    }
                }
                f.b(f.d, "OpenUi, onActivityResult, onComplete");
                iUiListener.onComplete(new JSONObject());
                return;
            }
            f.e(f.d, "OpenUi, onActivityResult, onError = " + intExtra + "");
            iUiListener.onError(new UiError(intExtra, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
        } else if (SystemUtils.ACTION_SHARE.equals(stringExtra)) {
            stringExtra = intent.getStringExtra("result");
            stringExtra2 = intent.getStringExtra("response");
            if ("cancel".equals(stringExtra)) {
                iUiListener.onCancel();
            } else if (NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE.equals(stringExtra)) {
                iUiListener.onError(new UiError(-6, "unknown error", stringExtra2 + ""));
            } else if ("complete".equals(stringExtra)) {
                try {
                    if (stringExtra2 == null) {
                        stringExtra = "{\"ret\": 0}";
                    } else {
                        stringExtra = stringExtra2;
                    }
                    iUiListener.onComplete(new JSONObject(stringExtra));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                    iUiListener.onError(new UiError(-4, "json error", stringExtra2 + ""));
                }
            }
        }
    }

    Intent getActivityIntent() {
        return this.mActivityIntent;
    }

    protected Bundle composeCGIParams() {
        Bundle bundle = new Bundle();
        bundle.putString("format", "json");
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        bundle.putString("status_version", VERSION.SDK);
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        if (this.mToken != null && this.mToken.isSessionValid()) {
            bundle.putString("access_token", this.mToken.getAccessToken());
            bundle.putString("oauth_consumer_key", this.mToken.getAppId());
            bundle.putString("openid", this.mToken.getOpenId());
            bundle.putString("appid_for_getting_config", this.mToken.getAppId());
        }
        SharedPreferences sharedPreferences = Global.getContext().getSharedPreferences("pfStore", 0);
        if (isOEM) {
            bundle.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + NetworkUtils.DELIMITER_LINE + "android" + NetworkUtils.DELIMITER_LINE + registerChannel + NetworkUtils.DELIMITER_LINE + businessId);
        } else {
            bundle.putString(Constants.PARAM_PLATFORM_ID, sharedPreferences.getString(Constants.PARAM_PLATFORM_ID, "openmobile_android"));
        }
        return bundle;
    }

    protected String getCommonDownloadQQUrl(String str) {
        Bundle composeCGIParams = composeCGIParams();
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            composeCGIParams.putString("need_version", str);
        }
        stringBuilder.append(ServerSetting.NEED_QQ_VERSION_TIPS_URL);
        stringBuilder.append(Util.encodeUrl(composeCGIParams));
        return stringBuilder.toString();
    }

    protected Bundle composeActivityParams() {
        Bundle bundle = new Bundle();
        bundle.putString("appid", this.mToken.getAppId());
        if (this.mToken.isSessionValid()) {
            bundle.putString(Constants.PARAM_KEY_STR, this.mToken.getAccessToken());
            bundle.putString(Constants.PARAM_KEY_TYPE, "0x80");
        }
        String openId = this.mToken.getOpenId();
        if (openId != null) {
            bundle.putString("hopenid", openId);
        }
        bundle.putString(Constants.PARAM_PLATFORM, "androidqz");
        SharedPreferences sharedPreferences = Global.getContext().getSharedPreferences("pfStore", 0);
        if (isOEM) {
            bundle.putString(Constants.PARAM_PLATFORM_ID, "desktop_m_qq-" + installChannel + NetworkUtils.DELIMITER_LINE + "android" + NetworkUtils.DELIMITER_LINE + registerChannel + NetworkUtils.DELIMITER_LINE + businessId);
        } else {
            bundle.putString(Constants.PARAM_PLATFORM_ID, sharedPreferences.getString(Constants.PARAM_PLATFORM_ID, "openmobile_android"));
            bundle.putString(Constants.PARAM_PLATFORM_ID, "openmobile_android");
        }
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
        return bundle;
    }

    private Intent getAssitIntent(Activity activity) {
        Intent intent = new Intent(activity.getApplicationContext(), AssistActivity.class);
        intent.putExtra(SystemUtils.IS_LOGIN, true);
        return intent;
    }

    protected void startAssistActivity(Activity activity, int i) {
        AssistActivity.setApiObject(this);
        Intent intent = new Intent(activity.getApplicationContext(), AssistActivity.class);
        AssistActivity.hackShareSend = true;
        if (AssistActivity.isQQMobileShare) {
            intent.putExtra(SystemUtils.IS_QQ_MOBILE_SHARE, true);
            AssistActivity.isQQMobileShare = false;
        }
        activity.startActivityForResult(intent, i);
    }

    protected void startAssistActivity(Activity activity, Bundle bundle, int i) {
        AssistActivity.setApiObject(this);
        Intent intent = new Intent(activity.getApplicationContext(), AssistActivity.class);
        AssistActivity.hackShareSend = true;
        intent.putExtra(SystemUtils.H5_SHARE_DATA, bundle);
        activity.startActivityForResult(intent, i);
    }

    protected void startAssitActivity(Activity activity, IUiListener iUiListener) {
        AssistActivity.setApiObject(this);
        int i = sRequestCode;
        sRequestCode = i + 1;
        this.mActivityIntent.putExtra("key_request_code", i);
        this.mTaskList.add(new ApiTask(i, iUiListener));
        activity.startActivityForResult(getAssitIntent(activity), Constants.REQUEST_API);
    }

    protected void startAssitActivity(Fragment fragment, IUiListener iUiListener) {
        AssistActivity.setApiObject(this);
        int i = sRequestCode;
        sRequestCode = i + 1;
        this.mActivityIntent.putExtra("key_request_code", i);
        this.mTaskList.add(new ApiTask(i, iUiListener));
        fragment.startActivityForResult(getAssitIntent(fragment.getActivity()), Constants.REQUEST_API);
    }

    protected boolean hasActivityForIntent() {
        if (this.mActivityIntent != null) {
            return SystemUtils.isActivityExist(Global.getContext(), this.mActivityIntent);
        }
        return false;
    }

    protected Intent getTargetActivityIntent(String str) {
        Intent intent = new Intent();
        intent.setClassName("com.tencent.mobileqq", str);
        return SystemUtils.isActivityExist(Global.getContext(), intent) ? intent : null;
    }

    protected void handleDownloadLastestQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ServerSetting.DOWNLOAD_QQ_URL);
        stringBuilder.append(Util.encodeUrl(bundle));
        Context context = activity;
        new TDialog(context, "", stringBuilder.toString(), null, this.mToken).show();
    }

    protected void showProgressDialog(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            str = "请稍候";
        }
        if (TextUtils.isEmpty(str2)) {
            str2 = "正在加载...";
        }
        this.mProgressDialog = ProgressDialog.show(context, str, str2);
        this.mProgressDialog.setCancelable(true);
    }

    protected Intent getAgentIntent() {
        return getTargetActivityIntent(ACTIVITY_AGENT);
    }

    protected Intent getAgentIntentWithTarget(String str) {
        Intent intent = new Intent();
        Intent targetActivityIntent = getTargetActivityIntent(str);
        if (targetActivityIntent == null || targetActivityIntent.getComponent() == null) {
            return null;
        }
        intent.setClassName(targetActivityIntent.getComponent().getPackageName(), ACTIVITY_AGENT);
        return intent;
    }

    public void releaseResource() {
    }
}
