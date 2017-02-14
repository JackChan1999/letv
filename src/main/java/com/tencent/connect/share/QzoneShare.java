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
import com.tencent.connect.a.a;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.b.d;
import com.tencent.open.utils.AsynLoadImgBack;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.TemporaryStorage;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.net.URLEncoder;
import java.util.ArrayList;

/* compiled from: ProGuard */
public class QzoneShare extends BaseApi {
    public static final String SHARE_TO_QQ_APP_NAME = "appName";
    public static final String SHARE_TO_QQ_AUDIO_URL = "audio_url";
    public static final String SHARE_TO_QQ_EXT_INT = "cflag";
    public static final String SHARE_TO_QQ_EXT_STR = "share_qq_ext_str";
    public static final String SHARE_TO_QQ_IMAGE_LOCAL_URL = "imageLocalUrl";
    public static final String SHARE_TO_QQ_IMAGE_URL = "imageUrl";
    public static final String SHARE_TO_QQ_SITE = "site";
    public static final String SHARE_TO_QQ_SUMMARY = "summary";
    public static final String SHARE_TO_QQ_TARGET_URL = "targetUrl";
    public static final String SHARE_TO_QQ_TITLE = "title";
    public static final String SHARE_TO_QZONE_KEY_TYPE = "req_type";
    public static final int SHARE_TO_QZONE_TYPE_APP = 6;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE = 5;
    public static final int SHARE_TO_QZONE_TYPE_IMAGE_TEXT = 1;
    public static final int SHARE_TO_QZONE_TYPE_NO_TYPE = 0;
    private boolean a = true;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;
    public String mViaShareQzoneType = "";

    public QzoneShare(Context context, QQToken qQToken) {
        super(qQToken);
    }

    public void shareToQzone(final Activity activity, final Bundle bundle, final IUiListener iUiListener) {
        f.c(f.d, "shareToQzone() -- start");
        if (bundle == null) {
            iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_NULL_ERROR, null));
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_NULL_ERROR);
            return;
        }
        String string = bundle.getString("title");
        String string2 = bundle.getString("summary");
        String string3 = bundle.getString("targetUrl");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object applicationLable = Util.getApplicationLable(activity);
        if (applicationLable == null) {
            applicationLable = bundle.getString("appName");
        } else if (applicationLable.length() > 20) {
            applicationLable = applicationLable.substring(0, 20) + "...";
        }
        int i = bundle.getInt("req_type");
        switch (i) {
            case 1:
                this.mViaShareQzoneType = "1";
                break;
            case 5:
                this.mViaShareQzoneType = "2";
                break;
            case 6:
                this.mViaShareQzoneType = "4";
                break;
            default:
                this.mViaShareQzoneType = "1";
                break;
        }
        String str;
        switch (i) {
            case 1:
                this.a = true;
                this.b = false;
                this.c = true;
                this.d = false;
                str = string3;
                string3 = string;
                string = str;
                break;
            case 5:
                iUiListener.onError(new UiError(-5, Constants.MSG_SHARE_TYPE_ERROR, null));
                f.e(f.d, "shareToQzone() error--end暂不支持纯图片分享到空间，建议使用图文分享");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() 暂不支持纯图片分享到空间，建议使用图文分享");
                return;
            case 6:
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_5_0_0) >= 0) {
                    string3 = String.format(ServerSetting.APP_DETAIL_PAGE, new Object[]{this.mToken.getAppId(), "mqq"});
                    bundle.putString("targetUrl", string3);
                    str = string3;
                    string3 = string;
                    string = str;
                    break;
                }
                iUiListener.onError(new UiError(-15, Constants.MSG_PARAM_APPSHARE_TOO_LOW, null));
                f.b(f.d, "-->shareToQzone, app share is not support below qq5.0.");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone, app share is not support below qq5.0.");
                return;
            default:
                if (!Util.isEmpty(string) || !Util.isEmpty(string2)) {
                    this.a = true;
                } else if (stringArrayList == null || stringArrayList.size() == 0) {
                    string = "来自" + applicationLable + "的分享";
                    this.a = true;
                } else {
                    this.a = false;
                }
                this.b = false;
                this.c = true;
                this.d = false;
                str = string3;
                string3 = string;
                Object obj = str;
                break;
        }
        if (Util.hasSDCard() || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
            String str2;
            if (this.a) {
                if (TextUtils.isEmpty(obj)) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_NULL_ERROR, null));
                    f.e(f.d, "shareToQzone() targetUrl null error--end");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_TARGETURL_NULL_ERROR);
                    return;
                } else if (!Util.isValidUrl(obj)) {
                    iUiListener.onError(new UiError(-5, Constants.MSG_PARAM_TARGETURL_ERROR, null));
                    f.e(f.d, "shareToQzone() targetUrl error--end");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_PARAM_TARGETURL_ERROR);
                    return;
                }
            }
            if (this.b) {
                bundle.putString("title", "");
                bundle.putString("summary", "");
            } else if (this.c && Util.isEmpty(string3)) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_TITLE_NULL_ERROR, null));
                f.e(f.d, "shareToQzone() title is null--end");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() title is null");
                return;
            } else {
                if (!Util.isEmpty(string3) && string3.length() > 200) {
                    bundle.putString("title", Util.subString(string3, 200, null, null));
                }
                if (!Util.isEmpty(string2) && string2.length() > 600) {
                    bundle.putString("summary", Util.subString(string2, 600, null, null));
                }
            }
            if (!TextUtils.isEmpty(applicationLable)) {
                bundle.putString("appName", applicationLable);
            }
            if (stringArrayList != null && (stringArrayList == null || stringArrayList.size() != 0)) {
                for (int i2 = 0; i2 < stringArrayList.size(); i2++) {
                    str2 = (String) stringArrayList.get(i2);
                    if (!(Util.isValidUrl(str2) || Util.isValidPath(str2))) {
                        stringArrayList.remove(i2);
                    }
                }
                if (stringArrayList.size() == 0) {
                    iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_FORMAT_ERROR, null));
                    f.e(f.d, "shareToQzone() MSG_PARAM_IMAGE_URL_FORMAT_ERROR--end");
                    d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() 非法的图片地址!");
                    return;
                }
                bundle.putStringArrayList("imageUrl", stringArrayList);
            } else if (this.d) {
                iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_ERROR, null));
                f.e(f.d, "shareToQzone() imageUrl is null -- end");
                d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone() imageUrl is null");
                return;
            }
            if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
                a.a((Context) activity, stringArrayList, new AsynLoadImgBack(this) {
                    final /* synthetic */ QzoneShare d;

                    public void saved(int i, String str) {
                    }

                    public void batchSaved(int i, ArrayList<String> arrayList) {
                        if (i == 0) {
                            bundle.putStringArrayList("imageUrl", arrayList);
                        }
                        this.d.a(activity, bundle, iUiListener);
                    }
                });
            } else if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_2_0) < 0 || SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) >= 0) {
                b(activity, bundle, iUiListener);
            } else {
                QQShare qQShare = new QQShare(activity, this.mToken);
                if (stringArrayList != null && stringArrayList.size() > 0) {
                    str2 = (String) stringArrayList.get(0);
                    if (i != 5 || Util.fileExists(str2)) {
                        bundle.putString("imageLocalUrl", str2);
                    } else {
                        iUiListener.onError(new UiError(-6, Constants.MSG_PARAM_IMAGE_URL_MUST_BE_LOCAL, null));
                        f.e(f.d, "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToQzone()手Q版本过低，纯图分享不支持网路图片");
                        return;
                    }
                }
                if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_5_0) >= 0) {
                    bundle.putInt("cflag", 1);
                }
                qQShare.shareToQQ(activity, bundle, iUiListener);
            }
            f.c(f.d, "shareToQzone() --end");
            return;
        }
        iUiListener.onError(new UiError(-6, Constants.MSG_SHARE_NOSD_ERROR, null));
        f.e(f.d, "shareToQzone() sdcard is null--end");
        d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, Constants.MSG_SHARE_NOSD_ERROR);
    }

    private void a(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.c(f.d, "doshareToQzone() --start");
        StringBuffer stringBuffer = new StringBuffer("mqqapi://share/to_qzone?src_type=app&version=1&file_type=news");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object string = bundle.getString("title");
        Object string2 = bundle.getString("summary");
        Object string3 = bundle.getString("targetUrl");
        String string4 = bundle.getString("audio_url");
        int i = bundle.getInt("req_type", 1);
        Object string5 = bundle.getString("appName");
        int i2 = bundle.getInt("cflag", 0);
        String string6 = bundle.getString("share_qq_ext_str");
        CharSequence appId = this.mToken.getAppId();
        String openId = this.mToken.getOpenId();
        f.b("doshareToQzone", "openId:" + openId);
        if (stringArrayList != null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            int size = stringArrayList.size() > 9 ? 9 : stringArrayList.size();
            for (int i3 = 0; i3 < size; i3++) {
                stringBuffer2.append(URLEncoder.encode((String) stringArrayList.get(i3)));
                if (i3 != size - 1) {
                    stringBuffer2.append(";");
                }
            }
            stringBuffer.append("&image_url=" + Base64.encodeToString(Util.getBytesUTF8(stringBuffer2.toString()), 2));
        }
        if (!TextUtils.isEmpty(string)) {
            stringBuffer.append("&title=" + Base64.encodeToString(Util.getBytesUTF8(string), 2));
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuffer.append("&description=" + Base64.encodeToString(Util.getBytesUTF8(string2), 2));
        }
        if (!TextUtils.isEmpty(appId)) {
            stringBuffer.append("&share_id=" + appId);
        }
        if (!TextUtils.isEmpty(string3)) {
            stringBuffer.append("&url=" + Base64.encodeToString(Util.getBytesUTF8(string3), 2));
        }
        if (!TextUtils.isEmpty(string5)) {
            stringBuffer.append("&app_name=" + Base64.encodeToString(Util.getBytesUTF8(string5), 2));
        }
        if (!Util.isEmpty(openId)) {
            stringBuffer.append("&open_id=" + Base64.encodeToString(Util.getBytesUTF8(openId), 2));
        }
        if (!Util.isEmpty(string4)) {
            stringBuffer.append("&audioUrl=" + Base64.encodeToString(Util.getBytesUTF8(string4), 2));
        }
        stringBuffer.append("&req_type=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i)), 2));
        if (!Util.isEmpty(string6)) {
            stringBuffer.append("&share_qq_ext_str=" + Base64.encodeToString(Util.getBytesUTF8(string6), 2));
        }
        stringBuffer.append("&cflag=" + Base64.encodeToString(Util.getBytesUTF8(String.valueOf(i2)), 2));
        f.b("doshareToQzone, url: ", stringBuffer.toString());
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToNativeQQ");
        this.mActivityIntent = new Intent("android.intent.action.VIEW");
        this.mActivityIntent.setData(Uri.parse(stringBuffer.toString()));
        this.mActivityIntent.putExtra("pkg_name", activity.getPackageName());
        if (SystemUtils.compareQQVersion(activity, SystemUtils.QQ_VERSION_NAME_4_6_0) < 0) {
            if (hasActivityForIntent()) {
                startAssitActivity(activity, iUiListener);
            }
            f.c(f.d, "doShareToQzone() -- QQ Version is < 4.6.0");
        } else {
            f.c(f.d, "doShareToQzone() -- QQ Version is > 4.6.0");
            Object obj = TemporaryStorage.set(SystemUtils.QZONE_SHARE_CALLBACK_ACTION, iUiListener);
            if (obj != null) {
                f.c(f.d, "doShareToQzone() -- do listener onCancel()");
                ((IUiListener) obj).onCancel();
            }
            if (hasActivityForIntent()) {
                startAssistActivity(activity, Constants.REQUEST_QZONE_SHARE);
            }
        }
        if (hasActivityForIntent()) {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QZONE, "11", "3", "0", this.mViaShareQzoneType, "0", "1", "0");
            d.a().a(0, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "");
        } else {
            d.a().a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SHARE_TO_QZONE, "11", "3", "1", this.mViaShareQzoneType, "0", "1", "0");
            d.a().a(1, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "hasActivityForIntent fail");
        }
        f.c(f.d, "doShareToQzone() --end");
    }

    private void b(Activity activity, Bundle bundle, IUiListener iUiListener) {
        f.a(f.d, "shareToH5Qzone() -- start");
        Object obj = TemporaryStorage.set(SystemUtils.QZONE_SHARE_CALLBACK_ACTION, iUiListener);
        if (obj != null) {
            f.c(f.d, "shareToH5Qzone() -- do listener onCancel()");
            ((IUiListener) obj).onCancel();
        }
        StringBuffer stringBuffer = new StringBuffer("http://openmobile.qq.com/api/check2?page=qzshare.html&loginpage=loginindex.html&logintype=qzone");
        if (bundle == null) {
            bundle = new Bundle();
        }
        stringBuffer = a(stringBuffer, bundle);
        a.a(Global.getContext(), this.mToken, "requireApi", "shareToH5QQ");
        Bundle bundle2 = new Bundle();
        bundle2.putString("callbackAction", SystemUtils.QZONE_SHARE_CALLBACK_ACTION);
        bundle2.putString("viaShareType", this.mViaShareQzoneType);
        bundle2.putString("url", stringBuffer.toString());
        bundle2.putString("openId", this.mToken.getOpenId());
        bundle2.putString("appId", this.mToken.getAppId());
        startAssistActivity(activity, bundle2, Constants.REQUEST_QZONE_SHARE);
        d.a().a(0, "SHARE_CHECK_SDK", Constants.DEFAULT_UIN, this.mToken.getAppId(), String.valueOf(4), Long.valueOf(SystemClock.elapsedRealtime()), 0, 1, "shareToH5Qzone()");
        f.a(f.d, "shareToH5Qzone() --end");
    }

    private StringBuffer a(StringBuffer stringBuffer, Bundle bundle) {
        f.c(f.d, "fillShareToQQParams() --start");
        ArrayList stringArrayList = bundle.getStringArrayList("imageUrl");
        Object string = bundle.getString("appName");
        int i = bundle.getInt("req_type", 1);
        String string2 = bundle.getString("title");
        String string3 = bundle.getString("summary");
        bundle.putString("appId", this.mToken.getAppId());
        bundle.putString("sdkp", "a");
        bundle.putString("sdkv", Constants.SDK_VERSION);
        bundle.putString("status_os", VERSION.RELEASE);
        bundle.putString("status_machine", Build.MODEL);
        String str = "...";
        if (!Util.isEmpty(string2) && string2.length() > 40) {
            bundle.putString("title", string2.substring(0, 40) + "...");
        }
        if (!Util.isEmpty(string3) && string3.length() > 80) {
            bundle.putString("summary", string3.substring(0, 80) + "...");
        }
        if (!TextUtils.isEmpty(string)) {
            bundle.putString("site", string);
        }
        if (stringArrayList != null) {
            int size = stringArrayList.size();
            String[] strArr = new String[size];
            for (int i2 = 0; i2 < size; i2++) {
                strArr[i2] = (String) stringArrayList.get(i2);
            }
            bundle.putStringArray("imageUrl", strArr);
        }
        bundle.putString("type", String.valueOf(i));
        stringBuffer.append("&" + Util.encodeUrl(bundle).replaceAll("\\+", "%20"));
        f.c(f.d, "fillShareToQQParams() --end");
        return stringBuffer;
    }

    public void releaseResource() {
        TemporaryStorage.remove(SystemUtils.QZONE_SHARE_CALLBACK_ACTION);
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
    }
}
