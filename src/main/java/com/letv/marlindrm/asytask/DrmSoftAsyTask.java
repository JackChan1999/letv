package com.letv.marlindrm.asytask;

import android.content.Context;
import android.text.TextUtils;
import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.Runtime;
import com.intertrust.wasabi.Runtime.Property;
import com.intertrust.wasabi.media.PlaylistProxy;
import com.intertrust.wasabi.media.PlaylistProxy.Flags;
import com.intertrust.wasabi.media.PlaylistProxy.MediaSourceParams;
import com.intertrust.wasabi.media.PlaylistProxyListener;
import com.letv.core.utils.LogInfo;
import com.letv.marlindrm.DrmModel;
import com.letv.marlindrm.bean.DrmResultBean;
import com.letv.marlindrm.constants.ContentTypeEnum;
import com.letv.marlindrm.constants.DrmResultCodeEnum;
import com.letv.marlindrm.http.DrmCommenUtils;
import com.letv.marlindrm.http.HttpUtils;
import com.letv.marlindrm.intf.DrmDealCallBackInf;
import com.letv.marlindrm.util.HandlerUtils;
import java.util.EnumSet;

public class DrmSoftAsyTask extends DrmAsyTask implements PlaylistProxyListener {
    private String TAG = "drmTest";
    private ContentTypeEnum mContentType;
    private Context mContext;
    private String mDrmXmlUrl;
    private String mMediaSourceUrl;

    public DrmSoftAsyTask(Context context, String mediaSourceUrl, String drmXmlUrl, ContentTypeEnum contentType, DrmDealCallBackInf callBack) {
        super(callBack);
        this.mContext = context;
        this.mMediaSourceUrl = mediaSourceUrl;
        this.mDrmXmlUrl = drmXmlUrl;
        this.mContentType = contentType;
    }

    public boolean isCanRun() {
        return this.mIsCanRun;
    }

    public DrmResultBean doInBackground() {
        if (isCanRun()) {
            return startDrmSoftProcess();
        }
        return null;
    }

    private DrmResultBean initDrm() {
        DrmResultBean bean = new DrmResultBean();
        DrmResultCodeEnum result = DrmResultCodeEnum.DRM_OK;
        long beginTime = System.currentTimeMillis();
        LogInfo.log(this.TAG, "start to initDrm");
        try {
            Runtime.setProperty(Property.ROOTED_OK, Boolean.valueOf(true));
            Runtime.initialize(this.mContext.getDir(this.DRM_DRI_NAME, 0).getAbsolutePath());
            LogInfo.log("zhuqiao", "initialize path:" + this.mContext.getDir(this.DRM_DRI_NAME, 0).getAbsolutePath());
            LogInfo.log(this.TAG, "isPersionalized:" + Runtime.isPersonalized());
            if (!Runtime.isPersonalized()) {
                Runtime.personalize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogInfo.log(this.TAG, e.getMessage() + ";;;" + e.getCause());
            LogInfo.log(this.TAG, "runtime initialization or personalization error: " + e.getLocalizedMessage());
            bean.setResultCode(DrmResultCodeEnum.DRM_INIT_ERROR.getResultCode());
            bean.setLastProSuccess(false);
        }
        LogInfo.log(this.TAG, "---drm init and personalize time(ms)" + (System.currentTimeMillis() - beginTime));
        return bean;
    }

    public void readTokenXml(DrmResultBean bean) {
        LogInfo.log(this.TAG, "start to readTokenXml ");
        long tokenXmlReadTime = System.currentTimeMillis();
        String licenseToken = null;
        if (!TextUtils.isEmpty(this.mDrmXmlUrl)) {
            licenseToken = HttpUtils.doG3HttpGetRequestToString(this.mDrmXmlUrl);
        }
        if (TextUtils.isEmpty(licenseToken)) {
            bean.setLastProSuccess(false);
            bean.setResultCode(DrmResultCodeEnum.DRM_TOKEN_XML_READ_ERROR.getResultCode());
        }
        bean.setTokenXmlContent(licenseToken);
        LogInfo.log(this.TAG, "tokenContent:" + licenseToken);
        LogInfo.log(this.TAG, "---drm token xml read time(ms)" + (System.currentTimeMillis() - tokenXmlReadTime));
    }

    private void processToken(DrmResultBean bean) {
        LogInfo.log(this.TAG, "start to processToken ");
        long tokenProcessStartTime = System.currentTimeMillis();
        boolean isNeedProcessToken = true;
        try {
            isNeedProcessToken = isNeedProcessToken(bean);
            if (isNeedProcessToken) {
                Runtime.processServiceToken(bean.getTokenXmlContent());
            }
        } catch (ErrorCodeException e1) {
            LogInfo.log(this.TAG, "Could not acquire the license from the license acquisition token - exiting" + e1.getLocalizedMessage());
            bean.setResultCode(DrmResultCodeEnum.DRM_PROCESS_TOKEN_ERROR.getResultCode());
            bean.setLastProSuccess(false);
        }
        LogInfo.log(this.TAG, "---isNeddPocess" + isNeedProcessToken + "///License process Service Token time (ms): " + (System.currentTimeMillis() - tokenProcessStartTime));
    }

    private boolean isNeedProcessToken(DrmResultBean bean) {
        boolean result = true;
        if (!(bean == null || TextUtils.isEmpty(bean.getTokenXmlContent()))) {
            String content = bean.getTokenXmlContent();
            if (!TextUtils.isEmpty(content)) {
                try {
                    int start = content.indexOf("<ContentID>urn:letv:kid:") + 11;
                    content = content.substring(start, start + 45);
                    Runtime.checkLicense(content);
                    result = false;
                } catch (ErrorCodeException e) {
                    LogInfo.log(this.TAG, "drm checkLicense exception..." + e.getLocalizedMessage());
                    e.printStackTrace();
                    result = true;
                }
            }
            LogInfo.log(this.TAG, "drm contentId:" + content + ",isNeedProcessToken:" + result);
        }
        return result;
    }

    private PlaylistProxy startPlayProxy(DrmResultBean bean) {
        ErrorCodeException e;
        PlaylistProxy playerProxy = null;
        long playProxyStartTime = System.currentTimeMillis();
        LogInfo.log(this.TAG, "start to startPlayProxy ");
        try {
            PlaylistProxy playerProxy2 = new PlaylistProxy(EnumSet.noneOf(Flags.class), this, HandlerUtils.getUiThreadHandler());
            try {
                DrmModel.setPlayProxy(playerProxy2);
                playerProxy2.start();
                playerProxy = playerProxy2;
            } catch (ErrorCodeException e2) {
                e = e2;
                playerProxy = playerProxy2;
                LogInfo.log(this.TAG, "playlist proxy error: " + e.getLocalizedMessage());
                bean.setResultCode(DrmResultCodeEnum.DRM_PLAYPROXY_START_ERROR.getResultCode());
                bean.setLastProSuccess(false);
                LogInfo.log(this.TAG, "---playlist Proxy start time (ms): " + (System.currentTimeMillis() - playProxyStartTime));
                return playerProxy;
            }
        } catch (ErrorCodeException e3) {
            e = e3;
            LogInfo.log(this.TAG, "playlist proxy error: " + e.getLocalizedMessage());
            bean.setResultCode(DrmResultCodeEnum.DRM_PLAYPROXY_START_ERROR.getResultCode());
            bean.setLastProSuccess(false);
            LogInfo.log(this.TAG, "---playlist Proxy start time (ms): " + (System.currentTimeMillis() - playProxyStartTime));
            return playerProxy;
        }
        LogInfo.log(this.TAG, "---playlist Proxy start time (ms): " + (System.currentTimeMillis() - playProxyStartTime));
        return playerProxy;
    }

    public void makePlayUrl(PlaylistProxy playerProxy, DrmResultBean bean) {
        MediaSourceParams params = new MediaSourceParams();
        params.sourceContentType = this.mContentType.getType();
        long proxyMakeUrlStartTime = System.currentTimeMillis();
        LogInfo.log(this.TAG, "start to makePlayUrl: mContentType " + params.sourceContentType);
        LogInfo.log(this.TAG, "source Type " + DrmCommenUtils.getSourceTypeByContentType(this.mContentType));
        try {
            bean.setDrmMediaUrl(playerProxy.makeUrl(this.mMediaSourceUrl, DrmCommenUtils.getSourceTypeByContentType(this.mContentType), params));
            bean.setResultCode(DrmResultCodeEnum.DRM_OK.getResultCode());
        } catch (Exception e) {
            e.printStackTrace();
            LogInfo.log(this.TAG, "playback error: " + e.getLocalizedMessage());
            bean.setResultCode(DrmResultCodeEnum.DRM_MAKE_URL_ERROR.getResultCode());
        }
        LogInfo.log(this.TAG, "---playlist Proxy make url time (ms): " + (System.currentTimeMillis() - proxyMakeUrlStartTime));
    }

    public DrmResultBean startDrmSoftProcess() {
        LogInfo.log(this.TAG, "drmtask-------videoPlayUrl:" + this.mMediaSourceUrl + ".....drmtokenurl:" + this.mDrmXmlUrl);
        long drmBeginTime = System.currentTimeMillis();
        DrmResultBean bean = initDrm();
        if (bean.isPreProSuccess() && isCanRun()) {
            readTokenXml(bean);
            if (bean.isPreProSuccess() && isCanRun()) {
                processToken(bean);
                if (bean.isPreProSuccess() && isCanRun()) {
                    PlaylistProxy playerProxy = startPlayProxy(bean);
                    if (bean.isPreProSuccess() && isCanRun()) {
                        makePlayUrl(playerProxy, bean);
                        LogInfo.log(this.TAG, "---all drm deal play url use time (ms):" + (System.currentTimeMillis() - drmBeginTime));
                    }
                }
            }
        }
        return bean;
    }

    public void onErrorNotification(int errorCode, String errorString) {
        LogInfo.log(this.TAG, "PlaylistProxy Event: Error Notification, error code = " + errorCode + ", error string = " + errorString);
    }
}
