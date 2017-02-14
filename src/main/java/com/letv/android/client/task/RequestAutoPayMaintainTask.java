package com.letv.android.client.task;

import android.content.Context;
import com.letv.android.client.listener.AlipayMaintainCallback;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.AlipayAutoPayMaintainBean;
import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AlipayAutoSignMaintainParser;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestAutoPayMaintainTask {
    private AlipayMaintainCallback mAlipayMaintainCallback;
    private String mAutoRenewType;
    private int mAutoSignUserStatus;
    private Context mContext;

    public RequestAutoPayMaintainTask(Context context, String autorenew_type) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mAutoSignUserStatus = -2;
        this.mAlipayMaintainCallback = null;
        this.mContext = context;
        this.mAutoRenewType = autorenew_type;
    }

    public void setAlipayMaintainCallback(AlipayMaintainCallback alipayMaintainCallback) {
        this.mAlipayMaintainCallback = alipayMaintainCallback;
    }

    public void start() {
        new LetvRequest(AlipayAutoPayMaintainBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAutoSignMaintain(this.mAutoRenewType)).setCache(new VolleyNoCache()).setParser(new AlipayAutoSignMaintainParser()).setCallback(new SimpleResponse<AlipayAutoPayMaintainBean>(this) {
            final /* synthetic */ RequestAutoPayMaintainTask this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<AlipayAutoPayMaintainBean> volleyRequest, AlipayAutoPayMaintainBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            if (result.status == 0) {
                                this.this$0.mAutoSignUserStatus = 0;
                            } else if (result.status == 1) {
                                this.this$0.mAutoSignUserStatus = 1;
                            } else if (result.status == 2) {
                                this.this$0.mAutoSignUserStatus = 2;
                            } else if (result.status == 3) {
                                this.this$0.mAutoSignUserStatus = 3;
                            } else if (result.status == -1) {
                                this.this$0.mAutoSignUserStatus = -1;
                            } else {
                                this.this$0.mAutoSignUserStatus = 0;
                            }
                            if (this.this$0.mAlipayMaintainCallback != null) {
                                this.this$0.mAlipayMaintainCallback.onAlipayMaintainSuccess(this.this$0.mAutoSignUserStatus);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        if (this.this$0.mAlipayMaintainCallback != null) {
                            this.this$0.mAlipayMaintainCallback.onAlipayMaintainFail();
                            return;
                        }
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<AlipayAutoPayMaintainBean> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }
}
