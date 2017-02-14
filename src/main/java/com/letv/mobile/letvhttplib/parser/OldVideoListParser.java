package com.letv.mobile.letvhttplib.parser;

import com.letv.mobile.letvhttplib.bean.VideoListBean;
import org.json.JSONArray;
import org.json.JSONObject;

public class OldVideoListParser extends LetvMobileParser<VideoListBean> {
    private final int page;

    public OldVideoListParser(int page) {
        this.page = page;
    }

    public VideoListBean parse(JSONObject data) throws Exception {
        if (data != null) {
            JSONArray array = getJSONArray(data, "videoInfo");
            if (array != null && array.length() > 0) {
                VideoParser parser = new VideoParser();
                VideoListBean list = new VideoListBean();
                for (int i = 0; i < array.length(); i++) {
                    list.add(parser.parse(getJSONObject(array, i)));
                }
                if (has(data, "pagenum")) {
                    list.currPage = this.page;
                }
                if (!has(data, "videoPosition")) {
                    return list;
                }
                list.videoPosition = getInt(data, "videoPosition");
                return list;
            }
        }
        return null;
    }
}
