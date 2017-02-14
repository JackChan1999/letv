package com.letv.android.client.webapp;

import android.content.Context;
import com.coolcloud.uac.android.common.util.TextUtils;
import com.letv.core.api.LetvRequest;
import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.utils.LogInfo;
import java.util.ArrayList;

public class LetvWebAppManager {
    private static String CHECK_VERSION_URL_ONLINE = "http://s.webapp.m.le.com/l/webapp/api/version/";
    private static String CHECK_VERSION_URL_TEST = "http://10.176.30.203:8000/api/version/";
    private static String GET_LAST_VERSION_URL_ONLINE = "http://s.webapp.m.le.com/e/webapp/api/version/new";
    private static String GET_LAST_VERSION_URL_TEST = "http://10.176.30.203:8000/api/version/new";
    public static String MY_FOLLOW = "follow.html";
    public static String WBE_APP_DOWNLOAD_PATH;
    public static String WBE_APP_UNZIP_PATH;

    public static String getGetLastVersionUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return GET_LAST_VERSION_URL_TEST;
        }
        return GET_LAST_VERSION_URL_ONLINE;
    }

    public static String getCheckVersionUrl() {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return CHECK_VERSION_URL_TEST;
        }
        return CHECK_VERSION_URL_ONLINE;
    }

    public static void setBasePath(Context context) {
        if (context != null) {
            WBE_APP_DOWNLOAD_PATH = context.getApplicationContext().getFilesDir().getAbsolutePath() + "/webapp/";
            WBE_APP_UNZIP_PATH = WBE_APP_DOWNLOAD_PATH + "local/";
        }
    }

    public static void update(Context context) {
        setBasePath(context);
        new Thread(new 1(context)).start();
    }

    private static void checkVersion(Context context, String url) {
        if (!TextUtils.isEmpty(url) && context != null) {
            LogInfo.log("webapp", "checkVersion checkVersionUrl=" + url);
            new LetvRequest().setUrl(url).setParser(new LetvWebAppVersionPaser()).setMaxRetries(2).setCache(new VolleyNoCache()).setRequestType(RequestManner.NETWORK_ONLY).setCallback(new 2(context)).add();
        }
    }

    private static void coverNewVersion(Context context, ArrayList<LetvWebAppVersionListItem> list) {
        if (list != null && list.size() > 0) {
            LogInfo.log("webapp", "coverNewVersion list size=" + list.size());
            ((LetvWebAppVersionListItem) list.get(0)).toLog();
            LetvWebAppDownloadAsyncTask.download(context, ((LetvWebAppVersionListItem) list.get(0)).url, ((LetvWebAppVersionListItem) list.get(0)).version, new 3(list, context));
        }
    }
}
