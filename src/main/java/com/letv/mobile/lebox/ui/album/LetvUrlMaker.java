package com.letv.mobile.lebox.ui.album;

import android.text.TextUtils;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.constant.PlayConstant;
import com.letv.datastatistics.util.DataConstant.PAGE;
import com.tencent.open.SocialConstants;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

public class LetvUrlMaker {
    private static String getStaticHead() {
        return "http://static.app.m.letv.com/android";
    }

    public static String getAlbumByTimeUrl(String year, String month, String id, String videoType) {
        String head = getStaticHead() + "/mod/mob/ctl/videolistbydate/act/detail";
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        params.add(new BasicNameValuePair("pcode", LetvUtils.getPcode()));
        if (!TextUtils.isEmpty(year)) {
            params.add(new BasicNameValuePair("year", year));
        }
        if (!TextUtils.isEmpty(month)) {
            params.add(new BasicNameValuePair("month", month));
        }
        params.add(new BasicNameValuePair("id", id));
        if (!TextUtils.isEmpty(videoType)) {
            params.add(new BasicNameValuePair(PlayConstant.VIDEO_TYPE, videoType));
        }
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String getAlbumVideoInfoUrl(String aid) {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "album"));
        params.add(new BasicNameValuePair("id", aid));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair("pcode", LetvUtils.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public static String getVideolistUrl(String aid, String vid, String page, String count, String o, String merge) {
        String head = getStaticHead();
        String end = LetvHttpApiConfig.getStaticEnd();
        ArrayList<BasicNameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("mod", HOME_RECOMMEND_PARAMETERS.MOD_VALUE));
        params.add(new BasicNameValuePair("ctl", "videolist"));
        params.add(new BasicNameValuePair(SocialConstants.PARAM_ACT, "detail"));
        params.add(new BasicNameValuePair("id", aid));
        params.add(new BasicNameValuePair("vid", vid));
        params.add(new BasicNameValuePair(PAGE.MYSHARE, page));
        params.add(new BasicNameValuePair("s", count));
        params.add(new BasicNameValuePair("o", o));
        params.add(new BasicNameValuePair(PAGE.MYLETV, merge));
        params.add(new BasicNameValuePair("pcode", LetvUtils.getPcode()));
        params.add(new BasicNameValuePair("version", LetvUtils.getClientVersionName()));
        return ParameterBuilder.getPathUrl(params, head, end);
    }

    public String toString() {
        return super.toString();
    }
}
