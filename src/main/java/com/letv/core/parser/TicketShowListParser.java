package com.letv.core.parser;

import com.letv.core.bean.TicketShowListBean;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TicketShowListParser extends LetvMasterParser<TicketShowListBean> {
    protected boolean canParse(String data) {
        return true;
    }

    protected JSONObject getData(String data) throws Exception {
        return new JSONObject(data);
    }

    public TicketShowListBean parse(JSONObject data) throws Exception {
        int i;
        TicketShowListBean list = new TicketShowListBean();
        if (data.has("code")) {
            list.code = getString(data, "code");
        }
        if (data.has("ServletInfo")) {
            JSONObject servletInfo = data.getJSONObject("ServletInfo");
            if (servletInfo.has(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT)) {
                JSONArray contentArray = servletInfo.getJSONArray(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT);
                ArrayList<String> contentList = new ArrayList();
                for (i = 0; i < contentArray.length(); i++) {
                    contentList.add(contentArray.getString(i));
                }
                list.content = contentList;
            }
            if (servletInfo.has("mobilePic")) {
                list.mobilePic = getString(servletInfo, "mobilePic");
            }
        }
        if (has(data, "values")) {
            JSONObject values = getJSONObject(data, "values");
            if (values.has("totalSize")) {
                list.totalSize = getString(values, "totalSize");
            }
            if (values.has("ticketShows")) {
                JSONArray ticketShowsArray = getJSONArray(values, "ticketShows");
                for (i = 0; i < ticketShowsArray.length(); i++) {
                    list.add(new TicketShowParser().parse(ticketShowsArray.getJSONObject(i)));
                }
            }
        }
        return list;
    }
}
