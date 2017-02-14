package com.letv.android.client.worldcup.dao;

import android.content.Context;
import android.content.SharedPreferences;
import com.letv.core.constant.PlayConstant;
import com.letv.core.contentprovider.UserInfoDb;

public class PreferencesManager {
    private static final String API = "API";
    private static final String PERSONAL_CENTER_SP_NAME = "personal_center";
    private static final String PUSH = "push";
    private static final String SETTINGS = "settings";
    private static final String SHARE = "share";
    private static final String WORLD_CUP_FUNCTION = "world_cup";
    private static PreferencesManager instance = new PreferencesManager();

    public static PreferencesManager getInstance() {
        return instance;
    }

    public long getPushTime(Context context) {
        return context.getSharedPreferences(PUSH, 0).getLong("time", 0);
    }

    public int getPushDistance(Context context) {
        return context.getSharedPreferences(PUSH, 0).getInt("distance", 600);
    }

    public boolean isPush(Context context) {
        return context.getSharedPreferences(SHARE, 0).getBoolean("isPush", true);
    }

    public String getWorldCupStartTime(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("world_cup_start_time", "11:00$12:00$15:00$16:00");
    }

    public boolean isTestApi(Context context) {
        return context.getSharedPreferences(API, 4).getBoolean("test", false);
    }

    public boolean isDownloadHd(Context context) {
        return context.getSharedPreferences(SETTINGS, 4).getBoolean("isDownloadHd", false);
    }

    public void setPcode(Context context, String pcode) {
        context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).edit().putString("pcode", pcode).commit();
    }

    public void setVersion(Context context, String version) {
        context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).edit().putString("version", version).commit();
    }

    public String getPcode(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("pcode", "");
    }

    public String getVersion(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("version", "");
    }

    public void setContentProviderUri(Context context, String uri) {
        context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).edit().putString(PlayConstant.URI, uri).commit();
    }

    public String getContentProviderUri(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString(PlayConstant.URI, "");
    }

    public void setDownloadPath(Context context, String path, String defaultPath) {
        SharedPreferences sp = context.getSharedPreferences(WORLD_CUP_FUNCTION, 4);
        sp.edit().putString("download_path", path).commit();
        sp.edit().putString("download_default_path", defaultPath).commit();
    }

    public String getDownloadPath(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("download_path", "");
    }

    public String getDownloadDefaultPath(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("download_default_path", "");
    }

    public boolean getWorldCupFunc(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getBoolean("world_cup_func", false);
    }

    public String getUserId(Context context) {
        return context.getSharedPreferences(PERSONAL_CENTER_SP_NAME, 0).getString(UserInfoDb.USER_ID, "");
    }

    public String getPushText(Context context) {
        return context.getSharedPreferences(WORLD_CUP_FUNCTION, 4).getString("pushText", "已为您下载世界杯最新资讯！");
    }
}
