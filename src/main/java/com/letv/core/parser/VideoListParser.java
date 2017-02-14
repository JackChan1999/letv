package com.letv.core.parser;

import android.text.TextUtils;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.utils.BaseTypeUtils;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class VideoListParser extends LetvMobileParser<VideoListBean> {
    private static final String STYLE = "style";
    private static final String VIDEOLIST = "videoList";
    public String mStyle = "";

    public VideoListBean parse(JSONObject data) throws Exception {
        if (data == null) {
            return null;
        }
        VideoListBean videoListBean = new VideoListBean();
        this.mStyle = getString(data, STYLE);
        videoListBean.style = BaseTypeUtils.stoi(this.mStyle);
        JSONObject videoListJson = new JSONObject(getString(data, "videoList"));
        if (isNull(videoListJson)) {
            return videoListBean;
        }
        JSONArray array;
        int i;
        if (this.mStyle.equals("1") || this.mStyle.equals("2")) {
            int length;
            JSONObject videoObj;
            VideoBean video;
            videoListBean.currPage = videoListJson.optInt("pagenum", 1) - 1;
            videoListBean.totalNum = BaseTypeUtils.stoi(getString(videoListJson, "totalNum"));
            videoListBean.episodeNum = BaseTypeUtils.stoi(getString(videoListJson, "episodeNum"));
            videoListBean.varietyShow = BaseTypeUtils.stoi(getString(videoListJson, "varietyShow"));
            array = videoListJson.optJSONArray("videoInfo");
            if (!isNull(array)) {
                length = array.length();
                for (i = 0; i < length; i++) {
                    videoObj = array.optJSONObject(i);
                    if (!isNull(videoObj)) {
                        video = VideoBean.parse(videoObj);
                        video.page = videoListBean.currPage;
                        videoListBean.add(video);
                    }
                }
            }
            if (!isParsePreview()) {
                return videoListBean;
            }
            JSONArray previewArray = videoListJson.optJSONArray("previewList");
            if (isNull(previewArray)) {
                return videoListBean;
            }
            length = previewArray.length();
            for (i = 0; i < length; i++) {
                videoObj = previewArray.optJSONObject(i);
                if (!isNull(videoObj)) {
                    video = VideoBean.parse(videoObj);
                    video.page = videoListBean.currPage;
                    videoListBean.add(video);
                }
            }
            return videoListBean;
        } else if (!this.mStyle.equals("3")) {
            return videoListBean;
        } else {
            videoListBean.varietyShow = BaseTypeUtils.stoi(getString(videoListJson, "varietyShow"));
            if (onHandleParsePeriods(videoListJson, videoListBean)) {
                return videoListBean;
            }
            Iterator keyIter = videoListJson.keys();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                array = videoListJson.optJSONArray(key);
                if (!(TextUtils.isEmpty(key) || TextUtils.equals(key, "varietyShow") || array.length() == 0)) {
                    for (i = 0; i < array.length(); i++) {
                        JSONObject json = array.optJSONObject(i);
                        if (!isNull(json)) {
                            videoListBean.add(VideoBean.parse(json));
                        }
                    }
                }
            }
            return videoListBean;
        }
    }

    public String getStyle() {
        return this.mStyle;
    }

    public void setStyle(String style) {
        this.mStyle = style;
    }

    public boolean onHandleParsePeriods(JSONObject jsonObject, VideoListBean videoListBean) throws Exception {
        return false;
    }

    public boolean isParsePreview() {
        return true;
    }
}
