package com.letv.mobile.async;

import android.content.Context;
import android.util.Log;
import com.letv.mobile.http.bean.LetvDataHull;
import com.letv.mobile.http.builder.LetvHttpBaseUrlBuilder;
import com.letv.mobile.http.utils.DomainManagerController;
import com.letv.mobile.http.utils.HttpDomainManager;
import java.util.List;
import java.util.Map;

public abstract class LetvTeleHttpAsyncRequest extends LetvHttpAsyncRequest {
    protected HttpDomainManager mDomainManager;

    public LetvTeleHttpAsyncRequest(Context context, TaskCallBack callback) {
        super(context, callback);
    }

    protected LetvDataHull retryRequest(LetvDataHull dataHull, Map<String, List<String>> header, LetvHttpBaseUrlBuilder httpParameter) {
        Log.i("LetvTeleHttpAsyncRequest", "LetvTeleHttpAsyncRequest retryRequest()");
        if (this.mRetryCount < getTotalRetryCount() && isValidDNS(header)) {
            this.mRetryCount++;
            return requestData(httpParameter);
        } else if (isNeedIpPolling() && switchNextDomain(httpParameter)) {
            return requestData(httpParameter);
        } else {
            return dataHull;
        }
    }

    protected LetvDataHull requestData(LetvHttpBaseUrlBuilder httpParameter) {
        if (isNeedIpPolling() && DomainManagerController.getInstance().isExistDomainManager(httpParameter.getSourceDomain()) && !httpParameter.isChangeDomainRequest()) {
            initDomainManager(httpParameter);
            String domain = getFirstAvaiableDomain();
            if (domain != null) {
                httpParameter.domain = domain;
                httpParameter.setChangeDomainRequest(true);
            }
        }
        return super.requestData(httpParameter);
    }

    protected String getFirstAvaiableDomain() {
        if (this.mDomainManager != null) {
            return this.mDomainManager.getFirstAvailableDomain();
        }
        return null;
    }

    protected String getNextDomain() {
        if (this.mDomainManager != null) {
            return this.mDomainManager.getNextDomain();
        }
        return null;
    }

    private void initDomainManager(LetvHttpBaseUrlBuilder httpParameter) {
        if (this.mDomainManager == null) {
            this.mDomainManager = getHttpDomainManager(httpParameter);
            if (this.mDomainManager != null) {
                this.mDomainManager.initCurrentIpPos();
            }
        }
    }

    private boolean switchNextDomain(LetvHttpBaseUrlBuilder httpParameter) {
        initDomainManager(httpParameter);
        String nextDomain = getNextDomain();
        if (nextDomain == null) {
            return false;
        }
        httpParameter.domain = nextDomain;
        Log.i("LetvTeleHttpAsyncRequest", "Change domain to nextDomain = " + nextDomain);
        httpParameter.setChangeDomainRequest(true);
        return true;
    }

    protected void onChangeDomainRequestSuccess(LetvHttpBaseUrlBuilder params) {
        if (this.mDomainManager != null) {
            this.mDomainManager.setAvailableDomain(params.domain);
        }
        params.setChangeDomainRequest(false);
    }

    protected void onChangeDomainRequestFail(LetvHttpBaseUrlBuilder params) {
        if (this.mDomainManager != null) {
            this.mDomainManager.pointNextDomain(params.domain);
        }
    }

    protected HttpDomainManager getHttpDomainManager(LetvHttpBaseUrlBuilder httpParameter) {
        return null;
    }

    protected boolean isValidDNS(Map<String, List<String>> header) {
        return header != null && header.containsKey("LETV");
    }

    protected boolean isNeedIpPolling() {
        return true;
    }
}
