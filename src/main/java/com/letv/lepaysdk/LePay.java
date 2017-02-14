package com.letv.lepaysdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.letv.lepaysdk.activity.CashierAcitivity;
import com.letv.lepaysdk.model.LePayTradeInfo;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.lepaysdk.network.NetworkManager;
import com.letv.lepaysdk.utils.PackageUtils;
import com.letv.lepaysdk.utils.ThreadUtil;
import java.util.HashMap;
import java.util.Map;

public class LePay {
    private static LePay sInstance;
    private LePayConfig config = new LePayConfig();
    private Map<String, ILePayCallback> mCallbackMap;
    private Context mContext;
    private NetworkManager mNetworkManager;
    private TradeManager mTradeManager;

    public void setConfig(LePayConfig config) {
        this.config = config;
    }

    private LePay(Context context) {
        this.mContext = context;
        this.mTradeManager = TradeManager.getInstance();
        this.mNetworkManager = NetworkManager.getInstance(context);
        this.mCallbackMap = new HashMap();
    }

    public void setSign(String signKey) {
        this.mNetworkManager.setSign(signKey);
    }

    public static LePay getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LePay(context);
        }
        return sInstance;
    }

    public void pay(String paramUrl, ILePayCallback callback) {
        if (!PackageUtils.isPkgInstalled(this.mContext, "com.letv.lepaysdk")) {
            ThreadUtil.execUi(new 1(this, callback, paramUrl), new Void[0]);
        }
    }

    public void pay(LePayTradeInfo tradeInfo, ILePayCallback callback) {
        if (!PackageUtils.isPkgInstalled(this.mContext, "com.letv.lepaysdk")) {
            Intent intent = new Intent(this.mContext, CashierAcitivity.class);
            intent.putExtra(TradeInfo.LEPAY_TRADEINFO_PARAM, tradeInfo);
            intent.setFlags(268435456);
            intent.setPackage(this.mContext.getPackageName());
            ((Activity) this.mContext).startActivityForResult(intent, 4);
        }
    }

    public void finishPay(String key, ELePayState status, String message) {
        if (key != null && message != null && this.mCallbackMap != null && this.mCallbackMap.containsKey(key) && status != null && this.mCallbackMap.get(key) != null) {
            ((ILePayCallback) this.mCallbackMap.get(key)).payResult(status, message);
        }
    }
}
