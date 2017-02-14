package com.letv.core.parser;

import com.letv.core.bean.FavoriteDataContainerBean;
import com.letv.core.bean.MyFavoriteContentBean;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MyFavoriteContentBeanParser extends LetvMobileParser<FavoriteDataContainerBean> {
    protected final String BLOCK_CONTENT = "blockContent";

    public FavoriteDataContainerBean parse(JSONObject data) throws Exception {
        FavoriteDataContainerBean mFavoriteDataContainerBean = new FavoriteDataContainerBean();
        if (has(data, "blockContent")) {
            JSONArray array = getJSONArray(data, "blockContent");
            if (array != null && array.length() > 0) {
                ArrayList<MyFavoriteContentBean> mFavoriteContentBeanList = new ArrayList();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = getJSONObject(array, i);
                    MyFavoriteContentBean mFavoriteContentBean = new MyFavoriteContentBean();
                    if (object != null) {
                        if (has(object, "cmsid")) {
                            mFavoriteContentBean.cmsid = getString(object, "cmsid");
                        }
                        if (has(object, "pid")) {
                            mFavoriteContentBean.pid = getInt(object, "pid");
                        }
                        if (has(object, "nameCn")) {
                            mFavoriteContentBean.nameCn = getString(object, "nameCn");
                        }
                        if (has(object, "albumType")) {
                            mFavoriteContentBean.albumType = getString(object, "albumType");
                        }
                        if (has(object, "subTitle")) {
                            mFavoriteContentBean.subTitle = getString(object, "subTitle");
                        }
                        if (has(object, "cid")) {
                            mFavoriteContentBean.cid = getInt(object, "cid");
                        }
                        if (has(object, "type")) {
                            mFavoriteContentBean.type = getInt(object, "type");
                        }
                        if (has(object, PushDataParser.AT)) {
                            mFavoriteContentBean.at = getInt(object, PushDataParser.AT);
                        }
                        if (has(object, "episode")) {
                            mFavoriteContentBean.episode = getString(object, "episode");
                        }
                        if (has(object, "nowEpisodes")) {
                            mFavoriteContentBean.nowEpisodes = getString(object, "nowEpisodes");
                        }
                        if (has(object, "isEnd")) {
                            mFavoriteContentBean.at = getInt(object, "isEnd");
                        }
                        if (has(object, "pay")) {
                            mFavoriteContentBean.pay = getInt(object, "pay");
                        }
                        if (has(object, "mobilePic")) {
                            mFavoriteContentBean.mobilePic = getString(object, "mobilePic");
                        }
                        if (has(object, "tag")) {
                            mFavoriteContentBean.tag = getString(object, "tag");
                        }
                        if (has(object, "padPic")) {
                            mFavoriteContentBean.padPic = getString(object, "padPic");
                        }
                        if (has(object, "singer")) {
                            mFavoriteContentBean.singer = getString(object, "singer");
                        }
                        if (has(object, "is_rec")) {
                            mFavoriteContentBean.is_rec = getString(object, "is_rec");
                        }
                        if (has(object, "varietyShow")) {
                            mFavoriteContentBean.varietyShow = getBoolean(object, "varietyShow");
                        }
                        if (has(object, "extends_extendRange")) {
                            mFavoriteContentBean.extends_extendRange = getString(object, "extends_extendRange");
                        }
                        mFavoriteContentBeanList.add(mFavoriteContentBean);
                    }
                }
                mFavoriteDataContainerBean.mFavoriteContentBeanList = mFavoriteContentBeanList;
            }
        }
        return mFavoriteDataContainerBean;
    }
}
