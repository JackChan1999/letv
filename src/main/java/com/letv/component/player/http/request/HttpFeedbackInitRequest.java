package com.letv.component.player.http.request;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.letv.component.player.core.AppInfo;
import com.letv.component.player.core.LetvMediaPlayerManager;
import com.letv.component.player.http.HttpServerConfig;
import com.letv.component.player.utils.CpuInfosUtils;
import com.letv.component.player.utils.Cryptos;
import com.letv.component.player.utils.MD5;
import com.letv.component.player.utils.MemoryInfoUtil;
import com.letv.component.player.utils.Tools;
import com.letv.lepaysdk.model.TradeInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class HttpFeedbackInitRequest extends AsyncTask<String, Integer, String> {
    private String Tag = "HttpFeedbackRequest";
    private getResultCallback getResultCallBack;
    private Context mContext;

    public interface getResultCallback {
        void response(String str);
    }

    public HttpFeedbackInitRequest(Context context, getResultCallback getResultCallBack) {
        this.getResultCallBack = getResultCallBack;
        this.mContext = context;
    }

    protected String doInBackground(String... params) {
        MalformedURLException e;
        Throwable th;
        IOException e2;
        String str = null;
        HttpURLConnection conn = null;
        OutputStream output = null;
        Object bundle = new HashMap();
        bundle.put("upType", "2");
        bundle.put("appId", AppInfo.appID);
        bundle.put("appVersion", AppInfo.appVer);
        bundle.put("devOsVersion", Tools.getOSVersionName());
        bundle.put("devId", Tools.generateDeviceId(this.mContext));
        bundle.put("devVendor", Tools.getBrandName());
        bundle.put("devModel", Tools.getDeviceName());
        bundle.put("devMac", Tools.getMacAddress(this.mContext));
        bundle.put("devCpu", CpuInfosUtils.getCpuInfo() + " NumCores:" + CpuInfosUtils.getNumCores());
        bundle.put("devRam", new StringBuilder(String.valueOf(MemoryInfoUtil.getMemTotal() / 1024)).toString());
        bundle.put("devRom", new StringBuilder(String.valueOf(MemoryInfoUtil.getTotalInternalMemorySize() / 1048576)).toString());
        bundle.put("devSdcard", new StringBuilder(String.valueOf(MemoryInfoUtil.getSDCardMemory() / 1048576)).toString());
        bundle.put("resolution", Tools.getResolution(this.mContext));
        bundle.put("snNo", Tools.getIMSI(this.mContext));
        bundle.put("imeiNo", Tools.getIMEI(this.mContext));
        bundle.put("netType", Tools.getNetTypeName(this.mContext));
        bundle.put("sdkVersion", "player_" + LetvMediaPlayerManager.getInstance().getSdkVersion());
        String body = new Gson().toJson(bundle);
        try {
            conn = (HttpURLConnection) new URL(HttpServerConfig.getFeedbackInitUrl()).openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                if (i != 10) {
                    sb.append((int) (10.0d * Math.random()));
                }
            }
            String random = sb.toString();
            StringBuffer md5 = new StringBuffer(random.substring(0, 5));
            md5.append(ts.substring(7));
            md5.append(Cryptos.key);
            md5.append(ts.substring(0, 7));
            md5.append(random.substring(5));
            String sign = MD5.toMd5(md5.toString());
            conn.addRequestProperty("ts", ts);
            conn.addRequestProperty("random", random);
            conn.addRequestProperty(TradeInfo.SIGN, sign);
            conn.setRequestProperty("Charset", "utf-8");
            conn.setRequestProperty("connection", "keep-alive");
            output = conn.getOutputStream();
            output.write(body.getBytes("utf-8"));
            conn.connect();
            if (conn.getResponseCode() == 200) {
                Log.i("GetRequestUrl", "request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                while (true) {
                    int ss = input.read();
                    if (ss == -1) {
                        break;
                    }
                    sb1.append((char) ss);
                }
                String str2 = new String(sb1.toString().getBytes("iso8859-1"), "utf-8");
                try {
                    input.close();
                    str = str2;
                } catch (MalformedURLException e3) {
                    e = e3;
                    str = str2;
                    try {
                        e.printStackTrace();
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException e4) {
                            }
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                        return str;
                    } catch (Throwable th2) {
                        th = th2;
                        if (output != null) {
                            try {
                                output.close();
                            } catch (IOException e5) {
                            }
                        }
                        if (conn != null) {
                            conn.disconnect();
                        }
                        throw th;
                    }
                } catch (IOException e6) {
                    e2 = e6;
                    str = str2;
                    e2.printStackTrace();
                    if (output != null) {
                        try {
                            output.close();
                        } catch (IOException e7) {
                        }
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                    return str;
                } catch (Throwable th3) {
                    th = th3;
                    str = str2;
                    if (output != null) {
                        output.close();
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                    throw th;
                }
            }
            Log.i("GetRequestUrl", "request faild");
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e8) {
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        } catch (MalformedURLException e9) {
            e = e9;
        } catch (IOException e10) {
            e2 = e10;
        }
        return str;
    }

    protected void onPostExecute(String result) {
        this.getResultCallBack.response(result);
        Log.i(this.Tag, "urlresult=" + result);
        super.onPostExecute(result);
    }
}
