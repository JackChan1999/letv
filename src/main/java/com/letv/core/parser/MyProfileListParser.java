package com.letv.core.parser;

import com.letv.core.bean.MyProfileListBean;
import com.letv.core.bean.MyProfileListBean.MyProfileBean;
import com.letv.download.db.Download.DownloadBaseColumns;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyProfileListParser extends LetvMobileParser<MyProfileListBean> {
    public MyProfileListBean parse(JSONObject data) throws Exception {
        MyProfileListBean myProfileList = null;
        JSONArray jsonArray = data.getJSONArray("profile");
        if (jsonArray != null) {
            myProfileList = new MyProfileListBean();
            for (int i = 0; i < jsonArray.length(); i++) {
                MyProfileBean myProfile = new MyProfileBean();
                myProfile.name = getJSONObject(jsonArray, i).getString("name");
                myProfile.subname = getJSONObject(jsonArray, i).getString("subname");
                myProfile.type = getJSONObject(jsonArray, i).getString("type");
                myProfile.sort = Integer.valueOf(getJSONObject(jsonArray, i).getInt("sort"));
                myProfile.pic = getJSONObject(jsonArray, i).getString(DownloadBaseColumns.COLUMN_PIC);
                myProfile.display = getJSONObject(jsonArray, i).getString("display");
                myProfileList.addMyProfileBean(myProfile);
            }
        }
        return myProfileList;
    }
}
