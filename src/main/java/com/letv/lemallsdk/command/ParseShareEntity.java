package com.letv.lemallsdk.command;

import com.letv.core.parser.PushDataParser;
import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.ShareEntity;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class ParseShareEntity extends BaseParse {
    public BaseBean Json2Entity(String content) {
        ShareEntity titleObj = new ShareEntity();
        try {
            JSONObject all = new JSONObject(content);
            titleObj.setMessage(all.optString("message"));
            titleObj.setStatus(all.optString("status"));
            JSONObject resultObj = all.optJSONObject("result");
            if (resultObj != null) {
                titleObj.setTitle(all.optString("title"));
                titleObj.setContent(all.optString(WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT));
                titleObj.setUrl(all.optString("url"));
                JSONArray jsonArray = resultObj.optJSONArray("photos");
                if (jsonArray != null && jsonArray.length() > 0) {
                    ArrayList<String> list = new ArrayList();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.optJSONObject(i).optString(PushDataParser.PICURL));
                    }
                    titleObj.setPhotoUrls(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titleObj;
    }
}
