package com.letv.android.client.commonlib.popdialog;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.RemoteViews;
import com.letv.android.client.commonlib.R;
import com.letv.core.BaseApplication;
import com.letv.core.utils.UIsUtils;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

public class ApkDownloadAsyncTask extends AsyncTask<Void, Integer, Void> {
    public static int NOTIFICATION_ID = 112233;
    private static NotificationManager notificationManager;
    private static HashSet<String> runningSet = new HashSet();
    private Activity activity;
    private String apkNameCn;
    private String appName;
    private CallBack callback;
    private boolean flag = true;
    private Handler handler = new 1(this);
    private Notification notification;
    private int notificationId;
    private int oldProgress;
    private String path;
    public int progress;
    public int startPosition = 0;
    private String url;

    public ApkDownloadAsyncTask(Activity activity, String url, String apkNameCn, int notificationId, CallBack callback) {
        this.url = url;
        if (TextUtils.isEmpty(apkNameCn)) {
            apkNameCn = activity.getString(R.string.unknown_apk);
        }
        this.apkNameCn = apkNameCn;
        this.callback = callback;
        this.activity = activity;
        this.notificationId = notificationId;
        this.appName = System.currentTimeMillis() + ".apk";
        if (notificationManager == null) {
            notificationManager = (NotificationManager) activity.getSystemService("notification");
        }
        this.notification = new Notification();
    }

    protected void onProgressUpdate(Integer... values) {
        if (this.notification != null && this.progress > this.oldProgress + 2) {
            this.oldProgress = this.progress;
            this.handler.sendEmptyMessage(0);
        }
        super.onProgressUpdate(values);
    }

    protected Void doInBackground(Void... params) {
        Exception e;
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.path = Environment.getExternalStorageDirectory().getPath();
        } else {
            this.path = BaseApplication.getInstance().getDir("updata", 3).getPath();
        }
        try {
            URL uRL = new URL(this.url);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) uRL.openConnection();
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setAllowUserInteraction(true);
                httpURLConnection.connect();
                if (302 == httpURLConnection.getResponseCode()) {
                    this.url = httpURLConnection.getHeaderField(HttpRequest.HEADER_LOCATION);
                    httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                }
                int length = httpURLConnection.getContentLength();
                InputStream inputStream = httpURLConnection.getInputStream();
                File file = new File(this.path + "/" + this.appName);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                FileOutputStream fileOutputStream;
                try {
                    byte[] buf = new byte[1048576];
                    int curSize = this.startPosition;
                    while (this.flag) {
                        publishProgress(new Integer[]{Integer.valueOf(this.progress)});
                        int read = inputStream.read(buf);
                        if (read != -1) {
                            outputStream.write(buf, 0, read);
                            curSize += read;
                            this.progress = (int) ((((float) curSize) * 100.0f) / ((float) length));
                            if (curSize == length) {
                                this.progress = 100;
                                publishProgress(new Integer[]{Integer.valueOf(this.progress)});
                                break;
                            }
                        }
                        publishProgress(new Integer[]{Integer.valueOf(this.progress)});
                        break;
                    }
                    inputStream.close();
                    outputStream.close();
                    httpURLConnection.disconnect();
                    fileOutputStream = outputStream;
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = outputStream;
                }
            } catch (Exception e3) {
                e = e3;
                URL url = uRL;
                e.printStackTrace();
                return null;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void close() {
        this.flag = false;
    }

    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (this.progress == 100) {
            if (this.callback != null) {
                this.callback.Success();
            }
            try {
                new ProcessBuilder(new String[]{"chmod", "777", this.path + "/" + this.appName}).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.handler.sendEmptyMessage(1);
        } else if (this.callback != null) {
            this.callback.Failed();
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
        PendingIntent pendingIntent = PendingIntent.getActivity(this.activity, 0, new Intent(), 0);
        this.notification.icon = R.anim.notification_download;
        this.notification.tickerText = BaseApplication.getInstance().getString(R.string.update_asynctask_downloading);
        this.notification.flags = 16;
        RemoteViews remoteViews = new RemoteViews(this.activity.getPackageName(), R.layout.notification_updata_layout);
        remoteViews.setTextViewText(R.id.app_name, this.activity.getString(R.string.unknown_apk) + this.apkNameCn);
        remoteViews.setTextViewText(R.id.progress_text, "0%");
        remoteViews.setProgressBar(R.id.progress_value, 100, 0, false);
        this.notification.contentView = remoteViews;
        this.notification.contentIntent = pendingIntent;
        notificationManager.notify(this.notificationId, this.notification);
    }

    public static void downloadApk(Activity activity, String url, String apkNameCn) {
        if (url != null && url.length() > 0 && activity != null) {
            synchronized (runningSet) {
                if (runningSet.contains(url)) {
                    UIsUtils.showToast(R.string.recommend_apk_downloading_info);
                } else {
                    runningSet.add(url);
                    new ApkDownloadAsyncTask(activity, url, apkNameCn, NOTIFICATION_ID, new 2(url, activity)).execute(new Void[0]);
                    NOTIFICATION_ID++;
                    UIsUtils.showToast(R.string.recommend_apk_download_info);
                }
            }
        }
    }
}
