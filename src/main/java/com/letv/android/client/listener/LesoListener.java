package com.letv.android.client.listener;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.ViewGroup.LayoutParams;
import com.letv.android.client.activity.SearchMainActivity;
import com.letv.android.client.activity.StarActivity;
import com.letv.android.client.commonlib.config.AlbumPlayActivityConfig;
import com.letv.android.client.commonlib.config.LetvSearchWebViewActivityConfig;
import com.letv.android.client.utils.LiveLaunchUtils;
import com.letv.android.client.view.PublicLoadLayoutPlayerLibs;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.SearchTraceHandler;
import com.letv.core.messagebus.manager.LeMessageManager;
import com.letv.core.messagebus.message.LeMessage;
import com.letv.core.messagebus.message.LeResponseMessage;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.core.utils.StatisticsUtils;
import com.letv.core.utils.UIsUtils;
import com.letv.datastatistics.constant.PageIdConstant;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.lesophoneclient.ex.LeSoSearchListener;
import com.letv.plugin.pluginloader.activity.JarBaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class LesoListener implements LeSoSearchListener {
    private Activity activity;
    private Handler handler;

    public LesoListener(Activity activity) {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
        this.activity = activity;
        this.handler = new Handler(this.activity.getMainLooper());
    }

    private void doSearch(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            if (!TextUtils.isEmpty(keyword.trim())) {
                SearchTraceHandler.saveSearchTrace(this.activity, keyword, System.currentTimeMillis());
            }
            UIsUtils.hideSoftkeyboard(this.activity);
            new LetvSearchWebViewActivityConfig(this.activity).launch(SearchMainActivity.urlJoin(keyword) + SearchMainActivity.SEARCH_H5_WEB_URL_REF, this.activity.getResources().getString(2131100238));
        }
    }

    private void doPlay(Context context, String title, String aid, String vid, String dataType, String src, String weburl, String ref, String jump_external_url, boolean is_risk) {
        LogInfo.log("zhuqiao", "leso doPlay:src=" + src + ";jump_external_url=" + jump_external_url + ";is_risk=" + is_risk);
        if ("2".equals(src) || "3".equals(src)) {
            new LetvSearchWebViewActivityConfig(this.activity).launch(weburl, this.activity.getResources().getString(2131100791));
            return;
        }
        StatisticsUtils.setActionProperty("d15", -1, PageIdConstant.searchPage);
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.activity).create(BaseTypeUtils.stol(aid), BaseTypeUtils.stol(vid), 12, is_risk, jump_external_url)));
        StatisticsUtils.staticticsInfoPost(this.activity, "0", "d15", src, -1, "", PageIdConstant.searchPage, "", "", "", "", "");
    }

    public void onLivePlay(Context context, String liveType, boolean isPay, String liveId, boolean isHalf) {
        LiveLaunchUtils.launchLiveLeso(this.activity, liveType, isPay, liveId, isHalf);
    }

    public void onLiveLunboPlay(Context context, String channelid, String sourceid, String title, String cName, String eName) {
        LiveLaunchUtils.launchLiveLesoLunbo(context, title, channelid, sourceid, false, cName, eName);
    }

    public void onWebToPlay(String jsonString) {
        LogInfo.log("lxx", "fun_playVideo jsonString: " + jsonString);
        try {
            LeResponseMessage response = LeMessageManager.getInstance().dispatchMessage(new LeMessage(1110, jsonString));
            if (LeResponseMessage.checkResponseMessageValidity(response, String.class)) {
                JSONObject jo = new JSONObject((String) response.getData());
                String type = jo.getString("type");
                String screen = jo.getString("screen");
                this.handler.post(new 1(this, type, jo));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogInfo.log("lxx", "playVideo的name参数异常");
        }
    }

    public void onSearchKeyWords(String keywords) {
        doSearch(keywords);
    }

    public void onPlay(Context context, String title, String aid, String vid, String dataType, String src, String weburl, String ref, String jump_external_url, boolean is_risk) {
        doPlay(context, title, aid, vid, dataType, src, weburl, ref, jump_external_url, is_risk);
    }

    public void onError(Context context, Object obj) {
        ((JarBaseActivity) obj).setJarResource(false);
        PublicLoadLayoutPlayerLibs publicLoadLayout = new PublicLoadLayoutPlayerLibs(context);
        LogInfo.log("+-->", "onError...._____");
        if (context instanceof Activity) {
            ((Activity) context).addContentView(publicLoadLayout, new LayoutParams(-1, -1));
            publicLoadLayout.netError(true);
            publicLoadLayout.setmRefreshData(new 2(this, obj, context, publicLoadLayout));
        }
    }

    public void onJumpStar(Context context, String starId, String starName) {
        if (!TextUtils.isEmpty(starId)) {
            StarActivity.launch(context, starId, starName, PageIdConstant.searchResultPage);
        }
    }

    public String getPcode() {
        return LetvConfig.getPcode();
    }

    public String getVersion() {
        return LetvUtils.getClientVersionName();
    }

    public void onZidPlay(Context context, String aid, String zid, String ref) {
        LeMessageManager.getInstance().dispatchMessage(new LeMessage(1, new AlbumPlayActivityConfig(this.activity).createTopic(BaseTypeUtils.stol(zid), BaseTypeUtils.stol(aid), 0, 12)));
    }
}
