package com.letv.android.client.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.R;
import com.letv.android.client.commonlib.activity.LetvBaseActivity;
import com.letv.android.client.commonlib.bean.RedPacketFrom;
import com.letv.android.client.commonlib.config.LetvLoginActivityConfig;
import com.letv.android.client.controller.RedPacketSdkController;
import com.letv.android.client.fragment.LoginFragment;
import com.letv.android.client.fragment.LoginFragment.LoginFrom;
import com.letv.android.client.fragment.LoginFragment.LoginSuccessCallback;
import com.letv.android.client.receiver.TokenLoseReceiver;
import com.letv.android.client.thirdpartlogin.HongKongLoginWebview;
import com.letv.android.client.utils.MainLaunchUtils;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.constant.FragmentConstant;
import com.letv.core.constant.LetvConstant.Global;
import com.letv.core.constant.LoginConstant;
import com.letv.core.constant.PlayConstant;
import com.letv.core.constant.VipProductContant;
import com.letv.core.contentprovider.UserInfoTools;
import com.letv.core.db.DBManager;
import com.letv.core.db.PreferencesManager;
import com.letv.core.messagebus.config.LeMessageIds;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.utils.ActivityUtils;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lemallsdk.LemallPlatform;
import com.letv.redpacketsdk.RedPacketSdkManager;
import java.util.ArrayList;
import java.util.List;

public class LetvLoginActivity extends LetvBaseActivity implements LoginSuccessCallback {
    public static final String FINISHVIPPAGEACTION = "finishVipPageAction";
    public static final String FINISH_VIP_PAGE_ACTION = "finishVipPageAction";
    public static final int FORPLAY = 1;
    public static final int LOGIN = 16;
    public static final String WXLOGINACTION = "wxLoginAction";
    public static final String WX_LOGIN_ACTION = "wxLoginAction";
    private static List<Activity> mLoginActivities = new ArrayList();
    private boolean mIsFromThird;
    private LoginFragment mLoginFragment;
    private String mRedPacketAwardUrl;
    private int mforWhat;
    private BroadcastReceiver receiver;

    public LetvLoginActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mLoginFragment = new LoginFragment(LoginFrom.LOGIN, this);
        this.mIsFromThird = false;
        this.mforWhat = 0;
        this.mRedPacketAwardUrl = "";
        this.receiver = new BroadcastReceiver(this) {
            final /* synthetic */ LetvLoginActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("wxLoginAction") && !PreferencesManager.getInstance().getHaveLoginPage()) {
                    this.this$0.mLoginFragment.mThirdPartLoginLayout.requestUserInfoFromToken(PlayRecordApi.getInstance().loginWeixin(0, intent.getStringExtra("access_token"), intent.getStringExtra("openid"), "mobile_tv", "androidphone", Global.DEVICEID, Global.VERSION, Global.PCODE, Global.VERSION_CODE + ""));
                }
                if (intent.getAction().equals("finishVipPageAction")) {
                    LogInfo.log("LM", "收到广播     FINISHVIPPAGEACTION ");
                    this.this$0.finish();
                }
            }
        };
    }

    public static void launch(Context context) {
        if (LoginConstant.isHongKong()) {
            HongKongLoginWebview.launch((Activity) context);
            return;
        }
        Intent intent = new Intent(context, LetvLoginActivity.class);
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static void launch(Activity context) {
        if (LoginConstant.isHongKong()) {
            HongKongLoginWebview.launch(context);
        } else {
            context.startActivityForResult(new Intent(context, LetvLoginActivity.class), 16);
        }
    }

    public static void launch(int requestCode, Activity context) {
        if (LoginConstant.isHongKong()) {
            HongKongLoginWebview.launch(context);
        } else {
            context.startActivityForResult(new Intent(context, LetvLoginActivity.class), requestCode);
        }
    }

    public static void launch(Activity context, boolean from) {
        int i = 17;
        if (LoginConstant.isHongKong()) {
            HongKongLoginWebview.launch(context);
            return;
        }
        int i2;
        Intent intent = new Intent(context, LetvLoginActivity.class);
        String str = LetvLoginActivityConfig.FROM_HOME;
        if (from) {
            i2 = 17;
        } else {
            i2 = 16;
        }
        intent.putExtra(str, i2);
        if (!from) {
            i = 16;
        }
        context.startActivityForResult(intent, i);
    }

    public static void launch(Activity context, int forWhat) {
        if (LoginConstant.isHongKong()) {
            HongKongLoginWebview.launch(context, forWhat);
            return;
        }
        Intent intent = new Intent(context, LetvLoginActivity.class);
        intent.putExtra(LetvLoginActivityConfig.FOR_WHAT, forWhat);
        context.startActivityForResult(intent, 16);
    }

    public static void launch(Activity context, int forWhat, int requestCode) {
        if (LoginConstant.isHongKong()) {
            HongKongLoginWebview.launch(context);
            return;
        }
        Intent intent = new Intent(context, LetvLoginActivity.class);
        intent.putExtra(LetvLoginActivityConfig.FOR_WHAT, forWhat);
        context.startActivityForResult(intent, requestCode);
    }

    protected void onCreate(Bundle savedInstanceState) {
        getActivity().getWindow().setBackgroundDrawableResource(2131493324);
        if (!(getIntent() == null || getIntent().getExtras() == null)) {
            this.mIsFromThird = getIntent().getBooleanExtra(PlayConstant.BACK, false);
            this.mforWhat = getIntent().getIntExtra(LetvLoginActivityConfig.FOR_WHAT, 0);
            this.mRedPacketAwardUrl = getIntent().getStringExtra(LetvLoginActivityConfig.AWARDURL);
        }
        if (this.mIsFromThird) {
            this.mKeepSingle = false;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.letv_login_main);
        IntentFilter filter = new IntentFilter();
        filter.addAction("wxLoginAction");
        filter.addAction("finishVipPageAction");
        registerReceiver(this.receiver, filter);
        mLoginActivities.add(this);
        setRedPacketFrom(new RedPacketFrom(0));
    }

    protected void onStop() {
        super.onStop();
        UIsUtils.hideInputMethod(getActivity());
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mIsFromThird) {
            LogInfo.log("zhuqiao", "来自第三方登录跳转");
            ActivityUtils.getInstance().removeAll();
        }
        if (this.receiver != null) {
            unregisterReceiver(this.receiver);
        }
        if (mLoginActivities != null) {
            mLoginActivities.clear();
        }
        if (this.mLoginFragment != null && this.mLoginFragment.mThirdPartLoginLayout != null) {
            this.mLoginFragment.mThirdPartLoginLayout.destroyActivity();
        }
    }

    protected void onResume() {
        super.onResume();
        showFragmentIfNeeded(this.mLoginFragment);
    }

    public String[] getAllFragmentTags() {
        return FragmentConstant.TAG_LOGIN;
    }

    public String getActivityName() {
        return LetvLoginActivity.class.getName();
    }

    public Activity getActivity() {
        return this;
    }

    public void loginSuccess() {
        LogInfo.log("ZSM", "登陆成功后的数据统一处理");
        TokenLoseReceiver.sTokenTostShow = false;
        if (TextUtils.isEmpty(this.mRedPacketAwardUrl)) {
            setResult(1);
        } else {
            Intent redPacketIntent = new Intent();
            redPacketIntent.putExtra(LetvLoginActivityConfig.AWARDURL, this.mRedPacketAwardUrl);
            setResult(1, redPacketIntent);
        }
        if (PreferencesManager.getInstance().isVip()) {
            LetvUtils.sendBroadcast(this.mContext, VipProductContant.ACTION_VIP_AUTH_PASS);
        }
        try {
            LeMessageManager.getInstance().dispatchMessage(this, new LeMessage(LeMessageIds.MSG_WEBVIEW_SYNC_LOGIN));
            UserInfoTools.login(this, PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getSso_tk(), PreferencesManager.getInstance().getShareUserId(), PreferencesManager.getInstance().getShareToken());
            StatisticsUtils.statisticsLoginAndEnv(this, 2, true);
            StatisticsUtils.statisticsLoginAndEnv(this, 2, false);
        } catch (Exception e) {
        }
        RedPacketSdkManager.getInstance().setUid(PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getUserId() : "");
        RedPacketSdkManager.getInstance().setToken(PreferencesManager.getInstance().isLogin() ? PreferencesManager.getInstance().getSso_tk() : "");
        try {
            LemallPlatform.getInstance().setSsoToken(PreferencesManager.getInstance().getSso_tk());
        } catch (Exception e2) {
        }
        if (8 != this.mforWhat) {
            DBManager.getInstance().getFavoriteTrace().requestPostFavouriteThenDeleteDbBean();
        }
        if (3 == this.mforWhat) {
            MainLaunchUtils.jump2My(this);
        }
        if (BaseTypeUtils.isListEmpty(mLoginActivities)) {
            finish();
        } else {
            for (Activity activity : mLoginActivities) {
                if (!(activity == null || activity.isFinishing())) {
                    activity.finish();
                }
            }
            mLoginActivities.clear();
        }
        if (LoginConstant.LOGIN_FROM_RED_PACKET == this.mforWhat) {
            RedPacketSdkController.jumpRedPacketList(getActivity());
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            LogInfo.log("ZSM", "登陆成功返回到登陆页面");
            loginSuccess();
        } else if (this.mLoginFragment != null && this.mLoginFragment.mThirdPartLoginLayout != null) {
            this.mLoginFragment.mThirdPartLoginLayout.setSsoHandler(requestCode, resultCode, data);
        }
    }
}
