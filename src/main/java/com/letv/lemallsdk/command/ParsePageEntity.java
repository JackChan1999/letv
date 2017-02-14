package com.letv.lemallsdk.command;

import com.letv.lemallsdk.model.BaseBean;
import com.letv.lemallsdk.model.PageEntity;
import org.json.JSONObject;

public class ParsePageEntity extends BaseParse {
    public BaseBean Json2Entity(String content) {
        PageEntity pageObj = new PageEntity();
        try {
            JSONObject all = new JSONObject(content);
            pageObj.setMessage(all.optString("message"));
            pageObj.setStatus(all.optString("status"));
            JSONObject resultObj = all.optJSONObject("result");
            if (resultObj != null) {
                pageObj.setUrl(resultObj.optString("url"));
                pageObj.setTitle(resultObj.optString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageObj;
    }
}
