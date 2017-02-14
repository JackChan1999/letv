package com.letv.android.client.parser;

import com.letv.core.parser.LetvMobileParser;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelDetailPB;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelDetailPBPKG;
import com.letv.ltpbdata.LTStarRankModelDetailPBPKGOuterClass.LTStarRankModelPB;
import com.sina.weibo.sdk.component.ShareRequestParam;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class StarRankParser extends LetvMobileParser<LTStarRankModelDetailPBPKG> {
    public StarRankParser() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    protected LTStarRankModelDetailPBPKG parse(JSONObject data) throws Exception {
        LTStarRankModelDetailPBPKG starRankPB = new LTStarRankModelDetailPBPKG();
        LTStarRankModelPB starRankModel = starRankPB.data;
        JSONArray jsonArray = null;
        if (data.has("is_rank")) {
            starRankModel.is_rank = getString(data, "is_rank");
        }
        if (data.has(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA)) {
            jsonArray = data.getJSONArray(ShareRequestParam.RESP_UPLOAD_PIC_PARAM_DATA);
        }
        if (jsonArray != null && jsonArray.length() > 0) {
            ArrayList<LTStarRankModelDetailPB> mList = new ArrayList();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objs = getJSONObject(jsonArray, i);
                if (objs != null) {
                    mList.add(parseStarRankingBean(objs));
                }
            }
            starRankModel.data = mList;
        }
        return starRankPB;
    }

    private LTStarRankModelDetailPB parseStarRankingBean(JSONObject obj) {
        LTStarRankModelDetailPB rankingBean = new LTStarRankModelDetailPB();
        if (obj.has("id")) {
            rankingBean.id = getString(obj, "id");
        }
        if (obj.has("num")) {
            rankingBean.num = getString(obj, "num");
        }
        if (obj.has("nickname")) {
            rankingBean.nickname = getString(obj, "nickname");
        }
        if (obj.has("headimg")) {
            rankingBean.headimg = getString(obj, "headimg");
        }
        return rankingBean;
    }
}
