package com.letv.mobile.lebox.http.lebox;

import android.content.Context;
import android.net.Uri;
import com.letv.mobile.async.TaskCallBack;
import com.letv.mobile.http.HttpGlobalConfig;
import com.letv.mobile.http.bean.LetvBaseBean;
import com.letv.mobile.http.builder.LetvHttpBaseUrlBuilder;
import com.letv.mobile.http.model.LetvHttpBaseModel;
import com.letv.mobile.http.parameter.LetvBaseParameter;
import com.letv.mobile.http.request.LetvHttpBaseRequest;
import com.letv.mobile.http.utils.HttpDomainManager;
import java.util.HashMap;

public abstract class LeBoxHttpDynamicRequest<T extends LetvHttpBaseModel> extends LetvHttpBaseRequest {
    boolean mIsSync;

    public abstract LetvHttpBaseUrlBuilder getRequestUrl(LetvBaseParameter letvBaseParameter);

    public LeBoxHttpDynamicRequest(Context context, TaskCallBack callback) {
        super(context, callback);
    }

    public LeBoxHttpDynamicRequest(Context context, TaskCallBack callback, boolean isSync) {
        super(context, callback);
        this.mIsSync = isSync;
    }

    protected boolean isSync() {
        return this.mIsSync;
    }

    protected HttpDomainManager getHttpDomainManager(LetvHttpBaseUrlBuilder params) {
        return params == null ? null : null;
    }

    protected LetvBaseBean<T> parseData(String sourceData) throws Exception {
        LetvBaseBean<T> data = parse(sourceData);
        if (checkData(data)) {
            return data;
        }
        throw new Exception("check data and data is invalid");
    }

    protected LetvBaseBean<T> parse(String sourceData) throws Exception {
        return null;
    }

    protected boolean checkData(LetvBaseBean<T> object) {
        return checkDataDefault(object);
    }

    private boolean checkDataDefault(LetvBaseBean<T> object) {
        if (object == null) {
            return false;
        }
        if (!object.isSuccess() || object.isDataValid()) {
            return true;
        }
        return false;
    }

    protected boolean isNeedIpPolling() {
        return false;
    }

    protected HashMap<String, String> getHeader() {
        HashMap<String, String> header = new HashMap();
        header.put("Host", Uri.parse(HttpGlobalConfig.getDynamicDomain()).getHost());
        return header;
    }
}
