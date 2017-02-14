package com.letv.core.parser;

import com.letv.core.bean.TopicDetailInfoListBean;
import com.letv.core.bean.TopicDetailInfoListBean.TopicDetailItemBean;
import com.letv.core.bean.TopicSubject;
import com.letv.core.constant.PlayConstant;
import com.letv.download.db.Download.DownloadVideoTable;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class TopicInfoListParser extends LetvMobileParser<TopicDetailInfoListBean> {
    private static final String ALBUMLIST = "albumList";
    private static final String SUBJECT = "subject";
    private static final String VIDEOLIST = "videoList";

    public TopicDetailInfoListBean parse(JSONObject data) throws Exception {
        if (data == null) {
            return null;
        }
        TopicDetailInfoListBean topicDetailInfoListBean = new TopicDetailInfoListBean();
        topicDetailInfoListBean.list = new ArrayList();
        JSONArray jsonArray = null;
        boolean isAlbum = true;
        try {
            JSONObject jsonSubject = getJSONObject(data, SUBJECT);
            if (jsonSubject != null) {
                TopicSubject subject = new TopicSubject();
                subject.setName(getString(jsonSubject, "name"));
                subject.setDescription(getString(jsonSubject, "description"));
                subject.setPubName(getString(jsonSubject, "pubName"));
                subject.setTag(getString(jsonSubject, "tag"));
                subject.setType(getInt(jsonSubject, "type"));
                subject.setCtime(getString(jsonSubject, "ctime"));
                subject.setCid(getInt(jsonSubject, "cid"));
                topicDetailInfoListBean.subject = subject;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (has(data, ALBUMLIST)) {
            isAlbum = true;
            jsonArray = getJSONArray(data, ALBUMLIST);
        } else if (has(data, "videoList")) {
            isAlbum = false;
            jsonArray = getJSONArray(data, "videoList");
        }
        if (jsonArray == null || jsonArray == null || jsonArray.length() <= 0) {
            return topicDetailInfoListBean;
        }
        int len = jsonArray.length();
        for (int i = 0; i < len; i++) {
            try {
                TopicDetailItemBean item;
                JSONObject jsonAlbumItem = jsonArray.getJSONObject(i);
                if (isAlbum) {
                    item = getTopicDetailAlbumInfo(jsonAlbumItem);
                } else {
                    item = getTopicDetailVideoInfo(jsonAlbumItem);
                }
                if (topicDetailInfoListBean != null) {
                    topicDetailInfoListBean.list.add(item);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return topicDetailInfoListBean;
    }

    private TopicDetailItemBean getTopicDetailAlbumInfo(JSONObject data) {
        TopicDetailItemBean mTopicDetailInfoPlayerLibs = new TopicDetailItemBean();
        try {
            mTopicDetailInfoPlayerLibs.id = getString(data, "aid");
            mTopicDetailInfoPlayerLibs.name = getString(data, "name");
            mTopicDetailInfoPlayerLibs.subName = getString(data, "subname");
            mTopicDetailInfoPlayerLibs.type = getInt(data, "type");
            mTopicDetailInfoPlayerLibs.play = getInt(data, "play");
            mTopicDetailInfoPlayerLibs.download = getInt(data, "download");
            mTopicDetailInfoPlayerLibs.jump = getInt(data, "jump");
            mTopicDetailInfoPlayerLibs.isEnd = getInt(data, "isEnd");
            mTopicDetailInfoPlayerLibs.episode = getString(data, "episode");
            mTopicDetailInfoPlayerLibs.nowEpisode = getString(data, "nowEpisodes");
            mTopicDetailInfoPlayerLibs.albumOrVideoType = getString(data, "albumType");
            return getInt(data, "play") == 1 ? mTopicDetailInfoPlayerLibs : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TopicDetailItemBean getTopicDetailVideoInfo(JSONObject data) {
        TopicDetailItemBean mTopicDetailInfoPlayerLibs = new TopicDetailItemBean();
        try {
            mTopicDetailInfoPlayerLibs.id = getString(data, "id");
            mTopicDetailInfoPlayerLibs.pid = getString(data, "pid");
            mTopicDetailInfoPlayerLibs.name = getString(data, "nameCn");
            mTopicDetailInfoPlayerLibs.subName = getString(data, "subTitle");
            mTopicDetailInfoPlayerLibs.type = getInt(data, "type");
            mTopicDetailInfoPlayerLibs.duration = getInt(data, DownloadVideoTable.COLUMN_DURATION);
            mTopicDetailInfoPlayerLibs.play = getInt(data, "play");
            mTopicDetailInfoPlayerLibs.download = getInt(data, "download");
            mTopicDetailInfoPlayerLibs.jump = getInt(data, "jump");
            mTopicDetailInfoPlayerLibs.albumOrVideoType = getString(data, PlayConstant.VIDEO_TYPE);
            if (has(data, "picAll")) {
                JSONObject picAllObj = getJSONObject(data, "picAll");
                if (has(picAllObj, "120*90")) {
                    mTopicDetailInfoPlayerLibs.pic120_90 = getString(picAllObj, "120*90");
                }
                if (has(picAllObj, "300*300")) {
                    mTopicDetailInfoPlayerLibs.pic300_300 = getString(picAllObj, "300*300");
                }
                if (has(picAllObj, "400*300")) {
                    mTopicDetailInfoPlayerLibs.pic400_300 = getString(picAllObj, "400*300");
                }
            }
            return getInt(data, "play") == 1 ? mTopicDetailInfoPlayerLibs : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
