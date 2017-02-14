package com.letv.android.client.task;

import android.content.Context;
import com.letv.android.client.listener.AlipayOneKeyPayCallback;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.AlipayOneKeyPayBean;
import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AlipayOneKeyPayParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestAutoPayOneKeyPayTask {
    private String mActivityIds;
    private AlipayOneKeyPayCallback mAlipayOneKeyPayCallback;
    private String mAutoRenewFlag;
    private Context mContext;
    private String mCorderid;
    private String mPayType;
    private String mPrice;
    private String mProductid;
    private String mSvip;

    public RequestAutoPayOneKeyPayTask(Context context, String activityIds, String corderid, String productid, String price, String svip, String autoRenewFlag, String payType) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mAlipayOneKeyPayCallback = null;
        this.mContext = context;
        this.mActivityIds = activityIds;
        this.mCorderid = corderid;
        this.mProductid = productid;
        this.mPrice = price;
        this.mSvip = svip;
        this.mAutoRenewFlag = autoRenewFlag;
        this.mPayType = payType;
    }

    public void start() {
        LogInfo.log("CRL 支付宝自动续费  一键支付开始 url == " + PayCenterApi.getInstance().requestAutoPayWithOneKey(this.mActivityIds, this.mCorderid, this.mProductid, this.mPrice, this.mSvip, this.mAutoRenewFlag, this.mPayType));
        new LetvRequest(AlipayOneKeyPayBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAutoPayWithOneKey(this.mActivityIds, this.mCorderid, this.mProductid, this.mPrice, this.mSvip, this.mAutoRenewFlag, this.mPayType)).setCache(new VolleyNoCache()).setParser(new AlipayOneKeyPayParser()).setReadTimeOut(35000).setCallback(new SimpleResponse<AlipayOneKeyPayBean>(this) {
            final /* synthetic */ RequestAutoPayOneKeyPayTask this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<AlipayOneKeyPayBean> volleyRequest, AlipayOneKeyPayBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            this.this$0.mAlipayOneKeyPayCallback.onOneKeyPayCallback(result);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<AlipayOneKeyPayBean> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    public void setAlipayOneKeyPayCallback(AlipayOneKeyPayCallback alipayOneKeyPayCallback) {
        this.mAlipayOneKeyPayCallback = alipayOneKeyPayCallback;
    }
}
