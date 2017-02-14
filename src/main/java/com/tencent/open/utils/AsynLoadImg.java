package com.tencent.open.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.tencent.open.a.f;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: ProGuard */
public class AsynLoadImg {
    private static String c;
    private String a;
    private AsynLoadImgBack b;
    private long d;
    private Handler e;
    private Runnable f = new Runnable(this) {
        final /* synthetic */ AsynLoadImg a;

        {
            this.a = r1;
        }

        public void run() {
            f.a("AsynLoadImg", "saveFileRunnable:");
            String str = "share_qq_" + Util.encrypt(this.a.a) + ".jpg";
            String str2 = AsynLoadImg.c + str;
            File file = new File(str2);
            Message obtainMessage = this.a.e.obtainMessage();
            if (file.exists()) {
                obtainMessage.arg1 = 0;
                obtainMessage.obj = str2;
                f.a("AsynLoadImg", "file exists: time:" + (System.currentTimeMillis() - this.a.d));
            } else {
                boolean saveFile;
                Bitmap bitmap = AsynLoadImg.getbitmap(this.a.a);
                if (bitmap != null) {
                    saveFile = this.a.saveFile(bitmap, str);
                } else {
                    f.a("AsynLoadImg", "saveFileRunnable:get bmp fail---");
                    saveFile = false;
                }
                if (saveFile) {
                    obtainMessage.arg1 = 0;
                    obtainMessage.obj = str2;
                } else {
                    obtainMessage.arg1 = 1;
                }
                f.a("AsynLoadImg", "file not exists: download time:" + (System.currentTimeMillis() - this.a.d));
            }
            this.a.e.sendMessage(obtainMessage);
        }
    };

    public AsynLoadImg(Activity activity) {
        this.e = new Handler(this, activity.getMainLooper()) {
            final /* synthetic */ AsynLoadImg a;

            public void handleMessage(Message message) {
                f.a("AsynLoadImg", "handleMessage:" + message.arg1);
                if (message.arg1 == 0) {
                    this.a.b.saved(message.arg1, (String) message.obj);
                } else {
                    this.a.b.saved(message.arg1, null);
                }
            }
        };
    }

    public void save(String str, AsynLoadImgBack asynLoadImgBack) {
        f.a("AsynLoadImg", "--save---");
        if (str == null || str.equals("")) {
            asynLoadImgBack.saved(1, null);
        } else if (Util.hasSDCard()) {
            c = Environment.getExternalStorageDirectory() + "/tmp/";
            this.d = System.currentTimeMillis();
            this.a = str;
            this.b = asynLoadImgBack;
            new Thread(this.f).start();
        } else {
            asynLoadImgBack.saved(2, null);
        }
    }

    public boolean saveFile(Bitmap bitmap, String str) {
        IOException e;
        OutputStream outputStream;
        Throwable th;
        String str2 = c;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
            str2 = str2 + str;
            f.a("AsynLoadImg", "saveFile:" + str);
            OutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(new File(str2)));
            try {
                bitmap.compress(CompressFormat.JPEG, 80, bufferedOutputStream2);
                bufferedOutputStream2.flush();
                if (bufferedOutputStream2 != null) {
                    try {
                        bufferedOutputStream2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                return true;
            } catch (IOException e3) {
                e2 = e3;
                outputStream = bufferedOutputStream2;
                try {
                    e2.printStackTrace();
                    f.a("AsynLoadImg", "saveFile bmp fail---");
                    if (bufferedOutputStream != null) {
                        return false;
                    }
                    try {
                        bufferedOutputStream.close();
                        return false;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        return false;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e42) {
                            e42.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                outputStream = bufferedOutputStream2;
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        } catch (IOException e5) {
            e2 = e5;
            e2.printStackTrace();
            f.a("AsynLoadImg", "saveFile bmp fail---");
            if (bufferedOutputStream != null) {
                return false;
            }
            bufferedOutputStream.close();
            return false;
        }
    }

    public static Bitmap getbitmap(String str) {
        f.a("AsynLoadImg", "getbitmap:" + str);
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            f.a("AsynLoadImg", "image download finished." + str);
            return decodeStream;
        } catch (IOException e) {
            e.printStackTrace();
            f.a("AsynLoadImg", "getbitmap bmp fail---");
            return null;
        }
    }
}
