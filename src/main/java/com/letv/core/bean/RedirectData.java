package com.letv.core.bean;

import java.util.HashMap;

public class RedirectData implements LetvBaseBean {
    public static final int REDIRECT_TYPE_FILTER = 1;
    public static final int REDIRECT_TYPE_H5 = 3;
    public static final int REDIRECT_TYPE_HOMEPAGE = 2;
    private static final long serialVersionUID = 1;
    public HashMap<String, String> redField;
    public String redirectCid;
    public String redirectPageId;
    public int redirectType;
    public String redirectUrl;
    public String redirectVideoType;
}
