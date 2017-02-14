package com.letv.core.parser;

import com.letv.core.bean.FindDataBean;
import com.letv.core.bean.FindListDataBean;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class FindListDataParser extends LetvMobileParser<FindListDataBean> {
    private FindDataParser mParser = new FindDataParser();

    protected FindListDataBean parse(JSONObject data) throws Exception {
        if (data != null && data.has("result")) {
            data = getJSONObject(data, "result");
        }
        FindListDataBean findListDataBean = null;
        List<FindDataBean> findDataBeanList = null;
        if (data != null) {
            findListDataBean = new FindListDataBean();
            JSONArray jsonArray = data.optJSONArray(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
            if (jsonArray != null) {
                findDataBeanList = new ArrayList();
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    findDataBeanList.add(this.mParser.parse(jsonArray.getJSONObject(i)));
                }
            }
            findListDataBean.setData(findDataBeanList);
        }
        return findListDataBean;
    }
}
