package com.letv.component.player.http.request;

import android.content.Context;
import com.letv.component.core.async.LetvHttpAsyncRequest;
import com.letv.component.core.async.TaskCallBack;
import com.letv.component.core.http.bean.LetvBaseBean;
import com.letv.component.core.http.impl.LetvHttpBaseParameter;
import com.letv.component.core.http.impl.LetvHttpStaticParameter;
import com.letv.component.player.http.parser.HardSoftDecodeCapabilityPareser;
import com.letv.component.player.utils.LogTag;

public class HttpGetCdeStateRequest extends LetvHttpAsyncRequest {
    public HttpGetCdeStateRequest(Context context, TaskCallBack callback) {
        super(context, callback);
    }

    public LetvHttpBaseParameter getRequestParams(Object... params) {
        return new LetvHttpStaticParameter(String.valueOf(params[0]), "", "", null);
    }

    public LetvBaseBean parseData(String sourceData) throws Exception {
        LogTag.i("sourceData=" + sourceData);
        return (LetvBaseBean) new HardSoftDecodeCapabilityPareser().initialParse(sourceData);
    }

    protected boolean isSync() {
        return false;
    }
}
