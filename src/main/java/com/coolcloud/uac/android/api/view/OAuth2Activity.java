package com.coolcloud.uac.android.api.view;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.coolcloud.uac.android.api.ErrInfo;
import com.coolcloud.uac.android.api.OnResultListener;
import com.coolcloud.uac.android.api.invoker.TKTResolver;
import com.coolcloud.uac.android.api.view.basic.BasicActivity;
import com.coolcloud.uac.android.api.view.basic.L10NString;
import com.coolcloud.uac.android.common.Rcode;
import com.coolcloud.uac.android.common.util.Executor;
import com.coolcloud.uac.android.common.util.Executor.RunNoThrowable;
import com.coolcloud.uac.android.common.util.KVUtils;
import com.coolcloud.uac.android.common.util.LOG;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnAuthCodeListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnBundleListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnDownLogoListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnGetQihooTokenListener;
import com.coolcloud.uac.android.common.ws.BasicWsApi.OnTokenListener;
import com.letv.android.client.share.videoshot.VideoShotEditActivity;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.core.parser.LetvMasterParser;
import com.letv.datastatistics.util.DataConstant.ACTION.LE123.CHANNEL;
import org.json.JSONArray;
import org.json.JSONObject;

public class OAuth2Activity extends BasicActivity implements OnClickListener {
    private static final String TAG = "OAuth2Activity";
    private boolean abortCountdown = true;
    private ImageView m3thLogo = null;
    private TextView m3thName = null;
    private ImageView mAccountLogo = null;
    private TextView mAccountTv = null;
    private ImageView mCoolCloudLogo = null;
    private TextView mCoolCloudName = null;
    private TextView mErrorPrompt = null;
    private Button mOAuth2 = null;
    private TextView mOAuth2Prompt = null;
    private TextView mOAuth2Scope = null;
    private ImageView mRelation = null;
    private View mStatusBar = null;
    private TextView mSwitchAccount = null;
    private TextView mUserAccount = null;
    private ImageView mUserLogo = null;

    private class BasicResultHandler implements OnResultListener {
        private BasicResultHandler() {
        }

        public void onResult(Bundle result) {
            String uid = KVUtils.get(result, "uid");
            String tkt = KVUtils.get(result, "tkt");
            if (TextUtils.empty(uid) || TextUtils.empty(tkt)) {
                OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, (int) VideoShotEditActivity.VIDEOSHOT_PIC_DOWNLOAD_FINISHD);
                return;
            }
            KVUtils.put(OAuth2Activity.this.getIntent(), "uid", uid);
            KVUtils.put(OAuth2Activity.this.getIntent(), "tkt", tkt);
            onResult(uid, tkt);
        }

        public void onError(ErrInfo error) {
            OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, error != null ? error.getError() : 1);
        }

        public void onCancel() {
            OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, -1);
        }

        protected void onResult(String uid, String tkt) {
            LOG.d(OAuth2Activity.TAG, "nothing to do on result for basic result handler ...");
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        LOG.d(TAG, "on create ...");
        super.onCreate(savedInstanceState, "uac_sdk_oauth2", "umgr_oauth2_header", "umgr_title_auth");
        this.mStatusBar = this.mRootView.findViewWithTag("umgr_oauth2_header");
        initView();
        beautyView();
        String uid = KVUtils.get(getIntent(), "uid");
        String tkt = KVUtils.get(getIntent(), "tkt");
        String inputaccount = KVUtils.get(getIntent(), "inputaccount");
        String loginsource = KVUtils.get(getIntent(), "loginsource");
        if (TextUtils.equal(loginsource, CHANNEL.MOVIE_SPECIAL)) {
            this.mAccountLogo.setImageDrawable(this.crMgmt.getDrawable("uac_third_qihoo", false));
            beautyTextView(this.mAccountTv, L10NString.getString("umgr_360_name"));
        }
        this.mBackLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                OAuth2Activity.this.abortCountdown = true;
                OAuth2Activity.this.handleCancelOnFinish();
                ((InputMethodManager) OAuth2Activity.this.getSystemService("input_method")).hideSoftInputFromWindow(OAuth2Activity.this.mBackLayout.getWindowToken(), 2);
            }
        });
        doBootstrap();
        LOG.i(TAG, "[uid:" + uid + "][tkt:" + tkt + "][appId:" + this.appId + "][inputaccount:" + inputaccount + "][loginsource:" + loginsource + "] on create done ...");
    }

    protected void onDestroy() {
        this.abortCountdown = true;
        super.onDestroy();
    }

    private void doCountDown(final int countdown, long delayMillis) {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            public void run() {
                int count = countdown;
                if (!OAuth2Activity.this.abortCountdown && count >= 0) {
                    String format = L10NString.getString("umgr_auth_and_login_format");
                    OAuth2Activity.this.mOAuth2.setText(String.format(format, new Object[]{Integer.valueOf(count)}));
                    OAuth2Activity.this.doCountDown(count - 1, 1000);
                } else if (OAuth2Activity.this.abortCountdown) {
                    LOG.w(OAuth2Activity.TAG, "[abortCountdown:" + OAuth2Activity.this.abortCountdown + "][count:" + count + "] countdown over ...");
                } else {
                    OAuth2Activity.this.abortCountdown = true;
                    OAuth2Activity.this.doGetTokenOrAuthCode();
                    LOG.i(OAuth2Activity.TAG, "[abortCountdown:" + OAuth2Activity.this.abortCountdown + "][count:" + count + "] countdown over ...");
                }
            }
        }, delayMillis);
    }

    private void beautyView() {
        beautyTextView(this.m3thName, L10NString.getString("umgr_3th_name"));
        beautyTextView(this.mUserAccount, "");
        beautyTextView(this.mCoolCloudName, L10NString.getString("umgr_coolcloud_name"));
        beautyTextView(this.mOAuth2Scope, "");
        this.m3thLogo.setImageDrawable(this.crMgmt.getDrawable("uac_3th_default_logo", false));
        this.mCoolCloudLogo.setImageDrawable(this.crMgmt.getDrawable("uac_logo", false));
        this.mRelation.setImageDrawable(this.crMgmt.getDrawable("uac_auth_relation", false));
        this.mUserLogo.setImageDrawable(this.crMgmt.getDrawable("uac_auth_user_default_logo", false));
        beautyColorTextView(this.mSwitchAccount, "#FF0000", false, L10NString.getString("umgr_switch_user"), this);
        beautyButtonGreen(this.mOAuth2, L10NString.getString("umgr_auth_and_login"), this);
        beautyTextView(this.mOAuth2Prompt, L10NString.getString("umgr_allow_3th_access"));
        loadPrivateConfig();
    }

    private void initView() {
        this.m3thLogo = (ImageView) this.mRootView.findViewWithTag("umgr_oauth2_3th_logo");
        this.mCoolCloudLogo = (ImageView) this.mRootView.findViewWithTag("umgr_oauth2_coolcloud_logo");
        this.m3thName = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_3th_name");
        this.mRelation = (ImageView) this.mRootView.findViewWithTag("umgr_oauth2_relation");
        this.mAccountLogo = (ImageView) this.mRootView.findViewWithTag("umgr_oauth2_coolcloud_logo");
        this.mAccountTv = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_coolcloud_name");
        this.mUserLogo = (ImageView) this.mRootView.findViewWithTag("umgr_oauth2_user_logo");
        this.mUserAccount = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_user_account");
        this.mSwitchAccount = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_switch_account");
        this.mOAuth2 = (Button) this.mRootView.findViewWithTag("umgr_oauth2_submit");
        this.mOAuth2Scope = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_scope");
        this.mErrorPrompt = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_error_prompt");
        this.mOAuth2Prompt = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_prompt");
        this.mCoolCloudName = (TextView) this.mRootView.findViewWithTag("umgr_oauth2_coolcloud_name");
        this.mOAuth2Scope.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    private void doBootstrap() {
        new Handler(getMainLooper()).post(new Runnable() {
            public void run() {
                OAuth2Activity.this.doGetUserInfo();
                OAuth2Activity.this.doGetAppInfo();
            }
        });
    }

    public void onClick(View v) {
        String tag = String.valueOf(v.getTag());
        if (TextUtils.equal(tag, "umgr_oauth2_switch_account")) {
            this.abortCountdown = true;
            Bundle result = new Bundle();
            KVUtils.put(result, "responseCode", 111);
            handleResultOnFinish(result);
        } else if (TextUtils.equal(tag, "umgr_oauth2_submit")) {
            doGetTokenOrAuthCode();
        }
    }

    private void setImage(final ImageView view, final byte[] content) {
        try {
            Executor.execute(new Runnable() {
                public void run() {
                    if (content != null) {
                        OAuth2Activity.this.runOnUiThread(new 1(this, BitmapFactory.decodeByteArray(content, 0, content.length)));
                    }
                }
            });
        } catch (Exception e) {
            LOG.e(TAG, "decode and set image bitmap failed(Exception)");
        }
    }

    private void setAppScope(String scope) {
        String[] scopes = scope.split(",");
        StringBuffer sb = new StringBuffer();
        int index = 1;
        for (String tscope : scopes) {
            if (1 == index) {
                sb.append(index).append("  ").append(tscope);
            } else {
                sb.append("\n").append(index).append("  ").append(tscope);
            }
            index++;
        }
        this.mOAuth2Scope.setText(sb.toString());
    }

    private void doGetUserInfo() {
        String uid = KVUtils.get(getIntent(), "uid");
        String tkt = KVUtils.get(getIntent(), "tkt");
        final String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId:" + this.appId + "]";
        this.mUserAccount.setText(uid);
        getWsApi().getBasicUserInfo(uid, tkt, this.appId, new OnBundleListener() {
            public void onDone(int rcode, Bundle userInfo) {
                LOG.i(OAuth2Activity.TAG, prefix + " get user info callback(" + rcode + "," + userInfo + ")");
                if (rcode == 0) {
                    OAuth2Activity.this.handleUserInfoCallback(userInfo);
                    OAuth2Activity.this.abortCountdown = false;
                    OAuth2Activity.this.doCountDown(5, 0);
                } else if (Rcode.isNeedRelogin(rcode) || Rcode.isUnbind(rcode)) {
                    OAuth2Activity.this.doRelogin4UserInfo();
                } else {
                    OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, rcode);
                }
            }
        });
    }

    private void doGetAppInfo() {
        final String prefix = "[appId:" + this.appId + "]";
        getWsApi().getAppInfo(this.appId, new OnBundleListener() {
            public void onDone(int rcode, Bundle appInfo) {
                LOG.i(OAuth2Activity.TAG, prefix + " get app info callback(" + rcode + "," + appInfo + ")");
                if (rcode == 0) {
                    OAuth2Activity.this.handleAppInfoCallback(appInfo);
                } else {
                    OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, rcode);
                }
            }
        });
    }

    private void doRelogin4UserInfo() {
        Executor.execute(new RunNoThrowable() {
            public void rundo() {
                TKTResolver resolver = TKTResolver.get(OAuth2Activity.this);
                Bundle input = new Bundle();
                KVUtils.put(input, OAuth2Activity.this.getIntent(), "screenOrientation");
                KVUtils.put(input, OAuth2Activity.this.getIntent(), "loginType");
                KVUtils.put(input, OAuth2Activity.this.getIntent(), "appId");
                resolver.relogin(OAuth2Activity.this, input, new 1(this));
            }
        });
    }

    private void handleUserInfoCallback(Bundle userInfo) {
        if (userInfo == null) {
            showPrompt(this.mErrorPrompt, 1);
            return;
        }
        String nickname = userInfo.getString("nickname");
        String phone = userInfo.getString("phone");
        String avatarUrl = userInfo.getString("headimage");
        String uid = KVUtils.get(getIntent(), "uid");
        if (!TextUtils.empty(nickname) && !TextUtils.empty(phone)) {
            this.mUserAccount.setText(nickname + "(" + phone + ")");
        } else if (!TextUtils.empty(nickname)) {
            this.mUserAccount.setText(nickname);
        } else if (TextUtils.empty(phone)) {
            this.mUserAccount.setText(uid);
        } else {
            this.mUserAccount.setText(phone);
        }
        if (!TextUtils.empty(avatarUrl)) {
            doGetUserLogo(avatarUrl);
        }
    }

    private void handleAppInfoCallback(Bundle appInfo) {
        if (appInfo == null) {
            showPrompt(this.mErrorPrompt, 1);
            return;
        }
        String appName = appInfo.getString("appname");
        String appScope = appInfo.getString("scope");
        String appLogoUrl = appInfo.getString("applogo");
        if (!TextUtils.empty(appName)) {
            this.m3thName.setText(appName);
            String allow = String.format(L10NString.getString("umgr_allow_3th_access_format"), new Object[]{appName});
            if (!TextUtils.empty(allow)) {
                this.mOAuth2Prompt.setText(allow);
            }
        }
        if (!TextUtils.empty(appScope)) {
            Bundle bundle = parserScope(appScope);
            if (bundle != null) {
                String mAppScope = KVUtils.get(bundle, "scopediscribe");
                if (!TextUtils.empty(mAppScope)) {
                    setAppScope(mAppScope);
                }
            }
        }
        if (!TextUtils.empty(appLogoUrl)) {
            doGetAppLogo(appLogoUrl);
        }
    }

    private Bundle parserScope(String scope) {
        Exception e;
        if (TextUtils.isEmpty(scope)) {
            LOG.e(TAG, "[scope:" + scope + "] response is empty");
            return null;
        }
        if (scope.startsWith("[") && scope.endsWith("]")) {
            scope = scope.substring(1, scope.length() - 1);
        }
        Bundle bundle = null;
        try {
            Bundle bundle2 = new Bundle();
            try {
                JSONObject jo = new JSONObject(scope);
                JSONArray ja = jo.names();
                for (int i = 0; i < ja.length(); i++) {
                    String name = ja.getString(i);
                    KVUtils.put(bundle2, name, jo.getString(name));
                }
                bundle = bundle2;
            } catch (Exception e2) {
                e = e2;
                bundle = bundle2;
                LOG.e(TAG, "json parse failed(Exception)", e);
                LOG.d(TAG, "bundle : " + bundle.toString());
                return bundle;
            }
        } catch (Exception e3) {
            e = e3;
            LOG.e(TAG, "json parse failed(Exception)", e);
            LOG.d(TAG, "bundle : " + bundle.toString());
            return bundle;
        }
        LOG.d(TAG, "bundle : " + bundle.toString());
        return bundle;
    }

    private void doGetAppLogo(String appLogoUrl) {
        getWsApi().downLogo(appLogoUrl, new OnDownLogoListener() {
            public void onDone(int rcode, byte[] content) {
                if (rcode == 0) {
                    OAuth2Activity.this.setImage(OAuth2Activity.this.m3thLogo, content);
                }
            }
        });
    }

    private void doGetUserLogo(String avatarUrl) {
        getWsApi().downLogo(avatarUrl, new OnDownLogoListener() {
            public void onDone(int rcode, byte[] content) {
                if (rcode == 0) {
                    OAuth2Activity.this.setImage(OAuth2Activity.this.mUserLogo, content);
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.abortCountdown = true;
        return super.onKeyDown(keyCode, event);
    }

    private void doGetTokenOrAuthCode() {
        String uid = KVUtils.get(getIntent(), "uid");
        String tkt = KVUtils.get(getIntent(), "tkt");
        String scope = KVUtils.get(getIntent(), "scope");
        String responseType = KVUtils.get(getIntent(), LetvMasterParser.RESPONSETYPE);
        if (TextUtils.equal(responseType, "code")) {
            doGetAuthCode(uid, tkt, this.appId, scope);
        } else if (TextUtils.equal(responseType, UserInfoDb.TOKEN)) {
            doGetTokenImplicit(uid, tkt, this.appId, scope);
        } else if (TextUtils.equal(responseType, "360token")) {
            doGetqiku360TokenImplicit(uid, tkt, this.appId, scope);
        } else {
            doGetAuthCode(uid, tkt, this.appId, scope);
        }
    }

    private void doGetqiku360TokenImplicit(String uid, String tkt, String appId, String scope) {
        final String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId" + appId + "][scope:" + scope + "]";
        showProgress(true);
        final String str = uid;
        final String str2 = tkt;
        final String str3 = appId;
        getWsApi().getTokenImplicit(uid, tkt, appId, scope, new OnTokenListener() {
            public void onDone(int rcode, String openId, String accessToken, String refreshToken, long expiresin) {
                LOG.i(OAuth2Activity.TAG, prefix + " doGet qiku 360 Token implicit one step(get qiku token) callback(" + rcode + "," + openId + "," + accessToken + "," + refreshToken + "," + expiresin + ")");
                OAuth2Activity.this.showProgress(false);
                if (rcode == 0) {
                    Bundle result = new Bundle();
                    KVUtils.put(result, "openId", openId);
                    KVUtils.put(result, "accessToken", accessToken);
                    KVUtils.put(result, "refreshToken", refreshToken);
                    KVUtils.put(result, "expireTimeMillis", expiresin);
                    OAuth2Activity.this.doGet360Token(result, str, str2, str3);
                } else if (Rcode.isNeedRelogin(rcode) || Rcode.isUnbind(rcode)) {
                    OAuth2Activity.this.doRelogin4TKT();
                } else {
                    OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, rcode);
                }
            }
        });
    }

    private void doGet360Token(final Bundle input, String uid, String tkt, String appId) {
        final String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId" + appId + "]";
        showProgress(true);
        getWsApi().getQihooToken(appId, uid, tkt, "", CHANNEL.MOVIE_SPECIAL, new OnGetQihooTokenListener() {
            public void onDone(int rcode, String rtnmsg, String taccesstoken, String tuid, String tcookie, String qcookie) {
                LOG.i(OAuth2Activity.TAG, prefix + " doGet qiku 360 Token implicit two step(get 360 token) callback(" + rcode + "," + taccesstoken + "," + tuid + "," + tcookie + "," + qcookie + ")");
                OAuth2Activity.this.showProgress(false);
                if (rcode == 0) {
                    KVUtils.put(input, "tuid", tuid);
                    KVUtils.put(input, "taccesstoken", taccesstoken);
                    OAuth2Activity.this.handleResultOnFinish(input);
                    return;
                }
                OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, rcode);
            }
        });
    }

    private void doGetAuthCode(String uid, String tkt, final String appId, String scope) {
        final String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId" + appId + "][scope:" + scope + "]";
        showProgress(true);
        getWsApi().getAuthCode(uid, tkt, appId, scope, new OnAuthCodeListener() {
            public void onDone(int rcode, String code) {
                LOG.i(OAuth2Activity.TAG, prefix + " get auth code callback(" + rcode + "," + code + ")");
                OAuth2Activity.this.showProgress(false);
                if (rcode == 0) {
                    Bundle result = new Bundle();
                    KVUtils.put(result, "appId", appId);
                    KVUtils.put(result, "code", code);
                    KVUtils.put(result, "authCode", code);
                    OAuth2Activity.this.handleResultOnFinish(result);
                } else if (Rcode.isNeedRelogin(rcode) || Rcode.isUnbind(rcode)) {
                    OAuth2Activity.this.doRelogin4TKT();
                } else {
                    OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, rcode);
                }
            }
        });
    }

    private void doGetTokenImplicit(String uid, String tkt, String appId, String scope) {
        final String prefix = "[uid:" + uid + "][tkt:" + tkt + "][appId" + appId + "][scope:" + scope + "]";
        showProgress(true);
        getWsApi().getTokenImplicit(uid, tkt, appId, scope, new OnTokenListener() {
            public void onDone(int rcode, String openId, String accessToken, String refreshToken, long expiresin) {
                LOG.i(OAuth2Activity.TAG, prefix + " get token callback(" + rcode + "," + openId + "," + accessToken + "," + refreshToken + "," + expiresin + ")");
                OAuth2Activity.this.showProgress(false);
                if (rcode == 0) {
                    Bundle result = new Bundle();
                    KVUtils.put(result, "openId", openId);
                    KVUtils.put(result, "accessToken", accessToken);
                    KVUtils.put(result, "refreshToken", refreshToken);
                    KVUtils.put(result, "expireTimeMillis", expiresin);
                    OAuth2Activity.this.handleResultOnFinish(result);
                } else if (Rcode.isNeedRelogin(rcode) || Rcode.isUnbind(rcode)) {
                    OAuth2Activity.this.doRelogin4TKT();
                } else {
                    OAuth2Activity.this.showPrompt(OAuth2Activity.this.mErrorPrompt, rcode);
                }
            }
        });
    }

    private void doRelogin4TKT() {
        Executor.execute(new RunNoThrowable() {
            public void rundo() {
                TKTResolver resolver = TKTResolver.get(OAuth2Activity.this);
                Bundle input = new Bundle();
                KVUtils.put(input, OAuth2Activity.this.getIntent(), "screenOrientation");
                KVUtils.put(input, OAuth2Activity.this.getIntent(), "loginType");
                KVUtils.put(input, OAuth2Activity.this.getIntent(), "appId");
                resolver.relogin(OAuth2Activity.this, input, new 1(this));
            }
        });
    }
}
