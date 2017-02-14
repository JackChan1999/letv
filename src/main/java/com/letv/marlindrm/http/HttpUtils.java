package com.letv.marlindrm.http;

import android.text.TextUtils;
import com.letv.android.client.album.controller.AlbumGestureController;
import com.letv.core.api.UrlConstdata.HOME_RECOMMEND_PARAMETERS;
import com.letv.core.utils.LogInfo;
import com.letv.mobile.lebox.heartbeat.HeartbeatService;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpUtils {
    private static int CONNECT_OUT_TIME = HeartbeatService.AUTO_CONNECT_CHECK_INTERVAL_NOMAL;
    private static final String HTTP_HEADER_FIELD_LOCATION = "Location";
    private static final String OPERATORS_LIST_PARAMS = "&expect=5&format=1";
    private static int READ_OUT_TIME = AlbumGestureController.SCROLL_HALF_TIME;
    private static String TAG = "drmHttpUtil";

    public static String doG3HttpGetRequestToString(String g3Url) {
        if (TextUtils.isEmpty(g3Url)) {
            return null;
        }
        List<String> urlList = requestOperatorsList(g3Url);
        if (urlList == null || urlList.size() == 0) {
            return doHttpGetToString(g3Url);
        }
        for (String url : urlList) {
            String result = doHttpGetToString(url);
            if (!TextUtils.isEmpty(result)) {
                return result;
            }
        }
        return doHttpGetToString(g3Url);
    }

    private static List<String> requestOperatorsList(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String resultString = doHttpGetToString(url + OPERATORS_LIST_PARAMS);
        List<String> list = new ArrayList();
        if (TextUtils.isEmpty(resultString)) {
            list.add(url);
            return list;
        }
        String JSON_NODELIST = "nodelist";
        String JSON_LOCATION = HOME_RECOMMEND_PARAMETERS.LOCATION;
        try {
            JSONArray listArray = new JSONObject(resultString).getJSONArray("nodelist");
            for (int i = 0; i < listArray.length(); i++) {
                String ipUrl = ((JSONObject) listArray.get(i)).getString(HOME_RECOMMEND_PARAMETERS.LOCATION);
                if (!TextUtils.isEmpty(ipUrl)) {
                    list.add(ipUrl);
                }
                LogInfo.log(TAG, "Download url url" + i + " = " + ipUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() != 0) {
            return list;
        }
        list.add(url);
        return list;
    }

    public static String doHttpGetToString(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        InputStream input = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            conn.setConnectTimeout(CONNECT_OUT_TIME);
            conn.setReadTimeout(READ_OUT_TIME);
            input = conn.getInputStream();
            String inputStreamToString = inputStreamToString(input);
            if (conn != null) {
                conn.disconnect();
            }
            if (input == null) {
                return inputStreamToString;
            }
            try {
                input.close();
                return inputStreamToString;
            } catch (IOException e) {
                e.printStackTrace();
                return inputStreamToString;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (conn != null) {
                conn.disconnect();
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            if (conn != null) {
                conn.disconnect();
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e32) {
                    e32.printStackTrace();
                }
            }
        }
    }

    public static String obtain302RedirectedUrl(String sourceUrl) {
        if (!TextUtils.isEmpty(sourceUrl)) {
            LogInfo.log(TAG, "302=============+> " + sourceUrl);
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(sourceUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setInstanceFollowRedirects(false);
                conn.connect();
                if (conn.getResponseCode() == 302) {
                    String location = conn.getHeaderField("Location");
                    if (!TextUtils.isEmpty(location)) {
                        sourceUrl = obtain302RedirectedUrl(location);
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Throwable th) {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return sourceUrl;
    }

    public static String convertHttpToDash(String httpString) {
        String result = httpString;
        if (TextUtils.isEmpty(result)) {
            return null;
        }
        if (result.startsWith("http:")) {
            result = result.replace("http:", "dash:");
        } else if (result.startsWith("https:")) {
            result = result.replace("https:", "dash:");
        }
        return result;
    }

    private static String inputStreamToString(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }
        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        while (true) {
            int n = in.read(b);
            if (n == -1) {
                return out.toString();
            }
            out.append(new String(b, 0, n));
        }
    }
}
