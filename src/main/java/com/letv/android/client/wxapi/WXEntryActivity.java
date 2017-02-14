package com.letv.android.client.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.RegisterActivity;
import com.letv.android.client.commonlib.share.WXShareResultObserver;
import com.letv.android.client.controller.RedPacketSdkController;
import com.letv.android.client.share.LetvWeixinShare;
import com.letv.android.client.utils.UIs;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.DataHull;
import com.letv.core.constant.ShareConstant.Weixin;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.utils.LetvTools.PointsUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.ToastUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.pp.utils.NetworkUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.util.ArrayList;
import java.util.List;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    public static List<WXShareResultObserver> observers = new ArrayList();
    private IWXAPI api;
    private LetvApplication mLetvApplication;

    public WXEntryActivity() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        if (this.mLetvApplication == null) {
            this.mLetvApplication = (LetvApplication) getApplicationContext();
        }
        this.mLetvApplication.setWxisShare(true);
        this.api = WXAPIFactory.createWXAPI(this, Weixin.APP_ID, true);
        this.api.handleIntent(getIntent(), this);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.api.handleIntent(intent, this);
    }

    public void onReq(BaseReq arg0) {
    }

    protected void onPause() {
        super.onPause();
        this.mLetvApplication.setWxisShare(false);
    }

    public void onResp(BaseResp resq) {
        if (resq != null && (resq instanceof Resp) && resq.getType() == 1) {
            Resp resps = (Resp) resq;
            if (resps != null) {
                try {
                    if (resps.state.equals("none_weixin_login")) {
                        getCode(resps);
                    }
                } catch (Exception e) {
                    finish();
                    e.printStackTrace();
                }
            }
        } else if (resq.getType() != 4) {
            try {
                switch (resq.errCode) {
                    case -5:
                    case -4:
                    case -3:
                    case -1:
                        ToastUtils.showToast((Context) this, getString(2131100850));
                        if (observers != null && observers.size() > 0) {
                            for (WXShareResultObserver observer : observers) {
                                observer.onWXShareFail();
                            }
                            break;
                        }
                    case -2:
                        if (observers != null && observers.size() > 0) {
                            for (WXShareResultObserver observer2 : observers) {
                                observer2.onWXCanneled();
                            }
                            break;
                        }
                    case 0:
                        ToastUtils.showToast((Context) this, getString(2131100851));
                        LogInfo.log("lxx", "weixin ERR_OK");
                        if (PointsUtils.canShareGainPoints()) {
                        }
                        if (LetvWeixinShare.isShareFriendZone) {
                            LogInfo.log("fornia", "isShareFriendZone true LetvWeixinShare.mFragId" + LetvWeixinShare.mFragId + "|LetvWeixinShare.mStaticsId" + LetvWeixinShare.mStaticsId);
                            if (!TextUtils.isEmpty(LetvWeixinShare.mFragId)) {
                                StatisticsUtils.staticticsInfoPost(this, "19", LetvWeixinShare.mFragId, "5001", 1, null, LetvWeixinShare.mStaticsId, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                            }
                        } else {
                            LogInfo.log("fornia", "isShareFriendZone false LetvWeixinShare.mFragId" + LetvWeixinShare.mFragId + "|LetvWeixinShare.mStaticsId" + LetvWeixinShare.mStaticsId);
                            if (!TextUtils.isEmpty(LetvWeixinShare.mFragId)) {
                                StatisticsUtils.staticticsInfoPost(this, "19", LetvWeixinShare.mFragId, "5002", 2, null, LetvWeixinShare.mStaticsId, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE, NetworkUtils.DELIMITER_LINE);
                            }
                        }
                        LogInfo.log("fornia", "showAwardPage LetvWeixinShare.mGiftShareAwardCallback:" + LetvWeixinShare.mGiftShareAwardCallback + "|" + LetvWeixinShare.mAwardUrl);
                        if (!(LetvWeixinShare.mGiftShareAwardCallback == null || TextUtils.isEmpty(LetvWeixinShare.mAwardUrl))) {
                            LetvWeixinShare.mGiftShareAwardCallback.showAwardPage(LetvWeixinShare.mAwardUrl);
                            LetvWeixinShare.mGiftShareAwardCallback = null;
                            LetvWeixinShare.mAwardUrl = "";
                        }
                        if (LetvWeixinShare.mLaunchMode == 22 && LetvWeixinShare.mActivity != null) {
                            LetvWeixinShare.mLaunchMode = -1;
                            RedPacketSdkController.shareSuccess(LetvWeixinShare.mActivity);
                        }
                        if (observers != null && observers.size() > 0) {
                            for (WXShareResultObserver observer22 : observers) {
                                LogInfo.log("lxx", "observer.onWXShareSucceed()");
                                observer22.onWXShareSucceed();
                            }
                            break;
                        }
                    default:
                        if (observers != null && observers.size() > 0) {
                            for (WXShareResultObserver observer222 : observers) {
                                observer222.onWXShareFail();
                            }
                            break;
                        }
                }
                LogInfo.log("wx", "WXEntryActivity -------------- onResp arg0 = " + resq.errCode);
                finish();
            } catch (Exception e2) {
                finish();
                e2.printStackTrace();
            }
        }
    }

    private void getCode(Resp resps) {
        LogInfo.log("ZSM", "BaseResp == " + resps.errCode + "  resps.code == " + resps.code);
        switch (resps.errCode) {
            case -5:
                ToastUtils.showToast((Context) this, getString(2131100346));
                return;
            case 0:
                getAccessTokenByCode(resps.code, Weixin.APP_ID, Weixin.APP_KEY);
                return;
            default:
                return;
        }
    }

    private void getAccessTokenByCode(String code, String appid, String secret) {
        LogInfo.log("ZSM", "getAccessTokenByCode code == " + code + "  appid == " + appid + " secret == " + secret);
        new LetvRequest(WXLoginBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.getInstance().getWXTokenByCode(0, code, appid, secret, "authorization_code")).setCallback(new SimpleResponse<WXLoginBean>(this) {
            final /* synthetic */ WXEntryActivity this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<WXLoginBean> volleyRequest, WXLoginBean result, DataHull hull, NetworkResponseState state) {
                LogInfo.log("ZSM", "getAccessTokenByCode onNetworkResponse == " + state);
                switch (state) {
                    case SUCCESS:
                        LogInfo.log("ZSM", "wxloginbean   " + result.toString());
                        WXShareprefrenceKeeper.writeAccessToken(this.this$0, result.getmWxAccessToken());
                        WXShareprefrenceKeeper.writeExpiresin(this.this$0, result.getmWxExpiresIn());
                        WXShareprefrenceKeeper.writeRefreshToken(this.this$0, result.getmRefreshToken());
                        Intent intent = new Intent("wxLoginAction");
                        intent.putExtra("access_token", result.getmWxAccessToken());
                        intent.putExtra("openid", result.getmOpenId());
                        this.this$0.sendBroadcast(intent);
                        this.this$0.sendBroadcast(new Intent(RegisterActivity.registedAction));
                        if (PreferencesManager.getInstance().getClickLetvLogin()) {
                            this.this$0.sendBroadcast(new Intent("finishVipPageAction"));
                        }
                        LogInfo.LogStatistics("微信登陆成功");
                        StatisticsUtils.statisticsActionInfo(this.this$0, null, "0", "c75", null, 3, "time=" + LetvUtils.timeClockString("yyyyMMdd_HH:mm:ss"));
                        this.this$0.finish();
                        return;
                    case NETWORK_ERROR:
                        ToastUtils.showToast(this.this$0, 2131101011);
                        return;
                    case RESULT_ERROR:
                        UIs.call(this.this$0, hull.errMsg, null);
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<WXLoginBean> request, String errorInfo) {
                LogInfo.log("ZSM", "getAccessTokenByCode onErrorReport == " + errorInfo);
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mLetvApplication.setWxisShare(false);
    }
}
