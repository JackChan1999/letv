package com.letv.android.client.worldcup.async;

import android.content.Context;
import com.letv.android.client.worldcup.async.inter.RequestCallBack;
import com.letv.android.client.worldcup.bean.Video;
import com.letv.android.client.worldcup.bean.WorldCupBlock;
import com.letv.android.client.worldcup.dao.PreferencesManager;
import com.letv.android.client.worldcup.parse.VideoParser;
import com.letv.android.client.worldcup.parse.WorldCupBlockParser;
import com.letv.android.client.worldcup.util.LetvServiceConfiguration;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LogInfo;
import com.tencent.open.SocialConstants;

public class LetvApi {
    private static volatile LetvApi instance;
    public final String APP_End = ".mindex.html";
    private final String MEDIA_ASSET_DYNAMIC_APP_URL = "http://dynamic.search.app.m.letv.com/android/dynamic.php";
    private final String MEDIA_ASSET_STATIC_APP_HEAD = "http://static.app.m.letv.com/android";
    public final String STATIC_TEST_BASE_HEAD = "http://test2.m.letv.com/android";

    protected LetvApi() {
    }

    public static LetvApi getInstance() {
        if (instance == null) {
            synchronized (LetvApi.class) {
                if (instance == null) {
                    instance = new LetvApi();
                }
            }
        }
        return instance;
    }

    private String getStaticHead(Context context) {
        if (PreferencesManager.getInstance().isTestApi(context)) {
            return "http://test2.m.letv.com/android";
        }
        return "http://static.app.m.letv.com/android";
    }

    public void requestWorldCupBlock(Context context, RequestCallBack<WorldCupBlock> callBack) {
        String head = getStaticHead(context);
        StringBuffer sb = new StringBuffer();
        sb.append(head).append("/").append("mod").append("/").append(HOME_RECOMMEND_PARAMETERS.MOD_VALUE).append("/").append("ctl").append("/").append("block").append("/").append(SocialConstants.PARAM_ACT).append("/").append("index").append("/").append("id").append("/").append("1351").append("/").append("pcode").append("/").append(LetvServiceConfiguration.getPcode(context)).append("/").append("version").append("/").append(LetvServiceConfiguration.getVersion(context)).append(".mindex.html");
        LogInfo.log("", "requestWorldCupBlock URL : " + sb.toString());
        new LetvRequest(WorldCupBlock.class).setCache(new VolleyNoCache()).setUrl(sb.toString()).setParser(new WorldCupBlockParser()).setCallback(new 1(this, callBack)).add();
    }

    public void requestVideoDetail(Context context, int updataId, String vid, RequestCallBack callback) {
        String head = getStaticHead(context);
        StringBuffer sb = new StringBuffer();
        sb.append(head).append("/").append("mod").append("/").append(HOME_RECOMMEND_PARAMETERS.MOD_VALUE).append("/").append("ctl").append("/").append("video").append("/").append(SocialConstants.PARAM_ACT).append("/").append("detail").append("/").append("id").append("/").append(vid).append("/").append("pcode").append("/").append(LetvServiceConfiguration.getPcode(context)).append("/").append("version").append("/").append(LetvServiceConfiguration.getVersion(context)).append(".mindex.html");
        new LetvRequest(Video.class).setUrl(sb.toString()).setCache(new VolleyNoCache()).setParser(new VideoParser()).setCallback(new 2(this, callback)).add();
    }
}
