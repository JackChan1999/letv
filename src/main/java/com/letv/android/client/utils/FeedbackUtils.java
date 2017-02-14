package com.letv.android.client.utils;

import android.content.Context;
import android.text.TextUtils;
import com.letv.android.client.LetvApplication;
import com.letv.core.api.LetvRequest;
import com.letv.core.api.PlayRecordApi;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.bean.FeedBackBean;
import com.letv.core.bean.UploadFileBean;
import com.letv.core.db.PreferencesManager;
import com.letv.core.network.volley.VolleyRequest.RequestManner;
import com.letv.core.network.volley.toolbox.SimpleResponse;
import com.letv.core.network.volley.toolbox.VolleyNoCache;
import com.letv.core.parser.FeedBackParser;
import com.letv.core.utils.BaseTypeUtils;
import com.letv.core.utils.LetvLogApiTool;
import com.letv.core.utils.LetvUtils;
import com.letv.core.utils.LogInfo;
import com.letv.hackdex.VerifyLoad;
import com.letv.hotfixlib.HotFix;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import master.flame.danmaku.danmaku.parser.IDataSource;

public class FeedbackUtils {
    public FeedbackUtils() {
        if (HotFix.PREVENT_VERIFY) {
            System.out.println(VerifyLoad.class);
        }
    }

    public static void submitFeedbackInfo(Context context, String mobile, String feedback, UploadFileBean[] formFiles, SimpleResponse<FeedBackBean> callback) {
        LogInfo.log("zhuqiao", "submitFeedbackInfo");
        if (formFiles == null) {
            formFiles = new UploadFileBean[0];
        }
        UploadFileBean formFile = new UploadFileBean(LetvLogApiTool.getInstance().getExceptionFile(), IDataSource.SCHEME_FILE_TAG, null);
        UploadFileBean[] files = new UploadFileBean[(formFiles.length + 1)];
        for (int i = 0; i < files.length; i++) {
            if (i == files.length - 1) {
                files[i] = formFile;
            } else {
                files[i] = formFiles[i];
            }
        }
        new LetvRequest(FeedBackBean.class).setRequestType(RequestManner.NETWORK_ONLY).setUrl(PlayRecordApi.postExceptionInfo(LetvUtils.getUUID(context), mobile, feedback)).setCache(new VolleyNoCache()).addPostParam(IDataSource.SCHEME_FILE_TAG, IDataSource.SCHEME_FILE_TAG).setFilePostParam(files).addPostParam("feedback", feedback).addPostParam(HOME_RECOMMEND_PARAMETERS.COUNTRY, LetvUtils.getCountryCode()).addPostParam(HOME_RECOMMEND_PARAMETERS.LOCATION, getLocation()).setParser(new FeedBackParser()).setCallback(new 1(callback)).add();
    }

    public static void submitCDEExceptionInfo(Context context, String mobile, String feedback, SimpleResponse<FeedBackBean> callback) {
        LogInfo.log("zhuqiao", "submitCDEExceptionInfo");
        new LetvRequest(FeedBackBean.class).setRequestType(RequestManner.NETWORK_ONLY).setCache(new VolleyNoCache()).setUrl(PlayRecordApi.postExceptionInfo(LetvUtils.getUUID(context), mobile, feedback)).addPostParam(IDataSource.SCHEME_FILE_TAG, IDataSource.SCHEME_FILE_TAG).setFilePostParam(new UploadFileBean[]{new UploadFileBean(new File(LetvApplication.CDELOG1), IDataSource.SCHEME_FILE_TAG, null)}).addPostParam("feedback", feedback).addPostParam(HOME_RECOMMEND_PARAMETERS.COUNTRY, LetvUtils.getCountryCode()).addPostParam(HOME_RECOMMEND_PARAMETERS.LOCATION, getLocation()).setParser(new FeedBackParser()).setCallback(new 2(callback)).add();
    }

    private static String getLocation() {
        if (!TextUtils.isEmpty(PreferencesManager.getInstance().getLocationCode())) {
            String[] arrGeoCode = PreferencesManager.getInstance().getLocationCode().split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            if (BaseTypeUtils.getElementFromArray(arrGeoCode, 4) != null) {
                return arrGeoCode[4];
            }
        }
        return "";
    }
}
