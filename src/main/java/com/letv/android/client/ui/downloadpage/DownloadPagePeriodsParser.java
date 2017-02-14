package com.letv.android.client.ui.downloadpage;

import android.text.TextUtils;
import com.letv.core.bean.VideoBean;
import com.letv.core.bean.VideoListBean;
import com.letv.core.parser.VideoListParser;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class DownloadPagePeriodsParser extends VideoListParser {
    private static final String TAG = DownloadPagePeriodsParser.class.getSimpleName();

    public DownloadPagePeriodsParser() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public boolean onHandleParsePeriods(JSONObject videoListJson, VideoListBean videoListBean) throws Exception {
        Iterator keyIter = videoListJson.keys();
        videoListBean.periodHashMap = new LinkedHashMap();
        while (keyIter.hasNext()) {
            String key = (String) keyIter.next();
            JSONArray array = videoListJson.optJSONArray(key);
            if (!(array == null || TextUtils.isEmpty(key) || TextUtils.equals(key, "varietyShow") || TextUtils.equals(key, "previewList"))) {
                VideoListBean arrayVideoBean;
                if (array.length() > 0) {
                    arrayVideoBean = new VideoListBean();
                } else {
                    arrayVideoBean = null;
                }
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.optJSONObject(i);
                    if (!isNull(json)) {
                        arrayVideoBean.add(VideoBean.parse(json));
                    }
                }
                videoListBean.periodHashMap.put(key, arrayVideoBean);
            }
        }
        return true;
    }

    public boolean isParsePreview() {
        return false;
    }
}
