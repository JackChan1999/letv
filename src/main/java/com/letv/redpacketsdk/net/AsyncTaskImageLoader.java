package com.letv.redpacketsdk.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import com.letv.redpacketsdk.callback.AsyncTaskImageLoaderCallback;
import com.letv.redpacketsdk.utils.FileUtil;
import com.letv.redpacketsdk.utils.LogInfo;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskImageLoader extends AsyncTask<String, Void, Bitmap> {
    private AsyncTaskImageLoaderCallback asyncTaskImageLoaderCallback;
    private Context context;

    public AsyncTaskImageLoader(Context context, AsyncTaskImageLoaderCallback asyncTaskImageLoaderCallback, String url) {
        this.context = context;
        this.asyncTaskImageLoaderCallback = asyncTaskImageLoaderCallback;
        execute(new String[]{url});
    }

    protected Bitmap doInBackground(String... params) {
        URL url;
        Exception e;
        Bitmap bm = null;
        HttpURLConnection httpConn = null;
        try {
            URL url2 = new URL(params[0]);
            try {
                httpConn = (HttpURLConnection) url2.openConnection();
                httpConn.setDoInput(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                if (httpConn.getResponseCode() >= 200 && httpConn.getResponseCode() < 300) {
                    bm = BitmapFactory.decodeStream(httpConn.getInputStream());
                }
                if (bm != null) {
                    LogInfo.log("AsyncTaskImageLoader", "saveBitmapToSDCardPrivateCacheDir=" + saveBitmapToCache(bm, FileUtil.getDownloadFileName(params[0]), this.context));
                }
                httpConn.disconnect();
                url = url2;
                return bm;
            } catch (Exception e2) {
                e = e2;
                url = url2;
                e.printStackTrace();
                LogInfo.log("AsyncTaskImageLoader", "saveBitmapToSDCardPrivateCacheDir=Exception");
                if (httpConn != null) {
                    httpConn.disconnect();
                }
                if (bm != null) {
                    return bm;
                }
                bm.recycle();
                return null;
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            LogInfo.log("AsyncTaskImageLoader", "saveBitmapToSDCardPrivateCacheDir=Exception");
            if (httpConn != null) {
                httpConn.disconnect();
            }
            if (bm != null) {
                return bm;
            }
            bm.recycle();
            return null;
        }
    }

    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        LogInfo.log("AsyncTaskImageLoader", "onPostExecute");
        if (this.asyncTaskImageLoaderCallback != null && result != null) {
            this.asyncTaskImageLoaderCallback.imageDownloadResult(result);
        }
    }

    public boolean saveBitmapToCache(Bitmap bitmap, String fileName, Context context) {
        Exception e;
        Throwable th;
        BufferedOutputStream bufferedOutputStream = null;
        File file = new File(FileUtil.getCacheDir(context));
        if (file.exists()) {
            file.mkdirs();
        }
        LogInfo.log("AsyncTaskImageLoader", "saveBitmapToSDCardPrivateCacheDir+filepath=" + file.getPath());
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
            if (fileName != null) {
                try {
                    if (fileName.contains(".png") || fileName.contains(".PNG")) {
                        bitmap.compress(CompressFormat.PNG, 100, bos);
                        bos.flush();
                        if (bos == null) {
                            try {
                                bos.close();
                                bufferedOutputStream = bos;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                bufferedOutputStream = bos;
                            }
                        }
                        return true;
                    }
                } catch (Exception e3) {
                    e = e3;
                    bufferedOutputStream = bos;
                    try {
                        e.printStackTrace();
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedOutputStream = bos;
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    throw th;
                }
            }
            bitmap.compress(CompressFormat.JPEG, 100, bos);
            bos.flush();
            if (bos == null) {
            } else {
                bos.close();
                bufferedOutputStream = bos;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            return true;
        }
        return true;
    }

    public void cleanCallback() {
        this.asyncTaskImageLoaderCallback = null;
    }
}
