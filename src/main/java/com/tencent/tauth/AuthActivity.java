package com.tencent.tauth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.connect.auth.AuthMap;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.connect.common.AssistActivity;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.TemporaryStorage;
import com.tencent.open.utils.Util;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthActivity extends Activity {
    private static final String ACTION_ADD_TO_QQFAVORITES = "addToQQFavorites";
    public static final String ACTION_KEY = "action";
    private static final String ACTION_SEND_TO_MY_COMPUTER = "sendToMyComputer";
    public static final String ACTION_SHARE_PRIZE = "sharePrize";
    private static final String ACTION_SHARE_TO_QQ = "shareToQQ";
    private static final String ACTION_SHARE_TO_QZONE = "shareToQzone";
    private static final String ACTION_SHARE_TO_TROOP_BAR = "shareToTroopBar";
    private static final String SHARE_PRIZE_ACTIVITY_ID = "activityid";
    private static final String TAG = (f.d + ".AuthActivity");
    private static int mShareQzoneBackTime = 0;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Uri data = getIntent().getData();
        f.b(TAG, "-->onCreate, uri: " + data);
        handleActionUri(data);
    }

    private void handleActionUri(Uri uri) {
        if (uri == null || uri.toString().equals("")) {
            finish();
            return;
        }
        String uri2 = uri.toString();
        Bundle decodeUrl = Util.decodeUrl(uri2.substring(uri2.indexOf("#") + 1));
        String string = decodeUrl.getString("action");
        f.b(TAG, "-->handleActionUri, action: " + string);
        if (string == null) {
            execAuthCallback(decodeUrl, uri2);
        } else if (string.equals("shareToQQ") || string.equals("shareToQzone") || string.equals("sendToMyComputer") || string.equals("shareToTroopBar")) {
            if (string.equals("shareToQzone") && SystemUtils.getAppVersionName(this, "com.tencent.mobileqq") != null && SystemUtils.compareQQVersion(this, SystemUtils.QQ_VERSION_NAME_5_2_0) < 0) {
                mShareQzoneBackTime++;
                if (mShareQzoneBackTime == 2) {
                    mShareQzoneBackTime = 0;
                    finish();
                    return;
                }
            }
            Intent intent = new Intent(this, AssistActivity.class);
            intent.putExtras(decodeUrl);
            intent.setFlags(603979776);
            startActivity(intent);
            finish();
        } else if (string.equals("addToQQFavorites")) {
            Intent intent2 = getIntent();
            intent2.putExtras(decodeUrl);
            intent2.putExtra(Constants.KEY_ACTION, SystemUtils.ACTION_SHARE);
            r0 = TemporaryStorage.get(string);
            if (r0 != null) {
                BaseApi.handleDataToListener(intent2, (IUiListener) r0);
            }
            finish();
        } else if (string.equals(ACTION_SHARE_PRIZE)) {
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
            r0 = "";
            try {
                r0 = Util.parseJson(decodeUrl.getString("response")).getString("activityid");
            } catch (Exception e) {
                f.e(TAG, "sharePrize parseJson has exception.");
            }
            if (!TextUtils.isEmpty(r0)) {
                launchIntentForPackage.putExtra(ACTION_SHARE_PRIZE, true);
                decodeUrl = new Bundle();
                decodeUrl.putString("activityid", r0);
                launchIntentForPackage.putExtras(decodeUrl);
            }
            startActivity(launchIntentForPackage);
            finish();
        } else {
            execAuthCallback(decodeUrl, uri2);
        }
    }

    private void execAuthCallback(Bundle bundle, String str) {
        AuthMap instance = AuthMap.getInstance();
        String string = bundle.getString("serial");
        Auth auth = instance.get(string);
        if (auth != null) {
            if (str.indexOf("://cancel") != -1) {
                auth.listener.onCancel();
                auth.dialog.dismiss();
            } else {
                String string2 = bundle.getString("access_token");
                if (string2 != null) {
                    bundle.putString("access_token", instance.decode(string2, auth.key));
                }
                JSONObject decodeUrlToJson = Util.decodeUrlToJson(new JSONObject(), Util.encodeUrl(bundle));
                String optString = decodeUrlToJson.optString("cb");
                if ("".equals(optString)) {
                    auth.listener.onComplete(decodeUrlToJson);
                    auth.dialog.dismiss();
                } else {
                    auth.dialog.callJs(optString, decodeUrlToJson.toString());
                }
            }
            instance.remove(string);
        }
        finish();
    }
}
