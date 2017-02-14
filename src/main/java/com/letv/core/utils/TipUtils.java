package com.letv.core.utils;

import android.text.TextUtils;
import com.letv.core.BaseApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.bean.TipMapBean;
import com.letv.core.bean.TipMapBean.TipBean;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.VolleyDiskCache;
import com.letv.core.parser.TipBeanListParser;

public class TipUtils {
    public static TipMapBean mTipMapBean = null;

    private static TipMapBean getTipMapBean() {
        if (mTipMapBean != null) {
            return mTipMapBean;
        }
        new LetvRequest().setCache(new VolleyDiskCache(TipMapBean.TIP_CACHE_KEY)).setParser(new TipBeanListParser()).setRequestType(RequestManner.CACHE_ONLY).setCallback(new 1()).add();
        return new TipMapBean();
    }

    public static String getTipMessage(String constantId) {
        return getTipMessage(constantId, "");
    }

    public static String getTipMessage(String constantId, int defaultResId) {
        return getTipMessage(constantId, BaseApplication.getInstance().getString(defaultResId));
    }

    public static String getTipMessage(String constantId, String defaultText) {
        TipBean bean = getTipBean(constantId);
        if (bean == null || TextUtils.isEmpty(bean.message)) {
            return defaultText.replace("#", "\n");
        }
        if (bean.message.contains("\n") || bean.message.contains("\\n")) {
            return bean.message.replace("#", "").replace("\\n", "\n");
        }
        return bean.message.replace("#", "\n");
    }

    public static String getTipTitle(String constantId) {
        return getTipTitle(constantId, -1);
    }

    public static String getTipTitle(String constantId, String defaultText) {
        TipBean bean = getTipBean(constantId);
        if (bean == null || TextUtils.isEmpty(bean.title)) {
            return defaultText;
        }
        return bean.title;
    }

    public static String getTipTitle(String constantId, int defaultResId) {
        TipBean bean = getTipBean(constantId);
        if (bean != null && !TextUtils.isEmpty(bean.title)) {
            return bean.title;
        }
        if (defaultResId >= 0) {
            return BaseApplication.getInstance().getString(defaultResId);
        }
        return "";
    }

    public static TipBean getTipBean(String constantId) {
        TipMapBean mapBean = getTipMapBean();
        if (mapBean == null || !BaseTypeUtils.isMapContainsKey(mapBean.map, constantId)) {
            return null;
        }
        return (TipBean) mapBean.map.get(constantId);
    }
}
