package com.letv.android.client.task;

import android.content.Context;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.LeDianBean;
import com.letv.core.bean.PaymentMethodBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.PaymentMethodParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestVipPackageTask {
    public RequestVipPackageTask() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void getLedianTask(Context mContext, SimpleResponse<LeDianBean> callback) {
        String url = PlayRecordApi.getInstance().queryRecord(0, PreferencesManager.getInstance().getUserId(), PreferencesManager.getInstance().getUserName(), "", "", "02", "0", "", "", "");
        LogInfo.log("VipProductsActivity", "ledian URL == " + url);
        new LetvRequest(LeDianBean.class).setUrl(url).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new 1(callback)).add();
    }

    public static void getPayKindTask(Context mContext, int monthType, String vipType, SimpleResponse<PaymentMethodBean> callback) {
        int i = 42;
        LogInfo.log("ZSM", "getOrderPackageTask url == " + PayCenterApi.getInstance().requestPayKind(monthType + "", vipType, ""));
        VolleyRequest alwaysCallbackNetworkResponse = new LetvRequest(PaymentMethodBean.class).setRequestType(RequestManner.CACHE_THEN_NETROWK).setAlwaysCallbackNetworkResponse(true);
        StringBuilder append = new StringBuilder().append("GetPayKindTask_");
        if (monthType != 42) {
            i = 0;
        }
        alwaysCallbackNetworkResponse.setCache(new VolleyDiskCache(append.append(i).toString())).setParser(new PaymentMethodParser()).setCallback(new 2(callback, monthType, vipType)).add();
    }
}
