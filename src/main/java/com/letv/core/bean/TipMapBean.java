package com.letv.core.bean;

import java.util.HashMap;
import java.util.Map;

public class TipMapBean implements LetvBaseBean {
    public static final String TIP_CACHE_KEY = "cache_tip";
    private static final long serialVersionUID = 1;
    public Map<String, TipBean> map = new HashMap();
    public int max;
    public Map<String, String> messageMap = new HashMap();
    public Map<String, String> titleMap = new HashMap();

    public static class TipBean implements LetvBaseBean {
        private static final long serialVersionUID = 1;
        public String message = "";
        public String msgId = "";
        public String title = "";
    }
}
