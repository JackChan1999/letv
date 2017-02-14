package com.letv.component.player.http.request;

import android.os.AsyncTask;
import android.util.Log;
import com.letv.component.player.http.HttpServerConfig;
import com.letv.component.player.utils.Cryptos;
import com.letv.component.player.utils.MD5;
import com.letv.lepaysdk.model.TradeInfo;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class LetvUpLogTask extends AsyncTask<String, Integer, String> {
    private static final String CHARSET = "utf-8";
    private static final String TAG = "LetvUpLogTask";
    private String fbid;
    private FeedCallBack feedCallBack;
    private List<File> files;
    private File zipFile;

    public interface FeedCallBack {
        void response(String str);
    }

    public LetvUpLogTask(List<File> files, File zipFile, FeedCallBack feedCallBack, String fbid) {
        this.zipFile = zipFile;
        this.fbid = fbid;
        this.files = files;
        this.feedCallBack = feedCallBack;
    }

    protected String doInBackground(String... params) {
        Exception e;
        Throwable th;
        String result = null;
        String filename = "logFile.zip";
        String PREFIX = "--";
        String LINE_END = "\r\n";
        String BOUNDARY = UUID.randomUUID().toString();
        String CONTENT_TYPE = "multipart/form-data";
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        InputStream is = null;
        try {
            URL url = new URL(String.format(HttpServerConfig.getFeedbackUploadlogUrl(), new Object[]{this.fbid}));
            URL url2;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                String ts = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                StringBuilder rsb = new StringBuilder();
                for (int i = 0; i < 10; i++) {
                    if (i != 10) {
                        rsb.append((int) (10.0d * Math.random()));
                    }
                }
                String random = rsb.toString();
                StringBuffer stringBuffer = new StringBuffer(random.substring(0, 5));
                stringBuffer.append(ts.substring(7));
                stringBuffer.append(Cryptos.key);
                stringBuffer.append(ts.substring(0, 7));
                stringBuffer.append(random.substring(5));
                String sign = MD5.toMd5(stringBuffer.toString());
                conn.addRequestProperty("ts", ts);
                conn.addRequestProperty("random", random);
                conn.addRequestProperty(TradeInfo.SIGN, sign);
                conn.setRequestProperty("Charset", CHARSET);
                conn.setRequestProperty("connection", "keep-alive");
                conn.setRequestProperty(HttpRequest.HEADER_CONTENT_TYPE, new StringBuilder(String.valueOf(CONTENT_TYPE)).append(";boundary=").append(BOUNDARY).toString());
                DataOutputStream dos2 = new DataOutputStream(conn.getOutputStream());
                try {
                    StringBuffer sb = new StringBuffer();
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"logFile\"; filename=\"" + filename + "\"" + LINE_END);
                    sb.append("Content-Type: application/octet-stream; charset=utf-8" + LINE_END);
                    sb.append(LINE_END);
                    dos2.write(sb.toString().getBytes());
                    if (this.zipFile != null) {
                        InputStream fileInputStream = new FileInputStream(this.zipFile);
                        try {
                            byte[] bytes = new byte[1048576];
                            while (true) {
                                int len = fileInputStream.read(bytes);
                                if (len == -1) {
                                    break;
                                }
                                dos2.write(bytes, 0, len);
                            }
                            fileInputStream.close();
                            is = fileInputStream;
                        } catch (Exception e2) {
                            e = e2;
                            is = fileInputStream;
                            dos = dos2;
                            url2 = url;
                        } catch (Throwable th2) {
                            th = th2;
                            is = fileInputStream;
                            dos = dos2;
                            url2 = url;
                        }
                    }
                    dos2.write(LINE_END.getBytes());
                    dos2.write(new StringBuilder(String.valueOf(PREFIX)).append(BOUNDARY).append(PREFIX).append(LINE_END).toString().getBytes());
                    dos2.flush();
                    int res = conn.getResponseCode();
                    Log.i(TAG, "response code:" + res);
                    if (res == 200) {
                        Log.i(TAG, "request success");
                        InputStream input = conn.getInputStream();
                        StringBuffer sb1 = new StringBuffer();
                        while (true) {
                            int ss = input.read();
                            if (ss == -1) {
                                break;
                            }
                            sb1.append((char) ss);
                        }
                        result = new String(sb1.toString().getBytes("iso8859-1"), CHARSET);
                    } else {
                        Log.i(TAG, "request error");
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (dos2 != null) {
                        dos2.close();
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                    dos = dos2;
                    url2 = url;
                    return result;
                } catch (Exception e3) {
                    e = e3;
                    dos = dos2;
                    url2 = url;
                } catch (Throwable th3) {
                    th = th3;
                    dos = dos2;
                    url2 = url;
                }
            } catch (Exception e4) {
                e = e4;
                url2 = url;
            } catch (Throwable th4) {
                th = th4;
                url2 = url;
            }
        } catch (Exception e5) {
            e = e5;
            try {
                e.printStackTrace();
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception ex2) {
                        ex2.printStackTrace();
                        return null;
                    }
                }
                if (dos != null) {
                    dos.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
                return null;
            } catch (Throwable th5) {
                th = th5;
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception ex22) {
                        ex22.printStackTrace();
                        throw th;
                    }
                }
                if (dos != null) {
                    dos.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
                throw th;
            }
        }
    }

    protected void onPostExecute(String result) {
        Log.i(TAG, "result=" + result);
        if (result != null && this.files != null && this.files.size() > 0) {
            for (File file : this.files) {
                file.delete();
            }
            if (this.zipFile != null && this.zipFile.exists()) {
                this.zipFile.delete();
            }
        }
    }
}
