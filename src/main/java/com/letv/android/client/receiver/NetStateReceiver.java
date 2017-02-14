package com.letv.android.client.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.letv.ads.ex.utils.AdsManagerProxy;
import com.letv.android.client.LetvApplication;
import com.letv.android.client.activity.MainActivity;
import com.letv.android.client.push.LetvPushService;
import com.letv.android.client.utils.LetvLiveBookUtil;
import com.letv.android.client.worldcup.util.NetWorkTypeUtils;
import com.letv.business.flow.unicom.UnicomWoFlowDialogUtils;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.DataHull;
import com.letv.core.bean.IPBean;
import com.letv.core.bean.LiveDateInfo;
import com.letv.core.bean.NetIpBean;
import com.letv.core.bean.TokenResultBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.VolleyResult;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.IPParser;
import com.letv.core.parser.LiveDateInfoParser;
import com.letv.core.parser.NetIpParser;
import com.letv.core.parser.TokenResultParser;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.NetworkUtils;
import com.letv.datastatistics.DataStatistics;
import com.letv.datastatistics.constant.LetvErrorCode;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class NetStateReceiver extends BroadcastReceiver {
    public static final int NOTIFY_ID = 1;
    private static int oldNetStatue = -1;
    Context context;

    public NetStateReceiver() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public void onReceive(final Context context, Intent intent) {
        this.context = context;
        if (NetworkUtils.isNetworkAvailable()) {
            requestNetIp();
        }
        LogInfo.log("zhuqiao", "NetStateReceiver:onReceive, action = " + intent.getAction());
        if (MainActivity.getInstance() == null) {
            LetvPushService.schedule(context);
            return;
        }
        if (NetworkUtils.isNetworkAvailable()) {
            context.stopService(new Intent(context, LetvPushService.class));
            context.startService(new Intent(context, LetvPushService.class));
        }
        int type = NetworkUtils.getNetworkType();
        LogInfo.log("zhuqiao", "NetStateReceiver:onReceive, networkInfo = " + type);
        if (type != 0) {
            boolean mobile;
            new LetvRequest().setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setUrl("http://api.letv.com/getipgeo").setParser(new IPParser()).setCallback(new SimpleResponse<IPBean>(this) {
                final /* synthetic */ NetStateReceiver this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onNetworkResponse(VolleyRequest<IPBean> volleyRequest, IPBean result, DataHull hull, NetworkResponseState state) {
                    if (state == NetworkResponseState.SUCCESS) {
                        LetvApplication.getInstance().setIp(result);
                    }
                }
            }).add();
            LetvLiveBookUtil.updateLiveBook(context);
            if (NetworkUtils.isWifi()) {
                mobile = false;
            } else {
                mobile = true;
            }
            if (mobile && oldNetStatue != -1 && oldNetStatue != NetWorkTypeUtils.getNetType(context) && NetworkUtils.isUnicom3G(false)) {
                LogInfo.log("zhuqiao", "network change initSDK");
                new UnicomWoFlowDialogUtils().showWoMainDialog(context, null, context.getClass().getSimpleName());
            }
            if (PreferencesManager.getInstance().isLogin() && !PreferencesManager.getInstance().isPlayCloud()) {
                new LetvRequest().setCache(new VolleyNoCache()).setParser(new TokenResultParser()).setRequestType(RequestManner.NETWORK_ONLY).setUrl("http://api.letv.com/getipgeo").setCallback(new SimpleResponse<TokenResultBean>(this) {
                    final /* synthetic */ NetStateReceiver this$0;

                    public void onNetworkResponse(VolleyRequest<TokenResultBean> volleyRequest, TokenResultBean result, DataHull hull, NetworkResponseState state) {
                        if (hull.errMsg != 0) {
                            PreferencesManager.getInstance().logoutUser();
                        }
                        if (state == NetworkResponseState.SUCCESS) {
                            PreferencesManager.getInstance().setisPlayCloud(true);
                        }
                    }

                    public void onErrorReport(VolleyRequest<TokenResultBean> volleyRequest, String errorInfo) {
                        DataStatistics.getInstance().sendErrorInfo(context, "0", "0", LetvErrorCode.LTURLModule_UC_VERTIFY_TOKEN, null, errorInfo, null, null, null, null);
                    }
                }).add();
            }
            new Thread(new Runnable(this) {
                final /* synthetic */ NetStateReceiver this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void run() {
                    VolleyResult<LiveDateInfo> result = new LetvRequest().setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setUrl(LetvUrlMaker.getDataUrl()).setParser(new LiveDateInfoParser()).syncFetch();
                    if (result.networkState == NetworkResponseState.SUCCESS && result.result != null) {
                        LetvApplication.getInstance().setLiveDateInfo((LiveDateInfo) result.result);
                    }
                }
            }).start();
        }
        int curNetStatus = NetworkUtils.getNetworkType();
        if (curNetStatus != oldNetStatue) {
            AdsManagerProxy.getInstance(context).notifyNetStatusChange(context, curNetStatus);
        }
        oldNetStatue = curNetStatus;
    }

    private void requestNetIp() {
        if (PreferencesManager.getInstance().isApplyPermissionsSuccess()) {
            new LetvRequest().setUrl(LetvUrlMaker.getNetIPAddress()).setRequestType(RequestManner.NETWORK_ONLY).setParser(new NetIpParser()).setCallback(new SimpleResponse<NetIpBean>(this) {
                final /* synthetic */ NetStateReceiver this$0;

                {
                    if (HotFix.PREVENT_VERIFY) {
                        System.out.println(VerifyLoad.class);
                    }
                    this.this$0 = this$0;
                }

                public void onNetworkResponse(VolleyRequest<NetIpBean> volleyRequest, NetIpBean result, DataHull hull, NetworkResponseState state) {
                    if (state == NetworkResponseState.SUCCESS) {
                        PreferencesManager.getInstance().setNetIp(result.clientIp);
                        LetvLogApiTool.getInstance().saveExceptionInfo("公网IP:" + result.clientIp);
                    }
                }
            }).add();
        }
    }
}
