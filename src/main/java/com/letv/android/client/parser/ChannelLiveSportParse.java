package com.letv.android.client.parser;

import com.letv.core.bean.LiveRemenListBean;
import com.letv.core.bean.LiveRemenListBean.LiveRemenBaseBean;
import com.letv.core.parser.LetvMobileParser;
import com.letv.core.parser.LiveSportsParser;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChannelLiveSportParse extends LetvMobileParser<LiveRemenListBean> {
    public ChannelLiveSportParse() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public LiveRemenListBean parse(JSONObject data) throws Exception {
        JSONArray jsonArray = null;
        if (data.has("result")) {
            jsonArray = data.getJSONArray("result");
        }
        LiveRemenListBean list = new LiveRemenListBean();
        LiveSportsParser liveParser = new LiveSportsParser();
        liveParser.mFrom = 2;
        if (jsonArray != null && jsonArray.length() > 0) {
            for (int i = 0; i < jsonArray.length(); i++) {
                LiveRemenBaseBean liveBean = liveParser.parse(jsonArray.getJSONObject(i));
                if (liveBean != null) {
                    list.add(liveBean);
                }
            }
        }
        return list;
    }
}
