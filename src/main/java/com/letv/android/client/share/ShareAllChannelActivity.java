package com.letv.android.client.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.fragment.PersonalInfoFragment;
import com.letv.android.client.utils.UIs;
import com.letv.core.BaseApplication;
import com.letv.core.constant.LetvConstant.DialogMsgConstantId;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.sina.weibo.sdk.auth.sso.SsoHandler;

public class ShareAllChannelActivity extends LetvBaseActivity {
    private static final String LETVCLIENT = "letvclient";
    public static String invoker = "";
    public static String packageName = "";
    private String channel;
    private BaseApplication mLetvApplication;
    private SsoHandler mSsoHandler;

    public ShareAllChannelActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.share_all_channel);
        LogInfo.log("fornia", "ShareAllChannelActivity onCreate getIntent():" + getIntent() + getIntent() + getIntent().getPackage() + getIntent().getComponent().getPackageName() + getIntent().getComponent().getClassName());
        if (this.mLetvApplication == null) {
            this.mLetvApplication = (BaseApplication) getApplicationContext();
        }
        if (getIntent() != null && getIntent().getData() != null) {
            LogInfo.log("fornia", "ShareAllChannelActivity onCreate getIntent().getData():" + getIntent().getData());
            new Thread(new Runnable(this) {
                final /* synthetic */ ShareAllChannelActivity this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void run() {
                    this.this$0.resolveUri(this.this$0.getIntent(), this.this$0.getIntent().getData());
                }
            }).start();
        }
    }

    private void resolveUri(Intent intent, Uri data) {
        String scheme = intent.getScheme();
        if (scheme != null && "letvclient".equalsIgnoreCase(scheme)) {
            String actionType = "";
            String appName = "";
            String title = "";
            String officialSite = "";
            String text = "";
            String imgUrl = "";
            String playUrl = "";
            String from = "";
            try {
                actionType = data.getQueryParameter("actionType");
                appName = data.getQueryParameter("appName");
                title = data.getQueryParameter("title");
                officialSite = data.getQueryParameter("officialSite");
                text = data.getQueryParameter("text");
                imgUrl = data.getQueryParameter("imgUrl");
                playUrl = data.getQueryParameter("playUrl");
                from = data.getQueryParameter("from");
                packageName = data.getQueryParameter("package");
                invoker = data.getQueryParameter("invoker");
                LogInfo.log("fornia", "ShareAllChannelActivity  getIntent()" + actionType + "|" + appName + "|" + title + "|" + officialSite + "|" + text + "|" + imgUrl + "|" + playUrl + "|" + packageName + "|" + invoker + "|" + from);
                share2Channel(actionType, appName, title, officialSite, text, imgUrl, playUrl, from);
            } catch (NullPointerException e) {
                LogInfo.log("fornia", "NullPointerException e:" + e.getMessage() + "|" + e.toString());
                ToastUtils.showToast(getString(2131100833));
            } catch (Exception e2) {
                ToastUtils.showToast("三方传入参数未知错误，停止分享！");
                LogInfo.log("fornia", "Exception e:" + e2.getMessage() + "|" + e2.toString());
            }
        }
    }

    private void share2Channel(String type, String appName, String title, String website, String content, String imgUrl, String playUrl, String from) {
        if (!TextUtils.isEmpty(type)) {
            try {
                switch (Integer.valueOf(type).intValue()) {
                    case 0:
                        onShareWxTimeLine(true, appName, title, website, content, imgUrl, playUrl, from);
                        return;
                    case 1:
                        onShareWxTimeLine(false, appName, title, website, content, imgUrl, playUrl, from);
                        return;
                    case 2:
                        onShareSina(appName, title, website, content, imgUrl, playUrl, from);
                        return;
                    case 3:
                        onShareQzone(appName, title, website, content, imgUrl, playUrl, from);
                        return;
                    case 4:
                        onShareTencent(appName, title, website, content, imgUrl, playUrl, from);
                        return;
                    case 5:
                        onShareTencentWeibo(appName, title, website, content, imgUrl, playUrl, from);
                        return;
                    case 100:
                        runOnUiThread(new Runnable(this) {
                            final /* synthetic */ ShareAllChannelActivity this$0;

                            {
                                if (HotFix.PREVENT_VERIFY) {
                                    System.out.println(VerifyLoad.class);
                                }
                                this.this$0 = this$0;
                            }

                            public void run() {
                                PersonalInfoFragment.sysLogout(this.this$0);
                                this.this$0.finish();
                            }
                        });
                        return;
                    default:
                        return;
                }
            } catch (NumberFormatException e) {
                LogInfo.log("fornia", "NumberFormatException e:" + e.getMessage() + "|" + e.toString());
            }
        }
    }

    public void onShareWxTimeLine(boolean isWxTimeLine, String appName, String title, String website, String content, String imgUrl, String playUrl, String from) {
        if (ShareUtils.checkPackageInstalled(this, "com.tencent.mm")) {
            this.mLetvApplication.setWxisShare(true);
            LetvWeixinShare.share(this, title, content, playUrl, isWxTimeLine, 10, PageIdConstant.halpPlayPage, "s10");
            if (isWxTimeLine) {
                StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5001", 1, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
            } else {
                StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5002", 2, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
            }
        } else {
            UIs.callDialogMsgPositiveButton(this, DialogMsgConstantId.SEVEN_ZERO_SEVEN_CONSTANT, null);
        }
        this.channel = "4";
    }

    public void onShareSina(String appName, String title, String website, String content, String imgUrl, String playUrl, String from) {
        if (LetvSinaShareSSO.isLogin(this) == 1 || LetvSinaShareSSO.isLogin2(this)) {
            this.mSsoHandler = LetvSinaShareSSO.login(this, appName, title, content, imgUrl, playUrl, from, 10, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5003", 3, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            this.mSsoHandler = LetvSinaShareSSO.login(this, appName, title, content, imgUrl, playUrl, from, 10, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5003", 3, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else {
            ToastUtils.showToast((Context) this, 2131101012);
        }
        this.channel = "1";
    }

    public void onShareQzone(String appName, String title, String website, String content, String imgUrl, String playUrl, String from) {
        LetvQZoneShare.getInstance(this).gotoSharePage((Activity) this, title, content, imgUrl, playUrl, 10, PageIdConstant.halpPlayPage, "s10");
        StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5004", 4, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        this.channel = "3";
        finish();
    }

    public void onShareTencent(String appName, String title, String website, String content, String imgUrl, String playUrl, String from) {
        letvTencentShare.getInstance(this).gotoSharePage((Activity) this, title, content, imgUrl, playUrl, 10, PageIdConstant.halpPlayPage, "s10");
        StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5005", 5, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        finish();
    }

    public void onShareTencentWeibo(String appName, String title, String website, String content, String imgUrl, String playUrl, String from) {
        if (LetvTencentWeiboShare.isLogin(this) == 1) {
            LetvTencentWeiboShare.login((Activity) this, title, 2, website, "", 10, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5006", 6, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else if (com.letv.core.utils.NetworkUtils.isNetworkAvailable()) {
            LetvTencentWeiboShare.login((Activity) this, title, 2, website, "", 10, PageIdConstant.halpPlayPage, "s10");
            StatisticsUtils.staticticsInfoPost(this, "4", "h223", "5006", 6, null, PageIdConstant.halpPlayPage, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
        } else {
            ToastUtils.showToast((Context) this, 2131101012);
        }
        this.channel = "2";
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.mSsoHandler != null) {
            this.mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public String[] getAllFragmentTags() {
        return null;
    }

    public String getActivityName() {
        return ShareAllChannelActivity.class.getSimpleName();
    }

    public Activity getActivity() {
        return this;
    }
}
