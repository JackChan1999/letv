package com.letv.lepaysdk.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.letv.core.constant.PlayConstant;
import com.letv.lepaysdk.TestGetLocalAddress;
import com.letv.lepaysdk.model.PayCard;
import com.letv.lepaysdk.model.Result;
import com.letv.lepaysdk.model.Result.ResultConstant;
import com.letv.lepaysdk.model.TradeInfo;
import com.letv.lepaysdk.network.CommonHttpClient.HttpStatusException;
import com.letv.lepaysdk.utils.LOG;
import com.letv.lepaysdk.utils.NetworkUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.cybergarage.upnp.std.av.server.object.SearchCriteria;
import org.json.JSONException;
import org.json.JSONObject;

public class NetworkManager {
    private static final String SHOW_CASHIER_API = "lepay.app.api.show.cashier";
    private static NetworkManager sInstance;
    private Context mContext;
    private CommonHttpClient mHttpClient = CommonHttpClient.createInstance();
    private ImageCache mImageCache;
    private String sign;

    public String getSign() {
        LOG.logI("getSign: " + this.sign);
        return this.sign;
    }

    public void setSign(String sign) {
        LOG.logI("setSign: " + sign);
        this.sign = sign;
    }

    private NetworkManager(Context context) {
        this.mContext = context;
        this.mImageCache = ImageCache.getInstance(context);
    }

    public static NetworkManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NetworkManager(context.getApplicationContext());
        }
        return sInstance;
    }

    public Message requestTradeToken(String info) throws LePaySDKException {
        Message msg = new Message();
        LePaySDKException sdkException = new LePaySDKException();
        String url = createUrl("/app/showcashier");
        StringBuffer params = new StringBuffer();
        params.append(info);
        params.append("&").append(NetworkUtils.getUrlSuffix(this.mContext));
        LOG.logI(new StringBuilder(String.valueOf(url)).append("?").append(params.toString()).toString());
        try {
            String response = this.mHttpClient.commonPost(this.mContext, url, params.toString(), "utf-8");
            Result result = Result.fromJsonObject(new JSONObject(response));
            if (result.code == 0) {
                msg.arg1 = 0;
                msg.obj = TradeInfo.fromJsonObject(new JSONObject(result.data));
            } else {
                msg.arg1 = 1;
                msg.getData().putString(ResultConstant.errormsg, result.msg);
            }
            LOG.logI("requestTradeToken response:" + response);
        } catch (HttpStatusException e) {
            msg.arg1 = -1;
            sdkException.setHttpStatusExceptionState(e);
            e.printStackTrace();
            throw sdkException;
        } catch (IOException e2) {
            msg.arg1 = -1;
            sdkException.setExceptionState(4);
            e2.printStackTrace();
            throw sdkException;
        } catch (JSONException e3) {
            msg.arg1 = -1;
            sdkException.setExceptionState(5);
            e3.printStackTrace();
            throw sdkException;
        } catch (Exception e4) {
            msg.arg1 = -1;
        }
        return msg;
    }

    public Message createPay(String channelId, String lepayOrderNo, String merchantBusinessId, String verifycode, String sendBy) throws LePaySDKException {
        List<NameValuePair> paramslist = new LinkedList();
        paramslist.add(new BasicNameValuePair("lepay_order_no", lepayOrderNo));
        paramslist.add(new BasicNameValuePair("merchant_business_id", merchantBusinessId));
        paramslist.add(new BasicNameValuePair("verifycode", verifycode));
        paramslist.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, SHOW_CASHIER_API));
        String paramUrl = createSign(paramslist);
        StringBuffer url = new StringBuffer();
        url.append("/pay/app/");
        url.append(channelId);
        String sUrl = createUrl(url.toString());
        LOG.logI(new StringBuilder(String.valueOf(sUrl)).append("?").append(paramUrl).toString());
        return getDataAndJson(sUrl, paramUrl);
    }

    public Message createGetVerifyCode(PayCard payCard) throws LePaySDKException {
        String paramUrl = createSign(createPayCardValuePairs(payCard));
        String url = createUrl("/api/pay/getverifycode");
        LOG.logI(new StringBuilder(String.valueOf(url)).append("?").append(paramUrl).toString());
        return getDataAndJson(url, paramUrl);
    }

    public Message createQueryOrderState(String lepayOrderNo, String merchantBusinessId, String lepayPaymentNo) throws LePaySDKException {
        List<NameValuePair> paramslist = new LinkedList();
        paramslist.add(new BasicNameValuePair("lepay_order_no", lepayOrderNo));
        paramslist.add(new BasicNameValuePair("merchant_business_id", merchantBusinessId));
        paramslist.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, SHOW_CASHIER_API));
        String paramUrl = createSign(paramslist);
        String url = createUrl("/pay/app/querystat");
        LOG.logI(new StringBuilder(String.valueOf(url)).append("?").append(paramUrl).toString());
        return getDataAndJson(url, paramUrl);
    }

    public Message createQueryVerifyCode(String lepay_order_no, String merchant_business_id, String phone, String verifycode, String sendBy) throws LePaySDKException {
        List<NameValuePair> paramslist = new LinkedList();
        paramslist.add(new BasicNameValuePair("lepay_order_no", lepay_order_no));
        paramslist.add(new BasicNameValuePair("merchant_business_id", merchant_business_id));
        paramslist.add(new BasicNameValuePair("verifycode", verifycode));
        paramslist.add(new BasicNameValuePair("phone", phone));
        paramslist.add(new BasicNameValuePair("sendby", sendBy));
        paramslist.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, SHOW_CASHIER_API));
        String paramUrl = createSign(paramslist);
        String url = createUrl("/api/pay/verifycode");
        LOG.logI(new StringBuilder(String.valueOf(url)).append("?").append(paramUrl).toString());
        return getDataAndJson(url, paramUrl);
    }

    public Message createGetcardinfo(String businessid, String cardNo) throws LePaySDKException {
        List<NameValuePair> paramslist = new LinkedList();
        paramslist.add(new BasicNameValuePair("businessid", businessid));
        paramslist.add(new BasicNameValuePair("cardNo", cardNo));
        paramslist.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, SHOW_CASHIER_API));
        String paramUrl = createSign(paramslist);
        String url = createUrl("/api/getcardinfo");
        LOG.logI(new StringBuilder(String.valueOf(url)).append("?").append(paramUrl).toString());
        return getDataAndJson(url, paramUrl);
    }

    public Message createGetSupportBanklist(String card_type) throws LePaySDKException {
        List<NameValuePair> paramslist = new LinkedList();
        paramslist.add(new BasicNameValuePair("card_type", card_type));
        paramslist.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, SHOW_CASHIER_API));
        String paramUrl = createSign(paramslist);
        String url = createUrl("/api/getsupportbanklist");
        LOG.logI(new StringBuilder(String.valueOf(url)).append("?").append(paramUrl).toString());
        return getDataAndJson(url, paramUrl);
    }

    public Bitmap getBitmap(String url, int width, int height) {
        if (url == null && "".equals(url)) {
            return null;
        }
        Bitmap bmp = this.mImageCache.getBitmapFromMemCache(url, width, height);
        if (bmp == null) {
            bmp = this.mImageCache.getBitmapFromDiskCache(url, width, height);
        }
        if (bmp != null) {
            this.mImageCache.addBitmapToMemCache(url, bmp);
            return bmp;
        }
        bmp = this.mImageCache.getBitmapFromNetwork(url, width, height);
        this.mImageCache.flush();
        return bmp;
    }

    public Message getDataAndJson(String url, String paramUrl) throws LePaySDKException {
        Message msg = new Message();
        LePaySDKException sdkException = new LePaySDKException();
        try {
            String response = this.mHttpClient.commonPost(this.mContext, url, paramUrl, "utf-8");
            LOG.logI(response);
            Result result = Result.fromJsonObject(new JSONObject(response));
            if (result.code == 0) {
                msg.arg1 = 0;
                msg.obj = new JSONObject(result.data);
            } else {
                msg.arg1 = 1;
                msg.getData().putInt(ResultConstant.errorcode, result.errorcode);
                msg.getData().putString(ResultConstant.errormsg, result.msg);
            }
        } catch (HttpStatusException e) {
            msg.arg1 = -1;
            sdkException.setHttpStatusExceptionState(e);
            e.printStackTrace();
        } catch (IOException e2) {
            msg.arg1 = -1;
            sdkException.setExceptionState(4);
            e2.printStackTrace();
        } catch (JSONException jsonEx) {
            msg.arg1 = -1;
            sdkException.setExceptionState(5);
            jsonEx.printStackTrace();
        } catch (Exception e3) {
            msg.arg1 = -1;
        }
        return msg;
    }

    private String createSign(List<NameValuePair> paramslist) {
        StringBuffer paramUrl = new StringBuffer();
        paramUrl.append(NetworkUtils.getAppendUrlParams(paramslist));
        paramUrl.append("&").append(TradeInfo.SIGN).append(SearchCriteria.EQ).append(getSign());
        return paramUrl.toString();
    }

    private String createUrl(String paramUrl) {
        StringBuffer url = new StringBuffer(TestGetLocalAddress.getLocalAddress(this.mContext));
        url.append(paramUrl);
        return url.toString();
    }

    private List<NameValuePair> createPayCardValuePairs(PayCard payCard) {
        List<NameValuePair> paramlist = new ArrayList();
        paramlist.add(new BasicNameValuePair("bankcode", payCard.getBankcode()));
        paramlist.add(new BasicNameValuePair("bankname", payCard.getBankname()));
        paramlist.add(new BasicNameValuePair("cardno", payCard.getCardno()));
        paramlist.add(new BasicNameValuePair("cvv2", payCard.getCvv2()));
        paramlist.add(new BasicNameValuePair("lepay_order_no", payCard.getLepay_order_no()));
        paramlist.add(new BasicNameValuePair("merchant_business_id", payCard.getMerchant_business_id()));
        paramlist.add(new BasicNameValuePair("phone", payCard.getPhone()));
        paramlist.add(new BasicNameValuePair("validdate", payCard.getValiddate()));
        paramlist.add(new BasicNameValuePair("verifycode", payCard.getVerifycode()));
        paramlist.add(new BasicNameValuePair("idcard", payCard.getIdcard()));
        paramlist.add(new BasicNameValuePair("owner", payCard.getOwner()));
        paramlist.add(new BasicNameValuePair(PlayConstant.LIVE_CHANNEL_ID, payCard.getChannel_id()));
        paramlist.add(new BasicNameValuePair("sendby", payCard.getSendby()));
        paramlist.add(new BasicNameValuePair("bind_id", payCard.getBind_id()));
        paramlist.add(new BasicNameValuePair("change", payCard.getChange()));
        paramlist.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, SHOW_CASHIER_API));
        LOG.logI("paramlist size: " + paramlist.size());
        List<NameValuePair> newparamlist = new ArrayList();
        int count = paramlist.size();
        for (int i = 0; i < count; i++) {
            String value = ((NameValuePair) paramlist.get(i)).getValue();
            if (!TextUtils.isEmpty(value)) {
                newparamlist.add(new BasicNameValuePair(((NameValuePair) paramlist.get(i)).getName(), value));
            }
        }
        LOG.logI("newparamlist size: " + newparamlist.size());
        return newparamlist;
    }
}
