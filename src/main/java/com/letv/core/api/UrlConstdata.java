package com.letv.core.api;

import com.letv.core.config.LetvConfig;
import com.letv.core.db.PreferencesManager;
import com.letv.core.utils.LetvUtils;

public class UrlConstdata {
    public static final String IP_BASE_URL = "http://api.letv.com/getipgeo";
    private static final String STATIC_APP_BASE_END = ".mindex.html";
    private static final String USER_DYNAMIC_APP_URL = "http://dynamic.user.app.m.letv.com/android/dynamic.php";

    public interface HOME_RECOMMEND_PARAMETERS {
        public static final String ACT_VALUE = "index";
        public static final String CITY_LEVEL = "citylevel";
        public static final String COUNTRY = "country";
        public static final String CTL_VALUE = "homerecommend";
        public static final String DEVICEID_KEY = "devid";
        public static final String DISTRICT_ID = "districtid";
        public static final String HISTORY_KEY = "history";
        public static final String IS_NEW = "isnew";
        public static final String LOCATION = "location";
        public static final String MOD_VALUE = "mob";
        public static final String PROVINCE_ID = "provinceid";
        public static final String UID_KEY = "uid";
    }

    public static String getStaticEnd() {
        return STATIC_APP_BASE_END;
    }

    public static String getDynamicUrl() {
        return getDynamicUrl(LetvUtils.isInHongKong());
    }

    public static String getDynamicUrl(boolean isInHongKong) {
        if (PreferencesManager.getInstance().isTestApi() && LetvConfig.isForTest()) {
            return isInHongKong ? "http://hk.test2.m.letv.com/android/dynamic.php" : "http://test2.m.letv.com/android/dynamic.php";
        } else {
            return USER_DYNAMIC_APP_URL;
        }
    }
}
