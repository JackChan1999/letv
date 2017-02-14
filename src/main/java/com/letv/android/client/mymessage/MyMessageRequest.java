package com.letv.android.client.mymessage;

import com.letv.core.api.LetvRequest;
import com.letv.core.api.LetvUrlMaker;
import com.letv.core.bean.UnreadMessageCountBean;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class MyMessageRequest {

    public interface MessageListener {
        void onMessageCount();
    }

    public MyMessageRequest() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void requestUnreadMessageCountTask(MessageListener callback) {
        new LetvRequest(UnreadMessageCountBean.class).setUrl(LetvUrlMaker.getUnreadMessageCount()).setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new 1(callback)).add();
    }

    public static void requestUnreadMessageCountTask() {
        requestUnreadMessageCountTask(null);
    }
}
