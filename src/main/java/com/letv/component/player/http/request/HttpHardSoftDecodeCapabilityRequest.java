package com.letv.component.player.http.request;

import android.content.Context;
import android.os.Bundle;
import com.letv.component.core.async.LetvHttpAsyncRequest;
import com.letv.component.core.async.TaskCallBack;
import com.letv.component.core.http.bean.LetvBaseBean;
import com.letv.component.core.http.impl.LetvHttpBaseParameter;
import com.letv.component.core.http.impl.LetvHttpParameter;
import com.letv.component.player.core.Configuration;
import com.letv.component.player.hardwaredecode.CodecWrapper;
import com.letv.component.player.http.HttpServerConfig;
import com.letv.component.player.http.parser.HardSoftDecodeCapabilityPareser;
import com.letv.component.player.utils.CpuInfosUtils;
import com.letv.component.player.utils.LogTag;
import com.letv.component.player.utils.PreferenceUtil;
import com.letv.component.player.utils.Tools;
import com.letv.core.constant.LiveRoomConstant;

public class HttpHardSoftDecodeCapabilityRequest extends LetvHttpAsyncRequest {
    public HttpHardSoftDecodeCapabilityRequest(Context context, TaskCallBack callback) {
        super(context, callback);
    }

    public LetvHttpBaseParameter getRequestParams(Object... params) {
        String appKey = String.valueOf(params[0]);
        String pCode = String.valueOf(params[1]);
        String appVer = String.valueOf(params[2]);
        Bundle bundle = new Bundle();
        bundle.putString("model", Tools.getDeviceName());
        bundle.putString("cpu", String.valueOf(CodecWrapper.getCapbility()));
        bundle.putString("sysVer", Tools.getOSVersionName());
        bundle.putString("sdkVer", Configuration.SDK_VERSION);
        bundle.putString("cpuCore", String.valueOf(CpuInfosUtils.getNumCores()));
        bundle.putString("cpuHz", String.valueOf(CpuInfosUtils.getMaxCpuFrequence()));
        bundle.putString("decodeProfile", String.valueOf(CodecWrapper.getProfile()));
        bundle.putString("AVCLevel", String.valueOf(CodecWrapper.getAVCLevel()));
        bundle.putString("did", Tools.generateDeviceId(this.context));
        bundle.putString("appKey", appKey);
        bundle.putString("pCode", pCode);
        bundle.putString("appVer", appVer);
        LogTag.i("HttpHardSoftDecodeCapabilityRequest:url=" + HttpServerConfig.getServerUrl() + HttpServerConfig.HARD_SOFT_DECODE_CAPABILITY + ", params=" + bundle.toString());
        PreferenceUtil.setQuestParams(this.context, "系统当前时间:  " + Tools.getCurrentDate() + " 软硬解码请求接口参数  ：" + "HttpHardSoftDecodeCapabilityRequest:url=" + HttpServerConfig.getServerUrl() + HttpServerConfig.HARD_SOFT_DECODE_CAPABILITY + ", params=" + bundle.toString());
        return new LetvHttpParameter(HttpServerConfig.getServerUrl(), HttpServerConfig.HARD_SOFT_DECODE_CAPABILITY, bundle, LiveRoomConstant.EVENT_FLOW_ON_PLAY);
    }

    public LetvBaseBean parseData(String sourceData) throws Exception {
        LogTag.i("sourceData=" + sourceData);
        PreferenceUtil.setQuestResult(this.context, "系统当前时间:  " + Tools.getCurrentDate() + "  软硬解码能力返回值  ：" + sourceData);
        return (LetvBaseBean) new HardSoftDecodeCapabilityPareser().initialParse(sourceData);
    }

    protected int getReadTimeOut() {
        return 50000;
    }
}
