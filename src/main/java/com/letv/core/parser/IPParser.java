package com.letv.core.parser;

import com.letv.core.bean.IPBean;
import org.json.JSONException;
import org.json.JSONObject;

public class IPParser extends LetvMasterParser<IPBean> {
    private final String CLIENT_IP = "clientIP";
    private final String USER_COUNTRY = "userCountry";

    public IPBean parse(JSONObject data) throws JSONException {
        IPBean ip = new IPBean();
        ip.clientIp = getString(data, "clientIP");
        ip.userCountry = getString(data, "userCountry");
        return ip;
    }

    protected boolean canParse(String data) {
        return true;
    }

    protected JSONObject getData(String data) throws JSONException {
        return new JSONObject(data);
    }
}
