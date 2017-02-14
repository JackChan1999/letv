package com.tencent.connect.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Base64;
import com.sina.weibo.sdk.component.WidgetRequestParam;
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.AssistActivity;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.AsynLoadImg;
import com.tencent.open.utils.AsynLoadImgBack;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.TemporaryStorage;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QQShare extends BaseApi {
    public static final int QQ_SHARE_SUMMARY_MAX_LENGTH = 60;
    public static final int QQ_SHARE_TITLE_MAX_LENGTH = 45;
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final int SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN = 1;
    public static final int SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE = 2;
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_KEY_TYPE = "req_type";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final int SHARE_TO_QQ_TYPE_APP = 6;
    public static final int SHARE_TO_QQ_TYPE_AUDIO = 2;
    public static final int SHARE_TO_QQ_TYPE_DEFAULT = 1;
    public static final int SHARE_TO_QQ_TYPE_IMAGE = 5;
    private static final String a = (f.d + ".QQShare");
    public String mViaShareQQType = "";

    public QQShare(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void shareToQQ(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(a, "shareToQQ() -- start.");
        String string = bundle.getString("imageUrl");
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        String string4 = bundle.getString("targetUrl");
        String string5 = bundle.getString("imageLocalUrl");
        int i = bundle.getInt("req_type", 1);
        f.c(a, "shareToQQ -- type: " + i);
        switch (i) {
            case 1:
                this.mViaShareQQType = "1";
                break;
            case 2:
                this.mViaShareQQType = "3";
                break;
            case 5:
                this.mViaShareQQType = "2";
                break;
            case 6:
                this.mViaShareQQType = "4";
                break;
        }
        if (i == 6) {
            if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_0_0) < 0) {
                iUiListener.onError(new UiError(-15, Constants.MSG_PARAM_APPSHARE_TOO_LOW, null));
                f.e(a, "shareToQQ, app share is not support below qq5.0.");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, app share is not support below qq5.0.");
                return;
            }
            string4 = String.format(ServerSetting.APP_DETAIL_PAGE, new Object[]{this.mToken.getAppId(), "mqq"});
            bundle.putString("targetUrl", string4);
        }
        if (Util.hasSDCard() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
            if (i == 5) {
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_QQ_VERSION_ERROR, null));
                    f.e(a, "shareToQQ, version below 4.3 is not support.");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, version below 4.3 is not support.");
                    return;
                } else if (!Util.fileExists(string5)) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    f.e(a, "shareToQQ -- error: 非法的图片地址!");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR);
                    return;
                }
            }
            if (i != 5) {
                if (TextUtils.isEmpty(string4) || !(string4.startsWith("http://") || string4.startsWith("https://"))) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_ERROR, null));
                    f.e(a, "shareToQQ, targetUrl is empty or illegal..");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, targetUrl is empty or illegal..");
                    return;
                } else if (TextUtils.isEmpty(string2)) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                    f.e(a, "shareToQQ, title is empty.");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, title is empty.");
                    return;
                }
            }
            if (TextUtils.isEmpty(string) || string.startsWith("http://") || string.startsWith("https://") || new File(string).exists()) {
                if (!TextUtils.isEmpty(string2) && string2.length() > 45) {
                    bundle.putString("title", Util.subString(string2, 45, null, null));
                }
                if (!TextUtils.isEmpty(string3) && string3.length() > 60) {
                    bundle.putString("summary", Util.subString(string3, 60, null, null));
                }
                if (Util.isMobileQQSupportShare(activity)) {
                    a(activity, bundle, iUiListener);
                } else {
                    c(activity, bundle, iUiListener);
                }
                f.c(a, "shareToQQ() -- end.");
                return;
            }
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
            f.e(a, " shareToQQ, image url is emprty or illegal.");
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ, image url is emprty or illegal.");
            return;
        }
        iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        f.e(a, "shareToQQ sdcard is null--end");
        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQQ sdcard is null");
    }

    private void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "shareToMobileQQ() -- start.");
        String string = bundle.getString("imageUrl");
        final String string2 = bundle.getString("title");
        final String string3 = bundle.getString("summary");
        f.b(a, "shareToMobileQQ -- imageUrl: " + string);
        if (TextUtils.isEmpty(string)) {
            b(activity, bundle, iUiListener);
        } else if (!Util.isValidUrl(string)) {
            bundle.putString("imageUrl", null);
            if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) < 0) {
                f.b(a, "shareToMobileQQ -- QQ Version is < 4.3.0 ");
                b(activity, bundle, iUiListener);
            } else {
                f.b(a, "shareToMobileQQ -- QQ Version is > 4.3.0 ");
                r2 = bundle;
                r5 = iUiListener;
                r6 = activity;
                a.a((Context) activity, string, new AsynLoadImgBack(this) {
                    final /* synthetic */ QQShare f;

                    public void saved(int i, String str) {
                        if (i == 0) {
                            r2.putString("imageLocalUrl", str);
                        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                            if (r5 != null) {
                                r5.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                                f.e(QQShare.a, "shareToMobileQQ -- error: 获取分享图片失败!");
                            }
                            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                            return;
                        }
                        this.f.b(r6, r2, r5);
                    }

                    public void batchSaved(int i, ArrayList<String> arrayList) {
                    }
                });
            }
        } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
            if (iUiListener != null) {
                iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
                f.e(a, Constants.MSG_SHARE_NOSD_ERROR);
            }
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_NOSD_ERROR);
            return;
        } else if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_3_0) >= 0) {
            b(activity, bundle, iUiListener);
        } else {
            r2 = bundle;
            r5 = iUiListener;
            r6 = activity;
            new AsynLoadImg(activity).save(string, new AsynLoadImgBack(this) {
                final /* synthetic */ QQShare f;

                public void saved(int i, String str) {
                    if (i == 0) {
                        r2.putString("imageLocalUrl", str);
                    } else if (TextUtils.isEmpty(string2) && TextUtils.isEmpty(string3)) {
                        if (r5 != null) {
                            r5.onError(new UiError(-6, Constants.MSG_SHARE_GETIMG_ERROR, null));
                            f.e(QQShare.a, "shareToMobileQQ -- error: 获取分享图片失败!");
                        }
                        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.f.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_GETIMG_ERROR);
                        return;
                    }
                    this.f.b(r6, r2, r5);
                }

                public void batchSaved(int i, ArrayList<String> arrayList) {
                }
            });
        }
        f.c(f.d, "shareToMobileQQ() -- end");
    }

    private void b(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "doShareToQQ() -- start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_fri?src_type=app&version=1&file_type=news");
        Object string = bundle.getString("imageUrl");
        Object string2 = bundle.getString("title");
        Object string3 = bundle.getString("summary");
        Object string4 = bundle.getString("targetUrl");
        Object string5 = bundle.getString("audio_url");
        int i = bundle.getInt("req_type", 1);
        int i2 = bundle.getInt("cflag", 0);
        Object string6 = bundle.getString("share_qq_ext_str");
        String applicationLable = Util.getApplicationLable(activity);
        if (applicationLable == null) {
            applicationLable = bundle.getString("appName");
        }
        Object string7 = bundle.getString("imageLocalUrl");
        Object appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        f.a(a, "doShareToQQ -- openid: " + openId);
        if (!TextUtils.isEmpty(string)) {
            stringBuffer.append("&image_url=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
        }
        if (!TextUtils.isEmpty(string7)) {
            stringBuffer.append("&file_data=" + Base64.encodeToString(Util.getBytesUTF8(string7), 2));
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&title=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
        }
        if (!TextUtils.isEmpty(string3)) {
            stringBuffer.append("&description=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(string4)) {
            stringBuffer.append("&url=" + Base64.encodeToString(Util.getBytesUTF8(string4), 2));
        }
        if (!TextUtils.isEmpty(applicationLable)) {
            if (applicationLable.length() > 20) {
                applicationLable = applicationLable.substring(0, 20) + "...";
            }
            stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(applicationLable), 2));
        }
        if (!TextUtils.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
        }
        if (!TextUtils.isEmpty(string5)) {
            stringBuffer.append("&audioUrl=" + Base64.encodeToString(Util.getBytesUTF8(string5), 2));
        }
        stringBuffer.append("&req_type=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i)), 2));
        if (!TextUtils.isEmpty(string6)) {
            stringBuffer.append("&share_qq_ext_str=" + Base64.encodeToString(Util.getBytesUTF8(string6), 2));
        }
        stringBuffer.append("&cflag=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i2)), 2));
        f.a(a, "doShareToQQ -- url: " + stringBuffer.toString());
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToNativeQQ");
        this.mActivityIntent = new Intent("android.intent.action.VIEW");
        this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
        this.mActivityIntent.putExtra("pkg_name", activity.getPackageName());
        if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) < 0) {
            f.c(a, "doShareToQQ, qqver below 4.6.");
            if (hasActivityForIntent()) {
                startAssitActivity(activity, iUiListener);
            }
        } else {
            Object obj = TemporaryStorage.set(SystemUtils.QQ_SHARE_CALLBACK_ACTION, iUiListener);
            if (obj != null) {
                ((IUiListener) obj).onCancel();
                f.c(a, "doShareToQQ, last listener is not null, cancel it.");
            }
            if (hasActivityForIntent()) {
                AssistActivity.isQQMobileShare = true;
                startAssistActivity(activity, Constants.REQUEST_QQ_SHARE);
            }
        }
        if (hasActivityForIntent()) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QQ, "10", "3", "0", this.mViaShareQQType, "0", "1", "0");
            d.a().a(0, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
        } else {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QQ, "10", "3", "1", this.mViaShareQQType, "0", "1", "0");
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
        }
        f.c(f.d, "doShareToQQ() --end");
    }

    private void c(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "shareToH5QQ() -- start");
        Object obj = TemporaryStorage.set(SystemUtils.QQ_SHARE_CALLBACK_ACTION, iUiListener);
        if (obj != null) {
            f.c(a, "shareToH5QQ, last listener is not null, cancel it.");
            ((IUiListener) obj).onCancel();
        }
        StringBuffer stringBuffer = new StringBuffer("http://openmobile.qq.com/api/check?page=shareindex.html&style=9");
        if (bundle == null) {
            bundle = new Bundle();
        }
        stringBuffer = a(stringBuffer, bundle);
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToH5QQ");
        Bundle bundle2 = new Bundle();
        bundle2.putString("callbackAction", SystemUtils.QQ_SHARE_CALLBACK_ACTION);
        bundle2.putString("viaShareType", this.mViaShareQQType);
        bundle2.putString("url", stringBuffer.toString());
        bundle2.putString("openId", this.mToken.getOpenId());
        bundle2.putString("appId", this.mToken.getAppId());
        startAssistActivity(activity, bundle2, Constants.REQUEST_QQ_SHARE);
        d.a().a(0, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(0), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToH5QQ");
        f.c(f.d, "shareToH5QQ() --end");
    }

    private StringBuffer a(StringBuffer stringBuffer, Bundle bundle) {
        f.c(f.d, "fillShareToQQParams() --start");
        String str = "...";
        bundle.putString("action", SystemUtils.QQ_SHARE_CALLBACK_ACTION);
        bundle.putString("appId", this.mToken.getAppId());
        bundle.putString("sdkp", "a");
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        String str2 = WidgetRequestParam.REQ_PARAM_COMMENT_CONTENT;
        if (bundle.containsKey(str2) && bundle.getString(str2).length() > 40) {
            bundle.putString(str2, bundle.getString(str2).substring(0, 40) + str);
        }
        str2 = "summary";
        if (bundle.containsKey(str2) && bundle.getString(str2).length() > 80) {
            bundle.putString(str2, bundle.getString(str2).substring(0, 80) + str);
        }
        stringBuffer.append("&" + Util.encodeUrl(bundle).replaceAll("\\+", "%20"));
        f.c(f.d, "fillShareToQQParams() --end");
        return stringBuffer;
    }

    public void releaseResource() {
        TemporaryStorage.remove(SystemUtils.QQ_SHARE_CALLBACK_ACTION);
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }
}
