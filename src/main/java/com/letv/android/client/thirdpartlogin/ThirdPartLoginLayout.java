package com.letv.android.client.thirdpartlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.coolcloud.uac.android.api.Coolcloud;
import com.coolcloud.uac.android.common.util.Executor;
import com.letv.android.client.R;
import com.letv.android.client.activity.LetvLoginActivity;
import com.letv.android.client.share.ShareUtils;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.tencentlogin.TencentInstance;
import com.letv.android.client.utils.UIs;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.CoolPadUserBean;
import com.letv.core.bean.UserBean;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.constant.LiveRoomConstant;
import com.letv.core.constant.LoginConstant;
import com.letv.core.constant.ShareConstant.Weixin;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.CoolPadUserParser;
import com.letv.core.parser.QQLoginParser;
import com.letv.core.parser.UserParser;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.StringUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.external.weibo.SinaWeiboUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.mm.sdk.modelmsg.SendAuth.Req;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

public class ThirdPartLoginLayout extends RelativeLayout implements OnClickListener {
    private Oauth2AccessToken mAccessToken;
    private Activity mActivity;
    private Context mContext;
    private Coolcloud mCoolcloud;
    private ImageView mCoolpadLoginIv;
    private IWXAPI mIWXAPI;
    private boolean mIsLoginPage;
    private ThirdPartLoginSuccessCallBack mLoginSuccessCallBack;
    private ImageView mSinaLoginIv;
    private SsoHandler mSsoHandler;
    private Tencent mTencent;
    private ImageView mTencentLoginIv;
    private RelativeLayout mViewRootLayout;
    private AuthInfo mWeiboAuth;
    private ImageView mWeixinLoginIv;
    private int src;

    public interface ThirdPartLoginSuccessCallBack {
        void loading(boolean z);

        void thirdPartLoginSuccess(UserBean userBean);
    }

    public ThirdPartLoginLayout(Context context, AttributeSet attrs, int defStyle) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        super(context, attrs, defStyle);
        this.mCoolcloud = null;
        this.src = 0;
        this.mIsLoginPage = true;
        this.mContext = context;
    }

    public ThirdPartLoginLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCoolcloud = null;
        this.src = 0;
        this.mIsLoginPage = true;
        this.mContext = context;
    }

    public ThirdPartLoginLayout(Context context) {
        super(context);
        this.mCoolcloud = null;
        this.src = 0;
        this.mIsLoginPage = true;
        this.mContext = context;
    }

    public void initActivity(Activity activity, ThirdPartLoginSuccessCallBack loginSuccessCallBack) {
        this.mActivity = activity;
        this.mLoginSuccessCallBack = loginSuccessCallBack;
    }

    public void setSsoHandler(int requestCode, int resultCode, Intent data) {
        LogInfo.log("CRL", "sina login setSsoHandler == " + this.mSsoHandler);
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void destroyActivity() {
        this.mActivity = null;
        this.mCoolcloud = null;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mViewRootLayout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.login_third_part, this, true);
        initUI();
    }

    private void initUI() {
        this.mWeixinLoginIv = (ImageView) this.mViewRootLayout.findViewById(R.id.weixin_login);
        this.mTencentLoginIv = (ImageView) this.mViewRootLayout.findViewById(R.id.tencent_login);
        this.mSinaLoginIv = (ImageView) this.mViewRootLayout.findViewById(R.id.sina_login);
        this.mCoolpadLoginIv = (ImageView) this.mViewRootLayout.findViewById(R.id.coolpad_login);
        this.mWeixinLoginIv.setOnClickListener(this);
        this.mTencentLoginIv.setOnClickListener(this);
        this.mSinaLoginIv.setOnClickListener(this);
        UIs.zoomView(290, 37, this.mWeixinLoginIv);
        UIs.zoomView(290, 37, this.mTencentLoginIv);
        UIs.zoomView(290, 37, this.mSinaLoginIv);
        if (isCoolCloudOS()) {
            this.mCoolpadLoginIv.setVisibility(0);
            this.mCoolpadLoginIv.setOnClickListener(this);
            UIs.zoomView(290, 37, this.mCoolpadLoginIv);
        } else {
            this.mCoolpadLoginIv.setVisibility(8);
        }
        this.mIsLoginPage = this.mContext instanceof LetvLoginActivity;
        this.mViewRootLayout.findViewById(R.id.login_logo).setVisibility(0);
    }

    public void initThirtPart() {
        this.mIWXAPI = WXAPIFactory.createWXAPI(this.mContext, Weixin.APP_ID);
        this.mIWXAPI.registerApp(Weixin.APP_ID);
        this.mTencent = TencentInstance.getInstance(this.mActivity);
        this.mCoolcloud = Coolcloud.get(BaseApplication.getInstance(), LoginConstant.COOLPAD_APP_ID);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weixin_login /*2131363839*/:
                weixinLoginClick();
                return;
            case R.id.tencent_login /*2131363840*/:
                tencentLoginClick();
                return;
            case R.id.sina_login /*2131363841*/:
                sinaLoginClick();
                return;
            case R.id.coolpad_login /*2131363842*/:
                coolpadLoginClick();
                return;
            default:
                return;
        }
    }

    private void weixinLoginClick() {
        LetvLogApiTool.getInstance().saveExceptionInfo("微信登录开始 Current Time :" + StringUtils.getTimeStamp());
        if (NetworkUtils.isNetworkAvailable()) {
            String str;
            if (ShareUtils.checkBrowser(this.mActivity, "com.tencent.mm") && ShareUtils.isSpecificActivityExsit(this.mActivity)) {
                Req req = new Req();
                req.scope = "snsapi_userinfo";
                req.state = "none_weixin_login";
                this.mIWXAPI.sendReq(req);
            } else {
                UIs.callDialogMsgPositiveButton(this.mActivity, DialogMsgConstantId.SEVEN_ZERO_SEVEN_CONSTANT, null);
            }
            LogInfo.LogStatistics("微信注册");
            String pageId = this.mIsLoginPage ? PageIdConstant.loginPage : PageIdConstant.registerPage;
            String ep = "ref=" + pageId + "_072_3";
            Context context = this.mActivity;
            String str2 = "0";
            if (this.mIsLoginPage) {
                str = "c72";
            } else {
                str = "c82";
            }
            StatisticsUtils.statisticsActionInfo(context, pageId, str2, str, null, 3, ep);
            return;
        }
        ToastUtils.showToast(2131101012);
    }

    private void tencentLoginClick() {
        LetvLogApiTool.getInstance().saveExceptionInfo("QQ登录开始 Current Time :" + StringUtils.getTimeStamp());
        if (NetworkUtils.isNetworkAvailable()) {
            LogInfo.LogStatistics("qq注册");
            String pageId = this.mIsLoginPage ? PageIdConstant.loginPage : PageIdConstant.registerPage;
            StatisticsUtils.statisticsActionInfo(this.mActivity, pageId, "0", this.mIsLoginPage ? "c72" : "c82", null, 1, "ref=" + pageId + "_072_1");
            if (this.mTencent.isSupportSSOLogin(this.mActivity)) {
                this.mTencent.logout(this.mActivity);
                this.mTencent.login(this.mActivity, LiveRoomConstant.CHANNEL_TYPE_ALL, new 1(this));
                return;
            }
            LetvOpenIDOAuthLoginActivity.launch(this.mActivity, PlayRecordApi.getInstance().getQQLoginUrl(), getResources().getString(2131100348));
            return;
        }
        ToastUtils.showToast(2131101012);
    }

    private void sinaLoginClick() {
        LetvLogApiTool.getInstance().saveExceptionInfo("sina登录开始 Current Time :" + StringUtils.getTimeStamp());
        if (NetworkUtils.isNetworkAvailable()) {
            LogInfo.LogStatistics("新浪微博注册");
            String pageId = this.mIsLoginPage ? PageIdConstant.loginPage : PageIdConstant.registerPage;
            StatisticsUtils.statisticsActionInfo(this.mActivity, pageId, "0", this.mIsLoginPage ? "c72" : "c82", null, 2, "ref=" + pageId + "_072_2");
            if (SinaWeiboUtils.isWeiboAppSupportAPI(this.mActivity, "3830215581", false)) {
                this.mWeiboAuth = new AuthInfo(this.mActivity, "3830215581", "http://m.letv.com", "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write");
                this.mSsoHandler = new SsoHandler(this.mActivity, this.mWeiboAuth);
                this.mSsoHandler.authorize(new AuthListener(this));
                return;
            }
            LetvOpenIDOAuthLoginActivity.launch(this.mActivity, ShareUtils.getSinaLoginUrl(), getResources().getString(2131100352));
            return;
        }
        ToastUtils.showToast(2131101012);
    }

    private void coolpadLoginClick() {
        LetvLogApiTool.getInstance().saveExceptionInfo("酷派登录开始 Current Time :" + StringUtils.getTimeStamp());
        if (NetworkUtils.isNetworkAvailable()) {
            LogInfo.LogStatistics("酷派登录");
            StatisticsUtils.statisticsActionInfo(this.mActivity, PageIdConstant.loginPage, "0", "072", null, 5, "ref=" + PageIdConstant.loginPage + "_072_5");
            Executor.execute(new 2(this));
            return;
        }
        ToastUtils.showToast(2131101012);
    }

    public void requestUserInfoFromToken(String url) {
        this.mLoginSuccessCallBack.loading(true);
        this.mTencentLoginIv.setEnabled(true);
        this.mWeixinLoginIv.setEnabled(true);
        LogInfo.log("ZSM", "qq login url == " + url);
        LetvLogApiTool.getInstance().saveExceptionInfo("第三方登录开始 Current Time :" + StringUtils.getTimeStamp() + "　url == " + url);
        new LetvRequest(UserBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(url).setCache(new VolleyNoCache()).setParser(new QQLoginParser()).setCallback(new 3(this)).add();
    }

    private void mRequestLoginTaskCallBack(RequestManner requestType, String tk) {
        LogInfo.log("ZSM", "mRequestLoginTaskCallBack URL == " + PlayRecordApi.getInstance().openIDOAuthLoginUrl(0, tk));
        new LetvRequest(UserBean.class).setRequestType(requestType).setUrl(PlayRecordApi.getInstance().openIDOAuthLoginUrl(0, tk)).setParser(new UserParser()).setCallback(new 4(this)).add();
    }

    public void getUserByToken() {
        RequestUserByTokenTask.getUserByTokenTask(this.mActivity, PreferencesManager.getInstance().getSso_tk(), new 5(this));
    }

    private void requestUserInfoWithCoolpad(String code) {
        this.mLoginSuccessCallBack.loading(true);
        String coolpadLoginURL = PlayRecordApi.getInstance().getCoolpadTokenByCode(code, LetvUtils.getGSMInfo(this.mActivity));
        LogInfo.log("coolpad url = " + coolpadLoginURL);
        new LetvRequest(CoolPadUserBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(coolpadLoginURL).setParser(new CoolPadUserParser()).setCallback(new 6(this)).add();
    }

    public static boolean isCoolCloudOS() {
        String phoneTAG = Build.MANUFACTURER;
        return phoneTAG.equalsIgnoreCase("yulong") || phoneTAG.equalsIgnoreCase("coolpad") || phoneTAG.equalsIgnoreCase("IVVI");
    }
}
