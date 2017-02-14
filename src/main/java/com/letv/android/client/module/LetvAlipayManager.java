package com.letv.android.client.module;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import com.alipay.sdk.app.PayTask;
import com.letv.android.client.activity.LetvVipDialogActivity;
import com.letv.android.client.activity.PaySucceedActivity;
import com.letv.android.client.activity.VipOrderDetailActivity;
import com.letv.android.client.listener.AlipayOneKeyGetOrderInfoCallback;
import com.letv.android.client.task.RequestUserByTokenTask;
import com.letv.android.client.view.CustomLoadingDialog;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.ToastUtils;
import com.letv.core.utils.external.alipay.AlipayController;
import com.letv.core.utils.external.alipay.AlipayData;
import com.letv.core.utils.external.alipay.AlipayUtils;
import com.letv.core.utils.external.alipay.RequestValue;
import com.letv.core.utils.external.alipay.WxPayData;
import com.letv.core.utils.external.alipay.WxPayParser;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class LetvAlipayManager {
    public static long DISABLE_BACKKEY_TIME = 8000;
    public static final int VIP_DIALOG = 1;
    public static final int VIP_NORMAL = 2;
    public static String WX_PAY_APP_ID = "";
    private static final String WX_PAY_SUS = "1";
    private static LetvAlipayManager letvAlipayManager = null;
    private Activity activity = null;
    private IWXAPI api;
    private int fromFlag = 2;
    private boolean isCancel = false;
    private boolean isRegisterWx;
    private AlipayOneKeyGetOrderInfoCallback mAlipayOneKeyGetOrderInfoCallback;
    private CustomLoadingDialog mDialog = null;
    private float mPayPrice;
    PayTask mPayTask;
    private String mWxOrderNumber = "";
    private RequestValue requestValue = null;

    private LetvAlipayManager() {
    }

    public static LetvAlipayManager getInstance() {
        if (letvAlipayManager == null) {
            letvAlipayManager = new LetvAlipayManager();
        }
        return letvAlipayManager;
    }

    public void initBulid(Activity activity, RequestValue requestValue) {
        this.activity = activity;
        this.requestValue = requestValue;
    }

    public boolean isInstallAlipay(Activity activity) {
        return AlipayUtils.checkMobileSecurePayHelper(activity);
    }

    public void setFromFlag(int fromFlag) {
        this.fromFlag = fromFlag;
    }

    public void doWxpayClientTask(int fromFlag) {
        this.fromFlag = fromFlag;
        if (this.activity != null) {
            this.mDialog = new CustomLoadingDialog(this.activity);
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.setOnCancelListener(new 1(this));
            showDialog(this.mDialog);
            getWxpayClientTask();
        }
    }

    private void getWxpayClientTask() {
        LogInfo.log("ZSM", "getWxpayClientTask url == " + PayCenterApi.getInstance().requestWxpayData(0, this.requestValue));
        new LetvRequest(WxPayData.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestWxpayData(0, this.requestValue)).setCache(new VolleyNoCache()).setParser(new WxPayParser()).setCallback(new 2(this)).add();
    }

    private boolean initWxpayApi(String appId) {
        if (TextUtils.isEmpty(appId)) {
            ToastUtils.showToast(this.activity, this.activity.getString(2131100995));
            return false;
        }
        this.api = WXAPIFactory.createWXAPI(this.activity, appId);
        this.api.registerApp(appId);
        this.isRegisterWx = true;
        return true;
    }

    public void doAlipayClientTask(VipOrderDetailActivity activity) {
        if (activity != null) {
            this.mDialog = new CustomLoadingDialog(activity);
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.setOnDismissListener(new 3(this));
            showDialog(this.mDialog);
            LogInfo.log("ZSM", "doAlipayClientTask VipOrderDetailActivity url == " + PayCenterApi.getInstance().requestAlipayData(0, this.requestValue));
            new LetvRequest(AlipayData.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAlipayData(0, this.requestValue)).setCache(new VolleyNoCache()).setCallback(new 4(this, activity)).add();
        }
    }

    public void doAlipayClientTask(LetvVipDialogActivity activity) {
        if (activity != null) {
            this.mDialog = new CustomLoadingDialog(activity);
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.setOnDismissListener(new 5(this));
            showDialog(this.mDialog);
            LogInfo.log("ZSM", "doAlipayClientTask LetvVipDialogActivity url == " + PayCenterApi.getInstance().requestAlipayData(0, this.requestValue));
            new LetvRequest(AlipayData.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAlipayData(0, this.requestValue)).setCache(new VolleyNoCache()).setCallback(new 6(this, activity)).add();
        }
    }

    private void payByAli(AlipayData result, VipOrderDetailActivity activity) {
        AlipayController alipayController = new AlipayController(activity);
        alipayController.setPayInfo(result.getInfo(), result.getCorderid());
        alipayController.setAlipayCallback(activity);
        alipayController.pay();
    }

    public void setGetOrderInfoCallback(AlipayOneKeyGetOrderInfoCallback alipayOneKeyGetOrderInfoCallback) {
        this.mAlipayOneKeyGetOrderInfoCallback = alipayOneKeyGetOrderInfoCallback;
    }

    public void doAlipayContinuePayClientTask(VipOrderDetailActivity activity) {
        LogInfo.log("CRL 支付宝支付开始 支付宝自动续费之前请求订单信息");
        LogInfo.log(Log.getStackTraceString(new Throwable()));
        if (activity != null) {
            this.mDialog = new CustomLoadingDialog(activity);
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.setOnDismissListener(new 7(this));
            showDialog(this.mDialog);
            LogInfo.log("CRL 支付宝支付开始 支付宝自动续费之前请求订单信息 url == " + PayCenterApi.getInstance().requestAlipayData(0, this.requestValue));
            new LetvRequest(AlipayData.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAlipayData(0, this.requestValue)).setCache(new VolleyNoCache()).setCallback(new 8(this, activity)).add();
        }
    }

    public void startPaySucceedActivity(String orderId) {
        if (this.fromFlag == 1) {
            RequestUserByTokenTask.getUserByTokenTask(this.activity, PreferencesManager.getInstance().getSso_tk(), new 9(this));
        } else if (this.requestValue != null) {
            String str;
            Activity activity = this.activity;
            String productname = this.requestValue.getProductname();
            String orderId2 = this.requestValue.getOrderId();
            String formatDoubleNum = LetvUtils.formatDoubleNum(Double.valueOf((double) this.mPayPrice).doubleValue(), 2);
            String desc = this.requestValue.getDesc();
            if (TextUtils.isEmpty(orderId)) {
                str = this.mWxOrderNumber;
            } else {
                str = orderId;
            }
            PaySucceedActivity.launch(activity, productname, orderId2, formatDoubleNum, desc, str);
        }
    }

    public void finishWxPay() {
        if (this.isRegisterWx && this.api != null) {
            this.api.unregisterApp();
            this.isRegisterWx = false;
        }
    }

    public void setPayPrice(float payPrice) {
        this.mPayPrice = payPrice;
    }

    private boolean isPayPriceOverZero() {
        return this.mPayPrice > 0.0f;
    }

    private String createOrderInfo(AlipayData alipayData) {
        return alipayData.getInfo();
    }

    private void showDialog(Dialog dlg) {
        if (dlg != null) {
            try {
                if (dlg.isShowing()) {
                    dlg.cancel();
                } else {
                    dlg.show();
                }
            } catch (Exception e) {
            }
        }
    }
}
