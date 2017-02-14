package com.letv.redpacketsdk.net;

import android.os.AsyncTask;
import android.text.TextUtils;
import com.letv.core.contentprovider.UserInfoDb;
import com.letv.redpacketsdk.callback.AsyncTaskCallback;
import com.letv.redpacketsdk.utils.LogInfo;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskHttp extends AsyncTask<String, Integer, String> {
    private static final int TIME_OUT = 3000;
    private AsyncTaskCallback callback;
    private String mToken;
    private String result;
    private String url;

    public AsyncTaskHttp(String url, AsyncTaskCallback callback) {
        this.url = url;
        this.callback = callback;
        execute(new String[]{this.url});
        LogInfo.log("AsyncTaskHttp", "AsyncTaskHttp+url=" + url);
        LogInfo.log("AsyncTaskHttp", "AsyncTaskHttp+" + toString());
    }

    public AsyncTaskHttp(String url, String token, AsyncTaskCallback callback) {
        this.url = url;
        this.callback = callback;
        this.mToken = token;
        execute(new String[]{this.url});
        LogInfo.log("AsyncTaskHttp", "AsyncTaskHttp+url=" + url);
        LogInfo.log("AsyncTaskHttp", "AsyncTaskHttp+" + toString());
    }

    protected String doInBackground(String... params) {
        Exception e;
        LogInfo.log("AsyncTaskHttp", "doInBackground+" + toString());
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(params[0]);
            URL url2;
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setUseCaches(false);
                urlConn.setConnectTimeout(3000);
                urlConn.setReadTimeout(3000);
                urlConn.setDoInput(true);
                if (!TextUtils.isEmpty(this.mToken)) {
                    urlConn.setRequestProperty(UserInfoDb.TOKEN, this.mToken);
                }
                urlConn.connect();
                if ((urlConn.getResponseCode() >= 200 && urlConn.getResponseCode() < 300) || this.callback == null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                    StringBuilder stringbuilder = new StringBuilder();
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        stringbuilder.append(line);
                    }
                    this.result = stringbuilder.toString();
                }
                urlConn.disconnect();
                url2 = url;
            } catch (Exception e2) {
                e = e2;
                url2 = url;
                LogInfo.log("AsyncTaskHttp", "HttpURLConnection error=" + e.getMessage());
                if (urlConn != null) {
                    urlConn.disconnect();
                }
                return this.result;
            }
        } catch (Exception e3) {
            e = e3;
            LogInfo.log("AsyncTaskHttp", "HttpURLConnection error=" + e.getMessage());
            if (urlConn != null) {
                urlConn.disconnect();
            }
            return this.result;
        }
        return this.result;
    }

    protected void onPostExecute(String result) {
        LogInfo.log("AsyncTaskHttp", "onPostExecute" + toString());
        if (this.callback != null) {
            this.callback.getResult(this.result);
        }
    }

    protected void onCancelled() {
        LogInfo.log("AsyncTaskHttp", "onCancelled()");
    }

    public void cleanCallback() {
        this.callback = null;
    }
}
