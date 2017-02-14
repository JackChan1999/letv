package com.letv.redpacketsdk.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.letv.redpacketsdk.RedPacketSdkManager;
import com.letv.redpacketsdk.bean.PollingResult;
import com.letv.redpacketsdk.bean.RedPacketBean;
import com.letv.redpacketsdk.callback.AsyncTaskCallback;
import com.letv.redpacketsdk.callback.AsyncTaskImageLoaderCallback;
import com.letv.redpacketsdk.callback.GiftBeanCallback;
import com.letv.redpacketsdk.callback.RedPacketForecastCallback;
import com.letv.redpacketsdk.callback.RedPacketPollingCallback;
import com.letv.redpacketsdk.parser.GiftBeanParser;
import com.letv.redpacketsdk.parser.RedPacketParser;
import com.letv.redpacketsdk.ui.RedPacketForecastView;
import com.letv.redpacketsdk.utils.FileUtil;
import com.letv.redpacketsdk.utils.LogInfo;
import com.letv.redpacketsdk.utils.MobileUtil;
import java.io.File;
import org.json.JSONException;

public class NetworkManager {
    private static AsyncTaskHttp mGetRpBeanAsyncTask;

    public static void cleanGetRpBeanAsyncTaskCallback() {
        if (mGetRpBeanAsyncTask != null) {
            mGetRpBeanAsyncTask.cleanCallback();
            mGetRpBeanAsyncTask = null;
        }
    }

    public static void getRedpacketBean(final RedPacketPollingCallback callback) {
        if (MobileUtil.isNetworkConnected(RedPacketSdkManager.getInstance().getApplicationContext())) {
            if (mGetRpBeanAsyncTask != null) {
                mGetRpBeanAsyncTask.cleanCallback();
                mGetRpBeanAsyncTask = null;
            }
            mGetRpBeanAsyncTask = new AsyncTaskHttp(HTTPURL.getOpenUrl(), new AsyncTaskCallback() {
                public void getResult(String result) {
                    if (result != null) {
                        try {
                            RedPacketSdkManager.getInstance().setRedPacketBean(new RedPacketParser().parser(result, callback));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
            return;
        }
        PollingResult result = new PollingResult();
        result.hasRedPacket = false;
        callback.onReceivePollingResult(result);
        LogInfo.log("NetworkManager", "Network is not conn");
    }

    public static AsyncTaskImageLoader loadImage(String url, AsyncTaskImageLoaderCallback callback) {
        LogInfo.log("NetworkManager", "loadImage+url=" + url);
        Context context = RedPacketSdkManager.getInstance().getApplicationContext();
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String str = "";
        Bitmap bitmap = FileUtil.loadBitmapFromLocal(FileUtil.getCacheDir(context) + File.separator + FileUtil.getDownloadFileName(url));
        if (bitmap != null) {
            LogInfo.log("NetManager", "this image has cache");
            callback.imageDownloadResult(bitmap);
            return null;
        } else if (MobileUtil.isNetworkConnected(context)) {
            LogInfo.log("NetManager", "no cache, download image");
            return new AsyncTaskImageLoader(context, callback, url);
        } else {
            LogInfo.log("NetManager", "no cache, download image, no net");
            callback.imageDownloadResult(bitmap);
            return null;
        }
    }

    public static void getGiftBean(final GiftBeanCallback giftBeanCallBack) {
        Context context = RedPacketSdkManager.getInstance().getApplicationContext();
        if (MobileUtil.isNetworkConnected(context)) {
            LogInfo.log("NetworkManager", "getGiftBean");
            AsyncTaskHttp asyncTaskHttp = new AsyncTaskHttp(HTTPURL.getOpenRedPacketUrl(context), RedPacketSdkManager.getInstance().getToken(), new AsyncTaskCallback() {
                public void getResult(String result) {
                    LogInfo.log("getGiftBean", "getGiftBean result=" + result);
                    if (result != null) {
                        if (giftBeanCallBack != null) {
                            giftBeanCallBack.getGiftBeanCallBack(new GiftBeanParser().parser(result), result);
                        }
                    } else if (giftBeanCallBack != null) {
                        giftBeanCallBack.getGiftBeanCallBack(null, "");
                    }
                }
            });
        } else if (giftBeanCallBack != null) {
            giftBeanCallBack.getGiftBeanCallBack(null, "");
        }
    }

    public static void getRedPacketForecast(final Context context, final RedPacketForecastCallback callback) {
        LogInfo.log("NetworkManager", "getRedPacketForecast");
        if (MobileUtil.isNetworkConnected(RedPacketSdkManager.getInstance().getApplicationContext())) {
            AsyncTaskHttp asyncTaskHttp = new AsyncTaskHttp(HTTPURL.getPreviewUrl(), new AsyncTaskCallback() {
                public void getResult(String result) {
                    LogInfo.log("NetworkManager", "getRedPacketForecast + getResult =" + result);
                    if (result != null) {
                        try {
                            final RedPacketBean bean = new RedPacketParser().parserPreview(result);
                            RedPacketSdkManager.getInstance().setForecastBean(bean);
                            if (RedPacketBean.TYPE_FORCAST.equals(bean.type) && !TextUtils.isEmpty(bean.pic1) && !TextUtils.isEmpty(bean.mobilePic)) {
                                LogInfo.log("NetworkManager", "getRedPacketForecast download pic1");
                                NetworkManager.loadImage(bean.pic1, new AsyncTaskImageLoaderCallback() {
                                    public void imageDownloadResult(Bitmap bitmap) {
                                        if (bitmap != null) {
                                            bitmap.recycle();
                                            LogInfo.log("NetworkManager", "getRedPacketForecast download mobilePic");
                                            NetworkManager.loadImage(bean.mobilePic, new AsyncTaskImageLoaderCallback() {
                                                public void imageDownloadResult(Bitmap bitmap) {
                                                    if (bitmap != null) {
                                                        bitmap.recycle();
                                                        LogInfo.log("NetworkManager", "getRedPacketForecast success");
                                                        callback.getCallback(Boolean.valueOf(true), new RedPacketForecastView(context, bean));
                                                        return;
                                                    }
                                                    LogInfo.log("NetworkManager", "getRedPacketForecast download mobilePic error");
                                                    if (callback != null) {
                                                        callback.getCallback(Boolean.valueOf(false), null);
                                                    }
                                                }
                                            });
                                            return;
                                        }
                                        LogInfo.log("NetworkManager", "getRedPacketForecast download pic1 error");
                                        if (callback != null) {
                                            callback.getCallback(Boolean.valueOf(false), null);
                                        }
                                    }
                                });
                                return;
                            }
                            return;
                        } catch (JSONException e) {
                            LogInfo.log("NetworkManager", "getRedPacketForecast json error");
                            e.printStackTrace();
                            if (callback != null) {
                                callback.getCallback(Boolean.valueOf(false), null);
                                return;
                            }
                            return;
                        }
                    }
                    LogInfo.log("NetworkManager", "getRedPacketForecast json null");
                    if (callback != null) {
                        callback.getCallback(Boolean.valueOf(false), null);
                    }
                }
            });
            return;
        }
        LogInfo.log("NetworkManager", "getRedPacketForecast no net");
        if (callback != null) {
            callback.getCallback(Boolean.valueOf(false), null);
        }
    }
}
