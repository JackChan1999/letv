package com.letv.core.parser;

import com.letv.core.bean.HomeMetaData;
import com.letv.core.bean.MyFocusImageDataListBean;
import com.letv.core.bean.SiftKVP;
import com.letv.core.constant.PlayConstant;
import com.letv.core.utils.LogInfo;
import com.letv.download.db.Download.DownloadVideoTable;
import com.letv.lemallsdk.util.Constants;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyFocusImageListParser extends LetvMobileParser<MyFocusImageDataListBean> {
    public MyFocusImageDataListBean parse(JSONObject data) throws Exception {
        LogInfo.log("MyFocusImageDataList", ">>parse :  " + data);
        MyFocusImageDataListBean myFocusImageDataList = null;
        JSONArray jsonArray = data.getJSONArray("blockContent");
        if (jsonArray != null) {
            myFocusImageDataList = new MyFocusImageDataListBean();
            int nLength = jsonArray.length();
            for (int i = 0; i < nLength; i++) {
                HomeMetaData homeMetaData = new HomeMetaData();
                JSONObject jsonObject = getJSONObject(jsonArray, i);
                homeMetaData.cmsid = getString(jsonObject, "cmsid");
                homeMetaData.pid = getInt(jsonObject, "pid");
                homeMetaData.vid = getInt(jsonObject, "vid");
                homeMetaData.zid = getString(jsonObject, PlayConstant.ZID);
                homeMetaData.nameCn = getString(jsonObject, "nameCn");
                homeMetaData.subTitle = getString(jsonObject, "subTitle");
                homeMetaData.cid = getInt(jsonObject, "cid");
                homeMetaData.type = getInt(jsonObject, "type");
                homeMetaData.at = getInt(jsonObject, PushDataParser.AT);
                homeMetaData.episode = getString(jsonObject, "episode");
                homeMetaData.nowEpisodes = getString(jsonObject, "nowEpisodes");
                homeMetaData.isEnd = getInt(jsonObject, "isEnd");
                homeMetaData.pay = getInt(jsonObject, "pay");
                homeMetaData.pageid = getString(jsonObject, PlayConstant.PAGE_ID);
                homeMetaData.tag = getString(jsonObject, "tag");
                homeMetaData.mobilePic = getString(jsonObject, "mobilePic");
                if (has(jsonObject, "pic1")) {
                    homeMetaData.pic1 = getString(jsonObject, "pic1");
                }
                homeMetaData.streamCode = getString(jsonObject, "streamCode");
                homeMetaData.webUrl = getString(jsonObject, "webUrl");
                homeMetaData.webViewUrl = getString(jsonObject, "webViewUrl");
                homeMetaData.streamUrl = getString(jsonObject, "streamUrl");
                homeMetaData.tm = getString(jsonObject, "tm");
                homeMetaData.duration = getString(jsonObject, DownloadVideoTable.COLUMN_DURATION);
                homeMetaData.singer = getString(jsonObject, "singer");
                if (has(getJSONObject(jsonArray, i), "showTagList")) {
                    JSONArray jo = getJSONArray(getJSONObject(jsonArray, i), "showTagList");
                    if (jo != null && jo.length() > 0) {
                        ArrayList<SiftKVP> siftKVPs = new ArrayList();
                        for (int j = 0; j < jo.length(); j++) {
                            JSONObject job = getJSONObject(jo, i);
                            if (job != null) {
                                SiftKVP showTagList = new SiftKVP();
                                showTagList.id = getString(job, "id");
                                showTagList.key = getString(job, Constants.VALUE_ID);
                                showTagList.filterKey = getString(job, "key");
                                siftKVPs.add(showTagList);
                            }
                        }
                        homeMetaData.showTagList = siftKVPs;
                    }
                }
                myFocusImageDataList.add(homeMetaData);
            }
        }
        return myFocusImageDataList;
    }
}
