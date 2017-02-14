package com.letv.android.client.task;

import android.content.Context;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.bean.PraiseBean;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class RequestPraiseTask {
    public RequestPraiseTask(Context context) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        LogInfo.log("ZSM", "RequestPraiseTask url == " + PlayRecordApi.requestPraiseInfo(0));
        new LetvRequest(PraiseBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.requestPraiseInfo(0)).setCache(new VolleyNoCache()).setCallback(new 1(this, context)).add();
    }
}
