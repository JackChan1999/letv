package com.letv.core.parser;

import com.letv.core.bean.VideoShotFigureBodyBean;
import org.json.JSONObject;

public class VideoShotFigureParser extends LetvMobileParser<VideoShotFigureBodyBean> {
    protected VideoShotFigureBodyBean parse(JSONObject data) throws Exception {
        return (VideoShotFigureBodyBean) CommonParser.getJsonObj(VideoShotFigureBodyBean.class, data.toString());
    }
}
