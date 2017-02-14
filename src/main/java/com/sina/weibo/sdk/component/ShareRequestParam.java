package com.sina.weibo.sdk.component;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseRequest;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.net.WeiboParameters;
import com.sina.weibo.sdk.utils.Base64;
import com.sina.weibo.sdk.utils.MD5;
import com.sina.weibo.sdk.utils.Utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ShareRequestParam extends BrowserRequestParamBase {
    public static final String REQ_PARAM_AID = "aid";
    public static final String REQ_PARAM_KEY_HASH = "key_hash";
    public static final String REQ_PARAM_PACKAGENAME = "packagename";
    public static final String REQ_PARAM_PICINFO = "picinfo";
    public static final String REQ_PARAM_SOURCE = "source";
    public static final String REQ_PARAM_TITLE = "title";
    public static final String REQ_PARAM_TOKEN = "access_token";
    public static final String REQ_PARAM_VERSION = "version";
    public static final String REQ_UPLOAD_PIC_PARAM_IMG = "img";
    public static final String RESP_UPLOAD_PIC_PARAM_CODE = "code";
    public static final String RESP_UPLOAD_PIC_PARAM_DATA = "data";
    public static final int RESP_UPLOAD_PIC_SUCC_CODE = 1;
    private static final String SHARE_URL = "http://service.weibo.com/share/mobilesdk.php";
    public static final String UPLOAD_PIC_URL = "http://service.weibo.com/share/mobilesdk_uppic.php";
    private String mAppKey;
    private String mAppPackage;
    private WeiboAuthListener mAuthListener;
    private String mAuthListenerKey;
    private byte[] mBase64ImgData;
    private BaseRequest mBaseRequest;
    private String mHashKey;
    private String mShareContent;
    private String mToken;

    public ShareRequestParam(Context context) {
        super(context);
        this.mLaucher = BrowserLauncher.SHARE;
    }

    protected void onSetupRequestParam(Bundle data) {
        this.mAppKey = data.getString("source");
        this.mAppPackage = data.getString(REQ_PARAM_PACKAGENAME);
        this.mHashKey = data.getString(REQ_PARAM_KEY_HASH);
        this.mToken = data.getString("access_token");
        this.mAuthListenerKey = data.getString(AuthRequestParam.EXTRA_KEY_LISTENER);
        if (!TextUtils.isEmpty(this.mAuthListenerKey)) {
            this.mAuthListener = WeiboCallbackManager.getInstance(this.mContext).getWeiboAuthListener(this.mAuthListenerKey);
        }
        handleSharedMessage(data);
        this.mUrl = buildUrl("");
    }

    private void handleSharedMessage(Bundle bundle) {
        WeiboMultiMessage multiMessage = new WeiboMultiMessage();
        multiMessage.toObject(bundle);
        StringBuilder content = new StringBuilder();
        if (multiMessage.textObject instanceof TextObject) {
            content.append(multiMessage.textObject.text);
        }
        if (multiMessage.imageObject instanceof ImageObject) {
            ImageObject imageObject = multiMessage.imageObject;
            handleMblogPic(imageObject.imagePath, imageObject.imageData);
        }
        if (multiMessage.mediaObject instanceof TextObject) {
            content.append(((TextObject) multiMessage.mediaObject).text);
        }
        if (multiMessage.mediaObject instanceof ImageObject) {
            imageObject = (ImageObject) multiMessage.mediaObject;
            handleMblogPic(imageObject.imagePath, imageObject.imageData);
        }
        if (multiMessage.mediaObject instanceof WebpageObject) {
            content.append(" ").append(multiMessage.mediaObject.actionUrl);
        }
        if (multiMessage.mediaObject instanceof MusicObject) {
            content.append(" ").append(multiMessage.mediaObject.actionUrl);
        }
        if (multiMessage.mediaObject instanceof VideoObject) {
            content.append(" ").append(multiMessage.mediaObject.actionUrl);
        }
        if (multiMessage.mediaObject instanceof VoiceObject) {
            content.append(" ").append(multiMessage.mediaObject.actionUrl);
        }
        this.mShareContent = content.toString();
    }

    private void handleMblogPic(String picPath, byte[] thumbData) {
        Throwable th;
        try {
            if (!TextUtils.isEmpty(picPath)) {
                File picFile = new File(picPath);
                if (picFile.exists() && picFile.canRead() && picFile.length() > 0) {
                    byte[] tmpPic = new byte[((int) picFile.length())];
                    FileInputStream fis = null;
                    try {
                        FileInputStream fis2 = new FileInputStream(picFile);
                        try {
                            fis2.read(tmpPic);
                            this.mBase64ImgData = Base64.encodebyte(tmpPic);
                            if (fis2 != null) {
                                try {
                                    fis2.close();
                                    return;
                                } catch (Exception e) {
                                    return;
                                }
                            }
                            return;
                        } catch (IOException e2) {
                            fis = fis2;
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (Exception e3) {
                                }
                            }
                            if (thumbData != null) {
                                return;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fis = fis2;
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (Exception e4) {
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e5) {
                        if (fis != null) {
                            fis.close();
                        }
                        if (thumbData != null) {
                        }
                        return;
                    } catch (Throwable th3) {
                        th = th3;
                        if (fis != null) {
                            fis.close();
                        }
                        throw th;
                    }
                }
            }
        } catch (SecurityException e6) {
        }
        if (thumbData != null && thumbData.length > 0) {
            this.mBase64ImgData = Base64.encodebyte(thumbData);
        }
    }

    public void onCreateRequestParamBundle(Bundle data) {
        if (this.mBaseRequest != null) {
            this.mBaseRequest.toBundle(data);
        }
        if (!TextUtils.isEmpty(this.mAppPackage)) {
            this.mHashKey = MD5.hexdigest(Utility.getSign(this.mContext, this.mAppPackage));
        }
        data.putString("access_token", this.mToken);
        data.putString("source", this.mAppKey);
        data.putString(REQ_PARAM_PACKAGENAME, this.mAppPackage);
        data.putString(REQ_PARAM_KEY_HASH, this.mHashKey);
        data.putString("_weibo_appPackage", this.mAppPackage);
        data.putString("_weibo_appKey", this.mAppKey);
        data.putInt("_weibo_flag", 538116905);
        data.putString("_weibo_sign", this.mHashKey);
        if (this.mAuthListener != null) {
            WeiboCallbackManager manager = WeiboCallbackManager.getInstance(this.mContext);
            this.mAuthListenerKey = manager.genCallbackKey();
            manager.setWeiboAuthListener(this.mAuthListenerKey, this.mAuthListener);
            data.putString(AuthRequestParam.EXTRA_KEY_LISTENER, this.mAuthListenerKey);
        }
    }

    public void execRequest(Activity act, int action) {
        if (action == 3) {
            sendSdkCancleResponse(act);
            WeiboSdkBrowser.closeBrowser(act, this.mAuthListenerKey, null);
        }
    }

    public boolean hasImage() {
        if (this.mBase64ImgData == null || this.mBase64ImgData.length <= 0) {
            return false;
        }
        return true;
    }

    public WeiboParameters buildUploadPicParam(WeiboParameters param) {
        if (hasImage()) {
            param.put("img", new String(this.mBase64ImgData));
        }
        return param;
    }

    public String buildUrl(String picid) {
        Builder builder = Uri.parse(SHARE_URL).buildUpon();
        builder.appendQueryParameter("title", this.mShareContent);
        builder.appendQueryParameter("version", "0031105000");
        if (!TextUtils.isEmpty(this.mAppKey)) {
            builder.appendQueryParameter("source", this.mAppKey);
        }
        if (!TextUtils.isEmpty(this.mToken)) {
            builder.appendQueryParameter("access_token", this.mToken);
        }
        String aid = Utility.getAid(this.mContext, this.mAppKey);
        if (!TextUtils.isEmpty(aid)) {
            builder.appendQueryParameter("aid", aid);
        }
        if (!TextUtils.isEmpty(this.mAppPackage)) {
            builder.appendQueryParameter(REQ_PARAM_PACKAGENAME, this.mAppPackage);
        }
        if (!TextUtils.isEmpty(this.mHashKey)) {
            builder.appendQueryParameter(REQ_PARAM_KEY_HASH, this.mHashKey);
        }
        if (!TextUtils.isEmpty(picid)) {
            builder.appendQueryParameter(REQ_PARAM_PICINFO, picid);
        }
        return builder.build().toString();
    }

    private void sendSdkResponse(Activity activity, int errCode, String errMsg) {
        Bundle bundle = activity.getIntent().getExtras();
        if (bundle != null) {
            Intent intent = new Intent("com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY");
            intent.setFlags(131072);
            intent.setPackage(bundle.getString("_weibo_appPackage"));
            intent.putExtras(bundle);
            intent.putExtra("_weibo_appPackage", activity.getPackageName());
            intent.putExtra("_weibo_resp_errcode", errCode);
            intent.putExtra("_weibo_resp_errstr", errMsg);
            try {
                activity.startActivityForResult(intent, 765);
            } catch (ActivityNotFoundException e) {
            }
        }
    }

    public void sendSdkCancleResponse(Activity activity) {
        sendSdkResponse(activity, 1, "send cancel!!!");
    }

    public void sendSdkOkResponse(Activity activity) {
        sendSdkResponse(activity, 0, "send ok!!!");
    }

    public void sendSdkErrorResponse(Activity activity, String msg) {
        sendSdkResponse(activity, 2, msg);
    }

    public void setBaseRequest(BaseRequest request) {
        this.mBaseRequest = request;
    }

    public String getAppPackage() {
        return this.mAppPackage;
    }

    public void setAppPackage(String mAppPackage) {
        this.mAppPackage = mAppPackage;
    }

    public String getToken() {
        return this.mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public void setAppKey(String mAppKey) {
        this.mAppKey = mAppKey;
    }

    public String getHashKey() {
        return this.mHashKey;
    }

    public String getShareContent() {
        return this.mShareContent;
    }

    public byte[] getBase64ImgData() {
        return this.mBase64ImgData;
    }

    public WeiboAuthListener getAuthListener() {
        return this.mAuthListener;
    }

    public String getAuthListenerKey() {
        return this.mAuthListenerKey;
    }

    public void setAuthListener(WeiboAuthListener mAuthListener) {
        this.mAuthListener = mAuthListener;
    }
}
