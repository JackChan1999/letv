package com.letv.android.client.task;

import android.content.Context;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.UserBean;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.QQLoginParser;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;

public class WeiboGetUerNameTask {
    public WeiboGetUerNameTask(Context mContext, String appKey, String accessToken, String uid) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        String url = "https://api.weibo.com/2/users/show.json?source=" + appKey + "&access_token=" + accessToken + "&uid=" + uid;
        LogInfo.log("CRL", "WeiboGetUerNameTask url == " + url);
        new LetvRequest(UserBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(url).setCache(new VolleyNoCache()).setParser(new QQLoginParser()).setCallback(new 1(this)).add();
    }
}
