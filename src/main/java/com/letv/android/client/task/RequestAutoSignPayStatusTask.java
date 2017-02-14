package com.letv.android.client.task;

import android.content.Context;
import com.letv.android.client.listener.AlipayAutoPayUserSignStatusCallback;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PayCenterApi;
import com.letv.core.bean.AlipayUserSignStatusBean;
import com.letv.core.bean.DataHull;
import com.letv.core.network.volley.VolleyRequest;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.VolleyResponse.NetworkResponseState;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.AlipayUserSignStatusParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestAutoSignPayStatusTask {
    private AlipayAutoPayUserSignStatusCallback mAlipayAutoPayUserSignStatusCallback;
    private int mAllScreenSignFlag;
    private Context mContext;
    private int mMobileSignFlag;
    private String mSvip;

    public RequestAutoSignPayStatusTask(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.mAlipayAutoPayUserSignStatusCallback = null;
        this.mContext = context;
    }

    public void setVipType(String svip) {
        this.mSvip = svip;
    }

    public void setAutoSignUserStatusCallback(AlipayAutoPayUserSignStatusCallback alipayAutoPayUserSignStatusCallback) {
        this.mAlipayAutoPayUserSignStatusCallback = alipayAutoPayUserSignStatusCallback;
    }

    private void setAllScreenSignFlag(boolean isOneKey) {
        if (isOneKey) {
            this.mAllScreenSignFlag = 2;
        } else {
            this.mAllScreenSignFlag = 3;
        }
    }

    private void setMobileSignFlag(boolean isOneKey) {
        if (isOneKey) {
            this.mMobileSignFlag = 2;
        } else {
            this.mMobileSignFlag = 3;
        }
    }

    public void start() {
        LogInfo.log("CRL 支付宝支付开始2 第一个 URL== " + PayCenterApi.getInstance().requestAutoSignPayStatus(this.mSvip));
        new LetvRequest(AlipayUserSignStatusBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PayCenterApi.getInstance().requestAutoSignPayStatus(this.mSvip)).setCache(new VolleyNoCache()).setParser(new AlipayUserSignStatusParser()).setCallback(new SimpleResponse<AlipayUserSignStatusBean>(this) {
            final /* synthetic */ RequestAutoSignPayStatusTask this$0;

            {
                if (HotFix.PREVENT_VERIFY) {
                    System.out.println(VerifyLoad.class);
                }
                this.this$0 = this$0;
            }

            public void onNetworkResponse(VolleyRequest<AlipayUserSignStatusBean> volleyRequest, AlipayUserSignStatusBean result, DataHull hull, NetworkResponseState state) {
                switch (state) {
                    case SUCCESS:
                        if (result != null) {
                            boolean isNotOpenFlag = true;
                            if (this.this$0.mSvip.equals("9")) {
                                if (result.status == 0) {
                                    isNotOpenFlag = true;
                                    this.this$0.setAllScreenSignFlag(result.isOneKey);
                                } else if (result.status == 1) {
                                    this.this$0.mAllScreenSignFlag = 1;
                                } else if (result.status == 2) {
                                    isNotOpenFlag = false;
                                    this.this$0.setAllScreenSignFlag(result.isOneKey);
                                }
                                if (this.this$0.mAlipayAutoPayUserSignStatusCallback != null) {
                                    this.this$0.mAlipayAutoPayUserSignStatusCallback.onAutoPayUserSignStatusAllScreenCallback(this.this$0.mAllScreenSignFlag, result.paytype, isNotOpenFlag);
                                    return;
                                }
                                return;
                            }
                            if (result.status == 0) {
                                isNotOpenFlag = true;
                                this.this$0.setMobileSignFlag(result.isOneKey);
                            } else if (result.status == 1) {
                                this.this$0.mMobileSignFlag = 1;
                            } else if (result.status == 2) {
                                isNotOpenFlag = false;
                                this.this$0.setMobileSignFlag(result.isOneKey);
                            }
                            if (this.this$0.mAlipayAutoPayUserSignStatusCallback != null) {
                                this.this$0.mAlipayAutoPayUserSignStatusCallback.onAutoPayUserSignStatusMobileCallback(this.this$0.mMobileSignFlag, result.paytype, isNotOpenFlag);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }

            public void onErrorReport(VolleyRequest<AlipayUserSignStatusBean> request, String errorInfo) {
                super.onErrorReport(request, errorInfo);
            }
        }).add();
    }
}
