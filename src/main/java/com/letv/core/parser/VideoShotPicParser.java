package com.letv.core.parser;

import com.letv.core.bean.VideoShotPicDataBean;
import org.json.JSONObject;

public class VideoShotPicParser extends LetvMobileParser<VideoShotPicDataBean> {
    protected VideoShotPicDataBean parse(JSONObject data) throws Exception {
        return (VideoShotPicDataBean) CommonParser.getJsonObj(VideoShotPicDataBean.class, data.toString());
    }
}
