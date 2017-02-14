package com.letv.ads.ex.http;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.letv.android.client.task.base.LetvAsyncTask;
import com.letv.plugin.pluginconfig.R;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ApkDownloadAsyncTask extends LetvAsyncTask<Integer, Void> {
    public static int NOTIFICATION_ID = 112233;
    public static ArrayList<ApkDownloadAsyncTask> NOTIFICATION_ID_LIST = new ArrayList();
    private static NotificationManager notificationManager;
    private static HashSet<String> runningSet = new HashSet();
    private String apkNameCn;
    private String appName;
    private CallBack callback;
    private boolean flag = true;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (ApkDownloadAsyncTask.this.flag) {
                        ApkDownloadAsyncTask.this.notification.contentView.setProgressBar(R.id.progress_value, 100, ApkDownloadAsyncTask.this.progress, false);
                        ApkDownloadAsyncTask.this.notification.contentView.setTextViewText(R.id.progress_text, ApkDownloadAsyncTask.this.progress + "%");
                        ApkDownloadAsyncTask.notificationManager.notify(ApkDownloadAsyncTask.this.notificationId, ApkDownloadAsyncTask.this.notification);
                        return;
                    }
                    return;
                case 1:
                    if (ApkDownloadAsyncTask.this.flag) {
                        ApkDownloadAsyncTask.notificationManager.cancel(ApkDownloadAsyncTask.this.notificationId);
                        ApkDownloadAsyncTask.this.notification.icon = R.drawable.notification_icon;
                        ApkDownloadAsyncTask.this.notification.tickerText = ApkDownloadAsyncTask.this.mContext.getString(R.string.update_finish);
                        ApkDownloadAsyncTask.this.notification.contentView.setProgressBar(R.id.progress_value, 100, 100, false);
                        ApkDownloadAsyncTask.this.notification.contentView.setViewVisibility(R.id.app_name, 8);
                        ApkDownloadAsyncTask.this.notification.contentView.setTextViewText(R.id.progress_text, ApkDownloadAsyncTask.this.apkNameCn + " " + ApkDownloadAsyncTask.this.mContext.getString(R.string.update_finish_install));
                        ApkDownloadAsyncTask.this.notification.contentView.setViewVisibility(R.id.progress_value, 8);
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.addFlags(268435456);
                        intent.setDataAndType(Uri.fromFile(new File(ApkDownloadAsyncTask.this.path + "/" + ApkDownloadAsyncTask.this.appName)), "application/vnd.android.package-archive");
                        ApkDownloadAsyncTask.this.mContext.startActivity(intent);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private Context mContext;
    private IDownloadListener mDownloadListener;
    private Notification notification;
    private int notificationId;
    private int oldProgress;
    private String path;
    public int progress;
    public int startPosition = 0;
    private String url;

    public interface CallBack {
        void Failed();

        void Success();
    }

    public interface IDownloadListener {
        void failed(String str);

        void inProgress(String str, int i);

        void success(String str);
    }

    public ApkDownloadAsyncTask(Context context, String url, String apkNameCn, int notificationId, CallBack callback, IDownloadListener downloadListener) {
        this.url = url;
        if (TextUtils.isEmpty(apkNameCn)) {
            apkNameCn = context.getString(R.string.unknown_apk);
        }
        this.apkNameCn = apkNameCn;
        this.callback = callback;
        this.mContext = context;
        this.notificationId = notificationId;
        this.appName = System.currentTimeMillis() + ".apk";
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService("notification");
        }
        this.notification = new Notification();
        this.mDownloadListener = downloadListener;
    }

    protected Void doInBackground() {
        FileOutputStream fileOutputStream;
        Exception e;
        if (Environment.getExternalStorageState().equals("mounted")) {
            this.path = Environment.getExternalStorageDirectory().getPath();
        } else {
            this.path = this.mContext.getDir("updata", 3).getPath();
        }
        try {
            URL uRL = new URL(this.url);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) uRL.openConnection();
                httpURLConnection.setInstanceFollowRedirects(false);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setAllowUserInteraction(true);
                httpURLConnection.connect();
                if (302 == httpURLConnection.getResponseCode()) {
                    this.url = httpURLConnection.getHeaderField(HttpRequest.HEADER_LOCATION);
                    httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                }
                int length = httpURLConnection.getContentLength();
                InputStream inputStream = httpURLConnection.getInputStream();
                File file = new File(this.path + "/" + this.appName);
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
                FileOutputStream outputStream = new FileOutputStream(file);
                try {
                    byte[] buf = new byte[1048576];
                    int curSize = this.startPosition;
                    while (this.flag) {
                        int read = inputStream.read(buf);
                        if (read == -1) {
                            publishProgress(new Integer[]{Integer.valueOf(this.progress)});
                            break;
                        }
                        outputStream.write(buf, 0, read);
                        curSize += read;
                        this.progress = (int) ((((float) curSize) * 100.0f) / ((float) length));
                        if (this.progress - this.oldProgress >= 2) {
                            this.oldProgress = this.progress;
                            publishProgress(new Integer[]{Integer.valueOf(this.progress)});
                        }
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
        if (notificationManager != null) {
            notificationManager.cancel(this.notificationId);
        }
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
            if (this.mDownloadListener != null) {
                this.mDownloadListener.success(this.url);
                return;
            }
            return;
        }
        if (this.callback != null) {
            this.callback.Failed();
            close();
            Toast.makeText(this.mContext, this.mContext.getString(R.string.ad_apk_download_failed), 0).show();
        }
        if (this.mDownloadListener != null) {
            this.mDownloadListener.failed(this.url);
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
        PendingIntent pendingIntent = PendingIntent.getActivity(this.mContext, 0, new Intent(), 0);
        this.notification.icon = R.drawable.notification_icon;
        this.notification.tickerText = this.mContext.getString(R.string.update_asynctask_downloading);
        this.notification.flags = 16;
        RemoteViews remoteViews = new RemoteViews(this.mContext.getPackageName(), R.layout.notification_updata_layout);
        remoteViews.setTextViewText(R.id.app_name, this.apkNameCn);
        remoteViews.setTextViewText(R.id.progress_text, "0%");
        remoteViews.setProgressBar(R.id.progress_value, 100, 0, false);
        this.notification.contentView = remoteViews;
        notificationManager.notify(this.notificationId, this.notification);
    }

    protected void onProgressUpdate(Integer... values) {
        if (!(this.notification == null || this.handler == null)) {
            if (this.mDownloadListener != null) {
                this.mDownloadListener.inProgress(this.url, this.progress);
            }
            this.handler.sendEmptyMessage(0);
        }
        super.onProgressUpdate(values);
    }

    public static boolean apkIsDownloading(String url) {
        String realUrl = getQueryString(url, "u");
        if (runningSet == null || TextUtils.isEmpty(realUrl) || !runningSet.contains(realUrl)) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void downloadApk(android.content.Context r9, java.lang.String r10, java.lang.String r11, com.letv.ads.ex.http.ApkDownloadAsyncTask.IDownloadListener r12) {
        /*
        if (r10 == 0) goto L_0x000a;
    L_0x0002:
        r1 = r10.length();
        if (r1 <= 0) goto L_0x000a;
    L_0x0008:
        if (r9 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r1 = "u";
        r7 = getQueryString(r10, r1);
        r8 = runningSet;
        monitor-enter(r8);
        r1 = runningSet;	 Catch:{ all -> 0x0021 }
        r1 = r1.size();	 Catch:{ all -> 0x0021 }
        r2 = 10;
        if (r1 <= r2) goto L_0x0024;
    L_0x001f:
        monitor-exit(r8);	 Catch:{ all -> 0x0021 }
        goto L_0x000a;
    L_0x0021:
        r1 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0021 }
        throw r1;
    L_0x0024:
        r1 = runningSet;	 Catch:{ all -> 0x0021 }
        r1 = r1.contains(r7);	 Catch:{ all -> 0x0021 }
        if (r1 != 0) goto L_0x005f;
    L_0x002c:
        r1 = runningSet;	 Catch:{ all -> 0x0021 }
        r1.add(r7);	 Catch:{ all -> 0x0021 }
        r0 = new com.letv.ads.ex.http.ApkDownloadAsyncTask;	 Catch:{ all -> 0x0021 }
        r4 = NOTIFICATION_ID;	 Catch:{ all -> 0x0021 }
        r5 = new com.letv.ads.ex.http.ApkDownloadAsyncTask$2;	 Catch:{ all -> 0x0021 }
        r5.<init>(r7);	 Catch:{ all -> 0x0021 }
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r6 = r12;
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ all -> 0x0021 }
        r0.execute();	 Catch:{ all -> 0x0021 }
        r1 = NOTIFICATION_ID_LIST;	 Catch:{ all -> 0x0021 }
        r1.add(r0);	 Catch:{ all -> 0x0021 }
        r1 = NOTIFICATION_ID;	 Catch:{ all -> 0x0021 }
        r1 = r1 + 1;
        NOTIFICATION_ID = r1;	 Catch:{ all -> 0x0021 }
        r1 = com.letv.plugin.pluginconfig.R.string.ad_recommend_apk_download_info;	 Catch:{ all -> 0x0021 }
        r1 = r9.getString(r1);	 Catch:{ all -> 0x0021 }
        r2 = 0;
        r1 = android.widget.Toast.makeText(r9, r1, r2);	 Catch:{ all -> 0x0021 }
        r1.show();	 Catch:{ all -> 0x0021 }
    L_0x005d:
        monitor-exit(r8);	 Catch:{ all -> 0x0021 }
        goto L_0x000a;
    L_0x005f:
        r1 = com.letv.plugin.pluginconfig.R.string.ad_recommend_apk_downloading_info;	 Catch:{ all -> 0x0021 }
        r1 = r9.getString(r1);	 Catch:{ all -> 0x0021 }
        r2 = 0;
        r1 = android.widget.Toast.makeText(r9, r1, r2);	 Catch:{ all -> 0x0021 }
        r1.show();	 Catch:{ all -> 0x0021 }
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.ads.ex.http.ApkDownloadAsyncTask.downloadApk(android.content.Context, java.lang.String, java.lang.String, com.letv.ads.ex.http.ApkDownloadAsyncTask$IDownloadListener):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void downloadApk(android.content.Context r9, java.lang.String r10, java.lang.String r11) {
        /*
        if (r10 == 0) goto L_0x000a;
    L_0x0002:
        r1 = r10.length();
        if (r1 <= 0) goto L_0x000a;
    L_0x0008:
        if (r9 != 0) goto L_0x000b;
    L_0x000a:
        return;
    L_0x000b:
        r1 = "u";
        r7 = getQueryString(r10, r1);
        r8 = runningSet;
        monitor-enter(r8);
        r1 = runningSet;	 Catch:{ all -> 0x0021 }
        r1 = r1.size();	 Catch:{ all -> 0x0021 }
        r2 = 10;
        if (r1 <= r2) goto L_0x0024;
    L_0x001f:
        monitor-exit(r8);	 Catch:{ all -> 0x0021 }
        goto L_0x000a;
    L_0x0021:
        r1 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0021 }
        throw r1;
    L_0x0024:
        r1 = runningSet;	 Catch:{ all -> 0x0021 }
        r1 = r1.contains(r7);	 Catch:{ all -> 0x0021 }
        if (r1 != 0) goto L_0x005f;
    L_0x002c:
        r1 = runningSet;	 Catch:{ all -> 0x0021 }
        r1.add(r7);	 Catch:{ all -> 0x0021 }
        r0 = new com.letv.ads.ex.http.ApkDownloadAsyncTask;	 Catch:{ all -> 0x0021 }
        r4 = NOTIFICATION_ID;	 Catch:{ all -> 0x0021 }
        r5 = new com.letv.ads.ex.http.ApkDownloadAsyncTask$3;	 Catch:{ all -> 0x0021 }
        r5.<init>(r7);	 Catch:{ all -> 0x0021 }
        r6 = 0;
        r1 = r9;
        r2 = r10;
        r3 = r11;
        r0.<init>(r1, r2, r3, r4, r5, r6);	 Catch:{ all -> 0x0021 }
        r0.execute();	 Catch:{ all -> 0x0021 }
        r1 = NOTIFICATION_ID_LIST;	 Catch:{ all -> 0x0021 }
        r1.add(r0);	 Catch:{ all -> 0x0021 }
        r1 = NOTIFICATION_ID;	 Catch:{ all -> 0x0021 }
        r1 = r1 + 1;
        NOTIFICATION_ID = r1;	 Catch:{ all -> 0x0021 }
        r1 = com.letv.plugin.pluginconfig.R.string.ad_recommend_apk_download_info;	 Catch:{ all -> 0x0021 }
        r1 = r9.getString(r1);	 Catch:{ all -> 0x0021 }
        r2 = 0;
        r1 = android.widget.Toast.makeText(r9, r1, r2);	 Catch:{ all -> 0x0021 }
        r1.show();	 Catch:{ all -> 0x0021 }
    L_0x005d:
        monitor-exit(r8);	 Catch:{ all -> 0x0021 }
        goto L_0x000a;
    L_0x005f:
        r1 = com.letv.plugin.pluginconfig.R.string.ad_recommend_apk_downloading_info;	 Catch:{ all -> 0x0021 }
        r1 = r9.getString(r1);	 Catch:{ all -> 0x0021 }
        r2 = 0;
        r1 = android.widget.Toast.makeText(r9, r1, r2);	 Catch:{ all -> 0x0021 }
        r1.show();	 Catch:{ all -> 0x0021 }
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.letv.ads.ex.http.ApkDownloadAsyncTask.downloadApk(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void clearNotifys() {
        Iterator it = NOTIFICATION_ID_LIST.iterator();
        while (it.hasNext()) {
            ((ApkDownloadAsyncTask) it.next()).close();
        }
    }

    private static String getQueryString(String url, String key) {
        try {
            if (!TextUtils.isEmpty(url)) {
                url = Uri.parse(url).getQueryParameter(key);
            }
        } catch (Exception e) {
        }
        return url;
    }
}
