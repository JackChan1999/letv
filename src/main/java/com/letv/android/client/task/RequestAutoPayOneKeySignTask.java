package com.letv.android.client.task;

import android.content.Context;
import com.letv.android.client.listener.AlipayOneKeySignCallback;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.AlipayOneKeySignBean;
import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AlipayOneKeySignParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestAutoPayOneKeySignTask {
    private String mActivityIds;
    private AlipayOneKeySignCallback mAlipayOneKeySignCallback;
    private String mAutoRenewFlag;
    private Context mContext;
    private String mCorderid;
    private String mPrice;
    private String mProductid;
    private String mSvip;

    public RequestAutoPayOneKeySignTask(Context context, String activityIds, String corderid, String productid, String price, String svip, String autoRenewFlag) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mAlipayOneKeySignCallback = null;
        this.mContext = context;
        this.mActivityIds = activityIds;
        this.mCorderid = corderid;
        this.mProductid = productid;
        this.mPrice = price;
        this.mSvip = svip;
        this.mAutoRenewFlag = autoRenewFlag;
    }

    public void start() {
        LogInfo.log("CRL 支付宝一键签约开始  URL == " + PayCenterApi.getInstance().requestAutoSignWithOneKey(this.mActivityIds, this.mCorderid, this.mProductid, this.mPrice, this.mSvip, this.mAutoRenewFlag));
        new LetvRequest(AlipayOneKeySignBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAutoSignWithOneKey(this.mActivityIds, this.mCorderid, this.mProductid, this.mPrice, this.mSvip, this.mAutoRenewFlag)).setCache(new VolleyNoCache()).setParser(new AlipayOneKeySignParser()).setCallback(new SimpleResponse<AlipayOneKeySignBean>(this) {
            final /* synthetic */ RequestAutoPayOneKeySignTask this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<AlipayOneKeySignBean> volleyRequest, AlipayOneKeySignBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            this.this$0.mAlipayOneKeySignCallback.onOneKeySignCallback(result);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<AlipayOneKeySignBean> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }

    public void setAlipayOneKeySignCallback(AlipayOneKeySignCallback alipayOneKeySignCallback) {
        this.mAlipayOneKeySignCallback = alipayOneKeySignCallback;
    }
}
