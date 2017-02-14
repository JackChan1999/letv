package com.letv.core.parser;

import com.letv.core.bean.NetIpBean;
import org.json.JSONObject;

public class NetIpParser extends LetvMobileParser<NetIpBean> {
    protected NetIpBean parse(JSONObject data) throws Exception {
        NetIpBean netIpBean = new NetIpBean();
        netIpBean.clientIp = data.optString("clientIp");
        return netIpBean;
    }
}
