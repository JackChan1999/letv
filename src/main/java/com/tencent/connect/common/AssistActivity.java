package com.tencent.connect.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.webview.LetvBaseWebViewActivity;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.TemporaryStorage;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AssistActivity extends Activity {
    private static final String RESTART_FLAG = "RESTART_FLAG";
    private static final String TAG = "AssistActivity";
    public static boolean hackShareSend = false;
    public static boolean isQQMobileShare = false;
    private static BaseApi sApiObject;
    private BaseApi mAPiObject;

    public static Intent getAssistActivityIntent(Context context) {
        return new Intent(context, AssistActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        int i = 0;
        super.onCreate(bundle);
        requestWindowFeature(1);
        f.b(TAG, "AssistActivity--onCreate--");
        if (sApiObject != null) {
            int i2;
            this.mAPiObject = sApiObject;
            sApiObject = null;
            Intent activityIntent = this.mAPiObject.getActivityIntent();
            if (activityIntent == null) {
                i2 = 0;
            } else {
                i2 = activityIntent.getIntExtra(Constants.KEY_REQUEST_CODE, 0);
            }
            Bundle bundleExtra = getIntent().getBundleExtra(SystemUtils.H5_SHARE_DATA);
            if (bundle != null) {
                i = bundle.getBoolean(RESTART_FLAG);
            }
            if (i != 0) {
                return;
            }
            if (bundleExtra == null) {
                startActivityForResult(activityIntent, i2);
            } else {
                openBrowser(bundleExtra);
            }
        }
    }

    protected void onStart() {
        f.b(TAG, "-->onStart");
        super.onStart();
    }

    protected void onResume() {
        f.b(TAG, "-->onResume");
        super.onResume();
        Intent intent = getIntent();
        if (!intent.getBooleanExtra(SystemUtils.IS_LOGIN, false) && !intent.getBooleanExtra(SystemUtils.IS_QQ_MOBILE_SHARE, false)) {
            if (!(hackShareSend || isFinishing())) {
                finish();
            }
            hackShareSend = false;
        }
    }

    protected void onPause() {
        f.b(TAG, "-->onPause");
        super.onPause();
    }

    protected void onStop() {
        f.b(TAG, "-->onStop");
        super.onStop();
    }

    protected void onDestroy() {
        f.b(TAG, "-->onDestroy");
        super.onDestroy();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Object obj = TemporaryStorage.get(intent.getStringExtra("action"));
        f.b(TAG, "AssistActivity--onNewIntent--" + (obj == null ? "mAPiObject = null" : "mAPiObject != null"));
        intent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_SHARE);
        if (obj != null) {
            BaseApi.handleDataToListener(intent, (IUiListener) obj);
        } else {
            setResult(-1, intent);
        }
        if (!isFinishing()) {
            finish();
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        f.b(TAG, "AssistActivity--onSaveInstanceState--");
        bundle.putBoolean(RESTART_FLAG, true);
        super.onSaveInstanceState(bundle);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        f.b(TAG, "AssistActivity--onActivityResult--" + i2 + " data=" + intent);
        f.b(TAG, "--requestCode: " + i + " | resultCode: " + i2 + " | data: " + intent);
        super.onActivityResult(i, i2, intent);
        if (i != 0) {
            if (intent != null) {
                intent.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_LOGIN);
            }
            if (this.mAPiObject != null) {
                f.b(TAG, "AssistActivity--onActivityResult-- mAPiObject != null");
                this.mAPiObject.onActivityResult(this, i, i2, intent);
            } else {
                f.b(TAG, "AssistActivity--onActivityResult-- mAPiObject == null");
                setResultDataForLogin(this, intent);
            }
            finish();
        } else if (!isFinishing()) {
            finish();
        }
    }

    public static void setApiObject(BaseApi baseApi) {
        sApiObject = baseApi;
    }

    public static void setResultDataForLogin(Activity activity, Intent intent) {
        if (intent == null) {
            activity.setResult(Constants.RESULT_LOGIN, intent);
            return;
        }
        try {
            Object stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
            f.b(TAG, "AssistActivity--setResultDataForLogin-- " + stringExtra);
            if (!TextUtils.isEmpty(stringExtra)) {
                JSONObject jSONObject = new JSONObject(stringExtra);
                CharSequence optString = jSONObject.optString("openid");
                CharSequence optString2 = jSONObject.optString("access_token");
                if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2)) {
                    activity.setResult(LetvBaseWebViewActivity.Login_OK, intent);
                } else {
                    activity.setResult(Constants.RESULT_LOGIN, intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openBrowser(Bundle bundle) {
        String string = bundle.getString("viaShareType");
        String string2 = bundle.getString("callbackAction");
        String string3 = bundle.getString("url");
        String string4 = bundle.getString("openId");
        String string5 = bundle.getString("appId");
        String str = "";
        String str2 = "";
        if (SystemUtils.QQ_SHARE_CALLBACK_ACTION.equals(string2)) {
            str = Constants.VIA_SHARE_TO_QQ;
            str2 = "10";
        } else if (SystemUtils.QZONE_SHARE_CALLBACK_ACTION.equals(string2)) {
            str = Constants.VIA_SHARE_TO_QZONE;
            str2 = "11";
        }
        if (Util.openBrowser(this, string3)) {
            d.a().a(string4, string5, str, str2, "3", "0", string, "0", "2", "0");
        } else {
            IUiListener iUiListener = (IUiListener) TemporaryStorage.get(string2);
            if (iUiListener != null) {
                iUiListener.onError(new UiError(-6, Constants.MSG_OPEN_BROWSER_ERROR, null));
            }
            d.a().a(string4, string5, str, str2, "3", "1", string, "0", "2", "0");
            finish();
        }
        getIntent().removeExtra("shareH5");
    }
}
