package com.letv.android.client.task;

import android.content.Context;
import com.letv.ads.ex.utils.PlayConstantUtils.SPConstant;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.UserBean;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.UserParser;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StringUtils;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestUserByTokenTask {
    public static final String REQUEST_USER_TASK = "request_user_task";

    public RequestUserByTokenTask() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void getUserByTokenTask(Context context, String tk, SimpleResponse<UserBean> callback) {
        LetvLogApiTool.getInstance().saveExceptionInfo("获取个人信息的URL Current Time :" + StringUtils.getTimeStamp() + " url == " + PlayRecordApi.getInstance().requestUserInfoByTk("", tk));
        LogInfo.log("ZSM", "CRL RequestUserByTokenTask url == " + PlayRecordApi.getInstance().requestUserInfoByTk("", tk));
        new LetvRequest(UserBean.class).setRequestType(LetvUtils.checkClickEvent(SPConstant.DELAY_BUFFER_DURATION) ? RequestManner.NETWORK_THEN_CACHE : RequestManner.CACHE_ONLY).setCache(new VolleyDiskCache("RequestUserByTokenTask")).setParser(new UserParser()).setUrl(PlayRecordApi.getInstance().requestUserInfoByTk("", tk)).setTag(REQUEST_USER_TASK).setNeedCheckToken(true).setCallback(new 1(callback, context)).add();
    }
}
