package com.letv.core.parser;

import com.letv.base.R;
import com.letv.core.bean.PointBeanList;
import com.letv.core.bean.PointBeanList.PointBean;
import com.letv.core.utils.LetvUtils;
import com.sina.weibo.sdk.component.ShareRequestParam;
import org.json.JSONObject;

public class PointParser extends LetvMobileParser<PointBeanList> {
    public PointBeanList parse(JSONObject data) throws Exception {
        PointBeanList beanList = new PointBeanList();
        JSONObject result = getJSONObject(data, ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
        JSONObject result1 = result.getJSONObject("startmapp");
        if (result1 != null && result1.has("credit") && result1.has("cycletype") && result1.has("rewardnum") && result1.has("rname") && result1.has("state")) {
            beanList.getClass();
            PointBean bean = new PointBean(beanList);
            bean.credit = getString(result1, "credit");
            bean.cycletype = getString(result1, "cycletype");
            bean.rewardnum = Integer.parseInt(getString(result1, "rewardnum"));
            bean.rname = LetvUtils.getString(R.string.open_letv_phone);
            bean.state = Integer.parseInt(getString(result1, "state"));
            beanList.add(bean);
        }
        JSONObject result2 = result.getJSONObject("video");
        if (result2 != null && result2.has("credit") && result2.has("cycletype") && result2.has("rewardnum") && result2.has("rname") && result2.has("state")) {
            beanList.getClass();
            bean = new PointBean(beanList);
            bean.credit = getString(result2, "credit");
            bean.cycletype = getString(result2, "cycletype");
            bean.rewardnum = Integer.parseInt(getString(result2, "rewardnum"));
            bean.rname = getString(result2, "rname");
            bean.state = Integer.parseInt(getString(result2, "state"));
            beanList.add(bean);
        }
        JSONObject result3 = result.getJSONObject("sharevideo");
        if (result3 != null && result3.has("credit") && result3.has("cycletype") && result3.has("rewardnum") && result3.has("rname") && result3.has("state")) {
            beanList.getClass();
            bean = new PointBean(beanList);
            bean.credit = getString(result3, "credit");
            bean.cycletype = getString(result3, "cycletype");
            bean.rewardnum = Integer.parseInt(getString(result3, "rewardnum"));
            bean.rname = getString(result3, "rname");
            bean.state = Integer.parseInt(getString(result3, "state"));
            beanList.add(bean);
        }
        return beanList;
    }
}
