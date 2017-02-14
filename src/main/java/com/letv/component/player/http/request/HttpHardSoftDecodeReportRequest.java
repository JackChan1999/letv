package com.letv.component.player.http.request;

import android.content.Context;
import android.os.Bundle;
import com.letv.component.core.async.LetvHttpAsyncRequest;
import com.letv.component.core.async.TaskCallBack;
import com.letv.component.core.http.bean.LetvBaseBean;
import com.letv.component.core.http.impl.LetvHttpParameter;
import com.letv.component.player.core.Configuration;
import com.letv.component.player.hardwaredecode.CodecWrapper;
import com.letv.component.player.http.HttpServerConfig;
import com.letv.component.player.utils.CpuInfosUtils;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.Tools;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpHardSoftDecodeReportRequest extends LetvHttpAsyncRequest {
    public HttpHardSoftDecodeReportRequest(Context context, TaskCallBack callback) {
        super(context, callback);
    }

    public LetvHttpParameter getRequestParams(Object... params) {
        String appKey = String.valueOf(params[0]);
        String pcode = String.valueOf(params[1]);
        String appVer = String.valueOf(params[2]);
        String player = String.valueOf(params[3]);
        String vid = String.valueOf(params[4]);
        String playUrl = String.valueOf(params[5]);
        String isSuc = String.valueOf(params[6]);
        String errCode = String.valueOf(params[7]);
        String clarity = String.valueOf(params[8]);
        String isSmooth = String.valueOf(params[9]);
        Bundle bundle = new Bundle();
        bundle.putString("model", Tools.getDeviceName());
        bundle.putString("cpu", String.valueOf(CodecWrapper.getCapbility()));
        bundle.putString("sysVer", Tools.getOSVersionName());
        bundle.putString("sdkVer", Configuration.SDK_VERSION);
        bundle.putString("cpuCore", String.valueOf(CpuInfosUtils.getNumCores()));
        bundle.putString("cpuHz", String.valueOf(CpuInfosUtils.getMaxCpuFrequence()));
        bundle.putString("did", Tools.generateDeviceId(this.context));
        bundle.putString("appKey", appKey);
        bundle.putString("pCode", pcode);
        bundle.putString("appVer", appVer);
        bundle.putString("player", player);
        bundle.putString("isSuc", isSuc);
        bundle.putString("vid", vid);
        try {
            bundle.putString("playUrl", URLEncoder.encode(playUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        bundle.putString("clarity", clarity);
        bundle.putString("errCode", errCode);
        bundle.putString("isSmooth", isSmooth);
        LogTag.i("HttpHardSoftDecodeReportRequest:url=" + HttpServerConfig.getServerUrl() + HttpServerConfig.HARD_SOFT_DECODE_REPORT + ", params=" + bundle.toString());
        return new LetvHttpParameter(HttpServerConfig.getServerUrl(), HttpServerConfig.HARD_SOFT_DECODE_REPORT, bundle, 8194);
    }

    public LetvBaseBean parseData(String sourceData) throws Exception {
        LogTag.i("sourceData=" + sourceData);
        return null;
    }
}
